package com.usercenter.server.domain.vo.req.groupuser;

import com.usercenter.server.domain.vo.req.AbstractPageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * 列表请求
 */
@ApiModel("列表请求")
@Data
public class GroupUserListReq extends AbstractPageReq {

    @ApiModelProperty(notes="用户id", required = true)
    private Set<Long> ids;

    @ApiModelProperty(notes="当前集团id", required = true)
    private Long groupId;

    @ApiModelProperty(notes="业务单元id", required = false)
    private Long orgId;

    @ApiModelProperty(notes="用户编码")
    private String userCode;

    @ApiModelProperty(notes="手机号")
    private String phone;

    @ApiModelProperty(notes="用户账号")
    private String userName;

    @ApiModelProperty(notes="联系邮箱")
    private String contactEmail;

    @ApiModelProperty(notes="注册来源")
    private Integer source;

    @ApiModelProperty(notes="状态")
    private Integer state;


    @ApiModelProperty(notes="是否锁定")
    private Integer lockFlag;

}
