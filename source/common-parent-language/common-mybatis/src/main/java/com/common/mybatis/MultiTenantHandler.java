package com.common.mybatis;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.common.base.entity.CurrentUser;
import com.common.framework.user.FplUserUtil;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;

import java.util.List;

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
        if (FplUserUtil.getCurrentUser() != null){
            Long warehouseId = FplUserUtil.getCurrentUser().getWarehouseId();
            if (warehouseId != null){
                return new LongValue(warehouseId);
            }
        }else {
            CurrentUser currentUser = FplUserUtil.getValue();
            if(null != currentUser.getWarehouseId()){
                return new LongValue(currentUser.getWarehouseId());
            }
        }
        return null;
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
    @Override
    public boolean ignoreTable(String tableName) {

        //忽略指定用户对租户的数据过滤
        List<String> ignoreLoginNames = properties.getIgnoreLoginNames();
       /* String loginName = SecurityUtils.getTenantUsername();
        if (null != ignoreLoginNames && ignoreLoginNames.contains(loginName)) {
            return true;
        }*/

        //忽略指定表对租户数据的过滤
        List<String> ignoreTables = properties.getIgnoreTables();
        if (null != ignoreTables && ignoreTables.contains(tableName)) {
            return true;
        }

        if ((FplUserUtil.getCurrentUser() == null || FplUserUtil.getCurrentUser().getWarehouseId() == null ) && FplUserUtil.getValue() == null){
            return true;
        }
        return false;

    }
}
