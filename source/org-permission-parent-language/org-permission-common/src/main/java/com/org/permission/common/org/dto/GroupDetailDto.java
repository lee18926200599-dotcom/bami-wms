package com.org.permission.common.org.dto;

import com.org.permission.common.org.vo.BaseInfoVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 集团详细信息
 */
@ApiModel(description = "集团信息", value = "GroupDetailDto")
@Data
public class GroupDetailDto extends BaseInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "集团名称")
    private String orgName;

    @ApiModelProperty(value = "集团编码")
    private String orgCode;

    @ApiModelProperty(value = "集团简称")
    private String orgShortName;

    @ApiModelProperty(value = "所属行业码")
    private String industryCode;

    @ApiModelProperty(value = "所属行业名")
    private String industryName;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "网址")
    private String netAddress;

    @ApiModelProperty(value = "成立时间")
    private Long establishTime;

    @ApiModelProperty(value = "信用代码")
    private String creditCode;

    @ApiModelProperty(value = "本位币")
    private String currency;

    @ApiModelProperty(value = "业务类型ID")
    private String bizTypeId;


    @ApiModelProperty(value = "业务类型名")
    private String bizTypeName;

    @ApiModelProperty(value = "业务类型")
    private String businessType;

    @ApiModelProperty(value = "业务类型名")
    private String businessTypeName;

    @ApiModelProperty(value = "状态（1未启用；2启用；3停用; 4删除）")
    private Integer state;

    @ApiModelProperty(value = "初始化（0否；1是）")
    private Integer initFlag;

    @ApiModelProperty(value = "简介")
    private String remark;

    @ApiModelProperty(value = "主营业务")
    private String mainBusiness;

    @ApiModelProperty(value = "详细地址")
    private BaseAddressDto addressDetail;

    @ApiModelProperty(value = "集团logo")
    private String logoUrl;
}
