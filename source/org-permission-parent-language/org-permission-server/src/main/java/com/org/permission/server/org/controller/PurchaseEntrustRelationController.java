package com.org.permission.server.org.controller;

import cn.hutool.core.bean.BeanUtil;
import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.PurchaseEntrustRelationDto;
import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.common.org.param.PurchaseEntrustRelationQueryParam;
import com.org.permission.server.domain.user.UserDomainService;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.PurchaseEntrustRelationBean;
import com.org.permission.server.org.bean.PurchaseEntrustRelationInfoBean;
import com.org.permission.server.org.dto.param.PurchaseEntrustRelationParam;
import com.org.permission.server.org.dto.param.PurchaseEntrustRelationReqParam;
import com.org.permission.server.org.enums.EntrustRangeEnum;
import com.org.permission.server.org.enums.EntrustTypeEnum;
import com.org.permission.server.org.service.PurchaseEntrustRelationService;
import com.org.permission.server.org.util.OrgBeanUtil;
import com.org.permission.server.org.util.PageUtil;
import com.org.permission.server.org.validator.BaseEntrustRelationValidator;
import com.org.permission.server.org.validator.EnableOperateReqParamValidator;
import com.org.permission.server.org.validator.PurchaseEntrustRelationReqParamValidator;
import com.org.permission.server.org.vo.PurchaseEntrustRelationInfoVo;
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
import java.util.List;

/**
 * 采购委托关系控制器
 */
