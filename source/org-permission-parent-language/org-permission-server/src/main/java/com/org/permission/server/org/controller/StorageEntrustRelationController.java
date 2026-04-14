package com.org.permission.server.org.controller;


import com.common.language.util.I18nUtils;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.*;
import com.org.permission.common.org.param.*;
import com.org.permission.server.domain.crm.CustDomainService;
import com.org.permission.server.domain.user.UserDomainService;
import com.org.permission.server.domain.wms.StorageDomainService;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.*;
import com.org.permission.server.org.builder.FunctionLowLevelBuilder;
import com.org.permission.server.org.dto.MultipleOrgTreeDto;
import com.org.permission.server.org.dto.param.GroupStorageEntrustRelationParam;
import com.org.permission.server.org.dto.param.GroupStorageEntrustRelationReqParam;
import com.org.permission.server.org.dto.param.PlatformStorageEntrustRelationParam;
import com.org.permission.server.org.dto.param.PlatformStorageEntrustRelationReqParam;
import com.org.permission.server.org.enums.EntrustRangeEnum;
import com.org.permission.server.org.enums.EntrustTypeEnum;
import com.org.permission.server.org.service.OrganizationService;
import com.org.permission.server.org.service.StorageEntrustRelationService;
import com.org.permission.server.org.util.PageUtil;
import com.org.permission.server.org.validator.BaseEntrustRelationValidator;
import com.org.permission.server.org.validator.EnableOperateReqParamValidator;
import com.org.permission.server.org.validator.QueryStorageEntrustRelationLogisticsOrgParamValidator;
import com.org.permission.server.org.validator.StorageEntrustRelationReqParamValidator;
import com.org.permission.server.org.vo.StorageGroupEntrustRelationInfoVo;
import com.org.permission.server.org.vo.StoragePlatformEntrustRelationInfoVo;
import com.common.util.message.RestMessage;
import com.common.util.util.AssertUtils;
import com.common.framework.user.FplUserUtil;
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
import java.util.*;

/**
 * 仓储委托关系控制器
 */
