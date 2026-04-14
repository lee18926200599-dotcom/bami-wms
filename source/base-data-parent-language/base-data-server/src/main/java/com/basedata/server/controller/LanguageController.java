package com.basedata.server.controller;

import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 国际化语言接口文档
 */
@Api(tags = "国际化语言接口文档")
@RestController
@RequestMapping("/lang")
public class LanguageController {

    @GetMapping("/test")
    public RestMessage test(){

        String msg = I18nUtils.getMessage("base.testmessage");
        String msg1 = I18nUtils.getMessage("base.testmessage2",new String[]{"测试"});
        return RestMessage.querySuccess(msg+"------"+msg1);
    }
}
