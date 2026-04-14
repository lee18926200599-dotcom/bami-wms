package com.common.base.enums;

import cn.hutool.core.lang.Assert;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description 电商平台编码
 * <p>
 * PlatformTypeEnum.TB.name() = TB
 * <p>
 * PlatformTypeEnum.TB.getPlatformName() = 天猫
 * <p>
 * PlatformTypeEnum.TB.getBelongPlatformCode() = TM
 */
@AllArgsConstructor
@Getter
public enum PlatformTypeEnum {

    TM("TB", "天猫"),// 天猫属于淘系
    TB("TB", "淘宝"),
    JD("JD", "京东"),
    PDD("PDD", "拼多多"),
    WPH("WPH", "唯品会"),
    WPHMP("WPH", "唯品会MP"),
    DY("DY", "抖音"),
    DYXD("DY", "抖音小店"),
    KS("KS", "快手"),
    XHS("XHS", "小红书"),
    WXSPHXD("WXSPHXD", "微信视频号小店"),
    DW("DW", "得物"),
    DWZF("DWZF", "得物直发"),
    OTHERS("OTHERS", "其它"),
    ;

    /**
     * 归属平台编码（映射成同类平台编码）
     */
    private final String belongPlatformCode;
    /**
     * 平台名称
     */
    private final String platformName;

    public static Map<String, PlatformTypeEnum> getCodeEnumMap() {
        return Arrays.stream(values()).collect(Collectors.toMap(PlatformTypeEnum::name, x -> x));
    }

    /**
     * 根据code值获取describe
     *
     * @param code
     * @return describe
     */
    public static String getPlatformName(String code) {
        PlatformTypeEnum platformTypeEnum = getCodeEnumMap().get(code);
        return platformTypeEnum == null ? null : platformTypeEnum.getPlatformName();
    }

    /**
     * 根据code值获取describe
     * 匹配不到，抛异常
     *
     * @param code
     * @return describe
     */
    public static String getExistPlatformName(String code) {
        PlatformTypeEnum platformTypeEnum = getCodeEnumMap().get(code);
        Assert.notNull(platformTypeEnum, "平台编码【" + code + "】不存在！");
        return platformTypeEnum.getPlatformName();
    }

    /**
     * 根据  电商平台编码 得到所属于的平台
     *
     * @param code
     * @return
     */
    public static String getBelongPlatform(String code) {
        PlatformTypeEnum platformTypeEnum = getCodeEnumMap().get(code);
        Assert.notNull(platformTypeEnum, "平台编码【" + code + "】不存在！");
        return platformTypeEnum.getBelongPlatformCode();
    }

    /**
     * 根据 平台获取所有子平台编码
     *
     * @param platformCode
     * @return
     */
    public static List<String> getSubPlatformCodeByBelongPlatform(String platformCode) {
        String belongPlatform = getBelongPlatform(platformCode);
        return Arrays.stream(values()).filter(x -> x.getBelongPlatformCode().equals(belongPlatform)).map(Enum::name).collect(Collectors.toList());
    }

    // 测试
    public static void main(String[] args) {
        System.out.println("抖音 子平台 = " + getSubPlatformCodeByBelongPlatform("DY"));
        System.out.println("淘宝 子平台 = " + getSubPlatformCodeByBelongPlatform("TB"));
        System.out.println("TM 子平台 = " + getSubPlatformCodeByBelongPlatform("TM"));

    }
}
