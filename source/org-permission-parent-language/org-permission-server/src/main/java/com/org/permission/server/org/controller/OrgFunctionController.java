package com.org.permission.server.org.controller;

import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.common.org.dto.OrganizationFunctionDto;
import com.org.permission.common.org.param.QueryBizsFuncParam;
import com.org.permission.server.org.service.OrganizationFunctionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 组织职能管理控制器
 */
@RestController
@Api(tags = "0组织职能接口文档")
@RequestMapping("org-function")
public class OrgFunctionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrgFunctionController.class);

    @Autowired
    private OrganizationFunctionService organizationFunctionService;

    @ApiOperation(value = " 查询组织职能信息", httpMethod = "POST")
    @PostMapping(value = "/queryBizsFunc")
    public RestMessage<List<OrganizationFunctionDto>> queryBizsFunc(@RequestBody QueryBizsFuncParam reqParam) {
        LOGGER.info("queryBizWithFunc : {}.", reqParam);
        try {
            final List<OrganizationFunctionDto> list = organizationFunctionService.queryBizsFunc(reqParam);
            return RestMessage.doSuccess(list);
        } catch (Exception ex) {
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

}
