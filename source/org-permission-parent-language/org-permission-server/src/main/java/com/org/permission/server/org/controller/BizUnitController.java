package com.org.permission.server.org.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.language.util.I18nUtils;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.org.permission.common.dto.crm.CustInfoDomainDto;
import com.org.permission.common.enums.org.FunctionTypeEnum;
import com.org.permission.common.org.dto.*;
import com.org.permission.common.org.param.QueryGroupBUByFuncReqParam;
import com.org.permission.common.org.param.*;
import com.org.permission.common.org.vo.BizUnitWithFuncDetailVo;
import com.org.permission.server.domain.crm.CustDomainService;
import com.org.permission.server.domain.user.UserDomainService;
import com.org.permission.server.domain.wms.OrgWarehouseDto;
import com.org.permission.server.domain.wms.StorageDomainService;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.*;
import com.org.permission.server.org.builder.BizUnitRelationBuilder;
import com.org.permission.server.org.dto.param.*;
import com.org.permission.server.org.enums.OrgTypeEnum;
import com.org.permission.server.org.service.BizUnitService;
import com.org.permission.server.org.service.OrganizationService;
import com.org.permission.server.org.util.OrgBeanUtil;
import com.org.permission.server.org.util.PageUtil;
import com.org.permission.server.org.validator.*;
import com.org.permission.server.org.vo.BUVersionInfoVo;
import com.org.permission.server.org.vo.BizUnitWithFuncInfoVo;
import com.org.permission.server.utils.StringRegular;
import com.common.util.message.RestMessage;
import com.common.util.util.HuToolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 业务单元管理控制器
 */
