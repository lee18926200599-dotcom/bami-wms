package com.usercenter.server.controller;

import com.google.code.kaptcha.Producer;
import com.google.common.collect.Maps;
import com.usercenter.server.constant.BaseUserConstants;
import com.usercenter.server.utils.UserRedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * 验证码
 */
@Controller
@RequestMapping("/kaptcha")
@Api(tags = "验证码", description = "验证码")
public class CaptchaController {

    @Resource
    private Producer captchaProducer;
    @Autowired
    protected UserRedisUtil userRedisUtil;

    @RequestMapping(value = "/getKaptchaImage")
    @ResponseBody
    @ApiOperation(value = "获取验证码", httpMethod = "POST")
    public Map<String, String> getKaptchaImage(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, String> result = Maps.newHashMap();
        String capText = captchaProducer.createText();
        String uid = UUID.randomUUID().toString().replace("-", "");

        result.put("captchaToken", uid);
        userRedisUtil.set(uid + BaseUserConstants.CAPTCH_TOKEN_SUFFIX, capText, 300);

        BufferedImage bi = captchaProducer.createImage(capText);
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, "jpg", bos);
            byte[] imageBytes = bos.toByteArray();
            imageString = Base64.encodeBase64String(imageBytes);
            result.put("captcha", "data:image/jpeg;base64," + imageString);
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
