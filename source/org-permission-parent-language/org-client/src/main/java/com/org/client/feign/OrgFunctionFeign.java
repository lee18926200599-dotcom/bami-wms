package com.org.client.feign;


import com.common.util.message.RestMessage;
import com.org.permission.common.org.dto.OrganizationFunctionDto;
import com.org.permission.common.org.param.QueryBizsFuncParam;
import com.org.permission.common.util.Constant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = Constant.ORG_SERVER_NAME)
public interface OrgFunctionFeign {
    @PostMapping(value = "/org-function/queryBizsFunc")
    RestMessage<List<OrganizationFunctionDto>> queryBizsFunc(@RequestBody QueryBizsFuncParam reqParam);
}
