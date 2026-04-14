package com.usercenter.server.service;

import com.github.pagehelper.PageInfo;
import com.org.permission.common.permission.dto.GetUserMenuPermissionsDto;
import com.org.permission.common.permission.dto.UserAllPermissionDto;
import com.usercenter.common.dto.*;
import com.usercenter.common.dto.request.GetLoginInfoListRequest;
import com.usercenter.common.dto.request.GetMenusByPhoneReq;
import com.usercenter.common.dto.request.LoginRequest;
import com.usercenter.common.dto.request.UserSaveRespDTO;
import com.usercenter.common.enums.BusinessSystemEnum;
import com.usercenter.common.enums.SourceTypeEnum;
import com.usercenter.common.enums.ValidateTypeEnum;
import com.usercenter.server.entity.BaseUser;
import com.usercenter.server.entity.BaseUserInfo;
import com.usercenter.server.entity.BaseUserPasswordHistory;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * base_userService 用户注册
 */
public interface IBaseUserApiService extends IBaseUserCommonService {

    /**
     * 注册一个用户
     *
     * @param saveDTO 保存参数
     * @return 保存后的结果
     */
    UserSaveRespDTO register(UserSaveDto saveDTO);

    /**
     * 获取认证信息（包括权限信息）
     *
     * @param id 认证信息id
     * @return 认证信息（包括权限信息）
     */
    UserAuthDto getUserAuthInfoById(Long id);

    /**
     * 获取用户信息list
     *
     * @param ids 用户id集合
     * @return 用户信息集合
     */
    List<FplUser> getUserInfoList(Set<Long> ids);

    /**
     * 获取用户信息map
     *
     * @param ids 用户id集合
     * @return 用户信息map
     */
    Map<Long, FplUser> getUserInfoMap(Set<Long> ids);


    /**
     * 更新指定用户信息
     *
     * @param updateDTO 更新信息
     * @return 更新后的用户信息
     */
    void updateUser(FplUser updateDTO);

    /**
     * 根据token获取用户菜单权限
     *
     * @param token token信息
     * @return 用户菜单信息
     */
    UserAllPermissionDto getMenuPermissonsByToken(String token);

    /**
     * 登陆逻辑
     *
     * @param loginRequest 登陆参数
     * @return
     */
    FplUser login(LoginRequest loginRequest);

    /**
     * 获取用户下的登陆信息
     *
     * @param request
     * @return List<LoginInfoDTO>
     **/
    List<LoginInfoDto> getLoginInfoList(GetLoginInfoListRequest request);

    /**
     * 登出指定的用户
     *
     * @param logoutUserReq
     * @return List<LoginInfoDTO>
     **/
    void logoutUser(LogoutUserReq logoutUserReq);

    /**
     * 登出token
     *
     * @param token 登出
     */
    void logout(String token);

    /**
     * 根据token和集团获取用户信息
     *
     * @param token   token
     * @param groupId 集团id
     * @return 用户信息
     */
    FplUser getUserByToken(String token, Long groupId);

    /**
     * 根据用户id获取token和用户信息
     */
    FplUser getTokenByUserId(Long userId, BusinessSystemEnum businessSystem, SourceTypeEnum sourceType);

    /**
     * token验证
     *
     * @param userId 用户id
     * @param token  token
     */
    void isTokenValid(Long userId, String token);

    /**
     * 获取修改密码验证码
     *
     * @param phone        手机
     * @param email        邮箱
     * @param validateType 验证类型
     * @return 验证码
     */
    String getPasswordCode(String phone, String email, ValidateTypeEnum validateType);

    /**
     * 查询用户分页
     *
     * @param userDto  查询条件
     * @param pageNum  指定页数
     * @param pageSize 每页大小
     * @return 用户分页
     */
    PageInfo findUserListPage(UserDto userDto, Integer pageNum, Integer pageSize);

    /**
     * Description: 查询用户列表
     *
     * @return 用户列表
     */
    List<FplUser> findUserList(UserDto userDto);

    /**
     * 获取注册来源（数据字典）
     */
    List registerSource();

    /**
     * 修改用户名和密码【保存历史密码】
     *
     * @param user
     * @param userPasswordHistory
     */
    void updateUserNameAndPassword(BaseUser user, BaseUserPasswordHistory userPasswordHistory);

    /**
     * 用户模糊查询（编码，用户名，真实名称，手机号）
     *
     * @param condition 模糊查询的key
     * @param orgId     组织id
     * @return
     */
    List<FplUser> findUserList(String condition, Long orgId);

    /**
     * 根据用户id查询同主表所有子账号
     *
     * @param userId
     * @return 同主表所有子账号
     */
    List<FplUser> getDetailListByUserId(Long userId);


    /**
     * 根据业务系统和注册来源查询手机号是否已经注册
     *
     * @param phone  手机号
     * @param source 注册来源
     * @return 用户信息
     */
    BaseUserInfo getUserByPhone(String phone, Integer source);

    List<GetUserMenuPermissionsDto> getMenusByPhone(GetMenusByPhoneReq req);

}

