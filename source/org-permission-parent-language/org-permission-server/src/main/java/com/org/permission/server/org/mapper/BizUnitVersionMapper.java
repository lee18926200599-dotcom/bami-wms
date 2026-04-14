package com.org.permission.server.org.mapper;

import com.org.permission.server.org.bean.BizUnitWithFuncDetailBean;
import com.org.permission.server.org.dto.param.SaveNewVersionBUParam;
import com.org.permission.server.org.dto.param.VersionPageQueryParam;
import com.org.permission.server.org.vo.BUVersionInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 集团写mapper
 */
@Mapper
public interface BizUnitVersionMapper {
	/**
	 * 创建业务单元版本
	 *
	 * @param bean 业务单元版本信息
	 * @return 保存数量
	 */
	int saveBizUnitNewVersion(@Param(value = "bean") SaveNewVersionBUParam bean);
	/**
	 * 统计业务单元版本数量
	 *
	 * @param queryParam 查询请求参数
	 * @return 数量
	 */
	int countBUVersionList(@Param("queryParam") VersionPageQueryParam queryParam);

	/**
	 * 查询业务单元版本信息
	 *
	 * @param queryParam 查询请求参数
	 * @return 版本列表
	 */
	List<BUVersionInfoVo> queryBUVersionList(@Param("queryParam") VersionPageQueryParam queryParam);

	/**
	 * 根据版本ID查询业务单元
	 *
	 * @param versionId 版本ID
	 * @return 业务单元详细信息
	 */
	BizUnitWithFuncDetailBean queryBUVersionById(@Param(value = "versionId") Long versionId);
}
