package com.permission.client.feign;


import com.common.util.message.RestMessage;
import com.org.permission.common.permission.dto.AdminGroupDto;
import com.org.permission.common.permission.dto.UserAllPermissionDto;
import com.org.permission.common.permission.dto.UserPermission;
import com.org.permission.common.permission.param.MenuButtonPermissionParam;
import com.org.permission.common.permission.param.PermissionUserParam;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.common.util.Constant;


import com.usercenter.common.dto.FplUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 根据用户获取权限接口， 根据产品要求，先根据用户获取用户菜单级的权限树，再点击菜单时， 根据用户和菜单id获取菜单下所有的权限
 */
@FeignClient(value = Constant.ORG_SERVER_NAME)
public interface PermissionFeign {
    /**
     * 登录接口使用，根据用户id，获取用户的 功能权限（菜单级）、组织权限、数据权限
     *
     * @param param userId   用户id
     *              groupId  集团id
     *              domainId 域名id
     *              source   登录方式  PC、APP 大写英文
     *              user     登录后的用户对象
     * @return
     */
    @PostMapping(value = "/permission-user/getPermissionByUid")
    RestMessage<UserAllPermissionDto> getPermissionByUid(@RequestBody PermissionUserParam param);

    /**
     * 切换集团登录传参
     *
     * @param param userId   用户id
     *              groupId  集团id
     *              domainId 域名id
     *              source   登录方式  PC、APP 大写英文
     *              user     登录后的用户对象
     * @return
     */
    @PostMapping(value = "/permission-user/getPermissonsByToken")
    RestMessage<UserAllPermissionDto> getPermissonsByToken(@RequestBody PermissionUserParam param);

    /**
     * 登录后，有需要重新查询用户菜单权限使用
     *
     * @param userId   用户id
     * @param groupId  集团id
     * @param domainId 域名id
     * @return
     */
    @PostMapping(value = "/permission-user/getMenus")
    RestMessage<Map<String, Object>> getMenus(@RequestParam(value = "userId") Long userId, @RequestParam(value = "groupId") Long groupId, @RequestParam(value = "domainId") Integer domainId);

    /**
     * 根据登录返回的菜单id，点击菜单时，再获取菜单下的子功能权限
     *
     * @param param userId  用户id
     *              groupId 集团id
     *              menuId  点击菜单id
     */
    @PostMapping(value = "/permission-user/getPermissonsByMenuIdAndUid")
    RestMessage<List<UserPermission>> getPermissonsByMenuIdAndUid(@RequestBody MenuButtonPermissionParam param);

    /**
     * 根据用户id获取父级下的子菜单权限
     *
     * @param userId   用户id
     * @param groupId  集团id
     * @param parentId 父级id
     * @return
     */
    @PostMapping(value = "/permission-user/getPermissonsByParentIdAndUid")
    RestMessage<List<UserPermission>> getPermissonsByParentIdAndUid(@RequestParam(value = "userId") Long userId, @RequestParam(value = "groupId") Long groupId, @RequestParam(value = "parentId") Integer parentId);


    /**
     * 按照权限id和uri后台校验用户是否有权限
     *
     * @param userId       用户id
     * @param groupId      集团id
     * @param token        登录返回的token
     * @param permissionId 权限id
     * @param uri          数据库初始化配置的url
     * @return
     */
    @PostMapping(value = "/permission-user/checkPermissionById")
    RestMessage checkPermissionById(@RequestParam(value = "userId") Long userId, @RequestParam(value = "groupId") Long groupId, @RequestParam(value = "token") String token, @RequestParam(value = "permissionId") Integer permissionId, @RequestParam(value = "uri") String uri);


    /**
     * @param userId
     * @return
     */
    @PostMapping(value = "/permission-user/getGroupsById")
    RestMessage<List<AdminGroupDto>> getGroupsById(@RequestParam(value = "userId") Long userId);

    /**
     * 批量获取集团管理员集团列表
     *
     * @param query ids
     * @return
     */
    @PostMapping(value = "/permission-admin/getGroupsByIdSet")
    RestMessage<List<AdminGroupDto>> getGroupsByIdSet(@RequestBody BatchQueryParam query);

    /**
     * @param user(必须的 userid,groupId,managerLevel)
     * @return
     */
    @PostMapping(value = "/permission-user/getOrgPermissions")
    RestMessage<Set<Long>> getOrgPermissions(@RequestBody(required = true) FplUser user);

    /**
     * 批量获取用户跨组织权限
     *
     * @param userIdSet
     * @return
     */
    @PostMapping(value = "/permission-user/batchGetOrgPermissions")
    RestMessage<Set<Long>> batchGetOrgPermissions(@RequestParam(value = "userIdSet") Set<Long> userIdSet);

    /**
     * 根据用户id和集团id获取用户的组织权限
     *
     * @param userId
     * @param groupId
     * @return
     */
    @PostMapping(value = "/permission-user/getOrgsByUidAndGroupId")
    RestMessage<Set<Long>> getOrgsByUidAndGroupId(@RequestParam(value = "userId") Long userId, @RequestParam(value = "groupId") Long groupId);

    /**
     * 查询用户组织权限，(包含管理员相关配置)
     * @param param
     * @return
     */
    @PostMapping(value = "/permission-user/getOrgIdListByUidAndGroupId")
    RestMessage<Set<Long>> getOrgIdListByUidAndGroupId(@RequestBody PermissionUserParam param);
}
