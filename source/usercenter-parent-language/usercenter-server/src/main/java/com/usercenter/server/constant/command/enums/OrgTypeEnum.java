package com.usercenter.server.constant.command.enums;

/**
 * 组织类型枚举
 */
public enum OrgTypeEnum {
    ALL(0), GLOBAL(1), GROUP(2), BIZ_UNIT(3), DEPARTMENT(4);

    private int type;

    OrgTypeEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
