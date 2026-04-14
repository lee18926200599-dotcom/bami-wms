package com.basedata.common.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseDictionaryQuery implements Serializable {

    private static final long serialVersionUID = -1338645698055615417L;
    private String dictCode;

    private String itemCode;
    
    private Long groupId = 0L;
}
