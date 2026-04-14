package com.org.client.feign;


import com.common.util.message.RestMessage;
import com.org.permission.common.org.dto.SaleEntrustRelationDto;
import com.org.permission.common.org.param.SaleEntrustRelationQueryParam;
import com.org.permission.common.util.Constant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * 销售委托关系
 */
@FeignClient(value = Constant.ORG_SERVER_NAME)
public interface SaleEntrustRelationFeign {
	/**
	 * 销售委托关系查询
	 * @param reqParam 查询请求参数
	 * @return 销售委托关系
	 */
	@PostMapping(value = "/sale-entrust/querySaleEntrustRelation")
	RestMessage<List<SaleEntrustRelationDto>> querySaleEntrustRelation(SaleEntrustRelationQueryParam reqParam);
}
