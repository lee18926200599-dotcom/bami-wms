package com.usercenter.server.domain.vo.req.groupuser;

import com.org.permission.common.permission.dto.UserPermissionDto;
import com.org.permission.common.permission.dto.UserRoleDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 集团用户新增实体
 */
@ApiModel("修改请求")
@Data
public class GroupUserUpdateReq {

    @ApiModelProperty(notes = "当前集团id")
    private Long currentGroupId;

    @ApiModelProperty(notes = "用户细表id")
    private Long id;

    @ApiModelProperty(notes = "所属集团id")
    private Long groupId;

    @ApiModelProperty(notes = "注册来源 从registerSource接口中获取对应系统的itemCode")
    private Integer source;

    @ApiModelProperty(notes = "手机号码")
    private String phone;

    @ApiModelProperty(notes = "所属组织")
    private Long orgId;

    @ApiModelProperty(notes = "真实姓名")
    private String realName;

    @ApiModelProperty(notes = "身份类型   员工(0)、客户(1)、供应商(2)")
    private Integer identityType;

    @ApiModelProperty(notes = "管理员等级 (超级0、全局1、集团2、用户3）")
    private Integer managerLevel;

    @ApiModelProperty(notes = "用户帐号")
    private String userName;

    @ApiModelProperty(notes = "电子邮件")
    private String email;

    @ApiModelProperty(notes = "备注信息")
    private String remark;

    @ApiModelProperty(notes = "个人档案ID")
    private Long archivesId;

    @ApiModelProperty(notes = "联系邮箱")
    private String contactEmail;
    @ApiModelProperty(value = "角色-角色对象(角色id，生效时间，失效时间)")
    private List<UserRoleDto> roleList;

    @ApiModelProperty(value = "特殊授权-功能权限id")
    private List<UserPermissionDto> funcList;

    @ApiModelProperty(value = "特殊授权-组织权限id")
    private List<UserPermissionDto> orgList;

    @ApiModelProperty(value = "特殊授权-数据权限")
    private List<UserPermissionDto> dataList;
   }
