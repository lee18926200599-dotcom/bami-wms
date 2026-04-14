package com.basedata.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.basedata.common.dto.BasePlatformAuthDto;
import com.basedata.common.dto.BasePlatformAuthReqDTO;
import com.basedata.common.dto.GeneralBatchUpdateByIdsDto;
import com.basedata.common.dto.UpdateStatusDto;
import com.basedata.common.enums.BooleanEnum;
import com.basedata.common.enums.IntegrationPlatformTypeEnum;
import com.basedata.common.enums.StateEnum;
import com.basedata.common.util.PageUtil;
import com.basedata.common.vo.BasePlatformAuthQueryVo;
import com.basedata.common.vo.BasePlatformAuthVo;
import com.basedata.server.domain.OrgDomainService;
import com.basedata.server.domain.PermissionDomainService;
import com.basedata.server.dto.integration.PlatformTokenRespDTO;
import com.basedata.server.entity.BasePlatformAuth;
import com.basedata.server.mapper.BasePlatformAuthMapper;
import com.basedata.server.service.BasePlatformAuthService;
import com.basedata.server.vo.BasePlatformAuthUpdateVo;
import com.common.base.entity.CurrentUser;
import com.common.base.enums.PlatformTypeEnum;
import com.common.framework.execption.BizException;
import com.common.framework.number.BaseNoGenerateUtil;
import com.common.framework.user.FplUserUtil;
import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.common.util.util.AssertUtils;
import com.common.util.util.http.HttpUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.OrganizationDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 货主平台授权信息表 服务实现类
 * </p>
 */
@Slf4j
@Service
public class BasePlatformAuthServiceImpl implements BasePlatformAuthService {
    @Resource
    private BasePlatformAuthMapper platformAuthMapper;
    @Resource
    private OrgDomainService orgDomainService;

    @Autowired
    private PermissionDomainService permissionDomainService;

    @Value("${integration.api.getPlatformInfo:null}")
    private String getPlatformInfoUrl;
    @Value("${integration.api.getToken:null}")
    private String getTokenUrl;

    /**
     * 新增
     *
     * @param updateVo
     * @return
     */
    @Override
    public void save(BasePlatformAuthUpdateVo updateVo) {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));
        Long serviceProviderId = currentUser.getServiceProviderId();

        BasePlatformAuth existOne = this.findOne(serviceProviderId, updateVo.getOwnerId(), updateVo.getPlatformCode());
        if (existOne != null) {
            throw new BizException(I18nUtils.getMessage("base.check.platformauth.ownerecconfigexist",new String[]{existOne.getOwnerName(),existOne.getPlatformName(),existOne.getConfigCode()}));
        }

        OrganizationDto serviceProviderInfo = orgDomainService.getServiceProviderById(serviceProviderId);
        Assert.notNull(serviceProviderInfo, I18nUtils.getMessage("base.check.platformauth.groupunitnoexist"));

        updateVo.setId(null);
        BasePlatformAuth entity = new BasePlatformAuth();
        BeanUtils.copyProperties(updateVo, entity);
        entity.setConfigCode(BaseNoGenerateUtil.generateRuleCode(serviceProviderId + ""));
        // 授权编码/客户id(店铺id)：货主编码 +"_"+平台编码
        entity.setCustomerId(entity.getOwnerCode() + "_" + entity.getPlatformCode());
        entity.setServiceProviderId(serviceProviderId);
        entity.setServiceProviderName(serviceProviderInfo.getOrgName());
        entity.setGroupId(currentUser.getGroupId());
        entity.setDefaultCreateValue(currentUser);
        entity.setState(StateEnum.NOT_ENABLE.getCode());
        platformAuthMapper.insert(entity);
    }

    /**
     * 更新
     *
     * @param updateVo
     * @return
     */
    @Override
    public void update(BasePlatformAuthUpdateVo updateVo) {
        Assert.notNull(updateVo.getId(), I18nUtils.getMessage("base.check.platformauth.idnotnull"));

        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));
        Long serviceProviderId = currentUser.getServiceProviderId();

        BasePlatformAuth currentOne = this.findOneById(updateVo.getId());
