package com.usercenter.server.service;

import com.usercenter.common.dto.FplUser;
import com.usercenter.common.dto.UserSaveDto;
import com.usercenter.common.dto.request.LoginRequest;

/**
 * 用户校验服务
 */
public interface IBaseUserCheckService {

    /**
     * 主表信息校验
     * @param userName 用户名
     * @param email 邮箱
     * @param phone 手机号
     */
     void checkPrimaryUserInfo(String userName, String email, String phone);

    /**
     *  校验用户主表信息
     * @param userName 用户名
     * @param email 邮箱
     * @param phone 手机号
     * @param userAuthId 主表id
     * @return
     */
    void checkPrimaryUserInfo(String userName, String email, String phone, Long userAuthId);

    /**
     * 注册来源校验
     * @param source
     */
     void checkSource(String source);

    /**
     * 唯一性校验
     * @param saveDTO 用户信息
     * @param isUpdate 是否为更新
     */
     void uniqueCheck(UserSaveDto saveDTO, Boolean isUpdate);

    /**
     * 登陆校验
     * @param loginRequest
     */
     void loginCheck(LoginRequest loginRequest, FplUser fplUser);
}