@RestController
@Api(tags = "0仓储委托关系接口文档")
@RequestMapping("storage-entrust")
public class StorageEntrustRelationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageEntrustRelationController.class);

    @Resource
    private QueryStorageEntrustRelationLogisticsOrgParamValidator queryStorageEntrustRelationLogisticsOrgParamValidator;

    @Resource
    private StorageEntrustRelationReqParamValidator storageEntrustRelationReqParamValidator;

    @Resource
    private EnableOperateReqParamValidator enableOperateReqParamValidator;

    @Resource
    private StorageEntrustRelationService storageEntrustRelationService;

    @Resource
    private CustDomainService custDomainService;

    @Resource
    private StorageDomainService storageDomainService;

    @Resource
    private UserDomainService userDomainService;

    @Resource
    private FunctionLowLevelBuilder functionLowLevelBuilder;

    @Resource
    private OrganizationService organizationService;

    @Resource
    private BaseEntrustRelationValidator baseEntrustRelationValidator;

    @ApiOperation(value = "新增集团间仓储业务委托关系", httpMethod = "POST")
    @PostMapping(value = "addPlatformStorageEntrustRelation")
    public RestMessage<Boolean> addPlatformStorageEntrustRelation(@RequestBody PlatformStorageEntrustRelationParam reqParam) {
        LOGGER.info("add platform storage entrust relation req param :{}.", reqParam);
        try {
            storageEntrustRelationReqParamValidator.validatePlatform(reqParam);
            Long unique = baseEntrustRelationValidator.baseEntrustRelationUnique(EntrustRangeEnum.INTER_GROUP.getIndex(), EntrustTypeEnum.STORAGE.getIndex(), reqParam.getWarehouseId(), null, reqParam.getStockOrgId(), reqParam.getLogisticsOrgId(), null, null, null);
            if (!ObjectUtils.isEmpty(unique) && unique > 0) {
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.storage.entrust.relation.org.exist"));
            }
            StoragePlatformEntrustRelationBean storagePlatformEntrustRelationBean = new StoragePlatformEntrustRelationBean();
            BeanUtils.copyProperties(reqParam, storagePlatformEntrustRelationBean);
            LOGGER.debug("add platform storage entrust relation bean:{}.", storagePlatformEntrustRelationBean);
            storageEntrustRelationService.addPlatformStorageEntrustRelation(storagePlatformEntrustRelationBean);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("add platform storage entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("add platform storage entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "更新集团间仓储委托关系", httpMethod = "POST")
    @PostMapping(value = "updatePlatformStorageEntrustRelation")
    public RestMessage<Boolean> updatePlatformStorageEntrustRelation(@RequestBody PlatformStorageEntrustRelationParam reqParam) {
        LOGGER.info("update platform storage entrust relation req param :{}.", reqParam);
        try {
            if (reqParam.getId() == null) {
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.id.cannot.null"));
            }
            Long unique = baseEntrustRelationValidator.baseEntrustRelationUnique(EntrustRangeEnum.INTER_GROUP.getIndex(), EntrustTypeEnum.STORAGE.getIndex(), reqParam.getWarehouseId(), null, reqParam.getStockOrgId(), reqParam.getLogisticsOrgId(), null, null, null);
            if (!ObjectUtils.isEmpty(unique) && unique.intValue() != reqParam.getId()) {
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.storage.entrust.relation.org.exist"));
            }
            StoragePlatformEntrustRelationBean storagePlatformEntrustRelationBean = new StoragePlatformEntrustRelationBean();
            BeanUtils.copyProperties(reqParam, storagePlatformEntrustRelationBean);
            storageEntrustRelationService.updatePlatformStorageEntrustRelation(storagePlatformEntrustRelationBean);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("update platform storage entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("update platform storage entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "启停集团间仓储业务委托关系", httpMethod = "POST")
    @PostMapping(value = "/enablePlatformStorageEntrustRelation")
    public RestMessage<Boolean> enablePlatformStorageEntrustRelation(@RequestBody EnableOperateParam reqParam) {
        LOGGER.info("enable platform storage entrust relation req param :{}.", reqParam);
        try {
            enableOperateReqParamValidator.validate(reqParam);
            reqParam.setUserId(FplUserUtil.getUserId());
            reqParam.setUserName(FplUserUtil.getUserName());
            storageEntrustRelationService.enablePlatformStorageEntrustRelation(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("enable platform storage entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("enable platform storage entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    /**
     * 查询集团间仓储委托关系列表
     *
     * @param reqParam 分页查询集团间仓储委托关系查询请求参数
     * @return 集团间仓储委托关系集合
     */
    @ApiOperation(value = "查询集团间仓储委托关系列表", httpMethod = "POST")
    @PostMapping(value = "/queryPlatformStorageEntrustRelationList")
    public RestMessage<PageInfo<StoragePlatformEntrustRelationInfoVo>> queryPlatformStorageEntrustRelationList(@RequestBody PlatformStorageEntrustRelationReqParam reqParam) {
        LOGGER.info("page query platform storage entrust relation req param :{}.", reqParam);
        try {
            List<Long> ids = new ArrayList<>();
            if (Objects.nonNull(reqParam.getConditionName())) {
                List<Long> result = custDomainService.getCustInfoListByName(reqParam.getConditionName());
                if (CollectionUtils.isNotEmpty(result)) {
                    ids.addAll(result);
                }
                //根据仓库名字查询
                Map<Long, String> warehourseMap = storageDomainService.getListLikeWarehousename(reqParam.getConditionName());
                if (!ObjectUtils.isEmpty(warehourseMap)) {
                    ids.addAll(warehourseMap.keySet());
                }
                if (!ObjectUtils.isEmpty(ids)) {
                    reqParam.setIds(ids);
                }
                if (CollectionUtils.isEmpty(reqParam.getIds())){
                    return RestMessage.doSuccess(new PageInfo<>());
                }
            }

            final PageInfo<StoragePlatformEntrustRelationInfoBean> storagePage = storageEntrustRelationService.queryPlatformStorageEntrustRelationList(reqParam);
            final long totalCount = storagePage.getTotal();
            if (totalCount == 0) {
                return RestMessage.doSuccess(new PageInfo<>());
            }
            PageInfo<StoragePlatformEntrustRelationInfoVo> pageInfo = PageUtil.convert(storagePage, item -> {
                StoragePlatformEntrustRelationInfoVo vo = new StoragePlatformEntrustRelationInfoVo();
                BeanUtils.copyProperties(item, vo);
                return vo;
            });
            fillName(pageInfo.getList());
            userDomainService.batchFillUserName(pageInfo.getList());
            return RestMessage.doSuccess(pageInfo);
        } catch (OrgException oex) {
            LOGGER.info("page query platform storage entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("page query platform storage entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "新增集团内仓储业务委托关系", httpMethod = "POST")
    @PostMapping(value = "addGroupStorageEntrustRelation")
    public RestMessage<Boolean> addGroupStorageEntrustRelation(@RequestBody GroupStorageEntrustRelationParam reqParam) {
        LOGGER.info("add group storage entrust relation req param :{}.", reqParam);
        try {
            storageEntrustRelationReqParamValidator.validateGroup(reqParam);
            Long unique = baseEntrustRelationValidator.baseEntrustRelationUnique(EntrustRangeEnum.WITHIN_GROUP.getIndex(), EntrustTypeEnum.STORAGE.getIndex(), null, null, reqParam.getStockOrgId(), reqParam.getLogisticsOrgId(), reqParam.getSettleOrgId(), reqParam.getAccountOrgId(), null);
            if (!ObjectUtils.isEmpty(unique) && unique > 0) {
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.storage.entrust.relation.fourorg.exist"));
            }
            StorageGroupEntrustRelationBean storageGroupEntrustRelationBean = new StorageGroupEntrustRelationBean();
            BeanUtils.copyProperties(reqParam, storageGroupEntrustRelationBean);
            storageEntrustRelationService.addGroupStorageEntrustRelation(storageGroupEntrustRelationBean);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("add group storage entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("add group storage entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "更新集团内仓储委托关系", httpMethod = "POST")
    @PostMapping(value = "updateGroupStorageEntrustRelation")
    public RestMessage<Boolean> updateGroupStorageEntrustRelation(@RequestBody GroupStorageEntrustRelationParam reqParam) {
        LOGGER.info("update group storage entrust relation req param :{}.", reqParam);
        try {
            if (reqParam.getId() == null) {
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.id.cannot.null"));
            }
            Long unique = baseEntrustRelationValidator.baseEntrustRelationUnique(EntrustRangeEnum.WITHIN_GROUP.getIndex(), EntrustTypeEnum.STORAGE.getIndex(), null, null, reqParam.getStockOrgId(), reqParam.getLogisticsOrgId(), reqParam.getSettleOrgId(), reqParam.getAccountOrgId(), null);
            if (!ObjectUtils.isEmpty(unique) && unique.intValue() != reqParam.getId()) {
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.storage.entrust.relation.fourorg.exist"));
            }
            StorageGroupEntrustRelationBean storageGroupEntrustRelationBean = new StorageGroupEntrustRelationBean();
            BeanUtils.copyProperties(reqParam, storageGroupEntrustRelationBean);
            LOGGER.debug("update group storage entrust relation bean:{}.", reqParam);
            storageEntrustRelationService.updateGroupStorageEntrustRelation(storageGroupEntrustRelationBean);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("add group storage entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("add group storage entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "启停集团内仓储业务委托关系", httpMethod = "POST")
    @PostMapping(value = "/enableGroupStorageEntrustRelation")
    public RestMessage<Boolean> enableGroupStorageEntrustRelation(@RequestBody EnableOperateParam reqParam) {
        LOGGER.info("enable group storage entrust relation req param :{}.", reqParam);
        try {
            enableOperateReqParamValidator.validate(reqParam);
            storageEntrustRelationService.enableGroupStorageEntrustRelation(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("enable group storage entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("enable group storage entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询集团内仓储委托关系列表-分页", httpMethod = "POST")
    @PostMapping(value = "/queryGroupStorageEntrustRelationList")
    public RestMessage<PageInfo<StorageGroupEntrustRelationInfoVo>> queryGroupStorageEntrustRelationList(@RequestBody GroupStorageEntrustRelationReqParam reqParam) {
        LOGGER.info("page query group storage entrust relation req param :{}.", reqParam);
        try {
            final PageInfo<StorageGroupEntrustRelationInfoBean> storagePage = storageEntrustRelationService.queryGroupStorageEntrustRelationList(reqParam);
            final long totalCount = storagePage.getTotal();
            if (totalCount == 0) {
                return RestMessage.doSuccess(new PageInfo<>());
            }
            PageInfo<StorageGroupEntrustRelationInfoVo> pageInfo = PageUtil.convert(storagePage, item -> {
                StorageGroupEntrustRelationInfoVo vo = new StorageGroupEntrustRelationInfoVo();
                BeanUtils.copyProperties(item, vo);
                return vo;
            });
            userDomainService.batchFillUserName(pageInfo.getList());
            return RestMessage.doSuccess(pageInfo);
        } catch (OrgException oex) {
            LOGGER.info("page query group storage entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("page query group storage entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询集团间仓储委托关系", httpMethod = "POST")
    @PostMapping(value = "/queryPlatformStorageEntrustRelation")
    public RestMessage<List<StoragePlatformEntrustRelationDto>> queryPlatformStorageEntrustRelation(@RequestBody StoragePlatformEntrustRelationQueryParam reqParam) {
        LOGGER.info("query platform storage entrust relation req param :{}.", reqParam);
        try {
            final List<StoragePlatformEntrustRelationDto> entrustRelations = storageEntrustRelationService.queryPlatformStorageEntrustRelation(reqParam);
            return RestMessage.doSuccess(entrustRelations);
        } catch (OrgException oex) {
            LOGGER.info("query platform storage entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query platform storage entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }


    @ApiOperation(value = "查询集团间仓储委托关系", httpMethod = "POST")
    @PostMapping(value = "/extendQueryPlatformStorageEntrustRelation")
    public RestMessage<List<StoragePlatformEntrustRelationExtendDto>> extendQueryPlatformStorageEntrustRelation(@RequestBody StoragePlatformEntrustRelationQueryParam reqParam) {
        LOGGER.info("extend query platform storage entrust relation req param :{}.", reqParam);
        try {
            final List<StoragePlatformEntrustRelationDto> entrustRelations = storageEntrustRelationService.queryPlatformStorageEntrustRelation(reqParam);
            if (CollectionUtils.isNotEmpty(entrustRelations)) {
                List<StoragePlatformEntrustRelationExtendDto> result = new ArrayList<>(entrustRelations.size());
                Map<Long, Long> orgGroup = new HashMap<>();
                Set<Long> custIds = new HashSet<>();
                for (StoragePlatformEntrustRelationDto entrustRelation : entrustRelations) {
                    final Long logisticsOrgId = entrustRelation.getLogisticsOrgId();
                    QueryByIdReqParam param = new QueryByIdReqParam(logisticsOrgId);
                    final OrgCustDto orgCustDto = organizationService.queryOrgCust(param);
                    if (orgCustDto != null) {
                        custIds.add(orgCustDto.getCustId());
                    }
                    OrgInfoDto orgInfoDto = organizationService.getOrgInfoById(logisticsOrgId);
                    if (orgInfoDto != null) {
                        orgGroup.put(logisticsOrgId, orgInfoDto.getGroupId());
                    }
                    StoragePlatformEntrustRelationExtendDto dto = new StoragePlatformEntrustRelationExtendDto();
                    BeanUtils.copyProperties(entrustRelation, dto);
                    dto.setLogisticsOrgCustId(orgCustDto.getCustId());
                    result.add(dto);
                }
                final Map<Long, String> custNameMap = custDomainService.batchQueryCustInfoByIds(custIds);
                for (StoragePlatformEntrustRelationExtendDto entrustRelationDto : result) {
                    entrustRelationDto.setLogisticsOrgCustName(custNameMap.get(entrustRelationDto.getLogisticsOrgCustId()));
                }
                return RestMessage.doSuccess(result);
            }
            return RestMessage.doSuccess(new ArrayList<>());
        } catch (OrgException oex) {
            LOGGER.info("extend query platform storage entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("extend query platform storage entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询集团间仓储委托关系", httpMethod = "POST")
    @PostMapping(value = "/queryGroupStorageEntrustRelation")
    public RestMessage<List<StorageGroupEntrustRelationDto>> queryGroupStorageEntrustRelation(@RequestBody StorageGroupEntrustRelationQueryParam reqParam) {
        LOGGER.info("query group storage entrust relation req param :{}.", reqParam);
        try {
            final List<StorageGroupEntrustRelationDto> entrustRelations = storageEntrustRelationService.queryGroupStorageEntrustRelation(reqParam);
            return RestMessage.doSuccess(entrustRelations);
        } catch (OrgException oex) {
            LOGGER.info("query group storage entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query group storage entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询集团内仓储委托关系", httpMethod = "POST")
    @PostMapping(value = "/extendQueryGroupStorageEntrustRelation")
    public RestMessage<List<StorageGroupEntrustRelationExtendDto>> extendQueryGroupStorageEntrustRelation(@RequestBody StorageGroupEntrustRelationQueryParam reqParam) {
        LOGGER.info("extend query group storage entrust relation req param :{}.", reqParam);
        try {
            final List<StorageGroupEntrustRelationDto> entrustRelations = storageEntrustRelationService.queryGroupStorageEntrustRelation(reqParam);
            if (CollectionUtils.isNotEmpty(entrustRelations)) {
                List<StorageGroupEntrustRelationExtendDto> result = new ArrayList<>(entrustRelations.size());
                Set<Long> custIds = new HashSet<>();
                for (StorageGroupEntrustRelationDto entrustRelation : entrustRelations) {
                    final Long logisticsOrgId = entrustRelation.getLogisticsOrgId();
                    QueryByIdReqParam param = new QueryByIdReqParam(logisticsOrgId);
                    StorageGroupEntrustRelationExtendDto dto = new StorageGroupEntrustRelationExtendDto();
                    BeanUtils.copyProperties(entrustRelation, dto);
                    dto.setLogisticsOrgId(entrustRelation.getLogisticsOrgId());
                    //若是flag标志是true的时候根据根业务单元获取全局客商id
                    if (Objects.equals(reqParam.getFlag(), Boolean.TRUE)) {
                        SimpleBizUnitWithFuncBean bean = organizationService.queryOrgById(param.getId());
                        if (Objects.nonNull(bean)) {
                            SimpleBizUnitWithFuncBean unit = organizationService.queryOrgById(bean.getGroupId());
                            if (Objects.nonNull(unit) && Objects.nonNull(unit.getCustId()) && unit.getCustId().longValue() != 0) {
                                custIds.add(unit.getCustId());
                                dto.setLogisticsOrgCustId(unit.getCustId());
                            }
                        }
                    } else {
                        //flag是false的时候，当前业务单元有custId直接获取，如果没有则是跟业务单元上的custId
                        SimpleBizUnitWithFuncBean org = organizationService.queryOrgById(param.getId());
                        if (Objects.nonNull(org)) {
                            if (Objects.nonNull(org.getCustId()) && org.getCustId().longValue() != 0) {
                                custIds.add(org.getCustId());
                                dto.setLogisticsOrgCustId(org.getCustId());
                            } else {
                                SimpleBizUnitWithFuncBean bean = organizationService.queryOrgById(param.getId());
                                if (Objects.nonNull(bean)) {
                                    SimpleBizUnitWithFuncBean orgById = organizationService.queryOrgById(bean.getGroupId());
                                    if (Objects.nonNull(orgById.getCustId()) && orgById.getCustId().longValue() != 0) {
                                        custIds.add(orgById.getCustId());
                                        dto.setLogisticsOrgCustId(orgById.getCustId());
                                    }
                                }
                            }
                        }
                    }
                    result.add(dto);
                }

                final Map<Long, String> custNameMap = custDomainService.batchQueryCustInfoByIds(custIds);
                for (StorageGroupEntrustRelationExtendDto entrustRelationDto : result) {
                    entrustRelationDto.setLogisticsOrgCustName(custNameMap.get(entrustRelationDto.getLogisticsOrgCustId()));
                }
                return RestMessage.doSuccess(result);
            }
            return RestMessage.doSuccess(new ArrayList<>());
        } catch (OrgException oex) {
            LOGGER.info("extend  query group storage entrust relation failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("extend query group storage entrust relation error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询具有仓储委托关系的物流组织及其下具有结算能力的子业务单元", httpMethod = "POST")
    @PostMapping(value = "/queryHasStorageErLogisticsOrg")
    public RestMessage<List<OrgListDto>> queryHasStorageErLogisticsOrg(@RequestBody QueryStorageEntrustRelationLogisticsOrgParam reqParam) {
        LOGGER.info("query storage entrust relation settleable members req param :{}.", reqParam);
        try {
            queryStorageEntrustRelationLogisticsOrgParamValidator.validate(reqParam);
            final MultipleOrgTreeDto multipleOrgTree = storageEntrustRelationService.queryHasStorageErLogisticsOrg(reqParam);
            final List<OrgListDto> orgList = functionLowLevelBuilder.builde(multipleOrgTree);
            return RestMessage.doSuccess(orgList);
        } catch (OrgException oex) {
            LOGGER.info("query storage entrust relation settleable members failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query storage entrust relation settleable members error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询有仓储委托关系的物流组织信息", httpMethod = "POST")
    @PostMapping(value = "/queryHasStorageEntrustRelationLogisticsOrg")
    public RestMessage<PageInfo<OrgInfoDto>> queryHasStorageEntrustRelationLogisticsOrg(@RequestBody EntrustRelationQueryParam reqParam) {
        LOGGER.info("query entrust relation org req param : {}.", reqParam);
        try {
            final PageInfo<OrgInfoDto> orgList = storageEntrustRelationService.queryHasStorageEntrustRelationLogisticsOrg(reqParam);
            return RestMessage.doSuccess(orgList);
        } catch (OrgException oex) {
            LOGGER.info("query entrust relation org failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query entrust relation org error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }


    @ApiOperation(value = "查询有仓储委托关系的物流组织信息根据仓库信息（传参stockOrg,warehouseId）", httpMethod = "POST")
    @PostMapping(value = "/queryStorageErLogisticsOrgByWarehouse")
    public RestMessage<List<EntrustRelationOrgInfoDto>> queryStorageErLogisticsOrgByWarehouse(@RequestBody QueryStorageEntrustRelationLogisticsOrgParam reqParam) {
        AssertUtils.notNull(reqParam.getWarehouseId(), I18nUtils.getMessage("org.common.param.warehouseid.cannot.null"));
        AssertUtils.notNull(reqParam.getStockOrg(), I18nUtils.getMessage("org.storage.entrust.relation.wahouseorgid.null"));
        List<EntrustRelationOrgInfoDto> orgInfoDtos = storageEntrustRelationService.queryStorageErLogisticsOrgByWarehouse(reqParam);
        return RestMessage.doSuccess(orgInfoDtos);
    }

    /**
     * 填充名字
     *
     * @param storageInfoVos 完整信息的仓储委托关系
     */
    private void fillName(final List<StoragePlatformEntrustRelationInfoVo> storageInfoVos) {
        Set<Long> providerIds = new HashSet<>(storageInfoVos.size());
        Set<Long> warehouseIds = new HashSet<>(storageInfoVos.size());
        for (StoragePlatformEntrustRelationInfoVo storageInfoVo : storageInfoVos) {
            final Long warehouseProviderId = storageInfoVo.getWarehouseProviderId();
            if (null != warehouseProviderId) {
                providerIds.add(warehouseProviderId);
            }
            final Long logisticsProviderId = storageInfoVo.getLogisticsProviderId();
            if (null != logisticsProviderId) {
                providerIds.add(logisticsProviderId);
            }
            final Long warehouseId = storageInfoVo.getWarehouseId();
            if (null != warehouseId) {
                warehouseIds.add(storageInfoVo.getWarehouseId());
            }
        }
        final Map<Long, String> cunstNameMap = custDomainService.batchQueryCustInfoByIds(providerIds);
        final Map<Long, String> warehouseNameMap = storageDomainService.batchQueryWarehouseNameByIds(warehouseIds);
        for (StoragePlatformEntrustRelationInfoVo storageInfoVo : storageInfoVos) {
            storageInfoVo.setWarehouseName(warehouseNameMap.get(storageInfoVo.getWarehouseId()));
            storageInfoVo.setWarehouseProviderName(cunstNameMap.get(storageInfoVo.getWarehouseProviderId()));
            storageInfoVo.setLogisticsProviderName(cunstNameMap.get(storageInfoVo.getLogisticsProviderId()));
        }
    }
}
