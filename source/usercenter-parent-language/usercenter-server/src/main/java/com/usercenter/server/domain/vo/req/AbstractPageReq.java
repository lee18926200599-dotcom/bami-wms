package com.usercenter.server.domain.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 主要用于查询
 */
@Data
public abstract class AbstractPageReq extends AbstractUserReq {

    @ApiModelProperty(notes = "当前页面")
    protected Integer pageNum = 1;

    @ApiModelProperty(notes = "当前页面大小")
    protected Integer pageSize = 10;
}
