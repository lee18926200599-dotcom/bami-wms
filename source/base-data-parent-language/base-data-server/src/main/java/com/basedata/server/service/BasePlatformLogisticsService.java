package com.basedata.server.service;

import com.github.pagehelper.PageInfo;
import com.basedata.common.dto.BasePlatformLogisticsDetailDTO;
import com.basedata.common.dto.PlatformLogisticsRelationshipDTO;
import com.basedata.common.dto.PlatformLogisticsRelationshipReqDTO;
import com.basedata.common.dto.UpdateStatusDto;
import com.basedata.common.query.LogisticsDetailQuery;
import com.basedata.server.dto.BasePlatformLogisticsDTO;
import com.basedata.server.dto.BasePlatformLogisticsDetailReqDTO;
import com.basedata.server.dto.BasePlatformLogisticsUpdateReqDTO;
import com.basedata.server.entity.BasePlatformLogistics;
import com.basedata.server.entity.BasePlatformLogisticsDetail;
import com.basedata.server.query.BasePlatformLogisticsQueryVo;
import com.basedata.server.vo.BasePlatformLogisticsVo;

import java.util.List;

/**
 * <p>
 * 承运商编码对照关系表 服务类
 * </p>
 */
public interface BasePlatformLogisticsService {
    /**
     * 批量保存配置
     *
     * @param dto
     * @return
     */
    void batchSaveConfig(BasePlatformLogisticsUpdateReqDTO dto);

    /**
     * 分页查询
     *
     * @param queryVo
     * @return
     */
    PageInfo<BasePlatformLogisticsVo> queryPageList(BasePlatformLogisticsQueryVo queryVo) throws Exception;


    /**
     * 明细查询
     *
     * @param configId
     * @return
     */
    BasePlatformLogisticsDTO queryDetailList(Long configId) throws Exception;

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    boolean deleteByIds(List<Long> ids);

    /**
     * 批量启用停用
     *
     * @param statusDto
     * @return
     */
    boolean batchEnableOrDisable(UpdateStatusDto statusDto);

    /**
     * 根据 仓储服务商、配送方式、承运商ID 找唯一一条主配置
     *
     * @param serviceProviderId
     * @param deliveryType
     * @param logisticsId
     * @return 1、存在，返回对象；2、不存在，返回null；3、存在2条以上，抛异常
     */
    BasePlatformLogistics findOneHdr(Long serviceProviderId, Integer deliveryType, Long logisticsId);

    /**
     * 查找 仓储服务商、配送方式、系统承运商ID 找唯一一条主配置
     * configType = 1：电商平台、电商平台编码
     * configType = 2：仓库ID、货主ID、外部系统承运商编码
     *
     * @param detailReqDTO
     * @return 1、存在，返回对象；2、不存在，返回null；3、存在2条以上，抛异常
     */
    BasePlatformLogisticsDetail findOneDtl(BasePlatformLogisticsDetailReqDTO detailReqDTO);

    /**
     * 查询承运商对照关系明细
     *
     * @param detailReqDTO
     * @return
     */
    List<BasePlatformLogisticsDetail> findDetailList(BasePlatformLogisticsDetailReqDTO detailReqDTO);

    List<BasePlatformLogisticsDetailDTO> queryLogisticsByCondition(LogisticsDetailQuery query);

    /**
     * 查询电商平台下的承运商编码
     *
     * @param relationshipReqDTO
     * @return
     */
    List<PlatformLogisticsRelationshipDTO> queryPlatformLogistics(PlatformLogisticsRelationshipReqDTO relationshipReqDTO);

}
