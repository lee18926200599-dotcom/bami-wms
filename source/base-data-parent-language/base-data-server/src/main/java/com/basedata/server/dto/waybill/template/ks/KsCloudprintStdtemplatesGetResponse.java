
package com.basedata.server.dto.waybill.template.ks;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 快手电商开放平台-快手电商官网  https://open.kwaixiaodian.com/zone/docs/api?name=open.express.standard.template.list.get&version=1
 */
@NoArgsConstructor
@Data
public class KsCloudprintStdtemplatesGetResponse {


    @JsonProperty("data")
    private DataDTO data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("result")
        private Integer result;
        @JsonProperty("msg")
        private String msg;
        @JsonProperty("error_msg")
        private String errorMsg;
        @JsonProperty("code")
        private String code;
        @JsonProperty("data")
        private List<DataDTO.TempalteDTO> data;
        @JsonProperty("requestId")
        private String requestId;
        @JsonProperty("sub_msg")
        private String subMsg;
        @JsonProperty("sub_code")
        private String subCode;

        @NoArgsConstructor
        @Data
        public static class TempalteDTO {
            /**
             * 运单类型，枚举值：
             * (1, "快递标准面单(二联单)"),
             * (2, "快递三联面单"),
             * (3, "快递便携式三联单"),
             * (4, "快运标准面单"),
             * (5, "快运三联面单"),
             * (6, "快递一联单")
             */
            @JsonProperty("waybillType")
            private Integer waybillType;
            @JsonProperty("templateName")
            private String templateName;
            @JsonProperty("expressCompanyCode")
            private String expressCompanyCode;
            /**
             * 模板code，模板唯一值
             */
            @JsonProperty("templateCode")
            private String templateCode;
            @JsonProperty("templateExampleUrl")
            private String templateExampleUrl;
            @JsonProperty("templateUrl")
            private String templateUrl;
        }
    }
}
