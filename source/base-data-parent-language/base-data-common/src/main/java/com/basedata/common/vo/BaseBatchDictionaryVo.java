package com.basedata.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
public class BaseBatchDictionaryVo {

    @ApiModelProperty(value = "字典code")
    private String dictCode;

    @ApiModelProperty(value = "字典描述")
    private String dictDesc;

    @ApiModelProperty(value = "字典详情")
    private List<BaseBatchDictionaryDetailVo> detailVoList;

    @Data
    public static class BaseBatchDictionaryDetailVo{
        @ApiModelProperty(value = "itemCode")
        public String itemCode;

        @ApiModelProperty(value = "itemName")
        public String itemName;

        @ApiModelProperty(value = "子集")
        private List<BaseBatchDictionaryDetailVo> children;
    }

}
