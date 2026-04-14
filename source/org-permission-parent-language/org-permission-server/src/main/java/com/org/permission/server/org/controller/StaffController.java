package com.org.permission.server.org.controller;

import com.boss.crm.common.util.BeanUtil;
import com.common.base.entity.CurrentUser;
import com.common.base.enums.BooleanEnum;
import com.common.framework.user.FplUserUtil;
import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.common.util.util.AssertUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.*;
import com.org.permission.common.org.param.*;
import com.org.permission.common.org.vo.StaffDetailInfoVo;
import com.org.permission.common.org.vo.StaffDutyInfoVo;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.server.domain.crm.CustDomainService;
import com.org.permission.server.domain.permission.PermissionDomainService;
import com.org.permission.server.domain.user.UserDomainService;
import com.org.permission.server.domain.wms.StorageDomainService;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.StaffDutyInfoBean;
import com.org.permission.server.org.bean.StaffInfoBean;
import com.org.permission.server.org.bean.WarehouseBindingStaffInfoBean;
import com.org.permission.server.org.dto.param.BatchEnableStaffParam;
import com.org.permission.server.org.dto.param.BatchOpParam;
import com.org.permission.server.org.dto.param.BindingStaffParam;
import com.org.permission.server.org.dto.param.QueryArchiveListParam;
import com.org.permission.server.org.enums.ArchiveTypeEnum;
import com.org.permission.server.org.enums.OrgTypeEnum;
import com.org.permission.server.org.service.StaffService;
import com.org.permission.server.org.util.OrgBeanUtil;
import com.org.permission.server.org.util.PageUtil;
import com.org.permission.server.org.util.StaffUtil;
import com.org.permission.server.org.validator.*;
import com.org.permission.server.org.vo.ArchiveInfoVo;
import com.usercenter.client.feign.UserStaffMapFeign;
import com.usercenter.common.dto.FplUser;
import com.usercenter.common.dto.UserStaffMapDto;
import com.usercenter.common.dto.request.UserStaffMapFindListReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 人员管理控制器
 */
