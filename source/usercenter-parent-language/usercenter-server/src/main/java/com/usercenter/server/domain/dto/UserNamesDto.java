package com.usercenter.server.domain.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class UserNamesDto implements Serializable {

    @ApiModelProperty(value ="用户名列表")
    private List<String> userNames;

    @ApiModelProperty(value ="用户名列表")
    private Boolean withDeleted;

    public List<String> getUserNames() {
        return userNames;
    }

    public void setUserNames(List<String> userNames) {
        this.userNames = userNames;
    }

    public Boolean getWithDeleted() {
        return withDeleted;
    }

    public void setWithDeleted(Boolean withDeleted) {
        this.withDeleted = withDeleted;
    }
}
