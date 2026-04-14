package com.org.permission.server.org.service;

import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.*;
import com.org.permission.common.org.param.*;
import com.org.permission.server.org.bean.StorageGroupEntrustRelationBean;
import com.org.permission.server.org.bean.StorageGroupEntrustRelationInfoBean;
import com.org.permission.server.org.bean.StoragePlatformEntrustRelationBean;
import com.org.permission.server.org.bean.StoragePlatformEntrustRelationInfoBean;
import com.org.permission.server.org.dto.MultipleOrgTreeDto;
import com.org.permission.server.org.dto.param.GroupStorageEntrustRelationReqParam;
import com.org.permission.server.org.dto.param.PlatformStorageEntrustRelationReqParam;
import com.common.base.enums.StateEnum;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 仓储委托关系说明文档
 */
public interface StorageEntrustRelationService {
    /**
     * 新增集团间仓储委托关系
     *
     * @param reqParam 集团间仓储委托关系
     */
    void addPlatformStorageEntrustRelation(StoragePlatformEntrustRelationBean reqParam);

    /**
     * 更新集团间仓储委托关系
     *
     * @param reqParam 更新集团间仓储委托关系
     */
    void updatePlatformStorageEntrustRelation(StoragePlatformEntrustRelationBean reqParam);

    /**
     * 启停集团间仓储委托关系
     *
     * @param reqParam 启停操作请求参数
     */
    void enablePlatformStorageEntrustRelation(EnableOperateParam reqParam);

    /**
     * 查询集团间仓储委托关系列表
     *
     * @param reqParam 分页查询集团间仓储委托关系查询请求参数
     * @return 集团间仓储委托关系集合
     */
    PageInfo<StoragePlatformEntrustRelationInfoBean> queryPlatformStorageEntrustRelationList(PlatformStorageEntrustRelationReqParam reqParam);

    /**
     * 新增集团内仓储委托关系
     *
     * @param reqParam 集团内仓储委托关系
     */
    void addGroupStorageEntrustRelation(StorageGroupEntrustRelationBean reqParam);

    /**
     * 更新集团内仓储委托关系
     *
     * @param reqParam 更新集团内仓储委托关系
     */
    void updateGroupStorageEntrustRelation(StorageGroupEntrustRelationBean reqParam);

    /**
     * 业务单元更新级联更新委托关系
     *
     * @param reqParam 更新参数
     */
    void buFuncCascadeUpdateGroupStorageEntrustRelation(StorageGroupEntrustRelationBean reqParam, StateEnum stateEnu, Long groupId);

    /**
     * 启停集团内仓储委托关系
     *
     * @param reqParam 启停操作请求参数
     */
    void enableGroupStorageEntrustRelation(EnableOperateParam reqParam);

    /**
     * 查询集团内仓储委托关系列表
     *
     * @param reqParam 分页查询集团内仓储委托关系查询请求参数
     * @return 集团内仓储委托关系集合
     */
    PageInfo<StorageGroupEntrustRelationInfoBean> queryGroupStorageEntrustRelationList(GroupStorageEntrustRelationReqParam reqParam);

    /**
     * 查询集团间仓储委托关系(WMS)
     *
     * @param reqParam 集团间仓储委托关系查询参数
     * @return 集团间仓储委托关系实体数据
     */
    List<StoragePlatformEntrustRelationDto> queryPlatformStorageEntrustRelation(final StoragePlatformEntrustRelationQueryParam reqParam);

    /**
     * 查询集团间仓储委托关系(WMS)
     *
     * @param reqParam 集团间仓储委托关系查询参数
     * @return 集团间仓储委托关系实体数据
     */
    List<StorageGroupEntrustRelationDto> queryGroupStorageEntrustRelation(final StorageGroupEntrustRelationQueryParam reqParam);

    MultipleOrgTreeDto queryHasStorageErLogisticsOrg(QueryStorageEntrustRelationLogisticsOrgParam reqParam);

    /**
     * 查询具有委托关系的物流组织及其下具有结算能力的业务单元
     *
     * @return 物流组织列表信息
     */
    List<OrgListDto> queryLogisticOrgAndHasSettleableChildren(@RequestBody QueryStorageEntrustRelationLogisticsOrgParam reqParam);

    /**
     * 查询有仓储委托关系的物流组织信息
     *
     * @param reqParam 委托关系查询请求参数
     * @return 物流组织信息
     */
    PageInfo<OrgInfoDto> queryHasStorageEntrustRelationLogisticsOrg(EntrustRelationQueryParam reqParam);

    List<EntrustRelationOrgInfoDto> queryStorageErLogisticsOrgByWarehouse(QueryStorageEntrustRelationLogisticsOrgParam queryParam);
}
