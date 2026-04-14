package com.org.client.feign;


import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.WarehouseBindingStaffInfoDto;
import com.org.permission.common.org.param.KeyOperateParam;
import com.common.util.message.RestMessage;
import com.org.permission.common.org.vo.StaffDetailInfoVo;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.common.util.Constant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 人员
 */
@FeignClient(value = Constant.ORG_SERVER_NAME)
public interface StaffFeign {
    /**
     * 新增人员
     *
     * @param reqParam 请求参数
     * @return <code>true</code>成功
     */
    @PostMapping(value = "/staff/addStaff")
    RestMessage<Boolean> addStaff(@RequestBody com.org.permission.common.org.param.AddStaffParam reqParam);

    /**
     * 开放平台新增人员
     *
     * @param reqParam 请求参数
     * @return <code>true</code>成功
     */
    @PostMapping(value = "/staff/create")
    RestMessage<Integer> create(@RequestBody com.org.permission.common.org.param.CreateStaffInfoParam reqParam);

    /**
     * 根据主键修改人员
     *
     * @param reqParam
     * @return
     */
    @PostMapping(value = "/staff/modify")
    RestMessage<Boolean> modify(@RequestBody com.org.permission.common.org.param.ModifyStaffInfoDto reqParam);

    /**
     * 根据主键修改人员状态（同时修改用户状态）
     *
     * @param reqParam
     * @return
     */
    @PostMapping(value = "/staff/modifyStateOp")
    RestMessage<Boolean> modifyStateOp(@RequestBody com.org.permission.common.org.param.ModifyOperateParam reqParam);

    /**
     * 根据主键删除人员（同时删除用户）
     *
     * @param reqParam
     * @return
     */
    @PostMapping(value = "/staff/deleteStaff")
    RestMessage<Boolean> deleteStaff(@RequestBody KeyOperateParam reqParam);

    /**
     * 根据人员批量生成用户
     *
     * @param reqParam
     * @return
     */
    @PostMapping(value = "/staff/generateUser")
    RestMessage<com.org.permission.common.org.dto.GeneratorStaffDto> generateUser(@RequestBody com.org.permission.common.org.param.GenerateUserParam reqParam);

    /**
     * 根据主键查询人员详细信息
     *
     * @param reqParam 请求参数
     * @return 人员详细信息
     */
    @PostMapping(value = "/staff/queryStaff")
    RestMessage<com.org.permission.common.org.dto.StaffWithDutyInfoDto> queryStaff(@RequestBody com.org.permission.common.org.param.QueryByIdReqParam reqParam);


    /**
     * 递归业务单元查询人员
     * <ul>
     * <li>若当前组织是部门，则只返回当前部门人员</li>
     * <li>若当前组织是业务单元，则返回当前业务单元下所有子业务单元及所有子部门的人员</li>
     * </ul>
     *
     * @param reqParam 请求参数
     * @return 人员信息
     */
    @PostMapping(value = "/staff/recurseQueryStaffs")
    RestMessage<PageInfo<com.org.permission.common.org.dto.StaffInfoDto>> recurseQueryStaffs(@RequestBody com.org.permission.common.org.param.QueryStaffsReqParam reqParam);

    /**
     * 分页查询人员列表
     * <ul>
     * <li>分页查询</li>
     * <li>若 组织 ID 非空，则查询当前组织下的所有人员</li>
     * </ul>
     *
     * @param reqParam 人员查询请求参数
     * @return 人员信息
     */
    @PostMapping(value = "/staff/queryStaffs")
    RestMessage<PageInfo<com.org.permission.common.org.dto.StaffInfoDto>> queryStaffs(@RequestBody com.org.permission.common.org.param.QueryStaffsReqParam reqParam);

    /**
     * 批量查询人员信息
     *
     * @param queryParam 请求参数
     * @return 人员信息集合
     */
    // 批量查询人员信息，底层没有过滤状态
    @PostMapping(value = "/staff/batchQueryStaffsInfo")
    RestMessage<List<com.org.permission.common.org.dto.StaffInfoDto>> batchQueryStaffsInfo(@RequestBody BatchQueryParam queryParam);


    /**
     * 根据部门 ID 查询人员
     *
     * @param depId 部门 ID
     * @return 人员信息git
     */
    @GetMapping(value = "/staff/queryDepStaffsByDepId")
    RestMessage<List<com.org.permission.common.org.dto.StaffInfoDto>> queryDepStaffsByDepId(@RequestParam(value = "depId") Long depId);

