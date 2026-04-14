package com.org.permission.server.utils;

/**
 * dto 拷贝
 */
public interface DtoConverter<S,T> {

    /**
     * 将指定类转化为本类
     * @param s 制定类
     * @return 转换后的本类
     */
     T convertFrom(S s);

}
