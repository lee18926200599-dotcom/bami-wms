package com.common.excle;


public class ExcelContext {
    private ExcelTask task;
    private int totalCount=0;
    private int failCount=0;
    private int successCount=0;
    
    public ExcelTask getTask() {
        return task;
    }
    
    public void setTask(ExcelTask task) {
        this.task = task;
    }
    
    public void record(int dataSize){
        record(dataSize,0);
    }
    
    public void record(int dataSize,int errorSize){
        this.totalCount=this.totalCount+dataSize;
        this.successCount=this.successCount+dataSize-errorSize;
        this.failCount=this.failCount+errorSize;
    }
    
    public int getTotalCount() {
        return totalCount;
    }
    
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    
    public int getFailCount() {
        return failCount;
    }
    
    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }
    
    public int getSuccessCount() {
        return successCount;
    }
    
    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }
}
