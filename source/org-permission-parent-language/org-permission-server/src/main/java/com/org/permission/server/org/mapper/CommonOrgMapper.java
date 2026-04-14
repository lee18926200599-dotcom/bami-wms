package com.org.permission.server.org.mapper;


import com.org.permission.common.org.dto.BasicOrganizationDto;
import com.org.permission.common.org.dto.DepInfoDto;
import com.org.permission.common.org.dto.OrgInfoDto;
import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.common.org.param.QueryOrgListConditionParam;
import com.org.permission.common.org.param.QueryPermissionOrgInfoParam;
import com.org.permission.common.org.param.SyncCustBizTypeParam;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.server.org.bean.*;
import com.org.permission.server.org.dto.param.FindOrgListParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 通用 组织读 mapper,开启事务
 */
@Mapper
public interface CommonOrgMapper {

    /**
     * 统计客商绑定的（未删除态）组织数量
     *
     * @param custId 客户ID
     * @return 绑定数量
     */
    int countOrgByCustId(@Param(value = "custId") Long custId);

    /**
     * 启用组织
     *
     * @param orgId  组织ID
     * @param userId 用户ID
     */
    void enableOrg(@Param(value = "userId") Long userId, @Param(value = "orgId") Long orgId, @Param(value = "modifiedDate") Date modifiedDate);

    /**
     * 停用组织
     *
     * @param orgId  组织ID
     * @param userId 用户ID
     */
    void disableOrg(@Param(value = "userId") Long userId, @Param(value = "orgId") Long orgId, @Param(value = "modifiedDate") Date modifiedDate);

    /**
     * 停用组织
     *
     * @param orgIds 组织ID集合
     * @param userId 用户ID
     */
    void batchDisableOrg(@Param(value = "userId") Long userId, @Param(value = "orgIds") List<Long> orgIds, @Param(value = "state") Integer status, @Param(value = "orgType") Integer orgType);

    /**
     * 级联停用组织及子组织
     *
     * @param bean 停用请求参数
     */
    void cascadeDisableGroupOrg(@Param(value = "bean") EnableOperateParam bean);

    /**
     * 伪删除组织
     *
     * @param orgId  组织ID
     * @param userId 用户ID
     */
    void deleteOrgById(@Param(value = "orgId") Long orgId, @Param(value = "userId") long userId);

    /**
     * 查询组织绑定客商信息（锁）
     *
     * @param queryParam 集团ID
     * @return 组织绑定客商信息及其状态信息
     */
    OrgCustInfoBean queryOrgCustInfoLock(@Param(value = "queryParam") SyncCustBizTypeParam queryParam);

    /**
     * 修改集团业务类型
     *
     * @param id         主键
     * @param newBizType 新业务类型
     * @param operatorId 操作人ID
     */
    void modifyOrgBizType(@Param(value = "id") Long id, @Param(value = "newBizType") String newBizType, @Param(value = "operatorId") Long operatorId);

    /**
     * 根据组织ID,查询组织类型信息
     *
     * @param orgId 组织ID
     * @return 组织类型信息
     */
    OrgTypeBean queryEnableOrgTypeByOrgId(@Param(value = "orgId") Long orgId);

    /**
     * 根据客商ID,查询组织类型信息
     *
     * @param custId 组织ID
     * @return 组织类型信息
     */
    OrgListBean queryOrgListByCustId(@Param(value = "custId") Integer custId);

    /**
     * 根据组织ID,查询组织名称
     *
     * @param orgId 组织ID
     * @return 组织名称
     */
    String queryOrgNameByOrgId(@Param(value = "orgId") Long orgId);

    /**
     * 根据组织ID,查询组织编码
     *
     * @param orgId 组织ID
     * @return 组织编码
     */
    String queryOrgCodeByOrgId(@Param(value = "orgId") Long orgId);

    /**
     * 根据组织ID,查询该组织所属集团下的所有组织结构信息
     *
     * @param orgId 组织ID
     * @return 组织结构信息
     */
    List<OrgTreeBean> queryGroupEnableOrgTreeByOrgId(@Param(value = "orgId") Long orgId);

    /**
     * 根据组织id查询未删除的组织信息
     *
     * @param orgId
     * @return
     */
    List<OrgTreeBean> queryGroupNotStopOrgTreeByOrgId(@Param(value = "orgId") Long orgId);


    /**
     * 根据组织ID,查询该组织所属集团下的组织结构信息（不含部门）
     *
     * @param orgId 组织ID
     * @return 组织结构信息
     */
    List<OrgTreeBean> queryGroupOrgTreeNotContainDepByOrgId(@Param(value = "orgId") Long orgId);

    /**
     * 根据组织ID,查询该组织结构信息
     *
     * @param orgId 组织ID
     * @return 组织结构信息
     */
    OrgTreeBean queryOrgHierarchyInfoByOrgId(@Param(value = "orgId") Long orgId);

    /**
     * 根据组织ID，查询组织信息及组织功能信息
     *
     * @param orgId 组织ID
     * @return 组织信息
     */
    SimpleBizUnitWithFuncBean queryOrgAndFuncInfo(@Param(value = "orgId") Long orgId);

    /**
     * 根据组织名称查询启用的组织信息
     *
     * @param orgName 组织名称
     * @return 组织树信息集合
     */
    List<OrgTreeBean> queryEnableOrgByName(@Param(value = "orgName") String orgName);

    /**
     * 查询集团所有启用的业务单元以上的组织信息
     *
     * @param groupIds 集团ID 集合
     * @return 组织树信息集合
     */
    List<OrgTreeBean> queryGroupAllEnableOrg(@Param(value = "groupIds") List<Long> groupIds);

    /**
     * 批量查询组织信息
     *
     * @param orgIds 组织ID集合
     * @return 组织树信息集合
     */
    List<OrgTreeBean> queryAllEnableOrgWithSepicalFunc(@Param(value = "orgIds") List<Long> orgIds, @Param(value = "groupId") Long groupId, @Param(value = "funcs") List<Integer> funcs);

    /**
     * 根据条件查询组织列表
     *
     * @param queryParam 组织条件查询请求参数
     * @return 组织列表
     */
    List<OrgInfoDto> queryOrgListByCondition(@Param(value = "queryParam") QueryOrgListConditionParam queryParam);

    /**
     * 批量查询组织信息
     */
    List<OrgInfoDto> batchQueryOrgInfo(@Param(value = "queryParam") BatchQueryParam queryParam);

    List<BasicOrganizationDto> batchQueryOrganizations(@Param(value = "queryParam") BatchQueryParam queryParam);

    List<BasicOrganizationDto> findOrgList(@Param(value = "queryParam") FindOrgListParam queryParam);

    OrgInfoDto getOrgInfoById(@Param(value = "id") Long id);

    List<OrgInfoDto> batchQueryOrgInfoNofuc(@Param(value = "queryParam") BatchQueryParam queryParam);


    List<OrgInfoDto> batchQueryOrgInfoByCondition(@Param(value = "queryParam") BatchQueryParam queryParam);

    /**
     * 根据用户id和组织id查询组织信息
     *
     * @param reqParam
     * @return
     */
    DepInfoDto queryDepInfoByUserIdAndOrgId(@Param(value = "reqParam") QueryPermissionOrgInfoParam reqParam);

    /**
     * 根据名字和类型查询id
     *
     * @param orgName
     * @return
     */
    Long queryOrgIdByName(@Param(value = "orgName") String orgName, @Param(value = "orgType") Integer orgType, @Param(value = "groupId") Long groupId, @Param(value = "buId") Long buId);

}
