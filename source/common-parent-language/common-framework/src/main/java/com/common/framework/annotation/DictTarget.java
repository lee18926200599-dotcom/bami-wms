package com.common.framework.annotation;



import com.common.framework.aop.dict.DictionaryEnum;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DictTarget {

    DictionaryEnum value();

    boolean cover() default false;
}
