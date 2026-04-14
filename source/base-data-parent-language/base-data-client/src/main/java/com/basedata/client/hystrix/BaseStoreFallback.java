package com.basedata.client.hystrix;

import com.common.util.message.RestMessage;
import com.basedata.common.dto.FacesheetConfigDto;
import com.basedata.common.dto.QueryStoreInfoDto;
import com.basedata.common.vo.FacesheetConfigForPrintVo;
import com.basedata.common.vo.FacesheetConfigVo;
import com.basedata.client.feign.BaseStoreFeign;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import java.util.List;

@Component
@Slf4j
public class BaseStoreFallback implements FallbackFactory<BaseStoreFeign> {

    @Override
    public BaseStoreFeign create(Throwable throwable) {
        if (null != throwable && StringUtils.hasLength(throwable.getMessage())) {
            log.error(throwable.getMessage(), throwable);
        }
        return new BaseStoreFeign() {
            @Override
            public RestMessage<FacesheetConfigVo> getConfig(FacesheetConfigDto configDto) {
                return RestMessage.newInstance(false, "【basedata-service|查询店铺信息|getConfig】服务请求失败！", null);
            }

            @Override
            public RestMessage<List<FacesheetConfigForPrintVo>> getBatchConfig(@Valid QueryStoreInfoDto queryStoreInfoDto) {
                return RestMessage.newInstance(false, "【basedata-service|查询店铺信息|getBatchConfig】服务请求失败！", null);
            }
        };
    }
}
