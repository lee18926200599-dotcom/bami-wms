package com.usercenter.server.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.common.util.util.AssertUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.org.client.feign.StaffFeign;
import com.org.permission.common.org.dto.OrganizationDto;
import com.org.permission.common.org.dto.StaffWithDutyInfoDto;
import com.org.permission.common.org.param.QueryByIdReqParam;
import com.org.permission.common.permission.dto.InputUserDto;
import com.org.permission.common.permission.dto.InputUserUpdateDto;
import com.usercenter.common.dto.UserSaveDto;
import com.usercenter.common.dto.request.UserSaveRespDTO;
import com.usercenter.common.enums.BusinessSystemEnum;
import com.usercenter.common.enums.FlagEnum;
import com.usercenter.server.common.enums.ReturnCodesEnum;
import com.usercenter.common.enums.UserManagerLevelEnum;
import com.usercenter.server.constant.command.enums.BaseUserManagerLevelEnum;
import com.usercenter.server.domain.condition.UserDetailCondition;
import com.usercenter.server.domain.service.PermissionDomainService;
import com.usercenter.server.domain.vo.req.groupuser.GroupUserListReq;
import com.usercenter.server.domain.vo.req.groupuser.GroupUserSaveReq;
import com.usercenter.server.domain.vo.req.groupuser.GroupUserUpdateReq;
import com.usercenter.server.domain.vo.resp.GroupUserDetailResp;
import com.usercenter.server.domain.vo.resp.PageResult;
import com.usercenter.server.entity.BaseUser;
import com.usercenter.server.entity.BaseUserDetail;
import com.usercenter.server.entity.BaseUserInfo;
import com.usercenter.server.entity.BaseUserStaffMap;
import com.usercenter.server.mapper.BaseUserDetailMapper;
import com.usercenter.server.domain.dto.UpdateUserDTO;
import com.usercenter.server.domain.service.OrgDomainService;
import com.usercenter.server.service.IBaseUserApiService;
import com.usercenter.server.service.IBaseUserGroupService;
import com.usercenter.server.service.IBaseUserStaffMapService;
import com.usercenter.server.utils.TranslateUtil;
import com.usercenter.server.utils.UserStaffUtil;
import com.usercenter.server.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * 用户（集团）服务
 */
@Slf4j
@Service
public class BaseUserGroupServiceImpl extends BaseUserCommonServiceImpl implements IBaseUserGroupService {

    @Autowired
    private BaseUserDetailMapper userDetailMapper;

    @Autowired
    private IBaseUserApiService userApiService;

    @Autowired
    private IBaseUserStaffMapService userStaffMapService;

    @Autowired
    private StaffFeign staffFeign;
    @Autowired
    private PermissionDomainService permissionDomainService;

