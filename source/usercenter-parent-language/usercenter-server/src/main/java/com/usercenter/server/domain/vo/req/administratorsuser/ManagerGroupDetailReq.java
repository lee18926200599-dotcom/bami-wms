package com.usercenter.server.domain.vo.req.administratorsuser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 用户子表的 显示和 更新操作
 */
@ApiModel(value = "管理集团信息")
@Data
public class ManagerGroupDetailReq {


    /**
     * 数据主键
     */
    @ApiModelProperty(notes = "主键ID")
    private Long id;

    /**
     * 集团编码
     */
    @ApiModelProperty(notes = "集团编码")
    private String groupCode;

    /**
     * 集团名称
     */
    @ApiModelProperty(notes = "集团名称")
    private String groupName;

    /**
     * 转换所用集团ID
     */
    @ApiModelProperty(notes = "集团ID")
    private Long groupId;


    /**
     * 管理生效时间
     */
    @ApiModelProperty(notes = "管理生效时间")
    private Date effectiveTime;

    /**
     * 管理失效时间
     */
    @ApiModelProperty(notes = "管理失效时间")
    private Date expireTime;
}
