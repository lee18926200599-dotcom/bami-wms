package com.org.permission.server.permission.enums;

public enum ValidEnum {
    YES(1, "有效"), NO(0, "无效");
    private int code;
    private String desc;

    ValidEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
