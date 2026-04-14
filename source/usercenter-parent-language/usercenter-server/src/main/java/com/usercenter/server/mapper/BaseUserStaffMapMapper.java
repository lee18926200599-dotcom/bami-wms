package com.usercenter.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.usercenter.server.entity.BaseUserStaffMap;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BaseUserStaffMapMapper extends BaseMapper<BaseUserStaffMap> {

    /**
     * 同步用户和人员的映射
     * @param dateNumber
     */
    void syncUserStaffMap(String dateNumber);
}
