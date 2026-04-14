package com.org.permission.server.org.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
/**
 * comments:人员任职表实体类型
 * create date:2023-09-25 15:42:38
 */
@Data
@TableName(value = "base_staff_duty")
public class BaseStaffDuty {
    //
    
    @TableId(value = "id")
    
    private Long id;
    //状态（1未启用；2启用；3停用）
    @TableField(value = "state")
    private Integer state;
    //人员ID
    @TableField(value = "staff_id")
    private Long staffId;
    //集团ID
    @TableField(value = "group_id")
    private Long groupId;
    //业务单元ID
    @TableField(value = "org_id")
    private Long orgId;
    //部门ID
    @TableField(value = "dep_id")
    private Long depId;
    //开始时间
    @TableField(value = "start_date")
    private String startDate;
    //结束时间
    @TableField(value = "end_date")
    private String endDate;
    //人员类别ID
    @TableField(value = "staff_type_id")
    private String staffTypeId;
    //人员类别业务编码
    @TableField(value = "biz_code")
    private String bizCode;
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
