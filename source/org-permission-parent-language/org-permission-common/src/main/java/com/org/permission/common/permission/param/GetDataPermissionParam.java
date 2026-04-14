package com.org.permission.common.permission.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取数据权限请求类
 */
@ApiModel("获取数据权限请求类")
@Data
public class GetDataPermissionParam implements Serializable {

    private static final long serialVersionUID = -5360514729513454433L;

    /**
     * 数据权限维度：0所有（默认） 数据权限维度  1=仓库  2=客户 3=供应商 4=区域 5=业务平台 6=CRM客户查看权限 7=站点
     */
    @ApiModelProperty(notes = "数据权限维度：0所有（默认） 数据权限维度  1=仓库  2=客户 3=供应商 4=区域 5=业务平台 6=CRM客户查看权限 7=站点", required = true)
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
     * 数据权限维度：0所有（默认） 数据权限维度  1=仓库  2=客户 3=供应商 4=区域 5=业务平台 6=CRM客户查看权限 7=站点
     */
    public interface management {
        /**
         * 0 所有（默认）
         */
        Integer DEFAULT = 0;


        /**
         * 仓库
         */
        Integer WAREHOUSE = 1;

        /**
         * 供应商
         */
        Integer SUPPLIER = 2;

        /**
         * 区域
         */
        Integer REGION = 3;

        /**
         * 客户
         */
        Integer CUSTOMER = 4;

        /**
         * 业务平台编号
         */
        Integer BUSINESS_PLATFORM = 5;

        /**
         * 站点
         */
        Integer SITE = 7;

    }
}

