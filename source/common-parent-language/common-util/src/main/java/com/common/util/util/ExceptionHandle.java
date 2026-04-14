package com.common.util.util;

import com.common.util.message.RestMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class ExceptionHandle {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestMessage exceptionHandler(MethodArgumentNotValidException e)
    {
        return RestMessage.error(e.getBindingResult().getFieldError().getDefaultMessage());
    }
    @ExceptionHandler(Exception.class)
    public RestMessage allExceptionHandler(Exception e)
    {
        return RestMessage.error(e.getMessage());
    }
}
