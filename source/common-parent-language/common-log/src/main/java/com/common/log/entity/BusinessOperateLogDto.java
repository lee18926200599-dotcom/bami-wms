package com.common.log.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BusinessOperateLogDto<T> {

    /**
     * 所属流程节点
     */
    private LogFlowEnum logFlowEnum;

    /**
     * 日志内容
     */
    private String logContent;

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
     * 操作人
     */
    private String operateName;

    /**
     * 登陆账号
     */
    private String loginAccount;

    public BusinessOperateLogDto(){};

    public BusinessOperateLogDto(LogFlowEnum logFlowEnum, String logContent, Long referenceId, String referenceNo, String businessNo, String operateName, String loginAccount) {
        this.logFlowEnum = logFlowEnum;
        this.logContent = logContent;
        this.referenceId = referenceId;
        this.referenceNo = referenceNo;
        this.businessNo = businessNo;
        this.operateName = operateName;
        this.loginAccount = loginAccount;
    }
}
