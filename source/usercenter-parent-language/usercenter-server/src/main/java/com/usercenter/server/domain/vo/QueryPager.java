package com.usercenter.server.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

@ApiModel(description = "分页查询", value = "QueryPager")
public class QueryPager {


	@ApiModelProperty(value = "每页显示（默认50）")
	private int pageSize =50;
	@ApiModelProperty(value = "页码（默认为1）")
	private int pageNo=1 ;

	@ApiModelProperty(value = "可选排序字段：id(默认) , created_date , update_time")
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

	/**
	 * 计算每页大小
	 *
	 * @return
	 */
	public int getPageSize() {
		return pageSize ;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * 空字符串控制
	 *
	 * @return 排序字段
	 */
	public String getOrderBy() {
		if (StringUtils.isEmpty(orderBy)) {
			return null;
		}
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * 排序方式依据 排序字段控制，默认为升序
	 *
	 * @return 排序方式
	 */
	public Integer getOrderWay() {
		if (StringUtils.isEmpty(orderBy)) {
			return null;
		}
		if (orderWay == 1 || orderWay == 0) {
			return orderWay;
		}
		return 1;
	}

	public void setOrderWay(Integer orderWay) {
		this.orderWay = orderWay;
	}
}
