package com.basedata.server.service.integration.platform.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.basedata.common.vo.BasePlatformPrintTemplateDto;
import com.basedata.server.dto.integration.ReturnorderSenderVO;
import com.basedata.server.dto.netside.JDSuccessResponce;
import com.basedata.server.dto.waybill.template.jd.JdCloudprintStdtemplatesGetResponse;
import com.basedata.server.dto.waybill.template.jd.JdTemplateReq;
import com.basedata.server.service.integration.platform.PlatformMethodService;
import com.basedata.server.vo.integration.ShopNetsiteVo;
import com.basedata.server.vo.integration.ShopQueryVo;
import com.basedata.server.vo.integration.SyncFaceOrderTemplateReqVo;
import com.common.base.enums.PlatformTypeEnum;
import com.common.framework.execption.SystemException;
import com.common.language.util.I18nUtils;
import com.common.util.util.AssertUtils;
import com.common.util.util.http.HttpUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class JDPlatformMethodServiceImpl implements PlatformMethodService {

    @Value("${platformApi.netside.jdAddressUrl:}")
    private String jdAddressUrl;
    @Value("${platformApi.stdtemplate.jdStdTemplateUrl:}")
    private String jdTemplateUrl;

    /**
     * 获取网点信息
     *
     * @param dto
     * @return
     */
    @Override
    public List<ShopNetsiteVo> getNetsideAddress(ShopQueryVo dto) throws Exception {
        AssertUtils.isNotNull(dto, I18nUtils.getMessage("base.check.platform.netside.query.null"));
        Assert.notBlank(jdAddressUrl, I18nUtils.getMessage("base.check.platform.netside.url.null"));
        Assert.notBlank(dto.getVendorId(), I18nUtils.getMessage("base.check.platform.merchantid.notnull"));
        log.info("京东 获取网点地址接口：" + jdAddressUrl);

        //
        String carrierCode = dto.getPlatformLogisticsCode();

        Map<String, Object> param = new HashMap<>();
        //通过平台，授权码查询商家信息
        param.put("venderCode", dto.getVendorId());
        String JsonData = JSONObject.toJSONString(param);
        log.debug("京东 平台网点信息请求入参为:{}", param);
        String result = HttpUtil.postForIntegration(JsonData, jdAddressUrl, dto.getCustomer_id());
        log.debug("京东 接口返回信息：" + result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.getBoolean("success") != null && !jsonObject.getBoolean("success")) {
            throw new SystemException(jsonObject.getString("message"));
        }
        if (ObjectUtil.isEmpty(jsonObject.getString("data"))) {
            throw new RuntimeException(I18nUtils.getMessage("base.check.platform.netside.loadfail"));
        }

        List<ShopNetsiteVo> netSiteList = Lists.newArrayList();

        // 返回的data数据
        JSONObject respData = jsonObject.getJSONObject("data");
        if (respData.getJSONObject("error_response") != null) {
            JSONObject error_response = respData.getJSONObject("error_response");
            throw new RuntimeException(error_response.getString("zh_desc"));
        }

        JDSuccessResponce response = JSON.parseObject(jsonObject.getString("data"), JDSuccessResponce.class);
        JDSuccessResponce.JingdongLdopAlphaProviderSignSuccessInfoGetResponce.ResultInfo resultInfo = response.getJingdong_ldop_alpha_provider_sign_success_info_get_responce().getResultInfo();
        if (CollectionUtils.isEmpty(resultInfo.getData())) {
            throw new RuntimeException(resultInfo.getStatusMessage());
        }
        for (JDSuccessResponce.JingdongLdopAlphaProviderSignSuccessInfoGetResponce.ResultInfo.SuccessData data : resultInfo.getData()) {
            if (data.getProviderCode().equalsIgnoreCase(dto.getPlatformLogisticsCode())) {
                ShopNetsiteVo vo = new ShopNetsiteVo();
                vo.setShortAddress(data.getProviderName());
                vo.setNetsite_name(data.getBranchName());
                vo.setNetsite_code(data.getBranchCode());
                vo.setAmount(data.getAmount());
                vo.setSettleAccount(data.getSettlementCode());
                List<ReturnorderSenderVO> addList = new ArrayList<>();
                ReturnorderSenderVO senderVO = new ReturnorderSenderVO();
                senderVO.setProvince(data.getAddress().getProvinceName());
                senderVO.setProvinceCode(String.valueOf(data.getAddress().getProvinceId()));
                senderVO.setCity(data.getAddress().getCityName());
                senderVO.setCityCode(String.valueOf(data.getAddress().getCityId()));
                senderVO.setArea(data.getAddress().getCountryName());
                senderVO.setTown(data.getAddress().getCountrysideName());
                senderVO.setTownCode(String.valueOf(data.getAddress().getCountrysideId()));
                senderVO.setAddressId(String.valueOf(data.getAddress().getCountrysideId()));
                senderVO.setMonthAccount(vo.getSettleAccount());
                senderVO.setDetailAddress(data.getAddress().getAddress());
                addList.add(senderVO);
                // 承运商支持的增值服务
                if (!CollectionUtils.isEmpty(data.getValueAddedServices())) {
                    // 解析送货上门标识
                    boolean deliveryToDoorFlag = data.getValueAddedServices().stream().anyMatch(x -> "DELIVERY_TO_DOOR".equals(x.getServiceCode()));
                    vo.setAttrStr3(deliveryToDoorFlag ? "1" : "0");
                }
                vo.setSendAddress(addList);
                netSiteList.add(vo);
            }
        }
        return netSiteList;
    }

    /**
     * 获取打印模板列表
     * <p>
     * 京东物流开放平台  https://cloud.jdl.com/#/open-business-document/api-doc/157/1903
     * 获取打印模板列表 (getTemplates)
     *
     * @param reqVo
     * @return
     */
    @Override
    public List<BasePlatformPrintTemplateDto> getFaceOrderTemplate(SyncFaceOrderTemplateReqVo reqVo) throws Exception {
        AssertUtils.isNotNull(reqVo, I18nUtils.getMessage("base.check.platform.query.fail"));
        Assert.notBlank(jdTemplateUrl, I18nUtils.getMessage("base.check.platform.stdtemplate.url.notnull"));
        log.info("京东 获取面单打印模板：" + jdTemplateUrl);

        log.debug("京东 获取面单打印模板 请求入参为:{}", JSON.toJSONString(reqVo));
        JdTemplateReq.ParamDto paramDto = new JdTemplateReq.ParamDto();
        JdTemplateReq jdTemplateReq = new JdTemplateReq();
        jdTemplateReq.setParam1(paramDto);
        String result = HttpUtil.postForIntegration(jdTemplateReq, jdTemplateUrl, reqVo.getCustomerId());
        log.debug("京东 获取面单打印模板 接口返回信息：" + result);
        Assert.notBlank(result, I18nUtils.getMessage("base.check.platform.stdtemplate.loadfail"));
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.getBoolean("success") != null && !jsonObject.getBoolean("success")) {
            throw new SystemException(jsonObject.getString("message"));
        }
        JSONObject data = jsonObject.getJSONObject("data");
        if (ObjectUtil.isEmpty(data)) {
            throw new RuntimeException(I18nUtils.getMessage("base.check.platform.stdtemplate.loadfail"));
        }
        List<BasePlatformPrintTemplateDto> printTemplateDtoList = new ArrayList<>();
        JdCloudprintStdtemplatesGetResponse response = JSON.parseObject(result, JdCloudprintStdtemplatesGetResponse.class);
        if (response.getData() != null && response.getData().getJingdongPrintingTemplateGettemplatelistResponce() != null &&
                !response.getData().getJingdongPrintingTemplateGettemplatelistResponce().getCode().equals("0")) {
            throw new RuntimeException(response.getData().getJingdongPrintingTemplateGettemplatelistResponce().getReturnType().getMessage());
        }
        List<JdCloudprintStdtemplatesGetResponse.DataDTO.JingdongPrintingTemplateGetTemplateListResponceDTO.ReturnTypeDTO.DatasDTO.SDatasDTO> diyDatas = response.getData().getJingdongPrintingTemplateGettemplatelistResponce().getReturnType().getDatas().getSDatas();
        for (JdCloudprintStdtemplatesGetResponse.DataDTO.JingdongPrintingTemplateGetTemplateListResponceDTO.ReturnTypeDTO.DatasDTO.SDatasDTO standardTemplatesDto : diyDatas) {
            List<JdCloudprintStdtemplatesGetResponse.DataDTO.JingdongPrintingTemplateGetTemplateListResponceDTO.ReturnTypeDTO.DatasDTO.SDatasDTO.StandardTemplatesDTO> standardTemplates = standardTemplatesDto.getStandardTemplates();
            for (JdCloudprintStdtemplatesGetResponse.DataDTO.JingdongPrintingTemplateGetTemplateListResponceDTO.ReturnTypeDTO.DatasDTO.SDatasDTO.StandardTemplatesDTO standardTemplatesDTO : standardTemplates) {
                BasePlatformPrintTemplateDto printTemplateDto = new BasePlatformPrintTemplateDto();
                printTemplateDto.setTemplateName(standardTemplatesDTO.getStandardTemplateName());
                printTemplateDto.setTemplateUrl(standardTemplatesDTO.getStandardTemplateUrl());
                printTemplateDto.setTemplateId(standardTemplatesDTO.getStandardTemplateId().toString());
                printTemplateDto.setTemplateType(standardTemplatesDTO.getStandardWaybillType());
                printTemplateDto.setType(0);
                printTemplateDto.setAutoGetFlag(1);
                printTemplateDto.setPlatformCode(PlatformTypeEnum.JD.name());
                printTemplateDto.setPlatformName(PlatformTypeEnum.JD.getPlatformName());
                printTemplateDto.setPlatformLogisticsCode(standardTemplatesDto.getCpCode());
                printTemplateDtoList.add(printTemplateDto);
            }

        }

        return printTemplateDtoList;
    }
}
