package com.org.permission.common.permission.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 组织权限返回树对象
 */
@ApiModel(description = "组织权限返回树对象")
@Data
public class OrgDto implements Serializable {

    private static final long serialVersionUID = 8363856457343642029L;
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "组织机构编码")
    private String orgCode; //
    @ApiModelProperty(value = "组织机构名称")
    private String orgName; //
    @ApiModelProperty(value = "父级组织id")
    private Long parentId;
    @ApiModelProperty(value = "名称节点")
    private String name;
    @ApiModelProperty(value = "唯一主键")
    private String key;
    @ApiModelProperty(value = "唯一名称")
    private String label;
    @ApiModelProperty(value = "组织机构id")
    private Long orgId;
    @ApiModelProperty(value = "组织子节点")
    private List<OrgDto> childOrgs;
    @ApiModelProperty(value = "组织节点下的用户")
    private List<UserDto> userList;
    @ApiModelProperty(value = "组织节点下的角色")
    private List<RoleDto> roleList;
    @ApiModelProperty(value = "vue tree children节点")
    private Map<String, Object> children;
    @ApiModelProperty(value = "是否选中")
    private boolean check;

}
