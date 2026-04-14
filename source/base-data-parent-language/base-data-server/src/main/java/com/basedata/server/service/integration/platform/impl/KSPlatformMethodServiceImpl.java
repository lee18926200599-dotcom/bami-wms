package com.basedata.server.service.integration.platform.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.basedata.common.vo.BasePlatformPrintTemplateDto;
import com.basedata.server.dto.integration.ReturnorderSenderVO;
import com.basedata.server.dto.waybill.template.ks.KsCloudprintStdtemplatesGetResponse;
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
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class KSPlatformMethodServiceImpl implements PlatformMethodService {

    @Value("${platformApi.netside.ksAddressUrl:}")
    private String ksAddressUrl;
    @Value("${platformApi.stdtemplate.ksStdTemplateUrl:}")
    private String ksStdTemplateUrl;

    /**
     * 获取网点信息
     *
     * @param dto
     * @return
     */
    @Override
    public List<ShopNetsiteVo> getNetsideAddress(ShopQueryVo dto) throws Exception {
        AssertUtils.isNotNull(dto, I18nUtils.getMessage("base.check.platform.netside.query.null"));
        Assert.notBlank(ksAddressUrl, I18nUtils.getMessage("base.check.platform.netside.url.null"));
        log.info("快手 获取网点地址接口：" + ksAddressUrl);

        String carrierCode = dto.getPlatformLogisticsCode();

        Map<String, Object> param = new HashMap<>();
        param.put("expressCompanyCode", carrierCode);
        String jsonData = JSONObject.toJSONString(param);
        log.debug("快手 平台网点信息请求入参为:{}", param);
        String result = HttpUtil.postForRequestOpen(jsonData, ksAddressUrl, dto.getCustomer_id());
        log.debug("快手 接口返回信息：" + result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.getBoolean("success") != null && !jsonObject.getBoolean("success")) {
            log.error("获取 快手 网点信息失败：" + jsonObject.getString("message"));
            throw new RuntimeException(I18nUtils.getMessage("base.check.platform.authcode.config",new String[]{dto.getCustomer_id(),jsonObject.getString("message")}));
        }
        if (ObjectUtil.isEmpty(jsonObject.getString("data"))) {
            throw new RuntimeException(I18nUtils.getMessage("base.check.platform.netside.loadfail"));
        }

        List<ShopNetsiteVo> netSiteList = CollectionUtil.newArrayList();

        // 返回的data数据
        JSONObject respData = jsonObject.getJSONObject("data");
        // {"expressCompanyCode":"SF","data":{"result":28,"code":"803000","msg":"鉴权失败","error_msg":"TOKEN过期","sub_code":"28","sub_msg":"TOKEN过期","request_id":"704352221454586928"}}
        if ("803000".equals(respData.getString("code"))) {
            throw new RuntimeException(respData.getString("msg") + "，" + respData.getString("error_msg"));
        }
        JSONArray data = respData.getJSONArray("data");
        if ("1".equals(respData.get("result").toString()) && ObjectUtil.isNotEmpty(data)) {
            for (int i = 0; i < data.size(); i++) {
                JSONObject bo = data.getJSONObject(i);
                ShopNetsiteVo vo = new ShopNetsiteVo();
                vo.setShortAddress(bo.getString("netSiteName"));
                vo.setNetsite_code(bo.getString("netSiteCode"));
                vo.setNetsite_name(bo.getString("netSiteName"));
                vo.setAmount(String.valueOf(bo.get("availableQuantity")));
                vo.setSettleAccount(bo.getString("settleAccount"));
                JSONArray senderAddress = (JSONArray) bo.get("senderAddress");
                List<ReturnorderSenderVO> address = new ArrayList<>();
                if (ObjectUtil.isNotEmpty(senderAddress)) {
                    for (int j = 0; j < senderAddress.size(); j++) {
                        JSONObject send = senderAddress.getJSONObject(j);
                        ReturnorderSenderVO senderVO = new ReturnorderSenderVO();
                        senderVO.setProvince(send.getString("provinceName"));
                        senderVO.setProvinceCode(send.getString("provinceCode"));
                        senderVO.setCity(send.getString("cityName"));
                        senderVO.setCityCode(send.getString("cityCode"));
                        senderVO.setArea(send.getString("districtName"));
                        senderVO.setAreaCode(send.getString("districtCode"));
                        senderVO.setAddressId(send.getString("districtCode"));
                        senderVO.setMonthAccount(vo.getSettleAccount());
                        senderVO.setDetailAddress(send.getString("detailAddress"));
                        address.add(senderVO);
                    }
                }
                vo.setSendAddress(address);
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
        Assert.notBlank(ksStdTemplateUrl, I18nUtils.getMessage("base.check.platform.stdtemplate.url.notnull"));
        log.info("快手 获取标准模板：" + ksStdTemplateUrl);

        log.debug("快手 获取标准模板 请求入参为:{}", JSON.toJSONString(reqVo));
        String result = HttpUtil.postForIntegration(null, ksStdTemplateUrl, reqVo.getCustomerId());
        log.debug("快手 获取标准模板 接口返回信息：" + result);
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
        KsCloudprintStdtemplatesGetResponse response = JSON.parseObject(result, KsCloudprintStdtemplatesGetResponse.class);
        // {"data":{"result":28,"code":"803000","msg":"鉴权失败","error_msg":"TOKEN过期","sub_code":"28","sub_msg":"TOKEN过期","request_id":"714117886430531632"}}
        if (CollectionUtils.isEmpty(response.getData().getData())) {
            throw new BizException("Authorization Code【" + reqVo.getCustomerId() + "】" + response.getData().getMsg() + "-" + response.getData().getErrorMsg());
        }

        List<KsCloudprintStdtemplatesGetResponse.DataDTO.TempalteDTO> datas = response.getData().getData();
        for (KsCloudprintStdtemplatesGetResponse.DataDTO.TempalteDTO standardTemplateResult : datas) {
            BasePlatformPrintTemplateDto printTemplateDto = new BasePlatformPrintTemplateDto();
            printTemplateDto.setTemplateName(standardTemplateResult.getTemplateName());
            printTemplateDto.setTemplateUrl(standardTemplateResult.getTemplateUrl());
            printTemplateDto.setTemplateType(String.valueOf(standardTemplateResult.getWaybillType()));
            printTemplateDto.setTemplateId(standardTemplateResult.getTemplateCode());
            printTemplateDto.setPreviewUrl(standardTemplateResult.getTemplateExampleUrl());
            printTemplateDto.setType(0);
            printTemplateDto.setAutoGetFlag(1);
            printTemplateDto.setPlatformCode(PlatformTypeEnum.KS.name());
            printTemplateDto.setPlatformName(PlatformTypeEnum.KS.getPlatformName());
            printTemplateDto.setPlatformLogisticsCode(standardTemplateResult.getExpressCompanyCode());
            printTemplateDtoList.add(printTemplateDto);
        }

        return printTemplateDtoList;
    }

}
