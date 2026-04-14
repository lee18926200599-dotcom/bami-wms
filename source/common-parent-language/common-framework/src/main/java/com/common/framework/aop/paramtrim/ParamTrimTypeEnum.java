package com.common.framework.aop.paramtrim;

public enum ParamTrimTypeEnum {
    ALL(1,"全部"),
    START_END(2,"首尾"),
    ;
    private int type;
    private String desc;
    ParamTrimTypeEnum(int type, String desc){
        this.type=type;
        this.desc=desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
