package com.common.util.util;

import cn.hutool.core.lang.Assert;
import com.alibaba.excel.annotation.ExcelProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Description: 参数校验工具类：支持@NotNull、@NotBlank等注解、excel注解@ExcelProperty
 * <p>
 * 使用方式，例如：
 * ParamValidatorUtil paramValidatorUtil = new ParamValidatorUtil();
 * paramValidatorUtil.validate(list, "创建上架单参数校验失败！");
 * <p>
 * 或者注入依赖
 * 艾特Resource
 * ParamValidatorUtil paramValidatorUtil;
 */
@Component
public class ParamValidatorUtil {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    /**
     * 校验集合（抛出异常）
     *
     * @param collect 集合对象
     */
    public <E> void validate(Collection<E> collect) {
        validate(collect, "参数校验失败！");
    }

    /**
     * 校验集合（抛出异常）
     *
     * @param collect 集合对象
     */
    public <E> void validate(Collection<E> collect, String headMessage) {
        List<String> msgList = new ArrayList<>();
        headMessage = headMessage == null ? "" : headMessage + "：";
        int i = 0;
        for (E e : collect) {
            i++;
            String errorMsg = validate(e, null, i, false, Default.class);
            if (!StringUtils.isEmpty(errorMsg)) {
                msgList.add(errorMsg);
            }
        }
        if (!msgList.isEmpty()) {
            String errorList = msgList.size() > 10 ? String.join("", msgList.subList(0, 10)) + "..." : String.join("", msgList);
            throw new RuntimeException(headMessage + errorList);
        }
    }

    /**
     * 校验（注解约束条件）单个对象
     *
     * @param object 对象
     * @return 返回异常
     */
    public <T> String validateSingle(T object) {
        return validate(object, "参数校验失败！", null, true, Default.class);
    }

    /**
     * 校验（注解约束条件）单个对象
     *
     * @param object 对象
     * @return 返回异常
     */
    public <T> String validateSingle(T object, String headMessage) {
        return validate(object, headMessage, null, true, Default.class);
    }

    /**
     * excel实体类上的注解约束条件校验
     *
     * @param object         包含@ExcelProperty、@NotNull等注解的实体对象
     * @param index          对象位置索引，从1开始（可为null）
     * @param throwException true抛异常，false不抛异常
     * @param groups
     * @param <T>
     * @return
     */
    public <T> String validate(T object, String headMessage, Integer index, boolean throwException, Class<?>... groups) {
        String errorMessage = validate(object, groups);
        StringBuilder errorMsgBuilder = new StringBuilder();
        if (!StringUtils.isEmpty(errorMessage)) {
            if (headMessage != null) {
                errorMsgBuilder.append(headMessage).append("：");
            }
            if (index != null) {
                errorMsgBuilder.append("第").append(index).append("行：").append(errorMessage);
            } else {
                errorMsgBuilder.append(errorMessage);
            }
            if (throwException) {
                throw new RuntimeException(errorMsgBuilder.toString());
            }
        }
        return errorMsgBuilder.toString();
    }

    /**
     * excel实体类上的注解约束条件校验
     *
     * @param object 包含@ExcelProperty、@NotNull等注解的实体对象
     * @param groups
     * @param <T>
     * @return
     */
    public <T> String validate(T object, Class<?>... groups) {
        Assert.notNull("参数对象不能为空！");
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object, groups);
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            return constraintViolationsProcessor(constraintViolations, object);
        }
        return null;
    }

    private <T0> String constraintViolationsProcessor(Set<ConstraintViolation<T0>> constraintViolations, T0 object) {
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<T0> violation : constraintViolations) {
            Field declaredField;
            try {
                declaredField = object.getClass().getDeclaredField(violation.getPropertyPath().toString());
            } catch (Exception e) {
                throw new RuntimeException("实体类反射异常：" + e.getMessage());
            }
            ExcelProperty excelPropertyAnnotation = declaredField.getAnnotation(ExcelProperty.class);
            if (excelPropertyAnnotation != null) {
                // excel校验：拼接错误信息，包含当前出错数据的标题名字+错误信息
                sb.append("【").append(excelPropertyAnnotation.value()[0]).append("】").append(violation.getMessage());
            } else {
                // 默认异常：字段+约束信息
                sb.append(violation.getPropertyPath().toString())
                        .append(violation.getMessage()).append(";");
            }
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }
}