@RestController
@Api(tags = "0人员管理接口文档 ")
@RequestMapping("/staff")
public class StaffController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StaffController.class);

    @Resource
    private QueryStaffsReqParamValidator queryStaffsReqParamValidator;

    @Resource
    private QueryByIdReqParamValidator queryByIdReqParamValidator;

    @Resource
    private QueryStaffListReqParamValidator queryStaffListReqParamValidator;

    @Resource
    private DeleteOperateReqParamValidator deleteOperateReqParamValidator;

    @Resource
    private BatchQueryParamValidator batchQueryParamValidator;

    @Resource
    private GenerateUserParamValidator generateUserParamValidator;

    @Resource
    private StorageDomainService storageDomainService;

    @Resource
    private UserDomainService userDomainService;

    @Resource
    private StaffService staffService;

    @Resource
    private CustDomainService custDomainService;

    @Resource
    private QueryArchiveListParamValidator queryArchiveListParamValidator;
    private List<StaffDutyInfoBean> staffDuties;

    @Resource
    private UserStaffMapFeign userStaffMapFeign;
    @Resource
    private PermissionDomainService permissionDomainService;

    @ApiOperation(value = "创建人员", httpMethod = "POST")
    @PostMapping(value = "/create")
    public RestMessage<Long> create(@RequestBody CreateStaffInfoParam reqParam) {
        LOGGER.info("create staff with multiple staff type,param:{}.", reqParam);
        try {
            Long staffId = staffService.createStaff(reqParam);
            return RestMessage.doSuccess(staffId);
        } catch (OrgException oex) {
            LOGGER.warn("create staff with multiple staff type failed,param:" + reqParam, oex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception") + "：" + oex.getMessage());
        } catch (Exception ex) {
            LOGGER.warn("create staff with multiple staff type error,param:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception") + "：" + ex.getMessage());
        }
    }

    @ApiOperation(value = "查询人员详细信息", httpMethod = "POST")
    @PostMapping(value = "/queryDetailInfoById")
    public RestMessage<StaffDetailInfoVo> queryDetailInfoById(@RequestBody QueryByIdReqParam reqParam) {
        LOGGER.info("query staff with multiple staff type,param:{}.", reqParam);
        try {
            final StaffInfoBean staffBean = staffService.queryDetailInfoById(reqParam);
            //查询直属上级名字
            staffBean.getDirectSupervisorId();
            StaffUtil.setUserId(staffBean);
            StaffDetailInfoVo staffDetailInfoVo = wrapSingleStaff(staffBean);
            if (null != staffDetailInfoVo && staffDetailInfoVo.getUserId() != null) {
                FplUser fplUser = userDomainService.getUserInfo(staffBean.getUserId());
                staffDetailInfoVo.setUserName(null != fplUser ? fplUser.getUserName() : "");
            }
            return RestMessage.doSuccess(staffDetailInfoVo);
        } catch (OrgException oex) {
            LOGGER.warn("query staff with multiple staff type failed,param:" + reqParam, oex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception") + "：" + oex.getMessage());
        } catch (Exception ex) {
            LOGGER.warn("query staff with multiple staff type error,param:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception") + "：" + ex.getMessage());
        }

    }

    @ApiOperation(value = "修改状态操作", httpMethod = "POST")
    @PostMapping(value = "/modifyStateOp")
    public RestMessage<Boolean> modifyStateOp(@RequestBody ModifyOperateParam reqParam) {
        LOGGER.info("enable staff request, param : {}.", reqParam);
        try {
            staffService.modifyStateOp(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("enable staff failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("enable staff error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception") + "：" + ex.getMessage());
        }
    }

    @ApiOperation(value = "组织批量停用", httpMethod = "POST")
    @PostMapping(value = "/batchEnableStaff")
    public RestMessage<Boolean> batchEnableStaff(@RequestBody BatchEnableStaffParam param) {
        try {
            staffService.batchEnableStaff(param.getUserId(), param.getStaffIds(), param.getState());
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (Exception e) {
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception") + "：" + e.getMessage());
        }
    }


    /**
     * 人员生成用户
     *
     * @param reqParam 请求参数
     * @return 用户生成结果
     */
    @ApiOperation(value = "生成用户", httpMethod = "POST")
    @PostMapping(value = "/generateUser")
    public RestMessage<GeneratorStaffDto> generateUser(@RequestBody GenerateUserParam reqParam) {
        LOGGER.info("generate user request param :{}.", reqParam);
        try {
            generateUserParamValidator.validate(reqParam);
            GeneratorStaffDto generatorStaffDto = staffService.generateUser(reqParam);
            return RestMessage.doSuccess(generatorStaffDto);
        } catch (OrgException oex) {
            LOGGER.info("generate user failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("generate user error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "删除人员", httpMethod = "POST")
    @PostMapping(value = "/deleteStaff")
    public RestMessage<Boolean> deleteStaff(@RequestBody KeyOperateParam reqParam) {
        LOGGER.info("delete staff request, param : {}.", reqParam);
        try {
            deleteOperateReqParamValidator.validate(reqParam);
            staffService.deleteStaff(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("delete staff failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("delete staff error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "绑定人员", httpMethod = "POST")
    @PostMapping(value = "/bingdingStaff")
    public RestMessage<Boolean> bindingStaff(@RequestBody BindingStaffParam reqParam) {
        LOGGER.info("binding staffs param: {}.", reqParam);
        try {
            reqParam.setCreatedBy(FplUserUtil.getUserId());
            staffService.bindingStaff(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("binding staffs failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("binding staffs error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception") + "：" + ex.getMessage());
        }
    }

    @ApiOperation(value = "解绑人员", httpMethod = "POST")
    @PostMapping(value = "/unbindingStaff")
    public RestMessage<Boolean> unbindingStaff(@RequestBody BatchOpParam reqParam) {
        LOGGER.info("unbinding staffs param:{}.", reqParam);
        try {
            staffService.unbindingStaff(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("unbinding staffs failed param:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("unbinding staffs error param:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception") + "：" + ex.getMessage());
        }
    }

    @ApiOperation(value = "更新人员", httpMethod = "POST")
    @PostMapping(value = "/modify")
    public RestMessage<Boolean> modify(@RequestBody ModifyStaffInfoDto reqParam) {
        LOGGER.info("modify staff with multiple staff type,param:{}.", reqParam);
        try {
            staffService.modifyStaff(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.warn("modify staff with multiple staff type failed,param:" + reqParam, oex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception") + "：" + oex.getMessage());
        } catch (Exception ex) {
            LOGGER.warn("modify staff with multiple staff type error,param:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception") + "：" + ex.getMessage());
        }

    }

    @ApiOperation(value = "查询人员列表（根据组织id查询人员信息关联用户信息）", httpMethod = "POST")
    @PostMapping(value = "/queryStaffList")
    public RestMessage<PageInfo<StaffDetailInfoVo>> queryStaffList(@RequestBody QueryStaffListReqParam reqParam) {
        LOGGER.info("query staff list param : {}.", reqParam);
        try {
            queryStaffListReqParamValidator.validate(reqParam);
            // 添加权限过滤
            CurrentUser currentUser = FplUserUtil.getCurrentUser();
            Set<Long> orgIds = permissionDomainService.getOrgsInPermission(currentUser.getUserId(), currentUser.getGroupId(), OrgTypeEnum.ORGANIZATION);
            if (CollectionUtils.isEmpty(orgIds)) {
                return RestMessage.querySuccess(new PageInfo<>());
            }
            reqParam.setOrgIds(new ArrayList<>(orgIds));
            final PageInfo<StaffInfoBean> staffPagedList = staffService.queryStaffList(reqParam);

            return wrapStaffListInfo(staffPagedList);
        } catch (OrgException oex) {
            LOGGER.info("query staff list failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query staff list error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询人员简要信息", httpMethod = "POST")
    @PostMapping(value = "/getSimpleStaffList")
    public RestMessage<PageInfo<DepartmentStaffDto>> getSimpleStaffList(@RequestBody GetSimpleStaffListReq req) {
        LOGGER.info("getSimpleStaffList param : {}.", req);
        PageInfo<DepartmentStaffDto> pageResult = staffService.getSimpleStaffList(req);
        return RestMessage.doSuccess(pageResult);
    }

    /**
     * 封装人员列表 返回值 （对人员类别进行处理）
     *
     * @param staffPage 人员分页查询结果
     * @return 列表封装结果
     */
    private RestMessage<PageInfo<StaffDetailInfoVo>> wrapStaffListInfo(final PageInfo<StaffInfoBean> staffPage) {
        PageInfo<StaffDetailInfoVo> pageInfo = PageUtil.convert(staffPage, item -> {
            return wrapSingleStaff(item);
        });
        return RestMessage.doSuccess(pageInfo);
    }

    private StaffDetailInfoVo wrapSingleStaff(final StaffInfoBean staffBean) {
        if (Objects.isNull(staffBean)) {
            return null;
        }

        StaffDetailInfoVo staffDetailInfoVo = new StaffDetailInfoVo();
        BeanUtils.copyProperties(staffBean, staffDetailInfoVo);

        final List<StaffDutyInfoBean> staffDuties = staffBean.getStaffDuties();
        if (CollectionUtils.isNotEmpty(staffDuties)) {
            final StaffDutyInfoBean staffDutyInfoBean = staffDuties.get(0);
            StaffDutyInfoVo staffDutyInfoVo = new StaffDutyInfoVo();
            BeanUtils.copyProperties(staffDutyInfoBean, staffDutyInfoVo);
            final StringBuilder staffTypeNameBuilder = new StringBuilder();
            final StringBuilder staffTypeIdBuilder = new StringBuilder();
            staffDuties.forEach(staffDuty -> {
                staffTypeIdBuilder.append(",").append(staffDuty.getStaffTypeId());
                staffTypeNameBuilder.append(",").append(staffDuty.getStaffTypeName());
            });
            staffDutyInfoVo.setStaffTypeId(staffTypeIdBuilder.toString().substring(1));
            staffDutyInfoVo.setStaffTypeName(staffTypeNameBuilder.toString().substring(1));
            staffDetailInfoVo.setStaffDuty(staffDutyInfoVo);
        }
        //查询直属上级名字
        if (staffBean.getDirectSupervisorId() != null && staffBean.getDirectSupervisorId().longValue() != 0) {
            String directSuperVisor = staffService.queryStaffName(staffBean.getDirectSupervisorId());
            if (!StringUtils.isEmpty(directSuperVisor)) {
                staffDetailInfoVo.setDirectSupervisorName(directSuperVisor);
            }
        }
        return staffDetailInfoVo;
    }

    @ApiOperation(value = "查询绑定人员信息", httpMethod = "POST")
    @PostMapping(value = "/queryBindingStaff")
    public RestMessage<PageInfo<WarehouseBindingStaffInfoDto>> queryBindingStaff(@RequestBody QueryBindingStaffParam reqParam) {
        LOGGER.info("query bingding staffs param : {}.", reqParam);
        try {
            PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
            final PageInfo<WarehouseBindingStaffInfoBean> staffPage = staffService.queryBindingStaff(reqParam);
            long totalCount = staffPage.getTotal();
            if (totalCount == 0) {
                return RestMessage.doSuccess(new PageInfo<>());
            }
            Set<Long> set = new HashSet<>();
            List<WarehouseBindingStaffInfoBean> staffs = staffPage.getList();
            for (WarehouseBindingStaffInfoBean warehouseBindingStaffInfoBean : staffs) {
                set.add(warehouseBindingStaffInfoBean.getRelId());
            }
            Map<Long, String> warehouseNameMap = storageDomainService.batchQueryWarehouseNameByIds(set);

            final List<WarehouseBindingStaffInfoDto> staffInfoDtos = OrgBeanUtil.listConvert(staffs, new WarehouseBindingStaffInfoDto());

            for (WarehouseBindingStaffInfoDto staffInfoDto : staffInfoDtos) {
                staffInfoDto.setRelName(warehouseNameMap.get(staffInfoDto.getRelId()));
            }
            userDomainService.batchFillUserName(staffInfoDtos);
            StaffUtil.setUserId(staffInfoDtos);
            PageInfo<WarehouseBindingStaffInfoDto> pageInfo = new PageInfo<>();
            pageInfo.setTotal(totalCount);
            pageInfo.setList(staffInfoDtos);
            pageInfo.setPageNum(staffPage.getPageNum());
            pageInfo.setPageSize(staffPage.getPageSize());
            return RestMessage.doSuccess(pageInfo);
        } catch (OrgException oex) {
            LOGGER.info("query bingding staffs failed ,param:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query bingding staffs error ,param:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询未绑定人员信息", httpMethod = "POST")
    @PostMapping(value = "/queryNotBindingStaff")
    public RestMessage<PageInfo<WarehouseBindingStaffInfoDto>> queryNotBindingStaff(@RequestBody QueryBindingStaffParam reqParam) {
        LOGGER.info("query bingding staffs param : {}.", reqParam);
        try {
            PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
            final PageInfo<WarehouseBindingStaffInfoBean> staffPage = staffService.queryNotBindingStaff(reqParam);
            PageInfo<WarehouseBindingStaffInfoDto> pageInfo = PageUtil.convert(staffPage, item -> {
                WarehouseBindingStaffInfoDto warehouseBindingStaffInfoDto = new WarehouseBindingStaffInfoDto();
                BeanUtils.copyProperties(item, warehouseBindingStaffInfoDto);
                return warehouseBindingStaffInfoDto;
            });
            return RestMessage.doSuccess(pageInfo);
        } catch (OrgException oex) {
            LOGGER.info("query not binding staffs failed ,param:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query not binding staffs error ,param:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception") + ex.getMessage());
        }
    }

    @ApiOperation(value = "根据主键查询人员详细信息", httpMethod = "POST")
    @PostMapping(value = "/queryStaff")
    public RestMessage<StaffWithDutyInfoDto> queryStaff(@RequestBody QueryByIdReqParam reqParam) {
        LOGGER.info("query staff param:{}.", reqParam);
        try {
            queryByIdReqParamValidator.validate(reqParam);

            final StaffInfoBean staffInfoBean = staffService.queryStaffWithDuty(reqParam);
            if (staffInfoBean != null) {
                StaffUtil.setUserId(staffInfoBean);
                return wrapStaffDutyInfo(staffInfoBean);
            } else {
                return RestMessage.doSuccess(null);
            }
        } catch (OrgException oex) {
            LOGGER.info("query staff failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query staff error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception") + ex.getMessage());
        }
    }

    private RestMessage<StaffWithDutyInfoDto> wrapStaffDutyInfo(final StaffInfoBean staffInfoBean) {
        if (staffInfoBean == null) {
            return null;
        }
        StaffWithDutyInfoDto staffWithDutyInfoDto = new StaffWithDutyInfoDto();
        BeanUtils.copyProperties(staffInfoBean, staffWithDutyInfoDto);

        final List<StaffDutyInfoBean> staffDuties = staffInfoBean.getStaffDuties();
        if (!CollectionUtils.isEmpty(staffDuties)) {
            final StaffDutyInfoBean staffDutyInfoBean = staffDuties.get(0);
            StaffDutyInfoDvo staffDutyInfoDvo = new StaffDutyInfoDvo();
            BeanUtils.copyProperties(staffDutyInfoBean, staffDutyInfoDvo);
            final StringBuilder staffTypeNameBuilder = new StringBuilder();
            staffDuties.forEach(staffDuty -> {
                staffTypeNameBuilder.append(staffDuty.getStaffTypeName());
            });
            staffDutyInfoDvo.setStaffTypeName(staffTypeNameBuilder.toString());
            staffWithDutyInfoDto.setStaffDuty(staffDutyInfoDvo);
        }

        return RestMessage.doSuccess(staffWithDutyInfoDto);
    }

    @ApiOperation(value = "递归业务单元查询人员", httpMethod = "POST")
    @PostMapping(value = "/recurseQueryStaffs")
    public RestMessage<PageInfo<StaffInfoDto>> recurseQueryStaffs(@RequestBody QueryStaffsReqParam reqParam) {
        LOGGER.info("recurse query staffs param: {}.", reqParam);
        try {
            queryStaffsReqParamValidator.validate(reqParam);
            PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
            final PageInfo<StaffInfoBean> pageListStaffs = staffService.recurseQueryStaffs(reqParam);
            PageInfo<StaffInfoDto> pageInfo = PageUtil.convert(pageListStaffs, item -> wrapStaffInfo(item));
            StaffUtil.setUserId(pageInfo.getList());
            return RestMessage.doSuccess(pageInfo);
        } catch (OrgException oex) {
            LOGGER.info("recurse query staffs failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("recurse query staffs error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception") + ex.getMessage());
        }
    }

    @ApiOperation(value = "分页查询人员列表", httpMethod = "POST")
    @PostMapping(value = "/queryStaffs")
    public RestMessage<PageInfo<StaffInfoDto>> queryStaffs(@RequestBody QueryStaffsReqParam reqParam) {
        LOGGER.info("query staffs param : {}.", reqParam);
        try {
            queryStaffsReqParamValidator.validate(reqParam);
            PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
            final PageInfo<StaffInfoBean> pageListStaffs = staffService.queryCurrentOrgStaffs(reqParam);
            PageInfo<StaffInfoDto> pageInfo = PageUtil.convert(pageListStaffs, this::wrapStaffInfo);
            StaffUtil.setUserId(pageInfo.getList());
            return RestMessage.doSuccess(pageInfo);
        } catch (OrgException oex) {
            LOGGER.info("query staffs failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query staffs error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }


    /**
     * 批量封装人员信息
     *
     * @param staffInfoBeans 人员信息集合
     * @return 人员信息
     */
    private List<StaffInfoDto> wrapStaffInfos(final List<StaffInfoBean> staffInfoBeans) {
        if (CollectionUtils.isEmpty(staffInfoBeans)) {
            return Collections.emptyList();
        }
        List<StaffInfoDto> staffInfoDtos = new ArrayList<>(staffInfoBeans.size());
        staffInfoBeans.forEach(staffInfoBean -> {
            staffInfoDtos.add(wrapStaffInfo(staffInfoBean));
        });
        return staffInfoDtos;
    }

    /**
     * 封装人员信息
     *
     * @param staffInfoBean 人员信息
     * @return 人员信息
     */
    private StaffInfoDto wrapStaffInfo(final StaffInfoBean staffInfoBean) {
        if (Objects.isNull(staffInfoBean)) {
            return null;
        }
        StaffInfoDto staffInfoDto = new StaffInfoDto();
        BeanUtils.copyProperties(staffInfoBean, staffInfoDto);

        final List<StaffDutyInfoBean> staffDuties = staffInfoBean.getStaffDuties();
        if (!CollectionUtils.isEmpty(staffDuties)) {
            final StaffDutyInfoBean staffDutyInfoBean = staffDuties.get(0);
            StaffDutyInfoDvo staffDutyInfoDvo = new StaffDutyInfoDvo();
            BeanUtils.copyProperties(staffDutyInfoBean, staffDutyInfoDvo);
            final StringBuilder staffTypeNameBuilder = new StringBuilder();
            staffDuties.forEach(staffDuty -> {
                staffTypeNameBuilder.append(staffDuty.getStaffTypeName());
                staffTypeNameBuilder.append(",");
            });
            staffInfoDto.setStaffTypeName(org.apache.commons.lang.StringUtils.substringBeforeLast(staffTypeNameBuilder.toString(), ","));
        }

        return staffInfoDto;
    }

    @ApiOperation(value = "批量查询人员信息", httpMethod = "POST")
    @PostMapping(value = "/batchQueryStaffsInfo")
    public RestMessage<List<StaffInfoDto>> batchQueryStaffsInfo(@RequestBody BatchQueryParam queryParam) {
        LOGGER.info("batch query staff info param :{}.", queryParam);
        try {
            batchQueryParamValidator.validate(queryParam);
            final List<StaffInfoBean> staffList = staffService.batchQueryStaffInfo(queryParam);
            RestMessage<List<StaffInfoDto>> restMessage = new RestMessage<>();
            if (staffList.size() > 0) {
                final List<StaffInfoDto> staffInfoDtos = wrapStaffInfos(staffList);
                StaffUtil.setUserId(staffInfoDtos);
                restMessage.setData(staffInfoDtos);
                return restMessage;
            }
            return restMessage;
        } catch (final OrgException oex) {
            LOGGER.info("batch query staff info failed,reqParam:" + queryParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (final Exception ex) {
            LOGGER.info("batch query staff info error,reqParam:" + queryParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception") + ex.getMessage());
        }
    }

    @ApiOperation(value = "人员基本信息查询", httpMethod = "POST")
    @PostMapping(value = "/batchQueryStaffBasicInfo")
    public RestMessage<List<StaffInfoDto>> batchQueryStaffBasicInfo(@RequestBody StaffBasicParam queryParam) {
        if (!CollectionUtils.isEmpty(queryParam.getCodes()) && !CollectionUtils.isEmpty(queryParam.getNames())) {
            AssertUtils.isTrue(false, "codes和names参数不能同时不为空");
        }
        try {
            List<StaffInfoDto> staffInfoDtos = staffService.batchQueryStaffBasicInfo(queryParam);
            if (CollectionUtils.isNotEmpty(staffInfoDtos)) {
                return RestMessage.doSuccess(staffInfoDtos);
            }
            return RestMessage.doSuccess(new ArrayList<>());
        } catch (Exception e) {
            LOGGER.info("batch query staff info error,reqParam:" + queryParam, e);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception") + e.getMessage());
        }
    }

    @ApiOperation(value = "根据部门ID查询人员列表(不分页)", httpMethod = "GET")
    @GetMapping(value = "/queryDepStaffsByDepId")
    public RestMessage<List<StaffInfoDto>> queryDepStaffsByDepId(@RequestParam(value = "depId") Long depId) {
        LOGGER.info("query dep staffs param:depId:{}.", depId);
        try {
            Assert.isTrue(depId != null && depId > 1, I18nUtils.getMessage("org.department.departmentid.null"));
            List<StaffInfoBean> staffList = staffService.queryStaffByDepId(depId);
            RestMessage<List<StaffInfoDto>> restMessage = new RestMessage<>();
            if (staffList.size() > 0) {
                final List<StaffInfoDto> staffInfoDtos = wrapStaffInfos(staffList);
                StaffUtil.setUserId(staffInfoDtos);
                restMessage.setData(staffInfoDtos);
            }
            return restMessage;
        } catch (IllegalArgumentException iae) {
            LOGGER.warn("query dep staffs illegal param ,depId:{}.", depId);
            return RestMessage.error(OrgErrorCode.REQ_PARAM_ERROR_CODE + "", iae.getMessage());
        } catch (OrgException oex) {
            LOGGER.warn("query dep staffs failed,param:depId:" + depId, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query dep staffs error,prams:" + depId, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception") + ex.getMessage());
        }
    }

    @ApiOperation(value = "人员查询", httpMethod = "POST")
    @PostMapping(value = "/queryStaffByPojo")
    public RestMessage<List<StaffWithDutyInfoDto>> queryStaffByPojo(@RequestBody StaffWithDutyInfoDto staffWithDutyInfoDto) {
        LOGGER.info("queryStaffByPojo :{}.", staffWithDutyInfoDto);
        try {
            List<StaffWithDutyInfoDto> result = staffService.queryStaffByPojo(staffWithDutyInfoDto);
            StaffUtil.setUserId(result);
            return RestMessage.doSuccess(result);
        } catch (Exception e) {
            LOGGER.error("queryStaffByPojo:" + staffWithDutyInfoDto, e.getMessage());
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception") + e.getMessage());
        }
    }

    @ApiOperation(value = "人员排除查询", httpMethod = "POST")
    @PostMapping(value = "/queryStaffForCrm")
    public RestMessage<PageInfo<StaffInfoDto>> queryStaffForCrm(@RequestBody StaffForCrmParam staffForCrmParam) {
        PageInfo<StaffInfoDto> staffInfoDtoPagedList = staffService.queryStaffForCrm(staffForCrmParam);
        return RestMessage.doSuccess(staffInfoDtoPagedList);
    }

    @ApiOperation(value = "判断用户是否含有当前人员类别")
    @GetMapping(value = "/queryStaffTypeByUserId")
    public RestMessage<Boolean> queryStaffTypeByUserId(@RequestParam(value = "userId") Long userId, @RequestParam(value = "typeId") Integer typeId) {
        LOGGER.info("judge user wether has staff type param:userId:{},typeId:{}.", userId, typeId);
        try {
            Assert.isTrue(userId != null && userId > 0, I18nUtils.getMessage("org.common.param.userid.cannot.null"));
            Assert.isTrue(typeId != null && typeId > 0, I18nUtils.getMessage("org.staff.type.null"));
            Boolean hasStaffType = staffService.judgeUserHasStaffType(userId, typeId);
            return RestMessage.doSuccess(hasStaffType);
        } catch (IllegalArgumentException iae) {
            LOGGER.warn("judge user wether has staff type illegal param:userId:" + userId + "typeId:" + typeId);
            return RestMessage.error(OrgErrorCode.REQ_PARAM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        } catch (OrgException oex) {
            LOGGER.info("judge user wether has staff type failed,param:userId:" + userId + ",typeId:" + typeId, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("judge user wether has staff type error,param:userId:" + userId + ",typeId:" + typeId, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception") + ex.getMessage());
        }
    }

    @ApiOperation(value = "根据用户 ID 查询人员 ID", httpMethod = "GET")
    @GetMapping(value = "/queryStaffIdByUserId")
    public RestMessage<Long> queryStaffIdByUserId(@RequestParam(value = "userId") Long userId) {
        LOGGER.info("query staff id by user id,param:{}.", userId);
        try {
            Assert.isTrue((userId != null && userId > 0), I18nUtils.getMessage("org.common.param.userid.cannot.null"));

            Long staffId = staffService.queryStaffIdByUserId(userId);
            return RestMessage.doSuccess(staffId);
        } catch (IllegalArgumentException iae) {
            LOGGER.warn("query staff id by user id failed,param:{}.", userId);
            return RestMessage.error(OrgErrorCode.REQ_PARAM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception")+"：" + iae.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query staff id by user id error,param:" + userId, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception")+"："  + ex.getMessage());
        }
    }

    @ApiOperation(value = "根据用户 ID 查询人员信息", httpMethod = "GET")
    @GetMapping(value = "/queryStaffInfoByUserId")
    public RestMessage<StaffInfoDto> queryStaffInfoByUserId(@RequestParam(value = "userId") Long userId) {
        LOGGER.info("query staff id by user id,param:{}.", userId);
        try {
            AssertUtils.isTrue((userId != null && userId > 0), I18nUtils.getMessage("org.common.param.userid.cannot.null"));
            StaffInfoDto staffInfoDto = staffService.queryStaffInfoByUserId(userId);
            return RestMessage.doSuccess(staffInfoDto);
        } catch (IllegalArgumentException iae) {
            LOGGER.warn("query staff info by user id failed,param:{}.", userId);
            return RestMessage.error(OrgErrorCode.REQ_PARAM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception")+"："  + iae.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query staff info by user id error,param:" + userId, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception")+"："  + ex.getMessage());
        }
    }

    @ApiOperation(value = "根据人员ID删除用户ID", httpMethod = "GET")
    @GetMapping(value = "/deleteUserIdByStaffId")
    public RestMessage<Integer> deleteUserIdByStaffId(@RequestParam(value = "staffId") Long staffId) {
        LOGGER.info("staff generate a user req,param:{}.", staffId);
        try {
            Assert.isTrue(staffId != null, I18nUtils.getMessage("org.common.param.staffid.cannot.null"));
            Integer result = staffService.deleteUserIdByStaffId(staffId);
            return RestMessage.doSuccess(result);
        } catch (Exception e) {
            LOGGER.warn("system warning");
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception") + e.getMessage());
        }
    }

    @ApiOperation(value = "根据人员id更新用户id", httpMethod = "GET")
    @GetMapping(value = "/updateUserIdByStaffId")
    public RestMessage<Integer> updateUserIdByStaffId(@RequestParam(value = "staffId", required = true) Long staffId, @RequestParam(value = "userId", required = true) Long userId) {
        LOGGER.info("staff generate a user req,param:{}.", staffId);
        try {
            Assert.isTrue(staffId != null, I18nUtils.getMessage("org.common.param.staffid.cannot.null"));
            Integer result = staffService.updateUserIdByStaffId(staffId, userId);
            return RestMessage.doSuccess(result);
        } catch (Exception e) {
            LOGGER.warn("system warning");
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception") + e.getMessage());
        }
    }

    @ApiOperation(value = "查询档案列表（权限）", httpMethod = "POST")
    @PostMapping(value = "/queryArchiveList")
    public RestMessage<PageInfo<ArchiveInfoVo>> queryArchiveList(@RequestBody QueryArchiveListParam reqParam) {
        LOGGER.info("query archive req param :{}.", reqParam);
        try {
            queryArchiveListParamValidator.validate(reqParam);
            if (reqParam.getArchiveType() == ArchiveTypeEnum.STAFF.getType()) {
                UserStaffMapFindListReq query = UserStaffMapFindListReq.createStaffQuery(null, null, BooleanEnum.FALSE.getCode());
                RestMessage<List<UserStaffMapDto>> userStaffMapList = userStaffMapFeign.findList(query);
                if (userStaffMapList.isSuccess() && !ObjectUtils.isEmpty(userStaffMapList.getData())) {
                    reqParam.setBoundUserStaffIds(userStaffMapList.getData().stream().map(userStaffMapDTO -> userStaffMapDTO.getStaffId()).collect(Collectors.toList()));
                }
                PageInfo<ArchiveInfoVo> archivePageList = staffService.queryArchiveList(reqParam);
                return RestMessage.doSuccess(archivePageList);
            } else {
                PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
                PageInfo<ArchiveInfoVo> archivePageList = custDomainService.getCustArchiveList(reqParam);
                return RestMessage.doSuccess(archivePageList);
            }
        } catch (OrgException oex) {
            LOGGER.info("query archive failed,param:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query archive error,prams:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    /**
     * 查询直属上级
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "查询直属上级")
    @GetMapping(value = "/querySuperVisor")
    public RestMessage<Map<Long, String>> querySuperVisor(@RequestParam(value = "userId", required = true) Long userId, @RequestParam(value = "staffId", required = false) Long staffId, @RequestParam(value = "groupId", required = true) Long groupId) {
        Map<Long, String> map = staffService.querySuperVisor(userId, staffId, groupId);
        return RestMessage.doSuccess(map);
    }

    @ApiOperation(value = "人员编号跟姓名查询人员列表")
    @GetMapping(value = "/findStaffListByCondition")
    public RestMessage<List> findStaffListByCondition(@RequestParam("condition") String condition) {
        LOGGER.info("recurse query staffs param: {}.", condition);
        try {
            HashMap<String, Object> map = new HashMap();
            map.put("condition", condition);
            List<StaffInfoDto> resultList = staffService.findStaffListByCondition(map);
            if (resultList.size() == 0) {
                return RestMessage.error(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE + "", I18nUtils.getMessage("org.common.data.not.exist"));
            }
            return RestMessage.doSuccess(resultList);
        } catch (OrgException oex) {
            LOGGER.info("recurse query staffs failed,reqParam:" + condition, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("recurse query staffs error,reqParam:" + condition, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception") + ex.getMessage());
        }
    }

    @ApiOperation(value = "集团内身份证验重（true:重复）", httpMethod = "POST")
    @PostMapping(value = "/validateCardNoExsit")
    public RestMessage<Boolean> validateCardNoExsit(@RequestBody QueryStaffListReqParam param) {
        Boolean exsit = staffService.validateCardNoExsit(param);
        return RestMessage.doSuccess(exsit);
    }

    @ApiOperation(value = "查询绑定人员简要信息(人员编码、绑定关系ID)", httpMethod = "POST")
    @PostMapping(value = "/queryBindingStaffBriefInfo")
    public RestMessage<WarehouseBindingStaffInfoDto> queryBindingStaffBriefInfo(@RequestBody QueryBindingStaffParam reqParam) {
        LOGGER.info("query queryBindingStaffBriefInfo staffs param : {}.", reqParam);
        try {
            AssertUtils.isNotBlank(reqParam.getStaffCode(), I18nUtils.getMessage("org.staff.code.null"));
            AssertUtils.isNotNull(reqParam.getRelId(), I18nUtils.getMessage("org.staff.relationid.null"));
            final WarehouseBindingStaffInfoBean staffInfoBean = staffService.queryBindingStaffBriefInfo(reqParam);
            if (staffInfoBean == null) {
                return RestMessage.doSuccess(null);
            }
            WarehouseBindingStaffInfoDto warehouseBindingStaffInfoDto = BeanUtil.copyBean(staffInfoBean, WarehouseBindingStaffInfoDto.class);
            return RestMessage.doSuccess(warehouseBindingStaffInfoDto);
        } catch (OrgException oex) {
            LOGGER.info("query queryBindingStaffBriefInfo staffs failed ,param:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (RuntimeException runtimeException) {
            LOGGER.info("query queryBindingStaffBriefInfo staffs failed ,param:" + reqParam, runtimeException);
            return RestMessage.error(OrgErrorCode.REQ_PARAM_ERROR_CODE + "", runtimeException.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query queryBindingStaffBriefInfo staffs error ,param:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", StringUtils.isBlank(ex.getMessage()) ? I18nUtils.getMessage("org.common.system.exception") : ex.getMessage());
        }
    }

}