//        Assert.isFalse(StateEnum.DISABLE.getCode().intValue() != currentOne.getState()
//                        && StateEnum.NOT_ENABLE.getCode().intValue() != currentOne.getState()
//                , "非停用或创建状态，不可以操作！");

        if (StateEnum.NOT_ENABLE.getCode().equals(currentOne.getState())) {
            Assert.isFalse(!currentOne.getRemark().equals(updateVo.getRemark()) || !currentOne.getConfigCode().equals(updateVo.getConfigCode()), I18nUtils.getMessage("base.check.platformauth.createstatusnoupdate"));
        } else if (StateEnum.ENABLE.getCode().equals(currentOne.getState())) {
            Assert.isFalse(!currentOne.getRemark().equals(updateVo.getRemark()) || !currentOne.getConfigCode().equals(updateVo.getConfigCode()) || !currentOne.getOwnerId().equals(updateVo.getOwnerId()) || !currentOne.getPlatformCode().equals(updateVo.getPlatformCode()), I18nUtils.getMessage("base.check.platformauth.disablestatusnoupdate"));
        } else if (StateEnum.DISABLE.getCode().equals(currentOne.getState())) {
            Assert.isFalse(!currentOne.getRemark().equals(updateVo.getRemark()) || !currentOne.getConfigCode().equals(updateVo.getConfigCode()) || !currentOne.getOwnerId().equals(updateVo.getOwnerId()) || !currentOne.getPlatformCode().equals(updateVo.getPlatformCode()), I18nUtils.getMessage("base.check.platformauth.ablestatusnoupdate"));
        }

        BasePlatformAuth existOne = this.findOne(serviceProviderId, updateVo.getOwnerId(), updateVo.getPlatformCode());
        if (existOne != null && existOne.getId().longValue() != updateVo.getId()) {
            throw new BizException(I18nUtils.getMessage("base.check.platformauth.ownerecconfigexist",new String[]{existOne.getOwnerName(),existOne.getPlatformName(),existOne.getConfigCode()}));
        }

        BasePlatformAuth entity = new BasePlatformAuth();
        BeanUtils.copyProperties(updateVo, entity);
        // 授权编码/客户id(店铺id)：货主编码 +"_"+平台编码
        entity.setCustomerId(entity.getOwnerCode() + "_" + entity.getPlatformCode());
        entity.setDefaultUpdateValue(currentUser);
        platformAuthMapper.updateById(entity);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @Override
    public boolean deleteByIds(List<Long> ids) {
        Assert.notEmpty(ids, I18nUtils.getMessage("base.check.platformauth.configidarraynotnull"));
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));

        BasePlatformAuthReqDTO reqDTO = new BasePlatformAuthReqDTO();
        reqDTO.setIds(ids);
        List<BasePlatformAuth> currentList = platformAuthMapper.findList(reqDTO);
        Assert.notEmpty(currentList, I18nUtils.getMessage("base.check.common.selecteddatanoexist"));
        Assert.isFalse(currentList.stream().anyMatch(x -> StateEnum.NOT_ENABLE.getCode().intValue() != x.getState()),
                I18nUtils.getMessage("base.check.common.onlydeletecreatestate"));

        GeneralBatchUpdateByIdsDto updateByIdsDto = new GeneralBatchUpdateByIdsDto();
        updateByIdsDto.setIds(ids);
        updateByIdsDto.setDeletedFlag(BooleanEnum.TRUE.getCode());

        updateByIdsDto.setModifiedBy(currentUser.getUserId());
        updateByIdsDto.setModifiedName(currentUser.getUserName());
        updateByIdsDto.setModifiedDate(LocalDateTime.now());
        return platformAuthMapper.batchUpdate(updateByIdsDto) > 0;
    }

    /**
     * 分页查询
     *
     * @param queryVo
     * @return
     */
    @Override
    public PageInfo<BasePlatformAuthVo> queryPageList(BasePlatformAuthQueryVo queryVo) throws Exception {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));
        // 3PL基础数据，集团级别查询权限，无需切换业务单元（仓储服务商）
