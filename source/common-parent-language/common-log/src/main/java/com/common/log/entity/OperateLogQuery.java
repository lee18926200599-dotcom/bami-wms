package com.common.log.entity;

import lombok.Data;

@Data
public class OperateLogQuery {

    /**
     * 所属模块
     */
    private String module;

    /**
     * 参考id
     */
    private Long referenceId;

}
