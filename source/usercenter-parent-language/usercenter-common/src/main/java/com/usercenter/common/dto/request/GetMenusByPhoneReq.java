package com.usercenter.common.dto.request;

import com.usercenter.common.enums.SourceTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 根据手机号获取菜单权限请求实体
 */
@ApiModel("根据手机号获取菜单权限请求实体")
public class GetMenusByPhoneReq implements Serializable {

    private static final long serialVersionUID = 7096162295571484156L;

    /**
     * 用户手机号
     */
    @ApiModelProperty(value ="用户手机号")
    private String phone;

    /**
     * 端：app pc
     */
    @ApiModelProperty(value ="端：app pc")
    private SourceTypeEnum sourceTypeEnum;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public SourceTypeEnum getSourceTypeEnum() {
        return sourceTypeEnum;
    }

    public void setSourceTypeEnum(SourceTypeEnum sourceTypeEnum) {
        this.sourceTypeEnum = sourceTypeEnum;
    }
}
