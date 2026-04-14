package com.org.permission.server.org.vo;


import com.org.permission.common.enums.org.FunctionTypeEnum;
import com.org.permission.common.org.dto.BaseAddressDto;
import com.org.permission.common.org.dto.TreeDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 业务单元信息,前端展示
 */
@ApiModel
@Data
public class BizUnitDetailVo extends TreeDto implements Serializable {
    private static final long serialVersionUID = 1L;

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

    @ApiModelProperty(value = "状态（1未启用；2启用；3停用; 4删除）")
    private Integer state;

    @ApiModelProperty(value = "业务单元编码")
    private String orgCode;

    @ApiModelProperty(value = "业务单元名")
    private String orgName;

    @ApiModelProperty(value = "业务单元简称")
    private String orgShortName;

    @ApiModelProperty(value = "上级业务单元")
    private String parentName;

    @ApiModelProperty(value = "集团名称")
    private String groupName;

    @ApiModelProperty(value = "实体属性名")
    private String entityCode;

    @ApiModelProperty(value = "实体属性名")
    private String entityName;

    @ApiModelProperty(value = "信用代码")
    private String creditCode;

    @ApiModelProperty(value = "所属公司ID")
    private Integer companyId;

    @ApiModelProperty(value = "所属公司名")
    private String companyName;

    @ApiModelProperty(value = "所属行业")
    private String industryCode;

    @ApiModelProperty(value = "所属行业")
    private String industryName;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = " 本位币")
    private String currency;

    @ApiModelProperty(value = "客户ID")
    private Integer custId;

    @ApiModelProperty(value = "说明")
    private String note;

    @ApiModelProperty(value = "版本号")
    private String version;

    @ApiModelProperty(value = "详细地址")
    private BaseAddressDto addressDetail;

    @ApiModelProperty(value = "法人公司(true 有；false 无)")
    private Boolean corporation;

    @ApiModelProperty(value = "财务(true 有；false 无)")
    private Boolean finance;

    @ApiModelProperty(value = "采购(true 有；false 无)")
    private Boolean purchase;

    @ApiModelProperty(value = "销售(true 有；false 无)")
    private Boolean sale;

    @ApiModelProperty(value = "仓储(true 有；false 无)")
    private Boolean storage;

    @ApiModelProperty(value = "物流(true 有；false 无)")
    private Boolean logistics;

    @ApiModelProperty(value = "子业务单元")
    private List<BizUnitDetailVo> childBUs;

    @ApiModelProperty(value = "前端忽视")
    private List<Integer> simpleFunctions;

    public BizUnitDetailVo() {
    }

    /**
     * 法人公司组织职能
     *
     * @return <code>true</code>有；<code>false</code>无；
     */
    public Boolean getCorporation() {
        return !CollectionUtils.isEmpty(simpleFunctions) && simpleFunctions.contains(FunctionTypeEnum.CORPORATION.getIndex());
    }

    /**
     * 财务组织职能
     *
     * @return <code>true</code>有；<code>false</code>无；
     */
    public Boolean getFinance() {
        return !CollectionUtils.isEmpty(simpleFunctions) && simpleFunctions.contains(FunctionTypeEnum.FINANCE.getIndex());
    }

    /**
     * 采购组织职能
     *
     * @return <code>true</code>有；<code>false</code>无；
     */
    public Boolean getPurchase() {
        return !CollectionUtils.isEmpty(simpleFunctions) && simpleFunctions.contains(FunctionTypeEnum.PURCHASE.getIndex());
    }

    /**
     * 销售组织职能
     *
     * @return <code>true</code>有；<code>false</code>无；
     */
    public Boolean getSale() {
        return !CollectionUtils.isEmpty(simpleFunctions) && simpleFunctions.contains(FunctionTypeEnum.SALE.getIndex());
    }

    /**
     * 仓储组织职能
     *
     * @return <code>true</code>有；<code>false</code>无；
     */
    public Boolean getStorage() {
        return !CollectionUtils.isEmpty(simpleFunctions) && simpleFunctions.contains(FunctionTypeEnum.STORAGE.getIndex());
    }

    /**
     * 物流组织职能
     *
     * @return <code>true</code>有；<code>false</code>无；
     */
    public Boolean getLogistics() {
        return !CollectionUtils.isEmpty(simpleFunctions) && simpleFunctions.contains(FunctionTypeEnum.LOGISTICS.getIndex());
    }
}
