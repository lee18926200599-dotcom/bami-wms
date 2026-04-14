package com.common.excle.export;

public interface ISheetIndex {
    
    default void setSheetIndex(int sheetIndex) {}
    
    default int getSheetIndex() {
        return 0;
    }
}
