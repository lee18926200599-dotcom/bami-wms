package com.org.permission.server.permission.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取数据权限请求类
 */
@Data
@ApiModel("获取数据权限请求类")
public class GetDataPermissionReq {

    public GetDataPermissionReq() {
    }

    public GetDataPermissionReq(Integer managementId, Long userId, Long groupId) {
        this.managementId = managementId;
        this.userId = userId;
        this.groupId = groupId;
    }

    /**
     * 数据权限维度：0所有（默认） 1 站点 2仓库 3供应商 4区域 5客户 6业务平台编号 7CRM客户规则权限
     */
    @ApiModelProperty(notes = "数据权限维度：0所有（默认） 1 站点 2仓库 3供应商 4区域 5客户 6业务平台编号 7CRM客户规则权限", required = true)
    private Integer managementId;

    /**
     * 用户id
     */
    @ApiModelProperty(notes = "用户id", required = true)
    private Long userId;

    /**
     * 集团id
     */
    @ApiModelProperty(notes = "集团id", required = true)
    private Long groupId;

    /**
     * 数据权限维度：0所有（默认） 1 站点 2仓库 3供应商 4区域 5客户 6业务平台编号  7CRM客户规则权限
     */
    public interface management {
        /**
         * 0 所有（默认）
         */
        Integer DEFAULT = 0;

        /**
         * 站点
         */
        Integer SITE = 1;

        /**
         * 仓库
         */
        Integer WAREHOUSE = 2;

        /**
         * 供应商
         */
        Integer SUPPLIER = 3;

        /**
         * 区域
         */
        Integer REGION = 4;

        /**
         * 客户
         */
        Integer CUSTOMER = 5;

        /**
         * 业务平台编号
         */
        Integer BUSINESS_PLATFORM = 6;

        /**
         * CRM客户 规则权限
         */
        Integer CRM_CUSTOMER =7;


    }
}

