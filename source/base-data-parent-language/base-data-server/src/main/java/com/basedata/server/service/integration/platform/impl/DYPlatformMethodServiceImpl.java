package com.basedata.server.service.integration.platform.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.basedata.common.vo.BasePlatformPrintTemplateDto;
import com.basedata.server.dto.integration.ReturnorderSenderVO;
import com.basedata.server.dto.waybill.template.dy.DyStdTemplateResponse;
import com.basedata.server.dto.waybill.template.dy.TemplateData;
import com.basedata.server.dto.waybill.template.dy.TemplateInfo;
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
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *  抖音小店
 */
@Slf4j
@Service
public class DYPlatformMethodServiceImpl implements PlatformMethodService {

    @Value("${platformApi.netside.dyAddressUrl:}")
    private String dyAddressUrl;
    @Value("${platformApi.stdtemplate.dyStdTemplateUrl:}")
    private String dyStdTemplateUrl;
    @Value("${platformApi.customtemplate.dyCustomTemplateUrl:}")
    private String dyCustomTemplateUrl;

    /**
     * 获取网点信息
     *
     * @param dto
     * @return
     */
    @Override
    public List<ShopNetsiteVo> getNetsideAddress(ShopQueryVo dto) throws Exception {
        AssertUtils.isNotNull(dto, I18nUtils.getMessage("base.check.platform.netside.query.null"));
        Assert.notBlank(dyAddressUrl, I18nUtils.getMessage("base.check.platform.netside.url.null"));
        log.info("抖音 获取网点地址接口：" + dyAddressUrl);

        // throw new RuntimeException("抖音获取承运商编码失败");
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("logistics_code", dto.getPlatformLogisticsCode());
        String param = JSONObject.toJSONString(objectObjectHashMap);
        log.debug("抖音 平台网点信息请求入参为:{}", param);
        String result = HttpUtil.postForIntegration(param, dyAddressUrl, dto.getCustomer_id());
        log.debug("抖音 接口返回信息：" + result);
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
        if (respData.getInteger("code") == 40003) {
            // access_token不存在，请使用最新的access_token访问
            throw new RuntimeException(respData.getString("sub_msg"));
        }
        JSONObject data = respData.getJSONObject("data");

        // 遍历网点
        JSONArray netsites = data.getJSONArray("netsites");
        for (int i = 0; i < netsites.size(); i++) {
            ShopNetsiteVo shopNetsiteVo = new ShopNetsiteVo();
            //查到网点信息
            shopNetsiteVo.setNetsite_code(netsites.getJSONObject(i).getString("netsite_code"));
            shopNetsiteVo.setNetsite_name(netsites.getJSONObject(i).getString("netsite_name"));
            // 遍历网点地址
            JSONArray addre = netsites.getJSONObject(i).getJSONArray("sender_address");
            ReturnorderSenderVO returnorderSenderVO = new ReturnorderSenderVO();
            List<ReturnorderSenderVO> addList = new ArrayList<>();
            for (int j = 0; j < addre.size(); j++) {
                returnorderSenderVO.setArea(addre.getJSONObject(j).getString("district_name"));//区
                returnorderSenderVO.setCity(addre.getJSONObject(j).getString("city_name"));//市
                returnorderSenderVO.setProvince(addre.getJSONObject(j).getString("province_name"));//省
                returnorderSenderVO.setTown(addre.getJSONObject(j).getString("street_name"));//镇
                returnorderSenderVO.setDetailAddress(addre.getJSONObject(j).getString("detail_address"));//详细地址
                shopNetsiteVo.setShortAddress(addre.getJSONObject(j).getString("detail_address"));
                addList.add(returnorderSenderVO);
            }
            shopNetsiteVo.setSendAddress(addList);
            netSiteList.add(shopNetsiteVo);
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
        Assert.notBlank(dyStdTemplateUrl, I18nUtils.getMessage("base.check.platform.stdtemplate.url.notnull"));
        log.info("抖音 获取标准模板：" + dyStdTemplateUrl);

        log.debug("抖音 获取标准模板 请求入参为:{}", JSON.toJSONString(reqVo));
        String result = HttpUtil.postForIntegration(null, dyStdTemplateUrl, reqVo.getCustomerId());
        log.debug("抖音 获取标准模板 接口返回信息：" + result);
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
        DyStdTemplateResponse response = JSON.toJavaObject(data, DyStdTemplateResponse.class);
        if (response.getCode() != 10000) {
            throw new BizException(I18nUtils.getMessage("base.check.platform.stdtemplate.loadfail.msg",new String[]{response.getMsg() + "。" + response.getSub_msg()}));
        }
        if (!CollectionUtils.isEmpty(response.getData().getTemplate_data())) {
            for (TemplateData templateData : response.getData().getTemplate_data()) {
                for (TemplateInfo templateInfo : templateData.getTemplate_infos()) {
                    BasePlatformPrintTemplateDto printTemplateDto = new BasePlatformPrintTemplateDto();
                    printTemplateDto.setTemplateName(templateInfo.getTemplate_name());
                    printTemplateDto.setTemplateUrl(templateInfo.getTemplate_url());
                    printTemplateDto.setTemplateId(String.valueOf(templateInfo.getTemplate_id()));
                    printTemplateDto.setTemplateType(String.valueOf(templateInfo.getTemplate_type()));
                    printTemplateDto.setPreviewUrl(templateInfo.getPerview_url());
                    printTemplateDto.setType(0);
                    printTemplateDto.setAutoGetFlag(1);
                    printTemplateDto.setPlatformCode(PlatformTypeEnum.DY.name());
                    printTemplateDto.setPlatformName(PlatformTypeEnum.DY.getPlatformName());
                    printTemplateDto.setPlatformLogisticsCode(templateData.getLogistics_code());
                    printTemplateDtoList.add(printTemplateDto);
                }
            }
        }
        return printTemplateDtoList;
    }
}
