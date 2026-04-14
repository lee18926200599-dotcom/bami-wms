package com.usercenter.server.service;


import com.usercenter.server.domain.vo.req.globaluser.GlobalUserReq;
import com.usercenter.server.domain.vo.req.globaluser.UpdateGlobalReq;
import com.usercenter.server.domain.vo.resp.PageResult;
import com.usercenter.server.entity.BaseUser;

/**
 * base_userService 用户管理-全局
 */
public interface IBaseUserGlobalService{

    /**
    * 查询全局用户信息列表
    */
    PageResult<BaseUser> getGlobalUserList(GlobalUserReq globalUserReq);

    /**
    * 查询用户详情信息
    */
    BaseUser getUserDetail(Long userId);

    /**
    * 全局用户修改(起停用, 锁定解锁)
    */
    Boolean updateUserGlobal(UpdateGlobalReq updateGlobalReq);
}

