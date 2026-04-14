package com.org.permission.server.org.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
/**
 * comments:人员表实体类型
 * create date:2023-09-25 15:40:56
 */
@Data
@TableName(value = "base_staff")
public class BaseStaff {
    //自增主键
    
    @TableId(value = "id")
    
    private Long id;
    //数据来源
    @TableField(value = "regist_source")
    private Integer registSource;
    //数据来源Id
    @TableField(value = "regist_source_id")
    private String registSourceId;
    //部门ID
    @TableField(value = "dep_id")
    private Long depId;
    //业务单元ID
    @TableField(value = "org_id")
    private Long orgId;
    //集团ID
    @TableField(value = "group_id")
    private Long groupId;
    //人员编码
    @TableField(value = "staff_code")
    private String staffCode;
    //类别业务编码（供业务线调用）
    @TableField(value = "biz_code")
    private String bizCode;
    //人员姓名
    @TableField(value = "realname")
    private String realname;
    //直属上级
    @TableField(value = "direct_supervisor")
    private Long directSupervisor;
    //性别（0男;1女）
    @TableField(value = "sex")
    private Integer sex;
    //证件类型
    @TableField(value = "certificate_type")
    private String certificateType;
    //证件号码
    @TableField(value = "certificate_no")
    private String certificateNo;
    //生日
    @TableField(value = "birthday")
    private String birthday;
    //电话
    @TableField(value = "phone")
    private String phone;
    //邮件
    @TableField(value = "email")
    private String email;
    //省code
    @TableField(value = "province_code")
    private Long provinceCode;
    //省名称
    @TableField(value = "province_name")
    private String provinceName;
    //市code
    @TableField(value = "city_code")
    private Long cityCode;
    //市名称
    @TableField(value = "city_name")
    private String cityName;
    //区code
    @TableField(value = "district_code")
    private Long districtCode;
    //区名称
    @TableField(value = "district_name")
    private String districtName;
    //街道code
    @TableField(value = "street_code")
    private Long streetCode;
    //街道名称
    @TableField(value = "street_name")
    private String streetName;
    //详细地址
    @TableField(value = "address")
    private String address;
    //用工性质（0 正式工;1临时工）
    @TableField(value = "employment_type")
    private String employmentType;
    //状态
    @TableField(value = "state")
    private Integer state;
    //用户Id
    @TableField(value = "user_id")
    private Long userId;
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

    @TableField(value = "deleted_flag")
    private Integer deletedFlag;
}
