package com.org.permission.server.domain.user;

import com.alibaba.fastjson.JSON;
import com.common.language.util.I18nUtils;
import com.org.permission.common.dto.crm.CustInfoDomainDto;
import com.org.permission.common.dto.crm.LinkerInfoReqParam;
import com.org.permission.common.org.dto.OrgUser;
import com.org.permission.common.org.vo.BaseInfoVo;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.BizUnitWithFuncInfoBean;
import com.org.permission.server.org.bean.StaffBean;
import com.org.permission.server.org.enums.RegistSourceEnum;
import com.common.util.message.RestMessage;
import com.common.base.enums.BooleanEnum;
import com.common.base.enums.StateEnum;
import com.common.framework.redis.RedisUtil;
import com.usercenter.client.feign.UserFeign;
import com.usercenter.common.dto.FplUser;
import com.usercenter.common.dto.UserDto;
import com.usercenter.common.dto.UserSaveDto;
import com.usercenter.common.dto.request.BatchUpdateReq;
import com.usercenter.common.dto.request.UserInfoListDto;
import com.usercenter.common.enums.BusinessSystemEnum;
import com.usercenter.common.enums.ReturnCodesEnum;
import com.usercenter.common.enums.SourceTypeEnum;
import com.usercenter.common.enums.UserManagerLevelEnum;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * 用户领域封装
 */
