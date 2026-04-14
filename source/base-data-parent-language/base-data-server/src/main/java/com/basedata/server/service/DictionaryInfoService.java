package com.basedata.server.service;


import com.basedata.server.query.DictionaryInfoQuery;
import com.github.pagehelper.PageInfo;
import com.basedata.common.dto.DictionaryInfoDto;
import com.basedata.common.query.BaseDictionaryBatchQuery;
import com.basedata.common.query.BaseDictionaryQuery;
import com.basedata.common.vo.BaseBatchDictionaryVo;
import com.basedata.common.vo.BaseDictionaryVo;

import java.io.Serializable;
import java.util.List;


public interface DictionaryInfoService {


    /**
     * 分页查询
     * @param dictionaryInfoQuery
     * @return
     */
    PageInfo<DictionaryInfoDto> queryPageList(DictionaryInfoQuery dictionaryInfoQuery) throws Exception;

    List<DictionaryInfoDto> queryNoPage(DictionaryInfoQuery query, Integer pageSize)throws Exception;

    Integer insert(DictionaryInfoDto DictionaryInfoDto) throws Exception;

    DictionaryInfoDto queryById(Serializable id) throws Exception;

    Integer updateById(DictionaryInfoDto dictionaryInfoDto) throws Exception;

    void updateBatchById(List<DictionaryInfoDto> dictionaryInfoDtoList) throws Exception;

    void insertBatch(List<DictionaryInfoDto> dictionaryInfoDtoList) throws Exception;

    /**
     * 字典查询
     *
     * @param dictionaryInfoQuery
     * @return
     */
    List<BaseDictionaryVo> queryDictionary(BaseDictionaryQuery dictionaryInfoQuery);

    /**
     * 查询多个字典
     *
     * @param dictionaryInfoQuery
     * @return
     */
    List<BaseBatchDictionaryVo> queryDictionaryList(BaseDictionaryBatchQuery dictionaryInfoQuery);
}

