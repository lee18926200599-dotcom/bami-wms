package com.org.permission.server.org.service.verify;

import com.org.permission.common.org.param.AddStaffParam;
import org.springframework.stereotype.Component;

/**
 * 新增人员验证器

 */
@Component(value = "addStaffVerify")
public class AddStaffVerify {

	public void verify(final AddStaffParam reqParam) {

		//throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, "人员不存在");
		// TODO 查看PRD 人员唯一性校验
	}
}
