package com.usercenter.server.controller;

import com.common.util.message.RestMessage;
import com.usercenter.common.enums.BaseUserEnum;
import com.usercenter.server.common.enums.ReturnCodesEnum;
import com.usercenter.server.constant.command.enums.BaseUserEnumsManager;
import com.usercenter.server.constant.command.enums.BaseUserManagerLevelEnum;
import com.usercenter.server.domain.vo.req.administratorsuser.AdministratorsQueryReq;
import com.usercenter.server.domain.vo.req.administratorsuser.AdministratorsSaveUpdateReq;
import com.usercenter.server.domain.vo.resp.AdministratorAddResp;
import com.usercenter.server.domain.vo.resp.AdministratorsDetailResp;
import com.usercenter.server.domain.vo.resp.PageResult;
import com.usercenter.server.domain.vo.resp.QueryByPhoneDetailResp;
import com.usercenter.server.entity.BaseUserDetail;
import com.usercenter.server.entity.BaseUserInfo;
import com.usercenter.server.service.IBaseAdministratorsUserService;
import com.common.framework.user.FplUserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.ResolverUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 管理员控制类
 */
@Api(tags = "管理员列表接口", description = "管理员列表接口")
@RestController
@RequestMapping("/administrators")
public class AdministratorsUserController extends AbstractUserCheckController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorsUserController.class);


    @Autowired
    private IBaseAdministratorsUserService iBaseAdministratorsUserService;

    /**
     * 管理员列表
     *
     * @return
     */
    @ApiOperation(value = "根据当前用户，条件查找管理员列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public RestMessage<PageResult<AdministratorsDetailResp>> getListAdministrator(@RequestBody AdministratorsQueryReq req) {
        //获取当前登录用户
        Long userId = req.getId();
        RestMessage<BaseUserDetail> baseUserDataResult = super.checkUser(userId);
        if (!baseUserDataResult.isSuccess()) {
            return RestMessage.error(baseUserDataResult.getCode(), baseUserDataResult.getMessage());
        }
        BaseUserDetail loginUser = baseUserDataResult.getData();
        Integer managerLevel = loginUser.getManagerLevel();
        //根据用户不同级别获取对应的数据
        PageResult<AdministratorsDetailResp> listByPage = iBaseAdministratorsUserService.getAdministratorListByPage(req, managerLevel);
        return RestMessage.doSuccess(listByPage);
    }


    /**
     * 根据手机号获取管理员
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "根据手机号查找用户信息，传递phone就好")
    @RequestMapping(value = "/getAdministratorsByPhone", method = RequestMethod.POST)
    public RestMessage<QueryByPhoneDetailResp> getAdministratorsByPhone(@RequestBody AdministratorsQueryReq req) {
        String phone = req.getPhone();
        if (StringUtils.isBlank(phone)) {
            return null;
        }
        BaseUserInfo existUser = iBaseAdministratorsUserService.getUserByPhone(phone, null, null);
        QueryByPhoneDetailResp builder = QueryByPhoneDetailResp.builder(existUser);
        builder.setManagerLevel(null);
        return RestMessage.doSuccess(builder);
    }


    /**
     * 添加管理员
     *
     * @return
     */
    @ApiOperation(value = "添加集团管理员")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RestMessage<AdministratorAddResp> addAdministrator(@RequestBody AdministratorsSaveUpdateReq req) {
        RestMessage<BaseUserDetail> restMessage = super.checkUser(req.getUserId());
        if (!restMessage.isSuccess()) {
            return RestMessage.error(restMessage.getCode(), restMessage.getMessage());
        }
        BaseUserDetail baseUserDetail = restMessage.getData();
        if (baseUserDetail.getManagerLevel() > BaseUserManagerLevelEnum.GLOBAL_ADMINISTRATORS.getCode()) {
            return RestMessage.error(ReturnCodesEnum.ERROR_LOGIN_OPERATE.getMessage());
        }
        req.setGroupId(FplUserUtil.getGroupId());
        req.setOrgId(FplUserUtil.getOrgId());
        AdministratorAddResp administratorAddResp = iBaseAdministratorsUserService.addAdministrator(req);
        return RestMessage.doSuccess(administratorAddResp);
    }


    /**
     * 修改管理员
     */
    @ApiOperation(value = "更新集团管理员")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestMessage<Integer> updateAdministrator(@RequestBody AdministratorsSaveUpdateReq req) {
        RestMessage<BaseUserDetail> restMessage = super.checkUser(req.getUserId());
        if (!restMessage.isSuccess()) {
            return RestMessage.error(restMessage.getCode(), restMessage.getMessage());
        }

        Integer update = iBaseAdministratorsUserService.updateAdministrators(req);
        if (update > 0) {
            return RestMessage.doSuccess(update);
        } else {
            return RestMessage.error(ReturnCodesEnum.USER_UPDATE_FAIL.getMessage());
        }
    }


    /**
     * 管理员详情
     */
    @ApiOperation(value = "查找管理员详情,传递id")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public RestMessage<AdministratorsDetailResp> getDetail(@RequestBody AdministratorsQueryReq req) {
        AdministratorsDetailResp administratorsDetailResp = iBaseAdministratorsUserService.getAdministratorDetail(req.getId());
        return RestMessage.doSuccess(administratorsDetailResp);
    }

    @ApiOperation(value = "全局常量说明")
    @RequestMapping(value = "/show", method = RequestMethod.POST)
    public RestMessage showConstant() {
        ResolverUtil<BaseUserEnum> baseUserEnumResolverUtil = new ResolverUtil<>();
        ResolverUtil<BaseUserEnum> implementations = baseUserEnumResolverUtil.findImplementations(BaseUserEnum.class, ClassUtils.getPackageName(BaseUserEnum.class));
        Set<Class<? extends BaseUserEnum>> classes = implementations.getClasses();
        Map<String, List<Map<Integer, String>>> response = new HashMap<>();
        classes.forEach(clazz -> {
            if (clazz.isEnum()) {
                BaseUserEnumsManager byClass = BaseUserEnumsManager.getByClass(clazz);
                BaseUserEnum[] enumConstants = clazz.getEnumConstants();
                List<Map<Integer, String>> enumsTypes = new ArrayList<>();
                Arrays.stream(enumConstants).forEach(constant -> {
                    Map<Integer, String> items = new HashMap<>();
                    items.put(constant.getCode(), constant.getName());
                    enumsTypes.add(items);
                });
                if (byClass == null) {
                    return;
                }
                response.put(byClass.getDesc(), enumsTypes);
            }
        });
        return RestMessage.doSuccess(response);
    }


}
