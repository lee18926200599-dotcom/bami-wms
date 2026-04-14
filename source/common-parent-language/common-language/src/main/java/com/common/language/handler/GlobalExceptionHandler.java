package com.common.language.handler;

import com.common.language.util.I18nUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidException(MethodArgumentNotValidException e) {
        // 这里可以对错误消息进行国际化处理
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return I18nUtils.getMessage(message);
    }
}
