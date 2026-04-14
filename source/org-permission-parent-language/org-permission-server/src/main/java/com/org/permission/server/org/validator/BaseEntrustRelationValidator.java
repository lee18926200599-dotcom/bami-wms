package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.common.dto.crm.CustInfoDomainDto;
import com.org.permission.common.org.dto.OrgInfoDto;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.server.domain.crm.CustDomainService;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.enums.EntrustRangeEnum;
import com.org.permission.server.org.enums.EntrustTypeEnum;
import com.org.permission.server.org.service.CommonEntrustRelationService;
import com.org.permission.server.org.service.OrganizationService;
import com.common.base.enums.StateEnum;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component("baseEntrustRelationValidator")
public class BaseEntrustRelationValidator {
    @Resource
    private OrganizationService organizationService;

    @Resource
    private CommonEntrustRelationService commonEntrustRelationService;

    @Resource
    private CustDomainService custDomainService;

    //未启用停用状态才可以修改
    public static void statusValidate(Integer state) {
        if (state != StateEnum.CREATE.getCode() || state != StateEnum.DISABLE.getCode()) {
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.entrust.relation.state.error"));
        }
    }

    //采销委托关系与仓储委托关系在新增与修改的时候一对客商对应的业务单元对应的集团不能相等
    public Boolean groupIdValidate(Long custId, Long cust) {
        Long org = null;
        Long orgs = null;
        //调用客商查询关联的业务单元
        CustInfoDomainDto info = custDomainService.getCustInfoById(custId);
        CustInfoDomainDto infos = custDomainService.getCustInfoById(cust);
        if (!ObjectUtils.isEmpty(info)) {
            org = info.getUnitOrg();
        }
        if (!ObjectUtils.isEmpty(infos)) {
            orgs = infos.getUnitOrg();
        }
        if (!ObjectUtils.isEmpty(org) && !ObjectUtils.isEmpty(orgs)) {
            List<Long> list = new ArrayList<>();
            list.add(org);
            list.add(orgs);
            //根据业务单元id查询相应的集团
            BatchQueryParam batchQueryParam = new BatchQueryParam();
            batchQueryParam.setIds(list);
            batchQueryParam.setState(StateEnum.ENABLE.getCode());
            List<OrgInfoDto> dtos = organizationService.batchQueryOrgInfoNofuc(batchQueryParam);
            if (!ObjectUtils.isEmpty(dtos) && dtos.size() > 1 && dtos.get(0).getGroupId() != dtos.get(1).getGroupId()) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    /**
     * //集团内组织唯一性校验
     *
     * @param entrustRange    委托关系范围
     * @param entrustType     委托关系类型
     * @param warehourseId    仓库id
     * @param purchaseSaleOrg 采销采购组织
     * @param stockOrg        库存组织
     * @param logisticsOrg    物流组织
     * @param settmentOrg     结算组织
     * @param accountOrg      核算组织
     */
    public Long baseEntrustRelationUnique(Integer entrustRange, Integer entrustType, Long warehourseId, Long purchaseSaleOrg, Long stockOrg,
                                          Long logisticsOrg, Long settmentOrg, Long accountOrg, Long businessOrg) {
        Long id = 0L;
        //1.采销委托关系唯一性：采销组织、库存组织、仓库
        if (Objects.equals(entrustRange, EntrustRangeEnum.INTER_GROUP.getIndex()) && entrustType == EntrustTypeEnum.PURCHASEANDSALE.getIndex()) {
            id = commonEntrustRelationService.queryEntrustRelationWithCondition(entrustRange, entrustType,
                    warehourseId, purchaseSaleOrg, stockOrg, null, null, null, null);
        }
        //2.集团间仓储物流委托关系唯一性：库存组织、仓库、物流组织
        if (Objects.equals(entrustRange, EntrustRangeEnum.INTER_GROUP.getIndex()) && entrustType == EntrustTypeEnum.STORAGE.getIndex()) {
            id = commonEntrustRelationService.queryEntrustRelationWithCondition(entrustRange, entrustType,
                    warehourseId, null, stockOrg, logisticsOrg, null, null, null);
        }
        //3.集团内采购委托关系唯一性：采购组织、库存组织
        if (Objects.equals(entrustRange, EntrustRangeEnum.WITHIN_GROUP.getIndex()) && entrustType == EntrustTypeEnum.PURCHASE.getIndex()) {
            id = commonEntrustRelationService.queryEntrustRelationWithCondition(entrustRange, entrustType,
                    null, purchaseSaleOrg, stockOrg, null, settmentOrg, accountOrg, null);
        }
        //5.集团内销售委托关系唯一性：采购组织、库存组织、应收组织、结算组织
        if (Objects.equals(entrustRange, EntrustRangeEnum.WITHIN_GROUP.getIndex()) && entrustType == EntrustTypeEnum.SALE.getIndex()) {
            id = commonEntrustRelationService.queryEntrustRelationWithCondition(entrustRange, entrustType,
                    null, purchaseSaleOrg, stockOrg, null, settmentOrg, accountOrg, null);
        }
        //6.集团内仓储物流委托关系唯一性：库存组织、物流组织、核算组织、结算组织
        if (Objects.equals(entrustRange, EntrustRangeEnum.WITHIN_GROUP.getIndex()) && entrustType == EntrustTypeEnum.LOGISTICS.getIndex()) {
            id = commonEntrustRelationService.queryEntrustRelationWithCondition(entrustRange, entrustType,
                    null, null, stockOrg, logisticsOrg, settmentOrg, accountOrg, null);
        }
        //7.集团内财务委托关系唯一性：业务组织、核算组织、结算组织
        if (Objects.equals(entrustRange, EntrustRangeEnum.WITHIN_GROUP.getIndex()) && entrustType == EntrustTypeEnum.FINANCE.getIndex()) {
            id = commonEntrustRelationService.queryEntrustRelationWithCondition(entrustRange, entrustType,
                    null, null, null, null, settmentOrg, accountOrg, businessOrg);
        }
        return id;
    }
}
