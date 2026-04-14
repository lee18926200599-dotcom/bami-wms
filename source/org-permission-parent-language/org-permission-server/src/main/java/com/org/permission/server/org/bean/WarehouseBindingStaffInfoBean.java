package com.org.permission.server.org.bean;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 仓库绑定人员信息
 */
@Data
public class WarehouseBindingStaffInfoBean extends StaffInfoBean {
	private static final long serialVersionUID = 1L;

	/**
	 * 关系表 ID
	 */
	private Integer relationId;
	/**
	 * 关联数据 ID
	 */
	private Long relId;
	/**
	 * 人员类别名
	 */
	private String staffTypeName;

	public WarehouseBindingStaffInfoBean() {
	}

	@Override
	public String getStaffTypeName() {
		final List<StaffDutyInfoBean> staffDuties = getStaffDuties();
		if (!CollectionUtils.isEmpty(staffDuties)) {
			StringBuilder staffTypeNameBuilder = new StringBuilder();
			staffDuties.forEach(staffDuty -> {
				staffTypeNameBuilder.append(",").append(staffDuty.getStaffTypeName());
			});
			return staffTypeNameBuilder.toString().substring(1);
		}
		return "";
	}
}
