package com.basedata.server.dto.waybill.template.ks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class KsCloudprintCustomTemplatesGetResponse implements Serializable {

    private Integer result;
    private String error_msg;
    private List<Data> data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @lombok.Data
    public static class Data implements Serializable {
        /**
         * customTemplateName : xxx自定义模板
         * customTemplateCode : CTC1000
         * standardTemplateCode : ST1000
         * customTemplateUrl : http://sssss
         * expressCompanyName : 顺丰
         * type : 1
         * expressCompanyCode : SF
         * placeholderKeys : []
         */

        private String customTemplateName;
        private String customTemplateCode;
        private String standardTemplateCode;
        private String customTemplateUrl;
        private String expressCompanyName;
        private Integer type;
        private String expressCompanyCode;
        private List<?> placeholderKeys;
    }
}
