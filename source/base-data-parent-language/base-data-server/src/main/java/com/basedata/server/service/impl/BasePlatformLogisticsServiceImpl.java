package com.basedata.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.basedata.common.dto.*;
import com.basedata.common.enums.BooleanEnum;
import com.basedata.common.enums.StateEnum;
import com.basedata.common.query.LogisticsDetailQuery;
import com.basedata.common.util.HuToolUtil;
import com.basedata.common.util.PageUtil;
import com.basedata.server.domain.OrgDomainService;
import com.basedata.server.domain.PermissionDomainService;
import com.basedata.server.dto.*;
import com.basedata.server.entity.BasePlatformLogistics;
import com.basedata.server.entity.BasePlatformLogisticsDetail;
import com.basedata.server.entity.BaseWarehouse;
import com.basedata.server.mapper.BasePlatformLogisticsMapper;
import com.basedata.server.mapper.BaseWarehouseMapper;
import com.basedata.server.query.BasePlatformLogisticsQueryVo;
import com.basedata.server.service.BasePlatformLogisticsService;
import com.basedata.server.vo.BasePlatformLogisticsVo;
import com.common.base.entity.CurrentUser;
import com.common.framework.number.BaseNoGenerateUtil;
import com.common.framework.user.FplUserUtil;
import com.common.language.util.I18nUtils;
import com.common.util.util.AssertUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.OrganizationDto;
import com.org.permission.common.org.param.QueryByIdReqParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 承运商编码对照关系表 服务实现类
 * </p>
 */
@Service
public class BasePlatformLogisticsServiceImpl implements BasePlatformLogisticsService {
    @Resource
    private BasePlatformLogisticsMapper platformLogisticsMapper;
    @Resource
    private OrgDomainService orgDomainService;
    @Resource
    private BaseWarehouseMapper warehouseMapper;
    @Resource
    private PermissionDomainService permissionDomainService;

    /**
     * 配置类别（1电商平台，2外部系统）
     */
    private static final int CONFIG_TYPE_PLATFORM = 1;
    private static final int CONFIG_TYPE_EXTERNAL_SYSTEM = 2;

