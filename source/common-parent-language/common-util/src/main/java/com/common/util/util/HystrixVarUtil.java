package com.common.util.util;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;

import java.util.HashMap;
import java.util.Map;

public class HystrixVarUtil {
    private static final HystrixRequestVariableDefault<Map<String, String>> USER_DATA_VAR = new HystrixRequestVariableDefault();

    public HystrixVarUtil() {
    }

    public static void setUserKey(String userKey) {
        setValue("UserKey", userKey);
    }

    public static String getUserKey() {
        return getValue("UserKey");
    }

    public static void setUserDTO(String userDTO) {
        setValue("UserInfo", userDTO);
    }

    public static String getUserDTO() {
        return getValue("UserInfo");
    }

    public static void setValue(String key, String value) {
        Map<String, String> map = (Map)USER_DATA_VAR.get();
        if (map == null) {
            map = new HashMap();
            map.put(key, value);
            USER_DATA_VAR.set(map);
        } else {
            map.put(key, value);
        }

    }

    public static String getValue(String key) {
        Map<String, String> map = (Map)USER_DATA_VAR.get();
        return map == null ? null : (String)map.get(key);
    }
}
