package com.org.permission.server.org.builder;


import com.org.permission.server.org.bean.OrgTreeBean;
import com.org.permission.common.org.dto.OrgTreeDto;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 部门树构建器
 */
@Component(value = "depTreeBuilder")
public class DepTreeBuilder {
	public List<OrgTreeDto> builde(final List<OrgTreeBean> deps) {
		if (CollectionUtils.isEmpty(deps)) {
			return Collections.EMPTY_LIST;
		}

		final Map<Long, List<OrgTreeBean>> pIdMap = convertToPIdMap(deps);
		final Map<Long, OrgTreeBean> idMap = convertToIdMap(deps);
		final List<OrgTreeBean> topDeps = fillTopDeps(deps);
		List<OrgTreeDto> treeList = new ArrayList<>(topDeps.size());
		for (OrgTreeBean topDepBean : topDeps) {
			OrgTreeDto topDepDto = new OrgTreeDto();
			BeanUtils.copyProperties(topDepBean, topDepDto);
			recursiveBuildDepTree(topDepDto, pIdMap,idMap);
			treeList.add(topDepDto);
		}
		return treeList;
	}

	/**
	 * 过滤顶级部门
	 *
	 * @param deps 部门集合
	 * @return 顶级部门集合
	 */
	private List<OrgTreeBean> fillTopDeps(final List<OrgTreeBean> deps) {
		List<OrgTreeBean> topDeps = new ArrayList<>();
		for (OrgTreeBean dep : deps) {
			final Long parentId = dep.getParentId();
			if (parentId == null || parentId.longValue() == 0) {
				topDeps.add(dep);
			}
		}
		return topDeps;
	}

	/**
	 * 递归构建部门树
	 */
	private void recursiveBuildDepTree(final OrgTreeDto currentTopDep, final Map<Long, List<OrgTreeBean>> pIdMap,final Map<Long, OrgTreeBean> idMap) {

		final Long id = currentTopDep.getId();
		final Long parentId = currentTopDep.getParentId();
		if (parentId!=null&&parentId.longValue()>0) {
			final OrgTreeBean parentTreeBean = idMap.get(parentId);
			OrgTreeDto parentDepDto = new OrgTreeDto();
			BeanUtils.copyProperties(parentTreeBean, parentDepDto);
			currentTopDep.setParentDep(parentDepDto);
		}

		final List<OrgTreeBean> childDeps = pIdMap.get(id);
		if (CollectionUtils.isEmpty(childDeps)) {
			return;
		}

		List<OrgTreeDto> childDepDtos = new ArrayList<>(childDeps.size());
		for (OrgTreeBean childDep : childDeps) {
			OrgTreeDto childDepDto = new OrgTreeDto();
			BeanUtils.copyProperties(childDep, childDepDto);
			childDepDtos.add(childDepDto);
			recursiveBuildDepTree(childDepDto, pIdMap,idMap);
		}
		currentTopDep.setChildeDeps(childDepDtos);
	}

	/**
	 * 将List 转换为 Map
	 *
	 * @param deps 部门集合
	 * @return 上级部门ID : 部门集合
	 */
	private Map<Long, List<OrgTreeBean>> convertToPIdMap(List<OrgTreeBean> deps) {
		Map<Long, List<OrgTreeBean>> pIdmap = new HashMap<>();
		for (OrgTreeBean dep : deps) {
			final Long parentId = dep.getParentId();
			if (parentId == null || parentId == 0) {
				continue;
			}
			if (CollectionUtils.isEmpty(pIdmap.get(parentId))) {
				pIdmap.put(parentId, new ArrayList<>());
			}
			pIdmap.get(parentId).add(dep);
		}
		return pIdmap;
	}

	/**
	 * 将List 转换为 Map
	 *
	 * @param deps 部门集合
	 * @return 上级部门ID : 部门集合
	 */
	private Map<Long, OrgTreeBean> convertToIdMap(List<OrgTreeBean> deps) {
		Map<Long, OrgTreeBean> idmap = new HashMap<>();
		for (OrgTreeBean dep : deps) {
			idmap.put(dep.getId(),dep);
		}
		return idmap;
	}
}
