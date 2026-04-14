package com.org.permission.server.org.builder;

import com.org.permission.server.org.bean.OrgTreeBean;
import com.org.permission.common.org.dto.OrgTreeDto;
import com.org.permission.server.org.dto.MultipleOrgTreeDto;
import com.common.base.enums.BooleanEnum;
import com.org.permission.server.org.enums.OrgTypeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 多组织树构建器
 */
@Component(value = "multipleOrgTreeBuilder")
public class MultipleOrgTreeBuilder {
    static final Long ROOT_BIZ_UNIT_TAP = -99L;

    public List<OrgTreeDto> buildOrgTree(final MultipleOrgTreeDto multipleOrgTreeDto) {

        final List<OrgTreeBean> matchOrgs = multipleOrgTreeDto.getMatchOrgs();
        final List<OrgTreeBean> groupAllOrgs = multipleOrgTreeDto.getGroupAllOrgs();

        final Map<Long, List<OrgTreeBean>> matchOrgMap = separateByGroupId(matchOrgs);
        final Map<Long, List<OrgTreeBean>> groupAllOrgMap = separateByGroupId(groupAllOrgs);

        List<OrgTreeDto> orgTrees = new ArrayList<>();
        for (Long groupId : matchOrgMap.keySet()) {
            final OrgTreeDto orgTree = buildGroupOrgTree(groupId, matchOrgMap.get(groupId), groupAllOrgMap.get(groupId));
            if (Objects.nonNull(orgTree)) {
                orgTrees.add(orgTree);
            }
        }
        return orgTrees;
    }

    /**
     * 将各组织按集团分开
     *
     * @param orgs 组织信息
     * @return key:集团ID;value:集团下的所有组织
     */
    private Map<Long, List<OrgTreeBean>> separateByGroupId(List<OrgTreeBean> orgs) {
        Map<Long, List<OrgTreeBean>> orgMap = new HashMap<>();
        for (OrgTreeBean org : orgs) {
            final Long groupId = org.getGroupId();
            if (CollectionUtils.isEmpty(orgMap.get(groupId))) {
                orgMap.put(groupId, new ArrayList<>());
            }
            orgMap.get(groupId).add(org);
        }
        return orgMap;
    }

    /**
     * 构建集团的组织树
     *
     * @return 集团组织树
     */
    private OrgTreeDto buildGroupOrgTree(Long groupId, List<OrgTreeBean> matchOrgs, List<OrgTreeBean> groupAllOrgs) {
        // 上级组织包含关系
        Set<Long> containOrgIds = new HashSet<>(8);
        Map<Long, OrgTreeBean> orgMap = new HashMap<>(groupAllOrgs.size());
        final Map<Long, OrgTreeBean> idMap = convertToIdMap(groupAllOrgs);
        final Map<Long, List<OrgTreeBean>> pIdMap = convertToPIdMap(groupAllOrgs);
        OrgTreeBean groupOrg = idMap.get(groupId);
        OrgTreeBean rootBizUnit = idMap.get(ROOT_BIZ_UNIT_TAP);
        for (OrgTreeBean matchOrg : matchOrgs) {
            final Integer orgType = matchOrg.getOrgType();
            final Integer mainOrgFlag = matchOrg.getMainOrgFlag();
            if (OrgTypeEnum.GROUP.getIndex() == orgType || BooleanEnum.TRUE.getCode() == mainOrgFlag) {
                matchOrg = rootBizUnit;
            }

            recursiveSearchUpBU(matchOrg, idMap, containOrgIds);

            recursiveSearchDownBU(matchOrg, pIdMap, containOrgIds);
        }
        groupOrg.childeOrgs.add(rootBizUnit);

        return buildFinalTree(groupOrg);
    }

    /**
     * 将List 转换为 Map
     * 使用 ：OrgListFilter
     *
     * @param orgs 组织集合
     * @return 组织ID : 组织
     */
    public Map<Long, OrgTreeBean> convertToIdMap(List<OrgTreeBean> orgs) {
        Map<Long, OrgTreeBean> map = new HashMap<>(orgs.size());
        for (OrgTreeBean org : orgs) {
            map.put(org.getId(), org);
            // 根业务单元特殊处理下
            final Integer mainOrgFlag = org.getMainOrgFlag();
            if (BooleanEnum.TRUE.getCode() == mainOrgFlag) {
                map.put(ROOT_BIZ_UNIT_TAP, org);
            }
        }
        return map;
    }

    /**
     * 将List 转换为 Map
     * 使用 ：OrgListFilter
     *
     * @param orgs 组织集合
     * @return 上级组织ID : 组织
     */
    Map<Long, List<OrgTreeBean>> convertToPIdMap(List<OrgTreeBean> orgs) {
        Map<Long, List<OrgTreeBean>> pIdmap = new HashMap<>();
        for (OrgTreeBean org : orgs) {
            final Long parentId = org.getParentId();

            if (CollectionUtils.isEmpty(pIdmap.get(parentId))) {
                pIdmap.put(parentId, new ArrayList<>());
            }
            pIdmap.get(parentId).add(org);
        }
        return pIdmap;
    }

    /**
     * 递归找到上级
     */
    private void recursiveSearchUpBU(final OrgTreeBean matchOrg, final Map<Long, OrgTreeBean> idMap, final Set<Long> containOrgIds) {

        final Long parentId = matchOrg.getParentId();
        if (parentId == null || parentId.longValue() == 0) {
            return;
        }
        final Long matchId = matchOrg.getId();
        if (containOrgIds.contains(matchId)) {
            return;
        }

        final OrgTreeBean parentOrg = idMap.get(parentId);
        parentOrg.childeOrgs.add(matchOrg);

        containOrgIds.add(matchId);

        recursiveSearchUpBU(parentOrg, idMap, containOrgIds);
    }

    /**
     * 递归找到下级
     */
    private void recursiveSearchDownBU(final OrgTreeBean currentOrg, final Map<Long, List<OrgTreeBean>> pIdMap, final Set<Long> containOrgIds) {

        final Long currentOrgId = currentOrg.getId();
        if (containOrgIds.contains(currentOrgId)) {
            return;
        }
        containOrgIds.add(currentOrgId);

        final List<OrgTreeBean> downOrgs = pIdMap.get(currentOrgId);
        if (CollectionUtils.isEmpty(downOrgs)) {
            return;
        }

        currentOrg.childeOrgs.addAll(downOrgs);

        // 避免重复向下找
        for (OrgTreeBean downOrg : downOrgs) {
            recursiveSearchDownBU(downOrg, pIdMap, containOrgIds);
        }
    }

    /**
     * 构建前端树
     *
     * @param groupOrg 集团
     */
    private OrgTreeDto buildFinalTree(OrgTreeBean groupOrg) {

        OrgTreeDto treeRoot = new OrgTreeDto();
        BeanUtils.copyProperties(groupOrg, treeRoot);

        recursiveBuildeTree(treeRoot, groupOrg);

        return treeRoot;

    }

    /**
     * 递归找到下级
     */
    private void recursiveBuildeTree(OrgTreeDto tree, OrgTreeBean org) {
        final Set<OrgTreeBean> childeOrgs = org.childeOrgs;
        if (CollectionUtils.isEmpty(childeOrgs)) {
            return;
        }

        for (OrgTreeBean childeOrg : childeOrgs) {
            OrgTreeDto newNode = new OrgTreeDto();
            BeanUtils.copyProperties(childeOrg, newNode);
            tree.getChildeBUs().add(newNode);
            recursiveBuildeTree(newNode, childeOrg);
        }
    }

}
