package com.usercenter.server.domain.vo.resp;


import com.usercenter.server.entity.BaseUserInfo;
import lombok.Data;

import java.io.Serializable;

@Data
public class QueryByPhoneDetailResp implements Serializable {

    private Long id;

    private String userName;

    private Integer managerLevel;

    private String email;

    private String phone;
    public static QueryByPhoneDetailResp builder(BaseUserInfo user){
        if(user==null){
            return new QueryByPhoneDetailResp();
        }
        QueryByPhoneDetailResp resp = new QueryByPhoneDetailResp();
        resp.setId(user.getUserId());
        resp.setEmail(user.getEmail());
        resp.setManagerLevel(user.getManagerLevel());
        resp.setPhone(user.getPhone());
        resp.setUserName(user.getUserName());
        return resp;
    }
}
