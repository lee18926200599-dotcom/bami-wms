
package com.common.framework.aop.repetition;


import java.lang.annotation.*;

/**
 * 重复检查
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ReqRepetitionCheck {

    /**
     * 过期时间（默认3秒）
     */
    long value() default 3000;

    /**
     * 是否抛出重复提交的错误(0-不需要；1-需要)
     */
    ReqRepetitionCheckEnum isThrowException() default ReqRepetitionCheckEnum.NO;

    /**
     * 自定义抛出的异常
     */
    String errorMessage() default "请勿重复提交";
}
