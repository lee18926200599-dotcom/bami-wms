package com.org.permission.server.org.builder;

import com.org.permission.common.org.dto.OrgInfoDto;
import com.org.permission.common.org.param.QueryOrgListReqParam;
import com.org.permission.server.org.bean.OrgTreeBean;
import com.org.permission.server.org.enums.OrgListFilterLevelEnum;
import com.org.permission.server.org.enums.OrgTypeEnum;
import com.common.base.enums.BooleanEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static cn.hutool.core.bean.BeanUtil.toBean;

/**
 * 组织过滤器
 */
@Component(value = "orgListFilter")
public class OrgListFilter {

    @Resource
    private MultipleOrgTreeBuilder multipleOrgTreeBuilder;

    public List<OrgInfoDto> filter(final List<OrgTreeBean> orgList, final QueryOrgListReqParam reqParam) {
        List<OrgInfoDto> orgInfoList = new ArrayList<>(8);

        final Integer filterLevel = reqParam.getFilterLevel();

        Map<Long, List<OrgTreeBean>> pIdMap = multipleOrgTreeBuilder.convertToPIdMap(orgList);
        Map<Long, OrgTreeBean> idMap = multipleOrgTreeBuilder.convertToIdMap(orgList);

        final Long orgId = reqParam.getOrgId();
        final OrgTreeBean matchOrg = idMap.get(orgId);

        if (OrgListFilterLevelEnum.ALL_UP_ORG.getFilterLevel() == filterLevel) {
            recursiveSearchUpBU(matchOrg, idMap, orgInfoList);
        }

        if (OrgListFilterLevelEnum.ALL_DOWN_BU.getFilterLevel() == filterLevel) {
            recursiveSearchDownBU(matchOrg, pIdMap, idMap, orgInfoList);
        }

        if (OrgListFilterLevelEnum.GROUP_ALL_ORG.getFilterLevel() == filterLevel) {
            orgInfoList = toBeanList(orgList, OrgInfoDto.class);
        }

        return orgInfoList;
    }

    public <T, E> List<T> toBeanList(Collection<E> sourceList, Class<T> destinationClass) {
        return sourceList != null && !sourceList.isEmpty() && destinationClass != null ? (List) sourceList.parallelStream().filter((item) -> {
            return item != null;
        }).map((source) -> {
            return toBean(source, destinationClass);
        }).collect(Collectors.toList()) : Collections.emptyList();
    }

    /**
     * 递归找上级
     */
    private void recursiveSearchUpBU(final OrgTreeBean matchOrg, final Map<Long, OrgTreeBean> idMap, List<OrgInfoDto> orgInfoList) {

        if (matchOrg.getOrgType() == OrgTypeEnum.GROUP.getIndex()) {
            return;
        }

        if (BooleanEnum.TRUE.getCode().equals(matchOrg.getMainOrgFlag())) {
            final OrgTreeBean groupBean = idMap.get(matchOrg.getGroupId());
            OrgInfoDto groupOrg = new OrgInfoDto();
            BeanUtils.copyProperties(groupBean, groupOrg);
            orgInfoList.add(groupOrg);
            return;
        }

        final Long parentId = matchOrg.getParentId();
        final OrgTreeBean upOrg = idMap.get(parentId);

        OrgInfoDto parentOrg = new OrgInfoDto();
        BeanUtils.copyProperties(upOrg, parentOrg);
        orgInfoList.add(parentOrg);

        recursiveSearchUpBU(upOrg, idMap, orgInfoList);
    }

    /**
     * 递归找到下级
     */
    private void recursiveSearchDownBU(OrgTreeBean matchOrg, final Map<Long, List<OrgTreeBean>> pIdMap, Map<Long, OrgTreeBean> idMap, List<OrgInfoDto> orgInfoList) {

        if (OrgTypeEnum.GROUP.getIndex().equals(matchOrg.getOrgType())) {
            final OrgTreeBean rootOrgBean = idMap.get(MultipleOrgTreeBuilder.ROOT_BIZ_UNIT_TAP);
            OrgInfoDto rootOrg = new OrgInfoDto();
            BeanUtils.copyProperties(rootOrgBean, rootOrg);
            orgInfoList.add(rootOrg);

            matchOrg = rootOrgBean;
        }
        final Long currentOrgId = matchOrg.getId();
        final List<OrgTreeBean> downOrgBeans = pIdMap.get(currentOrgId);
        if (CollectionUtils.isEmpty(downOrgBeans)) {
            return;
        }

        for (OrgTreeBean downOrgBean : downOrgBeans) {
            OrgInfoDto downOrg = new OrgInfoDto();
            BeanUtils.copyProperties(downOrgBean, downOrg);
            orgInfoList.add(downOrg);

            recursiveSearchDownBU(downOrgBean, pIdMap, idMap, orgInfoList);
        }
    }
}
