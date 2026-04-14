package com.org.permission.server.org.controller;


import com.basedata.common.dto.warehouse.BaseWarehouseDto;
import com.common.util.message.RestMessage;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.dto.BaseEntrustRelationshipDto;
import com.org.permission.common.dto.BaseOrganizationDto;
import com.org.permission.server.org.vo.BaseCustInfoVO;
import com.org.permission.server.org.vo.BaseEntrustRelationshipVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Api(tags = "业务委托")
@RestController
@RequestMapping("/v1/baseEntrustRelationship")
public class BaseEntrustRelationshipController {

    /**
     * 业务委托新增
     * @param  baseEntrustRelationshipDto
     * @return
     */

    @ApiOperation(value = "业务委托新增")
    @PostMapping("/save")
    public RestMessage save(@RequestBody BaseEntrustRelationshipDto baseEntrustRelationshipDto) {
        try {
            return RestMessage.doSuccess(null);
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }

    /**
     * 业务委托条件查询
     * @param  baseEntrustRelationshipVO
     * @return
     */
    @ApiOperation(value = "业务委托条件查询")
    @PostMapping("/queryPageList")
    public RestMessage<PageInfo<BaseEntrustRelationshipDto>> queryPageList(@RequestBody BaseEntrustRelationshipVO baseEntrustRelationshipVO) {
        return RestMessage.doSuccess(new PageInfo<>());
    }

    /**
     * 业务委托修改
     * @param  baseEntrustRelationshipDto
     * @return
     */
    @ApiOperation(value = "业务委托修改")
    @PostMapping("/update")
    public RestMessage update(@RequestBody BaseEntrustRelationshipDto baseEntrustRelationshipDto) {
        try {
            return RestMessage.doSuccess(1);
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }

    /**
     * 业务委托/启用/停用
     * @param  baseEntrustRelationshipDto
     * @return
     */
    @ApiOperation(value = "业务委托 启用/停用")
    @PostMapping("/enableOrDisable")
    public RestMessage enableOrDisable(@RequestBody BaseEntrustRelationshipDto baseEntrustRelationshipDto) {
        try {
            return RestMessage.doSuccess(1);
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }


    /**
     * 业务委托/启用/停用
     * @param  idz
     * @return
     */
    @ApiOperation(value = "业务委托 删除 ")
    @GetMapping("/batchDelete")
    public RestMessage batchDelete(@RequestParam("idz") String idz) {
        try {
            return RestMessage.doSuccess(1);
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }



    /**
     * 业务委托新增页面货主下拉框
     * @return
     */
    @ApiOperation(value = "业务委托新增页面货主下拉框")
    @PostMapping("/getGoodsOwner")
    public RestMessage<List<BaseCustInfoVO>> getGoodsOwner() {
        try {
            List<BaseCustInfoVO> list = new ArrayList<>();
            return RestMessage.doSuccess(list);
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }

    /**
     * 业务委托新增页面采销组织下拉框
     * @return
     */
    @ApiOperation(value = "业务委托新增页面采销组织下拉框")
    @GetMapping("/getOrganization")
    public RestMessage<List<BaseOrganizationDto>> getOrganization(Long groupId) {
        try {
            List<BaseOrganizationDto> list = new ArrayList<>();
            return RestMessage.doSuccess(list);
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }

    /**
     * 业务委托新增页面仓储服务商下拉框
     * @return
     */
    @ApiOperation(value = "业务委托新增页面仓储服务商下拉框")
    @PostMapping("/getStorageServiceSupplier")
    public RestMessage<List<BaseCustInfoVO>> getStorageServiceSupplier() {
        try {
            List<BaseCustInfoVO> list =new ArrayList<>();
            return RestMessage.doSuccess(list);
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }


    /**
     * 根据  用户权限  和  业务类型  获取相应业务的服务商
     * @return
     */
    @ApiOperation(value = "根据  用户权限  和  业务类型  获取相应业务的服务商")
    @GetMapping("/getServiceSupplierByPermissonAndBusinessType")
    public RestMessage<List<BaseCustInfoVO>> getServiceSupplierByPermissonAndBusinessType(String bussinessType) {
        try {
            List<BaseCustInfoVO> list = new ArrayList<>();
            return RestMessage.doSuccess(list);
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }

    /**
     * 业务委托新增页面仓库存组织下拉框
     * @return
     */
    @ApiOperation(value = "业务委托新增页面仓库存组织下拉框")
    @GetMapping("/getStorageOrgannization")
    public RestMessage<List<BaseOrganizationDto>> getStorageOrgannization(Long groupId) {
        try {
            List<BaseOrganizationDto> list = new ArrayList<>();
            return RestMessage.doSuccess(list);
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }


    /**
     * 业务委托新增页面仓库存组织下拉框
     * @return
     */
    @ApiOperation(value = "业务委托新增页面仓库下拉框")
    @GetMapping("/getWareHouse")
    public RestMessage<List<BaseWarehouseDto>> getWareHouse(Long orgId) {
        try {
            return RestMessage.doSuccess(new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }


}
