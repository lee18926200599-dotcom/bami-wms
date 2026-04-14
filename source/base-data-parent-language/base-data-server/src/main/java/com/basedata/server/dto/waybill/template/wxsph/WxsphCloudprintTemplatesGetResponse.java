package com.basedata.server.dto.waybill.template.wxsph;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class WxsphCloudprintTemplatesGetResponse {


    @JsonProperty("data")
    private DataDTO data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("errcode")
        private Integer errcode;
        @JsonProperty("errmsg")
        private String errmsg;
        @JsonProperty("total_template")
        private List<TotalTemplateDTO> totalTemplate;

        @NoArgsConstructor
        @Data
        public static class TotalTemplateDTO {
            @JsonProperty("delivery_id")
            private String deliveryId;
            @JsonProperty("default_template_id")
            private String defaultTemplateId;
            @JsonProperty("template_list")
            private List<TemplateListDTO> templateList;

            @NoArgsConstructor
            @Data
            public static class TemplateListDTO {
                @JsonProperty("template_id")
                private String templateId;
                @JsonProperty("template_name")
                private String templateName;
                @JsonProperty("template_desc")
                private String templateDesc;
                @JsonProperty("template_type")
                private String templateType;
                @JsonProperty("create_time")
                private Integer createTime;
                @JsonProperty("update_time")
                private Integer updateTime;
                @JsonProperty("is_default")
                private Boolean isDefault;
                @JsonProperty("options")
                private List<OptionsDTO> options;

                @NoArgsConstructor
                @Data
                public static class OptionsDTO {
                    @JsonProperty("option_id")
                    private Integer optionId;
                    @JsonProperty("font_size")
                    private Integer fontSize;
                    @JsonProperty("is_bold")
                    private Boolean isBold;
                    @JsonProperty("is_open")
                    private Boolean isOpen;
                }
            }
        }
    }
}
