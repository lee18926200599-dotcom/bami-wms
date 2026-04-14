package com.usercenter.server.common.exception;


import com.usercenter.server.common.enums.ReturnCodesEnum;
import lombok.Data;

import java.util.Map;

/**
 * 报警异常
 */
@Data
public class WarnException extends RuntimeException {

    /**
     * 提示枚举
     */
    private ReturnCodesEnum returnCodesEnum;

    /**
     * 提示码
     */
    private Integer code;

    /**
     * 附加属性
     */
    private Map<String, Object> attachment;

    public WarnException() {
        super();
    }

    public WarnException(String message) {
        super(message);
    }

    public WarnException(ReturnCodesEnum returnCodesEnum) {
        super(returnCodesEnum.getMessage());
        this.code = returnCodesEnum.getCode();
    }

    public WarnException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public WarnException(ReturnCodesEnum returnCodes, Map<String, Object> attachment) {
        super(returnCodes.getMessage());
        this.code = returnCodes.getCode();
        this.attachment = attachment;
    }
}
