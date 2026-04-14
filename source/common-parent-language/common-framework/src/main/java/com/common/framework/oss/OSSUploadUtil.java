package com.common.framework.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
public class OSSUploadUtil {

    @Value("${oss.accessKeyId:}")
    private String accessKeyId;

    @Value("${oss.accessKeySecret:}")
    private String accessKeySecret;

    @Value("${oss.endpoint:}")
    private String endpoint;

    @Value("${oss.bucketName:}")
    private String bucketName;

    public String upload(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + file.getOriginalFilename();
        String fileUrl = getUrl(fileName);

        OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, fileName, file.getInputStream());
        ossClient.shutdown();

        return fileUrl;
    }

    public String uploadByte(byte[] excelBytes, String fileName) {
        OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(excelBytes);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(excelBytes.length);
        ossClient.putObject(new PutObjectRequest(bucketName, fileName, inputStream, metadata));
        ossClient.shutdown();
        return getUrl(fileName);
    }

    public String uploadInputSteam(InputStream inputStream, String fileName) {
        OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, fileName, inputStream);
        ossClient.shutdown();
        return getUrl(fileName);
    }

    public String uploadFile(File file, String fileName) {
        OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, fileName, file);
        ossClient.shutdown();
        return getUrl(fileName);
    }

    private String getUrl(String fileName) {
        // 可根据实际情况拼接Url
        return "https://" + bucketName + "." + endpoint + "/" + fileName;
    }
}
