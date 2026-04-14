package com.org.permission.common.permission.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 集团参数设置
 * @ClassName: BasePermissionGroupParamDto
 * @date
 */
@Data
public class BasePermissionGroupParamDto implements Serializable {
    private static final long serialVersionUID = -4688967228559197075L;
    private Integer id;
    private Long groupId; //所属集团id
    private String paramCode; //参数代码
    private String paramName; //参数名称
    private String remark; //备注
    private String paramValue; //参数值
    private Integer state; //状态
}
