package com.org.permission.server.org.builder;

import com.org.permission.server.org.bean.OrgTreeBean;
import com.org.permission.common.org.dto.OrgListDto;
import com.org.permission.server.org.dto.MultipleOrgTreeDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 组织职能层级构建器
 */
@Component("functionLowLevelBuilder")
public class FunctionLowLevelBuilder {

	@Resource
	private MultipleOrgTreeBuilder multipleOrgTreeBuilder;

	public List<OrgListDto> builde(final MultipleOrgTreeDto multipleOrgTree) {
		final List<Long> matchOrgIds = multipleOrgTree.getMatchOrgIds();
		if (CollectionUtils.isEmpty(matchOrgIds)) {
			return Collections.emptyList();
		}

		final List<OrgTreeBean> groupAllOrgsWithSepicalFunc = multipleOrgTree.getGroupAllOrgs();
		final Map<Long, OrgTreeBean> idMap = multipleOrgTreeBuilder.convertToIdMap(groupAllOrgsWithSepicalFunc);
		final Map<Long, List<OrgTreeBean>> pIdMap = multipleOrgTreeBuilder.convertToPIdMap(groupAllOrgsWithSepicalFunc);

		List<OrgListDto> resultList = new ArrayList<>(matchOrgIds.size());
		for (Long matchOrgId : matchOrgIds) {
			final OrgTreeBean orgTreeBean = idMap.get(matchOrgId);
			OrgListDto matchOrgDto = new OrgListDto();
			BeanUtils.copyProperties(orgTreeBean, matchOrgDto);
			recursiveSearchDownBU(matchOrgDto, orgTreeBean, pIdMap);
			resultList.add(matchOrgDto);
		}
		return resultList;
	}

	/**
	 * 递归找到下级
	 */
	private void recursiveSearchDownBU(OrgListDto matchOrgDto, final OrgTreeBean currentOrg,
			final Map<Long, List<OrgTreeBean>> pIdMap) {

		final Long currentOrgId = currentOrg.getId();
		final List<OrgTreeBean> orgTreeBeans = pIdMap.get(currentOrgId);
		if (CollectionUtils.isEmpty(orgTreeBeans)) {
			return;
		}

		for (OrgTreeBean orgTreeBean : orgTreeBeans) {
			OrgListDto childOrgListDto = new OrgListDto();
			BeanUtils.copyProperties(orgTreeBean, childOrgListDto);
			if (CollectionUtils.isEmpty(matchOrgDto.getChildren())) {
				matchOrgDto.setChildren(new ArrayList<OrgListDto>(orgTreeBeans.size()));
			}
			matchOrgDto.getChildren().add(childOrgListDto);

			recursiveSearchDownBU(matchOrgDto, orgTreeBean, pIdMap);
		}
	}
}
