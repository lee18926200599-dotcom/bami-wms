package com.org.permission.server.org.mapper;


import com.org.permission.common.org.dto.*;
import com.org.permission.common.org.param.QueryBizUnitListByFuncParam;
import com.org.permission.common.org.param.QueryBizUnitListReqParam;
import com.org.permission.common.org.param.QueryByIdReqParam;
import com.org.permission.common.org.param.QueryGroupBUByFuncReqParam;
import com.org.permission.server.org.bean.*;
import com.org.permission.server.org.dto.param.BindingCustReqParam;
import com.org.permission.server.org.dto.param.RootBUListQueryParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 集团写mapper,开启事务
 */
@Mapper
public interface BizUnitMapper  {
    /**
     * 创建集团
     *
     * @param bean 集团信息
     * @return 生成ID
     */
    int addBizUnit(@Param(value = "bean") BizUnitWithFuncDetailBean bean);

    /**
     * 回写新增根业务单元的公司ID
     *
     * @param rootBUId 根业务单元ID
     */
    void writebackRootBUCompanyId(@Param(value = "rootBUId") Long rootBUId);

    /**
     * 更新业务单元
     *
     * @param bean 更新参数
     */
    void updateBizUnit(@Param(value = "bean") UpdateBizUnitWriteBean bean);

    /**
     * 根据ID查询业务单元状态值
     *
     * @param bizUnitId 业务单元ID
     * @return 数据库对应的组织状态数据实体
     */
    OrgBean getBizUnitStateByIdLock(@Param(value = "bizUnitId") Long bizUnitId);

    /**
     * 业务单元绑定内部客商
     *
     * @param param 绑定客商请求参数
     */
    void bindingInnerCust(@Param(value = "param") BindingCustReqParam param);

    /**
     * 业务单元绑定客商
     *
     * @param param 绑定客商请求参数
     */
    void bindingCust(@Param(value = "param") BindingCustReqParam param);

    /**
     * 根据名称查询非删除态业务单元状态值
     *
     * @param buName  业务单元名称
     * @param groupId 集团ID
     * @return 数据库对应的组织状态数据实体
     */
    OrgBean queryAvailableBUByNameLock(@Param(value = "buName") String buName, @Param(value = "groupId") Long groupId);

    /**
     * 根据ID查询业务单元
     *
     * @param bizUnitId 业务单元ID
     * @return 数据库对应的业务单元数据实体
     */
    BizUnitWithFuncDetailBean queryBizUnitByIdLock(@Param(value = "bizUnitId") Long bizUnitId);

    /**
     * 启用集团根业务单元
     *
     * @param groupId      集团 ID
     * @param userId       用户 ID
     * @param modifiedDate 更新时间
     */
    void enableRootBUByGroupId(@Param(value = "groupId") Long groupId, @Param(value = "userId") Long userId, @Param(value = "modifiedDate") Date modifiedDate);

    /**
     * 根据ID查询业务单元
     *
     * @param reqParam 业务单元ID
     * @return 数据库对应的业务单元数据实体
     */
    BizUnitWithFuncDetailBean queryBizUnitById(@Param(value = "reqParam") QueryByIdReqParam reqParam);

    /**
     * 根据ID查询业务单元
     *
     * @param buIds 业务单元ID集合
     * @return 数据库对应的业务单元数据实体
     */
    List<BizUnitWithFuncDetailBean> queryBizUnitByIds(@Param(value = "buIds") List<Long> buIds);

    List<BizUnitWithFuncDetailBean> queryBizByGroupIdAndAddress(@Param(value = "param") QueryBizByGroupIdAndAddressParam param);

    /**
     * 根据参数统计业务单元数量
     *
     * @param queryParam 查询请求参数
     * @return 符合条件总条数
     */
    int countBizUnit(@Param(value = "queryParam") QueryBizUnitListReqParam queryParam);

