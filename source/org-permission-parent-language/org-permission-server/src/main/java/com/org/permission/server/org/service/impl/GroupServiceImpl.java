package com.org.permission.server.org.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.common.language.util.I18nUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.org.permission.common.dto.crm.CustInfoDomainDto;
import com.org.permission.common.dto.crm.LinkerInfoReqParam;
import com.org.permission.common.enums.org.FunctionTypeEnum;
import com.org.permission.common.org.dto.BaseAddressDto;
import com.org.permission.common.org.dto.OrgInfoDto;
import com.org.permission.common.org.dto.OrgUser;
import com.org.permission.common.org.dto.OrganizationFunctionDto;
import com.org.permission.common.org.dto.func.CorporateOrgFuncBean;
import com.org.permission.common.org.param.GroupListQueryParam;
import com.org.permission.common.org.param.*;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.server.domain.base.CodeDomainService;
import com.org.permission.server.domain.crm.CustDomainService;
import com.org.permission.server.domain.user.UserDomainService;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.*;
import com.org.permission.server.org.builder.BizUnitEntrustRelationshipBuilder;
import com.org.permission.server.org.builder.RootBizUnitOrgFunctionBuilder;
import com.org.permission.server.org.dto.ManageGroupLogo;
import com.org.permission.server.org.dto.param.*;
import com.org.permission.server.org.enums.EntityAttrEnum;
import com.org.permission.server.org.enums.OrgStateEnum;
import com.org.permission.server.org.enums.OrgTypeEnum;
import com.org.permission.server.org.mapper.BizUnitMapper;
import com.org.permission.server.org.mapper.CommonOrgMapper;
import com.org.permission.server.org.mapper.GroupMapper;
import com.org.permission.server.org.mapper.StaffMapper;
import com.org.permission.server.org.service.GroupService;
import com.org.permission.server.org.service.OrganizationFunctionService;
import com.org.permission.server.permission.service.IBasePermissionGroupParamService;
import com.org.permission.server.utils.NumericUtil;
import com.common.util.util.AssertUtils;
import com.boss.crm.common.dto.customer.CustSaicRawDto;
import com.common.base.enums.BooleanEnum;
import com.common.base.enums.StateEnum;
import com.common.framework.user.FplUserUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 集团管理服务业务逻辑处理
 */
