package com.basedata.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.basedata.common.dto.BasePlatformNetsideQueryDTO;
import com.basedata.server.entity.BasePlatformNetside;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 平台网点信息 Mapper 接口
 * </p>
 */
@Mapper
public interface BasePlatformNetsideMapper extends BaseMapper<BasePlatformNetside> {
    /**
     * 查询
     *
     * @param queryVo
     * @return
     */
    List<BasePlatformNetside> findList(BasePlatformNetsideQueryDTO queryVo);

    /**
     * 插入数据
     *
     * @param entity
     */
    int insert(BasePlatformNetside entity);

    /**
     * 更新数据
     *
     * @param entity
     */
    long update(BasePlatformNetside entity);
}
