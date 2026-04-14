package com.common.framework.aop.repetition;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSONObject;
import com.common.framework.execption.SystemException;
import com.common.util.message.RestMessage;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * 校验重复请求并返回成功
 */
@Component
@Aspect
@Slf4j
public class ReqRepetitionCheckAspect {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Pointcut("@annotation(com.common.framework.aop.repetition.ReqRepetitionCheck)")
    public void idempotentCheckAspect() {
    }


    @Around("idempotentCheckAspect()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        if (isTwiceInvoke(point)) {
            //如果是2次调用则生成默认返回参数
//            return RestMessage.success("重复请求！", null);
            return point.proceed();
        }
        ReqRepetitionCheck check = ((MethodSignature) point.getSignature()).getMethod().getAnnotation(ReqRepetitionCheck.class);
        if (ReqRepetitionCheckEnum.YES.equals(check.isThrowException())) {
            throw new SystemException(check.errorMessage());
        }else{
            return null;
        }
//        return point.proceed();
    }

    //根据参数校验是否重复调用相同接口
    private Boolean isTwiceInvoke(ProceedingJoinPoint point) {

        //参数为空直接跳过
        Object[] args = point.getArgs();
        if (args == null) {
            return true;
        }

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        ReqRepetitionCheck check = method.getAnnotation(ReqRepetitionCheck.class);

        //封装KEY
        String key = String.format("4PL:M:%s:P:%s", method.getName(), SecureUtil.md5(JSONObject.toJSONString(args)));
        log.info("isTwiceInvoke---key:{}", key);
        // 1000毫秒过期，1000ms内的重复请求会认为重复
        long expireTime = check.value();
        long expireAt = System.currentTimeMillis() + expireTime;
        String val = "expireAt@" + expireAt;

        Boolean firstSet = stringRedisTemplate.execute((RedisCallback<Boolean>) connection -> connection.set(key.getBytes(), val.getBytes(), Expiration.milliseconds(expireTime), RedisStringCommands.SetOption.SET_IF_ABSENT));

        if (firstSet != null && firstSet) {
            return true;
        }

//        if (ReqRepetitionCheckEnum.YES.equals(check.isThrowException())) {
//            throw new SystemException(check.errorMessage());
//        }
        return false;
    }

}
