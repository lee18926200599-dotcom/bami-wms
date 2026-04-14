package com.usercenter.client.feign;


import com.common.util.message.RestMessage;
import com.github.pagehelper.PageInfo;
import com.usercenter.client.dto.UserNamesDto;
import com.usercenter.common.dto.*;
import com.usercenter.common.dto.request.*;
import com.usercenter.common.enums.BusinessSystemEnum;
import com.usercenter.common.enums.SourceTypeEnum;
import com.usercenter.common.enums.ValidateTypeEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

@FeignClient(value = "zzz-usercenter-server")
public interface UserFeign {

    /**
     * 根据id集合获取用户map
     */
    @PostMapping(value = "/user/getUserInfoMap")
    RestMessage<Map<Long, FplUser>> getUserInfoMap(@RequestBody Set<Long> ids);

    /**
     * 获取用户信息
     *
     * @return
     */
    @PostMapping(value = "/user/getUserInfoList")
    RestMessage<List<FplUser>> getUserInfoList(@RequestBody UserInfoListDto userInfoDto);

    /**
     * 根据id合获取用户信息
     */
    @PostMapping(value = "/user/getUserInfo")
    RestMessage<FplUser> getUserInfo(@RequestParam(value = "userId") Long userId);

    /**
     * 用户注册
     */
    @PostMapping(value = "/user/register")
    RestMessage<FplUser> register(@RequestBody UserSaveDto userSaveDto);

    /**
     * 用户修改
     *
     * @param fplUser
     * @return
     */
    @PostMapping("/user/update")
    RestMessage<FplUser> update(@RequestBody FplUser fplUser);


    @PostMapping("/user/login")
    RestMessage<FplUser> login(@RequestBody LoginRequest loginRequest);


    @PostMapping("/user/getLoginInfoList")
    RestMessage<List<LoginInfoDto>> getLoginInfoList(@RequestBody GetLoginInfoListRequest request);

    @PostMapping("/user/logoutUser")
    RestMessage logoutUser(@RequestBody LogoutUserReq request);

    /**
     * 用户状态
     *
     * @param userId
     * @return
     */
    @PostMapping("/user/status")
    RestMessage<FplUser> status(@RequestParam(value = "userId", required = true) Integer userId);

    /**
     * 用户修改密码
     *
     * @param loginName
     * @param oldPassword
     * @param newPassword
     * @param code
     * @return
     */
    @PostMapping("/user/updatePassword")
    RestMessage<String> updatePassword(@RequestParam(value = "loginName", required = true) String loginName, @RequestParam(value = "oldPassword", required = false) String oldPassword, @RequestParam(value = "newPassword", required = true) String newPassword, @RequestParam(value = "code", required = false) String code);

    /**
     * 修改用户名跟密码
     *
     * @param token
     * @param userName
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @PostMapping("/user/updateUserNameAndPassword")
    RestMessage<String> updateUserNameAndPassword(@RequestParam(value = "token", required = true) String token, @RequestParam(value = "userName", required = true) String userName, @RequestParam(value = "oldPassword", required = false) String oldPassword, @RequestParam(value = "newPassword", required = true) String newPassword);

    /**
     * 是否历史密码
     *
     * @param userId
     * @param password
     * @return
     */
    @PostMapping("/user/isHistoryPassword")
    RestMessage<Boolean> isHistoryPassword(@RequestParam(value = "userId", required = true) Integer userId, @RequestParam(value = "password", required = true) String password);

    /**
     * 用户重置密码
     *
     * @param userId
     * @return
     */
    @PostMapping("/user/resetPassword")
    RestMessage<String> resetPassword(@RequestParam(value = "userId", required = true) Long userId);

    /**
     * 用户列表
     *
     * @param userDto
     * @return
     */
    @PostMapping("/user/findUserList")
    RestMessage<List<FplUser>> findUserList(@RequestBody UserDto userDto);

    /**
     * 获取修改密码验证码
     *
     * @param phone        手机号
     * @param email        邮箱
     * @param validateType 验证类型
     * @return
     */
    @PostMapping("/user/getPasswordCode")
    RestMessage<String> getPasswordCode(@RequestParam(value = "phone", required = false) String phone, @RequestParam(value = "email", required = false) String email, @RequestParam(value = "validateType", required = true) ValidateTypeEnum validateType);

    /**
     * 验证token是否有效
     *
     * @param userId
     * @param token
     * @return
     */
    @PostMapping("/user/isTokenValid")
    RestMessage<String> isTokenValid(@RequestParam(value = "userId", required = true) Long userId, @RequestParam(value = "token", required = true) String token);

