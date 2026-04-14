package com.common.language.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;


@Component
public class I18nUtils {
    private static MessageSource messageSource;

    @Autowired
    public I18nUtils(MessageSource messageSource) {
        I18nUtils.messageSource = messageSource;
    }

    public static String getMessage(String key) {
        return getMessage(key, null);
    }

    public static String getMessageBlank(String key, Object arg) {
        return getMessage(key, new Object[]{arg});
    }

    public static String getMessage(String key, Object[] args) {
        Locale locale = LocaleContextHolder.getLocale();
        System.out.println("当前语言：" + locale);
        return messageSource.getMessage(key, args, locale);
    }
}
