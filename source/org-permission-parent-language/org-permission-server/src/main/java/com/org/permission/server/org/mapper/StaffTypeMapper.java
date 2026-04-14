package com.org.permission.server.org.mapper;

import com.org.permission.common.org.param.EnableOperateParam;
import com.org.permission.common.org.param.KeyOperateParam;
import com.org.permission.common.org.param.QueryStaffTypeTreeReqParam;
import com.org.permission.server.org.bean.StaffTypeBean;
import com.org.permission.server.org.bean.StaffTypeInfoBean;
import com.org.permission.server.org.bean.StaffTypeTreeBean;
import com.org.permission.server.org.bean.StateBean;
import com.org.permission.server.org.dto.param.StaffTypeParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 人员类别
 */
@Mapper
public interface StaffTypeMapper {
    /**
     * 新增人员类别
     *
     * @param bean 人员类别实体
     * @return 插入数量
     */
    int addStaffType(@Param(value = "bean") StaffTypeBean bean);

    /**
     * 更新人员类别
     *
     * @param reqParam 更新人员类别请求参数
     */
    void updateStaffType(@Param(value = "bean") StaffTypeParam reqParam, @Param(value = "modifiedDate") Date modifiedDate);

    /**
     * 启停人员类别
     *
     * @param reqParam   启停参数
     * @param modifiedDate 更新时间
     */
    void enableStaffType(@Param(value = "bean") EnableOperateParam reqParam, @Param(value = "modifiedDate") Date modifiedDate);

    /**
     * 批量停用人员类别
     *
     * @param staffTypeIds 人员类别 ID
     * @param userId       更新人
     * @param modifiedDate   更新时间
     */
    void batchDisableStaffType(@Param(value = "staffTypeIds") List<Long> staffTypeIds, @Param(value = "userId") Long userId, @Param(value = "modifiedDate") Date modifiedDate);

    /**
     * 删除人员类别
     *
     * @param reqParam   请求参数
     * @param modifiedDate 删除时间
     */
    void deleteStaffType(@Param(value = "bean") KeyOperateParam reqParam, @Param(value = "modifiedDate") Date modifiedDate);

    /**
     * 主键查询人员类别状态信息（锁）
     *
     * @param id 主键
     * @return 状态 bean
     */
    StateBean queryStaffTypeStateByIdLock(@Param(value = "id") Long id);

    /**
     * 主键查询人员类别信息（锁）
     *
     * @param id 主键
     * @return 状态 bean
     */
    StaffTypeBean queryStaffTypeByIdLock(@Param(value = "id") Long id);

    /**
     * 查询一个组织（集团级）下具有某个名字的人员类别
     *
     * @param typeName  人员类别名
     * @param belongOrg 所属集团
     * @return 类别 ID
     */
    List<Long> queryStaffTypeByUnixCondition(@Param(value = "typeName") String typeName, @Param(value = "bizCode") String bizCode, @Param(value = "belongOrg") Long belongOrg);

    /**
     * 查询一个组织（集团级）下所有人员类别
     *
     * @param belongOrg 所属集团
     * @return 类别 ID
     */
    List<StaffTypeTreeBean> queryStaffTypeByOrg(@Param(value = "belongOrg") Long belongOrg);

    /**
     * 查询人员类别集合（构建类别树）
     *
     * @param queryParam 查询人员类别树请求参数
     * @return 人员类别集合
     */
    List<StaffTypeTreeBean> queryStaffTypeTree(@Param(value = "queryParam") QueryStaffTypeTreeReqParam queryParam);

    /**
     * 查询人员类别名称
     *
     * @param typeId 类别ID
     * @return 类别名称
     */
    String queryStaffTypeNameById(@Param(value = "typeId") Long typeId);

    /**
     * 根据ID查询人员类别信息
     *
     * @param typeId 类别ID
     * @return 人员类别实体数据
     */
    StaffTypeInfoBean queryStaffTypeById(@Param(value = "typeId") Long typeId);

    /**
     * 查询直接下级
     *
     * @param
     * @return
     */
    List<StaffTypeInfoBean> queryChildren(@Param("parentId") Long parentId, @Param("state") Integer state);

    Integer queryStaffTypeIdByTypeName(@Param(value = "typeName") String typeName, @Param(value = "belongOrg") Long belongOrg);
}
