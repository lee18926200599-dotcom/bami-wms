package com.org.permission.server.org.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.common.base.enums.BooleanEnum;
import com.common.base.enums.StateEnum;
import com.common.framework.execption.BizException;
import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.google.common.collect.Lists;
import com.org.permission.common.org.dto.OrgInfoDto;
import com.org.permission.common.org.vo.BankAccountVo;
import com.org.permission.common.query.BatchQueryParam;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.OrgBankAccountBean;
import com.org.permission.server.org.dto.BankAccountResp;
import com.org.permission.server.org.dto.BankAccountUpdateStatusResp;
import com.org.permission.server.org.dto.param.BankAccountAllotParam;
import com.org.permission.server.org.dto.param.BankAccountQueryReq;
import com.org.permission.server.org.service.BankAccountService;
import com.org.permission.server.org.service.OrganizationService;
import com.usercenter.client.feign.UserFeign;
import com.usercenter.common.dto.FplUser;
import com.usercenter.common.dto.request.UserInfoListDto;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Component
public class BankAccountDealService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountDealService.class);

    @Autowired
    private BankAccountService bankAccountService;


    @Autowired
    private OrganizationService organizationService;

    @Resource
    private UserFeign userFeign;


    public List<BankAccountResp> getList(BankAccountQueryReq req) {
        //1.从本应用中获取数据
        List<OrgBankAccountBean> accountBeans = bankAccountService.getList(req.builderBean().setQueryAccountSnLike(1));
        if (CollectionUtils.isEmpty(accountBeans)) {
            return null;
        }
        //1.获取用户
        List<Long> userIds = accountBeans.stream().map(account -> account.getUserId()).collect(Collectors.toList());
        List<FplUser> userInfos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(userIds)) {
            UserInfoListDto params = new UserInfoListDto();
            params.setUserIds(userIds);
            RestMessage<List<FplUser>> userRestMessage = userFeign.getUserInfoList(params);
            if (!userRestMessage.isSuccess()) {
                throw new BizException(userRestMessage.getMessage());
            }
            userInfos = userRestMessage.getData();
        }
        Map<Long, FplUser> userMap = userInfos.stream().collect(Collectors.toMap(key -> key.getId(), value -> value));
        //2.从支付应用中获取数据
        List<Long> accountIds = accountBeans.stream().map(account -> account.getAccountId()).collect(Collectors.toList());
        List<BankAccountResp> resps = BankAccountResp.generatorFromBeanList(accountBeans);
        List<Long> orgIds = initPayDomainInfo(resps, accountIds, userMap);
        //4.进行条件过滤
        if (req.getBankNameCode() != null) {
            resps = resps.stream().filter(resp -> resp.getBankNameCode().contains(req.getBankNameCode())).collect(Collectors.toList());
        }
        if (req.getOpenBankName() != null) {
            resps = resps.stream().filter(resp -> resp.getOpenBankName().contains(req.getOpenBankName())).collect(Collectors.toList());
        }
        if (req.getAccountName() != null) {
            resps = resps.stream().filter(resp -> req.getAccountName() != null && resp.getAccountName().contains(req.getAccountName())).collect(Collectors.toList());
        }
        //填充组织信息
        initOrgInfo(resps, orgIds);
        return resps;
    }


    /**
     * 新增账号
     *
     * @param bean
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveAccount(OrgBankAccountBean bean, BankAccountQueryReq req) {
        //判断添加的数据是否合法，需要保证唯一
        OrgBankAccountBean params = new OrgBankAccountBean();
        params.setBuId(bean.getBuId());
        params.setUseOrgId(bean.getUseOrgId());
        params.setAccountSn(bean.getAccountSn());
        params.setAccountCategory(bean.getAccountCategory());
        params.setAccountType(bean.getAccountType());
        List<OrgBankAccountBean> list = bankAccountService.getList(params);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new OrgException(OrgErrorCode.ORG_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.service.impl.orgaccount.exist"));
        }
        Long accountId = -1L;
        //绑定资金账号
        bean.setAccountId(accountId);
        bean.setCreatedDate(new Date());
        bean.setCreatedBy(req.getUserId());
        bean.setModifiedBy(req.getUserId());
        bean.setModifiedDate(new Date());
        bean.setState(StateEnum.CREATE.getCode());
        bean.setAddedValueTax(BooleanEnum.FALSE.getCode());
        //保存账号
        bankAccountService.save(bean);
    }


    /**
     * 分配接口
     *
     * @param param
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public List<BankAccountUpdateStatusResp> allot(BankAccountAllotParam param) {
        Set<Integer> ids = param.getIds();
        Set<Long> orgIds = param.getOrgIds();
        Map<Long, String> orgNames = getOrgName(orgIds.stream().collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(ids)) {
            LOGGER.error("未接受到选中的资金账户ID:{}", JSONObject.toJSONString(param));
            return null;
        }
        //选择的数据
        List<OrgBankAccountBean> lists = bankAccountService.getListByIds(ids);
        //对选择数据进行过滤
        lists = lists.stream().filter(orgBank -> orgBank.getBuId().equals(orgBank.getUseOrgId())).filter(orgBank -> StateEnum.ENABLE.getCode().equals(orgBank.getState())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(lists)) {
            throw new OrgException(OrgErrorCode.ORG_SYSTEM_ERROR_CODE, I18nUtils.getMessage("org.service.impl.select.enableorg.allot"));
        }
        LOGGER.info("要进行分配的资金账号:{}", JSONObject.toJSONString(lists));
        //获取去重后的的账号ID
        Set<Long> accountIds = lists.stream().map(list -> list.getAccountId()).collect(Collectors.toSet());
        //查找账号分配的使用组织，并进行分组。
        List<OrgBankAccountBean> orgBankAccountList = bankAccountService.getListByAccountIds(accountIds);
        if (CollectionUtils.isEmpty(orgBankAccountList)) {
            LOGGER.error("根据账户ID，未查找到数据,AccountIds:{}", accountIds);
            return null;
        }
        Map<Long, OrgBankAccountBean> accountCatch = orgBankAccountList.stream().filter(distinctByKey(a -> a.getAccountId())).collect(Collectors.toMap(key -> key.getAccountId(), value -> value));
        Map<Long, List<Long>> allotSuccess = new HashMap<>();
        Map<Long, List<Long>> allotError = new HashMap<>();
        Map<Long, List<OrgBankAccountBean>> groupByAccountId = orgBankAccountList.stream().collect(Collectors.groupingBy(org -> org.getAccountId()));
        for (Map.Entry<Long, List<OrgBankAccountBean>> entry : groupByAccountId.entrySet()) {
            Long accountId = entry.getKey();
            List<OrgBankAccountBean> value = entry.getValue();
            List<Long> useOrgIds = value.stream().map(v -> v.getUseOrgId()).collect(Collectors.toList());
            //与要分配的组织做对比，交集的结果集为失败，余集为成功
            List<Long> error = orgIds.stream().filter(orgId -> useOrgIds.contains(orgId)).collect(Collectors.toList());
            List<Long> success = orgIds.stream().filter(orgId -> !useOrgIds.contains(orgId)).collect(Collectors.toList());
            allotSuccess.put(accountId, success);
            allotError.put(accountId, error);
        }
        LOGGER.info("需要分配的信息:{},不需要分配的信息:{}", allotSuccess, allotError);
        List<OrgBankAccountBean> addBeans = new ArrayList<>();
        List<BankAccountUpdateStatusResp> response = new ArrayList<>();
        for (Map.Entry<Long, List<Long>> entry : allotSuccess.entrySet()) {
            Long accountId = entry.getKey();
            List<Long> addUseOrgIds = entry.getValue();
            OrgBankAccountBean bean = accountCatch.get(accountId);
            addUseOrgIds.forEach(useOrgId -> {
                BankAccountUpdateStatusResp resp = new BankAccountUpdateStatusResp();
                resp.setFlag(true);
                resp.setMsg( I18nUtils.getMessage("org.service.impl.bankaccount.allot.success",new Object[]{bean.getAccountSn(),orgNames.get(useOrgId)}));
                response.add(resp);
                OrgBankAccountBean addBean = new OrgBankAccountBean();
                BeanUtils.copyProperties(bean, addBean);
                addBean.setId(null);
                addBean.setGroupId(param.getGroupId());
                addBean.setCreatedBy(param.getUserId());
                addBean.setCreatedDate(new Date());
                addBean.setUseOrgId(useOrgId);
                addBean.setState(StateEnum.CREATE.getCode());
                addBeans.add(addBean);
            });
        }
        bankAccountService.saveList(addBeans);
        for (Map.Entry<Long, List<Long>> entry : allotError.entrySet()) {
            Long accountId = entry.getKey();
            OrgBankAccountBean bean = accountCatch.get(accountId);
            List<Long> erroUseOrgIds = entry.getValue();
            erroUseOrgIds.forEach(errorOrgId -> {
                BankAccountUpdateStatusResp resp = new BankAccountUpdateStatusResp();
                resp.setFlag(false);
                resp.setMsg(I18nUtils.getMessage("org.service.impl.bankaccount.allot.fail",new Object[]{ bean.getAccountSn(),orgNames.get(errorOrgId)}));
                response.add(resp);
            });
        }
        return response;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }


    /**
     * 根据组织ID 获取组织名称
     */
    public Map<Long, String> getOrgName(List<Long> orgIds) {
        BatchQueryParam params = new BatchQueryParam();
        Map<Long, String> orgNames = new HashMap<>();
        if (CollectionUtils.isNotEmpty(orgIds)) {
            params.setIds(orgIds);
            List<OrgInfoDto> orgInfoDtos = organizationService.batchQueryOrgInfo(params);
            if (CollectionUtils.isNotEmpty(orgInfoDtos)) {
                orgInfoDtos.forEach(orgInfoDto -> orgNames.put(orgInfoDto.getId(), orgInfoDto.getOrgName()));
            }
        }
        return orgNames;
    }

    public String getOrgName(Long orgId) {
        if (orgId != null) {
            Map<Long, String> orgName = getOrgName(Lists.newArrayList(orgId));
            return orgName.get(orgId);
        }
        return null;
    }


    /**
     * 取消分配
     *
     * @param param
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public List<BankAccountUpdateStatusResp> cancel(BankAccountAllotParam param) {
        Set<Integer> ids = param.getIds();
        List<BankAccountUpdateStatusResp> response = new ArrayList<>();
        //删除该账号分配
        List<OrgBankAccountBean> bankAccountBeans = bankAccountService.getListByIds(ids);
        List<Long> useOrgIds = bankAccountBeans.stream().map(bean -> bean.getUseOrgId()).collect(Collectors.toList());
        Map<Long, String> orgName = getOrgName(useOrgIds);
        //不符合取消分配的数据
        List<OrgBankAccountBean> errorOrgBank = bankAccountBeans.stream().filter(bankAccount -> !StateEnum.CREATE.getCode().equals(bankAccount.getState())).collect(Collectors.toList());
        //不符合取消分配的
        List<OrgBankAccountBean> successOrgBank = bankAccountBeans.stream().filter(bankAccount -> StateEnum.CREATE.getCode().equals(bankAccount.getState())).collect(Collectors.toList());
        errorOrgBank.forEach(error -> {
            BankAccountUpdateStatusResp resp = new BankAccountUpdateStatusResp();
            resp.setFlag(false);
            resp.setMsg(I18nUtils.getMessage("org.service.impl.bankaccount.allot.cancel.fail",new Object[]{error.getAccountSn(),orgName.get(error.getUseOrgId())}));
            response.add(resp);
        });
        //符合取消分配的
        List<Long> updateIds = new ArrayList<>();
        //需要解绑的资金账户
        List<OrgBankAccountBean> unBindings = new ArrayList<>();
        //不需要解绑的资金账户
        successOrgBank.forEach(success -> {
            if (success.getBuId().equals(success.getUseOrgId())) {
                unBindings.add(success);
                return;
            }
            BankAccountUpdateStatusResp resp = new BankAccountUpdateStatusResp();
            updateIds.add(success.getId());
            resp.setFlag(true);
            resp.setMsg(I18nUtils.getMessage("org.service.impl.bankaccount.allot.cancel.success",new Object[]{ success.getAccountSn() ,orgName.get(success.getUseOrgId())}));
            response.add(resp);
        });
        //进行批量更新
        bankAccountService.deleteByIds(updateIds);
        return response;
    }


    /**
     * 分配查询
     *
     * @param requestQuery
     * @return
     */
    public List<BankAccountResp> allotQuery(BankAccountAllotParam requestQuery) {
        OrgBankAccountBean params = new OrgBankAccountBean();
        if (CollectionUtils.isEmpty(requestQuery.getAccountIds())) {
            return null;
        }
        params.setAccountIds(requestQuery.getAccountIds());
        params.setGroupId(requestQuery.getGroupId());
        List<OrgBankAccountBean> list = bankAccountService.getList(params);
        List<BankAccountResp> resps = BankAccountResp.generatorFromBeanList(list);
        //填充支付信息
        List<Long> accountIds = requestQuery.getAccountIds().stream().collect(Collectors.toList());
        List<Long> orgIds = initPayDomainInfo(resps, accountIds, null);
        //填充组织信息
        initOrgInfo(resps, orgIds);
        return resps;
    }


    /**
     * 调用支付初始化
     * 填充支付内容信息
     *
     * @param resps      返回对象
     * @param accountIds 支付账号ID
     * @param userMap    用户缓存，用于填充返回值
     * @return 需要填充的组织信息
     */
    private List<Long> initPayDomainInfo(List<BankAccountResp> resps, List<Long> accountIds, Map<Long, FplUser> userMap) {
        List<BankAccountVo> bankAccountVos = new ArrayList<>();
        Map<Long, BankAccountVo> bankAccountVoMap = new HashMap<>();
        bankAccountVos.forEach(bankAccountVo -> {
            bankAccountVoMap.put(bankAccountVo.getId(), bankAccountVo);
        });
        //3.填充返回值
        List<Long> orgIds = new ArrayList<>();
        resps.forEach(resp -> {
            BankAccountVo bankAccountVo = bankAccountVoMap.get(resp.getAccountId());
            resp.setOpenBankName(bankAccountVo.getBankName());
            resp.setBankNameCode(bankAccountVo.getBankCode());
            resp.setAccountName(bankAccountVo.getAccountName());
            if (resp.getAccountCategory() == null) {
                resp.setAccountCategory(bankAccountVo.getAccountCategory());
            }
            if (resp.getAccountType() == null) {
                resp.setAccountType(bankAccountVo.getAccountType());
            }
            if (resp.getAccountSn() == null) {
                resp.setAccountSn(bankAccountVo.getAccountSn());
            }
            if (userMap != null) {
                FplUser user = userMap.get(resp.getManagerUserId());
                if (user != null) {
                    resp.setManagerUserName(user.getUserName());
                }
            }
            orgIds.add(resp.getUseOrgId());
            orgIds.add(resp.getOpenOrgId());
        });
        return orgIds;
    }


    /**
     * 调用组织信息
     * 填充组织信息
     */
    private void initOrgInfo(List<BankAccountResp> resps, List<Long> orgIds) {
        BatchQueryParam param = new BatchQueryParam();
        param.setIds(orgIds);
        List<OrgInfoDto> orgInfoDtos = organizationService.batchQueryOrgInfo(param);
        Map<Long, String> orgInfoMap = new HashMap<>();
        orgInfoDtos.forEach(orgInfo -> orgInfoMap.put(orgInfo.getId(), orgInfo.getOrgName()));
        //组织名称
        resps.forEach(resp -> {
            resp.setUseOrgName(orgInfoMap.get(resp.getUseOrgId()));
            resp.setOpenOrgName(orgInfoMap.get(resp.getOpenOrgId()));
        });
    }
}
