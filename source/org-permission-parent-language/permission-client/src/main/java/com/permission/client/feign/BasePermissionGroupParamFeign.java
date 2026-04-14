package com.permission.client.feign;


import com.common.util.message.RestMessage;
import com.org.permission.common.permission.dto.BasePermissionGroupParamDto;
import com.org.permission.common.util.Constant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 集团参数接口
 * @ClassName: BasePermissionGroupParamFeign
 * @date
 */
@FeignClient(value = Constant.ORG_SERVER_NAME)
public interface BasePermissionGroupParamFeign {
    /**
     * 根据集团ID和参数code获取集团参数设置
     * @param groupId 集团ID
     * @param paramCode 参数code
     */
    @PostMapping(value = "/permission-group-param/getBasePermissionGroupParamsByGroupIdAndCode")
    public RestMessage<BasePermissionGroupParamDto> getBasePermissionGroupParamsByGroupIdAndCode(@RequestParam(name = "groupId",required = true) Long groupId,
                                                                                                 @RequestParam(name = "paramCode",required = true) String  paramCode);

}
