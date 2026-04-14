package com.org.permission.server.org.mapper;

import com.org.permission.common.org.dto.func.*;
import com.org.permission.server.org.bean.OrgFunctionDetailBean;
import com.org.permission.common.org.dto.OrganizationFunctionDto;
import com.org.permission.common.org.param.QueryBizsFuncParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 组织职能 写 mapper
 */
@Mapper
public interface OrgFunctionMapper {

    /**
     * 批量新增组织职能
     *
     * @param beans 组织职能数据集合
     */
    int batchAddOrganizationFunction(List<OrgFunctionDetailBean> beans);

    /**
     * 修改组织的所有组织职能
     *
     * @param buId 业务单元ID
     */
    void modifyFuncStateByBUId(@Param("buId") Long buId, @Param("modifiedBy") Long modifiedBy, @Param("modifystate") Integer modifystate, @Param("sourceStates") List<Integer> sourceStates, @Param("modifiedDate") Date modifiedDate);

    /**
     * 根据 BU ID + 职能类型 删除组织职能
     *
     * @param orgId 组织ID
     */
    void delOrgFuncByOrgIdAndFuncTypes(@Param("orgId") Long orgId, @Param("funcTypes") List<Integer> funcTypes, @Param("modifiedBy") Long modifiedBy, @Param("modifiedDate") Date modifiedDate);

    /**
     * 查询业务单元的组织职能
     *
     * @param buId 业务单元ID
     * @return 组织职能集合
     */
    List<Integer> queryOrgFunctionsByOrgId(@Param("buId") Long buId);

    /**
     * 根据业务单元ID查询法人组织职能
     *
     * @param buId 业务单元ID
     * @return 法人组织职能
     */
    CorporateOrgFuncBean queryOrgCorporateFuncByBUId(@Param("buId") Integer buId);

    /**
     * 根据业务单元ID查询财务组织职能
     *
     * @param buId 业务单元ID
     * @return 财务组织职能
     */
    FinanceOrgFuncBean queryOrgFinanceFuncByBUId(@Param("buId") Integer buId);

    /**
     * 根据业务单元ID查询采购组织职能
     *
     * @param buId 业务单元ID
     * @return 采购组织职能
     */
    PurchaseOrgFuncBean queryOrgPurchaseFuncByBUId(@Param("buId") Integer buId);

    /**
     * 根据业务单元ID查询销售组织职能
     *
     * @param buId 业务单元ID
     * @return 销售组织职能
     */
    SaleOrgFuncBean queryOrgSaleFuncByBUId(@Param("buId") Integer buId);

    /**
     * 根据业务单元ID查询仓储组织职能
     *
     * @param buId 业务单元ID
     * @return 仓储组织职能
     */
    StorageOrgFuncBean queryOrgStorageFuncByBUId(@Param("buId") Integer buId);

    /**
     * 根据业务单元ID查询物流组织职能
     *
     * @param buId 业务单元ID
     * @return 物流组织职能
     */
    LogisticsOrgFuncBean queryOrgLogisticsFuncByBUId(@Param("buId") Integer buId);

    /**
     * 根据业务单元id查询金融职能
     *
     * @param buId
     * @return 金融组织职能
     */
    BankingOrgFuncBean queryOrgBankFuncByBUId(@Param("buId") Integer buId);

    /**
     * 根据组织id集合以及职能类型查询组织职能信息
     *
     * @param reqParam
     * @return
     */
    List<OrganizationFunctionDto> queryBizsFunc(@Param("reqParam") QueryBizsFuncParam reqParam);


}

