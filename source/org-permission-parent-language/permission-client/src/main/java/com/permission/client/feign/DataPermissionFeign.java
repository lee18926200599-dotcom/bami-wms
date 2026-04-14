package com.permission.client.feign;


import com.common.util.message.RestMessage;
import com.org.permission.common.permission.param.SyncDataPermissionParam;
import com.org.permission.common.util.Constant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 数据权限同步接口
 */
@FeignClient(value = Constant.ORG_SERVER_NAME)
public interface DataPermissionFeign {


    /**
     * wms同步 仓库数据权限
     *
     * @param param userId        用户id
     *              orgId         仓库所属组织id
     *              groupId       仓库所属集团id
     *              warehouseId   仓库id
     *              warehouseName 仓库名称
     *              state         启用 停用状态(1:启用，2：停用)
     * @return
     */
    @RequestMapping(value = "/sync-data-permission/data/syncWarehouse", method = RequestMethod.POST)
    public RestMessage syncWarehouse(@RequestBody SyncDataPermissionParam param);


    /**
     * 站点同步 仓库数据权限
     *
     * @param userId   用户id
     * @param siteId   站点id
     * @param siteName 站点名称
     * @param groupId  集团id
     * @return
     */
    @RequestMapping(value = "/sync-data-permission/data/site", method = RequestMethod.POST)
    public RestMessage syncSite(@RequestParam(value = "userId") Long userId, @RequestParam(value = "groupId") Long groupId, @RequestParam(value = "newGroupId", required = false) Long newGroupId, @RequestParam(value = "siteId") String siteId, @RequestParam(value = "siteName") String siteName);


    /**
     * 站点名称同步 仓库数据权限
     *
     * @param userId   用户id
     * @param siteId   站点id
     * @param siteName 站点名称
     * @param groupId  集团id
     * @return
     */
    @RequestMapping(value = "/sync-data-permission/data/siteName", method = RequestMethod.POST)
    public RestMessage syncSiteName(@RequestParam(value = "userId") Long userId, @RequestParam(value = "groupId") Long groupId, @RequestParam(value = "siteId") String siteId, @RequestParam(value = "siteName") String siteName);

    /**
     * 客户同步
     *
     * @param userId   操作用户id
     * @param groupId  集团id
     * @param dataId   数据id(客户id 或者 区域id 或者 业务类型id 根据类型定)
     * @param dataName 数据名称(客户名称 或者 区域名称 或者 业务类型名称 根据类型定)
     * @param type     （1：客户,2:区域,3:业务类型）
     * @param parentId 上级关联id，根节点传 -1 没有级联关系的也传 -1
     * @return
     */
    @RequestMapping(value = "/sync-data-permission/data/customer", method = RequestMethod.POST)
    public RestMessage syncCustomer(@RequestParam(value = "userId") Long userId, @RequestParam(value = "groupId") Long groupId, @RequestParam(value = "dataId") String dataId, @RequestParam(value = "dataName") String dataName, @RequestParam(value = "parentId") Long parentId, @RequestParam(value = "type") Integer type);

    /**
     * 同步供应商
     *
     * @param userId       操作人id
     * @param groupId      集团id
     * @param supplierId   供应商id
     * @param supplierName 供应商名称
     * @return
     */
    @RequestMapping(value = "/sync-data-permission/data/supplier", method = RequestMethod.POST)
    public RestMessage syncSupplier(@RequestParam(value = "userId") Long userId, @RequestParam(value = "groupId") Long groupId, @RequestParam(value = "supplierId") String supplierId, @RequestParam(value = "supplierName") String supplierName);

    /**
     * @param userId     操作人id
     * @param regionId   地点id(主键id)
     * @param gbCode     (国标码）
     * @param regionName 地点名称
     * @param parentId   上级关联id，根节点传 -1 没有级联关系的也传 -1
     * @return
     */
    @RequestMapping(value = "/sync-data-permission/data/region", method = RequestMethod.POST)
    public RestMessage syncRegion(@RequestParam(value = "userId") Long userId, @RequestParam(value = "regionId") String regionId, @RequestParam(value = "gbCode") String gbCode, @RequestParam(value = "regionName") String regionName, @RequestParam(value = "parentId") Integer parentId);
}
