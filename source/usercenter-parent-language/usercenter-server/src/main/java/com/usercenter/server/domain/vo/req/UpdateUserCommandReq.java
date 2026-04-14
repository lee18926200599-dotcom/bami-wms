package com.usercenter.server.domain.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("用户更新请求")
@Data
public class UpdateUserCommandReq extends AbstractUserReq {

    @ApiModelProperty(notes = "主表数据ID")
    private Long id;

    @ApiModelProperty(notes = "字表数据ID")
    private Long detailId;

    @ApiModelProperty(notes = "操作类型【1.启用。2停用。3.锁定。4.解锁。5.重置密码.6,删除】")
    private Integer operate;


    @ApiModelProperty(notes = "密码重置验证码")
    private String code;


    @ApiModelProperty(notes = "手机号")
    private String phone;
}
