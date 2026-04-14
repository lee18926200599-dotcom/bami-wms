package com.basedata.server.dto;

import com.basedata.common.dto.BaseStorePrintTemplateDetailUpdateReqDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BaseStorePrintTemplateUpdateReqDTO {
    @ApiModelProperty(value = "配置项id：修改的数据有则必传，新增的数据没有ID不要传")
    private Long id;

    @ApiModelProperty(value = "配置编码")
    private String configCode;

    @ApiModelProperty(value = "配置名称")
    private String configName;

    @NotNull(message = "系统承运商ID不能为空！")
    @ApiModelProperty(value = "系统承运商ID（来源于系统客商体系）")
    private Long logisticsId;

    @NotBlank(message = "系统承运商编码不能为空！")
    @ApiModelProperty(value = "系统承运商编码")
    private String logisticsCode;

    @NotBlank(message = "系统承运商名称不能为空！")
    @ApiModelProperty(value = "系统承运商名称")
    private String logisticsName;

    @NotBlank(message = "电商平台编码不能为空！")
    @ApiModelProperty(value = "电商平台编码")
    private String platformCode;

    @NotBlank(message = "电商平台名称不能为空！")
    @ApiModelProperty(value = "电商平台名称")
    private String platformName;

    @ApiModelProperty(value = "授权店铺所属货主ID")
    private Long belongOwnerId;

    @ApiModelProperty(value = "授权店铺所属货主")
    private String belongOwnerName;

    @ApiModelProperty(value = "仓储服务商id")
    private Long serviceProviderId;

    @ApiModelProperty(value = "仓储服务商名称")
    private String serviceProviderName;

    @ApiModelProperty(value = "状态（0-已创建，1-启用，2-停用）")
    private Integer state;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "乐观锁控制")
    private Integer version;

    @ApiModelProperty(value = "是否删除")
    private Integer deletedFlag;

    @ApiModelProperty(value = "模版明细列表")
    List<BaseStorePrintTemplateDetailUpdateReqDTO> templateDetailList;

}
