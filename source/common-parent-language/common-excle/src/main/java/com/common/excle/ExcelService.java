package com.common.excle;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.common.excle.export.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class ExcelService {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(10);
    public Long doExport(Class<? extends ExportHandler> cls, DataExportParam param){
        ExportHandler handler = SpringBeanLoaderUtil.getSpringBean(cls);

        long count = handler.count(param);
        if (count > 1000000){
            throw new RuntimeException("导出超限!");
        }
        ExportSupport support = SpringBeanLoaderUtil.getSpringBean(ExportSupport.class);
        ExcelTask task = support.createTask(param);
        ExportContext ctx=new ExportContext();
        ctx.setTask(task);
        ctx.setLimit(param.getLimit());
        ctx.setHeadClass(param.getHeadClass());
        ctx.setDynamicHead(param.isDynamicHead());
        ctx.setHeadList(param.getHeadList());
        ctx.setWriteHandlers(param.getWriteHandlers());
        ctx.setConverters(param.getConverters());
        ctx.setSheetName(param.getSheetName());
        String fileName=param.getExportFileName();
        StringBuilder sb=new StringBuilder(fileName).append("_").append(task.getId());
        String writeExcel = sb.toString() + ".zip";
        File file = new File(writeExcel);
        ctx.setFile(file);
        ctx.setFileName(sb.toString());
        AsyncExcelExporter asyncExcelExporter=new AsyncExcelExporter(EXECUTOR_SERVICE);
        asyncExcelExporter.exportData(handler,support,param,ctx);
        return task.getId();
    }
    
    public Long doExport(DataExportParam param, Class<? extends ExportHandler>... clses) {
        String filePrefix = "导出";
        ExportHandler[] handlers = new ExportHandler[clses.length];
        for (int i = 0; i < clses.length; i++) {
            ExportHandler handler = SpringBeanLoaderUtil.getSpringBean(clses[i]);
            handlers[i]=handler;
        }
        
        ExportSupport support = SpringBeanLoaderUtil.getSpringBean(ExportSupport.class);
        ExcelTask task = support.createTask(param);
        ExportContext ctx = new ExportContext();
        ctx.setLimit(param.getLimit());
        ctx.setTask(task);
        ctx.setHeadClass(param.getHeadClass());
        ctx.setDynamicHead(param.isDynamicHead());
        ctx.setHeadList(param.getHeadList());
        ctx.setWriteHandlers(param.getWriteHandlers());
        ctx.setConverters(param.getConverters());
        ctx.setSheetName(param.getSheetName());
        String fileName = param.getExportFileName();
        StringBuilder sb = new StringBuilder(filePrefix).append(task.getId()).append("-");
        if (StringUtils.isEmpty(fileName)) {
            sb.append(ExcelTypeEnum.XLSX.getValue());
        } else {
            if (fileName.lastIndexOf(".") != -1) {
                String extension = fileName.substring(fileName.lastIndexOf("."));
                if (!ExcelTypeEnum.XLSX.getValue().equals(extension)) {
                    sb.append(fileName).append(ExcelTypeEnum.XLSX.getValue());
                } else {
                    sb.append(fileName);
                }
            } else {
                sb.append(fileName).append(ExcelTypeEnum.XLSX.getValue());
            }
        }
        ctx.setFileName(sb.toString());
        AsyncExcelExporter asyncExcelExporter = new AsyncExcelExporter(EXECUTOR_SERVICE);
        asyncExcelExporter.exportData(support, param, ctx, handlers);
        return task.getId();
    }

}
