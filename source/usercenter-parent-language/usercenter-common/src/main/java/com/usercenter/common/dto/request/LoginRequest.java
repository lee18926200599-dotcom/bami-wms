package com.usercenter.common.dto.request;

import com.usercenter.common.enums.BusinessSystemEnum;
import com.usercenter.common.enums.LoginModeEnum;
import com.usercenter.common.enums.SourceTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登录参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest implements Serializable {
    private static final long serialVersionUID = 3842442304453507428L;
    /**
     * 登陆名
     */
    @ApiModelProperty(value = "登录名", required = true)
    private String loginName;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码")
    private String code;

    /**
     * 是否使用验证码
     */
    @ApiModelProperty(value = "使用验证码(true,false) 默认 false")
    private Boolean useCode;

    /**
     * 登陆系统
     */
    @ApiModelProperty(value = "当前业务系统")
    private BusinessSystemEnum businessSystem;

    /**
     * 端（pc/app/rf）
     */
    @ApiModelProperty(value = "登录来源类型")
    private SourceTypeEnum sourceType;

    /**
     * 验证码token
     */
    @ApiModelProperty(value = "验证码Token(获取验证码接口header里)")
    private String captchaToken;

    @ApiModelProperty(value = "登录模式(NONE,APP_SINGLE,GLOBAL_SINGLE)")
    private LoginModeEnum loginMode;
    @ApiModelProperty(value = "集团Id,切换集团时使用")
    private Long groupId;

    public LoginRequest(String loginName, String password, String code, Boolean useCode, BusinessSystemEnum businessSystem, SourceTypeEnum sourceType, String captchaToken) {
        this.loginName = loginName;
        this.password = password;
        this.code = code;
        this.useCode = useCode;
        this.businessSystem = businessSystem;
        this.sourceType = sourceType;
        this.captchaToken = captchaToken;
    }

    public LoginRequest(String loginName, String password, String code, Boolean useCode, BusinessSystemEnum businessSystem, SourceTypeEnum sourceType, String captchaToken, LoginModeEnum loginMode) {
        this.loginName = loginName;
        this.password = password;
        this.code = code;
        this.useCode = useCode;
        this.businessSystem = businessSystem;
        this.sourceType = sourceType;
        this.captchaToken = captchaToken;
        this.loginMode = loginMode;
    }

    public LoginRequest(BusinessSystemEnum businessSystem, SourceTypeEnum sourceType) {
        this.businessSystem = businessSystem;
        this.sourceType = sourceType;
    }
}
