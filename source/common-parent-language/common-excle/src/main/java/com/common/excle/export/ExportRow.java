package com.common.excle.export;

import com.alibaba.excel.annotation.ExcelIgnore;

public class ExportRow implements ISheetRow {
    
    @ExcelIgnore
    private int row;
    
    @Override
    public int getRow() {
        return row;
    }
    
    @Override
    public void setRow(int row) {
        this.row = row;
    }
}
