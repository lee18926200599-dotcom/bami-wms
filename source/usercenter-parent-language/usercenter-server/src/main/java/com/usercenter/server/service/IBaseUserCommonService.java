package com.usercenter.server.service;

import com.usercenter.server.domain.dto.UpdateUserDTO;
import com.usercenter.server.domain.dto.UserNamesDto;
import com.usercenter.common.dto.FplUser;
import com.usercenter.common.dto.UserSaveDto;
import com.usercenter.common.dto.request.BatchUpdateReq;
import com.usercenter.server.service.command.response.UpdateUserCommandResp;
import com.usercenter.common.enums.BusinessSystemEnum;
import com.usercenter.common.enums.SourceTypeEnum;
import com.usercenter.common.enums.UserStateEnum;
import com.usercenter.server.entity.BaseUser;
import com.usercenter.server.entity.BaseUserDetail;
import com.usercenter.server.entity.BaseUserInfo;
import com.usercenter.server.entity.BaseUserPasswordHistory;

import java.util.List;

/**
 * base_userService 用户抽象服务
 */
public interface IBaseUserCommonService {

    /**
     *  根据id查询用户信息
     * @param id 用户id
     * @return 用户信息
     */
    FplUser getUserById(Long id);

    /**
     * 查询一条用户主表信息
     * @param baseUser
     * @return
     */
    BaseUser getOnePrimary(BaseUser baseUser);

    /**
     * 保存用户
     * @param saveDTO
     * @return
     */
    Integer save(UserSaveDto saveDTO);

    /**
     * 根据id查询主表信息
     * @param id 主表id
     * @return
     */
    BaseUser getPrimaryById(Long id);

    /**
     * 查询用户主表列表
     * @param baseUser
     * @return 用户列表
     */
    List<BaseUser> getPrimaryList(BaseUser baseUser);


    /**
     * 根据条件查询一条细表信息
     * @param detail
     * @return
     */
    BaseUserDetail getOneDetail(BaseUserDetail detail);


    /**
     * 根据id查询细表信息
     * @param id
     * @return
     */
    BaseUserDetail getDetailById(Long id);

    /**
     * 更新用户信息
     * @param baseUser 用户信息
     */
    void updateUser(BaseUser baseUser);


    /**
     * 更新字表信息
     * @param baseUserDetail
     */
    void updateUserDetail(BaseUserDetail baseUserDetail);


    /**
     * 更新策略
     * @param req 更新参数
     * @return
     */
    UpdateUserCommandResp batchUpdate(BatchUpdateReq req);


    /**
     * 数据校验
     * @param user
     */
    void checkUser(UserSaveDto user);


    /**
     * 更新校验
     * @param updateUserDTO 更新的参数
     */
    void updateCheck(UpdateUserDTO updateUserDTO);

    /**
     * 历史密码保存
     * @param userPasswordHistory
     */
    void insertUserPasswordHistory(BaseUserPasswordHistory userPasswordHistory);


    /**
     * 根据登录名查找用户
     * @param loginName
     * @return
     */
    BaseUser getUserByLoginName(String loginName);


    /**
     * 根据认证信息获取用户信息，如果对应多个用户信息，取最小一条
     * @param loginName      登录账号
     * @param businessSystem 登陆系统
     * @param groupId        集团id
     * @return 获取用户信息
     */
    FplUser getUserByLoginInfo(String loginName, BusinessSystemEnum businessSystem, SourceTypeEnum sourceTypeEnum, Long groupId);



    /**
     * 根据认证信息获取用户信息，如果对应多个用户信息，取最小一条
     * @param userId         用户id
     * @param businessSystem 登陆系统
     * @param groupId        集团id
     * @return 获取用户信息
     */
    FplUser getUserByLoginInfo(Long userId, BusinessSystemEnum businessSystem, Long groupId);


    /**
     * 根据用户名获取用户列表
     * @param  userName 用户名
     * @param  userStateEnum 是否启用
     * @param  lock 锁定状态
     * @return 用户
     */
    List<BaseUserInfo> getUserListByUserName(String userName, UserStateEnum userStateEnum, Integer lock);

    /**
     * 根据邮箱获取用户列表
     * @param  email 邮箱
     * @param  userStateEnum 是否启用
     * @param  lock 锁定状态
     * @return 用户
     */
    List<BaseUserInfo> getUserListByEmail(String email, UserStateEnum userStateEnum, Integer lock);

    /**
     * 根据手机号获取用户列表
     * @param  phone 手机号
     * @param  userStateEnum 是否启用
     * @param  lock 锁定状态
     * @return 用户
     */
    List<BaseUserInfo> getUserListByPhone(String phone, UserStateEnum userStateEnum, Integer lock);

    /**
     * 根据用户名获取用户
     * @param  userName 用户名
     * @param  userStateEnum 是否启用
     * @param  lock 锁定状态
     * @return 用户
     */
    BaseUserInfo getUserByUserName(String userName, UserStateEnum userStateEnum, Integer lock);

    /**
     *  根据邮箱获取用户
     * @param  email 邮箱
     * @param  userStateEnum 是否启用
     * @param  lock 锁定状态
     * @return 用户
     */
    BaseUserInfo getUserByEmail(String email,UserStateEnum userStateEnum, Integer lock);

    /**
     * 根据手机号获取用户
     * @param  phone 手机号
     * @param  userStateEnum 是否启用
     * @param  lock 锁定状态
     * @return 用户
     */
    BaseUserInfo getUserByPhone(String phone, UserStateEnum userStateEnum, Integer lock);

    /**
     * 是否是历史密码
     * @param userId
     * @param password
     * @return
     */
    boolean isHistoryPassword(Long userId, String password);

    /**
     * 根据用户名获取用户信息【获取不到详情】
     * @param userName
     * @return
     */
    List<BaseUserInfo> getListByUserNames(List<String> userName);

    /**
     * 根据用户名获取用户信息【获取不到详情】
     * @param userNamesDto
     * @return
     */
    List<BaseUserInfo> getListByUserNames(UserNamesDto userNamesDto);

}

