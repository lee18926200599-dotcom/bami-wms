package com.usercenter.server.mapper;

import com.usercenter.server.domain.condition.UserDetailCondition;
import com.usercenter.server.domain.dto.UpdateUserStrategyDTO;
import com.usercenter.server.domain.dto.UserNamesDto;
import com.usercenter.server.domain.vo.req.administratorsuser.AdministratorsQueryReq;
import com.usercenter.common.dto.UserDto;
import com.usercenter.server.entity.BaseUserDetail;
import com.usercenter.server.entity.BaseUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * comments:用户子表数据读取接口
 */
@Mapper
public interface BaseUserDetailMapper {

    /**
     * 单条插入 用户子表 数据
     *
     * @param baseUserDetail
     * @return 插入数据 影响行数
     */
    int insert(BaseUserDetail baseUserDetail);

    /**
     * 批量插入 用户子表 数据
     *
     * @param baseUserDetails
     * @return 插入数据 影响行数
     */
    int insertList(List<BaseUserDetail> baseUserDetails);

    /**
     * 动态插入 用户子表 数据
     *
     * @param baseUserDetail
     * @return 插入数据 影响行数
     */
    int insertSelective(BaseUserDetail baseUserDetail);


    /**
     * 单条更新 用户子表 数据
     *
     * @param baseUserDetail
     * @return 更新数据 影响行数
     */
    int update(BaseUserDetail baseUserDetail);

    /**
     * 批量更新 用户子表 数据
     *
     * @param baseUserDetails
     * @return 更新数据 影响行数
     */
    int updateList(List<BaseUserDetail> baseUserDetails);


    /**
     * 根据主键删除 用户子表 数据
     *
     * @param id 主键id
     * @return 删除数据 影响行数
     */
    int delete(Long id);

    /**
     * 根据查询条件删除 用户子表 数据
     *
     * @param baseUserDetail
     * @return 删除数据 影响行数
     */
    int deleteByParam(BaseUserDetail baseUserDetail);


    /**
     * 根据主键查询 用户子表 数据
     *
     * @param id
     * @return 查询到的用户子表数据
     */
    BaseUserDetail load(Long id);

    /**
     * 根据条件查询 用户子表 数据
     *
     * @param baseUserDetail
     * @return 查询到的用户子表数据
     */
    BaseUserDetail loadByParam(BaseUserDetail baseUserDetail);

    /**
     * 不分页根据条件查询 用户子表 数据
     *
     * @param baseUserDetail
     * @return 查询到的列表数据 不分页
     */
    List<BaseUserDetail> listByParam(BaseUserDetail baseUserDetail);


    /**
     * 根据查询条件分页查询 用户子表 数据
     *
     * @param baseUserDetail
     * @return 查询到的列表数据 分页
     */
    List<BaseUserDetail> find(BaseUserDetail baseUserDetail);

    /**
     * 查询满足条件的 用户子表数据的记录数
     *
     * @param baseUserDetail
     * @return 满足条件的记录数
     */
    int findCount(BaseUserDetail baseUserDetail);

    List<BaseUserInfo> selectDetailList(UserDetailCondition userDetailCondition);
    /**
     * 管理员用户
     * @param req 查询条件
     * @return
     */
    List<BaseUserInfo> selectAdministratorByQuery(AdministratorsQueryReq req);
    /**
     * 查询一条明细
     * @param detailCondition
     * @return
     */
    BaseUserInfo selectDetail(UserDetailCondition detailCondition);
    /**
     * 根据登录名获取用户信息
     * @param loginName 登录名
     * @return 详情
     */
    List<BaseUserInfo> selectUserListByLoginName(@Param("loginName") String loginName);
    /**
     * 查询用户信息接口
     * @param
     * @return
     */
    List<BaseUserInfo> findUserList(UserDto userDto);
    List<BaseUserDetail> selectDetailListByUserId(Long userId);
    /**
     * 根据用户名查找所有的用户信息
     * @param userNamesDto
     * @return
     */
    List<BaseUserInfo> selectListByUserNames(UserNamesDto userNamesDto);
    /**
     * 根据参数更新
     * @param updateUserStrategyDTO
     */
    void updateByIds(UpdateUserStrategyDTO updateUserStrategyDTO);
    /**
     * 根据用户ID更新
     * @param detail
     * @return
     */
    Integer updateLockAndEnableByUserId(BaseUserDetail detail);
}
