package com.common.util.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IdworkInitializing implements ApplicationListener<WebServerInitializedEvent> {


    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        log.info("------"+webServerInitializedEvent.getWebServer().getPort());
        IdWorker idWorker = new IdWorker(webServerInitializedEvent.getWebServer().getPort());
    }
}
