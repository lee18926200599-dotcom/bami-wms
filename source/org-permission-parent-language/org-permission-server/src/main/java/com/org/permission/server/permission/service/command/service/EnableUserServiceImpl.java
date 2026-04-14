package com.org.permission.server.permission.service.command.service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.*;
import com.org.permission.server.permission.entity.BasePermissionUserRole;
import com.org.permission.server.permission.service.command.dto.PermissionDto;
import com.org.permission.server.permission.service.command.enums.PermissionCommandEnum;
import com.org.permission.server.permission.service.command.interfaces.PermissionStrategyService;
import com.org.permission.server.permission.entity.BasePermissionUserFunc;
import com.org.permission.server.permission.mapper.BasePermissionUserFuncMapper;
import com.org.permission.server.permission.mapper.BasePermissionUserRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * 启用用户，启用该集团下用户权限
 * base_permission_user_func 启用用户特殊权限
 * base_permission_user_role 启用用户角色权限
 */
@Service("enableUserServiceImpl")
public class EnableUserServiceImpl implements PermissionStrategyService {
    private final static Logger LOG = LoggerFactory.getLogger(EnableUserServiceImpl.class);
    final static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(80));

    @Autowired
    private BasePermissionUserFuncMapper basePermissionUserFuncMapper;
    @Autowired
    private BasePermissionUserRoleMapper basePermissionUserRoleMapper;

    @Override
    public boolean optionPermission(PermissionDto permissionDto) {
        ListenableFuture enableUserFunc = service.submit(new Callable<Map<String, Object>>() {
            public Map<String, Object> call() throws InterruptedException {
                long begin = System.currentTimeMillis();
                Map<String, Object> map = Maps.newHashMap(ImmutableMap.of("user", 0));
                BasePermissionUserFunc basePermissionUserFunc = new BasePermissionUserFunc();
                basePermissionUserFunc.setModifiedDate(new Date());
                basePermissionUserFunc.setModifiedBy(permissionDto.getLoginUserId());
                basePermissionUserFunc.setUserId(permissionDto.getUserId());
                basePermissionUserFunc.setGroupId(permissionDto.getGroupId());
                basePermissionUserFunc.setState(PermissionCommandEnum.ENABLE.getId());
                try {
                    basePermissionUserFuncMapper.updateBasePermissionUserFuncExtends(basePermissionUserFunc);
                    map.put("user", 1);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOG.error("启用base_permission_user_func用户权限失败,失败原因{}", e.getMessage());
                }
                long end = System.currentTimeMillis();
                LOG.info("启用base_permission_user_func用户权限成功,耗时-->" + (end - begin));
                return map;
            }
        });
        ListenableFuture enableUserRole = service.submit(new Callable<Map<String, Object>>() {
            public Map<String, Object> call() throws InterruptedException {
                long begin = System.currentTimeMillis();
                Map<String, Object> map = Maps.newHashMap(ImmutableMap.of("role", 0));
                BasePermissionUserRole basePermissionUserRole = new BasePermissionUserRole();
                basePermissionUserRole.setModifiedDate(new Date());
                basePermissionUserRole.setModifiedBy(permissionDto.getLoginUserId());
                basePermissionUserRole.setUserId(permissionDto.getUserId());
                basePermissionUserRole.setGroupId(permissionDto.getGroupId());
                basePermissionUserRole.setState(PermissionCommandEnum.ENABLE.getId());
                try {
                    basePermissionUserRoleMapper.updateBasePermissionUserRoleExtends(basePermissionUserRole);
                    map.put("role", 1);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOG.error("启用base_permission_user_role用户权限失败,失败原因{}", e.getMessage());
                }
                long end = System.currentTimeMillis();
                LOG.info("启用base_permission_user_role用户权限成功,耗时-->" + (end - begin));
                return map;
            }
        });


        final ListenableFuture allEnable = Futures.allAsList(enableUserFunc, enableUserRole);

        final ListenableFuture transformEnable = Futures.transformAsync(allEnable, new AsyncFunction<List, Boolean>() {
            @Override
            public ListenableFuture apply(List results) throws Exception {
                boolean flag = false;
                Map enableUserResult = (Map<String, Object>) results.get(0);
                int userResult = (int) enableUserResult.get("user");
                Map enableRoleResult = (Map<String, Object>) results.get(1);
                int roleResult = (int) enableRoleResult.get("role");
                int enableResult = userResult + roleResult;
                if (enableResult == 2) {
                    LOG.info("启用用户成功");
                    flag = true;
                }
                return Futures.immediateFuture(flag);
            }
        });
        try {
            LOG.info("启用用户完毕");
            return (boolean) transformEnable.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }
}
