package com.org.permission.server.domain.crm;


import com.alibaba.fastjson.JSON;
import com.boss.crm.client.feign.CustOrgFeign;
import com.boss.crm.common.dto.customer.CustInfoDTO;
import com.boss.crm.common.dto.customer.CustSubDTO;
import com.boss.crm.common.dto.param.CustOrgParam;
import com.boss.crm.common.dto.param.GenerateRelationParam;
import com.boss.crm.common.query.CustInfoQuery;
import com.boss.crm.common.query.CustOrgQuery;
import com.boss.crm.common.query.IdListQuery;
import com.common.base.enums.BooleanEnum;
import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.common.util.message.RestMsgUtils;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.dto.crm.CustInfoDomainDto;
import com.org.permission.common.dto.crm.LinkerInfoReqParam;
import com.org.permission.common.org.dto.BaseAddressDto;
import com.org.permission.common.org.vo.BaseInfoVo;
import com.org.permission.server.domain.base.DicDomainService;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.dto.param.InitCustEntrustRelParam;
import com.org.permission.server.org.dto.param.QueryArchiveListParam;
import com.org.permission.server.org.enums.CustEntrustTypeEnum;
import com.org.permission.server.org.mapper.CommonOrgMapper;
import com.org.permission.server.org.util.PageUtil;
import com.org.permission.server.org.vo.ArchiveInfoVo;
import com.org.permission.server.utils.NumericUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 客户领域封装
 */
