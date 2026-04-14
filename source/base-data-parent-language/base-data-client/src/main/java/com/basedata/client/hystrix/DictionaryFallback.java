package com.basedata.client.hystrix;

import com.basedata.client.feign.DictionaryFeign;
import com.common.util.message.RestMessage;
import com.basedata.common.dto.DictionaryItemDto;
import com.basedata.common.query.BaseDictionaryBatchQuery;
import com.basedata.common.query.BaseDictionaryQuery;
import com.basedata.common.query.DictionaryItemQuery;
import com.basedata.common.vo.BaseBatchDictionaryVo;
import com.basedata.common.vo.BaseDictionaryVo;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@Slf4j
public class DictionaryFallback implements FallbackFactory<DictionaryFeign> {
    @Override
    public DictionaryFeign create(Throwable cause) {
        if (null != cause && StringUtils.hasLength(cause.getMessage())) {
            log.error(cause.getMessage(), cause);
        }

        return new DictionaryFeign() {

            @Override
            public RestMessage<List<BaseDictionaryVo>> queryDictionary(BaseDictionaryQuery dictionaryInfoQuery) {
                return RestMessage.newInstance(false, "【basedata-service|查询字典信息|queryDictionary】请求服务失败！", null);
            }

            @Override
            public RestMessage<List<BaseBatchDictionaryVo>> queryDictionaryList(BaseDictionaryBatchQuery dictionaryInfoQuery) {
                return RestMessage.newInstance(false, "【basedata-service|查询字典信息|queryDictionaryList】请求服务失败！", null);
            }

            @Override
            public RestMessage<DictionaryItemDto> queryDictionaryItemByCode(@RequestParam("dictCode") String dictCode, @RequestParam("itemCode")String itemCode) {
                return RestMessage.newInstance(false, "【basedata-service|查询字典信息|queryDictionaryItemByCode】请求服务失败！", null);
            }

            @Override
            public RestMessage<DictionaryItemDto> queryGroupItemByCode(String dictCode, String itemCode, Long groupId) {
                return RestMessage.newInstance(false, "【basedata-service|查询字典信息|queryGroupItemByCode】请求服务失败！", null);
            }

            @Override
            public RestMessage<DictionaryItemDto> queryGroupDictionaryItemByCode(BaseDictionaryQuery dictionaryQuery) {
                return RestMessage.newInstance(false, "【basedata-service|查询集团字典信息|queryGroupDictionaryItemByCode】请求服务失败！", null);
            }

            @Override
            public RestMessage<List<DictionaryItemDto>> queryNoPage(DictionaryItemQuery dictionaryItemQuery) {
                return RestMessage.newInstance(false, "【basedata-service|查询字典条目信息|queryNoPage】请求服务失败！", null);
            }
        };
    }
}
