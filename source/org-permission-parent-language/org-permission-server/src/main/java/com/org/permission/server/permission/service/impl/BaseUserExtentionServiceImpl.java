package com.org.permission.server.permission.service.impl;

import com.org.permission.server.permission.mapper.BaseUserExtentionMapper;
import com.org.permission.server.permission.service.IBaseUserExtentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * base_user_extentionServiceImpl类  用户扩展信息管理
 */
@Service
public class BaseUserExtentionServiceImpl implements IBaseUserExtentionService {
    @Autowired
    private BaseUserExtentionMapper dao;

}

