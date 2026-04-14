package com.basedata.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.basedata.common.dto.DeleteDto;
import com.basedata.common.dto.GeneralBatchUpdateByIdsDto;
import com.basedata.common.dto.UpdateStatusDto;
import com.basedata.common.enums.BooleanEnum;
import com.basedata.common.enums.StateEnum;
import com.basedata.common.util.PageUtil;
import com.basedata.server.domain.OrgDomainService;
import com.basedata.server.domain.PermissionDomainService;
import com.basedata.server.dto.BasePlatformStoreReqDTO;
import com.basedata.server.entity.BasePlatformStore;
import com.basedata.server.mapper.BasePlatformStoreMapper;
import com.basedata.server.query.BasePlatformStoreQueryVo;
import com.basedata.server.service.BasePlatformStoreService;
import com.basedata.server.vo.BasePlatformStoreUpdateVo;
import com.basedata.server.vo.BasePlatformStoreVo;
import com.common.base.entity.CurrentUser;
import com.common.framework.execption.BizException;
import com.common.framework.number.BaseNoGenerateUtil;
import com.common.framework.user.FplUserUtil;
import com.common.language.util.I18nUtils;
import com.common.util.util.IdWorker;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.OrganizationDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 快递面单模板授权店铺 服务实现类
 * </p>
 */
@Service
public class BasePlatformStoreServiceImpl implements BasePlatformStoreService {

    @Resource
    private BasePlatformStoreMapper platformStoreMapper;
    @Resource
    private OrgDomainService orgDomainService;
    @Resource
    private PermissionDomainService permissionDomainService;

    /**
     * 新增
     *
     * @param updateVo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(BasePlatformStoreUpdateVo updateVo) {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));
        Long serviceProviderId = currentUser.getServiceProviderId();

        BasePlatformStore existOne = this.findOne(serviceProviderId, updateVo.getPlatformCode(), updateVo.getBelongOwnerId());
        Assert.isNull(existOne, I18nUtils.getMessage("base.check.platformstore.ownerstore.exist"));

        OrganizationDto serviceProviderInfo = orgDomainService.getServiceProviderById(serviceProviderId);
        Assert.notNull(serviceProviderInfo, I18nUtils.getMessage("base.check.common.groupunitnoexist"));

        // 生成ID
        updateVo.setId(IdWorker.getNextId());
        BasePlatformStore entity = new BasePlatformStore();
        BeanUtils.copyProperties(updateVo, entity);
        // 配置编号生成规则:YYMMDD+4 位流水号，4 位流水号每天从 0001 开始
        if (StringUtils.isBlank(entity.getConfigCode())) {
            entity.setConfigCode(BaseNoGenerateUtil.generateRuleCode(serviceProviderId + ""));
        }
        entity.setServiceProviderId(serviceProviderId);
        entity.setServiceProviderName(serviceProviderInfo.getOrgName());
        entity.setGroupId(currentUser.getGroupId());
        entity.setDefaultCreateValue(currentUser);
        entity.setState(StateEnum.NOT_ENABLE.getCode());
        platformStoreMapper.insert(entity);
    }

    /**
     * 更新
     *
     * @param updateVo
     * @return
     */
    @Override
    public void update(BasePlatformStoreUpdateVo updateVo) {
        Assert.notNull(updateVo.getId(), I18nUtils.getMessage("base.check.common.idnotnull"));
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));
        Long serviceProviderId = currentUser.getServiceProviderId();


        BasePlatformStore currentOne = this.findOneById(updateVo.getId());
