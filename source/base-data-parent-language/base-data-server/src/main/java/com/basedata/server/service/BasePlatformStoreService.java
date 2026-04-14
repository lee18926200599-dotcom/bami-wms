package com.basedata.server.service;

import com.github.pagehelper.PageInfo;
import com.basedata.common.dto.DeleteDto;
import com.basedata.common.dto.UpdateStatusDto;
import com.basedata.server.entity.BasePlatformStore;
import com.basedata.server.query.BasePlatformStoreQueryVo;
import com.basedata.server.vo.BasePlatformStoreUpdateVo;
import com.basedata.server.vo.BasePlatformStoreVo;

import java.util.List;

/**
 * <p>
 * 快递面单模板授权店铺 服务类
 * </p>
 */
public interface BasePlatformStoreService {
    /**
     * 新增
     *
     * @param updateVo
     * @return
     */
    void save(BasePlatformStoreUpdateVo updateVo);

    /**
     * 更新
     *
     * @param updateVo
     * @return
     */
    void update(BasePlatformStoreUpdateVo updateVo);

    /**
     * 批量删除
     *
     * @param deleteDto
     * @return
     */
    boolean deleteByIds(DeleteDto deleteDto);

    /**
     * 批量启用停用
     *
     * @param statusDto
     * @return
     */
    boolean batchEnableOrDisable(UpdateStatusDto statusDto);

    /**
     * 分页查询
     *
     * @param queryVo
     * @return
     */
    PageInfo<BasePlatformStoreVo> queryPageList(BasePlatformStoreQueryVo queryVo) throws Exception;

    /**
     * 根据电商平台查找有效授权店铺列表
     *
     * @param platformCode
     * @return
     * @throws Exception
     */
    List<BasePlatformStore> getAuthStoreByPlatformCode(String platformCode);
}
