package com.basedata.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.basedata.common.dto.BasePlatformLogisticsDetailDTO;
import com.basedata.common.dto.GeneralBatchUpdateByIdsDto;
import com.basedata.common.dto.PlatformLogisticsRelationshipDTO;
import com.basedata.common.dto.PlatformLogisticsRelationshipReqDTO;
import com.basedata.common.query.LogisticsDetailQuery;
import com.basedata.server.dto.BasePlatformLogisticsDetailBatchUpdateDto;
import com.basedata.server.dto.BasePlatformLogisticsDetailReqDTO;
import com.basedata.server.dto.BasePlatformLogisticsReqDTO;
import com.basedata.server.entity.BasePlatformLogistics;
import com.basedata.server.entity.BasePlatformLogisticsDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 承运商编码对照关系表 Mapper 接口
 * </p>
 */
@Mapper
public interface BasePlatformLogisticsMapper extends BaseMapper<BasePlatformLogistics> {
    /**
     * 查询主配置表
     *
     * @param reqDTO
     * @return
     */
    List<BasePlatformLogistics> findList(BasePlatformLogisticsReqDTO reqDTO);

    /**
     * 查询明细配置
     *
     * @param detailReqDTO
     * @return
     */
    List<BasePlatformLogisticsDetail> findDetailList(BasePlatformLogisticsDetailReqDTO detailReqDTO);

    /**
     * 更新明细
     *
     * @param entity
     */
    void updateDetailById(BasePlatformLogisticsDetail entity);

    /**
     * 批量保存明细
     *
     * @param addDetailList
     */
    void batchSaveDetail(@Param("addDetailList") List<BasePlatformLogisticsDetail> addDetailList);

    /**
     * 保存主表信息
     *
     * @param hdr
     */
    void saveHeader(BasePlatformLogistics hdr);

    /**
     * 批量更新操作
     *
     * @param updateByIdsDto
     * @return
     */
    int batchUpdate(GeneralBatchUpdateByIdsDto updateByIdsDto);

    /**
     * 查询承运商编码
     *
     * @param query
     * @return
     */
    List<BasePlatformLogisticsDetailDTO> queryLogisticsByCondition(LogisticsDetailQuery query);

    /**
     * 查询电商平台下的承运商编码
     *
     * @param relationshipReqDTO
     * @return
     */
    List<PlatformLogisticsRelationshipDTO> queryPlatformLogistics(PlatformLogisticsRelationshipReqDTO relationshipReqDTO);

    /**
     * 根据主表配置ID  批量更新 运单号与面单获取 明细表数据
     *
     * @param batchUpdateDto
     * @return
     */
    int batchUpdateDetail(BasePlatformLogisticsDetailBatchUpdateDto batchUpdateDto);
}
