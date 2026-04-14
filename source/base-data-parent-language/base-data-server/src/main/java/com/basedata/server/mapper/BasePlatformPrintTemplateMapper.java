package com.basedata.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.basedata.common.dto.GeneralBatchUpdateByIdsDto;
import com.basedata.server.entity.BasePlatformPrintTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 平台面单模版配置 Mapper 接口
 * </p>
 */
@Mapper
public interface BasePlatformPrintTemplateMapper extends BaseMapper<BasePlatformPrintTemplate> {

    List<BasePlatformPrintTemplate> findTemplateList(BasePlatformPrintTemplate dto);

    int saveTemplate(@Param("list") List<BasePlatformPrintTemplate> templateVOList);

    int batchUpdate(GeneralBatchUpdateByIdsDto updateByIdsDto);
}
