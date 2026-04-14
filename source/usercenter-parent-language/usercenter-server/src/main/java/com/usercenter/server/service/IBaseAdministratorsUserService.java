package com.usercenter.server.service;


import com.usercenter.server.domain.vo.req.administratorsuser.AdministratorsQueryReq;
import com.usercenter.server.domain.vo.req.administratorsuser.AdministratorsSaveUpdateReq;
import com.usercenter.server.domain.vo.resp.AdministratorAddResp;
import com.usercenter.server.domain.vo.resp.AdministratorsDetailResp;
import com.usercenter.server.domain.vo.resp.PageResult;

/**
 * base_userService 管理员管理接口
 */
public interface IBaseAdministratorsUserService extends IBaseUserCommonService {


    /**
     * 分页查找管理员列表
     * @param req 当前请求参数
     * @param level 当前用户的管理级别
     * @return
     */
    PageResult<AdministratorsDetailResp> getAdministratorListByPage(AdministratorsQueryReq req, Integer level);


    /**
     * 管理员列表更新
     * @param req
     * @return
     */
    Integer updateAdministrators(AdministratorsSaveUpdateReq req);

    /**
     * 根据用户ID查找用户详情
     * @param id
     * @return
     */
    AdministratorsDetailResp getAdministratorDetail(Long id);


    /**
     * 新增管理员接口
     * @param administratorsSaveReq
     * @return
     */
    AdministratorAddResp addAdministrator(AdministratorsSaveUpdateReq administratorsSaveReq);
}

