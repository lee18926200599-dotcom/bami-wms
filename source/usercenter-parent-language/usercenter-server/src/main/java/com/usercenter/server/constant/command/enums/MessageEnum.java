package com.usercenter.server.constant.command.enums;

/**
 * 消息类枚举
 */
public enum  MessageEnum {
    /**
     * 用户重置密码
     */
    USER_RET_PASSWORD("sms","ucenter_code");


    private String messageType;


    /**
     * 模板编码
     */
    private String templateCode;


    public String getMessageType() {
        return messageType;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    MessageEnum(String messageType, String templateCode) {
        this.messageType = messageType;
        this.templateCode = templateCode;
    }
}
