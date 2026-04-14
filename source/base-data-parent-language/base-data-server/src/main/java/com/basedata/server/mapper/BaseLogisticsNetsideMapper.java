package com.basedata.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.basedata.common.dto.GeneralBatchUpdateByIdsDto;
import com.basedata.server.dto.BaseLogisticsNetsideDetailDTO;
import com.basedata.server.dto.BaseLogisticsNetsideDetailReqDTO;
import com.basedata.server.dto.BaseLogisticsNetsideQueryReqDTO;
import com.basedata.server.entity.BaseLogisticsNetside;
import com.basedata.server.entity.BaseLogisticsNetsideDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 承运商网点对应关系 Mapper 接口
 * </p>
 */
@Mapper
public interface BaseLogisticsNetsideMapper extends BaseMapper<BaseLogisticsNetside> {
    /**
     * 查询主配置表
     *
     * @param queryReqDTO
     * @return
     */
    List<BaseLogisticsNetside> findList(BaseLogisticsNetsideQueryReqDTO queryReqDTO);

    /**
     * 查询明细列表
     *
     * @param detailReqDTO
     * @return
     */
    List<BaseLogisticsNetsideDetailDTO> findDetailList(BaseLogisticsNetsideDetailReqDTO detailReqDTO);

    /**
     * 保存主表信息
     *
     * @param hdr
     */
    void saveHeader(BaseLogisticsNetside hdr);

    /**
     * 批量更新操作
     *
     * @param updateByIdsDto
     * @return
     */
    int batchUpdate(GeneralBatchUpdateByIdsDto updateByIdsDto);

    /**
     * 更新明细
     *
     * @param detailEntity
     */
    void updateDetailById(BaseLogisticsNetsideDetail detailEntity);

    /**
     * 批量保存明细
     *
     * @param addDetailList
     */
    void batchSaveDetail(@Param("addDetailList") List<BaseLogisticsNetsideDetail> addDetailList);

}
