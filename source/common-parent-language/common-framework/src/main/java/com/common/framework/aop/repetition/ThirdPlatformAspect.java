package com.common.framework.aop.repetition;

import cn.hutool.core.bean.BeanUtil;
import com.common.framework.filter.ParameterRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Aspect
@Slf4j
@Component
public class ThirdPlatformAspect {

    @Around(value = "@annotation(thirdPlatformAnno)")
    public Object around(ProceedingJoinPoint point, ThirdPlatformAnno thirdPlatformAnno) throws Throwable {
        return fuc(point, thirdPlatformAnno);
    }
    private Object fuc(ProceedingJoinPoint point,ThirdPlatformAnno thirdPlatformAnno) throws Throwable {
        try {
            //获取目标方法执行的参数
            Object[] args = point.getArgs();
            Map<String,Object> map = BeanUtil.beanToMap(args[0]);
            String warehouseId = "";
            if(map.containsKey("warehouseId")){
                warehouseId = String.valueOf(map.get("warehouseId"));
            }
            String warehouseCode = "";
            if(map.containsKey("warehouseCode")){
                warehouseCode = String.valueOf(map.get("warehouseCode"));
            }
            String warehouseName = "";
            if(map.containsKey("warehouseName")){
                warehouseName = String.valueOf(map.get("warehouseName"));
            }
            String groupId = "";
            if(map.containsKey("groupId")){
                groupId = String.valueOf(map.get("groupId"));
            }
            ParameterRequestWrapper request = (ParameterRequestWrapper) ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
            if(StringUtils.isNotBlank(warehouseId)){
                request.putHeader("Warehouseid",warehouseId);
            }
            if(StringUtils.isNotBlank(warehouseCode)){
                request.putHeader("Warehousecode",warehouseCode);
            }
            if(StringUtils.isNotBlank(warehouseName)){
                request.putHeader("Warehousename",warehouseName);
            }
            if(StringUtils.isNotBlank(groupId)){
                request.putHeader("Groupid",groupId);
            }
        }catch (Exception ex){
            log.error("日志记录异常:"+ex);
        }
        // 执行原有方法
        Object result=point.proceed();
        return result;
    }

    private SpelExpressionParser parser=new SpelExpressionParser();
    private DefaultParameterNameDiscoverer parameterNameDiscoverer=new DefaultParameterNameDiscoverer();
    public List<String> findIdsByValueKey(String valueKey,ProceedingJoinPoint joinPoint){
        Expression expression=parser.parseExpression(valueKey);
        EvaluationContext context=new StandardEvaluationContext();
        MethodSignature methodSignature=(MethodSignature)joinPoint.getSignature();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(methodSignature.getMethod());
        Object[] args = joinPoint.getArgs();
        for(int i=0;i<args.length;i++){
            context.setVariable(parameterNames[i],args[i]);
        }
        Object value = expression.getValue(context);
        return objToList(value);
    }

    public String findBusinessNo(String businessNoKey,ProceedingJoinPoint joinPoint){
        Expression expression=parser.parseExpression(businessNoKey);
        EvaluationContext context=new StandardEvaluationContext();
        MethodSignature methodSignature=(MethodSignature)joinPoint.getSignature();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(methodSignature.getMethod());
        Object[] args = joinPoint.getArgs();
        for(int i=0;i<args.length;i++){
            context.setVariable(parameterNames[i],args[i]);
        }
        return expression.getValue(context,String.class);
    }

    public List<String> objToList(Object obj){
        List<String> list=new ArrayList<>();
        if(obj instanceof ArrayList<?>){
            for(Object o:(List<?>)obj){
				list.add(String.class.cast(o));
            }
            return list;
        }else{
            list.add(String.class.cast(obj));
        }
		return list;
    }
}
