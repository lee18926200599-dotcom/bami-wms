package com.basedata.server.service;

import com.github.pagehelper.PageInfo;
import com.basedata.common.dto.UpdateStatusDto;
import com.basedata.server.dto.BaseStorePrintTemplateDTO;
import com.basedata.server.dto.BaseStorePrintTemplateDetailDTO;
import com.basedata.server.dto.BaseStorePrintTemplateUpdateReqDTO;
import com.basedata.server.entity.BaseStorePrintTemplate;
import com.basedata.server.query.BaseStorePrintTemplateQueryVo;
import com.basedata.server.vo.BaseStorePrintTemplateReqVo;

import java.util.List;

/**
 * <p>
 * 快递面单模版配置 服务类
 * </p>
 */
public interface BaseStorePrintTemplateService {
    /**
     * 新增
     *
     * @param updateReqDTO
     * @return
     */
    void batchSaveConfig(BaseStorePrintTemplateUpdateReqDTO updateReqDTO);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    boolean deleteByIds(List<Long> ids);

    /**
     * 批量启用停用主配置
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
    PageInfo<BaseStorePrintTemplate> queryPageList(BaseStorePrintTemplateQueryVo queryVo);


    /**
     * 查询明细列表（根据配置项主ID查）
     *
     * @param configId
     * @return
     */
    BaseStorePrintTemplateDTO queryDetailList(Long configId);

    /**
     * 查询面单模板列表（标准、自定义）
     *
     * @param reqVo
     * @return
     */
    List<BaseStorePrintTemplateDetailDTO> queryTemplateDetailList(BaseStorePrintTemplateReqVo reqVo);

}
