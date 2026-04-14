package com.org.permission.server.org.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 业务类型状态枚举
 */
public enum EntrustRangeEnum {

    INTER_GROUP(1, "1", "集团间"),
    WITHIN_GROUP(2, "2", "集团内");

    private Integer index;

    private String code;

    private String name;


    EntrustRangeEnum(Integer index, String code, String name) {
        this.index = index;
        this.code = code;
        this.name = name;
    }

    public static EntrustRangeEnum getEnum(String code) {
        return Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
    public static EntrustRangeEnum getEnum(Integer index) {
        return Arrays.stream(values())
                .filter(item -> item.getIndex().equals(index))
                .findFirst()
                .orElse(null);
    }
    public static List<LinkedHashMap> getEnumList() {
        List<LinkedHashMap> list = new ArrayList<>();
        LinkedHashMap<String ,String> map = new LinkedHashMap<>();
        for (EntrustRangeEnum type : values()) {
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
