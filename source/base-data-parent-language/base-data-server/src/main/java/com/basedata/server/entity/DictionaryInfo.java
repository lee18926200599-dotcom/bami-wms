package com.basedata.server.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据字典表
 */
@Data
@TableName("dictionary_info")
public class DictionaryInfo implements Serializable {

	@ApiModelProperty(value = "主键ID")
	@TableId("id")
	@TableField(value = "id")
	private Long id;

	@ApiModelProperty(value = "数据字典编码")
	@TableField(value = "dict_code")
	private String dictCode;

	@ApiModelProperty(value = "数据字典名称")
	@TableField(value = "dict_name")
	private String dictName;

	@ApiModelProperty(value = "数据字典属性  1=系统数据字典 2=集团数据字典")
	@TableField(value = "dict_attr")
	private Integer dictAttr;

	@ApiModelProperty(value = "是否分级")
	@TableField(value = "grade_flag")
	private Integer gradeFlag;

	@ApiModelProperty(value = "简称")
	@TableField(value = "short_name")
	private String shortName;

	@ApiModelProperty(value = "助记码")
	@TableField(value = "mnemonic_code")
	private String mnemonicCode;

	@ApiModelProperty(value = "集团ID")
	@TableField(value = "group_id")
	private Long groupId;

	@ApiModelProperty(value = "状态")
	@TableField(value = "state")
	private Integer state;

	@ApiModelProperty(value = "备注")
	@TableField(value = "remark")
	private String remark;

	@ApiModelProperty(value = "字典上级id")
	@TableField(value = "parent_id")
	private Long parentId;

	@ApiModelProperty(value = "删除标识 0=未删除 1=删除")
	@TableField(value = "deleted_flag")
	private Integer deletedFlag;

	@ApiModelProperty(value = "创建人id")
	@TableField(value = "created_by")
	private Long createdBy;

	@ApiModelProperty(value = "创建人")
	@TableField(value = "created_name")
	private String createdName;

	@ApiModelProperty(value = "创建日期")
	@TableField(value = "created_date")
	private Date createdDate;

	@ApiModelProperty(value = "修改人id")
	@TableField(value = "modified_by")
	private Long modifiedBy;

	@ApiModelProperty(value = "修改人")
	@TableField(value = "modified_name")
	private String modifiedName;

	@ApiModelProperty(value = "修改时间")
	@TableField(value = "modified_date")
	private Date modifiedDate;

}
