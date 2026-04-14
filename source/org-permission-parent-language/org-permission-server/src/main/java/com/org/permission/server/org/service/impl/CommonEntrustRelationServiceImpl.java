package com.org.permission.server.org.service.impl;

import com.common.language.util.I18nUtils;
import com.org.permission.common.org.dto.EntrustRelationOrgInfoDto;
import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.common.org.param.KeyOperateParam;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.StateBean;
import com.org.permission.server.org.enums.EntrustSourceEnum;
import com.org.permission.server.org.mapper.CommonEntrustRelationMapper;
import com.org.permission.server.org.service.CommonEntrustRelationService;
import com.org.permission.server.org.service.verify.EntrustRelationVerify;
import com.common.base.enums.BooleanEnum;
import com.common.base.enums.StateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 业务委托关系逻辑处理
 */
@Service(value = "commonEntrustRelationService")
public class CommonEntrustRelationServiceImpl implements CommonEntrustRelationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonEntrustRelationServiceImpl.class);

	@Resource
	private EntrustRelationVerify entrustRelationVerify;

	@Resource
	private CommonEntrustRelationMapper commonEntrustRelationMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void deleteEntrustRelation(final KeyOperateParam reqParam) {
		final StateBean stateBean = commonEntrustRelationMapper
				.queryEntrustRelationStateByIdLock(reqParam.getId());
		if (Objects.isNull(stateBean)) {
			throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.common.data.not.exist"));
		}

		if (Objects.equals(stateBean.getDeletedFlag(), BooleanEnum.TRUE.getCode())) {
			return;
		}

		if (Objects.equals(stateBean.getState(), StateEnum.ENABLE.getCode())) {
			throw new OrgException(OrgErrorCode.STATE_CANNOT_OP_ERROR_CODE, I18nUtils.getMessage("org.service.impl.entrust.relation.enable.cannot.delete"));
		}

		if (Objects.equals(stateBean.getSource(), EntrustSourceEnum.BUSINESS_UNIT.getCode())) {
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.impl.entrust.relation.org.create.cannot.delete"));
		}

		if (Objects.equals(stateBean.getDefaultFlag(), BooleanEnum.TRUE.getCode())) {
			throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.impl.entrust.relation.default.cannot.delete"));
		}

		commonEntrustRelationMapper.deleteEntrustRelation(reqParam.getId());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void enableEntrustRelation(final EnableOperateParam reqParam) {
		commonEntrustRelationMapper.enableEntrustRelation(reqParam,new Date());
	}

	public Long queryEntrustRelationWithCondition(Integer entrustRange, Integer entrustType, Long wareHourseId,
													 Long purchaseSaleOrg, Long stockOrg,
													 Long logisticsOrg, Long settmentOrg,
													 Long accountOrg, Long businessOrg){
        return commonEntrustRelationMapper.queryEntrustRelationWithCondition(entrustRange, entrustType, wareHourseId,
                purchaseSaleOrg, stockOrg, logisticsOrg, settmentOrg, accountOrg,businessOrg);
    }

	@Override
	public List<EntrustRelationOrgInfoDto> queryPurchaseEntrustRelation(Long purchaseSaleOrgId) {
		return commonEntrustRelationMapper.queryPurchaseEntrustRelation(purchaseSaleOrgId);
	}
}

