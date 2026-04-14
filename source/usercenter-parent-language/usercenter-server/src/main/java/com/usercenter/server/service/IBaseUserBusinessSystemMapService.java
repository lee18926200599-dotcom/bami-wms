package com.usercenter.server.service;


import com.usercenter.common.enums.BusinessSystemEnum;
import com.usercenter.common.enums.BusinessSystemMapTypeEnum;
import com.usercenter.server.entity.BaseUserBusinessSystemMap;

import java.util.List;
import java.util.Set;

/**
 * Description: 业务系统-映射关系
 */
public interface IBaseUserBusinessSystemMapService {

    Set<Integer> getMapIdsBySystem(BusinessSystemEnum businessSystemEnum, BusinessSystemMapTypeEnum businessSystemMapType);

    Set<String> getMapNamesBySystem(BusinessSystemEnum system, BusinessSystemMapTypeEnum businessSystemMapType);

    void add(BaseUserBusinessSystemMap baseUserBusinessSystemMap);

    void update(BaseUserBusinessSystemMap baseUserBusinessSystemMap);

    List<BaseUserBusinessSystemMap> getList(BaseUserBusinessSystemMap baseUserBusinessSystemMap);
}

