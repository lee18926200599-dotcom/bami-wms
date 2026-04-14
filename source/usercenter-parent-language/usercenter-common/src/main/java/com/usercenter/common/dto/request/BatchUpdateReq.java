package com.usercenter.common.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * 配量用户启用，禁用，锁定，解锁，删除
 */
@ApiModel(value = "批量用户，启用，禁用，锁定，解锁，删除")
@Data
public class BatchUpdateReq implements Serializable {


    /**
     * 用户主键
     */
    @ApiModelProperty(notes = "用户ID集合")
    private Set<Long> ids;


    /**
     * 集团ID
     */
    @ApiModelProperty(notes = "根据集团ID去更新用户启用状态")
    private Long groupId;

    /**
     * 功能操作
     */
    @ApiModelProperty(notes = "操作类型【0,非启用。1.启用。2停用。3.锁定。4.解锁.6,删除】")
    private Integer operate;

    /**
     * 当前登录用户
     */
    @ApiModelProperty(notes = "当前登录用户ID")
    private Long userId;
}