    /**
     * 根据用户token获取用户信息，支持切换集团
     *
     * @param param token   用户token
     *              groupId 所属集团
     * @return
     */
    @PostMapping(value = "rpc/user/getUserByToken")
    RestMessage<FplUser> getUserByToken(@RequestBody UserTokenParam param);

    /**
     * 根据token获取菜单权限
     *
     * @param token
     * @return
     */
    @PostMapping(value = "/user/getMenuPermissonsByToken")
    RestMessage<Map<String, Object>> getMenuPermissonsByToken(@RequestParam(value = "token", required = true) String token);

    /**
     * 用户名是否存在
     *
     * @param userName
     * @return
     */
    @PostMapping(value = "/user/userNameIsExists")
    RestMessage<Boolean> userNameIsExists(@RequestParam(value = "userName", required = true) String userName);

    /**
     * 手机号是否存在
     *
     * @param phone
     * @return
     */
    @PostMapping(value = "/user/phoneIsExists")
    RestMessage<Boolean> phoneIsExists(@RequestParam(value = "phone") String phone, @RequestParam(value = "businessSystem") BusinessSystemEnum businessSystem, @RequestParam(value = "source") Integer source);

    /**
     * 邮箱是否存在
     *
     * @param email
     * @return
     */
    @PostMapping(value = "/user/emailIsExists")
    RestMessage<Boolean> emailIsExists(@RequestParam(value = "email", required = true) String email);

    /**
     * 手机跟邮箱验证码验证
     *
     * @param loginName
     * @param code
     * @return
     */
    @PostMapping(value = "/user/phoneAndEmailCodeValid")
    RestMessage<Boolean> phoneAndEmailCodeValid(@RequestParam(value = "loginName", required = true) String loginName, @RequestParam(value = "code", required = true) String code);

    /**
     * 图片验证码验证
     *
     * @param captchaToken
     * @param code
     * @return
     */
    @PostMapping(value = "/user/captchaValid")
    RestMessage<Boolean> captchaValid(@RequestParam(value = "captchaToken", required = true) String captchaToken, @RequestParam(value = "code", required = true) String code);

    /**
     * 根据用户所属集团更新用户启用状态
     *
     * @param groupId
     * @param enable
     * @return
     */
    @PostMapping(value = "/user/updateEnableByGroupId")
    RestMessage<String> updateEnableByGroupId(@RequestParam(value = "groupId") Long groupId, @RequestParam(value = "enable") Integer enable);

    /**
     * 更新用户与档案ID绑定关系
     *
     * @param userId
     * @param archivesId
     * @return
     */
    @PostMapping(value = "/user/updateArchivesId")
    RestMessage<String> updateStaffId(@RequestParam(value = "userId") Long userId, @RequestParam(value = "archivesId", required = false) Long archivesId);

    /**
     * 修改手机验证码验证
     *
     * @param phone
     * @param code
     * @return
     */
    @PostMapping("/user/updatePhoneCodeValid")
    RestMessage<Boolean> updatePhoneCodeValid(@RequestParam(value = "phone", required = true) String phone, @RequestParam(value = "code", required = true) String code);

    /**
     * 根据用户ID获取token
     *
     * @param userId
     * @param businessSystem
     * @param sourceType
     * @return
     */
    @PostMapping("/user/getTokenByUserId")
    RestMessage<FplUser> getTokenByUserId(@RequestParam(value = "userId", required = true) Integer userId, @RequestParam(value = "businessSystem", required = true) BusinessSystemEnum businessSystem, @RequestParam(value = "sourceType", defaultValue = "PC") SourceTypeEnum sourceType);

    /**
     * 用户列表（不控制用户查询权限）
     *
     * @param userDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/user/findUserListPage")
    RestMessage<PageInfo<FplUser>> findUserListPage(@RequestBody UserDto userDto, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize);

    /**
     * 批量根据用户名获取用户
     *
     * @param userNamesDto
     * @return
     */
    @PostMapping(value = "/user/findUserListByUserName")
    RestMessage<List> findUserListByUserName(UserNamesDto userNamesDto);

    /**
     * 查询用户信息接口
     */
    @PostMapping(value = "/user/getUserList")
    RestMessage<List<FplUser>> getUserList(@RequestParam(value = "condition", required = false) String condition, @RequestParam(value = "orgId", required = true) Integer orgId);

    @PostMapping(value = "/user/batchUpdate")
    RestMessage<String> batchUpdate(@RequestBody BatchUpdateReq batchUpdateReq);

    /**
     * 根据用户字表id查询用户的兄弟子表集合
     */
    @RequestMapping(value = "/user/getDetailListByUserId")
    RestMessage<List<UserDetail>> getDetailListByUserId(@RequestParam(value = "userId") Long userId);

}
