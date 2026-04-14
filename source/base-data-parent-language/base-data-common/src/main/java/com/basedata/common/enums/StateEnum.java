package com.basedata.common.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 状态枚举
 */
public enum StateEnum {

    NOT_ENABLE(0, "创建"),
    ENABLE(1, "启用"),
    DISABLE(2, "停用");

    private Integer code;

    private String name;


    StateEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static StateEnum getEnum(Integer code) {
        return Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    public static Map<Integer, String> getCodeNameMap() {
        return Arrays.stream(StateEnum.values()).collect(Collectors.toMap(StateEnum::getCode, StateEnum::getName));
    }

    /**
     * 根据code值获取name
     *
     * @param code
     * @return name
     */
    public static String getCodeName(Integer code) {
        return getCodeNameMap().getOrDefault(code, null);
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }


}
