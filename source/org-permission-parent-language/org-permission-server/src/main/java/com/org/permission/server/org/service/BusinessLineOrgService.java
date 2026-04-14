package com.org.permission.server.org.service;

import com.org.permission.server.org.dto.BusinessLineOrgDto;

import java.util.List;
import java.util.Map;

public interface BusinessLineOrgService {

    void saveOrUpdate(BusinessLineOrgDto businessLineOrgDto);

    List<BusinessLineOrgDto> getUsableLineByOrg(Long orgId);

    Map<Long,List<Long>> getUsableLineByGroup(Long groupId);
}
