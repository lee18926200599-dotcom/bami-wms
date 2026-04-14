package com.basedata.common.query;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WarehouseCategoryQuery implements Serializable {
    private static final long serialVersionUID = 5119198484745986817L;

    private List<Long> idList;
}
