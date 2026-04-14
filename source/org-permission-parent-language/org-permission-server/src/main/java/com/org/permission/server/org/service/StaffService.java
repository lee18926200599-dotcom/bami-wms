package com.org.permission.server.org.service;

import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.DepartmentStaffDto;
import com.org.permission.common.org.dto.GeneratorStaffDto;
import com.org.permission.common.org.dto.StaffInfoDto;
import com.org.permission.common.org.dto.StaffWithDutyInfoDto;
import com.org.permission.common.org.param.*;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.StaffInfoBean;
import com.org.permission.server.org.bean.WarehouseBindingStaffInfoBean;
import com.org.permission.server.org.dto.param.BatchOpParam;
import com.org.permission.server.org.dto.param.BindingStaffParam;
import com.org.permission.server.org.dto.param.QueryArchiveListParam;
import com.org.permission.server.org.vo.ArchiveInfoVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 人员服务
 */
public interface StaffService {

    /**
     * 创建人员
     * <p>
     * 拆分人员任职信息的类别 ID，组装多个任职信息
     *
     * @param reqParam 请求参数
     */
    Long createStaff(final CreateStaffInfoParam reqParam) throws OrgException;

    /**
     * 查询人员任职信息明细（含人员任职及类别信息）
     * <p>
     * 人员任职信息的封装，可在服务层实现，后期优化到控制层
     *
     * @param reqParam 请求参数
     * @return 人员详细
     */
    StaffInfoBean queryDetailInfoById(final QueryByIdReqParam reqParam);

    String queryStaffName(Long staffId);

    /**
     * 修改人员类别
     * <p>
     * 删除该人员的原任职信息，并新增所有任职信息（任职信息的 ID 对外不公开）
     *
     * @param reqParam 请求参数
     */
    void modifyStaff(final ModifyStaffInfoDto reqParam);

    /**
     * 启停操作
     *
     * @param reqParam 请求参数
     */
    void modifyStateOp(ModifyOperateParam reqParam);

    void batchEnableStaff(Long userId, List<Long> staffIds, Integer state);

    /**
     * 人员生成用户
     *
     * @param reqParam 请求参数
     * @return 人员编码及其对应失败信息
     */
    GeneratorStaffDto generateUser(GenerateUserParam reqParam);

    /**
     * 删除人员
     *
     * @param reqParam 删除操作请求参数
     * @return <code>true</code> 操作成功，<code>false</code> 重复操作
     */
    void deleteStaff(KeyOperateParam reqParam);

    /**
     * 绑定人员
     *
     * @param reqParam 请求参数
     */
    void bindingStaff(final BindingStaffParam reqParam);

    /**
     * 解绑人员
     *
     * @param reqParam 请求参数
     */
    void unbindingStaff(final BatchOpParam reqParam);

    Boolean validateCardNoExsit(QueryStaffListReqParam param);

    /**
     * 查询人员列表
     *
     * @param reqParam 请求参数
     * @return 分页查询结果
     */
    PageInfo<StaffInfoBean> queryStaffList(QueryStaffListReqParam reqParam);

    /**
     * 查询人员简要信息
     **/
    PageInfo<DepartmentStaffDto> getSimpleStaffList(GetSimpleStaffListReq getStaffSimpleListReq);

    /**
     * 查询绑定人员基础信息
     *
     * @return 人员基础信息集合
     */
    PageInfo<WarehouseBindingStaffInfoBean> queryBindingStaff(final QueryBindingStaffParam queryParam);

    /**
     * 查询未绑定人员基础信息
     *
     * @param queryParam
     * @return
     */
    PageInfo<WarehouseBindingStaffInfoBean> queryNotBindingStaff(final QueryBindingStaffParam queryParam);

    /**
     * 查询人员及其任职信息
     *
     * @param reqParam 请求参数
     * @return 人员及其任职信息
     */
    StaffInfoBean queryStaffWithDuty(final QueryByIdReqParam reqParam);

    /**
     * 递归查询人员信息
     *
     * @param reqParam 查询参数
     * @return 人员信息
     */
    PageInfo<StaffInfoBean> recurseQueryStaffs(QueryStaffsReqParam reqParam);

    /**
     * 分页查询人员信息
     *
     * @param reqParam 请求参数
     * @return 人员信息分页结果
     */
    PageInfo<StaffInfoBean> queryCurrentOrgStaffs(final QueryStaffsReqParam reqParam);

    /**
     * 批量查询人员信息
     *
     * @param reqParam 请求参数
     * @return 人员信息集合
     */
    List<StaffInfoBean> batchQueryStaffInfo(BatchQueryParam reqParam);

    /**
     * @param reqParam
     * @return
     */
    List<StaffInfoDto> batchQueryStaffBasicInfo(StaffBasicParam reqParam);

    /**
     * 查询该部门人员信息
     *
     * @param depId 部门 ID
     * @return 部门人员信息集合
     */
    List<StaffInfoBean> queryStaffByDepId(Long depId);

    /**
     * 判断用户是否含有当前人员类别
     *
     * @param userId 用户 ID
     * @param typeId 人员类别 ID
     * @return true 含有；false 没有
     */
    Boolean judgeUserHasStaffType(Long userId, Integer typeId);

    /**
     * 根据用户 ID 查询人员 ID
     *
     * @param userId 用户 ID
     * @return 若用户绑定人员，返回人员 ID，否则为空
     */
    Long queryStaffIdByUserId(Long userId);

    /**
     * 根据用户ID查询人员信息
     * @param userId
     * @return
     */
    StaffInfoDto queryStaffInfoByUserId(Long userId);

    /**
     * 根据人员id删除用户id
     *
     * @param staffId
     * @return
     */
    Integer deleteUserIdByStaffId(Long staffId);

    /**
     * 根据人员id更新用户id
     *
     * @param staffId
     * @return
     */
    Integer updateUserIdByStaffId(Long staffId, Long userId);

    /**
     * 根据组织ID，查询当前组织下的所有人员
     *
     * @param reqParam 查询请求参数
     * @return 人员信息集合
     */
    List<StaffInfoBean> queryStaffsByOrgId(QueryAllStaffsInOrgReqParam reqParam);

    /**
     * 批量停用人员
     *
     * @param buId   业务单元ID
     * @param userId 用户ID
     */
    void disableStaffByBUId(Long buId, Long userId);

    /**
     * 查询人员档案
     *
     * @param reqParam
     * @return
     */
    PageInfo<ArchiveInfoVo> queryArchiveList(QueryArchiveListParam reqParam);

    /**
     * 根据实体查询（可传入组织id）
     *
     * @param staffWithDutyInfoDto
     * @return
     */
    List<StaffWithDutyInfoDto> queryStaffByPojo(StaffWithDutyInfoDto staffWithDutyInfoDto);

    /**
     * 客商排除查询
     *
     * @param staffForCrmParam
     * @return
     */
    PageInfo<StaffInfoDto> queryStaffForCrm(StaffForCrmParam staffForCrmParam);

    Map<Long, String> querySuperVisor(Long userId, Long staffId, Long groupId);


    /**
     * 模糊查询人员信息
     */
    List<StaffInfoDto> findStaffListByCondition(HashMap<String, Object> map);

    WarehouseBindingStaffInfoBean queryBindingStaffBriefInfo(QueryBindingStaffParam reqParam);

}
