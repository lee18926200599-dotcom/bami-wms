package com.common.framework.aop.paramtrim;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Aspect
@Slf4j
public class ParamStringTrimAspect {
    @Pointcut(value = "@annotation(com.common.framework.aop.paramtrim.ParamStringTrim)")
    public void trimPointcut() throws Throwable {

    }

    @Around("trimPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        try{
            //获取参数值
            Object[] paramValues = joinPoint.getArgs();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            ParamStringTrim paramStringTrim = method.getAnnotation(ParamStringTrim.class);
            ParamTrimTypeEnum trimType = paramStringTrim.value();
            for (Object arg : paramValues) {
                //获取类的字段
                Field[] fields = arg.getClass().getDeclaredFields();
                for (Field field : fields) {
                    // 设置字段可访问， 否则无法访问private修饰的变量值
                    field.setAccessible(true);
                    //获取字段名
                    String name = field.getName();
                    if (name.equals("serialVersionUID")) {
                        continue;
                    }
                    // 获取字段类型
                    String type = field.getGenericType().toString();
                    // 拼装get set方法  例如 setName getName
                    String setMethodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                    String getMethodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                    //通过方法名称获取对应的方法，这里是获取字段的get方法
                    Method getMethod = arg.getClass().getMethod(getMethodName);
                    Method setMethod;
                    // 判断类型是string 类型
                    if (type.equals(String.class)) {
                        // 获取set方法
                        setMethod = arg.getClass().getMethod(setMethodName, String.class);
                        // 通过 invoke 获取到get方法的Value值  PS: invoke()方法就是用来执行指定对象的方法
                        String value = (String) getMethod.invoke(arg);
                        // 再invoke 执行 set方法  StringUtils.trim 对空格进行处理
                        setMethod.invoke(arg, replaceResult(value,trimType));
                    } else if ("java.util.List<java.lang.String>".equals(type)) {
                        // 判断类型是List<String> 类型
                        setMethod = arg.getClass().getMethod(setMethodName, List.class);
                        List<String> valueList = (List<String>) getMethod.invoke(arg);
                        if(CollectionUtil.isNotEmpty(valueList)){
                            List<String> collect = valueList.stream().map(val->replaceResult(val,trimType)).filter(val->StringUtils.isNotBlank(val)).collect(Collectors.toList());
                            setMethod.invoke(arg, collect);
                        }
                    }
                }
            }
        }catch (Exception ex){
            log.error("参数去掉空格异常:"+ex);
        }

        return joinPoint.proceed();
    }

    private String replaceResult(String val, ParamTrimTypeEnum trimType) {
        if (StringUtils.isBlank(val)) {
            return null;
        }
        val = val.replaceAll("\\r", "").replaceAll("\\n", "");
        switch (trimType) {
            case ALL:
                return val.replaceAll("\\s+", "");
            case START_END:
                return val.trim();
            default:
                return val.trim();
        }
    }
}
