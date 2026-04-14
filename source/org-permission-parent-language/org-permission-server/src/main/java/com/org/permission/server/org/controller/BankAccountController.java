package com.org.permission.server.org.controller;


import com.org.permission.server.org.dto.BankAccountResp;
import com.org.permission.server.org.dto.BankAccountUpdateStatusResp;
import com.org.permission.server.org.dto.param.BankAccountAllotParam;
import com.org.permission.server.org.dto.param.BankAccountQueryReq;
import com.org.permission.server.org.enums.BankAccountStateServiceEnum;
import com.org.permission.server.org.factory.ServiceBeanFactory;
import com.org.permission.server.org.service.AbstractBankAccountDealState;
import com.common.util.message.RestMessage;
import com.org.permission.server.org.service.impl.BankAccountDealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/org/account")
@Api(tags = "0银行账户管理接口文档")
public class BankAccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountController.class);


    @Autowired
    private BankAccountDealService bankAccountDealService;

    @Resource
    private ServiceBeanFactory serviceBeanFactory;

    /**
     *
     */
    @ApiOperation(value = "添加资金账号",httpMethod = "POST")
    @PostMapping(value = "/addAccount")
    public RestMessage<Boolean> addAccount(@RequestBody BankAccountQueryReq req){
        bankAccountDealService.saveAccount(req.builderBean(),req);
        return RestMessage.doSuccess(Boolean.TRUE);
    }

    /**
     * 组织资金账号列表
     * @return
     */
    @ApiOperation(value = "获取资金列表",httpMethod = "POST")
    @PostMapping(value = "/list")
    public RestMessage<List<BankAccountResp>> getListByParams(@RequestBody BankAccountQueryReq req){
        List<BankAccountResp> list = bankAccountDealService.getList(req);
        return RestMessage.doSuccess(list);
    }




    @ApiOperation(value = "更新组织资金状态",httpMethod = "POST")
    @PostMapping(value = "/status")
    public RestMessage<BankAccountUpdateStatusResp> status(@RequestBody BankAccountQueryReq req){
        AbstractBankAccountDealState service = serviceBeanFactory.getBean(BankAccountStateServiceEnum.getEnum(req.getState()), AbstractBankAccountDealState.class);
        BankAccountUpdateStatusResp resp = service.status(req);
        return RestMessage.doSuccess(resp);
    }


    /**
     * 资金账号分配
     */
    @ApiOperation(value = "分配资金账号",httpMethod = "POST")
    @PostMapping(value = "/allot")
    public RestMessage<List<BankAccountUpdateStatusResp>> allot(@RequestBody BankAccountAllotParam param){
        List<BankAccountUpdateStatusResp> result = bankAccountDealService.allot(param);
        return RestMessage.doSuccess(result);
    }


    /**
     * 取消分配
     * @return
     */
    @ApiOperation(value = "取消资金账号分配",httpMethod = "POST")
    @PostMapping(value = "/cancel")
    public RestMessage<List<BankAccountUpdateStatusResp>> cancel(@RequestBody BankAccountAllotParam param){
        List<BankAccountUpdateStatusResp> result = bankAccountDealService.cancel(param);
        return RestMessage.doSuccess(result);
    }


    @ApiOperation(value = "分配查询",httpMethod = "POST")
    @PostMapping(value = "/allotQuery")
    public RestMessage<List<BankAccountResp>> allotQuery(@RequestBody BankAccountAllotParam param){
        List<BankAccountResp> result = bankAccountDealService.allotQuery(param);
        return RestMessage.doSuccess(result);
    }
}
