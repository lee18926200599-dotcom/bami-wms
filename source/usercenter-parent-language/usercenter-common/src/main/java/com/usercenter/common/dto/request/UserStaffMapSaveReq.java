package com.usercenter.common.dto.request;

import com.usercenter.common.dto.CommonDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("用户人员映射保存请求")
public class UserStaffMapSaveReq extends CommonDto implements Serializable {

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 人员id
     */
    @ApiModelProperty(value = "人员id")
    private Integer staffId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }
}