//        Assert.isFalse(StateEnum.DISABLE.getCode().intValue() != currentOne.getState()
//                        && StateEnum.NOT_ENABLE.getCode().intValue() != currentOne.getState()
//                , "非停用或创建状态，不可以操作！");
        if (StateEnum.NOT_ENABLE.getCode().equals(currentOne.getState())) {
            Assert.isFalse(!currentOne.getConfigName().equals(updateVo.getConfigName()) || !currentOne.getConfigCode().equals(updateVo.getConfigCode()), "创建状态:规则描述、配置编码不能修改！");
        } else if (StateEnum.ENABLE.getCode().equals(currentOne.getState())) {
            Assert.isFalse(!currentOne.getConfigName().equals(updateVo.getConfigName()) || !currentOne.getConfigCode().equals(updateVo.getConfigCode()) || !currentOne.getPlatformCode().equals(updateVo.getPlatformCode()), "停用状态:规则描述、配置编码、电商平台不能修改！");
        } else if (StateEnum.DISABLE.getCode().equals(currentOne.getState())) {
            Assert.isFalse(!currentOne.getConfigName().equals(updateVo.getConfigName()) || !currentOne.getConfigCode().equals(updateVo.getConfigCode()) || !currentOne.getPlatformCode().equals(updateVo.getPlatformCode()), "启用状态:规则描述、配置编码、电商平台不能修改！");
        }
        BasePlatformStore existOne = this.findOne(serviceProviderId, updateVo.getPlatformCode(), updateVo.getBelongOwnerId());
        if (existOne != null && existOne.getId() != updateVo.getId().longValue()) {
            throw new BizException(I18nUtils.getMessage("base.check.platformstore.ownerstore.exist"));
        }

        BasePlatformStore entity = new BasePlatformStore();
        BeanUtils.copyProperties(updateVo, entity);
        entity.setDefaultUpdateValue(currentUser);
        platformStoreMapper.updateById(entity);
    }

    private BasePlatformStore findOneById(Long id) {
        BasePlatformStoreReqDTO reqDTO = new BasePlatformStoreReqDTO();
        reqDTO.setId(id);
        List<BasePlatformStore> currentList = platformStoreMapper.findList(reqDTO);
        Assert.notEmpty(currentList, I18nUtils.getMessage("base.check.platformstore.ec.autostore.config.noexist"));
        return currentList.get(0);
    }

    /**
     * 批量删除
     *
     * @param deleteDto
     * @return
     */
    @Override
    public boolean deleteByIds(DeleteDto deleteDto) {
        Assert.notEmpty(deleteDto.getIds(), I18nUtils.getMessage("base.check.common.configidarraynotnull"));
        BasePlatformStoreReqDTO reqDTO = new BasePlatformStoreReqDTO();
        reqDTO.setIds(deleteDto.getIds());
        List<BasePlatformStore> currentList = platformStoreMapper.findList(reqDTO);
        Assert.notEmpty(currentList, I18nUtils.getMessage("base.check.common.selecteddatanoexist"));
        Assert.isFalse(currentList.stream().anyMatch(x -> StateEnum.NOT_ENABLE.getCode().intValue() != x.getState()),
                I18nUtils.getMessage("base.check.common.onlydeletecreatestate"));

        GeneralBatchUpdateByIdsDto updateByIdsDto = new GeneralBatchUpdateByIdsDto();
        updateByIdsDto.setIds(deleteDto.getIds());
        updateByIdsDto.setDeletedFlag(BooleanEnum.TRUE.getCode());

        updateByIdsDto.setModifiedBy(FplUserUtil.getUserId());
        updateByIdsDto.setModifiedName(FplUserUtil.getUserName());
        updateByIdsDto.setModifiedDate(LocalDateTime.now());
        return platformStoreMapper.batchUpdate(updateByIdsDto) > 0;
    }

    @Override
    public boolean batchEnableOrDisable(UpdateStatusDto statusDto) {
        Assert.notEmpty(statusDto.getIds(), I18nUtils.getMessage("base.check.common.configidarraynotnull"));
        BasePlatformStoreReqDTO reqDTO = new BasePlatformStoreReqDTO();
        reqDTO.setIds(statusDto.getIds());
        List<BasePlatformStore> currentList = platformStoreMapper.findList(reqDTO);
        Assert.notEmpty(currentList, I18nUtils.getMessage("base.check.common.selecteddatanoexist"));
        // 要改成启用状态
        if (StateEnum.ENABLE.getCode().intValue() == statusDto.getState()) {
            Assert.isFalse(currentList.stream().anyMatch(x ->
                            StateEnum.DISABLE.getCode().intValue() != x.getState()
                                    && StateEnum.NOT_ENABLE.getCode().intValue() != x.getState()),
                    I18nUtils.getMessage("base.check.common.canenablestate"));
        } else if (StateEnum.DISABLE.getCode().intValue() == statusDto.getState()) {
            Assert.isFalse(currentList.stream().anyMatch(x ->
                            StateEnum.ENABLE.getCode().intValue() != x.getState()),
                    I18nUtils.getMessage("base.check.common.candisablestate"));
        }

        GeneralBatchUpdateByIdsDto updateByIdsDto = new GeneralBatchUpdateByIdsDto();
        updateByIdsDto.setIds(statusDto.getIds());
        updateByIdsDto.setState(statusDto.getState());

        updateByIdsDto.setModifiedBy(FplUserUtil.getUserId());
        updateByIdsDto.setModifiedName(FplUserUtil.getUserName());
        updateByIdsDto.setModifiedDate(LocalDateTime.now());
        return platformStoreMapper.batchUpdate(updateByIdsDto) > 0;
    }

    /**
     * 分页查询
     *
     * @param queryVo
     * @return
     */
    @Override
    public PageInfo<BasePlatformStoreVo> queryPageList(BasePlatformStoreQueryVo queryVo) throws Exception {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));
        // 3PL基础数据，集团级别查询权限，无需切换业务单元（仓储服务商）
