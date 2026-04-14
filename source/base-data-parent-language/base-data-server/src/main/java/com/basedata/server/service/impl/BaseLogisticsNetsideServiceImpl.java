package com.basedata.server.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.basedata.common.dto.GeneralBatchUpdateByIdsDto;
import com.basedata.common.dto.UpdateStatusDto;
import com.basedata.common.enums.BooleanEnum;
import com.basedata.common.enums.DeliveryTypeEnum;
import com.basedata.common.enums.StateEnum;
import com.basedata.server.domain.OrgDomainService;
import com.basedata.server.domain.PermissionDomainService;
import com.basedata.server.dto.*;
import com.basedata.server.entity.BaseLogisticsNetside;
import com.basedata.server.entity.BaseLogisticsNetsideDetail;
import com.basedata.server.mapper.BaseLogisticsNetsideDetailMapper;
import com.basedata.server.mapper.BaseLogisticsNetsideMapper;
import com.basedata.server.query.BaseLogisticsNetsideDetailQuery;
import com.basedata.server.query.BaseLogisticsNetsideQueryVo;
import com.basedata.server.service.BaseLogisticsNetsideService;
import com.common.base.entity.CurrentUser;
import com.common.framework.number.BaseNoGenerateUtil;
import com.common.framework.user.FplUserUtil;
import com.common.language.util.I18nUtils;
import com.common.util.util.AssertUtils;
import com.common.util.util.HuToolUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.OrganizationDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 承运商网点对应关系 服务实现类
 * </p>
 */
@Service
public class BaseLogisticsNetsideServiceImpl implements BaseLogisticsNetsideService {
    @Resource
    private BaseLogisticsNetsideMapper logisticsNetsideMapper;
    @Resource
    private BaseLogisticsNetsideDetailMapper logisticsNetsideDetailMapper;
    @Resource
    private OrgDomainService orgDomainService;
    @Resource
    private PermissionDomainService permissionDomainService;

