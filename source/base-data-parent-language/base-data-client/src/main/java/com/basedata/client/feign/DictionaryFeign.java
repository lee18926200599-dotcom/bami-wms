package com.basedata.client.feign;

import com.common.util.message.RestMessage;
import com.basedata.common.dto.DictionaryItemDto;
import com.basedata.common.query.BaseDictionaryBatchQuery;
import com.basedata.common.query.BaseDictionaryQuery;
import com.basedata.common.query.DictionaryItemQuery;
import com.basedata.common.vo.BaseBatchDictionaryVo;
import com.basedata.common.vo.BaseDictionaryVo;
import com.basedata.client.hystrix.DictionaryFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "${zzz.services.basedata.name}", url = "${zzz.services.basedata.url}",
        fallbackFactory = DictionaryFallback.class)
public interface DictionaryFeign {

    @PostMapping("/dictionaryInfo/common/query")
    RestMessage<List<BaseDictionaryVo>> queryDictionary(@RequestBody BaseDictionaryQuery dictionaryInfoQuery);


    @PostMapping("/dictionaryInfo/common/queryList")
    RestMessage<List<BaseBatchDictionaryVo>> queryDictionaryList(@RequestBody BaseDictionaryBatchQuery dictionaryInfoQuery);

    /**
     * 此方法过期，建议替换为：queryGroupDictionaryItemByCode
     * @param dictCode
     * @param itemCode
     * @return
     */
    @Deprecated
    @GetMapping("/v1/dictionaryItem/queryDictionaryItemByCode")
    RestMessage<DictionaryItemDto> queryDictionaryItemByCode(@RequestParam("dictCode") String dictCode, @RequestParam("itemCode") String itemCode);
    @GetMapping("/dictionary-item-group/queryGroupItemByCode")
    RestMessage<DictionaryItemDto> queryGroupItemByCode(@RequestParam("dictCode") String dictCode, @RequestParam("itemCode") String itemCode, @RequestParam(value = "groupId", required = false, defaultValue = "0") Long groupId);

    @PostMapping("/dictionary-item-group/queryDictionaryItemByCode")
    RestMessage<DictionaryItemDto> queryGroupDictionaryItemByCode(@RequestBody BaseDictionaryQuery dictionaryQuery);

    @PostMapping("/dictionary-item-group/queryNoPage")
    RestMessage<List<DictionaryItemDto>> queryNoPage(@RequestBody DictionaryItemQuery dictionaryItemQuery);
}
