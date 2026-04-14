package com.common.log.aop;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.common.base.entity.CurrentUser;
import com.common.framework.user.FplUserUtil;
import com.common.log.annotation.BusinessOperationLog;
import com.common.log.entity.BusinessOperateLogDto;
import com.common.log.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Aspect
@Slf4j
@Component
public class BusinessOperationLogAspect {
    @Resource
    private LogService logService;


    @Around(value = "@annotation(businessOperationLog)")
    public Object around(ProceedingJoinPoint joinPoint, BusinessOperationLog businessOperationLog) throws Throwable {
        return around0(joinPoint, businessOperationLog);
    }
    private Object around0(ProceedingJoinPoint joinPoint,BusinessOperationLog businessOperationLog) throws Throwable {
        // 执行原有方法
        Object result=joinPoint.proceed();
        List<String> orderNos=new ArrayList<>();
        List<BusinessOperateLogDto> listBean=new ArrayList<>();
        try {
            String logContent = businessOperationLog.logContent();
            String valueKey=businessOperationLog.valueKey();
            String businessNoKey = businessOperationLog.businessNoKey();
            if(StringUtils.isNotBlank(businessNoKey)){
                orderNos.addAll(findIdsByValueKey(valueKey, joinPoint));
            }
            CurrentUser currentUser = FplUserUtil.getCurrentUser();
            if(businessOperationLog.returnVauleEnable()){
                orderNos.addAll(objToList(result));
            }
            orderNos.forEach(item->{
                BusinessOperateLogDto logDto=new BusinessOperateLogDto();
                logDto.setReferenceNo(item);
                logDto.setLogContent(logContent);
                logDto.setLogFlowEnum(businessOperationLog.logFlow()[0]);
                logDto.setOperateName(currentUser==null?"系统":currentUser.getUserName());
                logDto.setLoginAccount(currentUser==null?"System":currentUser.getUserName());
                logDto.setBusinessNo(logDto.getReferenceNo());
                if(StringUtils.isNotBlank(businessNoKey)){
                    logDto.setBusinessNo(findBusinessNo(businessNoKey,joinPoint));
                }
                listBean.add(logDto);
            });
            logService.saveBusinessLogBatch(listBean);
        }catch (Exception ex){
            log.error("日志记录异常:"+ex);
        }
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
