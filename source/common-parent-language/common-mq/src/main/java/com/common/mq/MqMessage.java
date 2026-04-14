package com.common.mq;

import lombok.Data;

import java.io.Serializable;

@Data
public class MqMessage<T> implements Serializable {

    /**
     * 业务对象
     */
    private T data;

    /**
     * 参考id
     */
    private Long referenceId;

    /**
     * 参考单号
     */
    private String referenceNo;

    /**
     * 交换机
     */
    private String exchange;

    /**
     * 队列
     */
    private String routingKey;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 用户信息
     */
    private String userEntity;

    /**
     * 仓库Id
     */
    private Long warehouseId;

}
