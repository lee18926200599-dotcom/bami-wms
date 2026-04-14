package com.org.permission.server.org.service.event;

import com.org.permission.server.org.bean.FinanceEntrustRelationBean;
import com.org.permission.server.org.mapper.FinanceEntrustRelationMapper;
import com.common.base.enums.StateEnum;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 级联创建财务委托关系事件(主要实现去重逻辑)
 */
@Component("cascadeCreateFinanceEntrustRelEvent")
public class CascadeCreateFinanceEntrustRelEvent {

	@Resource
	private FinanceEntrustRelationMapper financeEntrustRelationMapper;

	/**
	 * 委托关系启用时创建财务委托关系
	 *
	 * @param newEntrustRel 新财务委托关系
	 */
	public void entrustCreate(FinanceEntrustRelationBean newEntrustRel) {
		if (Objects.isNull(newEntrustRel)) {
			return;
		}

		if (newEntrustRel.getState() != StateEnum.ENABLE.getCode()) {
			return;
		}

		addIfNotExist(newEntrustRel);
	}

	/**
	 * 委托关系启用时创建财务委托关系
	 *
	 * @param newEntrustRel 新财务委托关系
	 */
	public void addIfNotExist(FinanceEntrustRelationBean newEntrustRel) {
		Long oldEntrustRelId = financeEntrustRelationMapper.queryFinanceEntrustRelByBean(newEntrustRel);
		if (ObjectUtils.isEmpty(oldEntrustRelId)) {
			financeEntrustRelationMapper.addFinanceEntrustRelation(newEntrustRel);
		}else{
			newEntrustRel.setId(oldEntrustRelId);
			financeEntrustRelationMapper.updateFinanceEntrustRelation(newEntrustRel);
		}
	}
}
