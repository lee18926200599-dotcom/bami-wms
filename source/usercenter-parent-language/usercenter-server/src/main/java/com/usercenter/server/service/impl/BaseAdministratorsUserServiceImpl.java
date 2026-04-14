package com.usercenter.server.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.common.base.enums.BooleanEnum;
import com.common.framework.user.FplUserUtil;
import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.common.util.util.AssertUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.org.client.feign.StaffFeign;
import com.org.permission.common.org.dto.OrganizationDto;
import com.usercenter.common.dto.FplUser;
import com.usercenter.common.dto.UserDto;
import com.usercenter.common.dto.UserSaveDto;
import com.usercenter.common.dto.request.UserSaveRespDTO;
import com.usercenter.common.enums.*;
import com.usercenter.server.constant.DictionaryConstants;
import com.usercenter.server.constant.command.enums.BaseUserManagerLevelEnum;
import com.usercenter.server.domain.service.DictionaryDomainService;
import com.usercenter.server.domain.vo.req.administratorsuser.AdministratorsQueryReq;
import com.usercenter.server.domain.vo.req.administratorsuser.AdministratorsSaveUpdateReq;
import com.usercenter.server.domain.vo.resp.AdministratorAddResp;
import com.usercenter.server.domain.vo.resp.AdministratorsDetailResp;
import com.usercenter.server.domain.vo.resp.PageResult;
import com.usercenter.server.entity.BaseUser;
import com.usercenter.server.entity.BaseUserDetail;
import com.usercenter.server.entity.BaseUserInfo;
import com.usercenter.server.common.exception.WarnException;
import com.usercenter.server.domain.dto.UpdateUserDTO;
import com.usercenter.server.service.IBaseAdministratorsUserService;
import com.usercenter.server.service.IBaseUserApiService;
import com.usercenter.server.utils.PasswordEncoder;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 管理员管理服务
 */
@Service
public class BaseAdministratorsUserServiceImpl extends AbstractBaseAdministratorsUserService implements IBaseAdministratorsUserService {


