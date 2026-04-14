package com.org.permission.server.permission.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* 调整用户失效性返回对象
 */
@Data
@ApiModel(description="调整用户失效性返回对象")
public class UserExpireDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="用户角色关系主键id")
	private Long id;
	@ApiModelProperty(value="用户id")
	private Long userId;
	@ApiModelProperty(value="组织名称")
	private String orgName;
	@ApiModelProperty(value="组织id")
    private Long orgId;
	@ApiModelProperty(value="用户编码")
    private String userNumber;
	@ApiModelProperty(value="用户账号")
    private String userName;
	@ApiModelProperty(value="用户真实名称")
    private String realName;
	@ApiModelProperty(value="生效时间")
    private Date effectiveTime;
	@ApiModelProperty(value="失效时间")
    private Date expireTime;
	@ApiModelProperty(value="角色id")
    private Long roleId;

}
