
package com.basedata.server.dto.waybill.template.wph;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *  获取打印模板列表
 */
@Data
public class WphTemplateReq {

    private RequestDTO request;

    @Data
    public static class RequestDTO {
        @JsonProperty("header")
        private HeaderDTO header;
        @JsonProperty("ownerId")
        private String ownerId;

        @Data
        public static class HeaderDTO {
            @JsonProperty("requestTime")
            private Long requestTime;
        }
    }
}
