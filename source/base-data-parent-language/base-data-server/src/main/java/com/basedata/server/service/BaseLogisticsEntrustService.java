package com.basedata.server.service;

import com.github.pagehelper.PageInfo;
import com.basedata.common.dto.BaseLogisticsEntrustDto;
import com.basedata.common.dto.DeleteDto;
import com.basedata.common.dto.UpdateStatusDto;
import com.basedata.common.query.LogisticsEntrustQuery;
import com.basedata.common.vo.BaseLogisticsEntrustDetailVo;
import com.basedata.common.vo.BaseLogisticsEntrustVo;
import com.basedata.server.query.BaseLogisticsEntrustQuery;

public interface BaseLogisticsEntrustService  {

    /**
     * 新增
     *
     * @param baseLogisticsEntrustDto
     */
    void save(BaseLogisticsEntrustDto baseLogisticsEntrustDto);

    /**
     * 修改
     *
     * @param baseLogisticsEntrustDto
     */
    void update(BaseLogisticsEntrustDto baseLogisticsEntrustDto);


    /**
     * 列表
     *
     * @param query
     * @return
     */
    PageInfo<BaseLogisticsEntrustVo> page(BaseLogisticsEntrustQuery query);

    /**
     * 启用禁用
     *
     * @param statusDto
     */
    void updateStatus(UpdateStatusDto statusDto);

    /**
     * 详情
     *
     * @param id
     * @return
     */
    BaseLogisticsEntrustDetailVo detail(Long id);

    /**
     * 删除
     *
     * @param deleteDto
     */
    void delete(DeleteDto deleteDto);

    BaseLogisticsEntrustDto queryByCondition(LogisticsEntrustQuery logisticsEntrustQuery);
}
