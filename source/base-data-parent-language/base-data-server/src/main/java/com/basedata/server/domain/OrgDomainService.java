package com.basedata.server.domain;

import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.common.util.message.RestMsgUtils;
import com.org.client.feign.BizUnitFeign;
import com.org.client.feign.OrganizationFeign;
import com.org.permission.common.org.dto.OrganizationDto;
import com.org.permission.common.org.param.QueryByIdReqParam;
import com.org.permission.common.org.vo.BizUnitWithFuncDetailVo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *  组织相关接口
 */
@Component("orgDomainService")
public class OrgDomainService {

    @Resource
    private BizUnitFeign bizUnitFeign;
    @Resource
    private OrganizationFeign organizationFeign;
    /**
     * 查询业务单元信息
     * @param orgId
     * @return
     */
    public BizUnitWithFuncDetailVo getOrgById(Long orgId) {
        RestMessage<BizUnitWithFuncDetailVo> unitRestMsg = bizUnitFeign.queryBizUnitById(new QueryByIdReqParam(orgId));
        return unitRestMsg.getData();
    }

    public OrganizationDto getServiceProviderById(Long serviceProviderId) {
        QueryByIdReqParam reqParam = new QueryByIdReqParam();
        reqParam.setId(serviceProviderId);
        OrganizationDto serviceProviderInfo = RestMsgUtils.retrieveResult(organizationFeign.queryOrgInfoById(reqParam), I18nUtils.getMessage("base.org.groupunit.queryerror"));
        return serviceProviderInfo;
    }
}
