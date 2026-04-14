package com.org.permission.server.org.mapper;

import com.org.permission.common.org.param.QueryBindingStaffParam;
import com.org.permission.server.org.bean.WarehouseBindingStaffInfoBean;
import com.org.permission.server.org.dto.param.BatchOpParam;
import com.org.permission.server.org.dto.param.BindingStaffParam;
import com.org.permission.server.org.dto.param.BingdingStaffReqParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 人员绑定关系
 */
@Mapper
public interface StaffBindingRelationMapper {

	/**
	 * 绑定人员
	 *
	 * @param param 绑定关系请求参数
	 */
	void bindingStaff(@Param("param") BindingStaffParam param);

	/**
	 * 查询已存在的绑定关系，判重使用
	 *
	 * @param param 绑定关系请求参数
	 * @return 人员ID集合
	 */
	List<Integer> qureyBindingRelation(@Param("param") BingdingStaffReqParam param);

	/**
	 * 解除绑定关系
	 *
	 * @param param 请求参数
	 */
	void unbindingRelation(@Param("param") BatchOpParam param);

	/**
	 * 统计绑定人员
	 *
	 * @return 人员总数
	 */
	Integer countBindingStaff(@Param(value = "queryParam") QueryBindingStaffParam queryParam);

	/**
	 * 查询绑定人员基础信息
	 *
	 * @return 人员基础信息集合
	 */
	List<WarehouseBindingStaffInfoBean> queryBindingStaff(@Param(value = "queryParam") QueryBindingStaffParam queryParam);

	/**
	 * 查询人员绑定信息简要信息（根据关系ID和人员编码）
	 * @param queryParam
	 * @return
	 */
	WarehouseBindingStaffInfoBean queryBindingStaffBriefInfo(@Param(value = "queryParam") QueryBindingStaffParam queryParam);

	/**
	 * 查询未绑定仓库的人员信息
	 * @param queryParam
	 * @return
	 */
	List<WarehouseBindingStaffInfoBean> queryNotBindingStaff(@Param(value = "queryParam") QueryBindingStaffParam queryParam);
}
