package com.org.permission.server.config;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: zhouyk
 * @CreateTime: 2025-09-23  22:46
 * @Description: TODO
 */
@Component
public class MapperStatementChecker implements ApplicationRunner {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void run(ApplicationArguments args)  {
        Configuration configuration = sqlSessionFactory.getConfiguration();
        System.out.println("=== 已加载的Mapper语句 ===");
        try {
            configuration.getMappedStatementNames().forEach(name -> System.out.println("已加载mapper:"+name));
        }catch (Exception e){
            System.out.println("=== 检查失败 ===");
            e.printStackTrace();
        }
        System.out.println("=== 检查完成 ===");
    }
}