    @Autowired
    private OrgDomainService orgDomainService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserSaveRespDTO add(GroupUserSaveReq saveReq) {
        UserSaveDto saveDTO = saveReq.buildSaveDTO();
        AssertUtils.isNotNull(saveReq.getOrgId(), I18nUtils.getMessage("user.check.orgid.notnull"));
        if (saveReq.getGroupId() == null) {
            OrganizationDto organizationDto = orgDomainService.getOrgInfoById(saveReq.getOrgId());
            AssertUtils.isNotNull(organizationDto, I18nUtils.getMessage("user.check.org.data.null"));
            saveDTO.setGroupId(organizationDto.getGroupId());
        }
        UserSaveRespDTO respDTO = userApiService.register(saveDTO);
        // 权限
        InputUserDto inputUserDto = new InputUserDto();
        BeanUtil.copyProperties(saveReq, inputUserDto);
        inputUserDto.setGroupId(UserUtil.getGroupId());// 当前集团
        inputUserDto.setUserId(respDTO.getId());
        inputUserDto.setLoginUserId(UserUtil.getUserId());
        inputUserDto.setLoginUserName(UserUtil.getUserName());
        inputUserDto.setRoleList(saveReq.getRoleList());
        RestMessage restMessage = permissionDomainService.insertUserPermission(inputUserDto);
        AssertUtils.isTrue(restMessage != null && restMessage.isSuccess(), I18nUtils.getMessage("user.check.permission.module.fail"));
        //更新人员的用户(主表id)
        if (Objects.nonNull(saveReq.getArchivesId())) {
            staffFeign.updateUserIdByStaffId(saveReq.getArchivesId(), saveDTO.getId());
        }
        return respDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(GroupUserUpdateReq updateReq) {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        BeanUtil.copyProperties(updateReq, updateUserDTO);
        updateUserDTO.setBusinessSystem(BusinessSystemEnum.PERMISSION.getCode());
        BaseUserDetail baseUserDetail = getDetailById(updateReq.getId());
        updateUserDTO.setUserAuthId(baseUserDetail.getUserId());
        updateCheck(updateUserDTO);

        //主表信息
        BaseUser baseUser = new BaseUser();
        baseUser.setId(baseUserDetail.getUserId());
        baseUser.setPhone(updateReq.getPhone());
        baseUser.setModifiedBy(UserUtil.getUserId());
        baseUser.setModifiedName(UserUtil.getUserName());
        baseUser.setModifiedDate(new Date());
        baseUser.setUserName(updateReq.getUserName());
        baseUser.setEmail(updateReq.getEmail());
        //细表信息
        BaseUserDetail detail = new BaseUserDetail();
        detail.setId(updateReq.getId());
        detail.setOrgId(updateReq.getOrgId());
        detail.setGroupId(updateReq.getGroupId());
        detail.setRealName(updateReq.getRealName());
        detail.setIdentityType(updateReq.getIdentityType());
        detail.setRemark(updateReq.getRemark());
        detail.setArchivesId(updateReq.getArchivesId());
        detail.setContactEmail(updateReq.getContactEmail());
        detail.setSource(updateReq.getSource());
        detail.setModifiedBy(UserUtil.getUserId());
        detail.setModifiedName(UserUtil.getUserName());
        detail.setModifiedDate(new Date());
        baseUser.setDetailList(Arrays.asList(detail));
        userApiService.updateUser(baseUser);
        //用户人员映射
        if (detail.getArchivesId() != null) {
            userStaffMapService.deleteOtherMapping(new BaseUserStaffMap(detail.getId(), detail.getArchivesId(), null, null));
            userStaffMapService.saveCaseNotExist(new BaseUserStaffMap(detail.getId(), detail.getArchivesId(), null, null));
        }
        //删除其他映射
        //同步权限信息
        InputUserUpdateDto inputUserUpdateDto = new InputUserUpdateDto();
        BeanUtil.copyProperties(updateReq, inputUserUpdateDto);
        inputUserUpdateDto.setLoginUserId(UserUtil.getUserId());
        inputUserUpdateDto.setLoginUserName(UserUtil.getUserName());
        inputUserUpdateDto.setUserId(baseUserDetail.getId());
        if (CollectionUtils.isEmpty(inputUserUpdateDto.getDataList())) {
            inputUserUpdateDto.setDataList(Lists.newArrayList());
        }
        if (CollectionUtils.isEmpty(inputUserUpdateDto.getFuncList())) {
            inputUserUpdateDto.setFuncList(Lists.newArrayList());
        }
        if (CollectionUtils.isEmpty(inputUserUpdateDto.getOrgList())) {
            inputUserUpdateDto.setOrgList(Lists.newArrayList());
        }
        if (CollectionUtils.isEmpty(inputUserUpdateDto.getRoleList())) {
            inputUserUpdateDto.setRoleList(Lists.newArrayList());
        }
        log.info("同步用户角色权限参数:{}", JSON.toJSONString(inputUserUpdateDto));
        RestMessage restMessage = permissionDomainService.updateUserPermission(inputUserUpdateDto);
        log.info("同步用户角色权限出参:{}", JSON.toJSONString(restMessage));
    }

    @Override
    public PageResult<GroupUserDetailResp> getPage(GroupUserListReq req) {
        UserDetailCondition condition = new UserDetailCondition();
        BeanUtil.copyProperties(req, condition, "userName", "userCode", "phone", "contactEmail");
        condition.setUserName(null);
        condition.setUserId(null);
        condition.setDeletedFlag(FlagEnum.FALSE.getCode());
        condition.setManagerLevel(BaseUserManagerLevelEnum.USER.getCode());
        condition.addLike("bu.user_name", req.getUserName());
        condition.addLike("bu.user_code", req.getUserCode());
        condition.addLike("bu.phone", req.getPhone());
        condition.addLike("bud.contact_email", req.getContactEmail());
        if (!ObjectUtils.isEmpty(req.getOrgId())) {
            condition.setOrgIds(Sets.newHashSet(req.getOrgId()));
        }
        BaseUserDetail userDetail = getDetailById(UserUtil.getUserId());
        AssertUtils.notNull(userDetail, ReturnCodesEnum.USER_NOTEXISTS.getMessage());
        condition.setGroupId(UserUtil.getGroupId());
        //如果是普通用户，需要过滤用户组织权限
        if (userDetail.getManagerLevel() == UserManagerLevelEnum.USER.getCode()) {
            //  过滤组织权限
            Set<Long> orgIds = permissionDomainService.getOrgsByUidAndGroupId(userDetail.getId(), req.getGroupId());
            if (CollectionUtils.isNotEmpty(orgIds)) {
                condition.setOrgIds(orgIds);
            }
            //如果是全局管理员，查询所有用户
        } else if (userDetail.getManagerLevel() < UserManagerLevelEnum.GROUP_ADMINI.getCode().intValue()) {
            condition.setGroupId(null);
        }
        Page<Object> page = PageHelper.startPage(req.getPageNum(), req.getPageSize());
        List<BaseUserInfo> baseUserInfoList = userDetailMapper.selectDetailList(condition);
        // 补充人员信息
        UserStaffUtil.setStaffInfo(baseUserInfoList);
        List<GroupUserDetailResp> list = new ArrayList<>();
        for (BaseUserInfo baseUserInfo : baseUserInfoList) {
            list.add(BeanUtil.copyProperties(baseUserInfo, GroupUserDetailResp.class));
        }
        TranslateUtil.translateOrgIds(list, new String[]{"orgId:name>orgName"});
        PageResult<GroupUserDetailResp> result = new PageResult<>(list, page.toPageInfo());
        return result;
    }

    @Override
    public GroupUserDetailResp getDetail(Long id) {
        UserDetailCondition condition = new UserDetailCondition();
        condition.setId(id);
        BaseUserInfo baseUserInfo = userDetailMapper.selectDetail(condition);
        GroupUserDetailResp resp = new GroupUserDetailResp();
        BeanUtil.copyProperties(baseUserInfo, resp);
        // 赋值组织名称和真实姓名
        OrganizationDto organizationDto = orgDomainService.getOrgInfoById(resp.getOrgId());
        if (organizationDto != null) {
            resp.setOrgName(organizationDto.getOrgName());
        }
        RestMessage<StaffWithDutyInfoDto> staffWithDutyInfoDtoRestMessage = staffFeign.queryStaff(new QueryByIdReqParam(resp.getArchivesId()));
        if (staffWithDutyInfoDtoRestMessage.isSuccess() && staffWithDutyInfoDtoRestMessage.getData() != null) {
            resp.setRealName(staffWithDutyInfoDtoRestMessage.getData().getRealname());
        }
        return resp;
    }
}
