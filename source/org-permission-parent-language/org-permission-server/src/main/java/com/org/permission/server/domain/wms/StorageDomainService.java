package com.org.permission.server.domain.wms;

import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.basedata.common.dto.warehouse.BaseWarehouseDto;
import com.basedata.common.query.WarehouseQuery;
import com.basedata.client.feign.WareHouseFeign;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.common.base.enums.StateEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 仓储领域服务
 */
@Component("storageDomainService")
public class StorageDomainService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageDomainService.class);


    @Autowired
    private WareHouseFeign wareHouseFeign;

    /**
     * 查询库存组织可用仓库
     *
     * @param stockOrgId 库存组织ID
     * @return 库存组织下的仓库
     */
    public List<OrgWarehouseDto> queryStockOrgWarehouses(Long stockOrgId) {
        WarehouseQuery warehouseQuery = new WarehouseQuery();
        warehouseQuery.setServiceProviderId(stockOrgId);
        warehouseQuery.setState(StateEnum.ENABLE.getCode());
        RestMessage<List<BaseWarehouseDto>> restMessage = wareHouseFeign.getWarehouseList(warehouseQuery);
        if (restMessage == null || CollectionUtils.isEmpty(restMessage.getData())) {
            return new ArrayList<>();
        }
        List<OrgWarehouseDto> list = new ArrayList<>();
        for (BaseWarehouseDto baseWarehouseDto : restMessage.getData()) {
            OrgWarehouseDto orgWarehouseDto = new OrgWarehouseDto();
            orgWarehouseDto.setWarehouseId(baseWarehouseDto.getId());
            orgWarehouseDto.setWarehouseCode(baseWarehouseDto.getWarehouseCode());
            orgWarehouseDto.setWarehouseName(baseWarehouseDto.getWarehouseName());
            list.add(orgWarehouseDto);
        }
        return list;
    }

    /**
     * 批量获取仓库的名称
     *
     * @param warehouseIds 仓库ID集合
     */
    public Map<Long, String> batchQueryWarehouseNameByIds(final Set<Long> warehouseIds) {

        try {
            LOGGER.info("batch query warehouse info ,warehouseIds:{}.", warehouseIds);
            if (CollectionUtils.isEmpty(warehouseIds)) {
                return Collections.emptyMap();
            }
            WarehouseQuery warehouseQuery = new WarehouseQuery();
            warehouseQuery.setWarehouseIdList(new ArrayList<>(warehouseIds));
            List<BaseWarehouseDto> list = getWarehouseList(warehouseQuery);
            if (CollectionUtils.isEmpty(list)) {
                return Collections.emptyMap();
            }
            return list.stream().collect(Collectors.toMap(BaseWarehouseDto::getId, BaseWarehouseDto::getWarehouseName));
        } catch (Exception ex) {
            LOGGER.error("batch query wms info error.", ex);
            throw new OrgException(OrgErrorCode.WMS_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.wms.select.warehouse.exception"));
        }
    }

    public Long get(Long warehouseId) {
        LOGGER.info("根据仓库id查询库存组织，仓库id{}", warehouseId);
        if (ObjectUtils.isEmpty(warehouseId)) {
            return null;
        }
        try {
            WarehouseQuery warehouseQuery = new WarehouseQuery();
            warehouseQuery.setWarehouseId(warehouseId);
            List<BaseWarehouseDto> list = getWarehouseList(warehouseQuery);
            if (CollectionUtils.isNotEmpty(list)) {
                return list.get(0).getServiceProviderId();
            }
        } catch (Exception e) {
            LOGGER.error("batch query crm info error.", e);
            throw new OrgException(OrgErrorCode.WMS_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.wms.select.warehouse.exception"));
        }
        return null;
    }

    /**
     * 根据名字模糊匹配仓库id
     *
     * @param warehouseName
     * @return
     */
    public Map<Long, String> getListLikeWarehousename(String warehouseName) {
        if (StringUtils.isBlank(warehouseName)) {
            Collections.emptyMap();
        }
        WarehouseQuery warehouseQuery = new WarehouseQuery();
        warehouseQuery.setWarehouseName(warehouseName);
        List<BaseWarehouseDto> list = getWarehouseList(warehouseQuery);
        return list.stream().collect(Collectors.toMap(BaseWarehouseDto::getId, BaseWarehouseDto::getWarehouseName, (v1, v2) -> v2));
    }

    /**
     * 根据库存组织ids查询仓库id集合
     *
     * @param ids
     * @return
     */
    public List<Long> getListByStockOrgIDList(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            Collections.emptyList();
        }
        WarehouseQuery warehouseQuery = new WarehouseQuery();
        warehouseQuery.setServiceProviderIds(ids);
        List<BaseWarehouseDto> list = getWarehouseList(warehouseQuery);
        return list.stream().map(BaseWarehouseDto::getId).collect(Collectors.toList());
    }

    private List<BaseWarehouseDto> getWarehouseList(WarehouseQuery warehouseQuery) {
        if (warehouseQuery == null) {
            Collections.emptyList();
        }
        RestMessage<List<BaseWarehouseDto>> restMessage = wareHouseFeign.getWarehouseList(warehouseQuery);
        if (restMessage == null || CollectionUtils.isEmpty(restMessage.getData())) {
            return Collections.emptyList();
        }
        return restMessage.getData();
    }
}
