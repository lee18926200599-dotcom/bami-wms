package com.usercenter.server.domain.service;

import com.basedata.client.feign.DictionaryFeign;
import com.basedata.common.dto.DictionaryItemDto;
import com.basedata.common.query.BaseDictionaryBatchQuery;
import com.basedata.common.query.DictionaryItemQuery;
import com.basedata.common.vo.BaseBatchDictionaryVo;
import com.common.base.enums.StateEnum;
import com.common.util.message.RestMessage;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Component(value = "dictionaryDomainService")
public class DictionaryDomainService {
    @Resource
    private DictionaryFeign dictionaryFeign;

    /**
     * 默认查询启用
     *
     * @param dicCode
     * @return
     */
    public List<DictionaryItemDto> getItemList(String dicCode) {
        DictionaryItemQuery dictionaryItemQuery = new DictionaryItemQuery();
        dictionaryItemQuery.setDictionaryCode(dicCode);
        dictionaryItemQuery.setState(StateEnum.ENABLE.getCode());
        RestMessage<List<DictionaryItemDto>> restMessage = dictionaryFeign.queryNoPage(dictionaryItemQuery);
        return restMessage.getData();
    }

    public List<DictionaryItemDto> getItemList(String dicCode, Integer state) {
        DictionaryItemQuery dictionaryItemQuery = new DictionaryItemQuery();
        dictionaryItemQuery.setDictionaryCode(dicCode);
        dictionaryItemQuery.setState(state);
        RestMessage<List<DictionaryItemDto>> restMessage = dictionaryFeign.queryNoPage(dictionaryItemQuery);
        return restMessage.getData();
    }

    public Map<String, String> getItemMap(String dicCode) {
        DictionaryItemQuery dictionaryItemQuery = new DictionaryItemQuery();
        dictionaryItemQuery.setDictionaryCode(dicCode);
        RestMessage<List<DictionaryItemDto>> restMessage = dictionaryFeign.queryNoPage(dictionaryItemQuery);
        return convertToMap(restMessage.getData());
    }

    public Map<String, String> convertToMap(final List<DictionaryItemDto> dicItems) {
        if (CollectionUtils.isEmpty(dicItems)) {
            return Collections.emptyMap();
        }
        Map<String, String> idMap = dicItems.stream().collect(Collectors.toMap(DictionaryItemDto::getItemCode, DictionaryItemDto::getItemName, (key1, key2) -> key2));
        return idMap;
    }

    public List<DictionaryItemDto> getItemList(String dicCode, String itemCode) {
        DictionaryItemQuery dictionaryItemQuery = new DictionaryItemQuery();
        dictionaryItemQuery.setDictionaryCode(dicCode);
        dictionaryItemQuery.setItemCode(itemCode);
        dictionaryItemQuery.setState(StateEnum.ENABLE.getCode());
        RestMessage<List<DictionaryItemDto>> restMessage = dictionaryFeign.queryNoPage(dictionaryItemQuery);
        return restMessage.getData();
    }
    public Map<String, List<BaseBatchDictionaryVo.BaseBatchDictionaryDetailVo>> getDicMap(Map<String, Set<String>> dictMap) {
        Map<String, List<BaseBatchDictionaryVo.BaseBatchDictionaryDetailVo>> map=new HashMap<>();
        BaseDictionaryBatchQuery dictionaryBatchQuery = new BaseDictionaryBatchQuery();
        dictionaryBatchQuery.setDictCodeList(new ArrayList<>(dictMap.keySet()));
        RestMessage<List<BaseBatchDictionaryVo>> restMessage = dictionaryFeign.queryDictionaryList(dictionaryBatchQuery);
        if (restMessage.isSuccess()&& org.apache.commons.collections4.CollectionUtils.isNotEmpty(restMessage.getData())){
            for (BaseBatchDictionaryVo dictionaryVo:restMessage.getData()){
                map.put(dictionaryVo.getDictCode(),dictionaryVo.getDetailVoList());
            }
            return map;
        }
        return new HashMap<>(0);
    }
}
