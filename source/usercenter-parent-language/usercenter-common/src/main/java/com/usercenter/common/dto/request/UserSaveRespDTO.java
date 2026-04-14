package com.usercenter.common.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.usercenter.common.dto.BaseUserCommonDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserSaveRespDTO extends BaseUserCommonDto {

    @ApiModelProperty(value ="是否展示密码")
    private Boolean showPassword;

    @ApiModelProperty(value ="showPassword为false时的提示内容")
    private String message;
    /**
     * token
     */
    @ApiModelProperty(value ="token")
    private String token;
    /**
     * token 失效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value ="token 失效时间 yyyy-MM-dd HH:mm:ss")
    private Date tokenExpireDate;
    /**
     * token 失效时间
     */
    @ApiModelProperty(value ="token 失效时间（秒）")
    private Integer tokenExpireTime;
}
