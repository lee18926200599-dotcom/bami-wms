package com.usercenter.server.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.basedata.common.dto.DictionaryItemDto;
import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.common.util.util.AssertUtils;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.google.common.collect.Sets;
import com.org.permission.common.org.dto.OrgInfoDto;
import com.org.permission.common.org.dto.OrganizationDto;
import com.org.permission.common.permission.dto.UserAllPermissionDto;
import com.usercenter.common.dto.*;
import com.usercenter.common.dto.request.*;
import com.usercenter.common.enums.*;
import com.usercenter.server.service.command.response.UpdateUserCommandResp;
import com.usercenter.server.common.exception.WarnException;
import com.usercenter.server.constant.BaseUserConstants;
import com.usercenter.server.domain.dto.UserNamesDto;
import com.usercenter.server.domain.service.DictionaryDomainService;
import com.usercenter.server.domain.service.OrgDomainService;
import com.usercenter.server.domain.service.PermissionDomainService;
import com.usercenter.server.entity.BaseUser;
import com.usercenter.server.entity.BaseUserDetail;
import com.usercenter.server.entity.BaseUserInfo;
import com.usercenter.server.entity.BaseUserPasswordHistory;
import com.usercenter.server.utils.RegexUtils;
import com.usercenter.server.utils.UserStaffUtil;
import com.usercenter.server.utils.UserUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import com.usercenter.server.common.enums.ReturnCodesEnum;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/user")
@Api(tags = "用户管理", description = "用户管理")
public class UserController extends AbstractUserController {

    @Resource
    private OrgDomainService orgDomainService;
    @Resource
    private DictionaryDomainService dictionaryDomainService;

    @Resource
    private PermissionDomainService permissionDomainService;

