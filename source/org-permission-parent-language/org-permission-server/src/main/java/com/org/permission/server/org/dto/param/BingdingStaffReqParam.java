package com.org.permission.server.org.dto.param;

import com.org.permission.common.dto.BaseDto;
import com.org.permission.common.org.dto.StaffInfoDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 绑定人员请求参数
 */
@Data
public class BingdingStaffReqParam extends BaseDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID（选填）
	 */
	private Long userId;
	/**
	 * 人员类别业务编码（初始化人员配置）（必填）
	 */
	private String bizCode;
	/**
	 * 绑定关系ID（必填）
	 */
	private Long bingdingId;
	/**
	 * 人员ID集合（必填）
	 */
	private List<StaffInfoDto> staffs;

	/**
	 * 解绑数据ID
	 */
	private List<Integer> ids;
}
