package com.basedata.common.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Data
public class BaseQuery implements Serializable {

    private int pageNum = 1;

    private int pageSize = 10;

    private List<OrderByEntry> orderByFields;

    @Getter
    @Setter
    public static class OrderByEntry implements Serializable {

        /**
         * 字段名称
         */
        private String fieldName;

        /**
         * 是否 isAsc
         */
        private boolean isAsc;

    }


}
