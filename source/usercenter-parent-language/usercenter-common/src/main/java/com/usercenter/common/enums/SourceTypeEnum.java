package com.usercenter.common.enums;

/**
 * 登录系统来源
 */
public enum SourceTypeEnum {
    /**
     * pc
     */
    PC(0),
    /**
     * app
     */
    APP(1),
    /**
     * rf
     */
    RF(2);

    private SourceTypeEnum(int code) {
        this.code = code;
    }

    private int code;

    public int getCode() {
        return code;
    }


    /**
     * 枚举转换
     *
     * @param code
     * @return
     */
    public static SourceTypeEnum getEnum(int code) {
        SourceTypeEnum[] values = SourceTypeEnum.values();
        for (SourceTypeEnum sourceTypeEnum : values) {
            if (sourceTypeEnum.code == code) {
                return sourceTypeEnum;
            }
        }
        return null;
    }
    public static SourceTypeEnum parser(String sourceType) {
        SourceTypeEnum[] values = SourceTypeEnum.values();
        for (SourceTypeEnum sourceTypeEnum : values) {
            if (sourceType.equals(sourceTypeEnum.toString())) {
                return sourceTypeEnum;
            }
        }
        return null;
    }
}