    @PostMapping(value = "/getUserInfo")
    @ApiOperation(value = "获取用户信息", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "用户编号", required = true, paramType = "query", dataType = "int"),})
    public RestMessage<FplUser> getUserInfo(@RequestParam(value = "userId") Long userId) {
        FplUser fplUserInfo = userApiService.getUserById(userId);
//        AssertUtils.notNull(fplUserInfo, "查询不到用户信息！");
        if (fplUserInfo == null) {
            return RestMessage.error(I18nUtils.getMessage("user.check.select.user.exist"));
        }
        if (fplUserInfo.getOrgId() != null) {
            OrganizationDto organizationDto = orgDomainService.getOrgInfoById(fplUserInfo.getOrgId());
            if (organizationDto != null) {
                fplUserInfo.setOrgName(organizationDto.getOrgName());
            }
        }
        UserStaffUtil.setStaffInfo(fplUserInfo);
        return RestMessage.doSuccess(fplUserInfo);
    }


    @PostMapping(value = "/getUserInfoList")
    @ApiOperation(value = "获取用户信息列表", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "userInfoDto", value = "用户", required = true, paramType = "body", dataType = "UserInfoListDto")})
    public RestMessage<List<FplUser>> getUserInfoList(@RequestBody UserInfoListDto userInfoDto) {
        List<FplUser> fplUserInfoList = userApiService.getUserInfoList(Sets.newHashSet(userInfoDto.getUserIds()));
        UserStaffUtil.setStaffInfo(fplUserInfoList);
        return RestMessage.doSuccess(fplUserInfoList);
    }

    @ApiOperation(value = "获取用户信息map", httpMethod = "POST")
    @PostMapping(value = "/getUserInfoMap")
    public RestMessage<Map<Long, FplUser>> getUserInfoMap(@RequestBody Set<Long> ids) {
        Map<Long, FplUser> userInfoMap = userApiService.getUserInfoMap(ids);
        return RestMessage.doSuccess(userInfoMap);
    }


    @ApiOperation(value = "更新用户信息", httpMethod = "POST")
    @PostMapping(value = "/updateUser")
    public RestMessage updateUser(@RequestBody UserUpdateDto updateDTO) {
        FplUser fplUser = new FplUser();
        BeanUtil.copyProperties(updateDTO, fplUser);
        userApiService.updateUser(fplUser);
        return RestMessage.doSuccess(null);
    }


    @ApiOperation(value = "批量更新用户账号信息", httpMethod = "POST")
    @PostMapping(value = "/batchUpdate")
    public RestMessage<String> batchUpdate(@RequestBody BatchUpdateReq batchUpdateReq) {
        UpdateUserCommandResp updateUserCommandResp = iBaseUserCommonService.batchUpdate(batchUpdateReq);
        if (updateUserCommandResp.getReturnCodesEnum() == null) {
            return RestMessage.doSuccess(updateUserCommandResp.getMessage());
        } else {
            ReturnCodesEnum returnCodesEnum = updateUserCommandResp.getReturnCodesEnum();
            return RestMessage.error(String.valueOf(returnCodesEnum.getCode()), returnCodesEnum.getMessage());
        }
    }

    /**
     * 用户注册
     *
     * @param userSaveDto businessSystem 业务系统必填
     *                    sourceType 注册来源，默认PC
     * @return
     */
    @ApiOperation(value = "用户注册（）", httpMethod = "POST")
    @ApiImplicitParam(name = "userSaveDto", value = "userSaveDto", required = true, paramType = "body", dataType = "UserSaveDto")
    @PostMapping("/register")
    public RestMessage<FplUser> register(@RequestBody UserSaveDto userSaveDto) {
        FplUser result = new FplUser();
        logger.info("用户注册参数：{}", JSON.toJSONString(userSaveDto));
        UserSaveRespDTO userSaveRespDTO = userApiService.register(userSaveDto);
        BeanUtil.copyProperties(userSaveRespDTO, result);
        //没有密码代表改手机号已经存在主表仅仅生成细表
        if (ObjectUtils.isEmpty(result.getPassword())) {
            return RestMessage.error(String.valueOf(ReturnCodesEnum.GENERATE_DETAIL_SUCCESS.getCode()),String.format(ReturnCodesEnum.GENERATE_DETAIL_SUCCESS.getMessage(),result.getUserName()));
        }
        return RestMessage.doSuccess(result);
    }


    @PostMapping("/update")
    @ApiOperation(value = "用户修改", httpMethod = "POST")
    public RestMessage<FplUser> update(@RequestBody FplUser fplUser) {
        userApiService.updateUser(fplUser);
        return RestMessage.doSuccess(fplUser);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "用户删除", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "query", dataType = "int"),})
    public RestMessage<String> delete(@RequestParam(value = "userId", required = true) Long userId) {
        BaseUserDetail userDetailQuery = new BaseUserDetail();
        userDetailQuery.setId(userId);
        BaseUserDetail userDetail = iBaseUserCommonService.getOneDetail(userDetailQuery);
        AssertUtils.notNull(userDetail, ReturnCodesEnum.USER_NOTEXISTS.getMessage());
        AssertUtils.isTrue(!UserStateEnum.ENABLE.getCode().equals(userDetail.getState()), ReturnCodesEnum.NOT_ENABLE.getMessage());
        BatchUpdateReq updateReq = new BatchUpdateReq();
        updateReq.setIds(Sets.newHashSet(userId));
        updateReq.setOperate(6);
        iBaseUserCommonService.batchUpdate(updateReq);
        return RestMessage.doSuccess(I18nUtils.getMessage("user.check.common.success"));
    }

    @PostMapping("/enable")
    @ApiOperation(value = "用户启用", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "query", dataType = "int"),})
    public RestMessage<String> enable(@RequestParam(value = "userId", required = true) Long userId) {
        BaseUserDetail userDetailQuery = new BaseUserDetail();
        userDetailQuery.setId(userId);
        BaseUserDetail userDetail = iBaseUserCommonService.getOneDetail(userDetailQuery);
        AssertUtils.notNull(userDetail, ReturnCodesEnum.USER_NOTEXISTS.getMessage());
        BaseUser user = iBaseUserCommonService.getPrimaryById(userDetail.getUserId());
        AssertUtils.notNull(user, ReturnCodesEnum.USER_NOTEXISTS.getMessage());
        if (StringUtils.isNotEmpty(user.getPhone())) {
            List<BaseUserInfo> userList = userApiService.getUserListByPhone(user.getPhone(), UserStateEnum.ENABLE, null);
            if (!ObjectUtils.isEmpty(userList)) {
                boolean flag = false;
                for (BaseUserInfo tempUser : userList) {
                    if (tempUser.getUserId().intValue() != user.getId().intValue()) {
                        flag = true;
                    }
                }
                AssertUtils.isNotTrue(flag, ReturnCodesEnum.NOT_ENABLE.getMessage());
            }
        }
        BatchUpdateReq req = new BatchUpdateReq();
        req.setIds(Sets.newHashSet(userDetail.getId()));
        req.setOperate(1);
        iBaseUserCommonService.batchUpdate(req);
        return RestMessage.doSuccess(I18nUtils.getMessage("user.check.common.success"));
    }

    @PostMapping("/batchEnable")
    @ApiOperation(value = "批量用户启用", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "userIds", value = "用户编号列表", required = true, paramType = "form", dataType = "java.util.List", allowMultiple = true),})
    public RestMessage<String> batchEnable(@RequestParam(value = "userIds") List<Long> userIds) {
        BatchUpdateReq batchUpdateReq = new BatchUpdateReq();
        Set<Long> ids = userIds.stream().collect(Collectors.toSet());
        batchUpdateReq.setIds(ids);
        batchUpdateReq.setOperate(1);
        UpdateUserCommandResp updateUserCommandResp = iBaseUserCommonService.batchUpdate(batchUpdateReq);
        if (updateUserCommandResp.getReturnCodesEnum() != null) {
            return RestMessage.error(String.valueOf(updateUserCommandResp.getReturnCodesEnum().getCode()), updateUserCommandResp.getReturnCodesEnum().getMessage());
        }
        return RestMessage.doSuccess(I18nUtils.getMessage("user.check.common.success"));
    }

    @PostMapping("/disabled")
    @ApiOperation(value = "用户停用", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "query", dataType = "int"),})
    public RestMessage<String> disabled(@RequestParam(value = "userId", required = true) Long userId) {
        BatchUpdateReq batchUpdateReq = new BatchUpdateReq();
        Set<Long> ids = new HashSet<>(1);
        ids.add(userId);
        batchUpdateReq.setIds(ids);
        batchUpdateReq.setOperate(2);
        UpdateUserCommandResp updateUserCommandResp = iBaseUserCommonService.batchUpdate(batchUpdateReq);
        if (updateUserCommandResp.getReturnCodesEnum() != null) {
            return RestMessage.error(String.valueOf(updateUserCommandResp.getReturnCodesEnum().getCode()), updateUserCommandResp.getReturnCodesEnum().getMessage());
        }
        return RestMessage.doSuccess(I18nUtils.getMessage("user.check.common.success"));
    }

    @PostMapping("/batchDisabled")
    @ApiOperation(value = "用户停用", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "userIds", value = "用户编号列表", required = true, paramType = "form", dataType = "java.util.List", allowMultiple = true),})
    public RestMessage<String> batchDisabled(@RequestParam(value = "userIds", required = true) List<Long> userIds) {
        Set<Long> ids = userIds.stream().collect(Collectors.toSet());
        BatchUpdateReq batchUpdateReq = new BatchUpdateReq();
        batchUpdateReq.setIds(ids);
        batchUpdateReq.setOperate(2);
        UpdateUserCommandResp updateUserCommandResp = iBaseUserCommonService.batchUpdate(batchUpdateReq);
        if (updateUserCommandResp.getReturnCodesEnum() != null) {
            return RestMessage.error(String.valueOf(updateUserCommandResp.getReturnCodesEnum().getCode()), updateUserCommandResp.getReturnCodesEnum().getMessage());
        }
        return RestMessage.doSuccess(I18nUtils.getMessage("user.check.common.success"));
    }

    @PostMapping("/lock")
    @ApiOperation(value = "用户锁定", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "query", dataType = "int"),})
    public RestMessage<String> lock(@RequestParam(value = "userId", required = true) Long userId) {
        Set<Long> ids = new HashSet<>(1);
        ids.add(userId);
        BatchUpdateReq batchUpdateReq = new BatchUpdateReq();
        batchUpdateReq.setIds(ids);
        batchUpdateReq.setOperate(3);
        UpdateUserCommandResp updateUserCommandResp = iBaseUserCommonService.batchUpdate(batchUpdateReq);
        if (updateUserCommandResp.getReturnCodesEnum() != null) {
            return RestMessage.error(String.valueOf(updateUserCommandResp.getReturnCodesEnum().getCode()), updateUserCommandResp.getReturnCodesEnum().getMessage());
        }
        return RestMessage.doSuccess(I18nUtils.getMessage("user.check.common.success"));
    }

    @PostMapping("/updateLock")
    @ApiOperation(value = "修改用户锁定状态", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "loginName", value = "登录名", required = true, paramType = "query", dataType = "String"), @ApiImplicitParam(name = "lock", value = "锁定状态(true锁定、false解锁)", required = true, paramType = "query", dataType = "String"),})
    public RestMessage<String> updateLock(@RequestParam(value = "loginName", required = true) String loginName, @RequestParam(value = "lock", required = true) Boolean lock) {
        BaseUser baseUser = iBaseUserCommonService.getUserByLoginName(loginName);
        AssertUtils.notNull(baseUser, ReturnCodesEnum.USER_NOTEXISTS.getMessage());
        BaseUserDetail userDetailQuery = new BaseUserDetail();
        userDetailQuery.setUserId(baseUser.getId());
        userDetailQuery.setDeletedFlag(FlagEnum.FALSE.getCode());
        BaseUserDetail oneDetail = iBaseUserCommonService.getOneDetail(userDetailQuery);
        AssertUtils.notNull(oneDetail, ReturnCodesEnum.USER_NOTEXISTS.getMessage());
        BatchUpdateReq req = new BatchUpdateReq();
        req.setIds(Sets.newHashSet(oneDetail.getId()));
        if (lock) {
            req.setOperate(3);
        } else {
            req.setOperate(4);
        }
        iBaseUserCommonService.batchUpdate(req);
        return RestMessage.doSuccess(I18nUtils.getMessage("user.check.common.success"));
    }

    @PostMapping("/batchLock")
    @ApiOperation(value = "批量用户锁定", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "userIds", value = "用户编号列表", required = true, paramType = "form", dataType = "java.util.List", allowMultiple = true),})
    public RestMessage<String> batchLock(@RequestParam(value = "userIds", required = true) List<Long> userIds) {
        Set<Long> ids = userIds.stream().collect(Collectors.toSet());
        BatchUpdateReq batchUpdateReq = new BatchUpdateReq();
        batchUpdateReq.setIds(ids);
        batchUpdateReq.setOperate(3);
        UpdateUserCommandResp updateUserCommandResp = iBaseUserCommonService.batchUpdate(batchUpdateReq);
        if (updateUserCommandResp.getReturnCodesEnum() != null) {
            return RestMessage.error(String.valueOf(updateUserCommandResp.getReturnCodesEnum().getCode()), updateUserCommandResp.getReturnCodesEnum().getMessage());
        }
        return RestMessage.doSuccess(I18nUtils.getMessage("user.check.common.success"));
    }

    @PostMapping("/unlock")
    @ApiOperation(value = "用户解锁", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "query", dataType = "int"),})
    public RestMessage<String> unlock(@RequestParam(value = "userId", required = true) Long userId) {
        Set<Long> ids = new HashSet<>(1);
        ids.add(userId);
        BatchUpdateReq batchUpdateReq = new BatchUpdateReq();
        batchUpdateReq.setIds(ids);
        batchUpdateReq.setOperate(4);
        UpdateUserCommandResp updateUserCommandResp = iBaseUserCommonService.batchUpdate(batchUpdateReq);
        if (updateUserCommandResp.getReturnCodesEnum() != null) {
            return RestMessage.error(String.valueOf(updateUserCommandResp.getReturnCodesEnum().getCode()), updateUserCommandResp.getReturnCodesEnum().getMessage());
        }
        return RestMessage.doSuccess(I18nUtils.getMessage("user.check.common.success"));
    }

    @PostMapping("/batchUnlock")
    @ApiOperation(value = "批量用户解锁", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "userIds", value = "用户编号列表", required = true, paramType = "form", dataType = "java.util.List", allowMultiple = true),})
    public RestMessage<String> batchUnlock(@RequestParam(value = "userIds", required = true) List<Long> userIds) {
        Set<Long> ids = userIds.stream().collect(Collectors.toSet());
        BatchUpdateReq batchUpdateReq = new BatchUpdateReq();
        batchUpdateReq.setIds(ids);
        batchUpdateReq.setOperate(4);
        UpdateUserCommandResp updateUserCommandResp = iBaseUserCommonService.batchUpdate(batchUpdateReq);
        if (updateUserCommandResp.getReturnCodesEnum() != null) {
            return RestMessage.error(String.valueOf(updateUserCommandResp.getReturnCodesEnum().getCode()), updateUserCommandResp.getReturnCodesEnum().getMessage());
        }
        return RestMessage.doSuccess(I18nUtils.getMessage("user.check.common.success"));
    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录", httpMethod = "POST")
    public RestMessage<FplUser> login(@RequestBody LoginRequest loginRequest) {
        FplUser fplUser = userApiService.login(loginRequest);
        UserStaffUtil.setStaffInfo(fplUser);
        return RestMessage.doSuccess(fplUser);
    }

    @PostMapping("/getLoginInfoList")
    @ApiOperation(value = "获取用户登陆信息", httpMethod = "POST")
    public RestMessage<List<LoginInfoDto>> getLoginInfoList(@RequestBody GetLoginInfoListRequest request) {
        List<LoginInfoDto> loginInfoList = userApiService.getLoginInfoList(request);
        return RestMessage.doSuccess(loginInfoList);
    }

    @PostMapping("/logoutUser")
    @ApiOperation(value = "登出指定的用户", httpMethod = "POST")
    public RestMessage logoutUser(@RequestBody LogoutUserReq request) {
        userApiService.logoutUser(request);
        return RestMessage.doSuccess(null);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "用户登出", httpMethod = "POST")
    @ApiImplicitParam(name = "request", value = "登出参数", required = true, paramType = "body", dataType = "LogoutUserReq")
    public RestMessage logout(@RequestBody LogoutUserReq request) {
        userApiService.logout(request.getToken());
        return RestMessage.doSuccess(I18nUtils.getMessage("user.check.common.success"));
    }

    @PostMapping("/status")
    @ApiOperation(value = "用户状态", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "query", dataType = "Long"),})
    public RestMessage<FplUser> status(@RequestParam(value = "userId", required = true) Long userId) {
        BaseUserDetail userDetailQuery = new BaseUserDetail();
        userDetailQuery.setId(userId);
        BaseUserDetail userDetail = iBaseUserCommonService.getOneDetail(userDetailQuery);
        if (userDetail == null) {
            logger.error("用户不存在,userDetailId:{}", userId);
            return RestMessage.error(String.valueOf(ReturnCodesEnum.USER_NOTEXISTS.getCode()), ReturnCodesEnum.USER_NOTEXISTS.getMessage());
        }
        BaseUser baseUser = iBaseUserCommonService.getPrimaryById(userDetail.getUserId());
        if (baseUser == null) {
            logger.error("用户不存在,userId；{}", userDetail.getUserId());
            return RestMessage.error(String.valueOf(ReturnCodesEnum.USER_NOTEXISTS.getCode()), ReturnCodesEnum.USER_NOTEXISTS.getMessage());
        }
        FplUser fplUser = new FplUser();
        fplUser.setId(userId);
        fplUser.setArchivesId(userDetail.getArchivesId());
        fplUser.setContactEmail(userDetail.getContactEmail());
        fplUser.setCreatedDate(userDetail.getCreatedDate());
        fplUser.setCreatedBy(userDetail.getCreatedBy());
        fplUser.setDeletedFlag(userDetail.getDeletedFlag());
        fplUser.setState(userDetail.getState());
        fplUser.setGlobalCustomer(userDetail.getGlobalCustomerId());
        fplUser.setGroupId(userDetail.getGroupId());
        fplUser.setIdentityType(userDetail.getIdentityType());
        fplUser.setLockFlag(userDetail.getLockFlag());
        fplUser.setOrgId(userDetail.getOrgId());
        fplUser.setSource(userDetail.getSource());
        fplUser.setManagerLevel(userDetail.getManagerLevel());
        fplUser.setEmail(baseUser.getEmail());
        fplUser.setFirstTimeLoginFlag(baseUser.getFirstTimeLoginFlag());
        fplUser.setPhone(baseUser.getPhone());
        fplUser.setUserAuthId(baseUser.getId());
        return RestMessage.doSuccess(fplUser);
    }


    /**
     * 用户修改密码/提供了外部API【因未对 验证码/次数做限制，存在安全问题】
     *
     * @param loginName
     * @param oldPassword
     * @param newPassword
     * @param code
     * @return
     */
    @PostMapping("/updatePassword")
    @ApiOperation(value = "用户修改密码【因提供外部服务，未增加安全校验】", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "loginName", value = "登录名", required = true, paramType = "query", dataType = "String"), @ApiImplicitParam(name = "oldPassword", value = "旧密码", required = false, paramType = "query", dataType = "String"), @ApiImplicitParam(name = "newPassword", value = "新密码", required = true, paramType = "query", dataType = "String"), @ApiImplicitParam(name = "code", value = " 验证码", required = false, paramType = "query", dataType = "String"),})
    public RestMessage<String> updatePassword(@RequestParam(value = "loginName", required = true) String loginName, @RequestParam(value = "oldPassword", required = false) String oldPassword, @RequestParam(value = "newPassword", required = true) String newPassword, @RequestParam(value = "code", required = false) String code) {
        System.out.println(String.format("loginName:%s oldPassword:%s newPassword:%s code:%s", loginName, oldPassword, newPassword, code));
        BaseUser baseUser = iBaseUserCommonService.getUserByLoginName(loginName);
        if (baseUser == null) {
            return RestMessage.error(String.valueOf(ReturnCodesEnum.USER_NOTEXISTS.getCode()), ReturnCodesEnum.USER_NOTEXISTS.getMessage());
        }
        Integer errorInvoke = super.errorInvoke(baseUser.getId());
        if (errorInvoke >= BaseUserConstants.LOGIN_FAIL_COUNT) {
            super.updatePasswordLock(baseUser.getId());
        }
        if (super.getUpdatePasswordLock(baseUser.getId())) {
            return RestMessage.error(String.valueOf(ReturnCodesEnum.USER_LOCK.getCode()), ReturnCodesEnum.USER_LOCK.getMessage());
        }
        com.usercenter.server.common.enums.ReturnCodesEnum returnCodesEnum = super.updateUserPasswordCheck(baseUser, code, oldPassword, newPassword);
        if (returnCodesEnum != null) {
            super.writeErrorToRedis(baseUser.getId(), errorInvoke);
            return RestMessage.error(String.valueOf(returnCodesEnum.getCode()), returnCodesEnum.getMessage());

        }
        super.updateUserPassword(baseUser, newPassword);
        return RestMessage.doSuccess(I18nUtils.getMessage("user.check.common.success"));
    }


    @PostMapping("/updateUserNameAndPassword")
    @ApiOperation(value = "修改用户名跟密码", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true, paramType = "query", dataType = "String"), @ApiImplicitParam(name = "userName", value = "登录名", required = false, paramType = "query", dataType = "String"), @ApiImplicitParam(name = "oldPassword", value = "旧密码", required = false, paramType = "query", dataType = "String"), @ApiImplicitParam(name = "newPassword", value = "新密码", required = true, paramType = "query", dataType = "String"), @ApiImplicitParam(name = "phone", value = "手机号", required = false, paramType = "query", dataType = "String"),})
    public RestMessage<String> updateUserNameAndPassword(@RequestParam(value = "token", required = true) String token, @RequestParam(value = "userName", required = false) String userName, @RequestParam(value = "oldPassword", required = false) String oldPassword, @RequestParam(value = "newPassword", required = true) String newPassword, @RequestParam(value = "phone", required = false) String phone) {
        BaseUser user = null;
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(userTokenSecretkey).parseClaimsJws(token).getBody();
            user = iBaseUserCommonService.getPrimaryById(Long.valueOf(claims.getId()));
        } catch (Exception e) {
            throw new WarnException(ReturnCodesEnum.INVALID_TOKEN.getMessage());
        }
        AssertUtils.notNull(user, ReturnCodesEnum.USER_NOTEXISTS.getMessage());
        if (StringUtils.isNotEmpty(oldPassword)) {
            AssertUtils.isTrue(passwordEncoder.encode(oldPassword).equals(user.getPassword()), ReturnCodesEnum.PASSWORD_WRONG.getMessage());
        }
        if (!user.getUserName().equals(userName) && StringUtils.isNotEmpty(userName)) {
            AssertUtils.notNull(userApiService.getUserByUserName(userName, null, null), ReturnCodesEnum.USER_NOTEXISTS.getMessage());
            AssertUtils.isTrue(RegexUtils.isUsername(userName), ReturnCodesEnum.REGEX_FAILD_USERNAME.getMessage());
            user.setUserName(userName);
        }
        AssertUtils.isTrue(RegexUtils.isPassword(newPassword), ReturnCodesEnum.REGEX_FAILD_PASSWORD.getMessage());
        AssertUtils.isTrue(!passwordEncoder.encode(newPassword).equals(user.getPassword()), ReturnCodesEnum.PASSWORD_REUSE.getMessage());
        if (StringUtils.isNotEmpty(phone)) {
            if (!phone.equals(user.getPhone())) {
                AssertUtils.isTrue(RegexUtils.isMobileSimple(phone), ReturnCodesEnum.REGEX_FAILD_PHONE.getMessage());
                //判断手机号码是否存在
                AssertUtils.isTrue(userApiService.getUserByPhone(phone, null, null) == null, ReturnCodesEnum.PHONE_EXISTS.getMessage());
                user.setPhone(phone);
            }
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setPasswordFlag(FlagEnum.FALSE.getCode());
        if (user.getFirstTimeLoginFlag() == FlagEnum.TRUE.getCode()) {
            user.setFirstTimeLoginFlag(FlagEnum.FALSE.getCode());
        }
        BaseUserPasswordHistory userPasswordHistory = new BaseUserPasswordHistory();
        userPasswordHistory.setUserId(user.getId());
        userPasswordHistory.setPassword(passwordEncoder.encode(newPassword));
        userPasswordHistory.setCreateDate(new Date());
        userApiService.updateUserNameAndPassword(user, userPasswordHistory);
        return RestMessage.doSuccess(I18nUtils.getMessage("user.check.common.success"));
    }


    @PostMapping("/isHistoryPassword")
    @ApiOperation(value = "是否历史密码", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "query", dataType = "int"), @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "String"),})
    public RestMessage<Boolean> isHistoryPassword(@RequestParam(value = "userId", required = true) Long userId, @RequestParam(value = "password", required = true) String password) {
        BaseUserDetail userDetailQuery = new BaseUserDetail();
        userDetailQuery.setId(userId);
        userDetailQuery.setDeletedFlag(FlagEnum.FALSE.getCode());
        BaseUserDetail userDetail = iBaseUserCommonService.getOneDetail(userDetailQuery);
        if (userDetail == null) {
            logger.error("用户不存在,userId:{}", userId);
            return RestMessage.error(String.valueOf(ReturnCodesEnum.USER_NOTEXISTS.getCode()), ReturnCodesEnum.USER_NOTEXISTS.getMessage());
        }
        BaseUser baseUser = iBaseUserCommonService.getPrimaryById(userDetail.getUserId());
        if (baseUser == null) {
            return RestMessage.error(String.valueOf(ReturnCodesEnum.USER_NOTEXISTS.getCode()), ReturnCodesEnum.USER_NOTEXISTS.getMessage());
        }
        return RestMessage.doSuccess(iBaseUserCommonService.isHistoryPassword(baseUser.getId(), passwordEncoder.encode(password)));
    }

    @PostMapping("/resetPassword")
    @ApiOperation(value = "用户重置密码", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "query", dataType = "int"),})
    public RestMessage<String> resetPassword(@RequestParam(value = "userId", required = true) Long userId) {
        BaseUserDetail userDetailQuery = new BaseUserDetail();
        userDetailQuery.setId(userId);
        BaseUserDetail userDetail = iBaseUserCommonService.getOneDetail(userDetailQuery);
        if (userDetail == null) {
            logger.error("用户不存在,userDetailId:{}", userId);
            return RestMessage.error(String.valueOf(ReturnCodesEnum.USER_NOTEXISTS.getCode()), ReturnCodesEnum.USER_NOTEXISTS.getMessage());
        }
        BaseUser baseUser = iBaseUserCommonService.getPrimaryById(userDetail.getUserId());
        if (baseUser == null) {
            logger.error("用户不存在,userId:{}", userDetail.getUserId());
            return RestMessage.error(String.valueOf(ReturnCodesEnum.USER_NOTEXISTS.getCode()), ReturnCodesEnum.USER_NOTEXISTS.getMessage());
        }
        String password = BaseUserConstants.USER_PASSWORD_PREFIX + UserUtil.getCode();
        baseUser.setPassword(passwordEncoder.encode(password));
        baseUser.setPasswordFlag(FlagEnum.TRUE.getCode());
        iBaseUserCommonService.updateUser(baseUser);
        return RestMessage.doSuccess(password);


    }

    @PostMapping("/findUserListByPage")
    @ApiOperation(value = "用户列表（前端使用）", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "userDto", value = "userDto", defaultValue = "1", paramType = "body", dataType = "UserDto"), @ApiImplicitParam(name = "pageNum", value = "pageNum", defaultValue = "1", paramType = "query", dataType = "int"), @ApiImplicitParam(name = "pageSize", value = "pageSize", defaultValue = "10", paramType = "query", dataType = "int"),})
    public RestMessage<PageInfo<FplUser>> findUserListByPage(@RequestBody UserDto userDto, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageInfo<FplUser> pageInfo = new PageInfo<>();
        FplUser fplUser = null;
        if (userDto.getId() != null) {
            fplUser = userApiService.getUserById(userDto.getId());
        }
        AssertUtils.notNull(fplUser, ReturnCodesEnum.USER_NOTEXISTS.getMessage());
        userDto.setDeletedFlag(FlagEnum.FALSE.getCode());
        userDto.setManagerLevel(UserManagerLevelEnum.USER.getCode());
        if (fplUser.getManagerLevel() == UserManagerLevelEnum.USER.getCode()) {
            FplUser puser = new FplUser();
            BeanUtils.copyProperties(fplUser, puser);
            Set<Long> orgIds = permissionDomainService.getOrgsByUidAndGroupId(userDto.getId(), userDto.getGroupId());
            if (CollectionUtils.isNotEmpty(orgIds)) {
                userDto.setOrgIdList(new ArrayList<>(orgIds));
                userDto.setGroupId(null);
                pageInfo = userApiService.findUserListPage(userDto, pageNum, pageSize);
            }
        } else if (fplUser.getManagerLevel() == UserManagerLevelEnum.GROUP_ADMINI.getCode()) {
            pageInfo = userApiService.findUserListPage(userDto, pageNum, pageSize);
        } else {
            userDto.setGroupId(null);
            pageInfo = userApiService.findUserListPage(userDto, pageNum, pageSize);
        }
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            List<Long> orgIds = pageInfo.getList().stream().map(FplUser::getOrgId).distinct().collect(Collectors.toList());
            List<OrgInfoDto> orgInfoDtoList = orgDomainService.getOrgByIdList(orgIds);
            Map<Long, String> orgMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(orgInfoDtoList)) {
                orgMap = orgInfoDtoList.stream().collect(Collectors.toMap(OrgInfoDto::getId, OrgInfoDto::getOrgName));
            }
            Map<String, String> registerSourceMap = dictionaryDomainService.getItemMap("4PL_USER_REGISTER_SOURCE");
            for (FplUser fplUser1 : pageInfo.getList()) {
                fplUser1.setOrgName(orgMap.get(fplUser1.getOrgId()));
                fplUser1.setSourceName(registerSourceMap.get(fplUser1.getSource()));
            }
        }
        return RestMessage.doSuccess(pageInfo);
    }

    @PostMapping("/findManagerListByPage")
    @ApiOperation(value = "管理员列表（前端使用）", httpMethod = "POST")
    @ApiImplicitParam(name = "userDto", value = "userDto", paramType = "body", dataType = "UserDto")
    public RestMessage<PageInfo> findManagerListByPage(@RequestBody UserDto userDto) {
        FplUser loginFplUser = null;
        PageInfo<FplUser> pageInfo = new PageInfo<>();
        if (userDto.getId() != null) {
            loginFplUser = userApiService.getUserById(userDto.getId());
        }
        AssertUtils.notNull(loginFplUser, ReturnCodesEnum.USER_NOTEXISTS.getMessage());
        if (loginFplUser.getManagerLevel() == UserManagerLevelEnum.SUPER_ADMINI.getCode()) {
            userDto.setManagerLevel(UserManagerLevelEnum.GLOBAL_ADMINI.getCode());
            pageInfo = userApiService.findUserListPage(userDto, userDto.getPageNum(), userDto.getPageSize());
        } else if (loginFplUser.getManagerLevel() == UserManagerLevelEnum.GLOBAL_ADMINI.getCode()) {
            userDto.setManagerLevel(UserManagerLevelEnum.GROUP_ADMINI.getCode());
            pageInfo = userApiService.findUserListPage(userDto, userDto.getPageNum(), userDto.getPageSize());
        }
        try {
            List<Long> orgIds = pageInfo.getList().stream().map(user -> user.getOrgId()).distinct().collect(Collectors.toList());
            List<OrgInfoDto> orgInfoDtoList = orgDomainService.getOrgByIdList(orgIds);
            if (CollectionUtils.isNotEmpty(orgInfoDtoList)) {
                Map<Long, String> orgMap = orgInfoDtoList.stream().collect(Collectors.toMap(OrgInfoDto::getId, OrgInfoDto::getOrgName));
                pageInfo.getList().forEach(u -> {
                    u.setOrgName(orgMap.get(u.getOrgId()));
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RestMessage.doSuccess(pageInfo);
    }

    @PostMapping("/findUserList")
    @ApiOperation(value = "用户列表", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "userDto", value = "userDto", defaultValue = "1", paramType = "body", dataType = "UserDto"),})
    public RestMessage<List<FplUser>> findUserList(@RequestBody UserDto userDto) {
        logger.info("findUserList调用：" + JSON.toJSONString(userDto));
        List<FplUser> fplUserList = userApiService.findUserList(userDto);
        UserStaffUtil.setStaffInfo(fplUserList);
        return RestMessage.doSuccess(fplUserList);
    }


    @PostMapping("/findUserListPage")
    @ApiOperation(value = "用户列表（不控制用户查询权限）", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "userDto", value = "userDto", defaultValue = "1", paramType = "body", dataType = "UserDto"), @ApiImplicitParam(name = "pageNum", value = "pageNum", defaultValue = "1", paramType = "query", dataType = "int"), @ApiImplicitParam(name = "pageSize", value = "pageSize", defaultValue = "10", paramType = "query", dataType = "int"),})
    public RestMessage<PageInfo<FplUser>> findUserListPage(@RequestBody UserDto userDto, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageInfo<FplUser> userListPage = userApiService.findUserListPage(userDto, pageNum, pageSize);
        return RestMessage.doSuccess(userListPage);
    }

    /**
     * PC【暂定】 忘记密码使用绑定主键，用户ID
     *
     * @param phone
     * @param email
     * @param validateType
     * @return
     */
    @PostMapping("/getPasswordCode")
    @ApiOperation(value = "获取修改密码验证码", httpMethod = "POST")
    public RestMessage<String> getPasswordCode(@RequestParam(value = "phone", required = false) String phone, @RequestParam(value = "email", required = false) String email, @RequestParam(value = "validateType", required = true) ValidateTypeEnum validateType) {
        String code = userApiService.getPasswordCode(phone, email, validateType);
        if (StringUtil.isNotEmpty(code)) {
            return RestMessage.doSuccess(code);
        }
        return RestMessage.doSuccess(I18nUtils.getMessage("user.check.common.success"));
    }


    /**
     * PC【暂定】/ 忘记密码 验证码校验
     *
     * @param loginName
     * @param code
     * @return
     */
    @PostMapping(value = "/phoneAndEmailCodeValid")
    @ApiOperation(value = "手机跟邮箱验证码验证", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "loginName", value = "登录名", required = true, paramType = "query", dataType = "String"), @ApiImplicitParam(name = "code", value = "用户名", required = true, paramType = "query", dataType = "String"),})
    public RestMessage<Boolean> phoneAndEmailCodeValid(@RequestParam(value = "loginName", required = true) String loginName, @RequestParam(value = "code", required = true) String code) {
        BaseUser baseUser = iBaseUserCommonService.getUserByLoginName(loginName);
        String redisCode = null;
        Integer errorCount = null;
        if (baseUser != null) {
            //尝试次数校验
            errorCount = errorInvoke(baseUser.getId());
            if (errorCount >= BaseUserConstants.LOGIN_FAIL_COUNT) {
                return RestMessage.error(String.valueOf(ReturnCodesEnum.CODE_NOTEXISTS.getCode()), ReturnCodesEnum.CODE_NOTEXISTS.getMessage());
            }
            redisCode = userRedisUtil.getStr(baseUser.getId().toString() + BaseUserConstants.PASSWORD_CODE_SUFFIX);
        }
        Boolean flag = false;
        if (StringUtils.isNotEmpty(redisCode) && code.equals(redisCode)) {
            flag = true;
        }
        //校验结果写入缓存
        if (baseUser != null) {
            userRedisUtil.set(BaseUserConstants.BASE_USER_CODE_RESULT + baseUser.getId(), flag.toString(), BaseUserConstants.TTL_5_MIN);
            //尝试次数校验
            if (!flag) {
                writeErrorToRedis(baseUser.getId(), errorCount);
            }
        }
        return RestMessage.doSuccess(flag);
    }

    @PostMapping("/isTokenValid")
    @ApiOperation(value = "验证token是否有效", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "query", dataType = "int"), @ApiImplicitParam(name = "token", value = "token", required = true, paramType = "query", dataType = "String"),})
    public RestMessage<String> isTokenValid(@RequestParam(value = "userId") Long userId, @RequestParam(value = "token") String token) {
        userApiService.isTokenValid(userId, token);
        return RestMessage.doSuccess(I18nUtils.getMessage("user.check.common.success"));
    }

    @PostMapping(value = "/registerSource")
    @ApiOperation(value = "获取注册来源", httpMethod = "POST")
    public RestMessage<List<DictionaryItemDto>> registerSource() {
        List<DictionaryItemDto> list = userApiService.registerSource();
        return RestMessage.doSuccess(list);
    }

    @PostMapping(value = "/getUserByToken")
    @ApiOperation(value = "根据token获取用户信息（groupId切换集团）", httpMethod = "POST")
    @ApiImplicitParam(name = "param", value = "参数", required = true, paramType = "body", dataType = "UserTokenParam")
    public RestMessage<FplUser> getUserByToken(@RequestBody UserTokenParam param) {
        FplUser fplUser = userApiService.getUserByToken(param.getToken(), param.getGroupId());
        return RestMessage.doSuccess(fplUser);
    }

    @PostMapping(value = "/getMenuPermissonsByToken")
    @ApiOperation(value = "根据token获取菜单权限", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true, paramType = "query", dataType = "String"),})
    public RestMessage<UserAllPermissionDto> getMenuPermissonsByToken(@RequestParam(value = "token", required = true) String token) {
        UserAllPermissionDto menuPermissions = userApiService.getMenuPermissonsByToken(token);
        return RestMessage.doSuccess(menuPermissions);
    }


    @PostMapping(value = "/userNameIsExists")
    @ApiOperation(value = "用户名是否存在", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "userName", value = "用户名", required = true, paramType = "query", dataType = "String"),})
    public RestMessage<Boolean> userNameIsExists(@RequestParam(value = "userName", required = true) String userName) {
        BaseUserInfo one = null;
        if (userName != null) {
            one = userApiService.getUserByUserName(userName, null, null);
        }
        return RestMessage.doSuccess(one != null ? true : false);
    }


    @PostMapping(value = "/phoneIsExists")
    @ApiOperation(value = "手机号是否存在", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "phone", value = "手机号", required = true, paramType = "query", dataType = "String"),})
    public RestMessage<Boolean> phoneIsExists(@RequestParam(value = "phone", required = true) String phone, @RequestParam(value = "businessSystem", required = true) BusinessSystemEnum businessSystem, @RequestParam(value = "source", required = true) Integer source) {
        BaseUserInfo one = null;
        if (StringUtils.isNotEmpty(phone)) {
            //业务系统暂时不用
            one = userApiService.getUserByPhone(phone, source);
        }
        return RestMessage.doSuccess(one != null ? true : false);
    }

    @PostMapping(value = "/emailIsExists")
    @ApiOperation(value = "邮箱是否存在", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "email", value = "邮箱", required = true, paramType = "query", dataType = "String"),})
    public RestMessage<Boolean> emailIsExists(@RequestParam(value = "email", required = true) String email) {
        BaseUserInfo one = null;
        if (StringUtils.isNotEmpty(email)) {
            one = userApiService.getUserByEmail(email, null, null);
        }
        return RestMessage.doSuccess(one != null ? true : false);
    }


    @PostMapping(value = "/captchaValid")
    @ApiOperation(value = "图片验证码验证", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "captchaToken", value = "图片验证码对应KEY", required = true, paramType = "query", dataType = "String"), @ApiImplicitParam(name = "code", value = "用户名", required = true, paramType = "query", dataType = "String"),})
    public RestMessage<Boolean> captchaValid(@RequestParam(value = "captchaToken", required = true) String captchaToken, @RequestParam(value = "code", required = true) String code) {
        String reqCode = userRedisUtil.getStr(captchaToken + BaseUserConstants.CAPTCH_TOKEN_SUFFIX);
        Boolean flag = false;
        if (StringUtils.isNotEmpty(reqCode) && code.equalsIgnoreCase(reqCode)) {
            flag = true;
        }
        return RestMessage.doSuccess(flag);
    }

    @PostMapping(value = "/updateEnableByGroupId")
    @ApiOperation(value = "根据用户所属集团更新用户启用状态", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "groupId", value = "所属集团", required = true, paramType = "query", dataType = "String"), @ApiImplicitParam(name = "enable", value = "启用状态（非启用(0)、启用(1)、停用(2)）", required = true, paramType = "query", dataType = "String"),})
    public RestMessage<String> updateEnableByGroupId(@RequestParam(value = "groupId") Long groupId, @RequestParam(value = "enable") Integer enable) {
        BatchUpdateReq req = new BatchUpdateReq();
        req.setGroupId(groupId);
        req.setOperate(enable);
        UpdateUserCommandResp updateUserCommandResp = iBaseUserCommonService.batchUpdate(req);
        if (updateUserCommandResp != null && updateUserCommandResp.getReturnCodesEnum() != null) {
            return RestMessage.error(String.valueOf(updateUserCommandResp.getReturnCodesEnum().getCode()), updateUserCommandResp.getReturnCodesEnum().getMessage());
        }
        return RestMessage.doSuccess(I18nUtils.getMessage("user.check.common.success"));
    }


    @PostMapping(value = "/updateArchivesId")
    @ApiOperation(value = "更新用户与档案ID绑定关系", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "用户ID", required = true, paramType = "query", dataType = "Integer"), @ApiImplicitParam(name = "archivesId", value = "档案ID", required = false, paramType = "query", dataType = "Integer"),})
    public RestMessage<String> updateStaffId(@RequestParam(value = "userId") Long userId, @RequestParam(value = "archivesId", required = false) Long archivesId) {
        BaseUserDetail userDetailQuery = new BaseUserDetail();
        userDetailQuery.setId(userId);
        userDetailQuery.setDeletedFlag(FlagEnum.FALSE.getCode());
        BaseUserDetail userDetail = iBaseUserCommonService.getOneDetail(userDetailQuery);
        AssertUtils.notNull(userDetail, ReturnCodesEnum.USER_NOTEXISTS.getMessage());
        userDetail.setArchivesId(archivesId);
        iBaseUserCommonService.updateUserDetail(userDetail);
        return RestMessage.doSuccess(I18nUtils.getMessage("user.check.common.success"));
    }


    /**
     * 注册获取手机验证码
     *
     * @param phone
     * @param validateCode
     * @param captchaToken
     * @return
     */
    @PostMapping("/validateCode")
    @ApiOperation(value = "获取手机验证码", httpMethod = "POST")
    public RestMessage<String> getValidateCode(@RequestParam(value = "phone", required = true) String phone, @RequestParam(value = "validateCode", required = true) String validateCode, @RequestHeader(value = "captchaToken", required = false) String captchaToken) {
        try {
            String code = UserUtil.getCode();
            //验证手机号不合法，不能发验证码
            AssertUtils.isNotEmpty(phone, ReturnCodesEnum.PARAM_PHONE_NULL.getMessage());
            AssertUtils.isTrue(RegexUtils.isMobileSimple(phone), ReturnCodesEnum.REGEX_FAILD_PHONE.getMessage());
            //获取不到正确图片验证码，不能发
            String reqCode = userRedisUtil.getStr(captchaToken + "_capText");

            if (StringUtils.isEmpty(reqCode) && !StringUtils.isEmpty(captchaToken)) {
                throw new WarnException(ReturnCodesEnum.CODE_NOTEXISTS.getMessage());
            }
            AssertUtils.isTrue(reqCode.equalsIgnoreCase(validateCode), ReturnCodesEnum.CODE_WRONG.getMessage());
            userRedisUtil.set(phone + BaseUserConstants.PASSWORD_CODE_SUFFIX, code, 3600);
        } catch (Exception e) {
            throw new WarnException(ReturnCodesEnum.GET_CODE_ERROR.getMessage());
        }
        return RestMessage.doSuccess(I18nUtils.getMessage("user.check.common.success"));
    }

    /**
     * 校验验证码
     *
     * @param phone
     * @param code
     * @return
     */
    @PostMapping("/updatePhoneCodeValid")
    @ApiOperation(value = "修改手机验证码验证", httpMethod = "POST")
    public RestMessage<Boolean> updatePhoneCodeValid(@RequestParam(value = "phone", required = true) String phone, @RequestParam(value = "code", required = true) String code) {
        String s = userRedisUtil.getStr(phone + BaseUserConstants.PASSWORD_CODE_SUFFIX);
        if (code.equals(s)) {
            return RestMessage.doSuccess(true);
        }
        return RestMessage.doSuccess(false);
    }

    @PostMapping("/getUpdateEmailCode")
    @ApiOperation(value = "获取修改邮箱验证码", httpMethod = "POST")
    public RestMessage<String> getUpdateEmailCode(@RequestParam(value = "email", required = true) String email) {

        return RestMessage.doSuccess(I18nUtils.getMessage("user.check.common.success"));
    }

    @PostMapping("/updateEmailCodeValid")
    @ApiOperation(value = "修改邮箱验证码验证", httpMethod = "POST")
    public RestMessage<Boolean> updateEmailCodeValid(@RequestParam(value = "email", required = true) String email, @RequestParam(value = "code", required = true) String code) {
        return RestMessage.doSuccess(true);
    }

    @PostMapping(value = "/updateGroupUserStatus")
    @ApiOperation(value = "更新集团用户状态", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "groupId", value = "集团ID", required = true, paramType = "query", dataType = "Integer"), @ApiImplicitParam(name = "enable", value = "启用状态（非启用(0)、启用(1)、停用(2)）", required = true, paramType = "query", dataType = "Integer"),})
    public RestMessage<String> updateGroupUserStatus(@RequestParam(value = "groupId") Long groupId, @RequestParam(value = "enable") Integer enable) {
        BatchUpdateReq req = new BatchUpdateReq();
        req.setOperate(enable);
        req.setGroupId(groupId);
        if (groupId != null) {
            UpdateUserCommandResp updateUserCommandResp = iBaseUserCommonService.batchUpdate(req);
            if (updateUserCommandResp != null && updateUserCommandResp.getReturnCodesEnum() != null) {
                return RestMessage.doError(updateUserCommandResp.getReturnCodesEnum().getMessage());
            }
        }
        return RestMessage.doSuccess(I18nUtils.getMessage("user.check.common.success"));
    }

    @PostMapping("/getTokenByUserId")
    @ApiOperation(value = "根据用户ID获取token", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "query", dataType = "String"), @ApiImplicitParam(name = "businessSystem", value = "当前业务系统", required = true, paramType = "query", dataType = "BusinessSystemEnum"), @ApiImplicitParam(name = "sourceType", value = "登录来源类型", defaultValue = "PC", paramType = "query", dataType = "LoginType"),})
    public RestMessage<FplUser> getTokenByUserId(@RequestParam(value = "userId", required = true) Long userId, @RequestParam(value = "businessSystem", required = true) BusinessSystemEnum businessSystem, @RequestParam(value = "sourceType", defaultValue = "PC") SourceTypeEnum sourceType) {
        FplUser fplUser = userApiService.getTokenByUserId(userId, businessSystem, sourceType);
        return RestMessage.doSuccess(fplUser);
    }


    @PostMapping(value = "/findUserListByUserName")
    @ApiOperation(value = "批量根据用户名获取用户", httpMethod = "POST")
    public RestMessage<List<FplUser>> findUserListByUserName(@RequestBody UserNamesDto userNamesDto) {
        //默认不查询删除的数据
        if (userNamesDto.getWithDeleted() == null) {
            userNamesDto.setWithDeleted(false);
        }
        List<BaseUserInfo> baseUsers = iBaseUserCommonService.getListByUserNames(userNamesDto);
        List<FplUser> results = new ArrayList<>();
        baseUsers.forEach(baseUserInfo -> {
            FplUser fplUserInfoDTO = new FplUser();

            BeanUtil.copyProperties(baseUserInfo, fplUserInfoDTO);
            fplUserInfoDTO.setUserAuthId(baseUserInfo.getUserId());
            results.add(fplUserInfoDTO);
        });
        return RestMessage.doSuccess(results);
    }


    @PostMapping(value = "/getUserList")
    @ApiOperation(value = "条件查询用户信息", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "condition", value = "编码/账号/真实姓名/手机号", required = false, paramType = "query", dataType = "String"), @ApiImplicitParam(name = "orgId", value = "orgId", required = true, paramType = "query", dataType = "Integer")})
    public RestMessage<List<FplUser>> getUserList(@RequestParam(value = "condition", required = false) String condition, @RequestParam(value = "orgId", required = true) Long orgId) {
        List<FplUser> fplUserInfoList = userApiService.findUserList(condition, orgId);
        UserStaffUtil.setStaffInfo(fplUserInfoList);
        return RestMessage.doSuccess(fplUserInfoList);
    }


    @PostMapping(value = "/getDetailListByUserId")
    @ApiOperation(value = "根据用户id查询同主表所有子账号", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "query", dataType = "String")})
    public RestMessage<List<FplUser>> getDetailListByUserId(@RequestParam(value = "userId") Long userId) {
        return RestMessage.doSuccess(userApiService.getDetailListByUserId(userId));
    }
}
