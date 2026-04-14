package com.basedata.client.hystrix;

import com.common.util.message.RestMessage;
import com.basedata.common.dto.BaseWarehouseCategoryDto;
import com.basedata.common.query.WarehouseCategoryQuery;
import com.basedata.client.feign.BaseWarehouseCategroyFeign;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
@Slf4j
public class BaseWarehouseCategroyFallback implements FallbackFactory<BaseWarehouseCategroyFeign> {
    @Override
    public BaseWarehouseCategroyFeign create(Throwable cause) {
        if (null != cause && StringUtils.hasLength(cause.getMessage())) {
            log.error(cause.getMessage(), cause);
        }

        return new BaseWarehouseCategroyFeign() {
            @Override
            public RestMessage<List<Long>> queryCategoryListByParentId(Long id) {
                return RestMessage.newInstance(false, "【basedata-service|根据仓库分类编号查询子编号集合|queryCategoryListByParentId】请求服务失败！", null);
            }

            @Override
            public RestMessage<BaseWarehouseCategoryDto> queryCategoryById(Long id) {
                return RestMessage.newInstance(false, "【basedata-service|根据仓库分类id查询仓储分类|queryCategoryById】请求服务失败！", null);
            }

            @Override
            public RestMessage<List<BaseWarehouseCategoryDto>> queryCategoryList(WarehouseCategoryQuery query) {
                return RestMessage.newInstance(false, "【basedata-service|根据参数查询仓储分类|queryCategoryList】请求服务失败！", null);
            }

            @Override
            public RestMessage enableOrDisable(BaseWarehouseCategoryDto baseWarehouseCategoryDto) {
                return RestMessage.newInstance(false, "【basedata-service|启停用仓储分类|enableOrDisable】请求服务失败！", null);
            }
        };
    }
}
