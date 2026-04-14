//package com.org.permission.server.config;
//
//import com.baomidou.mybatisplus.core.MybatisConfiguration;
//import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
//import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.mapper.MapperScannerConfigurer;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.core.io.support.ResourcePatternResolver;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//
//
///**
// * @Author: zhouyk
// * @CreateTime: 2025-09-24  00:01
// * @Description: TODO
// */
//@Configuration
//@EnableTransactionManagement
//@AutoConfigureAfter(DataSource.class)
//// 禁用可能冲突的自动配置
//@EnableAutoConfiguration
//public class ForceMybatisPlusConfig {
//
//    @Bean
//    @Primary
//    public MybatisSqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws Exception {
//        MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource);
//
//        // 强制设置Mapper位置（使用绝对路径）
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        Resource[] resources = resolver.getResources("classpath*:mappers/**/*.xml");
//        System.out.println("找到XML文件数量: " + resources.length);
//        for (Resource resource : resources) {
//            System.out.println("XML文件: " + resource.getFilename());
//        }
//        sessionFactory.setMapperLocations(resources);
//
//        // 强制配置
//        MybatisConfiguration configuration = new MybatisConfiguration();
//        configuration.setMapUnderscoreToCamelCase(true);
//        configuration.setCacheEnabled(true);
//        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
//        sessionFactory.setConfiguration(configuration);
//
//        // 添加插件
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
//        sessionFactory.setPlugins(interceptor);
//
//        return sessionFactory;
//    }
//
//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer() {
//        MapperScannerConfigurer scanner = new MapperScannerConfigurer();
//        scanner.setBasePackage("com.org.permission.server.**.mapper");
//        scanner.setSqlSessionFactoryBeanName("sqlSessionFactory");
//        // 设置注解，确保只扫描Mapper接口
//        scanner.setAnnotationClass(org.apache.ibatis.annotations.Mapper.class);
//        return scanner;
//    }
//
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//}
