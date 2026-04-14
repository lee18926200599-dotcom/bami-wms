package com.basedata.server.service.impl;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.basedata.common.dto.BaseStorePrintTemplateDetailUpdateReqDTO;
import com.basedata.common.dto.BaseStorePrintTemplateReqDTO;
import com.basedata.common.dto.GeneralBatchUpdateByIdsDto;
import com.basedata.common.dto.UpdateStatusDto;
import com.basedata.common.enums.BooleanEnum;
import com.basedata.common.enums.StateEnum;
import com.basedata.server.constant.MqConstants;
import com.basedata.server.domain.OrgDomainService;
import com.basedata.server.domain.PermissionDomainService;
import com.basedata.server.dto.BaseStorePrintTemplateDTO;
import com.basedata.server.dto.BaseStorePrintTemplateDetailDTO;
import com.basedata.server.dto.BaseStorePrintTemplateDetailReqDTO;
import com.basedata.server.dto.BaseStorePrintTemplateUpdateReqDTO;
import com.basedata.server.entity.BaseStorePrintTemplate;
import com.basedata.server.entity.BaseStorePrintTemplateDetail;
import com.basedata.server.mapper.BaseStorePrintTemplateMapper;
import com.basedata.server.query.BaseStorePrintTemplateQueryVo;
import com.basedata.server.service.BaseStorePrintTemplateService;
import com.basedata.server.vo.BaseStorePrintTemplateReqVo;
import com.common.base.entity.CurrentUser;
import com.common.base.enums.PlatformTypeEnum;
import com.common.framework.execption.BizException;
import com.common.framework.number.BaseNoGenerateUtil;
import com.common.framework.user.FplUserUtil;
import com.common.language.util.I18nUtils;
import com.common.mq.MqMessage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.OrganizationDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 快递面单模版配置 服务实现类
 * </p>
 */
@Service
public class BaseStorePrintTemplateServiceImpl implements BaseStorePrintTemplateService {
    @Resource
    private BaseStorePrintTemplateMapper storePrintTemplateMapper;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private OrgDomainService orgDomainService;
    @Resource
    private PermissionDomainService permissionDomainService;

    /**
     * 批量保存配置
     *
     * @param updateReqDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchSaveConfig(BaseStorePrintTemplateUpdateReqDTO updateReqDTO) {
        boolean a = !CollectionUtils.isEmpty(updateReqDTO.getTemplateDetailList());
        Assert.isTrue(a, I18nUtils.getMessage("base.check.storeprinttemplate.detail.notnull"));

        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));
        Long serviceProviderId = currentUser.getServiceProviderId();

        /**
         * 1.2 如果系统中已存在同承运商同电商平台的配置 (无论什么状态)，则提示“已经为承运商({承运商])电商平台 (电商平台]) 配置快递面单模板(编码:[配置编
         * 号]) ，不能重复配置!
         */
        BaseStorePrintTemplateReqDTO reqDTO = new BaseStorePrintTemplateReqDTO();
        reqDTO.setServiceProviderId(serviceProviderId);
        reqDTO.setLogisticsId(updateReqDTO.getLogisticsId());
        reqDTO.setPlatformCode(updateReqDTO.getPlatformCode());
//        reqDTO.setAuthStoreCode(updateReqDTO.getAuthStoreCode());
        reqDTO.setBelongOwnerId(updateReqDTO.getBelongOwnerId());
        BaseStorePrintTemplate existOne = this.findOne(reqDTO);

        List<BaseStorePrintTemplateDetailUpdateReqDTO> updateDetailList = new ArrayList<>();
        List<BaseStorePrintTemplateDetail> addDetailList = new ArrayList<>();

        OrganizationDto serviceProviderInfo = orgDomainService.getServiceProviderById(serviceProviderId);
        Assert.notNull(serviceProviderInfo, I18nUtils.getMessage("base.check.common.groupunitnoexist"));

