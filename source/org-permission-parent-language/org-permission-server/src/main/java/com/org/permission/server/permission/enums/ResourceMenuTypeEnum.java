package com.org.permission.server.permission.enums;

/**
 * 菜单资源类型
 */
public enum ResourceMenuTypeEnum {
    PC(0),
    APP(1),
    RF(2);
    private int code;
    ResourceMenuTypeEnum(int code){
        this.code=code;
    }

    public static ResourceMenuTypeEnum getEnum(int code) {
        for (ResourceMenuTypeEnum e : ResourceMenuTypeEnum.values()) {
            if (e.getCode()==code) {
                return e;
            }
        }
        return PC;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
