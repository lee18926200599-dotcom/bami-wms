package com.basedata.server.service;

import com.common.base.enums.FileUploadType;
import org.springframework.web.multipart.MultipartFile;

public interface BaseFileUploadService {

    String upload(MultipartFile file, FileUploadType type);
}
