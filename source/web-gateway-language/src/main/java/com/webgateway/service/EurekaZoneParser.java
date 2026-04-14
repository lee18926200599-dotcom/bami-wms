package com.webgateway.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import com.jayway.jsonpath.Criteria;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import net.minidev.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RefreshScope
public class EurekaZoneParser {

    private static final Logger log = LoggerFactory.getLogger(EurekaZoneParser.class);

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Value("${eureka.client.serviceUrl.defaultZone:}")
    private String eurekaUrl;

    public Map<String, Object> parseEurekaStatus(String serviceName, String authorization) {
        return getCache(serviceName, authorization);
    }

    public String parseEureka(String authorization) {

        if (!StringUtils.hasText(eurekaUrl)) {
            return null;
        }

        //可能是多个
        List<String> urls = StrUtil.split(eurekaUrl, ',');

        String defaultUrl = urls.get(0);

        if (!StrUtil.startWith(defaultUrl, "http")) {
            return null;
        }

        String realUrl = URLUtil.completeUrl(URLUtil.url(defaultUrl).getAuthority(), "/data/status");


        return get(realUrl, authorization);

    }

    public String resend(String url, String authorization) {
        if (!StringUtils.hasText(url)) {
            return null;
        }

        if (!StrUtil.startWith(url, "http")) {
            return null;
        }

        String realUrl = StrUtil.replace(url, "apidoc.html", "v2/api-docs", true);

        return get(realUrl, authorization);

    }

    private String get(String url, String auth) {
        return HttpUtil.createGet(url).header("Authrization", auth).timeout(30000).execute().body();
    }

    private Map<String, Object> getCache(String serviceName, String token) {
        String redisKey = "apidocs:" + serviceName;

        Map<String, Object> map = (Map<String, Object>) redisTemplate.opsForValue().get(redisKey);
        if (null != map) {
            return map;
        }

        map = new HashMap<>(8);

        String realUrl = parseServiceUrl(parseEureka(token), serviceName);
        if (realUrl == null) {
            return null;
        }
        map.put("url", realUrl);
        map.put("tags", parseTags(realUrl, token));
        redisTemplate.opsForValue().set(redisKey, map, 24, TimeUnit.HOURS);
        return map;

    }


    private Object parseTags(String serviceUrl, String token) {
        String data = resend(serviceUrl, token);
        return JsonPath.read(data, "$.tags");
    }

    private String parseServiceUrl(String data, String serviceName) {
        ReadContext readContext = JsonPath.parse(data);
        Filter filter = Filter.filter(Criteria.where("name").eq(serviceName.toUpperCase()));
        JSONArray instanceInfos = readContext.read("$.apps[?]", filter);

        if (null == instanceInfos) {
            return null;
        }

        String path = JsonPath.read(instanceInfos.get(0), "$.instanceInfos[0].instances[0].url");

        log.info("- > parseServiceUrl = {},{}", serviceName, path);

        return path;

    }
}
