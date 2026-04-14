package com.org.permission.server.org.mapper;

import com.org.permission.server.org.bean.SaleEntrustRelationBean;
import com.org.permission.server.org.bean.SaleEntrustRelationInfoBean;
import com.org.permission.common.org.param.SaleEntrustRelationQueryParam;
import com.org.permission.server.org.dto.param.SaleEntrustRelationReqParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 销售业务委托关系
 */
@Mapper
public interface SaleEntrustRelationMapper {

    /**
     * 新增销售业务委托关系
     *
     * @param bean 销售业务委托关系数据实体
     */
    void addSaleEntrustRelation(@Param(value = "bean") SaleEntrustRelationBean bean);

    /**
     * 更新销售业务委托关系
     *
     * @param bean 销售业务委托关系数据实体
     */
    void updateSaleEntrustRelation(@Param(value = "bean") SaleEntrustRelationBean bean);

    /**
     * 更新默认销售业务委托关系
     *
     * @param bean 销售业务委托关系数据实体
     */
    void updateBUProductSaleEntrustRelation(@Param(value = "bean") SaleEntrustRelationBean bean);

    /**
     * 根据ID,查询销售业务委托关系
     *
     * @param entruestId 委托关系 ID
     * @return 销售业务委托关系
     */
    SaleEntrustRelationBean querySaleEntrustRelationByIdLock(@Param(value = "entruestId") Long entruestId);

    /**
     * 根据销售组织ID,查询集团内销售业务委托关系
     *
     * @param saleOrgId 销售组织ID
     * @return 集团内销售业务委托关系集合
     */
    List<SaleEntrustRelationBean> querySaleEntrustRelationBySaleOrgIdLock(@Param(value = "saleOrgId") Long saleOrgId);

    /**
     * 统计销售委托关系
     *
     * @param reqParam 分页查询销售委托关系查询请求参数
     * @return 数量
     */
    int countSaleEntrustRelation(@Param(value = "queryParam") SaleEntrustRelationReqParam reqParam);

    /**
     * 查询销售委托关系列表
     *
     * @param reqParam 分页查询销售委托关系查询请求参数
     * @return 销售委托关系集合
     */
    List<SaleEntrustRelationInfoBean> querySaleEntrustRelationList(
            @Param(value = "queryParam") SaleEntrustRelationReqParam reqParam);

    /**
     * 查询销售委托关系
     *
     * @param reqParam 查询销售委托关系请求参数
     * @return 销售委托关系集合
     */
    List<SaleEntrustRelationInfoBean> querySaleEntrustRelation(@Param(value = "queryParam") SaleEntrustRelationQueryParam reqParam);
}