//        queryVo.setServiceProviderId(currentUser.getServiceProviderId());
        queryVo.setGroupId(currentUser.getGroupId());
        List<Long> orgIdList = permissionDomainService.getOrgList(currentUser.getGroupId(),currentUser.getUserId());
        if (CollectionUtils.isEmpty(orgIdList)){
            return new PageInfo<>();
        }
        PageHelper.startPage(queryVo.getPageNum(), queryVo.getPageSize());
        BasePlatformStoreReqDTO reqDTO = new BasePlatformStoreReqDTO();
        BeanUtils.copyProperties(queryVo, reqDTO);
        reqDTO.setServiceProviderIdList(orgIdList);
        // 模糊查询
        reqDTO.setAuthStoreCodeLike(queryVo.getAuthStoreCode());
        reqDTO.setAuthStoreNameLike(queryVo.getAuthStoreName());
        List<BasePlatformStore> list = platformStoreMapper.findList(reqDTO);
        return PageUtil.convert(new PageInfo<>(list), item -> BeanUtil.toBean(item, BasePlatformStoreVo.class));
    }

    /**
     * 根据电商平台查找有效授权店铺
     *
     * @param platformCode
     * @return
     * @throws Exception
     */
    @Override
    public List<BasePlatformStore> getAuthStoreByPlatformCode(String platformCode) {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));
        Long serviceProviderId = currentUser.getServiceProviderId();

        BasePlatformStoreReqDTO reqDTO = new BasePlatformStoreReqDTO();
        reqDTO.setServiceProviderId(serviceProviderId);
        reqDTO.setPlatformCode(platformCode);
        reqDTO.setState(StateEnum.ENABLE.getCode());
        List<BasePlatformStore> authList = platformStoreMapper.findList(reqDTO);
        Assert.notEmpty(authList, I18nUtils.getMessage("base.check.platformstore.ec.autostore.config.null"));
        return authList;
    }


    /**
     * 根据仓储服务商、平台编码、货主ID 查找唯一一条平台配置
     *
     * @param serviceProviderId
     * @param platformCode
     * @param belongOwnerId
     * @return 1、存在，返回对象；2、不存在，返回null；3、存在2条以上，抛异常
     */
    private BasePlatformStore findOne(Long serviceProviderId, String platformCode, Long belongOwnerId) {
        BasePlatformStoreReqDTO reqDTO = new BasePlatformStoreReqDTO();
        reqDTO.setServiceProviderId(serviceProviderId);
        reqDTO.setPlatformCode(platformCode);
        reqDTO.setBelongOwnerId(belongOwnerId);
        List<BasePlatformStore> authList = platformStoreMapper.findList(reqDTO);
        if (CollectionUtils.isEmpty(authList)) {
            return null;
        }
        Assert.isFalse(authList.size() > 1, I18nUtils.getMessage("base.check.platformstore.service.ec.config.exist",new String[]{platformCode}));
        return authList.get(0);
    }
}
