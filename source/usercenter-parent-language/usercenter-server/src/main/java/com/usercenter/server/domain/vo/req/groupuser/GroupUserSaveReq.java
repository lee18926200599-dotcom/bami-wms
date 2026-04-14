package com.usercenter.server.domain.vo.req.groupuser;

import com.org.permission.common.permission.dto.UserRoleDto;
import com.usercenter.common.dto.UserSaveDto;
import com.usercenter.common.enums.BusinessSystemEnum;
import com.usercenter.common.enums.SourceTypeEnum;
import com.usercenter.server.constant.command.enums.BaseUserManagerLevelEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 集团用户新增实体
 */
@ApiModel("保存请求")
@Data
public class GroupUserSaveReq {

    @ApiModelProperty(value = "所属组织")
    private Long orgId;

    @ApiModelProperty(value = "当前集团id")
    private Long currentGroupId;

    @ApiModelProperty(value = "用户所属集团")
    private Long groupId;

    @ApiModelProperty(value = "注册来源 （来源系统-数据字典中获取）")
    private Integer sourceId;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "身份类型   员工(0)、客户(1)、供应商(2)")
    private Integer identityType;

    @ApiModelProperty(value = "用户帐号")
    private String userName;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "电子邮件")
    private String email;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    @ApiModelProperty(value = "个人档案ID")
    private Long archivesId;

    @ApiModelProperty(value = "联系邮箱")
    private String contactEmail;

    @ApiModelProperty(value = "头像")
    private String headImg;
    @ApiModelProperty(value = "创建人id")
    private Long createdBy;
    @ApiModelProperty(value = "创建人")
    private String createdName;
    @ApiModelProperty(value = "创建日期")
    private Date createdDate;


    @ApiModelProperty(value = "特殊授权-功能权限id")
    private List<Long> funcList;

    @ApiModelProperty(value = "特殊授权-组织权限id")
    private List<Long> orgList;

    @ApiModelProperty("角色对象(角色id，生效时间，失效时间)")
    private List<UserRoleDto> roleList;

    public UserSaveDto buildSaveDTO() {
        UserSaveDto saveDTO = new UserSaveDto();
        saveDTO.setCreatedBy(createdBy);
        saveDTO.setCreatedName(createdName);
        saveDTO.setCreatedDate(createdDate);
        saveDTO.setGroupId(groupId);
        saveDTO.setManagerLevel(BaseUserManagerLevelEnum.USER.getCode());
        saveDTO.setOrgId(orgId);
        saveDTO.setSource(sourceId == null ? null : String.valueOf(sourceId));
        saveDTO.setEmail(email);
        saveDTO.setUserName(userName);
        saveDTO.setHeadImg(headImg);
        saveDTO.setPhone(phone);
        saveDTO.setIdentityType(identityType);
        saveDTO.setRealName(realName);
        saveDTO.setRemark(remark);
        saveDTO.setArchivesId(archivesId);
        saveDTO.setContactEmail(contactEmail);
        saveDTO.setBusinessSystem(BusinessSystemEnum.PERMISSION.getCode());
        saveDTO.setSourceType(SourceTypeEnum.PC.getCode());
        return saveDTO;
    }
}
