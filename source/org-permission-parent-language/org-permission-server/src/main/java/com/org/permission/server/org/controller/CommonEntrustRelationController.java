package com.org.permission.server.org.controller;

import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.org.permission.common.org.dto.EntrustRelationOrgInfoDto;
import com.org.permission.common.org.param.KeyOperateParam;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.service.CommonEntrustRelationService;
import com.org.permission.server.org.validator.DeleteOperateReqParamValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务委托关系控制器
 */
@RestController
@Api(tags = "0通用业务委托关系接口文档")
@RequestMapping("common-entrust")
public class CommonEntrustRelationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonEntrustRelationController.class);
    @Resource
    private DeleteOperateReqParamValidator deleteOperateReqParamValidator;
    @Resource
    private CommonEntrustRelationService commonEntrustRelationService;

    @ApiOperation(value = "删除业务委托关系", httpMethod = "POST")
    @PostMapping(value = "/deleteEntrustRelation")
    public RestMessage<Boolean> deleteEntrustRelation(@RequestBody KeyOperateParam reqParam) {
        LOGGER.info("delete entrust relation by id req param : {}.", reqParam);
        try {
            deleteOperateReqParamValidator.validate(reqParam);
            commonEntrustRelationService.deleteEntrustRelation(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("delete entrust relationship failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("delete entrust relationship error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    /**
     * 通过采销组织采销委托关系信息
     *
     * @param purchaseSaleOrgId
     * @return
     */
    @ApiOperation(value = "通过采销组织采销委托关系信息", httpMethod = "GET")
    @GetMapping(value = "/queryPurchaseErByPurchaseSaleOrgId")
    public RestMessage<List<EntrustRelationOrgInfoDto>> queryPurchaseErByPurchaseSaleOrgId(@RequestParam("purchaseSaleOrgId") Long purchaseSaleOrgId) {
        List<EntrustRelationOrgInfoDto> orgInfoDtos = commonEntrustRelationService.queryPurchaseEntrustRelation(purchaseSaleOrgId);
        return RestMessage.doSuccess(orgInfoDtos);
    }
}

