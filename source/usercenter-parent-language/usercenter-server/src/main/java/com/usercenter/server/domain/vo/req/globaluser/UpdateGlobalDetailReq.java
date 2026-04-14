package com.usercenter.server.domain.vo.req.globaluser;

import io.swagger.annotations.ApiModelProperty;

/**
 * 修改全局用户信息
 */
public class UpdateGlobalDetailReq {

    @ApiModelProperty(value="ID")
    private Integer id; //ID
    @ApiModelProperty(value="用户编码")
    private String userNumber; //用户编码
    @ApiModelProperty(value="启用状态  非启用(0)、启用(1)、停用(2)")
    private Boolean enable;
    @ApiModelProperty(value="锁定状态   未锁定(0)、锁定(1)")
    private Boolean lock;
    @ApiModelProperty(value="用户密码")
    private String password;
    @ApiModelProperty(value="备注信息")
    private String remark;

    public Integer getId() {
        return id;
    }

    public UpdateGlobalDetailReq setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public UpdateGlobalDetailReq setUserNumber(String userNumber) {
        this.userNumber = userNumber;
        return this;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    public String getPassword() {
        return password;
    }

    public UpdateGlobalDetailReq setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public UpdateGlobalDetailReq setRemark(String remark) {
        this.remark = remark;
        return this;
    }
}
