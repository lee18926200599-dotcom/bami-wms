package com.usercenter.server.domain.vo.req.administratorsuser;

import com.usercenter.server.domain.vo.req.AbstractUserReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "更新集团管理员请求参数")
public class AdministratorsUpdateReq extends AbstractUserReq {


    @ApiModelProperty(notes = "管理员ID")
    private Integer id;


    @ApiModelProperty(notes = "手机号")
    private String phone;


    @ApiModelProperty(notes = "安全邮箱")
    private String email;


    @ApiModelProperty(notes = "管理员账号")
    private String userName;


    /**
     * 明细ID
     */
    @ApiModelProperty(notes = "明细ID")
    private Integer detailId;

    /**
     * 联系邮箱
     */
    @ApiModelProperty(notes = "联系邮箱")
    private String contactEmail;

    /**
     * 身份类型
     */
    @ApiModelProperty(notes = "身份类型")
    private Integer identityType;


    /**
     * 名称
     */
    @ApiModelProperty(notes = "名称")
    private String realName;


    /**
     * 所属集团
     */
    @ApiModelProperty(notes = "所属集团")
    private Integer groupId;

    /**
     * 所属组织
     */
    @ApiModelProperty(notes = "所属组织ID")
    private Integer orgId;


    /**
     * 注册来源
     */
    @ApiModelProperty(notes = "注册来源")
    private Integer sourceId;


    /**
     * 备注
     */
    @ApiModelProperty(notes = "备注")
    private String remark;

    private List<ManagerGroupDetailReq> managerGroupInfos;
}
