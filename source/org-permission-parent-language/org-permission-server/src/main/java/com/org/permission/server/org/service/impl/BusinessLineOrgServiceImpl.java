package com.org.permission.server.org.service.impl;

import com.org.permission.server.domain.permission.PermissionDomainService;
import com.org.permission.server.org.dto.BusinessLineOrgDto;
import com.org.permission.server.org.bean.BaseBusinessLine;
import com.org.permission.server.org.bean.BaseBusinessLineOrg;
import com.org.permission.server.org.mapper.BusinessLineOrgMapper;
import com.org.permission.server.org.service.BusinessLineOrgService;
import com.org.permission.server.org.service.BusinessLineService;
import com.org.permission.common.enums.BooleanEnum;
import com.org.permission.common.permission.param.SyncDataPermissionParam;
import com.org.permission.server.permission.enums.ValidEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *  组织业务线关系
 */
@Service
public class BusinessLineOrgServiceImpl implements BusinessLineOrgService {

    @Autowired
    private BusinessLineOrgMapper businessLineOrgMapper;
    @Autowired
    private BusinessLineService businessLineService;

    @Resource
    private PermissionDomainService permissionDomainService;

    /**
     * 新增(兼容修改)
     *
     * @param businessLineOrgDto
     */
    public void saveOrUpdate(BusinessLineOrgDto businessLineOrgDto) {
        if (Objects.isNull(businessLineOrgDto)) {
            return;
        }
        if (Objects.isNull(businessLineOrgDto.getGroupId())) {
            return;
        }
        if (Objects.isNull(businessLineOrgDto.getOrgId())) {
            return;
        }
        if (CollectionUtils.isEmpty(businessLineOrgDto.getLineIdList())) {
            return;
        }
        // 查出已有业务线
        BaseBusinessLineOrg param = new BaseBusinessLineOrg();
        param.setGroupId(businessLineOrgDto.getGroupId());
        List<BaseBusinessLineOrg> groupExistList = businessLineOrgMapper.listByParam(param);
        List<BaseBusinessLineOrg> existList = groupExistList.stream().filter(item -> item.getOrgId().equals(businessLineOrgDto.getOrgId())).collect(Collectors.toList());
        List<BaseBusinessLineOrg> insertList = new ArrayList<>();
        List<BaseBusinessLineOrg> updateList = new ArrayList<>();
        Set<Long> lineIdList = new HashSet<>();
        lineIdList.addAll(businessLineOrgDto.getLineIdList());
        lineIdList.addAll(groupExistList.stream().map(BaseBusinessLineOrg::getLineId).collect(Collectors.toSet()));
        if (CollectionUtils.isEmpty(existList)) {
            // 全部保存
            for (Long lineId : businessLineOrgDto.getLineIdList()) {
                BaseBusinessLineOrg baseBusinessLineOrg = buildBl(businessLineOrgDto, lineId);
                insertList.add(baseBusinessLineOrg);
            }
        } else {
            // 根据已有数据处理新增、删除
            Map<Long, BaseBusinessLineOrg> map = existList.stream().collect(Collectors.toMap(BaseBusinessLineOrg::getLineId, Function.identity(),(k1,k2)->k2));
            for (Long lineId : businessLineOrgDto.getLineIdList()) {
                BaseBusinessLineOrg baseBusinessLineOrg = map.get(lineId);
                if (baseBusinessLineOrg == null) {
                    // 新增
                    BaseBusinessLineOrg insertBl = buildBl(businessLineOrgDto, lineId);
                    insertList.add(insertBl);
                } else {
                    if (Objects.equals(BooleanEnum.TRUE.getCode(), baseBusinessLineOrg.getDeletedFlag())) {
                        // 如果是已经删除的，恢复可用状态
                        BaseBusinessLineOrg updateBl = new BaseBusinessLineOrg();
                        updateBl.setId(baseBusinessLineOrg.getId());
                        updateBl.setLineId(baseBusinessLineOrg.getLineId());
                        updateBl.setOrgId(baseBusinessLineOrg.getOrgId());
                        updateBl.setGroupId(baseBusinessLineOrg.getGroupId());
                        updateBl.setDeletedFlag(BooleanEnum.FALSE.getCode());
                        updateBl.setModifiedBy(businessLineOrgDto.getModifiedBy());
                        updateBl.setModifiedDate(new Date());
                        updateBl.setModifiedName(businessLineOrgDto.getModifiedName());
                        updateList.add(updateBl);
                    }
                }
            }
            // 不在新增里面都删除
            List<BaseBusinessLineOrg> delList = existList.stream().filter(item -> !businessLineOrgDto.getLineIdList().contains(item.getLineId())).collect(Collectors.toList());
            for (BaseBusinessLineOrg delData : delList) {
                BaseBusinessLineOrg updateBl = new BaseBusinessLineOrg();
                updateBl.setId(delData.getId());
                updateBl.setLineId(delData.getLineId());
                updateBl.setOrgId(businessLineOrgDto.getOrgId());
                updateBl.setGroupId(businessLineOrgDto.getGroupId());
                updateBl.setDeletedFlag(BooleanEnum.TRUE.getCode());
                updateBl.setDeletedTime(new Date());
                updateBl.setModifiedBy(businessLineOrgDto.getModifiedBy());
                updateBl.setModifiedDate(new Date());
                updateBl.setModifiedName(businessLineOrgDto.getModifiedName());
                updateList.add(updateBl);
            }
        }
        List<BaseBusinessLine> lineList = businessLineService.getByIdList(new ArrayList<>(lineIdList));
        Map<Long, BaseBusinessLine> lineMap = lineList.stream().collect(Collectors.toMap(BaseBusinessLine::getId, Function.identity()));
        List<SyncDataPermissionParam> syncDataList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(insertList)) {
            businessLineOrgMapper.insertList(insertList);
            for (BaseBusinessLineOrg baseBusinessLineOrg : insertList) {
                SyncDataPermissionParam syncDataPermissionParam = new SyncDataPermissionParam();
                syncDataPermissionParam.setLineId(baseBusinessLineOrg.getLineId());
                syncDataPermissionParam.setGroupId(baseBusinessLineOrg.getGroupId());
                syncDataPermissionParam.setOrgId(baseBusinessLineOrg.getOrgId());
                syncDataPermissionParam.setUserId(baseBusinessLineOrg.getCreatedBy());
                syncDataPermissionParam.setState(ValidEnum.YES.getCode());
                BaseBusinessLine businessLine = lineMap.get(baseBusinessLineOrg.getLineId());
                if (businessLine == null) {
                    syncDataPermissionParam.setState(ValidEnum.NO.getCode());
                    syncDataList.add(syncDataPermissionParam);
                    continue;
                }
                syncDataPermissionParam.setLineName(businessLine.getBusinessLineName());
                syncDataPermissionParam.setLineCode(businessLine.getBusinessLineCode());
                syncDataList.add(syncDataPermissionParam);
            }
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
            businessLineOrgMapper.updateList(updateList);
            // 修改情况下，如果有有删除数据权限的，需验证当前集团下是不是都已不存在当前数据权限
            Set<Long> otherAbleLineIdList = groupExistList.stream().filter(item -> !Objects.equals(item.getOrgId(), businessLineOrgDto.getOrgId()) && Objects.equals(item.getDeletedFlag(), BooleanEnum.FALSE.getCode())).map(BaseBusinessLineOrg::getLineId).collect(Collectors.toSet());
            for (BaseBusinessLineOrg baseBusinessLineOrg : updateList) {
                SyncDataPermissionParam syncDataPermissionParam = new SyncDataPermissionParam();
                syncDataPermissionParam.setLineId(baseBusinessLineOrg.getLineId());
                syncDataPermissionParam.setGroupId(baseBusinessLineOrg.getGroupId());
                syncDataPermissionParam.setOrgId(baseBusinessLineOrg.getOrgId());
                syncDataPermissionParam.setUserId(baseBusinessLineOrg.getModifiedBy());
                syncDataPermissionParam.setState(Objects.equals(BooleanEnum.FALSE.getCode(), baseBusinessLineOrg.getDeletedFlag()) ? ValidEnum.YES.getCode() : ValidEnum.NO.getCode());
                if (Objects.equals(ValidEnum.NO.getCode(), syncDataPermissionParam.getState()) && otherAbleLineIdList.contains(baseBusinessLineOrg.getLineId())) {
                    syncDataPermissionParam.setState(ValidEnum.YES.getCode());
                }
                BaseBusinessLine businessLine = lineMap.get(baseBusinessLineOrg.getLineId());
                if (businessLine == null) {
                    syncDataPermissionParam.setState(ValidEnum.NO.getCode());
                    syncDataList.add(syncDataPermissionParam);
                    continue;
                }
                syncDataPermissionParam.setLineName(businessLine.getBusinessLineName());
                syncDataPermissionParam.setLineCode(businessLine.getBusinessLineCode());
                syncDataList.add(syncDataPermissionParam);
            }
        }

