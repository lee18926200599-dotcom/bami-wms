package com.usercenter.server.domain.vo.resp;

import com.usercenter.server.domain.vo.req.administratorsuser.ManagerGroupDetailReq;
import com.usercenter.server.entity.BaseUser;

import java.util.List;

/**
 * 用户详情信息
 */
public class GlobalUserResp {

    private BaseUser baseUser;

    private List<ManagerGroupDetailReq> baseUserDetailList;

    public BaseUser getBaseUser() {
        return baseUser;
    }

    public GlobalUserResp setBaseUser(BaseUser baseUser) {
        this.baseUser = baseUser;
        return this;
    }

    public List<ManagerGroupDetailReq> getBaseUserDetailList() {
        return baseUserDetailList;
    }

    public GlobalUserResp setBaseUserDetailList(List<ManagerGroupDetailReq> baseUserDetailList) {
        this.baseUserDetailList = baseUserDetailList;
        return this;
    }
}
