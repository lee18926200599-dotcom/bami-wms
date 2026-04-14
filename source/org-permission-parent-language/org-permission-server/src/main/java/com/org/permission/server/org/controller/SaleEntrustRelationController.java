package com.org.permission.server.org.controller;


import cn.hutool.core.bean.BeanUtil;
import com.common.language.util.I18nUtils;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.SaleEntrustRelationDto;
import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.common.org.param.SaleEntrustRelationQueryParam;
import com.org.permission.server.domain.crm.CustDomainService;
import com.org.permission.server.domain.user.UserDomainService;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.SaleEntrustRelationBean;
import com.org.permission.server.org.bean.SaleEntrustRelationInfoBean;
import com.org.permission.server.org.dto.param.SaleEntrustRelationParam;
import com.org.permission.server.org.dto.param.SaleEntrustRelationReqParam;
import com.org.permission.server.org.enums.EntrustRangeEnum;
import com.org.permission.server.org.enums.EntrustTypeEnum;
import com.org.permission.server.org.service.SaleEntrustRelationService;
import com.org.permission.server.org.util.OrgBeanUtil;
import com.org.permission.server.org.util.PageUtil;
import com.org.permission.server.org.validator.BaseEntrustRelationValidator;
import com.org.permission.server.org.validator.EnableOperateReqParamValidator;
import com.org.permission.server.org.validator.SaleEntrustRelationReqParamValidator;
import com.org.permission.server.org.vo.SaleEntrustRelationInfoVo;
import com.common.util.message.RestMessage;
import com.common.framework.user.FplUserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 销售委托关系控制器
 */