        // 更新主表
        BaseStorePrintTemplate hdr = new BaseStorePrintTemplate();
        BeanUtils.copyProperties(updateReqDTO, hdr);
        if (updateReqDTO.getId() == null) {
            // 新增重复
            if (existOne != null) {
                String str = PlatformTypeEnum.WXSPHXD.name().equals(reqDTO.getPlatformCode()) ? I18nUtils.getMessage("base.check.storeprinttemplate.show.owner") : "";

                throw new BizException(I18nUtils.getMessage("base.check.storeprinttemplate.logistics.ec.config.exist",new String[]{str,existOne.getConfigCode()}));
            }
            // 配置编号生成规则:YYMMDD+4 位流水号，4 位流水号每天从 0001 开始
            hdr.setConfigCode(BaseNoGenerateUtil.generateRuleCode(serviceProviderId + ""));
            hdr.setServiceProviderId(serviceProviderId);
            hdr.setServiceProviderName(serviceProviderInfo.getOrgName());
            hdr.setGroupId(currentUser.getGroupId());
            hdr.setDefaultCreateValue(currentUser);
            hdr.setState(StateEnum.NOT_ENABLE.getCode());
            storePrintTemplateMapper.saveHeader(hdr);
            updateReqDTO.setId(hdr.getId());
        } else {
            if (existOne != null && existOne.getId().longValue() != updateReqDTO.getId()) {
                String str = PlatformTypeEnum.WXSPHXD.name().equals(reqDTO.getPlatformCode()) ? I18nUtils.getMessage("base.check.storeprinttemplate.show.store") : "";
                throw new BizException(I18nUtils.getMessage("base.check.storeprinttemplate.logistics.ec.config.exist",new String[]{str,existOne.getConfigCode()}));
            }
            BaseStorePrintTemplate currentOne = this.findOne(updateReqDTO.getId());
            Assert.notNull(currentOne, I18nUtils.getMessage("base.check.storeprinttemplate.config.noexist"));
            if (StateEnum.NOT_ENABLE.getCode().equals(currentOne.getState())) {
                Assert.isFalse(!currentOne.getConfigName().equals(updateReqDTO.getConfigName()) || !currentOne.getConfigCode().equals(updateReqDTO.getConfigCode()) || !Objects.equals(currentOne.getBelongOwnerId(), updateReqDTO.getBelongOwnerId()), "创建状态:规则描述、配置编码、授权店铺、所属货主不能修改！");
            } else if (StateEnum.ENABLE.getCode().equals(currentOne.getState())) {
                Assert.isFalse(!currentOne.getConfigName().equals(updateReqDTO.getConfigName()) || !currentOne.getConfigCode().equals(updateReqDTO.getConfigCode()) || !currentOne.getLogisticsCode().equals(updateReqDTO.getLogisticsCode()) || !Objects.equals(currentOne.getBelongOwnerId(), updateReqDTO.getBelongOwnerId()), "停用状态:规则描述、配置编码、承运商、授权店铺、所属货主不能修改！");
            } else if (StateEnum.DISABLE.getCode().equals(currentOne.getState())) {
                Assert.isFalse(!currentOne.getConfigName().equals(updateReqDTO.getConfigName()) || !currentOne.getConfigCode().equals(updateReqDTO.getConfigCode()) || !currentOne.getLogisticsCode().equals(updateReqDTO.getLogisticsCode()) || !Objects.equals(currentOne.getBelongOwnerId(), updateReqDTO.getBelongOwnerId()), "启用状态:规则描述、配置编码、承运商、授权店铺、所属货主不能修改！");
            }
            currentOne.setLogisticsId(updateReqDTO.getLogisticsId());
            currentOne.setLogisticsCode(updateReqDTO.getLogisticsCode());
            currentOne.setLogisticsName(updateReqDTO.getLogisticsName());
            currentOne.setPlatformCode(updateReqDTO.getPlatformCode());
            currentOne.setPlatformName(updateReqDTO.getPlatformName());
            currentOne.setBelongOwnerId(updateReqDTO.getBelongOwnerId());
            currentOne.setBelongOwnerName(updateReqDTO.getBelongOwnerName());
            currentOne.setDefaultUpdateValue(currentUser);
            storePrintTemplateMapper.updateById(currentOne);
            hdr.setId(updateReqDTO.getId());
        }

