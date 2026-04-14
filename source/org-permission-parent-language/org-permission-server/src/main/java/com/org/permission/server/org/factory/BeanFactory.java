package com.org.permission.server.org.factory;

public interface BeanFactory<V> {


    /**
     *
     * @param v  请求参数
     * @param clzz  返回类型
     * @param <T> 类型泛型
     * @return
     */
    <T> T getBean(V v,Class<T> clzz);

}
