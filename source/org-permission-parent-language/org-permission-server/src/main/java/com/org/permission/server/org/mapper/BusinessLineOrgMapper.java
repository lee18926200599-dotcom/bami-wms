package com.org.permission.server.org.mapper;

import com.org.permission.server.org.dto.BusinessLineOrgDto;
import com.org.permission.server.org.bean.BaseBusinessLineOrg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BusinessLineOrgMapper {
    /**
     * 单条插入 业务线和组织关系表 数据
     *
     * @param baseBusinessLineOrg
     * @return 插入数据 影响行数
     */
    int insert(BaseBusinessLineOrg baseBusinessLineOrg);

    /**
     * 批量插入 业务线和组织关系表 数据
     *
     * @param baseBusinessLineOrgs
     * @return 插入数据 影响行数
     */
    int insertList(List<BaseBusinessLineOrg> baseBusinessLineOrgs);

    /**
     * 单条更新 业务线和组织关系表 数据
     *
     * @param baseBusinessLineOrg
     * @return 更新数据 影响行数
     */
    int update(BaseBusinessLineOrg baseBusinessLineOrg);

    /**
     * 批量更新 业务线和组织关系表 数据
     *
     * @param baseBusinessLineOrgs
     * @return 更新数据 影响行数
     */
    int updateList(List<BaseBusinessLineOrg> baseBusinessLineOrgs);


    /**
     * 根据主键删除 业务线和组织关系表 数据
     *
     * @param id 主键id
     * @return 删除数据 影响行数
     */
    int delete(Long id);

    /**
     * 根据查询条件删除 业务线和组织关系表 数据
     *
     * @param baseBusinessLineOrg
     * @return 删除数据 影响行数
     */
    int deleteByParam(BaseBusinessLineOrg baseBusinessLineOrg);


    /**
     * 根据主键查询 业务线和组织关系表 数据
     *
     * @param id
     * @return 查询到的业务线和组织关系表数据
     */
    BaseBusinessLineOrg load(Long id);

    /**
     * 根据条件查询 业务线和组织关系表 数据
     *
     * @param baseBusinessLineOrg
     * @return 查询到的业务线和组织关系表数据
     */
    BaseBusinessLineOrg loadByParam(BaseBusinessLineOrg baseBusinessLineOrg);

    /**
     * 不分页根据条件查询 业务线和组织关系表 数据
     *
     * @param baseBusinessLineOrg
     * @return 查询到的列表数据 不分页
     */
    List<BaseBusinessLineOrg> listByParam(BaseBusinessLineOrg baseBusinessLineOrg);

    List<BusinessLineOrgDto> getDtoList(@Param("groupId") Long groupId, @Param("orgId") Long orgId);

    List<Long> getLineIdList(@Param("orgId") Long orgId);

    List<Long> getUsableLineIdList(@Param("orgId") Long orgId);


    /**
     * 查询满足条件的 业务线和组织关系表数据的记录数
     *
     * @param baseBusinessLineOrg
     * @return 满足条件的记录数
     */
    int findCount(BaseBusinessLineOrg baseBusinessLineOrg);
}
