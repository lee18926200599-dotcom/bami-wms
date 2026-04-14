package com.org.permission.server.org.service.event;

import com.common.language.util.I18nUtils;
import com.org.permission.server.org.bean.FinanceEntrustRelationBean;
import com.org.permission.server.org.bean.PurchaseEntrustRelationBean;
import com.org.permission.server.org.bean.SaleEntrustRelationBean;
import com.org.permission.server.org.bean.StorageGroupEntrustRelationBean;
import com.org.permission.server.org.enums.EntrustRangeEnum;
import com.org.permission.server.org.enums.EntrustSourceEnum;
import com.org.permission.server.org.enums.EntrustTypeEnum;
import com.org.permission.server.org.service.PurchaseEntrustRelationService;
import com.org.permission.server.org.service.SaleEntrustRelationService;
import com.org.permission.server.org.service.StorageEntrustRelationService;
import com.org.permission.common.org.dto.func.*;
import com.common.base.enums.StateEnum;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 业务单元组织职能变更级联修改委托关系
 */
@Component("buFuncCascadeModifyEntrustEvent")
public class BUFuncCascadeModifyEntrustEvent {

	@Resource
	private PurchaseEntrustRelationService purchaseEntrustRelationService;

	@Resource
	private SaleEntrustRelationService saleEntrustRelationService;

	@Resource
	private StorageEntrustRelationService storageEntrustRelationService;

	@Resource
	private CascadeCreateFinanceEntrustRelEvent cascadeCreateFinanceEntrustRelEvent;

	/**
	 * 采购组织职能更新，级联修改采购委托关系，并依据状态判断是否需要新增财务委托关系
	 */
	public void purchaseOrgFuncUpdate(final PurchaseOrgFuncBean purchaseOrgFunc, Long groupId, Long updateUser) {
		// 更新采购组织职能，级联更新采购委托关系
		PurchaseEntrustRelationBean purchaseEntrustRelationBean = new PurchaseEntrustRelationBean();


		purchaseEntrustRelationBean.setEntrustType(EntrustTypeEnum.PURCHASE.getIndex());
		purchaseEntrustRelationBean.setEntrustSource(EntrustSourceEnum.BUSINESS_UNIT.getIndex());
		purchaseEntrustRelationBean.setEntrustSourceId(purchaseOrgFunc.getId());
		purchaseEntrustRelationBean.setGroupId(groupId);
		purchaseEntrustRelationBean.setModifiedDate(new Date());
		purchaseEntrustRelationBean.setModifiedBy(updateUser);
		purchaseEntrustRelationBean.setPurchaseOrgId(purchaseOrgFunc.getOrgId());
		purchaseEntrustRelationBean.setPayOrgId(purchaseOrgFunc.getPayOrgId());
		purchaseEntrustRelationBean.setSettleOrgId(purchaseOrgFunc.getSettleOrgId());
		purchaseEntrustRelationBean.setStockOrgId(purchaseOrgFunc.getStockOrgId());
		purchaseEntrustRelationBean.setState(purchaseOrgFunc.getState());

		purchaseEntrustRelationService.buFuncCascadeUpdatePurchaseEntrustRelation(purchaseEntrustRelationBean,
				StateEnum.getEnum(purchaseOrgFunc.getState()),groupId);
	}

	/**
	 * 销售组织职能更新，级联修改销售委托关系，并依据状态判断是否需要新增财务委托关系
	 */
	public void saleOrgFuncUpdate(final SaleOrgFuncBean saleOrgFunc, Long groupId, Long updateUser) {

		SaleEntrustRelationBean saleEntrustRelationBean = new SaleEntrustRelationBean();
		saleEntrustRelationBean.setEntrustType(EntrustTypeEnum.SALE.getIndex());
		saleEntrustRelationBean.setEntrustSource(EntrustSourceEnum.BUSINESS_UNIT.getIndex());
		saleEntrustRelationBean.setEntrustSourceId(saleOrgFunc.getId());
		saleEntrustRelationBean.setGroupId(groupId);
		saleEntrustRelationBean.setModifiedDate(new Date());
		saleEntrustRelationBean.setModifiedBy(updateUser);
		saleEntrustRelationBean.setReceiveOrgId(saleOrgFunc.getReceiveOrgId());
		saleEntrustRelationBean.setSettleOrgId(saleOrgFunc.getSettleOrgId());
		saleEntrustRelationBean.setStockOrgId(saleOrgFunc.getStockOrgId());
		saleEntrustRelationBean.setSaleOrgId(saleOrgFunc.getOrgId());
		saleEntrustRelationBean.setState(saleOrgFunc.getState());

		saleEntrustRelationService.buFuncCascadeUpdateSaleEntrustRelation(saleEntrustRelationBean,
				StateEnum.getEnum(saleOrgFunc.getState()),groupId);
	}

