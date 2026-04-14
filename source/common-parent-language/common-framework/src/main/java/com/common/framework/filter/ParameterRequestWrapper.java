package com.common.framework.filter;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

/**
 * 参数去除空格
 */
public class ParameterRequestWrapper extends HttpServletRequestWrapper {
    private Map<String, String[]> params = new HashMap<String, String[]>();

    private Map<String, String> customHeaders;



    public ParameterRequestWrapper(HttpServletRequest request, Map<String, String> customHeaders) {
        super(request);
        this.customHeaders = customHeaders != null ? customHeaders : new HashMap<>();
    }


    public void putHeader(String name, String value) {
        this.customHeaders.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        // 优先返回自定义的header值
        String headerValue = customHeaders.get(name);
        return (headerValue != null) ? headerValue : super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        // 返回自定义header的名字和原始的header的名字
        Set<String> set = new HashSet<>(customHeaders.keySet());
        Enumeration<String> e = super.getHeaderNames();
        while (e.hasMoreElements()) {
            set.add(e.nextElement());
        }
        return Collections.enumeration(set);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        // 返回自定义header的值和原始的header的值
        List<String> values = new ArrayList<>();
        values.add(customHeaders.get(name));
        Enumeration<String> e = super.getHeaders(name);
        while (e.hasMoreElements()) {
            values.add(e.nextElement());
        }
        return Collections.enumeration(values);
    }

    public ParameterRequestWrapper(HttpServletRequest request) {
        super(request);
        Map<String, String[]> requestMap = request.getParameterMap();
        this.params.putAll(requestMap);
        this.modifyParameterValues();
        this.customHeaders = new HashMap<>();
    }

    /**
     * 重写getInputStream方法  post类型的请求参数必须通过流才能获取到值
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        //非json类型，直接返回
        if (!super.getHeader(HttpHeaders.CONTENT_TYPE).equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            return super.getInputStream();
        }
        //为空，直接返回
        String json = IOUtils.toString(super.getInputStream(), "utf-8");
        if (StringUtils.isEmpty(json)) {
            return super.getInputStream();
        }

        /*
         * 只拦截 map 类型json数据，对于数组类型json数据放行
         */
        final char[] strChar = json.substring(0, 1).toCharArray();
        final char firstChar = strChar[0];
        if (firstChar == '{') {
//            Map<String, Object> map = (Map<String, Object>) JSON.parse(json);
//            map.forEach((key, value) -> {
//                System.out.println(key + ":" + value);
//                if (value instanceof String) {
//                    value = replaceResult((String) value);
//                    map.put(key, value);
//                } else if (value instanceof JSONArray) {
//                    JSONArray valArr = (JSONArray) value;
//                    List<Object> list = new ArrayList<>();
//                    for (Object obj : valArr) {
//                        if (obj instanceof String) {
//                            list.add(replaceResult((String) obj));
//                        } else {
//                            list.add(obj);
//                        }
//                    }
//                    map.put(key, value);
//                }
//            });
            Map<String, Object> map = dealParam(json);
            ByteArrayInputStream bis = new ByteArrayInputStream(JSON.toJSONString(map).getBytes("utf-8"));
            return new MyServletInputStream(bis);
        } else {
            ByteArrayInputStream bis = new ByteArrayInputStream(json.getBytes("utf-8"));
            return new MyServletInputStream(bis);
        }

    }

    private  Map<String,Object> dealParam(String json) {
        Map<String, Object> map = new HashMap<>();
        JSONObject jsonObject = JSON.parseObject(json);
        if (jsonObject != null) {
            jsonObject.forEach((key, value) -> {
                //根据字段类型进行处理
                if (value instanceof JSONArray) {
                    //如果第一层字段为数组，再对数组中每个元素类型判断递归处理
                    List<Object> list = new ArrayList<>();
                    for (Object obj : (JSONArray) value) {
                        if (obj instanceof JSONObject) {
                            list.add(dealParam(obj.toString()));
                        } else if (obj instanceof String) {
                            list.add(replaceResult(obj.toString()));
                        } else {
                            list.add(obj);
                        }
                    }
                    map.put(key, list);
                } else if (value instanceof JSONObject) {
                    //第一层字段为对象 对象内部进行递归处理
                    map.put(key, dealParam(value.toString()));
                } else if (value instanceof String) {
                    //第一层字段为字符串 直接处理
                    map.put(key, replaceResult(value.toString()));
                } else {
                    //其他
                    map.put(key, value);
                }
            });
        }

        return map;
    }
    /**
     * 将parameter的值去除空格后重写回去
     */
    public void modifyParameterValues() {
        Set<String> set = params.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            String[] values = params.get(key);
            values[0] = replaceResult(values[0]);
            params.put(key, values);
        }
    }

    /**
     * 重写getParameter 参数从当前类中的map获取
     */
    @Override
    public String getParameter(String name) {
        String[] values = params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    /**
     * 重写getParameterValues
     */
    public String[] getParameterValues(String name) {
        return params.get(name);
    }

    private String replaceResult(String val) {
        if (org.apache.commons.lang.StringUtils.isBlank(val)) {
            return null;
        }
        val = val.replaceAll("\\r", "").replaceAll("\\n", "");
        return val.trim();

    }

    class MyServletInputStream extends ServletInputStream {
        private ByteArrayInputStream bis;

        public MyServletInputStream(ByteArrayInputStream bis) {
            this.bis = bis;
        }

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {

        }

        @Override
        public int read() throws IOException {
            return bis.read();
        }
    }
}
