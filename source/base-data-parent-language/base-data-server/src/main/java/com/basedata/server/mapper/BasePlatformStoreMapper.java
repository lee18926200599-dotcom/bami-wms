package com.basedata.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.basedata.common.dto.GeneralBatchUpdateByIdsDto;
import com.basedata.server.dto.BasePlatformStoreReqDTO;
import com.basedata.server.entity.BasePlatformStore;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 快递面单模板授权店铺 Mapper 接口
 * </p>
 */
@Mapper
public interface BasePlatformStoreMapper extends BaseMapper<BasePlatformStore> {
    /**
     * 分页查询
     *
     * @param reqDTO
     * @return
     */
    List<BasePlatformStore> findList(BasePlatformStoreReqDTO reqDTO);

    /**
     * 批量更新操作
     *
     * @param updateByIdsDto
     * @return
     */
    int batchUpdate(GeneralBatchUpdateByIdsDto updateByIdsDto);

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    int updateById(BasePlatformStore entity);


}
