package com.org.permission.server.domain.crm;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询客户请求参数
 */
@ApiModel
@Data
public class QueryCustReqParam extends BaseQuery implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户ID")
	private Long userId;

	@ApiModelProperty(value = "客户名称")
	private String custName;

	@ApiModelProperty(value = "是否入驻(1 入驻；0未入驻)")
	private Integer presenceFlag;

	@ApiModelProperty(value = "业务类型（1平台；2仓储；3物流；4渠道商；5品牌商；6劳务；7金融；8地产；9商户；10快递；11站点运营；12经销商代运营；13代运营）")
	private String businessType;

	@ApiModelProperty("客商状态（0创建；1启用；2停用；null 所有）")
	private Integer state=1;
}
