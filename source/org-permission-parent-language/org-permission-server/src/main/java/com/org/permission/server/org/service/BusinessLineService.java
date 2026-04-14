package com.org.permission.server.org.service;

import com.github.pagehelper.PageInfo;
import com.org.permission.server.org.dto.BusinessLineDto;
import com.org.permission.server.org.dto.param.BusinessLineParam;
import com.org.permission.server.org.bean.BaseBusinessLine;

import java.util.List;

public interface BusinessLineService {
    int save(BusinessLineDto businessLineDto);
    int update(BusinessLineDto businessLineDto);
    int updateState(BusinessLineDto businessLineDto);
    PageInfo<BusinessLineDto> getList(BusinessLineParam businessLineParam);

    List<BaseBusinessLine> getByIdList(List<Long> lineIdList);
}
