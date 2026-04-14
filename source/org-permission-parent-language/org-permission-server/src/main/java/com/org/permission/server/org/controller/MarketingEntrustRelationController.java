package com.org.permission.server.org.controller;

import cn.hutool.core.bean.BeanUtil;
import com.common.language.util.I18nUtils;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.MarketEntrustRelationDto;
import com.org.permission.common.org.dto.OrgCustDto;
import com.org.permission.common.org.dto.WarehouseEnterOwnerRankingListDto;
import com.org.permission.common.org.param.*;
import com.org.permission.server.domain.crm.CustDomainService;
import com.org.permission.server.domain.user.UserDomainService;
import com.org.permission.server.domain.wms.StorageDomainService;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.MarketEntrustRelationBean;
import com.org.permission.server.org.bean.MarketEntrustRelationInfoBean;
import com.org.permission.server.org.bean.PurchaseEntrustRelationInfoBean;
import com.org.permission.server.org.bean.SaleEntrustRelationInfoBean;
import com.org.permission.server.org.dto.param.MarketEntrustRelationParam;
import com.org.permission.server.org.dto.param.MarketEntrustRelationReqParam;
import com.org.permission.server.org.enums.EntrustRangeEnum;
import com.org.permission.server.org.enums.EntrustTypeEnum;
import com.org.permission.server.org.service.MarketEntrustRelationshipService;
import com.org.permission.server.org.service.OrganizationService;
import com.org.permission.server.org.service.PurchaseEntrustRelationService;
import com.org.permission.server.org.service.SaleEntrustRelationService;
import com.org.permission.server.org.util.PageUtil;
import com.org.permission.server.org.validator.BaseEntrustRelationValidator;
import com.org.permission.server.org.validator.EnableOperateReqParamValidator;
import com.org.permission.server.org.validator.MarketEntrustRelationReqParamValidator;
import com.org.permission.server.org.validator.QueryMarketingEntrustRelationParamValidator;
import com.org.permission.server.org.vo.MarketEntrustRelationInfoVo;
import com.org.permission.server.utils.NumericUtil;
import com.common.util.message.RestMessage;
import com.common.base.entity.CurrentUser;
import com.common.framework.user.FplUserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * 采销委托关系对外服务控制器
 */
