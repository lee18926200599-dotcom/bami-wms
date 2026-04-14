package com.org.permission.server.org.dto.param;

import com.org.permission.common.dto.BaseDto;
import com.org.permission.common.org.dto.BaseAddressDto;
import com.org.permission.common.org.param.AddPersonDutyParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 新增员工请求参数
 */
@ApiModel(description = "更新员工请求参数")
@Data
public class UpdateStaffReqParam extends BaseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "状态")
    private Integer state;

    @ApiModelProperty(value = "人员编码")
    private String staffCode;

    @ApiModelProperty(value = "用工性质(0 正式工;1 临时工）")
    private Integer employmentType;

    @ApiModelProperty(value = "所属部门")
    private Long depId;

    @ApiModelProperty(value = "所属组织")
    private Long buId;

    @ApiModelProperty(value = "所属集团")
    private Long groupId;

    @ApiModelProperty(value = "姓名")
    private String realname;

    @ApiModelProperty(value = "直属上级")
    private Integer directSupervisor;

    @ApiModelProperty(value = "性别(0男;1女)")
    private Integer sex;

    @ApiModelProperty(value = "证件类型")
    private Integer certificateType;

    @ApiModelProperty(value = "证件号码")
    private String certificateNo;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "邮件")
    private String email;

    @ApiModelProperty(value = "类别业务编码（供业务线调用）")
    private String bizCode;

    @ApiModelProperty(value = "人员任职信息")
    private AddPersonDutyParam personDuty;

    @ApiModelProperty(value = "地址详情")
    private BaseAddressDto addressDetail;

    @ApiModelProperty(value = "创建更新信息")
    private BaseDto baseDto;
}
