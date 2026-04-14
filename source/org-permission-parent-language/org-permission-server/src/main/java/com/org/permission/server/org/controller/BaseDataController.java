package com.org.permission.server.org.controller;

import com.org.permission.server.org.dto.param.InitCustEntrustRelParam;
import com.common.util.message.RestMessage;
import com.basedata.common.dto.DictionaryItemDto;
import com.org.permission.server.domain.base.DicDomainService;
import com.org.permission.server.domain.crm.CustDomainService;
import com.org.permission.common.dto.crm.CustInfoDomainDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 基础数据控制器
 */
@RestController
@Api(tags = "0基础数据接口文档")
@RequestMapping("base-data")
public class BaseDataController {


    private static final String INDUSTRY_TYPE_DIC_CODE = "FPL_CRM_HYLX";
    private static final String INDUSTRY_TYPE_DIC_NAME = "行业";

    private static final String ENTITY_ATTR_DIC_CODE = "FPL_CRM_STSX";
    private static final String ENTITY_ATTR_DIC_NAME = "实体属性";

    private static final String TAXPAYER_DIC_CODE = "FPL_CRM_NSRLX";
    private static final String TAXPAYER_DIC_NAME = "纳税人类型";

    private static final String BUSINESS_TYPE_DIC_CODE = "FPL_BUSSINESS_TYPE";
    private static final String BUSINESS_TYPE_DIC_NAME = "业务类型";

    private static final String ENTERPRISE_TYPE_DIC_CODE = "FPL_CRM_QYLX";
    private static final String ENTERPRISE_TYPE_DIC_NAME = "企业类型";

    @Resource
    private DicDomainService dicDomainService;

    @Resource
    private CustDomainService custDomainService;

    @ApiOperation(value = "行业类型", httpMethod = "GET")
    @GetMapping(value = "/industryType")
    public RestMessage<List<DictionaryItemDto>> industryType() {
        return getDicItems(INDUSTRY_TYPE_DIC_CODE, INDUSTRY_TYPE_DIC_NAME);
    }

    @ApiOperation(value = "行业类型树", httpMethod = "GET")
    @GetMapping(value = "/industryTypeTree")
    public RestMessage<List<DictionaryItemDto>> industryTypeTree() {
        return getDicItemsTree(INDUSTRY_TYPE_DIC_CODE, INDUSTRY_TYPE_DIC_NAME);
    }

    @ApiOperation(value = "实体属性", httpMethod = "GET")
    @GetMapping(value = "/entityAttr")
    public RestMessage<List<DictionaryItemDto>> entityAttr() {
        return getDicItems(ENTITY_ATTR_DIC_CODE, ENTITY_ATTR_DIC_NAME);
    }

    @ApiOperation(value = "纳税人类型", httpMethod = "GET")
    @GetMapping(value = "/taxpayer")
    public RestMessage<List<DictionaryItemDto>> taxpayer() {
        return getDicItems(TAXPAYER_DIC_CODE, TAXPAYER_DIC_NAME);
    }

    @ApiOperation(value = "企业类型", httpMethod = "GET")
    @GetMapping(value = "/enterpriseType")
    public RestMessage<List<DictionaryItemDto>> enterpriseType() {
        return getDicItems(ENTERPRISE_TYPE_DIC_CODE, ENTERPRISE_TYPE_DIC_NAME);
    }

    private RestMessage<List<DictionaryItemDto>> getDicItems(String dicCode, String dicName) {
        final List<DictionaryItemDto> dicItems = dicDomainService.dicItems(dicCode, dicName);
        return RestMessage.doSuccess(dicItems);
    }

    private RestMessage<List<DictionaryItemDto>> getDicItemsTree(String dicCode, String dicName) {
        List<DictionaryItemDto> dicTree = dicDomainService.dicItems(dicCode, dicName);
        return RestMessage.doSuccess(dicTree);
    }

    @ApiOperation(value = "客户业务类型", httpMethod = "GET")
    @GetMapping(value = "/custBusinessType")
    public RestMessage<List<DictionaryItemDto>> custBusinessType(Long custId) {
        final CustInfoDomainDto custInfoById = custDomainService.getCustInfoById(custId);
        final String businessType = custInfoById.getBizTypeId();
        if (StringUtils.isEmpty(businessType)) {
            return new RestMessage<>();
        }
        final String[] split = businessType.split(",");
        if (split.length == 0) {
            return new RestMessage<>();
        }
        final List<DictionaryItemDto> dicItems = dicDomainService.dicItems(BUSINESS_TYPE_DIC_CODE, BUSINESS_TYPE_DIC_NAME);

        if (CollectionUtils.isEmpty(dicItems)) {
            return new RestMessage<>();
        }

        final List<String> businessTypes = Arrays.asList(split);
        final Iterator<DictionaryItemDto> iterator = dicItems.iterator();
        while (iterator.hasNext()) {
            final DictionaryItemDto nextItem = iterator.next();
            final String itemCode = nextItem.getItemCode();
            if (!businessTypes.contains(itemCode)) {
                iterator.remove();
            }
        }
        return RestMessage.doSuccess(dicItems);
    }

    /**
     * 启动全局委托关系时，触发客商操作
     */
    @ApiOperation(value = "生成全局关系时触发的客商档案生成",httpMethod = "POST")
    @PostMapping(value = "/initCustEntrustRel")
    public RestMessage<Boolean> initCustEntrustRel(@RequestBody InitCustEntrustRelParam param) {
        custDomainService.initCustEntrustRel(param);
        return RestMessage.doSuccess(Boolean.TRUE);
    }
    @ApiOperation(value = "前端行业树模糊查询", httpMethod = "GET")
    @GetMapping(value = "/searchByDicCodeAndItemId")
    public RestMessage<?> searchByDicCodeAndItemId(@RequestParam(value = "itemId", required = false) Integer itemId, @RequestParam(value = "itemName", required = false) String itemName) {
        return dicDomainService.searchByDicCodeAndItemId(itemId, itemName);
    }


}
