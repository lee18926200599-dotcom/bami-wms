package com.common.bms.mybatis.config;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.common.base.entity.CurrentUser;
import com.common.framework.user.FplUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


/**
 * 填充当前时间
 */
@Slf4j
@Component
public class AuditMetaObjectConfig implements MetaObjectHandler {

    /**
     * 插入填充
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        this.setFieldValByName("createdBy", 0L, metaObject);
        this.setFieldValByName("createdName", "sys", metaObject);
        this.setFieldValByName("modifiedName", "sys", metaObject);
        if(null !=  currentUser){
            if(null != currentUser.getUserId()){
                this.setFieldValByName("createdBy", currentUser.getUserId(), metaObject);
            }
            if(StringUtils.isNotBlank(currentUser.getUserName())){
                this.setFieldValByName("createdName", currentUser.getUserName(), metaObject);
                this.setFieldValByName("modifiedName", currentUser.getUserName(), metaObject);
            }
            if (null == this.getFieldValByName("serviceProviderId", metaObject) &&  null != currentUser.getServiceProviderId() ) {
                this.setFieldValByName("serviceProviderId", currentUser.getServiceProviderId(), metaObject);
            }
            if (null == this.getFieldValByName("warehouseId", metaObject) && null != currentUser.getWarehouseId()) {
                this.setFieldValByName("warehouseId", currentUser.getWarehouseId(), metaObject);
            }
            if (null == this.getFieldValByName("warehouseName", metaObject) && StringUtils.isNotBlank(currentUser.getWarehouseCode())) {
                this.setFieldValByName("warehouseName", currentUser.getWarehouseName(), metaObject);
            }
            if (null == this.getFieldValByName("warehouseCode", metaObject) && StringUtils.isNotBlank(currentUser.getWarehouseName())) {
                this.setFieldValByName("warehouseCode", currentUser.getWarehouseCode(), metaObject);
            }


        }

        LocalDateTime now = LocalDateTime.now();
        this.setFieldValByName("createdDate", now, metaObject);
        this.setFieldValByName("deletedFlag", 0, metaObject);
        this.setFieldValByName("modifiedDate", now, metaObject);

    }

    /**
     * 修改填充
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        this.setFieldValByName("modifiedDate", now, metaObject);
        this.setFieldValByName("modifiedBy", 0L, metaObject);
        this.setFieldValByName("modifiedName", "sys", metaObject);
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        if(null !=  currentUser){
            if (null != currentUser.getUserId()) {
                this.setFieldValByName("modifiedBy", currentUser.getUserId(), metaObject);
            }
            if (StringUtils.isNotBlank(currentUser.getUserName())) {
                this.setFieldValByName("modifiedName", currentUser.getUserName(), metaObject);
            }

        }
    }
}