    /**
     * 新增
     *
     * @param reqDTO
     * @return
     */
    @Override
    public void saveAll(BaseLogisticsNetsideReqDTO reqDTO) {
        boolean a = !CollectionUtils.isEmpty(reqDTO.getDetailList());
        Assert.isTrue(a, I18nUtils.getMessage("base.check.logisticsnetside.configdetailnutnoll"));
        reqDTO.setId(null);

        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));
        Long serviceProviderId = currentUser.getServiceProviderId();

        BaseLogisticsNetside oneHdr = this.findOne(serviceProviderId, reqDTO.getDeliveryType(), reqDTO.getLogisticsId(), reqDTO.getPlatformCode());
        Assert.isNull(oneHdr, I18nUtils.getMessage("base.check.logisticsnetside.configexist"));

        OrganizationDto serviceProviderInfo = orgDomainService.getServiceProviderById(serviceProviderId);
        Assert.notNull(serviceProviderInfo, I18nUtils.getMessage("base.check.logisticsnetside.groupunitnoexist"));

        List<BaseLogisticsNetsideDetail> updateList = new ArrayList<>();
        List<BaseLogisticsNetsideDetail> addDetailList = new ArrayList<>();

        // 更新主表
        BaseLogisticsNetside hdr = new BaseLogisticsNetside();
        BeanUtils.copyProperties(reqDTO, hdr);
        // 配置编号生成规则:YYMMDD+4 位流水号，4 位流水号每天从 0001 开始
        if (StringUtils.isBlank(hdr.getConfigCode())) {
            hdr.setConfigCode(BaseNoGenerateUtil.generateRuleCode(serviceProviderId + ""));
        }
        hdr.setDefaultCreateValue(currentUser);
        hdr.setServiceProviderId(serviceProviderId);
        hdr.setServiceProviderName(serviceProviderInfo.getOrgName());
        hdr.setGroupId(currentUser.getGroupId());
        logisticsNetsideMapper.saveHeader(hdr);
        reqDTO.setId(hdr.getId());

        for (BaseLogisticsNetsideDetailReqDTO detailDTO : reqDTO.getDetailList()) {
            detailDTO.setId(null);
            // TODO 校验必填参数
            BaseLogisticsNetsideDetail detailEntity = new BaseLogisticsNetsideDetail();
            BeanUtils.copyProperties(detailDTO, detailEntity);
            // 新增
            detailEntity.setConfigId(hdr.getId());
            detailEntity.setDefaultCreateValue(currentUser);
            detailEntity.setState(StateEnum.ENABLE.getCode());
            addDetailList.add(detailEntity);
        }
        if (!CollectionUtils.isEmpty(addDetailList)) {
            logisticsNetsideMapper.batchSaveDetail(addDetailList);
        }
    }

    /**
     * 更新
     *
     * @param reqDTO
     * @return
     */
    @Override
    public void updateAll(BaseLogisticsNetsideReqDTO reqDTO) {
        boolean a = !CollectionUtils.isEmpty(reqDTO.getDetailList());
        Assert.isTrue(a, I18nUtils.getMessage("base.check.logisticsnetside.configdetailnutnoll"));
        Assert.notNull(reqDTO.getId(), I18nUtils.getMessage("base.check.logisticsnetside.idnotnull"));

        BaseLogisticsNetside targetOne = this.findOneById(reqDTO);
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));

        List<BaseLogisticsNetsideDetail> updateList = new ArrayList<>();
        List<BaseLogisticsNetsideDetail> addDetailList = new ArrayList<>();

        /**
         *  一：任何状态下，编码和描述都不能改
         *  二：启用或停用状态下，承运商，配送方式，电商平台不能修改
         * **/
        AssertUtils.isTrue(StringUtils.equals(reqDTO.getConfigName(), targetOne.getConfigName()) && StringUtils.equals(reqDTO.getConfigCode(), targetOne.getConfigCode()), "规则描述、配置编码不能修改！");
        if (Objects.equals(StateEnum.ENABLE.getCode(), targetOne.getState()) || Objects.equals(StateEnum.DISABLE.getCode(), targetOne.getState())) {
            AssertUtils.isTrue(Objects.equals(reqDTO.getLogisticsId(), targetOne.getLogisticsId())
                    && Objects.equals(reqDTO.getDeliveryType(), targetOne.getDeliveryType())
                    && StringUtils.equals(reqDTO.getPlatformCode(), targetOne.getPlatformCode()), I18nUtils.getMessage("base.check.logisticsnetside.noupdate"));
        }
        targetOne.setRemark(reqDTO.getRemark());
        targetOne.setDefaultUpdateValue(currentUser);
        logisticsNetsideMapper.updateById(targetOne);

        for (BaseLogisticsNetsideDetailReqDTO detailDTO : reqDTO.getDetailList()) {
            // TODO 校验必填参数
            BaseLogisticsNetsideDetail detailEntity = new BaseLogisticsNetsideDetail();
            BeanUtils.copyProperties(detailDTO, detailEntity);
            if (detailDTO.getId() == null) {
                // 新增
                detailEntity.setConfigId(targetOne.getId());
                detailEntity.setDefaultCreateValue(currentUser);
                detailEntity.setState(StateEnum.ENABLE.getCode());
                addDetailList.add(detailEntity);
            } else {

                detailEntity.setDefaultUpdateValue(currentUser);
                logisticsNetsideMapper.updateDetailById(detailEntity);
            }
        }
        if (!CollectionUtils.isEmpty(addDetailList)) {
            logisticsNetsideMapper.batchSaveDetail(addDetailList);
        }
    }

    private BaseLogisticsNetside findOneById(BaseLogisticsNetsideReqDTO reqDTO) {
        BaseLogisticsNetsideQueryReqDTO queryReqDTO = new BaseLogisticsNetsideQueryReqDTO();
        queryReqDTO.setId(reqDTO.getId());
        List<BaseLogisticsNetside> targetList = logisticsNetsideMapper.findList(queryReqDTO);
        Assert.notEmpty(targetList, I18nUtils.getMessage("base.check.logisticsnetside.confignoexist"));
        return targetList.get(0);
    }

    /**
     * 根据 仓储服务商、配送方式、承运商ID、电商平台 找唯一一条主配置
     *
     * @param serviceProviderId
     * @param deliveryType
     * @param logisticsId
     * @param platformCode
     * @return 1、存在，返回对象；2、不存在，返回null；3、存在2条以上，抛异常
     */
    private BaseLogisticsNetside findOne(Long serviceProviderId, Integer deliveryType, Long logisticsId, String platformCode) {
        Assert.notNull(serviceProviderId, I18nUtils.getMessage("base.check.logisticsnetside.serviceidnotnull"));
        Assert.notNull(deliveryType, I18nUtils.getMessage("base.check.logisticsnetside.deliverytypenotnull"));
        Assert.notNull(logisticsId, I18nUtils.getMessage("base.check.logisticsnetside.logisticsidnotnull"));
        Assert.notBlank(platformCode, I18nUtils.getMessage("base.check.logisticsnetside.ecplatnotnull"));

        BaseLogisticsNetsideQueryReqDTO queryReqDTO = new BaseLogisticsNetsideQueryReqDTO();
        queryReqDTO.setServiceProviderId(serviceProviderId);
        queryReqDTO.setDeliveryType(deliveryType);
        queryReqDTO.setLogisticsId(logisticsId);
        queryReqDTO.setPlatformCode(platformCode);
        List<BaseLogisticsNetside> targetList = logisticsNetsideMapper.findList(queryReqDTO);
        if (CollectionUtils.isEmpty(targetList)) {
            return null;
        }
        Assert.isFalse(targetList.size() > 1, I18nUtils.getMessage("base.check.logisticsnetside.findconfigexist"));
        return targetList.get(0);

    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @Override
    public boolean deleteByIds(List<Long> ids) {

        Assert.notEmpty(ids, I18nUtils.getMessage("base.check.logisticsnetside.configidarraynotnull"));
        BaseLogisticsNetsideQueryReqDTO queryReqDTO = new BaseLogisticsNetsideQueryReqDTO();
        queryReqDTO.setIds(ids);
        List<BaseLogisticsNetside> currentList = logisticsNetsideMapper.findList(queryReqDTO);
        Assert.notEmpty(currentList, I18nUtils.getMessage("base.check.logisticsnetside.selecteddatanoexist"));
        Assert.isFalse(currentList.stream().anyMatch(x -> StateEnum.NOT_ENABLE.getCode().intValue() != x.getState()),
                I18nUtils.getMessage("base.check.logisticsnetside.olydeletecreatestate"));

        GeneralBatchUpdateByIdsDto updateByIdsDto = new GeneralBatchUpdateByIdsDto();
        updateByIdsDto.setIds(ids);
        updateByIdsDto.setDeletedFlag(BooleanEnum.TRUE.getCode());

        updateByIdsDto.setModifiedBy(FplUserUtil.getUserId());
        updateByIdsDto.setModifiedName(FplUserUtil.getUserName());
        updateByIdsDto.setModifiedDate(LocalDateTime.now());
        return logisticsNetsideMapper.batchUpdate(updateByIdsDto) > 0;
    }

    /**
     * 批量启用停用主配置
     *
     * @param statusDto
     * @return
     */
    @Override
    public boolean batchEnableOrDisable(UpdateStatusDto statusDto) {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser,  I18nUtils.getMessage("base.check.user.infonull"));

        Assert.notEmpty(statusDto.getIds(), I18nUtils.getMessage("base.check.logisticsnetside.configidarraynotnull"));
        BaseLogisticsNetsideQueryReqDTO queryReqDTO = new BaseLogisticsNetsideQueryReqDTO();
        queryReqDTO.setIds(statusDto.getIds());
        List<BaseLogisticsNetside> currentList = logisticsNetsideMapper.findList(queryReqDTO);
        Assert.notEmpty(currentList, I18nUtils.getMessage("base.check.logisticsnetside.selecteddatanoexist"));
        // 要改成启用状态
        if (StateEnum.ENABLE.getCode().intValue() == statusDto.getState()) {
            Assert.isFalse(currentList.stream().anyMatch(x ->
                            StateEnum.DISABLE.getCode().intValue() != x.getState()
                                    && StateEnum.NOT_ENABLE.getCode().intValue() != x.getState()),
                    I18nUtils.getMessage("base.check.logisticsnetside.canenablestate"));
        } else if (StateEnum.DISABLE.getCode().intValue() == statusDto.getState()) {
            Assert.isFalse(currentList.stream().anyMatch(x ->
                            StateEnum.ENABLE.getCode().intValue() != x.getState()),
                    I18nUtils.getMessage("base.check.logisticsnetside.candisablestate"));
        }

        GeneralBatchUpdateByIdsDto updateByIdsDto = new GeneralBatchUpdateByIdsDto();
        updateByIdsDto.setIds(statusDto.getIds());
        updateByIdsDto.setState(statusDto.getState());

        updateByIdsDto.setModifiedBy(currentUser.getUserId());
        updateByIdsDto.setModifiedName(currentUser.getUserName());
        updateByIdsDto.setModifiedDate(LocalDateTime.now());
        return logisticsNetsideMapper.batchUpdate(updateByIdsDto) > 0;
    }

    /**
     * 分页查询
     *
     * @param queryVo
     * @return
     */
    @Override
    public PageInfo<BaseLogisticsNetside> queryPageList(BaseLogisticsNetsideQueryVo queryVo) {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));
        List<Long> orgIdList = permissionDomainService.getOrgList(currentUser.getGroupId(), currentUser.getUserId());
        if (CollectionUtils.isEmpty(orgIdList)) {
            return new PageInfo<>();
        }
        BaseLogisticsNetsideQueryReqDTO queryReqDTO = new BaseLogisticsNetsideQueryReqDTO();
        BeanUtils.copyProperties(queryVo, queryReqDTO);
        queryReqDTO.setServiceProviderIdList(orgIdList);
        // 查询内容在明细表：先找到主表ID，再查主表
        if (StringUtils.isNotBlank(queryVo.getBranchId())) {
            BaseLogisticsNetsideDetailReqDTO detailReqDTO = new BaseLogisticsNetsideDetailReqDTO();
            BeanUtils.copyProperties(queryVo, detailReqDTO);
            detailReqDTO.setSysNetsiteCode(queryVo.getBranchId());
            List<BaseLogisticsNetsideDetailDTO> detailList = logisticsNetsideMapper.findDetailList(detailReqDTO);
            if (CollectionUtil.isEmpty(detailList)) {
                return PageInfo.of(new ArrayList<>());
            }
            queryReqDTO.setIds(detailList.stream().map(BaseLogisticsNetsideDetailDTO::getConfigId).distinct().collect(Collectors.toList()));
        }
        queryReqDTO.setGroupId(currentUser.getGroupId());
        PageHelper.startPage(queryVo.getPageNum(), queryVo.getPageSize());
        return PageInfo.of(logisticsNetsideMapper.findList(queryReqDTO));
    }

    /**
     * 查询明细列表（根据配置项主ID查）
     *
     * @param configId
     * @return
     */
    @Override
    public BaseLogisticsNetsideDTO queryDetailList(Long configId) {
        BaseLogisticsNetsideDTO dto = new BaseLogisticsNetsideDTO();
        BaseLogisticsNetsideDetailReqDTO detailReqDTO = new BaseLogisticsNetsideDetailReqDTO();
        detailReqDTO.setConfigId(configId);
        List<BaseLogisticsNetsideDetailDTO> detailList = logisticsNetsideMapper.findDetailList(detailReqDTO);
        if (CollectionUtil.isEmpty(detailList)) {
            return dto;
        }

        BeanUtils.copyProperties(detailList.get(0), dto);
        // 纠正BeanUtils拷贝id覆盖问题
        dto.setId(detailList.get(0).getConfigId());
        dto.setDetailList(detailList);
        return dto;
    }


    @Override
    public List<BaseLogisticsNetsideDetailDTO> queryNetsideDetail(BaseLogisticsNetsideDetailQuery queryVo) {

        LambdaQueryWrapper<BaseLogisticsNetside> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (queryVo.getServiceProviderId() != null) {
            lambdaQueryWrapper.eq(BaseLogisticsNetside::getServiceProviderId, queryVo.getServiceProviderId());
        }
        if (queryVo.getLogisticsId() != null) {
            lambdaQueryWrapper.eq(BaseLogisticsNetside::getLogisticsId, queryVo.getLogisticsId());
        }
        if (StringUtils.isNotEmpty(queryVo.getPlatformCode())) {
            lambdaQueryWrapper.eq(BaseLogisticsNetside::getPlatformCode, queryVo.getPlatformCode());
        }
        lambdaQueryWrapper.eq(BaseLogisticsNetside::getState, StateEnum.ENABLE.getCode());
        lambdaQueryWrapper.eq(BaseLogisticsNetside::getDeletedFlag, 0);
        List<BaseLogisticsNetside> byCriteria = this.logisticsNetsideMapper.selectList(lambdaQueryWrapper);
        if (CollectionUtils.isEmpty(byCriteria)) {
            return null;
        }

        List<Long> ids = byCriteria.stream().map(BaseLogisticsNetside::getId).collect(Collectors.toList());
        LambdaQueryWrapper<BaseLogisticsNetsideDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
        detailLambdaQueryWrapper.in(BaseLogisticsNetsideDetail::getConfigId, ids);
        detailLambdaQueryWrapper.eq(BaseLogisticsNetsideDetail::getState, StateEnum.ENABLE.getCode());
        detailLambdaQueryWrapper.eq(BaseLogisticsNetsideDetail::getDeletedFlag, 0);
        List<BaseLogisticsNetsideDetail> logisticsNetsideDetails = logisticsNetsideDetailMapper.selectList(detailLambdaQueryWrapper);

        List<BaseLogisticsNetsideDetailDTO> exchange = HuToolUtil.exchange(logisticsNetsideDetails, BaseLogisticsNetsideDetailDTO.class);

        return exchange;
    }

    @Override
    public List<BaseLogisticsDTO> queryBaseLogisticsList(Integer deliveryType) {
        List<BaseLogisticsDTO> list = new ArrayList<>();
        BaseLogisticsDTO dto = new BaseLogisticsDTO();
        dto.setLogisticsId(1L);
        dto.setLogisticsCode("SF");
        dto.setLogisticsName("顺丰");
        list.add(dto);
        dto = new BaseLogisticsDTO();
        dto.setLogisticsId(2L);
        dto.setLogisticsCode("YTO");
        dto.setLogisticsName("圆通快递");
        list.add(dto);
        dto = new BaseLogisticsDTO();
        dto.setLogisticsId(3L);
        dto.setLogisticsCode("ZTO");
        dto.setLogisticsName("中通快递");
        list.add(dto);
        dto = new BaseLogisticsDTO();
        dto.setLogisticsId(4L);
        dto.setLogisticsCode("STO");
        dto.setLogisticsName("申通快递");
        list.add(dto);
        dto = new BaseLogisticsDTO();
        dto.setLogisticsId(5L);
        dto.setLogisticsCode("YUNDA");
        dto.setLogisticsName("韵达快递");
        list.add(dto);
        dto = new BaseLogisticsDTO();
        dto.setLogisticsId(6L);
        dto.setLogisticsCode("EMS");
        dto.setLogisticsName("EMS");
        list.add(dto);
        dto = new BaseLogisticsDTO();
        dto.setLogisticsId(7L);
        dto.setLogisticsCode("JDWL");
        dto.setLogisticsName("京东物流");
        list.add(dto);
        dto = new BaseLogisticsDTO();
        dto.setLogisticsId(8L);
        dto.setLogisticsCode("POSTB");
        dto.setLogisticsName("中国邮政");
        list.add(dto);
        dto = new BaseLogisticsDTO();
        dto.setLogisticsId(9L);
        dto.setLogisticsCode("ZTZS");
        dto.setLogisticsName("自提自送");
        list.add(dto);
        dto = new BaseLogisticsDTO();
        dto.setLogisticsId(10L);
        dto.setLogisticsCode("ZJS");
        dto.setLogisticsName("宅急送");
        list.add(dto);
        dto = new BaseLogisticsDTO();
        dto.setLogisticsId(11L);
        dto.setLogisticsCode("JD");
        dto.setLogisticsName("京东配送");
        list.add(dto);
        dto = new BaseLogisticsDTO();
        dto.setLogisticsId(12L);
        dto.setLogisticsCode("JTSD");
        dto.setLogisticsName("极兔速递");
        list.add(dto);
        dto = new BaseLogisticsDTO();
        dto.setLogisticsId(13L);
        dto.setLogisticsCode("JDKY");
        dto.setLogisticsName("京东快运");
        list.add(dto);
        dto = new BaseLogisticsDTO();
        dto.setLogisticsId(14L);
        dto.setLogisticsCode("ZCZS");
        dto.setLogisticsName("专车直送");
        list.add(dto);

        dto = new BaseLogisticsDTO();
        dto.setLogisticsId(16L);
        dto.setLogisticsCode("LDKY");
        dto.setLogisticsName("零担快运");
        list.add(dto);
        dto = new BaseLogisticsDTO();
        dto.setLogisticsId(17L);
        dto.setLogisticsCode("SFBK");
        dto.setLogisticsName("顺丰标快");
        list.add(dto);

        dto = new BaseLogisticsDTO();
        dto.setDeliveryType(DeliveryTypeEnum.SELF_PICKUP.getCode());
        dto.setLogisticsId(99L);
        dto.setLogisticsCode("KHZT");
        dto.setLogisticsName("客户自提");
        list.add(dto);
        //todo:待实现

        return list;
    }

    @Override
    public List<BaseNetsideDTO> queryBaseNetsideList(Long logisticsId) {
        List<BaseNetsideDTO> list = new ArrayList<>();
        List<BaseLogisticsDTO> logisticsDTOS = queryBaseLogisticsList(null);
        logisticsDTOS.forEach(l -> {
            if (logisticsId.equals(l.getLogisticsId())) {
                BaseNetsideDTO dto = new BaseNetsideDTO();
                dto.setNetsiteCode(l.getLogisticsId() + "1");
                dto.setNetsiteName(l.getLogisticsName() + "越秀收发点");
                list.add(dto);
            }
        });
        //todo:待实现
        return list;
    }
}
