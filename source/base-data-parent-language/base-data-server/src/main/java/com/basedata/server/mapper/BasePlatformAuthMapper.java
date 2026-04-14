package com.basedata.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.basedata.common.dto.BasePlatformAuthReqDTO;
import com.basedata.common.dto.GeneralBatchUpdateByIdsDto;
import com.basedata.server.entity.BasePlatformAuth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 货主平台授权信息表 Mapper 接口
 * </p>
 */
@Mapper
public interface BasePlatformAuthMapper extends BaseMapper<BasePlatformAuth> {
    /**
     * 查询
     *
     * @param reqDTO
     * @return
     */
    List<BasePlatformAuth> findList(BasePlatformAuthReqDTO reqDTO);

    /**
     * 批量更新操作
     *
     * @param updateByIdsDto
     * @return
     */
    int batchUpdate(GeneralBatchUpdateByIdsDto updateByIdsDto);

    /**
     * 更新（授权信息）
     *
     * @param entity
     * @return
     */
    int updateById(BasePlatformAuth entity);

    /**
     * 保存
     *
     * @param entity
     */
    int insert(BasePlatformAuth entity);
}
