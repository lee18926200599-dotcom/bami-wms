package com.basedata.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseDictionaryVo {

    @ApiModelProperty(value = "字典code")
    public String dictCode;

    @ApiModelProperty(value = "字典name")
    public String dictName;

    @ApiModelProperty(value = "助记码")
    private String mnemonicCode;
}
