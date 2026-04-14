package com.common.log.annotation;

import com.common.log.entity.LogFlowEnum;

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
public @interface BusinessOperationLog {

    /**
     * 指定参数，参考单号
     * @return
     */
    String valueKey() default "";

    /**
     * 指定参数，业务单号
     * @return
     */
    String businessNoKey() default "";

    /**
     * 所属流程
     * @return
     */
    LogFlowEnum[] logFlow();

    /**
     * 日志内容
     * @return
     */
    String logContent();

    /**
     * 开启日志记录
     * @return
     */
    boolean enable() default true;

    /**
     * 是否使用返回值
     * @return
     */
    boolean returnVauleEnable() default false;
}
