package com.org.permission.server.org.mapper;

import com.org.permission.server.org.bean.StaffDutyBean;
import com.org.permission.server.org.bean.StaffDutyInfoBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 人员任职信息
 */
@Mapper
public interface StaffDutyMapper {
    /**
     * 批量新增人员任职信息
     *
     * @param bean 人员任职信息
     */
    void batchCreatStaffDutyInfo(@Param(value = "bean") List<StaffDutyBean> bean);

    /**
     * 批量删人员任职信息
     *
     * @param staffId 人员 ID
     */
    void batchDeleteStaffyInfo(@Param(value = "staffId") Long staffId);

    void UpdateStaffDutyInfoEndDate(@Param(value = "staffId") Long staffId, @Param(value = "endDate") Date endDate);

    /**
     * 根据人员ID查询任职信息集合
     *
     * @param staffId 人员ID
     * @return 人员任职信息
     */
    List<StaffDutyInfoBean> queryDutyInfoListByStaffId(@Param("staffId") Long staffId);

    /**
     * 根据参数参数人员任职信息
     *
     * @param userId 用户ID
     * @param typeId 人员类别ID
     * @return 人员任职信息
     */
    StaffDutyBean queryStaffDuty(@Param(value = "userId") Long userId, @Param(value = "typeId") Integer typeId);
}

