package com.usercenter.server.domain.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户认证信息
 */
@ApiModel("用户认证信息")
@Data
public class UserAuthInfoResp {

    @ApiModelProperty(notes = "用户帐号（暂时不用，冗余，为不同集团或者2b、2c密码不同做准备）")
    private String userName;

    @ApiModelProperty(notes = "邮箱")
    private String email;

    @ApiModelProperty(notes = "扩展字段 暂时未定义")
    private String attribute;
}
