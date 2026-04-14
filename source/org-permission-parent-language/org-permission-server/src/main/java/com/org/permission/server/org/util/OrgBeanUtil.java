package com.org.permission.server.org.util;

import com.org.permission.server.org.bean.BaseIdBean;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 基础BEAN 工具
 */
public class OrgBeanUtil {
	/**
	 * 筛选基础实体的ID 集合
	 *
	 * @param objs
	 * @return
	 */
	public static final List<Integer> filterBaseIdBeanIds(List<?> objs) {
		if (CollectionUtils.isEmpty(objs)) {
			return Collections.emptyList();
		}
		List<Integer> ids = new ArrayList<>(objs.size());
		for (Object obj : objs) {
			if (obj instanceof BaseIdBean) {
				ids.add(((BaseIdBean) obj).getId());
			}
		}
		return ids;
	}

	/**
	 * 集合转换
	 *
	 * @param sourceList 原集合
	 * @param <E>        原泛型
	 * @param <T>        目标泛型
	 * @return 目标泛型集合
	 */
	public static <E, T> List<T> listConvert(final List<E> sourceList, T t) {
		if (CollectionUtils.isEmpty(sourceList)) {
			return Collections.emptyList();
		}
		List<T> targetList = new ArrayList<>(sourceList.size());
		for (Object source : sourceList) {
			T target = null;
			try {
				target = (T) BeanUtils.instantiateClass(t.getClass());
			} catch (Exception ignored) {
			}
			BeanUtils.copyProperties(source, target);
			targetList.add(target);
		}
		return targetList;
	}
}
