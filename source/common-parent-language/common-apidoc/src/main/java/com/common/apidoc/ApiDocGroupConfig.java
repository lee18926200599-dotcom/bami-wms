package com.common.apidoc;

import lombok.Data;

@Data
public class ApiDocGroupConfig {
    private String groupName = "defualt";
    private String basePackage;
    private String title = "接口文档";
    private String description;
    private String version = "v1.0.0";
    private Boolean enable = true;
}
