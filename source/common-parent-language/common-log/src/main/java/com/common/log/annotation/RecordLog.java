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
public @interface RecordLog {

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

    /**
     * 变更实体
     * @return
     */
    Class<? extends Object> beanClass();

    /**
     * 操作名称
     * @return
     */
    String value() default "";

    Class<? extends BaseMapper>[] mapper() default {};

    /**
     * 开启日志记录
     * @return
     */
    boolean enable() default true;

    /**
     * 入参是否可用于新值
     * @return
     */
    boolean parameter() default true;
}
