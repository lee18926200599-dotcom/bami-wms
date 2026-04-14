package com.org.permission.server.org.factory;


import com.common.framework.web.SpringBeanLoader;
import com.org.permission.server.org.enums.BeanEnum;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ServiceBeanFactory implements BeanFactory<BeanEnum> {

    @Resource
    private SpringBeanLoader springBeanApplication;


    @Override
    public <T> T getBean(BeanEnum beanEnum, Class<T> clzz) {
        return springBeanApplication.getSpringBean(beanEnum.getBeanName(),clzz);
    }
}
