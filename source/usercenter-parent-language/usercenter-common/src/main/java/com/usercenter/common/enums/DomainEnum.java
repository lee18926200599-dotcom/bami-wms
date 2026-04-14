package com.usercenter.common.enums;

/**
 * 域枚举 与base_domian_config.id 保持一致
 */
public enum DomainEnum {
    BOSS(1){
        @Override
        public boolean matches(String system, String source) {
            if("BOSS".equalsIgnoreCase(system) && source.equalsIgnoreCase("PC")){
                return true;
            }
            return false;
        }
    },
    WMS(2){
        @Override
        public boolean matches(String system, String source) {
            if("WMS".equalsIgnoreCase(system) && source.equalsIgnoreCase("PC")){
                return true;
            }else if("WMS".equalsIgnoreCase(system) && source.equalsIgnoreCase("RF")){
                return true;
            }
            return false;
        }
    },
    TMS(3){
        @Override
        public boolean matches(String system, String source) {
            if("TMS".equalsIgnoreCase(system) && source.equalsIgnoreCase("PC")){
                return true;
            }else if("TMS".equalsIgnoreCase(system) && source.equalsIgnoreCase("APP")){
                return true;
            }
            return false;
        }
    },
    SCM(4){
        @Override
        public boolean matches(String system, String source) {
            if("SCM".equalsIgnoreCase(system) && source.equalsIgnoreCase("PC")){
                return true;
            }else if("SCM".equalsIgnoreCase(system) && source.equalsIgnoreCase("APP")){
                return true;
            }
            return false;
        }
    },
    CRM(7){
        @Override
        public boolean matches(String system, String source) {
            if("CRM".equalsIgnoreCase(system) && source.equalsIgnoreCase("APP")){
                return true;
            }
            return false;
        }
    },
    OSS(12){
        @Override
        public boolean matches(String system, String source) {
            if("OSS".equalsIgnoreCase(system) && source.equalsIgnoreCase("PC")){
                return true;
            }
            return false;
        }
    },
    unkown(0){
        @Override
        public boolean matches(String system, String source) {
            return false;
        }
    };

    private DomainEnum(Integer value) {
        this.value = value;
    }

    private Integer value;

    public Integer getValue() {
         return value;
    }

    public abstract boolean matches(String system, String source);

    public static DomainEnum parser(String system, String source){
        for(DomainEnum domain:values()){
            if(domain.matches(system,source)){
                return domain;
            }
        }
        return DomainEnum.unkown;
    }
}
