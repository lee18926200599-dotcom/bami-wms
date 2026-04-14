package com.org.permission.server.org.mapper;


import com.org.permission.server.org.bean.PurchaseEntrustRelationBean;
import com.org.permission.server.org.bean.PurchaseEntrustRelationInfoBean;
import com.org.permission.common.org.param.PurchaseEntrustRelationQueryParam;
import com.org.permission.server.org.dto.param.PurchaseEntrustRelationReqParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 采购业务委托关系写
 */
@Mapper
public interface PurchaseEntrustRelationMapper {

    /**
     * 新增采购业务委托关系
     *
     * @param bean 采购业务委托关系数据实体
     */
    void addPurchaseEntrustRelation(@Param(value = "bean") PurchaseEntrustRelationBean bean);

    /**
     * 新增采购业务委托关系
     *
     * @param bean 采购业务委托关系数据实体
     */
    void updatePurchaseEntrustRelation(@Param(value = "bean") PurchaseEntrustRelationBean bean);

    /**
     * 更新业务单元产生的采购业务委托关系
     *
     * @param bean 采购业务委托关系数据实体
     */
    void updateBUProductPurchaseEntrustRelation(@Param(value = "bean") PurchaseEntrustRelationBean bean);

    /**
     * 根据ID,查询采购业务委托关系
     *
     * @param entruestId 委托关系 ID
     * @return 采购业务委托关系集合
     */
    PurchaseEntrustRelationBean queryPurchaseEntrustRelationByIdLock(@Param(value = "entruestId") Long entruestId);

    /**
     * 根据采购组织ID,查询集团内采购业务委托关系
     *
     * @param purchaseOrgId 采购
     * @return 集团内采购业务委托关系集合
     */
    List<PurchaseEntrustRelationBean> queryPurchaseEntrustRelationByPurchaseOrgIdLock(@Param(value = "purchaseOrgId") Long purchaseOrgId);

    /**
     * 根据采购组织ID,删除采购业务委托关系
     *
     * @param purchaseOrgId 采购
     */
    void deleteEntrustRelationByPurchaseOrgId(@Param(value = "purchaseOrgId") Long purchaseOrgId);

    /**
     * 统计采购委托关系
     *
     * @param queryParam 分页查询采购委托关系查询请求参数
     * @return 数量
     */
    int countPurchaseEntrustRelation(@Param(value = "queryParam") PurchaseEntrustRelationReqParam queryParam);

    /**
     * 查询采购委托关系列表
     *
     * @param queryParam 分页查询采购委托关系查询请求参数
     * @return 采购委托关系集合
     */
    List<PurchaseEntrustRelationInfoBean> queryPurchaseEntrustRelationList(@Param(value = "queryParam") PurchaseEntrustRelationReqParam queryParam);

    List<PurchaseEntrustRelationInfoBean> queryPurchaseEntrustRelation(@Param(value = "queryParam") PurchaseEntrustRelationQueryParam queryParam);
}
