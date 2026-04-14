package com.org.permission.common.org.param;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 集团查询请求参数
 */
@ApiModel(description = "集团单元查询请求参数", value = "GroupListQueryParam")
@Data
public class GroupListQueryParam extends BaseQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("集团名字")
    private String orgName;

    @ApiModelProperty("集团简称")
    private String orgShortName;

    @ApiModelProperty("是否分页,默认false不分页")
    private Boolean flag = false;

    @ApiModelProperty("业务类型")
    private List<String> businessType;

    @ApiModelProperty("集团id")
    private Long id;

    @ApiModelProperty("集团id集合")
    private List<Long> ids;

    @ApiModelProperty(notes = "创建时间-开始时间", required = false)
    private Date createdDateStart;

    @ApiModelProperty(notes = "创建时间-结束时间", required = false)
    private Date createdDateEnd;


}
