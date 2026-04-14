package com.webgateway.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;

public class ServerHttpRequestUtil {

    /**
     * 获取客户端IP
     * @param request
     * @return
     */
    public static String getClientIp(ServerHttpRequest request) {
        String ip = getHeaderByName(request,"x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = getHeaderByName(request,"Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = getHeaderByName(request,"WL-Proxy-Client-IP");
        }
        if ((ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) && request.getRemoteAddress()!=null) {
            ip = request.getRemoteAddress().getHostString();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

    /**
     * 获取user-agent
     * @param request
     * @return
     */
    public static String getUserAgent(ServerHttpRequest request){
       return getHeaderByName(request,"User-Agent");
    }


    /**
     * 从头部中解析token
     * @param request
     * @param header
     * @param tokenHead
     * @param paramName
     * @return
     */
    public static String getTokenFromRequest(ServerHttpRequest request,String header,String tokenHead,String paramName){
        String authToken =null;
        String authHeader = getHeaderByName(request,header);

        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            // The part after "Bearer "
            authToken = authHeader.substring(tokenHead.length());
        }else{
            String param = getParameterByName(request,paramName);
            if(!StringUtils.isEmpty(param)){
                authToken = param;
            }
        }
        return authToken;
    }

    public static String getUidFromRequest(ServerHttpRequest request,String header,String paramName){
        String uid = getHeaderByName(request,header);
        if(StringUtils.isBlank(uid)){
            uid = getParameterByName(request,paramName);
        }
        if(StringUtils.isBlank(uid)){
            uid = ServerHttpRequestUtil.getClientIp(request);
        }
        return uid;
    }

    public static String getHeaderByName(ServerHttpRequest request,String header){
        String result = null;
        List<String> arr = request.getHeaders().get(header);
        if(CollectionUtils.isNotEmpty(arr)){
            result = arr.get(0);
        }
        return result;
    }
    public static String getParameterByName(ServerHttpRequest request,String header){
        String result = null;
        List<String> arr = request.getQueryParams().get(header);
        if(CollectionUtils.isNotEmpty(arr)){
            result = arr.get(0);
        }
        return result;
    }
}
