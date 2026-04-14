package com.common.util.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class ClassUtils {
    private static final Logger logger = LoggerFactory.getLogger(ClassUtils.class);

    private ClassUtils() {
    }

    public static Object getGetterFieldValue(String fieldName, Object obj) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(fieldName, obj.getClass());
            Method getMethod = pd.getReadMethod();
            return getMethod.invoke(obj);
        } catch (IllegalArgumentException | InvocationTargetException | IntrospectionException | IllegalAccessException var4) {
            logger.warn("获取属性值失败，{}", fieldName);
            return null;
        }
    }

    public static Object setFieldValue(String fieldName, Object obj, Object value) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(fieldName, obj.getClass());
            Method setMethod = pd.getWriteMethod();
            return setMethod.invoke(obj, value);
        } catch (IllegalArgumentException | InvocationTargetException | IntrospectionException | IllegalAccessException var5) {
            logger.warn("获取属性值失败，{}", fieldName);
            return null;
        }
    }

    public static Object getFieldValue(String fieldName, Object obj) {
        Field field = null;
        Object result = null;
        Class<?> clazz = obj.getClass();

        while(clazz != Object.class) {
            try {
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                result = field.get(obj);
                break;
            } catch (Exception var6) {
                clazz = clazz.getSuperclass();
            }
        }

        return result;
    }

    public static List<Field> getFields(Class<?> c) {
        List<Field> list = new ArrayList();
        getFields(c, list);
        return list;
    }

    public static Field getField(Class<?> c, String name) {
        List<Field> list = new ArrayList();
        getFields(c, list);
        Iterator iterator = list.iterator();

        Field field;
        do {
            if (!iterator.hasNext()) {
                return null;
            }

            field = (Field)iterator.next();
        } while(!field.getName().equals(name));

        return field;
    }

    public static boolean containsAnnotation(Field field, Class annotationClass) {
        Annotation obj = field.getAnnotation(annotationClass);
        return obj != null;
    }

    public static boolean containsAnnotation(Class object, Class annotationClass) {
        Annotation obj = object.getAnnotation(annotationClass);
        return obj != null;
    }

    public static boolean containsAnnotation(Method method, Class annotationClass) {
        Annotation obj = method.getAnnotation(annotationClass);
        return obj != null;
    }

    public static Object getAnnotationValue(Class<?> cls, Class<? extends Annotation> annotationClass, String property) {
        try {
            Object an = cls.getAnnotation(annotationClass);
            if (an != null) {
                Method gm = annotationClass.getMethod(property);
                return gm.invoke(an);
            }
        } catch (Exception var5) {
            logger.warn("获取注解值失败，{}", property);
        }

        return null;
    }

    public static Object getAnnotationValue(Method m, Class<? extends Annotation> acls, String proterty) {
        try {
            Object an = m.getAnnotation(acls);
            if (an != null) {
                Method am = acls.getMethod(proterty);
                return am.invoke(an);
            }
        } catch (Exception var5) {
            logger.warn("获取注解值失败，{}", proterty);
        }

        return null;
    }

    public static Object getAnnotationValue(Field field, Class<? extends Annotation> acls, String proterty) {
        try {
            Object an = field.getAnnotation(acls);
            if (an != null) {
                Method am = acls.getMethod(proterty);
                return am.invoke(an);
            }
        } catch (Exception var5) {
            logger.warn("获取注解值失败，{}", proterty);
        }

        return null;
    }

    public static Method getMethodByName(String methodName, Class<?> cls, Object[] arguments) {
        try {
            Method[] methods = cls.getMethods();
            Method[] methods1 = methods;
            int length = methods.length;

            for(int m = 0; m < length; ++m) {
                Method method = methods1[m];
                if (method.getName().equals(methodName)) {
                    Class<?>[] ptypes = method.getParameterTypes();
                    if (ptypes.length == arguments.length) {
                        boolean b = true;

                        for(int i = 0; i < ptypes.length; ++i) {
                            String tsn="";
                            switch (ptypes[i].getSimpleName()) {
                                case "int":
                                    tsn = "Integer";
                                    break;
                                case "short":
                                    tsn = "Short";
                                    break;
                                case "double":
                                    tsn = "Double";
                                    break;
                                case "float":
                                    tsn = "Float";
                                    break;
                                case "boolean":
                                    tsn = "Boolean";
                                    break;
                                case "long":
                                    tsn = "Long";
                                    break;
                                case "byte":
                                    tsn = "Byte";
                                    break;
                                case "char":
                                    tsn = "Character";
                            }

                            String asn = arguments[i].getClass().getSimpleName();
                            if (!asn.equals(tsn)) {
                                b = false;
                                break;
                            }
                        }

                        if (b) {
                            return method;
                        }
                    }
                }
            }

            return null;
        } catch (Exception var14) {
            return null;
        }
    }

    public static void getFields(Class<?> cls, List<Field> list) {
        if (cls != null) {
            if (!isBaseType(cls)) {
                for(Class<?> clazz = cls; clazz != Object.class && clazz != null; clazz = clazz.getSuperclass()) {
                    try {
                        Field[] fds = clazz.getDeclaredFields();
                        Field[] fds1 = fds;
                        int length = fds.length;

                        for(int m = 0; m < length; ++m) {
                            Field field = fds1[m];

                            try {
                                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), cls);
                                if (pd != null) {
                                    list.add(field);
                                }
                            } catch (IntrospectionException var9) {
                            }
                        }
                    } catch (Exception var10) {
                    }
                }

            }
        }
    }

    public static boolean isBaseType(Class<?> cls) {
        return cls == Integer.class || cls == Byte.class || cls == Long.class || cls == Double.class || cls == Float.class || cls == Character.class || cls == Short.class || cls == Boolean.class || cls == Long.TYPE || cls == Integer.TYPE || cls == Short.TYPE || cls == Boolean.TYPE || cls == Byte.TYPE || cls == Float.TYPE || cls == Double.TYPE || cls == Character.TYPE || cls == List.class || cls == ArrayList.class || cls == Set.class || cls == BigDecimal.class || cls == BigInteger.class || cls == HashSet.class || cls == HashMap.class || cls == Map.class;
    }
}
