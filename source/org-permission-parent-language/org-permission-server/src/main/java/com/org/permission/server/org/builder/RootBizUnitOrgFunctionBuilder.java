package com.org.permission.server.org.builder;


import com.org.permission.common.dto.crm.CustInfoDomainDto;
import com.org.permission.common.dto.crm.LinkerInfoReqParam;
import com.org.permission.common.org.dto.func.*;
import com.org.permission.server.domain.base.DicDomainService;
import com.org.permission.server.org.bean.GroupInfoBean;
import com.org.permission.server.org.bean.LabourOrgFuncBean;
import com.org.permission.server.org.bean.PlatformOrgFuncBean;
import com.org.permission.server.org.dto.param.AddBizUnitReqParam;
import com.org.permission.server.org.enums.BusinessTypeEnum;
import com.common.base.enums.BooleanEnum;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * 根业务单元组织职能构建器
 */
@Component(value = "rootBizUnitOrganizationFunctionBuilder")
public class RootBizUnitOrgFunctionBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(RootBizUnitOrgFunctionBuilder.class);

    private static final String BUSINESS_TYPE_DIC_CODE = "crm-ywlx";
    private static final String ENTERPRISE_TYPE_DIC_CODE = "crm-qylx";


    @Resource
    private DicDomainService dicDomainService;

    public AddBizUnitReqParam builde(final GroupInfoBean groupInfo, final Long rootBUId, final CustInfoDomainDto custInfo, final String taxpayerCode, final String taxpayerName, final String orgInstitutionCode, final String taxRegistrationNumber, Long operaterId, String operaterName) {

        AddBizUnitReqParam buFuncBean = new AddBizUnitReqParam();
        buFuncBean.setCreatedBy(operaterId);
        buFuncBean.setCreatedName(operaterName);
        buFuncBean.setCreatedDate(new Date());
        final String custBusinessType = custInfo.getBizTypeId();
        if (StringUtils.isEmpty(custBusinessType)) {
            return buFuncBean;
        }

        // 是否工商注册
        if (BusinessTypeEnum.hasCorporation(custBusinessType) && Objects.equals(custInfo.getSaicFlag(), BooleanEnum.TRUE.getCode())) {// 法人组织职能
            CorporateOrgFuncBean corporationFunc = buildeCorporation(groupInfo, custInfo, taxpayerCode, taxpayerName, orgInstitutionCode, rootBUId);
            corporationFunc.setCreatedBy(operaterId);
            corporationFunc.setCreatedName(operaterName);
            corporationFunc.setCreatedDate(new Date());
            buFuncBean.setCorporate(corporationFunc);
        }

        if (BusinessTypeEnum.hasFinance(custBusinessType)) {// 财务组织职能
            FinanceOrgFuncBean financeFunc = buildeFinance(groupInfo, custInfo, rootBUId, taxRegistrationNumber);
            financeFunc.setCreatedBy(operaterId);
            financeFunc.setCreatedName(operaterName);
            financeFunc.setCreatedDate(new Date());
            buFuncBean.setFinance(financeFunc);
        }

        if (BusinessTypeEnum.hasPurchase(custBusinessType)) {// 采购组织职能
            PurchaseOrgFuncBean purchaseFunc = buildePurchase(groupInfo, custInfo, rootBUId);
            purchaseFunc.setCreatedBy(operaterId);
            purchaseFunc.setCreatedName(operaterName);
            purchaseFunc.setCreatedDate(new Date());
            buFuncBean.setPurchase(purchaseFunc);
        }

        if (BusinessTypeEnum.hasSale(custBusinessType)) {// 销售组织职能
            SaleOrgFuncBean saleFunc = buildeSale(groupInfo, custInfo, rootBUId);
            saleFunc.setCreatedBy(operaterId);
            saleFunc.setCreatedName(operaterName);
            saleFunc.setCreatedDate(new Date());
            buFuncBean.setSale(saleFunc);
        }

        if (BusinessTypeEnum.hasStorage(custBusinessType)) {// 仓储组织职能
            StorageOrgFuncBean storageFunc = buildeStorage(groupInfo, custInfo, rootBUId);
            storageFunc.setCreatedBy(operaterId);
            storageFunc.setCreatedName(operaterName);
            storageFunc.setCreatedDate(new Date());
            buFuncBean.setStorage(storageFunc);
        }
        if (BusinessTypeEnum.hasLogistics(custBusinessType)) {// 物流组织职能
            LogisticsOrgFuncBean logisticsFunc = buildeLogistics(groupInfo, custInfo, rootBUId);
            logisticsFunc.setCreatedBy(operaterId);
            logisticsFunc.setCreatedName(operaterName);
            logisticsFunc.setCreatedDate(new Date());
            buFuncBean.setLogistics(logisticsFunc);
        }

        if (BusinessTypeEnum.hasPlatform(custBusinessType)) {// 平台组织职能
            PlatformOrgFuncBean platformFunc = buildePlatform(groupInfo, custInfo, rootBUId);
            platformFunc.setCreatedBy(operaterId);
            platformFunc.setCreatedName(operaterName);
            platformFunc.setCreatedDate(new Date());
            buFuncBean.setPlatform(platformFunc);
        }
        if (BusinessTypeEnum.hasLabour(custBusinessType)) {// 劳务组织职能
            LabourOrgFuncBean labouFunc = buildeLabour(groupInfo, custInfo, rootBUId);
            labouFunc.setCreatedBy(operaterId);
            labouFunc.setCreatedName(operaterName);
            labouFunc.setCreatedDate(new Date());
            buFuncBean.setLabour(labouFunc);
        }
        if (BusinessTypeEnum.hasBanking(custBusinessType)) {// 金融组织职能
            BankingOrgFuncBean bankingFunc = buildeBanking(groupInfo, custInfo, rootBUId);
            bankingFunc.setCreatedBy(operaterId);
            bankingFunc.setCreatedName(operaterName);
            bankingFunc.setCreatedDate(new Date());
            buFuncBean.setBanking(bankingFunc);
        }
        return buFuncBean;
    }

    /**
     * 构建法人公司数据信息实体
     *
     * @param groupInfo 集团信息
     * @param custInfo  客户信息
     * @return 法人公司组织职能信息
     */
    public CorporateOrgFuncBean buildeCorporation(final GroupInfoBean groupInfo, final CustInfoDomainDto custInfo, final String taxpayerCode, final String taxpayerName, final String orgInstitutionCode, Long rootBUId) {
        CorporateOrgFuncBean corporationFunc = new CorporateOrgFuncBean();
        corporationFunc.setTaxpayerCode(taxpayerCode);
        corporationFunc.setTaxpayerName(taxpayerName);
        corporationFunc.setOrgId(rootBUId);
        corporationFunc.setAddressDetail(groupInfo.getAddressDetail());
        corporationFunc.setEstablishTime(groupInfo.getEstablishTime());
        corporationFunc.setOrgInstitutionCode(orgInstitutionCode);
        corporationFunc.setBizStartTime(custInfo.getBusinessStartTime());
        corporationFunc.setBizEndTime(custInfo.getBusinessEndTime());
        final Integer custEnterpriseType = custInfo.getEnterpriseType();
        if (custEnterpriseType != null) {
            final String dicItemName = dicDomainService.getItemName(ENTERPRISE_TYPE_DIC_CODE, custEnterpriseType.toString());
            if (dicItemName != null) {
                corporationFunc.setEnterpriseCode(custEnterpriseType.toString());
                corporationFunc.setEnterpriseName(dicItemName);
            }
        }
        corporationFunc.setRegisteredCapital(custInfo.getRegisteredCapital());
        corporationFunc.setNetAddress(groupInfo.getNetAddress());

        final LinkerInfoReqParam linkerInfo = custInfo.getLinkerInfo();
        if (linkerInfo != null) {
            final String linker = linkerInfo.getLinker();
            if (linker != null) {
                corporationFunc.setLinker(linker);
            }
            final String phone = linkerInfo.getPhone();
            if (phone != null) {
                corporationFunc.setPhone(phone);
            }
            final String email = linkerInfo.getEmail();
            if (email != null) {
                corporationFunc.setEmail(email);
            }
        }
        return corporationFunc;
    }

    /**
     * 构建财务数据信息实体
     *
     * @return 财务组织职能信息
     */
    private FinanceOrgFuncBean buildeFinance(final GroupInfoBean groupInfo, final CustInfoDomainDto custInfo, Long rootBUId, final String taxRegistrationNumber) {
        FinanceOrgFuncBean financeFunc = new FinanceOrgFuncBean();
        financeFunc.setOrgId(rootBUId);
        financeFunc.setAloneFlag(Objects.equals(custInfo.getSaicFlag(), BooleanEnum.TRUE.getCode()));
        financeFunc.setTaxRegistrationNumber(Objects.equals(BooleanEnum.TRUE.getCode(), BooleanEnum.TRUE.getCode()) ? taxRegistrationNumber : "");
        return financeFunc;
    }

    /**
     * 构建采购数据信息实体
     *
     * @return 采购组织职能信息
     */
    private PurchaseOrgFuncBean buildePurchase(final GroupInfoBean groupInfo, final CustInfoDomainDto custInfo, final Long rootBUId) {
        PurchaseOrgFuncBean purchaseFunc = new PurchaseOrgFuncBean();
        purchaseFunc.setOrgId(rootBUId);
        return purchaseFunc;
    }

    /**
     * 构建销售数据信息实体
     *
     * @return 销售组织职能信息
     */
    private SaleOrgFuncBean buildeSale(final GroupInfoBean groupInfo, final CustInfoDomainDto custInfo, final Long rootBUId) {
        SaleOrgFuncBean saleFunc = new SaleOrgFuncBean();
        saleFunc.setOrgId(rootBUId);
        return saleFunc;
    }

    /**
     * 构建仓储数据信息实体
     *
     * @return 仓储组织职能信息
     */
    private StorageOrgFuncBean buildeStorage(final GroupInfoBean groupInfo, final CustInfoDomainDto custInfo, final Long rootBUId) {
        StorageOrgFuncBean storageFunc = new StorageOrgFuncBean();
        storageFunc.setAddressDetail(groupInfo.getAddressDetail());
        storageFunc.setOrgId(rootBUId);
        return storageFunc;
    }

    /**
     * 构建物流组织职能数据信息实体
     *
     * @return 物流组织职能信息
     */
    private LogisticsOrgFuncBean buildeLogistics(final GroupInfoBean groupInfo, final CustInfoDomainDto custInfo, final Long rootBUId) {
        LogisticsOrgFuncBean logisticsFunc = new LogisticsOrgFuncBean();
        logisticsFunc.setAddressDetail(groupInfo.getAddressDetail());
        logisticsFunc.setOrgId(rootBUId);
        return logisticsFunc;
    }

    /**
     * 构建平台组织职能数据信息实体
     *
     * @return 平台组织职能信息
     */
    private PlatformOrgFuncBean buildePlatform(final GroupInfoBean groupInfo, final CustInfoDomainDto custInfo, final Long rootBUId) {
        PlatformOrgFuncBean platformOrgFunc = new PlatformOrgFuncBean();
        platformOrgFunc.setOrgId(rootBUId);
        return platformOrgFunc;
    }

    /**
     * 构建物流组织职能数据信息实体
     *
     * @return 物流组织职能信息
     */
    private LabourOrgFuncBean buildeLabour(final GroupInfoBean groupInfo, final CustInfoDomainDto custInfo, final Long rootBUId) {
        LabourOrgFuncBean labourFunc = new LabourOrgFuncBean();
        labourFunc.setOrgId(rootBUId);
        return labourFunc;
    }

    /**
     * 构建金融组织职能数据信息实体
     *
     * @return 物流组织职能信息
     */
    private BankingOrgFuncBean buildeBanking(final GroupInfoBean groupInfo, final CustInfoDomainDto custInfo, final Long rootBUId) {
        BankingOrgFuncBean bankingFunc = new BankingOrgFuncBean();
        bankingFunc.setOrgId(rootBUId);
        bankingFunc.setSettleOrgId(rootBUId);
        bankingFunc.setAccountOrgId(rootBUId);
        return bankingFunc;
    }
}