    /**
     * 根据用户 ID 查询人员 ID
     *
     * @param userId 用户 ID
     * @return 人员 ID (如果当前用户绑定了人员)
     */
    @GetMapping(value = "/staff/queryStaffIdByUserId")
    RestMessage<Long> queryStaffIdByUserId(@RequestParam(value = "userId") Long userId);

    /**
     * 根据用户 ID 查询人员信息
     *
     * @param userId 用户 ID
     * @return 人员信息
     */
    @GetMapping(value = "/staff/queryStaffInfoByUserId")
    RestMessage<com.org.permission.common.org.dto.StaffInfoDto> queryStaffInfoByUserId(@RequestParam(value = "userId") Long userId);

    /**
     * 查询绑定人员信息
     *
     * @param reqParam 请求参数
     * @return 人员信息集合
     */
    @PostMapping(value = "/staff/queryBindingStaff")
    RestMessage<PageInfo<com.org.permission.common.org.dto.WarehouseBindingStaffInfoDto>> queryBindingStaff(@RequestBody com.org.permission.common.org.param.QueryBindingStaffParam reqParam);

    /**
     * 判断用户是否含有当前人员类别
     *
     * @param userId 用户 ID
     * @param typeId 类别 ID
     * @return true 含有；false 没有
     */
    @GetMapping(value = "/staff/queryStaffTypeByUserId")
    RestMessage<Boolean> queryStaffTypeByUserId(@RequestParam(value = "userId") Long userId, @RequestParam(value = "typeId") Integer typeId);

    /**
     * 根据人员id删除用户id
     *
     * @param staffId
     * @return
     */
    @GetMapping(value = "/staff/deleteUserIdByStaffId")
    RestMessage<Integer> deleteUserIdByStaffId(@RequestParam(value = "staffId") Long staffId);

    /**
     * 根据人员id更新用户id
     *
     * @param staffId
     * @param userId
     * @return
     */
    @GetMapping(value = "/staff/updateUserIdByStaffId")
    RestMessage<Integer> updateUserIdByStaffId(@RequestParam(value = "staffId") Long staffId, @RequestParam(value = "userId") Long userId);

    /**
     * 人员查询
     *
     * @param staffWithDutyInfoDto
     * @return
     */
    @PostMapping(value = "/staff/queryStaffByPojo")
    RestMessage<List<com.org.permission.common.org.dto.StaffWithDutyInfoDto>> queryStaffByPojo(@RequestBody com.org.permission.common.org.dto.StaffWithDutyInfoDto staffWithDutyInfoDto);

    /**
     * 人员排除查询
     *
     * @param staffForCrmParam
     * @return
     */
    @PostMapping(value = "/staff/queryStaffForCrm")
    RestMessage<PageInfo<com.org.permission.common.org.dto.StaffInfoDto>> queryStaffForCrm(@RequestBody com.org.permission.common.org.param.StaffForCrmParam staffForCrmParam);


    @PostMapping(value = "/staff/queryStaffList")
    RestMessage<PageInfo<StaffDetailInfoVo>> queryStaffList(@RequestBody com.org.permission.common.org.param.QueryStaffListReqParam reqParam);

    /**
     * 人员基础信息查询
     *
     * @param queryParam
     * @return
     */
    @PostMapping(value = "/staff/batchQueryStaffBasicInfo")
    RestMessage<List<com.org.permission.common.org.dto.StaffInfoDto>> batchQueryStaffBasicInfo(@RequestBody com.org.permission.common.org.param.StaffBasicParam queryParam);

    /**
     * 查询人员简要信息
     **/
    @PostMapping(value = "/staff/getSimpleStaffList")
    RestMessage<PageInfo<com.org.permission.common.org.dto.DepartmentStaffDto>> getSimpleStaffList(@RequestBody com.org.permission.common.org.param.GetSimpleStaffListReq req);

    /**
     * 查询绑定人员简要信息
     *
     * @param reqParam
     * @return
     */
    @PostMapping(value = "/staff/queryBindingStaffBriefInfo")
    RestMessage<WarehouseBindingStaffInfoDto> queryBindingStaffBriefInfo(@RequestBody com.org.permission.common.org.param.QueryBindingStaffParam reqParam);
}
