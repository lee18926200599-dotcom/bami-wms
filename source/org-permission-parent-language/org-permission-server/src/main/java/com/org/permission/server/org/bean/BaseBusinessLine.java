package com.org.permission.server.org.bean;


import com.org.permission.common.bean.BaseBean;
import lombok.Data;

/**
 *  业务线
 */
@Data
public class BaseBusinessLine extends BaseBean {

    /**
     * 业务线编码
     */
    private String businessLineCode;
    /**
     * 业务线名称
     */
    private String businessLineName;

    /**
     * 备注
     */
    private String remark;

}
