package com.org.permission.server.org.dto;


import com.org.permission.server.org.bean.OrgTreeBean;
import lombok.Data;

import java.util.List;

/**
 * 多组织树
 */
@Data
public class MultipleOrgTreeDto {

	private List<OrgTreeBean> matchOrgs;

	private List<Long> matchOrgIds;

	private List<OrgTreeBean> groupAllOrgs;
}