    /**
     * 批量保存配置
     *
     * @param dto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchSaveConfig(BasePlatformLogisticsUpdateReqDTO dto) {
        boolean a = !CollectionUtils.isEmpty(dto.getPlatformTabList()) || !CollectionUtils.isEmpty(dto.getExternalSystemTabList());
        Assert.isTrue(a, I18nUtils.getMessage("base.check.paltformlogistics.confignotnull"));

        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));
        Long serviceProviderId = currentUser.getServiceProviderId();

        List<BasePlatformLogisticsDetail> updateList = new ArrayList<>();
        List<BasePlatformLogisticsDetail> addDetailList = new ArrayList<>();

        BasePlatformLogistics oneHdr = this.findOneHdr(serviceProviderId, dto.getDeliveryType(), dto.getLogisticsId());

        QueryByIdReqParam reqParam = new QueryByIdReqParam();
        reqParam.setId(serviceProviderId);
        OrganizationDto serviceProviderInfo = orgDomainService.getServiceProviderById(serviceProviderId);
        Assert.notNull(serviceProviderInfo, I18nUtils.getMessage("base.check.paltformlogistics.groupunitnoexist"));

        // 更新主表
        BasePlatformLogistics hdr = new BasePlatformLogistics();
        BeanUtils.copyProperties(dto, hdr);
        if (dto.getId() == null) {
            Assert.isNull(oneHdr, I18nUtils.getMessage("base.check.paltformlogistics.configexist"));
            // 配置编号生成规则:YYMMDD+4 位流水号，4 位流水号每天从 0001 开始
            if (StringUtils.isBlank(hdr.getConfigCode())) {
                hdr.setConfigCode(BaseNoGenerateUtil.generateRuleCode(serviceProviderId + ""));
            }
            hdr.setState(StateEnum.NOT_ENABLE.getCode());
            hdr.setDefaultCreateValue(currentUser);
            hdr.setServiceProviderId(serviceProviderId);
            hdr.setServiceProviderName(serviceProviderInfo.getOrgName());
            hdr.setGroupId(currentUser.getGroupId());
            platformLogisticsMapper.saveHeader(hdr);
            dto.setId(hdr.getId());
        } else {
            BasePlatformLogistics currentOne = this.findOneHdrById(dto.getId());
            if (StateEnum.NOT_ENABLE.getCode().equals(currentOne.getState())) {
                Assert.isFalse(!currentOne.getConfigName().equals(dto.getConfigName()) || !currentOne.getConfigCode().equals(dto.getConfigCode()), "创建状态:规则描述、配置编码不能修改！");
            } else if (StateEnum.ENABLE.getCode().equals(currentOne.getState())) {
                Assert.isFalse(!currentOne.getConfigName().equals(dto.getConfigName()) || !currentOne.getConfigCode().equals(dto.getConfigCode()) || !currentOne.getDeliveryType().equals(dto.getDeliveryType()) || !currentOne.getLogisticsCode().equals(dto.getLogisticsCode()), "停用状态:规则描述、配置编码、配送方式、承运商不能修改！");
            } else if (StateEnum.DISABLE.getCode().equals(currentOne.getState())) {
                Assert.isFalse(!currentOne.getConfigName().equals(dto.getConfigName()) || !currentOne.getConfigCode().equals(dto.getConfigCode()) || !currentOne.getDeliveryType().equals(dto.getDeliveryType()) || !currentOne.getLogisticsCode().equals(dto.getLogisticsCode()), "启用状态:规则描述、配置编码、配送方式、承运商不能修改！");
            }

            Assert.isFalse(oneHdr != null && oneHdr.getId().longValue() != dto.getId(), I18nUtils.getMessage("base.check.paltformlogistics.configexist"));
            BasePlatformLogistics exist = findOneHdrById(dto.getId());
            exist.setDefaultUpdateValue(currentUser);
            platformLogisticsMapper.updateById(exist);
            hdr.setId(dto.getId());
        }

        if (!CollectionUtils.isEmpty(dto.getPlatformTabList())) {
            for (BasePlatformLogisticsUpdateReqDTO.PlatformTab platformTab : dto.getPlatformTabList()) {
                Assert.notNull(platformTab.getState(), I18nUtils.getMessage("base.check.paltformlogistics.configdetailstatenotnull"));
                // 校验并查找唯一配置
                BasePlatformLogisticsDetailReqDTO detailReqDTO = new BasePlatformLogisticsDetailReqDTO();
                detailReqDTO.setConfigType(CONFIG_TYPE_PLATFORM);
                detailReqDTO.setServiceProviderId(serviceProviderId);
                detailReqDTO.setDeliveryType(dto.getDeliveryType());
                detailReqDTO.setLogisticsId(dto.getLogisticsId());
                detailReqDTO.setPlatformCode(platformTab.getPlatformCode());
                detailReqDTO.setPlatformLogisticsCode(platformTab.getPlatformLogisticsCode());
                BasePlatformLogisticsDetail oneDtl = this.findOneDtl(detailReqDTO);

                // 根据是否传明细ID，判断配置明细 是新增还是更新
                BasePlatformLogisticsDetail entity = new BasePlatformLogisticsDetail();
                BeanUtils.copyProperties(dto, entity);
                BeanUtils.copyProperties(platformTab, entity);
                entity.setConfigState(hdr.getState());
                if (platformTab.getId() == null) {
                    // 是已存在当前同电商平台的配置行,如有则提示“已存在当前电商平台配 置行,不能重
                    Assert.isNull(oneDtl, I18nUtils.getMessage("base.check.paltformlogistics.ecconfigexist"));
                    // 新增
                    entity.setConfigId(hdr.getId());
                    // 配置类别（1电商平台，2外部系统）
                    entity.setConfigType(CONFIG_TYPE_PLATFORM);
                    entity.setDefaultCreateValue(currentUser);
                    // 是否启用，前端传
//                    entity.setState(StateEnum.ENABLE.getCode());
                    entity.setServiceProviderId(serviceProviderId);
                    addDetailList.add(entity);
                } else {
                    Assert.isFalse(oneDtl != null && oneDtl.getId().longValue() != platformTab.getId(), I18nUtils.getMessage("base.check.paltformlogistics.ecconfigexist"));
                    Assert.notNull(platformTab.getDeletedFlag(), I18nUtils.getMessage("base.check.paltformlogistics.ecconfigdeletestatenotnull"));
                    // 修改或删除
//                updateList.add(updateEntity);
                    entity.setDefaultUpdateValue(currentUser);
                    platformLogisticsMapper.updateDetailById(entity);
                }
            }
        }

        if (!CollectionUtils.isEmpty(dto.getExternalSystemTabList())) {
            for (BasePlatformLogisticsUpdateReqDTO.ExternalSystemTab externalSystemTab : dto.getExternalSystemTabList()) {
                Assert.notNull(externalSystemTab.getState(), I18nUtils.getMessage("base.check.paltformlogistics.externalconfigdetailstatenotnull"));
                BaseWarehouse baseWarehouse = warehouseMapper.selectById(externalSystemTab.getWarehouseId());
                AssertUtils.isNotNull(baseWarehouse,I18nUtils.getMessage("base.check.paltformlogistics.warehouseinfonoexist",new String[]{externalSystemTab.getWarehouseName()}));
                // 校验并查找唯一配置
                BasePlatformLogisticsDetailReqDTO detailReqDTO = new BasePlatformLogisticsDetailReqDTO();
                detailReqDTO.setConfigType(CONFIG_TYPE_EXTERNAL_SYSTEM);
                detailReqDTO.setServiceProviderId(baseWarehouse.getServiceProviderId());
                detailReqDTO.setDeliveryType(dto.getDeliveryType());
                detailReqDTO.setLogisticsId(dto.getLogisticsId());
                detailReqDTO.setWarehouseId(externalSystemTab.getWarehouseId());
                detailReqDTO.setOwnerId(externalSystemTab.getOwnerId());
                detailReqDTO.setExternalLogisticsCode(externalSystemTab.getExternalLogisticsCode());
                BasePlatformLogisticsDetail oneDtl = this.findOneDtl(detailReqDTO);

                // 根据是否传明细ID，判断配置明细 是新增还是更新
                BasePlatformLogisticsDetail entity = new BasePlatformLogisticsDetail();
                BeanUtils.copyProperties(dto, entity);
                BeanUtils.copyProperties(externalSystemTab, entity);
                entity.setServiceProviderId(baseWarehouse.getServiceProviderId());
                // 是否已存在当前选中同仓库同货主的配置行,如有则提示“已存在当前仓库当前货主配置行,不能重复配置！
                if (externalSystemTab.getId() == null) {
                    Assert.isNull(oneDtl, I18nUtils.getMessage("base.check.paltformlogistics.warehouseownerconfigexist"));
                    entity.setConfigId(hdr.getId());
                    entity.setConfigState(hdr.getState());
                    // 配置类别（1电商平台，2外部系统）
                    entity.setConfigType(CONFIG_TYPE_EXTERNAL_SYSTEM);
                    entity.setDefaultCreateValue(currentUser);
                    // 是否启用，前端传
//                    entity.setState(StateEnum.ENABLE.getCode());
//                    entity.setServiceProviderId(serviceProviderId);
                    addDetailList.add(entity);
                } else {
                    Assert.isFalse(oneDtl != null && oneDtl.getId().longValue() != externalSystemTab.getId(), I18nUtils.getMessage("base.check.paltformlogistics.warehouseownerconfigexist"));
                    Assert.notNull(externalSystemTab.getDeletedFlag(), I18nUtils.getMessage("base.check.paltformlogistics.externaldetaildeletestatenotnull"));
//                  updateList.add(updateEntity);
                    entity.setDefaultUpdateValue(currentUser);
                    platformLogisticsMapper.updateDetailById(entity);
                }
            }
        }
        if (!CollectionUtils.isEmpty(addDetailList)) {
            platformLogisticsMapper.batchSaveDetail(addDetailList);
        }
    }

    /**
     * 分页查询
     *
     * @param queryVo
     * @return
     */
    @Override
    public PageInfo<BasePlatformLogisticsVo> queryPageList(BasePlatformLogisticsQueryVo queryVo) throws Exception {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));
        // 3PL基础数据，集团级别查询权限，无需切换业务单元（仓储服务商）
