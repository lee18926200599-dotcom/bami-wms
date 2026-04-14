package com.usercenter.server.domain.vo.req.globaluser;

import com.usercenter.server.domain.vo.req.AbstractPageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户全局参数")
public class GlobalUserReq extends AbstractPageReq {

    @ApiModelProperty(value="用户编码")
    private String userCode;

    @ApiModelProperty(value="用户名")
    private String userName;

    @ApiModelProperty(value="用户密码")
    private String password; 

    @ApiModelProperty(value="手机号码")
    private String phone; 

    @ApiModelProperty(value="电子邮件")
    private String email; 

    @ApiModelProperty(value="属性（暂用于是否全局标识）")
    private Integer attribute; 

    @ApiModelProperty(value="启用状态  非启用(0)、启用(1)、停用(2)")
    private Integer enable; 

    @ApiModelProperty(value="锁定状态   未锁定(0)、锁定(1)")
    private Integer lock; 

    public String getUserCode() {
        return userCode;
    }

    public GlobalUserReq setUserCode(String userCode) {
        this.userCode = userCode;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public GlobalUserReq setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public GlobalUserReq setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public GlobalUserReq setEmail(String email) {
        this.email = email;
        return this;
    }

    public Integer getAttribute() {
        return attribute;
    }

    public GlobalUserReq setAttribute(Integer attribute) {
        this.attribute = attribute;
        return this;
    }

    public Integer getEnable() {
        return enable;
    }

    public GlobalUserReq setEnable(Integer enable) {
        this.enable = enable;
        return this;
    }

    public Integer getLock() {
        return lock;
    }

    public GlobalUserReq setLock(Integer lock) {
        this.lock = lock;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
