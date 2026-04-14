package com.usercenter.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class UserDto extends BaseUserCommonDto {
    /**
     * token
     */
    @ApiModelProperty(value = "token")
    private String token;
    /**
     * token 失效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "token 失效时间 yyyy-MM-dd HH:mm:ss")
    private Date tokenExpireDate;
    /**
     * token 失效时间
     */
    @ApiModelProperty(value = "token 失效时间（秒）")
    private Integer tokenExpireTime;

    /**
     * 业务单元列表
     */
    @ApiModelProperty(value = "业务单元列表")
    private Object orgInfo;
    /**
     * 集团列表
     */
    @ApiModelProperty(value = "集团列表")
    private Object groupInfo;

    /**
     * 用户菜单
     */
    @ApiModelProperty(value = "用户菜单")
    private Map<String, Object> menus;


    /**
     * 根据用户权限过滤可以查询的用户
     */
    private Boolean filterByUserPermission = false;

    /**
     * 查询人id
     */
    private Long queryUserId;

    /**
     * 根据id集合批量查询
     */
    private Set<Long> idSet;

    /**
     * 根据id不再某一集合中
     */
    private Set<Integer> idNotInSet;

    private String condition;
    /**
     * 是否包含已经删除的数据，默认为false
     */
    @ApiModelProperty(value = "是否包含已经删除的数据, 默认为false")
    private Boolean withDeleted = false;
    private List<Long> orgIdList;
    @ApiModelProperty(value ="是否模糊查询：默认为true， 控制的字段有：userCode、realName、userName、phone、email")
    private Boolean likeQuery=false;
}
