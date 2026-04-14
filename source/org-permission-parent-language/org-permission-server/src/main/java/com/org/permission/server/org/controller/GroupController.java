package com.org.permission.server.org.controller;

import com.alibaba.fastjson.JSON;
import com.boss.crm.common.dto.customer.CustSaicRawDto;
import com.common.framework.user.FplUserUtil;
import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.enums.org.FunctionTypeEnum;
import com.org.permission.common.org.dto.*;
import com.org.permission.common.org.param.*;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.server.domain.base.DicDomainService;
import com.org.permission.server.domain.crm.CustDomainService;
import com.org.permission.server.domain.user.UserDomainService;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.GroupInfoBean;
import com.org.permission.server.org.bean.OrgListBean;
import com.org.permission.server.org.dto.ManageGroupLogo;
import com.org.permission.server.org.dto.param.BindingCustReqParam;
import com.org.permission.server.org.dto.param.EnableGroupParam;
import com.org.permission.server.org.dto.param.GroupReqParam;
import com.org.permission.server.org.dto.param.QueryGroupIncludeBusinessTypeParam;
import com.org.permission.server.org.enums.BusinessTypeEnum;
import com.org.permission.server.org.service.GroupService;
import com.org.permission.server.org.util.PageUtil;
import com.org.permission.server.org.validator.*;
import com.org.permission.server.org.vo.OrgBizTypeVo;
import com.org.permission.server.utils.NumericUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

/**
 * 集团管理控制器
 */
