package com.org.permission.server.org.service;

import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.*;
import com.org.permission.common.org.param.*;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.server.org.bean.OrgTreeBean;
import com.org.permission.server.org.bean.SimpleBizUnitWithFuncBean;
import com.org.permission.server.org.dto.MultipleOrgTreeDto;
import com.org.permission.server.org.dto.OrgLogolDto;
import com.org.permission.server.org.dto.param.FindOrgListParam;
import com.org.permission.server.org.dto.param.UserLoginReqParam;

import java.util.List;

/**
 * 组织服务实现
 */
public interface OrganizationService {
    /**
     * 用户登陆，获取集团LOGO及对应客户ID
     *
     * @param reqParam 用户登陆请求参数
     * @return 用户登陆响应参数
     */
    OrgLogolDto queryOrgInfoByUser(UserLoginReqParam reqParam);

    /**
     * 查询组织对应的客商ID
     *
     * @param reqParam 查询请求参数
     * @return 客商ID
     */
    OrgCustDto queryOrgCust(QueryByIdReqParam reqParam);

    /**
     * 查询客商组织信息
     *
     * @param reqParam 客商 ID必填
     * @return 客商对应组织信息
     */
    CustOrgDto queryCustOrg(QueryByIdReqParam reqParam);

    /**
     * 当前组织的上级组织，同一节点同级组织，及其下属组织信息
     *
     * @param reqParam 组织树查询请求参数
     * @return 组织树
     */
    OrgTreeDto queryOrgTree(final QueryOrgTreeReqParam reqParam);


    /**
     * 查询上级组织信息
     *
     * @param orgId 组织ID
     * @return 组织信息
     */
    SimpleBizUnitWithFuncBean queryParentOrg(Long orgId);

    /**
     * ID查询组织信息
     *
     * @param orgId 组织ID
     * @return 组织信息
     */
    SimpleBizUnitWithFuncBean queryOrgById(Long orgId);

    /**
     * 批量查询组织信息
     * @param reqParam 查询请求参数
     * @return 组织信息集合
     */
    List<OrgInfoDto> batchQueryOrgInfo(BatchQueryParam reqParam);

    List<BasicOrganizationDto> batchQueryOrganizations(BatchQueryParam reqParam);

    PageInfo<BasicOrganizationDto> findOrgList(FindOrgListParam reqParam);

    OrgInfoDto getOrgInfoById(Long id);

    List<OrgInfoDto> batchQueryOrgInfoNofuc(BatchQueryParam reqParam);

    /**
     * 多组织树查询
     *
     * @param reqParam 多组织树查询请求参数
     * @return 多组织树封装结果
     */
    MultipleOrgTreeDto multipleOrgTreeQuery(final MultipleOrgTreeQueryDto reqParam);

    /**
     * 查询组织集合
     *
     * @param reqParam 查询组织列表请求参数
     * @return 组织集合
     */
    List<OrgTreeBean> queryOrgList(final QueryOrgListReqParam reqParam);

    /**
     * 查询组织权限集合
     *
     * @param reqParam 查询组织列表请求参数
     * @return 组织集合
     */
    List<OrgTreeBean> queryOrgPermissonList(QueryOrgPermissionListReqParam reqParam);

    /**
     * 根据条件查询组织列表（TMS）
     *
     * @param reqParam 组织条件查询请求参数
     * @return 组织列表
     */
    PageInfo<OrgInfoDto> queryOrgListByCondition(QueryOrgListConditionParam reqParam);

    /**
     * 同步客商业务类型
     *
     * @param reqParam 同步客商业务类型请求参数
     */
    void syncCustBizType(SyncCustBizTypeParam reqParam);

    /**
     * 查询组织的业务类型
     *
     * @param orgId 组织ID
     * @return 组织业务类型，逗号隔开（如1，2）
     */
    String queryOrgBizType(Long orgId);

    /**
     * 根据用户id和部门id查询部门信息有权限验证
     *
     * @param reqParam 查询参数
     * @return 部门信息
     */
    DepInfoDto queryDepInfoByUserIdAndOrgId(QueryPermissionOrgInfoParam reqParam);

    /**
     * 批量启停组织
     *
     * @param orgIds
     * @param userId
     * @param status
     */
    void batchEnableOrg(List<Long> orgIds, Long userId, Integer status, Integer orgType);


    /**
     * 查询本部门和本部门的上级所有部门
     *
     * @param depId
     * @return
     */
    List<DepartmentDto> findAllParentDepartment(Long depId);

    /**
     * 根据组织名称手机号查询
     *
     * @param paramList
     */
    void batchQueryOrgListByClue(List<BatchQueryOrgByClueParam> paramList);
}
