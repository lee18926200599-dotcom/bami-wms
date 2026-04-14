package com.org.permission.server.org.builder;

import com.org.permission.server.org.bean.StaffTypeTreeBean;
import com.org.permission.common.org.param.QueryStaffTypeTreeReqParam;
import com.org.permission.common.org.vo.StaffTypeTreeVo;
import com.common.base.enums.StateEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 人员类别树构建器
 */
@Component(value = "staffTypeTreeBuilder")
public class StaffTypeTreeBuilder {
    public StaffTypeTreeVo builde(final List<StaffTypeTreeBean> typeTrees, QueryStaffTypeTreeReqParam reqParam) {
        if (CollectionUtils.isEmpty(typeTrees)) {
            return null;
        }
        Map<Long, List<StaffTypeTreeBean>> typeMap = new HashMap<>();
        StaffTypeTreeBean topTree = null;
        for (StaffTypeTreeBean typeTree : typeTrees) {

            final Long parentId = typeTree.getParentId();
            if (CollectionUtils.isEmpty(typeMap.get(parentId))) {
                typeMap.put(parentId, new ArrayList<>(0));
            }
            typeMap.get(parentId).add(typeTree);

            if (Objects.equals(1L, typeTree.getId())) {
                topTree = typeTree;
            }
        }

        StaffTypeTreeVo finalTree = new StaffTypeTreeVo();
        BeanUtils.copyProperties(topTree, finalTree);

        recursiveChildNode(finalTree, typeMap);
        return finalTree;
    }

    /**
     * 人员类别转换为 map 数据结构
     *
     * @param typeTrees 类别集合
     * @return key 类别PID； value 子类别
     */
    public Map<Long, List<StaffTypeTreeBean>> convert2PIdMap(final List<StaffTypeTreeBean> typeTrees) {
        Map<Long, List<StaffTypeTreeBean>> typeMap = new HashMap<>();
        for (StaffTypeTreeBean typeTree : typeTrees) {

            final Long parentId = typeTree.getParentId();
            if (CollectionUtils.isEmpty(typeMap.get(parentId))) {
                typeMap.put(parentId, new ArrayList<>(0));
            }
            typeMap.get(parentId).add(typeTree);
        }
        return typeMap;
    }

    /**
     * 递归构建人员类别树
     *
     * @param typeTree 类别树VO
     * @param typeMap  类别集合缓存（key:StaffTypeTreeBean#parentId ; value:StaffTypeTreeBean）
     */
    private void recursiveChildNode(StaffTypeTreeVo typeTree, Map<Long, List<StaffTypeTreeBean>> typeMap) {
        final Long id = typeTree.getId();
        final List<StaffTypeTreeBean> staffTypes = typeMap.get(id);
        if (!CollectionUtils.isEmpty(staffTypes)) {
            List<StaffTypeTreeVo> childTypeTrees = new ArrayList<>(staffTypes.size());
            for (StaffTypeTreeBean staffType : staffTypes) {
                StaffTypeTreeVo childTypeTree = new StaffTypeTreeVo();
                BeanUtils.copyProperties(staffType, childTypeTree);
                childTypeTrees.add(childTypeTree);

                recursiveChildNode(childTypeTree, typeMap);
            }
            typeTree.setChildTypes(childTypeTrees);
        }
    }

    /**
     * 递归搜索某部门的所有子部门
     *
     * @param currentTypeId 当前部门
     * @param typeMap       类别缓存
     * @param types         类别 ID 集合
     */
    public void recurseSearchChildTypes(Long currentTypeId, Map<Long, List<StaffTypeTreeBean>> typeMap, List<Long> types) {
        if (CollectionUtils.isEmpty(typeMap)) {
            return;
        }

        final List<StaffTypeTreeBean> staffTypes = typeMap.get(currentTypeId);
        if (!CollectionUtils.isEmpty(staffTypes)) {
            for (StaffTypeTreeBean staffType : staffTypes) {
                if (staffType.getState() == StateEnum.ENABLE.getCode()) {
                    types.add(staffType.getId());
                    recurseSearchChildTypes(staffType.getId(), typeMap, types);
                }
            }
        }
        return;
    }
}
