package com.org.permission.server.org.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.FinanceEntrustRelationInfoDto;
import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.common.org.param.QueryByIdReqParam;
import com.org.permission.common.org.param.QueryFinanceEntrustRelationByUserPermissionParam;
import com.org.permission.common.org.param.QueryFinanceEntrustRelationParam;
import com.org.permission.server.domain.user.UserDomainService;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.FinanceEntrustRelationBean;
import com.org.permission.server.org.bean.FinanceEntrustRelationInfoBean;
import com.org.permission.server.org.dto.param.FinanceEntrustRelationParam;
import com.org.permission.server.org.dto.param.FinanceEntrustRelationReqParam;
import com.org.permission.server.org.enums.EntrustRangeEnum;
import com.org.permission.server.org.enums.EntrustTypeEnum;
import com.org.permission.server.org.service.FinanceEntrustRelationService;
import com.org.permission.server.org.util.OrgBeanUtil;
import com.org.permission.server.org.util.PageUtil;
import com.org.permission.server.org.validator.*;
import com.org.permission.server.org.vo.FinanceEntrustRelationInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 财务委托关系控制器
 */
@RestController
@Api(tags = "0财务委托关系接口文档")
@RequestMapping("finance-entrust")
public class FinanceEntrustRelationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FinanceEntrustRelationController.class);

    @Resource
    private FinanceEntrustRelationReqParamValidator financeEntrustRelationReqParamValidator;

    @Resource
    private QueryFinanceEntrustRelationParamValidator queryFinanceEntrustRelationParamValidator;

    @Resource
    private EnableOperateReqParamValidator enableOperateReqParamValidator;

    @Resource
    private QueryFinanceEntrustRelationByUserPermissionParamValidator queryFinanceEntrustRelationByUPParamValidator;

    @Resource
    private FinanceEntrustRelationService financeEntrustRelationService;

    @Resource
    private UserDomainService userDomainService;

    @Resource
    private BaseEntrustRelationValidator baseEntrustRelationValidator;

    @ApiOperation(value = "新增业务委托关系", httpMethod = "POST")
    @PostMapping(value = "addFinanceEntrustRelation")
    public RestMessage<Boolean> addFinanceEntrustRelation(@RequestBody FinanceEntrustRelationParam reqParam) {
        LOGGER.info("add finance entrust relation req param :{}.", reqParam);
        try {
            financeEntrustRelationReqParamValidator.validate(reqParam);
            Long unique = baseEntrustRelationValidator.baseEntrustRelationUnique(EntrustRangeEnum.WITHIN_GROUP.getIndex(), EntrustTypeEnum.FINANCE.getIndex(), null, null, null, null, reqParam.getSettleOrgId(), reqParam.getAccountOrgId(), reqParam.getBizOrgId());
            if (!ObjectUtils.isEmpty(unique) && unique > 0) {
                throw new OrgException(OrgErrorCode.ORG_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.finance.entrust.relation.exist"));
            }
            FinanceEntrustRelationBean financeEntrustRelationBean = FinanceEntrustRelationBean.buildeDefaultValue();
            BeanUtils.copyProperties(reqParam, financeEntrustRelationBean, FinanceEntrustRelationBean.BEAN_COPY_IGNORE_FIELD_LIST);

            financeEntrustRelationService.addFinanceEntrustRelation(financeEntrustRelationBean);

            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("add finance entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("add finance entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "更新业务委托关系", httpMethod = "POST")
    @PostMapping(value = "updateFinanceEntrustRelation")
    public RestMessage<Boolean> updateFinanceEntrustRelation(@RequestBody FinanceEntrustRelationParam reqParam) {
        LOGGER.info("update finance entrust relation req param :{}.", reqParam);
        try {
            if (reqParam.getId() == null) {
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.id.cannot.null"));
            }
            Long unique = baseEntrustRelationValidator.baseEntrustRelationUnique(EntrustRangeEnum.WITHIN_GROUP.getIndex(), EntrustTypeEnum.FINANCE.getIndex(), null, null, null, null, reqParam.getSettleOrgId(), reqParam.getAccountOrgId(), reqParam.getBizOrgId());
            if (!ObjectUtils.isEmpty(unique) && unique.intValue() != reqParam.getId()) {
                throw new OrgException(OrgErrorCode.ORG_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.finance.entrust.relation.org.exist"));
            }
            FinanceEntrustRelationBean financeEntrustRelationBean = new FinanceEntrustRelationBean();
            BeanUtils.copyProperties(reqParam, financeEntrustRelationBean);

            LOGGER.debug("update finance entrust relation bean:{}.", reqParam);
            financeEntrustRelationService.updateFinanceEntrustRelation(financeEntrustRelationBean);

            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("update finance entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("update finance entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "启停采购业务委托关系", httpMethod = "POST")
    @PostMapping(value = "/enableFinanceEntrustRelation")
    public RestMessage<Boolean> enableFinanceEntrustRelation(@RequestBody EnableOperateParam reqParam) {
        LOGGER.info("enable finance entrust relation req param :{}.", reqParam);
        try {
            financeEntrustRelationService.enableFinanceEntrustRelation(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("enable finance entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("enable finance entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询财务委托关系列表", httpMethod = "POST")
    @PostMapping(value = "/queryFinanceEntrustRelationList")
    public RestMessage<PageInfo<FinanceEntrustRelationInfoVo>> queryFinanceEntrustRelationList(@RequestBody FinanceEntrustRelationReqParam reqParam) {
        LOGGER.info("page query finance entrust relation req param :{}.", reqParam);
        try {
            final PageInfo<FinanceEntrustRelationInfoBean> financeList = financeEntrustRelationService.queryFinanceEntrustRelationList(reqParam);
            return wrapPagedList(financeList);
        } catch (OrgException oex) {
            LOGGER.info("page query finance entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("page query finance entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询财务委托关系", httpMethod = "POST")
    @PostMapping(value = "/queryFinanceEntrustRelation")
    public RestMessage<List<FinanceEntrustRelationInfoDto>> queryFinanceEntrustRelation(@RequestBody final QueryFinanceEntrustRelationParam reqParam) {
        LOGGER.info("query finance entrust relation request,param:{}.", reqParam);
        try {
            queryFinanceEntrustRelationParamValidator.validate(reqParam);
            final List<FinanceEntrustRelationInfoBean> financeEntrustRelations = financeEntrustRelationService.queryFinanceEntrustRelation(reqParam);
            RestMessage<List<FinanceEntrustRelationInfoDto>> restMessage = new RestMessage<>();
            if (!CollectionUtils.isEmpty(financeEntrustRelations)) {
                final List<FinanceEntrustRelationInfoDto> financeEntrustRelationInfoDtos = OrgBeanUtil.listConvert(financeEntrustRelations, new FinanceEntrustRelationInfoDto());
                restMessage.setData(financeEntrustRelationInfoDtos);
            }
            return restMessage;
        } catch (final OrgException oex) {
            LOGGER.info("query finance entrust relation request failed,reqParam:{" + reqParam + "}", oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query finance entrust relation request error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "批量查询财务委托关系集合（传参ids）", httpMethod = "POST")
    @PostMapping(value = "/queryFinanceEntrustRelationBatch")
    public RestMessage<Map<Long, List<FinanceEntrustRelationInfoDto>>> queryFinanceEntrustRelationBatch(@RequestBody QueryByIdReqParam queryByIdReqParam) {
        LOGGER.info("query finance entrust relation request,param:{}.", JSON.toJSONString(queryByIdReqParam));
        try {
            Map<Long, List<FinanceEntrustRelationInfoDto>> map = financeEntrustRelationService.queryFinanceEntrustRelationBatch(queryByIdReqParam.getIds());
            return RestMessage.doSuccess(map);
        } catch (final OrgException oex) {
            LOGGER.info("query finance entrust relation request failed,reqParam:{" + queryByIdReqParam.getIds() + "}", oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query finance entrust relation request error,reqParam:" + queryByIdReqParam.getIds(), ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }


    @ApiOperation(value = "查询财务委托关系", httpMethod = "POST")
    @PostMapping(value = "/queryFinanceEntrustRelationByUserPermission")
    public RestMessage<List<FinanceEntrustRelationInfoDto>> queryFinanceEntrustRelationByUserPermission(@RequestBody QueryFinanceEntrustRelationByUserPermissionParam reqParam) {
        LOGGER.info("query finance entrust relation by user permission request,param:{}.", reqParam);
        try {
            queryFinanceEntrustRelationByUPParamValidator.validate(reqParam);
            final List<FinanceEntrustRelationInfoBean> financeEntrustRelations = financeEntrustRelationService.queryFinanceEntrustRelationByUserPermiss(reqParam);
            if (!CollectionUtils.isEmpty(financeEntrustRelations)) {
                final List<FinanceEntrustRelationInfoDto> financeEntrustRelationInfoDtos = OrgBeanUtil.listConvert(financeEntrustRelations, new FinanceEntrustRelationInfoDto());
                return RestMessage.doSuccess(financeEntrustRelationInfoDtos);
            }
            return RestMessage.doSuccess(new ArrayList<>());
        } catch (final OrgException oex) {
            LOGGER.info("query finance entrust relation by user permission request failed,reqParam:{" + reqParam + "}", oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query finance entrust relation by user permission request error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    /**
     * 封装分页查询结果
     *
     * @param financePage 分页查询结果
     * @return 财务委托关系视图实体集合
     */
    private RestMessage<PageInfo<FinanceEntrustRelationInfoVo>> wrapPagedList(final PageInfo<FinanceEntrustRelationInfoBean> financePage) {
        PageInfo<FinanceEntrustRelationInfoVo> pageInfo = PageUtil.convert(financePage, item -> {
            FinanceEntrustRelationInfoVo vo = new FinanceEntrustRelationInfoVo();
            BeanUtil.copyProperties(item, vo);
            return vo;
        });
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            userDomainService.batchFillUserName(pageInfo.getList());
        }
        return RestMessage.doSuccess(pageInfo);
    }
}
