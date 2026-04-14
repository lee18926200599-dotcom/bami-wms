package com.usercenter.common.dto;

import lombok.Data;

@Data
public class RefreshRedisUserDto {

    private String userName;

    private Long orgId;

    private Long lineId;

}
