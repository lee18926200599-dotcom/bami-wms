package com.org.permission.server.org.dto.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiModel
@Data
public class BatchEnableOrgParam implements Serializable {
    private static final long serialVersionUID = -4753062366914577807L;
    private Long userId;
    private List<Long> orgIds;
    private Integer state;
    private Integer orgType;

}
