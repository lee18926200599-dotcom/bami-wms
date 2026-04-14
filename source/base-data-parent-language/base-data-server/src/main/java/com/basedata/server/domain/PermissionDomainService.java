package com.basedata.server.domain;

import com.alibaba.fastjson.JSON;
import com.common.util.message.RestMessage;
import com.common.util.message.RestMsgUtils;
import com.org.permission.common.permission.dto.DataPermissionDto;
import com.org.permission.common.permission.param.GetDataPermissionParam;
import com.org.permission.common.permission.param.PermissionUserParam;
import com.org.permission.common.permission.param.SyncDataPermissionParam;
import com.permission.client.feign.DataPermissionFeign;
import com.permission.client.feign.PermissionFeign;
import com.permission.client.feign.UserDataPermissionFeign;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 权限相关接口
 */
@Component("permissionDomainService")
@Slf4j
public class PermissionDomainService {
    @Resource
    private UserDataPermissionFeign userDataPermissionFeign;

    @Resource
    private PermissionFeign permissionFeign;
    @Resource
    private DataPermissionFeign dataPermissionFeign;

    /**
     * 根据集团和用户ID获取仓库数据权限ID
     *
     * @param groupId
     * @param userId
     * @return 仓库ID集合
     */
    public List<Long> getWarehouseIdList(Long groupId, Long userId) {
        List<DataPermissionDto> list = getWarehouseDataList(groupId, userId);
        return list.stream().map(DataPermissionDto::getPermissionId).distinct().collect(Collectors.toList());
    }

    /**
     * 根据集团和用户ID获取仓库数据权限ID
     *
     * @param groupId
     * @param userId
     * @return 数据权限对象集合
     */
    public List<DataPermissionDto> getWarehouseDataList(Long groupId, Long userId) {
        GetDataPermissionParam getDataPermissionParam = new GetDataPermissionParam();
        getDataPermissionParam.setGroupId(groupId);
        getDataPermissionParam.setUserId(userId);
        getDataPermissionParam.setManagementId(1);
        RestMessage<List<DataPermissionDto>> restMessage = userDataPermissionFeign.getDataPermissons(getDataPermissionParam);
        return RestMsgUtils.retrieveResult(restMessage);
    }

    /**
     * 根据集团和用户ID获取组织权限ID
     *
     * @param groupId
     * @param userId
     * @return 组织ID集合
     */
    public List<Long> getOrgList(Long groupId, Long userId) {
        PermissionUserParam permissionUserParam = new PermissionUserParam();
        permissionUserParam.setGroupId(groupId);
        permissionUserParam.setUserId(userId);
        RestMessage<Set<Long>> restMessage = permissionFeign.getOrgIdListByUidAndGroupId(permissionUserParam);
        Set<Long> orgIdList = restMessage.getData();
        if (CollectionUtils.isEmpty(orgIdList)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(orgIdList);
    }

    /**
     * 根据集团ID和用户ID，获取组织权限下的数据权限
     *
     * @param groupId
     * @param userId
     * @return 仓库ID集合
     */
    public List<Long> getWarehouseIdListFromOrgAndData(Long groupId, Long userId) {
        List<Long> orgIdList = getOrgList(groupId, userId);
        if (CollectionUtils.isEmpty(orgIdList)) {
            return new ArrayList<>();
        }
        List<DataPermissionDto> dataPermissionDtoList = getWarehouseDataList(groupId, userId);
        if (CollectionUtils.isEmpty(dataPermissionDtoList)) {
            return new ArrayList<>();
        }
        return dataPermissionDtoList.stream().filter(data -> orgIdList.contains(data.getOrgId())).map(DataPermissionDto::getPermissionId).collect(Collectors.toList());
    }

    public void syncWarehouse(SyncDataPermissionParam syncDataPermissionParam) {
        log.info("同步仓库权限数据源--->{}", JSON.toJSONString(syncDataPermissionParam));
        RestMessage restMessage = dataPermissionFeign.syncWarehouse(syncDataPermissionParam);
        log.info("同步仓库权限数据源结果--->{}", JSON.toJSONString(restMessage));
    }
}
