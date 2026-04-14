package com.org.permission.server.org.service;

import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.*;
import com.org.permission.common.org.param.QueryGroupBUByFuncReqParam;
import com.org.permission.common.org.param.*;
import com.org.permission.server.org.bean.BizUnitWithFuncDetailBean;
import com.org.permission.server.org.bean.BizUnitWithFuncInfoBean;
import com.org.permission.server.org.bean.OrgListBean;
import com.org.permission.server.org.bean.OrgTreeBean;
import com.org.permission.server.org.dto.param.*;
import com.org.permission.server.org.vo.BUVersionInfoVo;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 业务单元管理服务
 */
public interface BizUnitService {
	/**
	 * 新增业务单元
	 *
	 * @param reqParam 新增业务单元请求参数
	 */
	void addBizUnit(AddBizUnitReqParam reqParam);

	/**
	 * 业务单元新增版本
	 *
	 * @param reqParam 新增业务单元请求参数
	 */
	void saveNewVersion(SaveNewVersionBUParam reqParam);

	/**
	 * 删除业务单元
	 *
	 * @param reqParam 删除业务单元请求参数
	 */
	void deleteBizUnit(KeyOperateParam reqParam);

	/**
	 * 启用业务单元
	 *
	 * @param reqParam 启用业务单元请求参数
	 */
	void enableBizUnit(EnableOperateParam reqParam);

	/**
	 * 更新业务单元基础信息
	 *
	 * @param reqParam 更新业务单元请求参数
	 */
	void updateBizUnit(UpdateBizUnitReqParam reqParam);

	/**
	 * 根据ID查询业务单元及其组织职能信息
	 *
	 * @param reqParam 根据ID查询业务单元信息请求参数
	 * @return 集团信息
	 */
	BizUnitWithFuncDetailBean queryBizUnitWithFuncById(QueryByIdReqParam reqParam);



	/**
	 * 根据ID查询业务单元及其组织职能信息
	 *
	 * @param reqParam 根据ID查询业务单元信息请求参数
	 * @return 集团信息
	 */
	List<BizUnitWithFuncDetailBean> queryBizUnitWithFuncByIds(QueryByIdReqParam reqParam);

	/**
	 * 业务单元版本详情查询
	 *
	 * @param reqParam 查询参数
	 * @return 业务单元版本详情
	 */
	BizUnitWithFuncDetailBean queryBUVersionDetailInfo(QueryByIdReqParam reqParam);

	/**
	 * 业务单元列表信息查询
	 *
	 * @param reqParam 业务单元列表信息查询请求参数
	 * @return 业务单元信息集合
	 */
	PageInfo<BizUnitWithFuncInfoBean> queryBizUnitInfoList(QueryBizUnitListReqParam reqParam);

	/**
	 * 查询根业务单元列表
	 * 注：也产品线要求，没有进行实质性的分页处理
	 *
	 * @param reqParam 查询参数
	 * @return 查询结果
	 */
	PageInfo<OrgInfoDto> queryRootBUInfoList(RootBUListQueryParam reqParam);

	/**
	 * 组织职能业务单元列表
	 *
	 * @param reqParam 查询请求参数
	 * @return 组织信息集合
	 */
	PageInfo<OrgConciseInfoDto> queryBizUnitListByFunc(@RequestBody QueryBizUnitListByFuncParam reqParam);

	/**
	 * 查询一个集团下具有某组织职能的组织列表简要信息
	 *
	 * @param reqParam 查询请求参数
	 * @return 组织简要信息集合
	 */
	List<OrgListBean> queryGroupBUByFunc(QueryGroupBUByFuncReqParam reqParam);

	/**
	 * 根据用户id和集团id查询有权限的业务单元集合
	 *
	 * @param reqParam 查询请求参数
	 * @return 组织简要信息集合
	 */
	List<OrgInfoDto> queryBizByGroupIdAndUserId(QueryBizByGroupIdAndUserIdParam reqParam);

	/**
	 * 查询一个客商下具有某组织职能的组织列表简要信息
	 *
	 * @param reqParam 查询请求参数
	 * @return 组织简要信息集合
	 */
	List<OrgListBean> queryCustBUByFunc(QueryCustBUByFuncReqParam reqParam);

	/**
	 * 业务单元（解）绑定内部客商
	 *
	 * @param reqParam 业务单元绑定客商请求参数
	 */
	void bindingInnerCust(BindingCustReqParam reqParam);

	/**
	 * 业务单元绑定客商
	 *
	 * @param reqParam 业务单元绑定客商请求参数
	 */
	void bindingCust(BindingCustReqParam reqParam);

	/**
	 * 查询业务单元开票信息（）
	 *
	 * @param reqParam 请求参数
	 * @return 开票信息
	 */
	BUInvoiceInfoDto invoiceInfo(QueryByIdReqParam reqParam);

	/**
	 * 查询业务单元版本列表
	 *
	 * @param reqParam 查询请求参数
	 * @return 业务单元版本列表
	 */
	PageInfo<BUVersionInfoVo> queryBUVersionList(VersionPageQueryParam reqParam);

	/**
	 * 查询集团根业务单元
	 * 使用{@link BizUnitService#queryOrgRootBU(QueryByIdReqParam)}
	 *
	 * @param groupId 集团ID
	 * @return 组织信息
	 */
	@Deprecated
	OrgListBean queryGroupRootBU(Long groupId);

	/**
	 * 根据组织 ID 查询其根业务单元
	 *
	 * @param reqParam 查询请求参数
	 * @return 业务单元信息
	 */
	OrgListDto queryOrgRootBU(QueryByIdReqParam reqParam);

	/**
	 * 查询客商对应的业务单元
	 *
	 * @param custId 全局客商 ID
	 * @return 业务单元信息
	 */
	OrgListBean queryBUByCustId(Integer custId);

	/**
	 * 根据集团id查询业务单元
	 *
	 * @param reqParam
	 * @return
	 */
	List<OrgInfoDto> queryBUByGroupId(QueryByIdReqParam reqParam);

	/**
	 * 查询业务单元权限集合
	 *
	 * @param reqParam 查询业务单元列表请求参数
	 * @return 业务单元集合
	 */
	List<OrgTreeBean> queryBUPermissonList(QueryOrgPermissionListReqParam reqParam);

	/**
	 * 根据集团 ID 查询该集团下未绑定客商的业务单元
	 *
	 * @param reqParam 请求参数
	 * @return 业务单元列表
	 */
	List<OrgListBean> queryGroupUnbindingCustBUs(QueryBizUnitListReqParam reqParam);

	/**
	 * 业务单元是否有物流职能
	 * @param buId
	 * @return true:有
	 */
	Boolean hasLogisticsFunction(Long buId);

	List<BizUnitWithFuncDetailBean> queryBizByGroupIdAndAddress(QueryBizByGroupIdAndAddressParam param);

    List<OrgListBean> queryGroupBUByFuncList(QueryGroupBUByFuncReqParam reqParam);
}
