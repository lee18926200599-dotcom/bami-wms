package com.common.log.aop;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.common.base.entity.CurrentUser;
import com.common.framework.execption.BizException;
import com.common.log.annotation.RecordLog;
import com.common.log.entity.LogOperateEnum;
import com.common.log.entity.OperateLogDto;
import com.common.log.service.LogService;
import com.common.log.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;


@Aspect
@Slf4j
@Component
public class RecordLogAspect {
    @Resource
    private LogService logService;

    @Around(value = "@annotation(recordLog)")
    public Object around(ProceedingJoinPoint joinPoint, RecordLog recordLog) throws Throwable {
        return around0(joinPoint, recordLog);
    }

    private Object around0(ProceedingJoinPoint joinPoint, RecordLog recordLog) throws Throwable {
        // 执行原有方法
        if (!recordLog.enable()) {
            return joinPoint.proceed();
        }
        Object result = null;
        OperateLogDto<Object> objectOperateLogDto = null;
        // 记录正常执行时的操作日志
        try {
//             CurrentUser currentUser =FplUserUtil.getCurrenUser();
            CurrentUser currentUser = new CurrentUser();
            objectOperateLogDto = this.buildLog(joinPoint, recordLog, currentUser);
        } catch (Exception ex) {
            log.error("日志记录组装数据异常:" + ex);
        }

        try {
            result = joinPoint.proceed();
            if (objectOperateLogDto != null) {
                this.execute(recordLog, result, objectOperateLogDto);
            }
        } catch (Exception ex) {
            log.error("日志记录执行异常:" + ex);
        }
        return result;
    }

    private void execute(RecordLog recordLog, Object result, OperateLogDto logDto) {
        String operateName = recordLog.operate()[0].getName();
        if (LogOperateEnum.LOG_SAVE.getName().equals(operateName) && result != null) {
            if (!recordLog.parameter()) {
                if (result instanceof Long) {
                    logDto.setReferenceId((Long) result);
                    logDto.setNewBean(findBean(recordLog, logDto.getReferenceId()));
                } else if (result.getClass().getTypeName().equals(recordLog.beanClass().getTypeName())) {
                    logDto.setNewBean(result);
                }
            } else {
                Long id = getBeanId(result);
                if (id != null) {
                    logDto.setReferenceId(id);
                }
            }
        } else if (LogOperateEnum.LOG_UPDATE.getName().equals(operateName) && !recordLog.parameter()) {
            logDto.setNewBean(findBean(recordLog, logDto.getReferenceId()));
        } else {

        }
        this.logService.saveLog(logDto);
    }

    private OperateLogDto<Object> buildLog(ProceedingJoinPoint joinPoint, RecordLog recordLog, CurrentUser currentUser) {
        OperateLogDto<Object> logDto = new OperateLogDto();
        Class<?> aClass = recordLog.beanClass();
        String operateName = recordLog.operate()[0].getName();
        String targetTypeName = aClass.getTypeName();

        //单对象的
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            Object bean = args[0];
            if (recordLog.parameter()) {
                String typeName = args[0].getClass().getTypeName();
                if (!targetTypeName.equals(typeName)) {
                    bean = BeanUtil.toBean(args[0], aClass);
                }
                logDto.setNewBean(bean);
            }

            if (LogOperateEnum.LOG_UPDATE.getName().equals(operateName)) {
                if (recordLog.mapper() == null || recordLog.mapper().length == 0) {
                    throw new BizException("修改方法:" + joinPoint.getSignature().getName() + "未指定mapper");
                }
                Long id = getBeanId(bean);
                if (id == null) {
                    return null;
                }
                logDto.setReferenceId(id);
                logDto.setOldBean(findBean(recordLog, id));
            }
        }
        logDto.setModule(recordLog.module()[0]);
        logDto.setOperate(recordLog.operate()[0]);
        logDto.setOperateName(currentUser == null ? "系统" : currentUser.getUserName());
        logDto.setLoginAccount(currentUser == null ? "System" : currentUser.getUserName());
        return logDto;
    }

    private Object findBean(RecordLog recordLog, Long id) {
        Class<? extends BaseMapper> mapper = recordLog.mapper()[0];
        BaseMapper baseMapper = SpringUtil.getSpringBean(mapper);
        return baseMapper.selectById(id);
    }

    private Long getBeanId(Object bean) {
        Class<?> clazz = bean.getClass();
        try {
            Field field = clazz.getDeclaredField("id");
            field.setAccessible(true);
            Object obj = field.get(bean);
            if (obj == null) {
                log.error("日志记录解析，未获取到ID值:" + JSONUtil.toJsonStr(bean));
                return null;
            }
            return Long.valueOf(obj.toString());
        } catch (Exception ex) {
            log.error("获取对象ID值异常:" + ex);
        }
        return null;
    }

}