        for (BaseStorePrintTemplateDetailUpdateReqDTO detailDTO : updateReqDTO.getTemplateDetailList()) {
            // 校验必填参数
            BaseStorePrintTemplateDetail detailEntity = new BaseStorePrintTemplateDetail();
            BeanUtils.copyProperties(detailDTO, detailEntity);
            if (detailDTO.getId() == null) {
                // 新增
                detailEntity.setConfigId(hdr.getId());
                detailEntity.setServiceProviderId(serviceProviderId);
                detailEntity.setDefaultCreateValue(currentUser);
                detailEntity.setLogisticsId(updateReqDTO.getLogisticsId());
                detailEntity.setLogisticsCode(updateReqDTO.getLogisticsCode());
                detailEntity.setLogisticsName(updateReqDTO.getLogisticsName());
                detailEntity.setPlatformCode(updateReqDTO.getPlatformCode());
                detailEntity.setPlatformName(updateReqDTO.getPlatformName());
                detailEntity.setBelongOwnerId(updateReqDTO.getBelongOwnerId());
                detailEntity.setBelongOwnerName(updateReqDTO.getBelongOwnerName());
                addDetailList.add(detailEntity);
            } else {
                // 修改或删除
                detailEntity.setDefaultUpdateValue(currentUser);
                detailDTO.setDefaultUpdateValue(currentUser);
                updateDetailList.add(detailDTO);
                storePrintTemplateMapper.updateDetailById(detailEntity);
            }
        }
        if (!CollectionUtils.isEmpty(addDetailList)) {
            storePrintTemplateMapper.batchSaveDetail(addDetailList);
        }

