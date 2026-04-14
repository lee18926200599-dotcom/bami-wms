package com.org.permission.server.permission.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.org.permission.server.permission.dto.GetUserMenuPermissionsResp;
import com.org.permission.server.permission.service.impl.GroupConfigHelper;
import com.common.util.message.RestMessage;
import com.org.permission.common.permission.param.UserMenuParam;
import com.org.permission.common.permission.dto.UserPermission;
import com.usercenter.common.dto.FplUser;
import com.usercenter.common.enums.SourceTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 菜单权限
 */
@Api(tags = "1权限-菜单权限接口文档")
@RequestMapping(value = "permission-menus")
@RestController
public class MenusPermissionController {
    private final static Logger LOG = LoggerFactory.getLogger(MenusPermissionController.class);
    @Autowired
    private GroupConfigHelper groupConfigHelper;

    @Resource
    private ThreadPoolTaskExecutor taskExecutor;


    @ApiOperation(value = "获取用户菜单权限")
    @PostMapping(value = "/getUserMenuPermissions")
    public RestMessage<List<GetUserMenuPermissionsResp>> getUserMenuPermissions(@RequestBody List<UserMenuParam> userMenuParams) {
        List<GetUserMenuPermissionsResp> result = Lists.newArrayList();
        LOG.info("调用用户菜单权限，查询个数：{}，传参:{}", userMenuParams.size(), JSON.toJSONString(userMenuParams));
        //多线程处理一个人不同集团菜单权限
        List<Future<GetUserMenuPermissionsResp>> futureList = new ArrayList<Future<GetUserMenuPermissionsResp>>();
        for (int i = 0; i < userMenuParams.size(); i++) {
            MeunPermissionCallable sunCallable = new MeunPermissionCallable(userMenuParams.get(i));
            futureList.add(taskExecutor.submit(sunCallable));
        }
        /**对各个线程段结果进行解析**/
        for (Future<GetUserMenuPermissionsResp> future : futureList) {
            try {
                result.add(future.get(1, TimeUnit.SECONDS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return RestMessage.doSuccess(result);
    }

    /**
     * 根据条件获取用户所在集团的菜单权限
     */
    class MeunPermissionCallable implements Callable {
        private UserMenuParam userMenuParam;

        public MeunPermissionCallable(UserMenuParam userMenuParam) {
            this.userMenuParam = userMenuParam;
        }

        @Override
        public GetUserMenuPermissionsResp call() {
            GetUserMenuPermissionsResp getUserMenuPermissionsResp = new GetUserMenuPermissionsResp();
            long begin = System.currentTimeMillis();
            FplUser user = new FplUser();
            user.setId(userMenuParam.getUserId());
            user.setManagerLevel(userMenuParam.getManagerLevel());
            SourceTypeEnum sourceTypeEnum = SourceTypeEnum.parser(userMenuParam.getSource());
            Integer sourceType=sourceTypeEnum==null?null:sourceTypeEnum.getCode();
            List<UserPermission> userPermissions = groupConfigHelper.getUserPermissionByStrategy(userMenuParam.getUserId(), userMenuParam.getGroupId(), "", sourceType, 0L, null, 0L, user);
            long end = System.currentTimeMillis();
            LOG.info("单个线程耗时：{}ms", (end - begin));
            getUserMenuPermissionsResp.setUserId(userMenuParam.getUserId());
            getUserMenuPermissionsResp.setGroupId(userMenuParam.getGroupId());
            getUserMenuPermissionsResp.setPermissions(userPermissions.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getPermissionId()))), ArrayList::new)));
            return getUserMenuPermissionsResp;
        }


    }

}
