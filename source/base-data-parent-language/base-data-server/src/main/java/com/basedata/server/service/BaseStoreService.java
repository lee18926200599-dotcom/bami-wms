package com.basedata.server.service;

import com.github.pagehelper.PageInfo;
import com.basedata.common.dto.DeleteDto;
import com.basedata.common.dto.UpdateStatusDto;
import com.basedata.common.vo.BasePlatformStoreVo;
import com.basedata.common.vo.BaseStoreDetailVo;
import com.basedata.common.vo.BaseStorePageVo;
import com.basedata.common.vo.BaseStoreVo;
import com.basedata.server.dto.BaseStoreDto;
import com.basedata.server.query.BaseStoreMultiQuery;
import com.basedata.server.query.BaseStoreQuery;
import com.basedata.server.query.StoreQuery;

import java.util.List;

public interface BaseStoreService {

    /**
     * 新增店铺
     *
     * @param baseStoreDto
     */
    void save(BaseStoreDto baseStoreDto);

    /**
     * 修改店铺
     *
     * @param baseStoreDto
     */
    void update(BaseStoreDto baseStoreDto);


    /**
     * 店铺列表
     *
     * @param storeQuery
     * @return
     */
    PageInfo<BaseStorePageVo> page(StoreQuery storeQuery);

    /**
     * 启用禁用
     *
     * @param statusDto
     */
    void updateStatus(UpdateStatusDto statusDto);

    /**
     * 店铺详情
     *
     * @param id
     * @return
     */
    BaseStoreDetailVo detail(Long id);

    /**
     * 删除店铺
     *
     * @param deleteDto
     */
    void delete(DeleteDto deleteDto);

    /**
     * 根据条件查询店铺信息
     *
     * @param baseStoreQuery
     * @return
     */
    List<BaseStoreVo> getStoreList(BaseStoreQuery baseStoreQuery);

    /**
     * 多选多条件查询店铺
     * @param baseStoreQuery
     * @return
     */
    List<BaseStoreVo> getMultiStoreList(BaseStoreMultiQuery baseStoreQuery);

    /**
     * 根据平台获取店铺及所属货主
     * @param platformCode
     * @return
     */
    List<BasePlatformStoreVo> platformStore(String platformCode);
}