    /**
     * 根据参数查询业务单元列表
     *
     * @param queryParam 查询业务单元列表请求参数
     * @return 数据库对应的业务单元数据实体列表
     */
    List<BizUnitWithFuncInfoBean> queryBizUnitList(@Param(value = "queryParam") QueryBizUnitListReqParam queryParam, @Param(value = "buIds") List<Long> buIds);

    Integer queryBizUnitListTotalCount(@Param(value = "queryParam") QueryBizUnitListReqParam queryParam, @Param(value = "buIds") List<Long> buIds);

    /**
     * 查询根业务单元列表
     *
     * @param queryParam 查询请求参数
     * @return 根业务单元列表
     */
    List<OrgInfoDto> queryRootBUInfoList(@Param(value = "queryParam") RootBUListQueryParam queryParam);

    List<OrgListBean> queryGroupBUByFunc(@Param("queryParam") QueryGroupBUByFuncReqParam queryParam);

    /**
     * 根据组织ID，查询组织信息及组织功能信息
     *
     * @param orgId 组织ID
     * @return 组织信息
     */
    SimpleBizUnitWithFuncBean queryOrgAndFuncInfo(@Param(value = "orgId") Long orgId);

    /**
     * 根据组织职能查询组织简要信息
     *
     * @param orgIds   集团ID
     * @param funcType 职能类型
     * @return 组织简要信息集合
     */
    List<OrgTreeBean> queryGroupBUByOrgIdAndFunc(@Param("orgIds") List<Long> orgIds, @Param("funcType") Integer funcType);

    /**
     * 统计具有某组织职能的业务单元数量
     *
     * @param queryParam 查询参数
     * @return 总数量
     */
    int countBizUnitListByFunc(@Param("queryParam") QueryBizUnitListByFuncParam queryParam);

    /**
     * 查询具有某组织职能的业务单元
     *
     * @param queryParam 查询参数
     * @return 组织简要信息
     */
    List<OrgConciseInfoDto> queryBizUnitListByFunc(@Param("queryParam") QueryBizUnitListByFuncParam queryParam);

    /**
     * 过滤具有财务组织职能的业务单元
     *
     * @param orgIds 业务单元ID集合
     * @return 业务单元ID集合
     */
    List<Long> fillHasFinanceOrgFuncBU(@Param("orgIds") List<Long> orgIds);

    /**
     * 根据集团 ID 查询根业务单元信息
     *
     * @param groupId 集团ID
     * @return 根业务单元信息
     */
    OrgListBean queryRootBUByGroupId(@Param("groupId") Long groupId);

    /**
     * 根据组织 ID 查询其根业务单元
     *
     * @param orgId 组织 ID
     * @return 业务单元信息
     */
    OrgListDto queryOrgRootBU(@Param("orgId") Long orgId);

    /**
     * 根据全局客商 ID 查询其业务单元
     *
     * @param custId 客商ID
     * @return 业务单元信息
     */
    OrgListBean queryBUByCustId(@Param("custId") Integer custId);

    /**
     * 根据集团id查询业务单元
     *
     * @param reqParam id 必填
     * @return 组织新列表
     */
    List<OrgInfoDto> queryBUByGroupId(@Param("reqParam") QueryByIdReqParam reqParam);

    /**
     * 根据集团 ID 查询该集团下未绑定客商的业务单元
     *
     * @param reqParam 请求参数
     * @return 业务单元列表
     */
    List<OrgListBean> queryGroupUnbindingCustBUs(@Param("reqParam") QueryBizUnitListReqParam reqParam);

    /**
     * 根据集团id查询根业务单元
     *
     * @param groupId 集团 ID
     * @return 根业务单元 ID
     */
    Long queryBUByGroupIdForApp(@Param("groupId") Integer groupId);

    /**
     * 查询客商组织信息
     *
     * @param reqParam 客商 ID 必填
     * @return 客商对应组织信息
     */
    CustOrgDto queryCustOrg(@Param("reqParam") QueryByIdReqParam reqParam);

}
