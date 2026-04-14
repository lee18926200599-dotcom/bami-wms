package com.org.permission.server.org.builder;

import com.org.permission.common.dto.crm.LinkerInfoReqParam;
import com.org.permission.common.enums.org.FunctionTypeEnum;
import com.org.permission.server.org.bean.OrgFunctionDetailBean;
import com.org.permission.server.org.dto.param.OrgFunctionReqParam;
import com.common.base.enums.StateEnum;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 组织职能构建器
 */
@Component(value = "bizUnitOrgFunctionBuilder")
public class BizUnitOrgFunctionBuilder {

    public List<OrgFunctionDetailBean> builde(final List<OrgFunctionReqParam> orgFunctions, final Long buId) {

        if (CollectionUtils.isEmpty(orgFunctions)) {
            return null;
        }

        final Set<FunctionTypeEnum> orgFuncSet = converToSet(orgFunctions);

        List<OrgFunctionDetailBean> orgFunctionDetailBeans = new ArrayList<>(orgFunctions.size());

        for (OrgFunctionReqParam organizationFunction : orgFunctions) {
            final FunctionTypeEnum functionTypeEnum = FunctionTypeEnum.getEnum(organizationFunction.getFunctionType());
            OrgFunctionDetailBean orgFunctionDetailBean;

            switch (functionTypeEnum) {
                case CORPORATION:
                    orgFunctionDetailBean = buildeCorporation(organizationFunction);
                    break;
                case FINANCE:
                    orgFunctionDetailBean = buildeFinance(organizationFunction);
                    break;
                case PURCHASE:
                    orgFunctionDetailBean = buildePurchase(organizationFunction, orgFuncSet, buId);
                    break;
                case SALE:
                    orgFunctionDetailBean = buildeSale(organizationFunction, orgFuncSet, buId);
                    break;
                case STORAGE:
                    orgFunctionDetailBean = buildeStorage(organizationFunction, orgFuncSet, buId);
                    break;
                case LOGISTICS:
                    orgFunctionDetailBean = buildeLogistics(organizationFunction, orgFuncSet, buId);
                    break;
                default:
                    orgFunctionDetailBean = null;
            }

            if (orgFunctionDetailBean != null) {
                orgFunctionDetailBean.setState(StateEnum.CREATE.getCode());
                orgFunctionDetailBean.setOrgId(buId);
                orgFunctionDetailBeans.add(orgFunctionDetailBean);
            }
        }
        return orgFunctionDetailBeans;
    }

    /**
     * 将组织职能集合转换为 Map 数据结构
     *
     * @param orgfuncs 组织职能集合
     * @return key-FunctionTypeEnum value-OrgFunctionDetailBean
     */
    public Set<FunctionTypeEnum> converToSet(final List<OrgFunctionReqParam> orgfuncs) {
        Set<FunctionTypeEnum> orgFuncSet = new HashSet<>();
        if (CollectionUtils.isEmpty(orgfuncs)) {
            return orgFuncSet;
        }
        for (OrgFunctionReqParam reqParam : orgfuncs) {
            final Integer functionType = reqParam.getFunctionType();

            final FunctionTypeEnum functionTypeEnum = FunctionTypeEnum.getEnum(functionType);
            orgFuncSet.add(functionTypeEnum);
        }
        return orgFuncSet;
    }

