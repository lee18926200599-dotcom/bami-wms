package com.permission.client.feign;

import com.common.util.message.RestMessage;
import com.org.permission.common.permission.dto.GetUserMenuPermissionsDto;
import com.org.permission.common.permission.dto.OrgDto;
import com.org.permission.common.permission.dto.RoleUser;
import com.org.permission.common.permission.param.UserMenuParam;
import com.org.permission.common.util.Constant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 权限对外接口
 */
@FeignClient(value = Constant.ORG_SERVER_NAME)
public interface PermissionManagerFeign {

    /**
     * 根据用户获取组织权限下的角色树
     *
     * @param loginUserId    登录人id（必传）
     * @param userId
     * @param groupId        集团id（必传）
     * @param roleName
     * @param orgName
     * @param chooseRoleName
     * @param chooseOrgName
     * @return
     */
    @PostMapping(value = "/permission-user-role/getRoleTree")
    RestMessage<List<OrgDto>> getRoleTree(@RequestParam(value = "loginUserId") Long loginUserId, @RequestParam(value = "userId") Long userId, @RequestParam(value = "groupId") Long groupId, @RequestParam(required = false, value = "roleName") String roleName, @RequestParam(required = false, value = "orgName") String orgName, @RequestParam(required = false, value = "chooseRoleName") String chooseRoleName, @RequestParam(required = false, value = "chooseOrgName") String chooseOrgName);

    /**
     * 获取用户组织权限下的用户树
     *
     * @param userId   用户id（必传）
     * @param groupId  集团id （必传）
     * @param roleId   角色id
     * @param userName 用户名
     * @param orgName  组织名
     * @return
     */
    @GetMapping(value = "/permission-user-role/getUserTree")
    RestMessage<List<OrgDto>> getUserTree(@RequestParam(value = "userId") Long userId, @RequestParam(value = "groupId") Long groupId, @RequestParam(value = "roleId") Integer roleId, @RequestParam(required = false, value = "userName") String userName, @RequestParam(required = false, value = "orgName") String orgName);


    /**
     * @param roleId  角色id(必传)
     * @param groupId 角色id(必传)
     * @return
     */
    @PostMapping(value = "/permission-role/getRoleUsers")
    RestMessage<List<RoleUser>> getRoleUsers(@RequestParam(value = "roleId") Integer roleId, @RequestParam(value = "groupId") Long groupId);

    /**
     * 批量获取用户所在集团的菜单权限
     *
     * @param userMenuParams
     * @return
     */
    @PostMapping(value = "/permission-menus/getUserMenuPermissions")
    RestMessage<List<GetUserMenuPermissionsDto>> getUserMenuPermissions(@RequestBody List<UserMenuParam> userMenuParams);


    /**
     * 根据权限id获取支持的客户业务类型
     *
     * @param permissionId 权限id
     * @return
     */
    @GetMapping(value = "/permission-type-resource/getBusinessTypesByPermissionId")
    RestMessage getBusinessTypesByPermissionId(@RequestParam("permissionId") Integer permissionId);
}
