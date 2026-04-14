package com.basedata.client.hystrix;

import com.common.util.message.RestMessage;
import com.basedata.common.dto.UpdateStatusDto;
import com.basedata.common.dto.warehouse.BaseWarehouseDto;
import com.basedata.common.query.WarehouseQuery;
import com.basedata.client.feign.WareHouseFeign;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
@Slf4j
public class WareHouseFallback implements FallbackFactory<WareHouseFeign> {

    @Override
    public WareHouseFeign create(Throwable cause) {
        if (null != cause && StringUtils.hasLength(cause.getMessage())) {
            log.error(cause.getMessage(), cause);
        }

        return new WareHouseFeign() {

            @Override
            public RestMessage<List<BaseWarehouseDto>> getWarehouseByOrgId(Long orgId) {
                return RestMessage.newInstance(false, "【basedata-service|根据组织id查询仓库列表|queryDictionary】请求服务失败！", null);
            }

            @Override
            public RestMessage<BaseWarehouseDto> getWarehouseByCode(String warehouseCode) {
                return RestMessage.newInstance(false, "【basedata-service|根据 仓库编号查询仓库信息|warehouseCode】请求服务失败！", null);
            }

            @Override
            public RestMessage<BaseWarehouseDto> getWarehouseById(Long id) {
                return RestMessage.newInstance(false, "【basedata-service|根据 仓库id查询仓库信息|getWarehouseById】请求服务失败！", null);
            }

            @Override
            public RestMessage<List<BaseWarehouseDto>> getWarehouseList(WarehouseQuery warehouseQuery) {
                return RestMessage.newInstance(false, "【basedata-service|根据条件查询仓库信息|getWarehouseList】请求服务失败！", null);
            }

            @Override
            public RestMessage updateStatus(UpdateStatusDto updateStatusDto) {
                return RestMessage.newInstance(false, "【basedata-service|根据更新仓库信息|updateStatus】请求服务失败！", null);
            }
        };
    }
}
