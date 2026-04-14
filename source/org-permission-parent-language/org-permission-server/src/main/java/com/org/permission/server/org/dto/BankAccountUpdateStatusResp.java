package com.org.permission.server.org.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("更新返回结果")
public class BankAccountUpdateStatusResp {

    @ApiModelProperty("操作是否成功")
    private boolean flag;

    @ApiModelProperty("提示信息")
    private String msg;


    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
