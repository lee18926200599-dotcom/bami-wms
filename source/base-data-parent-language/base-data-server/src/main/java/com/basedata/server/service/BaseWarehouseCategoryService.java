package com.basedata.server.service;

import com.common.util.message.RestMessage;
import com.basedata.common.dto.BaseWarehouseCategoryDto;
import com.basedata.common.query.WarehouseCategoryQuery;
import com.basedata.common.vo.BaseWarehouseCategoryVO;
import com.basedata.server.entity.BaseWarehouseCategory;

import java.io.Serializable;
import java.util.List;

/**
 * 仓储分类
 */
public interface BaseWarehouseCategoryService {


    /**
     * 不分页
     *
     * @param
     * @param source
     * @return
     * @throws Exception
     */
    List<BaseWarehouseCategoryVO> queryNoPage(String source) throws Exception;


    /**
     * 保存
     *
     * @param
     * @return
     * @throws Exception
     */
    Integer insert(BaseWarehouseCategoryDto baseWarehouseCategoryDto) throws Exception;

    /**
     * 查询
     *
     * @param id
     * @return
     * @throws Exception
     */
    BaseWarehouseCategoryDto queryById(Serializable id) throws Exception;



    /**
     * 修改
     *
     * @param
     * @return
     * @throws Exception
     */
    Integer updateById(BaseWarehouseCategoryDto baseWarehouseCategoryDto) throws Exception;


    /**
     * 批量更新
     *
     * @param
     * @return
     * @throws Exception
     */
    void updateBatchById(List<BaseWarehouseCategoryDto> baseWarehouseCategoryDtoList) throws Exception;

    /**
     * 批量保存
     *
     * @param
     * @return
     * @throws Exception
     */
    void insertBatch(List<BaseWarehouseCategoryDto> baseWarehouseCategoryDtoList) throws Exception;
    /**
     * 启用/停用
     *
     * @param
     * @return
     * @throws Exception
     */
    RestMessage enableOrDisable(BaseWarehouseCategoryDto baseWarehouseCategoryDto) throws Exception;

    RestMessage delete(BaseWarehouseCategoryDto baseWarehouseCategoryDto) throws Exception;

    RestMessage move(Long id, Long id2) throws Exception;

    List<Long> queryCategoryListByParentId(Long id) throws Exception;

    BaseWarehouseCategoryDto queryCategoryById(Long id);

    /**
     * 根据条件查询分类列表
     *
     * @param query
     * @return
     */
    List<BaseWarehouseCategory> queryCategoryList(WarehouseCategoryQuery query);
}

