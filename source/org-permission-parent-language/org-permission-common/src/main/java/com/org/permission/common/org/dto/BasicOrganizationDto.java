package com.org.permission.common.org.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BasicOrganizationDto extends BaseAddressDto {
    /**
     * 组织ID
     */
    private Long id;
    /**
     * 组织名称
     */
    private String orgName;
    /**
     * 集团ID
     */
    private Long groupId;
    /**
     * 集团名
     */
    private String groupName;
    /**
     * 客户ID
     */
    private Long custId;
    /**
     * 组织类别
     * 2 集团
     * 3 业务单元
     * 4 部门
     */
    private Integer orgType;
    /**
     * 是否为根业务单元
     * 1是；0否
     */
    private Integer mainOrgFlag;
    /**
     * 组织编码
     */
    private String orgCode;
    /**
     * 组织简称
     */
    private String orgShortName;
    /**
     * 行业字典编码
     */
    private String industryCode;
    /**
     * 所属行业
     */
    private String industryName;
    /**
     * 业务类型
     */
    private String businessType;
    /**
     * 所属公司id
     */
    private Long companyId;
    /**
     * 所属公司名称
     */
    private String companyName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话
     */
    private String phone;

    /**
     * 上级组织
     */
    private Long parentId;

    /**
     * 上级组织名字
     */
    private String parentName;

    /**
     * 归属业务单元
     */
    private Long parentBuId;

    /**
     * 上级组织名字
     */
    private String parentBuName;
    /**
     * 状态
     * 1未启用；2启用；3停用；4删除
     */
    private Integer state;


    /**
     * 部门负责人id
     */
    private Long depDutyStaff;

    /**
     * 部门负责人
     */
    private String depDutyStaffName;

    /**
     * 备注
     */
    private String remark;

    private Date establishTime;

    private BaseAddressDto baseAddressDto;

    private String creditCode;

    private Date createdDate;

}
