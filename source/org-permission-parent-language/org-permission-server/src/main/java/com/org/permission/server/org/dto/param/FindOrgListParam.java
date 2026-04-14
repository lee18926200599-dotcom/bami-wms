package com.org.permission.server.org.dto.param;

import com.common.base.entity.BaseQuery;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiModel
@Data
public class FindOrgListParam extends BaseQuery implements Serializable {
    private List<Long> parentBuIds;
    private String orgCode;
    private String orgName;
    private Long parentId;
    private Integer state;

    private Integer orgType;
}
