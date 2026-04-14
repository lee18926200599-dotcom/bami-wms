package com.basedata.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.basedata.common.dto.DeleteDto;
import com.basedata.common.dto.UpdateStatusDto;
import com.basedata.common.enums.BooleanEnum;
import com.basedata.common.enums.StateEnum;
import com.basedata.common.util.PageUtil;
import com.basedata.common.vo.BasePlatformStoreVo;
import com.basedata.common.vo.BaseStoreDetailVo;
import com.basedata.common.vo.BaseStorePageVo;
import com.basedata.common.vo.BaseStoreVo;
import com.basedata.server.dto.BaseStoreDto;
import com.basedata.server.entity.BaseStore;
import com.basedata.server.mapper.BaseStoreMapper;
import com.basedata.server.query.BaseStoreMultiQuery;
import com.basedata.server.query.BaseStoreQuery;
import com.basedata.server.query.StoreQuery;
import com.basedata.server.service.BaseStoreService;
import com.common.base.constants.RedisConstants;
import com.common.base.entity.CurrentUser;
import com.common.base.enums.PlatformTypeEnum;
import com.common.framework.execption.BizException;
import com.common.framework.redis.RedisUtil;
import com.common.framework.user.FplUserUtil;
import com.common.language.util.I18nUtils;
import com.common.util.util.HuToolUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BaseStoreServiceImpl implements BaseStoreService {

    @Resource
    private BaseStoreMapper baseStoreMapper;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public void save(BaseStoreDto baseStoreDto) {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();

        BaseStore baseStore = BeanUtil.toBean(baseStoreDto, BaseStore.class);

        //判断仓库名是否存在
        LambdaQueryWrapper<BaseStore> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BaseStore::getOwnerId, baseStoreDto.getOwnerId())
                .eq(BaseStore::getStoreCode, baseStoreDto.getStoreCode())
                .eq(BaseStore::getPlatformCode, baseStoreDto.getPlatformCode())
                .eq(BaseStore::getDeletedFlag, BooleanEnum.FALSE.getCode());
        long countByCriteria = baseStoreMapper.selectCount(lambdaQueryWrapper);
        if (countByCriteria > 0) {
            throw new BizException(I18nUtils.getMessage("base.check.store.ec.owner.code.config.exist"));
        }
        lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BaseStore::getOwnerId, baseStoreDto.getOwnerId())
                .eq(BaseStore::getPlatformCode, baseStoreDto.getPlatformCode())
                .eq(BaseStore::getStoreName, baseStoreDto.getStoreName())
                .eq(BaseStore::getDeletedFlag, BooleanEnum.FALSE.getCode());
        long count = baseStoreMapper.selectCount(lambdaQueryWrapper);
        if (count > 0) {
            throw new BizException(I18nUtils.getMessage("base.check.store.ec.owner.name.config.exist"));
        }
        baseStore.setPlatformName(PlatformTypeEnum.getPlatformName(baseStore.getPlatformCode()));
        baseStore.setState(StateEnum.NOT_ENABLE.getCode());
        baseStore.setServiceProviderId(currentUser.getServiceProviderId());
        baseStore.setServiceProviderName(currentUser.getServiceProviderName());
        baseStore.setCreatedBy(currentUser.getUserId());
        baseStore.setCreatedName(currentUser.getUserName());
        baseStore.setCreatedDate(new Date());
        baseStore.setDeletedFlag(BooleanEnum.FALSE.getCode());
        baseStore.setVersion(0);
        this.baseStoreMapper.insert(baseStore);
    }

    @Override
    public void update(BaseStoreDto baseStoreDto) {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();

        BaseStore baseStore = BeanUtil.toBean(baseStoreDto, BaseStore.class);

        if (!baseStore.getStoreCode().equals(baseStore.getStoreCode())) {
            throw new BizException(I18nUtils.getMessage("base.check.store.storecode.cannot.modify"));
        }

        if (baseStore.getState() != StateEnum.NOT_ENABLE.getCode()) {
            if (!baseStore.getOwnerId().equals(baseStore.getOwnerId())) {
                throw new BizException(I18nUtils.getMessage("base.check.store.owner.cannot.modify"));
            }
            if (!baseStore.getPlatformCode().equals(baseStore.getPlatformCode())) {
                throw new BizException(I18nUtils.getMessage("base.check.store.ec.cannot.modify"));
            }
        }

        //判断仓库名是否存在
        LambdaQueryWrapper<BaseStore> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BaseStore::getOwnerId, baseStoreDto.getOwnerId())
                .eq(BaseStore::getPlatformCode, baseStoreDto.getPlatformCode())
                .eq(BaseStore::getStoreCode, baseStoreDto.getStoreCode())
                .eq(BaseStore::getDeletedFlag, BooleanEnum.FALSE.getCode());

        List<BaseStore> list = baseStoreMapper.selectList(lambdaQueryWrapper);
        if (!CollectionUtils.isEmpty(list)) {
            BaseStore store = list.get(0);
            if (!store.getId().equals(baseStoreDto.getId())) {
                throw new BizException(I18nUtils.getMessage("base.check.store.ec.owner.code.config.exist"));
            }
        }
        lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BaseStore::getOwnerId, baseStoreDto.getOwnerId())
                .eq(BaseStore::getPlatformCode, baseStoreDto.getPlatformCode())
                .eq(BaseStore::getStoreName, baseStoreDto.getStoreName())
                .eq(BaseStore::getDeletedFlag, BooleanEnum.FALSE.getCode());
        List<BaseStore> nameList = baseStoreMapper.selectList(lambdaQueryWrapper);
        if (!CollectionUtils.isEmpty(nameList)) {
            BaseStore store = nameList.get(0);
            if (!store.getId().equals(baseStoreDto.getId())) {
                throw new BizException(I18nUtils.getMessage("base.check.store.ec.owner.name.config.exist"));
            }
        }
        baseStore.setPlatformName(PlatformTypeEnum.getPlatformName(baseStore.getPlatformCode()));
        baseStore.setModifiedBy(currentUser.getUserId());
        baseStore.setModifiedName(currentUser.getUserName());
        baseStore.setModifiedDate(new Date());
        this.baseStoreMapper.updateById(baseStore);

        String key = RedisConstants.WMS_PRINT_CONFIG.concat(baseStoreDto.getPlatformCode())
                .concat(baseStoreDto.getOwnerId().toString());
        redisUtil.del(key);

    }

    @Override
    public PageInfo<BaseStorePageVo> page(StoreQuery storeQuery) {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        LambdaQueryWrapper<BaseStore> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BaseStore::getServiceProviderId, currentUser.getServiceProviderId());
        if (StringUtils.isNotEmpty(storeQuery.getStoreCode())) {
            lambdaQueryWrapper.likeLeft(BaseStore::getStoreCode, storeQuery.getStoreCode());
        }
        if (StringUtils.isNotEmpty(storeQuery.getStoreName())) {
            lambdaQueryWrapper.likeLeft(BaseStore::getStoreName, storeQuery.getStoreName());
        }
        if (StringUtils.isNotEmpty(storeQuery.getPlatformCode())) {
            lambdaQueryWrapper.eq(BaseStore::getPlatformCode, storeQuery.getPlatformCode());
        }
        if (storeQuery.getState() != null) {
            lambdaQueryWrapper.eq(BaseStore::getState, storeQuery.getState());
        }
        if (storeQuery.getOwnerId() != null) {
            lambdaQueryWrapper.eq(BaseStore::getOwnerId, storeQuery.getOwnerId());
        }
        if (storeQuery.getMainStoreFlag() != null) {
            lambdaQueryWrapper.eq(BaseStore::getMainStoreFlag, storeQuery.getMainStoreFlag());
        }
        lambdaQueryWrapper.eq(BaseStore::getDeletedFlag, 0);

        lambdaQueryWrapper.orderByDesc(BaseStore::getCreatedDate);

        PageHelper.startPage(storeQuery.getPageNum(), storeQuery.getPageSize());
        List<BaseStore> list = this.baseStoreMapper.selectList(lambdaQueryWrapper);

        return PageUtil.convert(new PageInfo<>(list), item -> {
            BaseStorePageVo storePageVo = BeanUtil.toBean(item, BaseStorePageVo.class);
            StateEnum anEnum = StateEnum.getEnum(item.getState());
            storePageVo.setStateName(anEnum.getName());
            return storePageVo;
        });
    }

    @Override
    public void updateStatus(UpdateStatusDto statusDto) {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();

        LambdaQueryWrapper<BaseStore> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(BaseStore::getId, statusDto.getIds());
        BaseStore baseStore = new BaseStore();
        baseStore.setModifiedBy(currentUser.getUserId());
        baseStore.setModifiedName(currentUser.getUserName());
        baseStore.setModifiedDate(new Date());
        baseStore.setState(statusDto.getState());
        this.baseStoreMapper.update(baseStore, lambdaQueryWrapper);

        if (Objects.equals(StateEnum.DISABLE.getCode(), statusDto.getState())) {
            List<BaseStore> byCriteria = this.baseStoreMapper.selectList(lambdaQueryWrapper);
            for (BaseStore store : byCriteria) {
                String key = RedisConstants.WMS_PRINT_CONFIG.concat(store.getPlatformCode())
                        .concat(store.getOwnerId().toString());
                redisUtil.del(key);
            }
        }
    }

    @Override
    public BaseStoreDetailVo detail(Long id) {
        BaseStore baseStore = this.baseStoreMapper.selectById(id);
        BaseStoreDetailVo detailVo = BeanUtil.toBean(baseStore, BaseStoreDetailVo.class);
        return detailVo;
    }

    @Override
    public void delete(DeleteDto deleteDto) {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        LambdaQueryWrapper<BaseStore> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(BaseStore::getId, deleteDto.getIds());
        List<BaseStore> storeList = this.baseStoreMapper.selectList(lambdaQueryWrapper);
        List<BaseStore> count = storeList.stream().filter(x -> x.getState() > 0).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(count)) {
            throw new BizException(I18nUtils.getMessage("base.check.store.batch.delete.exist.createstate"));
        }
        BaseStore baseStore = new BaseStore();
        baseStore.setModifiedBy(currentUser.getUserId());
        baseStore.setModifiedName(currentUser.getUserName());
        baseStore.setModifiedDate(new Date());
        baseStore.setDeletedFlag(BooleanEnum.TRUE.getCode());
        this.baseStoreMapper.update(baseStore, lambdaQueryWrapper);
    }


    @Override
    @SneakyThrows
    public List<BaseStoreVo> getStoreList(BaseStoreQuery storeQuery) {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        LambdaQueryWrapper<BaseStore> lambdaQueryWrapper = this.getValidStoreQueryParam();
//        lambdaQueryWrapper.and(q -> this.getValidStoreQueryParam().getExpression());
        lambdaQueryWrapper.eq(BaseStore::getServiceProviderId, currentUser.getServiceProviderId());
        if (StringUtils.isNotEmpty(storeQuery.getStoreCode())) {
            lambdaQueryWrapper.likeLeft(BaseStore::getStoreCode, storeQuery.getStoreCode());
        }
        if (StringUtils.isNotEmpty(storeQuery.getPlatformCode())) {
            lambdaQueryWrapper.eq(BaseStore::getPlatformCode, storeQuery.getPlatformCode());
        }
        if (storeQuery.getOwnerId() != null) {
            lambdaQueryWrapper.eq(BaseStore::getOwnerId, storeQuery.getOwnerId());
        }

        lambdaQueryWrapper.orderByDesc(BaseStore::getCreatedDate);

        List<BaseStore> list = this.baseStoreMapper.selectList(lambdaQueryWrapper);

        List<BaseStoreVo> exchange = null;
        try {
            exchange = HuToolUtil.exchange(list, BaseStoreVo.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return exchange;
    }

    @Override
    public List<BaseStoreVo> getMultiStoreList(BaseStoreMultiQuery baseStoreQuery) {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        LambdaQueryWrapper<BaseStore> lambdaQueryWrapper  = this.getValidStoreQueryParam();
        lambdaQueryWrapper.eq(BaseStore::getServiceProviderId, currentUser.getServiceProviderId());
        if (CollectionUtil.isNotEmpty(baseStoreQuery.getPlatformCodes())) {
            lambdaQueryWrapper.in(BaseStore::getPlatformCode, baseStoreQuery.getPlatformCodes());
        }
        if (CollectionUtil.isNotEmpty(baseStoreQuery.getOwnerIds())) {
            lambdaQueryWrapper.in(BaseStore::getOwnerId, baseStoreQuery.getOwnerIds());
        }
        if (CollectionUtil.isNotEmpty(baseStoreQuery.getStoreCodes())) {
            lambdaQueryWrapper.in(BaseStore::getStoreCode, baseStoreQuery.getStoreCodes());
        }
        lambdaQueryWrapper.orderByDesc(BaseStore::getCreatedDate);
        List<BaseStore> list = this.baseStoreMapper.selectList(lambdaQueryWrapper);
        return HuToolUtil.exchange(list, BaseStoreVo.class);
    }

    private LambdaQueryWrapper<BaseStore> getValidStoreQueryParam() {
        LambdaQueryWrapper<BaseStore> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BaseStore::getState, StateEnum.ENABLE.getCode());
        lambdaQueryWrapper.eq(BaseStore::getDeletedFlag, 0);
        return lambdaQueryWrapper;
    }

    @Override
    public List<BasePlatformStoreVo> platformStore(String platformCode) {
        LambdaQueryWrapper<BaseStore> lambdaQueryWrapper =  this.getValidStoreQueryParam();
        if (StringUtils.isNotEmpty(platformCode)) {
            lambdaQueryWrapper.eq(BaseStore::getPlatformCode, platformCode);
        }

        lambdaQueryWrapper.orderByDesc(BaseStore::getCreatedDate);
        List<BaseStore> list = this.baseStoreMapper.selectList(lambdaQueryWrapper);
        return HuToolUtil.exchange(list, BasePlatformStoreVo.class);
    }
}
