package com.usercenter.server.service;

import com.usercenter.server.domain.vo.req.groupuser.GroupUserListReq;
import com.usercenter.server.domain.vo.req.groupuser.GroupUserSaveReq;
import com.usercenter.server.domain.vo.req.groupuser.GroupUserUpdateReq;
import com.usercenter.server.domain.vo.resp.GroupUserDetailResp;
import com.usercenter.server.domain.vo.resp.PageResult;
import com.usercenter.common.dto.request.UserSaveRespDTO;

/**
 * base_userService 用户管理-集团
 */
public interface IBaseUserGroupService extends IBaseUserCommonService {

    /**
     * 保存用户信息
     *
     * @param saveReq 用户信息
     * @return 保存后的信息
     */
    UserSaveRespDTO add(GroupUserSaveReq saveReq);

    /**
     * 更新用户信息
     * @param updateReq 用户信息
     */
    void update(GroupUserUpdateReq updateReq);


    /**
     * 查询分页
     *
     * @param req 查询条件
     * @return 分页
     */
    PageResult<GroupUserDetailResp> getPage(GroupUserListReq req);

    /**
     * 详情页
     *
     * @param id 用户明细id
     * @return 用户信息
     */
    GroupUserDetailResp getDetail(Long id);
}

