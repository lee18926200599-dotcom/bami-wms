package com.common.excle.export;


import com.common.excle.ExceptionUtil;
import com.common.excle.TriFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.function.BiFunction;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public class AsyncExcelExporter {
    ExecutorService executor;

    public AsyncExcelExporter(ExecutorService executor) {
        this.executor = executor;
    }

    @Deprecated
    public void exportData(ExportHandler handler, ExportSupport support, DataExportParam param,
                           ExportContext ctx) {

        BiFunction<Integer, Integer, ExportPage> dataFunction = (start, limit) -> {
            support.onExport(ctx);
            try {
                handler.beforePerPage(ctx, param);
                ExportPage exportPage = handler.exportData(start, limit, param);
                if (exportPage == null) {
                    throw new RuntimeException("导出数据为空");
                }
                if (CollectionUtils.isEmpty(exportPage.getRecords())) {
                    return exportPage;
                }
                ctx.record(exportPage.getRecords().size());
                support.onWrite(exportPage.getRecords(), ctx);
                handler.afterPerPage(exportPage.getRecords(), ctx, param);
                return exportPage;
            } catch (Exception e) {
                log.error("导出过程发生异常");
                if (e instanceof ExportException) {
                    throw (ExportException) e;
                } else {
                    throw ExceptionUtil.wrap2Runtime(e);
                }
            }
        };
        executor.execute(() -> {
            try (FileOutputStream fos = new FileOutputStream(ctx.getFile());
                 ZipOutputStream zos = new ZipOutputStream(fos)){
                handler.init(ctx, param);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ctx.setOutputStream(baos);
                int cursor = 1;
                int fileIndex = 1;
                int size=0;

                Object reqParam = param.getReqParam();
                Field field = null;
                try {
                    field = reqParam.getClass().getSuperclass().getDeclaredField("exportType");
                }catch (NoSuchFieldException exception){
                    field = reqParam.getClass().getDeclaredField("exportType");
                }
                // 确保字段是可访问的
                field.setAccessible(true);
                // 获取字段值
                Enum fieldValue = (Enum)field.get(reqParam);

                if ("CURRENT".equals(fieldValue.name())){
                    Field pageSizeField = null;
                    try {
                        pageSizeField = reqParam.getClass().getSuperclass().getSuperclass().getDeclaredField("pageSize");
                    }catch (NoSuchFieldException exception){
                        pageSizeField = reqParam.getClass().getSuperclass().getDeclaredField("pageSize");
                    }
                    // 确保字段是可访问的
                    pageSizeField.setAccessible(true);
                    // 获取字段值
                    Integer pageSize = (Integer)pageSizeField.get(reqParam);
                    param.setLimit(pageSize);
                }
                do{
                    ExportPage page =dataFunction.apply(cursor, param.getLimit());
                    cursor++;
                    size = page.getRecords().size();
                    if (ctx.getTotalCount() > 50000){
                        ctx.getExcelWriter().close();
                        //写入zip
                        zos.putNextEntry(new ZipEntry(String.format("%s_%s.xlsx",ctx.getFileName(), fileIndex++)));
                        zos.write(baos.toByteArray());
                        ctx.setExcelWriter(null);
                        ctx.setWriteSheet(null);
                        baos = new ByteArrayOutputStream();
                        ctx.setOutputStream(baos);
                    }
                    if ("CURRENT".equals(fieldValue.name())){
                        break;
                    }
                }while (size==param.getLimit());
                if (ctx.getTotalCount() > 0){
                    ctx.getExcelWriter().finish();
                    zos.putNextEntry(new ZipEntry(String.format("%s_%s.xlsx",ctx.getFileName(), fileIndex++)));
                    zos.write(baos.toByteArray());
                }
                zos.finish();
                fos.flush();
                String s = support.uploadFile(ctx.getFileName(), ctx.getFile());
                ctx.setResultFile(s);

                support.onComplete(ctx);
            } catch (Exception e) {
                log.error("导出异常", e);
                if (e instanceof ExportException) {
                    ctx.setFailMessage(e.getMessage());
                } else {
                    ctx.setFailMessage("系统异常，联系管理员");
                }
                support.onError(ctx);
            } finally {
                handler.callBack(ctx, param);
                ctx.getFile().delete();
            }
        });
    }

    /**
     * 支持多sheet导出，支持按批次进行单元格合并等功能
     *
     * @param handlers
     * @param support
     * @param param
     * @param ctx
     */
    public void exportData(ExportSupport support, DataExportParam param, ExportContext ctx,
                           ExportHandler... handlers) {
        TriFunction<ExportHandler, Integer, Integer, ExportPage> dataFunction = (h, start, limit) -> {
            support.onExport(ctx);
            try {
                h.beforePerPage(ctx, param);
                ExportPage exportPage = h.exportData(start, limit, param);
                if (CollectionUtils.isEmpty(exportPage.getRecords())) {
                    return exportPage;
                }
                ctx.record(exportPage.getRecords().size());
                support.onWrite(exportPage.getRecords(), ctx);
                h.afterPerPage(exportPage.getRecords(), ctx, param);
                return exportPage;
            } catch (Exception e) {
                log.error("导出过程发生异常");
                if (e instanceof ExportException) {
                    throw (ExportException) e;
                } else {
                    throw ExceptionUtil.wrap2Runtime(e);
                }
            }
        };

        executor.execute(() -> {
            try {
                if (handlers == null || handlers.length == 0) {
                    throw new ExportException("未设置导出处理类");
                }
                int sheetNo = 0;
                for (ExportHandler handler : handlers) {
                    handler.init(ctx, param);
                    if (ctx.getWriteSheet() != null) {
                        ctx.getWriteSheet().setSheetNo(sheetNo);
                    }
                    sheetNo++;
                    int cursor = 1;
                    ExportPage page = dataFunction.apply(handler, cursor, ctx.getLimit());
                    int total = page.getTotal();
                    ctx.getTask().setEstimateCount(total + ctx.getTask().getEstimateCount());
                    int pageNum = (total + page.getSize() - 1) / page.getSize();
                    for (cursor++; cursor <= pageNum; cursor++) {
                        dataFunction.apply(handler, cursor, ctx.getLimit());
                    }
                }
                support.onComplete(ctx);
            } catch (Exception e) {
                log.error("导出异常", e);
                if (e instanceof ExportException) {
                    ctx.setFailMessage(e.getMessage());
                } else {
                    ctx.setFailMessage("系统异常，联系管理员");
                }
                support.onError(ctx);
            } finally {
                for (ExportHandler handler : handlers) {
                    handler.callBack(ctx, param);
                }
            }
        });
    }
}
