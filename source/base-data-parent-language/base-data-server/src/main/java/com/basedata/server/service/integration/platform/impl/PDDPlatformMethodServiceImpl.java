package com.basedata.server.service.integration.platform.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.basedata.common.vo.BasePlatformPrintTemplateDto;
import com.basedata.server.dto.integration.ReturnorderSenderVO;
import com.basedata.server.dto.waybill.template.pdd.PddCloudprintStdtemplatesGetResponse;
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
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PDDPlatformMethodServiceImpl implements PlatformMethodService {
    @Value("${platformApi.netside.pddAddressUrl:}")
    private String pddAddressUrl;
    @Value("${platformApi.stdtemplate.pddStdTemplateUrl:}")
    private String pddStdTemplateUrl;

    /**
     * 获取网点信息
     *
     * @param dto
     * @return
     */
    @Override
    public List<ShopNetsiteVo> getNetsideAddress(ShopQueryVo dto) throws Exception {
        AssertUtils.isNotNull(dto, I18nUtils.getMessage("base.check.platform.netside.query.null"));
        Assert.notBlank(pddAddressUrl, I18nUtils.getMessage("base.check.platform.netside.url.null"));
        log.info("拼多多 获取网点地址接口：" + pddAddressUrl);

        List<ShopNetsiteVo> list = CollectionUtil.newArrayList();

        // throw new RuntimeException("拼多多获取承运商编码失败");
        Map<String, Object> param = new HashMap<>();
        param.put("wp_code", dto.getPlatformLogisticsCode());
        String JsonData = JSONObject.toJSONString(param);
        log.debug("拼多多 平台网点信息请求入参为:{}", param);
        String result = HttpUtil.postForIntegration(JsonData, pddAddressUrl, dto.getCustomer_id());
        log.debug("拼多多 接口返回信息：" + result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.getBoolean("success") != null && !jsonObject.getBoolean("success")) {
            throw new SystemException(jsonObject.getString("message"));
        }
        if (ObjectUtil.isEmpty(jsonObject.getString("data"))) {
            throw new RuntimeException(I18nUtils.getMessage("base.check.platform.netside.loadfail"));
        }
        // 返回的data数据
        JSONObject respData = jsonObject.getJSONObject("data");
        // 返回异常
        // data:{"error_response":{"error_msg":"签名验证失败","sub_msg":"签名验证失败","sub_code":"20004","error_code":20004,"request_id":"17043503757231146"}}
        if (respData.getJSONObject("error_response") != null) {
            throw new RuntimeException(respData.getJSONObject("error_response").getString("error_msg"));
        }
        JSONObject responseData = respData.getJSONObject("pdd_waybill_search_response");
        JSONArray waybills = responseData.getJSONArray("waybill_apply_subscription_cols");
        log.debug("waybill_apply_subscription_cols:" + waybills.toJSONString());
        if (ObjectUtil.isNotEmpty(waybills)) {
            for (int i = 0; i < waybills.size(); i++) {
                JSONObject branch = waybills.getJSONObject(i);
                JSONArray account = branch.getJSONArray("branch_account_cols");
                for (int j = 0; j < account.size(); j++) {
                    ShopNetsiteVo vo = new ShopNetsiteVo();
                    JSONObject acc = account.getJSONObject(j);
                    if (StringUtils.isNotEmpty(acc.getString("branch_name"))) {
                        vo.setSettleAccount(acc.getString("branch_name"));
                        vo.setNetsite_name(acc.getString("branch_name"));
                    } else {
//                        vo.setSettleAccount(dto.getPlatformLogisticsCode() + "默认店铺" + (i + 1));
//                        vo.setNetsite_name(dto.getPlatformLogisticsCode() + "默认店铺" + (i + 1));
                    }
                    if (StringUtils.isNotEmpty(acc.getString("branch_code"))) {
                        vo.setNetsite_code(acc.getString("branch_code"));
                    }
                    vo.setAmount(acc.getString("quantity"));
                    JSONArray address = acc.getJSONArray("shipp_address_cols");
                    List<ReturnorderSenderVO> addList = new ArrayList<>();
                    for (int k = 0; k < address.size(); k++) {
                        JSONObject json = address.getJSONObject(k);
                        ReturnorderSenderVO senderVO = new ReturnorderSenderVO();
                        senderVO.setProvince(json.getString("province"));
                        senderVO.setCity(json.getString("city"));
                        senderVO.setArea(json.getString("district"));
                        senderVO.setDetailAddress(json.getString("detail"));
                        addList.add(senderVO);
                    }
                    vo.setSendAddress(addList);
                    list.add(vo);
                }
            }
        }
        return list;
    }

    /**
     * 获取标准面单模板
     *
     * @param reqVo
     * @return
     */
    @SneakyThrows
    @Override
    public List<BasePlatformPrintTemplateDto> getFaceOrderTemplate(SyncFaceOrderTemplateReqVo reqVo) {

        AssertUtils.isNotNull(reqVo, I18nUtils.getMessage("base.check.platform.query.fail"));
        Assert.notBlank(pddStdTemplateUrl, I18nUtils.getMessage("base.check.platform.stdtemplate.url.notnull"));
        log.info("拼多多 获取标准模板：" + pddStdTemplateUrl);

        log.debug("拼多多 获取标准模板 请求入参为:{}", JSON.toJSONString(reqVo));
        String result = HttpUtil.postForIntegration(null, pddStdTemplateUrl, reqVo.getCustomerId());
        log.debug("拼多多 获取标准模板 接口返回信息：" + result);
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
        PddCloudprintStdtemplatesGetResponse response = JSON.parseObject(result, PddCloudprintStdtemplatesGetResponse.class);
        if (response != null && response.getData() != null && response.getData().getPddCloudprintStdtemplatesGetResponse()!= null
            && response.getData().getPddCloudprintStdtemplatesGetResponse().getResult() != null) {
            List<PddCloudprintStdtemplatesGetResponse.DataDTO.PddCloudprintStdtemplatesGetResponseDTO.ResultDTO.DatasDTO> datas = response.getData().getPddCloudprintStdtemplatesGetResponse().getResult().getDatas();
            for (PddCloudprintStdtemplatesGetResponse.DataDTO.PddCloudprintStdtemplatesGetResponseDTO.ResultDTO.DatasDTO standardTemplateResult : datas) {

                String wpCode = standardTemplateResult.getWpCode();
                List<PddCloudprintStdtemplatesGetResponse.DataDTO.PddCloudprintStdtemplatesGetResponseDTO.ResultDTO.DatasDTO.StandardTemplatesDTO> standardTemplates = standardTemplateResult.getStandardTemplates();
                for (PddCloudprintStdtemplatesGetResponse.DataDTO.PddCloudprintStdtemplatesGetResponseDTO.ResultDTO.DatasDTO.StandardTemplatesDTO templatesDTO : standardTemplates){
                    BasePlatformPrintTemplateDto printTemplateDto = new BasePlatformPrintTemplateDto();
                    printTemplateDto.setTemplateName(templatesDTO.getStandardTemplateName());
                    printTemplateDto.setTemplateUrl(templatesDTO.getStandardTemplateUrl());
                    printTemplateDto.setTemplateId(String.valueOf(templatesDTO.getStandardTemplateId()));
                    printTemplateDto.setTemplateType(String.valueOf(templatesDTO.getStandardWaybillType()));
                    printTemplateDto.setType(0);
                    printTemplateDto.setAutoGetFlag(1);
                    printTemplateDto.setPlatformCode(PlatformTypeEnum.PDD.name());
                    printTemplateDto.setPlatformName(PlatformTypeEnum.PDD.getPlatformName());
                    printTemplateDto.setPlatformLogisticsCode(wpCode);
                    printTemplateDtoList.add(printTemplateDto);
                }
            }
        }
        return printTemplateDtoList;
    }
}
