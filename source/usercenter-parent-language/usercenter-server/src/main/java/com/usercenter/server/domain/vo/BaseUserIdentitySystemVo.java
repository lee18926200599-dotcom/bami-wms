package com.usercenter.server.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * base_user_identity_systemVo类  用户身份类型和用户系统定义的系统id对应关系管理
 */

@ApiModel(description = "用户身份类型和用户系统定义的系统id对应关系", value = "用户身份类型和用户系统定义的系统id对应关系")
@Data
public class BaseUserIdentitySystemVo extends QueryPager implements Serializable {
    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "身份类型id")
    private Integer identityId;
    @ApiModelProperty(value = "身份类型名称")
    private String identityName;
    @ApiModelProperty(value = "系统来源id")
    private Integer sourceId;
    @ApiModelProperty(value = "数据字典名称")
    private String sourceName;
    @ApiModelProperty(value = "扩展字段，自定义业务状态")
    private String state;
}

