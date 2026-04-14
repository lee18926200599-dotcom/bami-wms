package com.org.permission.server.org.service;


import com.org.permission.common.org.param.DepartmentReqParam;
import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.common.org.param.KeyOperateParam;
import com.org.permission.common.org.param.QueryByIdReqParam;
import com.org.permission.common.org.vo.DepWithStaffDetailVo;
import com.org.permission.server.org.bean.OrgTreeBean;

import java.util.List;

/**
 * 部门服务
 */
public interface DepartmentService {
    /**
     * 创建部门
     *
     * @param reqParam 创建部门请求参数
     */
    Long createDepartment(DepartmentReqParam reqParam);


    /**
     * 更新部门
     *
     * @param reqParam 更新部门参数
     */
    void updateDepartment(DepartmentReqParam reqParam);

    /**
     * 启|停 部门
     *
     * @param reqParam 启停操作请求参数
     */
    void enableDepartment(EnableOperateParam reqParam);

    /**
     * 停用部门
     *
     * @param buId 业务单元
     */
    void disableDepByBUId(Long userId, Long buId);

    /**
     * 删除部门
     *
     * @param reqParam 删除操作请求参数
     */
    void deleteDepartment(KeyOperateParam reqParam);

    /**
     * 根据业务单元删除部门
     *
     * @param buId   业务单元ID
     * @param userId 用户ID
     */
    void deleteDepartmentByBuId(Long buId, Long userId);

    /**
     * 查询部门及人员信息
     *
     * @param reqParam 查询请求参数
     * @return 部门及人员信息
     */
    DepWithStaffDetailVo queryDepDetail(QueryByIdReqParam reqParam);

    /**
     * 根据业务单元ID,查询该业务单元下所有部门
     *
     * @param reqParam 查询请求参数
     * @return 部门集合
     */
    List<OrgTreeBean> queryDepTreeByBUId(QueryByIdReqParam reqParam);

    /**
     * 根据部门id查询部门信息
     * 欧秀娟
     *
     * @param param
     * @return
     */
    DepWithStaffDetailVo queryDepInfoById(QueryByIdReqParam param);

    /**
     * 根据业务单元Id查询部门简要信息
     *
     * @param buId 业务单元Id
     * @return
     */
    List<DepWithStaffDetailVo> queryDepInfoByBuId(Long buId);
}
