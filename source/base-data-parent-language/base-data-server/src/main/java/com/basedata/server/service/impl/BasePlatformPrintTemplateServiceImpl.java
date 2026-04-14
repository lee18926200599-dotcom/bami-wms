package com.basedata.server.service.impl;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.basedata.common.dto.GeneralBatchUpdateByIdsDto;
import com.basedata.common.enums.BooleanEnum;
import com.basedata.common.enums.DeliveryTypeEnum;
import com.basedata.common.vo.BasePlatformPrintTemplateDto;
import com.basedata.server.dto.BasePlatformLogisticsDetailReqDTO;
import com.basedata.server.dto.BasePlatformPrintTemplateReqDTO;
import com.basedata.server.entity.BasePlatformLogisticsDetail;
import com.basedata.server.entity.BasePlatformPrintTemplate;
import com.basedata.server.mapper.BasePlatformLogisticsMapper;
import com.basedata.server.mapper.BasePlatformPrintTemplateMapper;
import com.basedata.server.service.BasePlatformPrintTemplateService;
import com.common.base.entity.CurrentUser;
import com.common.base.enums.PlatformTypeEnum;
import com.common.framework.user.FplUserUtil;
import com.common.language.util.I18nUtils;
import com.common.util.util.HuToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BasePlatformPrintTemplateServiceImpl implements BasePlatformPrintTemplateService {

    @Resource
    private BasePlatformPrintTemplateMapper basePlatformPrintTemplateMapper;
    @Resource
    private BasePlatformLogisticsMapper basePlatformLogisticsMapper;

    @Override
    public List<BasePlatformPrintTemplateDto> queryPlatformPrintTemplate(BasePlatformPrintTemplateReqDTO dto) {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));
        Long serviceProviderId = currentUser.getServiceProviderId();

        if (StringUtils.isEmpty(dto.getPlatformLogisticsCode())) {
            Assert.notBlank(dto.getLogisticsCode(), I18nUtils.getMessage("base.check.platformprinttemplate.logistics.notnull"));
            //  传的是系统承运商，则转成平台承运商
            // 改用查询唯一配置方法 basePlatformLogisticsService.findOneDtl()
            BasePlatformLogisticsDetailReqDTO reqDTO = new BasePlatformLogisticsDetailReqDTO();
            reqDTO.setConfigType(1);
            reqDTO.setServiceProviderId(serviceProviderId);
            reqDTO.setDeliveryType(DeliveryTypeEnum.EXPRESS.getCode());
            reqDTO.setLogisticsCode(dto.getLogisticsCode());
            reqDTO.setPlatformCode(dto.getPlatformCode()); // TODO
            List<BasePlatformLogisticsDetail> platformLogisticsDetailList = basePlatformLogisticsMapper.findDetailList(reqDTO);
            Assert.notEmpty(platformLogisticsDetailList,  I18nUtils.getMessage("base.check.platformprinttemplate.logisticscoderelation.null",new String[]{dto.getLogisticsCode()}));
            Assert.isFalse(platformLogisticsDetailList.size() > 1, I18nUtils.getMessage("base.check.platformprinttemplate.duplicate.platform.config"));
            dto.setPlatformLogisticsCode(platformLogisticsDetailList.get(0).getPlatformLogisticsCode());
        }
        BasePlatformPrintTemplate template = new BasePlatformPrintTemplate();
        BeanUtils.copyProperties(dto, template);
        // 统一成同一个平台
        template.setPlatformCode(PlatformTypeEnum.getBelongPlatform(dto.getPlatformCode()));
        List<BasePlatformPrintTemplate> list = basePlatformPrintTemplateMapper.findTemplateList(template);
        return HuToolUtil.exchange(list, BasePlatformPrintTemplateDto.class);
    }

    public List<BasePlatformPrintTemplate> findTemplateList(BasePlatformPrintTemplate dto) {
        Assert.notBlank(dto.getPlatformCode(), I18nUtils.getMessage("base.check.platformprinttemplate.eccode.notnull"));
        return basePlatformPrintTemplateMapper.findTemplateList(dto);
    }

    /**
     * 批量保存或者更新电商平台面单模板
     *
     * @param templateSaveReqList
     * @return
     */
    @Override
    public int batchSaveOrUpdate(List<BasePlatformPrintTemplate> templateSaveReqList) {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));

        BasePlatformPrintTemplate dto = new BasePlatformPrintTemplate();
        dto.setPlatformCode(templateSaveReqList.get(0).getPlatformCode());
        String platformName = PlatformTypeEnum.getExistPlatformName(templateSaveReqList.get(0).getPlatformCode());
        // 查该电商平台的模板
        List<BasePlatformPrintTemplate> templateList = basePlatformPrintTemplateMapper.findTemplateList(dto);
        if (!CollectionUtils.isEmpty(templateList)) {
            List<String> platformLogisticsCodeList = templateSaveReqList.stream().map(BasePlatformPrintTemplate::getPlatformLogisticsCode).distinct().collect(Collectors.toList());
            // 找出已保存的自动同步的模板，手工插入的模板不删除
            List<Long> removeIdList = templateList.stream().filter(x -> platformLogisticsCodeList.contains(x.getPlatformLogisticsCode())
                            && BooleanEnum.TRUE.getCode().equals(x.getAutoGetFlag()))
                    .map(BasePlatformPrintTemplate::getId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(removeIdList)) {
                // 先删除该承运商的自动同步的面单模板，再新增
                GeneralBatchUpdateByIdsDto updateByIdsDto = new GeneralBatchUpdateByIdsDto();
                updateByIdsDto.setIds(removeIdList);
                updateByIdsDto.setDeletedFlag(BooleanEnum.TRUE.getCode());
                updateByIdsDto.setModifiedBy(currentUser.getUserId());
                updateByIdsDto.setModifiedName(currentUser.getUserName());
                updateByIdsDto.setModifiedDate(LocalDateTime.now());
                basePlatformPrintTemplateMapper.batchUpdate(updateByIdsDto);
            }
        }

        templateSaveReqList.sort(Comparator.comparing(x -> x.getPlatformLogisticsCode() + "_" + x.getTemplateType()));
        log.info("保存电商平台面单模板：{}", JSON.toJSONString(templateSaveReqList));
        return basePlatformPrintTemplateMapper.saveTemplate(templateSaveReqList);
    }
}