        // 【快递面单模板配置】明细有修改，通知wms-base服务批量修改【运单号与面单获取】已经配置的模板地址
        if (!CollectionUtils.isEmpty(updateDetailList)) {
            MqMessage<List<BaseStorePrintTemplateDetailUpdateReqDTO>> mqMessage = new MqMessage<>();
            mqMessage.setData(updateDetailList);
            mqMessage.setWarehouseId(FplUserUtil.getWarehouseId());
            mqMessage.setReferenceId(hdr.getId());
            mqMessage.setReferenceNo(hdr.getConfigCode());
            mqMessage.setExchange(MqConstants.WMS_4PL_BASEDATA_STORE_PRINT_TEMPLATE_UPDATE_NOTIFY_EXCHANGE);
            mqMessage.setRoutingKey(MqConstants.WMS_4PL_BASEDATA_STORE_PRINT_TEMPLATE_UPDATE_NOTIFY_KEY);
            mqMessage.setUserEntity(JSON.toJSONString(currentUser));
            rabbitTemplate.convertAndSend(MqConstants.WMS_4PL_BASEDATA_STORE_PRINT_TEMPLATE_UPDATE_NOTIFY_EXCHANGE, MqConstants.WMS_4PL_BASEDATA_STORE_PRINT_TEMPLATE_UPDATE_NOTIFY_KEY, mqMessage);
        }
    }

    /**
     * 根据仓储服务商、系统承运商ID、平台编码查找唯一一条主配置
     * 一般：serviceProviderId、logisticsId、platformCode
     * 微信视频号：authStoreCode、belongOwnerId
     *
     * @param reqDTO
     * @param
     * @param
     * @return 1、存在，返回对象；2、不存在，返回null；3、存在2条以上，抛异常
     */
    private BaseStorePrintTemplate findOne(BaseStorePrintTemplateReqDTO reqDTO) {
        Assert.notNull(reqDTO.getServiceProviderId(), I18nUtils.getMessage("base.check.storeprinttemplate.warehouseserviceid.notnull"));
        Assert.notNull(reqDTO.getLogisticsId(), I18nUtils.getMessage("base.check.storeprinttemplate.logisticsid.notnull"));
        Assert.notNull(reqDTO.getPlatformCode(), I18nUtils.getMessage("base.check.storeprinttemplate.platformcode.notnull"));
        // BUG #45746 【快递面单模板配置】配置列表增加显示字段、配置重复校验调整 - 4PL-WMS - 禅道  http://192.168.1.22:8090/zentao/bug-view-45746.html
        if (PlatformTypeEnum.WXSPHXD.name().equals(reqDTO.getPlatformCode())) {
//            Assert.notNull(reqDTO.getAuthStoreCode(), "查询唯一主配置参数校验失败：授权店铺编码不能为空！");
            Assert.notNull(reqDTO.getBelongOwnerId(), I18nUtils.getMessage("base.check.storeprinttemplate.ownerid.notnull"));
        }
        List<BaseStorePrintTemplate> currentList = storePrintTemplateMapper.findList(reqDTO);
        if (CollectionUtils.isEmpty(currentList)) {
            return null;
        }
        Assert.isFalse(currentList.size() > 1, I18nUtils.getMessage("base.check.storeprinttemplate.warehouseservice.ec.logistics.config.exist"));
        return currentList.get(0);
    }

    /**
     * 根据ID查找唯一一条主配置
     *
     * @param configId
     * @return 1、存在，返回对象；2、不存在，返回null
     */
    private BaseStorePrintTemplate findOne(Long configId) {
        BaseStorePrintTemplateReqDTO reqDTO = new BaseStorePrintTemplateReqDTO();
        reqDTO.setId(configId);
        List<BaseStorePrintTemplate> currentList = storePrintTemplateMapper.findList(reqDTO);
        if (CollectionUtils.isEmpty(currentList)) {
            return null;
        }
        return currentList.get(0);
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

        BaseStorePrintTemplateReqDTO reqDTO = new BaseStorePrintTemplateReqDTO();
        reqDTO.setIds(ids);
        List<BaseStorePrintTemplate> currentList = storePrintTemplateMapper.findList(reqDTO);
        Assert.notEmpty(currentList, I18nUtils.getMessage("base.check.common.selecteddatanoexist"));
        Assert.isFalse(currentList.stream().anyMatch(x -> StateEnum.NOT_ENABLE.getCode().intValue() != x.getState()),
                I18nUtils.getMessage("base.check.common.onlydeletecreatestate"));

        GeneralBatchUpdateByIdsDto updateByIdsDto = new GeneralBatchUpdateByIdsDto();
        updateByIdsDto.setIds(ids);
        updateByIdsDto.setDeletedFlag(BooleanEnum.TRUE.getCode());

        updateByIdsDto.setModifiedBy(FplUserUtil.getUserId());
        updateByIdsDto.setModifiedName(FplUserUtil.getUserName());
        updateByIdsDto.setModifiedDate(LocalDateTime.now());
        return storePrintTemplateMapper.batchUpdate(updateByIdsDto) > 0;
    }


    /**
     * 批量启用停用主配置
     *
     * @param statusDto
     * @return
     */
    @Override
    public boolean batchEnableOrDisable(UpdateStatusDto statusDto) {
        Assert.notEmpty(statusDto.getIds(), I18nUtils.getMessage("base.check.common.configidarraynotnull"));

        BaseStorePrintTemplateReqDTO reqDTO = new BaseStorePrintTemplateReqDTO();
        reqDTO.setIds(statusDto.getIds());
        List<BaseStorePrintTemplate> currentList = storePrintTemplateMapper.findList(reqDTO);
        Assert.notEmpty(currentList, I18nUtils.getMessage("base.check.common.selecteddatanoexist"));

        // 要改成启用状态
        if (StateEnum.ENABLE.getCode().intValue() == statusDto.getState()) {
            Assert.isFalse(currentList.stream().anyMatch(x ->
                            StateEnum.DISABLE.getCode().intValue() != x.getState()
                                    && StateEnum.NOT_ENABLE.getCode().intValue() != x.getState()),
                    I18nUtils.getMessage("base.check.common.canenablestate"));
        } else if (StateEnum.DISABLE.getCode().intValue() == statusDto.getState()) {
            Assert.isFalse(currentList.stream().anyMatch(x ->
                            StateEnum.ENABLE.getCode().intValue() != x.getState()),
                    I18nUtils.getMessage("base.check.common.candisablestate"));
        }

        GeneralBatchUpdateByIdsDto updateByIdsDto = new GeneralBatchUpdateByIdsDto();
        updateByIdsDto.setIds(statusDto.getIds());
        updateByIdsDto.setState(statusDto.getState());

        updateByIdsDto.setModifiedBy(FplUserUtil.getUserId());
        updateByIdsDto.setModifiedName(FplUserUtil.getUserName());
        updateByIdsDto.setModifiedDate(LocalDateTime.now());
        return storePrintTemplateMapper.batchUpdate(updateByIdsDto) > 0;
    }

    /**
     * 分页查询
     *
     * @param queryVo
     * @return
     */
    @Override
    public PageInfo<BaseStorePrintTemplate> queryPageList(BaseStorePrintTemplateQueryVo queryVo) {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));
        List<Long> orgIdList = permissionDomainService.getOrgList(currentUser.getGroupId(), currentUser.getUserId());
        if (CollectionUtils.isEmpty(orgIdList)) {
            return new PageInfo<>();
        }
        BaseStorePrintTemplateReqDTO reqDTO = new BaseStorePrintTemplateReqDTO();
        BeanUtils.copyProperties(queryVo, reqDTO);
        reqDTO.setServiceProviderIdList(orgIdList);
        // 查询内容在明细表：先找到主表ID，再查主表
        if (StringUtils.isNotBlank(queryVo.getTemplateName())
                || StringUtils.isNotBlank(queryVo.getTemplateUrl())) {
            BaseStorePrintTemplateDetailReqDTO detailReqDTO = new BaseStorePrintTemplateDetailReqDTO();
            BeanUtils.copyProperties(queryVo, detailReqDTO);
            List<BaseStorePrintTemplateDetailDTO> detailList = storePrintTemplateMapper.findDetailList(detailReqDTO);
            if (CollectionUtils.isEmpty(detailList)) {
                return PageInfo.of(new ArrayList<>());
            }
            reqDTO.setIds(detailList.stream().map(BaseStorePrintTemplateDetailDTO::getConfigId).distinct().collect(Collectors.toList()));
        }
        // 3PL基础数据，集团级别查询权限，无需切换业务单元（仓储服务商）
