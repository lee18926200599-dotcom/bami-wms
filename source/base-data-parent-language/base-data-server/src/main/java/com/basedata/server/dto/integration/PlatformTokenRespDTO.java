package com.basedata.server.dto.integration;

import lombok.Data;

import java.util.Date;

/**
 *  接口平台货主平台店铺token返回对象
 */
@Data
public class PlatformTokenRespDTO {
    private String platformId;

    private String customerId;

    private String appKey;

    private String appName;

    private String appSecret;

    private String accessToken;

    private Date accessTokenExpired;
    /**
     * 平台openid
     */
    private String openId;

    private String shopName;

    private String refreshToken;
    /**
     * 商家编号
     */
    private String venderId;
    /**
     * 商家店铺id
     */
    private String venderShopId;
    /**
     * 商家店铺名称
     */
    private String venderShopName;


}
