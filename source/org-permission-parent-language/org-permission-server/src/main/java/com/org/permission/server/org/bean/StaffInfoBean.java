package com.org.permission.server.org.bean;


import com.org.permission.common.bean.BaseBean;
import com.org.permission.common.org.dto.BaseAddressDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 人员数据实体
 */
@Data
public class StaffInfoBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 注册来源
	 */
	private Integer registSource;
    /**
     * 注册来源Id
     */
    private String registSourceId;
	/**
	 * 所属部门ID
	 */
	private Long depId;
	/**
	 * 所属部门名
	 */
	private String depName;
	/**
	 * 所属业务单元ID
	 */
	private Long orgId;
	/**
	 * 所属业务单元名
	 */
	private String orgName;
	/**
	 * 所属集团ID
	 */
	private Long groupId;
	/**
	 * 所属集团ID
	 */
	private String groupName;
	/**
	 * 人员编码
	 */
	private String staffCode;
	/**
	 * 姓名
	 */
	private String realname;
	/**
	 * 用户 ID
	 */
	private Long userId;
	/**
	 * 人员类别ID
	 */
	private Integer staffTypeId;
	/**
	 * 人员岗位
	 */
	private String staffTypeName;
	/**
	 * 直属上级ID
	 */
	private Long directSupervisorId;
	/**
	 * 直属上级名
	 */
	private String directSupervisorName;
	/**
	 * 性别
	 */
	private Integer sex;
	/**
	 * 证件类型
	 */
	private Integer certificateType;
	/**
	 * 证件号码
	 */
	private String certificateNo;
	/**
	 * 生日
	 */
	private String birthday;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 邮件
	 */
	private String email;
	/**
	 * 用工性质
	 * 0 正式工
	 * 1 临时工
	 */
	private Integer employmentType;
	/**
	 * 详细地址
	 */
	private BaseAddressDto addressDetail;

	private List<StaffDutyInfoBean> staffDuties;
}
