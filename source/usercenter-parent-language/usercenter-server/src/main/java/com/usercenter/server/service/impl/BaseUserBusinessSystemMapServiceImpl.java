package com.usercenter.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.common.language.util.I18nUtils;
import com.common.util.util.AssertUtils;
import com.google.common.collect.Sets;
import com.usercenter.common.enums.BusinessSystemEnum;
import com.usercenter.common.enums.BusinessSystemMapTypeEnum;
import com.usercenter.common.enums.UserStateEnum;
import com.usercenter.server.entity.BaseUserBusinessSystemMap;
import com.usercenter.server.mapper.BaseUserBusinessSystemMapMapper;
import com.usercenter.server.service.IBaseUserBusinessSystemMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *  系统-用户身份映射
 */
@Service
public class BaseUserBusinessSystemMapServiceImpl implements IBaseUserBusinessSystemMapService {

    @Autowired
    private BaseUserBusinessSystemMapMapper baseUserBusinessSystemMapMapper;

    @Override
    public Set<Integer> getMapIdsBySystem(BusinessSystemEnum system, BusinessSystemMapTypeEnum businessSystemMapType) {
        //TODO 后期加缓存
        BaseUserBusinessSystemMap query = new BaseUserBusinessSystemMap();
        query.setBusinessSystemId(system.getCode());
        query.setMapType(businessSystemMapType.getCode());
        query.setState(UserStateEnum.ENABLE.getCode().toString());
        LambdaQueryWrapper<BaseUserBusinessSystemMap> queryWrapper = new LambdaQueryWrapper<BaseUserBusinessSystemMap>();
        queryWrapper.eq(BaseUserBusinessSystemMap::getBusinessSystemId,system.getCode());
        queryWrapper.eq(BaseUserBusinessSystemMap::getMapType,businessSystemMapType.getCode());
        queryWrapper.eq(BaseUserBusinessSystemMap::getState ,UserStateEnum.ENABLE.getCode());
//        List<BaseUserBusinessSystemMap> baseUserIdentitySystemSet = baseUserBusinessSystemMapMapper.selectByMap(BeanUtil.beanToMap(query,false,true));
        List<BaseUserBusinessSystemMap> baseUserIdentitySystemSet = baseUserBusinessSystemMapMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(baseUserIdentitySystemSet)) {
            return Sets.newHashSet();
        }
        return baseUserIdentitySystemSet.stream().map(baseUserIdentitySystem -> baseUserIdentitySystem.getMapId()).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getMapNamesBySystem(BusinessSystemEnum system, BusinessSystemMapTypeEnum businessSystemMapType) {
        //TODO 后期加缓存
        BaseUserBusinessSystemMap query = new BaseUserBusinessSystemMap();
        query.setBusinessSystemId(system.getCode());
        query.setMapType(businessSystemMapType.getCode());
        query.setState(UserStateEnum.ENABLE.getCode().toString());
        List<BaseUserBusinessSystemMap> baseUserIdentitySystemSet = baseUserBusinessSystemMapMapper.selectByMap(BeanUtil.beanToMap(query,true,true));
        if (CollectionUtils.isEmpty(baseUserIdentitySystemSet)) {
            return Sets.newHashSet();
        }
        return baseUserIdentitySystemSet.stream().map(baseUserIdentitySystem -> baseUserIdentitySystem.getMapName()).collect(Collectors.toSet());
    }

    @Override
    public void add(BaseUserBusinessSystemMap baseUserBusinessSystemMap) {
        baseUserBusinessSystemMapMapper.insert(baseUserBusinessSystemMap);
    }

    @Override
    public void update(BaseUserBusinessSystemMap baseUserBusinessSystemMap) {
        AssertUtils.isNotNull(baseUserBusinessSystemMap.getId(), I18nUtils.getMessage("user.check.id.notnull"));
        baseUserBusinessSystemMapMapper.updateById(baseUserBusinessSystemMap);
    }

    @Override
    public List<BaseUserBusinessSystemMap> getList(BaseUserBusinessSystemMap baseUserBusinessSystemMap) {
        return baseUserBusinessSystemMapMapper.selectByMap(BeanUtil.beanToMap(baseUserBusinessSystemMap,true,true));
    }


}
