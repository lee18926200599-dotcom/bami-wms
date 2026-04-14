
package com.basedata.server.dto.waybill.template.dy;

import lombok.Data;


@Data
public class DyStdTemplateResponse {
    private Long code;
    private ResponseData data;
    private String log_id;
    private String msg;
    private String sub_code;
    private String sub_msg;
}