//        queryVo.setServiceProviderId(currentUser.getServiceProviderId());
        List<Long> orgIdList = permissionDomainService.getOrgList(currentUser.getGroupId(), currentUser.getUserId());
        if (CollectionUtil.isEmpty(orgIdList)) {
            return new PageInfo<>();
        }
        queryVo.setGroupId(currentUser.getGroupId());
        PageHelper.startPage(queryVo.getPageNum(), queryVo.getPageSize());
        BasePlatformAuthReqDTO reqDTO = new BasePlatformAuthReqDTO();
        BeanUtils.copyProperties(queryVo, reqDTO);
        reqDTO.setServiceProviderIdList(orgIdList);
        List<BasePlatformAuth> list = platformAuthMapper.findList(reqDTO);
        return PageUtil.convert(new PageInfo<>(list), item -> BeanUtil.toBean(item, BasePlatformAuthVo.class));
    }

    /**
     * 查询列表（不分页）
     *
     * @param reqDTO
     * @return
     */
    @Override
    public List<BasePlatformAuth> queryValidList(BasePlatformAuthReqDTO reqDTO) {
        if (reqDTO.getServiceProviderId() == null) {
            CurrentUser currentUser = FplUserUtil.getCurrentUser();
            Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));
            reqDTO.setServiceProviderId(currentUser.getServiceProviderId());
        }
        Assert.notNull(reqDTO.getServiceProviderId(), I18nUtils.getMessage("base.check.common.serviceidnotnull"));
        reqDTO.setState(StateEnum.ENABLE.getCode());
        return platformAuthMapper.findList(reqDTO);
    }

    /**
     * 批量启用停用
     *
     * @param statusDto
     */
    @Override
    public boolean batchEnableOrDisable(UpdateStatusDto statusDto) {
        Assert.notEmpty(statusDto.getIds(), I18nUtils.getMessage("base.check.common.configidarraynotnull"));
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));

        BasePlatformAuthReqDTO reqDTO = new BasePlatformAuthReqDTO();
        reqDTO.setIds(statusDto.getIds());
        List<BasePlatformAuth> currentList = platformAuthMapper.findList(reqDTO);
        Assert.notEmpty(currentList, I18nUtils.getMessage("base.check.common.selecteddatanoexist"));

        // 要改成启用状态
        if (StateEnum.ENABLE.getCode().intValue() == statusDto.getState()) {
            Assert.isFalse(currentList.stream().anyMatch(x ->
                            StateEnum.DISABLE.getCode().intValue() != x.getState()
                                    && StateEnum.NOT_ENABLE.getCode().intValue() != x.getState()),
                    I18nUtils.getMessage("base.check.platformauth.onlyenabledata"));
        } else if (StateEnum.DISABLE.getCode().intValue() == statusDto.getState()) {
            Assert.isFalse(currentList.stream().anyMatch(x ->
                            StateEnum.ENABLE.getCode().intValue() != x.getState()),
                    I18nUtils.getMessage("base.check.platformauth.onlydisabledata"));
        }

        GeneralBatchUpdateByIdsDto updateByIdsDto = new GeneralBatchUpdateByIdsDto();
        updateByIdsDto.setIds(statusDto.getIds());
        updateByIdsDto.setState(statusDto.getState());

        updateByIdsDto.setModifiedBy(currentUser.getUserId());
        updateByIdsDto.setModifiedName(currentUser.getUserName());
        updateByIdsDto.setModifiedDate(LocalDateTime.now());
        return platformAuthMapper.batchUpdate(updateByIdsDto) > 0;
    }

    /**
     * 生成平台授权链接
     *
     * @param id
     * @return
     */
    @Override
    public String genPlatformAuthUrl(Long id) {
        Assert.notNull(getPlatformInfoUrl, I18nUtils.getMessage("base.check.platformauth.searchurlnotnull"));

        BasePlatformAuth currentOne = this.findOneById(id);
        Assert.isTrue(StateEnum.ENABLE.getCode().intValue() == currentOne.getState(), I18nUtils.getMessage("base.check.platformauth.noable"));

        String platformCode = currentOne.getPlatformCode();
        IntegrationPlatformTypeEnum platformType;
        if (PlatformTypeEnum.JD.name().equals(platformCode)) {
            platformType = IntegrationPlatformTypeEnum.JD;
        } else if (PlatformTypeEnum.PDD.name().equals(platformCode)) {
            platformType = IntegrationPlatformTypeEnum.PDD;
        } else if (PlatformTypeEnum.DY.name().equals(platformCode)) {
            platformType = IntegrationPlatformTypeEnum.DY;
        } else if (PlatformTypeEnum.WPH.name().equals(platformCode)) {
            platformType = IntegrationPlatformTypeEnum.WPH;
        } else if (PlatformTypeEnum.KS.name().equals(platformCode)) {
            platformType = IntegrationPlatformTypeEnum.KS;
        } else if (PlatformTypeEnum.TM.name().equals(platformCode) || PlatformTypeEnum.TB.name().equals(platformCode)) {
            platformType = IntegrationPlatformTypeEnum.TB;
        } else if (PlatformTypeEnum.XHS.name().equals(platformCode)) {
            platformType = IntegrationPlatformTypeEnum.XHS;
        } else if (PlatformTypeEnum.WXSPHXD.name().equals(platformCode)) {
            platformType = IntegrationPlatformTypeEnum.WX;
        } else if (PlatformTypeEnum.DWZF.name().equals(platformCode)) {
            platformType = IntegrationPlatformTypeEnum.DW;
        } else {
            throw new BizException(platformCode + I18nUtils.getMessage("base.check.platformauth.platnoauth"));
        }
        String result;
        try {
            String requestUrl = String.format(getPlatformInfoUrl, platformType.getCode(), 1);
            result = HttpUtil.getForOpen(requestUrl);
        } catch (Exception e) {
            throw new BizException(I18nUtils.getMessage("base.check.platformauth.intefaceerror")+"：" + e.getMessage());
        }
        Assert.notEmpty(result, I18nUtils.getMessage("base.check.platformauth.rspno"));
        // start 修改接口平台，直接返回授权链接
//        JSONObject jsonObject = JSON.parseObject(result);
//        Assert.isTrue(jsonObject.getBoolean("success"), "响应失败：" + jsonObject.getString("message"));
//        String data = jsonObject.getString("data");
//        log.info("查询 接口平台平台应用，返回数据：{}", data);
//        if (StringUtils.isBlank(data) || "[]".equals(data)) {
//            throw new BizException(platformType.getName() + "平台应用信息不存在！");
//        }
//        List<PlatformAppInfoRespDTO> respDTOList = JSONObject.parseArray(data, PlatformAppInfoRespDTO.class);
//        PlatformAppInfoRespDTO appInfo = respDTOList.stream().filter(x -> !x.getName().contains("废")).findFirst().orElse(null);
//        Assert.notNull(appInfo, "接口平台上找不到" + platformType.getName() + "平台应用有效配置！");
//
//        // 直接取接口平台的授权链接
//        return appInfo.getAuthUrl();
        // end
        RestMessage<String> restMessage = JSON.parseObject(result, RestMessage.class);
        if (!restMessage.isSuccess()) {
            throw new BizException(restMessage.getMessage());
        }
        return restMessage.getData();
    }

    /**
     * 根据配置ID查找唯一一条平台授权配置
     * @param id
     * @return 1、存在，返回对象；2、不存在，抛异常
     */
    @Override
    public BasePlatformAuth findOneById(Long id) {
        BasePlatformAuthReqDTO reqDTO = new BasePlatformAuthReqDTO();
        reqDTO.setId(id);
        List<BasePlatformAuth> list = platformAuthMapper.findList(reqDTO);
        Assert.notEmpty(list, I18nUtils.getMessage("base.check.platformauth.authconfignoexist"));
        return list.get(0);
    }

    /**
     * 根据仓储服务商、货主、平台编码查找唯一一条平台授权配置
     *
     * @param serviceProviderId
     * @param ownerId
     * @param platformCode
     * @return 1、存在，返回对象；2、不存在，返回null；3、存在2条以上，抛异常
     */
    @Override
    public BasePlatformAuth findOne(Long serviceProviderId, Long ownerId, String platformCode) {
        BasePlatformAuthReqDTO queryVo = new BasePlatformAuthReqDTO();
        queryVo.setServiceProviderId(serviceProviderId);
        queryVo.setOwnerId(ownerId);
        queryVo.setPlatformCode(platformCode);
        List<BasePlatformAuth> authList = platformAuthMapper.findList(queryVo);
        if (CollectionUtils.isEmpty(authList)) {
            return null;
        }
        Assert.isFalse(authList.size() > 1, I18nUtils.getMessage("base.check.platformauth.ownerauthconfigexist"));
        return authList.get(0);
    }

    /**
     * 刷新token（从接口平台更新token）
     *
     * @param id
     * @return
     */
    @Override
    public boolean refreshToken(Long id) {
        BasePlatformAuth currentOne = this.findOneById(id);
        Assert.isTrue(StateEnum.ENABLE.getCode().intValue() == currentOne.getState(), I18nUtils.getMessage("base.check.common.noablecannotopearte"));

        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));

        // 拼接参数：?customerId=%s&appKey=%s&appName=%
        // http://192.168.1.103:10001/platform/getToken?customerId=TM-WXLQX003&appName=淘宝平台（生产）
        String result;
        try {
//            String requestUrl = String.format(getTokenUrl, customerId, null);
//            result = HttpUtil.get(null, requestUrl);
            Map<String, Object> dataMap = new HashMap<>();
            // 授权编码/客户id(店铺id)：货主编码 +"_"+平台编码
            dataMap.put("customerId", currentOne.getCustomerId());
            dataMap.put("platformType", currentOne.getPlatformCode());
            dataMap.put("refreshToken", currentOne.getRefreshToken());
            result = HttpUtil.get(dataMap, getTokenUrl);
        } catch (Exception e) {
            throw new BizException(I18nUtils.getMessage("base.check.platformauth.intefaceerror")+"：" + e.getMessage());
        }
        Assert.notEmpty(result, I18nUtils.getMessage("base.check.platformauth.rspno"));
        JSONObject jsonObject = JSON.parseObject(result);
        Assert.isTrue(jsonObject.getBoolean("success"), I18nUtils.getMessage("base.check.common.responsefailed")+"：" + jsonObject.getString("message"));
        String data = jsonObject.getString("data");
        log.info("查询 接口平台平台应用，返回数据：{}", data);
        if (StringUtils.isBlank(data) || "[]".equals(data)) {
            throw new BizException(I18nUtils.getMessage("base.check.platformauth.authnoexist",new String[]{currentOne.getCustomerId()}));
        }
        PlatformTokenRespDTO respDTO = JSONObject.parseObject(jsonObject.getString("data"), PlatformTokenRespDTO.class);

        BasePlatformAuth entity = new BasePlatformAuth();
        entity.setId(id);
        entity.setVersion(currentOne.getVersion());
