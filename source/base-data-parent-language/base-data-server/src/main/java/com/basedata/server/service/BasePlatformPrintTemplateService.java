package com.basedata.server.service;


import com.basedata.common.vo.BasePlatformPrintTemplateDto;
import com.basedata.server.dto.BasePlatformPrintTemplateReqDTO;
import com.basedata.server.entity.BasePlatformPrintTemplate;

import java.util.List;

/**
 * <p>
 * 平台面单模版 服务类
 * </p>
 */
public interface BasePlatformPrintTemplateService {

    /**
     * 根据条件获取面单模板信息
     *
     * @param dto
     * @return
     */
    List<BasePlatformPrintTemplateDto> queryPlatformPrintTemplate(BasePlatformPrintTemplateReqDTO dto);

    /**
     * 批量保存或者更新电商平台面单模板
     *
     * @param templateList
     * @return
     */
    int batchSaveOrUpdate(List<BasePlatformPrintTemplate> templateList);
}
