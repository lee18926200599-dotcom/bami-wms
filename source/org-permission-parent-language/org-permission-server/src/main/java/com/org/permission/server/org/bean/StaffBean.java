package com.org.permission.server.org.bean;

import com.org.permission.common.bean.BaseBean;
import com.org.permission.common.org.dto.BaseAddressDto;
import com.org.permission.server.org.enums.EmploymentTypeEnum;
import com.org.permission.server.org.enums.RegistSourceEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 人员数据实体
 */
@Data
public class StaffBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 注册来源{@link RegistSourceEnum}
	 */
	private Integer registSource;

    /**
     * 注册来源Id
     */
    private String registSourceId;

	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 所属部门ID
	 */
	private Long depId;
	/**
	 * 所属业务单元ID
	 */
	private Long orgId;
	/**
	 * 所属业务单元名字
	 */
	private String buName;
	/**
	 * 所属集团ID
	 */
	private Long groupId;
	/**
	 * 人员编码
	 */
	private String staffCode;
	/**
	 * 姓名
	 */
	private String realname;
	/**
	 * 直属上级
	 */
	private Long directSupervisor;
	/**
	 * 性别
	 * false 男
	 * true 女
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
	 * {@link EmploymentTypeEnum}
	 */
	private Integer employmentType;
	/**
	 * 人员状态
	 * 1 未启用
	 * 2 启用
	 * 3 停用
	 * 4 删除
	 */
	private Integer state;

	/**
	 * 详细地址
	 */
	private BaseAddressDto addressDetail;

	private String bizCode;

	private Long relId;

	private String typeName;
}
