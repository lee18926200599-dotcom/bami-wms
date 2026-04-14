package com.common.bms.mybatis;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.common.base.entity.CurrentUser;
import com.common.framework.user.FplUserUtil;
import lombok.SneakyThrows;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;


public class MultiTenantHandler implements TenantLineHandler {
    private final TenantProperties properties;

    public MultiTenantHandler(TenantProperties properties) {
        this.properties = properties;
    }

    /**
     * 获取租户ID值表达式，只支持单个ID值 (实际应该从用户信息中获取)
     *
     * @return 租户ID值表达式
     */
    @Override
    public Expression getTenantId() {
        Long groupId = null;
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        if (ObjectUtil.isNotNull(currentUser)) {
            groupId = currentUser.getGroupId();
        }
        if (ObjectUtil.isNull(groupId)) {
            CurrentUser user = FplUserUtil.getValue();
            if (ObjectUtil.isNotNull(user)) {
                groupId = user.getGroupId();
            }
        }
        return ObjectUtil.isNotNull(groupId) ? new LongValue(groupId) : null;
    }

    /**
     * 获取租户字段名,默认字段名叫: tenant_id
     *
     * @return 租户字段名
     */
    @Override
    public String getTenantIdColumn() {
        return properties.getColumn();
    }

    /**
     * 根据表名判断是否忽略拼接多租户条件
     * <p>
     * 默认都要进行解析并拼接多租户条件
     *
     * @param tableName 表名
     * @return 是否忽略, true:表示忽略，false:需要解析并拼接多租户条件
     */
    @SneakyThrows
    @Override
    public boolean ignoreTable(String tableName) {

        List<String> ignoreTableNames = properties.getIgnoreTables();
        if (CollectionUtils.isNotEmpty(ignoreTableNames) && ignoreTableNames.contains(tableName)) {
            return true;
        }
        //没有配置租户id
        String tenantIdName = properties.getColumn();
        if (StringUtils.isEmpty(tenantIdName)) {
            return true;
        }
        //没有用户信息也不校验租户信息
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        CurrentUser user = FplUserUtil.getValue();
        if (ObjectUtil.isNull(currentUser) && ObjectUtil.isNull(user)) {
            return true;
        }
        //如果租户id没有值也不启用租户过滤
        Object tenantIdValue = null;
        if (ObjectUtil.isNotNull(currentUser)) {
            tenantIdValue = getTenantValue(currentUser, tenantIdName);
        }
        if (ObjectUtil.isNull(tenantIdValue) && ObjectUtil.isNotNull(user)) {
            tenantIdValue = getTenantValue(user, tenantIdName);
        }
        if (ObjectUtil.isNull(tenantIdValue)) {
            return true;
        }
        return false;

    }

    private Object getTenantValue(CurrentUser user, String tenantIdName) throws Exception {
        Class<? extends CurrentUser> clazz = CurrentUser.class;
        PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
        Method method = Stream.of(propertyDescriptors).filter(property -> StringUtils.equals(StrUtil.toCamelCase(tenantIdName), property.getName())).
                map(PropertyDescriptor::getReadMethod).findFirst().orElse(null);
        if (ObjectUtil.isNotNull(method)) {
            return method.invoke(user);
        }
        return null;
    }
}
