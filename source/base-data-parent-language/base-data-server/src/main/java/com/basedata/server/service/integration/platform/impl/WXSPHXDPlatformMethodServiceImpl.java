package com.basedata.server.service.integration.platform.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.basedata.common.vo.BasePlatformPrintTemplateDto;
import com.basedata.server.dto.integration.ReturnorderSenderVO;
import com.basedata.server.dto.netside.WxxdAccountGetRequest;
import com.basedata.server.dto.netside.WxxdAccountGetResponse;
import com.basedata.server.dto.waybill.template.wxsph.WxsphCloudprintTemplatesGetResponse;
import com.basedata.server.service.integration.platform.PlatformMethodService;
import com.basedata.server.vo.integration.ShopNetsiteVo;
import com.basedata.server.vo.integration.ShopQueryVo;
import com.basedata.server.vo.integration.SyncFaceOrderTemplateReqVo;
import com.common.base.enums.PlatformTypeEnum;
import com.common.framework.execption.BizException;
import com.common.framework.execption.SystemException;
import com.common.language.util.I18nUtils;
import com.common.util.util.AssertUtils;
import com.common.util.util.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 微信视频号小店
 */
@Slf4j
@Service
public class WXSPHXDPlatformMethodServiceImpl implements PlatformMethodService {

    @Value("${platformApi.netside.wxsphxdAddressUrl:}")
    private String wxsphxdAddressUrl;
    @Value("${platformApi.stdtemplate.wxsphStdTemplateUrl:}")
    private String wxsphStdTemplateUrl;

    /**
     * 获取网点信息
     *
     * @param dto
     * @return
     */
    @Override
    public List<ShopNetsiteVo> getNetsideAddress(ShopQueryVo dto) throws Exception {
        AssertUtils.isNotNull(dto, I18nUtils.getMessage("base.check.platform.netside.query.null"));
        Assert.notBlank(wxsphxdAddressUrl, I18nUtils.getMessage("base.check.platform.netside.url.null"));
        log.info("微信视频号小店 获取网点地址接口：" + wxsphxdAddressUrl);

        //
        String carrierCode = dto.getPlatformLogisticsCode();

        WxxdAccountGetRequest request = new WxxdAccountGetRequest();
        request.setDelivery_id(carrierCode);
        request.setNeed_balance(false);
        request.setLimit(100);
        String param = JSONObject.toJSONString(request);
        log.debug("微信视频号小店 平台网点信息请求入参为:{}", param);
        String result = HttpUtil.postForIntegration(param, wxsphxdAddressUrl, dto.getCustomer_id());
        log.debug("微信视频号小店 接口返回信息：" + result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.getBoolean("success") != null && !jsonObject.getBoolean("success")) {
            throw new SystemException(jsonObject.getString("message"));
        }
        WxxdAccountGetResponse response = JSONUtil.toBean(result, WxxdAccountGetResponse.class);
        if (!Objects.equals(response.getErrcode(), 0)) {
            throw new BizException(I18nUtils.getMessage("base.check.platform.store.config") + response.getErrmsg());
        }

        List<ShopNetsiteVo> netSiteList = CollectionUtil.newArrayList();

        for (WxxdAccountGetResponse.AccountInfo accountInfo : response.getAccount_list()) {
            ShopNetsiteVo vo = new ShopNetsiteVo();
            vo.setAttrStr1(accountInfo.getShop_id());
            vo.setAttrStr2(accountInfo.getAcct_id());
            vo.setAmount(String.valueOf(accountInfo.getAvailable()));
            ReturnorderSenderVO senderAddress = new ReturnorderSenderVO();
            senderAddress.setShopId(accountInfo.getShop_id());
            senderAddress.setAcctId(accountInfo.getAcct_id());
            if (Objects.equals(1, accountInfo.getCompany_type())) {
                WxxdAccountGetResponse.SiteInfo siteInfo = accountInfo.getSite_info();
                vo.setShortAddress(siteInfo.getSite_fullname());
                vo.setNetsite_code(siteInfo.getSite_code());
                vo.setNetsite_name(siteInfo.getSite_name());

                WxxdAccountGetResponse.SiteAddress address = siteInfo.getAddress();
                senderAddress.setProvince(address.getProvince_name());
                senderAddress.setProvinceCode(address.getProvince_code());
                senderAddress.setCity(address.getCity_name());
                senderAddress.setCityCode(address.getCity_code());
                senderAddress.setArea(address.getDistrict_name());
                senderAddress.setAreaCode(address.getDistrict_code());
                senderAddress.setTown(address.getStreet_name());
                senderAddress.setTownCode(address.getStreet_code());

                senderAddress.setDetailAddress(address.getDetail_address());

                WxxdAccountGetResponse.SiteContact contact = siteInfo.getContact();
                if (contact != null) {
                    senderAddress.setName(contact.getName());
                    senderAddress.setMobile(contact.getMobile());
                    senderAddress.setTel(contact.getPhone());
                }
            } else {
                vo.setSettleAccount(accountInfo.getMonthly_card());
                WxxdAccountGetResponse.SenderAddress sender_address = accountInfo.getSender_address();
                senderAddress.setProvince(sender_address.getProvince());
                senderAddress.setCity(sender_address.getCity());
                senderAddress.setArea(sender_address.getCounty());
                senderAddress.setTown(sender_address.getStreet());
                senderAddress.setDetailAddress(sender_address.getAddress());
                senderAddress.setMonthAccount(accountInfo.getMonthly_card());
            }
            vo.setSendAddress(Collections.singletonList(senderAddress));
            netSiteList.add(vo);
        }
        return netSiteList;
    }

