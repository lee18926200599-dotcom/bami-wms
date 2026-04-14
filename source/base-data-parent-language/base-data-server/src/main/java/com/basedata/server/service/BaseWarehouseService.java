package com.basedata.server.service;

import com.github.pagehelper.PageInfo;
import com.basedata.common.dto.DeleteDto;
import com.basedata.common.dto.UpdateStatusDto;
import com.basedata.common.dto.warehouse.BaseWarehouseDto;
import com.basedata.common.query.WarehouseQuery;
import com.basedata.common.vo.BaseWarehouseDetailVo;
import com.basedata.common.vo.BaseWarehousePageVo;
import com.basedata.common.vo.WarehouseVo;

import java.util.List;

public interface BaseWarehouseService {

    /**
     * 新增仓库
     *
     * @param warehouseDto
     */
    void save(BaseWarehouseDto warehouseDto);

    /**
     * 修改仓库
     *
     * @param warehouseDto
     */
    void update(BaseWarehouseDto warehouseDto);


    /**
     * 仓库列表
     *
     * @param warehouseQuery
     * @return
     */
    PageInfo<BaseWarehousePageVo> page(WarehouseQuery warehouseQuery);


    /**
     * 获取服务商下所有仓库
     *
     * @return
     */
    List<WarehouseVo> getWarehouse();

    /**
     * 启用禁用
     *
     * @param statusDto
     */
    void updateStatus(UpdateStatusDto statusDto);

    /**
     * 仓库详情
     *
     * @param id
     * @return
     */
    BaseWarehouseDetailVo detail(Long id);

    /**
     * 删除仓库
     *
     * @param deleteDto
     */
    void delete(DeleteDto deleteDto);

    List<BaseWarehouseDto> getWarehouseByOrgId(Long orgId) throws Exception;

    BaseWarehouseDto getWarehouseByCode(String warehouseCode);

    BaseWarehouseDto getWarehouseById(Long id);

    /**
     * 查询仓储信息 -Feign使用
     *
     * @param warehouseQuery
     * @return
     * @throws Exception
     */
    List<BaseWarehouseDto> getWarehouseList(WarehouseQuery warehouseQuery) throws Exception;
}
