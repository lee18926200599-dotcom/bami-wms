package com.org.permission.server.org.controller;

import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.google.common.collect.Lists;
import com.org.permission.common.org.dto.DepartmentDto;
import com.org.permission.common.org.dto.OrgInfoDto;
import com.org.permission.common.org.dto.OrgTreeDto;
import com.org.permission.common.org.param.*;
import com.org.permission.common.org.vo.DepWithStaffDetailVo;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.OrgTreeBean;
import com.org.permission.server.org.builder.DepTreeBuilder;
import com.org.permission.server.org.service.DepartmentService;
import com.org.permission.server.org.service.OrganizationService;
import com.org.permission.server.org.validator.*;
import com.org.permission.server.utils.NumericUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 部门管理控制器
 */
@RestController
@Api(tags = "0部门接口文档")
@RequestMapping( "/dep")
public class DepartmentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @Resource
    private AddDepartmentReqParamValidator addDepartmentReqParamValidator;

    @Resource
    private UpdateDepParamValidator updateDepParamValidator;

    @Resource
    private EnableOperateReqParamValidator enableOperateReqParamValidator;

    @Resource
    private DeleteOperateReqParamValidator deleteOperateReqParamValidator;

    @Resource
    private QueryByIdReqParamValidator queryByIdReqParamValidator;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private DepTreeBuilder depTreeBuilder;

    @Resource
    private OrganizationService organizationService;

    @ApiOperation(value = "新增部门", httpMethod = "POST")
    @PostMapping(value = "/add")
    public RestMessage<Boolean> addDepartment(@RequestBody DepartmentReqParam reqParam) {
        LOGGER.info("add department request param :{}.", reqParam);
        try {
            addDepartmentReqParamValidator.validate(reqParam);

            departmentService.createDepartment(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("add department failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("add department error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "更新部门", httpMethod = "POST")
    @PostMapping(value = "/update")
    public RestMessage<Boolean> updateDepartment(@RequestBody DepartmentReqParam reqParam) {
        LOGGER.info("update department request param :{}.", reqParam);
        try {
            updateDepParamValidator.validate(reqParam);
            departmentService.updateDepartment(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("update department failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("update department error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "启停部门", httpMethod = "POST")
    @PostMapping(value = "/enableDepartment")
    public RestMessage<Boolean> enableDepartment(@RequestBody EnableOperateParam reqParam) {
        LOGGER.info("enable department request, param : {}.", reqParam);
        try {
            enableOperateReqParamValidator.validate(reqParam);
            departmentService.enableDepartment(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("enable department failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("enable department error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "根据部门id查询所有的子部门", httpMethod = "GET")
    @GetMapping(value = "/queryChildrenDepsByDepId")
    public RestMessage<List<OrgInfoDto>> queryChildrenDepsByDepId(@RequestParam(value = "depId") Long depId) {
        LOGGER.info("通过部门id查询所有的子部门{}", depId);
        List<OrgInfoDto> dtos = recursiveChildrenDepsByDepId(depId);
        return RestMessage.doSuccess(dtos);
    }

    @ApiOperation(value = "根据部门id查询所有的父部门", httpMethod = "GET")
    @GetMapping(value = "/queryParentDepsByDepId")
    public RestMessage<List<OrgInfoDto>> queryParentDepsByDepId(@RequestParam(value = "depId") Long depId) {
        LOGGER.info("通过部门id查询所有的父部门{}", depId);
        List<Long> allParentIds = recursiveParentDepsByDepId(depId);
        List<OrgInfoDto> allDeps = new ArrayList<>();
        if (allParentIds.size() > 0) {
            BatchQueryParam param = new BatchQueryParam();
            param.setIds(allParentIds);
            param.setState(2);
            allDeps = organizationService.batchQueryOrgInfoNofuc(param);
        }
        return RestMessage.doSuccess(allDeps);
    }

    /**
     * 删除部门
     *
     * @param reqParam 删除操作请求参数
     * @return <code>true</code> 操作成功，<code>false</code> 重复操作
     */
    @ApiOperation(value = "删除部门", httpMethod = "POST")
    @PostMapping(value = "/deleteDepartment")
    public RestMessage<Boolean> deleteDepartment(@RequestBody KeyOperateParam reqParam) {
        LOGGER.info("delete department request, param : {}.", reqParam);
        try {
            deleteOperateReqParamValidator.validate(reqParam);
            departmentService.deleteDepartment(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("delete department failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("delete department error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询业务单元部门树", httpMethod = "POST")
    @PostMapping(value = "/queryBUDepTree")
    public RestMessage<List<OrgTreeDto>> queryBUDepTree(@RequestBody QueryByIdReqParam reqParam) {
        LOGGER.info("delete department request, param : {}.", reqParam);
        try {
            if (NumericUtil.nullOrlessThanOrEqualToZero(reqParam.getId())) {
                LOGGER.warn("delete department not exist,depId:{}.", reqParam.getId());
                throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.department.orgid.null"));
            }
            final List<OrgTreeBean> deps = departmentService.queryDepTreeByBUId(reqParam);
            final List<OrgTreeDto> buildeTree = depTreeBuilder.builde(deps);
            return RestMessage.doSuccess(buildeTree);
        } catch (OrgException oex) {
            LOGGER.info("delete department failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("delete department error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询部门详情", httpMethod = "POST")
    @PostMapping(value = "/queryDepDetail")
    public RestMessage<DepWithStaffDetailVo> queryDepDetail(@RequestBody QueryByIdReqParam reqParam) {
        LOGGER.info("query dep with staff detail request, param : {}.", reqParam);
        try {
            queryByIdReqParamValidator.validate(reqParam);

            final DepWithStaffDetailVo depWithStaff = departmentService.queryDepDetail(reqParam);

            return RestMessage.doSuccess(depWithStaff);
        } catch (OrgException oex) {
            LOGGER.info("query dep with staff detail failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query dep with staff detail error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    /**
     * 查询部门简单信息
     */
    @ApiOperation(value = "查询部门简要信息", httpMethod = "POST")
    @PostMapping(value = "/queryDepInfoById")
    public RestMessage<DepWithStaffDetailVo> queryDepInfoById(@RequestBody QueryByIdReqParam param) {
        LOGGER.info("queryDepinfoById(),param:{}", param);
        try {
            DepWithStaffDetailVo depWithStaffDetailVo = departmentService.queryDepInfoById(param);
            return RestMessage.doSuccess(depWithStaffDetailVo);
        } catch (OrgException oex) {
            LOGGER.info("query departmentInfo failed,param:" + param, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception e) {
            LOGGER.error("query departmentInfo error,param:" + param, e);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    private List<OrgInfoDto> recursiveChildrenDepsByDepId(Long depId) {
        LOGGER.info("根据部门id查询所有的子部门：{}", depId);
        Assert.notNull(depId, I18nUtils.getMessage("org.department.departmentid.null"));
        List<OrgInfoDto> allDeps = new ArrayList<>();
        BatchQueryParam param = new BatchQueryParam();
        param.setState(2);
        param.setParentId(depId);
        //找到子部门
        List<OrgInfoDto> list = organizationService.batchQueryOrgInfoNofuc(param);
        if (!ObjectUtils.isEmpty(list)) {
            allDeps.addAll(list);
            for (OrgInfoDto item : list) {
                if (!ObjectUtils.isEmpty(item.getParentId()) && item.getParentId() != 0) {
                    List<OrgInfoDto> children = recursiveChildrenDepsByDepId(item.getId());
                    allDeps.addAll(children);
                }
            }
        }
        return allDeps;
    }

    private List<Long> recursiveParentDepsByDepId(Long depId) {
        LOGGER.info("根据部门id查询所有的父部门：{}", depId);
        Assert.notNull(depId, I18nUtils.getMessage("org.department.departmentid.null"));
        OrgInfoDto orgInfoDto = organizationService.getOrgInfoById(depId);
        List<Long> allParentIds = new ArrayList<>();
        if (!ObjectUtils.isEmpty(orgInfoDto)) {
            if (!ObjectUtils.isEmpty(orgInfoDto.getParentId()) && orgInfoDto.getParentId() > 0) {
                allParentIds.add(orgInfoDto.getParentId());
                List<Long> ids = recursiveParentDepsByDepId(orgInfoDto.getParentId());
                allParentIds.addAll(ids);
            }
        }
        return allParentIds;
    }

    /**
     * 根据部门id查询该部门信息和所有上级部门信息
     *
     * @return
     */
    @ApiOperation(value = "查询指定部门的所有上级部门(包括该部门)", httpMethod = "POST")
    @PostMapping(value = "/getAllParentDepartment")
    public RestMessage<List<DepartmentDto>> getAllParentDepartment(@RequestBody GetAllParentDepartMentDto getAllParentDepartMentDto) {
        List<DepartmentDto> resultData = Lists.newArrayList();
        if (ObjectUtils.isEmpty(getAllParentDepartMentDto) || ObjectUtils.isEmpty(getAllParentDepartMentDto.getId())) {
            return RestMessage.doSuccess(resultData);
        }
        resultData = organizationService.findAllParentDepartment(getAllParentDepartMentDto.getId());
        return RestMessage.doSuccess(resultData);
    }

    /**
     * 根据业务单元Id查询部门简要信息
     *
     * @param buId
     * @return
     */
    @ApiOperation(value = "根据业务单元Id查询部门简要信息", httpMethod = "GET")
    @GetMapping(value = "/queryDepInfoByBuId")
    public RestMessage<List<DepWithStaffDetailVo>> queryDepInfoByBuId(@RequestParam(value = "buId") Long buId) {
        try {
            if (NumericUtil.nullOrlessThanOrEqualToZero(buId)) {
                throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.department.departmentid.null"));
            }
            List<DepWithStaffDetailVo> staffDetailVoList = departmentService.queryDepInfoByBuId(buId);
            return RestMessage.doSuccess(staffDetailVoList);
        } catch (Exception ex) {
            LOGGER.error("queryDepInfoByBuId error, buId:" + buId, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }
}
