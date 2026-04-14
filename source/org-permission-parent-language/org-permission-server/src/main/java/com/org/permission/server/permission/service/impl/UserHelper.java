package com.org.permission.server.permission.service.impl;


import com.common.language.util.I18nUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.org.permission.common.permission.dto.InputUserDto;
import com.org.permission.common.permission.dto.InputUserUpdateDto;
import com.org.permission.server.permission.entity.BasePermissionUserFunc;
import com.org.permission.server.permission.enums.ValidEnum;
import com.org.permission.server.permission.mapper.BasePermissionRoleFuncMapper;
import com.org.permission.server.permission.mapper.BasePermissionUserFuncMapper;
import com.common.util.message.RestMessage;
import com.org.permission.common.permission.dto.UserPermissionDto;
import com.org.permission.common.permission.dto.UserRoleDto;
import com.usercenter.client.feign.UserFeign;
import com.common.base.enums.StateEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户模块帮助类
 */
@Service
public class UserHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserHelper.class);
    @Autowired
    private BasePermissionRoleFuncMapper basePermissionRoleFuncMapper;
    @Autowired
    private BasePermissionUserFuncMapper basePermissionUserFuncMapper;
    @Autowired
    private UserFeign userFeign;
    @Autowired
    private GroupConfigHelper groupConfigHelper;

    public RestMessage insertUser(InputUserDto inputUserDto) {
        try {
            // 角色列表
            List<UserRoleDto> roleList = inputUserDto.getRoleList();
            List<Integer> funcList = inputUserDto.getFuncList();
            List<Long> orgList = inputUserDto.getOrgList();
            List<UserPermissionDto> dataList = inputUserDto.getDataList();
            if (CollectionUtils.isNotEmpty(dataList)) {
                dataList.stream().forEach(data -> {
                    boolean query = data.isQuery();
                    boolean edit = data.isEdit();
                    if (edit) {
                        data.setOptionPermission(2);
                    }
                    if (query) {//只能查询，不能编辑
                        if (!edit) {
                            data.setOptionPermission(1);
                        }
                    }
                    if (!query && !edit) {//没有设置
                        data.setOptionPermission(0);
                    }
                });
            }


            Map<String, Object> map = Maps.newHashMap();
            map.put("list", roleList);
            // 保存逻辑
            // roleList不为空建立用户和角色关系,funcList不为空建立用户和功能权限关系
            // orgList不为空建立用户和组织权限的关系,dataList不为空建立用户和数据权限的关系
            map.put("funcList", funcList);
            map.put("orgList", orgList);
            map.put("dataList", dataList);
            map.put("userId", inputUserDto.getUserId());
            map.put("groupId", inputUserDto.getGroupId());
            map.put("orgId", inputUserDto.getOrgId());
            map.put("authorUser", inputUserDto.getLoginUserId());
            map.put("authorTime", new Date());
            map.put("createdBy", inputUserDto.getLoginUserId());
            map.put("createdDate", new Date());
            map.put("createdName", inputUserDto.getLoginUserName());
            map.put("state", StateEnum.ENABLE.getCode());
            if (CollectionUtils.isNotEmpty(roleList) || CollectionUtils.isNotEmpty(funcList) || CollectionUtils.isNotEmpty(orgList) || CollectionUtils.isNotEmpty(dataList)) {
                basePermissionUserFuncMapper.insertUserRoleAndPermission(map);
            }
            return RestMessage.doSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("保存用户信息时权限部分失败，用户编码:{},创建人id {},管理员级别 {},orgid {},来源 {},邮箱 {},用户账号 {},手机号 {},身份类型 {},真实姓名 {}");
            return RestMessage.error("444", I18nUtils.getMessage("org.common.fail"));
        }

    }


    public Boolean updateUser(InputUserUpdateDto inputUserUpdateDto) {
        // 更新用户，依赖用户接口TBD
        try {
            BasePermissionUserFunc basePermissionUserFunc = new BasePermissionUserFunc();
            basePermissionUserFunc.setUserId(NumberUtils.toLong(inputUserUpdateDto.getUserId() + ""));
            basePermissionUserFunc.setState(ValidEnum.YES.getCode());
            List<BasePermissionUserFunc> basePermissionUserFuncs = basePermissionUserFuncMapper.getListBasePermissionUserFuncsByPOJO(basePermissionUserFunc);
            // 角色列表
            List<UserRoleDto> roleList = inputUserUpdateDto.getRoleList();
            List<UserPermissionDto> funcList = (inputUserUpdateDto.getFuncList() == null ? Lists.newArrayList() : inputUserUpdateDto.getFuncList());
            List<UserPermissionDto> orgList = (inputUserUpdateDto.getOrgList() == null ? Lists.newArrayList() : inputUserUpdateDto.getOrgList());
            List<UserPermissionDto> dataList = (inputUserUpdateDto.getDataList() == null ? Lists.newArrayList() : inputUserUpdateDto.getDataList());
            dataList.stream().forEach(bean -> {
                boolean query = bean.isQuery();
                boolean edit = bean.isEdit();
                if (edit) {
                    bean.setOptionPermission(2);
                }
                if (query) {//只能查询，不能编辑
                    if (!edit) {
                        bean.setOptionPermission(1);
                    }
                }
                if (!query && !edit) {//没有设置
                    bean.setOptionPermission(0);
                }
            });
            Map<String, Object> map = Maps.newHashMap();
            if (CollectionUtils.isNotEmpty(roleList)) {
                roleList = roleList.stream()
                        .collect(Collectors.toMap(UserRoleDto::getRoleId, ur -> ur, (p1, p2) -> p1))
                        .values()
                        .stream()
                        .collect(Collectors.toList());
            }
            map.put("list", roleList);

            // 保存逻辑
            // roleList不为空建立用户和角色关系,funcList不为空建立用户和功能权限关系
            // orgList不为空建立用户和组织权限的关系,dataList不为空建立用户和数据权限的关系
            map.put("funcList", funcList);
            map.put("orgList", orgList);
            map.put("dataList", dataList);
            map.put("userId", inputUserUpdateDto.getUserId());
            map.put("groupId", inputUserUpdateDto.getGroupId());
            map.put("orgId", inputUserUpdateDto.getOrgId());
            map.put("authorUser", inputUserUpdateDto.getLoginUserId());
            map.put("authorTime", new Date());
            map.put("createdBy", inputUserUpdateDto.getLoginUserId());
            map.put("createdDate", new Date());
            map.put("createdName", inputUserUpdateDto.getLoginUserName());
            map.put("state", StateEnum.ENABLE.getCode());
            if (roleList.size() > 0 || funcList.size() > 0 || orgList.size() > 0 || dataList.size() > 0) {
                basePermissionUserFuncMapper.updateUserRoleAndPermission(map);
            } else if (roleList.size() == 0 && funcList.size() == 0 && orgList.size() == 0 && dataList.size() == 0) {
                boolean isUserAuth = groupConfigHelper.isUserAuth(inputUserUpdateDto.getGroupId());//是否取用户权限
                if (isUserAuth) {
                    basePermissionUserFuncMapper.delUserRoleAndPermission(map);
                } else {
                    basePermissionUserFuncMapper.delRoleAndPermission(map);
                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}
