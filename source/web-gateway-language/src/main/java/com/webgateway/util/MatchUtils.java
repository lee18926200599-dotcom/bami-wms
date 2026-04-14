package com.webgateway.util;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.AntPathMatcher;

import java.util.Set;

/**
 * 路径匹配
 */
public class MatchUtils {

    private static AntPathMatcher MATCHER = new AntPathMatcher();

    /**
     * ant模式匹配（单个）
     */
    public static boolean antMatch(String pattern, String path) {
        return MATCHER.match(pattern, path);
    }

    /**
     * ant模式匹配（匹配任何一种模式即可）
     */
    public static boolean antMatchAny(Set<String> patterns, String path) {
        if (CollectionUtils.isNotEmpty(patterns)) {
            for (String p : patterns) {
                if (antMatch(p, path)) {
                    return true;
                }
            }
        }
        return false;
    }
}
