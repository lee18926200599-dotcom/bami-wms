package com.org.permission.server.org.service.impl;

import com.common.language.util.I18nUtils;
import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.common.org.param.KeyOperateParam;
import com.org.permission.common.org.param.QueryByIdReqParam;
import com.org.permission.common.org.param.QueryStaffTypeTreeReqParam;
import com.org.permission.server.domain.base.CodeDomainService;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.StaffTypeBean;
import com.org.permission.server.org.bean.StaffTypeInfoBean;
import com.org.permission.server.org.bean.StaffTypeTreeBean;
import com.org.permission.server.org.bean.StateBean;
import com.org.permission.server.org.builder.StaffTypeTreeBuilder;
import com.org.permission.server.org.dto.param.StaffTypeParam;
import com.org.permission.server.org.mapper.GroupMapper;
import com.org.permission.server.org.mapper.StaffTypeMapper;
import com.org.permission.server.org.service.StaffTypeService;
import com.common.base.enums.BooleanEnum;
import com.common.base.enums.StateEnum;
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

import javax.annotation.Resource;
import java.util.*;

/**
 * 人员类别服务实现
 */
@Service(value = "staffTypeService")
public class StaffTypeServiceImpl implements StaffTypeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StaffTypeServiceImpl.class);

    @Resource
    private CodeDomainService codeDomainService;

    @Resource
    private StaffTypeMapper staffTypeMapper;

    @Resource
    private StaffTypeTreeBuilder staffTypeTreeBuilder;

    @Resource
    private GroupMapper groupMapper;

    @Value(value = "${base_org_staff_type_code_biz_code}")
    private String staffTypeCodeBizCode;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void createStaffType(StaffTypeParam reqParam) {
        Long parentId;
        if ((parentId = reqParam.getParentId()).longValue() != 1) { // 非根人员类别，验证上级人员类别是否为启用状态；
            final StateBean stateBean = staffTypeMapper.queryStaffTypeStateByIdLock(parentId);
            if (stateBean == null) {
                LOGGER.warn("add staff type, illegal parent staff type,{}.", parentId);
                throw new OrgException(OrgErrorCode.DATA_STATE_ERROR_CODE, I18nUtils.getMessage("org.service.impl.stafftype.parent.not.exist"));
            }

            if (stateBean.getState() != StateEnum.ENABLE.getCode()) {
                LOGGER.warn("add staff type, illegal parent staff type,{}.", parentId);
                throw new OrgException(OrgErrorCode.DATA_STATE_ERROR_CODE, I18nUtils.getMessage("org.service.impl.stafftype.parent.unenable"));
            }
        }
        if (reqParam.getTypeName() != null) {
            Integer typeId = staffTypeMapper.queryStaffTypeIdByTypeName(reqParam.getTypeName(), reqParam.getBelongOrg());
            if (!ObjectUtils.isEmpty(typeId)) {
                LOGGER.warn("add staff type, illegal parent staff type,{}.", parentId);
                throw new OrgException(OrgErrorCode.DATA_STATE_ERROR_CODE, I18nUtils.getMessage("org.service.impl.stafftype.exist"));
            }
        }

        final Long belongOrg = reqParam.getBelongOrg();
        groupMapper.queryGroupByIdLock(belongOrg);// 获取集团锁，保证唯一性

        final StaffTypeBean staffType = buildeNewStaffType(reqParam);
        staffTypeMapper.addStaffType(staffType);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public List<StaffTypeTreeBean> queryStaffTypeTree(QueryStaffTypeTreeReqParam reqParam) {
        return staffTypeMapper.queryStaffTypeTree(reqParam);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void updateStaffType(StaffTypeParam reqParam) {
        final StaffTypeBean oldStaffType = staffTypeMapper.queryStaffTypeByIdLock(reqParam.getId());
        if (oldStaffType == null) {
            LOGGER.warn("staff type not exist,param:{}.", reqParam);
            throw new OrgException(OrgErrorCode.DATA_STATE_ERROR_CODE, I18nUtils.getMessage("org.service.impl.stafftype.not.exist"));
        }
        if (Objects.equals(oldStaffType.getDeletedFlag(), BooleanEnum.TRUE.getCode())) {
            LOGGER.warn("staff type not exist,param:{}.", reqParam);
            throw new OrgException(OrgErrorCode.DATA_STATE_ERROR_CODE, I18nUtils.getMessage("org.service.impl.stafftype.not.exist"));
        }

        final Long belongOrg = reqParam.getBelongOrg();
        groupMapper.queryGroupByIdLock(belongOrg);// 获取集团锁，保证唯一性
        final String newTypeName = reqParam.getTypeName();
        final String oldTypeName = oldStaffType.getTypeName();

        if (!(oldTypeName.equals(newTypeName))) {
            verifyUnique(newTypeName, null, belongOrg);
        }
        try {
            staffTypeMapper.updateStaffType(reqParam, new Date());
        } catch (Exception ex) {// 捕获 Name 唯一索引异常
            LOGGER.warn("insert staff type,typeName conflict.", ex);
            throw new OrgException(OrgErrorCode.DATA_STATE_ERROR_CODE, I18nUtils.getMessage("org.service.impl.stafftype.name.exist"));
        }
    }

    /**
     * 验证人员类别唯一性
     *
     * @param typeName  人员类别名
     * @param bizCode   人员类别业务编码
     * @param belongOrg 所属集团
     */
    private void verifyUnique(final String typeName, final String bizCode, Long belongOrg) {
        final List<Long> typeIds = staffTypeMapper.queryStaffTypeByUnixCondition(typeName, bizCode, belongOrg);
        if (!CollectionUtils.isEmpty(typeIds)) {
            LOGGER.warn("add staff type,typeName conflict,{}.", typeName);
            throw new OrgException(OrgErrorCode.DATA_STATE_ERROR_CODE, I18nUtils.getMessage("org.service.impl.stafftype.name.exist"));
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void enableStaffType(EnableOperateParam reqParam) {
        final Long typeId = reqParam.getId();
        final StaffTypeBean typeBean = staffTypeMapper.queryStaffTypeByIdLock(typeId);
        if (typeBean == null) {
            LOGGER.warn("staff type not exist,param:{}.", reqParam);
            throw new OrgException(OrgErrorCode.DATA_STATE_ERROR_CODE, I18nUtils.getMessage("org.service.impl.stafftype.not.exist"));
        }
        if (Objects.equals(typeBean.getDeletedFlag(), BooleanEnum.TRUE.getCode())) {
            LOGGER.warn("staff type not exist,param:{}.", reqParam);
            throw new OrgException(OrgErrorCode.DATA_STATE_ERROR_CODE, I18nUtils.getMessage("org.service.impl.stafftype.not.exist"));
        }

        if (reqParam.getState() == StateEnum.DISABLE.getCode()) {
            if (typeBean.getState() == StateEnum.DISABLE.getCode()) {
                LOGGER.warn("reption disable staff type ,param:{}.", reqParam);
                return;
            }

            // 检索当前部门下的所有子部门
            final List<StaffTypeTreeBean> staffTypeBeans = staffTypeMapper.queryStaffTypeByOrg(typeBean.getBelongOrg());
            final Map<Long, List<StaffTypeTreeBean>> pIdMap = staffTypeTreeBuilder.convert2PIdMap(staffTypeBeans);
            List<Long> childTypes = new ArrayList<>();
            staffTypeTreeBuilder.recurseSearchChildTypes(typeId, pIdMap, childTypes);
            childTypes.add(typeId);
            staffTypeMapper.batchDisableStaffType(childTypes, reqParam.getUserId(), new Date());
        }
        if (reqParam.getState() == StateEnum.ENABLE.getCode()) {
            if (typeBean.getState() == StateEnum.ENABLE.getCode()) {
                LOGGER.warn("reption enable staff type ,param:{}.", reqParam);
                return;
            }
            staffTypeMapper.enableStaffType(reqParam, new Date());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void deleteStaffType(KeyOperateParam reqParam) {
        final Long staffTypeId = reqParam.getId();
        final StateBean stateBean = staffTypeMapper.queryStaffTypeStateByIdLock(staffTypeId);

        if (null == stateBean) {
            LOGGER.warn("delete staff type not exist ,staffType:{}.", staffTypeId);
            throw new OrgException(OrgErrorCode.DATA_NOT_EXIST_ERROR_CODE, I18nUtils.getMessage("org.service.impl.stafftype.not.exist"));
        }

        if (Objects.equals(stateBean.getDeletedFlag(), BooleanEnum.TRUE.getCode())) {
            LOGGER.warn("delete staff type already deleted ,staffType:{}.", staffTypeId);
            return;
        }

        if (stateBean.getState() != StateEnum.CREATE.getCode()) {
            LOGGER.warn("delete staff type already start using ,staffType:{}.", staffTypeId);
            throw new OrgException(OrgErrorCode.DATA_STATE_ERROR_CODE, I18nUtils.getMessage("org.service.impl.stafftype.state.uncreate.cannot.delete"));
        }
        staffTypeMapper.deleteStaffType(reqParam, new Date());
    }

    @Override
    public StaffTypeInfoBean queryStaffType(QueryByIdReqParam reqParam) {
        return staffTypeMapper.queryStaffTypeById(reqParam.getId());
    }

    public List<StaffTypeInfoBean> queryChildren(QueryByIdReqParam reqParam) {
        return staffTypeMapper.queryChildren(reqParam.getId(), reqParam.getState());
    }

    public Integer queryStaffTypeIdByTypeName(String typeName) {
        return staffTypeMapper.queryStaffTypeIdByTypeName(typeName, null);
    }

    /**
     * 构建新人员类别数据实体
     *
     * @param reqParam 新增人员类别请求参数
     * @return 新人员类别数据实体
     */
    private StaffTypeBean buildeNewStaffType(final StaffTypeParam reqParam) {
        String staffTypeCode = codeDomainService.getPlatformCode(staffTypeCodeBizCode);
        StaffTypeBean staffTypeBean = new StaffTypeBean();
        BeanUtils.copyProperties(reqParam, staffTypeBean);
        staffTypeBean.setState(StateEnum.CREATE.getCode());
        staffTypeBean.setCreatedDate(new Date());
        staffTypeBean.setTypeCode(staffTypeCode);
        staffTypeBean.setBizCode(staffTypeCode);
        staffTypeBean.setParentId(Objects.isNull(staffTypeBean.getParentId()) ? 0L : staffTypeBean.getParentId());
        return staffTypeBean;
    }
}