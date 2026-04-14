package com.usercenter.server.service.command.updateusercommand.strategy;

import com.alibaba.fastjson.JSONObject;
import com.common.language.util.I18nUtils;
import com.org.client.feign.StaffFeign;
import com.common.util.message.RestMessage;
import com.usercenter.server.service.command.response.UpdateUserCommandResp;
import com.usercenter.common.enums.FlagEnum;
import com.usercenter.server.common.enums.ReturnCodesEnum;
import com.usercenter.common.enums.UserStateEnum;
import com.usercenter.server.constant.BaseUserConstants;
import com.usercenter.server.entity.BaseUser;
import com.usercenter.server.entity.BaseUserDetail;
import com.usercenter.server.entity.BaseUserStaffMap;
import com.usercenter.server.mapper.BaseUserDetailMapper;
import com.usercenter.server.mapper.BaseUserMapper;
import com.usercenter.server.common.exception.WarnException;
import com.usercenter.server.domain.dto.UpdateUserStrategyDTO;
import com.usercenter.server.service.IBaseUserStaffMapService;
import com.usercenter.server.utils.UserRedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;


public abstract class AbstractUpdateUserStrategyService implements UpdateUserStrategyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractUpdateUserStrategyService.class);

    @Autowired
    protected UserRedisUtil userRedisUtil;

    @Autowired
    protected BaseUserDetailMapper baseUserDetailMapper;

    @Autowired
    protected BaseUserMapper baseUserMapper;

    @Autowired
    protected IBaseUserStaffMapService userStaffMapService;

    @Autowired
    protected IBaseUserStaffMapService iBaseUserStaffMapService;
    @Resource
    protected StaffFeign staffFeign;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public UpdateUserCommandResp update(UpdateUserStrategyDTO updateUserStrategyDTO) {
        LOGGER.info("用户更新参数值,updateUserStrategy:{}", JSONObject.toJSONString(updateUserStrategyDTO));
        UpdateUserCommandResp updateUserCommandResp;
        Map<Long, BaseUser> ids = new HashMap<>();
        Map<Long, BaseUserDetail> detailIds = new HashMap<>();
        //数据获取/校验
        UpdateUserCommandResp resp = getIds(updateUserStrategyDTO, ids, detailIds);
        LOGGER.info("获取数据校验,resp:{},ids:{},detailIds:{}", JSONObject.toJSONString(resp), ids, detailIds);
        if (resp != null) {
            return resp;
        }
        //删除逻辑处理
        if (updateUserStrategyDTO.getDelete() != null) {
            resp = deleteCheck(detailIds, updateUserStrategyDTO.getDetailId(), updateUserStrategyDTO.getUserId());
        }
        //启用逻辑处理
        if (updateUserStrategyDTO.getEnable() != null && updateUserStrategyDTO.getEnable().equals(UserStateEnum.ENABLE.getCode())) {
            resp = updateEnable(updateUserStrategyDTO.getId(), detailIds);
        }
        if (resp != null) {
            LOGGER.error("校验信息,resp:{}", JSONObject.toJSONString(resp));
            return resp;
        }
        //数据处理
        updateUserCommandResp = updateDetail(updateUserStrategyDTO);
        //锁定逻辑处理
        if (updateUserStrategyDTO.getLock() != null) {
            //锁定逻辑，主表单独处理
            updateBaseUserLock(ids, detailIds, updateUserStrategyDTO.getLock());
            //缓存处理
            updateLockCatch(ids, detailIds, updateUserStrategyDTO.getLock());
        }
        return updateUserCommandResp;
    }


    /**
     * 缓存数据维护【锁定/解锁逻辑】
     *
     * @param detailMap
     * @param lock
     */
    private void updateLockCatch(Map<Long, BaseUser> idMap, Map<Long, BaseUserDetail> detailMap, Integer lock) {
        if (!CollectionUtils.isEmpty(detailMap)) {
            for (Map.Entry<Long, BaseUser> entry : idMap.entrySet()) {
                Long id = entry.getKey();
                userRedisUtil.set(BaseUserConstants.USER_ACCOUNT_LOCK_ID + id, lock.toString());
                userRedisUtil.set(id + BaseUserConstants.USER_LOCK_SUFFIX, lock.toString());
                if (FlagEnum.FALSE.getCode()==lock) {
                    userRedisUtil.del(id + BaseUserConstants.USER_LOCK_SUFFIX);
                    userRedisUtil.del(id + BaseUserConstants.LOGIN_FAIL_COUNT_SUFFIX);
                }
            }
            for (Map.Entry<Long, BaseUserDetail> entry : detailMap.entrySet()) {
                Long detailId = entry.getKey();
                userRedisUtil.set(BaseUserConstants.USER_ACCOUNT_LOCK_DETAIL + detailId, lock.toString());
            }
        }
    }

    /**
     * 子表锁定。主表同步锁定【锁定状态，主子表同步】
     */
    private void updateBaseUserLock(Map<Long, BaseUser> userMap, Map<Long, BaseUserDetail> detailMap, Integer lock) {
        if (!CollectionUtils.isEmpty(userMap)) {
            for (Map.Entry<Long, BaseUser> entry : userMap.entrySet()) {
                BaseUser baseUser = entry.getValue();
                baseUser.setLockFlag(lock);
                baseUserMapper.update(baseUser);
            }
        }
        if (!CollectionUtils.isEmpty(detailMap)) {
            for (Map.Entry<Long, BaseUserDetail> entry : detailMap.entrySet()) {
                BaseUserDetail baseUserDetail = entry.getValue();
                baseUserDetail.setLockFlag(lock);
                baseUserDetailMapper.update(baseUserDetail);
            }
        }
    }


    /**
     * 删除逻辑
     *
     * @param detailMap
     * @return
     */
    private UpdateUserCommandResp deleteCheck(Map<Long, BaseUserDetail> detailMap, Long detailId, Long operateUserId) {
        for (Map.Entry<Long, BaseUserDetail> entry : detailMap.entrySet()) {
            BaseUserDetail userDetail = entry.getValue();
            if (userDetail.getId().equals(detailId)) {
                if (UserStateEnum.ENABLE.getCode().equals(userDetail.getState())) {
                    return new UpdateUserCommandResp().setReturnCodesEnum(ReturnCodesEnum.NOT_DISABLED);
                }
            }
            userStaffMapService.logicDelete(userDetail.getId(), operateUserId);
            RestMessage<Integer> restMessage = staffFeign.deleteUserIdByStaffId(userDetail.getArchivesId());
            LOGGER.info("删除用户操作，员工进行删除的返回值,restMessage:{}",restMessage);
        }
        return null;
    }


    /**
     * 启用状态逻辑
     *
     * @param detailMap
     * @return
     */
    private UpdateUserCommandResp updateEnable(Long userId, Map<Long, BaseUserDetail> detailMap) {

        //子表启用,主表必须是启用状态
        if (!CollectionUtils.isEmpty(detailMap) && userId == null) {
            List<Long> userIds = detailMap.values().stream().map(userDetail -> userDetail.getUserId()).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(userIds)) {
                AtomicBoolean flag = new AtomicBoolean(false);
                userIds.forEach(user -> {
                    BaseUser baseUser = baseUserMapper.load(user);
                    if (!UserStateEnum.ENABLE.getCode().equals(baseUser.getState())) {
                        flag.set(true);
                        return;
                    }
                });
                if (flag.get()) {
                    throw new WarnException(ReturnCodesEnum.NOT_ENABLE.getCode(), I18nUtils.getMessage("user.check.main.user.noenable"));
                }
            }
        }
        //子表处理
        //启用用户对应的员工必须为启用状态
        if (!CollectionUtils.isEmpty(detailMap) && userId == null) {
            List<Long> userIds = detailMap.keySet().stream().collect(Collectors.toList());
            List<BaseUserStaffMap> userStaffMaps = iBaseUserStaffMapService.findListByCondition(userIds, null, null, FlagEnum.FALSE.getCode());
            Map<Long, Long> userStaff = userStaffMaps.stream().collect(Collectors.toMap(key -> key.getUserId(), value -> value.getStaffId(),(v1,v2)->v2));
            List<Long> staffIds = userStaff.values().stream().collect(Collectors.toList());
            if (CollectionUtils.isEmpty(staffIds)) {
                return null;
            }
            // TODO验证员工档案信息是否启用
        }
        return null;
    }

    private UpdateUserCommandResp getIds(UpdateUserStrategyDTO updateUserStrategyDTO, Map<Long, BaseUser> idMap, Map<Long, BaseUserDetail> detailMap) {
        if (updateUserStrategyDTO.getId() != null) {
            return getIdsAndDetailIdsById(updateUserStrategyDTO.getId(), idMap, detailMap);
        }
        if (updateUserStrategyDTO.getDetailId() != null) {
            Set<Long> userDetails = new HashSet<>(1);
            userDetails.add(updateUserStrategyDTO.getDetailId());
            return getIdsAndDetailIdsByDetailIds(userDetails, idMap, detailMap);
        }
        if (!CollectionUtils.isEmpty(updateUserStrategyDTO.getDetailIds())) {
            Set<Long> userDetailIds = updateUserStrategyDTO.getDetailIds();
            //根据字表ID集合查找用户主表
            return getIdsAndDetailIdsByDetailIds(userDetailIds, idMap, detailMap);
        }
        return null;
    }

    /**
     * 根据主表，获取所有的主子表ID
     *
     * @param id
     * @param ids
     * @param detailIds
     * @return
     */
    private UpdateUserCommandResp getIdsAndDetailIdsById(Long id, Map<Long, BaseUser> ids, Map<Long, BaseUserDetail> detailIds) {
        //查出所有的字表ID
        UpdateUserCommandResp updateUserCommandResp = null;
        BaseUser baseUser = baseUserMapper.selectBaseUserWithDetailsById(id);
        if (baseUser == null) {
            LOGGER.error("未查找到用户信息,id:{}", id);
            updateUserCommandResp = new UpdateUserCommandResp();
            updateUserCommandResp.setReturnCodesEnum(ReturnCodesEnum.USER_NOTEXISTS);
            return updateUserCommandResp;
        }
        ids.put(baseUser.getId(), baseUser);
        List<BaseUserDetail> detailList = baseUser.getDetailList();
        if (!CollectionUtils.isEmpty(detailList)) {
            detailList.forEach(detail -> {
                if (detail.getDeletedFlag()== FlagEnum.FALSE.getCode()) {
                    detailIds.put(detail.getId(), detail);
                }
            });
        } else {
            LOGGER.error("根据主表，没有查找到字表信息：{}", id);
        }
        return updateUserCommandResp;
    }


    /**
     * 根据子表ID，获取所有的主子表id
     *
     * @param userDetailIds
     * @param ids
     * @param detailIds
     * @return
     */
    private UpdateUserCommandResp getIdsAndDetailIdsByDetailIds(Set<Long> userDetailIds, Map<Long, BaseUser> ids, Map<Long, BaseUserDetail> detailIds) {
        BaseUserDetail userDetailQuery = new BaseUserDetail();
        UpdateUserCommandResp updateUserCommandResp = null;
        for (Long userDetailId : userDetailIds) {
            userDetailQuery.setId(userDetailId);
            userDetailQuery.setDeletedFlag(FlagEnum.FALSE.getCode());
            BaseUserDetail userDetail = baseUserDetailMapper.loadByParam(userDetailQuery);
            if (userDetail == null) {
                LOGGER.error("未找到用户,detailId:{}", userDetailId);
                updateUserCommandResp = new UpdateUserCommandResp();
                return updateUserCommandResp.setReturnCodesEnum(ReturnCodesEnum.USER_NOTEXISTS);
            }
            detailIds.put(userDetailId, userDetail);
            //查找父表
            BaseUser userQuery = new BaseUser();
            userQuery.setId(userDetail.getUserId());
            BaseUser baseUser = baseUserMapper.loadByParam(userQuery);
            if (baseUser == null) {
                LOGGER.error("未找到用户,detail:{},id:{}", userDetailId, userDetail.getUserId());
                updateUserCommandResp = new UpdateUserCommandResp();
                return updateUserCommandResp.setReturnCodesEnum(ReturnCodesEnum.USER_NOTEXISTS);
            }
            //反查所有的子表
            BaseUser baseUser1 = baseUserMapper.selectBaseUserWithDetailsById(baseUser.getId());
            List<BaseUserDetail> detailList = baseUser1.getDetailList();
            if (!CollectionUtils.isEmpty(detailList)) {
                detailList.forEach(detail -> {
                    if (detail.getDeletedFlag()==FlagEnum.FALSE.getCode()) {
                        detailIds.put(detail.getId(), detail);
                    }
                });
            }
            ids.put(baseUser.getId(), baseUser);
        }
        return updateUserCommandResp;
    }

    /**
     * 更新数据业务
     *
     * @param strategyDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public abstract UpdateUserCommandResp updateDetail(UpdateUserStrategyDTO strategyDTO);
}
