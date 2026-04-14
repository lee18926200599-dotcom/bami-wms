package com.org.permission.server.org.bean;


import com.org.permission.common.bean.BaseBean;
import com.org.permission.common.org.dto.BaseAddressDto;
import com.org.permission.server.org.enums.OrgTypeEnum;
import com.org.permission.server.org.enums.RegistSourceEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * 新增部门请求参数
 */
@Data
public class DepartmentBean extends BaseBean implements Serializable {
    private static final long serialVersionUID = 1;
    /**
     * 组织类型
     */
    private Integer orgType = OrgTypeEnum.DEPARTMENT.getIndex();

    /**
     * 部门编码
     */
    private String depCode;
    /**
     * 部门名称
     */
    private String depName;
    /**
     * 集团ID
     */
    private Long groupId;
    /**
     * 上级部门
     */
    private Long parentId;
    /**
     * 上级业务单元
     */
    private Long parentBUId;
    /**
     * 成立时间
     */
    private Date establishTime;
    /**
     * 电话
     */
    private String phone;
    /**
     * 简介
     */
    private String remark;

    /**
     * true是根部门，否不是根部门
     */
    private Integer mainOrgFlag;

    /**
     * 部门负责人
     */
    private Long depDutyStaff;

    /**
     * 注册来源{@link RegistSourceEnum}
     */
    private Integer registSource = RegistSourceEnum.BOSS.getValue();

    /**
     * 注册来源Id
     */
    private String registSourceId;

    /**
     * 详细地址
     */
    private BaseAddressDto addressDetail;

}
