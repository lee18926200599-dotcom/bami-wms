package com.common.framework.filter;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.common.base.entity.CurrentUser;
import com.common.framework.user.FplUserUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class FplFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {

        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        if (ObjectUtil.isNotNull(currentUser)) {
            log.info("当前登录用户的信息为:{}", JSON.toJSONString(Optional.ofNullable(currentUser)));
            Long warehouseId = currentUser.getWarehouseId();
            if (ObjectUtil.isNull(warehouseId)) {
                if (ObjectUtil.isNotNull(FplUserUtil.getValue())) {
                    warehouseId = FplUserUtil.getValue().getWarehouseId();
                }
            }
            Long groupId = currentUser.getGroupId();
            if (ObjectUtil.isNull(groupId)) {
                if (ObjectUtil.isNotNull(FplUserUtil.getValue())) {
                    groupId = FplUserUtil.getValue().getGroupId();
                }
            }
            if (ObjectUtil.isNotNull(warehouseId)) {
                requestTemplate.header("Warehouseid", new String[]{String.valueOf(warehouseId)});
            }
            if (ObjectUtil.isNotNull(groupId)) {
                requestTemplate.header("Groupid", new String[]{String.valueOf(groupId)});
            }
        }
//        FplUserUtil.remove();
    }
}
