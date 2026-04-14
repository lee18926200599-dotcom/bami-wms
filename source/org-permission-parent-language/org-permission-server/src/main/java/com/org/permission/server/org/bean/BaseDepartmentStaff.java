package com.org.permission.server.org.bean;

import com.org.permission.server.org.enums.EmploymentTypeEnum;
import com.org.permission.common.bean.BaseBean;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 人员实体
 * base_department_staff
 */
@Data
public class BaseDepartmentStaff extends BaseBean implements Serializable {


    /**
     * 数据来源
     */
    private Integer registSource;

    /**
     * 部门ID
     */
    private Long depId;

    /**
     * 业务单元ID
     */
    private Long buId;

    /**
     * 集团ID
     */
    private Long groupId;

    /**
     * 人员编码
     */
    private String staffCode;

    /**
     * 类别业务编码（供业务线调用）
     */
    private String bizCode;

    /**
     * 人员姓名
     */
    private String realname;

    /**
     * 直属上级
     */
    private Long directSupervisor;

    /**
     * 性别（0男;1女）
     */
    private Integer sex;

    /**
     * 证件类型
     */
    private Integer certificateType;

    /**
     * 证件号码
     */
    private String certificateNo;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮件
     */
    private String email;

    /**
     * 省ID
     */
    private Long provinceCode;

    /**
     * 省名称
     */
    private String provinceName;

    /**
     * 市ID
     */
    private Long cityCode;

    /**
     * 市名称
     */
    private String cityName;

    /**
     * 区ID
     */
    private Long districtCode;

    /**
     * 区名称
     */
    private String districtName;

    /**
     * 街道ID
     */
    private Long streetCode;

    /**
     * 街道名称
     */
    private String streetName;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 用工性质
     * {@link EmploymentTypeEnum}
     */
    private Integer employmentType;

    /**
     * 状态（1未启用;2启用;3停用;4删除）
     */
    private Integer state;

    /**
     * 用户Id
     */
    private Long userId;


}