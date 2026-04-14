package com.org.permission.server.org.bean;


import com.org.permission.common.bean.BaseBean;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 基础树数据实体
 */
@Data
public class BaseTreeBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long parentId;

	private List<Integer> simpleFunctions;
}
