package com.usercenter.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* 系统映射关系表
*/
@Data
@TableName(value = "base_user_business_system_map")
public class BaseUserBusinessSystemMap implements Serializable {
   @TableField(value = "id")
   private Integer id;
   //映射类型：1映射身份，2映射用户体系
   @TableField(value = "map_type")
   private Integer mapType;
   //系统id
   @TableField(value = "business_system_id")
   private Integer businessSystemId;
   //系统名称
   @TableField(value = "business_system_name")
   private String businessSystemName;
   //子系统id
   @TableField(value = "sub_business_system_id")
   private Integer subBusinessSystemId;
   //子系统名称
   @TableField(value = "sub_business_system_name")
   private String subBusinessSystemName;
   //映射id
   @TableField(value = "map_id")
   private Integer mapId;
   //映射名称
   @TableField(value = "map_name")
   private String mapName;
   //扩展字段，自定义业务状态
   @TableField(value = "state")
   private String state;
   //创建日期
   @TableField(value = "created_date")
   private Date createdDate;
   //修改时间
   @TableField(value = "modified_date")
   private Date modifiedDate;

}

