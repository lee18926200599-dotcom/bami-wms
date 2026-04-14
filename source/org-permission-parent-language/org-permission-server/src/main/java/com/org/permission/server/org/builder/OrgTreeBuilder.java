package com.org.permission.server.org.builder;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.OrgTreeBean;
import com.org.permission.common.org.dto.OrgTreeDto;
import com.org.permission.common.org.param.QueryOrgTreeReqParam;
import com.common.base.enums.BooleanEnum;
import com.org.permission.server.org.enums.OrgTreeLevelEnum;
import com.org.permission.server.org.enums.OrgTypeEnum;
import com.org.permission.server.utils.NumericUtil;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * 组织树构建器
 */
@Component(value = "orgTreeBuilder")
public class OrgTreeBuilder {
    @Resource
    private MultipleOrgTreeBuilder multipleOrgTreeBuilder;

    /**
     * 构建线性树
     *
     * @param orgTreeBeans 当前组织所属集团全部组织数据信息
     * @param reqParam     组织树查询请求参数
     * @return 组织树
     */
    public OrgTreeDto buildOrgTree(final List<OrgTreeBean> orgTreeBeans, final QueryOrgTreeReqParam reqParam) {
        // 树根
        OrgTreeBean rootBUBean = null;
        OrgTreeBean currentOrgBean = null;
        final Long orgId = reqParam.getId();

        // 业务单元缓存Map key=业务单元ID ，valut=业务单元组织信息
        final Map<Long, OrgTreeBean> bizUnitMap = new HashMap<>();
        // 部门缓存Map key=上级部门 | 所属业务单元 value=一个部门或业务单元下的部门
        final Map<Long, List<OrgTreeBean>> depsMap = new HashMap<>();
        // 业务单元缓存Map key=业务单元ID value=一个业务单元下的所有业务单元
        final Map<Long, List<OrgTreeBean>> busMap = new HashMap<>();
        for (final OrgTreeBean orgTreeBean : orgTreeBeans) {
            if (orgTreeBean.getId() == orgId.intValue()) {
                currentOrgBean = orgTreeBean;
            }

            if (Objects.equals(orgTreeBean.getMainOrgFlag(), BooleanEnum.TRUE.getCode())) {
                rootBUBean = orgTreeBean;
            }
            cacheOrg(orgTreeBean, bizUnitMap, busMap, depsMap, orgId);
        }
        if (currentOrgBean == null) {
            throw new OrgException(OrgErrorCode.DATA_STATE_ERROR_CODE, I18nUtils.getMessage("org.builder.OrgTree.org.not.exist"));
        }

        OrgTreeDto currentTreeDto = new OrgTreeDto();
        BeanUtils.copyProperties(currentOrgBean, currentTreeDto);

        final OrgTreeDto rootTreeDto = new OrgTreeDto();
        BeanUtils.copyProperties(rootBUBean, rootTreeDto);

        // 转换当前组织
        currentOrgBean = currentOrgBean.getOrgType() == OrgTypeEnum.GROUP.getIndex() ? rootBUBean : currentOrgBean;
        currentTreeDto = currentTreeDto.getOrgType() == OrgTypeEnum.GROUP.getIndex() ? rootTreeDto : currentTreeDto;

        if (reqParam.getLevel() == OrgTreeLevelEnum.UP_CONTAIN_DOWN.getLevel()) {
            final OrgTreeDto topBU = recursiveQueryParentBU(currentOrgBean, bizUnitMap, currentTreeDto, busMap, Boolean.TRUE);

            recursiveQueryChildBU(currentOrgBean, currentTreeDto, busMap, depsMap);
            return topBU;
        }
        if (reqParam.getLevel() == OrgTreeLevelEnum.ALL_DOWN.getLevel()) {
            recursiveQueryChildBU(currentOrgBean, currentTreeDto, busMap, depsMap);
            return currentTreeDto;
        }

        recursiveQueryChildBU(currentOrgBean, currentTreeDto, busMap, null);
        return currentTreeDto;
    }

