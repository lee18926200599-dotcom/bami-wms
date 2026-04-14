package com.org.permission.server.org.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.common.language.util.I18nUtils;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.enums.org.FunctionTypeEnum;
import com.org.permission.common.org.dto.*;
import com.org.permission.common.org.dto.func.*;
import com.org.permission.common.org.param.QueryGroupBUByFuncReqParam;
import com.org.permission.common.org.param.*;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.server.domain.base.CodeDomainService;
import com.org.permission.server.domain.crm.CustDomainService;
import com.org.permission.server.domain.permission.PermissionDomainService;
import com.org.permission.server.domain.user.UserDomainService;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.*;
import com.org.permission.server.org.builder.BizUnitEntrustRelationshipBuilder;
import com.org.permission.server.org.dto.BusinessLineOrgDto;
import com.org.permission.server.org.dto.param.*;
import com.org.permission.server.org.enums.CustTypeEnum;
import com.org.permission.server.org.enums.OrgTypeEnum;
import com.org.permission.server.org.mapper.*;
import com.org.permission.server.org.service.BizUnitService;
import com.org.permission.server.org.service.BusinessLineOrgService;
import com.org.permission.server.org.service.DepartmentService;
import com.org.permission.server.org.service.event.BUFuncCascadeModifyEntrustEvent;
import com.org.permission.server.org.service.event.CascadeCreateFinanceEntrustRelEvent;
import com.org.permission.server.org.service.event.OrgOpGroupStatusVerify;
import com.org.permission.server.org.service.verify.BUCircleQuoteVerify;
import com.org.permission.server.org.service.verify.BUEnableOrgFuncUpdateVerify;
import com.org.permission.server.org.service.verify.DeleteBizUnitVerify;
import com.org.permission.server.org.vo.BUVersionInfoVo;
import com.org.permission.server.utils.NumericUtil;
import com.boss.crm.common.dto.customer.CustSubDTO;
import com.common.base.entity.CurrentUser;
import com.common.base.enums.BooleanEnum;
import com.common.base.enums.StateEnum;
import com.common.framework.user.FplUserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 业务单元管理服务业务逻辑处理
 */
