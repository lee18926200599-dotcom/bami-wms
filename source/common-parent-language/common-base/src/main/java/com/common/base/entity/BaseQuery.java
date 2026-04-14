package com.common.base.entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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

    public String getOrderBy() {
        if (orderByFields != null && orderByFields.size() > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            for (OrderByEntry orderByEntry : orderByFields) {
                stringBuffer.append(orderByEntry.getFieldName()).append(Objects.equals(orderByEntry.isAsc(), Boolean.TRUE) ? " ASC," : " DESC,");
            }
            String orderBy = stringBuffer.toString();
            if (StringUtils.isNotBlank(orderBy)) {
                return orderBy.substring(0, orderBy.length() - 1);
            }
            return "";
        }
        return "";
    }

}
