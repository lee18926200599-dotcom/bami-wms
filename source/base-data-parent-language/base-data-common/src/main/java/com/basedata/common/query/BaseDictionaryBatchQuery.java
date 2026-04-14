package com.basedata.common.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BaseDictionaryBatchQuery implements Serializable {
    private static final long serialVersionUID = 4003466289550505616L;
    @ApiModelProperty(value = "字典编码集合")
    private List<String> dictCodeList;
    @ApiModelProperty(value = "是否树形展示默认1是(1=是，0=否)")
    private Integer treeFlag = 1;

    private Long groupId = 0L;

    private Integer state;

}
