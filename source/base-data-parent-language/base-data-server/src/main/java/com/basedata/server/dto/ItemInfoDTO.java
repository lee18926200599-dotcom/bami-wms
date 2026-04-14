package com.basedata.server.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ItemInfoDTO {
    @ApiModelProperty(value = "商品编码")
    private String itemCode;

    @ApiModelProperty(value = "商品id ")
    private String itemId;

    @ApiModelProperty(value = "商品名称")
    private String itemName;

    @ApiModelProperty(value = "货号")
    private String goodsCode;

    @ApiModelProperty(value = "商品简称")
    private String shortName;

    @ApiModelProperty(value = "英文名")
    private String englishName;

    @ApiModelProperty(value = "条形码(可多个;用分号;隔开)")
    private String barCode;

    @ApiModelProperty(value = "商品属性(如红色;XXL)")
    private String skuProperty;

    @ApiModelProperty(value = "商品计量单位")
    private String stockUnit;

    @ApiModelProperty(value = "长(单位：厘米)")
    private BigDecimal length;

    @ApiModelProperty(value = "宽(单位：厘米)")
    private BigDecimal width;

    @ApiModelProperty(value = "高(单位：厘米)")
    private BigDecimal height;

    @ApiModelProperty(value = "体积(单位：升)")
    private BigDecimal volume;

    @ApiModelProperty(value = "毛重(单位：千克)")
    private BigDecimal grossWeight;

    @ApiModelProperty(value = "净重(单位：千克)")
    private BigDecimal netWeight;

    @ApiModelProperty(value = "颜色")
    private String color;

    @ApiModelProperty(value = "尺寸")
    private String size;

    @ApiModelProperty(value = "渠道中的商品标题")
    private String title;

    @ApiModelProperty(value = "商品类别ID")
    private String categoryId;

    @ApiModelProperty(value = "商品类别名称")
    private String categoryName;

    @ApiModelProperty(value = "计价货类")
    private String pricingCategory;

    @ApiModelProperty(value = "安全库存")
    private String safetyStock;

    @ApiModelProperty(value = "商品类型(ZC=正常商品;FX=分销商品;ZH=组合商品;ZP=赠品;BC=包材;HC=耗材;FL=辅料;XN=虚拟品;FS=附属品;CC=残次品; OTHER=其它;只传英文编码)")
    private String itemType;

    @ApiModelProperty(value = "吊牌价")
    private BigDecimal tagPrice;

    @ApiModelProperty(value = "零售价")
    private BigDecimal retailPrice;

    @ApiModelProperty(value = "成本价")
    private BigDecimal costPrice;

    @ApiModelProperty(value = "采购价")
    private BigDecimal purchasePrice;

    @ApiModelProperty(value = "季节编码")
    private String seasonCode;

    @ApiModelProperty(value = "季节名称")
    private String seasonName;

    @ApiModelProperty(value = "品牌代码")
    private String brandCode;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "是否需要串号管理(Y/N ;默认为N)")
    private String isSNMgmt;

    @ApiModelProperty(value = "生产日期")
    private Date productDate;

    @ApiModelProperty(value = "过期日期")
    private Date expireDate;

    @ApiModelProperty(value = "是否需要保质期管理(Y/N ;默认为N)")
    private String isShelfLifeMgmt;

    @ApiModelProperty(value = "保质期(单位：小时)")
    private BigDecimal shelfLife;

    @ApiModelProperty(value = "保质期禁收天数")
    private BigDecimal rejectLifecycle;

    @ApiModelProperty(value = "保质期禁售天数")
    private BigDecimal lockupLifecycle;

    @ApiModelProperty(value = "保质期临期预警天数")
    private BigDecimal adventLifecycle;

    @ApiModelProperty(value = "是否需要批次管理(Y/N ;默认为N)")
    private String isBatchMgmt;

    @ApiModelProperty(value = "批次代码")
    private String batchCode;

    @ApiModelProperty(value = "批次备注")
    private String batchRemark;

    @ApiModelProperty(value = "包装代码")
    private String packCode;

    @ApiModelProperty(value = "箱规")
    private BigDecimal pcs;

    @ApiModelProperty(value = "商品的原产地")
    private String originAddress;

    @ApiModelProperty(value = "批准文号")
    private String approvalNumber;

    @ApiModelProperty(value = "是否易碎品(Y/N ;默认为N)")
    private String isFragile;

    @ApiModelProperty(value = "是否危险品(Y/N ;默认为N)")
    private String isHazardous;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否有效(Y/N ;默认为N)")
    private String isValid;

    @ApiModelProperty(value = "是否sku(Y/N ;默认为N)")
    private String isSku;

    @ApiModelProperty(value = "商品包装材料类型")
    private String packageMaterial;

    @ApiModelProperty(value = "")
    private String supplierCode;

    @ApiModelProperty(value = "销售配送方式（0=自配|1=菜鸟）")
    private String logisticsType;

    @ApiModelProperty(value = "是否液体, Y/N, (默认为N)")
    private String isLiquid;
}
