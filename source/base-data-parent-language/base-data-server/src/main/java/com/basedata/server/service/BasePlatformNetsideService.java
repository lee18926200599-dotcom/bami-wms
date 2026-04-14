package com.basedata.server.service;

import com.github.pagehelper.PageInfo;
import com.basedata.common.dto.BasePlatformNetsideReqDTO;
import com.basedata.server.entity.BasePlatformNetside;
import com.basedata.server.query.BasePlatformNetsideQueryVo;
import com.basedata.server.vo.BasePlatformNetsideVo;

import java.util.List;

/**
 * <p>
 * 平台网点信息 服务类
 * </p>
 */
public interface BasePlatformNetsideService {
    /**
     * 新增
     *
     * @param reqDTO
     * @return
     */
    void save(BasePlatformNetsideReqDTO reqDTO);

    /**
     * 更新
     *
     * @param reqDTO
     * @return
     */
    void update(BasePlatformNetsideReqDTO reqDTO);

    /**
     * 新增或者更新
     *
     * @param reqDTO
     * @return
     */
    void saveOrUpdate(BasePlatformNetsideReqDTO reqDTO);

    /**
     * 分页查询
     *
     * @param queryVo
     * @return
     */
    PageInfo<BasePlatformNetsideVo> queryPageList(BasePlatformNetsideQueryVo queryVo) throws Exception;

    /**
     * 查询列表（不分页）
     *
     * @param queryVo
     * @return
     */
    List<BasePlatformNetside> queryList(BasePlatformNetsideQueryVo queryVo);

}
