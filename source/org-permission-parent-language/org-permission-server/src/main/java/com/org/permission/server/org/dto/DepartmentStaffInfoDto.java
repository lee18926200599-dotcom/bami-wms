package com.org.permission.server.org.dto;


import com.org.permission.common.org.dto.BaseAddressDto;
import com.org.permission.common.org.vo.BaseInfoVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel
@Data
public class DepartmentStaffInfoDto extends BaseInfoVo implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("部门id")
	private Long depId;

	@ApiModelProperty("业务单元id")
	private Long buId;

	@ApiModelProperty("人员编码")
	private String staffCode;

	@ApiModelProperty("人员姓名")
	private String realname;

	@ApiModelProperty("直属上级")
	private Long directSupervisor;

	@ApiModelProperty("性别（0男；1女）")
	private Integer sex;

	@ApiModelProperty("证件编号")
	private String certificateNo;

	@ApiModelProperty("证件类型")
	private String certificateType;

	@ApiModelProperty("生日")
	private Date birthDay;

	@ApiModelProperty("电话")
	private String phone;

	@ApiModelProperty("邮箱")
	private String email;

	@ApiModelProperty("用工性质(1临时工;2正式工)")
	private Boolean employmentType;

	@ApiModelProperty("用户id")
	private Long userId;

	@ApiModelProperty("集团id")
	private Long groupId;

	@ApiModelProperty("业务类型编码")
	private String bizCode;

	@ApiModelProperty("状态（1未启用；2启用；3停用；4删除）")
	private Integer state;

	@ApiModelProperty("所属业务单元名字")
	private String buName;

	@ApiModelProperty("绑定ID")
	private Integer relId;

	@ApiModelProperty("绑定数据ID")
	private Long bindingId;

	@ApiModelProperty("仓库名")
	private String warehouseName;

	@ApiModelProperty("人员类别名")
	private String typeName;

	@ApiModelProperty(" 详细地址")
	private BaseAddressDto addressDetail;
}
