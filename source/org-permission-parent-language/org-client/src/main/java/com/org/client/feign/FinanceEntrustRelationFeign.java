package com.org.client.feign;

import com.common.util.message.RestMessage;
import com.org.permission.common.org.dto.FinanceEntrustRelationInfoDto;
import com.org.permission.common.org.param.QueryFinanceEntrustRelationByUserPermissionParam;
import com.org.permission.common.org.param.QueryFinanceEntrustRelationParam;
import com.org.permission.common.util.Constant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 财务委托关系
 */
@FeignClient(value = Constant.ORG_SERVER_NAME)
public interface FinanceEntrustRelationFeign {

    /**
     * 查询所有财务委托关系
     * <p>
     * <ul>
     * <li>返回所有满足条件的启用状态的财务委托关系</li>
     * </ul>
     *
     * @param reqParam 请求参数
     * @return 财务委托关系集合 if success
     */
    @PostMapping(value = "/finance-entrust/queryFinanceEntrustRelation")
    RestMessage<List<FinanceEntrustRelationInfoDto>> queryFinanceEntrustRelation(@RequestBody QueryFinanceEntrustRelationParam reqParam);

    /**
     * 根据用户权限查询财务委托关系
     * <p>
     * <ul>
     * <li>用户权限范围内，具有财务职能的业务单元</li>
     * <li>依据组织角色匹配所有启用状态的财务委托关系</li>
     * </ul>
     *
     * @param reqParam 查询请求参数
     * @return 财务委托关系 if success
     */
    @PostMapping(value = "/finance-entrust/queryFinanceEntrustRelationByUserPermission")
    RestMessage<List<FinanceEntrustRelationInfoDto>> queryFinanceEntrustRelationByUserPermission(@RequestBody QueryFinanceEntrustRelationByUserPermissionParam reqParam);


    /**
     * @return 批量查询财务委托关系集合
     */
    @PostMapping(value = "/finance-entrust/queryFinanceEntrustRelationBatch")
    RestMessage<Map<Integer, List<FinanceEntrustRelationInfoDto>>> queryFinanceEntrustRelationBatch(@RequestParam("orgIds") List<Long> orgIds);
}
