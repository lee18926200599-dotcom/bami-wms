package com.org.client.feign;


import com.org.permission.common.org.param.KeyOperateParam;
import com.common.util.message.RestMessage;
import com.org.permission.common.org.dto.DepartmentDto;
import com.org.permission.common.org.dto.OrgInfoDto;
import com.org.permission.common.org.dto.OrgTreeDto;
import com.org.permission.common.org.vo.DepWithStaffDetailVo;
import com.org.permission.common.util.Constant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门对外
 */
@FeignClient(value = Constant.ORG_SERVER_NAME)
public interface DepartmentFeign {
    /**
     * 新增部门
     *
     * @param reqParam
     * @return
     */
    @PostMapping(value = "/dep/add")
    RestMessage<Integer> addDepartment(@RequestBody com.org.permission.common.org.param.DepartmentReqParam reqParam);

    /**
     * 根据主键更新部门
     *
     * @param reqParam
     * @return
     */
    @PostMapping(value = "/dep/update")
    RestMessage<Boolean> updateDepartment(@RequestBody com.org.permission.common.org.param.DepartmentReqParam reqParam);

    /**
     * 根据主键启停部门
     *
     * @param reqParam
     * @return
     */
    @PostMapping(value = "/dep/enableDepartment")
    RestMessage<Boolean> enableDepartment(@RequestBody com.org.permission.common.org.param.EnableOperateParam reqParam);

    /**
     * 根据主键删除部门
     *
     * @param reqParam
     * @return
     */
    @PostMapping(value = "/dep/deleteDepartment")
    RestMessage<Boolean> deleteDepartment(@RequestBody KeyOperateParam reqParam);

    /**
     * 根据业务单元查询部门
     *
     * @param reqParam 查询请求参数
     * @return 部门树
     */
    @PostMapping(value = "/dep/queryBUDepTree")
    RestMessage<List<OrgTreeDto>> queryBUDepTree(@RequestBody com.org.permission.common.org.param.QueryByIdReqParam reqParam);

    /**
     * 根据部门id获取所有的子部门
     *
     * @param depId
     * @return
     */
    @GetMapping(value = "/dep/queryChildrenDepsByDepId")
    RestMessage<List<OrgInfoDto>> queryChildrenDepsByDepId(@RequestParam(value = "depId") Long depId);

    /**
     * 根据部门id获取所有的父部门
     *
     * @param depId
     * @return
     */
    @GetMapping(value = "/dep/queryParentDepsByDepId")
    RestMessage<List<OrgInfoDto>> queryParentDepsByDepId(@RequestParam(value = "depId") Long depId);

    /**
     * 查询指定部门的所有上级部门(包括该部门)
     *
     * @param getAllParentDepartMentDto
     * @return
     */
    @PostMapping(value = "/dep/getAllParentDepartment")
    RestMessage<List<DepartmentDto>> getAllParentDepartment(@RequestBody com.org.permission.common.org.param.GetAllParentDepartMentDto getAllParentDepartMentDto);

    /**
     * 根据业务单元Id查询部门简要信息
     *
     * @param buId
     * @return
     */
    @GetMapping(value = "/dep/queryDepInfoByBuId")
    RestMessage<List<DepWithStaffDetailVo>> queryDepInfoByBuId(@RequestParam(value = "buId") Long buId);
}