    private static final Logger LOGGER = LoggerFactory.getLogger(BaseAdministratorsUserServiceImpl.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${user.id.generated.name:ucenter}")
    private String idGeneratedName;

    @Autowired
    private IBaseUserApiService userApiService;
    @Autowired
    private StaffFeign staffFeign;

    @Autowired
    private DictionaryDomainService dictionaryDomainService;

    /**
     * 管理员列表更新
     *
     * @param req
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updateAdministrators(AdministratorsSaveUpdateReq req) {
        String remark = req.getRemark();
        AtomicInteger update = new AtomicInteger(0);
        //更新必要条件。主表ID存在
        if (req.getId() == null || req.getDetailId() == null) {
            throw new IllegalArgumentException(I18nUtils.getMessage("user.check.id.notnull"));
        }
        BaseUser baseUser = req.generatorBaseUserAndDetail();
        List<BaseUserDetail> detailList = baseUser.getDetailList();
        BaseUserDetail baseUserDetail = detailList.get(0);
        if (detailList.size() != 1) {
            throw new IllegalArgumentException(I18nUtils.getMessage("user.check.param.error"));
        }

        //判断账户唯一
        UserDto userDto = new UserDto();
        userDto.setUserName(req.getUserName());
        List<FplUser> userList = userApiService.findUserList(userDto);
        if (CollectionUtils.isNotEmpty(userList)){
            List<FplUser> fplUsers = userList.stream().filter(x -> !x.getUserAuthId().equals(req.getId())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(fplUsers)){
                throw new WarnException(I18nUtils.getMessage("user.check.account.exist"));
            }
        }

        UpdateUserDTO updateUserDTO = req.generatorUpdateUserDto();
        updateUserDTO.setBusinessSystem(BusinessSystemEnum.PERMISSION.getCode());
        //更新参数校验
        updateCheck(updateUserDTO);
        //全局管理员【更新】
        if (UserManagerLevelEnum.GLOBAL_ADMINI.getCode().equals(req.getManagerLevel())) {
            BaseUserDetail params = new BaseUserDetail();
            params.setId(req.getDetailId());
            params.setRemark(remark);
            update.getAndAdd(super.baseUserDetailMapper.update(params));
        }
        //集团管理员【更新】
        if (UserManagerLevelEnum.GROUP_ADMINI.getCode().equals(req.getManagerLevel())) {
            BaseUser userQuery = new BaseUser();
            userQuery.setId(req.getId());
            BaseUser originBaseUser = baseUserMapper.loadByParam(userQuery);
            if (originBaseUser == null) {
                LOGGER.error("用户不存在,id{}", req.getId());
                throw new IllegalArgumentException(I18nUtils.getMessage("user.check.user.noexist"));
            }
            baseUser.setModifiedDate(new Date());
            baseUser.setModifiedBy(req.getUserId());
            baseUserMapper.update(baseUser);
            baseUserDetail.setModifiedDate(new Date());
            baseUserDetail.setModifiedBy(req.getUserId());
            update.getAndAdd(baseUserDetailMapper.update(baseUserDetail));
            if (req.getArchivesId() != null) {
                staffFeign.updateUserIdByStaffId(req.getArchivesId(), req.getDetailId());
            }
        }
        permissionAPI(req, req.getDetailId(), req.getUserName(), FplUserUtil.getUserName());
        return update.get();
    }

    /**
     * 条件查找管理员列表
     *
     * @param req
     * @return
     */
    @Override
    public PageResult<AdministratorsDetailResp> getAdministratorListByPage(AdministratorsQueryReq req, Integer level) {
        Page<Object> objects = PageHelper.startPage(req.getPageNum(), req.getPageSize());
        //管理数据递减
        if (level != null) {
            level = level + 1;
        }
        req.setLevel(level);
        //如果是全局管理员。直接查找
        //如果是集团管理员。则联查组织的表
        List<BaseUserInfo> baseUsers = baseUserDetailMapper.selectAdministratorByQuery(req);
        //获取所有的集团
        List<Long> orgIds = new ArrayList<>();
        baseUsers.forEach(bu -> {
            if (bu.getGroupId() != null) {
                orgIds.add(bu.getGroupId());
            }
            if (bu.getOrgId() != null) {
                orgIds.add(bu.getOrgId());
            }
        });
        Map<Long, String> groupNames = super.getOrgNames(orgIds);
        List<AdministratorsDetailResp> list = new ArrayList<>();
        baseUsers.forEach(baseUser -> list.add(AdministratorsDetailResp.builder(baseUser).setGroupName(groupNames.get(baseUser.getGroupId())).setOrgName(getOrgName(baseUser.getOrgId()))));
        PageResult<AdministratorsDetailResp> result = new PageResult<>(list, objects.toPageInfo());
        return result;
    }


    /**
     * 新增管理员接口
     *
     * @param req
     * @return
     */
    @Override
    public AdministratorAddResp addAdministrator(AdministratorsSaveUpdateReq req) {
        AdministratorAddResp resp = new AdministratorAddResp();
        UserSaveDto userSaveDTO = new UserSaveDto();
        userSaveDTO.setSource(req.getSourceId().toString());
        if (BaseUserManagerLevelEnum.GLOBAL_ADMINISTRATORS.getCode().equals(req.getManagerLevel())) {
            userSaveDTO.setGlobalFlag(BooleanEnum.TRUE.getCode());
        } else {
            userSaveDTO.setGlobalFlag(BooleanEnum.FALSE.getCode());
        }
        userSaveDTO.setArchivesId(req.getArchivesId());
        userSaveDTO.setBusinessSystem(BusinessSystemEnum.BOSS.getCode());
        userSaveDTO.setContactEmail(req.getContactEmail());
        userSaveDTO.setCreatedBy(req.getUserId());
        userSaveDTO.setEmail(req.getEmail());
        userSaveDTO.setState(UserStateEnum.UNENABLE.getCode());
        userSaveDTO.setFirstTimeLoginFlag(FlagEnum.TRUE.getCode());
        Long groupId = req.getGroupId();
        if (ObjectUtil.isNull(groupId)) {
            OrganizationDto organizationDto = orgDomainService.getOrgInfoById(req.getOrgId());
            AssertUtils.isNotNull(organizationDto, I18nUtils.getMessage("user.check.org.data.null"));
            groupId = organizationDto.getGroupId();
        }
        userSaveDTO.setGroupId(groupId);
        userSaveDTO.setOrgId(req.getOrgId());
        userSaveDTO.setIdentityType(req.getIdentityType());
        userSaveDTO.setLockFlag(FlagEnum.FALSE.getCode());
        userSaveDTO.setManagerLevel(req.getManagerLevel());
        userSaveDTO.setPasswordFlag(FlagEnum.TRUE.getCode());
        userSaveDTO.setPhone(req.getPhone());
        userSaveDTO.setRemark(req.getRemark());
        userSaveDTO.setRealName(req.getRealName());
        userSaveDTO.setSourceType(SourceTypeEnum.PC.getCode());
        //如果是未存在【如果是新增集团管理员。则用户账户，传递，全局管理员。生成】
        userSaveDTO.setUserName(req.getUserName());

        //判断账户唯一
        UserDto userDto = new UserDto();
        userDto.setUserName(userSaveDTO.getUserName());
        List<FplUser> userList = userApiService.findUserList(userDto);
        if (CollectionUtils.isNotEmpty(userList)){
            throw new WarnException(I18nUtils.getMessage("user.check.account.exist"));
        }

        UserSaveRespDTO register = userApiService.register(userSaveDTO);
        if (req.getArchivesId() != null) {
            RestMessage<Integer> restMessage = staffFeign.updateUserIdByStaffId(req.getArchivesId(), register.getId());
            LOGGER.info("新增管理员,维护员工信息返回结果:{},staffId:{},userId:{}", restMessage, req.getArchivesId(), register.getId());
        }
        resp.setUserName(register.getUserName());
        resp.setShowPassword(register.getPassword() == null ? false : true);
        resp.setPassword(register.getPassword());
        permissionAPI(req, register.getId(), req.getUserName(),FplUserUtil.getUserName());
        return resp;
    }

    @Override
    public AdministratorsDetailResp getAdministratorDetail(Long id) {
        BaseUserDetail params = new BaseUserDetail();
        params.setId(id);
        params.setDeletedFlag(FlagEnum.FALSE.getCode());
        BaseUserDetail baseUserDetail = baseUserDetailMapper.loadByParam(params);
        if (baseUserDetail == null) {
            LOGGER.error("查找业务信息不存在,detailId:{}", id);
            return null;
        }
        BaseUser baseUser = baseUserMapper.load(baseUserDetail.getUserId());
        if (baseUser == null) {
            LOGGER.error("查找用户信息不存在,id:{}", baseUserDetail.getUserId());
            return null;
        }
        //构建详情
        AdministratorsDetailResp builder = AdministratorsDetailResp.builderDetail(baseUser, baseUserDetail);
        //所属组织字段翻译
        builder.setOrgName(getOrgName(builder.getOrgId()));
        //数据字段翻译
        if (builder.getSourceId() != null) {
            Map<String, String> sourceMap = dictionaryDomainService.convertToMap(dictionaryDomainService.getItemList(DictionaryConstants.FPL_USER_REGISTER_SOURCE, builder.getSourceId()));
            builder.setSourceName(sourceMap.get(String.valueOf(builder.getSourceId())));
        }
        return builder;
    }
}
