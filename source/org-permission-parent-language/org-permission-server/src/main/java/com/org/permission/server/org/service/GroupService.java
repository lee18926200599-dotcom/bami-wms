package com.org.permission.server.org.service;


import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.OrgInfoDto;
import com.org.permission.common.org.dto.OrgUser;
import com.org.permission.common.org.param.*;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.server.org.bean.GroupInfoBean;
import com.org.permission.server.org.bean.OrgListBean;
import com.org.permission.server.org.dto.ManageGroupLogo;
import com.org.permission.server.org.dto.param.BindingCustReqParam;
import com.org.permission.server.org.dto.param.EnableGroupParam;
import com.org.permission.server.org.dto.param.GroupReqParam;
import com.org.permission.server.org.dto.param.QueryGroupIncludeBusinessTypeParam;
import com.boss.crm.common.dto.customer.CustSaicRawDto;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 集团管理服务
 */
public interface GroupService {
	/**
	 * 创建集团
	 *
	 * @param reqParam 创建集团请求参数
	 */
	void createGroup(GroupReqParam reqParam);

	/**
	 * 客商生成集团
	 *
	 * @param reqParam 客商生成集团请求参数
	 */
	OrgUser custGenerateGroup(CustGenerateGroupParam reqParam);

	/**
	 * 绑定客商
	 *
	 * @param reqParam 绑定客商请求参数
	 */
	void bindingCust(BindingCustReqParam reqParam);

	/**
	 * 删除集团
	 *
	 * @param reqParam 删除操作请求参数
	 */
	void deleteGroup(KeyOperateParam reqParam);

	/**
	 * 修改集团
	 *
	 * @param reqParam 修改集团请求参数
	 */
	void updateGroup(GroupReqParam reqParam);

	/**
	 * 启停集团
	 *
	 * @param reqParam 启用集团请求参数
	 */
	OrgUser enableGroup(EnableGroupParam reqParam);

	/**
	 * 集团LOGO管理
	 *
	 * @param reqParam 集团logo管理请求参数
	 */
	void groupLogo(ManageGroupLogo reqParam);

	/**
	 * 根据ID查询集团信息
	 *
	 * @param reqParam 根据ID查询集团信息请求参数
	 * @return 集团信息
	 */
	GroupInfoBean queryGroupById(QueryByIdReqParam reqParam);

	/**
	 * 集团列表信息查询
	 *
	 * @param reqParam 集团列表信息查询请求参数
	 * @return 集团信息集合
	 */
	PageInfo<GroupInfoBean> queryGroupInfoList(QueryGroupListReqParam reqParam);
	/**
	 * 查询集团的业务类型
	 * 集团已初始化 且状态为2
	 *
	 * @param groupId 集团ID
	 * @return 集团业务类型，逗号隔开（如1，2）
	 */
	String queryGroupBusinessType(Long groupId);

	/**
	 * 首页特定业务类型特定时间统计
	 * @param reqParam
	 * @return
	 */
	List<String> queryGroupIncludeBusinessType(QueryGroupIncludeBusinessTypeParam reqParam);

	Integer queryTotalCountByBusiType(List<String> busiType);


	/**
	 * 查询集团列表
	 *
	 * @param reqParam 集团列表查询请求参数
	 * @return 集团列表
	 */
	List<OrgListBean> queryGroupList(@RequestBody QueryOrgListInfoReqParam reqParam);

	/**
	 * 查询根业务单元列表
	 * 注：也产品线要求，没有进行实质性的分页处理
	 *
	 * @param reqParam 查询参数
	 * @return 查询结果
	 */
	PageInfo<OrgInfoDto> queryAllGroupInfoList(GroupListQueryParam reqParam);

	/**
	 * 查询未关联客商的集团
	 * @param reqParam
	 * @return
	 */
	PageInfo<OrgInfoDto> queryGroupsNotRelCust(GroupListQueryParam reqParam);


	/**
	 * 根据组织ID列表查询对应的集团信息（权限）
	 *
	 * @param reqParam 批量查询请求参数
	 * @return 集团信息
	 */
	PageInfo<OrgInfoDto> queryGroupInfoByOrgIds(BatchQueryParam reqParam);

	/**
	 * 根据组织ID列表查询对应的集团信息（权限）
	 *
	 * @param reqParam 批量查询请求参数
	 * @return 集团信息
	 */
	List<OrgInfoDto> queryGroupInfoByOrgIdsNoPage(BatchQueryParam reqParam);

	/**
	 * 生成集团后，客户再由非工商改成已工商时，增加法人职能
	 * @param custSaicRawDto 客户的工商信息
	 * @param groupId 集团Id
	 * @param rootUnitOrgId  根业务单元Id
	 */
	void addCorpFunction(CustSaicRawDto custSaicRawDto, Long groupId, Long rootUnitOrgId);
}
