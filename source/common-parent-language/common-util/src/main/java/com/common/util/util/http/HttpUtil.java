package com.common.util.util.http;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.*;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;

@Slf4j
public class HttpUtil {
    private static RestTemplate restTemplate = SpringUtil.getBean(RestTemplate.class);
    public HttpUtil() {
    }

    /**
     * post调用接口
     *
     * @param dataObj post参数对象
     * @param url
     * @return
     * @throws Exception
     */
    public static String post(Object dataObj, String url) throws Exception {
        return post(dataObj, url, true);
    }

    /**
     * @param dataObj        post参数对象
     * @param url
     * @param throwException 抛出异常true；不抛出异常false
     * @return
     * @throws Exception
     */
    public static String post(Object dataObj, String url, boolean throwException) throws Exception {
        try {
            String msg = JSONUtil.toJsonStr(dataObj);
            HttpPost httpPost = new HttpPost(url);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            // 解决中文乱码问题
            StringEntity entity = new StringEntity(msg, "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("text/json");
            httpPost.setEntity(entity);
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity1 = httpResponse.getEntity();
            return EntityUtils.toString(entity1, "utf8");
        } catch (ClientProtocolException e) {
            if (throwException) {
                log.info("postWithError error: {}", e.getMessage());
                throw new Exception("调用异常：" + e.getMessage());
            }
            log.info("post request params: {}", dataObj);
        }
        return null;
    }

    /**
     * 发送get请求
     *
     * @param dataMap
     * @param url
     * @return
     * @throws Exception
     */
    public static String get(Map<String, Object> dataMap, String url) throws Exception {
        return get(dataMap, url, true);
    }

    public static String get(Map<String, Object> dataMap, String url, boolean throwException) throws Exception {
        try {
            log.info("send get url：" + url);
            HttpGet httpGet;
            if (CollectionUtils.isEmpty(dataMap)) {
                httpGet = new HttpGet(url);
            } else {
                List<NameValuePair> pairList = new ArrayList<>();
                for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                    pairList.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
                }
                URIBuilder uriBuilder = new URIBuilder();
                uriBuilder.setPath(url);
                uriBuilder.addParameters(pairList);
                URI uri = uriBuilder.build();
                httpGet = new HttpGet(uri);
                log.info("send get full url：" + uri.toString());
            }
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity1 = httpResponse.getEntity();
            return EntityUtils.toString(entity1, "utf8");
        } catch (ClientProtocolException e) {
            if (throwException) {
                log.info("send getWithError error: {}", e.getMessage());
                throw new Exception("调用异常：" + e.getMessage());
            }
            log.info("send get error: {}", e.getMessage());
            log.info("send get request params: {}", dataMap);
        }
        return null;
    }

    /**
     * post调用接口平台api（默认customer_id）
     *
     * @param dataObj post参数对象
     * @param url
     * @return
     * @throws Exception
     */
    public static String postDefault(Object dataObj, String url) throws Exception {
        return postDefault(dataObj, url, true);
    }

    public static String postDefault(Object dataObj, String url, boolean throwException) throws Exception {
        String msg = null;
        try {
            msg = JSONUtil.toJsonStr(dataObj);
            HttpPost httpPost = new HttpPost(url);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            // 解决中文乱码问题
            StringEntity entity = new StringEntity(msg, "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("text/json");
            httpPost.setEntity(entity);
            httpPost.addHeader("customer_id", "111111");
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity1 = httpResponse.getEntity();
            return EntityUtils.toString(entity1, "utf8");
        } catch (ClientProtocolException e) {
            if (throwException) {
                log.info("postWithError error: {}", e.getMessage());
                throw new Exception("调用异常：" + e.getMessage());
            }
            log.info("send postWithError error: {}", e.getMessage());
            log.info("send postWithError request params: {}", msg);
        }
        return null;
    }

    /**
     * post调用接口平台api（带customerId）
     *
     * @param dataObj    post参数对象
     * @param url
     * @param customerId 必传参数
     * @return
     * @throws Exception
     */
    public static String postForIntegration(Object dataObj, String url, String customerId) throws Exception {
        return postForIntegration(dataObj, url, customerId, true);
    }

    public static String postForIntegration(Object dataObj, String url, String customerId, boolean throwException) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        // CloseableHttpClient httpClient = HttpClients.createDefault();
        if (dataObj != null) {
            String msg = JSONUtil.toJsonStr(dataObj);
            // 解决中文乱码问题
            StringEntity stringEntity = new StringEntity(msg, "utf-8");
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("text/json");
            httpPost.setEntity(stringEntity);
        }
        httpPost.addHeader("customer_id", customerId);
        // 自动关闭 response
        try (CloseableHttpResponse httpResponse = BaseHttpUtil.getHttpClient().execute(httpPost)) {
            HttpEntity httpEntity = httpResponse.getEntity();
            return EntityUtils.toString(httpEntity, "utf8");
        } catch (Exception e) {
            if (throwException) {
                log.info("send postIntegrationWithError error: {}", e.getMessage());
                throw new Exception("调用异常：" + e.getMessage());
            }
            log.info("send postForIntegration error: {}", e.getMessage());
            log.info("send postForIntegration request params: {}", JSONUtil.toJsonStr(dataObj));
        } finally {
            httpPost.releaseConnection();
        }
        return null;
    }

    /**
     * post调用接口平台api（带shopCode）
     * @param JsonData
     * @param url
     * @param shopCode
     * @return
     * @throws Exception
     */
    public static String postForRequestOpen(String JsonData, String url, String shopCode) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("customer_id", shopCode);
        org.springframework.http.HttpEntity<Object> entity = new org.springframework.http.HttpEntity<>(JsonData, headers);
        ResponseEntity<String> response =  restTemplate.exchange(url, HttpMethod.POST, entity, String.class );
        if (response.getStatusCode() != HttpStatus.OK){
            throw new Exception("调用失败");
        }
        JSONObject result = JSONObject.parseObject(response.getBody());
        if (result == null || !Objects.equals(true, result.get("success"))){
            throw new Exception(ObjectUtil.isNotNull(result) ? result.get("message").toString() : "调用失败");
        }
        return result.toJSONString();
    }

    public static String getForOpen( String url) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        ResponseEntity<String> response =  restTemplate.exchange(url, HttpMethod.GET,null, String.class );
        if (response.getStatusCode() != HttpStatus.OK){
            throw new Exception("调用失败");
        }
        JSONObject result = JSONObject.parseObject(response.getBody());
        if (result == null || !Objects.equals(true, result.get("success"))){
            throw new Exception(ObjectUtil.isNotNull(result) ? result.get("message").toString() : "调用失败");
        }
        return result.toJSONString();
    }
}
