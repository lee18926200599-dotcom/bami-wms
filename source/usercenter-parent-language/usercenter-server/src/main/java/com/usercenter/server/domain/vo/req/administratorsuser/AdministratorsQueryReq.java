package com.usercenter.server.domain.vo.req.administratorsuser;

import com.common.base.entity.BaseQuery;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * 管理员管理请求查询
 */
@ApiModel(value = "管理员列表请求参数")
@Data
public class AdministratorsQueryReq extends BaseQuery {

    @ApiModelProperty(notes = "用户ID")
    private Long id;


    /**
     * 管理员类型
     */
    @ApiModelProperty(notes = "管理员类型1.全局管理员。2.集团管理员")
    private Integer managerLevel;


    /**
     * 所属集团集合
     */
    @ApiModelProperty(notes = "所属集团集合")
    private Set<Long> groupIds;

    /**
     * 所属集团
     */
    @ApiModelProperty(notes = "所属集团")
    private Long groupId;


    /**
     * 管理员编码
     */
    @ApiModelProperty(notes = "管理员编码")
    private String userCode;


    /**
     * 管理员手机
     */
    @ApiModelProperty(notes = "管理员手机号")
    private String phone;


    /**
     * 管理员账号
     */
    @ApiModelProperty(notes = "管理员账号")
    private String userName;


    /**
     * 当前用户 可查看用户的管理级别
     */
    @JsonIgnore
    private Integer level;


    public Integer getManagerLevel() {
        return managerLevel;
    }

    public AdministratorsQueryReq setManagerLevel(Integer managerLevel) {
        this.managerLevel = managerLevel;
        return this;
    }

}
