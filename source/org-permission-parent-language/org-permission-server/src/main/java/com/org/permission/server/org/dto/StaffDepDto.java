package com.org.permission.server.org.dto;


import com.org.permission.server.org.bean.StaffBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel
@Data
public class StaffDepDto extends StaffBean implements Serializable{
    private static final long serialVersionUID = 1L;
    /**
     * 部门名字
     */
    @ApiModelProperty("部门名字")
    private String depName;
}
