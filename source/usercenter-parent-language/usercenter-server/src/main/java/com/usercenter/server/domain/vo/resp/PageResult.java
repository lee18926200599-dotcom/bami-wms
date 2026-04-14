package com.usercenter.server.domain.vo.resp;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

public class PageResult<T> implements Serializable {

    private Integer pageNum;

    private Integer pageSize;

    private List<T> list;

    private Long totalCount;

    public PageResult(List<T> list, PageInfo pageInfo) {
        this.list = list;
        this.pageNum = pageInfo.getPageNum();
        this.pageSize=pageInfo.getPageSize();
        this.totalCount=pageInfo.getTotal();
    }


    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