@Service(value = "custDomainService")
public class CustDomainService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustDomainService.class);

    @Resource
    private CommonOrgMapper commonOrgMapper;

    @Resource
    private DicDomainService dicDomainService;
    @Resource
    private CustOrgFeign custOrgFeign;

    private static final String INDUSTRY_TYPE_DIC_CODE = "FPL_CRM_HYLX";


    /**
     * 获取并验证客商信息
     *
     * @param custId 客户ID
     * @return 客商信息
     */
    public CustInfoDomainDto getAndVerifyCust(final Long custId) {
        final int count = commonOrgMapper.countOrgByCustId(custId);
        if (count > 0) {
            LOGGER.warn("current cust:[{}] already binding org.", custId);
            throw new OrgException(OrgErrorCode.CUSTER_ALREADY_BINDING_GROUP_ERROR_CODE, I18nUtils.getMessage("org.domain.crm.customer.bind.org"));
        }

        final CustInfoDomainDto custInfo = getCustInfoById(custId);
        LOGGER.info("cust info,custId:{},info:{}.", custId, custInfo);
        if (custInfo == null) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.domain.crm.customer.data.not.exist"));
        }

        // 入驻状态
        final Integer enterState = custInfo.getEnterState();
        if (BooleanEnum.TRUE.getCode() == enterState) {
            LOGGER.warn("current cust:[{}]already enter bee.", custId);
            throw new OrgException(OrgErrorCode.CUSTER_ALREADY_BINDING_GROUP_ERROR_CODE, I18nUtils.getMessage("org.domain.crm.customer.platform.exist"));
        }

        if (!custInfo.canBindingOrg()) {
            LOGGER.warn("current cust:[{}] state can not bind org.", custId);
            throw new OrgException(OrgErrorCode.CUSTER_STATE_ERROR_CODE, I18nUtils.getMessage("org.domain.crm.customer.unenable"));
        }
        return custInfo;
    }

    /**
     * 获取用户信息（进行集团的初始化流程）
     *
     * @return 用户信息
     */
    public CustInfoDomainDto getCustInfoById(Long custId) {
        CustInfoDTO custInfo;
        try {
            LOGGER.info("查询客商信息请求参数:{}.", custId);
            RestMessage<CustInfoDTO> restMessage = custOrgFeign.getCustInfoById(custId);
            custInfo = RestMsgUtils.retrieveResult(restMessage);
        } catch (Exception ex) {
            LOGGER.warn("查询客商信息请求错误,参数:" + custId, ex);
            throw new OrgException(OrgErrorCode.CRM_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.crm.customer.system.exception"));
        }
        LOGGER.info("查询客商信息响应结果,数据:{},参数:{}.", custInfo, custId);
        if (custInfo == null) {
            throw new OrgException(OrgErrorCode.CRM_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.crm.customer.info.null"));
        }
        // 客商ID为空
        custInfo.setId(custId);
        return wrapCustInfo(custInfo);
    }

    /**
     * 客户信息转换
     *
     * @param custInfo CRM客户信息实体
     * @return 组织客户信息封装实体
     */
    private CustInfoDomainDto wrapCustInfo(final CustInfoDTO custInfo) {
        CustInfoDomainDto custInfoDomain = new CustInfoDomainDto();
        custInfoDomain.setCustId(custInfo.getId());
        custInfoDomain.setSaicFlag(custInfo.getSaicFlag());
        custInfoDomain.setRealName(custInfo.getIdentityName());
        custInfoDomain.setUserCode(custInfo.getCustCode());
        custInfoDomain.setBizTypeId(custInfo.getBusinessType());
        custInfoDomain.setEnterpriseType(custInfo.getEnterpriseType());
        custInfoDomain.setOrgCode(custInfo.getCustSaicOrgCode());
        custInfoDomain.setOrgShortName(custInfo.getShortName());
        custInfoDomain.setCustName(custInfo.getCustName());
        custInfoDomain.setUnitOrg(custInfo.getUnitOrg());

        BaseAddressDto baseAddressDto = new BaseAddressDto();
        //工商地址
        if (Objects.equals(custInfo.getSaicFlag(), BooleanEnum.TRUE.getCode())) {
            baseAddressDto.setInvoiceStreetCode(custInfo.getCustAddressStreetCode());
            baseAddressDto.setInvoiceDistrictCode(custInfo.getCustAddressDistrictCode());
            baseAddressDto.setInvoiceCityCode(custInfo.getCustAddressCityCode());
            baseAddressDto.setInvoiceProvCode(custInfo.getCustAddressProvinceCode());
            baseAddressDto.setAddress(custInfo.getCustSaicRegAddress());
            //存code和名字
            if (!ObjectUtils.isEmpty(custInfo.getCustSaicTradeCode())) {
                custInfoDomain.setIndustryCode(custInfo.getCustSaicTradeCode());
                String name = dicDomainService.getItemName(INDUSTRY_TYPE_DIC_CODE, custInfoDomain.getIndustryCode());
                if (!ObjectUtils.isEmpty(name)) {
                    custInfoDomain.setIndustryName(name);
                }
            }
        } else {
            //非工商地址
            baseAddressDto.setInvoiceStreetCode(custInfo.getStreetCode());
            baseAddressDto.setInvoiceDistrictCode(custInfo.getDistrictCode());
            baseAddressDto.setInvoiceCityCode(custInfo.getCityCode());
            baseAddressDto.setInvoiceProvCode(custInfo.getProvinceCode());
            baseAddressDto.setAddress(custInfo.getAddress());
            if (!ObjectUtils.isEmpty(custInfo.getTradeCode())) {
                custInfoDomain.setIndustryCode(custInfo.getTradeCode());
                String name = dicDomainService.getItemName(INDUSTRY_TYPE_DIC_CODE, custInfoDomain.getIndustryCode());
                if (!ObjectUtils.isEmpty(name)) {
                    custInfoDomain.setIndustryName(name);
                }
            }
        }
        custInfoDomain.setAddressDetail(baseAddressDto);
        custInfoDomain.setNetAddress(custInfo.getCustSaicWebsite());
        custInfoDomain.setEnableState(custInfo.getState());
        custInfoDomain.setFrozenState(custInfo.getFreezeFlag());
        custInfoDomain.setEnterState(custInfo.getPresenceFlag());
        custInfoDomain.setRegisteredCapital(custInfo.getCustSaicRegCapital());
        final Date operateBeginDate = custInfo.getCustSaicOperateBeginDate();
        if (!ObjectUtils.isEmpty(operateBeginDate)) {
            custInfoDomain.setBusinessStartTime(operateBeginDate);
        }

        final Date operateEndDate = custInfo.getCustSaicOperateEndDate();
        if (!ObjectUtils.isEmpty(operateEndDate)) {
            custInfoDomain.setBusinessEndTime(operateEndDate);
        }
        custInfoDomain.setOrgName(custInfo.getCustSaicCompanyName());
        final Date regDate = custInfo.getCustSaicRegDate();
        if (regDate != null) {
            custInfoDomain.setEstablishTime(regDate);
        } else {
            custInfoDomain.setEstablishTime(new Date());
        }
        custInfoDomain.setTaxRegistrationNumber(custInfo.getCustSaicRegCode());
        custInfoDomain.setCreditCode(custInfo.getCustSaicBusinessLicense());
        LinkerInfoReqParam linkerInfo = new LinkerInfoReqParam();
        linkerInfo.setLinker(custInfo.getCustSaicLegalPerson());
        linkerInfo.setPhone(custInfo.getCustSaicRegPhone());
        linkerInfo.setEmail(custInfo.getCustSaicEmail());
        custInfoDomain.setLinkerInfo(linkerInfo);
        custInfoDomain.setOrgShortName(custInfo.getShortName());
        custInfoDomain.setPhone(custInfo.getPhone());
        return custInfoDomain;
    }

    /**
     * 根据客商名字模糊查询客商id
     *
     * @param name
     * @return
     */
    public List<Long> getCustInfoListByName(String name) {
        List<Long> ids = new ArrayList<>();
        try {
            LOGGER.info("根据名字查询客商id{}", name);
            CustOrgQuery custOrgQuery = new CustOrgQuery();
            custOrgQuery.setCustName(name);
            RestMessage<List<CustInfoDTO>> restMessage = custOrgFeign.getCustInfoListByName(custOrgQuery);
            if (!restMessage.isSuccess()) {
                LOGGER.warn("查询客商信息请求失败,参数:{},失败原因:{}", restMessage.getMessage());
                throw new OrgException(OrgErrorCode.CRM_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.crm.customer.select.fail"));
            }
            if (CollectionUtils.isNotEmpty(restMessage.getData())) {
                ids = restMessage.getData().stream().filter(c -> c.getId() != null).map(CustInfoDTO::getId).collect(Collectors.toList());
            }
        } catch (Exception e) {
            LOGGER.warn("查询客商信息请求错误,参数:" + name, e);
            throw new OrgException(OrgErrorCode.CRM_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.crm.customer.system.exception"));
        }
        return ids;
    }

    /**
     * 组织绑定客户信息回写
     *
     * @param custId    客户ID
     * @param groupId   集团ID
     * @param bizUnitId 业务单元ID
     */
    public void custInfoWriteBack(Long custId, Long groupId, Long bizUnitId, Long userId) {
        try {
            CustOrgParam param = new CustOrgParam();
            param.setCustId(custId);
            param.setGroupId(groupId);
            param.setUnitOrgId(bizUnitId);
            param.setUserId(userId);
            LOGGER.info("group binding crm writeback ,custId:{},groupId:{},bizUnitId:{},userId:{}.", custId, groupId, bizUnitId, userId);
            final RestMessage<Integer> restMessage = custOrgFeign.settleOrgById(param);
            if (!restMessage.isSuccess()) {
                LOGGER.info("group binding crm writeback failed,custId:{},groupId:{},bizUnitId:{},userId:{},result:{}.", custId, groupId, bizUnitId, userId, restMessage.getMessage());
                throw new OrgException(OrgErrorCode.CRM_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.crm.customer.back.write.fail"));
            }
        } catch (Exception ex) {
            LOGGER.error("group binding crm writeback error.", ex);
        }
    }

    /**
     * 批量获取客户的名称
     *
     * @param custIds 客户ID
     */
    public Map<Long, String> batchQueryCustInfoByIds(final Set<Long> custIds) {
        try {
            LOGGER.info("batch query crm info ,custIds:{}.", custIds);
            if (CollectionUtils.isEmpty(custIds)) {
                return Collections.emptyMap();
            }
            IdListQuery idListQuery = new IdListQuery();
            idListQuery.setIdList(new ArrayList<>(custIds));
            final RestMessage<List<CustInfoDTO>> restMessage = custOrgFeign.getCustInfoByIds(idListQuery);
            if (!restMessage.isSuccess()) {
                LOGGER.info("batch query crm info failed,custId:{},restMessage:{}.", custIds, JSON.toJSONString(restMessage));
                throw new OrgException(OrgErrorCode.CRM_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.crm.customer.select.fail"));
            }
            Map<Long, String> custMap = restMessage.getData().stream().collect(Collectors.toMap(CustInfoDTO::getId, CustInfoDTO::getCustName));
            return custMap;
        } catch (Exception ex) {
            LOGGER.error("batch query crm info error.", ex);
            throw new OrgException(OrgErrorCode.CRM_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.crm.customer.system.exception"));
        }
    }


    /**
     * 查询可用绑定客户集合
     *
     * @param reqParam 客户查询请求参数
     */
    public PageInfo<CustInfoDto> queryCustList(final QueryCustReqParam reqParam) {
        LOGGER.info("query enable crm info list ,param:{}.", reqParam);
        final CustInfoQuery query = buildQueryCustCondition(reqParam);
        RestMessage<PageInfo<CustInfoDTO>> restMessage;
        try {
            restMessage = custOrgFeign.getListOfCustInfoByCondition(query);
        } catch (Exception ex) {
            LOGGER.error("query enable crm info list error.", ex);
            throw new OrgException(OrgErrorCode.CRM_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.crm.customer.system.exception"));
        }
        if (!restMessage.isSuccess()) {
            LOGGER.info("query enable crm info failed,reqParam:{},restmessage:{}.", reqParam, JSON.toJSONString(restMessage));
            throw new OrgException(OrgErrorCode.CRM_SYSTEM_ERROR_CODE,  I18nUtils.getMessage("org.domain.crm.customer.select.fail"));
        }
        final PageInfo<CustInfoDTO> pageRestMessage = restMessage.getData();
        if (null == pageRestMessage) {
            return null;
        }
        final long total = pageRestMessage.getTotal();
        if (total == 0) {
            return new PageInfo<>();
        }
        return PageUtil.convert(pageRestMessage, item -> {
            CustInfoDto custInfoDto = new CustInfoDto();
            custInfoDto.setCustId(item.getId());
            custInfoDto.setCustName(item.getCustName());
            custInfoDto.setBizTypeId(item.getBusinessType());
            return custInfoDto;
        });
    }

    private CustInfoQuery buildQueryCustCondition(final QueryCustReqParam reqParam) {
        CustInfoQuery query = new CustInfoQuery();
        query.setCustName(reqParam.getCustName());
        query.setPresenceFlag(reqParam.getPresenceFlag());
        query.addBusinessTypes(reqParam.getBusinessType());
        final Integer state = reqParam.getState();
        query.setState(state == null ? null : state);
        query.setFreezeFlag(BooleanEnum.FALSE.getCode());
        query.setPageNum(reqParam.getPageNum());
        query.setPageSize(reqParam.getPageSize());
        return query;
    }

    /**
     * 查询CRM 档案列表
     *
     * @param reqParam 查询请求参数
     * @return 档案信息列表
     */
    public PageInfo<ArchiveInfoVo> getCustArchiveList(QueryArchiveListParam reqParam) {
        RestMessage<PageInfo<CustSubDTO>> restMessage;
        try {
            CustOrgQuery custOrgQuery = new CustOrgQuery();
            custOrgQuery.setGroupId(reqParam.getGroupId());
            custOrgQuery.setCustType(reqParam.getArchiveType());
            custOrgQuery.setCustName(reqParam.getArchiveName());
            custOrgQuery.setPageNum(reqParam.getPageNum());
            custOrgQuery.setPageSize(reqParam.getPageSize());
            restMessage = custOrgFeign.getListOfCustSubByCondition(custOrgQuery);
        } catch (Exception ex) {
            LOGGER.error("query cust archive list error.", ex);
            throw new OrgException(OrgErrorCode.CRM_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.crm.customer.system.exception"));
        }
        if (!restMessage.isSuccess()) {
            LOGGER.info("query cust archive list failed,reqParam:{},restMessage:{}.", reqParam, JSON.toJSONString(restMessage));
            throw new OrgException(OrgErrorCode.CRM_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.crm.customer.select.fail"));
        }

        final PageInfo<CustSubDTO> pageRestMessage = restMessage.getData();

        return PageUtil.convert(pageRestMessage, item -> {
            ArchiveInfoVo archiveInfoVo = new ArchiveInfoVo();
            archiveInfoVo.setId(item.getId());
            archiveInfoVo.setName(item.getCustName());
            return archiveInfoVo;
        });
    }

    /**
     * 新增全局委托关系时，触发客商操作
     *
     * @param param delegationType 委托类型
     *              mainCustId     主客商 ID
     *              entrustCustId  委托客商 ID
     *              operateUser    操作人ID
     */
    public void initCustEntrustRel(InitCustEntrustRelParam param) {
        GenerateRelationParam generateRelationParam = new GenerateRelationParam();
        generateRelationParam.setDelegationType(param.getDelegationType());
        generateRelationParam.setOperateUserName(param.getOperateUserName());
        generateRelationParam.setOperateUser(param.getOperateUser());
        generateRelationParam.setState(param.getState());
        if (param.getDelegationType() == CustEntrustTypeEnum.MARKET.getType()) {
            generateRelationParam.setClientCustId(param.getMainCustId());
            generateRelationParam.setClientUnitOrg(param.getMainBUId());
            generateRelationParam.setSupplierCustId(param.getEntrustCustId());
            generateRelationParam.setSupplierUnitOrg(param.getEntrustBUId());
        } else if (param.getDelegationType() == CustEntrustTypeEnum.STORAGE.getType()) {
            generateRelationParam.setClientCustId(param.getWarehouseProviderId());
            generateRelationParam.setClientUnitOrg(param.getStockOrgId());
            generateRelationParam.setSupplierCustId(param.getLogisticsProviderId());
            generateRelationParam.setSupplierUnitOrg(param.getLogisticsOrgId());
        } else if (param.getDelegationType() == CustEntrustTypeEnum.LOGISTICS.getType()) {
            generateRelationParam.setClientCustId(param.getLogisticsProviderId());
            generateRelationParam.setClientUnitOrg(param.getLogisticsOrgId());
            generateRelationParam.setSupplierCustId(param.getRelevanceLogisticsProviderId());
            generateRelationParam.setSupplierUnitOrg(param.getRelevanceLogisticsOrgId());
        } else {
            return;
        }
        custOrgFeign.generateRelationForDelegation(generateRelationParam);
    }

    public RestMessage getListByOrgId(Long orgId) {
        LOGGER.info("getListByOrgId:orgId" + orgId);
        // TODO 查询客商接口
        return RestMessage.doSuccess(new ArrayList<>());
    }

    public List<CustSubDTO> getListOfCustSubByUnitOrg(List<Long> unitOrgs) {
        LOGGER.info("getListOfCustSubByUnitOrg:", JSON.toJSONString(unitOrgs));
        CustOrgQuery custOrgQuery = new CustOrgQuery();
        custOrgQuery.setUnitOrgIds(unitOrgs);
        RestMessage<List<CustSubDTO>> restMessage = custOrgFeign.getListOfCustSubByUnitOrg(custOrgQuery);
        return restMessage.getData();
    }

    /**
     * 填充客商名字
     *
     * @param voList 基础展示信息
     */
    public void fillCustName(List<? extends BaseInfoVo> voList) {
        if (CollectionUtils.isEmpty(voList)) {
            return;
        }

        Set<Long> custIds = new HashSet<>();
        for (BaseInfoVo baseInfoVo : voList) {
            final Long custId = baseInfoVo.getCustId();
            if (!NumericUtil.nullOrlessThanOrEqualToZero(custId)) {
                custIds.add(custId);
            }
        }

        final Map<Long, String> custIdNameMap = batchQueryCustInfoByIds(custIds);
        for (BaseInfoVo baseInfoVo : voList) {
            final Long custId = baseInfoVo.getCustId();
            if (!NumericUtil.nullOrlessThanOrEqualToZero(custId)) {
                final String custName = custIdNameMap.get(custId);
                if (!StringUtils.isEmpty(custName)) {
                    baseInfoVo.setCustName(custName);
                }
            }
        }
    }


    //根据客商查询对应的业务单元
    public CustInfoDto getCustInfoByOrgId(Integer orgId) {
        return null;
    }

}
