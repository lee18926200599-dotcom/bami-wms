package com.org.permission.server.org.service.impl;

import com.common.base.enums.BooleanEnum;
import com.common.base.enums.StateEnum;
import com.common.language.util.I18nUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.org.permission.common.enums.org.FunctionTypeEnum;
import com.org.permission.common.org.dto.*;
import com.org.permission.common.org.param.*;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.server.domain.permission.PermissionDomainService;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.*;
import com.org.permission.server.org.builder.MultipleOrgTreeBuilder;
import com.org.permission.server.org.builder.OrgTreeBuilder;
import com.org.permission.server.org.dto.MultipleOrgTreeDto;
import com.org.permission.server.org.dto.OrgLogolDto;
import com.org.permission.server.org.dto.param.FindOrgListParam;
import com.org.permission.server.org.dto.param.UserLoginReqParam;
import com.org.permission.server.org.enums.OrgTypeEnum;
import com.org.permission.server.org.mapper.*;
import com.org.permission.server.org.service.OrganizationService;
import com.org.permission.server.utils.NumericUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * 组织对外服务相关实现
 */
@Service(value = "organizationService")
public class OrganizationServiceImpl implements OrganizationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    @Resource
    private BizUnitMapper bizUnitMapper;

    @Resource
    private CommonOrgMapper commonOrgMapper;

    @Resource
    private GroupMapper groupMapper;

    @Resource
    private OrgTreeBuilder orgTreeBuilder;

    @Resource
    private MultipleOrgTreeBuilder multipleOrgTreeBuilder;

    @Resource
    private PermissionDomainService permissionDomainService;

