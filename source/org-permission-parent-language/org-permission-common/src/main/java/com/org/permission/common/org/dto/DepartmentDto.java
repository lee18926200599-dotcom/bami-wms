package com.org.permission.common.org.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 部门信息
 */
@ApiModel("部门信息")
@Data
public class DepartmentDto implements Serializable {
    private static final long serialVersionUID = 8421829489892598429L;
    /**
     * 主键
     */
    @ApiModelProperty("部门id")
    private Long id;


    /**
     * 部门编码
     */
    @ApiModelProperty("部门编码")
    private String orgCode;

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private String orgName;


    /**
     * 集团ID
     */
    @ApiModelProperty("集团ID")
    private Long groupId;

    /**
     * 上级部门id
     */
    @ApiModelProperty("上级部门id")
    private Long parentId;


    /**
     * 国家三位编码
     */
    @ApiModelProperty("国家三位编码")
    private String regionCode;


    private String regionName;

    /**
     * 省ID
     */
    @ApiModelProperty("省ID")
    private Long provinceCode;

    /**
     * 省名称
     */
    @ApiModelProperty("省名称")
    private String provinceName;

    /**
     * 市ID
     */
    @ApiModelProperty("市ID")
    private Long cityCode;

    /**
     * 市名称
     */
    @ApiModelProperty("市名称")
    private String cityName;

    /**
     * 区ID
     */
    @ApiModelProperty("区ID")
    private Long districtCode;

    /**
     * 区名称
     */
    @ApiModelProperty("区名称")
    private String districtName;

    /**
     * 街道ID
     */
    @ApiModelProperty("街道ID")
    private Long streetCode;

    /**
     * 街道名称
     */
    @ApiModelProperty("街道名称")
    private String streetName;

    /**
     * 地址
     */
    @ApiModelProperty("地址")
    private String address;


    /**
     * 部门负责人
     */
    @ApiModelProperty("部门负责人id")
    private Long depDutyStaff;

    /**
     * 部门负责人
     */
    @ApiModelProperty("部门负责人")
    private DeptDutyStaffDto deptDutyStaffDto;


    /**
     * 状态（1 未启用;2 启用;3 停用;4删除）
     */
    @ApiModelProperty("状态")
    private Integer state;


    /**
     * 简介
     */
    @ApiModelProperty("简介")
    private String remark;
}