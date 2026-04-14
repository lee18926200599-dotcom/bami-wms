package com.org.permission.server.org.service.verify;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.OrgBean;
import com.common.base.enums.BooleanEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 删除业务单元验证
 */
@Component("deleteBizUnitVerify")
public class DeleteBizUnitVerify {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeleteBizUnitVerify.class);

	public void verify(final OrgBean orgBean) {
		if (orgBean == null) {
			LOGGER.warn("biz unit not exit.");
			throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.service.impl.bizunit.org.not.exist"));
		}

		final Long buId = orgBean.getId();
		if (orgBean.getMainOrgFlag() == BooleanEnum.TRUE.getCode()) {
			LOGGER.warn("main biz unit can not delete,bizUnitId:{}.", buId);
			throw new OrgException(OrgErrorCode.MAIN_BIZ_UNIT_CAN_NOT_DELETE_ERROR_CODE, I18nUtils.getMessage("org.service.verify.root.org.cannot.delete"));
		}
		//TODO 判断删除状态
		if (BooleanEnum.TRUE.getCode() == orgBean.getState()) {
			LOGGER.warn("repetition delete biz unit,bizUnitId:{}.", buId);
			return;
		}

		final Integer init = orgBean.getInitFlag();
		if (init != null && BooleanEnum.TRUE.getCode() == init) {
			LOGGER.warn("enabled biz unit can not delete,bizUnitId:{}.", buId);
			throw new OrgException(OrgErrorCode.ENABLED_STATE_CAN_NOT_DELETE_ERROR_CODE, I18nUtils.getMessage("org.service.verify.init.org.cannot.delete"));
		}
	}
}