@Api(tags = "0业务单元管理接口文档")
@RestController
@RequestMapping("unit")
public class BizUnitController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BizUnitController.class);

    @Resource
    private BizUnitAddParamValidator bizUnitAddParamValidator;

    @Resource
    private SaveNewVersionBizUnitRepParamValidator saveNewVersionBizUnitRepParamValidator;

    @Resource
    private QueryByIdReqParamValidator queryByIdReqParamValidator;

    @Resource
    private QueryBizUnitListReqParamValidator queryBizUnitListReqParamValidator;

    @Resource
    private DeleteOperateReqParamValidator deleteOperateReqParamValidator;

    @Resource
    private EnableOperateReqParamValidator enableOperateReqParamValidator;

    @Resource
    private UpdateBizUnitReqParamValidator updateBizUnitReqParamValidator;

    @Resource
    private QueryBizUnitByFunctionReqParamValidator queryBizUnitByFunctionReqParamValidator;

    @Resource
    private QueryCustBUByFuncReqParamValidator queryCustBUByFuncReqParamValidator;

    @Resource
    private BindingCustReqParamValidator bindingCustReqParamValidator;

    @Resource
    private QueryBUWarehouseReqParamValidator queryBUWarehouseReqParamValidator;

    @Resource
    private QueryBizUnitListByFuncParamValidator queryBizUnitListByFuncParamValidator;

    @Resource
    private QueryOrgPermissionListReqParamValidator queryOrgPermissionListReqParamValidator;

    @Resource
    private BizUnitService bizUnitService;

    @Resource
    private StorageDomainService storageDomainService;

    @Resource
    private BizUnitRelationBuilder bizUnitRelationBuilder;

    @Resource
    private UserDomainService userDomainService;

    @Resource
    private CustDomainService custDomainService;

    @Resource
    private OrganizationService organizationService;

    @ApiOperation(value = "新增业务单元", httpMethod = "POST")
    @PostMapping(value = "/addBizUnit")
    public RestMessage<Boolean> addBizUnit(@RequestBody AddBizUnitReqParam reqParam) {
        LOGGER.info("add biz unit req param : {}.", reqParam);
        try {
            bizUnitAddParamValidator.validate(reqParam);

            bizUnitService.addBizUnit(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("add biz unit failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "" + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("add biz unit error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "" + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "存为新版本", httpMethod = "POST")
    @PostMapping(value = "/saveNewVersion")
    public RestMessage<Boolean> saveNewVersion(@RequestBody SaveNewVersionBUParam reqParam) {
        LOGGER.info("save biz unit new version request, param : {}.", reqParam);
        try {
            saveNewVersionBizUnitRepParamValidator.validate(reqParam);

            bizUnitService.saveNewVersion(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("save biz unit new version failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("save biz unit new version error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询业务单元版本列表", httpMethod = "POST")
    @PostMapping(value = "/queryBUVersionList")
    public RestMessage<PageInfo<BUVersionInfoVo>> queryBUVersionList(@RequestBody VersionPageQueryParam reqParam) {
        LOGGER.info("query bu version list info req param : {}.", reqParam);
        try {
            final PageInfo<BUVersionInfoVo> queryPage = bizUnitService.queryBUVersionList(reqParam);
            return RestMessage.doSuccess(queryPage);
        } catch (OrgException oex) {
            LOGGER.info("query bu version list info failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "" + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query bu version list info error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "" + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询业务单元版本详细信息", httpMethod = "POST")
    @PostMapping(value = "/queryBUVersionDetailInfo")
    public RestMessage<BizUnitWithFuncDetailVo> queryBUVersionDetailInfo(@RequestBody QueryByIdReqParam reqParam) {
        LOGGER.info("query bu version detail info req param : {}.", reqParam);
        try {
            queryByIdReqParamValidator.validate(reqParam);

            final BizUnitWithFuncDetailBean buVersionInfo = bizUnitService.queryBUVersionDetailInfo(reqParam);
            if (buVersionInfo != null) {
                BizUnitWithFuncDetailVo vo = new BizUnitWithFuncDetailVo();
                BeanUtils.copyProperties(buVersionInfo, vo);
                return RestMessage.querySuccess(vo);
            }
            return RestMessage.querySuccess(null);
        } catch (OrgException oex) {
            LOGGER.info("query bu version detail info failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "" + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query bu version detail info error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "" + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    public RestMessage<OrgListDto> queryGroupRootBU(@RequestBody QueryByIdReqParam reqParam) {
        LOGGER.info("query group root biz unit by group id , req param : {}.", reqParam);
        try {
            queryByIdReqParamValidator.validate(reqParam);
            final OrgListBean orgListBean = bizUnitService.queryGroupRootBU(reqParam.getId());
            if (orgListBean != null) {
                OrgListDto orgListDto = new OrgListDto();
                BeanUtils.copyProperties(orgListBean, orgListDto);
                return RestMessage.querySuccess(orgListDto);
            }
            return RestMessage.querySuccess(null);
        } catch (OrgException oex) {
            LOGGER.info("query group root biz unit by group id failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "" + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query group root biz unit by group id error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "" + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询组织的根业务单元", httpMethod = "POST")
    @PostMapping(value = "/queryOrgRootBU")
    public RestMessage<OrgListDto> queryOrgRootBU(@RequestBody QueryByIdReqParam reqParam) {
        LOGGER.info("query org root biz unit by group id , req param : {}.", reqParam);
        try {
            queryByIdReqParamValidator.validate(reqParam);

            final OrgListDto orgListDto = bizUnitService.queryOrgRootBU(reqParam);

            return RestMessage.doSuccess(orgListDto);
        } catch (OrgException oex) {
            LOGGER.info("query org root biz unit by org id failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "" + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query org root biz unit by org id error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "" + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    /**
     * 根据客商 ID 查询业务单元
     * 若当前客商绑定的是业务单元，返回当前业务单元信息；
     * 若单钱客商绑定的是集团，返回单钱集团的根业务单元；
     *
     * @param reqParam 查询请求参数
     * @return 业务单元信息
     */
    @ApiOperation(value = "根据客商 ID 查询业务单元", httpMethod = "POST")
    @PostMapping(value = "/queryBUByCustId")
    public RestMessage<OrgListDto> queryBUByCustId(@RequestBody QueryByIdReqParam reqParam) {
        LOGGER.info("query bu by cust id , req param : {}.", reqParam);
        try {
            queryByIdReqParamValidator.validate(reqParam);
            final OrgListBean orgListBean = bizUnitService.queryBUByCustId(reqParam.getId().intValue());
            if (orgListBean != null) {
                OrgListDto orgListDto = new OrgListDto();
                BeanUtils.copyProperties(orgListBean, orgListDto);
                return RestMessage.querySuccess(orgListDto);
            }
            return RestMessage.querySuccess(null);
        } catch (OrgException oex) {
            LOGGER.info("query bu by cust id failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "" + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query bu by cust id error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "" + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "根据集团id查询集团下的业务单元", httpMethod = "POST")
    @PostMapping(value = "/queryBUByGroupId")
    public RestMessage<List<OrgInfoDto>> queryBUByGroupId(@RequestBody QueryByIdReqParam reqParam) {
        LOGGER.info("queryBUByGroupId , req param : {}.", reqParam);
        try {
            queryByIdReqParamValidator.validate(reqParam);
            List<OrgInfoDto> list = bizUnitService.queryBUByGroupId(reqParam);
            Map<Long, String> orgInfoDtoMap = list.stream().collect(Collectors.toMap(key -> key.getId(), value -> value.getOrgName()));
            list.forEach(dto -> {
                dto.setParentOrgName(orgInfoDtoMap.get(dto.getParentId()));
            });
            return RestMessage.doSuccess(list);
        } catch (OrgException oex) {
            LOGGER.info("query bu by cust id failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "" + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query bu by cust id error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "" + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "开票信息", httpMethod = "POST")
    @PostMapping(value = "/invoiceInfo")
    public RestMessage<BUInvoiceInfoDto> invoiceInfo(@RequestBody QueryByIdReqParam reqParam) {
        LOGGER.info("query bu invoice info param : {}.", reqParam);
        try {
            queryByIdReqParamValidator.validate(reqParam);
            final BUInvoiceInfoDto invoiceInfo = bizUnitService.invoiceInfo(reqParam);
            return RestMessage.doSuccess(invoiceInfo);
        } catch (OrgException oex) {
            LOGGER.info("query bu invoice info failed,param:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "" + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query bu invoice info error,param:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "" + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "删除业务单元", httpMethod = "DELETE")
    @PostMapping(value = "/deleteBizUnit")
    public RestMessage<Boolean> deleteBizUnit(@RequestBody KeyOperateParam reqParam) {
        LOGGER.info("delete biz unit request, param : {}.", reqParam);
        try {
            deleteOperateReqParamValidator.validate(reqParam);
            bizUnitService.deleteBizUnit(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("delete biz unit failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "" + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("delete biz unit error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "" + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "更新业务单元", httpMethod = "POST")
    @PostMapping(value = "/updateBizUnit")
    public RestMessage<Boolean> updateBizUnit(@RequestBody UpdateBizUnitReqParam reqParam) {
        LOGGER.info("update biz unit req , param :{}.", reqParam);
        try {
            updateBizUnitReqParamValidator.validate(reqParam);
            bizUnitService.updateBizUnit(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("update biz unit failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "" + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("update biz unit error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "" + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "启停业务单元", httpMethod = "POST")
    @PostMapping(value = "/enableBizUnit")
    public RestMessage<Boolean> enableBizUnit(@RequestBody EnableOperateParam reqParam) {
        LOGGER.info("enable biz unit req param : {}.", reqParam);
        try {
            enableOperateReqParamValidator.validate(reqParam);
            bizUnitService.enableBizUnit(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("enable biz unit failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "" + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("enable biz unit error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "" + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询业务单元", httpMethod = "POST")
    @PostMapping(value = "/queryBizUnitById")
    public RestMessage<BizUnitWithFuncDetailVo> queryBizUnitById(@RequestBody QueryByIdReqParam reqParam) {
        LOGGER.info("query biz unit by id req param : {}.", reqParam);
        try {
            queryByIdReqParamValidator.validate(reqParam);
            if (reqParam.getId() == null || reqParam.getId().longValue() <= 0) {
                throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.common.param.id.cannot.null"));
            }
            final BizUnitWithFuncDetailBean bizUnitWithFuncDetailBean = bizUnitService.queryBizUnitWithFuncById(reqParam);
            LOGGER.info("query biz unit by id response,result:{}, param :{}.", bizUnitWithFuncDetailBean, reqParam);
            if (bizUnitWithFuncDetailBean == null) {
                return RestMessage.doSuccess(null);
            }
            BizUnitWithFuncDetailVo bizUnitDetailVo = new BizUnitWithFuncDetailVo();
            BeanUtils.copyProperties(bizUnitWithFuncDetailBean, bizUnitDetailVo);
            return RestMessage.doSuccess(bizUnitDetailVo);
        } catch (OrgException oex) {
            LOGGER.info("add biz unit failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "" + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query biz unit by id error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "" + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询业务单元", httpMethod = "POST")
    @PostMapping(value = "/queryBizUnitByIds")
    public RestMessage<List<BizUnitWithFuncDetailVo>> queryBizUnitByIds(@RequestBody QueryByIdReqParam reqParam) {
        LOGGER.info("query biz unit by id req param : {}.", reqParam);
        try {
            Assert.notNull(reqParam.getIds(), I18nUtils.getMessage("org.common.param.idarray.cannot.null"));
            final List<BizUnitWithFuncDetailBean> bizUnitWithFuncDetailBeans = bizUnitService.queryBizUnitWithFuncByIds(reqParam);
            if (CollectionUtils.isNotEmpty(bizUnitWithFuncDetailBeans)) {
                List<BizUnitWithFuncDetailVo> list = HuToolUtil.exchange(bizUnitWithFuncDetailBeans, BizUnitWithFuncDetailVo.class);
                return RestMessage.querySuccess(list);
            }
            return RestMessage.querySuccess(new ArrayList<>());
        } catch (OrgException oex) {
            LOGGER.info("add biz unit failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "" + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query biz unit by id error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "" + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "业务单元列表查询(权限)", httpMethod = "POST")
    @PostMapping(value = "/queryBizUnitInfoList")
    public RestMessage<PageInfo<BizUnitWithFuncInfoVo>> queryBizUnitInfoList(@RequestBody QueryBizUnitListReqParam reqParam) {
        LOGGER.info("query biz unit info list req param : {}.", reqParam);
        try {
            queryBizUnitListReqParamValidator.validate(reqParam);
            final PageInfo<BizUnitWithFuncInfoBean> queryPage = bizUnitService.queryBizUnitInfoList(reqParam);
            return wrapQueryListAndReslation(queryPage);
        } catch (OrgException oex) {
            LOGGER.info("query biz unit info list failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query biz unit info list error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception") + ex);
        }
    }

    @ApiOperation(value = "根业务单元列表查询", httpMethod = "POST")
    @PostMapping(value = "/queryRootBUInfoList")
    public RestMessage<PageInfo<OrgInfoDto>> queryRootBUInfoList(@RequestBody RootBUListQueryParam reqParam) {
        LOGGER.info("query root biz unit info list req param : {}.", reqParam);
        try {
            final PageInfo<OrgInfoDto> pageInfo = bizUnitService.queryRootBUInfoList(reqParam);
            return RestMessage.doSuccess(pageInfo);
        } catch (OrgException oex) {
            LOGGER.info("query root biz unit info list failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query root biz unit info list error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询组织职能业务单元列表", httpMethod = "POST")
    @PostMapping(value = "/queryBizUnitListByFunc")
    public RestMessage<PageInfo<OrgConciseInfoDto>> queryBizUnitListByFunc(@RequestBody QueryBizUnitListByFuncParam reqParam) {
        LOGGER.info("query biz unit list by func req param:{}.", reqParam);
        try {
            queryBizUnitListByFuncParamValidator.validate(reqParam);
            final PageInfo<OrgConciseInfoDto> pageInfo = bizUnitService.queryBizUnitListByFunc(reqParam);
            return RestMessage.doSuccess(pageInfo);
        } catch (OrgException oex) {
            LOGGER.info("query biz unit list by func failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query biz unit list by func error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询一个集团下具有某组织职能的组织列表简要信息", httpMethod = "POST")
    @PostMapping(value = "/queryGroupBUByFunc")
    public RestMessage<List<OrgListDto>> queryGroupBUByFunc(@RequestBody QueryGroupBUByFuncReqParam reqParam) {
        LOGGER.info("query group bu by org func param:{}.", reqParam);
        try {
            queryBizUnitByFunctionReqParamValidator.validate(reqParam);
            final List<OrgListBean> orgListBeans = bizUnitService.queryGroupBUByFunc(reqParam);
            if (!CollectionUtils.isEmpty(orgListBeans)) {
                final List<OrgListDto> treeList = bizUnitRelationBuilder.builder(orgListBeans, OrgListDto.class);
                return RestMessage.doSuccess(treeList);
            }
            return RestMessage.doSuccess(Lists.newArrayList());
        } catch (OrgException oex) {
            LOGGER.info("query biz unit by function failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query biz unit by function error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "根据用户id和集团id查询业务单元列表", httpMethod = "POST")
    @PostMapping(value = "/queryBizByGroupIdAndUserId")
    public RestMessage<List<OrgInfoDto>> queryBizByGroupIdAndUserId(@RequestBody QueryBizByGroupIdAndUserIdParam reqParam) {
        LOGGER.info("query group bu by org func param:{}.", reqParam);
        Assert.notNull(reqParam, I18nUtils.getMessage("org.common.param.cannot.null"));
        Assert.notNull(reqParam.getGroupId(), I18nUtils.getMessage("org.common.param.groupid.cannot.null"));
        Assert.notNull(reqParam.getUserId(), I18nUtils.getMessage("org.common.param.userid.cannot.null"));

        try {
            final List<OrgInfoDto> orgListBeans = bizUnitService.queryBizByGroupIdAndUserId(reqParam);
            return RestMessage.doSuccess(orgListBeans);
        } catch (Exception e) {
            LOGGER.error("query biz unit by groupId and userId error,reqParam:" + reqParam, e);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", e.toString());
        }

    }

    @ApiOperation(value = "查询一个客户下具有某组织职能的组织列表简要信息", httpMethod = "POST")
    @PostMapping(value = "/queryCustBUByFunc")
    public RestMessage<List<OrgListDto>> queryCustBUByFunc(@RequestBody final QueryCustBUByFuncReqParam reqParam) {
        LOGGER.info("query cust biz unit by function req ,param:{}.", reqParam);
        try {
            queryCustBUByFuncReqParamValidator.validate(reqParam);

            //先调用客商id出关联了全局客商的业务单元id，然后根据业务单元id所在集团，最后查出集团下的所有的业务单元
            CustInfoDomainDto custInfo = custDomainService.getCustInfoById(reqParam.getCustId());
            LOGGER.info("通过custId获取到的全局客户信息：{}", JSON.toJSONString(custInfo));
            Long unitOrg = custInfo.getUnitOrg();

            if (!ObjectUtils.isEmpty(unitOrg) && unitOrg.longValue() > 0) {
                SimpleBizUnitWithFuncBean orgInfo = organizationService.queryOrgById(unitOrg);
                LOGGER.info("通过unitOrg获取到的组织信息：{}", JSON.toJSONString(orgInfo));
                if (!ObjectUtils.isEmpty(orgInfo)) {
                    Long groupId = orgInfo.getGroupId();
                    if (!ObjectUtils.isEmpty(groupId)) {
                        reqParam.setGroupId(groupId);
                        final List<OrgListBean> orgList = bizUnitService.queryCustBUByFunc(reqParam);
                        LOGGER.info("通过groupId和职能id获取到集团下所有的业务单元信息：{}", orgList.toString());
                        if (CollectionUtils.isEmpty(orgList)) {
                            return RestMessage.doSuccess(Lists.newArrayList());
                        }
                        final List<OrgListDto> orgListDtos = bizUnitRelationBuilder.builder(orgList, OrgListDto.class);
                        return RestMessage.doSuccess(orgListDtos);
                    }
                }
            }
        } catch (OrgException oex) {
            LOGGER.info("query biz unit by function failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query biz unit by function error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
        return RestMessage.doSuccess(Lists.newArrayList());
    }

    @ApiOperation(value = "查询业务单元仓库", httpMethod = "POST")
    @PostMapping(value = "/queryBUWarehouse")
    public RestMessage<List<OrgWarehouseDto>> queryBUWarehouse(@RequestBody BUWarehouseReqParam reqParam) {
        LOGGER.info("query biz unit warehouseId req ,param:{}.", reqParam);
        try {
            queryBUWarehouseReqParamValidator.validate(reqParam);

            final List<OrgWarehouseDto> orgWarehouses = storageDomainService.queryStockOrgWarehouses(reqParam.getBuId());
            return RestMessage.doSuccess(orgWarehouses);
        } catch (OrgException oex) {
            LOGGER.info("query biz unit by function failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query biz unit by function error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "绑定内部客商", httpMethod = "POST")
    @PostMapping(value = "/bindingInnerCust")
    public RestMessage<Boolean> bindingInnerCust(@RequestBody BindingCustReqParam reqParam) {
        LOGGER.info("绑定内部客商请求参数:{}.", reqParam);
        try {
            bindingCustReqParamValidator.validate(reqParam);

            bizUnitService.bindingInnerCust(reqParam);

            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("绑定内部客商请求失败,参数:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("绑定内部客商请求错误,参数:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "绑定客商", httpMethod = "POST")
    @PostMapping(value = "/bindingCust")
    public RestMessage<Boolean> bindingCust(BindingCustReqParam reqParam) {
        LOGGER.info("绑定客商请求参数:{}.", reqParam);
        try {
            bindingCustReqParamValidator.validate(reqParam);

            bizUnitService.bindingCust(reqParam);

            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("绑定客商请求失败,参数:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("绑定客商请求错误,参数:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询业务单元权限列表", httpMethod = "POST")
    @PostMapping(value = "/queryBUPermissionList")
    public RestMessage<List<OrgInfoDto>> queryBUPermissionList(@RequestBody QueryOrgPermissionListReqParam reqParam) {
        LOGGER.info("query user org permission list param :{}.", reqParam);
        try {
            queryOrgPermissionListReqParamValidator.validate(reqParam);
            final List<OrgTreeBean> orgList = bizUnitService.queryBUPermissonList(reqParam);
            LOGGER.debug("org list in db, param :{},orgs:{}.", reqParam, orgList);
            return wrapOrgInfoDto(orgList);
        } catch (final OrgException oex) {
            LOGGER.info("query user org permission list failed,reqParam:{" + reqParam + "}", oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (final Exception ex) {
            LOGGER.error("query user org permission list error,reqParam:{" + reqParam + "}", ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询一个集团下未绑定客商的组织列表简要信息", httpMethod = "POST")
    @PostMapping(value = "/queryGroupUnbindingCustBUs")
    public RestMessage<List<OrgInfoDto>> queryGroupUnbindingCustBUs(@RequestBody QueryBizUnitListReqParam reqParam) {
        LOGGER.info("query group unbinding cust bus, param :{}.", reqParam);
        try {
            queryBizUnitListReqParamValidator.validate(reqParam);

            final List<OrgListBean> orgList = bizUnitService.queryGroupUnbindingCustBUs(reqParam);

            RestMessage<List<OrgInfoDto>> restMessage = new RestMessage<>();
            if (!CollectionUtils.isEmpty(orgList)) {
                final List<OrgInfoDto> orgInfoDtos = OrgBeanUtil.listConvert(orgList, new OrgInfoDto());
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

    @ApiOperation(value = "根据集团id和经纬度查询业务单元信息", httpMethod = "POST")
    @PostMapping(value = "queryBizByGroupIdAndAddress")
    public RestMessage<List<BizUnitWithFuncDetailVo>> queryBizByGroupIdAndAddress(@RequestBody QueryBizByGroupIdAndAddressParam param) {
        Assert.notNull(param.getGroupId(), I18nUtils.getMessage("org.common.param.groupid.cannot.null"));
        Assert.notNull(param.getStartLatitude(), I18nUtils.getMessage("org.bizunit.startlatitude.null"));
        Assert.notNull(param.getEndLatitude(), I18nUtils.getMessage("org.bizunit.endlatitude.null"));
        Assert.notNull(param.getStartLongitude(), I18nUtils.getMessage("org.bizunit.startlongitude.null"));
        Assert.notNull(param.getEndLongitude(), I18nUtils.getMessage("org.bizunit.endlongitude.null"));

        List<BizUnitWithFuncDetailBean> bizUnitWithFuncDetailBeans = bizUnitService.queryBizByGroupIdAndAddress(param);
        List<BizUnitWithFuncDetailVo> result = new ArrayList<>();
        if (bizUnitWithFuncDetailBeans.size() > 0) {
            for (int i = 0; i < bizUnitWithFuncDetailBeans.size(); i++) {
                BizUnitWithFuncDetailBean bizUnitWithFuncDetailBean = bizUnitWithFuncDetailBeans.get(i);
                BizUnitWithFuncDetailVo bizUnitWithFuncDetailVo = new BizUnitWithFuncDetailVo();
                BeanUtils.copyProperties(bizUnitWithFuncDetailBean, bizUnitWithFuncDetailVo);
                result.add(bizUnitWithFuncDetailVo);
            }
        }
        return RestMessage.doSuccess(result);
    }


    @ApiOperation(value = "查找集团下的网点代码", httpMethod = "GET")
    @GetMapping(value = "queryLogisticsFunctionCode")
    public RestMessage<Boolean> queryLogisticsFunctionCode(@RequestParam(value = "groupId") Long groupId, @RequestParam(value = "logisticsFunctionCode") String logisticsFunctionCode) {
        //校验网点代码
        boolean match = StringRegular.logisticsFuncCodeValid(logisticsFunctionCode);
        if (!match) {
            LOGGER.error("网点代码不合法code:{}", logisticsFunctionCode);
            return RestMessage.doSuccess(match);
        }
        QueryGroupBUByFuncReqParam req = new QueryGroupBUByFuncReqParam();
        req.setGroupId(groupId);
        req.setOrgType(OrgTypeEnum.ORGANIZATION.getIndex());
        req.setFunctionType(FunctionTypeEnum.LOGISTICS.getIndex());
        req.setLogisticsFunctionCode(logisticsFunctionCode);
        List<OrgListBean> orgListBeans = bizUnitService.queryGroupBUByFunc(req);
        if (CollectionUtils.isEmpty(orgListBeans)) {
            return RestMessage.querySuccess(true);
        } else {
            return RestMessage.querySuccess(false);
        }
    }

    @ApiOperation(value = "查询一个集团下具有某组织职能的组织列表", httpMethod = "POST")
    @PostMapping(value = "/queryGroupBUByFuncList")
    public RestMessage<List<OrgInfoDto>> queryGroupBUByFuncList(String queryGroupBUByFuncReqParam) {
       QueryGroupBUByFuncReqParam reqParam = new QueryGroupBUByFuncReqParam();
        if (!StringUtils.isEmpty(queryGroupBUByFuncReqParam)) {
            JSONObject jsonObject = JSONObject.parseObject(queryGroupBUByFuncReqParam);
            reqParam.setGroupId(jsonObject.getLong("groupId"));
            reqParam.setFunctionType(jsonObject.getInteger("functionType"));
        }
        List<OrgListBean> orgListBeans = bizUnitService.queryGroupBUByFuncList(reqParam);
        List<OrgInfoDto> orgInfoDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(orgListBeans)) {
            orgListBeans.forEach(orgListBean -> {
                OrgInfoDto orgInfoDto = new OrgInfoDto();
                orgInfoDto.setId(orgListBean.getId());
                orgInfoDto.setOrgName(orgListBean.getOrgName());
                orgInfoDto.setOrgCode(orgListBean.getOrgCode());
                orgInfoDto.setParentId(orgListBean.getParentId());
                orgInfoDtos.add(orgInfoDto);
            });
        }
        return RestMessage.querySuccess(orgInfoDtos);
    }

    /**
     * 封装分页查询结果(维护父子关系)
     *
     * @param queryPage 分页查询结果
     * @return 分页查询结果封装结果
     */
    private RestMessage<PageInfo<BizUnitWithFuncInfoVo>> wrapQueryListAndReslation(final PageInfo<BizUnitWithFuncInfoBean> queryPage) {
        final List<BizUnitWithFuncInfoBean> bizUnitWithFuncDetailBeans = queryPage.getList();
        userDomainService.batchFillUserName4Bean(bizUnitWithFuncDetailBeans);
        PageInfo<BizUnitWithFuncInfoVo> pageInfo = PageUtil.convert(queryPage, item -> {
            BizUnitWithFuncInfoVo vo = new BizUnitWithFuncInfoVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        });
        return RestMessage.doSuccess(pageInfo);
    }

    private RestMessage<List<OrgInfoDto>> wrapOrgInfoDto(final List<OrgTreeBean> orgList) {
        if (CollectionUtils.isEmpty(orgList)) {
            return RestMessage.doSuccess(new ArrayList<>());
        }
        List<OrgInfoDto> orgInfoDtos = new ArrayList<>(orgList.size());
        for (OrgTreeBean orgTreeBean : orgList) {
            OrgInfoDto orgInfoDto = new OrgInfoDto();
            BeanUtils.copyProperties(orgTreeBean, orgInfoDto);
            orgInfoDtos.add(orgInfoDto);
        }
        return RestMessage.doSuccess(orgInfoDtos);
    }
}
