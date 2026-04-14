package com.org.permission.server.domain.base;


import com.basedata.client.feign.DictionaryFeign;
import com.basedata.common.dto.DictionaryItemDto;
import com.basedata.common.query.DictionaryItemQuery;
import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据字典领域服务
 */
@Service(value = "dicDomainService")
public class DicDomainService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DicDomainService.class);

    private static final String BUSINESS_TYPE_DIC_CODE = "BUSSINESS_TYPE";
    private static final String BUSINESS_TYPE_DIC_NAME = "业务类型";

    @Resource
    private DictionaryFeign dictionaryFeign;

    public List<DictionaryItemDto> dicItems(String dicCode, String dicName) {
        LOGGER.info("search dictionary request,dicCode:{},dicName:{}.", dicCode, dicName);
        try {
            DictionaryItemQuery dictionaryItemQuery = new DictionaryItemQuery();
            dictionaryItemQuery.setDictionaryCode(dicCode);
            final RestMessage<List<DictionaryItemDto>> searchResult = dictionaryFeign.queryNoPage(dictionaryItemQuery);
            if (!searchResult.isSuccess()) {
                LOGGER.warn("search dictionary failed,dicCode:{},msg:{}.", dicCode, searchResult.getMessage());
                throw new OrgException(OrgErrorCode.DIC_SYSTEM_ERROR_CODE, "search dictionary exception:" + searchResult.getMessage());
            }
            return searchResult.getData();
        } catch (Exception ex) {
            LOGGER.error("search dictionary error,dicCode:" + dicCode + ",dicName:" + dicName, ex);
            throw new OrgException(OrgErrorCode.DIC_SYSTEM_ERROR_CODE, "search dictionary exception:" + ex.getMessage());
        }
    }



    /**
     * 获取字典项名字
     *
     * @param dicCode  字典编码
     * @param itemCode 项编码
     * @return 项名称
     */
    public String getItemName(String dicCode, String itemCode) {
        LOGGER.info("search dictionary request,dicCode:{},itemCode:{}.", dicCode, itemCode);
        try {
            if (StringUtils.isEmpty(dicCode) || StringUtils.isEmpty(itemCode)) {
                return null;
            }
            final RestMessage<DictionaryItemDto> restMessage = dictionaryFeign.queryDictionaryItemByCode(dicCode, itemCode);
            if (restMessage.isSuccess() && restMessage.getData() != null) {
                return restMessage.getData().getItemName();
            }
            throw new OrgException(OrgErrorCode.DIC_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.base.dictionary.select.fail"));
        } catch (Exception ex) {
            LOGGER.warn("search dictionary error,dicCode:" + dicCode + "itemCode:" + itemCode, ex);
            throw new OrgException(OrgErrorCode.DIC_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.base.dictionary.select.fail"));
        }
    }

    /**
     * 解析一个业务类型ID
     *
     * @param bizTypeId 业务类型ID
     * @return 业务类型名称
     */
    public String parseOneBizTypeId(final String bizTypeId) {
        if (StringUtils.isEmpty(bizTypeId)) {
            return "";
        }

        final List<DictionaryItemDto> dicItems = dicItems(BUSINESS_TYPE_DIC_CODE, BUSINESS_TYPE_DIC_NAME);
        final Map<String, String> itemCodeMap = convertToMap(dicItems);

        StringBuilder sb = new StringBuilder();
        final String[] split = bizTypeId.split(",");
        String bizTypeName;
        for (String itemCode : split) {
            sb.append(",").append(itemCodeMap.get(itemCode));
        }
        bizTypeName = sb.toString().replaceFirst(",", "");
        return bizTypeName;
    }


    /**
     * 将字典项转换为 id : name
     *
     * @param dicItems 字典项集合
     * @return id : name map
     */
    public Map<String, String> convertToMap(final List<DictionaryItemDto> dicItems) {
        if (CollectionUtils.isEmpty(dicItems)) {
            return Collections.emptyMap();
        }
        Map<String, String> idMap = dicItems.stream().collect(Collectors.toMap(DictionaryItemDto::getItemCode, DictionaryItemDto::getItemName, (key1, key2) -> key2));
        return idMap;
    }

    public RestMessage searchByDicCodeAndItemId(Integer itemId, String itemName) {
        String dicCode = "BOSS001";
        return RestMessage.doSuccess(null);
    }
}
