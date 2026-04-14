package com.org.permission.server.permission.service.command.enums;

public enum PermissionCommandEnum implements BaseEnum {
    ENABLE(1, "enablePermissionService", "启用"),
    DISABLE(0, "disablePermissionService", "停用");
    private Integer id;
    private String name;
    private String desc;

    PermissionCommandEnum(Integer id, String name, String desc) {
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

    public static PermissionCommandEnum getBeanByCommand(Integer command) {
        for (PermissionCommandEnum enums : PermissionCommandEnum.values()) {
            if (command.intValue() == enums.getId().intValue()) {
                return enums;
            }
        }
        throw new IllegalArgumentException("未找到执行命令");
    }
}