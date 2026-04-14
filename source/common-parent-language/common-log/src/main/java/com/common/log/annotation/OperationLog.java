package com.common.log.annotation;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.common.log.entity.LogModuleEnum;
import com.common.log.entity.LogOperateEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 业务日志注解
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLog {

    /**
     * 指定参数
     * @return
     */
    String valueKey() default "";

    /**
     * 所属模块
     * @return
     */
    LogModuleEnum[] module();

    /**
     * 操作类型
     * @return
     */
    LogOperateEnum[] operate() default LogOperateEnum.LOG_UPDATE;


    Class<? extends BaseMapper>[] mapper() default {};

    /**
     * 开启日志记录
     * @return
     */
    boolean enable() default true;

}
