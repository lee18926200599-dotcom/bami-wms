package com.usercenter.server.common.factory;


import com.usercenter.server.constant.command.enums.BeanEnum;

public interface BeanEnumFactory {

   /**
    * 根据枚举BeanEnum获取 对象
    * @param beanEnum
    * @param clazz
    * @param <T>
    * @return
    */
   <T> T getBean(BeanEnum beanEnum, Class<T> clazz);

}
