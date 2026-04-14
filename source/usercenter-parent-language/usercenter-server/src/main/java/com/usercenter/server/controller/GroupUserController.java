package com.usercenter.server.controller;

import cn.hutool.core.bean.BeanUtil;
import com.common.util.message.RestMessage;
import com.usercenter.server.domain.vo.req.groupuser.GroupUserListReq;
import com.usercenter.server.domain.vo.req.groupuser.GroupUserSaveReq;
import com.usercenter.server.domain.vo.req.groupuser.GroupUserUpdateReq;
import com.usercenter.server.domain.vo.resp.GroupUserDetailResp;
import com.usercenter.server.domain.vo.resp.PageResult;
import com.usercenter.server.domain.vo.resp.UserAuthInfoResp;
import com.usercenter.common.dto.request.UserSaveRespDTO;
import com.usercenter.server.common.enums.ReturnCodesEnum;
import com.usercenter.server.entity.BaseUserDetail;
import com.usercenter.server.entity.BaseUserInfo;
import com.usercenter.server.service.IBaseUserGroupService;
import com.usercenter.server.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 用户 api controller
 */
@Api(tags = "集团用户接口", description = "集团用户接口")
@RestController
@RequestMapping("/groupUser")
public class GroupUserController extends AbstractUserCheckController {

    @Autowired
    private IBaseUserGroupService userGroupService;


    @ApiOperation(value = "新增用户", httpMethod = "POST")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RestMessage<UserSaveRespDTO> add(@RequestBody GroupUserSaveReq saveReq) {
        UserSaveRespDTO saveRespDTO = userGroupService.add(saveReq);
        saveRespDTO.setShowPassword(saveRespDTO.getPassword() == null ? false : true);
        saveRespDTO.setMessage(ReturnCodesEnum.GENERATE_DETAIL_SUCCESS.getMessage());
        return RestMessage.doSuccess(saveRespDTO);
    }

    @ApiOperation(value = "修改用户", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestMessage update(@RequestBody GroupUserUpdateReq updateReq) {
        userGroupService.update(updateReq);
        return RestMessage.doSuccess(null);
    }

    @ApiOperation(value = "获取分页", httpMethod = "POST")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public RestMessage<PageResult<GroupUserDetailResp>> list(@RequestBody GroupUserListReq listReq) {
        PageResult<GroupUserDetailResp> page = userGroupService.getPage(listReq);
        return RestMessage.doSuccess(page);
    }


    @ApiOperation(value = "获取用户详情", httpMethod = "POST")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public RestMessage<GroupUserDetailResp> getDetail(@RequestParam Long id) {
        GroupUserDetailResp detail = userGroupService.getDetail(id);
        return RestMessage.doSuccess(detail);
    }

    @ApiOperation(value = "根据手机号获取用户信息", httpMethod = "POST")
    @RequestMapping(value = "/getGroupUserByPhone", method = RequestMethod.POST)
    public RestMessage<UserAuthInfoResp> getUserByPhone(@RequestParam String phone) {
        if (StringUtils.isBlank(phone)) {
            return null;
        }
        RestMessage<BaseUserDetail> restMessage = super.checkUser(UserUtil.getUserId());
        if (!restMessage.isSuccess()) {
            return RestMessage.error(restMessage.getCode(), restMessage.getMessage());
        }
        BaseUserInfo existUser = userGroupService.getUserByPhone(phone, null, null);
        if (existUser == null) {
            return RestMessage.doSuccess(null);
        }
        UserAuthInfoResp result = new UserAuthInfoResp();
        BeanUtil.copyProperties(existUser, result);
        return RestMessage.doSuccess(result);
    }

}