@Component(value = "groupService")
public class GroupServiceImpl implements GroupService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);

    private static final Long GLOABLE_ID = 1L;


    @Resource
    private CodeDomainService codeDomainService;

    @Resource
    private CustDomainService custDomainService;

    @Resource
    private UserDomainService userDomainService;

    @Resource
    private IBasePermissionGroupParamService permissionService;

    @Resource
    private RootBizUnitOrgFunctionBuilder rootBizUnitOrgFunctionBuilder;

    @Resource
    private BizUnitEntrustRelationshipBuilder bizUnitEntrustRelationshipBuilder;

    @Resource
    private CommonOrgMapper commonOrgMapper;

    @Resource
    private GroupMapper groupMapper;

    @Resource
    private BizUnitMapper bizUnitMapper;

    @Resource
    private StaffMapper staffMapper;


    @Resource(name = "bizUnitService")
    private BizUnitServiceImpl bizUnitService;


    @Resource(name = "OrganizationFunctionService")
    private OrganizationFunctionService organizationFunctionService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void createGroup(GroupReqParam reqParam) {

        groupMapper.getGroupStateByIdLock(GLOABLE_ID);

        GroupInfoBean groupInfoBean = new GroupInfoBean();
        BeanUtils.copyProperties(reqParam, groupInfoBean);
        groupInfoBean.setId(null);
        final Long custId = reqParam.getCustId();
        if (NumericUtil.greterThanZero(custId)) {
            CustInfoDomainDto custInfo = custDomainService.getAndVerifyCust(custId);//获取并验证客户信息
            groupInfoBean.setBizTypeId(custInfo.getBizTypeId());
        }

        groupInfoBean.setCreatedDate(new Date());
        groupInfoBean.setState(OrgStateEnum.CREATE.getCode());
        groupInfoBean.setInitFlag(BooleanEnum.FALSE.getCode());
        groupInfoBean.setOrgType(OrgTypeEnum.GROUP.getIndex());
        groupInfoBean.setOrgCode(codeDomainService.getGroupCode());

        groupInfoBean.setIndustryCode(reqParam.getIndustryCode());
        groupInfoBean.setIndustryName(reqParam.getIndustryName());

        LOGGER.info("add group to db ,bean:{}.", groupInfoBean);
        groupMapper.addGroup(groupInfoBean);
        final Long groupId = groupInfoBean.getId();
        groupMapper.writeBackGroupId(groupId);
    }

    @Override
    @Transactional
    public OrgUser custGenerateGroup(CustGenerateGroupParam reqParam) {

        final Long custId = reqParam.getCustId();
        final CustInfoDomainDto custInfo = custDomainService.getAndVerifyCust(custId);//获取并验证客户信息

        final GroupInfoBean groupInfoBean = custGenerateGroupInfo(custInfo, reqParam);
        final Long userId = reqParam.getUserId();
        groupInfoBean.setCreatedBy(userId);
        groupInfoBean.setCreatedDate(new Date());
        groupInfoBean.setCreatedName(reqParam.getUserName());
        groupInfoBean.setOrgCode(codeDomainService.getGroupCode());
        BaseAddressDto baseAddressDto = new BaseAddressDto();
        if (groupInfoBean.getAddressDetail() == null) {
            baseAddressDto.setRegionCode("CHN");
            baseAddressDto.setRegionName("中国");
            groupInfoBean.setAddressDetail(baseAddressDto);
        }
        groupMapper.addGroup(groupInfoBean);
        final Long groupId = groupInfoBean.getId();
        groupMapper.writeBackGroupId(groupId);//回写生成的集团ID

        final String taxpayerCode = reqParam.getTaxpayerCode();
        final String taxpayerName = reqParam.getTaxpayerName();
        final String orgInstitutionCode = reqParam.getOrgInstitutionCode();
        final String taxRegistrationNumber = reqParam.getTaxRegistrationNumber();
        final OrgUser orgUser = initGroup(groupInfoBean, userId, reqParam.getUserName(), taxpayerCode, taxpayerName, orgInstitutionCode, taxRegistrationNumber, Boolean.FALSE);
        commonOrgMapper.enableOrg(userId, groupId, new Date());//初始化完成，修改集团对应的状态
        return orgUser;
    }

    @Override
    @Transactional
    public void bindingCust(BindingCustReqParam reqParam) {
        final Long groupId = reqParam.getOrgId();
        final OrgBean groupState = groupMapper.getGroupStateByIdLock(groupId);
        if (groupState == null) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.impl.group.not.exist"));
        }
        if (groupState.getState() != OrgStateEnum.CREATE.getCode()) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.service.impl.group.state.cannot.relation.customer"));
        }

        final Long custId = reqParam.getCustId();
        final int count = commonOrgMapper.countOrgByCustId(custId);
        if (count > 1) {
            LOGGER.info("该客商已绑定其它组织,参数：{}.", reqParam);
            throw new OrgException(OrgErrorCode.CUSTER_ALREADY_BINDING_GROUP_ERROR_CODE, I18nUtils.getMessage("org.service.impl.group.customer.binded.org"));
        }

        // 获取客户信息
        custDomainService.getAndVerifyCust(custId);

        groupMapper.bindingCust(reqParam);

        custDomainService.custInfoWriteBack(custId, groupId, null, reqParam.getUserId());
    }

    @Override
    @Transactional
    public void deleteGroup(KeyOperateParam reqParam) {

        final Long groupId = reqParam.getId();
        final OrgBean groupState = groupMapper.getGroupStateByIdLock(groupId);
        if (groupState == null) {
            LOGGER.warn("delete group not exist,groupId:{}.", groupId);
            throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.service.impl.group.not.exist"));
        }

        if (groupState.getInitFlag() == BooleanEnum.TRUE.getCode()) {
            LOGGER.warn("already inited group not support delete ,groupId:{}.", groupId);
            throw new OrgException(OrgErrorCode.ENABLED_STATE_CAN_NOT_DELETE_ERROR_CODE, I18nUtils.getMessage("org.service.impl.group.enable.cannot.delete"));
        }
        commonOrgMapper.deleteOrgById(reqParam.getId(), reqParam.getUserId());
    }

    @Override
    @Transactional
    public void updateGroup(GroupReqParam reqParam) {
        final Long groupId = reqParam.getId();
        final OrgBean groupState = groupMapper.getGroupStateByIdLock(groupId);
        if (groupState == null) {
            LOGGER.warn("update group not exist,groupId:{}.", groupId);
            throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.service.impl.group.not.exist"));
        }
        if (Objects.equals(groupState.getDeletedFlag(), BooleanEnum.TRUE.getCode())) {
            LOGGER.warn("update group not exist,groupId:{}.", groupId);
            throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.service.impl.group.not.exist"));
        }

        final Long modifyCustId = reqParam.getCustId();
        final Long groupCustId = groupState.getCustId();
        if (groupState.getInitFlag() == BooleanEnum.TRUE.getCode()) {
            if (!groupCustId.equals(modifyCustId)) {
                LOGGER.warn("already inited group crm not support update ,groupId:{}.", groupId);
                throw new OrgException(OrgErrorCode.ENABLED_STATE_CAN_NOT_DELETE_ERROR_CODE, I18nUtils.getMessage("org.service.impl.group.init.cannot.modify"));
            }

            final String currency = reqParam.getCurrency();
            if (!groupState.getCurrency().equals(currency)) {
                LOGGER.warn("already inited group currency not support update ,groupId:{}.", groupId);
                throw new OrgException(OrgErrorCode.ENABLED_STATE_CAN_NOT_DELETE_ERROR_CODE, I18nUtils.getMessage("org.service.impl.group.init.currency.cannot.modify"));
            }
        }

        CustInfoDomainDto custInfo = null;
        if (groupState.getInitFlag() == BooleanEnum.FALSE.getCode()) {
            if (!Objects.equals(modifyCustId, groupCustId)) {
                if (NumericUtil.greterThanZero(modifyCustId)) {
                    final int count = commonOrgMapper.countOrgByCustId(modifyCustId);
                    if (count > 0) {
                        LOGGER.warn("current crm already binding group ,custId:{}.", modifyCustId);
                        throw new OrgException(OrgErrorCode.CUSTER_ALREADY_BINDING_GROUP_ERROR_CODE, I18nUtils.getMessage("org.domain.crm.customer.bind.org"));
                    }

                    custInfo = custDomainService.getCustInfoById(modifyCustId);
                    final Integer enterState = custInfo.getEnterState();
                    if (BooleanEnum.TRUE.getCode() == enterState) {
                        LOGGER.warn("current crm already enter bee ,custId:{}.", modifyCustId);
                        throw new OrgException(OrgErrorCode.CUSTER_ALREADY_BINDING_GROUP_ERROR_CODE, I18nUtils.getMessage("org.domain.crm.customer.bind.org"));
                    }

                    if (!custInfo.canBindingOrg()) {
                        LOGGER.warn("current crm state can not bind group ,custId:{}.", modifyCustId);
                        throw new OrgException(OrgErrorCode.CUSTER_STATE_ERROR_CODE, I18nUtils.getMessage("org.service.impl.group.customer.state.cannot.bind.group"));
                    }
                }
            }

        }

        UpdateGroupWriteBean updateGroupWriteBean = new UpdateGroupWriteBean();
        BeanUtils.copyProperties(reqParam, updateGroupWriteBean);
        updateGroupWriteBean.setModifiedDate(new Date());
        String bizType = groupState.getBizTypeId();
        if (groupState.getInitFlag() == BooleanEnum.FALSE.getCode()) {
            if (null != custInfo) {
                bizType = custInfo.getBizTypeId();
            }
        }
        updateGroupWriteBean.setBizTypeId(bizType);
        LOGGER.info("update group in db :{}.", updateGroupWriteBean);
        updateGroupWriteBean.setModifiedDate(new Date());
        groupMapper.updateGroup(updateGroupWriteBean);
    }

    @Override
    @Transactional
    public OrgUser enableGroup(EnableGroupParam reqParam) {

        final Long groupId = reqParam.getId();
        final GroupInfoBean groupInfoBean = groupMapper.queryGroupByIdLock(groupId);
        LOGGER.info("enable group info in db:{}.", groupInfoBean);

        if (groupInfoBean == null) {
            LOGGER.info("enable group not exit , reqParam:{}.", reqParam);
            throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.service.impl.group.not.exist"));
        }

        final Integer currentStatus = groupInfoBean.getState();
        final Integer reqEnableState = reqParam.getState();
        if (currentStatus.equals(reqEnableState)) {
            LOGGER.info("repetitive enable group, reqParam:{}.", reqParam);
        }

        if (OrgStateEnum.DISABLE.getCode() == reqEnableState) {
            if (OrgStateEnum.ENABLE.getCode() != currentStatus) {
                LOGGER.info("group has not start usesd, reqParam:{}.", reqParam);
                throw new OrgException(OrgErrorCode.STATE_CANNOT_OP_ERROR_CODE, I18nUtils.getMessage("org.service.impl.group.unenalbe.cannot.disable"));
            }
            reqParam.setEnableTime(new Date());
            // 1.停用集团下所有组织
            commonOrgMapper.cascadeDisableGroupOrg(reqParam);
            // 2.停用集团下的人员
            staffMapper.disableStaffByGroupId(groupId, reqParam.getUserId(), new Date());
            // 3.停用用户
            userDomainService.updateEnableUserByGroupId(reqParam.getUserId(), groupId, 2);
        }

        OrgUser orgUser = null;
        if (OrgStateEnum.ENABLE.getCode() == reqEnableState) {// 此次请求为启用
            final Date updateTime = new Date();
            final Integer initFlag = groupInfoBean.getInitFlag();
            final Long userId = reqParam.getUserId();
            if (BooleanEnum.FALSE.getCode().equals(initFlag)) {
                final Long custId = groupInfoBean.getCustId();
                if (NumericUtil.lessThanZero(custId)) {
                    LOGGER.info("未绑定客商的集团不可启用, groupId:{}.", groupId);
                    throw new OrgException(OrgErrorCode.UNBINDING_CUSTER_CANNOT_ENABLE_ERROR_CODE, I18nUtils.getMessage("org.service.impl.group.unbind.customer"));
                }
                final String taxpayerCode = reqParam.getTaxpayerCode();
                final String taxpayerName = reqParam.getTaxpayerName();
                final String orgInstitutionCode = reqParam.getOrgInstitutionCode();
                final String taxRegistrationNumber = reqParam.getTaxRegistrationNumber();
                orgUser = initGroup(groupInfoBean, userId, reqParam.getUserName(), taxpayerCode, taxpayerName, orgInstitutionCode, taxRegistrationNumber, Boolean.TRUE);
            }
            if (BooleanEnum.TRUE.getCode().equals(initFlag)) {
                bizUnitMapper.enableRootBUByGroupId(groupId, userId, updateTime);
            }
            commonOrgMapper.enableOrg(reqParam.getUserId(), groupId, updateTime);
        }
        return orgUser;
    }

    @Override
    @Transactional
    public void groupLogo(ManageGroupLogo reqParam) {

        final Long groupId = reqParam.getGroupId();
        final GroupInfoBean groupInfoBean = groupMapper.queryGroupByIdLock(groupId);
        LOGGER.debug("group info in db:{}.", groupInfoBean);

        if (groupInfoBean == null) {
            LOGGER.info("group not exit , groupId:{}.", groupId);
            throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.service.impl.group.not.exist"));
        }

        GroupLogoWriteBean groupLogoWriteBean = new GroupLogoWriteBean();
        BeanUtils.copyProperties(reqParam, groupLogoWriteBean);
        groupLogoWriteBean.setModifiedDate(new Date());

        groupMapper.manageLogo(groupLogoWriteBean);
    }

    /**
     * 初始化集团
     *
     * @param groupInfo             集团信息
     * @param operaterId            操作人 ID
     * @param taxpayerCode          纳税人编码
     * @param taxpayerName          纳税人名称
     * @param orgInstitutionCode    组织机构代码
     * @param taxRegistrationNumber 税务登记号
     * @param writeBackCust         是否回写客商
     * @return 用户密码
     */
    private OrgUser initGroup(final GroupInfoBean groupInfo, Long operaterId, String operaterName, String taxpayerCode, String taxpayerName, final String orgInstitutionCode, final String taxRegistrationNumber, Boolean writeBackCust) {

        // 获取客户信息
        final Long custId = groupInfo.getCustId();
        final CustInfoDomainDto custInfo = custDomainService.getCustInfoById(custId);
        if (custInfo == null) {
            LOGGER.info("init group crm is null , groupId:{}.", groupInfo.getId());
            throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.service.impl.group.cust.null"));
        }

        // 构建根业务单元
        final BizUnitWithFuncDetailBean rootBizUnit = buildRootBizUnit(groupInfo, operaterId);
        bizUnitMapper.addBizUnit(rootBizUnit);

        final Long rootBUId = rootBizUnit.getId();
        bizUnitMapper.writebackRootBUCompanyId(rootBUId);

        // 构建根业务单元组织职能
        final AddBizUnitReqParam rootBU = rootBizUnitOrgFunctionBuilder.builde(groupInfo, rootBUId, custInfo, taxpayerCode, taxpayerName, orgInstitutionCode, taxRegistrationNumber, operaterId, operaterName);
        final Long[] orgFuncIds = bizUnitService.writeOrgFuncs(rootBU, rootBUId, StateEnum.ENABLE);
        // 构建业务委托关系
        final Long groupId = groupInfo.getId();
        rootBU.setId(rootBUId);
        rootBU.setGroupId(groupId);
        rootBU.setCreatedBy(operaterId);
        bizUnitEntrustRelationshipBuilder.buGenerateEntrustRelation(rootBU, orgFuncIds, StateEnum.ENABLE.getCode());

        // 初始化集团管理员
        final OrgUser orgUser = userDomainService.initAdministrator(operaterId, custInfo, rootBUId, groupId);

        //String 集团的业务类型,String 集团管理员id,String 集团管理员名称,String 集团编码,String 集团名称
        String bizType = custInfo.getBizTypeId();
        String adminId = orgUser.getUserId() + "";
        String adminName = orgUser.getUserName();
        String groupCode = groupInfo.getOrgCode();
        String groupName = groupInfo.getOrgName();
        final long userId = operaterId.longValue();
        // 初始化集团参数配置 + 集团 数据和功能 权限
        StopWatch stopWatch = new StopWatch("init permission");
        stopWatch.start();
        permissionService.initGroupPermission(userId, rootBU.getCreatedName(), groupId, bizType, adminId, adminName, groupCode, groupName);
        stopWatch.stop();
        LOGGER.info("init permission total time millis:{}.", stopWatch.getTotalTimeMillis());

        // 回写客商信息
        if (writeBackCust) {
            custDomainService.custInfoWriteBack(custId, groupId, rootBUId, operaterId);
        }

        //TODO  初始化集团部门编码规则


        return orgUser;
    }

    /**
     * 客商生成集团信息
     *
     * @param custInfo 客商信息
     * @return 集团信息
     */
    private GroupInfoBean custGenerateGroupInfo(final CustInfoDomainDto custInfo, CustGenerateGroupParam reqParam) {
        GroupInfoBean groupInfoBean = new GroupInfoBean();
        groupInfoBean.setOrgType(OrgTypeEnum.GROUP.getIndex());
        final String groupNameParam = reqParam.getGroupName();
        String orgName = StringUtils.isEmpty(groupNameParam) ? custInfo.getOrgName() : groupNameParam;
        groupInfoBean.setOrgName(orgName);
        final String industryCode = custInfo.getIndustryCode();
        groupInfoBean.setIndustryName(industryCode);
        groupInfoBean.setAddressDetail(custInfo.getAddressDetail());
        final LinkerInfoReqParam linkerInfo = custInfo.getLinkerInfo();
        groupInfoBean.setPhone(linkerInfo == null ? "" : linkerInfo.getPhone());
        groupInfoBean.setEmail(linkerInfo == null ? "" : linkerInfo.getEmail());
        final String creditCodeParam = reqParam.getCreditCode();
        String creditCode = StringUtils.isEmpty(creditCodeParam) ? custInfo.getCreditCode() : creditCodeParam;
        groupInfoBean.setCreditCode(creditCode);
        groupInfoBean.setEstablishTime(custInfo.getEstablishTime());
        groupInfoBean.setCurrency(I18nUtils.getMessage("org.service.impl.group.rmb"));
        groupInfoBean.setNetAddress(custInfo.getNetAddress());
        groupInfoBean.setCustId(custInfo.getCustId());
        groupInfoBean.setBizTypeId(custInfo.getBizTypeId());
        groupInfoBean.setState(OrgStateEnum.ENABLE.getCode());
        //集团简称
        groupInfoBean.setOrgShortName(custInfo.getOrgShortName());
        //所属行业
        groupInfoBean.setIndustryName(custInfo.getIndustryName());
        groupInfoBean.setIndustryCode(custInfo.getIndustryCode());
        groupInfoBean.setRemark(I18nUtils.getMessage("org.service.impl.group.create.by.customer"));
        groupInfoBean.setPhone(!StringUtils.isEmpty(custInfo.getPhone()) ? custInfo.getPhone() : groupInfoBean.getPhone());
        return groupInfoBean;
    }

    /**
     * 构建根业务单元
     *
     * @param groupInfoBean 集团基本数据实体信息
     * @return 根业务单元信息
     */
    private BizUnitWithFuncDetailBean buildRootBizUnit(final GroupInfoBean groupInfoBean, Long userId) {
        BizUnitWithFuncDetailBean rootBizUnitWithFuncDetailBean = new BizUnitWithFuncDetailBean();
        rootBizUnitWithFuncDetailBean.setCreatedBy(userId);
        rootBizUnitWithFuncDetailBean.setCreatedDate(new Date());
        rootBizUnitWithFuncDetailBean.setCreatedName(FplUserUtil.getUserName());
        rootBizUnitWithFuncDetailBean.setInitFlag(BooleanEnum.TRUE.getCode());
        rootBizUnitWithFuncDetailBean.setStartTime(new Date());

        rootBizUnitWithFuncDetailBean.setOrgCode(codeDomainService.getOrgCode());
        rootBizUnitWithFuncDetailBean.setOrgType(OrgTypeEnum.ORGANIZATION.getIndex());
        rootBizUnitWithFuncDetailBean.setMainOrgFlag(BooleanEnum.TRUE.getCode());
        rootBizUnitWithFuncDetailBean.setParentId(0L);

        rootBizUnitWithFuncDetailBean.setOrgName(groupInfoBean.getOrgName());

        rootBizUnitWithFuncDetailBean.setEntityCode(EntityAttrEnum.COMPANY.getAttrCode());
        rootBizUnitWithFuncDetailBean.setEntityName(EntityAttrEnum.COMPANY.getAttrName());

        rootBizUnitWithFuncDetailBean.setCompanyId(0L);
        rootBizUnitWithFuncDetailBean.setCustId(groupInfoBean.getCustId());
        rootBizUnitWithFuncDetailBean.setGroupId(groupInfoBean.getId());
        rootBizUnitWithFuncDetailBean.setState(OrgStateEnum.ENABLE.getCode());

        final int year = Calendar.getInstance().get(Calendar.YEAR);
        rootBizUnitWithFuncDetailBean.setVersion(year + "01");

        rootBizUnitWithFuncDetailBean.setCreditCode(groupInfoBean.getCreditCode());
        rootBizUnitWithFuncDetailBean.setIndustryCode(groupInfoBean.getIndustryCode());
        rootBizUnitWithFuncDetailBean.setIndustryName(groupInfoBean.getIndustryName());
        rootBizUnitWithFuncDetailBean.setAddressDetail(groupInfoBean.getAddressDetail());
        rootBizUnitWithFuncDetailBean.setPhone(groupInfoBean.getPhone());
        rootBizUnitWithFuncDetailBean.setRemark(groupInfoBean.getRemark());
        rootBizUnitWithFuncDetailBean.setCurrency(groupInfoBean.getCurrency());

        return rootBizUnitWithFuncDetailBean;
    }

    @Override
    public GroupInfoBean queryGroupById(QueryByIdReqParam reqParam) {
        return groupMapper.queryGroupById(reqParam.getId());
    }

    @Override
    public PageInfo<GroupInfoBean> queryGroupInfoList(QueryGroupListReqParam reqParam) {
        PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize()).setOrderBy("created_date desc");
        final List<GroupInfoBean> groupInfoBeans = groupMapper.queryGroupList(reqParam);
        PageInfo<GroupInfoBean> pageInfo = new PageInfo<>(groupInfoBeans);
        return pageInfo;
    }

    @Override
    public String queryGroupBusinessType(Long groupId) {
        return groupMapper.queryGroupBusinessType(groupId);
    }

    public List<String> queryGroupIncludeBusinessType(QueryGroupIncludeBusinessTypeParam reqParam) {
        return groupMapper.queryGroupIncludeBusinessType(reqParam);
    }

    public Integer queryTotalCountByBusiType(List<String> busiType) {
        return groupMapper.queryTotalCountByBusiType(busiType);
    }

    @Override
    public List<OrgListBean> queryGroupList(QueryOrgListInfoReqParam reqParam) {
        if (ObjectUtil.isNull(reqParam.getState())) {
            reqParam.setState(OrgStateEnum.ENABLE.getCode());
        }
        return groupMapper.queryGroupListInfo(reqParam);
    }

    @Override
    public PageInfo<OrgInfoDto> queryAllGroupInfoList(GroupListQueryParam reqParam) {
        PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
        final List<OrgInfoDto> orgInfoDtos = groupMapper.queryAllGroupInfoList(reqParam);
        PageInfo<OrgInfoDto> pageInfo = new PageInfo<>(orgInfoDtos);
        return pageInfo;
    }

    public PageInfo<OrgInfoDto> queryGroupsNotRelCust(GroupListQueryParam reqParam) {
        PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
        final List<OrgInfoDto> orgInfoDtos = groupMapper.queryGroupsNotRelCust(reqParam);
        PageInfo<OrgInfoDto> pageInfo = new PageInfo<>(orgInfoDtos);
        return pageInfo;
    }

    @Override
    public PageInfo<OrgInfoDto> queryGroupInfoByOrgIds(BatchQueryParam reqParam) {
        PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
        final List<OrgInfoDto> orgInfoDtos = groupMapper.queryGroupInfoByOrgIds(reqParam);
        PageInfo<OrgInfoDto> pageInfo = new PageInfo<>(orgInfoDtos);
        return pageInfo;
    }

    @Override
    public List<OrgInfoDto> queryGroupInfoByOrgIdsNoPage(BatchQueryParam reqParam) {
        final List<OrgInfoDto> orgInfoDtos = groupMapper.queryGroupInfoByOrgIds(reqParam);
        return orgInfoDtos;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addCorpFunction(CustSaicRawDto custSaicRawDto, Long groupId, Long rootUnitOrgId) {
        // TODO 客商
        List<OrganizationFunctionDto> organizationFunctionDtos = getCorpFunction(rootUnitOrgId);
        if (CollectionUtils.isEmpty(organizationFunctionDtos)) {
            AssertUtils.isNotNull(custSaicRawDto.getCustId(), I18nUtils.getMessage("org.service.impl.group.custid.cannot.null"));
            CustInfoDomainDto custInfo = custDomainService.getCustInfoById(custSaicRawDto.getCustId());
            AssertUtils.isNotNull(custInfo, I18nUtils.getMessage("org.service.impl.group.gobal.customer.not.exist"));

            GroupInfoBean groupInfoBean = new GroupInfoBean();
            if (custSaicRawDto.getOperateBeginDate() != null) {
                groupInfoBean.setEstablishTime(custSaicRawDto.getOperateBeginDate());
            }
            groupInfoBean.setNetAddress(custSaicRawDto.getWebsite());

            CustInfoDomainDto custInfoDomainDto = new CustInfoDomainDto();
            if (custSaicRawDto.getOperateBeginDate() != null) {
                custInfoDomainDto.setBusinessStartTime(custSaicRawDto.getOperateBeginDate());
            }
            if (custSaicRawDto.getOperateEndDate() != null) {
                custInfoDomainDto.setBusinessEndTime(custSaicRawDto.getOperateEndDate());
            }

            custInfoDomainDto.setEnterpriseType(custInfo.getEnterpriseType());

            LinkerInfoReqParam linkerInfo = new LinkerInfoReqParam();
            linkerInfo.setEmail(custSaicRawDto.getEmail());
            linkerInfo.setLinker(custSaicRawDto.getLegalPerson());
            linkerInfo.setPhone(custSaicRawDto.getRegPhone());
            custInfoDomainDto.setLinkerInfo(linkerInfo);
            CorporateOrgFuncBean corporateOrgFuncBean = rootBizUnitOrgFunctionBuilder.buildeCorporation(
                    groupInfoBean, custInfoDomainDto, null, null, custSaicRawDto.getOrgCode(), rootUnitOrgId);

            AddBizUnitReqParam addBizUnitReqParam = new AddBizUnitReqParam();
            addBizUnitReqParam.setCorporate(corporateOrgFuncBean);
            addBizUnitReqParam.setCreatedBy(FplUserUtil.getUserId());
            bizUnitService.writeOrgFuncs(addBizUnitReqParam, rootUnitOrgId, StateEnum.ENABLE);
            AssertUtils.isTrue(CollectionUtils.isNotEmpty(getCorpFunction(rootUnitOrgId)), "addCorpFunction error;corpfunction is null");

            //更新集团名字和简称
            GroupInfoBean groupInfoBeanForUpdate = groupMapper.queryGroupById(groupId);
            if (groupInfoBeanForUpdate != null) {
                groupInfoBeanForUpdate.setOrgName(custSaicRawDto.getCompanyName());
                groupInfoBeanForUpdate.setOrgShortName(custSaicRawDto.getCompanyName());
                UpdateGroupWriteBean updateGroupWriteBean = new UpdateGroupWriteBean();
                BeanUtil.copyProperties(groupInfoBeanForUpdate, updateGroupWriteBean);
                groupMapper.updateGroup(updateGroupWriteBean);
            }

            //更新根业务单元名字和简称
            GroupInfoBean orgInfoBeanForUpdate = groupMapper.queryGroupById(rootUnitOrgId);
            if (orgInfoBeanForUpdate != null) {
                orgInfoBeanForUpdate.setOrgName(custSaicRawDto.getCompanyName());
                orgInfoBeanForUpdate.setOrgShortName(custSaicRawDto.getCompanyName());
                UpdateGroupWriteBean updateOrgWriteBean = new UpdateGroupWriteBean();
                BeanUtil.copyProperties(orgInfoBeanForUpdate, updateOrgWriteBean);
                groupMapper.updateGroup(updateOrgWriteBean);
            }

            LOGGER.info("addCorpFunction success|custId={}|rootUnitOrg={}", custSaicRawDto.getCustId(), rootUnitOrgId);
        }
    }

    private List<OrganizationFunctionDto> getCorpFunction(Long rootUnitOrgId) {
        QueryBizsFuncParam queryBizsFuncParam = new QueryBizsFuncParam();
        queryBizsFuncParam.setFunctionType(FunctionTypeEnum.CORPORATION.getIndex());
        queryBizsFuncParam.setIds(Lists.newArrayList(rootUnitOrgId));
        return organizationFunctionService.queryBizsFunc(queryBizsFuncParam);
    }
}