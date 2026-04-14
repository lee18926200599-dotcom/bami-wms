package com.org.client.feign;


import com.common.util.message.RestMessage;
import com.org.permission.common.org.dto.MarketEntrustRelationDto;
import com.org.permission.common.org.dto.WarehouseEnterOwnerRankingListDto;
import com.org.permission.common.org.param.MarketEntrustRelationQueryParam;
import com.org.permission.common.org.param.WarehouseEnterOwnerRankingListParam;
import com.org.permission.common.util.Constant;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 采销委托关系
 */
@FeignClient(value = Constant.ORG_SERVER_NAME)
public interface MarketingEntrustRelationFeign {
    /**
     * 查询所有采销委托关系
     * 返回满足条件的启用的采销委托关系</li>
     *
     * @param reqParam 查询请求参数
     * @return 采销委托关系
     */
    @Deprecated
    @PostMapping(value = "/marketing-entrust/queryMarketEntrustRelation")
    RestMessage<List<MarketEntrustRelationDto>> queryMarketEntrustRelation(@RequestBody MarketEntrustRelationQueryParam reqParam);

    /**
     * 仓库入驻货主排行榜
     * 依据仓库分组，去重统计货主数量</li>
     * 数据已实现 Comparable 接口 业务方自行排序（默认按降序排列）</li>
     *
     * @return 排行榜列表
     */
    @PostMapping(value = "/marketing-entrust/warehouseEnterOwnerRankingList")
    RestMessage<List<WarehouseEnterOwnerRankingListDto>> warehouseEnterOwnerRankingList(WarehouseEnterOwnerRankingListParam reqParam);

    /**
     * 专供SCM和金融根据采销组织查询仓库
     *
     * @param marketOrgId
     * @return
     */
    @PostMapping(value = "/marketing-entrust/queryWareHouseIdsByMarketOrgId")
    RestMessage<List<Long>> queryWareHouseIdsByMarketOrgId(@RequestBody Integer marketOrgId);

    /**
     * 专供SCM根据仓库id查询仓储服务商查询仓储服务商
     *
     * @param warehouseId
     * @return
     */
    @ApiOperation(value = "根据仓库id查询仓储服务商", httpMethod = "POST")
    @PostMapping(value = "/marketing-entrust/queryWareHouseProviderByWarehouseId")
    RestMessage<List<Integer>> queryWareHouseProviderByWarehouseId(@RequestParam(value = "warehouseId") Long warehouseId);
}
