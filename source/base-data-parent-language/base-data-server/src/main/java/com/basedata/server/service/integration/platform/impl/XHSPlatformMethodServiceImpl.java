package com.basedata.server.service.integration.platform.impl;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.basedata.common.vo.BasePlatformPrintTemplateDto;
import com.basedata.server.dto.integration.ReturnorderSenderVO;
import com.basedata.server.dto.netside.XhsNetSiteAddressGetResponse;
import com.basedata.server.dto.netside.XhsNetSiteAddressInfo;
import com.basedata.server.dto.netside.XhsNetSiteAddressRequestParam;
import com.basedata.server.dto.netside.XhsSubscribeInfo;
import com.basedata.server.dto.waybill.XhsSendAddress;
import com.basedata.server.dto.waybill.template.xhs.XhsCloudprintStdtemplatesGetResponse;
import com.basedata.server.service.integration.platform.PlatformMethodService;
import com.basedata.server.vo.integration.ShopNetsiteVo;
import com.basedata.server.vo.integration.ShopQueryVo;
import com.basedata.server.vo.integration.SyncFaceOrderTemplateReqVo;
import com.common.base.enums.PlatformTypeEnum;
import com.common.framework.execption.BizException;
import com.common.framework.execption.SystemException;
import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.common.util.util.AssertUtils;
import com.common.util.util.http.HttpUtil;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 小红书
 */
@Slf4j
@Service
public class XHSPlatformMethodServiceImpl implements PlatformMethodService {

    @Value("${platformApi.netside.xhsAddressUrl:}")
    private String xhsNetSiteAddressUrl;

    @Value("${platformApi.stdtemplate.xhsStdTemplateUrl:}")
    private String xhsStdTemplateUrl;

    /**
     * 获取网点信息
     *
     * @param dto
     * @return
     */
    @Override
    public List<ShopNetsiteVo> getNetsideAddress(ShopQueryVo dto) throws Exception {
        AssertUtils.isNotNull(dto, I18nUtils.getMessage("base.check.platform.netside.query.null"));
        Assert.notBlank(xhsNetSiteAddressUrl, I18nUtils.getMessage("base.check.platform.netside.url.null"));
        log.info("小红书 获取网点地址接口：" + xhsNetSiteAddressUrl);

        String carrierCode = dto.getPlatformLogisticsCode();

        XhsNetSiteAddressRequestParam requestParam = new XhsNetSiteAddressRequestParam();
        requestParam.setCpCode(carrierCode);
        requestParam.setNeedUsage(false);
        requestParam.setBrandCode("");

        String param = JSON.toJSONString(requestParam);
        log.debug("小红书 平台网点信息请求入参为:{}", param);
        String result = HttpUtil.postForRequestOpen(param, xhsNetSiteAddressUrl, dto.getCustomer_id());
        log.debug("小红书 接口返回信息：" + result);
        RestMessage<String> restMessage = com.alibaba.fastjson2.JSON.parseObject(result,RestMessage.class);
//        JSONObject jsonObject = JSONObject.parseObject(result);
//        if (jsonObject.containsKey("success") && !jsonObject.getBoolean("success") && jsonObject.containsKey("message")) {
//            throw new SystemException(jsonObject.getString("message"));
//        }
//        if (jsonObject.containsKey("success") && !jsonObject.getBoolean("success") && jsonObject.containsKey("msg")) {
//            //  {"msg":"根据accessToken和appId获取mercuryUser失败：accessToken expired","code":401,"data":{},"success":false}
//            throw new RuntimeException(jsonObject.getString("msg"));
//        }
        if (!restMessage.isSuccess()){
            throw new SystemException(restMessage.getMessage());
        }

        XhsNetSiteAddressGetResponse response = JSON.parseObject(restMessage.getData(), XhsNetSiteAddressGetResponse.class);
        if (!Objects.equals(response.getError_code(), 0)) {
            throw new BizException(I18nUtils.getMessage("base.check.platform.netside.subscribe",new String[]{carrierCode, response.getError_msg()}));
        }
        List<ShopNetsiteVo> netSiteList = Lists.newArrayList();
        List<XhsSubscribeInfo> subscribeList = response.getData().getSubscribeList();
        AssertUtils.isNotEmpty(subscribeList, I18nUtils.getMessage("base.check.platform.netside.noreview",new String[]{carrierCode}));
        subscribeList.forEach(subscribe -> {
            ShopNetsiteVo vo = new ShopNetsiteVo();
            vo.setNetsite_code(subscribe.getBranchCode());
            vo.setNetsite_name(subscribe.getBranchName());
            vo.setSettleAccount(subscribe.getCustomerCode());
            List<XhsNetSiteAddressInfo> senderAddressList = subscribe.getSenderAddressList();
            List<ReturnorderSenderVO> senderList = Lists.newArrayList();
            senderAddressList.forEach(senderAddress -> {
                XhsSendAddress address = senderAddress.getAddress();
                ReturnorderSenderVO sender = new ReturnorderSenderVO();
                sender.setProvince(address.getProvince());
                sender.setCity(address.getCity());
                sender.setArea(address.getDistrict());
                sender.setTown(address.getTown());
                sender.setDetailAddress(address.getDetail());
                sender.setAddressId(address.getAddressId());
                sender.setMonthAccount(subscribe.getCustomerCode());
                senderList.add(sender);
            });
            vo.setSendAddress(senderList);
            netSiteList.add(vo);
        });

        return netSiteList;
    }

