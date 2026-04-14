package com.basedata.server.service.integration.platform;

import com.basedata.common.vo.BasePlatformPrintTemplateDto;
import com.basedata.server.vo.integration.ShopNetsiteVo;
import com.basedata.server.vo.integration.ShopQueryVo;
import com.basedata.server.vo.integration.SyncFaceOrderTemplateReqVo;

import java.util.List;

/**
 * 电商平台接口
 */
public interface PlatformMethodService {

    /**
     * 获取网点信息
     *
     * @param dto
     * @return
     */
    List<ShopNetsiteVo> getNetsideAddress(ShopQueryVo dto) throws Exception;

    /**
     * 获取打印模板列表
     *
     * @param reqVo
     * @return
     */
    List<BasePlatformPrintTemplateDto> getFaceOrderTemplate(SyncFaceOrderTemplateReqVo reqVo) throws Exception;
}
