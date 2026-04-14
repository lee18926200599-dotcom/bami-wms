package com.org.permission.server.permission.dto.req;

import com.google.common.collect.Lists;
import com.org.permission.server.permission.entity.BasePermissionRoleFunc;
import com.org.permission.server.permission.enums.PermissionTypeEnum;
import com.org.permission.server.permission.enums.ValidEnum;
import com.common.framework.user.FplUserUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
*角色权限关联表管理
*/
@Data
@ApiModel("批量保存角色权限信息")
public class BatchSaveFuncReq implements Serializable {

	/**
	 * 角色id
	 */
    @ApiModelProperty("角色id")
	private Long roleId;

	/**
	 * 集团id
	 */
    @ApiModelProperty("集团id")
	private Long groupId;

    /**
     * 授权人id
     */
    @ApiModelProperty("授权人id")
    private Long authUserId;

	/**
	 * 菜单权限
	 */
    @ApiModelProperty("菜单权限")
	List<SaveFuncReq> funcList;

	/**
	 * 组织权限
	 */
    @ApiModelProperty("组织权限")
	List<SaveFuncReq> orgList;

    public static List<BasePermissionRoleFunc> convertList(List<SaveFuncReq> saveFuncReqList, Long groupId, Long authUserId, PermissionTypeEnum permissionType, Long roleId) {
        List<BasePermissionRoleFunc> result = Lists.newArrayList();
        if(ObjectUtils.isEmpty(saveFuncReqList)){
        	return result;
		}
        for (SaveFuncReq saveFuncReq : saveFuncReqList) {
            BasePermissionRoleFunc item = new BasePermissionRoleFunc();
            BeanUtils.copyProperties(saveFuncReq, item);
            item.setState(ValidEnum.YES.getCode());
            item.setAuthorUser(authUserId);
			item.setAuthorTime(new Date());
            item.setPermissionType(permissionType.getCode());
            item.setGroupId(groupId);
            item.setCreatedBy(FplUserUtil.getUserId());
            item.setCreatedName(FplUserUtil.getUserName());
            item.setCreatedDate(new Date());
            if(!ObjectUtils.isEmpty(roleId)){
				item.setRoleId(roleId);
			}
            result.add(item);
        }
        return result;
    }
}

