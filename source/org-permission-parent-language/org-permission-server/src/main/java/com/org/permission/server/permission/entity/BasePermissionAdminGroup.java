package com.org.permission.server.permission.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * base_permission_admin_group实体类 用户表中的管理员和集团关系表管理
 */
@ApiModel(description = "管理员和集团关系对象")
@Data
@TableName(value = "base_permission_admin_group")
public class BasePermissionAdminGroup extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//集团id(base_organization.id)
	@TableField(value = "group_id")
	private Long groupId;
	//集团编码
	@TableField(value = "group_code")
	private String groupCode;
	//集团名称
	@TableField(value = "group_name")
	private String groupName;
	//管理员id(base_user.id)
	@TableField(value = "admin_id")
	private Long adminId;
	//管理员名称
	@TableField(value = "admin_name")
	private String adminName;
	//管理生效时间
	@TableField(value = "effective_time")
	private Date effectiveTime;
	//管理失效时间
	@TableField(value = "expire_time")
	private Date expireTime;
	//状态
	@TableField(value = "state")
	private Integer state;

	@ApiModelProperty(value = "集团简称")
	private String groupShortName; // 集团名称

	@ApiModelProperty(notes = "创建时间-开始时间", required = false)
	private Date createdDateStart;
	@ApiModelProperty(notes = "创建时间-结束时间", required = false)
	private Date createdDateEnd;
}