@RestController
@Api(tags = "0采购委托关系接口文档")
@RequestMapping("purchase-entrust")
public class PurchaseEntrustRelationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseEntrustRelationController.class);

    @Resource
    private PurchaseEntrustRelationReqParamValidator purchaseEntrustRelationReqParamValidator;

    @Resource
    private EnableOperateReqParamValidator enableOperateReqParamValidator;

    @Resource
    private PurchaseEntrustRelationService purchaseEntrustRelationService;

    @Resource
    private UserDomainService userDomainService;

    @Resource
    private BaseEntrustRelationValidator baseEntrustRelationValidator;

    @ApiOperation(value = "新增采购业务委托关系", httpMethod = "POST")
    @PostMapping(value = "addPurchaseEntrustRelation")
    public RestMessage<Boolean> addPurchaseEntrustRelation(@RequestBody PurchaseEntrustRelationParam reqParam) {
        LOGGER.info("add purchase entrust relation req param :{}.", reqParam);
        try {
            purchaseEntrustRelationReqParamValidator.validate(reqParam);
            Long unique = baseEntrustRelationValidator.baseEntrustRelationUnique(EntrustRangeEnum.WITHIN_GROUP.getIndex(), EntrustTypeEnum.PURCHASE.getIndex(), null, reqParam.getPurchaseOrgId(), reqParam.getStockOrgId(), null, reqParam.getSettleOrgId(), reqParam.getPayOrgId(), null);
            if (!ObjectUtils.isEmpty(unique) && unique > 0) {
                throw new OrgException(OrgErrorCode.ORG_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.purchase.entrust.relation.org.exist"));
            }
            PurchaseEntrustRelationBean purchaseEntrustRelationBean = new PurchaseEntrustRelationBean();
            BeanUtils.copyProperties(reqParam, purchaseEntrustRelationBean);
            LOGGER.info("add purchase entrust relation bean :{}.", purchaseEntrustRelationBean);

            purchaseEntrustRelationService.addPurchaseEntrustRelation(purchaseEntrustRelationBean);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("add purchase entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("add purchase entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "更新采购业务委托关系", httpMethod = "POST")
    @PostMapping(value = "updatePurchaseEntrustRelation")
    public RestMessage<Boolean> updatePurchaseEntrustRelation(@RequestBody PurchaseEntrustRelationParam reqParam) {
        LOGGER.info("update purchase entrust relation req param :{}.", reqParam);
        try {
            Long unique = baseEntrustRelationValidator.baseEntrustRelationUnique(EntrustRangeEnum.WITHIN_GROUP.getIndex(), EntrustTypeEnum.PURCHASE.getIndex(), null, reqParam.getPurchaseOrgId(), reqParam.getStockOrgId(), null, reqParam.getSettleOrgId(), reqParam.getPayOrgId(), null);
            if (!ObjectUtils.isEmpty(unique) && unique > 0) {
                throw new OrgException(OrgErrorCode.ORG_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.purchase.entrust.relation.org.exist"));
            }
            PurchaseEntrustRelationBean purchaseEntrustRelationBean = new PurchaseEntrustRelationBean();
            BeanUtils.copyProperties(reqParam, purchaseEntrustRelationBean);

            LOGGER.info("update purchase entrust relation bean:{}.", reqParam);
            purchaseEntrustRelationService.updatePurchaseEntrustRelation(purchaseEntrustRelationBean);

            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("update purchase entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("update purchase entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "启停采购业务委托关系", httpMethod = "POST")
    @PostMapping(value = "/enablePurchaseEntrustRelation")
    public RestMessage<Boolean> enablePurchaseEntrustRelation(@RequestBody EnableOperateParam reqParam) {
        LOGGER.info("enable purchase entrust relation req, param : {}.", reqParam);
        try {
            enableOperateReqParamValidator.validate(reqParam);

            purchaseEntrustRelationService.enablePurchaseEntrustRelation(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("enable purchase entrust relationship failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("enable purchase entrust relationship error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询采购委托关系列表", httpMethod = "POST")
    @PostMapping(value = "/queryPurchaseEntrustRelationList")
    public RestMessage<PageInfo<PurchaseEntrustRelationInfoVo>> queryPurchaseEntrustRelationList(@RequestBody PurchaseEntrustRelationReqParam reqParam) {
        LOGGER.info("page query purchase entrust relation req param :{}.", reqParam);
        try {
            final PageInfo<PurchaseEntrustRelationInfoBean> purchaseList = purchaseEntrustRelationService.queryPurchaseEntrustRelationList(reqParam);
            return wrapPagedList(purchaseList);
        } catch (OrgException oex) {
            LOGGER.info("page query purchase entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("page query purchase entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询采购委托关系列表", httpMethod = "POST")
    @PostMapping(value = "/queryPurchaseEntrustRelation")
    public RestMessage<List<PurchaseEntrustRelationDto>> queryPurchaseEntrustRelation(@RequestBody PurchaseEntrustRelationQueryParam reqParam) {
        LOGGER.info("query purchase entrust relation req param :{}.", reqParam);
        try {
            final List<PurchaseEntrustRelationInfoBean> purchaseList = purchaseEntrustRelationService.queryPurchaseEntrustRelation(reqParam);

            RestMessage<List<PurchaseEntrustRelationDto>> restMessage = new RestMessage<>();
            if (!CollectionUtils.isEmpty(purchaseList)) {
                final List<PurchaseEntrustRelationDto> purchaseEntrustRelationDtos = OrgBeanUtil.listConvert(purchaseList, new PurchaseEntrustRelationDto());
                restMessage.setData(purchaseEntrustRelationDtos);
            }
            return restMessage;
        } catch (OrgException oex) {
            LOGGER.info("query purchase entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query purchase entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    /**
     * 封装分页查询结果
     *
     * @param purchasePage 分页查询结果
     * @return 物流委托关系视图实体集合
     */
    private RestMessage<PageInfo<PurchaseEntrustRelationInfoVo>> wrapPagedList(final PageInfo<PurchaseEntrustRelationInfoBean> purchasePage) {
        PageInfo<PurchaseEntrustRelationInfoVo> pageInfo = PageUtil.convert(purchasePage, item -> {
            PurchaseEntrustRelationInfoVo vo = new PurchaseEntrustRelationInfoVo();
            BeanUtil.copyProperties(item, vo);
            return vo;
        });
        userDomainService.batchFillUserName(pageInfo.getList());
        return RestMessage.doSuccess(pageInfo);
    }
}