    /**
     * 获取打印模板列表
     *
     * @param reqVo
     * @return
     */
    @Override
    public List<BasePlatformPrintTemplateDto> getFaceOrderTemplate(SyncFaceOrderTemplateReqVo reqVo) throws Exception {
        AssertUtils.isNotNull(reqVo, I18nUtils.getMessage("base.check.platform.query.fail"));
        Assert.notBlank(wxsphStdTemplateUrl, I18nUtils.getMessage("base.check.platform.stdtemplate.url.notnull"));
        log.info("微信视频号 获取标准模板：" + wxsphStdTemplateUrl);

        log.debug("微信视频号 获取标准模板 请求入参为:{}", JSON.toJSONString(reqVo));
        String result = HttpUtil.postForIntegration(null, wxsphStdTemplateUrl, reqVo.getCustomerId());
        log.debug("微信视频号 获取标准模板 接口返回信息：" + result);
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
        WxsphCloudprintTemplatesGetResponse response = JSON.parseObject(result, WxsphCloudprintTemplatesGetResponse.class);
        if (response.getData().getErrcode() != 0 || CollectionUtils.isEmpty(response.getData().getTotalTemplate())) {
            throw new BizException(response.getData().getErrmsg());
        }
        List<WxsphCloudprintTemplatesGetResponse.DataDTO.TotalTemplateDTO> results = response.getData().getTotalTemplate();
        for (WxsphCloudprintTemplatesGetResponse.DataDTO.TotalTemplateDTO totalTemplateDTO : results) {
            for (WxsphCloudprintTemplatesGetResponse.DataDTO.TotalTemplateDTO.TemplateListDTO templateListDTO : totalTemplateDTO.getTemplateList()) {
                BasePlatformPrintTemplateDto printTemplateDto = new BasePlatformPrintTemplateDto();
                printTemplateDto.setTemplateName(templateListDTO.getTemplateName() + "——" + templateListDTO.getTemplateDesc());
                printTemplateDto.setTemplateUrl(templateListDTO.getTemplateId());
                printTemplateDto.setTemplateId(templateListDTO.getTemplateId());
                printTemplateDto.setTemplateType(templateListDTO.getTemplateType());
                printTemplateDto.setType(0);
                printTemplateDto.setAutoGetFlag(1);
                printTemplateDto.setPlatformCode(PlatformTypeEnum.WXSPHXD.name());
                printTemplateDto.setPlatformName(PlatformTypeEnum.WXSPHXD.getPlatformName());
                printTemplateDto.setPlatformLogisticsCode(totalTemplateDTO.getDeliveryId());
                printTemplateDtoList.add(printTemplateDto);
            }
        }
        return printTemplateDtoList;
    }
}
