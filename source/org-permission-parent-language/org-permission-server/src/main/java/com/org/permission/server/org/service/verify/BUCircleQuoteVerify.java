package com.org.permission.server.org.service.verify;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.BizUnitWithFuncDetailBean;
import com.org.permission.server.org.dto.param.UpdateBizUnitReqParam;
import com.org.permission.server.org.mapper.BizUnitMapper;
import com.org.permission.common.org.dto.func.CorporateOrgFuncBean;
import com.org.permission.common.org.dto.func.SaleOrgFuncBean;
import com.org.permission.server.utils.NumericUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * 业务单元循环引用校验
 */
@Component("buCircleQuoteVerify")
public class BUCircleQuoteVerify {
	private static final Logger LOGGER = LoggerFactory.getLogger(BUCircleQuoteVerify.class);

	@Resource
	private BizUnitMapper bizUnitMapper;

	/**
	 * 业务单元更新操作 循环引用校验
	 *
	 * @param reqParam 更新业务单元请求参数
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Map<Long, BizUnitWithFuncDetailBean> verify(UpdateBizUnitReqParam reqParam) {
		// 更新校验上级业务 + 公司 + 销售组织是否存在循环引用
		Map<Long, BizUnitWithFuncDetailBean> relBUs = lockRelBU(reqParam);

		final Long buId = reqParam.getId();
		BizUnitWithFuncDetailBean buBean = relBUs.get(buId);
		if (buBean == null) {
			LOGGER.warn("update biz unit not exist,buId:{}.", reqParam.getId());
			throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.service.impl.bizunit.org.not.exist"));
		}
		final Long parentId = reqParam.getParentId();
		BizUnitWithFuncDetailBean parenteBUBean = relBUs.get(parentId);
		if (parenteBUBean != null) {
			if (parenteBUBean.getParentId().equals(buId)) {
				LOGGER.warn("update bu, parentId and buId circle quote,reqParam:{}.", reqParam);
				throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.org.use.parent.error"));
			}

			if (reqParam.hasCorporationFunc()) {
				final CorporateOrgFuncBean corporateOrgFunc = reqParam.getCorporate();
				final Long parentOrgId = corporateOrgFunc.getParentOrgId();
				if(!ObjectUtils.isEmpty(parentOrgId)){
					final BizUnitWithFuncDetailBean higherOrg = relBUs.get(parentOrgId);
					final CorporateOrgFuncBean higherCorporateOrgFunc = higherOrg.getCorporate();
					if (higherCorporateOrgFunc != null) {
						final Long originHigherOrg = higherCorporateOrgFunc.getParentOrgId();
						if (buId.equals(originHigherOrg)) {
							LOGGER.warn("update bu, corporate org func  parentOrgId and buId circle quote,reqParam:{}.", reqParam);
							throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.org.use.parent.error"));
						}
					}
				}
			}

			if (reqParam.hasSaleFunc()) {
				final SaleOrgFuncBean saleOrgFunc = reqParam.getSale();
				final Long parentSaleOrgId = saleOrgFunc.getParentSaleOrgId();
				if(!ObjectUtils.isEmpty(parentSaleOrgId)){
					final BizUnitWithFuncDetailBean parentSaleOrg = relBUs.get(parentSaleOrgId);
					if (parentSaleOrg != null) {
						final SaleOrgFuncBean parentSaleOrgFunc = parentSaleOrg.getSale();
						if (parentSaleOrgFunc != null) {
							final Long originParentSaleOrgId = parentSaleOrgFunc.getParentSaleOrgId();
							if (buId.equals(originParentSaleOrgId)) {
								LOGGER.warn("update bu, sale org func higherSaleOrg and buId circle quote,reqParam:{}.", reqParam);
								throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.verify.org.use.parent.sale.error"));
							}
						}
					}
				}
			}
		}
		return relBUs;
	}

	/**
	 * 依次获取相关业务单元锁
	 *
	 * @param reqParam 更新请求参数
	 * @return 关联业务单元ID Map
	 */
	private Map<Long, BizUnitWithFuncDetailBean> lockRelBU(UpdateBizUnitReqParam reqParam) {
		final Long buId = reqParam.getId();
		final Long parentId = reqParam.getParentId();
		// 补充业务逻辑，校验上下业务单元是否有循环引用问题
		TreeSet<Long> sortedBUIds = new TreeSet<>();
		if (reqParam.hasSaleFunc()) {
			final SaleOrgFuncBean saleOrgFunc = reqParam.getSale();
			final Long parentSaleOrgId = saleOrgFunc.getParentSaleOrgId();
			if (NumericUtil.greterThanZero(parentSaleOrgId)) {
				sortedBUIds.add(parentSaleOrgId);
			}
		}
		if (reqParam.hasCorporationFunc()) {
			final CorporateOrgFuncBean corporateOrgFunc = reqParam.getCorporate();
			final Long parentOrgId = corporateOrgFunc.getParentOrgId();
			if (NumericUtil.greterThanZero(parentOrgId)) {
				sortedBUIds.add(parentOrgId);
			}
		}

		if (NumericUtil.greterThanZero(parentId)) {
			if (buId.equals(parentId)) {
				LOGGER.warn("update bu, parentId equals buId,reqParam:{}.", reqParam);
				throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, "公司与上级公司重复");
			}
			if (NumericUtil.greterThanZero(parentId)) {
				sortedBUIds.add(parentId);
			}
		}
		sortedBUIds.add(buId);

		Map<Long, BizUnitWithFuncDetailBean> relBUs = new HashMap<>(sortedBUIds.size());
		if (sortedBUIds.size() > 0) {// 循环获取各业务单元的锁
			for (Long sortedBUId : sortedBUIds) {
				relBUs.put(sortedBUId, bizUnitMapper.queryBizUnitByIdLock(sortedBUId));
			}
		}
		return relBUs;
	}
}
