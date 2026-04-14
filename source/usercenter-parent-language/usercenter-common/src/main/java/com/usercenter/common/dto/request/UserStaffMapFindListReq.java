package com.usercenter.common.dto.request;

import com.usercenter.common.enums.FlagEnum;
import com.usercenter.common.enums.UserStateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel("查询条件")
public class UserStaffMapFindListReq implements Serializable {

    /**
     * 状态：false 未启用，true启用
     */
    @ApiModelProperty(value = "状态：1=启用，2=禁用")
    private Integer state;

    /**
     * 是否删除：false未删除， true 删除
     */
    @ApiModelProperty(value = " 是否删除：0=未删除， 1= 删除")
    private Integer deletedFlag;

    @ApiModelProperty(value = "人员id集合")
    private List<Long> staffIds;

    @ApiModelProperty(value = "用户id集合")
    private List<Long> userIds;

    /**
     * 推荐使用静态create方法创建查询条件实体
     */
    @Deprecated()
    public UserStaffMapFindListReq(){

    }


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getDeletedFlag() {
        return deletedFlag;
    }

    public void setDeletedFlag(Integer deletedFlag) {
        this.deletedFlag = deletedFlag;
    }

    public List<Long> getStaffIds() {
        return staffIds;
    }

    public void setStaffIds(List<Long> staffIds) {
        this.staffIds = staffIds;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }



    /**
     * 创建人员查询实体（state=1/deletedFlag=0）
     * @param staffIds 用户id集合
     * @return 用户人员映射
     **/
    public static UserStaffMapFindListReq createStaffQuery(List<Long> staffIds){
        return createQuery(staffIds, null, UserStateEnum.ENABLE.getCode(), FlagEnum.FALSE.getCode());
    }

    /**
     * 创建人员查询实体（deletedFlag=false）
     * @param staffIds 人员id集合
     * @param state 状态 1=启用 2=禁用
     * @return 用户人员映射
     **/
    public static UserStaffMapFindListReq createStaffQuery(List<Long> staffIds, Integer state){
        return createQuery(staffIds, null, state, FlagEnum.FALSE.getCode());
    }

    /**
     * 创建人员查询实体
     * @param staffIds 人员id集合
     * @param state 状态 1=启用 2=禁用
     * @param deletedFlag 是否删除：0 未删除 1 删除
     * @return 用户人员映射
     **/
    public static UserStaffMapFindListReq createStaffQuery(List<Long> staffIds,Integer state, Integer deletedFlag){
        return createQuery(staffIds, null, state, deletedFlag);
    }

    /**
     * 创建用户查询实体（state=1/deletedFlag=0）
     * @param userIds 用户id集合
     * @return 用户人员映射
     **/
    public static UserStaffMapFindListReq createUserQuery(List<Long> userIds){
        return createQuery(null, userIds, UserStateEnum.ENABLE.getCode(), FlagEnum.FALSE.getCode());
    }

    /**
     * 创建用户查询实体（deletedFlag=false）
     * @param userIds 用户id集合
     * @param state 状态 1=启用 2=禁用
     * @return 用户人员映射
     **/
    public static UserStaffMapFindListReq createUserQuery(List<Long> userIds, Integer state){
        return createQuery(null, userIds, state, FlagEnum.FALSE.getCode());
    }

    /**
     * 创建用户查询实体（deleted=false）
     * @param userIds 用户id集合
     * @param state 状态 1=启用 2=禁用
     * @param deletedFlag 是否删除：0 未删除 1 删除
     * @return 用户人员映射
     **/
    public static UserStaffMapFindListReq createUserQuery(List<Long> userIds,Integer state, Integer deletedFlag){
        return createQuery(null, userIds, state, deletedFlag);
    }


    public static UserStaffMapFindListReq createQuery(List<Long> staffIds, List<Long> userIds, Integer state, Integer deletedFlag) {
        UserStaffMapFindListReq findDTO = new UserStaffMapFindListReq();
        findDTO.setStaffIds(staffIds);
        findDTO.setUserIds(userIds);
        findDTO.setState(state);
        findDTO.setDeletedFlag(deletedFlag);
        return findDTO;
    }
}