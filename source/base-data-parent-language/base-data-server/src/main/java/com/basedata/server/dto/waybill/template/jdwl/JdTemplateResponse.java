
package com.basedata.server.dto.waybill.template.jdwl;

import lombok.Data;

/**
 *  获取打印模板列表-京东物流
 * 京东物流开放平台  https://cloud.jdl.com/#/open-business-document/api-doc/157/1903
 */
@Data
public class JdTemplateResponse {

    /**
     * 状态码，1000代表成功。参照下文错误码解释。长度1-10
     */
    private Long code;
    /**
     * 状态码信息说明，参照下文错误码解释。长度255
     */
    private String message;
    /**
     * 模板数据资源，当状态码不为1000时此项不返回。
     */
    private TemplateListResultDto data;
}
