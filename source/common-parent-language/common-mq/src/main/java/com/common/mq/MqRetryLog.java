package com.common.mq;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "MqRetryLog")
public class MqRetryLog {

    @Id
    private String id;

    /**
     * 仓库Id
     */
    private Long warehouseId;

    /**
     * 所属类型
     */
    private String type;

    /**
     * mq消息id
     */
    private String msgId;

    /**
     * 交换机
     */
    private String exchange;

    /**
     * 队列
     */
    private String routingKey;

    /**
     * 参考id
     */
    private Long referenceId;

    /**
     * 参考单号
     */
    private String referenceNo;

    /**
     * 日志内容
     */
    private String mqContent;

    /**
     * 重试次数
     */
    private Integer retryCount;

    private String className;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 错误异常备注
     */
    private String remark;

    /**
     * 下次执行时间
     */
    private String nextTime;
    /**
     * 新增时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;
}
