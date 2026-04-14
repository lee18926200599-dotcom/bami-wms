package com.basedata.server.service;

import com.basedata.common.dto.AreaInfoDto;
import com.basedata.common.query.AreaInfoQuery;

import java.io.Serializable;
import java.util.List;

/**
 *  行政区域表
 */
public interface AreaInfoService {


    /**
     * 不分页
     *
     * @param query
     * @return
     * @throws Exception
     */
    List<AreaInfoDto> queryNoPage(AreaInfoQuery query) throws Exception;


    /**
     * 保存
     *
     * @param
     * @return
     * @throws Exception
     */
    Integer insert(AreaInfoDto areaInfoDto) throws Exception;

    /**
     * 查询
     *
     * @param id
     * @return
     * @throws Exception
     */
    AreaInfoDto queryById(Serializable id) throws Exception;



    /**
     * 修改
     *
     * @param
     * @return
     * @throws Exception
     */
    Integer updateById(AreaInfoDto areaInfoDto) throws Exception;


    /**
     * 批量更新
     *
     * @param
     * @return
     * @throws Exception
     */
    void updateBatchById(List<AreaInfoDto> areaInfoDtoList) throws Exception;

    /**
     * 批量保存
     *
     * @param
     * @return
     * @throws Exception
     */
    void insertBatch(List<AreaInfoDto> areaInfoDtoList) throws Exception;

}

