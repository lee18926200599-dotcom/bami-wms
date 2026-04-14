package com.basedata.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.basedata.common.dto.BaseStorePrintTemplateReqDTO;
import com.basedata.common.dto.GeneralBatchUpdateByIdsDto;
import com.basedata.server.dto.BaseStorePrintTemplateDetailDTO;
import com.basedata.server.dto.BaseStorePrintTemplateDetailReqDTO;
import com.basedata.server.entity.BaseStorePrintTemplate;
import com.basedata.server.entity.BaseStorePrintTemplateDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 快递面单模版配置 Mapper 接口
 * </p>
 */
@Mapper
public interface BaseStorePrintTemplateMapper extends BaseMapper<BaseStorePrintTemplate> {
    /**
     * 查询主配置表
     *
     * @param reqDTO
     * @return
     */
    List<BaseStorePrintTemplate> findList(BaseStorePrintTemplateReqDTO reqDTO);

    /**
     * 查询明细列表
     *
     * @param detailReqDTO
     * @return
     */
    List<BaseStorePrintTemplateDetailDTO> findDetailList(BaseStorePrintTemplateDetailReqDTO detailReqDTO);

    /**
     * 保存主表信息
     *
     * @param hdr
     */
    void saveHeader(BaseStorePrintTemplate hdr);

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
    void updateDetailById(BaseStorePrintTemplateDetail detailEntity);

    /**
     * 批量保存明细
     *
     * @param addDetailList
     */
    void batchSaveDetail(@Param("addDetailList") List<BaseStorePrintTemplateDetail> addDetailList);
}
