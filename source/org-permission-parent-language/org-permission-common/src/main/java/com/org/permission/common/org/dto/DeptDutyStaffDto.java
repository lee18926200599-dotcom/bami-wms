package com.org.permission.common.org.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@ApiModel("部门负责人")
@Data
public class DeptDutyStaffDto implements Serializable {
    private static final long serialVersionUID = -55258373808209193L;
    /**
     * 自增主键
     */
    @ApiModelProperty("人员id")
    private Long id;

    /**
     * 数据来源
     */
    @ApiModelProperty("数据来源")
    private Integer registSource;

    /**
     * 部门ID
     */
    @ApiModelProperty("部门ID")
    private Long depId;

    /**
     * 业务单元ID
     */
    @ApiModelProperty("业务单元ID")
    private Long buId;

    /**
     * 集团ID
     */
    @ApiModelProperty("集团ID")
    private Long groupId;

    /**
     * 人员编码
     */
    @ApiModelProperty("人员编码")
    private String staffCode;

    /**
     * 类别业务编码（供业务线调用）
     */
    @ApiModelProperty("类别业务编码（供业务线调用）")
    private String bizCode;

    /**
     * 人员姓名
     */
    @ApiModelProperty("人员姓名")
    private String realname;

    /**
     * 直属上级
     */
    @ApiModelProperty("直属上级")
    private Integer directSupervisor;


    /**
     * 电话
     */
    @ApiModelProperty("电话")
    private String phone;

    /**
     * 邮件
     */
    @ApiModelProperty("邮件")
    private String email;


    /**
     * 用工性质（0 正式工;1临时工）
     */
    @ApiModelProperty("用工性质（0 正式工;1临时工）")
    private Integer employmentType;

    /**
     * 状态（1未启用;2启用;3停用;4删除）
     */
    @ApiModelProperty("状态（1未启用;2启用;3停用;4删除）")
    private Integer state;

    /**
     * 用户Id
     */
    @ApiModelProperty("用户Id")
    private Long userId;
}