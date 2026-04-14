package com.org.permission.server.org.service;


import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.common.org.param.KeyOperateParam;
import com.org.permission.common.org.param.QueryByIdReqParam;
import com.org.permission.common.org.param.QueryStaffTypeTreeReqParam;
import com.org.permission.server.org.bean.StaffTypeInfoBean;
import com.org.permission.server.org.bean.StaffTypeTreeBean;
import com.org.permission.server.org.dto.param.StaffTypeParam;

import java.util.List;

/**
 * 人员类别服务
 */
public interface StaffTypeService {
    /**
     * 创建人员类别
     *
     * @param reqParam 创建人员类别请求参数
     */
    void createStaffType(StaffTypeParam reqParam);

    /**
     * 查询人员类别集合（可用状态）
     *
     * @param reqParam 人员类别树查询请求参数
     * @return 人员类别集合
     */
    List<StaffTypeTreeBean> queryStaffTypeTree(QueryStaffTypeTreeReqParam reqParam);

    /**
     * 更新人员类别
     *
     * @param reqParam 更新人员类别请求参数
     */
    void updateStaffType(StaffTypeParam reqParam);

    /**
     * 启用人员类别
     *
     * @param reqParam 启用操作请求参数
     */
    void enableStaffType(EnableOperateParam reqParam);

    /**
     * 删除人员类别
     *
     * @param reqParam 删除操作请求参数
     */
    void deleteStaffType(KeyOperateParam reqParam);

    /**
     * 查询人员类别详细信息
     *
     * @param reqParam 查询请求参数
     */
    StaffTypeInfoBean queryStaffType(QueryByIdReqParam reqParam);

    /**
     * 查询直接下级
     *
     * @param reqParam
     * @return
     */
    List<StaffTypeInfoBean> queryChildren(QueryByIdReqParam reqParam);

    Integer queryStaffTypeIdByTypeName(String typeName);

}