@RestController
@Api(tags = "0集团管理接口文档")
@RequestMapping("group")
public class GroupController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupController.class);

    @Resource
    private AddGroupRepParamValidator addGroupRepParamValidator;

    @Resource
    private CustGenerateGroupParamValidator custGenerateGroupParamValidator;

    @Resource
    private EnableOperateReqParamValidator enableOperateReqParamValidator;

    @Resource
    private DeleteOperateReqParamValidator deleteOperateReqParamValidator;

    @Resource
    private QueryGroupListReqParamValidator queryGroupReqParamValidator;

    @Resource
    private QueryByIdReqParamValidator queryByIdReqParamValidator;

    @Resource
    private UpdateGroupReqParamValidator updateGroupReqParamValidator;

    @Resource
    private GroupLogoManageReqParamValidator groupLogoManageReqParamValidator;

    @Resource
    private BindingCustReqParamValidator bindingCustReqParamValidator;

    @Resource
    private BatchQueryParamValidator batchQueryParamValidator;

    @Resource
    private AddGroupValidator addGroupValidator;

    @Resource
    private GroupService groupService;

    @Resource
    private CustDomainService custDomainService;

    @Resource
    private DicDomainService dicDomainService;

    @Resource
    private UserDomainService userDomainService;

    /**
     * 新增集团
     *
     * @param reqParam 新增集团请求参数
     * @return <code>true</code> if create success
     */
    @ApiOperation(value = "新增集团", httpMethod = "POST")
    @PostMapping(value = "/addGroup")
    public RestMessage<Boolean> addGroup(@RequestBody GroupReqParam reqParam) {
        LOGGER.info("add group request, param:{}.", reqParam);
        try {
            addGroupRepParamValidator.validate(reqParam);
            //加上名字不能重复的校验
            addGroupValidator.validate(reqParam.getOrgName());
            groupService.createGroup(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (final OrgException oex) {
            LOGGER.info("add group request failed,reqParam:{" + reqParam + "}", oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("create group error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "客商生成集团", httpMethod = "POST")
    @PostMapping(value = "/custGenerateGroup")
    public RestMessage<OrgUser> custGenerateGroup(@RequestBody CustGenerateGroupParam reqParam) {
        LOGGER.info("客商生成集团请求参数:{}.", reqParam);
        try {
            custGenerateGroupParamValidator.validate(reqParam);
            //集团名字不能重复的校验
            addGroupValidator.validate(reqParam.getGroupName());
            final OrgUser orgUser = groupService.custGenerateGroup(reqParam);

            return RestMessage.doSuccess(orgUser);
        } catch (final OrgException oex) {
            LOGGER.info("客商生成集团请求失败,参数:{" + reqParam + "}", oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("客商生成集团请求错误,参数:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception") + ex.getMessage());
        }
    }

    @ApiOperation(value = "生成集团后，客户再由非工商改成已工商时，增加法人职能", httpMethod = "POST")
    @PostMapping(value = "/addCorpFunction")
    public RestMessage addCorpFunction(@RequestBody CustSaicRawDto custSaicRawDto) {
        groupService.addCorpFunction(custSaicRawDto, custSaicRawDto.getGroupId(), custSaicRawDto.getRootUnitOrg());
        return RestMessage.doSuccess(null);
    }

    @ApiOperation(value = "查询集团业务类型", httpMethod = "POST")
    @PostMapping(value = "/queryGroupBizType")
    public RestMessage<OrgBizTypeVo> queryGroupBizType(@RequestBody QueryByIdReqParam reqParam) {
        LOGGER.info("query group biz type req param:{}.", reqParam);
        try {
            final String businessType = groupService.queryGroupBusinessType(reqParam.getId());
            final OrgBizTypeVo orgBizTypeVo = buildGroupBizTypeVo(businessType);
            LOGGER.info("query group biz type response param:{},result:{}.", reqParam, orgBizTypeVo);

            return RestMessage.doSuccess(orgBizTypeVo);
        } catch (final OrgException oex) {
            LOGGER.info("query group biz type failed,param:{" + reqParam + "}", oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query group biz type error,param:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "绑定客商", httpMethod = "POST")
    @PostMapping(value = "/groupBindingCust")
    public RestMessage<Boolean> groupBindingCust(@RequestBody BindingCustReqParam reqParam) {
        LOGGER.info("集团绑定客商请求参数:{}.", reqParam);
        try {
            bindingCustReqParamValidator.validate(reqParam);

            groupService.bindingCust(reqParam);

            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (final OrgException oex) {
            LOGGER.info("集团绑定客商请求失败,参数:{" + reqParam + "}", oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("集团绑定客商请求错误,参数:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "删除集团", httpMethod = "POST")
    @PostMapping(value = "/deleteGroup")
    public RestMessage<Boolean> deleteGroup(@RequestBody KeyOperateParam reqParam) {
        LOGGER.info("delete group request, param : {}.", reqParam);
        try {
            deleteOperateReqParamValidator.validate(reqParam);

            groupService.deleteGroup(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (final OrgException oex) {
            LOGGER.info("delete group request failed,reqParam:{" + reqParam + "}", oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("delete group error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "修改集团", httpMethod = "POST")
    @PostMapping(value = "/updateGroup")
    public RestMessage<Boolean> updateGroup(@RequestBody GroupReqParam reqParam) {
        LOGGER.info("update group request, param : {}.", reqParam);
        try {
            updateGroupReqParamValidator.validate(reqParam);

            groupService.updateGroup(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (final OrgException oex) {
            LOGGER.info("update group request failed,reqParam:{" + reqParam + "}", oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("update group error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "启停集团", httpMethod = "POST")
    @PostMapping(value = "/enableGroup")
    public RestMessage<OrgUser> enableGroup(@RequestBody EnableGroupParam reqParam) {
        LOGGER.info("enable group request, param : {}.", reqParam);
        try {
            enableOperateReqParamValidator.validate(reqParam);
            reqParam.setUserId(FplUserUtil.getUserId());
            reqParam.setUserName(FplUserUtil.getUserName());
            final OrgUser orgUser = groupService.enableGroup(reqParam);
            return RestMessage.doSuccess(orgUser);
        } catch (final OrgException oex) {
            LOGGER.info("enable group request error,reqParam:{" + reqParam + "}", oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("enable group error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation("集团LOGO上传")
    @PostMapping(value = "/uploadGroupLogo")
    public RestMessage<String> uploadGroupLogo(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = "";//TODO 上传图片
            return RestMessage.doSuccess(fileUrl);
        } catch (Exception ex) {
            LOGGER.error("upload group logo error,reqParam:" + file.getName(), ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "集团LOGO管理", httpMethod = "POST")
    @PostMapping(value = "/groupLogo")
    public RestMessage<Boolean> groupLogo(@RequestBody ManageGroupLogo reqParam) {
        LOGGER.info("group logo manage request, param : {}.", reqParam);
        try {
            groupLogoManageReqParamValidator.validate(reqParam);

            groupService.groupLogo(reqParam);
            return RestMessage.doSuccess(Boolean.TRUE);
        } catch (OrgException oex) {
            LOGGER.info("group logo manage failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("group logo manage error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }


    @ApiOperation(value = "根据ID查询集团信息", httpMethod = "POST")
    @PostMapping(value = "/queryGroupById")
    public RestMessage<GroupDetailDto> queryGroupById(@RequestBody QueryByIdReqParam reqParam) {
        LOGGER.info("query group by id request, param : {}.", reqParam);
        try {
            queryByIdReqParamValidator.validate(reqParam);
            final GroupInfoBean groupInfoBean = groupService.queryGroupById(reqParam);
            if (groupInfoBean == null) {
                return RestMessage.doSuccess(null);
            }
            GroupDetailDto groupDetailDto = new GroupDetailDto();
            BeanUtils.copyProperties(groupInfoBean, groupDetailDto);
            fillName(groupDetailDto);
            return RestMessage.doSuccess(groupDetailDto);
        } catch (OrgException oex) {
            LOGGER.info("query group by id failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query group by id error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询集团组织职能", httpMethod = "POST")
    @PostMapping(value = "/queryGroupOrgFuctions")
    public RestMessage<List<String>> queryGroupOrgFuctions(@RequestBody final QueryByIdReqParam reqParam) {
        LOGGER.info("query group org function request param:{}.", reqParam);
        try {
            final String businessType = groupService.queryGroupBusinessType(reqParam.getId());
            return wrapGroupOrgFunction(businessType);
        } catch (OrgException oex) {
            LOGGER.info("query enable crm list failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query enable crm list error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询集团列表", httpMethod = "POST")
    @PostMapping(value = "/queryGroupList")
    public RestMessage<List<OrgListDto>> queryGroupList(@RequestBody final QueryOrgListInfoReqParam reqParam) {
        LOGGER.info("query group list info request param :{}.", reqParam);
        try {
            final List<OrgListBean> orgList = groupService.queryGroupList(reqParam);
            return wrapOrgList(orgList);
        } catch (OrgException oex) {
            LOGGER.info("query group list info failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query group list info error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }


    /**
     * 首页特定业务类型特定时间集团入驻统计
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "首页集团入驻排行榜", httpMethod = "GET")
    @GetMapping(value = "/queryGroupIncludeBusinessType")
    public RestMessage<QueryGroupIncludeBusinessTypeDto> queryGroupIncludeBusinessType(@RequestParam("userId") Integer userId) {

        QueryGroupIncludeBusinessTypeDto queryGroupIncludeBusinessTypeDto = new QueryGroupIncludeBusinessTypeDto();
        //品牌渠道商入驻日统计
        //当前时间为结束时间
        QueryGroupIncludeBusinessTypeParam reqParamDayBrand = new QueryGroupIncludeBusinessTypeParam();

        //当日0点为开始时间
        reqParamDayBrand.setStartTime(getTimesmorning());
        reqParamDayBrand.setEndTime(new Date());
        //日排行数量
        List<String> dayCount = groupService.queryGroupIncludeBusinessType(reqParamDayBrand);
        QueryGroupIncludeBusinessTypeDto dayDto = getCount(dayCount);
        if (dayDto != null) {
            queryGroupIncludeBusinessTypeDto.setBrandChannelDayCount(dayDto.getBrandChannelDayCount());
            queryGroupIncludeBusinessTypeDto.setWarehouseDayCount(dayDto.getWarehouseDayCount());
            queryGroupIncludeBusinessTypeDto.setTransportDayCount(dayDto.getTransportDayCount());
        }
        //周排行数量
        QueryGroupIncludeBusinessTypeParam reqParamWeekBrand = new QueryGroupIncludeBusinessTypeParam();
        reqParamWeekBrand.setStartTime(getTimesWeekmorning());
        reqParamWeekBrand.setEndTime(new Date());
        List<String> weekCount = groupService.queryGroupIncludeBusinessType(reqParamWeekBrand);
        QueryGroupIncludeBusinessTypeDto weekDto = getCount(weekCount);
        if (weekDto != null) {
            queryGroupIncludeBusinessTypeDto.setBrandChannelWeekCount(weekDto.getBrandChannelDayCount());
            queryGroupIncludeBusinessTypeDto.setWarehouseWeekCount(weekDto.getWarehouseDayCount());
            queryGroupIncludeBusinessTypeDto.setTransportWeekCount(weekDto.getTransportDayCount());
        }


        //月排行数量
        //当前月1号0点为开始时间
        QueryGroupIncludeBusinessTypeParam reqParamMonthBrand = new QueryGroupIncludeBusinessTypeParam();
        reqParamMonthBrand.setStartTime(getTimesMonthmorning());
        reqParamMonthBrand.setEndTime(new Date());

        List<String> monthCount = groupService.queryGroupIncludeBusinessType(reqParamMonthBrand);
        QueryGroupIncludeBusinessTypeDto monthDto = getCount(monthCount);

        if (monthDto != null) {
            queryGroupIncludeBusinessTypeDto.setBrandChannelMonthCount(monthDto.getBrandChannelDayCount());
            queryGroupIncludeBusinessTypeDto.setWarehouseMonthCount(monthDto.getWarehouseDayCount());
            queryGroupIncludeBusinessTypeDto.setTransportMonthCount(monthDto.getTransportDayCount());
        }
        //总入驻量
        QueryGroupIncludeBusinessTypeParam totalPara = new QueryGroupIncludeBusinessTypeParam();
        List<String> totalcount = groupService.queryGroupIncludeBusinessType(totalPara);
        QueryGroupIncludeBusinessTypeDto totalDto = getCount(totalcount);
        if (totalDto != null) {
            queryGroupIncludeBusinessTypeDto.setTotalBrandChannel(totalDto.getBrandChannelDayCount());
            queryGroupIncludeBusinessTypeDto.setTotalWarehouseCount(totalDto.getWarehouseDayCount());
            queryGroupIncludeBusinessTypeDto.setTotalTransportCount(totalDto.getTransportDayCount());
        }
        return RestMessage.doSuccess(queryGroupIncludeBusinessTypeDto);

    }

    /**
     * 封装组织列表的简要信息
     *
     * @param orgList 组织列表
     * @return 封装结果
     */
    private static RestMessage<List<OrgListDto>> wrapOrgList(final List<OrgListBean> orgList) {
        if (CollectionUtils.isEmpty(orgList)) {
            return new RestMessage<>();
        }
        List<OrgListDto> orgListDtos = new ArrayList<>(orgList.size());
        for (OrgListBean orgListBean : orgList) {
            OrgListDto orgListDto = new OrgListDto();
            BeanUtils.copyProperties(orgListBean, orgListDto);
            orgListDtos.add(orgListDto);
        }
        return RestMessage.doSuccess(orgListDtos);
    }

    @ApiOperation(value = "查询集团信息列表（分页）", httpMethod = "POST")
    @PostMapping(value = "/queryGroupInfoList")
    public RestMessage<PageInfo<GroupDetailDto>> queryGroupInfoList(@RequestBody QueryGroupListReqParam reqParam) {
        LOGGER.info("query group info list request, param : {}.", reqParam);
        try {
            queryGroupReqParamValidator.validate(reqParam);

            final PageInfo<GroupInfoBean> groupPage = groupService.queryGroupInfoList(reqParam);

            return wrapPageQueryList(groupPage);
        } catch (OrgException oex) {
            LOGGER.info("query group info list failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query group info list error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "集团单元列表查询（权限）", httpMethod = "POST")
    @PostMapping(value = "/queryAllGroupInfoList")
    public RestMessage<PageInfo<OrgInfoDto>> queryAllGroupInfoList(@RequestBody GroupListQueryParam reqParam) {
        LOGGER.info("query root biz unit info list req param : {}.", reqParam);
        try {
            final PageInfo<OrgInfoDto> pageInfo = groupService.queryAllGroupInfoList(reqParam);
            return RestMessage.doSuccess(pageInfo);
        } catch (OrgException oex) {
            LOGGER.info("query root biz unit info list failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query root biz unit info list error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "查询未关联客商的集团", httpMethod = "POST")
    @PostMapping(value = "/queryGroupsNotRelCust")
    public RestMessage<PageInfo<OrgInfoDto>> queryGroupsNotRelCust(@RequestBody GroupListQueryParam reqParam) {
        LOGGER.info("query root biz unit info list req param : {}.", reqParam);
        try {
            final PageInfo<OrgInfoDto> pageInfo = groupService.queryGroupsNotRelCust(reqParam);
            return RestMessage.doSuccess(pageInfo);
        } catch (OrgException oex) {
            LOGGER.info("query root biz unit info list failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query root biz unit info list error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "根据业务单元ID集合查询对应的集团信息（权限）", httpMethod = "POST")
    @PostMapping(value = "/queryGroupInfoByOrgIds")
    public RestMessage<PageInfo<OrgInfoDto>> queryGroupInfoByOrgIds(@RequestBody BatchQueryParam reqParam) {
        LOGGER.info("query group info by org id req param : {}.", JSON.toJSONString(reqParam));
        try {
            batchQueryParamValidator.validate(reqParam);
            final PageInfo<OrgInfoDto> pageInfo = groupService.queryGroupInfoByOrgIds(reqParam);
            return RestMessage.doSuccess(pageInfo);
        } catch (OrgException oex) {
            LOGGER.info("query group info by org id failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query group info by org id error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    @ApiOperation(value = "根据组织id集合批量查询集团信息")
    @PostMapping(value = "/queryGroupInfoByOrgIdsNoPage")
    public RestMessage<List<OrgInfoDto>> queryGroupInfoByOrgIdsNoPage(@RequestBody BatchQueryParam reqParam) {
        LOGGER.info("query group info by org id req param : {}.", reqParam);
        try {
            batchQueryParamValidator.validate(reqParam);
            final List<OrgInfoDto> list = groupService.queryGroupInfoByOrgIdsNoPage(reqParam);
            return RestMessage.doSuccess(list);
        } catch (OrgException oex) {
            LOGGER.info("query group info by org id failed,reqParam:" + reqParam, oex);
            return RestMessage.error(oex.getErrorCode() + "", oex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("query group info by org id error,reqParam:" + reqParam, ex);
            return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE + "", I18nUtils.getMessage("org.common.system.exception"));
        }
    }

    /**
     * 封装集团列表展示数据
     *
     * @param groupPage 集团分页查询结果
     * @return 封装结果
     */
    private RestMessage<PageInfo<GroupDetailDto>> wrapPageQueryList(final PageInfo<GroupInfoBean> groupPage) {
        PageInfo<GroupDetailDto> pageInfo = PageUtil.parallelConvert(groupPage, item -> {
            GroupDetailDto groupDetailDto = new GroupDetailDto();
            BeanUtils.copyProperties(item, groupDetailDto);
            if (groupDetailDto.getAddressDetail() == null) {
                BaseAddressDto baseAddressDto = new BaseAddressDto();
                baseAddressDto.setInvoiceProvCode(0L);
                baseAddressDto.setInvoiceCityCode(0L);
                baseAddressDto.setInvoiceDistrictCode(0L);
                baseAddressDto.setInvoiceStreetCode(0L);
                groupDetailDto.setAddressDetail(baseAddressDto);
            }
            return groupDetailDto;
        });
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            custDomainService.fillCustName(pageInfo.getList());
            userDomainService.batchFillUserName(pageInfo.getList());
        }
        return RestMessage.doSuccess(pageInfo);
    }

    /**
     * 封装组织职能
     *
     * @param businessType 业务类型
     * @return 组织职能
     */
    private RestMessage<List<String>> wrapGroupOrgFunction(final String businessType) {
        if (StringUtils.isEmpty(businessType)) {
            return RestMessage.doSuccess(new ArrayList<>());
        }
        List<String> orgFuctions = new ArrayList<>();

        if (BusinessTypeEnum.hasCorporation(businessType)) {
            orgFuctions.add(FunctionTypeEnum.CORPORATION.getName());
        }
        if (BusinessTypeEnum.hasFinance(businessType)) {
            orgFuctions.add(FunctionTypeEnum.FINANCE.getName());
        }
        if (BusinessTypeEnum.hasPurchase(businessType)) {
            orgFuctions.add(FunctionTypeEnum.PURCHASE.getName());
        }
        if (BusinessTypeEnum.hasSale(businessType)) {
            orgFuctions.add(FunctionTypeEnum.SALE.getName());
        }

        if (BusinessTypeEnum.hasStorage(businessType)) {
            orgFuctions.add(FunctionTypeEnum.STORAGE.getName());
        }

        if (BusinessTypeEnum.hasLogistics(businessType)) {
            orgFuctions.add(FunctionTypeEnum.LOGISTICS.getName());
        }

        if (BusinessTypeEnum.hasPlatform(businessType)) {
            orgFuctions.add(FunctionTypeEnum.PLATFORM.getName());
        }

        if (BusinessTypeEnum.hasLabour(businessType)) {
            orgFuctions.add(FunctionTypeEnum.LABOUR_SERVICE.getName());
        }

        if (BusinessTypeEnum.hasBanking(businessType)) {
            orgFuctions.add(FunctionTypeEnum.BANKING.getName());
        }
        return RestMessage.doSuccess(orgFuctions);
    }

    /**
     * 构建集团业务类型
     *
     * @param businessType 业务类型
     * @return 集团业务类型实体
     */
    private OrgBizTypeVo buildGroupBizTypeVo(String businessType) {
        OrgBizTypeVo orgBizTypeVo = new OrgBizTypeVo();
        if (!StringUtils.isEmpty(businessType)) {
            orgBizTypeVo.setBizType(businessType);
            List<Integer> simpleFunctions = new ArrayList<>();
            if (BusinessTypeEnum.hasCorporation(businessType)) {
                simpleFunctions.add(FunctionTypeEnum.CORPORATION.getIndex());
            }
            if (BusinessTypeEnum.hasFinance(businessType)) {
                simpleFunctions.add(FunctionTypeEnum.FINANCE.getIndex());
            }
            if (BusinessTypeEnum.hasPurchase(businessType)) {
                simpleFunctions.add(FunctionTypeEnum.PURCHASE.getIndex());
            }
            if (BusinessTypeEnum.hasSale(businessType)) {
                simpleFunctions.add(FunctionTypeEnum.SALE.getIndex());
            }

            if (BusinessTypeEnum.hasStorage(businessType)) {
                simpleFunctions.add(FunctionTypeEnum.STORAGE.getIndex());
            }

            if (BusinessTypeEnum.hasLogistics(businessType)) {
                simpleFunctions.add(FunctionTypeEnum.LOGISTICS.getIndex());
            }

            if (BusinessTypeEnum.hasPlatform(businessType)) {
                simpleFunctions.add(FunctionTypeEnum.PLATFORM.getIndex());
            }

            if (BusinessTypeEnum.hasLabour(businessType)) {
                simpleFunctions.add(FunctionTypeEnum.LABOUR_SERVICE.getIndex());
            }

            if (BusinessTypeEnum.hasBanking(businessType)) {
                simpleFunctions.add(FunctionTypeEnum.BANKING.getIndex());
            }
            orgBizTypeVo.setSimpleFunctions(simpleFunctions);
        }
        return orgBizTypeVo;
    }

    /**
     * 填充名字
     *
     * @param groupDetailDto 集团详细信息
     */
    private void fillName(final GroupDetailDto groupDetailDto) {
        final Long custId = groupDetailDto.getCustId();
        if (!NumericUtil.nullOrlessThanOrEqualToZero(custId)) {
            final Map<Long, String> custNameMap = custDomainService.batchQueryCustInfoByIds(Collections.singleton(custId));
            groupDetailDto.setCustName(custNameMap.get(custId));
        }

        final String businessType = groupDetailDto.getBusinessType();
        if (!StringUtils.isEmpty(businessType)) {
            final String bizTypeName = dicDomainService.parseOneBizTypeId(businessType);
            groupDetailDto.setBusinessTypeName(bizTypeName);
        }
    }

    //获得当天0点时间
    private Date getTimesmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        System.out.println(cal.getTimeInMillis());
        System.out.println(new Timestamp(cal.getTimeInMillis()));
        return cal.getTime();
    }

    //获得本周一0点时间
    private Date getTimesWeekmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        System.out.println(cal.getTimeInMillis());
        System.out.println(new Timestamp(cal.getTimeInMillis()));
        return cal.getTime();
    }

    //获得本月第一天0点时间
    private Date getTimesMonthmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        System.out.println(cal.getTimeInMillis());
        System.out.println(new Timestamp(cal.getTimeInMillis()));
        return cal.getTime();
    }

    private QueryGroupIncludeBusinessTypeDto getCount(List<String> count) {
        if (count.isEmpty()) {
            return null;
        }
        QueryGroupIncludeBusinessTypeDto queryGroupIncludeBusinessTypeDto = new QueryGroupIncludeBusinessTypeDto();
        Integer dayBrandCount = 0;
        Integer dayWarehouseCount = 0;
        Integer dayTransportCount = 0;
        for (int i = 0; i < count.size(); i++) {
            if (count.get(i) == null) {
                continue;
            }
            if (!count.get(i).isEmpty()) {
                String type = count.get(i);
                String[] typeArray = type.split(",");
                for (int j = 0; j < typeArray.length; j++) {
                    if (typeArray[j].equals(BusinessTypeEnum.CHANNEL.getCode())) {
                        dayBrandCount = dayBrandCount + 1;
                    }
                    if (typeArray[j].equals(BusinessTypeEnum.BRANDER.getCode())) {
                        dayBrandCount = dayBrandCount + 1;
                    }
                    if (typeArray[j].equals(BusinessTypeEnum.STORAGE.getCode())) {
                        dayWarehouseCount = dayWarehouseCount + 1;
                    }
                    if (typeArray[j].equals(BusinessTypeEnum.LOGISTICS.getCode())) {
                        dayTransportCount = dayTransportCount + 1;
                    }
                }
                //避免重复加
                Boolean channelFlag = Boolean.FALSE;
                for (String channel : typeArray) {
                    if (channel.equals(BusinessTypeEnum.CHANNEL.getCode())) {
                        channelFlag = true;
                    }
                }
                Boolean brandFlag = Boolean.FALSE;
                for (String brand : typeArray) {
                    if (brand.equals(BusinessTypeEnum.BRANDER.getCode())) {
                        brandFlag = true;
                    }
                }
                if (channelFlag && brandFlag) {
                    dayBrandCount = dayBrandCount - 1;
                }
            }
        }
        queryGroupIncludeBusinessTypeDto.setBrandChannelDayCount(dayBrandCount);
        queryGroupIncludeBusinessTypeDto.setWarehouseDayCount(dayWarehouseCount);
        queryGroupIncludeBusinessTypeDto.setTransportDayCount(dayTransportCount);
        return queryGroupIncludeBusinessTypeDto;
    }
}
