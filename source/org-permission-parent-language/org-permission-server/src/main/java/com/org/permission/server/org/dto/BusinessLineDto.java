package com.org.permission.server.org.dto;

import com.org.permission.common.dto.BaseDto;
import com.common.base.enums.StateEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * 业务线
 */
@Data
public class BusinessLineDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1l;
    @ApiModelProperty(value = "业务线编码")
    private String businessLineCode;

    @ApiModelProperty(value = "业务线名称")
    private String businessLineName;

    @ApiModelProperty(value = "状态")
    private Integer state;
    @ApiModelProperty(value = "状态")
    private String stateName;
    @ApiModelProperty(value = "备注")
    private String remark;

    public String getStateName(){
        if (Objects.isNull(this.state)){
            return "";
        }
        StateEnum stateEnum =  StateEnum.getEnum(this.state);
        if (Objects.isNull(stateEnum)){
            return "";
        }
        return stateEnum.getName();
    }
}
