package com.usercenter.server.domain.service;

import com.common.util.message.RestMessage;
import com.common.util.message.RestMsgUtils;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.org.client.feign.*;
import com.org.permission.common.org.dto.*;
import com.org.permission.common.org.param.*;
import com.org.permission.common.org.vo.BizUnitWithFuncDetailVo;
import com.org.permission.common.query.BatchQueryParam;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component(value = "orgDomainService")
public class OrgDomainService {
    @Resource
    private OrganizationFeign organizationFeign;
    @Resource
    private StorageEntrustRelationFeign storageEntrustRelationFeign;
    @Resource
    private MarketingEntrustRelationFeign marketingEntrustRelationFeign;
    @Resource
    private GroupFeign groupFeign;
    @Resource
    private BizUnitFeign bizUnitFeign;



    public List<OrgInfoDto> getOrgByIdList(List<Long> orgIdList) {
        BatchQueryParam param = new BatchQueryParam();
        param.setIds(orgIdList);
        RestMessage<List<OrgInfoDto>> restMessage = organizationFeign.batchQueryOrgInfo(param);
        return restMessage.getData();
    }



    public OrganizationDto getOrgInfoById(Long orgId) {
        RestMessage<OrganizationDto> restMessage = organizationFeign.queryOrgInfoById(new QueryByIdReqParam(orgId));
        return RestMsgUtils.retrieveResult(restMessage);
    }

    public List<StaffInfoDto> getStaffByIdList(List<Long> orgIdList) {

        return new ArrayList<>();
    }

    public Boolean syncCustBizType(SyncCustBizTypeParam param) {
        return true;
    }

    public CustOrgDto getOrgByCustId(Long custId) {
        return new CustOrgDto();
    }

    public OrgUser custGenerateGroup(CustGenerateGroupParam custGenerateGroupParam) {
        return new OrgUser();
    }

    public PageInfo queryAllGroupInfoList(GroupListQueryParam param) {
        RestMessage<PageInfo<OrgInfoDto>> restMessage = groupFeign.queryAllGroupInfoList(param);
        return restMessage.getData();
    }

    public List<OrgInfoDto> queryGroupUnbindingCustBUs(Long groupId, Integer type) {
        return new ArrayList<>();
    }

    public List<BizUnitWithFuncDetailVo> queryBizUnitByIds(QueryByIdReqParam param) {
        return new ArrayList<>();
    }


    public OrgCustDto queryOrgCust(QueryByIdReqParam reqParam) {
        RestMessage<OrgCustDto> restMessage = this.organizationFeign.queryOrgCust(reqParam);
        return restMessage.getData();
    }

    public List<StoragePlatformEntrustRelationExtendDto> extendQueryPlatformStorageEntrustRelation(StoragePlatformEntrustRelationQueryParam storagePlatformEntrustRelationQueryParam) {
        RestMessage<List<StoragePlatformEntrustRelationExtendDto>> restMessage = storageEntrustRelationFeign.extendQueryPlatformStorageEntrustRelation(storagePlatformEntrustRelationQueryParam);
        return restMessage.getData();
    }

    public List<StorageGroupEntrustRelationExtendDto> extendQueryGroupStorageEntrustRelation(StorageGroupEntrustRelationQueryParam storageGroupEntrustRelationQueryParam) {
        RestMessage<List<StorageGroupEntrustRelationExtendDto>> restMessage = storageEntrustRelationFeign.extendQueryGroupStorageEntrustRelation(storageGroupEntrustRelationQueryParam);
        if (restMessage != null && CollectionUtils.isNotEmpty(restMessage.getData())) {
            return restMessage.getData();
        }
        return Lists.newArrayList();
    }

    public List<MarketEntrustRelationDto> queryMarketEntrustRelation(MarketEntrustRelationQueryParam marketEntrustRelationQueryParam) {
        RestMessage<List<MarketEntrustRelationDto>> restMessage = marketingEntrustRelationFeign.queryMarketEntrustRelation(marketEntrustRelationQueryParam);
        if (restMessage != null && CollectionUtils.isNotEmpty(restMessage.getData())) {
            return restMessage.getData();
        }
        return Lists.newArrayList();
    }

    public List<OrgListDto> queryGroupList(QueryOrgListInfoReqParam param) {
        RestMessage<List<OrgListDto>> restMessage = groupFeign.queryGroupList(param);
        return restMessage.getData();
    }

    public List<OrgInfoDto> queryGroupByIdList(List<Long> groupIdList) {
        BatchQueryParam param = new BatchQueryParam();
        param.setIds(groupIdList);
        RestMessage<List<OrgInfoDto>> restMessage = groupFeign.queryGroupInfoByOrgIdsNoPage(param);
        return restMessage.getData();
    }

    public OrgTreeDto queryOrgTree(QueryOrgTreeReqParam reqParam) {
        RestMessage<OrgTreeDto> restMessage = organizationFeign.queryOrgTree(reqParam);
        return restMessage.getData();
    }

    public List<OrgInfoDto> queryBUPermissionList(QueryOrgPermissionListReqParam reqParam) {
        RestMessage<List<OrgInfoDto>> restMessage = this.bizUnitFeign.queryBUPermissionList(reqParam);
        return restMessage.getData();
    }

    public List<OrgInfoDto> queryOrgList(QueryOrgListReqParam reqParam) {
        RestMessage<List<OrgInfoDto>> restMessage = this.organizationFeign.queryOrgList(reqParam);
        return restMessage.getData();
    }

    public RestMessage<List<OrgInfoDto>> queryGroupInfoByParam(BatchQueryParam batchQueryParam) {
        return groupFeign.queryGroupInfoByOrgIdsNoPage(batchQueryParam);
    }

    public RestMessage<List<OrgInfoDto>> queryBUByGroupId(Long groupId) {
        QueryByIdReqParam queryByIdReqParam = new QueryByIdReqParam();
        queryByIdReqParam.setId(groupId);
        RestMessage<List<OrgInfoDto>> restMessage = bizUnitFeign.queryBUByGroupId(queryByIdReqParam);
        return restMessage;
    }

    public RestMessage<List<OrgInfoDto>> queryBizByGroupIdAndUserId(QueryBizByGroupIdAndUserIdParam queryBizByGroupIdAndUserIdParam) {
        RestMessage<List<OrgInfoDto>> restMessage = bizUnitFeign.queryBizByGroupIdAndUserId(queryBizByGroupIdAndUserIdParam);
        return restMessage;
    }

    public List<OrgInfoDto> batchQueryOrgInfo(BatchQueryParam reqParam) {
        return RestMsgUtils.retrieveResult(organizationFeign.batchQueryOrgInfo(reqParam));
    }

}
