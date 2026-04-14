package com.basedata.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 配送方式
 */
@AllArgsConstructor
@Getter
public enum DeliveryTypeEnum {
    EXPRESS(1, "快递"),
    URBAN_DISTRIBUTION(2, "快递单揽"),
    LOGISTICS(3, "物流"),
    SELF_PICKUP(4, "自提");
    ;

    private final Integer code;
    private final String name;

    public static Map<Integer, String> getCodeNameMap() {
        return Arrays.stream(values()).collect(Collectors.toMap(DeliveryTypeEnum::getCode, DeliveryTypeEnum::getName));
    }

    /**
     * 根据code值获取code名称
     *
     * @param code
     * @return code名称
     */
    public static String getCodeName(Integer code) {
        return getCodeNameMap().getOrDefault(code, null);
    }

}
