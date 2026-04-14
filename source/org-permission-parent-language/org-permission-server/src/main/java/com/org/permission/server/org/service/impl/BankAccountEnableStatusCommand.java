package com.org.permission.server.org.service.impl;


import com.common.language.util.I18nUtils;
import com.org.permission.server.org.bean.OrgBankAccountBean;
import com.org.permission.server.org.dto.BankAccountUpdateStatusResp;
import com.org.permission.server.org.service.AbstractBankAccountDealState;
import com.common.util.util.AssertUtils;
import com.common.base.enums.StateEnum;
import org.springframework.stereotype.Service;

@Service("bankAccountEnableStatusCommand")
public class BankAccountEnableStatusCommand extends AbstractBankAccountDealState {

    /**
     * 校验状态
     * @param bean
     * @param operate
     * @return
     */
    @Override
    public BankAccountUpdateStatusResp checkStatus(OrgBankAccountBean operate, OrgBankAccountBean bean) {
        //如果是同一条，则直接跳过
        BankAccountUpdateStatusResp resp = new BankAccountUpdateStatusResp();
        if(operate.getId().equals(bean.getId())){
            resp.setFlag(true);
            resp.setMsg(super.bankAccountDealService.getOrgName(operate.getUseOrgId())+operate.getAccountSn()+ I18nUtils.getMessage("org.service.impl.bankaccount.enable.success"));
        }else{
            AssertUtils.isTrue(StateEnum.ENABLE.getCode().equals(bean.getState()),super.bankAccountDealService.getOrgName(bean.getBuId())+I18nUtils.getMessageBlank("org.service.impl.bankaccount.unenable",operate.getAccountSn()));
        }
        resp.setFlag(true);
        resp.setMsg(super.bankAccountDealService.getOrgName(operate.getUseOrgId())+operate.getAccountSn()+I18nUtils.getMessage("org.service.impl.bankaccount.enable.success"));
        return resp;
    }
}
