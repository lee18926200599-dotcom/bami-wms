package com.org.permission.server.org.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
/**
 * comments:人员类别表实体类型
 * create date:2023-09-25 15:52:22
 */
@Data
@TableName(value = "base_staff_type")
public class BaseStaffType {
    //自增主键
    
    @TableId(value = "id")
    
    private Long id;
    //1 全局;2 集团
    @TableField(value = "type_level")
    private Integer typeLevel;
    //所属组织(集团级)
    @TableField(value = "belong_org")
    private Long belongOrg;
    //上级类别
    @TableField(value = "parent_id")
    private Long parentId;
    //类别业务编码（供业务线调用）
    @TableField(value = "biz_code")
    private String bizCode;
    //类别编码（无用，唯一）
    @TableField(value = "type_code")
    private String typeCode;
    //类别名称
    @TableField(value = "type_name")
    private String typeName;
    //状态
    @TableField(value = "state")
    private Integer state;
    @TableField(value = "deleted_flag")
    private Integer deletedFlag;
    //简介
    @TableField(value = "remark")
    private String remark;
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
