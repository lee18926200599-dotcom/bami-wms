package com.org.permission.server.org.service.impl;

import com.common.language.util.I18nUtils;
import com.org.permission.server.org.bean.OrgBankAccountBean;
import com.org.permission.server.org.dto.BankAccountUpdateStatusResp;
import com.org.permission.server.org.service.AbstractBankAccountDealState;
import com.common.util.util.AssertUtils;
import com.common.base.enums.StateEnum;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 禁用逻辑
 */
@Service("bankAccountUnableStatusCommand")
public class BankAccountUnableStatusCommand extends AbstractBankAccountDealState {


    /**
     * 校验操作
     * @param bean
     * @param operate
     * @return
     */
    @Override
    public BankAccountUpdateStatusResp checkStatus(OrgBankAccountBean operate, OrgBankAccountBean bean) {
        //禁用逻辑 1.如果禁用的是标准的，则需要判断其余的是否为禁用状态
        BankAccountUpdateStatusResp resp = new BankAccountUpdateStatusResp();
        if(operate.getId().equals(bean.getId())){
            //查找该资金账号的其余数据是否为禁用状态
            OrgBankAccountBean params = new OrgBankAccountBean();
            params.setAccountId(bean.getAccountId());
            List<OrgBankAccountBean> orgBankList = super.bankAccountService.getList(params);
            //查找启用状态
            boolean flag = false;
            Long orgId = null;
            for (OrgBankAccountBean orgBank:orgBankList) {
                //不做自身的查找
                if(operate.getId().equals(orgBank.getId())){
                    continue;
                }
                if(StateEnum.ENABLE.getCode().equals(orgBank.getState())){
                    flag=true;
                    orgId = orgBank.getUseOrgId();
                    break;
                }
            }
            //如果有启用状态的数据，则进行提示
            AssertUtils.isTrue(!flag,operate.getAccountSn()+ I18nUtils.getMessageBlank("org.service.impl.bankaccount.org.used",super.bankAccountDealService.getOrgName(orgId)));
        }
        resp.setFlag(true);
        resp.setMsg(super.bankAccountDealService.getOrgName(operate.getUseOrgId())+operate.getAccountSn()+I18nUtils.getMessage("org.service.impl.bankaccount.disable.success"));
        return resp;
    }



}
