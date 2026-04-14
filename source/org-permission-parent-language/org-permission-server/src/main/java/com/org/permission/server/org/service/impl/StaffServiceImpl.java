package com.org.permission.server.org.service.impl;

import com.common.base.enums.BooleanEnum;
import com.common.base.enums.StateEnum;
import com.common.framework.execption.BizException;
import com.common.framework.user.FplUserUtil;
import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.common.util.util.AssertUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.*;
import com.org.permission.common.org.param.*;
import com.org.permission.common.org.vo.DepWithStaffDetailVo;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.server.domain.base.CodeDomainService;
import com.org.permission.server.domain.permission.PermissionDomainService;
import com.org.permission.server.domain.user.UserDomainService;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.*;
import com.org.permission.server.org.builder.OrgTreeBuilder;
import com.org.permission.server.org.dto.param.BatchOpParam;
import com.org.permission.server.org.dto.param.BindingStaffParam;
import com.org.permission.server.org.dto.param.QueryArchiveListParam;
import com.org.permission.server.org.enums.EmploymentTypeEnum;
import com.org.permission.server.org.enums.OrgStateEnum;
import com.org.permission.server.org.enums.OrgTypeEnum;
import com.org.permission.server.org.enums.RegistSourceEnum;
import com.org.permission.server.org.mapper.*;
import com.org.permission.server.org.service.StaffService;
import com.org.permission.server.org.util.StaffUtil;
import com.org.permission.server.org.vo.ArchiveInfoVo;
import com.org.permission.server.utils.NumericUtil;
import com.usercenter.client.feign.UserStaffMapFeign;
import com.usercenter.common.dto.UserStaffMapDto;
import com.usercenter.common.dto.request.UserStaffMapFindListReq;
import com.usercenter.common.dto.request.UserStaffMapFindOneReq;
import jodd.util.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

/**
 * 人员服务实现，支持多人员类别
 */
