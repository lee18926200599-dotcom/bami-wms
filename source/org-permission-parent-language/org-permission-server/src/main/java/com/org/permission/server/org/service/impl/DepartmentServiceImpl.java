package com.org.permission.server.org.service.impl;

import com.common.language.util.I18nUtils;
import com.org.permission.common.org.dto.StaffInfoDto;
import com.org.permission.common.org.param.*;
import com.org.permission.common.org.vo.DepWithStaffDetailVo;
import com.org.permission.server.domain.base.CodeDomainService;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.DepartmentBean;
import com.org.permission.server.org.bean.DepartmentStateBean;
import com.org.permission.server.org.bean.OrgTreeBean;
import com.org.permission.server.org.enums.OrgTypeEnum;
import com.org.permission.server.org.mapper.CommonOrgMapper;
import com.org.permission.server.org.mapper.DepartmentMapper;
import com.org.permission.server.org.mapper.StaffMapper;
import com.org.permission.server.org.service.DepartmentService;
import com.org.permission.server.org.service.StaffService;
import com.org.permission.server.org.service.verify.DepartmentVerify;
import com.common.base.enums.BooleanEnum;
import com.common.base.enums.StateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 部门服务实现类
 */
@Service(value = "departmentService")
public class DepartmentServiceImpl implements DepartmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);



    @Resource
    private CodeDomainService codeDomainService;

    @Resource
    private DepartmentVerify departmentVerify;

    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private CommonOrgMapper commonOrgMapper;

    @Resource
    private StaffMapper staffMapper;


    @Resource
    private StaffService staffService;




    @Override
    @Transactional
    public Long createDepartment(DepartmentReqParam reqParam) {

        departmentVerify.verify(reqParam);
        DepartmentBean newDepartment = new DepartmentBean();
        BeanUtils.copyProperties(reqParam, newDepartment);

        newDepartment.setCreatedDate(new Date());
        newDepartment.setDepCode(codeDomainService.getDepCode());
        newDepartment.setMainOrgFlag(BooleanEnum.FALSE.getCode());

        if (newDepartment.getState() == null) {
            newDepartment.setState(StateEnum.CREATE.getCode());
        }
        departmentMapper.addDepartment(newDepartment);
        final Long depId = newDepartment.getId();
        return depId;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void updateDepartment(DepartmentReqParam reqParam) {
        QueryByIdReqParam param = new QueryByIdReqParam();
        param.setId(reqParam.getId());
        //查看部门是否已经存在
        DepWithStaffDetailVo depWithStaffDetailVo = departmentMapper.queryDepInfoById(param);
        String newDepName = reqParam.getDepName();
        if (depWithStaffDetailVo != null) {
            //查看同一个业务单元下面部门名称是否重复
            List<DepWithStaffDetailVo> listDep = new ArrayList();
            listDep = departmentMapper.queryDepNameByBuid(reqParam.getParentBUId(), newDepName);
            for (int i = 0; i < listDep.size() - 1; i++) {
                if (newDepName == listDep.get(i).getDepName() && !reqParam.getId().equals(depWithStaffDetailVo.getId())) {
                    throw new OrgException(OrgErrorCode.CODE_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.service.impl.department.sameorg.name.exist"));
                }
            }
            departmentMapper.updateDepartment(reqParam);

        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void enableDepartment(EnableOperateParam reqParam) {
        final Long depId = reqParam.getId();
        final DepartmentStateBean orgStateBean = departmentMapper.queryDepStateByIdLock(depId);

        if (Objects.equals(orgStateBean.getDeletedFlag(), BooleanEnum.TRUE.getCode())) {
            LOGGER.warn("deleted department can not enable,depId:{}.", depId);
            throw new OrgException(OrgErrorCode.DELETED_CAN_NOT_OPERATE_ERROR_CODE, I18nUtils.getMessage("org.service.impl.department.org.deleted"));
        }
        final Integer enableState = reqParam.getState();
        if (StateEnum.ENABLE.getCode() == enableState) {
            if (StateEnum.ENABLE.getCode() == orgStateBean.getState()) {
                LOGGER.warn("repation enbale department ,depId:{}.", depId);
                return;
            }
            commonOrgMapper.enableOrg(reqParam.getUserId(), depId, new Date());
        }
        if (StateEnum.DISABLE.getCode() == enableState) {
            if (StateEnum.DISABLE.getCode() == orgStateBean.getState()) {
                LOGGER.warn("repation stop department  ,depId:{}.", depId);
                return;
            }

            if (StateEnum.CREATE.getCode() == orgStateBean.getState()) {
                LOGGER.warn("un enable department can not stop ,depId:{}.", depId);
                throw new OrgException(OrgErrorCode.UNENABLE_BIZ_UNIT_CAN_NOT_STOP_ERROR_CODE,I18nUtils.getMessage("org.service.impl.department.unenable.cannot.disable"));
            }


            //检查该部门下面是否有人，有人非删除状态，部门不可停用。
            StaffForCrmParam staffForCrmParam = new StaffForCrmParam();
            staffForCrmParam.setDepId(reqParam.getId());
            List<StaffInfoDto> staffInfoDtos = staffMapper.queryStaffForCrm(staffForCrmParam);
            if (!ObjectUtils.isEmpty(staffInfoDtos)) {
                throw new OrgException(OrgErrorCode.QUOTED_NOT_CAN_DELETE_ERROR_CODE, I18nUtils.getMessage("org.service.impl.department.have.staff.cannot.disable"));
            } else {
                List<Long> listDeps = new ArrayList<>();
                listDeps.add(depId);
                commonOrgMapper.batchDisableOrg(reqParam.getUserId(), listDeps, StateEnum.DISABLE.getCode(), OrgTypeEnum.DEPARTMENT.getIndex());
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void disableDepByBUId(Long userId, Long buId) {
        final List<Long> enableDepIds = departmentMapper.queryEnableDepByBuIdLock(buId);
        if (CollectionUtils.isEmpty(enableDepIds)) {
            return;
        }
        commonOrgMapper.batchDisableOrg(userId, enableDepIds, StateEnum.DISABLE.getCode(), OrgTypeEnum.DEPARTMENT.getIndex());

        staffService.disableStaffByBUId(buId, userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void deleteDepartment(KeyOperateParam reqParam) {
        final Long depId = reqParam.getId();
        final DepartmentStateBean depStateBean = departmentMapper.queryDepStateByIdLock(depId);

        judgeDepDeleteableState(depStateBean, reqParam.getId());

        commonOrgMapper.deleteOrgById(depId, reqParam.getUserId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void deleteDepartmentByBuId(Long buId, Long userId) {
        final List<DepartmentStateBean> depStateBeans = departmentMapper.queryDepStateByBuIdLock(buId);
        if (CollectionUtils.isEmpty(depStateBeans)) {
            return;
        }

        for (DepartmentStateBean depStateBean : depStateBeans) {
            judgeDepDeleteableState(depStateBean, depStateBean.getId());
        }

        departmentMapper.deleteDepByBuId(buId, userId);
    }

    @Override
    public DepWithStaffDetailVo queryDepDetail(QueryByIdReqParam reqParam) {
        return departmentMapper.queryDepWithStaffInfoById(reqParam.getId());
    }

    @Override
    public List<OrgTreeBean> queryDepTreeByBUId(QueryByIdReqParam reqParam) {
        return departmentMapper.queryDepTreeByBUId(reqParam);
    }

    /**
     * 判断部门状态是否可删除
     *
     * @param depStateBean   部门状态信息
     * @param reqDeleteDepId 请求删除部门ID
     */
    private void judgeDepDeleteableState(final DepartmentStateBean depStateBean, Long reqDeleteDepId) {

        if (depStateBean == null) {
            LOGGER.warn("delete department not exist,depId:{}.", reqDeleteDepId);
            throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.service.impl.department.not.exist"));
        }

        final Long depId = depStateBean.getId();
        // 引用状态1期没有进行维护
        if (depStateBean.getQuotedFlag() == BooleanEnum.TRUE.getCode()) {
            LOGGER.warn("already quotedFlag departmeng not support delete ,depId:{}.", depId);
            throw new OrgException(OrgErrorCode.QUOTED_NOT_CAN_DELETE_ERROR_CODE, I18nUtils.getMessage("org.service.impl.department.used.cannot.delete"));
        }

        if (Objects.equals(depStateBean.getDeletedFlag() ,BooleanEnum.TRUE.getCode())) {
            LOGGER.warn("already deleted departmeng not support delete ,depId:{}.", depId);
            return;
        }

        if (depStateBean.getState() == StateEnum.ENABLE.getCode()) {
            LOGGER.warn("already start using departmeng not support delete ,depId:{}.", depId);
            throw new OrgException(OrgErrorCode.ENABLED_STATE_CAN_NOT_DELETE_ERROR_CODE, I18nUtils.getMessage("org.service.impl.department.undisable.cannot.delete"));
        }

        if (depStateBean.getState() == StateEnum.DISABLE.getCode()) {
            final int countStaff = staffMapper.countDepStaff(depId);
            if (countStaff > 0) {
                LOGGER.warn("departmeng has staff not support delete ,depId:{}.", depId);
                throw new OrgException(OrgErrorCode.HAVE_STAFF_DEP_NOT_CAN_DELETE_ERROR_CODE, I18nUtils.getMessage("org.service.impl.department.disable.have.staff.cannot.delete"));
            }
        }
    }


    /**
     * 根据部门id获得部门的简要信息
     *
     * @param param
     * @return
     */
    public DepWithStaffDetailVo queryDepInfoById(QueryByIdReqParam param) {
        return departmentMapper.queryDepInfoById(param);
    }

    /**
     * 递归查询子部门
     *
     * @param depId
     * @return
     */
    private List<Integer> queryChildDepByDepId(Integer depId) {
        List<Integer> depIdList = new ArrayList<>();
        List<Integer> childIds = departmentMapper.queryChildDepByDepId(depId);
        //当子部门存在的时候
        if (!childIds.isEmpty()) {
            //添加当前的子部门
            depIdList.addAll(childIds);
            //遍历查询子部门是否有孙部门
            for (int i = 0; i < childIds.size() - 1; i++) {
                queryChildDepByDepId(childIds.get(i));
            }
        }
        depIdList.add(depId);
        return depIdList;
    }

    /**
     * 根据部门id集合查询用户id集合
     *
     * @param depIdlist
     * @return
     */
    private List<Long> queryStaffByDepIds(List<Long> depIdlist) {
        List<Long> list = new ArrayList();
        list = staffMapper.queryStaffByDepIds(depIdlist);
        return list;
    }

    /**
     * 根据业务单元Id查询部门简要信息
     *
     * @param buId 业务单元Id
     * @return
     */
    @Override
    public List<DepWithStaffDetailVo> queryDepInfoByBuId(Long buId) {
        return departmentMapper.queryDepInfoByBuId(buId);
    }
}
