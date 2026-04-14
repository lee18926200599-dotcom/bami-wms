package com.basedata.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description:
 * integration接口平台上配置的 平台应用类型
 * <p>
 * 注释掉的平台没用
 * 以下接口返回的平台
 * http://127.0.0.1:9999/platform/platformTypeList
 * [{"name":"淘宝奇门","id":1},{"name":"京东","id":2},{"name":"拼多多","id":3},{"name":"抖音","id":4},{"name":"唯品会","id":5},{"name":"菜鸟","id":6},
 * {"name":"快手","id":7},{"name":"有赞","id":8},{"name":"淘宝","id":9},{"name":"OA","id":10},{"name":"美团","id":11},{"name":"京东物流","id":12},
 * {"name":"孔夫子","id":13},{"name":"小红书","id":14},{"name":"微信","id":15},{"name":"得物","id":16},{"name":"中交兴路物流","id":101}]
 */
@AllArgsConstructor
@Getter
public enum IntegrationPlatformTypeEnum {

    //    TB_QIMEN(1, "淘宝奇门"),
    JD(2, "京东"),
    PDD(3, "拼多多"),
    DY(4, "抖音"),
    WPH(5, "唯品会"),
    //    CN(6, "菜鸟"),
    KS(7, "快手"),
    //    YZ(8, "有赞"),
    TB(9, "淘宝"),
    //    OA(10, "OA"),
    //    MT(11, "美团"),
    JDWL(12, "京东物流"),
//    KFZ(13, "孔夫子"),
    XHS(14, "小红书"),
    WX(15, "微信"),
    DW(16, "得物"),

    ;

    private final Integer code;
    private final String name;

    public static IntegrationPlatformTypeEnum getByCode(Integer code) {
        for (IntegrationPlatformTypeEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}

