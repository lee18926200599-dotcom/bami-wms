package com.common.log.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OperateLogDto<T> {

    /**
     * 所属模块
     */
    private LogModuleEnum module;

    /**
     * 操作类型
     */
    private LogOperateEnum operate;

    /**
     * 参考id
     */
    private Long referenceId;

    /**
     * 参考单号
     */
    private String referenceNo;

    /**
     * 操作人
     */
    private String operateName;

    /**
     * 登陆账号
     */
    private String loginAccount;

    /**
     * 历史值
     */
    private T oldBean;

    /**
     * 新值
     */
    private T newBean;

    public OperateLogDto(){};

    public OperateLogDto(LogModuleEnum module, LogOperateEnum operate, Long referenceId,
                         String referenceNo, String operateName,
                         String loginAccount, T oldBean, T newBean) {
        this.module = module;
        this.operate = operate;
        this.referenceId = referenceId;
        this.referenceNo = referenceNo;
        this.operateName = operateName;
        this.loginAccount = loginAccount;
        this.oldBean = oldBean;
        this.newBean = newBean;
    }

}
