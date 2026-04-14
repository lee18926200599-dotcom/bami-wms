
package com.basedata.server.dto.waybill.template.jd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class JdCloudprintStdtemplatesGetResponse {


    @JsonProperty("param1")
    private Param1DTO param1;
    @JsonProperty("data")
    private DataDTO data;

    @NoArgsConstructor
    @Data
    public static class Param1DTO {
    }

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("jingdong_printing_template_getTemplateList_responce")
        private JingdongPrintingTemplateGetTemplateListResponceDTO jingdongPrintingTemplateGettemplatelistResponce;

        @NoArgsConstructor
        @Data
        public static class JingdongPrintingTemplateGetTemplateListResponceDTO {
            @JsonProperty("code")
            private String code;
            @JsonProperty("returnType")
            private ReturnTypeDTO returnType;

            @NoArgsConstructor
            @Data
            public static class ReturnTypeDTO {
                @JsonProperty("code")
                private String code;
                @JsonProperty("message")
                private String message;
                @JsonProperty("datas")
                private DatasDTO datas;

                @NoArgsConstructor
                @Data
                public static class DatasDTO {
                    @JsonProperty("udiyDatas")
                    private List<?> udiyDatas;
                    @JsonProperty("uDatas")
                    private List<?> uDatas;
                    @JsonProperty("diyDatas")
                    private List<DiyDatasDTO> diyDatas;
                    @JsonProperty("sDatas")
                    private List<SDatasDTO> sDatas;

                    @NoArgsConstructor
                    @Data
                    public static class DiyDatasDTO {
                        @JsonProperty("resourceId")
                        private Integer resourceId;
                        @JsonProperty("resourceContent")
                        private String resourceContent;
                        @JsonProperty("resourceUrl")
                        private String resourceUrl;
                        @JsonProperty("resourceName")
                        private String resourceName;
                        @JsonProperty("resourceType")
                        private String resourceType;
                    }

                    @NoArgsConstructor
                    @Data
                    public static class SDatasDTO {
                        @JsonProperty("cpCode")
                        private String cpCode;
                        @JsonProperty("standardTemplates")
                        private List<StandardTemplatesDTO> standardTemplates;

                        @NoArgsConstructor
                        @Data
                        public static class StandardTemplatesDTO {
                            @JsonProperty("standardTemplateId")
                            private Integer standardTemplateId;
                            @JsonProperty("standardWaybillType")
                            private String standardWaybillType;
                            @JsonProperty("standardTemplateUrl")
                            private String standardTemplateUrl;
                            @JsonProperty("standardTemplateName")
                            private String standardTemplateName;
                        }
                    }
                }
            }
        }
    }
}