//        reqDTO.setServiceProviderId(currentUser.getServiceProviderId());
        reqDTO.setGroupId(currentUser.getGroupId());
        PageHelper.startPage(queryVo.getPageNum(), queryVo.getPageSize());
        return PageInfo.of(storePrintTemplateMapper.findList(reqDTO));
    }

    /**
     * 查询明细列表（根据配置项主ID查）
     *
     * @param configId
     * @return
     */
    @Override
    public BaseStorePrintTemplateDTO queryDetailList(Long configId) {
        BaseStorePrintTemplate storePrintTemplate = this.findOne(configId);
        Assert.notNull(storePrintTemplate, I18nUtils.getMessage("base.check.common.data.noexist"));

        BaseStorePrintTemplateDetailReqDTO detailReqDTO = new BaseStorePrintTemplateDetailReqDTO();
        detailReqDTO.setConfigId(configId);
        List<BaseStorePrintTemplateDetailDTO> detailList = storePrintTemplateMapper.findDetailList(detailReqDTO);

        BaseStorePrintTemplateDTO dto = new BaseStorePrintTemplateDTO();
        BeanUtils.copyProperties(storePrintTemplate, dto);
        dto.setTemplateDetailList(detailList);
        return dto;
    }

    /**
     * 查询面单模板列表（标准、自定义）
     *
     * @param reqVo
     * @return
     */
    @Override
    public List<BaseStorePrintTemplateDetailDTO> queryTemplateDetailList(BaseStorePrintTemplateReqVo reqVo) {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));

        BaseStorePrintTemplateDetailReqDTO reqDTO = new BaseStorePrintTemplateDetailReqDTO();
        BeanUtils.copyProperties(reqVo, reqDTO);
        reqDTO.setServiceProviderId(currentUser.getServiceProviderId());
        reqDTO.setState(StateEnum.ENABLE.getCode());
        return storePrintTemplateMapper.findDetailList(reqDTO);
    }
}
