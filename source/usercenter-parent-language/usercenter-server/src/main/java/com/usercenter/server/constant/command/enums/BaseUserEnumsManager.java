package com.usercenter.server.constant.command.enums;

import com.usercenter.common.enums.UserStateEnum;

/**
 * 用户枚举管理类
 */
public enum BaseUserEnumsManager {
    MANAGER_LEVEL("管理员等级",BaseUserManagerLevelEnum.class),
    IDENTITY_TYPE("身份类型",BaseUserIdentityTypeEnum.class),
    ENABLE_STATUS("启用状态", UserStateEnum.class),
    LOCK_STATUS("锁定状态",BaseUserLockEnum.class),
    DELETE_STATUS("删除状态",BaseUserDeleteEnum.class),
    RST_PASSWORD("是否重置密码",BaseUserPasswordFlagEnum.class);


    private String desc;

    private Class<?> clazz;

    public String getDesc() {
        return desc;
    }


    public Class<?> getClazz() {
        return clazz;
    }


    BaseUserEnumsManager(String desc, Class<?> clazz) {
        this.desc = desc;
        this.clazz = clazz;
    }

    public static BaseUserEnumsManager getByClass(Class clazz){
        for(BaseUserEnumsManager enumsManager:BaseUserEnumsManager.values()){
            if(enumsManager.getClazz().equals(clazz)){
                return enumsManager;
            }
        }
        return null;
    }
}
