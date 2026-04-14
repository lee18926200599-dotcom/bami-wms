package com.org.permission.server.permission.service.command.service;

import com.common.framework.web.SpringBeanLoader;
import com.org.permission.server.permission.service.command.enums.BaseEnum;
import com.org.permission.server.permission.service.command.utils.BeanFactory;
import org.springframework.stereotype.Service;

@Service
public class BeanFactoryImpl implements BeanFactory {

    @Override
    public <T> T getBean(BaseEnum baseEnum, Class<T> clazz) {
        return SpringBeanLoader.getSpringBean(baseEnum.getName(), clazz);
    }
}