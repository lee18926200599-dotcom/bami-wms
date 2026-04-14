package com.common.framework.number;

import com.common.framework.web.SpringBeanLoader;
import com.common.framework.constants.RedisConstants;
import org.springframework.data.redis.core.BoundGeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;


/**
 *  基础数据编码生成
 */
public class BaseNoGenerateUtil {
    /**
     * 生成编码
     *
     * @param field 分层字段
     * @param type  类型
     * @param len   长度
     * @return
     */
    public static String generateCode(Long field, String type, int len) {
        return generateCode(String.valueOf(field), type, len);
    }

    /**
     * 生成编码 默认长度6位
     *
     * @param field 分层字段
     * @param type  类型
     * @return
     */
    public static String generateCode(Long field, String type) {
        return generateCode(String.valueOf(field), type, 6);
    }

    /**
     * 生成编码 默认长度6位
     *
     * @param field 分层字段
     * @param type  类型
     * @return
     */
    public static String generateCode(String field, String type) {
        return generateCode(String.valueOf(field), type, 6);
    }

    /**
     * 生成编码 默认长度6位
     *
     * @param field 分层字段
     * @param type  类型
     * @return
     */
    public static String generateCode(String keyPrefix, String field, String type, int len) {
        return generateCode(keyPrefix, field, type, len);
    }

    /**
     * 生成编码
     *
     * @param field 分层字段
     * @param type  类型
     * @param len   长度
     * @return
     */
    public static String generateCode(String field, String type, int len) {
        return generateCodeBase(RedisConstants.FPL_WAREHOUSE_CATEGORY, field, type, len);
    }

    /**
     * 生成编码
     *
     * @param field 分层字段
     * @param type  类型
     * @return
     */
    public static String generateRuleCode(String field, String type) {
        return generateCodeBase(RedisConstants.FPL_WMS_RULETYPE, field, type, 4);
    }

    /**
     * 生成编码
     *
     * @param field 分层字段
     * @return
     */
    public static String generateRuleCode(String field) {
        return generateCodeBase(RedisConstants.FPL_WMS_RULETYPE, field, "RULE", 4);
    }
    /**
     * 生成编码
     *
     * @param field 分层字段
     * @param type  类型
     * @param len   长度
     * @return
     */
    public static String generateCodeBase(String keyPrefix, String field, String type, int len) {
        StringRedisTemplate stringRedisTemplate = SpringBeanLoader.getSpringBean(StringRedisTemplate.class);
        String day = localDateParseStr(LocalDate.now(), "yyMMdd");
        keyPrefix = org.apache.commons.lang.StringUtils.isBlank(keyPrefix) ? RedisConstants.FPL_WMS_DEFAULT : keyPrefix;
        String key = null;
        if (StringUtils.hasLength(field)) {
            key = keyPrefix + field + type + day;
        } else {
            key = keyPrefix + type + day;
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
            ops.expire(25L, TimeUnit.HOURS);
            Long increment = forValue.increment(key, 1L);
            number = increment;
        }
        String businessNo = day + String.format("%0" + len + "d", number);
        if (number.compareTo(Long.valueOf(org.apache.commons.lang.StringUtils.rightPad("9", len, "9"))) > 0) {
            businessNo = day + number;
        }
        return businessNo;
    }


    public static String generateCodeSerial(String keyPrefix, String prefix, int len, Long groupId) {
        StringRedisTemplate stringRedisTemplate = SpringBeanLoader.getSpringBean(StringRedisTemplate.class);
        keyPrefix = org.apache.commons.lang.StringUtils.isBlank(keyPrefix) ? RedisConstants.FPL_WMS_DEFAULT : keyPrefix;
        String groupIdStr = groupId == null ? "" : String.valueOf(groupId);
        String key = null;
        if (StringUtils.hasLength(prefix)) {
            key = keyPrefix + ":" + prefix + ":" + groupIdStr;
        } else {
            key = keyPrefix + ":" + groupIdStr;
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
            Long increment = forValue.increment(key, 1L);
            number = increment;
        }
        String businessNo = String.format("%0" + len + "d", number);
        if (number.compareTo(Long.valueOf(org.apache.commons.lang.StringUtils.rightPad("9", len, "9"))) > 0) {
            return prefix +number;
        }
        return prefix + businessNo;
    }

    public static String localDateParseStr(LocalDate localDate, String format){
        if (localDate == null){
            return null;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return df.format(localDate);
    }
}
