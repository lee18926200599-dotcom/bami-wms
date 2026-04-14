package com.org.permission.common.enums.org;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 职能枚举
 */
public enum FunctionTypeEnum {

    CORPORATION(1, "1", "法人"),
    FINANCE(2, "2", "财务"),
    PURCHASE(3, "3", "采购"),
    SALE(4, "4", "销售"),
    STORAGE(5, "5", "仓储"),
    LOGISTICS(6, "6", "运输"),
    BANKING(7, "7", "金融"),
    LABOUR_SERVICE(8, "8", "劳务"),
    PLATFORM(9, "9", "平台");

    private Integer index;

    private String code;

    private String name;


    FunctionTypeEnum(Integer index, String code, String name) {
        this.index = index;
        this.code = code;
        this.name = name;
    }

    public static FunctionTypeEnum getEnum(String code) {
        return Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
    public static FunctionTypeEnum getEnum(Integer index) {
        return Arrays.stream(values())
                .filter(item -> item.getIndex().equals(index))
                .findFirst()
                .orElse(null);
    }
    public static List<LinkedHashMap> getEnumList() {
        List<LinkedHashMap> list = new ArrayList<>();
        LinkedHashMap<String ,String> map = new LinkedHashMap<>();
        for (FunctionTypeEnum type : values()) {
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