@Component(value = "bizUnitService")
public class BizUnitServiceImpl implements BizUnitService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BizUnitServiceImpl.class);

    @Value("${base_org_bizunit_code_biz_code}")
    private String bizUnitCodeBizCode;

    @Resource
    private CodeDomainService codeDomainService;

    @Resource
    private BizUnitEntrustRelationshipBuilder bizUnitEntrustRelationshipBuilder;

    @Resource
    private BizUnitMapper bizUnitMapper;

    @Resource
    private OrgFunctionMapper orgFunctionMapper;
    @Resource
    private DepartmentService departmentService;

    @Resource
    private CommonOrgMapper commonOrgMapper;

    @Resource
    private CommonEntrustRelationMapper commonEntrustRelationMapper;

    @Resource
    private BizUnitVersionMapper bizUnitVersionMapper;

    @Resource
    private CorporateOrgFuncMapper corporateOrgFuncMapper;

    @Resource
    private CorporateOrgFuncVersionMapper corporateOrgFuncVersionMapper;

    @Resource
    private FinanceOrgFuncMapper financeOrgFuncMapper;

    @Resource
    private FinanceOrgFuncVersionMapper financeOrgFuncVersionMapper;

    @Resource
    private PurchaseOrgFuncMapper purchaseOrgFuncMapper;

    @Resource
    private PurchaseOrgFuncVersionMapper purchaseOrgFuncVersionMapper;

    @Resource
    private PurchaseEntrustRelationMapper purchaseEntrustRelationMapper;

    @Resource
    private SaleOrgFuncMapper saleOrgFuncMapper;

    @Resource
    private SaleOrgFuncVersionMapper saleOrgFuncVersionMapper;

    @Resource
    private StorageOrgFuncMapper storageOrgFuncMapper;

    @Resource
    private StorageOrgFuncVersionMapper storageOrgFuncVersionMapper;

    @Resource
    private StorageGroupEntrustRelationMapper storageGroupEntrustRelationMapper;

    @Resource
    private LogisticsOrgFuncMapper logisticsOrgFuncMapper;

    @Resource
    private PlatformOrgFuncMapper platformOrgFuncMapper;

    @Resource
    private LabourOrgFuncMapper labourOrgFuncMapper;

    @Resource
    private BankingOrgFuncMapper bankingOrgFuncMapper;

    @Resource
    private LogisticsOrgFuncVersionMapper logisticsOrgFuncVersionMapper;

    @Resource
    private FinanceEntrustRelationMapper financeEntrustRelationMapper;


    @Resource
    private CustDomainService custDomainService;

    @Resource
    private DeleteBizUnitVerify deleteBizUnitVerify;

    @Resource
    private BUEnableOrgFuncUpdateVerify buEnableOrgFuncUpdateVerify;

    @Resource
    private BUCircleQuoteVerify buCircleQuoteVerify;

    @Resource
    private PermissionDomainService permissionDomainService;

    @Resource
    private OrgOpGroupStatusVerify orgOpGroupStatusVerify;

    @Resource
    private BUFuncCascadeModifyEntrustEvent buFuncCascadeModifyEntrustEvent;

    @Resource
    private CascadeCreateFinanceEntrustRelEvent cascadeCreateFinanceEntrustRelEvent;


    @Resource
    private UserDomainService userDomainService;
    @Resource
    private BusinessLineOrgService businessLineOrgService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void addBizUnit(AddBizUnitReqParam reqParam) {
        final Long groupId = reqParam.getGroupId();
        // 获取集团资源，确保集团内业务单元名称唯一
        orgOpGroupStatusVerify.verify(groupId);

        final OrgBean orgBean = bizUnitMapper.queryAvailableBUByNameLock(reqParam.getOrgName(), groupId);
        if (null != orgBean) {
            LOGGER.info("add biz unit error,reqParam:" + reqParam);
            throw new OrgException(OrgErrorCode.NAME_CONFLICT_ERROR_CODE, I18nUtils.getMessage("org.service.impl.bizunit.orgname.exist"));
        }
        reqParam.setCreatedBy(FplUserUtil.getUserId());
        reqParam.setCreatedName(FplUserUtil.getUserName());
        final int defaultStatus = StateEnum.CREATE.getCode();
        BizUnitWithFuncDetailBean bizUnitWithFuncDetailBean = new BizUnitWithFuncDetailBean();
        BeanUtils.copyProperties(reqParam, bizUnitWithFuncDetailBean);
        bizUnitWithFuncDetailBean.setState(defaultStatus);
        bizUnitWithFuncDetailBean.setInitFlag(BooleanEnum.FALSE.getCode());
        bizUnitWithFuncDetailBean.setCreatedDate(new Date());
        bizUnitWithFuncDetailBean.setCreatedBy(reqParam.getCreatedBy());
        bizUnitWithFuncDetailBean.setCreatedName(reqParam.getCreatedName());
        bizUnitWithFuncDetailBean.setOrgType(OrgTypeEnum.ORGANIZATION.getIndex());
        bizUnitWithFuncDetailBean.setMainOrgFlag(BooleanEnum.FALSE.getCode());
        bizUnitWithFuncDetailBean.setOrgCode(codeDomainService.getOrgCode());
        bizUnitWithFuncDetailBean.setIndustryCode(reqParam.getIndustryCode());
        bizUnitWithFuncDetailBean.setIndustryName(reqParam.getIndustryName());

        LOGGER.info("add biz unit to db, bean:{}.", bizUnitWithFuncDetailBean);
        bizUnitMapper.addBizUnit(bizUnitWithFuncDetailBean);
        final Long buId = bizUnitWithFuncDetailBean.getId();
        final String buName = bizUnitWithFuncDetailBean.getOrgName();
        //传入业务单元id,方便后期构建财务委托关系
        reqParam.setId(buId);
        Long[] orgFuncIds = writeOrgFuncs(reqParam, buId, StateEnum.CREATE);
        reqParam.setId(buId);
        bizUnitEntrustRelationshipBuilder.buGenerateEntrustRelation(reqParam, orgFuncIds, defaultStatus);
        // 同步业务线信息
        BusinessLineOrgDto businessLineOrgDto = new BusinessLineOrgDto();
        businessLineOrgDto.setLineIdList(reqParam.getLineIdList());
        businessLineOrgDto.setGroupId(reqParam.getGroupId());
        businessLineOrgDto.setOrgId(buId);
        businessLineOrgDto.setCreatedBy(reqParam.getCreatedBy());
        businessLineOrgDto.setCreatedDate(new Date());
        businessLineOrgDto.setCreatedName(reqParam.getCreatedName());
        businessLineOrgDto.setModifiedBy(reqParam.getCreatedBy());
        businessLineOrgDto.setModifiedDate(new Date());
        businessLineOrgDto.setModifiedName(reqParam.getCreatedName());
        businessLineOrgService.saveOrUpdate(businessLineOrgDto);
    }

    /**
     * 写入组织职能
     *
     * @param reqParam 新增业务单元请求参数
     * @param buId     业务单元 ID
     * @return 组织职能 ID(采购;销售;仓储;物流)
     */
    protected Long[] writeOrgFuncs(final AddBizUnitReqParam reqParam, final Long buId, StateEnum funcStautsEnum) {
        final CorporateOrgFuncBean corporate = reqParam.getCorporate();
        final Date createTime = new Date();

        if (reqParam.hasCorporationFunc()) {
            corporate.setOrgId(buId);
            corporate.setCreatedBy(reqParam.getCreatedBy());
            corporate.setCreatedName(reqParam.getCreatedName());
            corporate.setState(funcStautsEnum.getCode());
            corporate.setCreatedDate(createTime);
            corporateOrgFuncMapper.addCorporateFunc(corporate);
        }
        if (reqParam.hasFinanceFunc()) {
            final FinanceOrgFuncBean finance = reqParam.getFinance();
            finance.setOrgId(buId);
            finance.setCreatedBy(reqParam.getCreatedBy());
            finance.setCreatedName(reqParam.getCreatedName());
            finance.setCreatedDate(createTime);
            finance.setState(funcStautsEnum.getCode());
            financeOrgFuncMapper.addFinanceFunc(finance);
        }

        Long[] orgFuncIds = new Long[7];
        if (reqParam.hasPurchaseFunc()) {
            final PurchaseOrgFuncBean purchase = reqParam.getPurchase();
            purchase.setOrgId(buId);
            purchase.setCreatedBy(reqParam.getCreatedBy());
            purchase.setState(funcStautsEnum.getCode());
            purchase.setCreatedName(reqParam.getCreatedName());
            purchase.setCreatedDate(createTime);
            if (reqParam.hasFinanceFunc()) {
                purchase.setPayOrgId(buId);
                purchase.setSettleOrgId(buId);
            }
            if (reqParam.hasStorageFunc()) {
                purchase.setStockOrgId(buId);
            }
            purchaseOrgFuncMapper.addPurchaseFunc(purchase);
            orgFuncIds[0] = purchase.getId();
        }
        if (reqParam.hasSaleFunc()) {
            final SaleOrgFuncBean sale = reqParam.getSale();
            sale.setOrgId(buId);
            sale.setCreatedBy(reqParam.getCreatedBy());
            sale.setState(funcStautsEnum.getCode());
            sale.setCreatedDate(createTime);
            sale.setCreatedName(reqParam.getCreatedName());
            if (reqParam.hasFinanceFunc()) {
                sale.setReceiveOrgId(buId);
                sale.setSettleOrgId(buId);
            }
            if (reqParam.hasStorageFunc()) {
                sale.setStockOrgId(buId);
            }
            saleOrgFuncMapper.addSaleFunc(sale);
            orgFuncIds[1] = sale.getId();
        }
        if (reqParam.hasStorageFunc()) {
            final StorageOrgFuncBean storage = reqParam.getStorage();
            storage.setOrgId(buId);
            storage.setCreatedBy(reqParam.getCreatedBy());
            storage.setState(funcStautsEnum.getCode());
            storage.setCreatedDate(createTime);
            storage.setCreatedName(reqParam.getCreatedName());
            if (reqParam.hasFinanceFunc()) {
                storage.setAccountOrgId(buId);
                storage.setSettleOrgId(buId);
            }
            if (reqParam.hasLogisticFunc()) {
                storage.setLogisticsOrgId(buId);
            }
            storageOrgFuncMapper.addStorageFunc(storage);
            orgFuncIds[2] = storage.getId();
        }
        if (reqParam.hasLogisticFunc()) {
            final LogisticsOrgFuncBean logistics = reqParam.getLogistics();
            logistics.setOrgId(buId);
            logistics.setCreatedBy(reqParam.getCreatedBy());
            logistics.setState(funcStautsEnum.getCode());
            logistics.setCreatedDate(createTime);
            logistics.setCreatedName(reqParam.getCreatedName());
            if (reqParam.hasFinanceFunc()) {
                logistics.setAccountOrgId(buId);
                logistics.setSettleOrgId(buId);
            }
            logisticsOrgFuncMapper.addLogisticsFunc(logistics);
            orgFuncIds[3] = logistics.getId();
        }

        if (reqParam.hasPlatformFunc()) {
            final PlatformOrgFuncBean platform = reqParam.getPlatform();
            platform.setOrgId(buId);
            platform.setCreatedBy(reqParam.getCreatedBy());
            platform.setState(funcStautsEnum.getCode());
            platform.setCreatedDate(createTime);
            platform.setCreatedName(reqParam.getCreatedName());
            platformOrgFuncMapper.addPlatformFunc(platform);
            orgFuncIds[4] = platform.getId();
        }

        if (reqParam.hasLabourFunc()) {
            final LabourOrgFuncBean labour = reqParam.getLabour();
            labour.setOrgId(buId);
            labour.setCreatedBy(reqParam.getCreatedBy());
            labour.setState(funcStautsEnum.getCode());
            labour.setCreatedDate(createTime);
            labour.setCreatedName(reqParam.getCreatedName());
            labourOrgFuncMapper.addLabourFunc(labour);
            orgFuncIds[5] = labour.getId();
        }

        if (reqParam.hasBankingFunc()) {
            final BankingOrgFuncBean banking = reqParam.getBanking();
            banking.setOrgId(buId);
            banking.setCreatedBy(reqParam.getCreatedBy());
            banking.setCreatedName(reqParam.getCreatedName());
            banking.setState(funcStautsEnum.getCode());
            banking.setCreatedDate(createTime);
            banking.setSettleOrgId(buId);
            banking.setAccountOrgId(buId);
            bankingOrgFuncMapper.addBankingFunc(banking);
            orgFuncIds[6] = banking.getId();
        }
        return orgFuncIds;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void saveNewVersion(SaveNewVersionBUParam reqParam) {
        // 更新校验上级业务 + 公司 + 销售组织是否存在循环引用
        final Map<Long, BizUnitWithFuncDetailBean> relBUs = buCircleQuoteVerify.verify(reqParam);
        final Long buId = reqParam.getId();
        final List<Integer> orgFuncTypes = orgFunctionMapper.queryOrgFunctionsByOrgId(buId);
        final BizUnitWithFuncDetailBean buFunc = relBUs.get(buId);
        // 启用状态下，业务单元的组织职能部允许减少
        if (StateEnum.ENABLE.getCode() == buFunc.getState()) {
            buEnableOrgFuncUpdateVerify.verify(orgFuncTypes, reqParam);
        }
        bizUnitVersionMapper.saveBizUnitNewVersion(reqParam);

        if (reqParam.hasCorporationFunc()) {
            final CorporateOrgFuncBean corporateOrgFunc = reqParam.getCorporate();
            corporateOrgFuncVersionMapper.addCorporateFunc(corporateOrgFunc, reqParam);
        }
        if (reqParam.hasCorporationFunc()) {
            final FinanceOrgFuncBean finance = reqParam.getFinance();
            financeOrgFuncVersionMapper.addFinanceFunc(finance, reqParam);
        }
        if (reqParam.hasPurchaseFunc()) {
            final PurchaseOrgFuncBean purchase = reqParam.getPurchase();
            purchaseOrgFuncVersionMapper.addPurchaseFunc(purchase, reqParam);
        }
        if (reqParam.hasSaleFunc()) {
            final SaleOrgFuncBean saleOrgFunc = reqParam.getSale();
            saleOrgFuncVersionMapper.addSaleFunc(saleOrgFunc, reqParam);
        }
        if (reqParam.hasStorageFunc()) {
            final StorageOrgFuncBean storage = reqParam.getStorage();
            storageOrgFuncVersionMapper.addStorageFunc(storage, reqParam);
        }
        if (reqParam.hasLogisticFunc()) {
            final LogisticsOrgFuncBean logistics = reqParam.getLogistics();
            logisticsOrgFuncVersionMapper.addLogisticsFunc(logistics, reqParam);
        }
    }

    @Override
    @Transactional
    public void deleteBizUnit(KeyOperateParam reqParam) {

        final Long buId = reqParam.getId();
        final OrgBean orgBean = bizUnitMapper.getBizUnitStateByIdLock(buId);
        if (Objects.isNull(orgBean)) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.impl.bizunit.org.not.exist"));
        }

        orgOpGroupStatusVerify.verify(orgBean.getGroupId());

        // 验证业务单元是否可以删除
        deleteBizUnitVerify.verify(orgBean);

        final Long userId = reqParam.getUserId();


        // 级联删除部门
        departmentService.deleteDepartmentByBuId(buId, userId);

        final Date updateTime = new Date();
        final int modifyState = BooleanEnum.TRUE.getCode();

        // 删除当前业务单元
        commonOrgMapper.deleteOrgById(buId, userId);
        // 级联删除由该业务单元产生的委托关系
        commonEntrustRelationMapper.delEntrustRelationByOrgId(buId, userId, updateTime, modifyState, null);


        // 级联删除该业务单元的组织职能
        orgFunctionMapper.modifyFuncStateByBUId(buId, userId, modifyState, Arrays.asList(1, 2, 3), updateTime);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void enableBizUnit(EnableOperateParam reqParam) {

        final Long buId = reqParam.getId();
        final BizUnitWithFuncDetailBean orgBean = bizUnitMapper.queryBizUnitByIdLock(buId);
        if (orgBean == null) {
            LOGGER.warn("enable biz unit not exist,bizUnitId:{}.", reqParam.getId());
            throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.service.impl.bizunit.org.not.exist"));
        }

        orgOpGroupStatusVerify.verify(orgBean.getGroupId());

        final Integer enableState = reqParam.getState();
        final Long userId = reqParam.getUserId();


        if (StateEnum.ENABLE.getCode() == enableState) {
            enable(orgBean, userId);
        }

        if (StateEnum.DISABLE.getCode() == enableState) {
            disableBU(orgBean, userId);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void updateBizUnit(UpdateBizUnitReqParam reqParam) {
        // 更新校验上级业务 + 公司 + 销售组织是否存在循环引用
        final Map<Long, BizUnitWithFuncDetailBean> relBUs = buCircleQuoteVerify.verify(reqParam);

        final Long buId = reqParam.getId();
        BizUnitWithFuncDetailBean buBean = relBUs.get(buId);

        // 锁集团，验证集团 状态
        orgOpGroupStatusVerify.verify(buBean.getGroupId());

        // 当前业务单元具有的所有组织职能
        final List<Integer> orgFuncTypes = orgFunctionMapper.queryOrgFunctionsByOrgId(buId);

        // 已经初始化状态下，业务单元的组织职能部允许减少
        if (BooleanEnum.TRUE.getCode().equals(buBean.getInitFlag())) {
            buEnableOrgFuncUpdateVerify.verify(orgFuncTypes, reqParam);
        }
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        final Date updateTime = new Date();
        final Long updateUser = currentUser.getUserId();
        final String updateName = currentUser.getUserName();

        UpdateBizUnitWriteBean updateBizUnitWriteBean = new UpdateBizUnitWriteBean();
        BeanUtils.copyProperties(reqParam, updateBizUnitWriteBean);
        updateBizUnitWriteBean.setAddressDetail(reqParam.getAddressDetail());
        updateBizUnitWriteBean.setModifiedDate(updateTime);
        updateBizUnitWriteBean.setId(reqParam.getId());
        updateBizUnitWriteBean.setModifiedBy(reqParam.getModifiedBy());
        bizUnitMapper.updateBizUnit(updateBizUnitWriteBean);

        final Integer buState = buBean.getState();
        //未启用状态，可以对组织职能进行删除操作
        final List<Integer> delOrgFuncTypes = new ArrayList<>();
        // 以下各组织职能，具有ID值，认为更新操作；无ID值认为新增操作；
        if (reqParam.hasCorporationFunc()) {
            final CorporateOrgFuncBean corporateOrgFunc = reqParam.getCorporate();
            corporateOrgFunc.setState(buState);
            corporateOrgFunc.setOrgId(reqParam.getId());
            if (!orgFuncTypes.contains(FunctionTypeEnum.CORPORATION.getIndex())) {// 无法人，则新增
                corporateOrgFunc.setCreatedDate(updateTime);
                corporateOrgFunc.setCreatedBy(updateUser);
                corporateOrgFuncMapper.addCorporateFunc(corporateOrgFunc);
            } else {// 有法人，则修改
                corporateOrgFunc.setModifiedDate(updateTime);
                corporateOrgFunc.setModifiedBy(updateUser);
                corporateOrgFuncMapper.updateCorporateFunc(corporateOrgFunc);
            }
        } else {
            if (!CollectionUtils.isEmpty(orgFuncTypes) && orgFuncTypes.contains(FunctionTypeEnum.CORPORATION.getIndex())) {
                delOrgFuncTypes.add(FunctionTypeEnum.CORPORATION.getIndex());
            }
        }
        // 财务
        if (reqParam.hasFinanceFunc()) {
            final FinanceOrgFuncBean financeOrgFunc = reqParam.getFinance();
            financeOrgFunc.setState(buState);
            financeOrgFunc.setOrgId(reqParam.getId());
            if (!orgFuncTypes.contains(FunctionTypeEnum.FINANCE.getIndex())) {// 无财务，则新增
                financeOrgFunc.setCreatedDate(updateTime);
                financeOrgFunc.setCreatedBy(updateUser);
                financeOrgFuncMapper.addFinanceFunc(financeOrgFunc);
            } else {// 有财务，则修改
                financeOrgFunc.setModifiedDate(updateTime);
                financeOrgFunc.setModifiedBy(updateUser);
                financeOrgFuncMapper.updateFinanceFunc(financeOrgFunc);
            }
        } else if (!CollectionUtils.isEmpty(orgFuncTypes) && orgFuncTypes.contains(FunctionTypeEnum.FINANCE.getIndex())) {
            delOrgFuncTypes.add(FunctionTypeEnum.FINANCE.getIndex());
            financeEntrustRelationMapper.cleanFinanceEntrustRelationByFinanceOrgId(buId);
        }// 原有，删除

        final Long groupId = buBean.getGroupId();
        // 采购委托关系，新增或修改需级联更新集团内采购委托关系
        Set<FinanceEntrustRelationBean> financeEntrustRelationBeans = new HashSet<>(4);
        if (reqParam.hasPurchaseFunc()) {
            final PurchaseOrgFuncBean purchaseOrgFunc = reqParam.getPurchase();
            purchaseOrgFunc.setState(buState);
            if (!orgFuncTypes.contains(FunctionTypeEnum.PURCHASE.getIndex())) {
                purchaseOrgFunc.setOrgId(reqParam.getId());
                purchaseOrgFunc.setCreatedDate(updateTime);
                purchaseOrgFunc.setCreatedBy(updateUser);
                purchaseOrgFunc.setSettleOrgId(purchaseOrgFunc.getSettleOrgId());
                purchaseOrgFunc.setStockOrgId(purchaseOrgFunc.getStockOrgId());
                purchaseOrgFunc.setPayOrgId(purchaseOrgFunc.getPayOrgId());
                purchaseOrgFuncMapper.addPurchaseFunc(purchaseOrgFunc);
                final Long purchaseOrgFuncId = purchaseOrgFunc.getId();
                // 新增采购组织职能，级联新增采购委托关系
                final FinanceEntrustRelationBean financeEntrustRelationBean = bizUnitEntrustRelationshipBuilder.writePurchaseEntrustRelation(reqParam, groupId, purchaseOrgFuncId, buState, Boolean.FALSE, "BU新增采购职能级联财务");
                //当财务委托关系不存在时候新增财务委托关系
                if (!queryFinanceEntrustExsit(financeEntrustRelationBean)) {
                    financeEntrustRelationBeans.add(financeEntrustRelationBean);
                }
            } else {
                purchaseOrgFunc.setOrgId(reqParam.getId());
                purchaseOrgFunc.setModifiedDate(updateTime);
                purchaseOrgFunc.setModifiedBy(updateUser);
                purchaseOrgFunc.setSettleOrgId(purchaseOrgFunc.getSettleOrgId());
                purchaseOrgFunc.setStockOrgId(purchaseOrgFunc.getStockOrgId());
                purchaseOrgFunc.setPayOrgId(purchaseOrgFunc.getPayOrgId());
                purchaseOrgFuncMapper.updatePurchaseFunc(purchaseOrgFunc);
                buFuncCascadeModifyEntrustEvent.purchaseOrgFuncUpdate(purchaseOrgFunc, groupId, updateUser);
            }
        } else if (!CollectionUtils.isEmpty(orgFuncTypes) && orgFuncTypes.contains(FunctionTypeEnum.PURCHASE.getIndex())) {
            delOrgFuncTypes.add(FunctionTypeEnum.PURCHASE.getIndex());
        }

        // 销售委托关系，新增或修改需级联更新集团内采购委托关系
        if (reqParam.hasSaleFunc()) {
            final SaleOrgFuncBean saleOrgFunc = reqParam.getSale();
            saleOrgFunc.setState(buState);
            if (!orgFuncTypes.contains(FunctionTypeEnum.SALE.getIndex())) {
                saleOrgFunc.setOrgId(reqParam.getId());
                saleOrgFunc.setCreatedDate(updateTime);
                saleOrgFunc.setCreatedBy(updateUser);
                saleOrgFunc.setStockOrgId(saleOrgFunc.getStockOrgId());
                saleOrgFunc.setReceiveOrgId(saleOrgFunc.getReceiveOrgId());
                saleOrgFunc.setSettleOrgId(saleOrgFunc.getSettleOrgId());
                saleOrgFuncMapper.addSaleFunc(saleOrgFunc);
                final Long saleOrgFuncId = saleOrgFunc.getId();
                // 新增销售组织职能，级联新增销售委托关系
                final FinanceEntrustRelationBean financeEntrustRelationBean = bizUnitEntrustRelationshipBuilder.writeSaleEntrustRelation(reqParam, groupId, saleOrgFuncId, buState, Boolean.FALSE, "BU新增销售职能级联财务");
                //当财务委托关系不存在时候新增财务委托关系
                if (!queryFinanceEntrustExsit(financeEntrustRelationBean)) {
                    financeEntrustRelationBeans.add(financeEntrustRelationBean);
                }
            } else {
                saleOrgFunc.setOrgId(reqParam.getId());
                saleOrgFunc.setModifiedDate(updateTime);
                saleOrgFunc.setModifiedBy(updateUser);
                saleOrgFunc.setStockOrgId(saleOrgFunc.getStockOrgId());
                saleOrgFunc.setReceiveOrgId(saleOrgFunc.getReceiveOrgId());
                saleOrgFunc.setSettleOrgId(saleOrgFunc.getSettleOrgId());
                saleOrgFuncMapper.updateSaleFunc(saleOrgFunc);
                buFuncCascadeModifyEntrustEvent.saleOrgFuncUpdate(saleOrgFunc, groupId, updateUser);
            }
        } else if (!CollectionUtils.isEmpty(orgFuncTypes) && orgFuncTypes.contains(FunctionTypeEnum.SALE.getIndex())) {
            delOrgFuncTypes.add(FunctionTypeEnum.SALE.getIndex());
            // 删除销售组织能，级联删除采购委托关系
            purchaseEntrustRelationMapper.deleteEntrustRelationByPurchaseOrgId(buId);
        }
        // 仓储
        if (reqParam.hasStorageFunc()) {
            final StorageOrgFuncBean storageOrgFunc = reqParam.getStorage();
            storageOrgFunc.setState(buState);
            storageOrgFunc.setOrgId(reqParam.getId());
            if (!orgFuncTypes.contains(FunctionTypeEnum.STORAGE.getIndex())) {
                storageOrgFunc.setCreatedDate(updateTime);
                storageOrgFunc.setCreatedBy(updateUser);
                storageOrgFuncMapper.addStorageFunc(storageOrgFunc);
                final Long storageOrgFuncId = storageOrgFunc.getId();
                // 新增仓储组织职能，建立新增集团内仓储委托关系
                final FinanceEntrustRelationBean financeEntrustRelationBean = bizUnitEntrustRelationshipBuilder.writeGroupStorageEntrustRelation(reqParam, groupId, storageOrgFuncId, buState, Boolean.FALSE, "BU新增仓储职能级联财务");
                //当财务委托关系不存在时候新增财务委托关系
                if (!queryFinanceEntrustExsit(financeEntrustRelationBean)) {
                    financeEntrustRelationBeans.add(financeEntrustRelationBean);
                }
            } else {
                storageOrgFunc.setModifiedDate(updateTime);
                storageOrgFunc.setModifiedBy(updateUser);
                storageOrgFuncMapper.updateStorageFunc(storageOrgFunc);
                // 更新仓储组织职能，级联更新内仓储委托关系
                buFuncCascadeModifyEntrustEvent.storageOrgFuncUpdate(storageOrgFunc, groupId, updateUser);
            }
        } else if (!CollectionUtils.isEmpty(orgFuncTypes) && orgFuncTypes.contains(FunctionTypeEnum.STORAGE.getIndex())) {
            delOrgFuncTypes.add(FunctionTypeEnum.STORAGE.getIndex());//添加到删除集合
            // 删除销售组织能，级联删除采购委托关系
            storageGroupEntrustRelationMapper.deleteGroupStorageEntrustRelationByStockOrgId(buId);
        }
        // 物流
        if (reqParam.hasLogisticFunc()) {
            final LogisticsOrgFuncBean logisticsOrgFunc = reqParam.getLogistics();
            logisticsOrgFunc.setState(buState);
            logisticsOrgFunc.setOrgId(reqParam.getId());
            if (!orgFuncTypes.contains(FunctionTypeEnum.LOGISTICS.getIndex())) {
                logisticsOrgFunc.setCreatedDate(updateTime);
                logisticsOrgFunc.setCreatedBy(updateUser);
                logisticsOrgFunc.setLogisticsFunctionType(reqParam.getLogistics().getLogisticsFunctionType());
                logisticsOrgFuncMapper.addLogisticsFunc(logisticsOrgFunc);
                Long logisticsOrgFuncId = logisticsOrgFunc.getId();
                // 新增物流组织职能，级联新增财务委托关系
                final FinanceEntrustRelationBean financeEntrustRelationBean = bizUnitEntrustRelationshipBuilder.writeLogisticsEntrustRelation(reqParam, groupId, logisticsOrgFuncId, buState, Boolean.FALSE, "BU新增物流职能级联财务");
                //当财务委托关系不存在时候新增财务委托关系
                if (!queryFinanceEntrustExsit(financeEntrustRelationBean)) {
                    financeEntrustRelationBeans.add(financeEntrustRelationBean);
                }
            } else {
                logisticsOrgFunc.setModifiedDate(updateTime);
                logisticsOrgFunc.setModifiedBy(updateUser);
                logisticsOrgFunc.setLogisticsFunctionType(reqParam.getLogistics().getLogisticsFunctionType());
                logisticsOrgFuncMapper.updateLogisticsFunc(logisticsOrgFunc);
                buFuncCascadeModifyEntrustEvent.logisticsOrgFuncUpdate(logisticsOrgFunc, groupId, updateUser);
            }
        } else if (!CollectionUtils.isEmpty(orgFuncTypes) && orgFuncTypes.contains(FunctionTypeEnum.LOGISTICS.getIndex())) {
            delOrgFuncTypes.add(FunctionTypeEnum.LOGISTICS.getIndex());
        }

        //金融
        if (reqParam.hasBankingFunc()) {
            final BankingOrgFuncBean bankingOrgFuncBean = reqParam.getBanking();
            bankingOrgFuncBean.setState(buState);
            bankingOrgFuncBean.setOrgId(reqParam.getId());
            if (!orgFuncTypes.contains(FunctionTypeEnum.BANKING.getIndex())) {
                bankingOrgFuncBean.setCreatedDate(updateTime);
                bankingOrgFuncBean.setCreatedBy(updateUser);
                bankingOrgFuncBean.setAccountOrgId(reqParam.getBanking().getAccountOrgId());
                bankingOrgFuncBean.setSettleOrgId(reqParam.getBanking().getSettleOrgId());
                bankingOrgFuncMapper.addBankingFunc(bankingOrgFuncBean);
                Long bankOrgFuncId = bankingOrgFuncBean.getId();
                // 新增金融组织职能，级联新增财务委托关系
                final FinanceEntrustRelationBean financeEntrustRelationBean = bizUnitEntrustRelationshipBuilder.writeBankingEntrustRelation(reqParam, groupId, bankOrgFuncId, buState, Boolean.FALSE, "BU新增金融职能级联财务");
                //当财务委托关系不存在时候新增财务委托关系
                if (!queryFinanceEntrustExsit(financeEntrustRelationBean)) {
                    financeEntrustRelationBeans.add(financeEntrustRelationBean);
                }
            } else {
                bankingOrgFuncBean.setAccountOrgId(reqParam.getBanking().getAccountOrgId());
                bankingOrgFuncBean.setSettleOrgId(reqParam.getBanking().getSettleOrgId());
                bankingOrgFuncBean.setModifiedDate(updateTime);
                bankingOrgFuncBean.setModifiedBy(updateUser);
                bankingOrgFuncMapper.updateBankFunc(bankingOrgFuncBean);
                buFuncCascadeModifyEntrustEvent.bankOrgFunctionUpdate(bankingOrgFuncBean, groupId, updateUser);
            }
        } else if (!CollectionUtils.isEmpty(orgFuncTypes) && orgFuncTypes.contains(FunctionTypeEnum.BANKING.getIndex())) {
            delOrgFuncTypes.add(FunctionTypeEnum.BANKING.getIndex());
        }

        if (!CollectionUtils.isEmpty(delOrgFuncTypes)) {
            orgFunctionMapper.delOrgFuncByOrgIdAndFuncTypes(buId, delOrgFuncTypes, updateUser, updateTime);
            if (delOrgFuncTypes.contains(FunctionTypeEnum.PURCHASE.getIndex())) {
                // 删除采购组织能，级联删除采购委托关系
                purchaseEntrustRelationMapper.deleteEntrustRelationByPurchaseOrgId(buId);
            }
        }

        if (!CollectionUtils.isEmpty(financeEntrustRelationBeans)) {
            financeEntrustRelationBeans.forEach(financeEntrustRelationBean -> cascadeCreateFinanceEntrustRelEvent.entrustCreate(financeEntrustRelationBean));
        }
        // 同步业务线信息
        BusinessLineOrgDto businessLineOrgDto = new BusinessLineOrgDto();
        businessLineOrgDto.setLineIdList(reqParam.getLineIdList());
        businessLineOrgDto.setGroupId(buBean.getGroupId());
        businessLineOrgDto.setOrgId(buId);
        businessLineOrgDto.setModifiedBy(updateUser);
        businessLineOrgDto.setModifiedDate(updateTime);
        businessLineOrgDto.setModifiedName(updateName);
        businessLineOrgDto.setCreatedBy(updateUser);
        businessLineOrgDto.setCreatedDate(updateTime);
        businessLineOrgDto.setCreatedName(updateName);
        businessLineOrgService.saveOrUpdate(businessLineOrgDto);
    }

    @Override
    public BizUnitWithFuncDetailBean queryBizUnitWithFuncById(QueryByIdReqParam reqParam) {
        return bizUnitMapper.queryBizUnitById(reqParam);
    }

    public List<BizUnitWithFuncDetailBean> queryBizUnitWithFuncByIds(QueryByIdReqParam reqParam) {
        return bizUnitMapper.queryBizUnitByIds(reqParam.getIds());
    }

    @Override
    public BizUnitWithFuncDetailBean queryBUVersionDetailInfo(QueryByIdReqParam reqParam) {
        return bizUnitVersionMapper.queryBUVersionById(reqParam.getId());
    }

    @Override
    public PageInfo<BizUnitWithFuncInfoBean> queryBizUnitInfoList(QueryBizUnitListReqParam reqParam) {
        List<Long> permissionList = new ArrayList<>();
        //判断是否是4pl
        if (!reqParam.getFourPL()) {
            //加上组织权限控制
            Set<Long> orgsInPermission = null;
            orgsInPermission = permissionDomainService.getOrgsInPermission(reqParam.getUserId(), reqParam.getGroupId(), OrgTypeEnum.ORGANIZATION);

            permissionList = CollectionUtils.isEmpty(orgsInPermission) ? Collections.emptyList() : new ArrayList<>(orgsInPermission);

        }
        final List<BizUnitWithFuncInfoBean> buWithFuncBeans = bizUnitMapper.queryBizUnitList(reqParam, permissionList);
        Integer totalCount = bizUnitMapper.queryBizUnitListTotalCount(reqParam, permissionList);
        //1.调用客商服务获取客商名字
        //2 调用客商获取集团客户名字
        Map<Long, String> globleCustNameMap = new HashMap<>();
        Map<String, CustSubDTO> innerCustMap = new HashMap<>();
        if (!ObjectUtils.isEmpty(buWithFuncBeans)) {
            globleCustNameMap = getGlobleCustName(buWithFuncBeans);
            innerCustMap = getInnerCustSub(buWithFuncBeans);
        }
        // 客商信息
        for (BizUnitWithFuncInfoBean item : buWithFuncBeans) {
            CustSubDTO subCustomDTO = innerCustMap.get(item.getId() + "-" + CustTypeEnum.CUSTOM.getCode());
            if (Objects.nonNull(subCustomDTO)) {
                //集团客户
                item.setInnerCustId(subCustomDTO.getId());
                item.setInnerCustName(subCustomDTO.getCustName());
            }
            CustSubDTO subSupplierDTO = innerCustMap.get(item.getId() + "-" + CustTypeEnum.SUPPLIER.getCode());
            if (Objects.nonNull(subCustomDTO)) {
                //供应商
                item.setSupplierName(subSupplierDTO.getCustName());
            }
            //全局客户
            if (!NumericUtil.nullOrlessThanOrEqualToZero(item.getCustId())) {
                item.setCustName(globleCustNameMap.get(item.getCustId()));
            }
        }
        PageInfo<BizUnitWithFuncInfoBean> pageInfo = new PageInfo<>();
        pageInfo.setList(buWithFuncBeans);
        pageInfo.setPageSize(reqParam.getPageSize());
        pageInfo.setPageNum(reqParam.getPageNum());
        pageInfo.setTotal(totalCount);
        return pageInfo;
    }

    @Override
    public PageInfo<OrgInfoDto> queryRootBUInfoList(RootBUListQueryParam reqParam) {
        final List<OrgInfoDto> orgInfoDtos = bizUnitMapper.queryRootBUInfoList(reqParam);
        PageInfo<OrgInfoDto> pageInfo = new PageInfo<>();
        pageInfo.setList(orgInfoDtos);
        pageInfo.setPageSize(reqParam.getPageSize());
        pageInfo.setPageNum(reqParam.getPageNum());
        pageInfo.setTotal(0);
        return pageInfo;
    }

    @Override
    public PageInfo<OrgConciseInfoDto> queryBizUnitListByFunc(QueryBizUnitListByFuncParam reqParam) {
        PageInfo<OrgConciseInfoDto> pageInfo = new PageInfo<>();
        pageInfo.setList(new ArrayList<>());
        pageInfo.setPageSize(reqParam.getPageSize());
        pageInfo.setPageNum(reqParam.getPageNum());
        pageInfo.setTotal(0);
        final int totalCount = bizUnitMapper.countBizUnitListByFunc(reqParam);
        LOGGER.info("query biz unit list by func param:{}, count : {}.", reqParam, totalCount);
        if (totalCount == 0) {
            return pageInfo;
        }

        final List<OrgConciseInfoDto> orgBeans = bizUnitMapper.queryBizUnitListByFunc(reqParam);
        pageInfo.setTotal(totalCount);
        pageInfo.setList(orgBeans);
        return pageInfo;
    }

    @Override
    public List<OrgListBean> queryGroupBUByFunc(QueryGroupBUByFuncReqParam reqParam) {
        Set<Long> orgsInPermission = null;
        boolean needPermissionControl = reqParam.getFlag() == null ? Boolean.FALSE : reqParam.getFlag();
        if (needPermissionControl) {
            orgsInPermission = permissionDomainService.getOrgsInPermission(reqParam.getUserId(), reqParam.getGroupId(), OrgTypeEnum.ORGANIZATION);
            if (CollectionUtils.isEmpty(orgsInPermission)) {
                return Collections.emptyList();
            }
        }

        List<Long> permissionList = CollectionUtils.isEmpty(orgsInPermission) ? Collections.emptyList() : new ArrayList<>(orgsInPermission);
        reqParam.setPermissionOrgIds(permissionList);
        return bizUnitMapper.queryGroupBUByFunc(reqParam);
    }

    public List<OrgInfoDto> queryBizByGroupIdAndUserId(QueryBizByGroupIdAndUserIdParam reqParam) {
        Set<Long> orgsInPermission = null;
        orgsInPermission = permissionDomainService.getOrgsInPermission(reqParam.getUserId(), reqParam.getGroupId(), OrgTypeEnum.ORGANIZATION);
        if (!ObjectUtils.isEmpty(orgsInPermission)) {
            BatchQueryParam batchQueryParam = new BatchQueryParam();
            batchQueryParam.setIds(orgsInPermission.stream().collect(Collectors.toList()));
            List<OrgInfoDto> orgInfoDtos = commonOrgMapper.batchQueryOrgInfoByCondition(batchQueryParam);
            return orgInfoDtos;
        } else {
            return null;
        }
    }


    @Override
    public List<OrgListBean> queryCustBUByFunc(QueryCustBUByFuncReqParam reqParam) {
        QueryGroupBUByFuncReqParam param = new QueryGroupBUByFuncReqParam();
        param.setGroupId(reqParam.getGroupId());
        param.setFunctionType(reqParam.getFunctionType());
        BeanUtil.copyProperties(reqParam, param);
        final List<OrgListBean> orgListBeans = bizUnitMapper.queryGroupBUByFunc(param);
        if (CollectionUtils.isEmpty(orgListBeans)) {
            return Collections.emptyList();
        }
        return orgListBeans;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void bindingInnerCust(BindingCustReqParam reqParam) {

        final OrgBean buState = bizUnitMapper.getBizUnitStateByIdLock(reqParam.getOrgId());

        if (buState.getState() != StateEnum.ENABLE.getCode()) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.impl.bizunit.unenable.cannot.relation.customer"));
        }

        final Long innerCustId = buState.getInnerCustId();
        if (innerCustId != null && innerCustId.equals(reqParam.getCustId())) {
            LOGGER.info("重复绑定内部客商信息，参数：{}.", reqParam);
            return;
        }

        if (innerCustId != null && !innerCustId.equals(reqParam.getCustId())) {
            LOGGER.info("当前业务单元已绑定内部客商信息，参数：{}.", reqParam);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.impl.bizunit.binded.in.customer"));
        }
        bizUnitMapper.bindingInnerCust(reqParam);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void bindingCust(BindingCustReqParam reqParam) {
        final Long buId = reqParam.getOrgId();
        final OrgBean buState = bizUnitMapper.getBizUnitStateByIdLock(buId);

        if (buState == null) {
            LOGGER.info("binding cust bu not exist，reqParam：{}.", reqParam);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.impl.bizunit.org.not.exist"));
        }
        if (buState.getState() != StateEnum.ENABLE.getCode()) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.impl.bizunit.unenable.cannot.relation.customer"));
        }

        final Long custId = buState.getCustId();
        if (NumericUtil.greterThanZero(custId)) {
            if (custId.equals(reqParam.getCustId())) {
                LOGGER.info("bu repetitively binding cust，param：{}.", reqParam);
                return;
            }

            if (!custId.equals(reqParam.getCustId())) {
                LOGGER.info("bu already binding cust，param：{}.", reqParam);
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.impl.bizunit.binded.customer"));
            }
        }

        // 会有同步危险，概率很小，不同步了！

        custDomainService.getAndVerifyCust(reqParam.getCustId());

        bizUnitMapper.bindingCust(reqParam);

        custDomainService.custInfoWriteBack(reqParam.getCustId(), null, buId, reqParam.getUserId());
    }

    @Override
    public BUInvoiceInfoDto invoiceInfo(QueryByIdReqParam reqParam) {
        QueryByIdReqParam byIdReqParam = new QueryByIdReqParam();
        byIdReqParam.setId(reqParam.getId());
        final BizUnitWithFuncDetailBean buWithFunc = bizUnitMapper.queryBizUnitById(byIdReqParam);

        if (buWithFunc == null) {
            LOGGER.info("This bu does not exist,param:{}.", reqParam);
            throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.service.impl.bizunit.org.not.exist"));
        }

        //非工商注册公司没有法人职能,紧急处理
        if (BooleanEnum.TRUE.getCode().equals(buWithFunc.getMainOrgFlag()) && !buWithFunc.hasCorporationFunc()) {
            BUInvoiceInfoDto invoiceInfo = new BUInvoiceInfoDto();
            invoiceInfo.setTaxpayerCode("3");
            invoiceInfo.setTaxpayerName(I18nUtils.getMessage("org.service.impl.bizunit.nontax.subject"));
            invoiceInfo.setBuId(Long.valueOf(reqParam.getId()));
            return invoiceInfo;
        }

        BizUnitWithFuncDetailBean companyWithFunc = null;
        if (!buWithFunc.hasCorporationFunc()) {// 当前业务单元没有法人职能时
            final Long companyId = buWithFunc.getCompanyId();
            if (NumericUtil.nullOrlessThanOrEqualToZero(companyId)) {
                LOGGER.info("This bu data status illegal,param:{}.", reqParam);
                throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.service.impl.bizunit.data.incomplete"));
            }
            QueryByIdReqParam param = new QueryByIdReqParam();
            param.setId(companyId);
            companyWithFunc = bizUnitMapper.queryBizUnitById(param);
            if (companyWithFunc == null) {
                LOGGER.info("This bu belong company does not exist,param:{}.", reqParam);
                throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.service.impl.bizunit.belong.company.null"));
            }
        }

        //法人组织职能
        CorporateOrgFuncBean corporate = buWithFunc.hasCorporationFunc() ? buWithFunc.getCorporate() : companyWithFunc.getCorporate();
        //财务组织职能
        FinanceOrgFuncBean finance = buWithFunc.hasFinanceFunc() ? buWithFunc.getFinance() : companyWithFunc.getFinance();
        // 数据来源
        BizUnitWithFuncDetailBean sourceBU = buWithFunc.hasCorporationFunc() ? buWithFunc : companyWithFunc;

        BUInvoiceInfoDto invoiceInfo = new BUInvoiceInfoDto();
        invoiceInfo.setBuId(sourceBU.getId());
        invoiceInfo.setCreditCode(sourceBU.getCreditCode());
        invoiceInfo.setTaxpayerCode(corporate.getTaxpayerCode());
        invoiceInfo.setTaxpayerName(corporate.getTaxpayerName());
        invoiceInfo.setOrgInstitutionCode(corporate.getOrgInstitutionCode());
        invoiceInfo.setBizRegistNumber(corporate.getBizRegistNumber());
        invoiceInfo.setTaxRegistrationNumber(finance.getTaxRegistrationNumber());
        return invoiceInfo;
    }

    @Override
    public PageInfo<BUVersionInfoVo> queryBUVersionList(VersionPageQueryParam reqParam) {
        PageInfo<BUVersionInfoVo> pageInfo = new PageInfo<>();
        pageInfo.setList(new ArrayList<>());
        pageInfo.setTotal(0);
        pageInfo.setPageNum(reqParam.getPageNum());
        pageInfo.setPageSize(reqParam.getPageSize());

        final int count = bizUnitVersionMapper.countBUVersionList(reqParam);
        if (count == 0) {
            return pageInfo;
        }
        pageInfo.setTotal(count);
        final List<BUVersionInfoVo> bus = bizUnitVersionMapper.queryBUVersionList(reqParam);
        pageInfo.setList(bus);
        return pageInfo;
    }

    @Override
    public OrgListBean queryGroupRootBU(Long groupId) {
        return bizUnitMapper.queryRootBUByGroupId(groupId);
    }

    @Override
    public OrgListDto queryOrgRootBU(QueryByIdReqParam reqParam) {
        return bizUnitMapper.queryOrgRootBU(reqParam.getId());
    }

    @Override
    public OrgListBean queryBUByCustId(Integer custId) {
        return bizUnitMapper.queryBUByCustId(custId);
    }

    /**
     * 根据集团id查询业务单元
     *
     * @param reqParam 查询参数
     * @return 组织信息列表
     */
    public List<OrgInfoDto> queryBUByGroupId(QueryByIdReqParam reqParam) {

        return bizUnitMapper.queryBUByGroupId(reqParam);
    }

    /**
     * 过滤业务单元ID集合
     *
     * @param bizUnitWithFuncDetailBeans 业务单元集合
     * @return 业务单元ID集合
     */
    private List<Long> filterBizUnitIds(final List<BizUnitWithFuncDetailBean> bizUnitWithFuncDetailBeans) {
        Set<Long> bizUnitIds = new HashSet<>();
        for (BizUnitWithFuncDetailBean bizUnitWithFuncDetailBean : bizUnitWithFuncDetailBeans) {
            bizUnitIds.add(bizUnitWithFuncDetailBean.getId());
        }
        return new ArrayList<>(bizUnitIds);
    }

    /**
     * 将业务单元信息实体集合转换为Map数据结构
     *
     * @param bizUnitWithFuncDetailBeans 业务单元数据集合
     * @return 业务单元map数据结构
     */
    private Map<Long, BizUnitWithFuncDetailBean> covertToMap(final List<BizUnitWithFuncDetailBean> bizUnitWithFuncDetailBeans) {
        Map<Long, BizUnitWithFuncDetailBean> bizUnitInfoBeanMap = new HashMap<>();
        for (BizUnitWithFuncDetailBean bizUnitWithFuncDetailBean : bizUnitWithFuncDetailBeans) {
            bizUnitInfoBeanMap.put(bizUnitWithFuncDetailBean.getId(), bizUnitWithFuncDetailBean);
        }
        return bizUnitInfoBeanMap;
    }

    /**
     * 停用业务单元
     *
     * @param orgBean 业务单元
     */
    private void disableBU(final BizUnitWithFuncDetailBean orgBean, Long userId) {
        final Long buId = orgBean.getId();

        if (StateEnum.DISABLE.getCode() == orgBean.getState()) {
            LOGGER.warn("repation stop biz unit ,bizUnitId:{}.", buId);
            return;
        }

        if (StateEnum.ENABLE.getCode() != orgBean.getState()) {
            LOGGER.warn("disable biz unit can not stop ,bizUnitId:{}.", buId);
            throw new OrgException(OrgErrorCode.UNENABLE_BIZ_UNIT_CAN_NOT_STOP_ERROR_CODE, I18nUtils.getMessage("org.service.impl.bizunit.unenable.cannot.disable"));
        }

        // 无下级业务单元，或下级业务单元都已停用
        final List<OrgTreeBean> groupEnableOrgs = commonOrgMapper.queryGroupEnableOrgTreeByOrgId(buId);
        if (haseEnableChildBU(buId, groupEnableOrgs)) {
            LOGGER.warn("biz unit has enable child biz unit can not stop ,bizUnitId:{}.", buId);
            throw new OrgException(OrgErrorCode.UNENABLE_BIZ_UNIT_CAN_NOT_STOP_ERROR_CODE, I18nUtils.getMessage("org.service.impl.bizunit.have.undisable.child"));
        }
        final Date stopTime = new Date();

        // 停用当前业务单元
        commonOrgMapper.disableOrg(userId, buId, stopTime);

        // 停用当前部门
        departmentService.disableDepByBUId(userId, buId);

        // 级联停用业务单元具有的业务委托关系
        commonEntrustRelationMapper.modifyEntrustRelationByOrgId(buId, userId, stopTime, StateEnum.DISABLE.getCode(), StateEnum.ENABLE.getCode());

        // 级联停用业务单元的组织职能
        orgFunctionMapper.modifyFuncStateByBUId(buId, userId, StateEnum.DISABLE.getCode(), Collections.singletonList(2), stopTime);
    }

    /**
     * 启用业务单元
     *
     * @param orgBean 业务单元数据实体
     * @param userId  用户ID
     */
    private void enable(final BizUnitWithFuncDetailBean orgBean, Long userId) {
        final Long buId = orgBean.getId();
        if (StateEnum.ENABLE.getCode() == orgBean.getState()) {
            LOGGER.warn("repation enbale biz unit ,bizUnitId:{}.", buId);
            return;
        }

        // 级联启用组织下的所有组织职能
        final Date stopTime = new Date();
        orgFunctionMapper.modifyFuncStateByBUId(buId, userId, StateEnum.ENABLE.getCode(), Arrays.asList(1, 3), stopTime);

        // 启用当前业务单元
        commonOrgMapper.enableOrg(userId, buId, stopTime);

        final Long groupId = orgBean.getGroupId();
        if (orgBean.hasPurchaseFunc()) {
            final PurchaseOrgFuncBean purchase = orgBean.getPurchase();
            PurchaseOrgFuncBean purchaseOrgFunc = new PurchaseOrgFuncBean();
            BeanUtils.copyProperties(purchase, purchaseOrgFunc);
            purchaseOrgFunc.setModifiedBy(userId);
            purchaseOrgFunc.setModifiedDate(new Date());
            purchaseOrgFunc.setState(StateEnum.ENABLE.getCode());

            buFuncCascadeModifyEntrustEvent.purchaseOrgFuncUpdate(purchaseOrgFunc, groupId, userId);
        }

        if (orgBean.hasSaleFunc()) {
            final SaleOrgFuncBean sale = orgBean.getSale();
            SaleOrgFuncBean saleOrgFunc = new SaleOrgFuncBean();
            BeanUtils.copyProperties(sale, saleOrgFunc);
            saleOrgFunc.setState(StateEnum.ENABLE.getCode());
            saleOrgFunc.setModifiedBy(userId);
            saleOrgFunc.setModifiedDate(new Date());
            buFuncCascadeModifyEntrustEvent.saleOrgFuncUpdate(saleOrgFunc, groupId, userId);
        }
        if (orgBean.hasStorageFunc()) {
            final StorageOrgFuncBean storage = orgBean.getStorage();
            StorageOrgFuncBean storageOrgFunc = new StorageOrgFuncBean();
            BeanUtils.copyProperties(storage, storageOrgFunc);
            storageOrgFunc.setState(StateEnum.ENABLE.getCode());
            storageOrgFunc.setModifiedBy(userId);
            storageOrgFunc.setModifiedDate(new Date());
            buFuncCascadeModifyEntrustEvent.storageOrgFuncUpdate(storageOrgFunc, groupId, userId);
        }

        if (orgBean.hasLogisticFunc()) {
            final LogisticsOrgFuncBean logistics = orgBean.getLogistics();
            LogisticsOrgFuncBean logisticsOrgFunc = new LogisticsOrgFuncBean();
            BeanUtils.copyProperties(logistics, logisticsOrgFunc);
            logisticsOrgFunc.setState(StateEnum.ENABLE.getCode());
            logisticsOrgFunc.setModifiedBy(userId);
            logisticsOrgFunc.setModifiedDate(new Date());
            logisticsOrgFunc.setLogisticsFunctionType(orgBean.getLogistics().getLogisticsFunctionType());
            buFuncCascadeModifyEntrustEvent.logisticsOrgFuncUpdate(logisticsOrgFunc, groupId, userId);
        }
        if (orgBean.hasBankingFunc()) {
            final BankingOrgFuncBean bankOrgFuncInfoBean = orgBean.getBanking();
            BankingOrgFuncBean bankingOrgFuncBean = new BankingOrgFuncBean();
            BeanUtils.copyProperties(bankOrgFuncInfoBean, bankingOrgFuncBean);
            bankingOrgFuncBean.setState(StateEnum.ENABLE.getCode());
            bankingOrgFuncBean.setModifiedBy(userId);
            bankingOrgFuncBean.setModifiedDate(new Date());
            buFuncCascadeModifyEntrustEvent.bankOrgFunctionUpdate(bankingOrgFuncBean, groupId, userId);
        }
    }

    @Override
    public List<OrgTreeBean> queryBUPermissonList(QueryOrgPermissionListReqParam reqParam) {
        final Set<Long> orgPermissionIds = permissionDomainService.getOrgsInPermission(reqParam.getUserId(), reqParam.getGroupId(), OrgTypeEnum.ORGANIZATION);
        if (CollectionUtils.isEmpty(orgPermissionIds)) {
            LOGGER.warn("current user does not have org permission,reqParam:{}.", reqParam);
            throw new OrgException(OrgErrorCode.USER_PERMISION_NOT_ENOUGH_ERROR_CODE, I18nUtils.getMessage("org.service.impl.bizunit.user.permission.insufficient"));
        }

        return commonOrgMapper.queryAllEnableOrgWithSepicalFunc(new ArrayList<>(orgPermissionIds), reqParam.getGroupId(), reqParam.getOrgFuncs());
    }

    @Override
    public List<OrgListBean> queryGroupUnbindingCustBUs(QueryBizUnitListReqParam reqParam) {
        return bizUnitMapper.queryGroupUnbindingCustBUs(reqParam);
    }

    public List<BizUnitWithFuncDetailBean> queryBizByGroupIdAndAddress(QueryBizByGroupIdAndAddressParam param) {
        return bizUnitMapper.queryBizByGroupIdAndAddress(param);
    }

    @Override
    public List<OrgListBean> queryGroupBUByFuncList(QueryGroupBUByFuncReqParam reqParam) {
        List<OrgListBean> orgListBeans = new ArrayList<>();
        if (reqParam.getGroupId() == null) {
            return orgListBeans;
        }
        orgListBeans = bizUnitMapper.queryGroupBUByFunc(reqParam);
        return orgListBeans;
    }

    /**
     * 是否有启用的子业务单元
     *
     * @param currentBUId     当前业务单元ID
     * @param groupEnableOrgs 集团内所有启用的业务单元
     * @return <code>true</code>有;<code>fasle</code>无
     */
    private boolean haseEnableChildBU(Long currentBUId, final List<OrgTreeBean> groupEnableOrgs) {
        if (CollectionUtils.isEmpty(groupEnableOrgs)) {
            return false;
        }

        for (OrgTreeBean groupEnableOrg : groupEnableOrgs) {
            final Long parentId = groupEnableOrg.getParentId();
            if (currentBUId.equals(parentId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * List 转换为 Map
     *
     * @param orgListBeans 组织列表
     * @return id map 数据结构
     */
    private Map<Long, List<OrgListBean>> convertoPidMap(final List<OrgListBean> orgListBeans) {
        Map<Long, List<OrgListBean>> pidMap = new HashMap<>();

        for (OrgListBean orgListBean : orgListBeans) {
            final Long parentId = orgListBean.getParentId();
            if (parentId != null) {
                final List<OrgListBean> childList = pidMap.get(parentId);
                if (CollectionUtils.isEmpty(childList)) {
                    pidMap.put(parentId, new ArrayList<>(2));
                }
                pidMap.get(parentId).add(orgListBean);
            }
        }
        return pidMap;
    }

    //业务是否有物流职能（true:有）
    public Boolean hasLogisticsFunction(Long buId) {
        QueryByIdReqParam reqParam = new QueryByIdReqParam();
        reqParam.setId(buId);
        BizUnitWithFuncDetailBean bizUnitWithFuncDetailBean = queryBizUnitWithFuncById(reqParam);
        if (bizUnitWithFuncDetailBean.hasLogisticFunc()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    //查看财务委托关系是否存在
    private Boolean queryFinanceEntrustExsit(FinanceEntrustRelationBean financeEntrustRelationBean) {
        QueryFinanceEntrustRelationParam param = new QueryFinanceEntrustRelationParam();
        param.setSettleOrgId(financeEntrustRelationBean.getSettleOrgId());
        param.setBizOrgId(financeEntrustRelationBean.getBizOrgId());
        param.setAccountOrgId(financeEntrustRelationBean.getAccountOrgId());
        List<Integer> ids = financeEntrustRelationMapper.queryFinanceEntrustByOrgIdAndSettleOrgAndAccountOrg(param);
        if (!ObjectUtils.isEmpty(ids)) {
            return Boolean.TRUE;
        } else {
            return false;
        }
    }

    //异步调用客商服务
    private Map<Long, String> getGlobleCustName(List<BizUnitWithFuncInfoBean> buWithFuncBeans) {
        if (CollectionUtils.isEmpty(buWithFuncBeans)) {
            return new HashMap<>();
        }
        Set<Long> custSet = buWithFuncBeans.stream().filter(c -> Objects.nonNull(c.getCustId())).map(BizUnitWithFuncInfoBean::getCustId).collect(Collectors.toSet());
        //全局客商的名字
        Map<Long, String> custNameMap = custDomainService.batchQueryCustInfoByIds(custSet);
        return custNameMap;
    }

    private Map<String, CustSubDTO> getInnerCustSub(List<BizUnitWithFuncInfoBean> buWithFuncBeans) {
        //内部客商名字
        Map<String, CustSubDTO> custSubMap = new HashMap<>();
        Set<Long> orgIds = buWithFuncBeans.stream().map(BizUnitWithFuncInfoBean::getId).collect(Collectors.toSet());
        List<CustSubDTO> list = custDomainService.getListOfCustSubByUnitOrg(new ArrayList<>(orgIds));
        if (!ObjectUtils.isEmpty(list)) {
            custSubMap = list.stream().collect(Collectors.toMap(x -> x.getUnitOrg() + "-" + x.getCustType(), x -> x, (v1, v2) -> v2));
        }
        return custSubMap;
    }
}
