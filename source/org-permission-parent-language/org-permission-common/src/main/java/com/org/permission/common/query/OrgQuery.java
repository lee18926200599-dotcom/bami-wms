package com.org.permission.common.query;

import lombok.Data;

import java.util.List;


@Data
public class OrgQuery {
    private List<Integer> parentBuIds;
    private String orgCode;
    private String orgName;
    private Integer parentId;
    private Integer state;
    private Integer orgType;
}
