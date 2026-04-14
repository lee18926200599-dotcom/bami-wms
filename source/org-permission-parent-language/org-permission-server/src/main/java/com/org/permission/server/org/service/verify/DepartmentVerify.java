package com.org.permission.server.org.service.verify;

import com.common.language.util.I18nUtils;
import com.org.permission.common.org.param.DepartmentReqParam;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.OrgBean;
import com.org.permission.server.org.mapper.BizUnitMapper;
import com.org.permission.server.org.mapper.DepartmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 部门验证器
 */
@Component(value = "departmentVerify")
public class DepartmentVerify {
	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentVerify.class);
	@Resource
	private BizUnitMapper bizUnitMapper;

	@Resource
	private DepartmentMapper departmentMapper;

	public void verify(final DepartmentReqParam reqParam) {

		final Long parentId = reqParam.getParentId();
		final Long parentBUId = reqParam.getParentBUId();
		final OrgBean bizUnit = bizUnitMapper.getBizUnitStateByIdLock(parentBUId);
		if (null == bizUnit) {
			throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.service.impl.bizunit.org.not.exist"));
		}

		final List<String> depNames = departmentMapper.queryDepNamesByBUIdLock(parentBUId);
		if (CollectionUtils.isEmpty(depNames)) {
			return;
		}
		final String newDepName = reqParam.getDepName();
		if (depNames.contains(newDepName)) {
			throw new OrgException(OrgErrorCode.DEP_NAME_CONFLICT_ERROR_CODE, I18nUtils.getMessage("org.service.impl.department.sameorg.name.exist"));
		}
	}
}
