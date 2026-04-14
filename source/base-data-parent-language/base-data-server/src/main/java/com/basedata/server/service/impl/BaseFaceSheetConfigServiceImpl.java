package com.basedata.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.basedata.common.dto.FacesheetConfigDto;
import com.basedata.common.dto.QueryStoreInfoDto;
import com.basedata.common.enums.BooleanEnum;
import com.basedata.common.enums.StateEnum;
import com.basedata.common.vo.FacesheetConfigForPrintVo;
import com.basedata.common.vo.FacesheetConfigVo;
import com.basedata.server.entity.BaseStore;
import com.basedata.server.mapper.BasePlatformLogisticsMapper;
import com.basedata.server.mapper.BaseStoreMapper;
import com.basedata.server.service.BaseFaceSheetConfigService;
import com.common.framework.execption.BizException;
import com.common.language.util.I18nUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BaseFaceSheetConfigServiceImpl implements BaseFaceSheetConfigService {

    @Resource
    private BaseStoreMapper baseStoreMapper;

    @Resource
    private BasePlatformLogisticsMapper platformLogisticsMapper;

    @Override
    public FacesheetConfigVo getConfig(FacesheetConfigDto configDto) {

        //获取店铺信息
        LambdaQueryWrapper<BaseStore> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BaseStore::getServiceProviderId, configDto.getServiceProviderId());
        if (StringUtils.hasLength(configDto.getStoreCode())) {
            lambdaQueryWrapper.eq(BaseStore::getStoreCode, configDto.getStoreCode());
        }
        if (StringUtils.hasLength(configDto.getStoreName())) {
            lambdaQueryWrapper.eq(BaseStore::getStoreName, configDto.getStoreName());
        }
        lambdaQueryWrapper.eq(BaseStore::getOwnerId, configDto.getOwnerId());
        lambdaQueryWrapper.eq(BaseStore::getPlatformCode, configDto.getExtPlatformCode());
        lambdaQueryWrapper.eq(BaseStore::getState, StateEnum.ENABLE.getCode());
        lambdaQueryWrapper.eq(BaseStore::getDeletedFlag, BooleanEnum.FALSE.getCode());

        List<BaseStore> storeList = baseStoreMapper.selectList(lambdaQueryWrapper);
        if (CollectionUtils.isEmpty(storeList)){
            throw new BizException(I18nUtils.getMessage("base.store.noexist"));
        }
        BaseStore baseStore = storeList.get(0);
        FacesheetConfigVo facesheetConfigVo = new FacesheetConfigVo();
        facesheetConfigVo.setDelivererContact(baseStore.getContacts());
        facesheetConfigVo.setDelivererContactTel(baseStore.getContactNumber());
        facesheetConfigVo.setMerchantCode(baseStore.getMerchantCode());
        facesheetConfigVo.setStoreId(baseStore.getId());
        facesheetConfigVo.setStoreCode(baseStore.getStoreCode());
        facesheetConfigVo.setMpFlag(baseStore.getMpFlag());
        return facesheetConfigVo;
    }

    @Override
    public List<FacesheetConfigForPrintVo> getBatchConfig(QueryStoreInfoDto queryStoreInfoDto) {

        return null;
    }



}