    /**
     * 获取打印模板列表
     *
     * @param reqVo
     * @return
     */
    @SneakyThrows
    @Override
    public List<BasePlatformPrintTemplateDto> getFaceOrderTemplate(SyncFaceOrderTemplateReqVo reqVo) {
        AssertUtils.isNotNull(reqVo, I18nUtils.getMessage("base.check.platform.query.fail"));
        Assert.notBlank(xhsStdTemplateUrl, I18nUtils.getMessage("base.check.platform.stdtemplate.url.notnull"));
        log.info("小红书 获取标准模板：" + xhsStdTemplateUrl);

        log.debug("小红书 获取标准模板 请求入参为:{}", JSON.toJSONString(reqVo));
        String result = HttpUtil.postForIntegration(null, xhsStdTemplateUrl, reqVo.getCustomerId());
        log.debug("小红书 获取标准模板 接口返回信息：" + result);
        Assert.notBlank(result, I18nUtils.getMessage("base.check.platform.stdtemplate.loadfail"));
//        JSONObject jsonObject = JSONObject.parseObject(result);
//        if (jsonObject.getBoolean("success") != null && !jsonObject.getBoolean("success")) {
//            throw new SystemException(jsonObject.getString("message"));
//        }
//        JSONObject data = jsonObject.getJSONObject("data");
//        if (ObjectUtil.isEmpty(data)) {
//            throw new RuntimeException("获取标准模板失败");
//        }
//        if (!data.getBoolean("success") && data.containsKey("msg")) {
//            throw new RuntimeException(data.getString("msg"));
//        }
        RestMessage<String> restMessage = JSON.parseObject(result, RestMessage.class);
        if (!restMessage.isSuccess()){
            throw new SystemException(restMessage.getMessage());
        }
        List<BasePlatformPrintTemplateDto> printTemplateDtoList = new ArrayList<>();
        XhsCloudprintStdtemplatesGetResponse response = JSON.parseObject(result, XhsCloudprintStdtemplatesGetResponse.class);
        if (!response.getData().getSuccess()) {
            throw new BizException(response.getData().getErrorMsg());
        }
        if (response.getData() != null && response.getData().getSuccess() && response.getData().getData() != null) {
            XhsCloudprintStdtemplatesGetResponse.DataDTO.TemplateDTO templateDTO = response.getData().getData();
            for (XhsCloudprintStdtemplatesGetResponse.DataDTO.TemplateDTO.TemplateListDTO standardTemplateResult : templateDTO.getTemplateList()) {
                if (StringUtils.hasLength(standardTemplateResult.getStandardTemplateUrl())) {
                    BasePlatformPrintTemplateDto printTemplateDto = new BasePlatformPrintTemplateDto();
                    printTemplateDto.setTemplateName(standardTemplateResult.getTemplateName());
                    printTemplateDto.setTemplateUrl(standardTemplateResult.getStandardTemplateUrl());
                    printTemplateDto.setTemplateType(standardTemplateResult.getTemplateType());
                    printTemplateDto.setTemplateId(String.valueOf(standardTemplateResult.getId()));
                    printTemplateDto.setBrandCode(standardTemplateResult.getBrandCode());
                    printTemplateDto.setType(0);
                    printTemplateDto.setAutoGetFlag(1);
                    printTemplateDto.setPlatformCode(PlatformTypeEnum.XHS.name());
                    printTemplateDto.setPlatformName(PlatformTypeEnum.XHS.getPlatformName());
                    printTemplateDto.setPlatformLogisticsCode(standardTemplateResult.getCpCode());
                    printTemplateDto.setPreviewUrl(standardTemplateResult.getTemplatePreviewUrl());
                    printTemplateDtoList.add(printTemplateDto);
                }
                if (StringUtils.hasLength(standardTemplateResult.getCustomerTemplateUrl())) {
                    BasePlatformPrintTemplateDto printTemplateDto = new BasePlatformPrintTemplateDto();
                    printTemplateDto.setTemplateName(standardTemplateResult.getTemplateName());
                    printTemplateDto.setTemplateUrl(standardTemplateResult.getCustomerTemplateUrl());
                    printTemplateDto.setTemplateType(String.valueOf(standardTemplateResult.getTemplateCustomerType()));
                    printTemplateDto.setTemplateId(String.valueOf(standardTemplateResult.getId()));
                    printTemplateDto.setBrandCode(standardTemplateResult.getBrandCode());
                    printTemplateDto.setType(1);
                    printTemplateDto.setAutoGetFlag(1);
                    printTemplateDto.setPlatformCode(PlatformTypeEnum.XHS.name());
                    printTemplateDto.setPlatformName(PlatformTypeEnum.XHS.getPlatformName());
                    printTemplateDto.setPlatformLogisticsCode(standardTemplateResult.getCpCode());
                    printTemplateDto.setPreviewUrl(standardTemplateResult.getTemplatePreviewUrl());
                    printTemplateDtoList.add(printTemplateDto);
                }
            }
        }
        System.out.println("printTemplateDtoList = \n" + JSON.toJSONString(printTemplateDtoList));
        return printTemplateDtoList;
    }
}
