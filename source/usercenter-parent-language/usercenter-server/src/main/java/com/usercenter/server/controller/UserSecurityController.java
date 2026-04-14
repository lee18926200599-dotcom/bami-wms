package com.usercenter.server.controller;

import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.usercenter.common.dto.FplUser;
import com.usercenter.server.common.enums.ReturnCodesEnum;
import com.usercenter.server.constant.BaseUserConstants;
import com.usercenter.server.entity.BaseUser;
import com.usercenter.server.entity.BaseUserDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-security")
@Api(tags = "用户数据安全控制", description = "用户数据安全控制")
public class UserSecurityController extends AbstractUserController {


    /**
     * @param loginName
     * @param oldPassword
     * @param newPassword
     * @return
     * @see UserController
     * <p>
     * 更新用户密码
     * 同方法：updatePassword
     * 该方法的调用的前置条件
     * 发送验证码(getPasswordCode)--》校验验证(phoneAndEmailCodeValid)--》更新数据()
     * 需要在校验验证码中存入结果验证结果
     * 校验验证码参考
     */
    @ApiOperation(value = "用户修改密码【增加安全校验】，前置条件，调用phoneAndEmailCodeValid", httpMethod = "POST")
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public RestMessage<String> updatePassword(
            @RequestParam(value = "loginName") String loginName,
            @RequestParam(value = "oldPassword", required = false) String oldPassword,
            @RequestParam(value = "newPassword") String newPassword,
            @RequestParam(value = "code", required = false) String code) {
        BaseUser baseUser = iBaseUserCommonService.getUserByLoginName(loginName);
        //用户验证
        if (baseUser == null) {
            return RestMessage.error(ReturnCodesEnum.USER_NOTEXISTS.getMessage());
        }
        //获取该接口的调用错误次数
        Integer errorCount = errorInvoke(baseUser.getId());
        if (errorCount >= BaseUserConstants.COUNT) {
            return RestMessage.error(ReturnCodesEnum.CODE_NOTEXISTS.getMessage());
        }
        //安全校验
        String s = userRedisUtil.getStr(BaseUserConstants.BASE_USER_CODE_RESULT + baseUser.getId());
        if (StringUtils.isEmpty(s) || Boolean.FALSE.toString().equals(s)) {
            writeErrorToRedis(baseUser.getId(), errorCount);
            return RestMessage.error(ReturnCodesEnum.CODE_WRONG.getMessage());
        } else if (StringUtils.isNotEmpty(s) && s.equals(Boolean.TRUE.toString())) {
            ReturnCodesEnum returnCodesEnum = super.updateUserPasswordCheck(baseUser, code, oldPassword, newPassword);
            if (returnCodesEnum != null) {
                writeErrorToRedis(baseUser.getId(), errorCount);
                return RestMessage.error(returnCodesEnum.getMessage());
            }
        }
        super.updateUserPassword(baseUser, newPassword);
        return RestMessage.doSuccess(I18nUtils.getMessage("user.check.common.success"));
    }


    /**
     * 流程--》getPasswordCode)--》校验验证(phoneAndEmailCodeValid)->新手机号校验  getUpdatePhoneCode-->updatePhoneCodeValid -->修改手机号 update
     * <p>
     * 修改手机号
     *
     * @param fplUser
     * @return
     */
    @RequestMapping("/updateBindPhone")
    @ApiOperation(value = "用户修改手机号", httpMethod = "POST")
    public RestMessage<FplUser> update(@RequestBody FplUser fplUser) {
        BaseUserDetail query = new BaseUserDetail();
        query.setId(fplUser.getId());
        BaseUserDetail oneDetail = iBaseUserCommonService.getOneDetail(query);
        if (oneDetail == null) {
            return RestMessage.error(ReturnCodesEnum.USER_NOTEXISTS.getMessage());
        }
        //获取校验结果
        Integer errorInvoke = errorInvoke(oneDetail.getUserId());
        if (errorInvoke >= BaseUserConstants.COUNT) {
            updateBindPhoneLock(oneDetail.getUserId());
        }
        Boolean updateBindPhoneLock = getUpdateBindPhoneLock(oneDetail.getUserId());
        if (updateBindPhoneLock) {
            return RestMessage.error(ReturnCodesEnum.USER_LOCK.getMessage());
        }
        String s = userRedisUtil.getStr(BaseUserConstants.BASE_USER_CODE_RESULT + oneDetail.getUserId());
        if (StringUtils.isEmpty(s) || Boolean.FALSE.toString().equals(s)) {
            writeErrorToRedis(fplUser.getId(), errorInvoke);
            return RestMessage.error(ReturnCodesEnum.CODE_WRONG.getMessage());
        }
        userApiService.updateUser(fplUser);
        return RestMessage.doSuccess(fplUser);
    }

}
