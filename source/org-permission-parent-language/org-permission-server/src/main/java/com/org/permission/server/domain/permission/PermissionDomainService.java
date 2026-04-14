package com.org.permission.server.domain.permission;

import com.alibaba.fastjson.JSON;
import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.common.permission.dto.UserOrgPermissionDto;
import com.org.permission.common.permission.param.SyncDataPermissionParam;
import com.org.permission.server.org.enums.OrgTypeEnum;
import com.org.permission.server.permission.service.IBasePermissionDataService;
import com.org.permission.server.permission.service.impl.GroupConfigHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 权限领域封装
 */
@Service(value = "permissionDomainService")
public class PermissionDomainService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionDomainService.class);

    @Resource
    private GroupConfigHelper groupConfigHelper;

    @Autowired
    private IBasePermissionDataService basePermissionDataService;

    /**
     * 获取用户相应的组织权限
     *
     * @param userId  用户ID
     * @param groupId 集团ID
     * @param orgType 组织类型
     * @return 对应组织类型的ID
     */
    public Set<Long> getOrgsInPermission(Long userId, Long groupId, OrgTypeEnum orgType) {

        List<UserOrgPermissionDto> userOrgPermissionDtos;
        try {
            LOGGER.info("get user org permission param,custId:{},groupId:{},orgName:{}", userId, groupId, "");
            userOrgPermissionDtos = groupConfigHelper.getUserOrgPermissionByStrategy(userId.longValue(), groupId, "");
            LOGGER.info("get user org permission result:{}.", JSON.toJSONString(userOrgPermissionDtos));
        } catch (Exception ex) {
            LOGGER.warn("org service inner error.", ex);
            throw new OrgException(OrgErrorCode.ORG_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.domain.permission.system.exception")+":" + ex.getMessage());
        }
        if (CollectionUtils.isEmpty(userOrgPermissionDtos)) {
            return Collections.emptySet();
        }

        Set<Long> orgIds = new HashSet<>();
        for (UserOrgPermissionDto orgPermission : userOrgPermissionDtos) {
            switch (orgType) {
                case GLOBAL:
                    if (OrgTypeEnum.GLOBAL.getName().equals(orgPermission.getOrgType())) {
                        orgIds.add(Long.valueOf(orgPermission.getPermissionId()));
                    }
                    break;
                case GROUP:
                    if (OrgTypeEnum.GROUP.getName().equals(orgPermission.getOrgType())) {
                        orgIds.add(Long.valueOf(orgPermission.getPermissionId()));
                    }
                    break;
                case ORGANIZATION:
                    if (OrgTypeEnum.ORGANIZATION.getName().equals(orgPermission.getOrgType())) {
                        orgIds.add(Long.valueOf(orgPermission.getPermissionId()));
                    }
                    break;
                case DEPARTMENT:
                    if (OrgTypeEnum.DEPARTMENT.getName().equals(orgPermission.getOrgType())) {
                        orgIds.add(Long.valueOf(orgPermission.getPermissionId()));
                    }
                    break;
                case ALL:
                    orgIds.add(Long.valueOf(orgPermission.getPermissionId()));
                    break;
                default:

            }
        }
        return orgIds;
    }

    public void syncBusinessLine(SyncDataPermissionParam param) {
        basePermissionDataService.syncBusinessLine(param);
    }
}