@Service("staffService")
public class StaffServiceImpl implements StaffService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StaffServiceImpl.class);
    @Value(value = "${base_org_staff_code_biz_code}")
    private String staffCodeBizCode;

    @Resource
    private StaffMapper staffMapper;
    @Resource
    private StaffDutyMapper staffDutyMapper;
    @Resource
    private CodeDomainService codeDomainService;
    @Resource
    private CommonOrgMapper commonOrgMapper;
    @Resource
    private OrgTreeBuilder orgTreeBuilder;
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private UserDomainService userDomainService;
    @Resource
    private StaffBindingRelationMapper staffBindingRelationMapper;
    @Resource
    private PermissionDomainService permissionDomainService;
    @Resource
    private StaffTypeMapper staffTypeMapper;
    @Resource
    private UserStaffMapFeign userStaffMapFeign;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public Long createStaff(CreateStaffInfoParam reqParam) throws OrgException {
        AssertUtils.isNotNull(reqParam.getDepId(), I18nUtils.getMessage("org.service.impl.staff.select.department"));
        DepWithStaffDetailVo depWithStaffDetailVo = departmentMapper.queryDepInfoById(new QueryByIdReqParam(reqParam.getDepId()));
        AssertUtils.isNotNull(depWithStaffDetailVo, I18nUtils.getMessage("org.service.impl.staff.department.not.exist"));
        //人员新增查看姓名是否唯一
        String realname = reqParam.getRealname();
        Long unique = staffMapper.queryStaffUniqueByRealname(realname, depWithStaffDetailVo.getGroupId(), reqParam.getCertificateNo());
        if (!ObjectUtils.isEmpty(unique) && unique > 0) {
            throw new OrgException(OrgErrorCode.ORG_SYSTEM_ERROR_CODE, realname + I18nUtils.getMessage("org.service.impl.staff.name.exist"));
        }

        //查看上级是否冲突
        conflictDirectSuperVisor(reqParam.getDirectSupervisor());

        // 新增人员，并获取自增主键
        StaffBean staffBean = new StaffBean();
        BeanUtils.copyProperties(reqParam, staffBean);
        staffBean.setGroupId(depWithStaffDetailVo.getGroupId());
        staffBean.setOrgId(depWithStaffDetailVo.getParentBUId());
        // 如果未赋值，取默认值
        if (staffBean.getState() == null) {
            staffBean.setState(StateEnum.CREATE.getCode());
        }
        if (staffBean.getRegistSource() == null) {
            staffBean.setRegistSource(RegistSourceEnum.BOSS.getValue());
        }
        Boolean updateCodeFlag = StringUtil.isBlank(staffBean.getStaffCode());
        if (!updateCodeFlag) {
            // 不为空验证唯一
            Long existStaffId = staffMapper.queryStaffUniqueByStaffCode(staffBean.getStaffCode(), staffBean.getGroupId());
            if (existStaffId != null && existStaffId.compareTo(0L) > 0) {
                throw new OrgException(OrgErrorCode.ORG_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.service.impl.staff.code.exist"));
            }
        }
        staffBean.setCreatedDate(new Date());
        staffBean.setCreatedBy(FplUserUtil.getUserId());
        staffBean.setCreatedName(FplUserUtil.getUserName());
        LOGGER.debug("create staff to db:{}.", staffBean);
        staffMapper.createStaff(staffBean);
        final Long staffId = staffBean.getId();
        if (updateCodeFlag) {
            String staffCode = "";
            if (staffId.compareTo(9999L) > 0) {
                staffCode = "RY" + staffId;
            } else {
                staffCode = "RY" + String.format("%04d", staffId);
            }
            staffMapper.updateStaffCodeById(staffId, staffCode);
        }
        // 构建人员任职信息
        List<StaffDutyBean> staffDutyBeans = buildeStaffDuty(reqParam.getStaffDuty(), staffId);
        if (!CollectionUtils.isEmpty(staffDutyBeans)) {// 非空，新增人员任职信息
            staffDutyBeans.stream().forEach(s -> {
                s.setGroupId(depWithStaffDetailVo.getGroupId());
                s.setOrgId(depWithStaffDetailVo.getParentBUId());
            });
            staffDutyMapper.batchCreatStaffDutyInfo(staffDutyBeans);
        }
        return staffId;
    }

    @Override
    public StaffInfoBean queryDetailInfoById(QueryByIdReqParam reqParam) {
        return staffMapper.queryStaffWithDuty(reqParam.getId());
    }

    @Override
    public String queryStaffName(Long staffId) {
        return staffMapper.queryStaffName(staffId);
    }

    @Override
    @Transactional
    public void modifyStaff(ModifyStaffInfoDto reqParam) {
        //添加人员修改人员姓名不重复校验
        String realname = reqParam.getRealname();
        Long staffId = staffMapper.queryStaffUniqueByRealname(realname, reqParam.getGroupId(), reqParam.getCertificateNo());
        if (NumericUtil.greterThanZero(staffId)) {
            if (!staffId.equals(reqParam.getId())) {
                throw new OrgException(OrgErrorCode.NAME_CONFLICT_ERROR_CODE, I18nUtils.getMessage("org.service.impl.staff.name.exist"));
            }
        }
        //直属上级你我互斥
        if (reqParam.getDirectSupervisor() != null) {
            StaffWithDutyInfoDto staffWithDutyInfoDto = new StaffWithDutyInfoDto();
            staffWithDutyInfoDto.setId(reqParam.getDirectSupervisor());
            List<StaffInfoDto> staffInfoDtos = staffMapper.queryStaffByPojo(staffWithDutyInfoDto);
            if (staffInfoDtos != null) {
                for (int i = 0; i < staffInfoDtos.size(); i++) {
                    AssertUtils.isTrue(!Objects.equals(staffInfoDtos.get(i).getDirectSupervisorId(), reqParam.getId()), I18nUtils.getMessage("org.service.impl.staff.parent.mutex"));
                }
            }
        }
        AssertUtils.isNotNull(reqParam.getDepId(), I18nUtils.getMessage("org.service.impl.staff.select.department"));
        DepWithStaffDetailVo depWithStaffDetailVo = departmentMapper.queryDepInfoById(new QueryByIdReqParam(reqParam.getDepId()));
        AssertUtils.isNotNull(depWithStaffDetailVo, I18nUtils.getMessage("org.service.impl.staff.department.not.exist"));
        // 修改人员基础信息
        reqParam.setModifiedBy(FplUserUtil.getUserId());
        reqParam.setModifiedName(FplUserUtil.getUserName());
        reqParam.setModifiedDate(new Date());
        staffMapper.modifyStaff(reqParam);
        // 删除原人员所有任职信息
        staffDutyMapper.batchDeleteStaffyInfo(reqParam.getId());
        // 构建人员新任职信息
        List<StaffDutyBean> staffDutyBeans = buildeStaffDuty(reqParam.getStaffDuty(), reqParam.getId());
        if (!CollectionUtils.isEmpty(staffDutyBeans)) {// 非空，新增人员任职信息
            staffDutyBeans.stream().forEach(s -> {
                s.setGroupId(depWithStaffDetailVo.getGroupId());
                s.setOrgId(depWithStaffDetailVo.getParentBUId());
                s.setCreatedBy(FplUserUtil.getUserId());
            });
            staffDutyMapper.batchCreatStaffDutyInfo(staffDutyBeans);
        }
    }

    @Override
    @Transactional
    public void modifyStateOp(ModifyOperateParam reqParam) {
        final Long staffId = reqParam.getId();
        StaffStateBean staffState = staffMapper.queryStaffStateByIdLock(staffId);
        if (staffState == null) {
            LOGGER.warn("enable op staff not exist,staffId:{}.", staffId);
            throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.service.impl.staff.not.exist"));
        }
        if (!OrgStateEnum.canOp(staffState.getState(), reqParam.getState())) {// 验证请求状态是否允许操作
            throw new OrgException(OrgErrorCode.STATE_CANNOT_OP_ERROR_CODE, I18nUtils.getMessage("org.service.impl.staff.state.cannot.modify"));
        }
        //设置user_id为映射中的user_id
        StaffUtil.setUserId(staffState);
        Set<Long> set = new HashSet();
        set.add(staffState.getUserId());
        if (StateEnum.DISABLE.getCode() == reqParam.getState()) {// 请求停用人员
            //写入任职信息表，任职结束时间
            if (reqParam.getEndDate() != null) {
                staffDutyMapper.UpdateStaffDutyInfoEndDate(staffId, reqParam.getEndDate());
            }
            if (NumericUtil.greterThanZero(staffState.getUserId())) {// 停用人员时，按需停用用户（用户中心）
                userDomainService.disabled(staffState.getUserId());
            }
        }
        if (Objects.equals(reqParam.getDeletedFlag(), BooleanEnum.TRUE.getCode())) {// 请求删除人员
            if (NumericUtil.greterThanZero(staffState.getUserId())) {// 删除人员时，按需删除用户（用户中心）
                userDomainService.delete(staffState.getUserId());
            }
        }
        reqParam.setOpTime(new Date());
        staffMapper.modifyStaffState(reqParam);
    }

    public void batchEnableStaff(Long userId, List<Long> staffIds, Integer state) {
        staffMapper.batchEnableStaff(userId, staffIds, state);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public GeneratorStaffDto generateUser(GenerateUserParam reqParam) {
        final List<Long> staffIds = reqParam.getStaffIds();
        final List<RegistStaffDto> staffs = staffMapper.queryStaffsByIdLock(staffIds);
        GeneratorStaffDto generatorStaffDto = new GeneratorStaffDto();
        List<RegistStaffDto> fiallist = new ArrayList();
        List<RegistStaffDto> alreadyList = new ArrayList();
        List<RegistStaffDto> successList = new ArrayList<>();

        for (RegistStaffDto staff : staffs) {
            //1.临时工不能生成用户
            if (staff.getEmploymentType() == EmploymentTypeEnum.TEMP.getCode()) {
                staff.setFailMsg(I18nUtils.getMessage("org.service.impl.staff.temp.cannot.create.user"));
                fiallist.add(staff);
                continue;
            }
            //2.如果是已经生成用户的人员不做任何操作
            Long userId = staff.getUserId();
            if (!NumericUtil.nullOrlessThanOrEqualToZero(userId)) {
                alreadyList.add(staff);
                continue;
            } else {
                try {
                    //3.未启用的人员不能生成用户
                    if (!Objects.equals(staff.getState(), StateEnum.ENABLE.getCode())) {
                        staff.setFailMsg(I18nUtils.getMessage("org.service.impl.staff.unenable.or.disable.cannot.create.user"));
                        fiallist.add(staff);
                        continue;
                    }
                    //4.调用用户接口，生成用户并且记录到成功的list中
                    StaffBean staffBean = new StaffBean();
                    BeanUtils.copyProperties(staff, staffBean);
                    OrgUser orgUser = userDomainService.staffGenerateUser(staffBean, reqParam.getOperaterId());
                    staff.setUserId(orgUser.getUserId());
                    staff.setInitialPassword(orgUser.getPassword());
                    //业务单元名字
                    String orgName = commonOrgMapper.queryOrgNameByOrgId(staff.getOrgId());
                    staff.setOrgName(orgName);
                    successList.add(staff);
                } catch (Exception ex) {
                    staff.setFailMsg(ex.getMessage());
                    fiallist.add(staff);
                    continue;
                }
            }
        }
        generatorStaffDto.setFailStaffList(fiallist);
        generatorStaffDto.setSuccceedStaffList(successList);
        //根据成功的list批量更新staff表中的userId
        if (!CollectionUtils.isEmpty(successList)) {
            staffMapper.updataUserId(successList);
        }
        return generatorStaffDto;
    }

    @Override
    @Transactional
    public void deleteStaff(KeyOperateParam reqParam) {
        final Long staffId = reqParam.getId();
        System.out.println("删除时间开始" + new Date());
        final StaffStateBean staffStateBean = staffMapper.queryStaffStateByIdLock(staffId);
        if (staffStateBean == null) {
            LOGGER.warn("delete staff not exist,staffId:{}.", staffId);
            throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.service.impl.staff.not.exist"));
        }
        final Integer currentStatus = staffStateBean.getState();
        if (!OrgStateEnum.canDel(currentStatus)) {
            throw new OrgException(OrgErrorCode.ENABLED_STATE_CAN_NOT_DELETE_ERROR_CODE, I18nUtils.getMessage("org.service.impl.staff.state.cannot.delete"));
        }
        // 新增:人员删除，用户也删除
        if (NumericUtil.greterThanZero(staffStateBean.getUserId())) {
            userDomainService.disabled(staffStateBean.getUserId());
            userDomainService.delete(staffStateBean.getUserId());
        }
        staffMapper.deleteStaffById(staffId, staffStateBean.getUserId());
        staffDutyMapper.batchDeleteStaffyInfo(staffId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void bindingStaff(BindingStaffParam reqParam) {
        reqParam.setOpTime(new Date());
        staffBindingRelationMapper.bindingStaff(reqParam);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void unbindingStaff(BatchOpParam reqParam) {
        staffBindingRelationMapper.unbindingRelation(reqParam);
    }

    /**
     * @return true集团内身份证号重复
     */
    public Boolean validateCardNoExsit(QueryStaffListReqParam param) {
        //校验集团内身份证号是否重复
        List<StaffInfoBean> cardNoBeans = staffMapper.validateCardNoExsit(param);
        if (!ObjectUtils.isEmpty(cardNoBeans)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public PageInfo<StaffInfoBean> queryStaffList(QueryStaffListReqParam reqParam) {
        if (!ObjectUtils.isEmpty(reqParam.getIsUser())) {
            //先根据条件查出来所有记录:此处可优化,只查id
            List<Long> staffIds = staffMapper.queryStaffIdList(reqParam);
            //从这些id中查出已经生成用户的(未删除的)
            RestMessage<List<UserStaffMapDto>> userStaffMapResult = userStaffMapFeign.findList(UserStaffMapFindListReq.createStaffQuery(staffIds));
            if (userStaffMapResult.isSuccess() && !ObjectUtils.isEmpty(userStaffMapResult.getData())) {
                reqParam.setIsUserStaffIds(userStaffMapResult.getData().stream().map(userStaffMapDTO -> userStaffMapDTO.getStaffId().intValue()).collect(Collectors.toList()));
            }
        }
        PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize(), reqParam.getOrderBy());
        final List<StaffInfoBean> staffBeans = staffMapper.queryStaffList(reqParam);
        PageInfo<StaffInfoBean> pageInfo = new PageInfo<>(staffBeans);
        if (staffBeans.size() > 0) {
            for (int i = 0; i < staffBeans.size(); i++) {
                Long staffId = staffBeans.get(i).getId();
                List<StaffDutyInfoBean> staffDutyInfoBeans = staffDutyMapper.queryDutyInfoListByStaffId(staffId);
                staffBeans.get(i).setStaffDuties(staffDutyInfoBeans);
            }
            StaffUtil.setUserId(staffBeans);
            return pageInfo;
        } else {
            return pageInfo;
        }
    }

    @Override
    public PageInfo<DepartmentStaffDto> getSimpleStaffList(GetSimpleStaffListReq getStaffSimpleListReq) {
        //初始化参数
        //默认查询启用状态的
        if (ObjectUtils.isEmpty(getStaffSimpleListReq.getState())) {
            getStaffSimpleListReq.setState(StateEnum.ENABLE.getCode());
        }
        BaseDepartmentStaff query = new BaseDepartmentStaff();
        BeanUtils.copyProperties(getStaffSimpleListReq, query);
        // todo
        PageInfo<DepartmentStaffDto> pageInfo = new PageInfo<>();
        return pageInfo;
    }

    @Override
    public PageInfo<WarehouseBindingStaffInfoBean> queryBindingStaff(QueryBindingStaffParam reqParam) {
        PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
        final List<WarehouseBindingStaffInfoBean> staffBeans = staffBindingRelationMapper.queryBindingStaff(reqParam);
        PageInfo<WarehouseBindingStaffInfoBean> pageInfo = new PageInfo<>(staffBeans);
        if (staffBeans.size() > 0) {
            for (int i = 0; i < staffBeans.size(); i++) {
                Long staffId = staffBeans.get(i).getId();
                List<StaffDutyInfoBean> staffDutyInfoBeans = staffDutyMapper.queryDutyInfoListByStaffId(staffId);
                staffBeans.get(i).setStaffDuties(staffDutyInfoBeans);
            }
        }
        return pageInfo;
    }

    @Override
    public PageInfo<WarehouseBindingStaffInfoBean> queryNotBindingStaff(QueryBindingStaffParam reqParam) {
        PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
        final List<WarehouseBindingStaffInfoBean> staffBeans = staffBindingRelationMapper.queryNotBindingStaff(reqParam);
        StaffUtil.setUserId(staffBeans);
        PageInfo<WarehouseBindingStaffInfoBean> pageInfo = new PageInfo<>(staffBeans);
        return pageInfo;
    }

    @Override
    public StaffInfoBean queryStaffWithDuty(QueryByIdReqParam reqParam) {
        StaffInfoBean staffInfoBean = staffMapper.queryStaffWithDuty(reqParam.getId());
        StaffUtil.setUserId(staffInfoBean, StaffUtil.ID_FILED_ID, StaffUtil.USER_ID_FILED_USER_ID);
        return staffInfoBean;
    }

    @Override
    public PageInfo<StaffInfoBean> recurseQueryStaffs(QueryStaffsReqParam reqParam) {
        final Long orgId = reqParam.getOrgId();
        final OrgTypeBean orgBean = commonOrgMapper.queryEnableOrgTypeByOrgId(orgId);
        if (orgBean == null) {
            LOGGER.warn("recurse query staffs, org not exist ,orgId:{}.", orgId);
            throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.service.impl.staff.belong.org.not.exist"));
        }
        //如果传进来的userId不为空，掉用接口获取到对应的人员id
        Long userId = reqParam.getUserId();
        Long staffId = null;
        if (!ObjectUtils.isEmpty(userId)) {
            RestMessage<UserStaffMapDto> userStaffMapResult = userStaffMapFeign.findOne(UserStaffMapFindOneReq.createUserQuery(userId));
            if (userStaffMapResult.isSuccess() && userStaffMapResult.getData() != null) {
                staffId = userStaffMapResult.getData().getStaffId();
                if (staffId == null) {
                    staffId = -1L;
                }
                reqParam.setUserId(null);
            }
        }
        final Integer orgType = orgBean.getOrgType();
        //传进来的是集团id
        if (orgType == OrgTypeEnum.GROUP.getIndex()) {
            PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
            final List<StaffInfoBean> staffBeans = staffMapper.queryStaffsByGroupId(orgId, staffId, reqParam);
            PageInfo<StaffInfoBean> pageInfo = new PageInfo<>(staffBeans);
            return pageInfo;
        }
        Set<Long> depIds = new HashSet<>();
        if (orgType == OrgTypeEnum.DEPARTMENT.getIndex()) {
            depIds = Collections.singleton(orgId);
        }
        if (orgType == OrgTypeEnum.ORGANIZATION.getIndex()) {
            final List<OrgTreeBean> orgTreeBeans = commonOrgMapper.queryGroupEnableOrgTreeByOrgId(orgId);
            final List<Long> depIdList = orgTreeBuilder.filterUnderDepIds(orgTreeBeans, orgId);
            if (!CollectionUtils.isEmpty(depIdList)) {
                depIds.addAll(depIdList);
            }
        }
        if (CollectionUtils.isEmpty(depIds)) {
            return new PageInfo<>();
        }
        PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
        final List<StaffInfoBean> staffBeans = staffMapper.queryStaffsMainByDepIds(new ArrayList<>(depIds), staffId, reqParam);
        PageInfo<StaffInfoBean> pageInfo = new PageInfo<>(staffBeans);
        return pageInfo;
    }

    @Override
    public PageInfo<StaffInfoBean> queryCurrentOrgStaffs(final QueryStaffsReqParam reqParam) {
        final Long orgId = reqParam.getOrgId();
        final OrgTypeBean orgBean = commonOrgMapper.queryEnableOrgTypeByOrgId(orgId);
        if (orgBean == null) {
            LOGGER.warn("Query staffs, org not exist ,orgId:{}.", orgId);
            throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.service.impl.staff.belong.org.not.exist"));
        }
        PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
        final List<StaffInfoBean> staffBeans = staffMapper.pageableQueryStaffMainByOrgId(reqParam);
        PageInfo<StaffInfoBean> pageInfo = new PageInfo<>(staffBeans);
        if (staffBeans.size() > 0) {
            for (int i = 0; i < staffBeans.size(); i++) {
                Long staffId = staffBeans.get(i).getId();
                List<StaffDutyInfoBean> staffDutyInfoBeans = staffDutyMapper.queryDutyInfoListByStaffId(staffId);
                staffBeans.get(i).setStaffDuties(staffDutyInfoBeans);
            }
        }
        return pageInfo;
    }

    @Override
    public List<StaffInfoBean> batchQueryStaffInfo(BatchQueryParam queryParam) {
        List<Long> ids;
        if (!ObjectUtils.isEmpty(queryParam.getUserIds())) {
            RestMessage<List<UserStaffMapDto>> userStaffMapResult = userStaffMapFeign.findList(UserStaffMapFindListReq.createUserQuery(queryParam.getUserIds()));
            if (userStaffMapResult.isSuccess() && userStaffMapResult.getData() != null) {
                ids = userStaffMapResult.getData().stream().map(userStaffMapDTO -> userStaffMapDTO.getStaffId()).collect(Collectors.toList());
                if (ObjectUtils.isEmpty(ids)) {
                    ids.add(-1L);
                }
                queryParam.setIds(ids);
                queryParam.setUserIds(null);
            }
        }
        final List<StaffInfoBean> staffs = staffMapper.batchQueryStaffInfo(queryParam, queryParam.getStaffTypes(), queryParam.getUnitOrgIds());
        return CollectionUtils.isEmpty(staffs) ? Collections.emptyList() : staffs;
    }

    @Override
    public List<StaffInfoDto> batchQueryStaffBasicInfo(StaffBasicParam queryParam) {
        final List<StaffInfoDto> staffs = staffMapper.batchQueryStaffBasicInfo(queryParam);
        StaffUtil.setUserId(staffs);
        return CollectionUtils.isEmpty(staffs) ? Collections.emptyList() : staffs;
    }

    @Override
    public List<StaffInfoBean> queryStaffByDepId(final Long depId) {
        final List<StaffInfoBean> staffs = staffMapper.queryStaffByDepId(depId);
        return CollectionUtils.isEmpty(staffs) ? Collections.emptyList() : staffs;
    }

    @Override
    public Boolean judgeUserHasStaffType(final Long userId, final Integer typeId) {
        final StaffDutyBean staffDutyBean = staffDutyMapper.queryStaffDuty(userId, typeId);
        return Objects.nonNull(staffDutyBean);
    }

    @Override
    public Long queryStaffIdByUserId(Long userId) {
        return staffMapper.queryStaffIdByUserId(userId);
    }

    @Override
    public StaffInfoDto queryStaffInfoByUserId(Long userId) {
        return staffMapper.queryStaffInfoByUserId(userId);
    }

    /**
     * 构建人员任职信息，分别绑定人员类别
     *
     * @param dutyInfoDto 新增人员请求参数
     * @return 人员任职信息
     */
    private List<StaffDutyBean> buildeStaffDuty(final ModifyDutyInfoDto dutyInfoDto, final Long staffId) {
        if (dutyInfoDto == null) {
            return Collections.emptyList();
        }
        final String staffTypeId = dutyInfoDto.getStaffTypeId();
        if (StringUtils.isEmpty(staffTypeId)) {
            return Collections.emptyList();
        }
        final String[] split = staffTypeId.trim().split(",");
        if (split.length == 0) {
            return Collections.emptyList();
        }
        final List<StaffDutyBean> staffDutyBeans = new ArrayList<>(split.length);
        Arrays.asList(split).forEach(typeId -> {
            StaffDutyBean staffDutyBean = new StaffDutyBean();
            BeanUtils.copyProperties(dutyInfoDto, staffDutyBean);
            staffDutyBean.setStaffId(staffId);
            staffDutyBean.setStaffTypeBizCode("");// 前端传值，没有入库
            staffDutyBean.setStaffTypeId(Integer.valueOf(typeId));
            staffDutyBean.setCreatedDate(new Date());
            staffDutyBean.setGroupId(dutyInfoDto.getGroupId());
            staffDutyBean.setCreatedBy(FplUserUtil.getUserId());
            staffDutyBean.setCreatedName(FplUserUtil.getUserName());
            staffDutyBean.setStartDate(dutyInfoDto.getStartDate());
            staffDutyBean.setEndDate(dutyInfoDto.getEndDate());
            staffDutyBeans.add(staffDutyBean);
        });
        return staffDutyBeans;
    }

    @Override
    public Integer deleteUserIdByStaffId(Long staffId) {
        return staffMapper.deleteUserIdByStaffId(staffId);
    }

    @Override
    public Integer updateUserIdByStaffId(Long staffId, Long userId) {
        return staffMapper.updateUserIdByStaffId(staffId, userId);
    }

    @Override
    public List<StaffInfoBean> queryStaffsByOrgId(QueryAllStaffsInOrgReqParam reqParam) {
        final Long orgId = reqParam.getOrgId();
        final OrgTypeBean orgBean = commonOrgMapper.queryEnableOrgTypeByOrgId(orgId);
        if (orgBean == null) {
            LOGGER.warn("query staffs by org id ,org  not exist ,orgId:{}.", orgId);
            throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.service.impl.staff.org.not.exist"));
        }
        final Boolean permissionControl = reqParam.isPermissionControl();
        Set<Long> depIds = new HashSet<>();
        if (permissionControl) {// 需要权限控制
            Set<Long> depPermissonIds = permissionDomainService.getOrgsInPermission(reqParam.getUserId(), reqParam.getGroupId(), OrgTypeEnum.DEPARTMENT);
            if (CollectionUtils.isEmpty(depPermissonIds)) {
                LOGGER.warn("query staffs by org id ,user does not have dep permission ,reqParam:{}.", reqParam);
                throw new OrgException(OrgErrorCode.USER_PERMISION_NOT_ENOUGH_ERROR_CODE, I18nUtils.getMessage("org.service.impl.bizunit.user.permission.insufficient"));
            }
            final List<Long> buDeps = departmentMapper.queryOrgContainDepsByBUId(orgId);
            if (CollectionUtils.isEmpty(buDeps)) {
                LOGGER.warn("query staffs by org id ,org does not have deps ,reqParam:{}.", reqParam);
                throw new OrgException(OrgErrorCode.USER_PERMISION_NOT_ENOUGH_ERROR_CODE, I18nUtils.getMessage("org.service.impl.staff.org.nohave.department"));
            }
            for (Long depPermissonId : depPermissonIds) {
                if (buDeps.contains(depPermissonId)) {
                    depIds.add(depPermissonId);
                }
            }
        } else {
            final boolean cascaded = reqParam.isCascaded();
            if (cascaded) {
                final List<OrgTreeBean> orgTreeBeans = commonOrgMapper.queryGroupEnableOrgTreeByOrgId(orgId);
                depIds.addAll(orgTreeBuilder.filterUnderDepIds(orgTreeBeans, orgId));
            } else {
                final Integer orgType = orgBean.getOrgType();
                if (orgType == OrgTypeEnum.DEPARTMENT.getIndex()) {
                    depIds = new HashSet<>();
                    depIds.add(orgId);
                }
                if (orgType == OrgTypeEnum.ORGANIZATION.getIndex()) {
                    final List<Long> buDepIds = departmentMapper.queryDirectOrgContainDepsByBUIds(Collections.singletonList(orgId));
                    if (!CollectionUtils.isEmpty(buDepIds)) {
                        depIds.addAll(buDepIds);
                    }
                }
                if (orgType == OrgTypeEnum.GROUP.getIndex()) {
                    final List<Long> childeDepIds = departmentMapper.queryDirectOrgContainDepsByGroupId(orgId);
                    if (!CollectionUtils.isEmpty(childeDepIds)) {
                        depIds.addAll(childeDepIds);
                    }
                }
            }
        }
        if (CollectionUtils.isEmpty(depIds)) {
            return null;
        }
        return staffMapper.queryStaffsByDepIds(new ArrayList<>(depIds), reqParam);
    }

    @Override
    public void disableStaffByBUId(Long buId, Long userId) {
        final List<Long> staffUserIds = staffMapper.queryStaffUserIdsByBUId(buId);
        staffMapper.disableStaffsByBUId(buId, userId, new Date());
        if (!ObjectUtils.isEmpty(staffUserIds)) {
            userDomainService.batchDisableUser(staffUserIds, userId);
        }
    }

    @Override
    public PageInfo<ArchiveInfoVo> queryArchiveList(QueryArchiveListParam reqParam) {
        PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
        final List<ArchiveInfoVo> staffArchives = staffMapper.queryStaffArchive(reqParam);
        StaffUtil.setUserId(staffArchives);
        PageInfo<ArchiveInfoVo> pageInfo = new PageInfo<>(staffArchives);
        return pageInfo;
    }

    @Override
    public List<StaffWithDutyInfoDto> queryStaffByPojo(StaffWithDutyInfoDto staffWithDutyInfoDto) {
        if (!ObjectUtils.isEmpty(staffWithDutyInfoDto.getUserId())) {
            RestMessage<UserStaffMapDto> userStaffMapResult = userStaffMapFeign.findOne(UserStaffMapFindOneReq.createUserQuery(staffWithDutyInfoDto.getUserId()));
            if (userStaffMapResult.isSuccess() && !ObjectUtils.isEmpty(userStaffMapResult.getData())) {
                staffWithDutyInfoDto.setId(userStaffMapResult.getData().getStaffId());
                staffWithDutyInfoDto.setUserId(null);
            } else {
                staffWithDutyInfoDto.setId(-1L);
                staffWithDutyInfoDto.setUserId(null);
            }
        }
        List<StaffInfoDto> staffInfoDtos = staffMapper.queryStaffByPojo(staffWithDutyInfoDto);
        List<StaffWithDutyInfoDto> staffWithDutyInfoDtos = new ArrayList<>();
        if (!staffInfoDtos.isEmpty()) {
            for (int i = 0; i < staffInfoDtos.size(); i++) {
                StaffWithDutyInfoDto iter = new StaffWithDutyInfoDto();
                BeanUtils.copyProperties(staffInfoDtos.get(i), iter);
                staffWithDutyInfoDtos.add(iter);
            }
        }
        return staffWithDutyInfoDtos;
    }

    @Override
    public PageInfo<StaffInfoDto> queryStaffForCrm(StaffForCrmParam staffForCrmParam) {
        PageHelper.startPage(staffForCrmParam.getPageNum(), staffForCrmParam.getPageSize());
        List<StaffInfoDto> staffInfoDtos = staffMapper.queryStaffForCrm(staffForCrmParam);
        StaffUtil.setUserId(staffInfoDtos);
        PageInfo<StaffInfoDto> pageInfo = new PageInfo<>(staffInfoDtos);
        return pageInfo;
    }

    @Override
    public Map<Long, String> querySuperVisor(Long userId, Long staffId, Long groupId) {
        //1.查询用户的部门权限
        Set<Long> orgsInPermission = permissionDomainService.getOrgsInPermission(userId, groupId, OrgTypeEnum.DEPARTMENT);
        if (orgsInPermission == null || orgsInPermission.size() == 0) {
            LOGGER.info("用户权限不足");
            return null;
        }
        //2.根据部门id集合查询部门人员
        List<Long> depIds = new ArrayList<>(orgsInPermission.size());
        depIds.addAll(orgsInPermission);
        List<StaffInfoBean> staffs = staffMapper.queryStaffsByDepIds(depIds, null);
        if (staffs == null) {
            return null;
        }
        //3.根据人员id拼接map
        Map<Long, String> map = new HashMap<>();
        for (int i = 0; i < staffs.size(); i++) {
            StaffInfoBean staffInfoBean = staffs.get(i);
            //拼接直属上级
            String superVisor = staffInfoBean.getRealname();
            superVisor = superVisor + "(";
            superVisor = superVisor + staffInfoBean.getDepName();
            superVisor = superVisor + ")";
            map.put(staffs.get(i).getId(), superVisor);
        }
        //4.当修改的时候，不可以选择自己是直属上级
        if (staffId != null) {
            map.remove(map.get(staffId));
        }
        return map;
    }


    /**
     * 查询直属上级是否冲突
     *
     * @param directSuperVisor
     * @return true:冲突
     */
    private void conflictDirectSuperVisor(Long directSuperVisor) {
        //直属上级的处理
        if (NumericUtil.greterThanZero(directSuperVisor)) {
            QueryByIdReqParam queryByIdReqParam = new QueryByIdReqParam();
            queryByIdReqParam.setId(directSuperVisor);
            StaffInfoBean staffInfoBean = staffMapper.queryDetailInfoById(queryByIdReqParam);
            if (staffInfoBean != null) {
                if (NumericUtil.greterThanZero(staffInfoBean.getDirectSupervisorId())) {
                    if (directSuperVisor.equals(staffInfoBean.getDirectSupervisorId())) {
                        throw new BizException(I18nUtils.getMessage("org.service.impl.staff.parent.select.error"));
                    }
                }
            }
        }
    }


    private boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        if (str == null || "".equals(str)) {
            return false;
        }
        return str.matches("^1[0-9]{10}$");
    }

    /**
     * 验证身份证号码
     *
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */
    private boolean isIdCard(String idCard) {
        String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
        return Pattern.matches(regex, idCard);
    }


    private Boolean validateStaffType(String typeName, Long groupId) {
        Boolean flag = false;
        if (typeName != null) {
            String[] typeNames = typeName.split("#");
            for (String item : typeNames) {
                Integer typeId = staffTypeMapper.queryStaffTypeIdByTypeName(item, groupId);
                if (typeId != null && typeId != 0) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    @Override
    public List<StaffInfoDto> findStaffListByCondition(HashMap<String, Object> map) {
        return staffMapper.findStaffListByCondition(map);
    }

    /**
     * 查询人员绑定信息简要信息（根据关系ID和人员编码）
     *
     * @param reqParam
     * @return
     */
    @Override
    public WarehouseBindingStaffInfoBean queryBindingStaffBriefInfo(QueryBindingStaffParam reqParam) {
        WarehouseBindingStaffInfoBean staffInfoBean = staffBindingRelationMapper.queryBindingStaffBriefInfo(reqParam);
        return staffInfoBean;
    }
}
