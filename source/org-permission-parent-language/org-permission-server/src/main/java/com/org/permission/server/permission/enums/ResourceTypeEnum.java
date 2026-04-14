package com.org.permission.server.permission.enums;

/**
 * 资源类型
 */
public enum ResourceTypeEnum {
    MENU(1, "菜单"),
    BUTTON(2, "按钮"),
    ;
    private int code;
    private String name;

    ResourceTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ResourceTypeEnum getEnum(int code) {
        for (ResourceTypeEnum e : ResourceTypeEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
