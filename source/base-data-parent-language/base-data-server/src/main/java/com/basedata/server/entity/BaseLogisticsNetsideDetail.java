package com.basedata.server.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.basedata.common.enums.BooleanEnum;
import com.common.base.entity.CurrentUser;
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
@TableName("base_logistics_netside_detail")
@ApiModel(value="BaseLogisticsNetsideDetail对象", description="承运商网点对应关系明细")
public class BaseLogisticsNetsideDetail implements Serializable {


    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "明细id")
    @TableId("id")
    @TableField("id")
    private Long id;

    @ApiModelProperty(value = "配置项主表ID")
    @TableField("config_id")
    private Long configId;

    @ApiModelProperty(value = "网点编码（系统）")
    @TableField("sys_netsite_code")
    private String sysNetsiteCode;

    @ApiModelProperty(value = "网点名称（系统）")
    @TableField("sys_netsite_name")
    private String sysNetsiteName;

    @ApiModelProperty(value = "网点ID/编码（电商平台）")
    @TableField("netsite_code")
    private String netsiteCode;

    @ApiModelProperty(value = "网点名称（电商平台）")
    @TableField("netsite_name")
    private String netsiteName;

    @ApiModelProperty(value = "省（电商平台）")
    @TableField("province")
    private String province;

    @ApiModelProperty(value = "省编码")
    @TableField("province_code")
    private String provinceCode;

    @ApiModelProperty(value = "市（电商平台）")
    @TableField("city")
    private String city;

    @ApiModelProperty(value = "市编码")
    @TableField("city_code")
    private String cityCode;

    @ApiModelProperty(value = "区（电商平台）")
    @TableField("area")
    private String area;

    @ApiModelProperty(value = "区编码")
    @TableField("area_code")
    private String areaCode;

    @ApiModelProperty(value = "镇（电商平台）")
    @TableField("town")
    private String town;

    @ApiModelProperty(value = "镇编码")
    @TableField("town_code")
    private String townCode;

    @ApiModelProperty(value = "网点详细地址（电商平台）")
    @TableField("address_detail")
    private String addressDetail;

    @ApiModelProperty(value = "状态（0-已创建，1-启用，2-停用）")
    @TableField("state")
    private Integer state;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "创建人")
    @TableField("created_by")
    private Long createdBy;

    @ApiModelProperty(value = "创建人名称")
    @TableField("created_name")
    private String createdName;

    @ApiModelProperty(value = "创建时间")
    @TableField("created_date")
    private Date createdDate;

    @ApiModelProperty(value = "修改人")
    @TableField("modified_by")
    private Long modifiedBy;

    @ApiModelProperty(value = "修改人名称")
    @TableField("modified_name")
    private String modifiedName;

    @ApiModelProperty(value = "修改时间")
    @TableField("modified_date")
    private Date modifiedDate;

    @ApiModelProperty(value = "乐观锁控制")
    @TableField("version")
    private Integer version;

    @ApiModelProperty(value = "是否删除")
    @TableField("deleted_flag")
    private Integer deletedFlag;

    public void setDefaultCreateValue(CurrentUser currentUser) {
        this.setCreatedBy(currentUser.getUserId());
        this.setCreatedName(currentUser.getUserName());
        this.setCreatedDate(new Date());
        this.setDeletedFlag(BooleanEnum.FALSE.getCode());
        this.setVersion(0);
    }

    public void setDefaultUpdateValue(CurrentUser currentUser) {
        this.setModifiedBy(currentUser.getUserId());
        this.setModifiedName(currentUser.getUserName());
        this.setModifiedDate(new Date());
    }
}
