package com.org.permission.server.permission.dto;
import lombok.Data;

import java.io.Serializable;
/**
* 管理维度表管理
*/ 
@Data
public class BasePermissionManagementDto implements Serializable {
	private static final long serialVersionUID = -7994251883940616411L;
	private Long id;
	private Integer managementId;
	private String name; //管理维度名称 
	private String des; //描述 
	private String remark; //备注 
	private Integer state; //状态
}

