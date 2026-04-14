package com.common.log;

import cn.hutool.json.JSONUtil;
import com.common.log.annotation.ChangeLog;
import com.common.log.entity.ChangeBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ObjectUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description 日志记录，实体字典对比，注意日志一定要放在业务的最后一步
 */
@Slf4j
public class FieldCompareUtil<T> {

    /**
     * 修改前后对比
     * @param oldBean
     * @param newBean
     * @return
     */
    public String contrastUpdateObj(Object oldBean, Object newBean) {
        // 转换为传入的泛型T
        T oldPojo = (T) oldBean;
        // 通过反射获取类型及字段属性
        Field[] fields = oldPojo.getClass().getDeclaredFields();
        List<ChangeBean> compare = compare(Arrays.asList(fields), oldPojo, (T) newBean);
        return JSONUtil.toJsonStr(compare);
    }

    public String contrastAddObj(Object newBean) {
        // 转换为传入的泛型T
        T oldPojo = (T) newBean;
        // 通过反射获取类型及字段属性
        Field[] fields = oldPojo.getClass().getDeclaredFields();
        List<ChangeBean> compare = contrast(Arrays.asList(fields), (T) newBean);
        return JSONUtil.toJsonStr(compare);
    }

    private List<ChangeBean> contrast(List<Field> fields,T newBean) {
        List<ChangeBean> list=new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                // 获取属性值
                Object newValue = field.get(newBean);
                ChangeBean bean=new ChangeBean();
                bean.setFieldName(field.getName());
                bean.setNewValue(newValue);
                if(field.isAnnotationPresent(ChangeLog.class)){
                    bean.setFieldDescription(field.getAnnotation(ChangeLog.class).value());
                }
                list.add(bean);
            } catch (Exception e) {
                log.error("比对Bean属性是否变化失败，", e);
            }
        }
        return list;
    }

    private List<ChangeBean> compare(List<Field> fields, T oldBean, T newBean) {
        List<ChangeBean> list=new ArrayList<>();

        for (Field field : fields){
            field.setAccessible(true);
            try {
                // 获取属性值
                Object newValue = field.get(newBean);
                Object oldValue = field.get(oldBean);
                if(newValue==null){
                    continue;
                }
                if (ObjectUtils.notEqual(oldValue, newValue)) {
                    ChangeBean bean=new ChangeBean();
                    bean.setFieldName(field.getName());
                    if(field.isAnnotationPresent(ChangeLog.class)){
                        bean.setFieldDescription(field.getAnnotation(ChangeLog.class).value());
                    }
                    bean.setOldValue(oldValue);
                    bean.setNewValue(newValue);
                    list.add(bean);
                }

            } catch (Exception e) {
                log.error("比对Bean属性是否变化失败，", e);
            }
        }
        return list;
    }

}
