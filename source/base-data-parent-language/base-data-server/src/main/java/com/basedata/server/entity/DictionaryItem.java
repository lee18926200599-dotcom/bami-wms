package com.basedata.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据字典条目表
 */
@Data
@TableName("dictionary_item")
public class DictionaryItem implements Serializable {

	@ApiModelProperty(value = "主键ID")
	@TableId("id")
	@TableField(value = "id")
	private Long id;

	@ApiModelProperty(value = "条目编码")
	@TableField(value = "item_code")
	private String itemCode;

	@ApiModelProperty(value = "条目名称")
	@TableField(value = "item_name")
	private String itemName;

	@ApiModelProperty(value = "助记码")
	@TableField(value = "mnemonic_code")
	private String mnemonicCode;

	@ApiModelProperty(value = "状态")
	@TableField(value = "state")
	private Integer state;

	@ApiModelProperty(value = "备注")
	@TableField(value = "remark")
	private String remark;

	@ApiModelProperty(value = "数据字典id")
	@TableField(value = "dictionary_id")
	private Long dictionaryId;

	@ApiModelProperty(value = "数据字典编码")
	@TableField(value = "dictionary_code")
	private String dictionaryCode;

	@ApiModelProperty(value = "所属集团")
	@TableField(value = "group_id")
	private Long groupId;

	@ApiModelProperty(value = "排序号")
	@TableField(value = "sort_num")
	private Long sortNum;

	@ApiModelProperty(value = "上级id")
	@TableField(value = "parent_id")
	private Long parentId;

	@ApiModelProperty(value = "删除标识  0=未删除  1=删除")
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
