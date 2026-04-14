package com.org.client.feign;


import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.DepInfoDto;
import com.org.permission.common.org.param.BatchQueryOrgByClueParam;
import com.common.util.message.RestMessage;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.common.util.Constant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**

 * 组织
 */
@FeignClient(value = Constant.ORG_SERVER_NAME)
public interface OrganizationFeign {

	/**
	 * 查询组织客商信息
	 *
	 * @param reqParam 组织 ID必填
	 * @return 组织对应客商信息
	 */
	@PostMapping(value = "/organization/queryOrgCust")
	RestMessage<com.org.permission.common.org.dto.OrgCustDto> queryOrgCust(@RequestBody com.org.permission.common.org.param.QueryByIdReqParam reqParam);

	/**
	 * 查询客商组织信息
	 *
	 * @param reqParam 客商 ID必填
	 * @return 客商对应组织信息
	 */
	@PostMapping(value = "/organization/queryCustOrg")
	RestMessage<com.org.permission.common.org.dto.CustOrgDto> queryCustOrg(@RequestBody com.org.permission.common.org.param.QueryByIdReqParam reqParam);

	/**
	 * 查询上级组织信息(若可以拿到上级组织ID,可 使用 {@link #queryOrgInfoById(com.org.permission.common.org.param.QueryByIdReqParam)}} 替换)
	 * 若当前业务单元为根业务单元，返回集团信息
	 * 若当前组织为部门，返回上级部门，若上级部门为空则返回所属业务单元信息
	 *
	 * @param orgId 组织ID
	 * @return 组织信息（均不含组织职能）
	 */
	@GetMapping(value = "/organization/queryParentOrg")
	RestMessage<com.org.permission.common.org.dto.OrganizationDto> queryParentOrg(@RequestParam(name = "orgId") Long orgId);

	/**
	 * 根据组织ID,查询组织信息
	 *
	 * @param reqParam ID查询请求参数
	 * @return 组织信息（集团，业务单元无组织职能）
	 */
	@PostMapping(value = "/organization/queryOrgInfoById")
	RestMessage<com.org.permission.common.org.dto.OrganizationDto> queryOrgInfoById(@RequestBody com.org.permission.common.org.param.QueryByIdReqParam reqParam);

	/**
	 * 批量查询组织信息（可查集团;业务单元;部门）
	 *
	 * @param reqParam 查询参数
	 * @return 组织信息
	 */
	@PostMapping(value = "/organization/batchQueryOrgInfo")
	RestMessage<List<com.org.permission.common.org.dto.OrgInfoDto>> batchQueryOrgInfo(@RequestBody BatchQueryParam reqParam);

	/**
	 * 根据组织ID,查询组织树
	 *
	 * @param reqParam 组织树查询请求参数
	 * @return 组织树
	 */
	@PostMapping(value = "/organization/queryOrgTree")
	RestMessage<com.org.permission.common.org.dto.OrgTreeDto> queryOrgTree(@RequestBody final com.org.permission.common.org.param.QueryOrgTreeReqParam reqParam);

	/**
	 * 根据组织ID,查询组织下的所有员工信息
	 * 权限可控制
	 *
	 * @param reqParam 查询人员请求参数
	 * @return 员工信息集合
	 */
	@PostMapping(value = "/organization/queryAllStaffsInOrg")
	RestMessage<List<com.org.permission.common.org.dto.StaffInfoDto>> queryAllStaffsInOrg(@RequestBody com.org.permission.common.org.param.QueryAllStaffsInOrgReqParam reqParam);

	/**
	 * 多组织树查询
	 *
	 * 注：只返回业务单元
	 *
	 * @param reqParam 多组织树查询请求参数
	 * @return 多组织树
	 */
	@PostMapping(value = "/organization/multipleOrgTreeQuery")
	RestMessage<List<com.org.permission.common.org.dto.OrgTreeDto>> multipleOrgTreeQuery(@RequestBody final com.org.permission.common.org.param.MultipleOrgTreeQueryDto reqParam);

	/**
	 * 查询组织列表
	 *
	 * @param reqParam 组织列表查询请求参数
	 * @return 组织简要信息集合
	 */
	@PostMapping(value = "/organization/queryOrgList")
	RestMessage<List<com.org.permission.common.org.dto.OrgInfoDto>> queryOrgList(@RequestBody final com.org.permission.common.org.param.QueryOrgListReqParam reqParam);

	/**
	 * 查询组织权限列表(含集团、业务单元、部门)
	 *
	 * @param reqParam 组织权限列表查询请求参数
	 * @return 组织简要信息集合
	 */
	@PostMapping(value = "/organization/queryOrgPermissionList")
	RestMessage<List<com.org.permission.common.org.dto.OrgInfoDto>> queryOrgPermissionList(@RequestBody final com.org.permission.common.org.param.QueryOrgPermissionListReqParam reqParam);

	/**
	 * 根据条件查询组织列表（TMS）
	 *
	 * @param reqParam 组织条件查询请求参数
	 * @return 组织列表
	 */
	@PostMapping(value = "/organization/queryOrgListByCondition")
	RestMessage<PageInfo<com.org.permission.common.org.dto.OrgInfoDto>> queryOrgListByCondition(@RequestBody final com.org.permission.common.org.param.QueryOrgListConditionParam reqParam);

	/**
	 * 根据用户id和组织id查询（权限校验）查询部门信息
	 *
	 * @param reqParam 查询参数
	 * @return 部门信息
	 */
	@PostMapping(value = "/organization/queryDepInfoByUserIdAndOrgId")
	RestMessage<DepInfoDto> queryDepInfoByUserIdAndOrgId(@RequestBody final com.org.permission.common.org.param.QueryPermissionOrgInfoParam reqParam);


	/**
	 * 根据集团id同步客商类型
	 * @param reqParam 更新参数
	 * @return 无
	 */
	@PostMapping(value = "/organization/syncCustBizType")
	RestMessage<Boolean> syncCustBizType(@RequestBody com.org.permission.common.org.param.SyncCustBizTypeParam reqParam);

	//批量查询组织信息(无组织职能)
	@PostMapping(value = "/organization/batchQueryOrganizations")
	RestMessage<List<com.org.permission.common.org.dto.BasicOrganizationDto>> batchQueryOrganizations(@RequestBody BatchQueryParam reqParam);

	@PostMapping(value = "/organization/batchQueryOrgListByClue")
	RestMessage<List<com.org.permission.common.org.param.BatchQueryOrgByClueParam>> batchQueryOrgListByClue(@RequestBody List<BatchQueryOrgByClueParam> queryOrgByClueParamList);

}
