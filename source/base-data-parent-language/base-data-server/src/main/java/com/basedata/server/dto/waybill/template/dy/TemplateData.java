
package com.basedata.server.dto.waybill.template.dy;

import lombok.Data;

import java.util.List;

@Data
public class TemplateData {
    /* 物流公司 */
    private String logistics_code;
    /* 模版信息 */
    private List<TemplateInfo> template_infos;
}
