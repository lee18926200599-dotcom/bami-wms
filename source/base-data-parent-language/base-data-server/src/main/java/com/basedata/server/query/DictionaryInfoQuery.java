package com.basedata.server.query;


import com.basedata.common.vo.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DictionaryInfoQuery extends BaseQuery implements Serializable {

	@ApiModelProperty(value = "主键ID")
	private Long id;

	@ApiModelProperty(value = "数据字典编码")
	private String dictCode;

	@ApiModelProperty(value = "数据字典名称")
	private String dictName;

	@ApiModelProperty(value = "数据字典属性  1=系统数据字典 2=集团数据字典")
	private Integer dictAttr;

	@ApiModelProperty(value = "是否分级")
	private Integer gradeFlag;

	@ApiModelProperty(value = "简称")
	private String shortName;

	@ApiModelProperty(value = "助记码")
	private String mnemonicCode;

	@ApiModelProperty(value = "集团ID")
	private Long groupId;

	@ApiModelProperty(value = "状态")
	private Integer state;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "字典上级id")
	private Long parentId;

	@ApiModelProperty(value = "删除标识 0=未删除 1=删除")
	private Integer deletedFlag;

	@ApiModelProperty(value = "创建人id")
	private Long createdBy;

	@ApiModelProperty(value = "创建人")
	private String createdName;

	@ApiModelProperty(value = "创建日期")
	private Date createdDate;

	@ApiModelProperty(value = "修改人id")
	private Long modifiedBy;

	@ApiModelProperty(value = "修改人")
	private String modifiedName;

	@ApiModelProperty(value = "修改时间")
	private Date modifiedDate;

}
