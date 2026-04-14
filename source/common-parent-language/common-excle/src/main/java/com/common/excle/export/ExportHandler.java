package com.common.excle.export;

import com.common.excle.Handler;

import java.util.List;

public interface ExportHandler<T> extends Handler {
    
    /**
     * 分页导入导出
     * @param startPage 其实页
     * @param limit   每页限制大小
     * @param param   外部入参
     * @return 返回要导出的数据
     */
    ExportPage<T> exportData(int startPage, int limit, DataExportParam param);


    /**
     * 查询总数
     * @param param   外部入参
     * @return 返回要导出的数据
     */
    long count(DataExportParam param);
    
    /**
     * 每页开始前在exportData执行前执行
     * @param ctx 导出上下文，你可以在开始前进行修改
     * @param param 导出参数
     */
    default void beforePerPage(ExportContext ctx, DataExportParam param) {
    }
    
    /**
     * exportData执行后
     * @param list 得到的数据
     * @param ctx   上下文
     * @param param 外部入参
     */
    default void afterPerPage(List<T> list, ExportContext ctx, DataExportParam param) {
    }

}
