package com.org.permission.server.org.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 业务类型状态枚举
 */
public enum ChannelTypeEnum {

    BRANDER(1, "1", "品牌商"),
    AGENT(2, "2", "代理商"),
    DEALER(3, "3", "经销商"),
    RETAILER(4, "4", "零售商"),
    WHOLESALER(5, "5", "批发商"),
    KA(6, "6", "KA"),
    LINKAGE(7, "7", "连锁"),
    FRANCHISE(8, "8", "加盟");

    private Integer index;

    private String code;

    private String name;

    ChannelTypeEnum(Integer index, String code, String name) {
        this.index = index;
        this.code = code;
        this.name = name;
    }

    public static ChannelTypeEnum getEnum(String code) {
        return Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
    public static List<LinkedHashMap> getEnumList() {
        List<LinkedHashMap> list = new ArrayList<>();
        LinkedHashMap<String ,String> map = new LinkedHashMap<>();
        for (ChannelTypeEnum type : values()) {
            map.put(type.code,type.name);
        }
        list.add(map);
        return list;
    }

    public Integer getIndex() {
        return index;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }


    public boolean equals(Integer index) {
        return null != index && index.equals(this.index);
    }


    @Override
    public String toString() {
        return code;
    }
}
