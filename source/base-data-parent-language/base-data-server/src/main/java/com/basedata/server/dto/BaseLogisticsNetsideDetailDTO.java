package com.basedata.server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 承运商网点对应关系明细
 * </p>
 */
@Data
@ApiModel(value = "BaseLogisticsNetsideDetailReqDTO对象", description = "承运商网点对应关系明细请求")
public class BaseLogisticsNetsideDetailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "明细id：修改的数据有则必传，新增的数据没有ID不要传")
    private Long id;

    @ApiModelProperty(value = "配置项主表ID")
    private Long configId;

    @ApiModelProperty(value = "网点编码（系统）")
    private String sysNetsiteCode;

    @ApiModelProperty(value = "网点名称（系统）")
    private String sysNetsiteName;

    @ApiModelProperty(value = "网点ID/编码（电商平台）")
    private String netsiteCode;

    @ApiModelProperty(value = "网点名称（电商平台）")
    private String netsiteName;

    @ApiModelProperty(value = "省（电商平台）")
    private String province;

    @ApiModelProperty(value = "省编码")
    private String provinceCode;

    @ApiModelProperty(value = "市（电商平台）")
    private String city;

    @ApiModelProperty(value = "市编码")
    private String cityCode;

    @ApiModelProperty(value = "区（电商平台）")
    private String area;

    @ApiModelProperty(value = "区编码")
    private String areaCode;

    @ApiModelProperty(value = "镇（电商平台）")
    private String town;

    @ApiModelProperty(value = "镇编码")
    private String townCode;

    @ApiModelProperty(value = "网点详细地址（电商平台）")
    private String addressDetail;

    @ApiModelProperty(value = "状态（0-已创建，1-启用，2-停用）")
    private Integer state;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建人名称")
    private String createdName;

    @ApiModelProperty(value = "创建时间")
    private Date createdDate;

    @ApiModelProperty(value = "修改人")
    private Long modifiedBy;

    @ApiModelProperty(value = "修改人名称")
    private String modifiedName;

    @ApiModelProperty(value = "修改时间")
    private Date modifiedDate;

    @ApiModelProperty(value = "乐观锁控制")
    private Integer version;

    @ApiModelProperty(value = "是否删除")
    private Integer deletedFlag;


}
