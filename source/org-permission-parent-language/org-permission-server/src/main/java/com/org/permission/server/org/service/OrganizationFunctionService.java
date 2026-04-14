package com.org.permission.server.org.service;


import com.org.permission.common.org.dto.OrganizationFunctionDto;
import com.org.permission.common.org.param.QueryBizsFuncParam;

import java.util.List;

public interface OrganizationFunctionService {
    List<OrganizationFunctionDto> queryBizsFunc(QueryBizsFuncParam reqParam);
}
