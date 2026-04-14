package com.usercenter.server.service;

import com.usercenter.server.entity.BaseUserStaffMap;

import java.util.List;

/**
 * 用户人员映射
 */
public interface IBaseUserStaffMapService {

    /**
     * 插入用户人员映射
     *
     * @param userStaffMap
     * @return
     */
    BaseUserStaffMap save(BaseUserStaffMap userStaffMap);


    /**
     * 根据人员id禁用映射
     * @param staffId 人员id
     */
    void disableByStaffId(Integer staffId);

    /**
     * 根据用户id禁用映射
     * @param userId 用户id
     */
    void disableByUserId(Long userId);


    /**
     * 集合查询通用方法
     * @param userIds 用户id集合
     * @param staffIds 人员id 集合
     * @param state 是否禁用： 1=启用 2=禁用
     * @param deletedFlag 是否删除： 1 删除 0 未删除
     * @return 符合条件的数据
     */
    List<BaseUserStaffMap> findListByCondition(List<Long> userIds, List<Long> staffIds, Integer state, Integer deletedFlag);

    /**
     * 查询一条符合条件的映射
     * @param userStaffMap 查询条件
     * @return
     */
    BaseUserStaffMap findOneByCondition(BaseUserStaffMap userStaffMap);

    /**
     * 逻辑删除
     **/
    void logicDelete(Long userId, Long updateUserId);

    /**
     * 插入用户人员映射,当系统中不存在启用的映射时
     *
     * @param userStaffMap
     * @return
     */
    BaseUserStaffMap saveCaseNotExist(BaseUserStaffMap userStaffMap);

    /**
     * 删除除指定人员id的其他绑定
     * @param userStaffMap 删除参数
     */
    void deleteOtherMapping(BaseUserStaffMap userStaffMap);


    /**
     * 同步用户人员关系映射
     */
    void syncUserStaffMap();
}
