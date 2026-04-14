package com.org.permission.server.org.enums;

import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;

/**
 * 组织列表查询过滤级别枚举
 */
public enum OrgListFilterLevelEnum {
    /**
     * 过滤级别
     * <li>1 </li>
     * <li>2 所有下级业务单元</li>
     */
    ALL_UP_ORG(1, "所有上级组织"), ALL_DOWN_BU(2, "所有下级业务单元"), GROUP_ALL_ORG(3, "集团下所有组织");


    private int filterLevel;
    private String remark;

    OrgListFilterLevelEnum(int filterLevel, String remark) {
        this.filterLevel = filterLevel;
        this.remark = remark;
    }

    public int getFilterLevel() {
        return filterLevel;
    }

    public String getRemark() {
        return remark;
    }

    public static final OrgListFilterLevelEnum getOrgListFilterLevelEnum(Integer filterLevel) {
        if (null == filterLevel) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, "过滤级别不存在");
        }
        for (OrgListFilterLevelEnum orgListFilterLevelEnum : OrgListFilterLevelEnum.values()) {
            if (orgListFilterLevelEnum.getFilterLevel() == filterLevel) {
                return orgListFilterLevelEnum;
            }
        }
        throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, "过滤级别不存在");
    }
}
