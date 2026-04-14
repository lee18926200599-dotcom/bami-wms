package com.basedata.server.dto.netside;

import lombok.Data;

@Data
public class WxxdAccountGetRequest {

    /**
     * 快递公司编码
     */
    private String delivery_id;

    /**
     * 是否需要查询库存
     */
    private Boolean need_balance;

    /**
     * 状态过滤
     */
    private Integer status;

    /**
     * 分页起始位置，默认0
     */
    private Integer offset;

    /**
     * 单次请求数量
     */
    private Integer limit;

}
