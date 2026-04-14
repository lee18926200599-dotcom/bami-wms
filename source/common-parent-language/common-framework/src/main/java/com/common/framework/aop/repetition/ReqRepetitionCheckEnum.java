package com.common.framework.aop.repetition;

/**
 * 重复提供是否报错
 */
public enum ReqRepetitionCheckEnum {

    NO(0, "否"), YES(1, "是");

    private final int code;
    private final String msg;

    ReqRepetitionCheckEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 枚举类型转换，由于构造函数获取了枚举的子类enums，让遍历更加高效快捷
     *
     * @param code 数据库中存储的自定义code属性
     * @return code对应的枚举类
     */
    public static ReqRepetitionCheckEnum locateEnum(int code) {
        for (ReqRepetitionCheckEnum status : ReqRepetitionCheckEnum.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("未知的枚举类型：" + code);
    }
}
