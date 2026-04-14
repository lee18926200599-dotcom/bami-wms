package com.org.permission.server.org.bean;


import com.org.permission.common.bean.BaseBean;
import lombok.Data;

import java.util.Date;

/**
 *  组织业务线关系
 */
@Data
public class BaseBusinessLineOrg extends BaseBean {
    //业务线ID
    private Long lineId;
    //集团ID
    private Long groupId;
    //业务单元ID
    private Long orgId;
    //删除标识
    private Integer deletedFlag;
    //删除时间
    private Date deletedTime;
}
