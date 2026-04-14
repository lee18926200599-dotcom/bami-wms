package com.usercenter.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.org.permission.common.permission.dto.UserAllPermissionDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FplUser implements Serializable {


    @ApiModelProperty(notes = "用户认证信息id")
    private Long userAuthId;

    /**
     * ID
     */
    @ApiModelProperty(value = "userId")
    private Long id;
    /**
     * 个人档案ID
     */
    @ApiModelProperty(value = "用户档案ID（员工、客户、供应商、会员等）")
    private Long archivesId;

    /**
     * 用户编码
     */
    @ApiModelProperty(value = "用户编码")
    private String userCode;

    /**
     * 所属组织
     */
    @ApiModelProperty(value = "所属组织")
    private Long orgId;

    /**
     * 所属组织名称
     */
    @ApiModelProperty(value = "所属组织名称")
    private String orgName;

    /**
     * 当前集团
     */
    @ApiModelProperty(value = "当前集团")
    private Long groupId;

    /**
     * 所属集团
     */
    @ApiModelProperty(value = "所属集团")
    private Long belongGroupId;

    /**
     * 注册来源
     */
    @ApiModelProperty(value = "注册来源 从registerSource接口中获取对应系统的itemCode")
    private Integer source;

    /**
     * 注册来源
     */
    @ApiModelProperty(value = "注册来源 从registerSource接口中获取对应系统的itemCode")
    private String sourceName;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String realName;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像")
    private String headImg;

    /**
     * 管理员等级 (超级0、全局1、集团2、用户3）
     */
    @ApiModelProperty(value = "管理员等级 (超级0、全局1、集团2、用户3")
    private Integer managerLevel;

    /**
     * 身份类型 (员工0、客户1、供应商2)
     */
    @ApiModelProperty(value = "身份类型 (员工0、客户1、供应商2)")
    private Integer identityType;

    /**
     * 启用状态（0非启用，1启用，2停用）
     */
    @ApiModelProperty(value = "启用状态（0非启用，1启用，2停用）")
    private Integer state;

    /**
     * 锁定状态（0未锁定，1锁定）
     */
    @ApiModelProperty(value = "锁定状态（0未锁定，1锁定）")
    private Integer lockFlag;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户帐号
     */
    @ApiModelProperty(value = "用户帐号")
    private String userName;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String phone;

    /**
     * 电子邮件
     */
    @ApiModelProperty(value = "电子邮件")
    private String email;
    @ApiModelProperty(value = "创建人")
    private Long createdBy;
    @ApiModelProperty(value = "创建人")
    private String createdName;
    @ApiModelProperty(value = "创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    @ApiModelProperty(value = "修改人id")
    private Long modifiedBy;
    @ApiModelProperty(value = "修改人")
    private String modifiedName;
    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifiedDate;
    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    private String remark;
    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除")
    private Integer deletedFlag;
    /**
     * 是否需要重置密码(0否，1是)
     */
    @ApiModelProperty(value = "是否需要重置密码(0否，1是)")
    private Integer passwordFlag;
    /**
     * 首次登录(0否，1是)
     */
    @ApiModelProperty(value = "首次登录(0否，1是)")
    private Integer firstTimeLoginFlag;

    /**
     * 业务系统
     */
    @JsonIgnore
    private Integer businessSystem;
    /**
     * token
     */
    @ApiModelProperty(value = "token")
    private String token;
    /**
     * token 失效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "token 失效时间 yyyy-MM-dd HH:mm:ss")
    private Date tokenExpireDate;
    /**
     * token 失效时间
     */
    @ApiModelProperty(value = "token 失效时间（秒）")
    private Integer tokenExpireTime;

    /**
     * 业务单元列表
     */
    @ApiModelProperty(value = "业务单元列表")
    private Object orgInfo;
    /**
     * 集团列表
     */
    @ApiModelProperty(value = "集团列表")
    private Object groupInfo;
    /**
     * 全局客户
     */
    @ApiModelProperty(value = "全局客户")
    private Long globalCustomer;

    /**
     * 用户菜单
     */
    @ApiModelProperty(value = "用户菜单")
    private UserAllPermissionDto menus;
    /**
     * 小程序token认证使用
     */
    @ApiModelProperty(value = "小程序token认证使用")
    private String openKey;

    @ApiModelProperty(value = "联系邮箱")
    private String contactEmail;

}