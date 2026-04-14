package com.permission.client.feign;


import com.org.permission.common.permission.dto.InputAdminDto;
import com.common.util.message.RestMessage;
import com.org.permission.common.util.Constant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(value = Constant.ORG_SERVER_NAME)
public interface PermissionForUserFeign {

    /**
     * @param inputUserDto
     * @return
     */
    @PostMapping(value = "/permission-user-role/insertUser")
    RestMessage insertUserPermission(@RequestBody com.org.permission.common.permission.dto.InputUserDto inputUserDto);

    /**
     * @param inputUserUpdateDto
     * @return
     */
    @PostMapping(value = "/permission-user-role/updateUser")
    RestMessage<String> updateUserPermission(@RequestBody com.org.permission.common.permission.dto.InputUserUpdateDto inputUserUpdateDto);

    /**
     * @param inputAdminDto
     * @return
     */
    @PostMapping(value = "/permission-admin-group/insertAdminGroup")
    RestMessage insertAdminGroup(@RequestBody InputAdminDto inputAdminDto);

    /**
     *
     * @param inputAdminUpdateDto
     * @return
     */
    @PostMapping(value = "/permission-admin-group/updateAdminGroup")
    RestMessage updateAdminGroup(@RequestBody com.org.permission.common.permission.dto.InputAdminUpdateDto inputAdminUpdateDto);

    /**
     * 启用用户
     * 停用用户
     * 启用管理员
     * 停用管理员
     * 四种操作，权限的变化接口
     *
     * @param permissionDto
     * @return
     */
    @PostMapping(value = "/permission-for-user/optionUser")
    RestMessage optionUser(@RequestBody com.org.permission.common.permission.dto.PermissionDto permissionDto);
}
