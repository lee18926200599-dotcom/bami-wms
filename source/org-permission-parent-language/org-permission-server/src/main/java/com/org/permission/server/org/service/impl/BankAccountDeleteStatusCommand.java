package com.org.permission.server.org.service.impl;

import com.common.base.enums.StateEnum;
import com.common.language.util.I18nUtils;
import com.common.util.util.AssertUtils;
import com.org.permission.server.org.bean.OrgBankAccountBean;
import com.org.permission.server.org.dto.BankAccountUpdateStatusResp;
import com.org.permission.server.org.service.AbstractBankAccountDealState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 删除操作
 */
@Service("bankAccountDeleteStatusCommand")
public class BankAccountDeleteStatusCommand extends AbstractBankAccountDealState {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountDeleteStatusCommand.class);



    /**
     * 删除状态校验
     * @param bean
     * @param operate
     * @return
     */
    @Override
    public BankAccountUpdateStatusResp checkStatus(OrgBankAccountBean operate, OrgBankAccountBean bean) {
        //未启用状态可以删除
        BankAccountUpdateStatusResp resp = new BankAccountUpdateStatusResp();
        AssertUtils.isTrue(StateEnum.CREATE.getCode().equals(operate.getState()), I18nUtils.getMessage("org.service.impl.bankaccount.used"));
        resp.setFlag(true);
        return resp;
    }
}
