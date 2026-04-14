package com.org.permission.server.org.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;



@Data
@Accessors(chain = true)
public class BaseCustFinanceSubVO extends BaseVO {

	@ApiModelProperty(value = "客户ID，主键，ID生成器生成")
	private Long id;

	@ApiModelProperty(value = "客户基本信息子表ID")
	private Long custSubId;

	@ApiModelProperty(value = "业务单元ID")
	private Long orgId;

	@ApiModelProperty(value = "默认币种")
	private Integer currency;

	@ApiModelProperty(value = "默认收付款协议 基础数据—收付款协议")
	private Integer payAgreement;

	@ApiModelProperty(value = "专管部门 参照已分配采销组织的部门档案")
	private Long department;

	@ApiModelProperty(value = "专管业务员 与专管部门联动")
	private Integer worker;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "状态。0-未启用，1-已启用，2-已停用")
	private Integer state;

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

}
