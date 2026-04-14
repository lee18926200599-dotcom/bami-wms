package com.org.permission.server.permission.service.command.utils;


import com.org.permission.server.permission.service.command.enums.BaseEnum;

public interface BeanFactory {

    /**
     * 根据枚举BeanEnum获取 对象
     *
     * @param baseEnum
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getBean(BaseEnum baseEnum, Class<T> clazz);

}