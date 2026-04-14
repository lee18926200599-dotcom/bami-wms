package com.common.util.util.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.util.concurrent.TimeUnit;

public class BaseHttpUtil {
    /**
     * 设置超时时间，防止连接被长时间持有，耗尽资源
     */
    private static volatile CloseableHttpClient httpClient = null;

    private BaseHttpUtil() {
    }

    static CloseableHttpClient getHttpClient() {
        if (null == httpClient) {
            synchronized (BaseHttpUtil.class) {
                if (null == httpClient) {
                    PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
                    // 连接池总容量
                    connectionManager.setMaxTotal(200);
                    // 每个host为一组，此参数用于控制每组中连接池的容量 【重要】
                    connectionManager.setDefaultMaxPerRoute(30);

                    RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
                            // 客户端和服务器建立连接的timeout
                            .setConnectTimeout(30000)
                            // 从连接池获取连接的timeout
                            .setConnectionRequestTimeout(30000)
                            // 连接建立后，request没有回应的timeout
                            .setSocketTimeout(30000);

                    HttpClientBuilder clientBuilder = HttpClientBuilder.create()
                            // close retry
                            .disableAutomaticRetries()
                            // set pool
                            .setConnectionManager(connectionManager)
                            // config set
                            .setDefaultRequestConfig(requestConfigBuilder.build())
                            // 连接建立后，request没有回应的timeout
                            .setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(30000).build())
                            // keep alive
                            .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
                    httpClient = clientBuilder.evictIdleConnections(2, TimeUnit.MINUTES).build();
                }
            }
        }
        return httpClient;
    }
}
