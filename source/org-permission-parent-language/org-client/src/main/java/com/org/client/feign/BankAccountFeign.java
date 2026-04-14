package com.org.client.feign;


import com.common.util.message.RestMessage;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.param.BankAccountQueryParam;
import com.org.permission.common.org.vo.BankAccountVo;
import com.org.permission.common.util.Constant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = Constant.ORG_SERVER_NAME)
public interface BankAccountFeign {
    @PostMapping(value = "/bankAccount/list")
    RestMessage<PageInfo<BankAccountVo>> list(@RequestBody BankAccountQueryParam reqParam);
}
