package com.org.permission.server.org.mapper;

import com.org.permission.common.org.param.DepartmentReqParam;
import com.org.permission.common.org.param.QueryByIdReqParam;
import com.org.permission.common.org.vo.DepWithStaffDetailVo;
import com.org.permission.server.org.bean.DepartmentBean;
import com.org.permission.server.org.bean.DepartmentStateBean;
import com.org.permission.server.org.bean.OrgTreeBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门 写 mapper
 */
@Mapper
public interface DepartmentMapper {

    /**
     * 新增部门
     *
     * @param bean 部门数据实体
     * @return 插入数量
     */
    int addDepartment(@Param(value = "bean") DepartmentBean bean);

    /**
     * 更新部门
     *
     * @param bean 更新部门参数
     */
    void updateDepartment(@Param(value = "bean") DepartmentReqParam bean);

    /**
     * 根据BU ID 查询，部门的所有名字
     *
     * @param buId 业务单元ID
     * @return 部门名称集合
     */
    List<String> queryDepNamesByBUIdLock(@Param(value = "buId") Long buId);

    /**
     * 根据集团ID，查询当前集团下的最大部门编码
     *
     * @param groupId 集团ID
     * @return 部门编码
     */
    String queryDepCodeByGroupIdLock(@Param(value = "groupId") Integer groupId);

    /**
     * 根据部门ID，查询当前部门相关状态信息
     *
     * @param depId 部门ID
     * @return 部门状态信息
     */
    DepartmentStateBean queryDepStateByIdLock(@Param(value = "depId") Long depId);

    /**
     * 根据业务单元ID，查询当前部门相关状态信息
     *
     * @param buId 业务单元ID
     * @return 部门状态信息集合
     */
    List<DepartmentStateBean> queryDepStateByBuIdLock(@Param(value = "buId") Long buId);

    /**
     * 查询业务单元下启用的部门
     *
     * @param buId 业务单元ID
     * @return 部门信息
     */
    List<Long> queryEnableDepByBuIdLock(@Param(value = "buId") Long buId);

    /**
     * 根据业务单元ID 伪删除部门
     *
     * @param buId   业务单元ID
     * @param userId 用户ID
     */
    void deleteDepByBuId(@Param("buId") Long buId, @Param("userId") Long userId);

    /**
     * 根据主键查询部门及其人员信息
     *
     * @param depId 部门ID
     * @return 部门信息
     */
    DepWithStaffDetailVo queryDepWithStaffInfoById(@Param(value = "depId") Long depId);

    /**
     * 根据业务单元ID，查询该业务单元下的所有部门
     *
     * @param buId 业务单元ID
     * @return 部门ID
     */
    List<Long> queryOrgContainDepsByBUId(@Param(value = "buId") Long buId);

    /**
     * 根据业务单元ID集合，查询该业务单元下的部门
     *
     * @param buIds 业务单元ID
     * @return 部门ID
     */
    List<Long> queryDirectOrgContainDepsByBUIds(@Param(value = "buIds") List<Long> buIds);

    /**
     * 根据集团ID，查询根业务单元下的部门
     *
     * @param groupId 业务单元ID
     * @return 部门ID
     */
    List<Long> queryDirectOrgContainDepsByGroupId(@Param(value = "groupId") Long groupId);

    /**
     * 根据业务单元ID,查询该业务单元下所有部门
     *
     * @param
     * @return 部门集合
     */
    List<OrgTreeBean> queryDepTreeByBUId(@Param(value = "reqParam") QueryByIdReqParam reqParam);

    /**
     * 根据业务单元id查询业务单元简要信息
     *
     * @param param
     * @return
     */
    DepWithStaffDetailVo queryDepInfoById(@Param(value = "param") QueryByIdReqParam param);

    List<Integer> queryChildDepByDepId(Integer depId);

    List<DepWithStaffDetailVo> queryDepNameByBuid(@Param(value = "buId") Long buId, @Param(value = "orgName") String orgName);

    /**
     * 根据业务单元Id查询部门简要信息
     *
     * @param buId
     * @return
     */
    List<DepWithStaffDetailVo> queryDepInfoByBuId(@Param(value = "buId") Long buId);


    Long queryIdByDepCode(@Param(value = "depCode") String depCode, @Param(value = "groupId") Long groupId);

}

