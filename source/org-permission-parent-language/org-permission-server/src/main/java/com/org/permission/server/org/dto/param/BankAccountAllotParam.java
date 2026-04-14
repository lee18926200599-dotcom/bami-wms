package com.org.permission.server.org.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

@ApiModel("分配参数")
@Data
public class BankAccountAllotParam {
    @ApiModelProperty("当前登录用户ID")
    private Long userId;

    @ApiModelProperty("当前集团")
    private Long groupId;


    @ApiModelProperty("当前选中资金账号id")
    private Set<Integer> ids;


    @ApiModelProperty("当前选中业务单元IDs")
    private Set<Long> orgIds;


    @ApiModelProperty("当前支付账号ID")
    private Set<Long> accountIds;
}
