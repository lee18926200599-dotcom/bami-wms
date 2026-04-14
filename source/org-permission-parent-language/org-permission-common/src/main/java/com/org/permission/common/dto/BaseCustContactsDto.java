package com.org.permission.common.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class BaseCustContactsDto implements Serializable {

	@ApiModelProperty(value = "主键ID")
	private Long id;

	@ApiModelProperty(value = "客户信息表ID")
	private Long custId;

	@ApiModelProperty(value = "客户ID")
	private Long custSubId;

	@ApiModelProperty(value = "所属组织")
	private Long belongOrg;

	@ApiModelProperty(value = "归属的业务领域，业务类型：1:采销，2:仓储，3:物流，4:财务")
	private Integer businessType;

	@ApiModelProperty(value = "所属实体类型")
	private String entityType;

	@ApiModelProperty(value = "所属实体ID")
	private Integer entityId;

	@ApiModelProperty(value = "姓名")
	private String contactsName;

	@ApiModelProperty(value = "性别")
	private Integer sex;

	@ApiModelProperty(value = "职位")
	private String position;

	@ApiModelProperty(value = "电话")
	private String telephone;

	@ApiModelProperty(value = "手机")
	private String mobilePhone;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "联系人身份证号")
	private String idNumber;

	@ApiModelProperty(value = "是否默认 0:否，1:是")
	private Integer defaultFlag;

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
