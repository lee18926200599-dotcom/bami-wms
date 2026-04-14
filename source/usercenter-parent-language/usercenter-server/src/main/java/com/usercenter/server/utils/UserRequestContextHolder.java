package com.usercenter.server.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 用户请求上下文
 */
public class UserRequestContextHolder extends RequestContextHolder {

    /**
     * 在请求域中传参
     * @param name 参数名
     * @param value 值
     */
    public static void addRequestAttr(String name, Object value) {
        getRequestAttributes().setAttribute(name, value, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * 在请求域中传参
     * @param name 参数名
     */
    public static Object getRequestAttr(String name) {
        Object attribute = getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_REQUEST);
        return attribute;
    }
}
