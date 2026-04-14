package com.common.framework.aop.paramtrim;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamStringTrim {
    ParamTrimTypeEnum value() default ParamTrimTypeEnum.START_END;
}
