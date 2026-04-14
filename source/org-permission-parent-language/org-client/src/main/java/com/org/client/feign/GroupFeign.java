package com.org.client.feign;

import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.GroupDetailDto;
import com.org.permission.common.org.param.CustGenerateGroupParam;
import com.common.util.message.RestMessage;
import com.boss.crm.common.dto.customer.CustSaicRawDto;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.common.util.Constant;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 集团专属业务API
 */
@FeignClient(value = Constant.ORG_SERVER_NAME)
public interface GroupFeign {
    /**
     * 客商生成集团
     *
     * @param reqParam 请求参数
     * @return 用户密码
     */
    @ApiOperation(value = "客商生成集团", httpMethod = "POST")
    @PostMapping(value = "/group/custGenerateGroup")
    RestMessage<com.org.permission.common.org.dto.OrgUser> custGenerateGroup(@RequestBody CustGenerateGroupParam reqParam);

    /**
     * 同步客商业务类型
     *
     * @param reqParam 请求参数
     * @return <code>true</code>同步成功
     */
    @PostMapping(value = "/group/syncCustBizType")
    RestMessage<Boolean> syncCustBizType(@RequestBody com.org.permission.common.org.param.SyncCustBizTypeParam reqParam);

    /**
     * 查询所有集团信息列表
     *
     * @param reqParam 查询请求参数
     * @return 集团基本信息
     */
    @PostMapping(value = "/group/queryAllGroupInfoList")
    RestMessage<PageInfo<com.org.permission.common.org.dto.OrgInfoDto>> queryAllGroupInfoList(@RequestBody com.org.permission.common.org.param.GroupListQueryParam reqParam);

    @PostMapping(value = "/group/queryGroupList")
    RestMessage<List<com.org.permission.common.org.dto.OrgListDto>> queryGroupList(@RequestBody com.org.permission.common.org.param.QueryOrgListInfoReqParam reqParam);

    /**
     * 根据业务类型查询，集团下的业务单元
     *
     * @param reqParam 查询请求参数
     * @return 业务单元基本信息
     */
    @PostMapping(value = "/group/queryGroupBUByFunc")
    RestMessage<List<com.org.permission.common.org.dto.OrgListDto>> queryGroupBUByFunc(@RequestBody com.org.permission.common.org.param.QueryGroupBUByFuncReqParam reqParam);

    /**
     * 根据组织ID集合查询对应集团信息
     *
     * @param reqParam 查询请求参数
     * @return 集团基本信息
     */
    @PostMapping(value = "/group/queryGroupInfoByOrgIds")
    RestMessage<PageInfo<com.org.permission.common.org.dto.OrgInfoDto>> queryGroupInfoByOrgIds(@RequestBody BatchQueryParam reqParam);

    /**
     * 根据组织ID查询对应集团信息
     *
     * @param reqParam 查询请求参数
     * @return 集团基本信息
     */
    @PostMapping(value = "/group/queryGroupInfoByOrgId")
    RestMessage<com.org.permission.common.org.dto.OrgInfoDto> queryGroupInfoByOrgId(@RequestBody com.org.permission.common.org.param.QueryByIdReqParam reqParam);

    /**
     * 分页查询集团列表
     *
     * @param reqParam 查询参数
     * @return 集团信息
     */
    @PostMapping(value = "/group/queryGroupInfoList")
    RestMessage<PageInfo<com.org.permission.common.org.dto.GroupDetailDto>> queryGroupInfoList(@RequestBody com.org.permission.common.org.param.QueryGroupListReqParam reqParam);

    /**
     * 根据组织ID集合查询对应集团信息
     *
     * @param reqParam 查询请求参数
     * @return 集团基本信息
     */
    @PostMapping(value = "/group/queryGroupInfoByOrgIdsNoPage")
    RestMessage<List<com.org.permission.common.org.dto.OrgInfoDto>> queryGroupInfoByOrgIdsNoPage(@RequestBody BatchQueryParam reqParam);

    /**
     * 判断业务类型中是否包含商户
     *
     * @param reqParam 查询请求参数
     * @return true:包含 false:不包含
     */
    @PostMapping(value = "/group/queryGroupBizTypeIncludingMerchant")
    RestMessage<Boolean> queryGroupBizTypeIncludingMerchant(@RequestBody com.org.permission.common.org.param.QueryByIdReqParam reqParam);

    /**
     * 根据集团id查询集团信息
     *
     * @param reqParam
     * @return
     */
    @PostMapping(value = "/group/queryGroupById")
    RestMessage<GroupDetailDto> queryGroupById(@RequestBody com.org.permission.common.org.param.QueryByIdReqParam reqParam);

    /**
     * 查询未关联客商的集团
     *
     * @param reqParam
     * @return
     */
    @PostMapping(value = "/group/queryGroupsNotRelCust")
    RestMessage<PageInfo<com.org.permission.common.org.dto.OrgInfoDto>> queryGroupsNotRelCust(@RequestBody com.org.permission.common.org.param.GroupListQueryParam reqParam);

    /**
     * 查询入驻排行榜
     *
     * @param userId
     * @return
     */
    @PostMapping(value = "/group/queryGroupIncludeBusinessType")
    RestMessage<com.org.permission.common.org.dto.QueryGroupIncludeBusinessTypeDto> queryGroupIncludeBusinessType(@RequestParam(value = "userId", required = true) Long userId);

    /**
     * 生成集团后，客户再由非工商改成已工商时，增加法人职能
     *
     * @param custSaicRawDto 客户的工商信息
     *                       groupId               集团Id
     *                       rootUnitOrgId         根业务单元Id
     */
    @PostMapping(value = "/group/addCorpFunction")
    RestMessage addCorpFunction(@RequestBody CustSaicRawDto custSaicRawDto);
}
