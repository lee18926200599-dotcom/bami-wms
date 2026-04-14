package com.basedata.server.dto.waybill.template.wph;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class WphCloudprintTemplatesGetResponse {


    @JsonProperty("request")
    private RequestDTO request;
    @JsonProperty("data")
    private DataDTO data;

    @NoArgsConstructor
    @Data
    public static class RequestDTO {
        @JsonProperty("header")
        private HeaderDTO header;
        @JsonProperty("ownerId")
        private String ownerId;

        @NoArgsConstructor
        @Data
        public static class HeaderDTO {
            @JsonProperty("requestTime")
            private Long requestTime;
        }
    }

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("returnCode")
        private String returnCode;
        @JsonProperty("result")
        private ResultDTO result;

        @NoArgsConstructor
        @Data
        public static class ResultDTO {
            @JsonProperty("header")
            private HeaderDTO header;
            @JsonProperty("model")
            private ModelDTO model;

            @NoArgsConstructor
            @Data
            public static class HeaderDTO {
                @JsonProperty("hostIp")
                private String hostIp;
                @JsonProperty("costTime")
                private String costTime;
                @JsonProperty("resultCode")
                private String resultCode;
                @JsonProperty("resultMsg")
                private String resultMsg;
            }

            @NoArgsConstructor
            @Data
            public static class ModelDTO {
                @JsonProperty("templates")
                private List<TemplatesDTO> templates;
                @JsonProperty("ownerId")
                private String ownerId;

                @NoArgsConstructor
                @Data
                public static class TemplatesDTO {
                    @JsonProperty("platformTemplateURL")
                    private String platformTemplateURL;
                    @JsonProperty("platformTemplateName")
                    private String platformTemplateName;
                    @JsonProperty("templateWidth")
                    private Double templateWidth;
                    @JsonProperty("templateName")
                    private String templateName;
                    @JsonProperty("templateHeight")
                    private Double templateHeight;
                    @JsonProperty("carrierCode")
                    private String carrierCode;
                    @JsonProperty("templateIdStr")
                    private String templateIdStr;
                    @JsonProperty("templateId")
                    private Long templateId;
                    @JsonProperty("customRegionLayout")
                    private CustomRegionLayoutDTO customRegionLayout;
                    @JsonProperty("customDataKey")
                    private List<String> customDataKey;
                    @JsonProperty("decryJson")
                    private String decryJson;
                    @JsonProperty("templateUrl")
                    private String templateUrl;

                    @NoArgsConstructor
                    @Data
                    public static class CustomRegionLayoutDTO {
                        @JsonProperty("top")
                        private Double top;
                        @JsonProperty("left")
                        private Double left;
                        @JsonProperty("width")
                        private Double width;
                        @JsonProperty("height")
                        private Double height;
                    }
                }
            }
        }
    }
}
