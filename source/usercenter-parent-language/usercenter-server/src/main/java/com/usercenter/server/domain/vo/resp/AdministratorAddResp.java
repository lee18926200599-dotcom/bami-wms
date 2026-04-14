package com.usercenter.server.domain.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "添加管理员返回参数")
@Data
public class AdministratorAddResp implements Serializable {


    /**
     * 账户信息
     */
    @ApiModelProperty(notes = "账户")
    private String userName;

    /**
     * 默认密码
     */
    @ApiModelProperty(notes = "默认密码")
    private String password;


    @ApiModelProperty(notes = "是否展示密码")
    private Boolean showPassword;

}
