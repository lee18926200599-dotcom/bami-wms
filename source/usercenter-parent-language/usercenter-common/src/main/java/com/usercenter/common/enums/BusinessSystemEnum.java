package com.usercenter.common.enums;

/**
 * 系统类型枚举
 */
public enum BusinessSystemEnum {
    CRM(0),
    WMS(1),
    TMS(2),
    SCM(3),
    OMS(4),
    ORGANIZATION(5),
    PERMISSION(6),
    BOSS(7),
    PLATFORM(8),
    OSS(9),
    ;

    private int code;

    BusinessSystemEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    /**
     * 枚举转换
     *
     * @param code
     * @return
     */
    public static BusinessSystemEnum getEnum(int code) {
        BusinessSystemEnum[] values = BusinessSystemEnum.values();
        for (BusinessSystemEnum businessSystemEnum : values) {
            if (businessSystemEnum.code == code) {
                return businessSystemEnum;
            }
        }
        return null;
    }
    public static BusinessSystemEnum parser(String business) {
        BusinessSystemEnum[] values = BusinessSystemEnum.values();
        for (BusinessSystemEnum businessSystemEnum : values) {
            if (business.equals(businessSystemEnum.toString())) {
                return businessSystemEnum;
            }
        }
        return null;
    }
}
