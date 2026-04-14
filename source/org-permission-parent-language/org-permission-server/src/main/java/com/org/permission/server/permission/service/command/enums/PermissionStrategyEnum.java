package com.org.permission.server.permission.service.command.enums;

public enum PermissionStrategyEnum implements BaseEnum {
    ENABLE_USER(1, "enableUserServiceImpl", "启用用户"),
    DISABLE_USER(2, "disableUserServiceImpl", "停用用户"),
    ENABLE_ADMIN(3, "enableAdminServiceImpl", "启用管理员"),
    DISABLE_ADMIN(4, "disableAdminServiceImpl", "停用管理员");
    private Integer id;
    private String name;
    private String desc;

    PermissionStrategyEnum(Integer id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static PermissionStrategyEnum getBeanByStrategy(Integer strategy) {
        for (PermissionStrategyEnum enums : PermissionStrategyEnum.values()) {
            if (strategy.intValue() == enums.getId().intValue()) {
                return enums;
            }
        }
        throw new IllegalArgumentException("未找到执行策略");
    }
}