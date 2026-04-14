package com.usercenter.server.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {

    /**
     * 是否模糊查询：true，模糊查询
     */
    @ApiModelProperty(value ="是否模糊查询：默认为true， 控制的字段有：userNumber、realName、userName、phone、email")
    private Boolean likeQuery = true;

    /**
     * 是否包含已经删除的数据，默认为false
     */
    @ApiModelProperty(value ="是否包含已经删除的数据, 默认为false")
    private Boolean withDeleted = false;


    /**
     * 根据用户权限过滤可以查询的用户
     */
    private Boolean filterByUserPermission = false;

    /**
     * 查询人id
     */
    private Integer queryUserId;

    /**
     * 根据id集合批量查询
     */
    private Set<Integer> idSet;

    /**
     * 根据id不再某一集合中
     */
    private Set<Integer> idNotInSet;

    /**
     * ID
     */
    @ApiModelProperty(value ="userId")
    private Integer id;

    /**
     * 用户编码
     */
    @ApiModelProperty(value ="用户编码")
    private String userNumber;

    /**
     * 所属组织
     */
    @ApiModelProperty(value ="所属组织 (多个参数请用逗号分隔)")
    private String orgId;

    /**
     * 所属集团
     */
    @ApiModelProperty(value ="所属集团 (多个参数请用逗号分隔)")
    private String groupId;

    /**
     * 注册来源
     */
    @ApiModelProperty(value ="注册来源  (多个参数请用逗号分隔)")
    private String source;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value ="真实姓名")
    private String realName;

    /**
     * 用户头像
     */
    @ApiModelProperty(value ="用户头像")
    private String profile;

    /**
     * 管理员等级
     */
    @ApiModelProperty(value ="管理员等级 超级(0)、全局(1)、集团(2)、用户(3)  (多个参数请用逗号分隔)")
    private String managerLevel;

    /**
     * 身份类型
     */
    @ApiModelProperty(value ="身份类型  员工(0)、客户(1)、供应商(2)  (多个参数请用逗号分隔)")
    private String identityType;

    /**
     * 启用状态（0非启用，1启用，2停用）(多个参数请用逗号分隔)
     */
    @ApiModelProperty(value ="启用状态 非启用(0)，启用(1)，停用(2)")
    private String enable;

    /**
     * 锁定状态（0未锁定，1锁定）(多个参数请用逗号分隔)
     */
    @ApiModelProperty(value ="锁定状态  未锁定(0)，锁定(1)")
    private String lock;

    /**
     * 用户帐号
     */
    @ApiModelProperty(value ="用户帐号")
    private String userName;

    /**
     * 手机号码
     */
    @ApiModelProperty(value ="手机号码")
    private String phone;

    /**
     * 电子邮件
     */
    @ApiModelProperty(value ="电子邮件")
    private String email;

    /**
     * 备注信息
     */
    @ApiModelProperty(value ="备注信息")
    private String remark;
    /**
     * 是否删除
     */
    @ApiModelProperty(value ="是否删除")
    private Boolean deleted;
    /**
     * 个人档案ID
     */
    @ApiModelProperty(value ="个人档案ID")
    private String archivesId;


    @ApiModelProperty(value = "来源验证")
    private String appName;

}
