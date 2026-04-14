
package com.basedata.server.dto.waybill.template.xhs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class XhsCloudprintStdtemplatesGetResponse {


    @JsonProperty("data")
    private DataDTO data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("error_msg")
        private String errorMsg;
        @JsonProperty("data")
        private DataDTO.TemplateDTO data;
        @JsonProperty("success")
        private Boolean success;
        @JsonProperty("error_code")
        private Integer errorCode;

        @NoArgsConstructor
        @Data
        public static class TemplateDTO {
            @JsonProperty("templateList")
            private List<DataDTO.TemplateDTO.TemplateListDTO> templateList;

            @NoArgsConstructor
            @Data
            public static class TemplateListDTO {
                /**
                 * 模板id，取号的时候传入此值
                 */
                @JsonProperty("id")
                private Integer id;
                @JsonProperty("cpCode")
                private String cpCode;
                /**
                 * 品牌，仅部分快递支持
                 */
                @JsonProperty("brandCode")
                private String brandCode;
                /**
                 * 模板尺寸，76*130-一联，100*150-二联，100*180-三联
                 */
                @JsonProperty("templateType")
                private String templateType;
                @JsonProperty("templateName")
                private String templateName;
                @JsonProperty("templateDesc")
                private String templateDesc;
                @JsonProperty("standardTemplateUrl")
                private String standardTemplateUrl;
                @JsonProperty("customerTemplateUrl")
                private String customerTemplateUrl;
                /**
                 * 自定义类型 0-标准 1-订单号 2-商品名称/规格/数量 3-商品名称/规格/数量 + 买家留言 + 商家备注 4-订单号 + 商品名称/规格/数量 + 买家留言 + 商家备注
                 */
                @JsonProperty("templateCustomerType")
                private Integer templateCustomerType;
                @JsonProperty("templatePreviewUrl")
                private String templatePreviewUrl;
            }
        }
    }
}
