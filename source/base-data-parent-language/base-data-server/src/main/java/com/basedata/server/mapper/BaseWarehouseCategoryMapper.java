package com.basedata.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.basedata.server.entity.BaseWarehouseCategory;

import com.basedata.common.dto.BaseWarehouseCategoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 仓储分类
 */
@Mapper
public interface BaseWarehouseCategoryMapper extends BaseMapper<BaseWarehouseCategory> {

    int updateById(BaseWarehouseCategory baseWarehouseCategory);

    Long updateList(List<BaseWarehouseCategoryDto> dtoList);
}
