
package com.basedata.server.dto.waybill.template.jdwl;

import lombok.Data;

import java.util.List;

/**
 *  自定义区资源
 */
@Data
public class CustomAreaListDto {
    /**
     * 承运商编码，长度2-10
     */
    private String cpCode;
    /**
     * 对应承运商面单模板的自定义区资源列表
     */
    private List<CustomAreaDto> templates;
}
