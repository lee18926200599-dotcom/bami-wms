package com.basedata.server.controller;

import com.common.util.message.RestMessage;
import com.basedata.server.service.BaseFileUploadService;
import com.common.base.enums.FileUploadType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文件上传管理")
@RestController
@RequestMapping("/upload")
public class BaseFileUploadController {

    @Autowired
    private BaseFileUploadService service;


    @ApiOperation("图片上传")
    @PostMapping("/image")
    public RestMessage<String> imageUpload(MultipartFile file) {
        return RestMessage.doSuccess(service.upload(file, FileUploadType.IMAGE));
    }
}
