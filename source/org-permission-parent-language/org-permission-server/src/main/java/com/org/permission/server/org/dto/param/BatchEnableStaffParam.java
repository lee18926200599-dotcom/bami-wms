package com.org.permission.server.org.dto.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiModel
@Data
public class BatchEnableStaffParam implements Serializable {
    private static final long serialVersionUID = -2672733542483026578L;
    private Long userId;
    private List<Long> staffIds;
    private Integer state;

}
