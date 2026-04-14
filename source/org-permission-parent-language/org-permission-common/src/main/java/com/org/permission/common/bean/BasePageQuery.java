package com.org.permission.common.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(description = "分页", value = "BasePageQuery")
@Data
public class BasePageQuery {

    @ApiModelProperty(value = "每页显示（默认50）")
    private int pageSize = 50;
    @ApiModelProperty(value = "页码（默认为1）")
    private int pageNo = 1;

    @ApiModelProperty(value = "可选排序字段：id(默认) , create_time , update_time")
    private String sortBy = "id";

    @ApiModelProperty(value = "排序方式 0 降序,1 升序（默认）")
    private Integer order = 1;

    @ApiModelProperty(value = "可选排序字段：id(默认) , created_date , modified_date")
    private String orderBy = "id";

    @ApiModelProperty(value = "排序方式 0 降序,1 升序（默认）")
    private Integer orderWay = 1;

    /**
     * 计算起始便宜量
     *
     * @return 起始量
     */
    public int getStart() {
        pageNo = pageNo < 1 ? 1 : pageNo;
        return (pageNo - 1) * getPageSize();
    }
}
