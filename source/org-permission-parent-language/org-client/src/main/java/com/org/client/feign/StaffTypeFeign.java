package com.org.client.feign;


import com.common.util.message.RestMessage;
import com.org.permission.common.org.param.QueryStaffTypeTreeReqParam;
import com.org.permission.common.org.vo.StaffTypeTreeVo;
import com.org.permission.common.util.Constant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 人员类别
 */
@FeignClient(value = Constant.ORG_SERVER_NAME)
public interface StaffTypeFeign {
	/**
	 * 查询某组织的人员类别树
	 *
	 * 实现逻辑：
	 * <ul>
	 *     <li>依据查询条件返回指定集团级的人员类型树</li>
	 * </ul>
	 *
	 * @param reqParam 请求参数
	 * @return 人员类别树
	 */
	@PostMapping(value = "/staff-type/queryStaffTypeTree")
	RestMessage<StaffTypeTreeVo> queryStaffTypeTree(@RequestBody QueryStaffTypeTreeReqParam reqParam);
}
