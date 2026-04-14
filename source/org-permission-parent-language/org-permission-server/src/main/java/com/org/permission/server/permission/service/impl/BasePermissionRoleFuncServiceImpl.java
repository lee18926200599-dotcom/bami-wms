package com.org.permission.server.permission.service.impl;

import com.org.permission.server.permission.dto.req.BatchSaveFuncReq;
import com.org.permission.server.permission.entity.BasePermissionRoleFunc;
import com.org.permission.server.permission.enums.PermissionTypeEnum;
import com.org.permission.server.permission.vo.BasePermissionRoleFuncVo;
import com.org.permission.server.permission.mapper.BasePermissionRoleFuncMapper;
import com.org.permission.server.permission.service.IBasePermissionRoleFuncService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * base_permission_role_funcServiceImpl类  角色权限关联表管理
 */
@Service
public class BasePermissionRoleFuncServiceImpl implements IBasePermissionRoleFuncService {
    @Autowired
    private BasePermissionRoleFuncMapper dao;

    public int addBasePermissionRoleFunc(BasePermissionRoleFunc base_permission_role_func) {
        return dao.addBasePermissionRoleFunc(base_permission_role_func);
    }

    public int delBasePermissionRoleFunc(Integer Id) {
        return dao.delBasePermissionRoleFunc(Id);
    }

    public int delBasePermissionRoleFuncTrue(Integer Id) {
        return dao.delBasePermissionRoleFuncTrue(Id);
    }

    public int updateBasePermissionRoleFunc(BasePermissionRoleFunc base_permission_role_func) {
        return dao.updateBasePermissionRoleFunc(base_permission_role_func);
    }

    public BasePermissionRoleFunc getBasePermissionRoleFuncById(Integer Id) {
        return dao.getBasePermissionRoleFuncByID(Id);
    }

    public int getBasePermissionRoleFuncCount() {
        return dao.getBasePermissionRoleFuncCount();
    }

    public int getBasePermissionRoleFuncCountAll() {
        return dao.getBasePermissionRoleFuncCountAll();
    }

    public List<BasePermissionRoleFunc> getListBasePermissionRoleFuncsByPage(BasePermissionRoleFuncVo base_permission_role_func) {
        return dao.getListBasePermissionRoleFuncsByPage(base_permission_role_func);
    }

    public List<BasePermissionRoleFunc> getListBasePermissionRoleFuncsByPOJO(BasePermissionRoleFunc base_permission_role_func) {
        return dao.getListBasePermissionRoleFuncsByPOJO(base_permission_role_func);
    }

    public List<BasePermissionRoleFunc> getListBasePermissionRoleFuncsByPojoPage(BasePermissionRoleFunc base_permission_role_func) {
        Map map = new HashMap();
        map.put("pojo", base_permission_role_func);
        return dao.getListBasePermissionRoleFuncsByPojoPage(map);
    }

    @Override
    @Transactional
    public void batchSaveFunc(BatchSaveFuncReq batchSaveFuncReq) {
        //先删除权限
        BasePermissionRoleFunc delCondition = new BasePermissionRoleFunc();
        delCondition.setRoleId(batchSaveFuncReq.getRoleId());
        delCondition.setGroupId(batchSaveFuncReq.getGroupId());
        delCondition.setPermissionType(PermissionTypeEnum.ORG.getCode());
        dao.delByCondition(delCondition);
        delCondition.setPermissionType(PermissionTypeEnum.FUNC.getCode());
        dao.delByCondition(delCondition);
        //再插入功能权限
        if (CollectionUtils.isNotEmpty(batchSaveFuncReq.getFuncList())) {
            List<BasePermissionRoleFunc> basePermissionRoleFuncList = BatchSaveFuncReq.convertList(batchSaveFuncReq.getFuncList(),
                    batchSaveFuncReq.getGroupId(), batchSaveFuncReq.getAuthUserId(), PermissionTypeEnum.FUNC, batchSaveFuncReq.getRoleId());
            dao.batchInsert(basePermissionRoleFuncList);
        }

        //再插入组织权限
        if (CollectionUtils.isNotEmpty(batchSaveFuncReq.getOrgList())) {
            List<BasePermissionRoleFunc> basePermissionRoleOrgList = BatchSaveFuncReq.convertList(batchSaveFuncReq.getOrgList(),
                    batchSaveFuncReq.getGroupId(), batchSaveFuncReq.getAuthUserId(), PermissionTypeEnum.ORG, batchSaveFuncReq.getRoleId());
            dao.batchInsert(basePermissionRoleOrgList);
        }
    }

}

