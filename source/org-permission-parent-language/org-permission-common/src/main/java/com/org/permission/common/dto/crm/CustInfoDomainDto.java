package com.org.permission.common.dto.crm;

import com.org.permission.common.org.dto.BaseAddressDto;
import com.common.base.enums.BooleanEnum;


import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Objects;

/**
 * 客户领域返回信息
 */
@Data
public class CustInfoDomainDto {
    /**
     * 客户ID
     */
    private Long custId;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 客户启用状态
     * <p>
     * 启停状态 {@link com.common.base.enums.StateEnum}
     */
    private Integer enableState;
    /**
     * 客户冻结状态
     * <p>
     * 0 解冻；
     * 1 冻结；
     */
    private Integer frozenState;
    /**
     * 客户入驻状态
     * <p>
     * 0 未入驻
     * 1 已入住；
     */
    private Integer enterState;
    /**
     * 用户编码
     */
    private String userCode;
    /**
     * 客户业务类型
     */
    private String bizTypeId;
    /**
     * 企业类型
     */
    private Integer enterpriseType;
    /**
     * 组织机构代码
     */
    private String orgCode;
    /**
     * 注册资本
     */
    private String registeredCapital;
    /**
     * 集团名称
     */
    private String orgName;
    /**
     * 业务开始时间
     */
    private Date businessStartTime;
    /**
     * 业务结束时间
     */
    private Date businessEndTime;
    /**
     * 成立时间
     */
    private Date establishTime;
    /**
     * 税务登记号
     */
    private String taxRegistrationNumber;
    /**
     * 联系人信息
     */
    private LinkerInfoReqParam linkerInfo;
    /**
     * 所属行业字典码
     */
    private String industryCode;
    /**
     * 所属行业
     */
    private String industryName;
    /**
     * 电话
     */
    private String phone;
    /**
     * 邮箱
     */
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
     * 本位币
     */
    private String currency;
    /**
     * 简介
     */
    private String note;
    /**
     * 主营业务
     */
    private String mainBusiness;
    /**
     * 是否工商登记 0:否，1:是
     */
    private Integer saicFlag;

    /**
     * 集团简称
     */
    private String orgShortName;
    /**
     * 详细地址
     */
    private BaseAddressDto addressDetail;


    private String custName;

    /**
     * 入住的业务单元id
     */
    private Long unitOrg;

    /**
     * 用户状态是否可以绑定组织
     *
     * @return <code>true</code> 可绑定组织；<code>false</code> 不可绑定组织；
     */
    public boolean canBindingOrg() {
        return Objects.equals(enableState, 1) && Objects.equals(frozenState, BooleanEnum.FALSE.getCode());
    }


    public String getCurrency() {
        if (StringUtils.isEmpty(currency)) {
            return "人民币";
        }
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNote() {
        if (StringUtils.isEmpty(note)) {
            return "";
        }
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMainBusiness() {
        return mainBusiness;
    }

    public String getOrgName() {
        if (StringUtils.isEmpty(orgName)) {
            return "";
        }
        return orgName;
    }

    public com.org.permission.common.org.dto.BaseAddressDto getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(BaseAddressDto addressDetail) {
        this.addressDetail = addressDetail;
    }
}
