package com.org.permission.server.permission.service.impl;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.PermissionException;
import com.org.permission.server.permission.entity.BasePermissionGroupData;
import com.org.permission.server.permission.vo.BasePermissionGroupDataVo;
import com.org.permission.server.permission.mapper.BasePermissionGroupDataMapper;
import com.org.permission.server.permission.service.IBasePermissionGroupDataService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * base_permission_group_dataServiceImpl类  集团的数据权限关系表管理
 */
@Service
public class BasePermissionGroupDataServiceImpl implements IBasePermissionGroupDataService {
    @Autowired
    private BasePermissionGroupDataMapper dao;

    public int addBasePermissionGroupData(BasePermissionGroupData base_permission_group_data) {
        return dao.addBasePermissionGroupData(base_permission_group_data);
    }

    public int delBasePermissionGroupData(Integer Id) {
        return dao.delBasePermissionGroupData(Id);
    }

    public int delBasePermissionGroupDataTrue(Integer Id) {
        return dao.delBasePermissionGroupDataTrue(Id);
    }

    public int updateBasePermissionGroupData(BasePermissionGroupData base_permission_group_data) {
        return dao.updateBasePermissionGroupData(base_permission_group_data);
    }

    public BasePermissionGroupData getBasePermissionGroupDataById(Integer Id) {
        return dao.getBasePermissionGroupDataByID(Id);
    }

    public int getBasePermissionGroupDataCount() {
        return dao.getBasePermissionGroupDataCount();
    }

    public int getBasePermissionGroupDataCountAll() {
        return dao.getBasePermissionGroupDataCountAll();
    }

    public List<BasePermissionGroupData> getListBasePermissionGroupDatasByPage(BasePermissionGroupDataVo base_permission_group_data) {
        return dao.getListBasePermissionGroupDatasByPage(base_permission_group_data);
    }

    public List<BasePermissionGroupData> getListBasePermissionGroupDatasByPOJO(BasePermissionGroupData base_permission_group_data) {
        return dao.getListBasePermissionGroupDatasByPOJO(base_permission_group_data);
    }

    public List<BasePermissionGroupData> getListBasePermissionGroupDatasByPojoPage(BasePermissionGroupData base_permission_group_data) {
        Map map = new HashMap();
        map.put("pojo", base_permission_group_data);
        return dao.getListBasePermissionGroupDatasByPojoPage(map);
    }

    @Override
    public void adjustUserData(HashMap<String, Object> immutableMap) {
        List addList = (List) immutableMap.get("addList");
        List delList = (List) immutableMap.get("delList");
        if (CollectionUtils.isNotEmpty(addList)) {
            dao.inserBath(immutableMap);
        }
        if (CollectionUtils.isNotEmpty(delList)) {
            dao.delBatch(immutableMap);
        }
    }

    @Override
    public void adjustRoleData(HashMap<String, Object> immutableMap) {
        List addList = (List) immutableMap.get("addList");
        List delList = (List) immutableMap.get("delList");
        if (CollectionUtils.isNotEmpty(addList)) {
            if(dao.inserRoleDataBath(immutableMap)<1){
            	throw new PermissionException(-1, I18nUtils.getMessage("org.common.fail"));
            }
        }
        if (CollectionUtils.isNotEmpty(delList)) {
            dao.delRoleDataBatch(immutableMap);
        }
    }

}

