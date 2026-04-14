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
 * 平台网点信息
 * </p>
 */
@Data
@TableName("base_platform_netside")
@ApiModel(value = "BasePlatformNetside对象", description = "平台网点信息")
public class BasePlatformNetside implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId("id")
    @TableField("id")
    private Long id;

    @ApiModelProperty(value = "组合md5唯一值")
    @TableField("unique_no")
    private String uniqueNo;

    @ApiModelProperty(value = "服务商id")
    @TableField("service_provider_id")
    private Long serviceProviderId;

    @ApiModelProperty(value = "服务商名称")
    @TableField("service_provider_name")
    private String serviceProviderName;

    @ApiModelProperty(value = "系统承运商ID（来源于系统客商体系）")
    @TableField("logistics_id")
    private Long logisticsId;

    @ApiModelProperty(value = "系统承运商编码")
    @TableField("logistics_code")
    private String logisticsCode;

    @ApiModelProperty(value = "系统承运商名称")
    @TableField("logistics_name")
    private String logisticsName;

    @ApiModelProperty(value = "电商平台承运商编码")
    @TableField("platform_logistics_code")
    private String platformLogisticsCode;

    @ApiModelProperty(value = "电商平台编码")
    @TableField("platform_code")
    private String platformCode;

    @ApiModelProperty(value = "电商平台名称")
    @TableField("platform_name")
    private String platformName;

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

    @ApiModelProperty(value = "品牌编码，TM对应的SF")
    @TableField("brand_code")
    private String brandCode;

    @ApiModelProperty(value = "月结账号")
    @TableField("settle_account")
    private String settleAccount;

    @ApiModelProperty(value = "微信店铺id")
    @TableField("attr_str1")
    private String attrStr1;

    @ApiModelProperty(value = "微信电子面单账号id")
    @TableField("attr_str2")
    private String attrStr2;

    @ApiModelProperty(value = "扩展字符串3")
    @TableField("attr_str3")
    private String attrStr3;

    @ApiModelProperty(value = "扩展字符串4")
    @TableField("attr_str4")
    private String attrStr4;

    @ApiModelProperty(value = "扩展字符串5")
    @TableField("attr_str5")
    private String attrStr5;

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
        if (currentUser != null ) {
            this.setCreatedBy(currentUser.getUserId());
            this.setCreatedName(currentUser.getUserName());
        }
        this.setCreatedDate(new Date());
        this.setDeletedFlag(BooleanEnum.FALSE.getCode());
        this.setVersion(0);
    }

    public void setDefaultUpdateValue(CurrentUser currentUser) {
        if (currentUser != null ) {
            this.setModifiedBy(currentUser.getUserId());
            this.setModifiedName(currentUser.getUserName());
        }
        this.setModifiedDate(new Date());
    }
}