//        entity.setAppKey(respDTO.getAppKey());
//        entity.setAppSecret(respDTO.getAppSecret());
        entity.setAccessToken(respDTO.getAccessToken());
        entity.setAccessTokenExpired(respDTO.getAccessTokenExpired());
        entity.setRefreshToken(respDTO.getRefreshToken());
        // 存京东平台返回的商家编码
        entity.setVendorId(respDTO.getVenderId());
        entity.setRemark(I18nUtils.getMessage("base.check.platformauth.storetoekn",new String[]{respDTO.getShopName()}));

        entity.setDefaultUpdateValue(currentUser);
        return platformAuthMapper.updateById(entity) > 0;
    }

    @Override
    public BasePlatformAuthDto queryByOwnerIdAndPlatformName(BasePlatformAuthQueryVo queryVo) {

        BasePlatformAuthDto basePlatformAuthDto = null;
        LambdaQueryWrapper<BasePlatformAuth> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BasePlatformAuth::getOwnerId, queryVo.getOwnerId());
        lambdaQueryWrapper.eq(BasePlatformAuth::getPlatformCode, queryVo.getPlatformCode());
        lambdaQueryWrapper.eq(BasePlatformAuth::getDeletedFlag, 0);
        List<BasePlatformAuth> list = this.platformAuthMapper.selectList(lambdaQueryWrapper);

        if (CollectionUtil.isNotEmpty(list)) {

            basePlatformAuthDto = new BasePlatformAuthDto();

            BeanUtils.copyProperties(list.get(0), basePlatformAuthDto);

        }
        return basePlatformAuthDto;
    }

    /**
     * 授权后更新token相关信息
     *
     * @param basePlatformAuthDto
     */
    public Boolean updateToken(BasePlatformAuthDto basePlatformAuthDto) {
        LambdaQueryWrapper<BasePlatformAuth> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BasePlatformAuth::getCustomerId, basePlatformAuthDto.getCustomerId());
        List<BasePlatformAuth> basePlatformAuthList = platformAuthMapper.selectList(lambdaQueryWrapper);
        AssertUtils.isNotEmpty(basePlatformAuthList, I18nUtils.getMessage("base.check.platformauth.noauthcode"));
        BasePlatformAuth currentOne = basePlatformAuthList.get(0);
        BasePlatformAuth entity = new BasePlatformAuth();
        entity.setId(currentOne.getId());
        entity.setAccessToken(basePlatformAuthDto.getAccessToken());
        entity.setRefreshToken(basePlatformAuthDto.getRefreshToken());
        entity.setAccessTokenExpired(basePlatformAuthDto.getAccessTokenExpired());
        entity.setModifiedBy(0L);
        entity.setModifiedDate(new Date());
        entity.setModifiedName("System");
        int result = platformAuthMapper.updateById(entity);
        return result > 0;
    }
}
