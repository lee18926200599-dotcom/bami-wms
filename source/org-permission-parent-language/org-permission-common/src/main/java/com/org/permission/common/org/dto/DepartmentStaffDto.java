package com.org.permission.common.org.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 人员实体
 */
@ApiModel("人员简要信息")
@Data
public class DepartmentStaffDto implements Serializable {
    private static final long serialVersionUID = -6702998700845131054L;
    /**
     * 自增主键
     */
    @ApiModelProperty(value = "自增主键")
    private Integer id;

    /**
     * 数据来源
     */
    @ApiModelProperty(value = "数据来源")
    private Integer registSource;

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID")
    private Long depId;

    /**
     * 业务单元ID
     */
    @ApiModelProperty(value = "业务单元ID")
    private Long buId;

    /**
     * 集团ID
     */
    @ApiModelProperty(value = "集团ID")
    private Long groupId;

    /**
     * 人员编码
     */
    @ApiModelProperty(value = "人员编码")
    private String staffCode;

    /**
     * 类别业务编码（供业务线调用）
     */
    @ApiModelProperty(value = "类别业务编码（供业务线调用）")
    private String bizCode;

    /**
     * 人员姓名
     */
    @ApiModelProperty(value = "人员姓名")
    private String realname;

    /**
     * 直属上级
     */
    @ApiModelProperty(value = "直属上级")
    private Integer directSupervisor;

    /**
     * 性别（0男;1女）
     */
    @ApiModelProperty(value = "性别（0男;1女）")
    private Integer sex;

    /**
     * 证件类型
     */
    @ApiModelProperty(value = "证件类型")
    private Integer certificateType;

    /**
     * 证件号码
     */
    @ApiModelProperty(value = "证件号码")
    private String certificateNo;

    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    private Date birthday;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String phone;

    /**
     * 邮件
     */
    @ApiModelProperty(value = "邮件")
    private String email;

    /**
     * 省ID
     */
    @ApiModelProperty(value = "省ID")
    private Long provinceCode;

    /**
     * 省名称
     */
    @ApiModelProperty(value = "省名称")
    private String provinceName;

    /**
     * 市ID
     */
    @ApiModelProperty(value = "市ID")
    private Long cityCode;

    /**
     * 市名称
     */
    @ApiModelProperty(value = "市名称")
    private String cityName;

    /**
     * 区ID
     */
    @ApiModelProperty(value = "区ID")
    private Long districtCode;

    /**
     * 区名称
     */
    @ApiModelProperty(value = "区名称")
    private String districtName;

    /**
     * 街道ID
     */
    @ApiModelProperty(value = "街道ID")
    private Long streetCode;

    /**
     * 街道名称
     */
    @ApiModelProperty(value = "街道名称")
    private String streetName;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    private String address;

    /**
     * 用工性质（0 正式工;1临时工）
     */
    @ApiModelProperty(value = "用工性质（0 正式工;1临时工）")
    private Integer employmentType;

    /**
     * 状态（1未启用;2启用;3停用;4删除）
     */
    @ApiModelProperty(value = "状态（1未启用;2启用;3停用;4删除）")
    private Integer state;

    /**
     * 用户Id
     */
    @ApiModelProperty(value = "用户Id")
    private Long userId;
}