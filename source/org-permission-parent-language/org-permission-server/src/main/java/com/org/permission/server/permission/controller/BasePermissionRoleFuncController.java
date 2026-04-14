package com.org.permission.server.permission.controller;

import com.common.language.util.I18nUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.org.permission.server.permission.dto.req.BatchSaveFuncReq;
import com.org.permission.server.permission.entity.BasePermissionRoleFunc;
import com.org.permission.server.permission.vo.BasePermissionRoleFuncVo;
import com.common.util.message.RestMessage;
import com.org.permission.server.permission.service.IBasePermissionRoleFuncService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 角色权限关联表管理
 */
@Api(tags = "1权限-角色权限关联表管理接口文档")
@RequestMapping(value = "permission-role-func")
@RestController
public class BasePermissionRoleFuncController {
    @Autowired
    private IBasePermissionRoleFuncService basePermissionRoleFuncService;

    // 增加角色权限关联表
    @ApiOperation(value = "增加角色权限关联表")
    @PostMapping(value = "/addBasePermissionRoleFunc")
    public RestMessage addBasePermissionRoleFunc(@RequestBody BasePermissionRoleFunc basePermissionRoleFunc) {
        basePermissionRoleFunc.setCreatedDate(new Date());
        int result = basePermissionRoleFuncService.addBasePermissionRoleFunc(basePermissionRoleFunc);
        return RestMessage.doSuccess(result);
    }

    // 删除角色权限关联表
    @ApiOperation(value = "删除角色权限关联表")
    @GetMapping(value = "/delBasePermissionRoleFunc/{Id}")
    public RestMessage delBasePermissionRoleFunc(@PathVariable Integer Id) {
        int result = basePermissionRoleFuncService.delBasePermissionRoleFunc(Id);
        return RestMessage.doSuccess(result);
    }

    // 修改角色权限关联表
    @ApiOperation(value = "修改角色权限关联表")
    @PostMapping(value = "/updateBasePermissionRoleFunc")
    public RestMessage updateBasePermissionRoleFunc(@RequestBody BasePermissionRoleFunc basePermissionRoleFunc) {
        basePermissionRoleFunc.setModifiedDate(new Date());
        int result = basePermissionRoleFuncService.updateBasePermissionRoleFunc(basePermissionRoleFunc);
        return RestMessage.doSuccess(result);
    }

    // 分页查询角色权限关联表
    @ApiOperation(value = "分页查询角色权限关联表")
    @PostMapping(value = "/page/getListBasePermissionRoleFuncsByPage")
    public RestMessage getListBasePermissionRoleFuncsByPage(@RequestBody BasePermissionRoleFuncVo basePermissionRoleFuncVo) {
        PageHelper.startPage(basePermissionRoleFuncVo.getPageNum(), basePermissionRoleFuncVo.getPageSize());
        List result = basePermissionRoleFuncService.getListBasePermissionRoleFuncsByPage(basePermissionRoleFuncVo);
        PageInfo page = new PageInfo(result);
        return RestMessage.doSuccess(page);
    }

    // 不分页查询角色权限关联表
    @ApiOperation(value = "不分页查询角色权限关联表")
    @PostMapping(value = "/getListBasePermissionRoleFuncsByPOJO")
    public RestMessage getListBasePermissionRoleFuncsByPOJO(@RequestBody BasePermissionRoleFunc basePermissionRoleFunc) {
        List result = basePermissionRoleFuncService.getListBasePermissionRoleFuncsByPOJO(basePermissionRoleFunc);
        return RestMessage.doSuccess(result);
    }

    // 查询角色权限关联表
    @ApiOperation(value = "查询角色权限关联表")
    @GetMapping(value = "/getBasePermissionRoleFuncById/{Id}")
    public RestMessage getBasePermissionRoleFuncById(@PathVariable Integer id) {
        BasePermissionRoleFunc result = basePermissionRoleFuncService.getBasePermissionRoleFuncById(id);
        return RestMessage.doSuccess(result);
    }


    // 保存角色权限
    @ApiOperation(value = "保存角色权限")
    @PostMapping(value = "/batchSaveFunc")
    public RestMessage batchSaveFunc(@RequestBody BatchSaveFuncReq batchSaveFuncReq) {
        Assert.notNull(batchSaveFuncReq.getGroupId(), I18nUtils.getMessage("org.common.param.groupid.cannot.null"));
        Assert.notNull(batchSaveFuncReq.getRoleId(), I18nUtils.getMessage("permission.role.id.null"));
        Assert.notNull(batchSaveFuncReq.getAuthUserId(), I18nUtils.getMessage("org.common.param.userid.cannot.null"));
        basePermissionRoleFuncService.batchSaveFunc(batchSaveFuncReq);
        return RestMessage.doSuccess(null);
    }
}
