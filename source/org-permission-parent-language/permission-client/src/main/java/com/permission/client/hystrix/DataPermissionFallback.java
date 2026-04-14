package com.permission.client.hystrix;

import com.permission.client.feign.DataPermissionFeign;
import com.common.util.message.RestMessage;
import com.org.permission.common.permission.param.SyncDataPermissionParam;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;


@Component
public class DataPermissionFallback implements FallbackFactory<DataPermissionFeign> {
    @Override
    public DataPermissionFeign create(Throwable throwable) {
        return new DataPermissionFeign() {
            @Override
            public RestMessage syncWarehouse(@RequestBody SyncDataPermissionParam param) {
                return RestMessage.newInstance(false, "【org-permission-service|同步仓库数据权限】请求服务失败！", null);
            }

            @Override
            public RestMessage syncSite(Long userId, Long groupId, Long newGroupId, String siteId, String siteName) {
                return RestMessage.newInstance(false, "【org-permission-service|同步站点数据权限】请求服务失败！", null);
            }

            @Override
            public RestMessage syncSiteName(Long userId, Long groupId, String siteId, String siteName) {
                return RestMessage.newInstance(false, "【org-permission-service|同步站点名称数据权限】请求服务失败！", null);
            }

            @Override
            public RestMessage syncCustomer(Long userId, Long groupId, String dataId, String dataName, Long parentId, Integer type) {
                return RestMessage.newInstance(false, "【org-permission-service|同步客商数据权限】请求服务失败！", null);
            }

            @Override
            public RestMessage syncSupplier(Long userId, Long groupId, String supplierId, String supplierName) {
                return RestMessage.newInstance(false, "【org-permission-service|同步供应商数据权限】请求服务失败！", null);
            }

            @Override
            public RestMessage syncRegion(Long userId, String regionId, String gbCode, String regionName, Integer parentId) {
                return RestMessage.newInstance(false, "【org-permission-service|同步地点数据权限】请求服务失败！", null);
            }
        };
    }
}