    /**
     * 构建法人公司数据信息实体
     *
     * @param organizationFunction 组织职能
     * @return 法人公司组织职能信息
     */
    private OrgFunctionDetailBean buildeCorporation(OrgFunctionReqParam organizationFunction) {
        OrgFunctionDetailBean orgFunctionDetailBean = new OrgFunctionDetailBean();
        orgFunctionDetailBean.setFunctionType(FunctionTypeEnum.CORPORATION.getIndex());
        orgFunctionDetailBean.setParentOrgId(organizationFunction.getParentOrgId());
        orgFunctionDetailBean.setBusinessStartTime(organizationFunction.getBizTime()[0]);
        orgFunctionDetailBean.setBusinessEndTime(organizationFunction.getBizTime()[1]);
        orgFunctionDetailBean.setEnterpriseCode(organizationFunction.getEnterpriseCode());
        orgFunctionDetailBean.setEnterpriseName(organizationFunction.getEnterpriseName());
        orgFunctionDetailBean.setEstablishTime(organizationFunction.getEstablishTime());
        orgFunctionDetailBean.setRegisteredCapital(organizationFunction.getRegisteredCapital());
        orgFunctionDetailBean.setRemark(organizationFunction.getRemark());
        orgFunctionDetailBean.setTaxpayerCode(organizationFunction.getTaxpayerCode());
        orgFunctionDetailBean.setTaxpayerName(organizationFunction.getTaxpayerName());
        orgFunctionDetailBean.setOrgInstitutionCode(organizationFunction.getOrgCode());
        orgFunctionDetailBean.setBizRegistNumber(organizationFunction.getBizRegistNumber());
        orgFunctionDetailBean.setNetAddress(organizationFunction.getNetAddress());
        orgFunctionDetailBean.setAddressDetail(organizationFunction.getAddressDetail());
        final List<LinkerInfoReqParam> linkerInfos = organizationFunction.getLinkerInfos();
        if (!CollectionUtils.isEmpty(linkerInfos)) {
            final LinkerInfoReqParam linkerInfoReqParam = linkerInfos.get(0);
            orgFunctionDetailBean.setLinker1(linkerInfoReqParam.getLinker());
            orgFunctionDetailBean.setPhone1(linkerInfoReqParam.getPhone());
            orgFunctionDetailBean.setEmail1(linkerInfoReqParam.getEmail());

            if (linkerInfos.size() >= 2) {
                final LinkerInfoReqParam linkerInfoReqParam2 = linkerInfos.get(1);
                orgFunctionDetailBean.setLinker2(linkerInfoReqParam2.getLinker());
                orgFunctionDetailBean.setPhone2(linkerInfoReqParam2.getPhone());
                orgFunctionDetailBean.setEmail2(linkerInfoReqParam2.getEmail());
            }
        }
        return orgFunctionDetailBean;
    }

    /**
     * 构建财务数据信息实体
     *
     * @param organizationFunction 组织职能信息
     * @return 财务组织职能信息
     */
    private OrgFunctionDetailBean buildeFinance(OrgFunctionReqParam organizationFunction) {
        OrgFunctionDetailBean orgFunctionDetailBean = new OrgFunctionDetailBean();
        orgFunctionDetailBean.setFunctionType(FunctionTypeEnum.FINANCE.getIndex());
        orgFunctionDetailBean.setAloneFlag(organizationFunction.getAloneFlag());
        orgFunctionDetailBean.setTaxRegistrationNumber(organizationFunction.getTaxRegistrationNumber());
        return orgFunctionDetailBean;
    }

    /**
     * 构建采购数据信息实体
     *
     * @param organizationFunction 组织职能信息
     * @return 采购组织职能信息
     */
    private OrgFunctionDetailBean buildePurchase(OrgFunctionReqParam organizationFunction, final Set<FunctionTypeEnum> orgFuncSet, final Long buId) {
        OrgFunctionDetailBean orgFunctionDetailBean = new OrgFunctionDetailBean();
        orgFunctionDetailBean.setFunctionType(FunctionTypeEnum.PURCHASE.getIndex());
        Long defaultPayOrgId = orgFuncSet.contains(FunctionTypeEnum.FINANCE) ? buId : organizationFunction.getDefaultPayOrgId();
        orgFunctionDetailBean.setDefaultPayOrgId(defaultPayOrgId);

        Long defaultSettlementOrgId = orgFuncSet.contains(FunctionTypeEnum.FINANCE) ? buId : organizationFunction.getDefaultSettlementOrgId();
        orgFunctionDetailBean.setDefaultSettlementOrgId(defaultSettlementOrgId);

        Long defaultStorageOrg = orgFuncSet.contains(FunctionTypeEnum.STORAGE) ? buId : organizationFunction.getDefaultStockOrgId();
        orgFunctionDetailBean.setDefaultStockOrgId(defaultStorageOrg);

        return orgFunctionDetailBean;
    }

