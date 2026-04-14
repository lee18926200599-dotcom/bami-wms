package com.common.util.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@ApiModel(
        description = "返回响应数据"
)
public class RestMessage<T> implements Message, Serializable {
    private static final Logger logger = LoggerFactory.getLogger(RestMessage.class);
    private static final long serialVersionUID = 8847860234810402980L;
    @ApiModelProperty("是否成功")
    private boolean success;
    @ApiModelProperty("消息对象")
    private String message;
    @ApiModelProperty("消息代码")
    private String code;
    @ApiModelProperty("返回对象")
    private T data;
    @ApiModelProperty("异常服务")
    private String serviceName;

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public RestMessage() {
    }

    public static <T> RestMessage<T> querySuccess(T data) {
        return new RestMessage(true, "Success", data);
    }

    public static <T> RestMessage<T> doSuccess(T data) {
        return new RestMessage(true, "Success", data);
    }

    public static <T> RestMessage<T> success(String message, T data) {
        return new RestMessage(true, message, data);
    }

    public static <T> RestMessage<T> error(String message) {
        return new RestMessage(false, "-1", message, (Object)null);
    }

    public static <T> RestMessage<T> queryError(String message) {
        return new RestMessage(false, String.format("Fail，Reason：%s", message), (Object)null);
    }

    public static <T> RestMessage<T> doError(String message) {
        return new RestMessage(false, String.format("Fail，Reason：%s", message), (Object)null);
    }

    public static <T> RestMessage<T> error(String code, String message) {
        return new RestMessage(false, code, message, (Object)null);
    }

    public static <T> RestMessage<T> newInstance(boolean success, String message) {
        return new RestMessage(success, message, (Object)null);
    }

    public static <T> RestMessage<T> newInstance(boolean success, String message, T data) {
        return new RestMessage(success, message, data);
    }

    public static <T> RestMessage<T> newInstance(boolean success, String code, String message, T data) {
        return new RestMessage(success, code, message, data);
    }

    public RestMessage(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.code = "200";
    }

    public RestMessage(boolean success, String code, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.code = code;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String toJsonString() {
        ObjectMapper mapper = new ObjectMapper();
        return this.toJsonString(mapper);
    }

    public String toJsonString(ObjectMapper mapper) {
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException var3) {
            logger.warn("数据序列化失败");
            return null;
        }
    }

    public static <S> RestMessage<S> parseJsonString(ObjectMapper mapper, String jsonstr, TypeReference<RestMessage<S>> typeReference) throws IOException {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        RestMessage<S> rest = (RestMessage)mapper.readValue(jsonstr, typeReference);
        return rest;
    }

    public static <S> RestMessage<S> parseJsonString(String jsonstr, TypeReference<RestMessage<S>> typeReference) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return parseJsonString(mapper, jsonstr, typeReference);
    }

    public static <S> RestMessage<List<S>> parseJsonStringForList(ObjectMapper mapper, String jsonstr, TypeReference<RestMessage<List<S>>> typeReference) throws IOException {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        RestMessage<List<S>> rest = (RestMessage)mapper.readValue(jsonstr, typeReference);
        return rest;
    }

    public static <S> RestMessage<List<S>> parseJsonStringForList(String jsonstr, TypeReference<RestMessage<List<S>>> typeReference) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return parseJsonStringForList(mapper, jsonstr, typeReference);
    }
}
