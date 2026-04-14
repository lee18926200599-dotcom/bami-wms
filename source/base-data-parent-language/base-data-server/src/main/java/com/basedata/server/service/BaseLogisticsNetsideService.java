package com.basedata.server.service;

import com.basedata.server.dto.*;
import com.github.pagehelper.PageInfo;
import com.basedata.common.dto.UpdateStatusDto;
import com.basedata.server.entity.BaseLogisticsNetside;
import com.basedata.server.query.BaseLogisticsNetsideDetailQuery;
import com.basedata.server.query.BaseLogisticsNetsideQueryVo;

import java.util.List;

/**
 * <p>
 * 承运商网点对应关系 服务类
 * </p>
 */
public interface BaseLogisticsNetsideService {
    /**
     * 新增
     *
     * @param reqDTO
     * @return
     */
    void saveAll(BaseLogisticsNetsideReqDTO reqDTO);

    /**
     * 更新
     *
     * @param reqDTO
     * @return
     */
    void updateAll(BaseLogisticsNetsideReqDTO reqDTO);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    boolean deleteByIds(List<Long> ids);

    /**
     * 批量启用停用主配置
     *
     * @param statusDto
     * @return
     */
    boolean batchEnableOrDisable(UpdateStatusDto statusDto);

    /**
     * 分页查询
     *
     * @param queryVo
     * @return
     */
    PageInfo<BaseLogisticsNetside> queryPageList(BaseLogisticsNetsideQueryVo queryVo);

    /**
     * 查询明细列表（根据配置项主ID查）
     *
     * @param configId
     * @return
     */
    BaseLogisticsNetsideDTO queryDetailList(Long configId);


    List<BaseLogisticsNetsideDetailDTO> queryNetsideDetail(BaseLogisticsNetsideDetailQuery queryVo);

    List<BaseLogisticsDTO> queryBaseLogisticsList(Integer deliveryType);

    List<BaseNetsideDTO> queryBaseNetsideList(Long logisticsId);
}
