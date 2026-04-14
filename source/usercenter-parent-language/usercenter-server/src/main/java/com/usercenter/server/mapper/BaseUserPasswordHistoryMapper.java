package com.usercenter.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.usercenter.server.entity.BaseUserPasswordHistory;
import org.apache.ibatis.annotations.Mapper;

/**
 * 历史密码
 */
@Mapper
public interface BaseUserPasswordHistoryMapper extends BaseMapper<BaseUserPasswordHistory> {
}

