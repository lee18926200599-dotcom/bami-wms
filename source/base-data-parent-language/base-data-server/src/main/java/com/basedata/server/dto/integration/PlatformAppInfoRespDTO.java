package com.basedata.server.dto.integration;

import lombok.Data;

/**
 *  接口平台平台应用信息对象
 * /platform/list?platformType=2&state=1
 */
@Data
public class PlatformAppInfoRespDTO {

    private String id;

    private String platformType;

    private String name;

    private String serverUrl;

    private String appKey;

    private String appSecret;

    private String callbackUrl;

    private String clientIp;

    private String serviceId;

    private String format;

    private String v;

    private String status;

    private String authUrl;

}
