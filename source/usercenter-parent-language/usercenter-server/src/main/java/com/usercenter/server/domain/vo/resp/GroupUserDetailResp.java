package com.usercenter.server.domain.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户管理（集团）明细
 */
@ApiModel("用户信息")
@Data
public class GroupUserDetailResp {

    @ApiModelProperty(notes = "用户子表主键ID")
    private Integer id;

    @ApiModelProperty(notes = "用户主表主键id")
    private Integer userId;

    @ApiModelProperty(notes = "用户编码")
    private String userCode;

    @ApiModelProperty(notes = "头像")
    private String headImg;

    @ApiModelProperty(notes = "做业务的组织id")
    private Long orgId;

    @ApiModelProperty(notes = "组织名称")
    private String orgName;

    @ApiModelProperty(notes = "做业务的集团")
    private Long groupId;

    @ApiModelProperty(notes = "注册来源id （数据字典中获取）")
    private Integer source;

    @ApiModelProperty(notes = "注册来源 （数据字典中获取）")
    private String sourceName;

    @ApiModelProperty(notes = "真实姓名")
    private String realName;

    @ApiModelProperty(notes = "管理员等级    超级(0)、全局(1)、集团(2)、用户(3)")
    private Integer managerLevel;

    @ApiModelProperty(notes = "身份类型   员工(0)、客户(1)、供应商(2)、会员(3)、官网(4)")
    private Integer identityType;

    @ApiModelProperty(notes = "启用状态  创建(0)、启用(1)、停用(2)")
    private Integer state;

    @ApiModelProperty(notes = "锁定状态   未锁定(0)、锁定(1)")
    private Integer lockFlag;

    @ApiModelProperty(notes = "用户密码（暂时不用，冗余，为不同集团或者2b、2c密码不同做准备）")
    private String password;

    @ApiModelProperty(notes = "用户帐号（暂时不用，冗余，为不同集团或者2b、2c密码不同做准备）")
    private String userName;

    @ApiModelProperty(notes = "联系邮箱")
    private String contactEmail;

    @ApiModelProperty(notes = "昵称")
    private String nickname;

    @ApiModelProperty(notes = "生效时间（未使用，冗余）")
    private Long effectiveDate;

    @ApiModelProperty(notes = "失效日期（未使用，冗余）")
    private Long expireTime;

    @ApiModelProperty(notes = "备注信息")
    private String remark;

    @ApiModelProperty(notes = "是否删除   否(0)、是(1)")
    private Integer deletedFlag;

    @ApiModelProperty(notes = "是否需要重置密码    否(0)、是(1)（暂时不用，冗余，为不同集团或者2b、2c密码不同做准备）")
    private Boolean passwordFlag;

    @ApiModelProperty(notes = "首次登录（暂时不用，冗余，为不同集团或者2b、2c密码不同做准备）")
    private Boolean firstTimeLoginFlag;

    @ApiModelProperty(notes = "个人档案ID")
    private Long archivesId;

    @ApiModelProperty(notes = "全局客户")
    private Long globalCustomer;

    @ApiModelProperty(notes = "用户体系（ERP相关：erp,  电商体系：ec）")
    private String userSystem;

    @ApiModelProperty(notes = "扩展字段 暂时未定义")
    private String attribute;
    @ApiModelProperty(notes = "手机号码")
    private String phone;

    @ApiModelProperty(notes = "邮箱")
    private String email;
}
