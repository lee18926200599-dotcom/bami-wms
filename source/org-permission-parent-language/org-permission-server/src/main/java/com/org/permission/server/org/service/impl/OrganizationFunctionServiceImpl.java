package com.org.permission.server.org.service.impl;

import com.org.permission.server.org.mapper.OrgFunctionMapper;
import com.org.permission.common.org.dto.OrganizationFunctionDto;
import com.org.permission.common.org.param.QueryBizsFuncParam;
import com.org.permission.server.org.service.OrganizationFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component(value = "OrganizationFunctionService")
public class OrganizationFunctionServiceImpl implements OrganizationFunctionService {

    @Autowired
    private OrgFunctionMapper orgFunctionMapper;
   public List<OrganizationFunctionDto> queryBizsFunc(QueryBizsFuncParam reqParam){
       return orgFunctionMapper.queryBizsFunc(reqParam);
   }
}
