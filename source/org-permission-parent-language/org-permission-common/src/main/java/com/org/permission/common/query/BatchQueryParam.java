package com.org.permission.common.query;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 批量查询请求参数
 */
@ApiModel(description = "批量查询参数")
@Data
public class BatchQueryParam extends BaseQuery implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("用户ID（可空）（预留权限控制）")
	private Long userId;

	@ApiModelProperty("ID 集合（非空）")
	private List<Long> ids;

	@ApiModelProperty("需要组织类型（选填）（1平台；2集团；3业务单元；4部门），若给定orgTypes，将只返回指定类型的组织信息")
	private List<Integer> orgTypes;

	@ApiModelProperty("人员姓名")
	private String realname;

	@ApiModelProperty("组织名称")
	private String orgName;

    @ApiModelProperty("组织简称")
    private String orgShortName;

	@ApiModelProperty("状态:1未启用，2启用，3停用，4删除")
	private Integer state;

	@ApiModelProperty("上级业务单元id")
	private Long parentBuId;

	@ApiModelProperty("上级id")
	private Long parentId;

	@ApiModelProperty("集团id")
	private Long groupId;

	@ApiModelProperty("用户id集合")
	private List<Long> userIds;

	@ApiModelProperty("人员编码")
	private String staffCode;

	@ApiModelProperty("查询参数：人员编号/姓名")
	private String queryString;

	@ApiModelProperty("人员类别")
	private List<Integer> staffTypes;

    private List<String> businessType;

    @ApiModelProperty("业务单元id集合")
    private List<Long> unitOrgIds;

    @ApiModelProperty(notes = "创建时间-开始时间", required = false)
    private Date createdDateStart;

    @ApiModelProperty(notes = "创建时间-结束时间", required = false)
    private Date createdDateEnd;

    @ApiModelProperty("社会统一信用代码模糊查询")
    private String creditCode;

    @ApiModelProperty("组织编码集合")
    private List<String> codes;

    @ApiModelProperty("组织名称集合")
    private List<String> names;

    @ApiModelProperty("组织名称右模糊查询")
    private String orgNameRightLike;
}
