package com.usercenter.server.service.impl;

import com.google.common.collect.Lists;
import com.org.permission.common.org.dto.OrgInfoDto;
import com.org.permission.common.permission.dto.GroupDto;
import com.org.permission.common.permission.dto.InputAdminDto;
import com.org.permission.common.permission.dto.InputAdminUpdateDto;
import com.org.permission.common.query.BatchQueryParam;
import com.permission.client.feign.PermissionForUserFeign;
import com.usercenter.server.domain.vo.req.administratorsuser.AdministratorsSaveUpdateReq;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 业务逻辑公共抽象类
 */
public abstract class AbstractBaseAdministratorsUserService extends BaseUserCommonServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBaseAdministratorsUserService.class);

    @Autowired
    private PermissionForUserFeign permissionForUserFeign;

    protected void permissionAPI(AdministratorsSaveUpdateReq req, Long adminId, String adminName, String userName) {
        List<GroupDto> groupDtos = req.generatorGroupDto();
        if (req.getDetailId() == null) {
            //调用新增接口
            InputAdminDto inputAdminDto = new InputAdminDto();
            inputAdminDto.setAdminId(adminId);
            inputAdminDto.setAdminName(adminName);
            inputAdminDto.setLoginUserId(req.getUserId());
            inputAdminDto.setLoginUserName(userName);
            inputAdminDto.setGroupList(groupDtos);
            permissionForUserFeign.insertAdminGroup(inputAdminDto);
        } else {
            //调用修改接口
            InputAdminUpdateDto updateDto = new InputAdminUpdateDto();
            updateDto.setAdminId(adminId);
            updateDto.setAdminName(userName);
            updateDto.setLoginUserName(userName);
            updateDto.setLoginUserId(req.getUserId());
            updateDto.setGroupList(groupDtos);
            permissionForUserFeign.updateAdminGroup(updateDto);
        }
    }

    protected Map<Long, String> getOrgNames(List<Long> orgIds) {
        Map<Long, String> orgNameMap = new HashMap<>();
        if (CollectionUtils.isEmpty(orgIds)) {
            return orgNameMap;
        }
        BatchQueryParam params = new BatchQueryParam();
        params.setIds(orgIds);
        if (CollectionUtils.isNotEmpty(orgIds)) {
            List<OrgInfoDto> orgInfoDtoList = orgDomainService.batchQueryOrgInfo(params);
            if (CollectionUtils.isNotEmpty(orgInfoDtoList)) {
                return orgInfoDtoList.stream().collect(Collectors.toMap(OrgInfoDto::getId, OrgInfoDto::getOrgName, (v1, v2) -> v2));
            }
        }
        return orgNameMap;

    }

    protected String getOrgName(Long orgId) {
        if (orgId == null) {
            return null;
        }
        Map<Long, String> orgNames = getOrgNames(Lists.newArrayList(orgId));
        return orgNames.get(orgId);
    }


}
