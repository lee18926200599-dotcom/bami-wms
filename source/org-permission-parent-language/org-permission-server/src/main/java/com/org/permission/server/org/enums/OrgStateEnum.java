package com.org.permission.server.org.enums;

import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.common.base.enums.StateEnum;

import java.util.Arrays;

/**
 * 状态枚举
 */
public enum OrgStateEnum {

    CREATE(StateEnum.CREATE.getCode(), "创建"),
    ENABLE(StateEnum.ENABLE.getCode(), "启用"),
    DISABLE(StateEnum.DISABLE.getCode(), "停用");

    private Integer code;

    private String name;


    OrgStateEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static OrgStateEnum getEnum(Integer code) {
        return Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }


    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * 状态控制
     *
     * @param currentState 当前状态值
     * @param nextState    下一步状态值
     * @return <code>true</code>可操作；<code>false</code>重复操作；否则异常；
     */
    public static boolean canOp(Integer currentState, Integer nextState) {
        final OrgStateEnum current = getEnum(currentState);
        final OrgStateEnum next = getEnum(nextState);

        if (current == CREATE) {
            if (next == DISABLE) {// 未启用状态，不可进行停用操作
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, "未启用状态不可进行停用操作");
            }
            return current != next;// true 可进行删除 或 启用操作 ， false 重复未启用操作
        }

        if (current == ENABLE) {
            if (next == CREATE) {
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, "启用状态不可删除或未启用操作");
            }
            return current != next;// true 启用可以进行停用操作，false 重复启用操作
        }

        if (current == DISABLE) {
            if (next == CREATE) {// 停用状态不可进行未启用操作
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, "停用状态不可进行未启用操作");
            }
        }
        return current != next;// true 停用可以进行删除或启用操作，false 重复停用操作
    }

    public static boolean canDel(Integer currentState) {
        final OrgStateEnum current = getEnum(currentState);
        if (current != CREATE) {
            return false;
        }
        return true;
    }

}
