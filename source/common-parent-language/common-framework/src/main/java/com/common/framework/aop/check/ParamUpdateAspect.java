package com.common.framework.aop.check;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.common.framework.annotation.ParamUpdateCheck;
import com.common.framework.annotation.UpdateCheck;
import com.common.framework.execption.SystemException;
import com.common.framework.web.SpringBeanLoader;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Description 字典页面属性值获取统一切面
 */
@Aspect
@Component
@Slf4j
public class ParamUpdateAspect {

    @Around("@annotation(updateCheck)")
    public Object around(ProceedingJoinPoint joinPoint, UpdateCheck updateCheck) throws Throwable {

        //获取旧值
        Class clazz = updateCheck.value();
        BaseMapper baseMapper = (BaseMapper) SpringBeanLoader.getSpringBean(clazz);
        Long id = findIdByValueKey(updateCheck.valueKey(),joinPoint);
        Object oldData = baseMapper.selectById(id);
        Field stateField = oldData.getClass().getDeclaredField("state");
        stateField.setAccessible(true);
        Integer state = (Integer)stateField.get(oldData);
        Field[] oldDeclaredFields = oldData.getClass().getDeclaredFields();
        List<Field> oldFieldsList = Arrays.asList(oldDeclaredFields);
        Map<String,Object> oldMap = new HashMap<>();
        for (Field field : oldFieldsList){
            field.setAccessible(true);
            Object value = field.get(oldData);
            oldMap.put(field.getName(),value);
        }

        //获取参数值
        Object[] paramValues = joinPoint.getArgs();
        Object newParam = paramValues[0];
        Field[] declaredFields = newParam.getClass().getDeclaredFields();
        List<Field> fieldsList = Arrays.asList(declaredFields);

        Map<String,Object> newMap = new HashMap<>();
        Map<String,String> msgMap = new HashMap<>();
        for (Field field : fieldsList){
            field.setAccessible(true);
            if (field.isAnnotationPresent(ParamUpdateCheck.class)) {
                String stateStr = field.getAnnotation(ParamUpdateCheck.class).state();
                List<String> split = Arrays.asList(stateStr.split(","));
                if (split.contains(state.toString())){
                    Object value = field.get(newParam);
                    newMap.put(field.getName(),value);
                    String remark = field.getAnnotation(ApiModelProperty.class).value();
                    msgMap.put(field.getName(),remark);
                }
            }
        }
        List<String> errorList = new ArrayList<>();
        for (String name : newMap.keySet()){
            if (!newMap.get(name).equals(oldMap.get(name))){
                errorList.add(msgMap.get(name));
            }
        }

        if (!CollectionUtils.isEmpty(errorList)){
            throw new SystemException(String.format("当前状态下[%s]不能修改",String.join(",",errorList)));
        }

        Object result = joinPoint.proceed();

        return result;
    }


    private SpelExpressionParser parser=new SpelExpressionParser();
    private DefaultParameterNameDiscoverer parameterNameDiscoverer=new DefaultParameterNameDiscoverer();
    public Long findIdByValueKey(String valueKey,ProceedingJoinPoint joinPoint){
        Expression expression=parser.parseExpression(valueKey);
        EvaluationContext context=new StandardEvaluationContext();
        MethodSignature methodSignature=(MethodSignature)joinPoint.getSignature();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(methodSignature.getMethod());
        Object[] args = joinPoint.getArgs();
        for(int i=0;i<args.length;i++){
            context.setVariable(parameterNames[i],args[i]);
        }
        Object value = expression.getValue(context);
        return (Long) value;
    }
}
