package com.common.log.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "OperateLog")
public class ChangeBean {

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段描述
     */
    private String fieldDescription;

    /**
     * 修改前
     */
    private Object oldValue;

    /**
     * 修改后
     */
    private Object newValue;
}
