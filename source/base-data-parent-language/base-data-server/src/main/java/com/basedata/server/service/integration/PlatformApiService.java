package com.basedata.server.service.integration;


import com.github.pagehelper.PageInfo;
import com.basedata.server.dto.integration.GetPlatformNetsideAddressReqDto;
import com.basedata.server.dto.integration.GetPlatformNetsideAddressRespDto;
import com.basedata.server.dto.integration.GetPlatformNetsideRespDto;
import com.basedata.server.vo.integration.GetPlatformNetsideReqVo;
import com.basedata.server.vo.integration.SyncFaceOrderTemplateReqVo;

import java.util.List;


public interface PlatformApiService {
    /**
     * 获取快递网点信息
     *
     * @param platformNetsideReqVo
     * @return
     */
    List<GetPlatformNetsideRespDto> getNetsideAddress(GetPlatformNetsideReqVo platformNetsideReqVo);

    /**
     * 获取授权店铺下的快递网点信息
     *
     * @param reqVo
     * @return
     */
    PageInfo<GetPlatformNetsideAddressRespDto> getAuthStoreNetsideAddress(GetPlatformNetsideAddressReqDto reqVo);

    /**
     * 获取电商平台面单模板并同步到本地库
     *
     * @param reqVo
     * @return
     */
    String syncFaceOrderTemplate(SyncFaceOrderTemplateReqVo reqVo);
}