@Service(value = "userDomainService")
public class UserDomainService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDomainService.class);
    private static final String INIT_ADMIN_REMARK = "初始集团管理员";

    private static final String STAFF_GEN_USER_REMARK = "人员生成用户";

    private static final String ADDMIN_USER_NAME = "_01";

    /**
     * 虚拟手机号
     */
    private static final String VIRTUAL_PHONE_PREFFIX = "4PL_USERCENTER_VIRTUAL_PHONE";

    /**
     * 虚拟手机号基数
     */
    private static Long VIRTUAL_PHONE_BASE = 10000000000L;

    @Resource
    private UserFeign userFeign;

    @Autowired
    protected RedisUtil client;

    /**
     * 初始化集团管理员
     *
     * @param createUser    创建人
     * @param custInfo      客商信息
     * @param rootBizUnitId 根业务单元ID
     * @param groupId       集团ID
     */
    public OrgUser initAdministrator(Long createUser, final CustInfoDomainDto custInfo, final Long rootBizUnitId, final Long groupId) {
        UserSaveDto user = buildUser(createUser, rootBizUnitId, groupId, custInfo);
        user.setPhone(generateVirtualPhone());//集团管理员手机号码强制至为空！
        user.setEmail("");//集团管理员电子邮件强制至为空！
        return registUser(user);
    }

    /**
     * 生成虚拟手机号
     */
    public String generateVirtualPhone() {
        Long nextValue = client.incr(VIRTUAL_PHONE_PREFFIX, 1);
        return VIRTUAL_PHONE_BASE + nextValue + "";
    }

    /**
     * 填充用户名字
     *
     * @param voList 基础展示信息
     */
    public void batchFillUserName(List<? extends BaseInfoVo> voList) {
        if (CollectionUtils.isEmpty(voList)) {
            return;
        }

        Set<Long> userIds = new HashSet<>();
        for (BaseInfoVo baseInfoVo : voList) {
            final Long createdBy = baseInfoVo.getCreatedBy();
            if (createdBy != null) {
                userIds.add(createdBy);
            }
            final Long modifiedBy = baseInfoVo.getModifiedBy();
            if (modifiedBy != null) {
                userIds.add(modifiedBy);
            }
        }

        final Map<Long, FplUser> userNameMap = batchGetUserName(userIds);

        for (BaseInfoVo baseInfoVo : voList) {
            final Long createdBy = baseInfoVo.getCreatedBy();
            if (createdBy != null) {
                if (!ObjectUtils.isEmpty(userNameMap.get(createdBy))) {
                    final String userName = userNameMap.get(createdBy).getUserName();
                    if (!StringUtils.isEmpty(userName)) {
                        baseInfoVo.setCreatedName(userName);
                    }
                }
            }
            final Long modifiedBy = baseInfoVo.getModifiedBy();
            if (modifiedBy != null) {
                if (!ObjectUtils.isEmpty(userNameMap.get(modifiedBy))) {
                    final String userName = userNameMap.get(modifiedBy).getUserName();
                    if (!StringUtils.isEmpty(userName)) {
                        baseInfoVo.setModifiedName(userName);
                    }
                }
            }
        }
    }

    /**
     * 填充用户名字
     *
     * @param voList 基础展示信息
     */
    public void batchFillUserName4Bean(final List<BizUnitWithFuncInfoBean> voList) {
        if (CollectionUtils.isEmpty(voList)) {
            return;
        }

        Set<Long> userIds = new HashSet<>();
        for (BizUnitWithFuncInfoBean baseInfoVo : voList) {
            final Long createdBy = baseInfoVo.getCreatedBy();
            if (createdBy != null) {
                userIds.add(createdBy);
            }
            final Long modifiedBy = baseInfoVo.getModifiedBy();
            if (modifiedBy != null) {
                userIds.add(modifiedBy);
            }
        }

        final Map<Long, FplUser> userNameMap = batchGetUserName(userIds);

        for (BizUnitWithFuncInfoBean baseInfoVo : voList) {
            final Long createdBy = baseInfoVo.getCreatedBy();
            if (createdBy != null) {
                if (!ObjectUtils.isEmpty(userNameMap.get(createdBy))) {
                    final String userName = userNameMap.get(createdBy).getUserName();
                    if (!StringUtils.isEmpty(userName)) {
                        baseInfoVo.setCreatedName(userName);
                    }
                }
            }
            final Long modifiedBy = baseInfoVo.getModifiedBy();
            if (modifiedBy != null) {
                if (!ObjectUtils.isEmpty(userNameMap.get(modifiedBy))) {
                    final String userName = userNameMap.get(modifiedBy).getUserName();
                    if (!StringUtils.isEmpty(userName)) {
                        baseInfoVo.setModifiedName(userName);
                    }
                }
            }
        }
    }


    /**
     * 填充用户名字
     *
     * @param baseInfoVo 基础展示信息
     */
    public <T extends BaseInfoVo> T singleFillUserName(T baseInfoVo) {
        if (ObjectUtils.isEmpty(baseInfoVo)) {
            return baseInfoVo;
        }

        Set<Long> userIds = new HashSet<>();
        final Long createdBy = baseInfoVo.getCreatedBy();
        if (createdBy != null) {
            userIds.add(createdBy);
        }
        final Long modifiedBy = baseInfoVo.getModifiedBy();
        if (modifiedBy != null) {
            userIds.add(modifiedBy);
        }

        final Map<Long, FplUser> userNameMap = batchGetUserName(userIds);

        if (createdBy != null) {

            if (!ObjectUtils.isEmpty(userNameMap.get(createdBy))) {
                final String userName = userNameMap.get(createdBy).getUserName();
                if (!StringUtils.isEmpty(userName)) {
                    baseInfoVo.setCreatedName(userName);
                }
            }
        }
        if (modifiedBy != null) {
            if (!ObjectUtils.isEmpty(userNameMap.get(modifiedBy))) {
                final String userName = userNameMap.get(modifiedBy).getUserName();
                if (!StringUtils.isEmpty(userName)) {
                    baseInfoVo.setModifiedName(userName);
                }
            }
        }
        return baseInfoVo;
    }

    /**
     * 批量获取用户名字信息
     *
     * @param userIds 用户 ID 集合
     * @return 用户 ID name Map
     */
    public Map<Long, FplUser> batchGetUserName(Set<Long> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return Collections.emptyMap();
        }

        Map<Long, FplUser> idNameMap = new HashMap<>(userIds.size());
        try {
            UserInfoListDto userInfoListDto = new UserInfoListDto();
            userInfoListDto.setUserIds(new ArrayList<>(userIds));
            final RestMessage<List<FplUser>> queryResult = userFeign.getUserInfoList(userInfoListDto);
            if (!queryResult.isSuccess()) {
                LOGGER.error("query user name failed,param:{}.", userIds);
                throw new OrgException(OrgErrorCode.USER_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.user.system.exception") + ":" + queryResult.getMessage());
            }

            final List<FplUser> userList = queryResult.getData();
            if (CollectionUtils.isEmpty(userList)) {
                return Collections.emptyMap();
            }
            for (FplUser user : userList) {
                idNameMap.put(user.getId(), user);
            }
        } catch (Exception ex) {
            LOGGER.error("query user name error.", ex);
            throw new OrgException(OrgErrorCode.USER_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.user.system.exception") + ":" + ex.getMessage());
        }
        return idNameMap;
    }

    /**
     * 人员生成用户
     *
     * @param staff    人员信息
     * @param operater 操作人ID
     */
    public OrgUser staffGenerateUser(StaffBean staff, Long operater) {
        UserSaveDto user = buildStaffUser(staff, operater);
        return registUser(user);
    }

    /**
     * 批量停用人员生成用户S
     *
     * @param userIds    人员绑定用户ID
     * @param operaterId 操作人ID
     */
    public void batchDisableUser(List<Long> userIds, Long operaterId) {
        if (CollectionUtils.isEmpty(userIds)) {
            return;
        }
        try {
            LOGGER.info("batch disable user ,param:{}.", userIds);
            BatchUpdateReq batchUpdateReq = new BatchUpdateReq();
            batchUpdateReq.setIds(new HashSet<>(userIds));
            batchUpdateReq.setOperate(2);// 2=停用
            userFeign.batchUpdate(batchUpdateReq);
        } catch (Exception ex) {
            LOGGER.error("batch disable user error,param:" + userIds, ex);
            throw new OrgException(OrgErrorCode.USER_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.user.system.exception") + ":" + ex.getMessage());
        }
    }

    public void updateEnableUserByGroupId(Long operaterId, Long groupId, Integer enable) {
        try {
            Assert.notNull(groupId, "请传入集团id的值");
            Assert.notNull(operaterId, "请传入操作者的id值");

            userFeign.updateEnableByGroupId(groupId, enable);
        } catch (IllegalArgumentException ex) {
            LOGGER.warn(ex.hashCode() + ex.getMessage());
        } catch (Exception e) {
            LOGGER.error("UpdateEnableUserByGroupId error,param:" + groupId, e);
            throw new OrgException(OrgErrorCode.USER_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.user.system.exception") + ":" + e.getMessage());
        }
    }

    public Long findByPhone(String phone) {
        try {
            UserDto userDto = new UserDto();
            userDto.setPhone(phone);
            userDto.setLockFlag(BooleanEnum.FALSE.getCode());
            userDto.setState(StateEnum.ENABLE.getCode());
            userDto.setDeletedFlag(BooleanEnum.FALSE.getCode());
            RestMessage<List<FplUser>> restMessage = userFeign.findUserList(userDto);
            if (restMessage.getData() != null && restMessage.getData().size() > 0 && restMessage.getData().size() == 1) {
                return restMessage.getData().get(0).getId();
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.error("UpdateEnableUserByGroupId error,param:" + phone, e);
            throw new OrgException(OrgErrorCode.USER_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.user.system.exception") + ":" + e.getMessage());
        }
    }

    /**
     * 注册用户
     *
     * @param userSaveDto 用户信息
     * @return 用户ID
     */
    private OrgUser registUser(UserSaveDto userSaveDto) {
        RestMessage<FplUser> restMessage;
        try {
            LOGGER.info("staff generate user param:{}.", JSON.toJSONString(userSaveDto));
            userSaveDto.setBusinessSystem(BusinessSystemEnum.ORGANIZATION.getCode());
            userSaveDto.setSourceType(SourceTypeEnum.PC.getCode());
            restMessage = userFeign.register(userSaveDto);
        } catch (Exception ex) {
            LOGGER.error("staff generate user error,param:" + JSON.toJSONString(userSaveDto), ex);
            throw new OrgException(OrgErrorCode.USER_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.user.system.exception") + ":" + ex.getMessage());
        }
        if (!restMessage.isSuccess()) {
            LOGGER.info("staff generate user failed ,param:{}.", JSON.toJSONString(userSaveDto));

            throw new OrgException(OrgErrorCode.USER_SYSTEM_ERROR_CODE, restMessage.getMessage());
        }
        final FplUser registUser = restMessage.getData();
        OrgUser orgUser = new OrgUser();
        orgUser.setUserId(registUser.getId());
        //如果是已经注册过的用户，提示使用原密码登录使用
        if (Objects.equals(restMessage.getCode(), Integer.valueOf(ReturnCodesEnum.USERNAME_EXISTS.getCode()))) {
            orgUser.setPassword(I18nUtils.getMessage("org.domain.user.use.original.password"));
        } else {
            orgUser.setPassword(registUser.getPassword());
        }
        orgUser.setUserName(registUser.getUserName());
        orgUser.setGroupId(registUser.getGroupId());
        orgUser.setRootBUId(registUser.getOrgId());
        return orgUser;
    }

    /**
     * 构建人员用户信息
     *
     * @param staff    人员信息
     * @param operater 操作人
     * @return 用户信息
     */
    private UserSaveDto buildStaffUser(StaffBean staff, Long operater) {
        UserSaveDto user = new UserSaveDto();
        user.setCreatedBy(operater);
        user.setArchivesId(staff.getId());
        user.setGroupId(staff.getGroupId());
        user.setManagerLevel(3);
        user.setOrgId(staff.getOrgId());
        user.setSource(RegistSourceEnum.BOSS.getValue() + "");
        user.setUserName(staff.getStaffCode());
        user.setHeadImg("");
        user.setIdentityType(0);
        user.setRealName(staff.getRealname());
        user.setRemark(STAFF_GEN_USER_REMARK);
        user.setPhone(staff.getPhone());
        return user;
    }

    /**
     * 注册用户构造参数
     *
     * @param createUser    创建人
     * @param rootBizUnitId 更业务单元ID
     * @param groupId       集团ID
     * @param custInfo      客户信息
     * @return 注册用户
     */
    private UserSaveDto buildUser(Long createUser, Long rootBizUnitId, Long groupId, final CustInfoDomainDto custInfo) {
        final LinkerInfoReqParam linkerInfo = custInfo.getLinkerInfo();
        String userName = custInfo.getUserCode() + ADDMIN_USER_NAME;
        UserSaveDto userSaveDto = new UserSaveDto();
        userSaveDto.setCreatedBy(createUser);
        userSaveDto.setGroupId(groupId);
        userSaveDto.setManagerLevel(UserManagerLevelEnum.GROUP_ADMINI.getCode());
        userSaveDto.setOrgId(rootBizUnitId);
        userSaveDto.setSource(RegistSourceEnum.BOSS.getValue() + "");
        userSaveDto.setUserName(userName);
        userSaveDto.setHeadImg("");
        userSaveDto.setIdentityType(0);
        userSaveDto.setRealName(StringUtils.isBlank(custInfo.getRealName()) ? I18nUtils.getMessage("org.domain.user.group.admin") : custInfo.getRealName());
        userSaveDto.setRemark(INIT_ADMIN_REMARK);
        userSaveDto.setPhone(custInfo.getPhone());
        if (linkerInfo != null) {
            final String email = linkerInfo.getEmail();
            if (!StringUtils.isEmpty(email)) {
                userSaveDto.setEmail(email);
            }
            final String phone = linkerInfo.getPhone();
            if (!StringUtils.isEmpty(phone)) {
                userSaveDto.setPhone(phone);
            }
        }
        return userSaveDto;
    }

    /**
     * 用户删除
     *
     * @param userId
     * @return
     */
    public String delete(Long userId) {
        try {
            BatchUpdateReq batchUpdateReq = new BatchUpdateReq();
            batchUpdateReq.setUserId(userId);
            batchUpdateReq.setOperate(2);// 2=停用
            RestMessage<String> disabled = userFeign.batchUpdate(batchUpdateReq);
            if (disabled.isSuccess()) {
                batchUpdateReq.setOperate(6);// 6=删除
                RestMessage<String> delete = userFeign.batchUpdate(batchUpdateReq);
                return delete.getData();
            }
        } catch (Exception e) {
            LOGGER.warn("用户服务异常" + e.getMessage());
            throw new OrgException(OrgErrorCode.USER_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.user.system.exception") + ":"  + e.getMessage());
        }
        return null;
    }

    /**
     * 小程序用户更新停用
     *
     * @param userId
     * @return
     */
    public FplUser updateUser(Long userId) {
        try {
            System.out.println("用户更新开始");
            long start = System.currentTimeMillis();
            FplUser user = new FplUser();
            user.setId(userId);
            user.setState(StateEnum.CREATE.getCode());
            user.setDeletedFlag(BooleanEnum.TRUE.getCode());
            RestMessage<FplUser> result = userFeign.update(user);
            System.out.println("用户更新结束");
            long end = System.currentTimeMillis();
            System.out.println("总共用了：" + (end - start));
            if (result.isSuccess()) {
                return user;
            }
        } catch (Exception e) {
            LOGGER.warn("用户服务异常" + e.getMessage());
            throw new OrgException(OrgErrorCode.USER_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.user.system.exception") + ":" + e.getMessage());
        }
        return null;
    }

    /**
     * 根据用户id绑定人员id
     *
     * @param userId
     * @param staffId
     * @return
     */
    public String updateStaffId(Long userId, Long staffId) {
        try {
            RestMessage<String> restMessage = userFeign.updateStaffId(userId, staffId);
            return restMessage.getData();
        } catch (Exception e) {
            LOGGER.warn("用户服务异常" + e.getMessage());
            throw new OrgException(OrgErrorCode.USER_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.user.system.exception") + ":" + e.getMessage());
        }
    }

    /**
     * 根据用户id更新手机号
     *
     * @param user
     * @return
     */
    public RestMessage<FplUser> update(FplUser user) {
        try {
            RestMessage<FplUser> restMessage = userFeign.update(user);
            return restMessage;
        } catch (Exception e) {
            LOGGER.warn("用户服务异常" + e.getMessage());
            throw new OrgException(OrgErrorCode.USER_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.user.system.exception") + ":" + e.getMessage());
        }
    }

    /**
     * 用户停用
     *
     * @param userId
     * @return
     */
    public String disabled(Long userId) {
        try {
            BatchUpdateReq batchUpdateReq = new BatchUpdateReq();
            batchUpdateReq.setUserId(userId);
            batchUpdateReq.setOperate(2);// 2=停用
            RestMessage<String> dataResult = userFeign.batchUpdate(batchUpdateReq);
            return dataResult.getData();
        } catch (Exception e) {
            LOGGER.warn("用户服务异常" + e.getMessage());
            throw new OrgException(OrgErrorCode.USER_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.user.system.exception") + ":" + e.getMessage());
        }
    }


    public Map<Long, FplUser> getUserInfoMap(Set<Long> userIds) {
        try {
            RestMessage<Map<Long, FplUser>> userInfoMap = userFeign.getUserInfoMap(userIds);
            if (!ObjectUtils.isEmpty(userInfoMap)) {
                return userInfoMap.getData();
            }
        } catch (Exception e) {
            LOGGER.warn("用户服务异常" + e.getMessage());
            throw new OrgException(OrgErrorCode.USER_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.user.system.exception") + ":"  + e.getMessage());
        }
        return null;
    }

    //判断用户是否为4pl true是
    public Boolean isFourPL(Long userId) {
        Boolean flag = false;
        try {
            RestMessage<FplUser> user = userFeign.getUserInfo(userId);
            if (!ObjectUtils.isEmpty(user.getData())) {
                if (1 == user.getData().getManagerLevel()) {
                    flag = true;
                }
            }
        } catch (Exception e) {
            LOGGER.warn("用户服务异常" + e.getMessage());
            throw new OrgException(OrgErrorCode.USER_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.user.system.exception") + ":" + e.getMessage());
        }
        return flag;
    }

    public RestMessage<List<FplUser>> findUserList(UserDto dto) {
        try {
            RestMessage<List<FplUser>> list = userFeign.findUserList(dto);
            return list;
        } catch (Exception e) {
            LOGGER.warn("用户服务异常" + e.getMessage());
            throw new OrgException(OrgErrorCode.USER_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.user.system.exception") + ":" + e.getMessage());
        }
    }

    public FplUser getUserInfo(Long userId) {
        if (userId == null || userId <= 0) {
            return null;
        }
        RestMessage<FplUser> restMessage = userFeign.getUserInfo(userId);
        return restMessage.getData();
    }
}
