package com.common.language.config;


import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class I18nLocaleResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        //获取client端传递的"language"参数
        String language = httpServletRequest.getHeader("Lang");
        //获取默认的设置的"地区"和"语言"
        Locale locale = Locale.getDefault();
        //判断接收到client端传递的"language"是否为空
        if (!StringUtils.isEmpty(language)) {
            String[] split = new String[2]; //通过"_"进行分解language（格式如：zh_CN）
            if (StringUtils.contains(language, "_")){
                split = language.split("_");
            } else if (StringUtils.contains(language, "-")) {
                split = language.split("-");
            }
            locale = new Locale(split[0], split[1]); //手动创建前端的地区和语言
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
