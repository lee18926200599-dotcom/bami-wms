package com.basedata.server.service.integration.platform.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.basedata.common.vo.BasePlatformPrintTemplateDto;
import com.basedata.server.dto.integration.ReturnorderSenderVO;
import com.basedata.server.dto.waybill.template.wph.WphCloudprintTemplatesGetResponse;
import com.basedata.server.dto.waybill.template.wph.WphTemplateReq;
import com.basedata.server.service.integration.platform.PlatformMethodService;
import com.basedata.server.vo.integration.ShopNetsiteVo;
import com.basedata.server.vo.integration.ShopQueryVo;
import com.basedata.server.vo.integration.SyncFaceOrderTemplateReqVo;
import com.common.base.enums.PlatformTypeEnum;
import com.common.framework.execption.SystemException;
import com.common.language.util.I18nUtils;
import com.common.util.util.AssertUtils;
import com.common.util.util.http.HttpUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Slf4j
@Service
public class WPHPlatformMethodServiceImpl implements PlatformMethodService {
    @Value("${platformApi.netside.wphAddressUrl:}")
    private String wphAddressUrl;

    @Value("${platformApi.stdtemplate.wphStdTemplateUrl:}")
    private String wphStdTemplateUrl;

    /**
     * 获取网点信息
     * @param dto
     * @return
     */
    @Override
    public List<ShopNetsiteVo> getNetsideAddress(ShopQueryVo dto) throws Exception {
        AssertUtils.isNotNull(dto, I18nUtils.getMessage("base.check.platform.netside.query.null"));
        Assert.notBlank(wphAddressUrl, I18nUtils.getMessage("base.check.platform.netside.url.null"));
        log.info("唯品会 获取网点地址接口：" + wphAddressUrl);

        //
        String carrierCode = dto.getPlatformLogisticsCode();

        Map<String, Object> request = new HashMap<>();
        Map<String, Object> tms_request_header = new HashMap<>();
        tms_request_header.put("caller", dto.getPlatformCode());
        tms_request_header.put("request_time", new Date());
        request.put("tms_request_header", tms_request_header);
        request.put("store_id", dto.getVendorId());
        Map<String, Object> store_send_info_query_request = new HashMap<>();
        store_send_info_query_request.put("store_send_info_query_request", request);
        String param = JSONObject.toJSONString(store_send_info_query_request);
        log.debug("唯品会 平台网点信息请求入参为:{}", param);
        String result = HttpUtil.postForRequestOpen(param, wphAddressUrl, dto.getCustomer_id());
        log.debug("唯品会 接口返回信息：" + result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.getBoolean("success") != null && !jsonObject.getBoolean("success")) {
            throw new SystemException(jsonObject.getString("message"));
        }
        if (ObjectUtil.isEmpty(jsonObject.getString("data"))) {
            throw new RuntimeException(I18nUtils.getMessage("base.check.platform.netside.loadfail"));
        }

        List<ShopNetsiteVo> netSiteList = CollectionUtil.newArrayList();

        // 返回的data数据
        log.info("唯品会获取地址信息----------------" + result);
        JSONObject data = jsonObject.getJSONObject("data");
        // {"store_send_info_query_request":{"tms_request_header":{"caller":"WPH","request_time":1704351431244}},"data":{"returnCode":"vipapis.oauth-invalidate-failure","returnMessage":"store_id Field Value is not authorized!"}}
        if (ObjectUtil.isNotEmpty(data)) {
            if (data.get("returnCode").equals("vipapis.oauth-invalidate-failure")) {
                throw new RuntimeException(data.get("returnMessage") + "请检查该店铺是否是MP店铺");
            }
            JSONObject resultKey = data.getJSONObject("result");
            JSONArray accounts = resultKey.getJSONArray("store_send_info_list");
            for (int i = 0; i < accounts.size(); i++) {
                JSONObject acc = accounts.getJSONObject(i);
                ShopNetsiteVo vo = new ShopNetsiteVo();
                JSONObject waybill_cust = acc.getJSONObject("waybill_cust");
                vo.setNetsite_code(waybill_cust.getString("carrier_node_code"));
                if (!carrierCode.equals(waybill_cust.getString("carrier_code"))) {
                    continue;
                }
                JSONArray store_send_info_list = acc.getJSONArray("waybill_address_list");
                List<ReturnorderSenderVO> sendAddress = new ArrayList<>();
                for (int b = 0; b < store_send_info_list.size(); b++) {
                    ReturnorderSenderVO returnorderSenderVO = new ReturnorderSenderVO();
                    JSONObject obj = store_send_info_list.getJSONObject(b);
                    vo.setAmount("999");//唯品平台不给面单余额，默认999
                    returnorderSenderVO.setName(obj.get("name").toString());//联系人
                    returnorderSenderVO.setMobile(obj.get("tel").toString());//电话
                    returnorderSenderVO.setProvince(obj.get("province_name").toString());//省
                    returnorderSenderVO.setCity(obj.get("city_name").toString());//市
                    returnorderSenderVO.setArea(obj.get("region_name").toString());//区
                    returnorderSenderVO.setTown(obj.get("town_name").toString());//镇
                    returnorderSenderVO.setAddressId(obj.getString("address_code"));//地址ID
                    returnorderSenderVO.setDetailAddress(obj.get("address").toString());//详细地址

                    vo.setShortAddress(obj.get("address").toString());
                    vo.setNetsite_name(obj.get("region_name").toString());
                    sendAddress.add(returnorderSenderVO);
                    vo.setSendAddress(sendAddress);
                }
                netSiteList.add(vo);
            }
        }
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
        Assert.notBlank(wphStdTemplateUrl, I18nUtils.getMessage("base.check.platform.stdtemplate.url.notnull"));
        log.info("唯品会 获取标准模板：" + wphStdTemplateUrl);

        WphTemplateReq wphTemplateReq = new WphTemplateReq();
        WphTemplateReq.RequestDTO requestDTO = new WphTemplateReq.RequestDTO();
        WphTemplateReq.RequestDTO.HeaderDTO headerDTO = new WphTemplateReq.RequestDTO.HeaderDTO();
        headerDTO.setRequestTime(System.currentTimeMillis());
        requestDTO.setOwnerId(reqVo.getVendorId());
        requestDTO.setHeader(headerDTO);
        wphTemplateReq.setRequest(requestDTO);


        log.debug("唯品会 获取标准模板 请求入参为:{}", JSON.toJSONString(reqVo));
        String result = HttpUtil.postForIntegration(wphTemplateReq, wphStdTemplateUrl, reqVo.getCustomerId());
        log.debug("唯品会 获取标准模板 接口返回信息：" + result);
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
        WphCloudprintTemplatesGetResponse response = JSON.parseObject(result, WphCloudprintTemplatesGetResponse.class);
        if (response != null && response.getData().getResult() != null && response.getData().getResult().getModel() != null && !CollectionUtils.isEmpty(response.getData().getResult().getModel().getTemplates())) {
            List<WphCloudprintTemplatesGetResponse.DataDTO.ResultDTO.ModelDTO.TemplatesDTO> templates = response.getData().getResult().getModel().getTemplates();
            for (WphCloudprintTemplatesGetResponse.DataDTO.ResultDTO.ModelDTO.TemplatesDTO totalTemplateDTO : templates) {
                BasePlatformPrintTemplateDto printTemplateDto = new BasePlatformPrintTemplateDto();
                printTemplateDto.setTemplateName(totalTemplateDTO.getTemplateName());
                printTemplateDto.setTemplateUrl(totalTemplateDTO.getTemplateUrl());
                printTemplateDto.setTemplateId(String.valueOf(totalTemplateDTO.getTemplateId()));
                printTemplateDto.setType(0);
                printTemplateDto.setAutoGetFlag(1);
                printTemplateDto.setPlatformCode(PlatformTypeEnum.WPH.name());
                printTemplateDto.setPlatformName(PlatformTypeEnum.WPH.getPlatformName());
                printTemplateDto.setPlatformLogisticsCode(totalTemplateDTO.getCarrierCode());
                printTemplateDtoList.add(printTemplateDto);
            }
        }
        System.out.println("printTemplateDtoList = \n" + JSON.toJSONString(printTemplateDtoList));
        return printTemplateDtoList;
    }
}
