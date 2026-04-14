package com.basedata.server.service;


import com.github.pagehelper.PageInfo;
import com.basedata.common.dto.DictionaryItemDto;
import com.basedata.common.query.BaseDictionaryQuery;
import com.basedata.common.vo.DictionaryItemVO;
import com.basedata.server.query.DictionaryItemQuery;

import java.io.Serializable;
import java.util.List;


public interface DictionaryItemService {

    /**
     * 分页查询
     * @param dictionaryItemQuery
     * @return
     */
    PageInfo<DictionaryItemVO> queryPageList(DictionaryItemQuery dictionaryItemQuery) throws Exception;

    List<DictionaryItemVO> queryNoPage(DictionaryItemQuery query) throws Exception;

    Integer insert(DictionaryItemDto DictionaryItemDto) throws Exception;

    DictionaryItemDto queryById(Serializable id) throws Exception;

    Integer updateById(DictionaryItemDto dictionaryItemDto) throws Exception;

    void updateBatchById(List<DictionaryItemDto> dictionaryItemDtoList) throws Exception;

    void insertBatch(List<DictionaryItemDto> dictionaryItemDtoList) throws Exception;

    DictionaryItemDto queryDictionaryItemByCode(BaseDictionaryQuery dictionaryInfoQuery);
}

