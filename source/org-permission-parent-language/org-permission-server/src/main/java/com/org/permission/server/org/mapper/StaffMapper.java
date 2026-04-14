package com.org.permission.server.org.mapper;

import com.org.permission.common.org.dto.RegistStaffDto;
import com.org.permission.common.org.dto.StaffInfoDto;
import com.org.permission.common.org.dto.StaffWithDutyInfoDto;
import com.org.permission.common.org.param.*;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.server.org.bean.StaffBean;
import com.org.permission.server.org.bean.StaffInfoBean;
import com.org.permission.server.org.bean.StaffStateBean;
import com.org.permission.server.org.dto.param.QueryArchiveListParam;
import com.org.permission.server.org.vo.ArchiveInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 人员
 */
@Mapper
public interface StaffMapper {

    /**
     * 创建人员
     *
     * @param bean 请求参数
     */
    int createStaff(@Param(value = "bean") StaffBean bean);

    /**
     * 修改人员
     *
     * @param bean 请求参数
     */
    void modifyStaff(@Param(value = "bean") ModifyStaffInfoDto bean);

    /**
     * 查询人员基本状态信息
     *
     * @param staffId 人员 ID
     * @return 人员基本状态信息
     */
    StaffStateBean queryStaffStateByIdLock(@Param(value = "staffId") Long staffId);

    /**
     * 根据人员ID获取人员信息（锁）
     *
     * @param staffIds 人员ID集合
     * @return 人员信息集合
     */
    List<RegistStaffDto> queryStaffsByIdLock(@Param(value = "staffIds") List<Long> staffIds);

    /**
     * 伪删除人员
     *
     * @param staffId 人员ID
     * @param userId  用户ID
     * @return 删除数量
     */
    int deleteStaffById(@Param(value = "staffId") Long staffId, @Param(value = "userId") Long userId);

    /**
     * 根据人员id更新用户id
     *
     * @param successList
     */
    void updataUserId(@Param(value = "successList") List<RegistStaffDto> successList);

    /**
     * 启停人员
     *
     * @param param 请求参数
     * @return 更新数量
     */
    int modifyStaffState(@Param(value = "param") ModifyOperateParam param);

    void batchEnableStaff(@Param(value = "userId") Long userId, @Param(value = "staffIds") List<Long> staffIds, @Param(value = "state") Integer state);

    Integer deleteUserIdByStaffId(@Param(value = "staffId") Long staffId);

    Integer updateUserIdByStaffId(@Param(value = "staffId") Long staffId, @Param(value = "userId") Long userId);

    Integer updateStaffCodeById(@Param(value = "staffId") Long staffId, @Param(value = "staffCode") String staffCode);

    /**
     * 停用业务单元下所有启用人员
     *
     * @param buId   业务单元ID
     * @param userId 用户ID
     */
    void disableStaffsByBUId(@Param(value = "buId") Long buId, @Param(value = "userId") Long userId, @Param(value = "modifiedDate") Date modifiedDate);

    /**
     * 查询业务单元下所有人员绑定用户ID集合
     *
     * @param buId 业务单元ID
     * @return 人员ID集合
     */
    List<Long> queryStaffUserIdsByBUId(@Param(value = "buId") Long buId);

    /**
     * 统计部门的人员数量
     *
     * @param depId 部门ID
     * @return 人员数量
     */
    int countDepStaff(@Param(value = "depId") Long depId);

    /**
     * 根据部门id集合查询用户
     *
     * @param depIdlist
     * @return
     */
    List<Long> queryStaffByDepIds(@Param(value = "depIdlist") List<Long> depIdlist);

    /**
     * 根据集团id停用人员
     *
     * @param groupId
     */
    void disableStaffByGroupId(@Param(value = "groupId") Long groupId, @Param(value = "updateUser") Long updateUser, @Param("modifiedDate") Date modifiedDate);

    /**
     * 查询人员详细信息
     *
     * @param queryParam 查询参数
     * @return 人员详细信息（含人员任职及类别信息）
     */
    StaffInfoBean queryDetailInfoById(@Param(value = "queryParam") QueryByIdReqParam queryParam);

    /**
     * 查询人员及其任职信息
     *
     * @param staffId 人员ID
     * @return 人员及其任职信息
     */
    StaffInfoBean queryStaffWithDuty(@Param(value = "staffId") final Long staffId);

    /**
     * 人员列表查询请求参数(不分页)
     *
     * @param depIds     部门ID集合
     * @param queryParam 扩展参数
     * @return 人员基础信息集合
     */
    List<StaffInfoBean> queryStaffsMainByDepIds(@Param(value = "depIds") List<Long> depIds, @Param(value = "staffId") Long staffId, @Param(value = "queryParam") final QueryStaffsReqParam queryParam);

