package com.basedata.server.service.integration.platform.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.basedata.common.vo.BasePlatformPrintTemplateDto;
import com.basedata.server.dto.integration.ReturnorderSenderVO;
import com.basedata.server.dto.integration.WaybillApplyRequest;
import com.basedata.server.dto.waybill.template.tb.CainiaoCloudprintStdtemplatesGetResponse;
import com.basedata.server.dto.waybill.template.tb.StandardTemplateDo;
import com.basedata.server.dto.waybill.template.tb.StandardTemplateResult;
import com.basedata.server.service.integration.platform.PlatformMethodService;
import com.basedata.server.vo.integration.ShopNetsiteVo;
import com.basedata.server.vo.integration.ShopQueryVo;
import com.basedata.server.vo.integration.SyncFaceOrderTemplateReqVo;
import com.common.base.constants.RedisConstants;
import com.common.base.enums.PlatformTypeEnum;
import com.common.framework.execption.SystemException;
import com.common.framework.redis.RedisUtil;
import com.common.framework.web.SpringBeanLoader;
import com.common.language.util.I18nUtils;
import com.common.util.util.AssertUtils;
import com.common.util.util.http.HttpUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *  淘宝网点/面单余额获取实现
 */
@Slf4j
@Service
public class TBPlatformMethodServiceImpl implements PlatformMethodService {

    @Value("${platformApi.netside.tbAddressUrl:}")
    private String tbAddressUrl;
    @Value("${platformApi.netside.tbscAddressUrl:}")
    private String tbscAddressUrl;

    @Value("${platformApi.stdtemplate.tbStdTemplateUrl:}")
    private String tbStdTemplateUrl;


    /**
     * 获取网点信息
     *开放平台-文档中心  https://open.taobao.com/api.htm?docId=27125&docType=2
     * @param dto
     * @return
     */
    @Override
    public List<ShopNetsiteVo> getNetsideAddress(ShopQueryVo dto) throws Exception {
        AssertUtils.isNotNull(dto, I18nUtils.getMessage("base.check.platform.netside.query.null"));
        Assert.notBlank(tbscAddressUrl, I18nUtils.getMessage("base.check.platform.netside.url.null"));
        log.info("淘宝 获取网点地址接口：" + tbscAddressUrl);

        RedisUtil redisUtil = SpringBeanLoader.getApplicationContext().getBean(RedisUtil.class);
//        String reqUrl = tbscAddressUrl;
        String reqUrl = tbAddressUrl;
        // 手动塞数据
//        redisUtil.set(RedisConstants.WMS_TM_GG_CONFIG, "18119,2000,2001");
        if (redisUtil.hasKey(RedisConstants.WMS_TM_GG_CONFIG)){
            String serverOwner = (String) redisUtil.get(RedisConstants.WMS_TM_GG_CONFIG);
            if (StringUtils.isNotBlank(serverOwner)) {
                List<String> faceOwnerList = Arrays.asList(serverOwner.split(","));
                if (faceOwnerList.contains(String.valueOf(dto.getOwnerId()))){
                    reqUrl = tbAddressUrl;
                }
            }
        }
        String carrierCode = dto.getPlatformLogisticsCode();

        WaybillApplyRequest obj1 = new WaybillApplyRequest();
        obj1.setCp_code(carrierCode);
        String param = JSONObject.toJSONString(obj1);
        log.debug("淘宝 平台网点信息请求入参为:{}", param);
        String result = HttpUtil.postForRequestOpen(param, reqUrl, dto.getCustomer_id());
        log.debug("淘宝 接口返回信息：" + result);
        Assert.notBlank(result, I18nUtils.getMessage("base.check.platform.netside.loadfail"));
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.getBoolean("success") != null && !jsonObject.getBoolean("success")) {
            throw new SystemException(jsonObject.getString("message"));
        }
        if (ObjectUtil.isEmpty(jsonObject.getString("data"))) {
            throw new RuntimeException(I18nUtils.getMessage("base.check.platform.netside.loadfail"));
        }

        List<ShopNetsiteVo> netSiteList = Lists.newArrayList();

        // 返回的data数据
        JSONObject data = jsonObject.getJSONObject("data");
        if (ObjectUtil.isEmpty(data)) {
            throw new RuntimeException(I18nUtils.getMessage("base.check.platform.netside.loadfail"));
        }

        if (data.containsKey("error_response")) {
            throw new RuntimeException(data.getJSONObject("error_response").getString("sub_msg"));
        }

