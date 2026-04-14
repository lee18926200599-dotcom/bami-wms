package com.usercenter.common.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("查询条件")
public class UserStaffMapFindOneReq implements Serializable {

    /**
     * 状态：false 未启用，true启用
     */
    @ApiModelProperty(value = "状态：true 禁用，false 未禁用")
    private Boolean disabled;

    /**
     * 是否删除：false未删除， true 删除
     */
    @ApiModelProperty(value = " 是否删除：false未删除， true 删除")
    private Boolean deleted;

    @ApiModelProperty(value = "人员id")
    private Long staffId;

    @ApiModelProperty(value = "用户id")
    private Long userId;


    private static final long serialVersionUID = 1L;

    /**
     * 推荐使用静态create方法创建查询条件实体
     */
    @Deprecated()
    public UserStaffMapFindOneReq(){

    }


    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 创建人员查询实体（disabled/deleted=false）
     * @param staffId 用户id
     * @return 用户人员映射
     **/
    public static UserStaffMapFindOneReq createStaffQuery(Long staffId){
        return createQuery(staffId, null, false, false);
    }

    /**
     * 创建人员查询实体（deleted=false）
     * @param staffId 人员id
     * @param disabled 是否禁用：false 未禁用 true 禁用
     * @return 用户人员映射
     **/
    public static UserStaffMapFindOneReq createStaffQuery(Long staffId, Boolean disabled){
        return createQuery(staffId, null, disabled, false);
    }

    /**
     * 创建人员查询实体
     * @param staffId 人员id
     * @param disabled 是否禁用：false 未禁用 true 禁用
     * @param deleted 是否删除：false 未删除 true 删除
     * @return 用户人员映射
     **/
    public static UserStaffMapFindOneReq createStaffQuery(Long staffId, Boolean disabled, Boolean deleted){
        return createQuery(staffId, null, disabled, deleted);
    }

    /**
     * 创建用户查询实体（disabled/deleted=false）
     * @param userId 用户id
     * @return 用户人员映射
     **/
    public static UserStaffMapFindOneReq createUserQuery(Long userId){
        return createQuery(null, userId, false, false);
    }

    /**
     * 创建用户查询实体（deleted=false）
     * @param userId 用户id集合
     * @param disabled 是否禁用：false 未禁用 true 禁用
     * @return 用户人员映射
     **/
    public static UserStaffMapFindOneReq createUserQuery(Long userId, Boolean disabled){
        return createQuery(null, userId, disabled, false);
    }

    /**
     * 创建用户查询实体（deleted=false）
     * @param userId 用户id集合
     * @param disabled 是否禁用：false 未禁用 true 禁用
     * @param deleted 是否删除：false 未删除 true 删除
     * @return 用户人员映射
     **/
    public static UserStaffMapFindOneReq createUserQuery(Long userId, Boolean disabled, Boolean deleted){
        return createQuery(null, userId, disabled, deleted);
    }


    public static UserStaffMapFindOneReq createQuery(Long staffId, Long userId, Boolean disabled, Boolean deleted) {
        UserStaffMapFindOneReq findDTO = new UserStaffMapFindOneReq();
        findDTO.setStaffId(staffId);
        findDTO.setUserId(userId);
        findDTO.setDisabled(disabled);
        findDTO.setDeleted(deleted);
        return findDTO;
    }
}