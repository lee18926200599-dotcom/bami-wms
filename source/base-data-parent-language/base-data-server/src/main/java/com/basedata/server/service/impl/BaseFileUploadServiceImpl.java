package com.basedata.server.service.impl;

import com.basedata.server.service.BaseFileUploadService;
import com.common.base.enums.FileUploadType;
import com.common.framework.oss.OSSUploadUtil;
import com.common.language.util.I18nUtils;
import com.common.util.util.AssertUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BaseFileUploadServiceImpl implements BaseFileUploadService {


    @Autowired
    private OSSUploadUtil uploadUtil;

    @SneakyThrows
    @Override
    public String upload(MultipartFile file, FileUploadType type) {
        AssertUtils.isNotNull(file, I18nUtils.getMessage("base.file.upload"));
        String originalFilename = file.getOriginalFilename();
        //完整文件名
        String fileName = type.getPrefix().concat("/").concat(originalFilename).replaceFirst("/", "");

        return uploadUtil.uploadByte(file.getBytes(), fileName);
    }
}
