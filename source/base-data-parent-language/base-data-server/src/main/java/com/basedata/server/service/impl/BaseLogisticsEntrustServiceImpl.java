package com.basedata.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.basedata.common.dto.BaseLogisticsEntrustDto;
import com.basedata.common.dto.DeleteDto;
import com.basedata.common.dto.UpdateStatusDto;
import com.basedata.common.dto.warehouse.BaseWarehouseDto;
import com.basedata.common.enums.BooleanEnum;
import com.basedata.common.enums.DeliveryTypeEnum;
import com.basedata.common.enums.StateEnum;
import com.basedata.common.query.LogisticsEntrustQuery;
import com.basedata.common.util.PageUtil;
import com.basedata.common.vo.BaseLogisticsEntrustDetailVo;
import com.basedata.common.vo.BaseLogisticsEntrustVo;
import com.basedata.server.domain.OrgDomainService;
import com.basedata.server.domain.PermissionDomainService;
import com.basedata.server.entity.BaseLogisticsEntrust;
import com.basedata.server.mapper.BaseLogisticsEntrustMapper;
import com.basedata.server.query.BaseLogisticsEntrustQuery;
import com.basedata.server.service.BaseLogisticsEntrustService;
import com.basedata.server.service.BaseWarehouseService;
import com.common.base.entity.CurrentUser;
import com.common.framework.execption.BizException;
import com.common.framework.number.BaseNoGenerateUtil;
import com.common.framework.user.FplUserUtil;
import com.common.language.util.I18nUtils;
import com.common.util.util.AssertUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.vo.BizUnitWithFuncDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BaseLogisticsEntrustServiceImpl extends ServiceImpl<BaseLogisticsEntrustMapper,BaseLogisticsEntrust> implements BaseLogisticsEntrustService {

    @Resource
    private BaseLogisticsEntrustMapper baseLogisticsEntrustMapper;

    @Autowired
    private BaseWarehouseService warehouseService;
    @Autowired
    private OrgDomainService orgDomainService;
    @Autowired
    private PermissionDomainService permissionDomainService;

    @Override
    public void save(BaseLogisticsEntrustDto baseLogisticsEntrustDto) {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        AssertUtils.notNull(baseLogisticsEntrustDto.getServiceProviderId(), I18nUtils.getMessage("base.config.selectwarehouseorg"));
        AssertUtils.notNull(baseLogisticsEntrustDto.getWarehouseId(), I18nUtils.getMessage("base.config.selectwarehouse"));
        List<BaseLogisticsEntrustDto.OwnerDetailDto> ownerDetailDtos = baseLogisticsEntrustDto.getOwnerDetailDtos();
        Assert.notEmpty(ownerDetailDtos, I18nUtils.getMessage("base.check.shippernotnull"));
        BaseWarehouseDto baseWarehouseDto = warehouseService.getWarehouseById(baseLogisticsEntrustDto.getWarehouseId());
        AssertUtils.notNull(baseWarehouseDto, I18nUtils.getMessage("base.check.warehousenoexist"));
        baseLogisticsEntrustDto.setWarehouseCode(baseWarehouseDto.getWarehouseCode());
        baseLogisticsEntrustDto.setWarehouseName(baseWarehouseDto.getWarehouseName());
        BizUnitWithFuncDetailVo bizUnitWithFuncDetailVo = orgDomainService.getOrgById(baseLogisticsEntrustDto.getServiceProviderId());
        AssertUtils.isNotTrue(Objects.isNull(bizUnitWithFuncDetailVo), I18nUtils.getMessage("base.check.storageorgnotfound"));
        baseLogisticsEntrustDto.setServiceProviderName(bizUnitWithFuncDetailVo.getOrgName());
        List<BaseLogisticsEntrust> list = new ArrayList<>();
        for (BaseLogisticsEntrustDto.OwnerDetailDto ownerDetailDto : ownerDetailDtos) {

            //在同仓储组织同仓库同配送方式同承运商同承运商网点同货主的配置
            //判断仓库名是否存在
            LambdaQueryWrapper<BaseLogisticsEntrust> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(BaseLogisticsEntrust::getServiceProviderId, baseLogisticsEntrustDto.getServiceProviderId())
                    .eq(BaseLogisticsEntrust::getWarehouseId, baseLogisticsEntrustDto.getWarehouseId())
                    .eq(BaseLogisticsEntrust::getOwnerId, ownerDetailDto.getOwnerId())
                    .eq(BaseLogisticsEntrust::getLogisticsId, baseLogisticsEntrustDto.getLogisticsId())
                    .eq(BaseLogisticsEntrust::getDeliveryType, baseLogisticsEntrustDto.getDeliveryType())
                    .eq(BaseLogisticsEntrust::getBranchId, baseLogisticsEntrustDto.getBranchId())
                    .eq(BaseLogisticsEntrust::getDeletedFlag, BooleanEnum.FALSE.getCode());
            List<BaseLogisticsEntrust> countByCriteria = baseLogisticsEntrustMapper.selectList(lambdaQueryWrapper);
            if (!CollectionUtils.isEmpty(countByCriteria)) {
                BaseLogisticsEntrust baseLogisticsEntrust = countByCriteria.get(0);
                I18nUtils.getMessage("base.check.LogisticsEntrust.config.execption",new String[]{baseLogisticsEntrust.getOwnerName(), baseLogisticsEntrust.getConfigCode()});
                throw new BizException(I18nUtils.getMessage("base.check.LogisticsEntrust.config.execption",new String[]{baseLogisticsEntrust.getOwnerName(), baseLogisticsEntrust.getConfigCode()}));
            }

            if (baseLogisticsEntrustDto.getDefaultFlag() != null && baseLogisticsEntrustDto.getDefaultFlag() == 1) {
                LambdaQueryWrapper<BaseLogisticsEntrust> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
                lambdaQueryWrapper1.eq(BaseLogisticsEntrust::getServiceProviderId, baseLogisticsEntrustDto.getServiceProviderId())
                        .eq(BaseLogisticsEntrust::getWarehouseId, baseLogisticsEntrustDto.getWarehouseId())
                        .eq(BaseLogisticsEntrust::getOwnerId, ownerDetailDto.getOwnerId())
                        .eq(BaseLogisticsEntrust::getDeliveryType, baseLogisticsEntrustDto.getDeliveryType())
                        .eq(BaseLogisticsEntrust::getDeletedFlag, BooleanEnum.FALSE.getCode());
                List<BaseLogisticsEntrust> entrusts = baseLogisticsEntrustMapper.selectList(lambdaQueryWrapper1);

                if (!CollectionUtils.isEmpty(entrusts)) {
                    BaseLogisticsEntrust baseLogisticsEntrust = entrusts.get(0);
                    throw new BizException(I18nUtils.getMessage("base.check.LogisticsEntrust.config.defualt",new String[]{baseLogisticsEntrust.getOwnerName(), baseLogisticsEntrust.getConfigCode()}));
                }
            }

            BaseLogisticsEntrust entrust = BeanUtil.toBean(baseLogisticsEntrustDto, BaseLogisticsEntrust.class);
            entrust.setConfigCode(BaseNoGenerateUtil.generateRuleCode(baseLogisticsEntrustDto.getServiceProviderId() + ""));
            entrust.setOwnerId(ownerDetailDto.getOwnerId());
            entrust.setOwnerName(ownerDetailDto.getOwnerName());
            entrust.setState(StateEnum.NOT_ENABLE.getCode());
            entrust.setGroupId(currentUser.getGroupId());
            entrust.setCreatedBy(currentUser.getUserId());
            entrust.setCreatedName(currentUser.getUserName());
            entrust.setCreatedDate(new Date());
            entrust.setDeletedFlag(BooleanEnum.FALSE.getCode());
            entrust.setVersion(0);
            list.add(entrust);
        }
        this.saveBatch(list);
    }

    @Override
    public void update(BaseLogisticsEntrustDto baseLogisticsEntrustDto) {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        List<BaseLogisticsEntrustDto.OwnerDetailDto> ownerDetailDtos = baseLogisticsEntrustDto.getOwnerDetailDtos();
        if (ownerDetailDtos.size() > 1) {
            throw new BizException(I18nUtils.getMessage("base.check.shipper.multiple"));
        }
        BaseLogisticsEntrust currentOne = baseLogisticsEntrustMapper.selectById(baseLogisticsEntrustDto.getId());
        if (StateEnum.NOT_ENABLE.getCode().equals(currentOne.getState())) {
            Assert.isFalse(!currentOne.getConfigName().equals(baseLogisticsEntrustDto.getConfigName()), I18nUtils.getMessage("base.check.logisticsentrust.createnameupdate"));
            Assert.isFalse(!currentOne.getConfigCode().equals(baseLogisticsEntrustDto.getConfigCode()), I18nUtils.getMessage("base.check.logisticsentrust.createcodeupdate"));
        } else if (StateEnum.DISABLE.getCode().equals(currentOne.getState())) {
            Assert.isFalse(!currentOne.getConfigName().equals(baseLogisticsEntrustDto.getConfigName()), I18nUtils.getMessage("base.check.logisticsentrust.disablenameupdate"));
            Assert.isFalse(!currentOne.getConfigCode().equals(baseLogisticsEntrustDto.getConfigCode()), I18nUtils.getMessage("base.check.logisticsentrust.disablecodeupdate"));
            Assert.isFalse(!currentOne.getServiceProviderId().equals(baseLogisticsEntrustDto.getServiceProviderId()), I18nUtils.getMessage("base.check.logisticsentrust.disablewarehouseorgplatupdate"));
            Assert.isFalse(!currentOne.getWarehouseId().equals(baseLogisticsEntrustDto.getWarehouseId()), I18nUtils.getMessage("base.check.logisticsentrust.disablewarehouseupdate"));
            Assert.isFalse(!currentOne.getOwnerId().equals(baseLogisticsEntrustDto.getOwnerDetailDtos().get(0).getOwnerId()), I18nUtils.getMessage("base.check.logisticsentrust.disableownerupdate"));
        } else if (StateEnum.ENABLE.getCode().equals(currentOne.getState())) {
            Assert.isFalse(!currentOne.getConfigName().equals(baseLogisticsEntrustDto.getConfigName()), I18nUtils.getMessage("base.check.logisticsentrust.enablenameupdate"));
            Assert.isFalse(!currentOne.getConfigCode().equals(baseLogisticsEntrustDto.getConfigCode()), I18nUtils.getMessage("base.check.logisticsentrust.ensablecodeupdate"));
            Assert.isFalse(!currentOne.getServiceProviderId().equals(baseLogisticsEntrustDto.getServiceProviderId()), I18nUtils.getMessage("base.check.logisticsentrust.ensablewarehouseorgplatupdate"));
            Assert.isFalse(!currentOne.getWarehouseId().equals(baseLogisticsEntrustDto.getWarehouseId()), I18nUtils.getMessage("base.check.logisticsentrust.ensablewarehouseupdate"));
            Assert.isFalse(!currentOne.getOwnerId().equals(baseLogisticsEntrustDto.getOwnerDetailDtos().get(0).getOwnerId()), I18nUtils.getMessage("base.check.logisticsentrust.ensableownerupdate"));
        }

        List<BaseLogisticsEntrust> list = new ArrayList<>();
        for (BaseLogisticsEntrustDto.OwnerDetailDto ownerDetailDto : ownerDetailDtos) {

            //在同仓储组织同仓库同配送方式同承运商同承运商网点同货主的配置
            //判断仓库名是否存在
            LambdaQueryWrapper<BaseLogisticsEntrust> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(BaseLogisticsEntrust::getServiceProviderId, baseLogisticsEntrustDto.getServiceProviderId())
                    .eq(BaseLogisticsEntrust::getWarehouseId, baseLogisticsEntrustDto.getWarehouseId())
                    .eq(BaseLogisticsEntrust::getOwnerId, ownerDetailDto.getOwnerId())
                    .eq(BaseLogisticsEntrust::getLogisticsId, baseLogisticsEntrustDto.getLogisticsId())
                    .eq(BaseLogisticsEntrust::getDeliveryType, baseLogisticsEntrustDto.getDeliveryType())
                    .eq(BaseLogisticsEntrust::getBranchId, baseLogisticsEntrustDto.getBranchId())
                    .eq(BaseLogisticsEntrust::getDeletedFlag, BooleanEnum.FALSE.getCode());

            List<BaseLogisticsEntrust> countByCriteria = baseLogisticsEntrustMapper.selectList(lambdaQueryWrapper);
            if (!CollectionUtils.isEmpty(countByCriteria)) {
                BaseLogisticsEntrust baseLogisticsEntrust = countByCriteria.get(0);
                if (!baseLogisticsEntrust.getId().equals(baseLogisticsEntrustDto.getId())) {
                    throw new BizException(I18nUtils.getMessage("base.check.LogisticsEntrust.config.execption",new String[]{baseLogisticsEntrust.getOwnerName(), baseLogisticsEntrust.getConfigCode()}));
                }
            }

            if (baseLogisticsEntrustDto.getDefaultFlag() != null && baseLogisticsEntrustDto.getDefaultFlag() == 1) {
                LambdaQueryWrapper<BaseLogisticsEntrust> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
                lambdaQueryWrapper1.eq(BaseLogisticsEntrust::getServiceProviderId, baseLogisticsEntrustDto.getServiceProviderId())
                        .eq(BaseLogisticsEntrust::getWarehouseId, baseLogisticsEntrustDto.getWarehouseId())
                        .eq(BaseLogisticsEntrust::getOwnerId, ownerDetailDto.getOwnerId())
                        .eq(BaseLogisticsEntrust::getDeliveryType, baseLogisticsEntrustDto.getDeliveryType())
                        .eq(BaseLogisticsEntrust::getDeletedFlag, BooleanEnum.FALSE.getCode());
                List<BaseLogisticsEntrust> entrusts = baseLogisticsEntrustMapper.selectList(lambdaQueryWrapper1);

                if (!CollectionUtils.isEmpty(entrusts)) {
                    BaseLogisticsEntrust baseLogisticsEntrust = entrusts.get(0);
                    if (!baseLogisticsEntrust.getId().equals(baseLogisticsEntrustDto.getId())) {
                        throw new BizException(I18nUtils.getMessage("base.check.LogisticsEntrust.config.defualt",new String[]{baseLogisticsEntrust.getOwnerName(), baseLogisticsEntrust.getConfigCode()}));
                    }
                }
            }

            BaseLogisticsEntrust entrust = BeanUtil.toBean(baseLogisticsEntrustDto, BaseLogisticsEntrust.class);
            entrust.setOwnerId(ownerDetailDto.getOwnerId());
            entrust.setOwnerName(ownerDetailDto.getOwnerName());
            entrust.setModifiedBy(currentUser.getUserId());
            entrust.setModifiedName(currentUser.getUserName());
            entrust.setModifiedDate(new Date());
            list.add(entrust);

        }
        BaseLogisticsEntrust logisticsEntrust = list.get(0);
        this.baseLogisticsEntrustMapper.updateById(logisticsEntrust);
    }

    @Override
    public PageInfo<BaseLogisticsEntrustVo> page(BaseLogisticsEntrustQuery query) {
        LambdaQueryWrapper<BaseLogisticsEntrust> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(query.getServiceProviderId())) {
            lambdaQueryWrapper.eq(BaseLogisticsEntrust::getServiceProviderId, query.getServiceProviderId());
        }
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        List<Long> warehouseIdList = permissionDomainService.getWarehouseIdListFromOrgAndData(currentUser.getGroupId(),currentUser.getUserId());
        if (CollectionUtils.isEmpty(warehouseIdList)){
            // 没有仓库权限，返回空
            return new PageInfo<>();
        }
        if (query.getWarehouseId() != null) {
            if (!warehouseIdList.contains(query.getWarehouseId())){
                return new PageInfo<>();
            }
            lambdaQueryWrapper.eq(BaseLogisticsEntrust::getWarehouseId, query.getWarehouseId());
        }else {
            lambdaQueryWrapper.in(BaseLogisticsEntrust::getWarehouseId, warehouseIdList);
        }
        if (query.getOwnerId() != null) {
            lambdaQueryWrapper.eq(BaseLogisticsEntrust::getOwnerId, query.getOwnerId());
        }
        if (query.getLogisticsId() != null) {
            lambdaQueryWrapper.eq(BaseLogisticsEntrust::getLogisticsId, query.getLogisticsId());
        }
        if (query.getDeliveryType() != null) {
            lambdaQueryWrapper.eq(BaseLogisticsEntrust::getDeliveryType, query.getDeliveryType());
        }
        if (query.getSettleMethod() != null) {
            lambdaQueryWrapper.eq(BaseLogisticsEntrust::getSettleMethod, query.getSettleMethod());
        }
        if (StringUtils.hasLength(query.getBranchId())) {
            lambdaQueryWrapper.eq(BaseLogisticsEntrust::getBranchId, query.getBranchId());
        }
        if (query.getState() != null) {
            lambdaQueryWrapper.eq(BaseLogisticsEntrust::getState, query.getState());
        }
        if (query.getDefaultFlag() != null) {
            lambdaQueryWrapper.eq(BaseLogisticsEntrust::getDefaultFlag, query.getDefaultFlag());
        }
        if (StringUtils.hasLength(query.getConfigCode())) {
            lambdaQueryWrapper.like(BaseLogisticsEntrust::getConfigCode, query.getConfigCode());
        }
        lambdaQueryWrapper.eq(BaseLogisticsEntrust::getDeletedFlag, 0);
        lambdaQueryWrapper.orderByDesc(BaseLogisticsEntrust::getCreatedDate);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<BaseLogisticsEntrust> list = this.baseLogisticsEntrustMapper.selectList(lambdaQueryWrapper);

        PageInfo<BaseLogisticsEntrustVo> pageInfo = PageUtil.convert(new PageInfo<>(list), item -> {
            BaseLogisticsEntrustVo entrustVo = BeanUtil.toBean(item, BaseLogisticsEntrustVo.class);
            String codeName = DeliveryTypeEnum.getCodeName(item.getDeliveryType());
            entrustVo.setDeliveryTypeName(codeName);
            return entrustVo;
        });
        return pageInfo;
    }

    @Override
    public void updateStatus(UpdateStatusDto statusDto) {
        LambdaQueryWrapper<BaseLogisticsEntrust> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(BaseLogisticsEntrust::getId, statusDto.getIds());
        BaseLogisticsEntrust entrust = new BaseLogisticsEntrust();
        entrust.setModifiedBy(FplUserUtil.getUserId());
        entrust.setModifiedName(FplUserUtil.getUserName());
        entrust.setModifiedDate(new Date());
        entrust.setState(statusDto.getState());
        this.baseLogisticsEntrustMapper.update(entrust, lambdaQueryWrapper);
    }

    @Override
    public BaseLogisticsEntrustDetailVo detail(Long id) {
        BaseLogisticsEntrust entrust = this.baseLogisticsEntrustMapper.selectById(id);
        BaseLogisticsEntrustDetailVo detailVo = BeanUtil.toBean(entrust, BaseLogisticsEntrustDetailVo.class);
        return detailVo;
    }

    @Override
    public void delete(DeleteDto deleteDto) {
        LambdaQueryWrapper<BaseLogisticsEntrust> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(BaseLogisticsEntrust::getId, deleteDto.getIds());
        List<BaseLogisticsEntrust> storeList = this.baseLogisticsEntrustMapper.selectList(lambdaQueryWrapper);
        List<BaseLogisticsEntrust> count = storeList.stream().filter(x -> x.getState() > 0).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(count)) {
            throw new BizException(I18nUtils.getMessage("base.check.logisticsentrust.batchdelete"));
        }
        BaseLogisticsEntrust entrust = new BaseLogisticsEntrust();
        entrust.setModifiedBy(FplUserUtil.getUserId());
        entrust.setModifiedName(FplUserUtil.getUserName());
        entrust.setModifiedDate(new Date());
        entrust.setDeletedFlag(BooleanEnum.TRUE.getCode());
        this.baseLogisticsEntrustMapper.update(entrust, lambdaQueryWrapper);
    }

    @Override
    public BaseLogisticsEntrustDto queryByCondition(LogisticsEntrustQuery logisticsEntrustQuery) {
        LambdaQueryWrapper<BaseLogisticsEntrust> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (logisticsEntrustQuery.getGroupId() != null) {
            lambdaQueryWrapper.eq(BaseLogisticsEntrust::getGroupId, logisticsEntrustQuery.getGroupId());
        }
        if (logisticsEntrustQuery.getServiceProviderId() != null) {
            lambdaQueryWrapper.eq(BaseLogisticsEntrust::getServiceProviderId, logisticsEntrustQuery.getServiceProviderId());
        }
        if (logisticsEntrustQuery.getWarehouseId() != null) {
            lambdaQueryWrapper.eq(BaseLogisticsEntrust::getWarehouseId, logisticsEntrustQuery.getWarehouseId());
        }
        if (logisticsEntrustQuery.getLogisticsCode() != null) {
            lambdaQueryWrapper.eq(BaseLogisticsEntrust::getLogisticsCode, logisticsEntrustQuery.getLogisticsCode());
        }
        if (logisticsEntrustQuery.getDeliveryType() != null) {
            lambdaQueryWrapper.eq(BaseLogisticsEntrust::getDeliveryType, logisticsEntrustQuery.getDeliveryType());
        }
        lambdaQueryWrapper.eq(BaseLogisticsEntrust::getState, StateEnum.ENABLE.getCode());
        if (logisticsEntrustQuery.getOwnerId() != null) {
            lambdaQueryWrapper.eq(BaseLogisticsEntrust::getOwnerId, logisticsEntrustQuery.getOwnerId());
        }
        lambdaQueryWrapper.eq(BaseLogisticsEntrust::getDeletedFlag, BooleanEnum.FALSE.getCode());
        List<BaseLogisticsEntrust> logisticsEntrustList = this.baseLogisticsEntrustMapper.selectList(lambdaQueryWrapper);
        BaseLogisticsEntrustDto baseLogisticsEntrustDto = null;

        if (CollectionUtil.isNotEmpty(logisticsEntrustList)) {
            for (int i = 0; i < logisticsEntrustList.size(); i++) {
                if (null != logisticsEntrustList.get(i).getDefaultFlag() && logisticsEntrustList.get(i).getDefaultFlag() == 1) {
                    baseLogisticsEntrustDto = BeanUtil.toBean(logisticsEntrustList.get(i), BaseLogisticsEntrustDto.class);
                }
            }
            if (null == baseLogisticsEntrustDto) {
                baseLogisticsEntrustDto = BeanUtil.toBean(logisticsEntrustList.get(0), BaseLogisticsEntrustDto.class);
            }
        }
        return baseLogisticsEntrustDto;
    }

}
