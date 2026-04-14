package com.org.permission.server.org.controller;

import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.*;
import com.org.permission.common.org.param.*;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.server.domain.crm.CustDomainService;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.OrgTreeBean;
import com.org.permission.server.org.bean.SimpleBizUnitWithFuncBean;
import com.org.permission.server.org.bean.StaffInfoBean;
import com.org.permission.server.org.builder.MultipleOrgTreeBuilder;
import com.org.permission.server.org.builder.OrgListFilter;
import com.org.permission.server.org.dto.MultipleOrgTreeDto;
import com.org.permission.server.org.dto.OrgLogolDto;
import com.org.permission.server.org.dto.param.BatchEnableOrgParam;
import com.org.permission.server.org.dto.param.FindOrgListParam;
import com.org.permission.server.org.dto.param.UserLoginReqParam;
import com.org.permission.server.org.service.OrganizationService;
import com.org.permission.server.org.service.StaffService;
import com.org.permission.server.org.util.OrgBeanUtil;
import com.org.permission.server.org.validator.*;
import com.org.permission.server.utils.NumericUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 组织对外服务控制器
 */
@RestController
@Api(tags = "0组织接口文档")
@RequestMapping("organization")
public class OrganizationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationController.class);

    @Resource
    private UserLoginReqParamValidator userLoginReqParamValidator;

    @Resource
    private SyncCustBizTypeParamValidator syncCustBizTypeParamValidator;

    @Resource
    private QueryByIdReqParamValidator queryByIdReqParamValidator;

    @Resource
    private QueryAllStaffsInOrgReqParamValidator queryAllStaffsInOrgReqParamValidator;

    @Resource
    private QueryOrgTreeReqParamValidator queryOrgTreeReqParamValidator;

    @Resource
    private QueryOrgListReqParamValidator queryOrgListReqParamValidator;

    @Resource
    private QueryOrgPermissionListReqParamValidator queryOrgPermissionListReqParamValidator;

    @Resource
    private OrganizationService organizationService;

    @Resource
    private StaffService staffService;

    @Resource
    private LikeQueryOrgTreeReqParamValidator likeQueryOrgTreeReqParamValidator;

    @Resource
    private BatchQueryParamValidator batchQueryParamValidator;

    @Resource
    private MultipleOrgTreeBuilder multipleOrgTreeBuilder;

    @Resource
    private OrgListFilter orgListFilter;

    @Resource
    private QueryPermissionOrgInfoParamValidator queryPermissionOrgInfoParamValidator;

    @Resource
    private CustDomainService custDomainService;

    /**
     * 用户登陆，获取集团LOGO及对应客户ID
     *
     * @param reqParam 用户登陆请求参数
     * @return 用户登陆响应参数
     */
    @ApiOperation(value = " 查询组织 logo 信息", httpMethod = "POST")
    @PostMapping(value = "/queryOrgLogoInfo")
    public RestMessage<OrgLogolDto> queryOrgLogoInfo(@RequestBody final UserLoginReqParam reqParam) {
        LOGGER.info("user login request param :{}.", reqParam);
        try {
            userLoginReqParamValidator.validate(reqParam);

            final OrgLogolDto orgLogolDto = organizationService.queryOrgInfoByUser(reqParam);
            LOGGER.info("user login response ,result:{},param:{}.", orgLogolDto, reqParam);
            return RestMessage.doSuccess(orgLogolDto);
        } catch (final OrgException oex) {
            LOGGER.info("user login request failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (final Exception ex) {
            LOGGER.error("user login request error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "根据组织id查询组织客商", httpMethod = "POST")
    @PostMapping(value = "/queryOrgCust")
    public RestMessage<OrgCustDto> queryOrgCust(@RequestBody QueryByIdReqParam reqParam) {
        LOGGER.info("query org cust request param :{}.", reqParam);
        try {
            queryByIdReqParamValidator.validate(reqParam);

            final OrgCustDto orgCustDto = organizationService.queryOrgCust(reqParam);
            final Long custId = orgCustDto.getCustId();
            if (NumericUtil.greterThanZero(custId)) {
                final Map<Long, String> idNameMap = custDomainService.batchQueryCustInfoByIds(Collections.singleton(custId));
                orgCustDto.setCustName(idNameMap.get(custId));
            }
            return RestMessage.doSuccess(orgCustDto);
        } catch (final OrgException oex) {
            LOGGER.info("query org cust request failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (final Exception ex) {
            LOGGER.error("query org cust request error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "根据客商id查询客商组织信息", httpMethod = "POST")
    @PostMapping(value = "/queryCustOrg")
    public RestMessage<CustOrgDto> queryCustOrg(@RequestBody QueryByIdReqParam reqParam) {
        LOGGER.info("query cust org request param :{}.", reqParam);
        try {
            queryByIdReqParamValidator.validate(reqParam);
            final CustOrgDto custOrgDto = organizationService.queryCustOrg(reqParam);
            return RestMessage.doSuccess(custOrgDto);
        } catch (final OrgException oex) {
            LOGGER.info("query cust org request failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (final Exception ex) {
            LOGGER.error("query cust org request error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }

    }

    /**
     * 若当前业务单元为根业务单元，返回集团信息
     * 若当前组织为部门，返回上级部门，若上级部门为空则返回所属业务单元信息
     * @param orgId
     * @return
     */
    @ApiOperation(value = "查询上级组织信息", httpMethod = "GET")
    @GetMapping(value = "/queryParentOrg")
    public RestMessage<OrganizationDto> queryParentOrg(@RequestParam(value = "orgId") final Long orgId) {
        LOGGER.info("query parent org request param :{}.", orgId);
        if (NumericUtil.lessThanZero(orgId)) {
            LOGGER.warn("query parent org request illegal param :{}.", orgId);
            return RestMessage.error(OrgErrorCode.REQ_PARAM_ERROR_CODE + "", I18nUtils.getMessage("org.org.id.null"));
        }
        try {
            final SimpleBizUnitWithFuncBean buWithFunc = organizationService.queryParentOrg(orgId);
            return wrapOrgDto(buWithFunc, orgId);
        } catch (final OrgException oex) {
            LOGGER.info("query parent org request failed,reqParam:" + orgId, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (final Exception ex) {
            LOGGER.info("query parent org request error,reqParam:" + orgId, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    /**
     * 根据集团ID,查询所属集团信息
     *
     * @param reqParam ID查询请求参数
     * @return 集团信息（无组织职能）
     */
    @ApiOperation(value = "根据组织ID,查询组织信息", httpMethod = "POST")
    @PostMapping(value = "/queryOrgInfoById")
    public RestMessage<OrganizationDto> queryOrgInfoById(@RequestBody final QueryByIdReqParam reqParam) {
        LOGGER.info("query group info by id request param :{}.", reqParam);
        try {
            queryByIdReqParamValidator.validate(reqParam);

            final SimpleBizUnitWithFuncBean buWithFunc = organizationService.queryOrgById(reqParam.getId());
            return wrapOrgDto(buWithFunc, reqParam.getId());
        } catch (final OrgException oex) {
            LOGGER.info("query group info by id request failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (final Exception ex) {
            LOGGER.info("query group info by id request error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "批量查询组织信息", httpMethod = "POST")
    @PostMapping(value = "/batchQueryOrgInfo")
    public RestMessage<List<OrgInfoDto>> batchQueryOrgInfo(@RequestBody BatchQueryParam reqParam) {
        LOGGER.info("batch query org info request param :{}.", reqParam);
        try {
            batchQueryParamValidator.validate(reqParam);
            final List<OrgInfoDto> orgList = organizationService.batchQueryOrgInfo(reqParam);
            return RestMessage.doSuccess(orgList);
        } catch (final OrgException oex) {
            LOGGER.info("batch query org info request failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (final Exception ex) {
            LOGGER.info("batch query org info request error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }
    @ApiOperation(value = "批量查询组织信息(无组织职能)", httpMethod = "POST")
    @PostMapping(value = "/batchQueryOrganizations")
    public RestMessage<List<BasicOrganizationDto>> batchQueryOrganizations(@RequestBody BatchQueryParam reqParam) {
        LOGGER.info("batch query org info request param :{}.", reqParam);
        try {
            batchQueryParamValidator.validate(reqParam);
            final List<BasicOrganizationDto> orgList = organizationService.batchQueryOrganizations(reqParam);
            return RestMessage.doSuccess(orgList);
        } catch (final OrgException oex) {
            LOGGER.info("batch query org info request failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (final Exception ex) {
            LOGGER.info("batch query org info request error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }
    @ApiOperation(value = "组织查询", httpMethod = "POST")
    @PostMapping(value = "/findOrgList")
    public RestMessage<PageInfo<BasicOrganizationDto>> findOrgList(@RequestBody FindOrgListParam reqParam) {
        try {
            PageInfo<BasicOrganizationDto> pageInfo = organizationService.findOrgList(reqParam);
            //封装上级组织，归属业务单元名称
            Map<Long, String> staffMap = new HashMap<>();
            List<Long> staffIds = new ArrayList<>();
            if (!ObjectUtils.isEmpty(pageInfo.getList())) {
                for (BasicOrganizationDto item : pageInfo.getList()) {
                    if (!ObjectUtils.isEmpty(item.getDepDutyStaff()) && item.getDepDutyStaff() > 0) {
                        staffIds.add(item.getDepDutyStaff());
                    }
                }
                if (!ObjectUtils.isEmpty(staffIds)) {
                    StaffBasicParam param = new StaffBasicParam();
                    param.setIds(staffIds);
                    List<StaffInfoDto> dtos = staffService.batchQueryStaffBasicInfo(param);
                    if (CollectionUtils.isNotEmpty(dtos)) {
                        staffMap = dtos.stream().collect(Collectors.toMap(s->s.getId(),s->s.getRealname(),(k1,k2)->k2));
                    }
                }
                for (BasicOrganizationDto item : pageInfo.getList()) {
                    if (!ObjectUtils.isEmpty(item.getDepDutyStaff()) && item.getDepDutyStaff() > 0) {
                        item.setDepDutyStaffName(staffMap.get(item.getDepDutyStaff()));
                    }
                }
            }
            return RestMessage.doSuccess(pageInfo);
        } catch (Exception ex) {
            LOGGER.info("batch query org info request error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    /**
     * 根据组织ID,查询组织树
     *
     * @param reqParam ID查询请求参数
     * @return 当前组织的上级组织，同一节点下的同级组织，及其下属组织信息
     */
    @ApiOperation(value = "根据组织ID,查询组织树", httpMethod = "POST")
    @PostMapping(value = "/queryOrgTree")
    public RestMessage<OrgTreeDto> queryOrgTree(@RequestBody final QueryOrgTreeReqParam reqParam) {
        LOGGER.info("query org tree request param :{}.", reqParam);
        try {
            queryOrgTreeReqParamValidator.validate(reqParam);
            final OrgTreeDto orgTreeDto = organizationService.queryOrgTree(reqParam);
            LOGGER.info("query org tree by org id response,result:{}, param :{}.", orgTreeDto, reqParam);
            return RestMessage.doSuccess(orgTreeDto);
        } catch (final OrgException oex) {
            LOGGER.info("query org tree by org id request failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (final Exception ex) {
            LOGGER.info("query org tree by org id request error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    /**
     * 根据组织ID,查询组织下的所有员工信息
     *
     * @param reqParam 查询人员请求参数
     * @return 员工信息集合
     */
    @ApiOperation(value = "根据组织ID,查询组织下的所有员工信息", httpMethod = "POST")
    @PostMapping(value = "/queryAllStaffsInOrg")
    public RestMessage<List<StaffInfoDto>> queryAllStaffsInOrg(@RequestBody final QueryAllStaffsInOrgReqParam reqParam) {
        LOGGER.info("query org staffs by org id request param :{}.", reqParam);
        try {
            queryAllStaffsInOrgReqParamValidator.validate(reqParam);
            final List<StaffInfoBean> staffs = staffService.queryStaffsByOrgId(reqParam);
            return wrapStaff(staffs, reqParam);
        } catch (final OrgException oex) {
            LOGGER.info("query org staffs by org id request failed,reqParam:{" + reqParam + "}", oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (final Exception ex) {
            LOGGER.error("query org staffs by org id request error,reqParam:{" + reqParam + "}", ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "多组织树查询", httpMethod = "POST")
    @PostMapping(value = "/multipleOrgTreeQuery")
    public RestMessage<List<OrgTreeDto>> multipleOrgTreeQuery(@RequestBody final MultipleOrgTreeQueryDto reqParam) {
        LOGGER.info("multiple query org tree request param :{}.", reqParam);
        try {
            likeQueryOrgTreeReqParamValidator.validate(reqParam);
            final MultipleOrgTreeDto multipleOrgTree = organizationService.multipleOrgTreeQuery(reqParam);
            LOGGER.debug("org list in db, param :{},orgs:{}.", reqParam, multipleOrgTree);
            if (null == multipleOrgTree) {
                return new RestMessage<>();
            }
            final List<OrgTreeDto> orgTreeDtos = multipleOrgTreeBuilder.buildOrgTree(multipleOrgTree);
            return RestMessage.doSuccess(orgTreeDtos);
        } catch (final OrgException oex) {
            LOGGER.info("multiple query org tree request failed,reqParam:{" + reqParam + "}", oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (final Exception ex) {
            LOGGER.error("multiple query org tree request error,reqParam:{" + reqParam + "}", ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    /**
     * 根据组织ID查询该集团下的业务单元列表
     *
     * @param reqParam 组织列表查询请求参数
     * @return 组织列表
     */
    @ApiOperation(value = "查询组织列表", httpMethod = "POST")
    @PostMapping(value = "/queryOrgList")
    public RestMessage<List<OrgInfoDto>> queryOrgList(@RequestBody final QueryOrgListReqParam reqParam) {
        LOGGER.info("query org list request param :{}.", reqParam);
        try {
            queryOrgListReqParamValidator.validate(reqParam);

            final List<OrgTreeBean> orgList = organizationService.queryOrgList(reqParam);
            LOGGER.debug("org list in db, param :{},orgs:{}.", reqParam, orgList);
            if (CollectionUtils.isEmpty(orgList)) {
                return new RestMessage<>();
            }

            final List<OrgInfoDto> filterList = orgListFilter.filter(orgList, reqParam);
            return RestMessage.doSuccess(filterList);
        } catch (final OrgException oex) {
            LOGGER.info("query org list request failed,reqParam:{" + reqParam + "}", oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (final Exception ex) {
            LOGGER.error("query org list request error,reqParam:{" + reqParam + "}", ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    /**
     * 查询组织权限范围内的业务单元信息
     *
     * @param reqParam 组织权限列表查询请求参数
     * @return 组织简要信息集合
     */
    @ApiOperation(value = "查询组织权限列表", httpMethod = "POST")
    @PostMapping(value = "/queryOrgPermissionList")
    public RestMessage<List<OrgInfoDto>> queryOrgPermissionList(@RequestBody QueryOrgPermissionListReqParam reqParam) {
        LOGGER.info("query org permission list request param :{}.", reqParam);
        try {
            queryOrgPermissionListReqParamValidator.validate(reqParam);

            final List<OrgTreeBean> orgList = organizationService.queryOrgPermissonList(reqParam);
            LOGGER.debug("org list in db, param :{},orgs:{}.", reqParam, orgList);

            final RestMessage<List<OrgInfoDto>> restMessage = new RestMessage<>();
            if (!CollectionUtils.isEmpty(orgList)) {
                List<OrgInfoDto> orgInfoDtos = OrgBeanUtil.listConvert(orgList, new OrgInfoDto());
                restMessage.setData(orgInfoDtos);
            }
            return restMessage;
        } catch (final OrgException oex) {
            LOGGER.info("query org permission list request failed,reqParam:{" + reqParam + "}", oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (final Exception ex) {
            LOGGER.error("query org permission list request error,reqParam:{" + reqParam + "}", ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "根据条件查询组织列表", httpMethod = "POST")
    @PostMapping(value = "/queryOrgListByCondition")
    public RestMessage<PageInfo<OrgInfoDto>> queryOrgListByCondition(@RequestBody QueryOrgListConditionParam reqParam) {
        LOGGER.info("condition query org list request param :{}.", reqParam);
        try {
            final String orgName = reqParam.getOrgName();
            final String orgCode = reqParam.getOrgCode();
            if (StringUtils.isEmpty(orgName) && StringUtils.isEmpty(orgCode) && ObjectUtils.isEmpty(reqParam.getOrgId()) && StringUtils.isBlank(reqParam.getKeyWord())) {
                LOGGER.warn("condition query org list request param 【orgName】= {}. 【orgCode】 = {}", orgName, orgCode);
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.cannot.null"));
            }
            final PageInfo<OrgInfoDto> pageInfo = organizationService.queryOrgListByCondition(reqParam);
            return RestMessage.doSuccess(pageInfo);
        } catch (final OrgException oex) {
            LOGGER.info("condition query org list request failed,reqParam:{" + reqParam + "}", oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (final Exception ex) {
            LOGGER.error("condition query org list request error,reqParam:{" + reqParam + "}", ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    /**
     * 封装组织数据，处理组织职能
     *
     * @param buWithFunc 组织信息（业务单元含组织职能）
     * @param orgId      组织ID
     * @return 组织数据
     */
    private RestMessage<OrganizationDto> wrapOrgDto(final SimpleBizUnitWithFuncBean buWithFunc, final Long orgId) {
        if (buWithFunc == null) {
            LOGGER.info("query org info by id response,result:【is null】, param :{}.", orgId);
            return RestMessage.doSuccess(null);
        }
        final OrganizationDto orgDto = new OrganizationDto();
        BeanUtils.copyProperties(buWithFunc, orgDto);
        return RestMessage.doSuccess(orgDto);
    }

    /**
     * 封装人员信息
     *
     * @param staffs   人员信息数据实体
     * @param reqParam 查询请求参数
     * @return 人员信息传输实体
     */
    private RestMessage<List<StaffInfoDto>> wrapStaff(final List<StaffInfoBean> staffs, final QueryAllStaffsInOrgReqParam reqParam) {
        final RestMessage<List<StaffInfoDto>> restMessage = new RestMessage<>();
        if (!CollectionUtils.isEmpty(staffs)) {
            final List<StaffInfoDto> staffDtos = new ArrayList<>(staffs.size());
            for (final StaffInfoBean staffBean : staffs) {
                final StaffInfoDto staff = new StaffInfoDto();
                BeanUtils.copyProperties(staffBean, staff);
                staffDtos.add(staff);
            }
            restMessage.setData(staffDtos);
        }
        LOGGER.info("query org staffs by org id response ,result:{}, param :{}.", restMessage, reqParam);
        return restMessage;
    }

    @ApiOperation(value = "同步客商业务类型", httpMethod = "POST")
    @PostMapping(value = "/syncCustBizType")
    public RestMessage<Boolean> syncCustBizType(@RequestBody SyncCustBizTypeParam reqParam) {
        LOGGER.info("sync cust biz type param:{}.", reqParam);
        try {
            syncCustBizTypeParamValidator.validate(reqParam);

            organizationService.syncCustBizType(reqParam);

            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (final OrgException oex) {
            LOGGER.info("sync cust biz type failed, param:{" + reqParam + "}", oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("sync cust biz type error, param:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    /**
     * 根据组织id和用户id查询组织信息（有权限验证）
     *
     * @param reqParam
     * @return
     */
    @ApiOperation(value = "根据用户id和组织id查询（权限）查询部门信息", httpMethod = "POST")
    @PostMapping(value = "/queryDepInfoByUserIdAndOrgId")
    public RestMessage<DepInfoDto> queryDepInfoByUserIdAndOrgId(@RequestBody QueryPermissionOrgInfoParam reqParam) {
        LOGGER.info("query org departmentInfo request param :{}.", reqParam);
        try {
            queryPermissionOrgInfoParamValidator.validate(reqParam);
            //调用接口（先权限验证，后查询部门信息接口）
            DepInfoDto depInfoDto = organizationService.queryDepInfoByUserIdAndOrgId(reqParam);
            return RestMessage.doSuccess(depInfoDto);
        } catch (final OrgException oex) {
            LOGGER.info("query DepartmentInfo  request failed,reqParam:{" + reqParam + "}", oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (final Exception ex) {
            LOGGER.error("query DepartmentInfo request error,reqParam:{" + reqParam + "}", ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }
    @ApiOperation(value = "组织批量停用", httpMethod = "POST")
    @PostMapping(value = "/batchEnableOrg")
    public RestMessage<Boolean> batchEnableOrg(@RequestBody BatchEnableOrgParam batchEnableOrgParam) {
        try {
            organizationService.batchEnableOrg(batchEnableOrgParam.getOrgIds(), batchEnableOrgParam.getUserId(), batchEnableOrgParam.getState(), batchEnableOrgParam.getOrgType());
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (Exception e) {
            LOGGER.error("batchEnableOrg", e);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }

    }
    @ApiOperation(value = "根据组织和手机号批量查询", httpMethod = "POST")
    @PostMapping(value = "/batchQueryOrgListByClue")
    public RestMessage<List<BatchQueryOrgByClueParam>> batchQueryOrgListByClue(@RequestBody List<BatchQueryOrgByClueParam> queryOrgByClueParamList) {
        try {
            organizationService.batchQueryOrgListByClue(queryOrgByClueParamList);
            return RestMessage.doSuccess(queryOrgByClueParamList);
        } catch (Exception e) {
            LOGGER.error("batchQueryOrgListByClue", e);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

}