@RestController
@Api(tags = "0采销委托关系接口文档")
@RequestMapping("marketing-entrust")
public class MarketingEntrustRelationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MarketingEntrustRelationController.class);

    @Resource
    private MarketEntrustRelationReqParamValidator marketEntrustRelationReqParamValidator;

    @Resource
    private MarketEntrustRelationshipService marketEntrustRelationshipService;

    @Resource
    private EnableOperateReqParamValidator enableOperateReqParamValidator;

    @Resource
    private QueryMarketingEntrustRelationParamValidator queryMarketingEntrustRelationParamValidator;

    @Resource
    private CustDomainService custDomainService;

    @Resource
    private StorageDomainService storageDomainService;

    @Resource
    private UserDomainService userDomainService;

    @Resource
    private PurchaseEntrustRelationService purchaseEntrustRelationService;

    @Resource
    private SaleEntrustRelationService saleEntrustRelationService;

    @Resource
    private OrganizationService organizationService;

    @Resource
    private BaseEntrustRelationValidator baseEntrustRelationValidator;

    @ApiOperation(value = "新增采销业务委托关系", httpMethod = "POST")
    @PostMapping(value = "/addMarketEntrustRelation")
    public RestMessage<Boolean> addMarketEntrustRelation(@RequestBody MarketEntrustRelationParam reqParam) {
        LOGGER.info("add market entrust relation req param :{}.", reqParam);
        try {
            marketEntrustRelationReqParamValidator.validate(reqParam);
            Long unique = baseEntrustRelationValidator.baseEntrustRelationUnique(EntrustRangeEnum.INTER_GROUP.getIndex(), EntrustTypeEnum.PURCHASEANDSALE.getIndex(), reqParam.getWarehouseId(), reqParam.getMarketOrgId(), reqParam.getStockOrgId(), null, null, null, null);
            if (!ObjectUtils.isEmpty(unique) && unique > 0) {
                throw new OrgException(OrgErrorCode.ORG_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.market.entrust.relation.org.exist"));
            }
            MarketEntrustRelationBean marketBean = new MarketEntrustRelationBean();
            BeanUtils.copyProperties(reqParam, marketBean);
            LOGGER.info("add market entrust relation bean :{}.", marketBean);

            marketEntrustRelationshipService.addMarketEntrustRelation(marketBean);

            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("add market entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("add market entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "更新采销业务委托关系", httpMethod = "POST")
    @PostMapping(value = "/updateMarketEntrustRelation")
    public RestMessage<Boolean> updateMarketEntrustRelation(@RequestBody MarketEntrustRelationParam reqParam) {
        LOGGER.info("update market entrust relation req param :{}.", reqParam);
        try {
            marketEntrustRelationReqParamValidator.validate(reqParam);
            if (reqParam.getId() == null) {
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.id.cannot.null"));
            }
            Long unique = baseEntrustRelationValidator.baseEntrustRelationUnique(EntrustRangeEnum.INTER_GROUP.getIndex(), EntrustTypeEnum.PURCHASEANDSALE.getIndex(), reqParam.getWarehouseId(), reqParam.getMarketOrgId(), reqParam.getStockOrgId(), null, null, null, null);
            if (!ObjectUtils.isEmpty(unique) && unique.intValue() != reqParam.getId()) {
                throw new OrgException(OrgErrorCode.ORG_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.market.entrust.relation.org.exist"));
            }
            MarketEntrustRelationBean marketBean = new MarketEntrustRelationBean();
            BeanUtils.copyProperties(reqParam, marketBean);
            LOGGER.info("update market entrust relation bean :{}.", marketBean);
            marketEntrustRelationshipService.updateMarketEntrustRelation(marketBean);

            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("update market entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("update market entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }


    @ApiOperation(value = "启停采购业务委托关系", httpMethod = "POST")
    @PostMapping(value = "/enableMarketEntrustRelation")
    public RestMessage<Boolean> enableMarketEntrustRelation(@RequestBody EnableOperateParam reqParam) {
        LOGGER.info("enable market entrust relation req param :{}.", reqParam);
        try {
            enableOperateReqParamValidator.validate(reqParam);
            CurrentUser currentUser =FplUserUtil.getCurrentUser();
            reqParam.setUserName(currentUser.getUserName());
            reqParam.setUserId(currentUser.getUserId());
            marketEntrustRelationshipService.enableMarketEntrustRelation(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("enable market entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("enable market entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }


    @ApiOperation(value = "查询启用采销委托关系", httpMethod = "POST")
    @PostMapping(value = "/queryMarketEntrustRelation")
    public RestMessage<List<MarketEntrustRelationDto>> queryMarketEntrustRelation(@RequestBody MarketEntrustRelationQueryParam reqParam) {
        LOGGER.info("query marketing entrust relation request,param:{}.", reqParam);
        try {
            queryMarketingEntrustRelationParamValidator.validate(reqParam);
            final List<MarketEntrustRelationDto> marketEntrustRelations = marketEntrustRelationshipService.queryMarketEntrustRelation(reqParam);
            return RestMessage.doSuccess(marketEntrustRelations);
        } catch (final OrgException oex) {
            LOGGER.info("query marketing entrust relation request failed,reqParam:{" + reqParam + "}", oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query marketing entrust relation request error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }


    @ApiOperation(value = "查询采销委托关系列表", httpMethod = "POST")
    @PostMapping(value = "/queryMarketEntrustRelationList")
    public RestMessage<PageInfo<MarketEntrustRelationInfoVo>> queryMarketEntrustRelationList(@RequestBody MarketEntrustRelationReqParam reqParam) {
        LOGGER.info("page query marketing entrust relation req param :{}.", reqParam);
        try {
            //保存最终的id集合
            List<Long> ids = new ArrayList<>();
            if (Objects.nonNull(reqParam.getConditionName())) {
                //根据仓库名字查询
                Map<Long, String> warehourseMap = storageDomainService.getListLikeWarehousename(reqParam.getConditionName());
                if (Objects.nonNull(warehourseMap)) {
                    ids.addAll(warehourseMap.keySet());
                }
                List<Long> result = custDomainService.getCustInfoListByName(reqParam.getConditionName());
                if (CollectionUtils.isNotEmpty(result)) {
                    ids.addAll(result);
                }
                if (CollectionUtils.isEmpty(ids)) {
                    return RestMessage.querySuccess(PageInfo.of(new ArrayList<>()));
                }
                reqParam.setIds(ids);
            }
            final PageInfo<MarketEntrustRelationInfoBean> marketList = marketEntrustRelationshipService.queryMarketEntrustRelationList(reqParam);
            return wrapPagedList(marketList);
        } catch (OrgException oex) {
            LOGGER.info("page query marketing entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("page query marketing entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }


    @ApiOperation(value = "仓库入驻货主排行榜", httpMethod = "POST")
    @PostMapping(value = "/warehouseEnterOwnerRankingList")
    public RestMessage<List<WarehouseEnterOwnerRankingListDto>> warehouseEnterOwnerRankingList(@RequestBody WarehouseEnterOwnerRankingListParam reqParam) {
        LOGGER.info("Count warehouseId enter ownerId ranking list param :{}.", reqParam);
        try {
            final List<WarehouseEnterOwnerRankingListDto> rankingList = marketEntrustRelationshipService.warehouseEnterOwnerRankingList(reqParam);
            return RestMessage.doSuccess(rankingList);
        } catch (OrgException oex) {
            LOGGER.info("Count warehouseId enter ownerId ranking list failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("Count warehouseId enter ownerId ranking list error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "根据采销组织查询仓库id", httpMethod = "GET")
    @GetMapping(value = "/queryWareHouseIdsByMarketOrgId")
    public RestMessage<List<Long>> queryWareHouseIdsByMarketOrgId(@RequestBody Long marketOrgId) {
        //查询集团内的采购和销售委托关系的库存组织，然后根据库存组织id去拿仓
        //库存库存组织id集合
        List<Long> orgIds = new ArrayList<>();
        PurchaseEntrustRelationQueryParam param = new PurchaseEntrustRelationQueryParam();
        param.setPurchaseOrgId(marketOrgId);
        //查询集团内的采购委托关系
        List<PurchaseEntrustRelationInfoBean> puOrgs = purchaseEntrustRelationService.queryPurchaseEntrustRelation(param);
        if (!ObjectUtils.isEmpty(puOrgs)) {
            for (PurchaseEntrustRelationInfoBean puItem : puOrgs) {
                if (!ObjectUtils.isEmpty(puItem.getStockOrgId())) {
                    if (!orgIds.contains(puItem.getStockOrgId())) {
                        orgIds.add(puItem.getStockOrgId());
                    }
                }
            }
        }
        //查询集团内的销售委托关系
        SaleEntrustRelationQueryParam saParam = new SaleEntrustRelationQueryParam();
        saParam.setSaleOrgId(marketOrgId);
        List<SaleEntrustRelationInfoBean> saOrgs = saleEntrustRelationService.querySaleEntrustRelation(saParam);

        if (!ObjectUtils.isEmpty(saOrgs)) {
            for (SaleEntrustRelationInfoBean saItem : saOrgs) {
                if (!ObjectUtils.isEmpty(saItem.getStockOrgId())) {
                    if (!orgIds.contains(saItem.getStockOrgId())) {
                        orgIds.add(saItem.getStockOrgId());
                    }
                }
            }
        }
        List<Long> wareHouseIds = new ArrayList<>();
        //根据库存组织查询仓库
        if (!ObjectUtils.isEmpty(orgIds)) {
            List<Long> idlistTemp = storageDomainService.getListByStockOrgIDList(orgIds);
            if (!ObjectUtils.isEmpty(idlistTemp)) {
                wareHouseIds.addAll(idlistTemp);
            }
        }
        //查询集团间全局采销委托关系中的仓库id
        MarketEntrustRelationQueryParam req = new MarketEntrustRelationQueryParam();
        req.setMarketingOrgId(marketOrgId);
        List<MarketEntrustRelationDto> marketEntrustRelationDtos = marketEntrustRelationshipService.queryMarketEntrustRelation(req);
        if (!ObjectUtils.isEmpty(marketEntrustRelationDtos)) {
            for (MarketEntrustRelationDto item : marketEntrustRelationDtos) {
                wareHouseIds.add(item.getWarehouseId());
            }
        }
        if (!ObjectUtils.isEmpty(wareHouseIds)) {
            //去重
            Set set = new HashSet();
            set.addAll(wareHouseIds);
            wareHouseIds.clear();
            wareHouseIds.addAll(set);
        }
        return RestMessage.doSuccess(wareHouseIds);
    }


    @ApiOperation(value = "根据仓库id查询仓储服务商", httpMethod = "GET")
    @GetMapping(value = "/queryWareHouseProviderByWarehouseId")
    public RestMessage<List<Long>> queryWareHouseProviderByWarehouseId(@RequestParam(value = "warehouseId") Long warehouseId) {
        Assert.notNull(warehouseId, I18nUtils.getMessage("org.market.entrust.relation.warehouseid.null"));
        //查询集团间的仓储服务商
        MarketEntrustRelationQueryParam param = new MarketEntrustRelationQueryParam();
        param.setWarehouseId(warehouseId);
        List<MarketEntrustRelationDto> dtos = marketEntrustRelationshipService.queryMarketEntrustRelation(param);
        List<Long> custIds = new ArrayList<>();
        if (!ObjectUtils.isEmpty(dtos)) {
            for (MarketEntrustRelationDto item : dtos) {
                if (!custIds.contains(item.getWarehouseProviderId())) {
                    custIds.add(item.getWarehouseProviderId());
                }
            }
        }
        //根据仓库id查询集团内的库存组织
        Long stockOrgId = storageDomainService.get(warehouseId);
        //根据库存组织查询仓储服务商（业务单元有商，直接取，否则获取所属集团上的商）
        QueryByIdReqParam reqParam = new QueryByIdReqParam();
        if (!ObjectUtils.isEmpty(stockOrgId)) {
            reqParam.setId(stockOrgId);
            OrgCustDto orgCustDto = organizationService.queryOrgCust(reqParam);
            if (!ObjectUtils.isEmpty(orgCustDto)) {
                if (!custIds.contains(orgCustDto.getCustId())) {
                    custIds.add(orgCustDto.getCustId());
                }
            }
        }
        return RestMessage.doSuccess(custIds);
    }

    /**
     * 封装分页查询结果
     *
     * @param marketPage 分页查询结果
     * @return 采销委托关系视图实体集合
     */
    private RestMessage<PageInfo<MarketEntrustRelationInfoVo>> wrapPagedList(final PageInfo<MarketEntrustRelationInfoBean> marketPage) {
        PageInfo<MarketEntrustRelationInfoVo> pageInfo = PageUtil.convert(marketPage, item -> {
            MarketEntrustRelationInfoVo vo = new MarketEntrustRelationInfoVo();
            BeanUtil.copyProperties(item, vo);
            return vo;
        });
        fillName(pageInfo.getList());
        userDomainService.batchFillUserName(pageInfo.getList());
        return RestMessage.doSuccess(pageInfo);
    }

    /**
     * 填充名字
     *
     * @param marketInfoVos 完整信息的采销委托关系
     */
    private void fillName(final List<MarketEntrustRelationInfoVo> marketInfoVos) {
        if (CollectionUtils.isEmpty(marketInfoVos)) {
            return;
        }
        Set<Long> custIds = new HashSet<>(marketInfoVos.size());
        Set<Long> warehouseIds = new HashSet<>(marketInfoVos.size());
        for (MarketEntrustRelationInfoVo marketInfoVo : marketInfoVos) {
            final Long warehouseProviderId = marketInfoVo.getWarehouseProviderId();
            if (!NumericUtil.nullOrlessThanOrEqualToZero(warehouseProviderId)) {
                custIds.add(warehouseProviderId);
            }
            final Long ownerId = marketInfoVo.getOwnerId();
            if (!NumericUtil.nullOrlessThanOrEqualToZero(ownerId)) {
                custIds.add(ownerId);
            }
            final Long warehouseId = marketInfoVo.getWarehouseId();
            if (!NumericUtil.nullOrlessThanOrEqualToZero(warehouseId)) {
                warehouseIds.add(warehouseId);
            }
        }

        final Map<Long, String> custNameMap = custDomainService.batchQueryCustInfoByIds(custIds);
        final Map<Long, String> warehouseNameMap = storageDomainService.batchQueryWarehouseNameByIds(warehouseIds);
        for (MarketEntrustRelationInfoVo marketInfoVo : marketInfoVos) {
            marketInfoVo.setWarehouseName(warehouseNameMap.get(marketInfoVo.getWarehouseId()));
            marketInfoVo.setWarehouseProviderName(custNameMap.get(marketInfoVo.getWarehouseProviderId()));
            marketInfoVo.setOwnerName(custNameMap.get(marketInfoVo.getOwnerId()));
        }
    }

    /**
     * 过滤货主ID集合
     *
     * @param marketEntrustRelations 采销委托关系
     * @return 货主ID集合
     */
    private Set<Long> filterOwnerIds(final List<MarketEntrustRelationBean> marketEntrustRelations) {
        Set<Long> owerIds = new HashSet<>(marketEntrustRelations.size());
        for (MarketEntrustRelationBean marketEntrustRelation : marketEntrustRelations) {
            owerIds.add(marketEntrustRelation.getOwnerId());
        }
        return owerIds;
    }
}
