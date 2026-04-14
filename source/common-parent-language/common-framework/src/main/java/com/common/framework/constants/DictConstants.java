package com.common.framework.constants;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DictConstants {

    /**
     * 字典翻译文本后缀
     */
    public static final String DICT_TEXT_SUFFIX = "Name";

    public static final LoadingCache<String, Map<String, String>> DICTIONARY_CACHE = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(30, TimeUnit.DAYS)
            .expireAfterAccess(10, TimeUnit.DAYS)
            .build(new CacheLoader<String, Map<String, String>>() {
                @Override
                public Map<String, String> load(String key) {
                    Map<String, String> map = new HashMap<>();
                    map.put("nullValue", "nullValue");
                    return map;
                }
            });
}
