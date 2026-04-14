package com.org.permission.server.domain.org;

import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.OrgInfoDto;
import com.org.permission.common.org.param.GroupListQueryParam;
import com.org.permission.common.org.param.QueryOrgListInfoReqParam;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.server.org.bean.OrgListBean;
import com.org.permission.server.org.dto.param.RootBUListQueryParam;
import com.org.permission.server.org.service.BizUnitService;
import com.org.permission.server.org.service.GroupService;
import com.org.permission.server.org.service.OrganizationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service(value = "orgDomainService")
public class OrgDomainService {

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private BizUnitService bizUnitService;

    public List<OrgInfoDto> batchQueryOrgInfo(BatchQueryParam batchQueryParam) {
        List<OrgInfoDto> list = organizationService.batchQueryOrgInfo(batchQueryParam);
        return list;
    }

    public List<OrgListBean> queryGroupList(QueryOrgListInfoReqParam param) {
        return groupService.queryGroupList(param);
    }

    public PageInfo<OrgInfoDto> queryAllGroupInfoList(GroupListQueryParam reqParam) {
        return groupService.queryAllGroupInfoList(reqParam);
    }

    public List<OrgInfoDto> queryGroupInfoByOrgIdsNoPage(BatchQueryParam reqParam) {
        return groupService.queryGroupInfoByOrgIdsNoPage(reqParam);
    }

    public PageInfo<OrgInfoDto> queryGroupInfoByOrgIds(BatchQueryParam reqParam) {
        return groupService.queryGroupInfoByOrgIds(reqParam);
    }

    public String queryGroupBusinessType(Long groupId) {
        return StringUtils.defaultString(groupService.queryGroupBusinessType(groupId), "");
    }
    public List<OrgInfoDto> queryRootBUInfoList(RootBUListQueryParam param){
        PageInfo<OrgInfoDto> pageInfo =  bizUnitService.queryRootBUInfoList(param);
        return pageInfo.getList();
    }
}