    /**
     * 过滤下级部门集合
     *
     * @param orgTreeBeans 当前组织所属集团全部组织数据信息
     * @param orgId        当前组织ID
     * @return 下级DEP IDS
     */
    public List<Long> filterUnderDepIds(final List<OrgTreeBean> orgTreeBeans, final Long orgId) {
        final List<Long> depIds = new ArrayList<>();
        OrgTreeBean currentOrg = null;
        OrgTreeBean rootOrg = null;

        // 部门缓存Map key=上级部门 | 所属业务单元 value=一个部门或业务单元下的部门
        final Map<Long, List<OrgTreeBean>> depsMap = new HashMap<>();
        // 业务单元缓存Map key=业务单元ID value=一个业务单元下的所有业务单元
        Map<Long, List<OrgTreeBean>> busMap = new HashMap<>();
        for (final OrgTreeBean orgTreeBean : orgTreeBeans) {
            if (orgTreeBean.getId() == orgId.intValue()) {
                currentOrg = orgTreeBean;
            }
            if (orgTreeBean.getMainOrgFlag() == BooleanEnum.TRUE.getCode().intValue()) {
                rootOrg = orgTreeBean;
            }
            cacheOrg(orgTreeBean, null, busMap, depsMap, orgId);
        }

        if (currentOrg == null) {
            throw new OrgException(OrgErrorCode.DATA_STATE_ERROR_CODE, I18nUtils.getMessage("org.builder.OrgTree.org.not.exist"));
        }

        final Integer orgType = currentOrg.getOrgType();
        if (orgType == OrgTypeEnum.GROUP.getIndex()) {
            recursiveDepIds(depIds, depsMap, busMap, rootOrg);
        }

        if (orgType == OrgTypeEnum.DEPARTMENT.getIndex()) {
            recursiveDepIds(depIds, depsMap.get(orgId), depsMap, currentOrg);
        }

        if (orgType == OrgTypeEnum.ORGANIZATION.getIndex()) {
            recursiveDepIds(depIds, depsMap, busMap, currentOrg);
        }

        return depIds;
    }

    /**
     * /**
     * 递归获取业务单元ID集合
     *
     * @param depIds    部门ID集合
     * @param depsMap   部门父子Map缓存
     * @param busMap    业务单元父子Map缓存
     * @param currentBU 当前业务单元
     */
    private void recursiveDepIds(final List<Long> depIds, final Map<Long, List<OrgTreeBean>> depsMap,
                                 final Map<Long, List<OrgTreeBean>> busMap, final OrgTreeBean currentBU) {

        final Long currentBUId = currentBU.getId();
        final List<OrgTreeBean> childDeps = depsMap.get(currentBUId);
        if (!CollectionUtils.isEmpty(childDeps)) {
            for (OrgTreeBean childDep : childDeps) {
                depIds.add(childDep.getId());
                recursiveDepIds(depIds, depsMap.get(currentBUId), depsMap, childDep);
            }
        }

        final List<OrgTreeBean> childBUs = busMap.get(currentBUId);
        if (!CollectionUtils.isEmpty(childBUs)) {
            for (final OrgTreeBean childBU : childBUs) {
                recursiveDepIds(depIds, depsMap, busMap, childBU);
            }
        }
    }

    /**
     * 递归获取部门ID集合
     *
     * @param depIds    部门ID集合
     * @param childDeps 下级部门集合
     * @param depsMap   部门缓存Map key=上级部门 | 所属业务单元 value=一个部门或业务单元下的部门
     */
    private void recursiveDepIds(final List<Long> depIds, final List<OrgTreeBean> childDeps,
                                 final Map<Long, List<OrgTreeBean>> depsMap, final OrgTreeBean currentDep) {
        depIds.add(currentDep.getId());
        if (!CollectionUtils.isEmpty(childDeps)) {
            for (final OrgTreeBean childDep : childDeps) {
                final Long depId = childDep.getId();
                depIds.add(childDep.getId());
                final List<OrgTreeBean> underChildDeps = depsMap.get(depId);
                recursiveDepIds(depIds, underChildDeps, depsMap, currentDep);
            }
        }
    }

    /**
     * 缓存组织信息
     *
     * @param orgTreeBean 组织结构信息
     * @param bizUnitMap  业务单元缓存Map key=业务单元ID ，valut=业务单元组织信息
     * @param busMap      业务单元缓存Map key=业务单元ID value=一个业务单元下的所有业务单元
     * @param depsMap     部门缓存Map key=上级部门 | 所属业务单元 value=一个部门或业务单元下的部门
     * @param orgId       当前组织ID
     */
    private void cacheOrg(final OrgTreeBean orgTreeBean, final Map<Long, OrgTreeBean> bizUnitMap,
                          final Map<Long, List<OrgTreeBean>> busMap, final Map<Long, List<OrgTreeBean>> depsMap, final Long orgId) {
        final Long parentId = orgTreeBean.getParentId();

        if (orgTreeBean.getOrgType() == OrgTypeEnum.ORGANIZATION.getIndex()) {// 业务单元
            if (bizUnitMap != null) {
                bizUnitMap.put(orgTreeBean.getId(), orgTreeBean);
            }
            if (busMap != null) {
                if (NumericUtil.greterThanZero(parentId)) {
                    if (CollectionUtils.isEmpty(busMap.get(parentId))) {
                        busMap.put(parentId, new ArrayList<>());
                    }
                    if (orgTreeBean.getId() != orgId) {// 同级业务单元排除当前的业务单元，避免后续构建时重复
                        busMap.get(parentId).add(orgTreeBean);
                    }
                }
            }
        }
        if (depsMap != null) {
            if (orgTreeBean.getOrgType() == OrgTypeEnum.DEPARTMENT.getIndex()) {// 部门
                final Long orgKey = NumericUtil.greterThanZero(parentId) ? parentId : orgTreeBean.getParentBUId();
                if (NumericUtil.lessThanZero(orgKey)) {
                    throw new OrgException(OrgErrorCode.DATA_STATE_ERROR_CODE, I18nUtils.getMessage("org.builder.OrgTree.org.data.exception"));
                }
                if (CollectionUtils.isEmpty(depsMap.get(orgKey))) {
                    depsMap.put(orgKey, new ArrayList<>());
                }
                depsMap.get(orgKey).add(orgTreeBean);
            }
        }
    }

