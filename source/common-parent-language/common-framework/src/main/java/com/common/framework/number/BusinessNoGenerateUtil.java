package com.common.framework.number;


import com.common.framework.web.SpringBeanLoader;
import com.common.framework.constants.RedisConstants;
import org.springframework.data.redis.core.BoundGeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;


public class BusinessNoGenerateUtil {

    public static String generate(String field, String type) {
        return generateCode(field, type, 6);
    }


    public static String generateV2(String field, DocumentTypeEnum typeEnum) {
        return generateCode(field, typeEnum.getCode(), typeEnum.getSize());
    }

    private static String generateCode(String field, String type, int len) {
        StringRedisTemplate stringRedisTemplate = SpringBeanLoader.getSpringBean(StringRedisTemplate.class);
        String dateTime = BaseNoGenerateUtil.localDateParseStr(LocalDate.now(), "yyMMdd");
        String key = null;
        if (StringUtils.hasLength(field)) {
            key = RedisConstants.BUSINESS_KEY + field + type + dateTime;
        } else {
            key = RedisConstants.BUSINESS_KEY + type + dateTime;
        }
        ValueOperations<String, String> forValue = stringRedisTemplate.opsForValue();
        Long number = null;
        if (stringRedisTemplate.hasKey(key)) {
            Long increment = forValue.increment(key, 1L);
            number = increment;
        } else {
            //设置初值
            forValue.set(key, "0");
            BoundGeoOperations<String, String> ops = stringRedisTemplate.boundGeoOps(key);
            //多久之后过期.设置一天后过期
            ops.expire(1, TimeUnit.DAYS);
            Long increment = forValue.increment(key, 1L);
            number = increment;
        }
        String businessNo = type + dateTime + String.format("%0" + len + "d", number);
        if (number.compareTo(Long.valueOf(org.apache.commons.lang.StringUtils.rightPad("9", len, "9"))) > 0) {
            businessNo = type + dateTime + number;
        }
        return businessNo;
    }


    public static String generateSerialNumber(String field, int len) {
        StringRedisTemplate stringRedisTemplate = SpringBeanLoader.getSpringBean(StringRedisTemplate.class);
        String key = RedisConstants.BUSINESS_KEY + field;
        ValueOperations<String, String> forValue = stringRedisTemplate.opsForValue();
        Long number = null;
        if (stringRedisTemplate.hasKey(key)) {
            Long increment = forValue.increment(key, 1L);
            number = increment;
        } else {
            //设置初值
            forValue.set(key, "0");
            BoundGeoOperations<String, String> ops = stringRedisTemplate.boundGeoOps(key);
            //多久之后过期.设置一天后过期
            ops.expire(30, TimeUnit.DAYS);
            Long increment = forValue.increment(key, 1L);
            number = increment;
        }
        String businessNo = String.format("%0" + len + "d", number);
        return businessNo;
    }

}
