package com.org.permission.server.permission.dto.req;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 获取集团列表请求
 **/
@Data
@ApiModel("获取集团列表请求")
public class GetGroupListReq extends BaseQuery implements Serializable {

    @ApiModelProperty(notes = "当前登陆的用户id", required = true)
    private Long currentUserId;

    @ApiModelProperty(notes = "业务类型集合", required = false)
    private List<String> bussinessTypes;

    @ApiModelProperty(notes = "集团名称", required = false)
    private String groupName;

    @ApiModelProperty(notes = "集团简称", required = false)
    private String groupShortName;

    @ApiModelProperty(notes = "创建时间-开始时间", required = false)
    private Date createdDateStart;

    @ApiModelProperty(notes = "创建时间-结束时间", required = false)
    private Date createdDateEnd;

}
