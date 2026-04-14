package com.basedata.common.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * comments:仓库实体类型
 */
@Data
public class BaseWarehouseDetailVo {
    @ApiModelProperty(value = "仓库id")
    private Long id;
    @ApiModelProperty(value = "仓库编号")
    private String warehouseCode;
    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;
    @ApiModelProperty(value = "物流园区")
    private String logisticsPark;
    @ApiModelProperty(value = "服务商id")
    private Long serviceProviderId;
    @ApiModelProperty(value = "服务商名称")
    private String serviceProviderName;
    @ApiModelProperty(value = "所属集团")
    private Long groupId;
    @ApiModelProperty(value = "所属集团名称")
    private String groupName;
    @ApiModelProperty(value = "库房数量")
    private Integer warehouseQty;
    @ApiModelProperty(value = "运营类型字典code")
    private String operationType;
    @ApiModelProperty(value = "仓库性质字典code")
    private String warehouseProperty;
    @ApiModelProperty(value = "仓库面积")
    private BigDecimal warehouseArea;
    @ApiModelProperty(value = "联系人")
    private String contactPerson;
    @ApiModelProperty(value = "联系电话")
    private String contactTel;
    @ApiModelProperty(value = "联系手机")
    private String contactPhone;
    @ApiModelProperty(value = "联系邮箱")
    private String contactEmail;
    @ApiModelProperty(value = "省")
    private String provinceCode;
    @ApiModelProperty(value = "省名称")
    private String provinceName;
    @ApiModelProperty(value = "市")
    private String cityCode;
    @ApiModelProperty(value = "市名称")
    private String cityName;
    @ApiModelProperty(value = "区（县）")
    private String districtCode;
    @ApiModelProperty(value = "区名称 ")
    private String districtName;
    @ApiModelProperty(value = "乡（镇）")
    private String townCode;
    @ApiModelProperty(value = "乡名称 ")
    private String townName;
    @ApiModelProperty(value = "详细地址")
    private String address;
    @ApiModelProperty(value = "开仓时间")
    private String openDate;
    @ApiModelProperty(value = "开仓开始时间")
    private String openStartTime;
    @ApiModelProperty(value = "开仓结束时间")
    private String openEndDate;
    @ApiModelProperty(value = "0-已创建，1-启用，2-停用")
    private Integer state;
    @ApiModelProperty(value = "备注")
    private String remark;
}
