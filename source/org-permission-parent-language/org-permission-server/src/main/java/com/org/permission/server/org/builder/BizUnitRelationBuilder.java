package com.org.permission.server.org.builder;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.BaseTreeBean;
import com.org.permission.common.org.dto.TreeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 业务单元层级构建器
 */
@Component("bizUnitRelationBuilder")
public class BizUnitRelationBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(BizUnitRelationBuilder.class);

    public <T extends TreeDto> List<T> builder(final List<? extends BaseTreeBean> orgListBeans, Class<T> clazz) {
        if (CollectionUtils.isEmpty(orgListBeans)) {
            return Collections.emptyList();
        }

        final Map<Long, BaseTreeBean> idMap = convertToIdMap(orgListBeans);
        final Set<Long> topIds = filterTopNode(orgListBeans, idMap);
        final Map<Long, List<BaseTreeBean>> pidMap = convertToPidMap(orgListBeans);

        List<T> voList = new ArrayList<>(topIds.size());
        for (Long topId : topIds) {
            final BaseTreeBean topBean = idMap.get(topId);
            T treeDto;
            try {
                treeDto = clazz.newInstance();
            } catch (Exception ignored) {
                LOGGER.error("", ignored);
                throw new OrgException(OrgErrorCode.ORG_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.builder.BizUnitRelation.org.system.exception"));
            }
            BeanUtils.copyProperties(topBean, treeDto);
            voList.add(treeDto);
            recursiveSearchDownBu2(treeDto, idMap, pidMap, clazz);
        }
        return voList;
    }

    private <T extends TreeDto> void recursiveSearchDownBu2(T vo, final Map<Long, BaseTreeBean> idMap, final Map<Long, List<BaseTreeBean>> pidMap, Class<T> clazz) {

        final Long topId = vo.getId();
        final List<BaseTreeBean> childBeans = pidMap.get(topId);
        if (CollectionUtils.isEmpty(childBeans)) {
            return;
        }

        List<T> childVos = new ArrayList<>(childBeans.size());
        for (BaseTreeBean orgListBean : childBeans) {
            T treeDto;
            try {
                treeDto = clazz.newInstance();
            } catch (Exception ignored) {
                LOGGER.error("", ignored);
                throw new OrgException(OrgErrorCode.ORG_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.builder.BizUnitRelation.org.system.exception"));
            }
            BeanUtils.copyProperties(orgListBean, treeDto);
            childVos.add(treeDto);
            recursiveSearchDownBu2(treeDto, idMap, pidMap, clazz);
        }
        vo.setChildren(childVos);
    }

    /**
     * 过滤顶级节点
     *
     * @param orgListBeans 业务单元集合
     * @param idMap        idMap
     * @return 顶级节点业务单元ID
     */
    private Set<Long> filterTopNode(final List<? extends BaseTreeBean> orgListBeans, final Map<Long, BaseTreeBean> idMap) {
        Set<Long> topIds = new HashSet<>();

        final Iterator<? extends BaseTreeBean> iterator = orgListBeans.iterator();
        while (iterator.hasNext()) {
            final BaseTreeBean next = iterator.next();
            final Long parentId = next.getParentId();

            final BaseTreeBean upBizUnit = idMap.get(parentId);
            if (upBizUnit == null) {
                topIds.add(next.getId());
            }
        }
        return topIds;
    }

    /**
     * 转成 ID Map
     *
     * @param orgListBeans 业务单元实体信息
     * @return ID Map
     */
    private Map<Long, BaseTreeBean> convertToIdMap(final List<? extends BaseTreeBean> orgListBeans) {
        Map<Long, BaseTreeBean> idMap = new HashMap<>(orgListBeans.size());
        for (BaseTreeBean orgListBean : orgListBeans) {
            idMap.put(orgListBean.getId(), orgListBean);
        }
        return idMap;
    }

    /**
     * 转成 PID Map
     *
     * @param orgListBeans 业务单元实体信息集合
     * @return PID Map
     */
    private Map<Long, List<BaseTreeBean>> convertToPidMap(final List<? extends BaseTreeBean> orgListBeans) {
        Map<Long, List<BaseTreeBean>> pidMap = new HashMap<>();
        for (BaseTreeBean orgListBean : orgListBeans) {
            final Long parentId = orgListBean.getParentId();
            if (parentId == null || parentId == 0) {
                continue;
            }
            final List<BaseTreeBean> cacheList = pidMap.get(parentId);
            if (CollectionUtils.isEmpty(cacheList)) {
                pidMap.put(parentId, new ArrayList<>(4));
            }
            pidMap.get(parentId).add(orgListBean);
        }
        return pidMap;
    }
}
