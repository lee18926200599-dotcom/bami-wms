package com.usercenter.server.mapper;

import com.usercenter.server.entity.BaseUser;
import com.usercenter.server.domain.vo.req.globaluser.GlobalUserReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * comments:用户基本信息表数据读取接口
 */
@Mapper
public interface BaseUserMapper {

    /**
     * 单条插入 用户基本信息表 数据
     * @param baseUser
     * @return 插入数据 影响行数
     */
    int insert(BaseUser baseUser);


    /**
     * 批量插入 用户基本信息表 数据
     * @param baseUsers
     * @return 插入数据 影响行数
     */
    int insertList(List<BaseUser> baseUsers);

    /**
     * 动态插入 用户基本信息表 数据
     * @param baseUser
     * @return 插入数据 影响行数
     */
    int insertSelective(BaseUser baseUser);


    /**
     * 单条更新 用户基本信息表 数据
     * @param baseUser
     * @return 更新数据 影响行数
     */
    int update(BaseUser baseUser);

    /**
     * 批量更新 用户基本信息表 数据
     * @param baseUsers
     * @return 更新数据 影响行数
     */
    int updateList(List<BaseUser> baseUsers);


    /**
     * 根据主键删除 用户基本信息表 数据
     * @param id 主键id
     * @return 删除数据 影响行数
     */
    int delete(Long id);

    /**
     * 根据查询条件删除 用户基本信息表 数据
     * @param baseUser
     * @return 删除数据 影响行数
     */
    int deleteByParam(BaseUser baseUser);


    /**
     * 根据主键查询 用户基本信息表 数据
     * @param id
     * @return 查询到的用户基本信息表数据
     */
    BaseUser load(Long id);

    /**
     * 根据条件查询 用户基本信息表 数据
     * @param baseUser
     * @return 查询到的用户基本信息表数据
     */
    BaseUser loadByParam(BaseUser baseUser);

    /**
     * 不分页根据条件查询 用户基本信息表 数据
     * @param baseUser
     * @return 查询到的列表数据 不分页
     */
    List<BaseUser> listByParam(BaseUser baseUser);


    /**
     * 根据查询条件分页查询 用户基本信息表 数据
     * @param baseUser
     * @return 查询到的列表数据 分页
     */
    List<BaseUser> find(BaseUser baseUser);


    /**
     * 查询满足条件的 用户基本信息表数据的记录数
     * @param baseUser
     * @return 满足条件的记录数
     */
    int findCount(BaseUser baseUser);

    /**
     * 根据登录名查询用户信息
     * @param loginName
     * @return
     */
    List<BaseUser> getByLoginName(@Param("loginName") String loginName);
    /**
     * 联查用户信息
     * @param id
     * @return 用户对象
     */
    BaseUser selectBaseUserWithDetailsById(@Param("id") Long id);
    /**
     * 登录名查找一个用户
     * @param loginName
     * @return
     */
    BaseUser selectByLoginName(@Param("loginName") String loginName);
    List<BaseUser> selectGlobalList(GlobalUserReq globalUserReq);

}