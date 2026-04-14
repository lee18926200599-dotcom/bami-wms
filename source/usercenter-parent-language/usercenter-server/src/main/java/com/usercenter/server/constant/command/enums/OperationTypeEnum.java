package com.usercenter.server.constant.command.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 操作类型
 */
public enum OperationTypeEnum {
    /**
     * 新增，添加
     */
    ADD("新增"),

    /**
     * 修改，更新
     */
    UPDATE("修改"),

    /**
     * 删除
     */
    DELETE("删除"),

    /**
     * 保存
     */
    SAVE("保存"),

    /**
     * 下载
     */
    DOWNLOAD("下载"),

    /**
     * 查询
     */
    QUERY("查询"),

    /**
     * 登入
     */
    LOGIN("登入"),

    /**
     * 登出
     */
    LOGOUT("登出");

    private String value;


    OperationTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * 获取所有的枚举集合
     *
     * @return
     */
    public static List<OperationTypeEnum> getOperationTypes() {
        return new ArrayList<OperationTypeEnum>(Arrays.asList(OperationTypeEnum
                .values()));
    }
}
