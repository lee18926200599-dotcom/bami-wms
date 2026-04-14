
package com.basedata.server.dto.waybill.template.jd;

import lombok.Data;

/**
 * 获取打印模板列表-京东物流
 * <p>
 * 京东物流开放平台  https://cloud.jdl.com/#/open-business-document/api-doc/157/1903
 */
@Data
public class JdTemplateReq {


    private ParamDto param1;

    public static class ParamDto{

    }
}