        // 同步数据权限
        for (SyncDataPermissionParam syncDataPermissionParam : syncDataList) {
            permissionDomainService.syncBusinessLine(syncDataPermissionParam);
        }
    }

    private BaseBusinessLineOrg buildBl(BusinessLineOrgDto businessLineOrgDto, Long lineId) {
        BaseBusinessLineOrg baseBusinessLineOrg = new BaseBusinessLineOrg();
        baseBusinessLineOrg.setLineId(lineId);
        baseBusinessLineOrg.setDeletedFlag(BooleanEnum.FALSE.getCode());
        baseBusinessLineOrg.setGroupId(businessLineOrgDto.getGroupId());
        baseBusinessLineOrg.setOrgId(businessLineOrgDto.getOrgId());
        baseBusinessLineOrg.setCreatedBy(businessLineOrgDto.getCreatedBy());
        baseBusinessLineOrg.setCreatedDate(new Date());
        baseBusinessLineOrg.setCreatedName(businessLineOrgDto.getCreatedName());
        baseBusinessLineOrg.setModifiedBy(businessLineOrgDto.getCreatedBy());
        baseBusinessLineOrg.setModifiedDate(new Date());
        baseBusinessLineOrg.setModifiedName(businessLineOrgDto.getCreatedName());
        return baseBusinessLineOrg;
    }


    /**
     * 查询可用的业务线
     *
     * @param orgId
     * @return
     */
    public List<BusinessLineOrgDto> getUsableLineByOrg(Long orgId) {
        // 查出已有业务线
        List<BusinessLineOrgDto> list = businessLineOrgMapper.getDtoList(null, orgId);
        return list;
    }

    /**
     * 查询可用的业务线
     *
     * @param groupId
     * @return
     */
    public Map<Long, List<Long>> getUsableLineByGroup(Long groupId) {
        // 查出已有业务线
        List<BusinessLineOrgDto> list = businessLineOrgMapper.getDtoList(groupId, null);
        if (CollectionUtils.isEmpty(list)) {
            return new HashMap<>();
        }
        Map<Long, List<Long>> lineMap = list.stream().collect(Collectors.groupingBy(BusinessLineOrgDto::getOrgId, Collectors.mapping(BusinessLineOrgDto::getLineId, Collectors.toList())));
        return lineMap;
    }
}
