package com.common.excle.export;

import java.util.ArrayList;
import java.util.List;

public class ExportPage<T> {
    private int total;
    private int size;
    private int current;
    
    List<T> records=new ArrayList<>();
    
    public List<T> getRecords() {
        return records;
    }
    
    public void setRecords(List<T> records) {
        this.records = records;
    }
    
    public int getTotal() {
        return total;
    }
    
    public void setTotal(int total) {
        this.total = total;
    }
    
    public int getSize() {
        return size;
    }
    
    public void setSize(int size) {
        this.size = size;
    }
    
    public int getCurrent() {
        return current;
    }
    
    public void setCurrent(int current) {
        this.current = current;
    }
}
