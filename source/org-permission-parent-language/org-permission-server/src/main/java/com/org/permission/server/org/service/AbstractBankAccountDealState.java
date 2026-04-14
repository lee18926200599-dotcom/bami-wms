package com.org.permission.server.org.service;

import com.org.permission.server.org.bean.OrgBankAccountBean;
import com.org.permission.server.org.dto.BankAccountUpdateStatusResp;
import com.org.permission.server.org.dto.param.BankAccountQueryReq;
import com.org.permission.server.org.service.impl.BankAccountDealService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractBankAccountDealState {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBankAccountDealState.class);

    @Autowired
    protected BankAccountService bankAccountService;

    @Autowired
    protected OrganizationService organizationService;

    @Autowired
    protected BankAccountDealService bankAccountDealService;

    public BankAccountUpdateStatusResp status(BankAccountQueryReq req){
        OrgBankAccountBean operateOrgBank = bankAccountService.getOne(req.getId());
        if(operateOrgBank==null){
            return null;
        }
        OrgBankAccountBean params = new OrgBankAccountBean();
        params.setUseOrgId(operateOrgBank.getBuId());
        params.setBuId(operateOrgBank.getBuId());
        params.setAccountId(operateOrgBank.getAccountId());
        List<OrgBankAccountBean> orgBankAccountBeans = bankAccountService.getList(params);
        if(orgBankAccountBeans==null||orgBankAccountBeans.size()!=1){
            LOGGER.error("数据有误id:{},buid:{}",req.getId(),operateOrgBank.getBuId());
            return null;
        }
        OrgBankAccountBean standard  = orgBankAccountBeans.get(0);
        //1.校验状态是否合法
        BankAccountUpdateStatusResp resp = checkStatus(operateOrgBank,standard);
        if(resp.isFlag()) {
            operateOrgBank.setState(req.getState());
            updateStatus(operateOrgBank);
        }
        return resp;
    }


    /**
     * 执行前校验
     * @param bean
     * @param operate
     * @return
     */
    protected abstract BankAccountUpdateStatusResp checkStatus(OrgBankAccountBean operate, OrgBankAccountBean bean);

    /**
     *
     * 每个状态都需要单独处理【包括，启用，禁用，删除】
     * @param operateOrgBank 当前操作
     * @return
     */
    private void updateStatus(OrgBankAccountBean operateOrgBank){
        this.bankAccountService.updateStatusByIds(Arrays.asList(operateOrgBank.getId()),operateOrgBank.getState());
    }

}
