package com.org.permission.server.org.controller;


import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.common.org.param.KeyOperateParam;
import com.org.permission.common.org.param.QueryByIdReqParam;
import com.org.permission.common.org.param.QueryStaffTypeTreeReqParam;
import com.org.permission.common.org.vo.StaffTypeTreeVo;
import com.org.permission.server.domain.user.UserDomainService;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.StaffTypeInfoBean;
import com.org.permission.server.org.bean.StaffTypeTreeBean;
import com.org.permission.server.org.builder.StaffTypeTreeBuilder;
import com.org.permission.server.org.dto.param.StaffTypeParam;
import com.org.permission.server.org.service.StaffTypeService;
import com.org.permission.server.org.validator.*;
import com.org.permission.server.org.vo.StaffTypeInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 人员类别控制器
 */
@Api(tags = "0人员类别管理接口文档 ")
@RequestMapping("/staff-type")
@RestController
public class StaffTypeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StaffTypeController.class);

    @Resource
    private AddStaffTypeParamValidator addStaffTypeParamValidator;

    @Resource
    private QueryStaffTypeTreeReqParamValidator queryStaffTypeTreeReqParamValidator;

    @Resource
    private UpdateStaffTypeParamValidator updateStaffTypeParamValidator;

    @Resource
    private EnableOperateReqParamValidator enableOperateReqParamValidator;

    @Resource
    private DeleteOperateReqParamValidator deleteOperateReqParamValidator;

    @Resource
    private QueryByIdReqParamValidator queryByIdReqParamValidator;

    @Resource
    private StaffTypeService staffTypeService;

    @Resource
    private StaffTypeTreeBuilder staffTypeTreeBuilder;

    @Resource
    private UserDomainService userDomainService;

    @ApiOperation(value = "新增", httpMethod = "POST")
    @PostMapping(value = "/add")
    public RestMessage<Boolean> add(@RequestBody StaffTypeParam reqParam) {
        LOGGER.info("add staff type request param :{}.", reqParam);
        try {
            addStaffTypeParamValidator.validate(reqParam);
            staffTypeService.createStaffType(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("add staff type failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("add staff type error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "删除", httpMethod = "POST")
    @PostMapping(value = "/delete")
    public RestMessage<Boolean> delete(@RequestBody KeyOperateParam reqParam) {
        LOGGER.info("delete staff type request param :{}.", reqParam);
        try {
            deleteOperateReqParamValidator.validate(reqParam);
            staffTypeService.deleteStaffType(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("delete staff type failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("delete staff type error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "更新", httpMethod = "POST")
    @PostMapping(value = "/update")
    public RestMessage<Boolean> update(@RequestBody StaffTypeParam reqParam) {
        LOGGER.info("update staff type request param :{}.", reqParam);
        try {
            updateStaffTypeParamValidator.validate(reqParam);
            staffTypeService.updateStaffType(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("update staff type failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("update staff type error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "主键查", httpMethod = "POST")
    @PostMapping(value = "/queryById")
    public RestMessage<StaffTypeInfoVo> queryById(@RequestBody QueryByIdReqParam reqParam) {
        LOGGER.info("query staff type request param :{}.", reqParam);
        try {
            queryByIdReqParamValidator.validate(reqParam);
            final StaffTypeInfoBean staffTypeInfoBean = staffTypeService.queryStaffType(reqParam);
            if (staffTypeInfoBean != null) {
                StaffTypeInfoVo staffTypeInfoVo = new StaffTypeInfoVo();
                BeanUtils.copyProperties(staffTypeInfoBean, staffTypeInfoVo);
                userDomainService.singleFillUserName(staffTypeInfoVo);
                return RestMessage.doSuccess(staffTypeInfoVo);
            }
            return RestMessage.doSuccess(null);
        } catch (OrgException oex) {
            LOGGER.info("query staff type failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query staff type error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "启停人员类别", httpMethod = "POST")
    @PostMapping(value = "/enable")
    public RestMessage<Boolean> enable(@RequestBody EnableOperateParam reqParam) {
        LOGGER.info("enable staff type request param :{}.", reqParam);
        try {
            enableOperateReqParamValidator.validate(reqParam);
            staffTypeService.enableStaffType(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("enable staff type failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("enable staff type error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询人员类别树", httpMethod = "POST")
    @PostMapping(value = "/queryStaffTypeTree")
    public RestMessage<StaffTypeTreeVo> queryStaffTypeTree(@RequestBody QueryStaffTypeTreeReqParam reqParam) {
        LOGGER.info("query staff type tree request param :{}.", reqParam);
        try {
            queryStaffTypeTreeReqParamValidator.validate(reqParam);
            final List<StaffTypeTreeBean> staffTypeTrees = staffTypeService.queryStaffTypeTree(reqParam);
            LOGGER.info("query staff type tree in db ,result:{}, param :{}.", staffTypeTrees, reqParam);
            final StaffTypeTreeVo treeVo = staffTypeTreeBuilder.builde(staffTypeTrees, reqParam);
            return RestMessage.doSuccess(treeVo);
        } catch (OrgException oex) {
            LOGGER.info("query staff type tree failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query staff type tree error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception") + ":" + ex.getMessage());
        }
    }

    @ApiOperation(value = "查询停用人员类别列表", httpMethod = "POST")
    @PostMapping(value = "/queryDisableStaffType")
    public RestMessage<List<StaffTypeTreeBean>> queryDisableStaffType(@RequestBody QueryStaffTypeTreeReqParam reqParam) {
        LOGGER.info("query staff type tree request param :{}.", reqParam);
        try {
            queryStaffTypeTreeReqParamValidator.validate(reqParam);
            final List<StaffTypeTreeBean> staffTypeTrees = staffTypeService.queryStaffTypeTree(reqParam);
            LOGGER.info("query staff type tree in db ,result:{}, param :{}.", staffTypeTrees, reqParam);
            return RestMessage.doSuccess(staffTypeTrees);
        } catch (Exception ex) {
            LOGGER.error("query staff type tree error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception") + ":" + ex.getMessage());
        }
    }

    /**
     * 根据父级id查询直接下级的节点
     *
     * @param reqParam
     * @return
     */
    @ApiOperation(value = "查询人员类别子节点", httpMethod = "POST")
    @PostMapping(value = "/queryChildren")
    public RestMessage<List<StaffTypeInfoBean>> queryChildren(@RequestBody QueryByIdReqParam reqParam) {
        LOGGER.info("query staff type request param :{}.", reqParam);
        List<StaffTypeInfoBean> staffTypeInfoBeans = staffTypeService.queryChildren(reqParam);
        RestMessage<List<StaffTypeInfoBean>> restMessage = new RestMessage<>();
        if (!ObjectUtils.isEmpty(staffTypeInfoBeans)) {
            restMessage.setData(staffTypeInfoBeans);
        }
        return restMessage;
    }
}
