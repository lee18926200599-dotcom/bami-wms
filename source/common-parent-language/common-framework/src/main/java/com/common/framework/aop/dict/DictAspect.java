package com.common.framework.aop.dict;

import com.common.framework.redis.RedisUtil;
import com.common.framework.user.FplUserUtil;
import com.common.framework.web.SpringBeanLoader;
import com.common.util.message.RestMessage;
import com.github.pagehelper.PageInfo;
import com.common.framework.annotation.DictTarget;
import com.common.framework.annotation.NeedSetValueFeild;
import com.common.framework.constants.RedisConstants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 字典页面属性值获取统一切面
 */
@Aspect
@Component
@Slf4j
public class DictAspect {

    @Autowired
    private RedisUtil redisUtil;

    @Around("@annotation(needSetValueFeild)")
    public Object around(ProceedingJoinPoint joinPoint, NeedSetValueFeild needSetValueFeild) throws Throwable {
        Class clazz = needSetValueFeild.value();
        Long groupId = FplUserUtil.getGroupId();
        groupId = groupId == null ? 0L : groupId;
        Object result = joinPoint.proceed();
        if(null == result){
            return null;
        }
        if (result instanceof List) {
            List<Object> list = (List<Object>) result;
            for (Object sub : list) {
                setDictName(sub, clazz, groupId);
            }
        } else if (result instanceof PageInfo) {
            PageInfo pageInfo = (PageInfo) result;
            List<Object> list = pageInfo.getList();
            if (CollectionUtils.isEmpty(list)) {
                return result;
            }
            for (Object sub : list) {
                setDictName(sub, clazz, groupId);
            }
        } else {
            setDictName(result, clazz, groupId);
        }
        return result;
    }


    private void setDictName(Object obj, Class clazz, long groupId) {
        Field[] fields = obj.getClass().getDeclaredFields();
        List<Field> fieldsList = Arrays.asList(fields);
        Map<String, Object> valueMap = new HashMap<>();
        fieldsList.forEach(field -> {
            field.setAccessible(true);
            if (field.isAnnotationPresent(DictTarget.class)) {
                String dictCode = field.getAnnotation(DictTarget.class).value().name();
                boolean cover = field.getAnnotation(DictTarget.class).cover();
                try {
                    Object itemCode = field.get(obj);
                    if (itemCode == null) {
                        return;
                    }
                    String redisKey = RedisConstants.FPL_DIC_CODE + dictCode + "_" + groupId;
                    Object hmget = redisUtil.hget(redisKey, itemCode.toString());
                    valueMap.put(field.getName() + "Name", hmget);
                    Class<?> type = field.getType();
                    Method method = null;
                    if (type.equals(Integer.class)) {
                        method = obj.getClass().getMethod("set" + firstLetterToUpper(field.getName()), Integer.class);
                    } else if (type.equals(String.class)) {
                        method = obj.getClass().getMethod("set" + firstLetterToUpper(field.getName()), String.class);
                    }

                    if (null != hmget) {
                        if (cover) {
                            method.invoke(obj, hmget);
                        }
                    } else {
//                        Method method2 = clazz.getMethod("queryDictionaryItemByCode", String.class, String.class);
//                        Object dicFein = SpringBeanLoader.getSpringBean(clazz);
//                        RestMessage resObj = (RestMessage) method2.invoke(dicFein, dictCode, itemCode.toString());
                        Method method2 = clazz.getMethod("queryGroupItemByCode", String.class, String.class, Long.class);
                        Object dicFein = SpringBeanLoader.getSpringBean(clazz);
                        RestMessage resObj = (RestMessage) method2.invoke(dicFein, dictCode, itemCode.toString(), groupId);
                        if (resObj.isSuccess()) {
                            Map<String, Object> map = objectToMap(resObj.getData());
                            if (cover) {
                                method.invoke(obj, map.get("itemName"));
                            }
                            valueMap.put(field.getName() + "Name", map.get("itemName"));
                        }
                    }
                } catch (Exception e) {
                    log.error("获取字典类型值失败,{}", e);
                }
            }
        });

        //为了兼容Name字段
        for (String f : valueMap.keySet()) {
            fieldsList.forEach(field -> {
                field.setAccessible(true);
                if (field.getName().equals(f)) {
                    try {
                        Method method = obj.getClass().getMethod("set" + firstLetterToUpper(f), String.class);
                        method.invoke(obj, valueMap.get(f));
                    } catch (Exception e) {
                        log.error("获取字典类型值失败,{}", e);
                    }
                }
            });
        }
    }

    /**
     * 将0bject对象里面的属性和值转化成Map对象
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            map.put(fieldName, value);
        }
        return map;
    }

    public static String firstLetterToUpper(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}
