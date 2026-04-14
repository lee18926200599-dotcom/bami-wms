package com.org.client.feign;

import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.QueryBizByGroupIdAndAddressParam;
import com.org.permission.common.org.param.QueryBizUnitListReqParam;
import com.common.util.message.RestMessage;
import com.org.permission.common.org.vo.BizUnitWithFuncDetailVo;
import com.org.permission.common.util.Constant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 业务单元
 */
@FeignClient(value = Constant.ORG_SERVER_NAME)
public interface BizUnitFeign {

    /**
     * 依据组织职能<b>分页</b>查询<b>所有</b>业务单元列表
     * <p>
     * <ul>实现如下逻辑：</ul>
     * <li>名字模糊匹配</li>
     * <li>状态过滤</li>
     *
     * @param reqParam 请求参数（职能不可空）
     * @return 组织简要信息列表
     */
    @PostMapping(value = "/unit/queryBizUnitListByFunc")
    RestMessage<PageInfo<com.org.permission.common.org.dto.OrgConciseInfoDto>> queryBizUnitListByFunc(@RequestBody com.org.permission.common.org.param.QueryBizUnitListByFuncParam reqParam);

    /**
     * 查询<b>集团</b>下业务单元列表
     * <p>
     * <ul>实现如下逻辑：</ul>
     * <li>可进行权限控制（默认不控制）</li>
     * <li>过滤有某种职能</li>
     * <li>过滤无某种职能</li>
     * <li>状态过滤</li>
     *
     * @param reqParam 请求参数（集团 ID 不能为空）
     * @return 业务单元列表
     */
    @PostMapping(value = "/unit/queryGroupBUByFunc")
    RestMessage<List<com.org.permission.common.org.dto.OrgListDto>> queryGroupBUByFunc(@RequestBody com.org.permission.common.org.param.QueryGroupBUByFuncReqParam reqParam);

    /**
     * 查询业务单元相关开票信息
     * <p>
     * <ul>实现如下逻辑：</ul>
     * <li>若当前业务单元有法人职能，取其法人职能信息</li>
     * <li>若当前业务单元无法人职能，取其法人公司法人职能信息</li>
     *
     * @param reqParam 查询请求参数
     * @return 开票信息
     */
    @PostMapping(value = "/unit/invoiceInfo")
    RestMessage<com.org.permission.common.org.dto.BUInvoiceInfoDto> invoiceInfo(@RequestBody com.org.permission.common.org.param.QueryByIdReqParam reqParam);

    /**
     * 查询用户权限范围内指定集团的业务单元列表
     * <p>
     * <ul>实现如下逻辑：</ul>
     * <li>查询用户权限范围内指定集团的业务单元列表</li>
     * <li>过滤有某种职能</li>
     *
     * @param reqParam 查询请求参数（集团 ID ；用户 ID 必填）
     * @return 业务单元简要信息集合
     */
    @PostMapping(value = "/unit/queryBUPermissionList")
    RestMessage<List<com.org.permission.common.org.dto.OrgInfoDto>> queryBUPermissionList(@RequestBody final com.org.permission.common.org.param.QueryOrgPermissionListReqParam reqParam);

    /**
     * 查询集团的根业务单元
     * 使用 {@link BizUnitFeign#queryOrgRootBU(com.org.permission.common.org.param.QueryByIdReqParam)} 替代
     *
     * @param reqParam 查询请求参数
     * @return 业务单元列表
     */
    @Deprecated
    @PostMapping(value = "/unit/queryGroupRootBU")
    RestMessage<com.org.permission.common.org.dto.OrgListDto> queryGroupRootBU(@RequestBody com.org.permission.common.org.param.QueryByIdReqParam reqParam);

    /**
     * 查询集团的根业务单元
     *
     * @param reqParam 查询请求参数
     * @return 业务单元列表
     */
    @PostMapping(value = "/unit/queryOrgRootBU")
    RestMessage<com.org.permission.common.org.dto.OrgListDto> queryOrgRootBU(@RequestBody com.org.permission.common.org.param.QueryByIdReqParam reqParam);

    /**
     * 根据客商 ID 查询业务单元
     * <p>
     * 若当前客商绑定的是业务单元，返回当前业务单元信息；
     * 若当前客商绑定的是集团，返回当前集团的根业务单元；
     *
     * @param reqParam 查询请求参数
     * @return 业务单元信息
     */
    @PostMapping(value = "/unit/queryBUByCustId")
    RestMessage<com.org.permission.common.org.dto.OrgListDto> queryBUByCustId(@RequestBody com.org.permission.common.org.param.QueryByIdReqParam reqParam);

    /**
     * 根据集团id查询集团下的业务单元
     *
     * @param reqParam 请求参数
     * @return 业务单元信息
     */
    @PostMapping(value = "/unit/queryBUByGroupId")
    RestMessage<List<com.org.permission.common.org.dto.OrgInfoDto>> queryBUByGroupId(@RequestBody com.org.permission.common.org.param.QueryByIdReqParam reqParam);

    /**
     * 查询集团未绑定客商的所有业务单元
     *
     * @param reqParam 查询参数（集团 ID + status）
     * @return 业务单元列表
     */
    @PostMapping(value = "/unit/queryGroupUnbindingCustBUs")
    RestMessage<List<com.org.permission.common.org.dto.OrgInfoDto>> queryGroupUnbindingCustBUs(@RequestBody QueryBizUnitListReqParam reqParam);

    /**
     * 根据 ID 查询业务单元信息
     *
     * @param reqParam 查询参数
     * @return 业务单元信息
     */
    @PostMapping(value = "/unit/queryBizUnitById")
    RestMessage<BizUnitWithFuncDetailVo> queryBizUnitById(@RequestBody com.org.permission.common.org.param.QueryByIdReqParam reqParam);

    /**
     * 根据 IDs 查询业务单元信息
     *
     * @param reqParam 查询参数
     * @return 业务单元信息
     */
    @PostMapping(value = "/unit/queryBizUnitByIds")
    RestMessage<List<BizUnitWithFuncDetailVo>> queryBizUnitByIds(@RequestBody com.org.permission.common.org.param.QueryByIdReqParam reqParam);

    /**
     * 根据集团id和经纬度查询业务单元信息（专为wms提供）
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/unit/queryBizByGroupIdAndAddress")
    RestMessage<List<BizUnitWithFuncDetailVo>> queryBizByGroupIdAndAddress(@RequestBody QueryBizByGroupIdAndAddressParam param);

    /**
     * 根据用户id和集团id查询有权限的业务单元（权限用）
     *
     * @param reqParam
     * @return
     */
    @PostMapping(value = "/unit/queryBizByGroupIdAndUserId")
    RestMessage<List<com.org.permission.common.org.dto.OrgInfoDto>> queryBizByGroupIdAndUserId(@RequestBody com.org.permission.common.org.dto.QueryBizByGroupIdAndUserIdParam reqParam);

    /**
     * 查询一个集团下具有某组织职能的组织列表
     *
     * @param queryGroupBUByFuncReqParam
     * @return
     */
    @PostMapping(value = "/unit/queryGroupBUByFuncList")
    RestMessage<List<com.org.permission.common.org.dto.OrgInfoDto>> queryGroupBUByFuncList(@RequestParam(value = "queryGroupBUByFuncReqParam", required = false) String queryGroupBUByFuncReqParam);
}
