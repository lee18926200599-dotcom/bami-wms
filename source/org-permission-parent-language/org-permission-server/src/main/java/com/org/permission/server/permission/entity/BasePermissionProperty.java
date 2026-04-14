package com.org.permission.server.permission.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * base_permission_property实体类  权限体系配置表（域名首页url配置在这里）管理
 */
@Data
@TableName(value = "base_permission_property")
public class BasePermissionProperty implements Serializable {
    public String comment = "权限体系配置表（域名首页url配置在这里）";
    public LinkedHashMap<String, HashMap> commentMap = new LinkedHashMap<String, HashMap>();
    
    @TableId(value = "id")
    private Long id;
    //配置key
    @TableField(value = "name")
    private String name;
    //配置value
    @TableField(value = "property_value")
    private String propertyValue;
    //创建人id
    @TableField(value = "created_by")
    private Long createdBy;
    //创建人
    @TableField(value = "created_name")
    private String createdName;
    //创建日期
    @TableField(value = "created_date")
    private Date createdDate;
    //修改人id
    @TableField(value = "modified_by")
    private Long modifiedBy;
    //修改人
    @TableField(value = "modified_name")
    private String modifiedName;
    //修改时间
    @TableField(value = "modified_date")
    private Date modifiedDate;
}

