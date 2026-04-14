package com.org.permission.server.permission.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* 角色权限关联表管理
*/
@Data
@ApiModel("角色权限信息")
public class SaveFuncReq implements Serializable {

	@ApiModelProperty("角色id")
	private Long roleId; //角色id

	@ApiModelProperty("权限id")
	private Long permissionId; //权限id(功能、组织、数据)

}

