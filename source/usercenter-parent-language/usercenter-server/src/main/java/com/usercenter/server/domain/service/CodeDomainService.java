package com.usercenter.server.domain.service;

import com.common.framework.number.BaseNoGenerateUtil;
import org.springframework.stereotype.Component;

@Component(value = "codeDomainService")
public class CodeDomainService {
    public String getUserCode(){
        return "UC"+ BaseNoGenerateUtil.generateCodeBase("4PL_USER_","BASE","USER_CODE",3);
    }
    public String getUserDetailCode(){
        return "D_UC"+ BaseNoGenerateUtil.generateCodeBase("4PL_USER_","DETAIL","USER_CODE",3);
    }
}
