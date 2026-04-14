package com.org.permission.server.org.service.event;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.GroupInfoBean;
import com.org.permission.server.org.mapper.GroupMapper;
import com.common.base.enums.StateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 组织操作集团状态校验
 */
@Component("orgOpGroupStatusVerify")
public class OrgOpGroupStatusVerify {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrgOpGroupStatusVerify.class);
	@Resource
	private GroupMapper groupMapper;

	public void verify(Long groupId) {
		final GroupInfoBean groupInfoBean = groupMapper.queryGroupByIdLock(groupId);
		if (groupInfoBean == null) {
			LOGGER.warn("The group not exist,groupId:{}.", groupId);
			throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.service.event.group.not.exist"));
		}

		if (groupInfoBean.getState() != StateEnum.ENABLE.getCode()) {
			throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.service.event.group.unenable"));
		}
	}
}
