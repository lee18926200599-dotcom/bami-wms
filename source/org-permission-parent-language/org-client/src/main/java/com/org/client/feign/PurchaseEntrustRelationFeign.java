package com.org.client.feign;


import com.common.util.message.RestMessage;
import com.org.permission.common.org.dto.EntrustRelationOrgInfoDto;
import com.org.permission.common.org.dto.PurchaseEntrustRelationDto;
import com.org.permission.common.org.param.PurchaseEntrustRelationQueryParam;
import com.org.permission.common.util.Constant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 采购委托关系
 */
@FeignClient(value = Constant.ORG_SERVER_NAME)
public interface PurchaseEntrustRelationFeign {
    /**
     * 采购委托关系查询
     *
     * @param reqParam 查询请求参数
     * @return 采购委托关系
     */
    @PostMapping(value = "/purchase-entrust/queryPurchaseEntrustRelation")
    RestMessage<List<PurchaseEntrustRelationDto>> queryPurchaseEntrustRelation(PurchaseEntrustRelationQueryParam reqParam);


    /**
     * 通过采销组织获取采销委托
     *
     * @param purchaseSaleOrgId
     * @return
     */
    @GetMapping(value = "/common-entrust/queryPurchaseErByPurchaseSaleOrgId")
    RestMessage<List<EntrustRelationOrgInfoDto>> queryPurchaseErByPurchaseSaleOrgId(@RequestParam("purchaseSaleOrgId") Long purchaseSaleOrgId);
}
