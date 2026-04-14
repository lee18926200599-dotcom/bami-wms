package com.common.util.util;



import cn.hutool.core.util.ObjectUtil;
import com.common.util.exception.AssertException;
import org.apache.commons.lang3.StringUtils;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

public class AssertUtils {
    public AssertUtils() {
    }

    public static void notNull(Object obj) throws AssertException {
        if (ObjectUtil.isNull(obj)) {
            throw new AssertException("参数错误");
        }
    }

    public static void isNotNull(Object obj) throws AssertException {
        notNull(obj);
    }

    public static void isNotNull(Object... objs) throws AssertException {
        Object[] var1 = objs;
        int var2 = objs.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Object o = var1[var3];
            notNull(o);
        }

    }

    public static void isNotNull(String errorMessage, Object... objs) throws AssertException {
        Object[] var2 = objs;
        int var3 = objs.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Object o = var2[var4];
            notNull(o, errorMessage);
        }

    }

    public static void notNull(Object obj, String errorMessage) throws AssertException {
        if (ObjectUtil.isNull(obj)) {
            throw new AssertException(errorMessage);
        }
    }

    public static void isNotNull(Object obj, String errorMessage) throws AssertException {
        notNull(obj, errorMessage);
    }

    public static void isNull(Object obj) throws AssertException {
        if (ObjectUtil.isNotNull(obj)) {
            throw new AssertException("参数错误");
        }
    }

    public static void isNull(Object obj, String errorMessage) throws AssertException {
        if (ObjectUtil.isNotNull(obj)) {
            throw new AssertException(errorMessage);
        }
    }

    public static void isTrue(boolean b) throws AssertException {
        if (!b) {
            throw new AssertException("参数错误");
        }
    }

    public static void isTrue(Boolean b) throws AssertException {
        if (b == null || !b) {
            throw new AssertException("参数错误");
        }
    }

    public static void isTrue(boolean b, String errorMessage) throws AssertException {
        if (!b) {
            throw new AssertException(errorMessage);
        }
    }

    public static void isTrue(Boolean b, String errorMessage) throws AssertException {
        if (b == null || !b) {
            throw new AssertException(errorMessage);
        }
    }

    public static void isNotTrue(boolean b) throws AssertException {
        if (b) {
            throw new AssertException("参数错误");
        }
    }

    public static void isNotTrue(Boolean b) throws AssertException {
        if (b != null && b) {
            throw new AssertException("参数错误");
        }
    }

    public static void isNotTrue(boolean b, String errorMessage) throws AssertException {
        if (b) {
            throw new AssertException(errorMessage);
        }
    }

    public static void isNotTrue(Boolean b, String errorMessage) throws AssertException {
        if (b != null && b) {
            throw new AssertException(errorMessage);
        }
    }

    public static void isEmpty(Integer c) throws AssertException {
        if (c != null && c != 0) {
            throw new AssertException("参数错误");
        }
    }

    public static void isEmpty(Short c) throws AssertException {
        if (c != null && c != 0) {
            throw new AssertException("参数错误");
        }
    }

    public static void isEmpty(Double c) throws AssertException {
        if (c != null && c != 0.0) {
            throw new AssertException("参数错误");
        }
    }

    public static void isEmpty(Float c) throws AssertException {
        if (c != null && c != 0.0F) {
            throw new AssertException("参数错误");
        }
    }

    public static void isEmpty(Collection c) throws AssertException {
        if (c != null && c.size() > 0) {
            throw new AssertException("参数错误");
        }
    }

    public static void isEmpty(Collection c, String errorMessage) throws AssertException {
        if (c != null && c.size() > 0) {
            throw new AssertException(errorMessage);
        }
    }

    public static void isEmpty(Object[] c) throws AssertException {
        if (c != null && c.length > 0) {
            throw new AssertException("参数错误");
        }
    }

    public static void isEmpty(Object[] c, String errorMessage) throws AssertException {
        if (c != null && c.length > 0) {
            throw new AssertException(errorMessage);
        }
    }

    public static void isNotEmpty(Collection c) throws AssertException {
        if (c == null || c.size() == 0) {
            throw new AssertException("参数错误");
        }
    }

    public static void isNotEmpty(Collection c, String errorMessage) throws AssertException {
        if (c == null || c.size() == 0) {
            throw new AssertException(errorMessage);
        }
    }

    public static void isNotEmpty(Object[] c) throws AssertException {
        if (c == null || c.length == 0) {
            throw new AssertException("参数错误");
        }
    }

    public static void isNotEmpty(Object[] c, String errorMessage) throws AssertException {
        if (c == null || c.length == 0) {
            throw new AssertException(errorMessage);
        }
    }

    public static void isBlank(String str) throws AssertException {
        if (!StringUtils.isBlank(str)) {
            throw new AssertException("参数错误");
        }
    }

    public static void isBlank(String str, String errorMessage) throws AssertException {
        if (!StringUtils.isBlank(str)) {
            throw new AssertException(errorMessage);
        }
    }

    public static void isNotBlank(String str) throws AssertException {
        if (StringUtils.isBlank(str)) {
            throw new AssertException("参数错误");
        }
    }

    public static void isNotBlank(String str, String errorMessage) throws AssertException {
        if (StringUtils.isBlank(str)) {
            throw new AssertException(errorMessage);
        }
    }

    public static void isEmpty(String str) throws AssertException {
        if (!StringUtils.isEmpty(str)) {
            throw new AssertException("参数错误");
        }
    }

    public static void isEmpty(String str, String errorMessage) throws AssertException {
        if (!StringUtils.isEmpty(str)) {
            throw new AssertException(errorMessage);
        }
    }

    public static void isNotEmpty(String str) throws AssertException {
        if (StringUtils.isEmpty(str)) {
            throw new AssertException("参数错误");
        }
    }

    public static void isNotEmpty(String str, String errorMessage) throws AssertException {
        if (StringUtils.isEmpty(str)) {
            throw new AssertException(errorMessage);
        }
    }

    public static void isInstanceOf(Class clz, Object obj) throws AssertException {
        if (!clz.isInstance(obj)) {
            throw new AssertException("参数错误");
        }
    }

    public static void isInstanceOf(Class clz, Object obj, String errorMessage) throws AssertException {
        if (!clz.isInstance(obj)) {
            throw new AssertException(errorMessage);
        }
    }

    public static void isNotInstanceOf(Class clz, Object obj) throws AssertException {
        if (clz.isInstance(obj)) {
            throw new AssertException("参数错误");
        }
    }

    public static void isNotInstanceOf(Class clz, Object obj, String errorMessage) throws AssertException {
        if (clz.isInstance(obj)) {
            throw new AssertException(errorMessage);
        }
    }

    public static void fieldsIsNotNull(Object obj, String[] fieldNames) throws AssertException {
        isNotNull(obj);
        if (fieldNames != null && fieldNames.length > 0) {
            String[] var2 = fieldNames;
            int var3 = fieldNames.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String property = var2[var4];
                Object value = ClassUtils.getFieldValue(property, obj);
                isNotNull(value);
            }
        }

    }

    public static void fieldsIsNotNull(Object obj, String errorMessage, String[] fieldNames) throws AssertException {
        isNotNull(obj, errorMessage);
        if (fieldNames != null && fieldNames.length > 0) {
            String[] var3 = fieldNames;
            int var4 = fieldNames.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String property = var3[var5];
                Object value = ClassUtils.getFieldValue(property, obj);
                isNotNull(value, errorMessage + "[" + property + "]");
            }
        }

    }

    public static void pojoEquals(Object expected, Object actual, String... ignoreFields)  {
        List<Field> expectedFields = ClassUtils.getFields(expected.getClass());
        expectedFields.forEach((expectedField) -> {
            if (!expectedField.isSynthetic()) {
                if (!isContains(ignoreFields, expectedField.getName())) {
                    Field actualField = ClassUtils.getField(actual.getClass(), expectedField.getName());
                    if (actualField != null) {
                        if (ClassUtils.getFieldValue(expectedField.getName(), expected) != null && ClassUtils.getFieldValue(actualField.getName(), actual) != null && !ClassUtils.getFieldValue(expectedField.getName(), expected).equals(ClassUtils.getFieldValue(actualField.getName(), actual))) {
                            throw new AssertException("对象不匹配");
                        }
                    }
                }
            }
        });
    }
    private static boolean isContains(String[] array, String target) {
        String[] array1 = array;
        int length = array.length;

        for(int i = 0; i < length; ++i) {
            String v = array1[i];
            if (v.equals(target)) {
                return true;
            }
        }

        return false;
    }   
}
