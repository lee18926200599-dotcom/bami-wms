package com.org.permission.common.util;

import cn.hutool.core.bean.BeanUtil;

import java.util.ArrayList;
import java.util.List;


public class HuToolUtil {

    public static <T> List<T> exchange(List<?> source, Class<T> clz) throws Exception {
        List<T> target = new ArrayList<>();
        for (Object o : source) {
            T t = clz.getConstructor().newInstance();
            BeanUtil.copyProperties(o, t);
            target.add(t);
        }
        return target;
    }

}


