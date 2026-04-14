package com.basedata.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.basedata.common.query.AreaInfoQuery;
import com.basedata.server.entity.AreaInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 *  行政区域表
 */
@Mapper
public interface AreaInfoMapper extends BaseMapper<AreaInfo> {

    int updateById(AreaInfo areaInfo);

    List<AreaInfo> selectByParentIdList(AreaInfoQuery query);
}
