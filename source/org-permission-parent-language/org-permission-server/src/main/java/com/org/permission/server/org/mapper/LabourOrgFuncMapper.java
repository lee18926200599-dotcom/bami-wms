package com.org.permission.server.org.mapper;

import com.org.permission.server.org.bean.LabourOrgFuncBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 组织职能 写 mapper
 */
@Mapper
public interface LabourOrgFuncMapper {

	/**
	 * 新增物流组织职能
	 *
	 * @param bean 物流组织职能数据实体
	 */
	int addLabourFunc(@Param("bean") LabourOrgFuncBean bean);

}

