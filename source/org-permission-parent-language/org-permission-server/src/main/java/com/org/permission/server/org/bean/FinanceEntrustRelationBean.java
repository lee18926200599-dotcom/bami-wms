package com.org.permission.server.org.bean;

import com.org.permission.server.org.enums.EntrustRangeEnum;
import com.org.permission.server.org.enums.EntrustTypeEnum;
import com.common.base.enums.BooleanEnum;
import com.common.base.enums.StateEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 财务业务委托关系数据实体
 */
@Data
public class FinanceEntrustRelationBean extends BaseEntrustRelationBean implements Serializable {
    private static final long serialVersionUID = 1L;

    // 忽略拷贝属性列表
    public static final String[] BEAN_COPY_IGNORE_FIELD_LIST = new String[]{"defaultFlag", "entrustRange", "entrustType", "state", "createTime"};

    /**
     * 业务组织
     */
    private Long bizOrgId;
    /**
     * 核算组织(应收 | 应付 组织)
     */
    private Long accountOrgId;
    /**
     * 结算组织(收 | 付款 组织)
     */
    private Long settleOrgId;

    public FinanceEntrustRelationBean() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof FinanceEntrustRelationBean)) {
            return false;
        }

        FinanceEntrustRelationBean target = ((FinanceEntrustRelationBean) obj);

        return getUniqueString().equals(target.getUniqueString());
    }

    @Override
    public int hashCode() {
        return getUniqueString().hashCode();
    }

    /**
     * 获取财务委托关系唯一标识
     *
     * @return 唯一标识
     */
    private String getUniqueString() {
        return bizOrgId + ":" + accountOrgId + ":" + settleOrgId + ":" + getState();
    }

    /**
     * 构建默认值的财务委托关系
     * <p>
     * 配合使用
     * {@link FinanceEntrustRelationBean#BEAN_COPY_IGNORE_FIELD_LIST}
     *
     * @return 具有默认值的财务委托关系
     */
    public static FinanceEntrustRelationBean buildeDefaultValue() {
        return new FinanceEntrustRelationBean(BooleanEnum.FALSE.getCode(), EntrustRangeEnum.INTER_GROUP, EntrustTypeEnum.FINANCE, StateEnum.CREATE);
    }

    private FinanceEntrustRelationBean(Integer defaultFlag, EntrustRangeEnum entrustRange, EntrustTypeEnum entrustType, StateEnum state) {
        super(defaultFlag, entrustRange.getIndex(), entrustType.getIndex(), state.getCode());
    }

}

