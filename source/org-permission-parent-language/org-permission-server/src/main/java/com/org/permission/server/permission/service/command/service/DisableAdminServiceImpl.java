package com.org.permission.server.permission.service.command.service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.*;
import com.org.permission.server.permission.entity.BasePermissionAdminGroup;
import com.org.permission.server.permission.entity.BasePermissionUserRole;
import com.org.permission.server.permission.entity.BasePermissionUserFunc;
import com.org.permission.server.permission.mapper.BasePermissionAdminGroupMapper;
import com.org.permission.server.permission.mapper.BasePermissionUserFuncMapper;
import com.org.permission.server.permission.mapper.BasePermissionUserRoleMapper;
import com.org.permission.server.permission.service.command.dto.PermissionDto;
import com.org.permission.server.permission.service.command.enums.PermissionCommandEnum;
import com.org.permission.server.permission.service.command.interfaces.PermissionStrategyService;
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
 * 停用管理员，管理员相关权限全部停用
 * base_permission_admin_group 取集团权限 停用
 * base_permission_user_func 取用户权限 停用
 * base_permission_user_role 取角色权限 停用
 */
@Service("disableAdminServiceImpl")
public class DisableAdminServiceImpl implements PermissionStrategyService {
    private final static Logger LOG = LoggerFactory.getLogger(DisableAdminServiceImpl.class);
    final static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(80));
    @Autowired
    private BasePermissionAdminGroupMapper basePermissionAdminGroupMapper;
    @Autowired
    private BasePermissionUserFuncMapper basePermissionUserFuncMapper;
    @Autowired
    private BasePermissionUserRoleMapper basePermissionUserRoleMapper;

    @Override
    public boolean optionPermission(PermissionDto permissionDto) {
        ListenableFuture stopAdminGroup = service.submit(new Callable<Map<String, Object>>() {
            public Map<String, Object> call() throws InterruptedException {
                long begin = System.currentTimeMillis();
                Map<String, Object> map = Maps.newHashMap(ImmutableMap.of("admin", 0));
                BasePermissionAdminGroup basePermissionAdminGroup = new BasePermissionAdminGroup();
                basePermissionAdminGroup.setAdminId(permissionDto.getUserId());
                basePermissionAdminGroup.setModifiedBy(permissionDto.getLoginUserId());
                basePermissionAdminGroup.setModifiedDate(new Date());
                basePermissionAdminGroup.setState(PermissionCommandEnum.DISABLE.getId());
                try {
                    basePermissionAdminGroupMapper.updateBasePermissionAdminGroupByAdminId(basePermissionAdminGroup);
                    map.put("admin", 1);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOG.error("停止base_permission_admin_group管理员权限失败,失败原因{}", e.getMessage());
                }
                long end = System.currentTimeMillis();
                LOG.info("停止base_permission_admin_group管理员权限成功,耗时-->" + (end - begin));
                return map;
            }
        });
        ListenableFuture stopUserFunc = service.submit(new Callable<Map<String, Object>>() {
            public Map<String, Object> call() throws InterruptedException {
                long begin = System.currentTimeMillis();
                Map<String, Object> map = Maps.newHashMap(ImmutableMap.of("user", 0));
                BasePermissionUserFunc basePermissionUserFunc = new BasePermissionUserFunc();
                basePermissionUserFunc.setModifiedDate(new Date());
                basePermissionUserFunc.setModifiedBy(permissionDto.getLoginUserId());
                basePermissionUserFunc.setUserId(permissionDto.getUserId());
                basePermissionUserFunc.setState(PermissionCommandEnum.DISABLE.getId());
                try {
                    basePermissionUserFuncMapper.updateBasePermissionUserFuncExtends(basePermissionUserFunc);
                    map.put("user", 1);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOG.error("停止base_permission_user_func管理员权限失败,失败原因{}", e.getMessage());
                }
                long end = System.currentTimeMillis();
                LOG.info("停止base_permission_user_func管理员权限成功,耗时-->" + (end - begin));
                return map;
            }
        });
        ListenableFuture stopUserRole = service.submit(new Callable<Map<String, Object>>() {
            public Map<String, Object> call() throws InterruptedException {
                long begin = System.currentTimeMillis();
                Map<String, Object> map = Maps.newHashMap(ImmutableMap.of("role", 0));
                BasePermissionUserRole basePermissionUserRole = new BasePermissionUserRole();
                basePermissionUserRole.setModifiedDate(new Date());
                basePermissionUserRole.setModifiedBy(permissionDto.getLoginUserId());
                basePermissionUserRole.setUserId(permissionDto.getUserId());
                basePermissionUserRole.setState(PermissionCommandEnum.DISABLE.getId());
                try {
                    basePermissionUserRoleMapper.updateBasePermissionUserRoleExtends(basePermissionUserRole);
                    map.put("role", 1);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOG.error("停止base_permission_user_role管理员权限失败,失败原因{}", e.getMessage());
                }
                long end = System.currentTimeMillis();
                LOG.info("停止base_permission_user_role管理员权限成功,耗时-->" + (end - begin));
                return map;
            }
        });


        final ListenableFuture allStop = Futures.allAsList(stopAdminGroup, stopUserFunc, stopUserRole);

        final ListenableFuture transformStop = Futures.transformAsync(allStop, new AsyncFunction<List, Boolean>() {
            @Override
            public ListenableFuture apply(List results) throws Exception {
                boolean flag = false;
                Map stopAdminResult = (Map<String, Object>) results.get(0);
                int adminResult = (int) stopAdminResult.get("admin");
                Map stopUserResult = (Map<String, Object>) results.get(1);
                int userResult = (int) stopUserResult.get("user");
                Map stopRoleResult = (Map<String, Object>) results.get(2);
                int roleResult = (int) stopRoleResult.get("role");
                int result = adminResult + userResult + roleResult;
                if (result == 3) {
                    flag = true;
                }
                return Futures.immediateFuture(flag);
            }
        });
        try {
            LOG.info("停用管理员完毕");
            return (boolean) transformStop.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }


}
