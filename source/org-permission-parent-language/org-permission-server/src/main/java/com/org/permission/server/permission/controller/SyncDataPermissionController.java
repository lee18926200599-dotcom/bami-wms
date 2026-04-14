package com.org.permission.server.permission.controller;


import com.common.language.util.I18nUtils;
import com.org.permission.server.permission.enums.DataManagementEnum;
import com.org.permission.server.permission.enums.ValidEnum;
import com.common.util.message.RestMessage;
import com.org.permission.common.permission.param.SyncDataPermissionParam;
import com.org.permission.server.permission.entity.BasePermissionData;
import com.org.permission.server.permission.service.IBasePermissionDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 数据权限对外接口<br>
 */
@Api(tags = "1权限-数据权限对外接口文档")
@RequestMapping(value = "sync-data-permission")
@RestController
public class SyncDataPermissionController {

    private final static Logger LOG = LoggerFactory.getLogger(SyncDataPermissionController.class);

    @Autowired
    private IBasePermissionDataService basePermissionDataService;


    @ApiOperation(value = "同步数据权限-仓库")
    @PostMapping(value = "/data/syncWarehouse")
    public RestMessage syncWarehouse(@RequestBody SyncDataPermissionParam param) {
        return basePermissionDataService.syncwarehouse(param);
    }

    //站点同步 仓库数据权限
    @ApiOperation(value = "仓库数据权限")
    @PostMapping(value = "/data/site")
    public RestMessage site(@RequestParam Long userId, @RequestParam Long groupId, @RequestParam(value = "newGroupId", required = false) Long newGroupId, @RequestParam String siteId, @RequestParam String siteName) {
        return basePermissionDataService.site(userId, groupId, siteId, siteName, newGroupId);
    }

    //站点名称同步
    @ApiOperation(value = "")
    @PostMapping(value = "/data/siteName")
    public RestMessage siteName(@RequestParam Long userId, @RequestParam Long groupId, @RequestParam String siteId, @RequestParam String siteName) {
        return basePermissionDataService.siteName(userId, groupId, siteId, siteName);
    }


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
    @ApiOperation(value = "")
    @PostMapping(value = "/data/customer")
    public RestMessage syncCustomer(@RequestParam Long userId, @RequestParam Long groupId, @RequestParam String dataId, @RequestParam String dataName, @RequestParam Long parentId, @RequestParam Integer type) {
        return basePermissionDataService.syncCustomer(userId, groupId, dataId, dataName, parentId, type);
    }

    /**
     * 同步供应商
     *
     * @param userId       操作人id
     * @param groupId      集团id
     * @param supplierId   供应商id
     * @param supplierName 供应商名称
     * @return
     */
    @ApiOperation(value = "同步供应商")
    @PostMapping(value = "data/supplier")
    public RestMessage syncSupplier(@RequestParam Long userId, @RequestParam Long groupId, @RequestParam String supplierId, @RequestParam String supplierName) {
        return basePermissionDataService.syncSupplier(userId, groupId, supplierId, supplierName);
    }

    /**
     * @param userId     操作人id
     * @param regionId   地点id
     * @param gbCode     国标码
     * @param regionName 地点名称
     * @param parentId   上级关联id，根节点传 -1 没有级联关系的也传 -1
     * @return
     */
    @ApiOperation(value = "")
    @PostMapping(value = "data/region")
    public RestMessage syncRegion(@RequestParam Long userId, @RequestParam String regionId, @RequestParam String gbCode, @RequestParam String regionName, @RequestParam Integer parentId) {
        int managementId = DataManagementEnum.REGION.getCode();
        String distributionType = "按个体";
        BasePermissionData bpd = new BasePermissionData();
        bpd.setDataResourceId(regionId);
        bpd.setManagementId(managementId);
        List<BasePermissionData> list = basePermissionDataService.getListBasePermissionDatasByPOJO(bpd);

        BasePermissionData basePermissionData = new BasePermissionData();
        basePermissionData.setManagementId(managementId);
        basePermissionData.setDistributionType(distributionType);
        basePermissionData.setGbCode(gbCode);
        basePermissionData.setDataResourceId(regionId);
        basePermissionData.setDataResource(regionName);
        basePermissionData.setOptionPermission(2);//1:查询 2:查询和维护
        if ((parentId == null ? 0 : parentId.intValue()) == 0) {
            basePermissionData.setParentId(211L);//国家根节点
        } else {
            BasePermissionData bpdParam = new BasePermissionData();
            bpdParam.setManagementId(managementId);
            bpdParam.setDataResourceId(parentId + "");
            List<BasePermissionData> bpds = basePermissionDataService.getListBasePermissionDatasByPOJO(bpd);
            for (BasePermissionData b : bpds) {
                if (b.getParentId() != -1) {
                    bpd = b;
                }
            }
            basePermissionData.setParentId(bpd.getId());
        }
        basePermissionData.setCreatedDate(new Date());
        basePermissionData.setCreatedBy(userId);
        basePermissionData.setModifiedDate(new Date());
        basePermissionData.setModifiedBy(userId);
        basePermissionData.setState(ValidEnum.YES.getCode());
        if (list.size() > 0) {
            basePermissionData.setId(list.get(0).getId());
            basePermissionDataService.updateBasePermissionData(basePermissionData);
        } else {
            basePermissionDataService.addBasePermissionData(basePermissionData);
        }
        return RestMessage.doSuccess(I18nUtils.getMessage("org.common.success"));
    }

    @ApiOperation(value = "同步数据权限-业务线")
    @PostMapping(value = "/data/syncBusinessLine")
    public RestMessage syncBusinessLine(@RequestBody SyncDataPermissionParam param) {
        return basePermissionDataService.syncBusinessLine(param);
    }
}