        JSONObject cainiao_waybill_ii_search_response = data.getJSONObject("cainiao_waybill_ii_search_response");
        JSONObject waybill_apply_subscription_cols = cainiao_waybill_ii_search_response.getJSONObject("waybill_apply_subscription_cols");
        if (waybill_apply_subscription_cols.size() == 0) {
            throw new RuntimeException("店铺" + dto.getCustomer_id() + "下没有" + obj1.getCp_code() + "网点信息");
        }
        JSONObject info = (JSONObject) waybill_apply_subscription_cols.getJSONArray("waybill_apply_subscription_info").get(0);
        if (null == info) {
            throw new RuntimeException("店铺" + dto.getCustomer_id() + "下没有" + obj1.getCp_code() + "网点信息");
        }
        JSONArray accounts = info.getJSONObject("branch_account_cols").getJSONArray("waybill_branch_account");
        for (Object account : accounts) {
            JSONObject acc = (JSONObject) account;
            ShopNetsiteVo vo = new ShopNetsiteVo();
            vo.setAmount(acc.getString("quantity"));
            if (StringUtils.isNotEmpty((String) acc.get("branch_name"))) {
                vo.setShortAddress((String) acc.get("branch_name"));
                vo.setNetsite_name((String) acc.get("branch_name"));
            } else {

            }
            vo.setNetsite_code(acc.getString("branch_code"));
            // 品牌
            vo.setBrandCode(acc.getString("brand_code"));
            JSONArray addresss = acc.getJSONObject("shipp_address_cols").getJSONArray("address_dto");
            //解析月结账号

            Map<String, String> customer_code_map = new HashMap<String, String>();
            if (acc.containsKey("customer_code_map")) {
                String customers = (String) acc.get("customer_code_map");
                customer_code_map = (Map<String, String>) JSONObject.parse(customers);
            }

            List<ReturnorderSenderVO> addList = new ArrayList<>();
            for (Object o : addresss) {
                JSONObject address = (JSONObject) o;
                ReturnorderSenderVO senderVO = new ReturnorderSenderVO();
                senderVO.setProvince(address.getString("province"));
                senderVO.setCity(address.getString("city"));
                senderVO.setArea(address.getString("district"));
                senderVO.setDetailAddress(address.getString("detail"));
                //解析地址ID
                String addressId = address.getString("waybill_address_id");
                senderVO.setAddressId(addressId);
                //存在对应关系获取月结账号，不存在置空
                senderVO.setMonthAccount(customer_code_map.containsKey(addressId) ? customer_code_map.get(addressId) : "");
                addList.add(senderVO);
            }
            vo.setSendAddress(addList);
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
        Assert.notBlank(tbStdTemplateUrl, I18nUtils.getMessage("base.check.platform.stdtemplate.url.notnull"));
        log.info("淘宝 获取标准模板：" + tbStdTemplateUrl);

        log.debug("淘宝 获取标准模板 请求入参为:{}", JSON.toJSONString(reqVo));
        String result = HttpUtil.postForIntegration(null, tbStdTemplateUrl, reqVo.getCustomerId());
        log.debug("淘宝 获取标准模板 接口返回信息：" + result);
        Assert.notBlank(result, I18nUtils.getMessage("base.check.platform.stdtemplate.loadfail"));
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.getBoolean("success") != null && !jsonObject.getBoolean("success")) {
            throw new SystemException(jsonObject.getString("message"));
        }
        JSONObject data = jsonObject.getJSONObject("data");
        if (ObjectUtil.isEmpty(data)) {
            throw new RuntimeException(I18nUtils.getMessage("base.check.platform.stdtemplate.loadfail"));
        }
        if (data.containsKey("error_response")) {
            throw new RuntimeException(data.getJSONObject("error_response").getString("sub_msg"));
        }
        List<BasePlatformPrintTemplateDto> printTemplateDtoList = new ArrayList<>();
        CainiaoCloudprintStdtemplatesGetResponse response = JSON.toJavaObject(data.getJSONObject("cainiao_cloudprint_stdtemplates_get_response"), CainiaoCloudprintStdtemplatesGetResponse.class);
        if (response != null && response.getResult().getSuccess() && response.getResult().getDatas() != null) {
            List<StandardTemplateResult> results = response.getResult().getDatas().getStandard_template_result();
            for (StandardTemplateResult standardTemplateResult : results) {
                for (StandardTemplateDo standardTemplateDo : standardTemplateResult.getStandard_templates().getStandard_template_do()) {
                    BasePlatformPrintTemplateDto printTemplateDto = new BasePlatformPrintTemplateDto();
                    printTemplateDto.setTemplateName(standardTemplateDo.getStandard_template_name());
                    printTemplateDto.setTemplateUrl(standardTemplateDo.getStandard_template_url());
                    printTemplateDto.setTemplateId(String.valueOf(standardTemplateDo.getStandard_template_id()));
                    printTemplateDto.setTemplateType(String.valueOf(standardTemplateDo.getStandard_waybill_type()));
                    printTemplateDto.setType(0);
                    printTemplateDto.setAutoGetFlag(1);
                    printTemplateDto.setPlatformCode(PlatformTypeEnum.TB.name());
                    printTemplateDto.setPlatformName(PlatformTypeEnum.TB.getPlatformName());
                    printTemplateDto.setPlatformLogisticsCode(standardTemplateResult.getCp_code());
                    printTemplateDtoList.add(printTemplateDto);
                }
            }
        }
        return printTemplateDtoList;
    }

}
