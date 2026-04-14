package com.org.permission.server.org.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 业务类型状态枚举
 */
public enum EntrustTypeEnum {

    PURCHASEANDSALE(1, "1", "采销"),
    PURCHASE(2, "2", "采购"),
    SALE(3, "3", "销售"),
    STORAGE(4, "4", "仓储"),
    LOGISTICS(5, "5", "物流"),
    FINANCE(6, "6", "财务");

    private Integer index;

    private String code;

    private String name;


    EntrustTypeEnum(Integer index, String code, String name) {
        this.index = index;
        this.code = code;
        this.name = name;
    }

    public static EntrustTypeEnum getEnum(String code) {
        return Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
    public static List<LinkedHashMap> getEnumList() {
        List<LinkedHashMap> list = new ArrayList<>();
        LinkedHashMap<String ,String> map = new LinkedHashMap<>();
        for (EntrustTypeEnum type : values()) {
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
