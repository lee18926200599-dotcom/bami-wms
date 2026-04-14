package com.org.permission.server.permission.service.impl;


import com.common.language.util.I18nUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.org.permission.server.permission.entity.BasePermissionAdminGroup;
import com.common.util.message.RestMessage;
import com.org.permission.common.permission.dto.GroupDto;
import com.org.permission.common.permission.dto.InputAdminDto;
import com.org.permission.common.permission.dto.InputAdminUpdateDto;
import com.org.permission.server.permission.mapper.BasePermissionAdminGroupMapper;
import com.org.permission.server.permission.service.IBasePermissionAdminGroupService;
import com.usercenter.common.dto.FplUser;
import com.common.base.enums.StateEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 用户表中的管理员和集团关系表管理
 */
@Service
public class BasePermissionAdminGroupServiceImpl implements IBasePermissionAdminGroupService {
    private final static Logger LOGGER = LoggerFactory.getLogger(BasePermissionAdminGroupServiceImpl.class);
    @Autowired
    private BasePermissionAdminGroupMapper dao;

    public int update(BasePermissionAdminGroup basePermissionAdminGroup) {
        return dao.update(basePermissionAdminGroup);
    }

    public BasePermissionAdminGroup getBasePermissionAdminGroupById(Integer id) {
        return dao.load(id);
    }

    public List<BasePermissionAdminGroup> getListBasePermissionAdminGroupsByPOJO(BasePermissionAdminGroup basePermissionAdminGroup) {
        return dao.getListUnion(basePermissionAdminGroup);
    }

    @Override
    public List<BasePermissionAdminGroup> getListBasePermissionAdminByUserIdSet(Set<Long> userIdSet) {
        return dao.getListByAdminId(userIdSet);
    }

    @Override
    public List<BasePermissionAdminGroup> getChooseList(BasePermissionAdminGroup basePermissionAdminGroup) {
        return dao.listByParam(basePermissionAdminGroup);
    }


    @Override
    public void batchUpdateGroupAdmin(Map map) {
        dao.batchUpdateGroupAdmin(map);
    }

    @Override
    public RestMessage insertAdminGroup(InputAdminDto inputAdminDto) {
        RestMessage<FplUser> result = new RestMessage<>();
        try {
            // 创建管理员和集团关系
            if (CollectionUtils.isNotEmpty(inputAdminDto.getGroupList())) {
                List<BasePermissionAdminGroup> list = new ArrayList<>();
                for (GroupDto groupDto : inputAdminDto.getGroupList()) {
                    BasePermissionAdminGroup basePermissionAdminGroup = new BasePermissionAdminGroup();
                    basePermissionAdminGroup.setAdminId(inputAdminDto.getAdminId());
                    basePermissionAdminGroup.setAdminName(inputAdminDto.getAdminName());
                    basePermissionAdminGroup.setGroupName(groupDto.getGroupName());
                    basePermissionAdminGroup.setGroupId(groupDto.getGroupId());
                    basePermissionAdminGroup.setGroupCode(groupDto.getGroupCode());
                    basePermissionAdminGroup.setEffectiveTime(groupDto.getEffectiveTime());
                    basePermissionAdminGroup.setExpireTime(groupDto.getExpireTime());
                    basePermissionAdminGroup.setCreatedBy(inputAdminDto.getLoginUserId());
                    basePermissionAdminGroup.setCreatedName(inputAdminDto.getLoginUserName());
                    basePermissionAdminGroup.setCreatedDate(new Date());
                    basePermissionAdminGroup.setModifiedBy(inputAdminDto.getLoginUserId());
                    basePermissionAdminGroup.setModifiedName(inputAdminDto.getLoginUserName());
                    basePermissionAdminGroup.setModifiedDate(new Date());
                    basePermissionAdminGroup.setState(StateEnum.ENABLE.getCode());
                    list.add(basePermissionAdminGroup);
                }
                if (CollectionUtils.isNotEmpty(list)) {
                    dao.insertList(list);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("新增管理员和管理员集团关系错误--->管理员id：{},管理员账号：{},创建人id:{}", inputAdminDto.getAdminId(), inputAdminDto.getAdminName(), inputAdminDto.getLoginUserId());
            return RestMessage.error(I18nUtils.getMessage("org.common.fail"));
        }
    }

    @Override
    @Transactional
    public RestMessage updateAdminGroup(InputAdminUpdateDto inputAdminUpdateDto) {
        // 更新管理员
        RestMessage<FplUser> userResult = new RestMessage<>();
        try {
            // 更新管理员和集团关系
            Map<String, Object> map = Maps.newHashMap();
            List<GroupDto> addList = Lists.newArrayList();
            List<Long> delList = Lists.newArrayList();
            List<GroupDto> updateList = inputAdminUpdateDto.getGroupList();
            Map<String, Object> param = Maps.newHashMap();
            param.put("adminId", inputAdminUpdateDto.getAdminId());
            List<GroupDto> chooseGroupDtos = dao.getChooseList(param);
            if (CollectionUtils.isNotEmpty(chooseGroupDtos)) {
                dao.deleteByAdminId(inputAdminUpdateDto.getAdminId());
            }
            List<BasePermissionAdminGroup> list = new ArrayList<>();
            for (GroupDto groupDto : updateList) {
                BasePermissionAdminGroup basePermissionAdminGroup = new BasePermissionAdminGroup();
                basePermissionAdminGroup.setAdminId(inputAdminUpdateDto.getAdminId());
                basePermissionAdminGroup.setAdminName(inputAdminUpdateDto.getAdminName());
                basePermissionAdminGroup.setGroupName(groupDto.getGroupName());
                basePermissionAdminGroup.setGroupId(groupDto.getGroupId());
                basePermissionAdminGroup.setGroupCode(groupDto.getGroupCode());
                basePermissionAdminGroup.setEffectiveTime(groupDto.getEffectiveTime());
                basePermissionAdminGroup.setExpireTime(groupDto.getExpireTime());
                basePermissionAdminGroup.setCreatedBy(inputAdminUpdateDto.getLoginUserId());
                basePermissionAdminGroup.setCreatedName(inputAdminUpdateDto.getLoginUserName());
                basePermissionAdminGroup.setCreatedDate(new Date());
                basePermissionAdminGroup.setModifiedBy(inputAdminUpdateDto.getLoginUserId());
                basePermissionAdminGroup.setModifiedName(inputAdminUpdateDto.getLoginUserName());
                basePermissionAdminGroup.setModifiedDate(new Date());
                basePermissionAdminGroup.setState(StateEnum.ENABLE.getCode());
                list.add(basePermissionAdminGroup);
            }
            if (CollectionUtils.isNotEmpty(list)) {
                dao.insertList(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("更新管理员和管理员集团关系错误--->管理员id：{},管理员：{},创建人id:{}", inputAdminUpdateDto.getAdminId(), inputAdminUpdateDto.getAdminName(), inputAdminUpdateDto.getLoginUserId());
            return RestMessage.error(I18nUtils.getMessage("org.common.fail"));
        }
        return userResult;
    }


}
