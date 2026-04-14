package com.org.permission.server.org.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 联系人信息
 */
@Data
@TableName("base_cust_contacts")
public class BaseCustContacts implements Serializable {

	@ApiModelProperty(value = "主键ID")
	
	@TableId(value = "id")
	private Long id;

	@ApiModelProperty(value = "客户信息表ID")
	@TableField(value = "cust_id")
	private Long custId;

	@ApiModelProperty(value = "客户ID")
	@TableField(value = "cust_sub_id")
	private Long custSubId;

	@ApiModelProperty(value = "所属组织")
	@TableField(value = "belong_org")
	private Long belongOrg;

	@ApiModelProperty(value = "归属的业务领域，业务类型：1:采销，2:仓储，3:物流，4:财务")
	@TableField(value = "business_type")
	private Integer businessType;

	@ApiModelProperty(value = "所属实体类型")
	@TableField(value = "entity_type")
	private String entityType;

	@ApiModelProperty(value = "所属实体ID")
	@TableField(value = "entity_id")
	private Integer entityId;

	@ApiModelProperty(value = "姓名")
	@TableField(value = "contacts_name")
	private String contactsName;

	@ApiModelProperty(value = "性别")
	@TableField(value = "sex")
	private Integer sex;

	@ApiModelProperty(value = "职位")
	@TableField(value = "position")
	private String position;

	@ApiModelProperty(value = "电话")
	@TableField(value = "telephone")
	private String telephone;

	@ApiModelProperty(value = "手机")
	@TableField(value = "mobile_phone")
	private String mobilePhone;

	@ApiModelProperty(value = "邮箱")
	@TableField(value = "email")
	private String email;

	@ApiModelProperty(value = "联系人身份证号")
	@TableField(value = "id_number")
	private String idNumber;

	@ApiModelProperty(value = "是否默认 0:否，1:是")
	@TableField(value = "default_flag")
	private Integer defaultFlag;

	@ApiModelProperty(value = "创建人id")
	@TableField(value = "created_by")
	private Long createdBy;

	@ApiModelProperty(value = "创建人")
	@TableField(value = "created_name")
	private String createdName;

	@ApiModelProperty(value = "创建日期")
	@TableField(value = "created_date")
	private Date createdDate;

	@ApiModelProperty(value = "修改人id")
	@TableField(value = "modified_by")
	private Long modifiedBy;

	@ApiModelProperty(value = "修改人")
	@TableField(value = "modified_name")
	private String modifiedName;

	@ApiModelProperty(value = "修改时间")
	@TableField(value = "modified_date")
	private Date modifiedDate;
}
