package com.org.permission.server.org.mapper;

import com.org.permission.common.org.dto.OrgInfoDto;
import com.org.permission.common.org.param.GroupListQueryParam;
import com.org.permission.common.org.param.QueryGroupListReqParam;
import com.org.permission.common.org.param.QueryOrgListInfoReqParam;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.server.org.bean.*;
import com.org.permission.server.org.dto.param.BindingCustReqParam;
import com.org.permission.server.org.dto.param.QueryGroupIncludeBusinessTypeParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 集团写mapper,开启事务
 */
@Mapper
public interface GroupMapper {
	/**
	 * 创建集团
	 *
	 * @param bean 集团信息
	 * @return 插入条数
	 */
	int addGroup(@Param(value = "bean") GroupInfoBean bean);

	/**
	 * 回写集团ID
	 *
	 * @param id 集团ID
	 */
	void writeBackGroupId(@Param(value = "id") Long id);

	/**
	 * 根据ID查询集团（锁）
	 *
	 * @param groupId 集团ID
	 * @return 数据库对应的集团数据实体
	 */
	GroupInfoBean queryGroupByIdLock(@Param(value = "groupId") Long groupId);

	/**
	 * 根据集团ID查询集团客商信息（锁）
	 *
	 * @param groupId 集团ID
	 * @return 集团客商信息及其状态信息
	 */
	OrgCustInfoBean queryGroupCustInfoByGroupIdLock(@Param(value = "groupId") Long groupId);

	/**
	 * 根据客商ID查询启用的集团
	 *
	 * @param custId 客商ID
	 * @return 同一客商启用的集团数量
	 */
	int queryEnableGroupsByCustIdLock(@Param(value = "custId") Integer custId);

	/**
	 * 集团logo管理
	 *
	 * @param bean 集团LOGO管理请求参数
	 * @return 影响行数
	 */
	int manageLogo(@Param(value = "bean") GroupLogoWriteBean bean);

	/**
	 * 根据ID查询集团状态值
	 *
	 * @param groupId 集团ID
	 * @return 数据库对应的组织状态数据实体
	 */
	OrgBean getGroupStateByIdLock(@Param(value = "groupId") Long groupId);

	/**
	 * 更新集团
	 *
	 * @param bean 更新集团请求参数
	 */
	void updateGroup(@Param(value = "bean") UpdateGroupWriteBean bean);

	/**
	 * 绑定客商
	 *
	 * @param bean 绑定客商请求参数
	 */
	void bindingCust(@Param(value = "bean") BindingCustReqParam bean);

	/**
	 * 创建集团
	 *
	 * @param orgName 集团名
	 * @return 集团信息
	 */
	List<OrgTypeBean> queryGroupByNameLock(@Param(value = "orgName") String orgName);

	/**
	 * 根据ID查询集团
	 *
	 * @param groupId 集团ID
	 * @return 数据库对应的集团数据实体
	 */
	GroupInfoBean queryGroupById(@Param(value = "groupId") Long groupId);

	/**
	 * 根据参数统计集团数量
	 *
	 * @param queryParam 查询参数
	 * @return 符合条件总条数
	 */
	int countGroup(@Param(value = "queryParam") QueryGroupListReqParam queryParam);

	/**
	 * 根据参数查询集团列表
	 *
	 * @param queryParam 查询集团列表请求参数
	 * @return 数据库对应的集团数据实体列表
	 */
	List<GroupInfoBean> queryGroupList(@Param(value = "queryParam") QueryGroupListReqParam queryParam);

	/**
	 * 查询集团logo
	 *
	 * @param groupId 集团ID
	 * @return logo字节流
	 */
	String queryGroupLogoById(@Param(value = "groupId") Long groupId);

	/**
	 * 根据集团ID,查询全局客户ID
	 *
	 * @param groupId 集团ID
	 * @return 客户ID
	 */
	Long queryCustByGroupId(@Param(value = "groupId") Long groupId);

	/**
	 * 查询集团的业务类型
	 *
	 * @param groupId 集团ID
	 * @return 业务类型
	 */
	String queryGroupBusinessType(@Param(value = "groupId") Long groupId);

	/**
	 * 组织列表信息查询
	 *
	 * @param queryParam 查询请求参数
	 * @return 组织列表
	 */
	List<OrgListBean> queryGroupListInfo(@Param("queryParam") QueryOrgListInfoReqParam queryParam);

	/**
	 * 根据时间,查询业务
	 *
	 * @param param
	 * @return
	 */
	List<String> queryGroupIncludeBusinessType(@Param(value = "param") QueryGroupIncludeBusinessTypeParam param);

	Integer queryTotalCountByBusiType(@Param(value = "param") List<String> param);

	/**
	 * 查询根业务单元列表
	 *
	 * @param queryParam 查询请求参数
	 * @return 根业务单元列表
	 */
	List<OrgInfoDto> queryAllGroupInfoList(@Param(value = "queryParam") GroupListQueryParam queryParam);

	Integer queryGroupsByPojo(@Param(value = "queryParam") GroupListQueryParam queryParam);


	/**
	 * 查询未关联客商的集团
	 *
	 * @param queryParam
	 * @return
	 */
	List<OrgInfoDto> queryGroupsNotRelCust(@Param(value = "queryParam") GroupListQueryParam queryParam);

	/**
	 * 根据业务单元ID集合查询集团信息（权限）
	 *
	 * @param queryParam 查询请求参数
	 * @return 集团信息列表
	 */
	List<OrgInfoDto> queryGroupInfoByOrgIds(@Param(value = "queryParam") BatchQueryParam queryParam);
}
