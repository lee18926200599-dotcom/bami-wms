package com.common.excle;

import lombok.Data;

import java.util.Map;

@Data
public class DataParam {
    private Map<String, Object> parameters;
    private Long serviceProviderId;
    private Long createBy;
    private String createdName;
}
