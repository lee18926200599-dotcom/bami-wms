package com.permission.client.feign;


import com.common.util.message.RestMessage;
import com.org.permission.common.permission.dto.DataPermissionDto;
import com.org.permission.common.permission.param.GetDataPermissionParam;
import com.org.permission.common.util.Constant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 根据用户和集团获取用户数据权限
 */
@FeignClient(value = Constant.ORG_SERVER_NAME)
public interface UserDataPermissionFeign {

    /**
     * 根据用户id和集团id获取用户的数据权限
     *
     * @param userId
     * @param groupId
     * @return
     */
    @PostMapping(value = "/permission-user-data/getDataPermissonsByUid")
    RestMessage<Map<String, Object>> getDataPermissonsByUid(@RequestParam(value = "userId") Long userId, @RequestParam(value = "groupId") Long groupId);


    /**
     * 获取用户权限
     *
     * @param getDataPermissonReq 请求
     * @return 数据权限
     */
    @PostMapping(value = "/permission-user-data/getDataPermissons")
    RestMessage<List<DataPermissionDto>> getDataPermissons(@RequestBody GetDataPermissionParam getDataPermissonReq);

}
