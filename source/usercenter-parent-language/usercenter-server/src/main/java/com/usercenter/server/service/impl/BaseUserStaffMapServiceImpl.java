package com.usercenter.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.common.base.enums.StateEnum;
import com.common.language.util.I18nUtils;
import com.usercenter.common.enums.FlagEnum;
import com.usercenter.common.enums.UserStateEnum;
import com.usercenter.server.entity.BaseUserStaffMap;
import com.usercenter.server.mapper.BaseUserStaffMapMapper;
import com.usercenter.server.service.IBaseUserStaffMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BaseUserStaffMapServiceImpl implements IBaseUserStaffMapService {

    @Autowired
    private BaseUserStaffMapMapper baseUserStaffMapMapper;

    @Override
    public BaseUserStaffMap save(BaseUserStaffMap userStaffMap) {
        userStaffMap.setCreatedDate(new Date());
        userStaffMap.setState(StateEnum.ENABLE.getCode());
        baseUserStaffMapMapper.insert(userStaffMap);
        return userStaffMap;
    }

    @Override
    public void disableByStaffId(Integer staffId) {
        disable(staffId, null);
    }

    @Override
    public void disableByUserId(Long userId) {
        disable(null, userId);
    }


    /**
     * 禁用
     *
     * @param staffId 人员id
     * @param userId  用户id
     */
    private void disable(Integer staffId, Long userId) {
        if (ObjectUtils.isEmpty(staffId) && ObjectUtils.isEmpty(userId)) {
            throw new IllegalArgumentException(I18nUtils.getMessage("user.check.userid.personid.notnull"));
        }
        BaseUserStaffMap baseUserStaffMap = new BaseUserStaffMap();
        baseUserStaffMap.setState(UserStateEnum.ENABLE.getCode());
        LambdaQueryWrapper<BaseUserStaffMap> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!ObjectUtils.isEmpty(staffId)) {
            lambdaQueryWrapper.eq(BaseUserStaffMap::getStaffId, staffId);

        } else {
            lambdaQueryWrapper.eq(BaseUserStaffMap::getUserId, userId);
        }
        baseUserStaffMapMapper.update(baseUserStaffMap, lambdaQueryWrapper);
    }


    @Override
    public List<BaseUserStaffMap> findListByCondition(List<Long> userIds, List<Long> staffIds,Integer state, Integer deletedFlag) {
        LambdaQueryWrapper<BaseUserStaffMap> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (state!=null) {
            lambdaQueryWrapper.eq(BaseUserStaffMap::getState, state);
        }
        if (deletedFlag!=null) {
            lambdaQueryWrapper.eq(BaseUserStaffMap::getDeletedFlag, deletedFlag);
        }
        if (!ObjectUtils.isEmpty(userIds)) {
            lambdaQueryWrapper.in(BaseUserStaffMap::getUserId, userIds);
        }
        if (!ObjectUtils.isEmpty(staffIds)) {
            lambdaQueryWrapper.in(BaseUserStaffMap::getStaffId, staffIds);
        }
        return baseUserStaffMapMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public BaseUserStaffMap findOneByCondition(BaseUserStaffMap userStaffMap) {
        List<BaseUserStaffMap> result = baseUserStaffMapMapper.selectByMap(BeanUtil.beanToMap(userStaffMap,true,true));
        return ObjectUtils.isEmpty(result) ? null : result.get(0);
    }

    @Override
    public void logicDelete(Long userId, Long updateUserId) {
        if (ObjectUtils.isEmpty(userId)) {
            throw new IllegalArgumentException(I18nUtils.getMessage("user.check.userid.notnull"));
        }
        BaseUserStaffMap baseUserStaffMap = new BaseUserStaffMap();
        baseUserStaffMap.setDeletedFlag(FlagEnum.TRUE.getCode());
        baseUserStaffMap.setModifiedBy(updateUserId);
        baseUserStaffMap.setModifiedDate(new Date());
        LambdaQueryWrapper<BaseUserStaffMap> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BaseUserStaffMap::getUserId, userId);
        baseUserStaffMapMapper.update(baseUserStaffMap,lambdaQueryWrapper);
    }


    @Override
    public BaseUserStaffMap saveCaseNotExist(BaseUserStaffMap userStaffMap) {
        BaseUserStaffMap query = new BaseUserStaffMap();
        query.setStaffId(userStaffMap.getStaffId());
        query.setUserId(userStaffMap.getUserId());
        query.setState(UserStateEnum.ENABLE.getCode());
        query.setDeletedFlag(FlagEnum.FALSE.getCode());
        BaseUserStaffMap existMp = findOneByCondition(query);
        if (!ObjectUtils.isEmpty(existMp)) {
            existMp.setModifiedBy(userStaffMap.getModifiedBy());
            existMp.setModifiedDate(new Date());
            baseUserStaffMapMapper.updateById(existMp);
            return existMp;
        }
        return save(userStaffMap);
    }


    @Override
    public void deleteOtherMapping(BaseUserStaffMap userStaffMap) {
        BaseUserStaffMap update = new BaseUserStaffMap();
        update.setDeletedFlag(FlagEnum.TRUE.getCode());
        update.setModifiedBy(userStaffMap.getModifiedBy());
        update.setModifiedDate(new Date());
        LambdaQueryWrapper<BaseUserStaffMap> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BaseUserStaffMap::getUserId, userStaffMap.getUserId());
        lambdaQueryWrapper.ne(BaseUserStaffMap::getStaffId, userStaffMap.getStaffId());
        baseUserStaffMapMapper.update(update, lambdaQueryWrapper);
    }

    @Override
    public void syncUserStaffMap() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateString = sdf.format(new Date());
        baseUserStaffMapMapper.syncUserStaffMap(dateString);
    }
}