	/**
	 * 仓储组织职能更新，级联修改仓储委托关系，并依据状态判断是否需要新增财务委托关系
	 */
	public void storageOrgFuncUpdate(final StorageOrgFuncBean storageOrgFunc, Long groupId, Long updateUser) {
		// 更新仓储组织职能，级联更新内仓储委托关系
		StorageGroupEntrustRelationBean storageGroupEntrustRelationBean = new StorageGroupEntrustRelationBean();
		storageGroupEntrustRelationBean.setEntrustType(EntrustTypeEnum.STORAGE.getIndex());
		storageGroupEntrustRelationBean.setEntrustRange(EntrustRangeEnum.WITHIN_GROUP.getIndex());
		storageGroupEntrustRelationBean.setEntrustSource(EntrustSourceEnum.BUSINESS_UNIT.getIndex());
		storageGroupEntrustRelationBean.setEntrustSourceId(storageOrgFunc.getId());
		storageGroupEntrustRelationBean.setGroupId(groupId);
		storageGroupEntrustRelationBean.setModifiedDate(new Date());
		storageGroupEntrustRelationBean.setModifiedBy(updateUser);
		storageGroupEntrustRelationBean.setLogisticsOrgId(storageOrgFunc.getLogisticsOrgId());
		storageGroupEntrustRelationBean.setSettleOrgId(storageOrgFunc.getSettleOrgId());
		storageGroupEntrustRelationBean.setAccountOrgId(storageOrgFunc.getAccountOrgId());
		storageGroupEntrustRelationBean.setStockOrgId(storageOrgFunc.getOrgId());
		storageGroupEntrustRelationBean.setState(storageOrgFunc.getState());

		storageEntrustRelationService.buFuncCascadeUpdateGroupStorageEntrustRelation(storageGroupEntrustRelationBean,
				StateEnum.getEnum(storageOrgFunc.getState()),groupId);
	}

	/**
	 * 仓储组织职能更新，级联修改仓储委托关系，并依据状态判断是否需要新增财务委托关系
	 */
	public void logisticsOrgFuncUpdate(final LogisticsOrgFuncBean logisticsOrgFunc, Long groupId, Long updateUser) {
		// 更新物流组织职能，级联更新财务委托关系
		FinanceEntrustRelationBean financeEntrustRelationBean = FinanceEntrustRelationBean.buildeDefaultValue();

		financeEntrustRelationBean.setEntrustSource(EntrustSourceEnum.MANUAL.getIndex());
		financeEntrustRelationBean.setEntrustSourceId(logisticsOrgFunc.getId());

		financeEntrustRelationBean.setGroupId(groupId);

		financeEntrustRelationBean.setBizOrgId(logisticsOrgFunc.getOrgId());
		financeEntrustRelationBean.setSettleOrgId(logisticsOrgFunc.getSettleOrgId());
		financeEntrustRelationBean.setAccountOrgId(logisticsOrgFunc.getAccountOrgId());
		financeEntrustRelationBean.setLogisticFunctionType(logisticsOrgFunc.getLogisticsFunctionType());

		financeEntrustRelationBean.setState(logisticsOrgFunc.getState());
		financeEntrustRelationBean.setCreatedDate(new Date());
		financeEntrustRelationBean.setCreatedBy(updateUser);

		financeEntrustRelationBean.setRemark(I18nUtils.getMessage("org.service.event.logistics.org.finance.add"));
		cascadeCreateFinanceEntrustRelEvent.entrustCreate(financeEntrustRelationBean);
	}

	public void bankOrgFunctionUpdate(final BankingOrgFuncBean bankingOrgFuncBean, Long groupId, Long updateUser){
		// 更新物流组织职能，级联更新财务委托关系
		FinanceEntrustRelationBean financeEntrustRelationBean = FinanceEntrustRelationBean.buildeDefaultValue();

		financeEntrustRelationBean.setEntrustSource(EntrustSourceEnum.MANUAL.getIndex());
		financeEntrustRelationBean.setEntrustSourceId(bankingOrgFuncBean.getId());

		financeEntrustRelationBean.setGroupId(groupId);

		financeEntrustRelationBean.setBizOrgId(bankingOrgFuncBean.getOrgId());
		financeEntrustRelationBean.setSettleOrgId(bankingOrgFuncBean.getSettleOrgId());
		financeEntrustRelationBean.setAccountOrgId(bankingOrgFuncBean.getAccountOrgId());


		financeEntrustRelationBean.setState(bankingOrgFuncBean.getState());
		financeEntrustRelationBean.setCreatedDate(new Date());
		financeEntrustRelationBean.setCreatedBy(updateUser);

		financeEntrustRelationBean.setRemark(I18nUtils.getMessage("org.service.event.bank.org.finance.add"));
		cascadeCreateFinanceEntrustRelEvent.entrustCreate(financeEntrustRelationBean);
	}
}
