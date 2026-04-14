package com.basedata.server.dto.waybill.template.dy;

import lombok.Data;

import java.util.List;

@Data
public class ResponseData {
    /* 模版数据 */
    private List<TemplateData> template_data;
}
