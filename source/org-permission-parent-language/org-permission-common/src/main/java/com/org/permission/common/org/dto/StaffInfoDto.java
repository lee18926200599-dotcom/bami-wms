package com.org.permission.common.org.dto;

import com.org.permission.common.org.vo.BaseInfoVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 员工信息
 */
@ApiModel
@Data
public class StaffInfoDto extends BaseInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 注册来源
     */
    private Integer registSource;

    /**
     * 注册来源Id
     */
    private String registSourceId;
    /**
     * 部门ID
     */
    private Long depId;
    /**
     * 部门名
     */
    private String depName;
    /**
     * 业务单元ID
     */
    private Long orgId;
    /**
     * 业务单元名
     */
    private String orgName;

    private Long groupId;
    /**
     * 人员编码
     */
    private String staffCode;
    /**
     * 姓名
     */
    private String realname;
    /**
     * 人员类别ID
     */
    private Integer staffTypeId;
    /**
     * 人员类别名
     */
    private String staffTypeName;
    /**
     * 直属上级
     */
    private Long directSupervisorId;
    /**
     * 直属上级
     */
    private String directSupervisorName;
    /**
     * 性别
     * 0 男
     * 1 女
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
    private String birthday;
    /**
     * 电话
     */
    private String phone;
    /**
     * 邮件
     */
    private String email;
    /**
     * 用工性质
     * 1=正式工
     * 2=临时工
     */
    private Integer employmentType;
    /**
     * 人员状态
     */
    private Integer state;
    /**
     * 对应用户ID
     */
    private Long userId;
    /**
     * 业务编码
     */
    private String bizCode;

    @ApiModelProperty("是否生成用户")
    private Integer generateUser;

    @ApiModelProperty("地址明细")
    private BaseAddressDto addressDetail;

}
