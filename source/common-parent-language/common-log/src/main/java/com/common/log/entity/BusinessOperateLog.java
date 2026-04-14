package com.common.log.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "BusinessOperateLog")
public class BusinessOperateLog {

    @Id
    private String id;

    /**
     * 所属流程节点
     */
    private Integer node;

    /**
     * 参考id
     */
    private Long referenceId;

    /**
     * 参考单号
     */
    private String referenceNo;

    /**
     * 业务单号
     */
    private String businessNo;

    /**
     * 日志内容
     */
    private String logContent;

    /**
     * 操作人
     */
    private String operateName;

    /**
     * 登陆账号
     */
    private String loginAccount;

    /**
     * 操作时间
     */
    private String operateTime;
}
