package com.org.permission.server.org.mapper;

import com.org.permission.server.org.dto.param.BusinessLineParam;
import com.org.permission.server.org.bean.BaseBusinessLine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BusinessLineMapper {

    /**
     * 单条插入 业务线管理 数据 生成主键id，赋值到实体
     * @param baseBusinessLine
     * @return 插入数据 影响行数
     */
    int insert(BaseBusinessLine baseBusinessLine);


    /**
     * 单条更新 业务线管理 数据
     * @param baseBusinessLine
     * @return 更新数据 影响行数
     */
    int update(BaseBusinessLine baseBusinessLine);
    int updateState(BaseBusinessLine baseBusinessLine);

    /**
     * 批量更新 业务线管理 数据
     * @param baseBusinessLines
     * @return 更新数据 影响行数
     */
    int updateList(List<BaseBusinessLine> baseBusinessLines);


    /**
     * 根据主键查询 业务线管理 数据
     * @param id
     * @return 查询到的业务线管理数据
     */
    BaseBusinessLine load(Long id);


    /**
     * 根据查询条件分页查询 业务线管理 数据
     * @param baseBusinessLine
     * @return 查询到的列表数据 分页
     */
    List<BaseBusinessLine> listByParam(BaseBusinessLine baseBusinessLine);
    BaseBusinessLine getLastData();
    List<BaseBusinessLine> getPageList(BusinessLineParam businessLineParam);
    List<BaseBusinessLine> getByIdList(@Param("idList") List<Long> idList);
}
