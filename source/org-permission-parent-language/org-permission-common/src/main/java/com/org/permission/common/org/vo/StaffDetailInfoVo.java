package com.org.permission.common.org.vo;

import com.org.permission.common.org.dto.BaseAddressDto;
import com.org.permission.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 人员详细信息（含任职及类别信息（前端展示）
 */
@ApiModel("人员详细信息（前端展示）")
@Data
public class StaffDetailInfoVo extends BaseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("所属部门ID")
    private Long depId;

    @ApiModelProperty("所属部门名")
    private String depName;

    @ApiModelProperty("所属业务单元ID")
    private Long orgId;

    @ApiModelProperty("所属业务单元名")
    private String orgName;

    @ApiModelProperty("人员编码")
    private String staffCode;

    @ApiModelProperty("姓名")
    private String realname;

    @ApiModelProperty("性别(0男，1女)")
    private Integer sex;

    @ApiModelProperty("证件类型")
    private Integer certificateType;

    @ApiModelProperty("证件号码")
    private String certificateNo;

    @ApiModelProperty("生日")
    private String birthday;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("邮件")
    private String email;

    @ApiModelProperty("用工性质（0 正式工;1 临时工）")
    private Integer employmentType;

    @ApiModelProperty(value = "状态(0未启用;1启用;2停用")
    private Integer state;

    @ApiModelProperty(value = "生成用户的用户id")
    private Long userId;
    @ApiModelProperty(value = "生成用户的用户名")
    private String userName;

    @ApiModelProperty("关联用户（true是;false否）")
    private Boolean relatedUser;

    @ApiModelProperty(value = "详细地址")
    private BaseAddressDto addressDetail;

    @ApiModelProperty("人员任职信息")
    private StaffDutyInfoVo staffDuty;

    @ApiModelProperty("直属上级id")
    private Long directSupervisorId;
    @ApiModelProperty("直属上级名字")
    private String directSupervisorName;

    @ApiModelProperty("注册来源")
    private Integer registSource;

    @ApiModelProperty("注册来源Id")
    private String registSourceId;

    public StaffDetailInfoVo() {
    }

    public Boolean getRelatedUser() {
        if (userId != null && userId.compareTo(0L) > 0) {
            return true;
        }
        return false;
    }
}
