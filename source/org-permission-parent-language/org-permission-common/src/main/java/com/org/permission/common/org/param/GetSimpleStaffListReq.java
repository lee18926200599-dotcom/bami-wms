package com.org.permission.common.org.param;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 人员简要信息请求
 */
@ApiModel("人员简要信息请求")
@Data
public class GetSimpleStaffListReq extends BaseQuery {

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
    @ApiModelProperty(value = "人员编码 模糊查询")
    private String staffCode;

    /**
     * 人员姓名
     */
    @ApiModelProperty(value = "人员姓名 模糊查询")
    private String realname;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话 模糊查询")
    private String phone;

    /**
     * 邮件
     */
    @ApiModelProperty(value = "邮件 模糊查询")
    private String email;


    /**
     * 用工性质（0 正式工;1临时工）
     */
    @ApiModelProperty(value = "用工性质（0 正式工;1临时工）")
    private Integer employmentType;

    /**
     * 状态（1未启用;2启用;3停用;4删除）
     */
    @ApiModelProperty(value = "状态（1未启用;2启用;3停用;4删除） 如果不传默认查询2启用的")
    private Integer state;
}
