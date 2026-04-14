package com.usercenter.server.common.factory.impl;

import com.usercenter.server.common.factory.BeanEnumFactory;
import com.usercenter.server.constant.command.enums.BeanEnum;
import com.common.framework.web.SpringBeanLoader;
import org.springframework.stereotype.Service;


@Service
public class BeanEnumFactoryImpl implements BeanEnumFactory {

    @Override
    public <T> T getBean(BeanEnum beanEnum, Class<T> clazz) {
        return SpringBeanLoader.getSpringBean(beanEnum.getBean(),clazz);
    }
}
