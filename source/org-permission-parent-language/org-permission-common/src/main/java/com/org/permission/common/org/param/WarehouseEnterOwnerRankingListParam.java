package com.org.permission.common.org.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 仓库入驻货主排行榜参数
 */
@ApiModel(description = "仓库入驻货主排行榜")
@Data
public class WarehouseEnterOwnerRankingListParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("排行榜大小,默认前十")
	private Integer rankingList = 10;


	@ApiModelProperty("排序方式，0 降序（默认）,1 升序")
	private Integer orderWay = 0;

	@ApiModelProperty("状态（状态（1未启用；2启用；3停用；null所有）（默认为2））")
	private Integer state = 2;
}
