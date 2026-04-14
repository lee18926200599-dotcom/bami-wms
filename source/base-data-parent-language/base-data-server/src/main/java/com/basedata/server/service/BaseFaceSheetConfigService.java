package com.basedata.server.service;

import com.basedata.common.dto.FacesheetConfigDto;
import com.basedata.common.dto.QueryStoreInfoDto;
import com.basedata.common.vo.FacesheetConfigForPrintVo;
import com.basedata.common.vo.FacesheetConfigVo;

import java.util.List;


public interface BaseFaceSheetConfigService {
    FacesheetConfigVo getConfig(FacesheetConfigDto configDto);

    List<FacesheetConfigForPrintVo> getBatchConfig(QueryStoreInfoDto queryStoreInfoDto);
}