    /**
     * 根据集团id查询人员
     *
     * @param groupId
     * @param extendParam
     * @return
     */
    List<StaffInfoBean> queryStaffsByGroupId(@Param(value = "groupId") Long groupId, @Param(value = "staffId") Long staffId, @Param(value = "extendParam") final QueryStaffsReqParam extendParam);

    /**
     * 统计人员数量（主要依据组织 ID）
     *
     * @param queryParam 查询参数
     * @return 人员数量
     */
    int countStaffMainByOrgId(@Param(value = "queryParam") QueryStaffsReqParam queryParam);

    /**
     * 分页查询人员信息（主要依据组织 ID）
     *
     * @param queryParam 查询参数
     * @return 人员信息集合
     */
    List<StaffInfoBean> pageableQueryStaffMainByOrgId(@Param(value = "queryParam") QueryStaffsReqParam queryParam);

    /**
     * 批量查询人员信息
     *
     * @param reqParam   人员 ID
     * @param unitOrgIds
     * @return 人员信息集合
     */
    List<StaffInfoBean> batchQueryStaffInfo(@Param(value = "reqParam") BatchQueryParam reqParam, @Param("staffTypes") List<Integer> staffTypes, @Param("unitOrgIds") List<Long> unitOrgIds);

    List<StaffInfoDto> batchQueryStaffBasicInfo(@Param(value = "reqParam") StaffBasicParam reqParam);

    /**
     * 查询该部门人员信息
     *
     * @param depId 部门 ID
     * @return 部门人员信息集合
     */
    List<StaffInfoBean> queryStaffByDepId(@Param(value = "depId") Long depId);

    /**
     * 根据用户 ID 查询人员 ID
     *
     * @param userId 用户 ID
     * @return 若绑定人员，返回人员 ID，否则为空
     */
    Long queryStaffIdByUserId(@Param(value = "userId") Long userId);

    /**
     * 根据用户ID查询人员信息
     *
     * @param userId
     * @return
     */
    StaffInfoDto queryStaffInfoByUserId(@Param(value = "userId") Long userId);

    /**
     * 查询人员姓名
     *
     * @param staffId 人员ID
     * @return 人员姓名
     */
    String queryStaffName(@Param(value = "staffId") Long staffId);

    /**
     * 统计人员列表总数
     *
     * @param queryParam 查询请求参数
     * @return 总数
     */
    int countStaffList(@Param(value = "queryParam") final QueryStaffListReqParam queryParam);

    /**
     * 人员列表查询请求参数
     *
     * @param queryParam 查询请求参数
     * @return 人员基础信息集合
     */
    List<StaffInfoBean> queryStaffList(@Param(value = "queryParam") final QueryStaffListReqParam queryParam);


    /**
     * 人员列表查询请求参数
     *
     * @param queryParam 查询请求参数
     * @return 人员基础信息集合
     */
    List<Long> queryStaffIdList(@Param(value = "queryParam") final QueryStaffListReqParam queryParam);


    /**
     * 检查集团内身份证号是否存在
     *
     * @param queryParam
     * @return
     */
    List<StaffInfoBean> validateCardNoExsit(@Param(value = "queryParam") final QueryStaffListReqParam queryParam);

    /**
     * 统计人员列表总数
     *
     * @param queryParam
     * @return
     */
    Integer queryyStaffListCount(@Param(value = "queryParam") final QueryStaffListReqParam queryParam);

    /**
     * 根据部门ID,人员状态，关联用户，查询人员信息
     *
     * @param depIds 部门ID
     * @return 人员信息
     */
    List<StaffInfoBean> queryStaffsByDepIds(@Param(value = "depIds") List<Long> depIds, @Param(value = "queryParam") QueryAllStaffsInOrgReqParam reqParam);

    /**
     * 统计人员档案数量
     *
     * @param queryParam 查询请求参数
     * @return 总数量
     */
    Integer countStaffArchive(@Param(value = "queryParam") QueryArchiveListParam queryParam);

    /**
     * 查询人员档案
     *
     * @param queryParam 查询参数
     * @return 人员档案列表
     */
    List<ArchiveInfoVo> queryStaffArchive(@Param(value = "queryParam") QueryArchiveListParam queryParam);


    List<StaffInfoDto> queryStaffByPojo(@Param(value = "param") StaffWithDutyInfoDto param);

    List<StaffInfoDto> queryStaffForCrm(@Param(value = "queryParam") StaffForCrmParam queryParam);

    Integer queryStaffForCrmTotalCount(@Param(value = "queryParam") StaffForCrmParam queryParam);

    Long queryStaffUniqueByRealname(@Param(value = "realname") String realname, @Param(value = "groupId") Long groupId, @Param(value = "certificateNo") String certificateNo);

    Long queryStaffUniqueByStaffCode(@Param(value = "staffCode") String staffCode, @Param(value = "groupId") Long groupId);

    /**
     * 模糊查询人员信息
     */
    List<StaffInfoDto> findStaffListByCondition(HashMap<String, Object> map);

}

