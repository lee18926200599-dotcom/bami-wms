package com.common.util.util;

import cn.hutool.core.bean.BeanUtil;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

public class HuToolUtil {

    @SneakyThrows
    public static <T> List<T> exchange(List<?> source, Class<T> clz) {
        List<T> target = new ArrayList<>();
        for (Object o : source) {
            T t = clz.getConstructor().newInstance();
            BeanUtil.copyProperties(o, t);
            target.add(t);
        }
        return target;
    }

    @SneakyThrows
    public static <T> List<T> exchangeIgnoreId(List<?> source, Class<T> clz)  {
        List<T> target = new ArrayList<>();
        for (Object o : source) {
            T t = clz.getConstructor().newInstance();
            BeanUtil.copyProperties(o, t,new String[]{"id"});
            target.add(t);
        }
        return target;
    }

}


