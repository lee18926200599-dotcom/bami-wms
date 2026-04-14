package com.common.excle.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.common.excle.ExcelTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;


@Slf4j
public abstract class AsyncExportTaskSupport implements ExportSupport {
    
    @Override
    public ExcelTask createTask(DataExportParam param) {
        ExcelTask task = new ExcelTask();
        task.setType(2);
        task.setStatus(0);
        task.setStartTime(LocalDateTime.now());
        task.setCreatedDate(new Date());
        task.setBusinessCode(param.getBusinessCode());
        task.setFileName(param.getExportFileName());
        this.save(task);
        return task;
    }


    public abstract void save(ExcelTask task);
    
    @Override
    public void onExport(ExportContext ctx) {
        ExcelTask excelTask = ctx.getTask();
        excelTask.setStatus(1);
        excelTask.setFailedCount(ctx.getFailCount());
        excelTask.setSuccessCount(ctx.getSuccessCount());
        excelTask.setTotalCount(ctx.getTotalCount());

        this.update(excelTask);
    }

    public abstract void update(ExcelTask task);
    
    @Override
    public void onWrite(Collection<?> dataList, ExportContext ctx) {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }
        //创建excel
        if (ctx.getExcelWriter() == null) {
            ExcelWriterBuilder writerBuilder = EasyExcel.write(ctx.getOutputStream(),ctx.getHeadClass());
            ExcelWriter excelWriter = writerBuilder.build();
        
            ctx.setExcelWriter(excelWriter);
        }
        //创建sheet 此代码块将在后续某个版本的迭代中移除，sheet将由handler的init方法创建
        if (ctx.getWriteSheet()==null){
            WriteSheet writeSheet = EasyExcel.writerSheet("sheet1")
                    .needHead(Boolean.TRUE)
                    .build();
            //动态表头以sheet为单位
            /*if (ctx.isDynamicHead()) {
                sheetBuilder.head(ctx.getHeadList());
            } else {
                sheetBuilder.head(ctx.getHeadClass());
            }
            //自定义样式以sheet为单位
            if (ctx.getWriteHandlers() != null && ctx.getWriteHandlers().size() > 0) {
                for (WriteHandler writeHandler : ctx.getWriteHandlers()) {
                    sheetBuilder.registerWriteHandler(writeHandler);
                }
            }
            //自定义类型转换器以sheet为单位
            if (ctx.getConverters() != null && ctx.getConverters().size() > 0) {
                for (Converter<?> converter : ctx.getConverters()) {
                    sheetBuilder.registerConverter(converter);
                }
            }
            WriteSheet writeSheet = sheetBuilder.build();*/
            ctx.setWriteSheet(writeSheet);
        }
        
        ctx.getExcelWriter().write(dataList, ctx.getWriteSheet());
        
    }

    @Override
    public String uploadFile(String fileName,File file) {
        try (InputStream in = new FileInputStream(file)) {
            String upload = upload(fileName, in);
            return upload;
        }catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            return null;
        }
    }

    public abstract String upload(String fileName, InputStream in);
    
    public void close(ExportContext ctx) {
        if (ctx.getExcelWriter() != null) {
            ctx.getExcelWriter().finish();
        }
        if (ctx.getOutputStream() != null) {
            try {
                ctx.getOutputStream().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (ctx.getInputStream() != null) {
            try {
                ctx.setResultFile(ctx.getFuture().get());
                ctx.getInputStream().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void onComplete(ExportContext ctx) {
        close(ctx);
        ExcelTask excelTask = ctx.getTask();
        excelTask.setStatus(2);
        excelTask.setFailedCount(ctx.getFailCount());
        excelTask.setSuccessCount(ctx.getSuccessCount());
        excelTask.setEndTime(LocalDateTime.now());
        excelTask.setTotalCount(ctx.getTotalCount());
        excelTask.setFileUrl(ctx.getResultFile());
        excelTask.setFileName(ctx.getFileName()+".zip");
        update(excelTask);
        log.info("task completed");
    }
    
    @Override
    public void onError(ExportContext ctx) {
        close(ctx);
        ExcelTask excelTask = ctx.getTask();
        excelTask.setStatus(3);
        excelTask.setFailedCount(ctx.getFailCount());
        excelTask.setSuccessCount(ctx.getSuccessCount());
        excelTask.setEndTime(LocalDateTime.now());
        excelTask.setTotalCount(ctx.getTotalCount());
        excelTask.setFileUrl(ctx.getResultFile());
        excelTask.setFailedMessage(ctx.getFailMessage());
        update(excelTask);
        log.info("task Error");
    }


}
