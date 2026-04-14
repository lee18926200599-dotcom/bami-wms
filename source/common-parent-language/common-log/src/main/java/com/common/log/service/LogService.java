package com.common.log.service;


import com.common.log.entity.*;

import java.util.List;

public interface LogService {

    void saveLog(OperateLogDto operateLogDto);

    void saveLogBatch(List<OperateLogDto> list);

    void saveBusinessLogBatch(List<BusinessOperateLogDto> list);

    List<OperateLog> getOperateLog(OperateLogQuery query);

    List<BusinessOperateLog> getBusinessOperateLog(BusinessOperateLogQuery query);
}
