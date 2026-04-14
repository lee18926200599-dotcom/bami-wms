package com.usercenter.server.domain.vo.req.administratorsuser;

import com.org.permission.common.permission.dto.GroupDto;
import com.usercenter.server.entity.BaseUser;
import com.usercenter.server.entity.BaseUserDetail;
import com.usercenter.server.domain.dto.UpdateUserDTO;
import com.usercenter.server.domain.vo.req.AbstractUserReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "保存集团管理员")
@Data
public class AdministratorsSaveUpdateReq extends AbstractUserReq {


    @ApiModelProperty(notes = "ID")
    private Long id;

    /**
     * 管理员类型
     */
    @ApiModelProperty(notes = "管理级别")
    private Integer managerLevel;

    /**
     * 手机号
     */
    @ApiModelProperty(notes = "手机号")
    private String phone;


    /**
     * 安全邮箱
     */
    @ApiModelProperty(notes = "安全邮箱")
    private String email;


    @ApiModelProperty(notes = "管理员账号")
    private String userName;

    /**
     * 管理集团数据
     */
    @ApiModelProperty(notes = "集团管理员管理集团数据")
    private List<ManagerGroupDetailReq> managerGroupInfos;


    //以下是字表明细

    /**
     * 明细ID
     */
    @ApiModelProperty(notes = "明细ID")
    private Long detailId;

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
     * 人员ID
     */
    @ApiModelProperty(notes = "人员ID")
    private Long archivesId;

    /**
     * 名称
     */
    @ApiModelProperty(notes = "名称")
    private String realName;


    /**
     * 所属集团
     */
    @ApiModelProperty(notes = "所属集团【必填】")
    @NotNull(message = "所属集团不能为空")
    private Long groupId;

    /**
     * 所属组织
     */
    @ApiModelProperty(notes = "所属组织ID【必填】")
    @NotNull(message = "所属集团不能为空")
    private Long orgId;


    /**
     * 注册来源
     */
    @ApiModelProperty(notes = "注册来源【必填】")
    @NotNull(message = "注册来源不能为空")
    private String sourceId;


    /**
     * 备注
     */
    @ApiModelProperty(notes = "备注")
    private String remark;

    public BaseUser generatorBaseUserAndDetail() {
        BaseUser baseUser = new BaseUser();
        baseUser.setUserName(this.getUserName());
        baseUser.setPhone(this.phone);
        baseUser.setEmail(this.email);
        baseUser.setId(this.id);
        baseUser.setRemark(this.remark);
        //字表信息
        List<BaseUserDetail> baseUserDetails = new ArrayList<>();
        BaseUserDetail baseUserDetail = new BaseUserDetail();
        baseUserDetail.setContactEmail(this.contactEmail);
        baseUserDetail.setOrgId(this.orgId);
        baseUserDetail.setGroupId(this.groupId);
        baseUser.setManagerLevel(this.managerLevel);
        baseUserDetail.setIdentityType(this.identityType);
        baseUserDetail.setRealName(this.realName);
        baseUserDetail.setSource(StringUtils.isBlank(this.sourceId) ? null : Integer.valueOf(this.sourceId));
        baseUserDetail.setId(this.detailId);
        baseUserDetail.setArchivesId(this.archivesId);
        baseUserDetail.setRemark(this.remark);
        baseUserDetails.add(baseUserDetail);
        baseUser.setDetailList(baseUserDetails);
        return baseUser;
    }


    public UpdateUserDTO generatorUpdateUserDto() {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setEmail(this.email);
        updateUserDTO.setGroupId(this.getGroupId());
        updateUserDTO.setId(this.detailId);
        updateUserDTO.setIdentityType(this.identityType);
        updateUserDTO.setManagerLevel(this.managerLevel);
        updateUserDTO.setPhone(this.phone);
        updateUserDTO.setSource(this.sourceId);
        updateUserDTO.setUserAuthId(this.id);
        updateUserDTO.setUserName(this.userName);
        return updateUserDTO;
    }

    public List<GroupDto> generatorGroupDto() {
        List<GroupDto> groupDtos = new ArrayList<>();
        if (CollectionUtils.isEmpty(this.managerGroupInfos)) {
            return groupDtos;
        }
        this.managerGroupInfos.forEach(managerGroup -> {
            GroupDto groupDto = new GroupDto();
            groupDto.setId(managerGroup.getId());
            groupDto.setGroupName(managerGroup.getGroupName());
            groupDto.setGroupCode(managerGroup.getGroupCode());
            groupDto.setGroupId(managerGroup.getGroupId());
            groupDto.setExpireTime(managerGroup.getExpireTime());
            groupDto.setEffectiveTime(managerGroup.getEffectiveTime());
            groupDtos.add(groupDto);
        });
        return groupDtos;
    }
}
