package com.common.log.aop;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.common.base.entity.CurrentUser;
import com.common.framework.user.FplUserUtil;
import com.common.log.annotation.OperationLog;
import com.common.log.entity.LogOperateEnum;
import com.common.log.entity.OperateLogDto;
import com.common.log.service.LogService;
import com.common.log.util.SpringUtil;
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

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Aspect
@Slf4j
@Component
public class OperationLogAspect {
    @Resource
    private LogService logService;


    @Around(value = "@annotation(operationLog)")
    public Object around(ProceedingJoinPoint joinPoint, OperationLog operationLog) throws Throwable {
        return around0(joinPoint, operationLog);
    }
    private Object around0(ProceedingJoinPoint joinPoint,OperationLog operationLog) throws Throwable {
        // 执行原有方法
        if (!operationLog.enable()) {
            return joinPoint.proceed();
        }

        Map<Long, Object> oldBeanMap=new HashMap<>();
        Map<Long, Object> newBeanMap=new HashMap<>();
        List<Long> ids=new ArrayList<>();
        List<OperateLogDto> listBean=new ArrayList<>();
        try {
            String operateName = operationLog.operate()[0].getName();
            String valueKey=operationLog.valueKey();
            ids.addAll(findIdsByValueKey(valueKey, joinPoint));
            if(CollectionUtil.isEmpty(ids)){
                return joinPoint.proceed();
            }
            if(LogOperateEnum.LOG_UPDATE.getName().equals(operateName)){
                //执行之前获取旧值
                oldBeanMap.putAll(queryBeanByIds(operationLog, ids));
            }
        }catch (Exception ex){
            log.error("日志记录获取旧值异常:"+ex);
        }
        //执行原有方法
        Object result=joinPoint.proceed();
        try {
            String operateName = operationLog.operate()[0].getName();
            if(LogOperateEnum.LOG_UPDATE.getName().equals(operateName)){
                //获取新值
                newBeanMap.putAll(queryBeanByIds(operationLog, ids));
            }else{
                if(result instanceof List){
                    for(Object obj:(List<?>)result){
                        Long id= getBeanId(obj);
                        newBeanMap.put(id,obj);
                    }
                }else{
                    Long id= getBeanId(result);
                    newBeanMap.put(id,result);
                }
            }

            CurrentUser currentUser = FplUserUtil.getCurrentUser();
            newBeanMap.forEach((id,value)->{
                OperateLogDto logDto=new OperateLogDto();
                logDto.setReferenceId(id);
                logDto.setNewBean(value);
                logDto.setOldBean(oldBeanMap.get(id));
                logDto.setModule(operationLog.module()[0]);
                logDto.setOperate(operationLog.operate()[0]);
                logDto.setOperateName(currentUser==null?"系统":currentUser.getUserName());
                logDto.setLoginAccount(currentUser==null?"System":currentUser.getUserName());
                listBean.add(logDto);
            });
            logService.saveLogBatch(listBean);
        }catch (Exception ex){
            log.error("日志记录获取新值异常:"+ex);
        }

        return result;
    }

    private Map<Long,Object> queryBeanByIds(OperationLog operationLog,List<Long> ids){
        List<Object> list= findBean(operationLog,ids);
        return convertMap(list);
    }

    private Map<Long,Object> convertMap(List<Object> list){
        Map<Long,Object> map=new HashMap<>();
        list.forEach(item->{
            Long id= getBeanId(item);
            map.put(id,item);
        });
        return map;
    }


    private List<Object> findBean(OperationLog operationLog,List<Long> ids){
        Class<? extends BaseMapper> mapper = operationLog.mapper()[0];
        BaseMapper baseMapper = SpringUtil.getSpringBean(mapper);
        return baseMapper.selectBatchIds(ids);
    }

    private SpelExpressionParser parser=new SpelExpressionParser();
    private DefaultParameterNameDiscoverer parameterNameDiscoverer=new DefaultParameterNameDiscoverer();
    public List<Long> findIdsByValueKey(String valueKey,ProceedingJoinPoint joinPoint){
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

    public List<Long> objToList(Object obj){
        List<Long> list=new ArrayList<>();
        if(obj instanceof ArrayList<?>){
            for(Object o:(List<?>)obj){
				list.add(Long.class.cast(o));
            }
            return list;
        }else if(obj instanceof Long){
            list.add(Long.class.cast(obj));
        }
		return list;
    }
    private Long getBeanId(Object bean){
        Class<?> clazz = bean.getClass();
        try {
            Field field = clazz.getDeclaredField("id");
            field.setAccessible(true);
            Object obj = field.get(bean);
            if(obj==null){
                log.error("日志记录解析，未获取到ID值:"+ JSONUtil.toJsonStr(bean));
                return null;
            }
            return Long.valueOf(obj.toString());
        } catch (Exception ex) {
            log.error("获取对象ID值异常:"+ex);
        }
        return null;
    }
}
