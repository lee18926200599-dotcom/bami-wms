
package com.basedata.server.dto.waybill.template.dy;

import lombok.Data;

/**
 * 模版信息
 */
@Data
public class TemplateInfo {
    /* 模版id */
    private Integer template_id;
    /* 模版编码 */
    private String template_code;
    /* 模版名称 */
    private String template_name;
    /* 模版URL */
    private String template_url;
    /* 模版类型； 1-一联单 2-二联单 */
    private Integer template_type;
    /* 预览URL */
    private String perview_url;
    /* 版本 */
    private Integer version;
}