    /**
     * 递归查询构建上级业务单元
     *
     * @param treeBean 当前组织结构信息数据
     * @param buMap    业务单元数据
     * @param treeDto  当前节点
     * @param contain  是否包含同级业务单元
     */
    private OrgTreeDto recursiveQueryParentBU(final OrgTreeBean treeBean, final Map<Long, OrgTreeBean> buMap,
                                              final OrgTreeDto treeDto, final Map<Long, List<OrgTreeBean>> busMap, final Boolean contain) {

        final Long parentId = treeBean.getParentId();
        if (NumericUtil.lessThanZero(parentId)) {
            return treeDto;
        } else {
            final OrgTreeBean parentTreeBean = buMap.get(parentId);
            final OrgTreeDto parentTreeDto = new OrgTreeDto();
            BeanUtils.copyProperties(parentTreeBean, parentTreeDto);
            if (contain) {
                final List<OrgTreeBean> containOrgs = busMap.get(parentId);

                final List<OrgTreeDto> depsDto = new ArrayList<>(containOrgs.size());
                for (final OrgTreeBean bizUnitBean : containOrgs) {
                    final OrgTreeDto orgTreeDto = new OrgTreeDto();
                    BeanUtils.copyProperties(bizUnitBean, orgTreeDto);
                    depsDto.add(orgTreeDto);
                }
                parentTreeDto.getChildeBUs().addAll(depsDto);
            }
            parentTreeDto.getChildeBUs().add(treeDto);
            return recursiveQueryParentBU(parentTreeBean, buMap, parentTreeDto, busMap, Boolean.FALSE);
        }
    }

    /**
     * 递归查询构建子业务单元
     *
     * @param treeBean 当前组织结构信息数据
     * @param treeDto  当前节点
     * @param busMap   业务单元上下级
     */
    private void recursiveQueryChildBU(final OrgTreeBean treeBean, final OrgTreeDto treeDto,
                                       final Map<Long, List<OrgTreeBean>> busMap, final Map<Long, List<OrgTreeBean>> depsMap) {
        final Long currentId = treeBean.getId();
        final List<OrgTreeBean> bizUnits = busMap.get(currentId);

        // 部门树
        if (!MapUtils.isEmpty(depsMap)) {
            recursiveChildDep(treeBean, treeDto, depsMap);
        }

        if (CollectionUtils.isEmpty(bizUnits)) {
            return;
        }

        final List<OrgTreeDto> childeBUs = new ArrayList<>(bizUnits.size());
        for (final OrgTreeBean bizUnitBean : bizUnits) {
            final OrgTreeDto orgTreeDto = new OrgTreeDto();
            BeanUtils.copyProperties(bizUnitBean, orgTreeDto);
            childeBUs.add(orgTreeDto);

            recursiveQueryChildBU(bizUnitBean, orgTreeDto, busMap, depsMap);
        }

        treeDto.getChildeBUs().addAll(childeBUs);
    }

    private void recursiveChildDep(final OrgTreeBean treeBean, final OrgTreeDto treeDto,
                                   final Map<Long, List<OrgTreeBean>> depsMap) {
        final Long orgId = treeBean.getId();
        final List<OrgTreeBean> childDeps = depsMap.get(orgId);
        if (!CollectionUtils.isEmpty(childDeps)) {
            final List<OrgTreeDto> childeDeps = treeDto.getChildeDeps();
            for (OrgTreeBean childDep : childDeps) {
                OrgTreeDto orgTree = new OrgTreeDto();
                BeanUtils.copyProperties(childDep, orgTree);
                childeDeps.add(orgTree);
                // 递归构建子部门
                recursiveChildDep(childDep, orgTree, depsMap);
            }
        }
    }
}
