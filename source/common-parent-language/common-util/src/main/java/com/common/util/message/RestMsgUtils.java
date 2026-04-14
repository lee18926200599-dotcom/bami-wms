package com.common.util.message;

import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class RestMsgUtils {
    private static final Logger log = LoggerFactory.getLogger(RestMsgUtils.class);

    public RestMsgUtils() {
    }

    public static <T> T retrieveResult(RestMessage<T> restMessage) {
        return retrieveResult(restMessage, "");
    }

    public static <T> T retrieveResult(RestMessage<T> restMessage, String msg) {
        log.info("- > RestMessage input msg = {} and result = {}", msg, JSONUtil.toJsonStr(restMessage));
        Assert.notNull(restMessage, "RestMessage is null.");
        if (!restMessage.isSuccess()) {
            throw new RemoteAccessException(msg + " " + restMessage.getMessage());
        } else {
            return restMessage.getData();
        }
    }

    public static <T> List<T> retrieveList(RestMessage<T> restMessage, String msg) {
        log.info("- > RestMessage input msg = {} and result = {}", msg, JSONUtil.toJsonStr(restMessage));
        Assert.notNull(restMessage, "RestMessage is null.");
        if (!restMessage.isSuccess()) {
            throw new RemoteAccessException(msg + " " + restMessage.getMessage());
        } else {
            return restMessage.getData() instanceof List ? (List)restMessage.getData() : Collections.singletonList(restMessage.getData());
        }
    }

    public static <T> T retrieveResult(Supplier<RestMessage<T>> supplier, String msg) {
        RestMessage<T> restMessage = (RestMessage)supplier.get();
        return retrieveResult(restMessage, msg);
    }
}
