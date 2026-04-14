package com.basedata.server.dto.waybill.template.pdd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class PddCloudprintStdtemplatesGetResponse {


    @JsonProperty("data")
    private DataDTO data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("pdd_cloudprint_stdtemplates_get_response")
        private PddCloudprintStdtemplatesGetResponseDTO pddCloudprintStdtemplatesGetResponse;

        @NoArgsConstructor
        @Data
        public static class PddCloudprintStdtemplatesGetResponseDTO {
            @JsonProperty("result")
            private ResultDTO result;
            @JsonProperty("request_id")
            private String requestId;

            @NoArgsConstructor
            @Data
            public static class ResultDTO {
                @JsonProperty("datas")
                private List<DatasDTO> datas;

                @NoArgsConstructor
                @Data
                public static class DatasDTO {
                    @JsonProperty("standard_templates")
                    private List<StandardTemplatesDTO> standardTemplates;
                    @JsonProperty("wp_code")
                    private String wpCode;

                    @NoArgsConstructor
                    @Data
                    public static class StandardTemplatesDTO {
                        @JsonProperty("standard_template_name")
                        private String standardTemplateName;
                        @JsonProperty("standard_template_url")
                        private String standardTemplateUrl;
                        @JsonProperty("standard_waybill_type")
                        private Integer standardWaybillType;
                        @JsonProperty("standard_template_id")
                        private Integer standardTemplateId;
                    }
                }
            }
        }
    }
}