//        queryVo.setServiceProviderId(currentUser.getServiceProviderId());
        List<Long> orgIdList = permissionDomainService.getOrgList(currentUser.getGroupId(), currentUser.getUserId());
        if (CollectionUtils.isEmpty(orgIdList)){
            return new PageInfo<>();
        }
        BasePlatformLogisticsReqDTO reqDTO = BeanUtil.toBean(queryVo, BasePlatformLogisticsReqDTO.class);
        reqDTO.setServiceProviderIdList(orgIdList);
        // 查询内容在明细表：先找到主表ID，再查主表
        if(StringUtils.isNotBlank(queryVo.getPlatformCode()) ||
                queryVo.getWarehouseId() !=null ||!CollectionUtils.isEmpty(queryVo.getOwnerIdList())){
            BasePlatformLogisticsDetailReqDTO detailReqDTO = new BasePlatformLogisticsDetailReqDTO();
            BeanUtils.copyProperties(queryVo, detailReqDTO);
            detailReqDTO.setPlatformCode(queryVo.getPlatformCode());
            detailReqDTO.setWarehouseId(queryVo.getWarehouseId());
            detailReqDTO.setOwnerIdList(queryVo.getOwnerIdList());
            List<BasePlatformLogisticsDetail> detailList = platformLogisticsMapper.findDetailList(detailReqDTO);
            if(CollectionUtils.isEmpty(detailList)){
                return PageInfo.of(new ArrayList<>());
            }
            reqDTO.setIds(detailList.stream().map(BasePlatformLogisticsDetail::getConfigId).distinct().collect(Collectors.toList()));
        }
        reqDTO.setGroupId(currentUser.getGroupId());
        PageHelper.startPage(queryVo.getPageNum(), queryVo.getPageSize());
        List<BasePlatformLogistics> list = platformLogisticsMapper.findList(reqDTO);
        return PageUtil.convert(new PageInfo<>(list), item -> BeanUtil.toBean(item, BasePlatformLogisticsVo.class));
    }

    /**
     * 明细查询
     *
     * @param configId
     * @return
     */
    @Override
    public BasePlatformLogisticsDTO queryDetailList(Long configId) throws Exception {
        BasePlatformLogisticsDetailReqDTO detailReqDTO = new BasePlatformLogisticsDetailReqDTO();
        detailReqDTO.setConfigId(configId);
        List<BasePlatformLogisticsDetail> detailList = platformLogisticsMapper.findDetailList(detailReqDTO);

        BasePlatformLogisticsDTO dto = new BasePlatformLogisticsDTO();
        BeanUtils.copyProperties(detailList.get(0), dto);
        List<BasePlatformLogisticsDetail> platform = detailList.stream().filter(x -> x.getConfigType() == CONFIG_TYPE_PLATFORM).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(platform)) {
            dto.setPlatformTabList(HuToolUtil.exchange(platform, BasePlatformLogisticsDTO.PlatformTab.class));
        }
        List<BasePlatformLogisticsDetail> externalSystemTabList = detailList.stream().filter(x -> x.getConfigType() == CONFIG_TYPE_EXTERNAL_SYSTEM).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(externalSystemTabList)) {
            dto.setExternalSystemTabList(HuToolUtil.exchange(externalSystemTabList, BasePlatformLogisticsDTO.ExternalSystemTab.class));
        }
        return dto;
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @Override
    public boolean deleteByIds(List<Long> ids) {
        Assert.notEmpty(ids, I18nUtils.getMessage("base.check.common.configidarraynotnull"));
        BasePlatformLogisticsReqDTO reqDTO = new BasePlatformLogisticsReqDTO();
        reqDTO.setIds(ids);
        List<BasePlatformLogistics> currentList = platformLogisticsMapper.findList(reqDTO);
        Assert.notEmpty(currentList, I18nUtils.getMessage("base.check.common.selecteddatanoexist"));
        Assert.isFalse(currentList.stream().anyMatch(x -> StateEnum.NOT_ENABLE.getCode().intValue() != x.getState()),
                I18nUtils.getMessage("base.check.common.onlydeletecreatestate"));

        // 逻辑删除明细配置
        BasePlatformLogisticsDetailBatchUpdateDto detailBatchUpdateDto = new BasePlatformLogisticsDetailBatchUpdateDto();
        detailBatchUpdateDto.setConfigIdList(ids);
        detailBatchUpdateDto.setDeletedFlag(BooleanEnum.TRUE.getCode());
        detailBatchUpdateDto.setModifiedBy(FplUserUtil.getUserId());
        detailBatchUpdateDto.setModifiedName(FplUserUtil.getUserName());
        detailBatchUpdateDto.setModifiedDate(LocalDateTime.now());
        detailBatchUpdateDto.setRemark(I18nUtils.getMessage("base.check.paltformlogistics.mainconfigdelete"));
        platformLogisticsMapper.batchUpdateDetail(detailBatchUpdateDto);
        // 逻辑删除主配置
        GeneralBatchUpdateByIdsDto updateByIdsDto = new GeneralBatchUpdateByIdsDto();
        updateByIdsDto.setIds(ids);
        updateByIdsDto.setDeletedFlag(BooleanEnum.TRUE.getCode());
        updateByIdsDto.setModifiedBy(FplUserUtil.getUserId());
        updateByIdsDto.setModifiedName(FplUserUtil.getUserName());
        updateByIdsDto.setModifiedDate(LocalDateTime.now());
        return platformLogisticsMapper.batchUpdate(updateByIdsDto) > 0;
    }

    /**
     * 批量启用停用
     *
     * @param statusDto
     * @return
     */
    @Override
    public boolean batchEnableOrDisable(UpdateStatusDto statusDto) {
        Assert.notEmpty(statusDto.getIds(), I18nUtils.getMessage("base.check.common.configidarraynotnull"));

        BasePlatformLogisticsReqDTO reqDTO = new BasePlatformLogisticsReqDTO();
        reqDTO.setIds(statusDto.getIds());
        List<BasePlatformLogistics> currentList = platformLogisticsMapper.findList(reqDTO);
        Assert.notEmpty(currentList, I18nUtils.getMessage("base.check.common.selecteddatanoexist"));
        // 要改成启用状态
        if (StateEnum.ENABLE.getCode().intValue() == statusDto.getState()) {
            Assert.isFalse(currentList.stream().anyMatch(x ->
                            StateEnum.DISABLE.getCode().intValue() != x.getState()
                                    && StateEnum.NOT_ENABLE.getCode().intValue() != x.getState()),
                    I18nUtils.getMessage("base.check.paltformlogistics.canenablestate"));
        } else if (StateEnum.DISABLE.getCode().intValue() == statusDto.getState()) {
            Assert.isFalse(currentList.stream().anyMatch(x ->
                            StateEnum.ENABLE.getCode().intValue() != x.getState()),
                    I18nUtils.getMessage("base.check.paltformlogistics.candisablestate"));
        }

        // 批量更新明细冗余的主表状态
        BasePlatformLogisticsDetailBatchUpdateDto detailBatchUpdateDto = new BasePlatformLogisticsDetailBatchUpdateDto();
        detailBatchUpdateDto.setConfigIdList(statusDto.getIds());
        detailBatchUpdateDto.setConfigState(statusDto.getState());
        detailBatchUpdateDto.setModifiedBy(FplUserUtil.getUserId());
        detailBatchUpdateDto.setModifiedName(FplUserUtil.getUserName());
        detailBatchUpdateDto.setModifiedDate(LocalDateTime.now());
        platformLogisticsMapper.batchUpdateDetail(detailBatchUpdateDto);
        // 更新主配置状态
        GeneralBatchUpdateByIdsDto updateByIdsDto = new GeneralBatchUpdateByIdsDto();
        updateByIdsDto.setIds(statusDto.getIds());
        updateByIdsDto.setState(statusDto.getState());
        updateByIdsDto.setModifiedBy(FplUserUtil.getUserId());
        updateByIdsDto.setModifiedName(FplUserUtil.getUserName());
        updateByIdsDto.setModifiedDate(LocalDateTime.now());
        return platformLogisticsMapper.batchUpdate(updateByIdsDto) > 0;
    }

    public BasePlatformLogistics findOneHdrById(Long id) {
        BasePlatformLogisticsReqDTO reqDTO = new BasePlatformLogisticsReqDTO();
        reqDTO.setId(id);
        List<BasePlatformLogistics> hdrList = platformLogisticsMapper.findList(reqDTO);
        Assert.notEmpty(hdrList, I18nUtils.getMessage("base.check.paltformlogistics.confignoexist"));
        return hdrList.get(0);
    }

    /**
     * 根据 仓储服务商、配送方式、承运商ID 找唯一一条主配置
     *
     * @param serviceProviderId
     * @param deliveryType
     * @param logisticsId
     * @return 1、存在，返回对象；2、不存在，返回null；3、存在2条以上，抛异常
     */
    @Override
    public BasePlatformLogistics findOneHdr(Long serviceProviderId, Integer deliveryType, Long logisticsId) {
        Assert.notNull(deliveryType, I18nUtils.getMessage("base.check.paltformlogistics.deliverytype.notnull"));
        Assert.notNull(serviceProviderId, I18nUtils.getMessage("base.check.paltformlogistics.serviceproviderid.notnull"));
        Assert.notNull(logisticsId, I18nUtils.getMessage("base.check.paltformlogistics.logisticsid.notnull"));

        BasePlatformLogisticsReqDTO reqDTO = new BasePlatformLogisticsReqDTO();
        reqDTO.setServiceProviderId(serviceProviderId);
        reqDTO.setDeliveryType(deliveryType);
        reqDTO.setLogisticsId(logisticsId);
        List<BasePlatformLogistics> hdrList = platformLogisticsMapper.findList(reqDTO);
        if (CollectionUtils.isEmpty(hdrList)) {
            return null;
        }
        Assert.isFalse(hdrList.size() > 1, I18nUtils.getMessage("base.check.paltformlogistics.warehousedeliveylogistisc.exist"));
        return hdrList.get(0);
    }

    /**
     * 校验并查找唯一配置
     * 查找 仓储服务商、配送方式、系统承运商ID 找唯一一条主配置
     * configType = 1：电商平台、电商平台编码
     * configType = 2：仓库ID、货主ID、外部系统承运商编码
     *
     * @param detailReqDTO
     * @return 1、存在，返回对象；2、不存在，返回null；3、存在2条以上，抛异常
     */
    @Override
    public BasePlatformLogisticsDetail findOneDtl(BasePlatformLogisticsDetailReqDTO detailReqDTO) {
        Assert.notNull(detailReqDTO.getConfigType(), I18nUtils.getMessage("base.check.paltformlogistics.configtype.notnull"));
        Assert.notNull(detailReqDTO.getServiceProviderId(), I18nUtils.getMessage("base.check.paltformlogistics.serviceproviderid.notnull"));
        Assert.notNull(detailReqDTO.getDeliveryType(), I18nUtils.getMessage("base.check.paltformlogistics.deliverytype.notnull"));
        Assert.notNull(detailReqDTO.getLogisticsId(), I18nUtils.getMessage("base.check.paltformlogistics.logisticsid.notnull"));
        if (detailReqDTO.getConfigType() == CONFIG_TYPE_PLATFORM) {
            Assert.notBlank(detailReqDTO.getPlatformCode(), I18nUtils.getMessage("base.check.paltformlogistics.platformcode.notnull"));
            Assert.notBlank(detailReqDTO.getPlatformLogisticsCode(), I18nUtils.getMessage("base.check.paltformlogistics.platformlogisticscode.notnull"));
        } else if (detailReqDTO.getConfigType() == CONFIG_TYPE_EXTERNAL_SYSTEM) {
            Assert.notNull(detailReqDTO.getWarehouseId(), I18nUtils.getMessage("base.check.paltformlogistics.warehouseid.notnull"));
            Assert.notNull(detailReqDTO.getOwnerId(), I18nUtils.getMessage("base.check.paltformlogistics.ownerid.notnull"));
            Assert.notBlank(detailReqDTO.getExternalLogisticsCode(), I18nUtils.getMessage("base.check.paltformlogistics.externallogisticscode.notnull"));
        }
        List<BasePlatformLogisticsDetail> detailList = findDetailList(detailReqDTO);
        if (CollectionUtils.isEmpty(detailList)) {
            return null;
        }
        if (detailReqDTO.getConfigType() == CONFIG_TYPE_PLATFORM) {
            Assert.isFalse(detailList.size() > 1, I18nUtils.getMessage("base.check.paltformlogistics.duplicate.platform.config"));
        } else if (detailReqDTO.getConfigType() == CONFIG_TYPE_EXTERNAL_SYSTEM) {
            Assert.isFalse(detailList.size() > 1, I18nUtils.getMessage("base.check.paltformlogistics.duplicate.external.config"));
        }
        return detailList.get(0);
    }

    /**
     * 查询承运商对照关系明细
     *
     * @param detailReqDTO
     * @return
     */
    @Override
    public List<BasePlatformLogisticsDetail> findDetailList(BasePlatformLogisticsDetailReqDTO detailReqDTO) {
        return platformLogisticsMapper.findDetailList(detailReqDTO);
    }

    /**
     * 查询外部系统承运商编码-系统承运商编码对照关系
     *
     * @param query
     * @return
     */
    @Override
    public List<BasePlatformLogisticsDetailDTO> queryLogisticsByCondition(LogisticsDetailQuery query) {
        return platformLogisticsMapper.queryLogisticsByCondition(query);
    }

    /**
     * 查询电商平台下的承运商编码
     *
     * @param relationshipReqDTO
     * @return
     */
    @Override
    public List<PlatformLogisticsRelationshipDTO> queryPlatformLogistics(PlatformLogisticsRelationshipReqDTO relationshipReqDTO) {
        return platformLogisticsMapper.queryPlatformLogistics(relationshipReqDTO);
    }

}
