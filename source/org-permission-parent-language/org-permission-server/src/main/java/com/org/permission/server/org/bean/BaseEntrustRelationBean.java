package com.org.permission.server.org.bean;


import com.org.permission.common.bean.BaseBean;
import com.common.base.enums.BooleanEnum;
import com.common.base.enums.StateEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * 业务委托关系数据实体
 */
@Data
public abstract class BaseEntrustRelationBean extends BaseBean implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 是否默认
     */
    private Integer defaultFlag = 0;
    private Integer deletedFlag;
    /**
     * 集团ID
     */
    private Long groupId;
    /**
     * 委托范围
     * 1 集团间；
     * 2 集团内
     */
    private Integer entrustRange;
    /**
     * 委托关系
     * 1 采销业务委托关系；
     * 2 采购业务委托关系；
     * 3 销售业务委托关系；
     * 4 仓储业务委托关系；
     * 5 物流业务委托关系；
     * 6 财务委托关系；
     */
    private Integer entrustType;
    /**
     * 委托关系来源
     * 0 级联
     * 1 手工
     * 2 合同
     * 3 调拨单
     * 4 业务单元产生
     */
    private Integer entrustSource;
    /**
     * 委托关系来源ID
     */
    private Long entrustSourceId;
    /**
     * 备注
     */
    private String remark;

    /**
     * 默认委托关系Id(用于更新时使用)
     */
    private Long defaultEntrustId;

    /**
     * 物流职能网点类型：0业务网点，1职能网点
     */
    private Integer logisticFunctionType = 0;

    public BaseEntrustRelationBean() {

    }

    protected BaseEntrustRelationBean(Integer defaultFlag, Integer entrustRange, Integer entrustType, Integer state) {
        super(state, new Date());
        this.defaultFlag = defaultFlag;
        this.entrustRange = entrustRange;
        this.entrustType = entrustType;
    }

    /**
     * 是否为更新操作
     *
     * @return <code>true</code>是更新操作；<code>false</code>是新增操作;
     */
    public boolean updateOp() {
        return getId() != null;
    }

    /**
     * 销售业务委托关系是否需要验证
     *
     * @return <code>true</code>需要;<code> false</code>不需;
     */
    public boolean needVerify() {
        return (defaultFlag != null && BooleanEnum.TRUE.getCode().equals(defaultFlag)) || StateEnum.ENABLE.getCode().equals(getState());
    }

}

