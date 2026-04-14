package com.org.permission.server.org.vo;

import com.org.permission.common.org.vo.BaseInfoVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 人员类别实体数据模型
 */
@ApiModel(description = "人员类别展示数据")
@Data
public class StaffTypeInfoVo extends BaseInfoVo implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("类别级别（1 全局;2 集团）")
	private Integer typeLevel;

	@ApiModelProperty("所属组织ID")
	private Long belongOrgId;

	@ApiModelProperty("所属组织名")
	private String belongOrgName;

	@ApiModelProperty("类别编码")
	private String typeCode;

	@ApiModelProperty("类别名称")
	private String typeName;

	@ApiModelProperty("类别编码")
	private String bizCode;

	@ApiModelProperty("上级类别")
	private Long parentId;

	@ApiModelProperty("上级类别名")
	private String parentName;

	@ApiModelProperty(value = "状态(0创建;1启用;2停用;")
	private Integer state;


	@ApiModelProperty("简介")
	private String remark;

}
