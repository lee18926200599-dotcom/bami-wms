package com.org.permission.common.dto;

import com.common.framework.annotation.DictTarget;
import com.common.framework.aop.dict.DictionaryEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 人员表实体类型
 */
@Data
public class BaseStaffDto {
    @ApiModelProperty(value = "自增主键")
    private Long id;
    @ApiModelProperty(value = "数据来源")
    private Integer registSource;
    @ApiModelProperty(value = "数据来源Id")
    private String registSourceId;
    @NotNull(message = "部门ID不能为空")
    @ApiModelProperty(value = "部门ID")
    private Long depId;
    @ApiModelProperty(value = "部门名称")
    private String depName;
    @ApiModelProperty(value = "业务单元ID")
    @NotNull(message = "组织ID不能为空")
    private Long orgId;
    @ApiModelProperty(value = "业务单元名称")
    private String orgName;
    @ApiModelProperty(value = "集团ID")
    @NotNull(message = "集团ID不能为空")
    private Long groupId;
    @ApiModelProperty(value = "人员编码")
    private String staffCode;
    @ApiModelProperty(value = "人员类别Id")
    @NotNull(message = "类别不能为空")
    private String staffTypeId;
    @ApiModelProperty(value = "人员类别名称")
    private String staffTypeName;
    @ApiModelProperty(value = "类别业务编码（供业务线调用）")
    @NotNull(message = "类别业务编码不能为空")
    private String bizCode;
    @ApiModelProperty(value = "人员姓名")
    @NotNull(message = "名称不能为空")
    private String realname;
    @ApiModelProperty(value = "直属上级")
    private Long directSupervisor;
    @ApiModelProperty(value = "性别（0男;1女）")
    private Integer sex;
    @ApiModelProperty(value = "证件类型")
    private String certificateType;
    @ApiModelProperty(value = "证件号码")
    private String certificateNo;
    @ApiModelProperty(value = "生日")
    private String birthday;
    @ApiModelProperty(value = "电话")
    @NotNull(message = "电话不能为空")
    private String phone;
    @ApiModelProperty(value = "邮件")
    private String email;
    @ApiModelProperty(value = "省code")
    private Long provinceCode;
    @ApiModelProperty(value = "省名称")
    private String provinceName;
    @ApiModelProperty(value = "市code")
    private Long cityCode;
    @ApiModelProperty(value = "市名称")
    private String cityName;
    @ApiModelProperty(value = "区code")
    private Long districtCode;
    @ApiModelProperty(value = "区名称")
    private String districtName;
    @ApiModelProperty(value = "街道code")
    private Long streetCode;
    @ApiModelProperty(value = "街道名称")
    private String streetName;
    @ApiModelProperty(value = "详细地址")
    private String address;
    @ApiModelProperty(value = "用工性质（0 正式工;1临时工）")
    @DictTarget(DictionaryEnum.EMPLOYMENT_TYPE)
    private String employmentType;
    @ApiModelProperty(value = "状态")
    private Integer state;
    @ApiModelProperty(value = "用户Id")
    private Long userId;
    @ApiModelProperty(value = "创建人id")
    private Long createdBy;
    @ApiModelProperty(value = "创建人")
    private String createdName;
    @ApiModelProperty(value = "创建日期")
    private Date createdDate;
    @ApiModelProperty(value = "修改人id")
    private Long modifiedBy;
    @ApiModelProperty(value = "修改人")
    private String modifiedName;
    @ApiModelProperty(value = "修改时间")
    private Date modifiedDate;

    @ApiModelProperty(value = "开始时间")
    private String startDate;
    @ApiModelProperty(value = "结束时间")
    private String endDate;
    @ApiModelProperty(value = "deleted_flag")
    private Integer deletedFlag;
}