    /**
     * 构建销售数据信息实体
     *
     * @param organizationFunction 组织职能信息
     * @return 销售组织职能信息
     */
    private OrgFunctionDetailBean buildeSale(OrgFunctionReqParam organizationFunction, final Set<FunctionTypeEnum> orgFuncSet, final Long buId) {
        OrgFunctionDetailBean orgFunctionDetailBean = new OrgFunctionDetailBean();
        orgFunctionDetailBean.setFunctionType(FunctionTypeEnum.SALE.getIndex());
        orgFunctionDetailBean.setParentSaleOrgId(organizationFunction.getParentSaleOrgId());

        Long defaultReceiveOrgId = orgFuncSet.contains(FunctionTypeEnum.FINANCE) ? buId : organizationFunction.getDefaultReceiveOrgId();
        orgFunctionDetailBean.setDefaultReceiveOrgId(defaultReceiveOrgId);

        Long defaultSettlementOrgId = orgFuncSet.contains(FunctionTypeEnum.FINANCE) ? buId : organizationFunction.getDefaultSettlementOrgId();
        orgFunctionDetailBean.setDefaultSettlementOrgId(defaultSettlementOrgId);

        Long defaultStorageOrg = orgFuncSet.contains(FunctionTypeEnum.STORAGE) ? buId : organizationFunction.getDefaultStockOrgId();
        orgFunctionDetailBean.setDefaultStockOrgId(defaultStorageOrg);

        return orgFunctionDetailBean;
    }

    /**
     * 构建仓储数据信息实体
     *
     * @param organizationFunction 组织职能信息
     * @return 仓储组织职能信息
     */
    private OrgFunctionDetailBean buildeStorage(OrgFunctionReqParam organizationFunction, final Set<FunctionTypeEnum> orgFuncSet, final Long buId) {
        OrgFunctionDetailBean orgFunctionDetailBean = new OrgFunctionDetailBean();
        orgFunctionDetailBean.setFunctionType(FunctionTypeEnum.STORAGE.getIndex());

        Long defaultAccountOrgId = orgFuncSet.contains(FunctionTypeEnum.FINANCE) ? buId : organizationFunction.getDefaultAccountOrgId();
        orgFunctionDetailBean.setDefaultAccountOrgId(defaultAccountOrgId);

        Long defaultSettlementOrgId = orgFuncSet.contains(FunctionTypeEnum.FINANCE) ? buId : organizationFunction.getDefaultSettlementOrgId();
        orgFunctionDetailBean.setDefaultSettlementOrgId(defaultSettlementOrgId);

        Long defaultLogisticsOrgId = orgFuncSet.contains(FunctionTypeEnum.LOGISTICS) ? buId : organizationFunction.getDefaultLogisticsOrgId();
        orgFunctionDetailBean.setDefaultLogisticsOrgId(defaultLogisticsOrgId);

        orgFunctionDetailBean.setAddressDetail(organizationFunction.getAddressDetail());
        return orgFunctionDetailBean;
    }

    /**
     * 构建物流组织职能数据信息实体
     *
     * @param organizationFunction 组织职能信息
     * @return 物流组织职能信息
     */
    private OrgFunctionDetailBean buildeLogistics(OrgFunctionReqParam organizationFunction, final Set<FunctionTypeEnum> orgFuncSet, final Long buId) {
        OrgFunctionDetailBean orgFunctionDetailBean = new OrgFunctionDetailBean();

        orgFunctionDetailBean.setFunctionType(FunctionTypeEnum.LOGISTICS.getIndex());

        Long defaultAccountOrgId = orgFuncSet.contains(FunctionTypeEnum.FINANCE) ? buId : organizationFunction.getDefaultAccountOrgId();
        orgFunctionDetailBean.setDefaultAccountOrgId(defaultAccountOrgId);

        Long defaultSettlementOrgId = orgFuncSet.contains(FunctionTypeEnum.FINANCE) ? buId : organizationFunction.getDefaultSettlementOrgId();
        orgFunctionDetailBean.setDefaultSettlementOrgId(defaultSettlementOrgId);
        orgFunctionDetailBean.setAddressDetail(organizationFunction.getAddressDetail());
        orgFunctionDetailBean.setLongitude(organizationFunction.getLongitude());
        orgFunctionDetailBean.setLatitude(organizationFunction.getLatitude());

        return orgFunctionDetailBean;
    }
}
