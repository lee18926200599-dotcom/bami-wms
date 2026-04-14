package com.usercenter.server.constant.command.enums;

/**
 * 用户更新状态统一操作
 */
public enum UpdateUserCommandBeanEnum implements BeanEnum {
    NOTABLE(0,"notableCommand","未启动命令"),
    ENABLE(1,"enableCommand","启用命令"),
    DISABLE(2,"disableCommand","停用命令"),
    LOCK(3,"lockCommand","锁定命令"),
    UNLOCK(4,"unLockCommand","解锁命令"),
    RESETPASSWORD(5,"resetPasswordCommand","重置密码"),
    DELETE(6,"deleteCommand","删除命令");

    private Integer id;

    private String bean;


    private String desc;


    public Integer getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String getBean() {
        return bean;
    }

    UpdateUserCommandBeanEnum(Integer id, String bean, String desc) {
        this.id = id;
        this.bean = bean;
        this.desc = desc;
    }

    public static UpdateUserCommandBeanEnum getBeanEnum(Integer operate){
        for(UpdateUserCommandBeanEnum enums:UpdateUserCommandBeanEnum.values()){
            if(enums.getId().equals(operate)){
                return enums;
            }
        }
        throw new IllegalArgumentException("未找到执行命令");
    }


}
