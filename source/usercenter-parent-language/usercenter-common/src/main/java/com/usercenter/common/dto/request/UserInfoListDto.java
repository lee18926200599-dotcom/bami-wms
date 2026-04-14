package com.usercenter.common.dto.request;

import java.io.Serializable;
import java.util.List;

public class UserInfoListDto implements Serializable {

    private List<Long> userIds;


    public List<Long> getUserIds() {
        return userIds;
    }

    public UserInfoListDto setUserIds(List<Long> userIds) {
        this.userIds = userIds;
        return this;
    }
}
