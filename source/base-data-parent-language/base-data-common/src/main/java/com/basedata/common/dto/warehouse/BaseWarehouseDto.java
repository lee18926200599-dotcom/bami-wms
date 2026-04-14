package com.basedata.common.dto.warehouse;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * comments:仓库实体类型
 */
@Data
public class BaseWarehouseDto {
    @ApiModelProperty(value = "仓库id")
    private Long id;
    @ApiModelProperty(value = "所属单位id")
    @NotNull(message = "所属单位不能为空")
    private Long serviceProviderId;
    @ApiModelProperty(value = "单位名称")
    @NotEmpty(message = "所属单位不能为空")
    private String serviceProviderName;
    @ApiModelProperty(value = "仓库编号")
    private String warehouseCode;
    @ApiModelProperty(value = "仓库名称")
    @NotEmpty(message = "仓库名称不能为空")
    @Length(min = 1,max = 30,message = "仓库名称不能超过30个字符")
    private String warehouseName;
    @ApiModelProperty(value = "所属集团")
    @NotNull(message = "所属集团不能为空")
    private Long groupId;
    @ApiModelProperty(value = "所属集团名称")
    @NotEmpty(message = "所属集团不能为空")
    private String groupName;
    @ApiModelProperty(value = "运营类型字典code")
    @NotEmpty(message = "运营类型不能为空")
    private String operationType;
    @ApiModelProperty(value = "仓库性质字典code")
    @NotEmpty(message = "仓库性质不能为空")
    private String warehouseProperty;
    @ApiModelProperty(value = "仓库面积")
    @NotNull(message = "仓库面积不能为空")
    private BigDecimal warehouseArea;
    @ApiModelProperty(value = "联系人")
    @NotEmpty(message = "联系人不能为空")
    private String contactPerson;
    @ApiModelProperty(value = "联系电话")
    private String contactTel;
    @ApiModelProperty(value = "联系手机")
    @NotEmpty(message = "联系手机号不能为空")
    private String contactPhone;
    @ApiModelProperty(value = "联系邮箱")
    private String contactEmail;
    @ApiModelProperty(value = "省")
    @NotEmpty(message = "省不能为空")
    private String provinceCode;

    @ApiModelProperty(value = "省")
    @NotEmpty(message = "省不能为空")
    private String provinceName;

    @ApiModelProperty(value = "市")
    @NotEmpty(message = "市不能为空")
    private String cityCode;

    @ApiModelProperty(value = "市")
    @NotEmpty(message = "市不能为空")
    private String cityName;

    @ApiModelProperty(value = "区（县）")
    @NotEmpty(message = "区（县）不能为空")
    private String districtCode;

    @ApiModelProperty(value = "区（县）")
    @NotEmpty(message = "区（县）不能为空")
    private String districtName;

    @ApiModelProperty(value = "乡（镇）")
    @NotEmpty(message = "乡（镇）不能为空")
    private String townCode;

    @ApiModelProperty(value = "乡（镇）")
    @NotEmpty(message = "乡（镇）不能为空")
    private String townName;

    @ApiModelProperty(value = "详细地址")
    @NotEmpty(message = "详细地址不能为空")
    private String address;
    @ApiModelProperty(value = "开仓时间")
    private String openDate;
    @ApiModelProperty(value = "开仓开始时间")
    private String openStartTime;
    @ApiModelProperty(value = "开仓结束时间")
    private String openEndDate;
    @ApiModelProperty(value = "备注")
    private String remark;
    private Integer state;
}