//    @Resource
//    private BaseOrganizationMapper organizationMapper;

    @Resource
    private StaffMapper staffMapper;

    @Override
    public OrgLogolDto queryOrgInfoByUser(UserLoginReqParam reqParam) {
        final Long orgId = reqParam.getGroupId();
        final OrgCustDto custAndLogo = getCustAndGroup(orgId);
        LOGGER.info("org corporation function crm:param:{},custAndLogo:{}.", orgId, custAndLogo);

        final String logoUrl = groupMapper.queryGroupLogoById(custAndLogo.getGroupId());
        return new OrgLogolDto(custAndLogo.getCustId(), logoUrl);
    }

    @Override
    public OrgCustDto queryOrgCust(QueryByIdReqParam reqParam) {
        return getCustAndGroup(reqParam.getId());
    }

    @Override
    public CustOrgDto queryCustOrg(QueryByIdReqParam reqParam) {
        return bizUnitMapper.queryCustOrg(reqParam);
    }

    @Override
    public OrgTreeDto queryOrgTree(final QueryOrgTreeReqParam reqParam) {
        final Long groupId = reqParam.getGroupId();
        final List<OrgTreeBean> orgTreeBeans = commonOrgMapper.queryGroupEnableOrgTreeByOrgId(groupId);
        LOGGER.debug("org tree members in db,groupId:{},tres:{}.", reqParam, orgTreeBeans);
        if (CollectionUtils.isEmpty(orgTreeBeans)) {
            LOGGER.debug("org tree members in db is null,groupId:{}.", groupId);
            return null;
        }
        return orgTreeBuilder.buildOrgTree(orgTreeBeans, reqParam);
    }


    @Override
    public SimpleBizUnitWithFuncBean queryParentOrg(Long orgId) {
        final OrgTreeBean currentOrgHierarch = commonOrgMapper.queryOrgHierarchyInfoByOrgId(orgId);
        if (null == currentOrgHierarch) {
            LOGGER.warn("query parent org ,current org is null ,orgId:{}. ", orgId);
            throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.common.data.not.exist"));
        }

        final Integer orgType = currentOrgHierarch.getOrgType();
        if (orgType == OrgTypeEnum.GROUP.getIndex()) {
            LOGGER.warn("query parent org ,current org is group ,orgId:{}. ", orgId);
            throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.service.impl.org.group.no.parent"));
        }

        Long highOrgId = null;
        if (orgType == OrgTypeEnum.ORGANIZATION.getIndex()) {
            highOrgId = currentOrgHierarch.getMainOrgFlag() == BooleanEnum.TRUE.getCode() ? currentOrgHierarch.getGroupId() : currentOrgHierarch.getParentId();
        }
        if (orgType == OrgTypeEnum.DEPARTMENT.getIndex()) {
            final Long depParentId = currentOrgHierarch.getParentId();
            highOrgId = NumericUtil.greterThanZero(depParentId) ? depParentId : currentOrgHierarch.getParentBUId();
        }

        if (highOrgId == null) {
            LOGGER.warn("query parent org ,thp higer org of current org is null ,orgId:{}. ", orgId);
            throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.builder.OrgTree.org.data.exception"));
        }

        final SimpleBizUnitWithFuncBean bizUnitWithFunc = bizUnitMapper.queryOrgAndFuncInfo(highOrgId);
        LOGGER.debug("the parent org of current org :{},is:{}.", orgId, bizUnitWithFunc);
        return bizUnitWithFunc;
    }

    @Override
    public SimpleBizUnitWithFuncBean queryOrgById(Long orgId) {
        return commonOrgMapper.queryOrgAndFuncInfo(orgId);
    }

    @Override
    public List<OrgInfoDto> batchQueryOrgInfo(BatchQueryParam reqParam) {
        return commonOrgMapper.batchQueryOrgInfo(reqParam);
    }

    public List<BasicOrganizationDto> batchQueryOrganizations(BatchQueryParam reqParam) {
        return commonOrgMapper.batchQueryOrganizations(reqParam);
    }

    public PageInfo<BasicOrganizationDto> findOrgList(FindOrgListParam reqParam) {
        PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize(), reqParam.getOrderBy());
        final List<BasicOrganizationDto> staffBeans = commonOrgMapper.findOrgList(reqParam);
        PageInfo<BasicOrganizationDto> pageInfo = new PageInfo<>(staffBeans);
        return pageInfo;
    }

    public OrgInfoDto getOrgInfoById(Long id) {
        return commonOrgMapper.getOrgInfoById(id);
    }

    @Override
    public MultipleOrgTreeDto multipleOrgTreeQuery(MultipleOrgTreeQueryDto reqParam) {
        final List<OrgTreeBean> matchOrgTrees = commonOrgMapper.queryEnableOrgByName(reqParam.getOrgName());
        if (CollectionUtils.isEmpty(matchOrgTrees)) {
            return null;
        }

        final List<Long> groupIds = filterGroupIds(matchOrgTrees);
        final List<OrgTreeBean> groupAllOrgs = commonOrgMapper.queryGroupAllEnableOrg(groupIds);
        MultipleOrgTreeDto multipleOrgTree = new MultipleOrgTreeDto();
        multipleOrgTree.setMatchOrgs(matchOrgTrees);
        multipleOrgTree.setGroupAllOrgs(groupAllOrgs);
        return multipleOrgTree;
    }

    @Override
    public List<OrgTreeBean> queryOrgList(QueryOrgListReqParam reqParam) {
        return commonOrgMapper.queryGroupOrgTreeNotContainDepByOrgId(reqParam.getOrgId());
    }

    @Override
    public List<OrgTreeBean> queryOrgPermissonList(QueryOrgPermissionListReqParam reqParam) {
        final Set<Long> orgPermissionIds = permissionDomainService.getOrgsInPermission(reqParam.getUserId(), reqParam.getGroupId(), OrgTypeEnum.ALL);
        if (CollectionUtils.isEmpty(orgPermissionIds)) {
            LOGGER.warn("current user does not have org permission,reqParam:{}.", reqParam);
            throw new OrgException(OrgErrorCode.USER_PERMISION_NOT_ENOUGH_ERROR_CODE, I18nUtils.getMessage("org.service.impl.bizunit.user.permission.insufficient"));
        }
        return commonOrgMapper.queryAllEnableOrgWithSepicalFunc(new ArrayList<>(orgPermissionIds), reqParam.getGroupId(), reqParam.getOrgFuncs());
    }

    public List<OrgInfoDto> batchQueryOrgInfoNofuc(BatchQueryParam reqParam) {
        return commonOrgMapper.batchQueryOrgInfoNofuc(reqParam);
    }

    @Override
    public PageInfo<OrgInfoDto> queryOrgListByCondition(QueryOrgListConditionParam reqParam) {
        PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
        final List<OrgInfoDto> orgList = commonOrgMapper.queryOrgListByCondition(reqParam);
        PageInfo<OrgInfoDto> pageInfo = new PageInfo<>(orgList);
        return pageInfo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void syncCustBizType(SyncCustBizTypeParam reqParam) {
        System.out.println(reqParam.getGroupId());
        final OrgCustInfoBean orgCustInfo = commonOrgMapper.queryOrgCustInfoLock(reqParam);
        if (null == orgCustInfo) {
            LOGGER.warn("sync org not exist ,reqParam:{}.", reqParam);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.builder.OrgTree.org.not.exist"));
        }
        if (reqParam.getNewBizType() != null) {
            if (reqParam.getNewBizType().startsWith(",")) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(reqParam.getNewBizType());
                stringBuilder.deleteCharAt(0);
                reqParam.setNewBizType(stringBuilder.toString());
            }
        }
        commonOrgMapper.modifyOrgBizType(orgCustInfo.getGroupId(), reqParam.getNewBizType(), reqParam.getOperaterId());
    }

    @Override
    public String queryOrgBizType(Long orgId) {
        final OrgCustDto orgCustDto = getCustAndGroup(orgId);
        return orgCustDto.getBizType();
    }

    /**
     * 根据用户id和部门id查询部门信息有权限验证
     *
     * @param reqParam
     * @return
     */
    public DepInfoDto queryDepInfoByUserIdAndOrgId(QueryPermissionOrgInfoParam reqParam) {
        //权限验证
        final Set<Long> orgPermissionIds = permissionDomainService.getOrgsInPermission(reqParam.getUserId(), reqParam.getUserId(), OrgTypeEnum.ORGANIZATION);
        if (CollectionUtils.isEmpty(orgPermissionIds)) {
            LOGGER.warn("current user does not have org permission,reqParam:{}.", reqParam);
            throw new OrgException(OrgErrorCode.USER_PERMISION_NOT_ENOUGH_ERROR_CODE, I18nUtils.getMessage("org.service.impl.bizunit.user.permission.insufficient"));
        }
        return commonOrgMapper.queryDepInfoByUserIdAndOrgId(reqParam);
    }

    public void batchEnableOrg(List<Long> orgIds, Long userId, Integer status, Integer orgType) {
        if (!ObjectUtils.isEmpty(orgIds)) {
            commonOrgMapper.batchDisableOrg(userId, orgIds, status, orgType);
        }
    }

    @Override
    public List<DepartmentDto> findAllParentDepartment(Long depId) {
        List<DepartmentDto> result = Lists.newArrayList();
        //查询到本部门的信息
        BaseOrganization baseOrganization = new BaseOrganization();// organizationMapper.selectById(depId);
        BizUnitWithFuncDetailBean bizUnitWithFuncDetailBean = bizUnitMapper.queryBizUnitById(new QueryByIdReqParam(depId));
        findParentDepartment(bizUnitWithFuncDetailBean, result);
        return result;
    }

    /**
     * 查询父部门
     *
     * @param organization
     * @param result
     */
    private void findParentDepartment(BizUnitWithFuncDetailBean organization, List<DepartmentDto> result) {
        if (organization == null) {
            return;
        }
        DepartmentDto departMentDto = new DepartmentDto();
        BeanUtils.copyProperties(organization, departMentDto);
        setDeptDutyStaff(departMentDto);
        result.add(departMentDto);
        if (organization.getParentId() != null && organization.getParentId() != 0) {
            BaseOrganization parentOrganization = new BaseOrganization();// organizationMapper.selectById(organization.getParentId());
            BizUnitWithFuncDetailBean bizUnitWithFuncDetailBean = bizUnitMapper.queryBizUnitById(new QueryByIdReqParam(organization.getParentId()));

            findParentDepartment(bizUnitWithFuncDetailBean, result);
        }
    }

    /**
     * 设置部门负责人
     *
     * @param departMentDto
     */
    private void setDeptDutyStaff(DepartmentDto departMentDto) {
        if (!ObjectUtils.isEmpty(departMentDto.getDepDutyStaff())) {
            QueryByIdReqParam query = new QueryByIdReqParam();
            query.setId(Long.valueOf(departMentDto.getDepDutyStaff()));
            StaffInfoBean staffInfoBean = staffMapper.queryDetailInfoById(query);
            if (ObjectUtils.isEmpty(staffInfoBean) || !staffInfoBean.getState().equals(StateEnum.ENABLE.getCode())) {
                return;
            }
            DeptDutyStaffDto deptDutyStaffDto = new DeptDutyStaffDto();
            BeanUtils.copyProperties(staffInfoBean, deptDutyStaffDto);
            departMentDto.setDeptDutyStaffDto(deptDutyStaffDto);
        }

    }

    /**
     * 过滤集团ID 并去重
     *
     * @param orgTrees 组织树
     * @return 集团ID集合
     */
    private List<Long> filterGroupIds(final List<OrgTreeBean> orgTrees) {
        Set<Long> groupIdSet = new HashSet<>();
        for (OrgTreeBean orgTree : orgTrees) {
            groupIdSet.add(orgTree.getGroupId());
        }
        return new ArrayList<>(groupIdSet);
    }

    /**
     * 递归获取业务单元
     *
     * @param orgId 组织ID
     * @return 业务单元，若为空，返回集团对应的全局客户
     */
    private OrgCustDto getCustAndGroup(final Long orgId) {
        final List<OrgTreeBean> orgTreeBeans = commonOrgMapper.queryGroupOrgTreeNotContainDepByOrgId(orgId);
        if (CollectionUtils.isEmpty(orgTreeBeans)) {
            LOGGER.warn("group orgs is empty.");
            throw new OrgException(OrgErrorCode.ORG_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.service.impl.org.no.customer"));
        }

        final Map<Long, OrgTreeBean> idMap = multipleOrgTreeBuilder.convertToIdMap(orgTreeBeans);

        final OrgTreeBean currentOrg = idMap.get(orgId);

        return recursiveSearchCust(currentOrg, idMap);
    }

    /**
     * 递归搜索业务单元客商ID
     *
     * @param currentOrg 当前组织
     * @param idMap      组织ID Map
     * @return 客商ID + 集团ID
     */
    private OrgCustDto recursiveSearchCust(final OrgTreeBean currentOrg, final Map<Long, OrgTreeBean> idMap) {
        final Long orgId = currentOrg.getId();
        final Long custId = currentOrg.getCustId();
        final Long groupId = currentOrg.getGroupId();
        if (NumericUtil.greterThanZero(custId)) {
            final OrgTreeBean orgTreeBean = idMap.get(orgId);
            return new OrgCustDto(orgId, custId, groupId, orgTreeBean.getOrgCode(), orgTreeBean.getOrgName(), orgTreeBean.getBizType());
        }

        final OrgTreeBean group = idMap.get(groupId);
        return new OrgCustDto(idMap.get(-99).getId(), group.getCustId(), groupId, group.getOrgCode(), group.getOrgName(), group.getBizType());
    }

    /**
     * 当前业务单元具有法人公司职能
     *
     * @return <code>true</code> 有法人公司职能，<code>false</code>无法人公司职能
     */
    private Boolean bizUnitHasCorporationFunction(final SimpleBizUnitWithFuncBean bizUnitWithFunc) {
        final List<OrgFunctionDto> simpleFunctions = bizUnitWithFunc.getOrgFuncs();
        if (CollectionUtils.isEmpty(simpleFunctions)) {
            return Boolean.FALSE;
        }

        for (OrgFunctionDto simpleFunction : simpleFunctions) {
            final Integer functionType = simpleFunction.getFunctionType();
            if (functionType == FunctionTypeEnum.CORPORATION.getIndex()) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public void batchQueryOrgListByClue(List<BatchQueryOrgByClueParam> paramList) {
        if (CollectionUtils.isEmpty(paramList)) {
            return;
        }
        p:
        for (BatchQueryOrgByClueParam param : paramList) {
            if (StringUtils.isBlank(param.getOrgName()) || StringUtils.isBlank(param.getPhone())) {
                continue;
            }
            BatchQueryParam queryParam = new BatchQueryParam();
            queryParam.setOrgNameRightLike(param.getOrgName());
            queryParam.setOrgTypes(Arrays.asList(OrgTypeEnum.GROUP.getIndex()));
            List<BasicOrganizationDto> basicOrganizationDtoList = commonOrgMapper.batchQueryOrganizations(queryParam);
            if (basicOrganizationDtoList == null || basicOrganizationDtoList.size() <= 0) {
                continue;
            }
            a:
            for (BasicOrganizationDto basicOrganizationDto : basicOrganizationDtoList) {
                if (param.getPhone().equals(basicOrganizationDto.getPhone())) {
                    param.setGroupId(basicOrganizationDto.getId());

                    BatchQueryParam orgQueryParam = new BatchQueryParam();
                    orgQueryParam.setOrgTypes(Arrays.asList(OrgTypeEnum.ORGANIZATION.getIndex()));
                    orgQueryParam.setGroupId(basicOrganizationDto.getId());
                    List<BasicOrganizationDto> orgDtoList = commonOrgMapper.batchQueryOrganizations(orgQueryParam);
                    if (orgDtoList == null || orgDtoList.size() <= 0) {
                        continue a;
                    }
                    Long orgId = 0L;
                    for (BasicOrganizationDto orgDto : orgDtoList) {
                        if (orgDto.getMainOrgFlag() != null && orgDto.getMainOrgFlag() == BooleanEnum.TRUE.getCode()) {
                            orgId = orgDto.getId();
                        }
                    }
                    if (orgId <= 0) {
                        orgId = orgDtoList.get(0).getId();
                    }
                    param.setOrgId(orgId);
                    break a;
                }
            }
        }
    }
}
