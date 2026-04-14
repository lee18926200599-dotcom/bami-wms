package com.usercenter.client.feign;


import com.common.util.message.RestMessage;
import com.usercenter.common.dto.UserStaffMapDto;
import com.usercenter.common.dto.request.UserStaffMapFindListReq;
import com.usercenter.common.dto.request.UserStaffMapFindOneReq;
import com.usercenter.common.dto.request.UserStaffMapSaveReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "zzz-usercenter-server")
public interface UserStaffMapFeign {

    /**
     * 保存用户人员映射
     *
     * @param userStaffMapSaveReq
     * @return
     */
    @RequestMapping(value = "/user/user-staff/save", method = RequestMethod.POST)
    RestMessage<UserStaffMapDto> saveUserStaffMap(@RequestBody UserStaffMapSaveReq userStaffMapSaveReq);


    /**
     * 根据条件查询映射关系
     *
     * @param userStaffMapFindListReq
     * @return
     */
    @RequestMapping(value = "/user/user-staff/findList", method = RequestMethod.POST)
    RestMessage<List<UserStaffMapDto>> findList(@RequestBody UserStaffMapFindListReq userStaffMapFindListReq);

    /**
     * 根据条件查询映射关系
     *
     * @param userStaffMapFindOneReq
     * @return
     */
    @RequestMapping(value = "/user/user-staff/findOne", method = RequestMethod.POST)
    RestMessage<UserStaffMapDto> findOne(@RequestBody UserStaffMapFindOneReq userStaffMapFindOneReq);
}
