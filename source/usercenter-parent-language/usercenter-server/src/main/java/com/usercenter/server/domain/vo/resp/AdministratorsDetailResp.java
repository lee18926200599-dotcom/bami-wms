package com.usercenter.server.domain.vo.resp;

import com.common.base.enums.BooleanEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.usercenter.server.domain.vo.req.administratorsuser.ManagerGroupDetailReq;
import com.usercenter.server.entity.BaseUser;
import com.usercenter.server.entity.BaseUserDetail;
import com.usercenter.server.entity.BaseUserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@ApiModel(value = "管理员列表返回值")
@Data
public class AdministratorsDetailResp {


    /**
     * 用户ID
     */
    @ApiModelProperty(notes = "用户主表ID")
    private Long id;


    @ApiModelProperty(notes = "用户子表ID")
    private Long detailId;


    /**
     * 管理员编码
     */
    @ApiModelProperty(notes = "管理员编码")
    private String userCode;

    /**
     * 管理员账号
     */
    @ApiModelProperty(notes = "管理员账号")
    private String userName;

    /**
     * 管理员类型
     */
    @ApiModelProperty(notes = "管理员类型")
    private Integer managerLevel;


    /**
     * 安全邮箱
     */
    @ApiModelProperty(notes = "安全邮箱")
    private String email;

    /**
     * 手机号
     */
    @ApiModelProperty(notes = "手机号")
    private String phone;

    /**
     * 启用状态
     */
    @ApiModelProperty(notes = "启用状态")
    private Integer state;

    /**
     * 锁定状态
     */
    @ApiModelProperty(notes = "锁定状态")
    private Integer lockFlag;
    /**
     * 锁定状态
     */
    @ApiModelProperty(notes = "锁定状态")
    private String lockFlagName;
    /**
     * 备注信息
     */
    @ApiModelProperty(notes = "备注信息")
    private String remark;


    /**
     * 联系邮箱
     */
    @ApiModelProperty(notes = "联系邮箱")
    private String contactEmail;

    /**
     * 所属组织
     */
    @ApiModelProperty(notes = "所属组织")
    private String orgName;


    private Long orgId;

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
     * 注册来源
     */
    @ApiModelProperty(notes = "注册来源")
    private Integer sourceId;


    /**
     * 注册来源名称
     */
    @ApiModelProperty(notes = "注册来源名称")
    private String sourceName;


    @ApiModelProperty(notes = "所属集团")
    private Long groupId;

    @ApiModelProperty(notes = "集团名称")
    private String groupName;

    /**
     * 集团管理员 -》管理集团信息
     */
    @ApiModelProperty(notes = "集团管理员，管理集团数据")
    @JsonIgnore
    private List<ManagerGroupDetailReq> managerGroupInfos;


    public AdministratorsDetailResp setOrgName(String orgName) {
        this.orgName = orgName;
        return this;
    }

    public AdministratorsDetailResp setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    /**
     * 构建详情页
     *
     * @param baseUser
     * @return
     */
    public static AdministratorsDetailResp builderDetail(BaseUser baseUser, BaseUserDetail baseUserDetail) {
        AdministratorsDetailResp resp;
        if (baseUser == null || baseUserDetail == null) {
            return new AdministratorsDetailResp();
        } else {
            resp = new AdministratorsDetailResp();
            resp.setId(baseUser.getId());
            resp.setPhone(baseUser.getPhone());
            resp.setEmail(baseUser.getEmail());
            resp.setState(baseUserDetail.getState());
            resp.setLockFlag(baseUserDetail.getLockFlag() == null ? null : baseUserDetail.getLockFlag());
            resp.setRemark(baseUserDetail.getRemark());
            resp.setUserName(baseUser.getUserName());
            resp.setUserCode(baseUserDetail.getUserCode());
            resp.setManagerLevel(baseUser.getManagerLevel());
            resp.setContactEmail(baseUserDetail.getContactEmail());
            resp.setGroupId(baseUserDetail.getGroupId());
            resp.setOrgId(baseUserDetail.getOrgId());
            resp.setIdentityType(baseUserDetail.getIdentityType());
            resp.setRealName(baseUserDetail.getRealName());
            resp.setArchivesId(baseUserDetail.getArchivesId());
            resp.setSourceId(baseUserDetail.getSource());
            resp.setDetailId(baseUserDetail.getId());
        }
        return resp;
    }


    /**
     * 构建管理员列表页面
     *
     * @param baseUserInfo
     * @return
     */

    public static AdministratorsDetailResp builder(BaseUserInfo baseUserInfo) {
        AdministratorsDetailResp resp;
        if (baseUserInfo == null) {
            return new AdministratorsDetailResp();
        } else {
            resp = new AdministratorsDetailResp();
            resp.setId(baseUserInfo.getUserId());
            resp.setDetailId(baseUserInfo.getId());
            resp.setUserCode(baseUserInfo.getUserCode());
            resp.setUserName(baseUserInfo.getUserName());
            resp.setManagerLevel(baseUserInfo.getManagerLevel());
            resp.setEmail(baseUserInfo.getContactEmail());
            resp.setPhone(baseUserInfo.getPhone());
            resp.setState(baseUserInfo.getState());
            resp.setLockFlag(baseUserInfo.getLockFlag());
            resp.setRemark(baseUserInfo.getRemark());
            resp.setGroupId(baseUserInfo.getGroupId());
            resp.setOrgId(baseUserInfo.getOrgId());
            return resp;
        }
    }

    public String getLockFlagName() {
        if (this.lockFlag == null) {
            return this.lockFlagName;
        }
        if (Objects.equals(this.lockFlag, BooleanEnum.TRUE.getCode())) {
            return "已锁定";
        } else {
            return "未锁定";
        }
    }
}
