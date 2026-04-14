package com.org.permission.server.org.enums;

public enum BankAccountStateServiceEnum implements BeanEnum {
    //启用服务
    ENABLE_SERVICE(OrgStateEnum.ENABLE.getCode(),"bankAccountEnableStatusCommand","启用服务"),
    //禁用服务
    DISABLE_SERVICE(OrgStateEnum.DISABLE.getCode(),"bankAccountUnableStatusCommand","禁用服务"),
    //删除服务 TODO
    DELETE_SERVICE(4,"bankAccountDeleteStatusCommand","删除服务");


    private Integer id;

    private String beanName;

    private String desc;


    @Override
    public String getBeanName() {
        return beanName;
    }

    public Integer getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    BankAccountStateServiceEnum(Integer id, String beanName, String desc) {
        this.id = id;
        this.beanName = beanName;
        this.desc = desc;
    }


    public static BankAccountStateServiceEnum getEnum(Integer id){
        for (BankAccountStateServiceEnum bank:BankAccountStateServiceEnum.values()){
            if(bank.getId().equals(id)){
                return bank;
            }
        }
        throw new IllegalArgumentException("参数错误");
    }
}
