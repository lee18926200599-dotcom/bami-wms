package com.basedata.server.service.integration.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.basedata.common.dto.BasePlatformAuthReqDTO;
import com.basedata.common.dto.PlatformLogisticsRelationshipDTO;
import com.basedata.common.dto.PlatformLogisticsRelationshipReqDTO;
import com.basedata.common.util.ManualPageUtil;
import com.basedata.common.vo.BasePlatformPrintTemplateDto;
import com.basedata.server.dto.integration.GetPlatformNetsideAddressReqDto;
import com.basedata.server.dto.integration.GetPlatformNetsideAddressRespDto;
import com.basedata.server.dto.integration.GetPlatformNetsideRespDto;
import com.basedata.server.dto.integration.ReturnorderSenderVO;
import com.basedata.server.entity.BasePlatformAuth;
import com.basedata.server.entity.BasePlatformPrintTemplate;
import com.basedata.server.service.*;
import com.basedata.server.service.integration.PlatformApiService;
import com.basedata.server.service.integration.platform.PlatformMethodService;
import com.basedata.server.vo.integration.GetPlatformNetsideReqVo;
import com.basedata.server.vo.integration.ShopNetsiteVo;
import com.basedata.server.vo.integration.ShopQueryVo;
import com.basedata.server.vo.integration.SyncFaceOrderTemplateReqVo;
import com.common.base.entity.CurrentUser;
import com.common.base.enums.PlatformTypeEnum;
import com.common.base.enums.StateEnum;
import com.common.framework.execption.SystemException;
import com.common.framework.user.FplUserUtil;
import com.common.framework.web.SpringBeanLoader;
import com.common.language.util.I18nUtils;
import com.common.util.util.MD5Util;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PlatformApiServiceImpl implements PlatformApiService {
    @Autowired
    private BaseStoreService baseStoreService;
    @Resource
    private BasePlatformPrintTemplateService basePlatformPrintTemplateService;
    @Resource
    private BasePlatformLogisticsService basePlatformLogisticsService;
    @Resource
    private BasePlatformStoreService basePlatformStoreService;
    @Resource
    private BasePlatformAuthService basePlatformAuthService;

    /**
     * 获取快递网点信息
     *
     * @param reqVo
     * @return
     */
    @Override
    public List<GetPlatformNetsideRespDto> getNetsideAddress(GetPlatformNetsideReqVo reqVo) {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));
        Long serviceProviderId = currentUser.getServiceProviderId();

        //拿到平台编码 调用接口获取信息
        List<GetPlatformNetsideRespDto> respDtoList = new ArrayList<>();
        // 获取平台编码
        String platformCode = reqVo.getPlatformCode();
        String platformName = PlatformTypeEnum.getExistPlatformName(platformCode);
        // 统一成同一个平台
        String belongPlatform = PlatformTypeEnum.getBelongPlatform(reqVo.getPlatformCode());

        PlatformLogisticsRelationshipReqDTO relationshipReqDTO = new PlatformLogisticsRelationshipReqDTO();
        relationshipReqDTO.setServiceProviderId(serviceProviderId);
        //  DYXD、DY 都属于抖音
        relationshipReqDTO.setPlatformCode(platformCode);
        relationshipReqDTO.setLogisticsCode(reqVo.getLogisticsCode());
        List<PlatformLogisticsRelationshipDTO> relationshipDTOList = basePlatformLogisticsService.queryPlatformLogistics(relationshipReqDTO);

        Assert.notEmpty(relationshipDTOList, I18nUtils.getMessage("base.check.platformapi.relation.config.notnull",new String[]{platformName,reqVo.getLogisticsCode()}));
        log.info("获取电商平台网点，承运商编码对照关系：" + JSON.toJSONString(relationshipDTOList));

        // 找到当前平台、货主下的所有授权
        BasePlatformAuth platformAuth = basePlatformAuthService.findOne(serviceProviderId, reqVo.getOwnerId(), reqVo.getPlatformCode());
        Assert.notNull(platformAuth, I18nUtils.getMessage("base.check.platformapi.ec.owner.auth.noexist"));
        Assert.isFalse(!StateEnum.ENABLE.getCode().equals(platformAuth.getState()), I18nUtils.getMessage("base.check.platformapi.ec.owner.auth.unenable"));

        ShopQueryVo shopQueryVo = new ShopQueryVo();
        shopQueryVo.setCustomer_id(platformAuth.getCustomerId());
        // 统一成同一个平台
        shopQueryVo.setPlatformCode(belongPlatform);
        // TODO relationshipDTOList
        shopQueryVo.setPlatformLogisticsCode(relationshipDTOList.get(0).getPlatformLogisticsCode());
        shopQueryVo.setVendorId(platformAuth.getVendorId());
        shopQueryVo.setOwnerId(reqVo.getOwnerId());
        //根据承运商和Intergation上对应的客户ID查询店铺下承运商的网点信息
        PlatformMethodService platformMethodService = (PlatformMethodService) SpringBeanLoader.getBean(shopQueryVo.getPlatformCode() + "PlatformMethodServiceImpl");
        Assert.notNull(platformMethodService,  I18nUtils.getMessage("base.check.platformapi.paltform.method.noimpl",new String[]{shopQueryVo.getPlatformCode()}));
        List<ShopNetsiteVo> storeAddressTotal;
        try {
            storeAddressTotal = platformMethodService.getNetsideAddress(shopQueryVo);
        } catch (SystemException e) {
            e.printStackTrace();
            log.error("获取 {} 网点信息失败：{}", platformName, e.getMessage());
            throw new SystemException(I18nUtils.getMessage("base.check.platformapi.paltform.netside.fail",new String[]{ platformAuth.getCustomerId(),e.getMessage()}));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException( I18nUtils.getMessage("base.check.platformapi.paltform.netside.loadfail",new String[]{ platformName,e.getMessage()}));
        }
        if (!CollectionUtils.isEmpty(storeAddressTotal)) {
            for (ShopNetsiteVo shopNetsiteVo : storeAddressTotal) {
                GetPlatformNetsideRespDto respDto = new GetPlatformNetsideRespDto();
                respDto.setOwnerId(reqVo.getOwnerId());
                respDto.setOwnerName(platformAuth.getOwnerName());
                respDto.setVendorId(platformAuth.getVendorId());
                respDto.setLogisticsCode(reqVo.getLogisticsCode());
                respDto.setLogisticsCodeName(relationshipDTOList.get(0).getLogisticsName());
                // TODO 仓库信息
                respDto.setAllocatedQuantity(shopNetsiteVo.getAmount());
                respDto.setPlatformCode(platformCode);
                respDto.setPlatformName(platformName);
                respDto.setPlatformLogisticsCode(shopQueryVo.getPlatformLogisticsCode());
                respDto.setBranchCode(shopNetsiteVo.getNetsite_code());
                //网点名称
                respDto.setBranchName(shopNetsiteVo.getNetsite_name());
                respDto.setBrandCode(shopNetsiteVo.getBrandCode());
                respDto.setSendAddress(shopNetsiteVo.getSendAddress());
                for (ReturnorderSenderVO sendAddress : respDto.getSendAddress()) {
                    // 统一加唯一行标识，方便前端选中
                    sendAddress.setLineNo(UUID.randomUUID().toString().replaceAll("-", ""));
                }
                respDto.setAttrStr1(shopNetsiteVo.getAttrStr1());
                respDto.setAttrStr2(shopNetsiteVo.getAttrStr2());
                respDto.setAttrStr3(shopNetsiteVo.getAttrStr3());
                respDto.setAttrStr4(shopNetsiteVo.getAttrStr4());
                respDto.setAttrStr5(shopNetsiteVo.getAttrStr5());
                respDtoList.add(respDto);
            }
        }
        return respDtoList;
    }

    /**
     * 获取授权店铺下的快递网点信息
     *
     * @param reqDto
     * @return
     */
    @Override
    public PageInfo<GetPlatformNetsideAddressRespDto> getAuthStoreNetsideAddress(GetPlatformNetsideAddressReqDto reqDto) {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));
        Long serviceProviderId = currentUser.getServiceProviderId();

        // 获取平台编码
        String platformCode = reqDto.getPlatformCode();
        String platformName = PlatformTypeEnum.getExistPlatformName(platformCode);
        // 统一成同一个平台
        String belongPlatform = PlatformTypeEnum.getBelongPlatform(reqDto.getPlatformCode());

        PlatformLogisticsRelationshipReqDTO relationshipReqDTO = new PlatformLogisticsRelationshipReqDTO();
        relationshipReqDTO.setServiceProviderId(serviceProviderId);
        relationshipReqDTO.setPlatformCode(platformCode);
        relationshipReqDTO.setLogisticsCode(reqDto.getLogisticsCode());
        List<PlatformLogisticsRelationshipDTO> relationshipDTOList = basePlatformLogisticsService.queryPlatformLogistics(relationshipReqDTO);

        Assert.notEmpty(relationshipDTOList, I18nUtils.getMessage("base.check.platformapi.relation.config.notnull",new String[]{platformName,reqDto.getLogisticsCode()}));
        log.info("获取电商平台网点，承运商编码对照关系：" + JSON.toJSONString(relationshipDTOList));

        // 找到当前平台下的所有授权
        BasePlatformAuthReqDTO reqDTO = new BasePlatformAuthReqDTO();
        reqDTO.setServiceProviderId(serviceProviderId);
        reqDTO.setPlatformCode(platformCode);
        List<BasePlatformAuth> basePlatformAuthList = basePlatformAuthService.queryValidList(reqDTO);
        Assert.notEmpty(basePlatformAuthList, I18nUtils.getMessage("base.check.platformapi.ec.owner.auth.noexist"));

        //拿到平台编码 调用接口获取信息
        List<GetPlatformNetsideAddressRespDto> respDtoList = new ArrayList<>();
        for (BasePlatformAuth auth : basePlatformAuthList) {
            ShopQueryVo shopQueryVo = new ShopQueryVo();
            shopQueryVo.setCustomer_id(auth.getCustomerId());
            // 统一成同一个平台
            shopQueryVo.setPlatformCode(belongPlatform);
            // TODO relationshipDTOList
            shopQueryVo.setPlatformLogisticsCode(relationshipDTOList.get(0).getPlatformLogisticsCode());
            shopQueryVo.setVendorId(auth.getVendorId());
            shopQueryVo.setOwnerId(auth.getOwnerId());
            //根据承运商和Intergation上对应的客户ID查询店铺下承运商的网点信息
            PlatformMethodService platformMethodService = (PlatformMethodService) SpringBeanLoader.getBean(shopQueryVo.getPlatformCode() + "PlatformMethodServiceImpl");
            Assert.notNull(platformMethodService, I18nUtils.getMessage("base.check.platformapi.paltform.method.noimpl",new String[]{ shopQueryVo.getPlatformCode()}));
            List<ShopNetsiteVo> storeAddressTotal;
            try {
                storeAddressTotal = platformMethodService.getNetsideAddress(shopQueryVo);
            } catch (SystemException e) {
                e.printStackTrace();
                log.error("获取 {} 网点信息失败：{}", platformName, e.getMessage());
                throw new SystemException(I18nUtils.getMessage("base.check.platformapi.paltform.netside.fail", new String[]{auth.getCustomerId(),e.getMessage()}));
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(I18nUtils.getMessage("base.check.platformapi.paltform.netside.loadfail",new String[]{ platformName,e.getMessage()}));
            }
            if (CollectionUtils.isEmpty(storeAddressTotal)) {
                continue;
            }
            for (ShopNetsiteVo shopNetsiteVo : storeAddressTotal) {
                for (ReturnorderSenderVO sendAddress : shopNetsiteVo.getSendAddress()) {
                    GetPlatformNetsideAddressRespDto respDto = BeanUtil.toBean(sendAddress, GetPlatformNetsideAddressRespDto.class);
                    respDto.setVendorId(auth.getVendorId());
                    respDto.setLogisticsCode(reqDto.getLogisticsCode());
                    respDto.setLogisticsCodeName(relationshipDTOList.get(0).getLogisticsName());
                    respDto.setAllocatedQuantity(shopNetsiteVo.getAmount());
                    respDto.setPlatformCode(platformCode);
                    respDto.setPlatformName(platformName);
                    respDto.setPlatformLogisticsCode(shopQueryVo.getPlatformLogisticsCode());
                    respDto.setBranchCode(shopNetsiteVo.getNetsite_code());
                    //网点名称
                    respDto.setBranchName(shopNetsiteVo.getNetsite_name());
                    respDto.setBrandCode(shopNetsiteVo.getBrandCode());
                    respDto.setAttrStr1(shopNetsiteVo.getAttrStr1());
                    respDto.setAttrStr2(shopNetsiteVo.getAttrStr2());
                    respDto.setAttrStr3(shopNetsiteVo.getAttrStr3());
                    respDto.setAttrStr4(shopNetsiteVo.getAttrStr4());
                    respDto.setAttrStr5(shopNetsiteVo.getAttrStr5());
                    // 统一加唯一行标识，方便前端选中
                    respDto.setLineNo(MD5Util.md5(JSON.toJSONString(respDto)));
                    respDto.setOwnerId(auth.getOwnerId());
                    respDto.setOwnerName(auth.getOwnerName());
                    respDtoList.add(respDto);
                }
            }
        }
        if (CollectionUtils.isEmpty(respDtoList)) {
            return PageInfo.of(new ArrayList<>());
        }
        log.info("去重前：平台={}，平台授权数量={}，承运商={}，网点数量={}：", platformName, basePlatformAuthList.size(), reqDto.getLogisticsCode(), respDtoList.size());
        List<GetPlatformNetsideAddressRespDto> respDtoListNew = new ArrayList<>();
        Set<String> uniqueLine = new HashSet<>();
        for (GetPlatformNetsideAddressRespDto respDto : respDtoList) {
            if (uniqueLine.add(respDto.getLineNo())) {
                respDtoListNew.add(respDto);
            }
        }
        log.info("去重后：平台={}，平台授权数量={}，承运商={}，网点数量={}：", platformName, basePlatformAuthList.size(), reqDto.getLogisticsCode(), respDtoListNew.size());
        // 根据网点名称排序
        respDtoListNew.sort(Comparator.comparing(x -> x.getAddressId() + "_" + x.getMonthAccount()));
        // 手工分页
        return ManualPageUtil.manualPage(reqDto.getPageNum(), reqDto.getPageSize(), respDtoListNew);
    }


    /**
     * 获取电商平台面单模板并同步到本地库
     *
     * @param reqVo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String syncFaceOrderTemplate(SyncFaceOrderTemplateReqVo reqVo) {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));
        Long serviceProviderId = currentUser.getServiceProviderId();

        // 获取平台编码
        String platformCode = reqVo.getPlatformCode();
        String platformName = PlatformTypeEnum.getExistPlatformName(platformCode);
        // 统一成同一个平台
        String belongPlatform = PlatformTypeEnum.getBelongPlatform(reqVo.getPlatformCode());

        BasePlatformAuth platformAuth = basePlatformAuthService.findOne(serviceProviderId, reqVo.getOwnerId(), reqVo.getPlatformCode());
        Assert.notNull(platformAuth, I18nUtils.getMessage("base.check.platformapi.ec.owner.auth.noexist"));
        Assert.isFalse(!StateEnum.ENABLE.getCode().equals(platformAuth.getState()), I18nUtils.getMessage("base.check.platformapi.ec.owner.auth.unenable"));
        reqVo.setVendorId(platformAuth.getVendorId());
        reqVo.setCustomerId(platformAuth.getCustomerId());

        PlatformLogisticsRelationshipReqDTO relationshipReqDTO = new PlatformLogisticsRelationshipReqDTO();
        relationshipReqDTO.setServiceProviderId(serviceProviderId);
        relationshipReqDTO.setPlatformCode(platformCode);
        relationshipReqDTO.setLogisticsCode(reqVo.getLogisticsCode());
        // 杂七杂八的面单模板太多，过滤一下，只同步系统配置的承运商
        List<PlatformLogisticsRelationshipDTO> relationshipDTOList = basePlatformLogisticsService.queryPlatformLogistics(relationshipReqDTO);
        Assert.notEmpty(relationshipDTOList, I18nUtils.getMessage("base.check.platformapi.relation.config.notnull",new String[]{platformName, reqVo.getLogisticsCode()}));
        log.info("获取电商平台面单模板并同步到本地库，承运商编码对照关系：" + JSON.toJSONString(relationshipDTOList));

        PlatformMethodService platformMethodService = (PlatformMethodService) SpringBeanLoader.getBean(belongPlatform + "PlatformMethodServiceImpl");
        Assert.notNull(platformMethodService, I18nUtils.getMessage("base.check.platformapi.paltform.method.noimpl",new String[]{belongPlatform}));
        List<BasePlatformPrintTemplateDto> templateDtoList;
        try {
            templateDtoList = platformMethodService.getFaceOrderTemplate(reqVo);
        } catch (SystemException e) {
            e.printStackTrace();
            log.error("获取 {} 平台面单模板获取失败：{}", platformName, e.getMessage());
            throw new SystemException(I18nUtils.getMessage("base.check.platformapi.authcode.config",new String[]{reqVo.getCustomerId(),e.getMessage()}));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(I18nUtils.getMessage("base.check.platformapi.paltform.printtemplate.loadfail",new String[]{platformName, e.getMessage()}));
        }
        Assert.notEmpty(templateDtoList, I18nUtils.getMessage("base.check.platformapi.paltform.printtemplate.isnull"));
        log.info(platformName + "平台下共" + templateDtoList.size() + "条模板！");

        List<String> platformLogisticsCodeList = relationshipDTOList.stream().map(PlatformLogisticsRelationshipDTO::getPlatformLogisticsCode).collect(Collectors.toList());
        List<BasePlatformPrintTemplateDto> needSyncTemplate = templateDtoList.stream().filter(x -> platformLogisticsCodeList.contains(x.getPlatformLogisticsCode())).collect(Collectors.toList());
        log.info("获取电商平台面单模板并同步到本地库，需要同步的面单模板：{}", JSON.toJSONString(needSyncTemplate));

        Assert.notEmpty(needSyncTemplate, I18nUtils.getMessage("base.check.platformapi.logistics.printtemplate.isnull",new String[]{platformName,String.join(",", platformLogisticsCodeList)}));

        List<BasePlatformPrintTemplate> list = new ArrayList<>();
        for (BasePlatformPrintTemplateDto templateDto : needSyncTemplate) {
            BasePlatformPrintTemplate template = new BasePlatformPrintTemplate();
            BeanUtils.copyProperties(templateDto, template);
            if ("null".equals(templateDto.getTemplateId())) {
                template.setTemplateId(null);
            }
            if ("null".equals(templateDto.getTemplateType())) {
                template.setTemplateType(null);
            }
            template.setState(StateEnum.ENABLE.getCode());
            template.setDefaultCreateValue(currentUser);
            list.add(template);
        }
        // 批量保存或者更新电商平台面单模板
        int num = basePlatformPrintTemplateService.batchSaveOrUpdate(list);
        return I18nUtils.getMessage("base.check.platformapi.printtemplate.sync",new String[]{platformName,relationshipDTOList.get(0).getLogisticsName(),String.valueOf(needSyncTemplate.size()),String.valueOf(num)});
    }
}
