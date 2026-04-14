package com.usercenter.server.utils;

import com.common.framework.redis.RedisUtil;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;


@Component
public class UserRedisUtil extends RedisUtil {


//    @Value("${redis.cache.prefix}")
//    private String redisCachePrefix;

    public String getStr(String key) {
        Object object = get(key);
        return Objects.isNull(object) ? null : object.toString();
    }

    private String addPrefix(String key, String prefix) {
        return prefix + ":" + key;
    }

    /**
     * 设置token
     *
     * @param key     key
     * @param value   值
     * @param seconds 有效时间
     * @return
     **/
    public boolean setToken(String key, String value, int seconds) {
        key = addPrefix(key, "token");
        set(key, value, seconds, TimeUnit.SECONDS);
        return true;
    }

    /**
     * 获取用户所有的keys
     *
     * @param userId
     * @return
     */
    public TreeSet<String> getUserKeys(String userId) {
        userId = addPrefix(userId + ":", "token");
        userId += "*";
        Set<String> k = keys(userId);
        return new TreeSet<>(k);
    }
}
