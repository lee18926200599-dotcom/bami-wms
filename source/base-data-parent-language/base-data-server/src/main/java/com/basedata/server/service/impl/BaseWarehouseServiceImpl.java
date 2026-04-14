package com.basedata.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.basedata.common.dto.DeleteDto;
import com.basedata.common.dto.UpdateStatusDto;
import com.basedata.common.dto.warehouse.BaseWarehouseDto;
import com.basedata.common.enums.BooleanEnum;
import com.basedata.common.enums.StateEnum;
import com.basedata.common.query.BaseDictionaryBatchQuery;
import com.basedata.common.query.WarehouseQuery;
import com.basedata.common.util.Constant;
import com.basedata.common.util.DateUtil;
import com.basedata.common.util.HuToolUtil;
import com.basedata.common.util.PageUtil;
import com.basedata.common.vo.BaseBatchDictionaryVo;
import com.basedata.common.vo.BaseWarehouseDetailVo;
import com.basedata.common.vo.BaseWarehousePageVo;
import com.basedata.common.vo.WarehouseVo;
import com.basedata.server.domain.PermissionDomainService;
import com.basedata.server.entity.BaseWarehouse;
import com.basedata.server.mapper.BaseWarehouseMapper;
import com.basedata.server.service.BaseWarehouseService;
import com.basedata.server.service.DictionaryInfoService;
import com.common.base.entity.CurrentUser;
import com.common.framework.aop.dict.DictionaryEnum;
import com.common.framework.execption.BizException;
import com.common.framework.execption.SystemException;
import com.common.framework.redis.RedisUtil;
import com.common.framework.user.FplUserUtil;
import com.common.language.util.I18nUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.permission.param.SyncDataPermissionParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class BaseWarehouseServiceImpl implements BaseWarehouseService {

    @Autowired
    private BaseWarehouseMapper baseWarehouseMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DictionaryInfoService dictionaryInfoService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private PermissionDomainService permissionDomainService;
    @Override
    public void save(BaseWarehouseDto warehouseDto) {
        BaseWarehouse baseWarehouse = BeanUtil.toBean(warehouseDto, BaseWarehouse.class);

        //判断仓库名是否存在（仓储服务商维度）
        LambdaQueryWrapper<BaseWarehouse> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BaseWarehouse::getWarehouseName, baseWarehouse.getWarehouseName())
                .eq(BaseWarehouse::getDeletedFlag, BooleanEnum.FALSE.getCode())
                .eq(BaseWarehouse::getServiceProviderId, baseWarehouse.getServiceProviderId());

        long countByCriteria = baseWarehouseMapper.selectCount(lambdaQueryWrapper);
        if (countByCriteria > 0) {
            throw new BizException(I18nUtils.getMessage("base.check.warehouse.exist"));
        }
        if(StringUtils.isNotBlank(warehouseDto.getWarehouseCode())){
            LambdaQueryWrapper<BaseWarehouse> existQueryWrapper = new LambdaQueryWrapper<>();
            existQueryWrapper.eq(BaseWarehouse::getWarehouseCode, warehouseDto.getWarehouseCode())
                    .eq(BaseWarehouse::getDeletedFlag, BooleanEnum.FALSE.getCode())
                    .eq(BaseWarehouse::getServiceProviderId, baseWarehouse.getServiceProviderId());
            List<BaseWarehouse> list = baseWarehouseMapper.selectList(existQueryWrapper);
            if(CollectionUtil.isNotEmpty(list)){
                throw new BizException(I18nUtils.getMessage("base.check.warehouse.code.exist"));
            }else{
                baseWarehouse.setWarehouseCode(warehouseDto.getWarehouseCode());
            }
        }else{
            //生成仓库自定义编码
            String warehouseCode = getWarehouseCode();
            baseWarehouse.setWarehouseCode(warehouseCode);
        }
        baseWarehouse.setState(StateEnum.NOT_ENABLE.getCode());
        baseWarehouse.setCreatedBy(FplUserUtil.getUserId());
        baseWarehouse.setCreatedName(FplUserUtil.getUserName());
        baseWarehouse.setCreatedDate(new Date());
        baseWarehouse.setDeletedFlag(BooleanEnum.FALSE.getCode());
        baseWarehouse.setVersion(0);

        this.baseWarehouseMapper.insert(baseWarehouse);

        //TODO 日志记录
    }

    private String getWarehouseCode() {

        PageHelper.startPage(1, 1);
        List<BaseWarehouse> list = baseWarehouseMapper.selectList(Wrappers.lambdaQuery(BaseWarehouse.class).orderByDesc(BaseWarehouse::getId));
        String code = "C0001";
        Integer codeNum = 0;
        if (CollectionUtils.isEmpty(list)) {
            return code;
        }
        BaseWarehouse baseWarehouse = list.get(0);
        if (StringUtils.isBlank(baseWarehouse.getWarehouseCode())) {
            return code;
        }
        try {
            String existCode = baseWarehouse.getWarehouseCode().substring(1);
            codeNum = Integer.valueOf(existCode) + 1;

        } catch (Exception e) {
            Pattern pattern = Pattern.compile("[0-9]+");
            Matcher matcher = pattern.matcher(baseWarehouse.getWarehouseCode());
            if (matcher.find()) {
                String num = matcher.group();
                codeNum = Integer.valueOf(num) + 1;
            } else {
                return code;
            }
        }
        if (codeNum > 9999) {
            code = "C" + codeNum;
        } else {
            code = "C" + String.format("%04d", codeNum);
        }
        return code;
    }
    @Override
    public void update(BaseWarehouseDto warehouseDto) {
        if (warehouseDto.getId() == null) {
            throw new SystemException(I18nUtils.getMessage("base.check.common.idnotnull"));
        }
        BaseWarehouse baseWarehouse = baseWarehouseMapper.selectById(warehouseDto.getId());
        if (baseWarehouse == null) {
            throw new SystemException(I18nUtils.getMessage("base.check.common.data.noexist"));
        }

        if (!baseWarehouse.getWarehouseCode().equals(baseWarehouse.getWarehouseCode())){
            throw new BizException(I18nUtils.getMessage("base.check.warehouse.code.cannot.modify"));
        }

        if (baseWarehouse.getState() != StateEnum.NOT_ENABLE.getCode()){
            if (!baseWarehouse.getServiceProviderId().equals(warehouseDto.getServiceProviderId())){
                throw new BizException(I18nUtils.getMessage("base.check.warehouse.unit.cannot.modify"));
            }
            if (!baseWarehouse.getGroupId().equals(warehouseDto.getGroupId())){
                throw new BizException(I18nUtils.getMessage("base.check.warehouse.group.cannot.modify"));
            }
        }

        BaseWarehouse baseWarehouseDo = BeanUtil.toBean(warehouseDto, BaseWarehouse.class);
        baseWarehouseDo.setModifiedBy(FplUserUtil.getUserId());
        baseWarehouseDo.setModifiedName(FplUserUtil.getUserName());
        baseWarehouseDo.setModifiedDate(new Date());
        this.baseWarehouseMapper.updateById(baseWarehouseDo);
        redisUtil.del(Constant.BASE_WAREHOUSE + baseWarehouse.getWarehouseCode());
        //TODO 日志记录

    }

    @Override
    public PageInfo<BaseWarehousePageVo> page(WarehouseQuery warehouseQuery) {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        PageHelper.startPage(warehouseQuery.getPageNum(), warehouseQuery.getPageSize());
        List<Long> orgIdList = permissionDomainService.getOrgList(currentUser.getGroupId(),currentUser.getUserId());
        if (CollectionUtils.isEmpty(orgIdList)){
            return new PageInfo<>();
        }
        LambdaQueryWrapper<BaseWarehouse> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(BaseWarehouse::getServiceProviderId,orgIdList);
        if (StringUtils.isNotEmpty(warehouseQuery.getWarehouseCode())) {
            lambdaQueryWrapper.likeLeft(BaseWarehouse::getWarehouseCode, warehouseQuery.getWarehouseCode());
        }
        if (StringUtils.isNotEmpty(warehouseQuery.getWarehouseName())) {
            lambdaQueryWrapper.likeLeft(BaseWarehouse::getWarehouseName, warehouseQuery.getWarehouseName());
        }
        if (warehouseQuery.getState() != null) {
            lambdaQueryWrapper.eq(BaseWarehouse::getState, warehouseQuery.getState());
        }
        if (warehouseQuery.getServiceProviderId() != null) {
            lambdaQueryWrapper.eq(BaseWarehouse::getServiceProviderId, warehouseQuery.getServiceProviderId());
        }
        if (warehouseQuery.getGroupId() != null) {
            lambdaQueryWrapper.eq(BaseWarehouse::getGroupId, warehouseQuery.getGroupId());
        } else {
            lambdaQueryWrapper.eq(BaseWarehouse::getGroupId, currentUser.getGroupId());
        }
        lambdaQueryWrapper.eq(BaseWarehouse::getDeletedFlag, 0);

        lambdaQueryWrapper.orderByDesc(BaseWarehouse::getCreatedDate);

        List<BaseWarehouse> list = this.baseWarehouseMapper.selectList(lambdaQueryWrapper);

        if (CollectionUtils.isEmpty(list)) {
            return new PageInfo<>();
        }

        BaseDictionaryBatchQuery dictionaryInfoQuery = new BaseDictionaryBatchQuery();
        dictionaryInfoQuery.setDictCodeList(Arrays.asList(DictionaryEnum.OPERATE_TYPE.name(), DictionaryEnum.WAREHOUSE_PROPERTIES.name()));
        List<BaseBatchDictionaryVo> datas = dictionaryInfoService.queryDictionaryList(dictionaryInfoQuery);

        PageInfo<BaseWarehousePageVo> pageInfo = PageUtil.convert(new PageInfo<>(list), item -> {
            BaseWarehousePageVo warehousePageVo = BeanUtil.toBean(item, BaseWarehousePageVo.class);
            String dateToStr = DateUtil.parseDateToStr(DateUtil.YYYY_MM_DD, item.getOpenDate());
            warehousePageVo.setOpenDate(dateToStr);

            BaseBatchDictionaryVo category = datas.stream().filter(x -> x.getDictCode().equals(DictionaryEnum.OPERATE_TYPE.name())).findFirst().orElse(null);
            if (category != null) {
                if (StringUtils.isNotEmpty(item.getOperationType())) {
                    BaseBatchDictionaryVo.BaseBatchDictionaryDetailVo dictionaryVo = category.getDetailVoList().stream().filter(x -> item.getOperationType().equals(x.getItemCode()))
                            .findFirst().orElse(null);
                    if (dictionaryVo != null) {
                        warehousePageVo.setOperationTypeName(dictionaryVo.getItemName());
                    }
                }
            }

            BaseBatchDictionaryVo ruleVo = datas.stream().filter(x -> x.getDictCode().equals(DictionaryEnum.WAREHOUSE_PROPERTIES.name())).findFirst().orElse(null);
            if (ruleVo != null) {
                if (StringUtils.isNotEmpty(item.getWarehouseProperty())) {
                    BaseBatchDictionaryVo.BaseBatchDictionaryDetailVo dictionaryVo = ruleVo.getDetailVoList().stream().filter(x -> item.getWarehouseProperty().equals(x.getItemCode()))
                            .findFirst().orElse(null);
                    if (dictionaryVo != null) {
                        warehousePageVo.setWarehousePropertyName(dictionaryVo.getItemName());
                    }
                }
            }
            return warehousePageVo;
        });
        return pageInfo;
    }

    @Override
    public List<WarehouseVo> getWarehouse() {
        LambdaQueryWrapper<BaseWarehouse> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BaseWarehouse::getDeletedFlag, BooleanEnum.FALSE.getCode());
        lambdaQueryWrapper.eq(BaseWarehouse::getState, StateEnum.ENABLE.getCode());
        List<BaseWarehouse> byCriteria = this.baseWarehouseMapper.selectList(lambdaQueryWrapper);
        List<WarehouseVo> voList = byCriteria.stream().map(item -> {
            WarehouseVo warehouseVo = new WarehouseVo();
            warehouseVo.setWarehouseId(item.getId());
            warehouseVo.setWarehouseCode(item.getWarehouseCode());
            warehouseVo.setWarehouseName(item.getWarehouseName());
            return warehouseVo;
        }).collect(Collectors.toList());
        return voList;
    }

    @Override
    public void updateStatus(UpdateStatusDto statusDto) {
        LambdaQueryWrapper<BaseWarehouse> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(BaseWarehouse::getId, statusDto.getIds());

        BaseWarehouse baseWarehouse = new BaseWarehouse();
        baseWarehouse.setModifiedBy(FplUserUtil.getUserId());
        baseWarehouse.setModifiedName(FplUserUtil.getUserName());
        baseWarehouse.setModifiedDate(new Date());
        baseWarehouse.setState(statusDto.getState());
        this.baseWarehouseMapper.update(baseWarehouse, lambdaQueryWrapper);

        List<BaseWarehouse> list = baseWarehouseMapper.selectList(lambdaQueryWrapper);
        if (!CollectionUtils.isEmpty(list)) {
            Long userId = FplUserUtil.getUserId();
            for (BaseWarehouse warehouse : list) {
                redisUtil.del(Constant.BASE_WAREHOUSE+warehouse.getWarehouseCode());
                SyncDataPermissionParam syncDataPermissionParam = new SyncDataPermissionParam();
                syncDataPermissionParam.setWarehouseId(warehouse.getId());
                syncDataPermissionParam.setWarehouseName(warehouse.getWarehouseName());
                syncDataPermissionParam.setWarehouseCode(warehouse.getWarehouseCode());
                syncDataPermissionParam.setState(statusDto.getState());
                syncDataPermissionParam.setGroupId(warehouse.getGroupId());
                syncDataPermissionParam.setOrgId(warehouse.getServiceProviderId());
                syncDataPermissionParam.setUserId(userId);
                permissionDomainService.syncWarehouse(syncDataPermissionParam);
            }
        }

    }


    @Override
    public BaseWarehouseDetailVo detail(Long id) {
        BaseWarehouse baseWarehouse = this.baseWarehouseMapper.selectById(id);
        if (baseWarehouse == null) {
            throw new SystemException(I18nUtils.getMessage("base.check.common.data.noexist"));
        }
        BaseWarehouseDetailVo detailVo = BeanUtil.toBean(baseWarehouse, BaseWarehouseDetailVo.class);
        String dateToStr = DateUtil.parseDateToStr(DateUtil.YYYY_MM_DD, baseWarehouse.getOpenDate());
        detailVo.setOpenDate(dateToStr);
        return detailVo;
    }


    @Override
    public void delete(DeleteDto deleteDto) {

        LambdaQueryWrapper<BaseWarehouse> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(BaseWarehouse::getId, deleteDto.getIds());

        List<BaseWarehouse> warehouseList = this.baseWarehouseMapper.selectList(lambdaQueryWrapper);
        List<BaseWarehouse> count = warehouseList.stream().filter(x -> x.getState() > 0).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(count)) {
            throw new BizException(I18nUtils.getMessage("base.check.warehouse.batchdelete"));
        }
        BaseWarehouse baseWarehouse = new BaseWarehouse();
        baseWarehouse.setModifiedBy(FplUserUtil.getUserId());
        baseWarehouse.setModifiedName(FplUserUtil.getUserName());
        baseWarehouse.setModifiedDate(new Date());
        baseWarehouse.setDeletedFlag(BooleanEnum.TRUE.getCode());
        this.baseWarehouseMapper.update(baseWarehouse, lambdaQueryWrapper);
        warehouseList.forEach(item -> {
            redisUtil.del(Constant.BASE_WAREHOUSE + item.getWarehouseCode());
        });
    }

    @Override
    public List<BaseWarehouseDto> getWarehouseByOrgId(Long orgId) throws Exception {
        LambdaQueryWrapper<BaseWarehouse> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BaseWarehouse::getServiceProviderId, orgId);
        lambdaQueryWrapper.eq(BaseWarehouse::getDeletedFlag, BooleanEnum.FALSE.getCode());
        List<BaseWarehouse> warehouseList = this.baseWarehouseMapper.selectList(lambdaQueryWrapper);
        List<BaseWarehouseDto> dtoList = null;
        if (CollectionUtil.isNotEmpty(warehouseList)) {
            dtoList = HuToolUtil.exchange(warehouseList, BaseWarehouseDto.class);
        }
        return dtoList;
    }

    @Override
    public BaseWarehouseDto getWarehouseById(Long id) {
        BaseWarehouse baseWarehouse = baseWarehouseMapper.selectById(id);
        BaseWarehouseDto baseWarehouseDto = BeanUtil.toBean(baseWarehouse, BaseWarehouseDto.class);
        return baseWarehouseDto;
    }

    @Override
    public BaseWarehouseDto getWarehouseByCode(String warehouseCode) {
        Object obj = redisUtil.get(Constant.BASE_WAREHOUSE + warehouseCode);
        if (obj != null) {
            return (BaseWarehouseDto) obj;
        }
        LambdaQueryWrapper<BaseWarehouse> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BaseWarehouse::getWarehouseCode, warehouseCode);
        lambdaQueryWrapper.eq(BaseWarehouse::getDeletedFlag, BooleanEnum.FALSE.getCode());

        List<BaseWarehouse> warehouseList = this.baseWarehouseMapper.selectList(lambdaQueryWrapper);
        BaseWarehouseDto baseWarehouseDto = null;
        if (CollectionUtil.isNotEmpty(warehouseList)) {
            baseWarehouseDto = new BaseWarehouseDto();
            baseWarehouseDto = BeanUtil.toBean(warehouseList.get(0), BaseWarehouseDto.class);
        }
        if (baseWarehouseDto != null) {
            redisUtil.set(Constant.BASE_WAREHOUSE + warehouseCode, baseWarehouseDto, Constant.REDIS_TIME_OUT);
        }
        return baseWarehouseDto;
    }

    /**
     * 查询仓储信息 -Feign使用
     * @param warehouseQuery
     * @return
     * @throws Exception
     */
    public List<BaseWarehouseDto> getWarehouseList(WarehouseQuery warehouseQuery) throws Exception {
        // TODO 待改造
        LambdaQueryWrapper<BaseWarehouse> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (warehouseQuery.getServiceProviderId()!=null){
            lambdaQueryWrapper.eq(BaseWarehouse::getServiceProviderId, warehouseQuery.getServiceProviderId());
        }
        if (warehouseQuery.getWarehouseId() != null) {
            lambdaQueryWrapper.eq(BaseWarehouse::getId, warehouseQuery.getWarehouseId());
        }
        if (warehouseQuery.getGroupId() != null) {
            lambdaQueryWrapper.eq(BaseWarehouse::getGroupId, warehouseQuery.getGroupId());
        }
        if (!CollectionUtils.isEmpty(warehouseQuery.getServiceProviderIds())) {
            lambdaQueryWrapper.in(BaseWarehouse::getServiceProviderId, warehouseQuery.getServiceProviderIds());
        }
        if (warehouseQuery.getState() != null) {
            lambdaQueryWrapper.eq(BaseWarehouse::getState, warehouseQuery.getState());
        }
        if (!CollectionUtils.isEmpty(warehouseQuery.getWarehouseIdList())) {
            lambdaQueryWrapper.in(BaseWarehouse::getId, warehouseQuery.getWarehouseIdList());
        }
        if (StringUtils.isNotBlank(warehouseQuery.getWarehouseName())) {
            lambdaQueryWrapper.like(BaseWarehouse::getWarehouseName, warehouseQuery.getWarehouseName());
        }
        List<BaseWarehouse> warehouseList = this.baseWarehouseMapper.selectList(lambdaQueryWrapper);
        List<BaseWarehouseDto> dtoList = null;
        if (CollectionUtil.isNotEmpty(warehouseList)) {
            dtoList = HuToolUtil.exchange(warehouseList, BaseWarehouseDto.class);
        }
        return dtoList;
    }
}
