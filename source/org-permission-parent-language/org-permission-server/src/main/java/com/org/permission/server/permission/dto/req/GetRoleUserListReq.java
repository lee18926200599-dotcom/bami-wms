package com.org.permission.server.permission.dto.req;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 获取可分配权限用户请求
 **/
@Data
@ApiModel("查询角色已分配的用户和可以分配的用户")
public class GetRoleUserListReq extends BaseQuery {

    /**
     * 组织id
     */
    @ApiModelProperty("组织id，回填选择的组织")
    private List<Long> orgIdList;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String userName;



    /**
     * 根据人员返回的用户id
     */
    @ApiModelProperty("用户id，回填根据人员模糊查询的用户id")
    private Long userId;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phone;


    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id",required = true)
    private Long roleId;

    /**
     * 集团id
     */
    @ApiModelProperty(value = "当前集团id", required = true)
    private Long groupId;

    /**
     * 当前用户id
     */
    @ApiModelProperty(value = "当前登陆的用户id", required = true)
    private Long currentUserId;

    /**
     * 查询类型：assigned 已分配用户 notAssigned 未分配用户
     */
    @ApiModelProperty(value = "查询类型：assigned 已分配用户 notAssigned 未分配用户",required = true,example = "assigned")
    private String queryType;

}