@RestController
@Api(tags = "0采购委托关系接口文档")
@RequestMapping("sale-entrust")
public class SaleEntrustRelationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaleEntrustRelationController.class);

    @Resource
    private SaleEntrustRelationReqParamValidator saleEntrustRelationReqParamValidator;


    @Resource
    private EnableOperateReqParamValidator enableOperateReqParamValidator;

    @Resource
    private SaleEntrustRelationService saleEntrustRelationService;

    @Resource
    private CustDomainService custDomainService;

    @Resource
    private UserDomainService userDomainService;

    @Resource
    private BaseEntrustRelationValidator baseEntrustRelationValidator;

    @ApiOperation(value = "新增销售业务委托关系", httpMethod = "POST")
    @PostMapping(value = "addSaleEntrustRelation")
    public RestMessage<Boolean> addSaleEntrustRelation(@RequestBody SaleEntrustRelationParam reqParam) {
        LOGGER.info("add sale entrust relation req param :{}.", reqParam);
        try {
            Long unique = baseEntrustRelationValidator.baseEntrustRelationUnique(EntrustRangeEnum.WITHIN_GROUP.getIndex(), EntrustTypeEnum.SALE.getIndex(), null, reqParam.getSaleOrgId(), reqParam.getStockOrgId(), null, reqParam.getReceiveOrgId(), reqParam.getSettleOrgId(), null);
            if (!ObjectUtils.isEmpty(unique) && unique > 0) {
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.sale.entrust.relation.org.exist"));
            }
            SaleEntrustRelationBean saleEntrustRelationBean = new SaleEntrustRelationBean();
            BeanUtils.copyProperties(reqParam, saleEntrustRelationBean);
            LOGGER.info("add sale entrust relation bean:{}.", saleEntrustRelationBean);

            saleEntrustRelationService.addSaleEntrustRelation(saleEntrustRelationBean);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("add sale entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("add sale entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "更新销售业务委托关系", httpMethod = "POST")
    @PostMapping(value = "updateSaleEntrustRelation")
    public RestMessage<Boolean> updateSaleEntrustRelation(@RequestBody SaleEntrustRelationParam reqParam) {
        LOGGER.info("update sale entrust relation req param :{}.", reqParam);
        try {

            Long unique = baseEntrustRelationValidator.baseEntrustRelationUnique(EntrustRangeEnum.WITHIN_GROUP.getIndex(), EntrustTypeEnum.SALE.getIndex(), null, reqParam.getSaleOrgId(), reqParam.getStockOrgId(), null, reqParam.getReceiveOrgId(), reqParam.getSettleOrgId(), null);
            if (!ObjectUtils.isEmpty(unique) && unique > 0) {
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.sale.entrust.relation.org.exist"));
            }
            SaleEntrustRelationBean saleEntrustRelationBean = new SaleEntrustRelationBean();
            BeanUtils.copyProperties(reqParam, saleEntrustRelationBean);
            saleEntrustRelationBean.setModifiedBy(FplUserUtil.getUserId());
            saleEntrustRelationBean.setModifiedName(FplUserUtil.getUserName());
            LOGGER.info("update sale entrust relation bean:{}.", reqParam);
            saleEntrustRelationService.updateSaleEntrustRelation(saleEntrustRelationBean);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("update sale entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("update sale entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "启停销售业务委托关系", httpMethod = "POST")
    @PostMapping(value = "enableSaleEntrustRelation")
    public RestMessage<Boolean> enableSaleEntrustRelation(@RequestBody EnableOperateParam reqParam) {
        LOGGER.info("enable sale entrust relation req param :{}.", reqParam);
        try {
            enableOperateReqParamValidator.validate(reqParam);
            reqParam.setUserId(FplUserUtil.getUserId());
            reqParam.setUserName(FplUserUtil.getUserName());
            saleEntrustRelationService.enableSaleEntrustRelation(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("enable sale entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("enable sale entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询销售委托关系列表", httpMethod = "POST")
    @PostMapping(value = "/querySaleEntrustRelationList")
    public RestMessage<PageInfo<SaleEntrustRelationInfoVo>> querySaleEntrustRelationList(@RequestBody SaleEntrustRelationReqParam reqParam) {
        LOGGER.info("page query sale entrust relation req param :{}.", reqParam);
        try {
            final PageInfo<SaleEntrustRelationInfoBean> saleList = saleEntrustRelationService.querySaleEntrustRelationList(reqParam);
            return wrapPagedList(saleList);
        } catch (OrgException oex) {
            LOGGER.info("page query sale entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("page query sale entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询销售委托关系", httpMethod = "POST")
    @PostMapping(value = "/querySaleEntrustRelation")
    public RestMessage<List<SaleEntrustRelationDto>> querySaleEntrustRelation(@RequestBody SaleEntrustRelationQueryParam reqParam) {
        LOGGER.info("query sale entrust relation req param :{}.", reqParam);
        try {
            final List<SaleEntrustRelationInfoBean> saleList = saleEntrustRelationService.querySaleEntrustRelation(reqParam);

            RestMessage<List<SaleEntrustRelationDto>> restMessage = new RestMessage<>();
            if (!CollectionUtils.isEmpty(saleList)) {
                final List<SaleEntrustRelationDto> saleEntrustRelationDtos = OrgBeanUtil.listConvert(saleList, new SaleEntrustRelationDto());
                restMessage.setData(saleEntrustRelationDtos);
            }
            return restMessage;
        } catch (OrgException oex) {
            LOGGER.info("query sale entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query sale entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    /**
     * 封装分页查询结果
     *
     * @param salePage 分页查询结果
     * @return 物流委托关系视图实体集合
     */
    private RestMessage<PageInfo<SaleEntrustRelationInfoVo>> wrapPagedList(final PageInfo<SaleEntrustRelationInfoBean> salePage) {
        PageInfo<SaleEntrustRelationInfoVo> pageInfo = PageUtil.convert(salePage, item -> {
            SaleEntrustRelationInfoVo vo = new SaleEntrustRelationInfoVo();
            BeanUtil.copyProperties(item, vo);
            return vo;
        });
        userDomainService.batchFillUserName(pageInfo.getList());
        return RestMessage.doSuccess(pageInfo);

    }
}
