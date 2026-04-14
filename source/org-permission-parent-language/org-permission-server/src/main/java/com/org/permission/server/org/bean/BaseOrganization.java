package com.org.permission.server.org.bean;


import com.org.permission.common.bean.BaseBean;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * 组织实体
 */
@Data
public class BaseOrganization extends BaseBean implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 组织类型（1 平台;2 集团;3 业务单元;4部门）
     */
    private Integer orgType;

    /**
     * 组织编码
     */
    private String orgCode;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 主组织
     */
    private Boolean mainOrgFlag;

    /**
     * 集团ID
     */
    private Long groupId;

    /**
     * 上级组织
     */
    private Long parentId;

    /**
     * 上级业务单元
     */
    private Long parentBuId;

    /**
     * 组织简称
     */
    private String orgShortName;

    /**
     * 实体属性字典编码
     */
    private String entityCode;

    /**
     * 实体属性
     */
    private String entityName;

    /**
     * 行业字典码
     */
    private String industryCode;

    /**
     * 行业字典名
     */
    private String industryName;

    /**
     * 国家三位编码
     */
    private String regionCode;

    private String regionName;

    /**
     * 省ID
     */
    private Long provinceCode;

    /**
     * 省名称
     */
    private String provinceName;

    /**
     * 市ID
     */
    private Long cityCode;

    /**
     * 市名称
     */
    private String cityName;

    /**
     * 区ID
     */
    private Long districtCode;

    /**
     * 区名称
     */
    private String districtName;

    /**
     * 街道ID
     */
    private Long streetCode;

    /**
     * 街道名称
     */
    private String streetName;

    /**
     * 地址
     */
    private String address;

    /**
     * 电话
     */
    private String phone;

    private String email;

    /**
     * 网址
     */
    private String netAddress;

    /**
     * 信用代码
     */
    private String creditCode;

    /**
     * 所属公司
     */
    private Long companyId;

    /**
     * 成立时间
     */
    private Date establishTime;

    /**
     * 本位币
     */
    private String currency;

    /**
     * 全局客户ID
     */
    private Long custId;

    /**
     * 部门负责人
     */
    private Long depDutyStaff;

    /**
     * 内部客户ID
     */
    private Long innerCustId;

    /**
     * 客户业务类型
     */
    private String businessType;

    /**
     * 状态（1 未启用;2 启用;3 停用;4删除）
     */
    private Integer state;

    /**
     * 启用时间
     */
    private Date startTime;

    /**
     * 停用时间
     */
    private Date stopTime;

    /**
     * 初始化状态（0 未完成;1 完成）
     */
    private Integer initFlag;

    /**
     * 引用状态（0 未引用;1 已引用）
     */
    private Integer quotedFlag;

    /**
     * 简介
     */
    private String remark;

    /**
     * 主营业务
     */
    private String mainBusiness;

    /**
     * 版本号
     */
    private String version;

    /**
     * LOGO URL
     */
    private String logoUrl;
}