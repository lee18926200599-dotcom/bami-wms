package com.basedata.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.basedata.common.dto.DictionaryInfoDto;
import com.basedata.common.enums.BooleanEnum;
import com.basedata.common.query.BaseDictionaryBatchQuery;
import com.basedata.common.query.BaseDictionaryQuery;
import com.basedata.common.util.PageUtil;
import com.basedata.common.vo.BaseBatchDictionaryVo;
import com.basedata.common.vo.BaseDictionaryVo;
import com.basedata.server.entity.DictionaryInfo;
import com.basedata.server.entity.DictionaryItem;
import com.basedata.server.mapper.DictionaryInfoMapper;
import com.basedata.server.mapper.DictionaryItemMapper;
import com.basedata.server.query.DictionaryInfoQuery;
import com.basedata.server.service.DictionaryInfoService;
import com.common.base.entity.CurrentUser;
import com.common.framework.constants.RedisConstants;
import com.common.framework.redis.RedisUtil;
import com.common.framework.user.FplUserUtil;
import com.common.language.util.I18nUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
public class DictionaryInfoServiceImpl implements DictionaryInfoService {

    @Resource
    private DictionaryInfoMapper dictionaryInfoMapper;

    @Resource
    private DictionaryItemMapper dictionaryItemMapper;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 分页查询
     *
     * @param query
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo<DictionaryInfoDto> queryPageList(DictionaryInfoQuery query) throws Exception {
        Integer start = query.getPageNum();
        Integer limit = query.getPageSize();

        PageHelper.startPage(start, limit);
        LambdaQueryWrapper<DictionaryInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();;
        if (null != query.getDictName()) {
            lambdaQueryWrapper.and(i->i.like(DictionaryInfo::getDictName, query.getDictName()).or().like(DictionaryInfo::getDictCode, query.getDictName()));
        }
        if (StringUtils.isNotEmpty(query.getDictCode())) {
            lambdaQueryWrapper.eq(DictionaryInfo::getDictCode, query.getDictCode());
        }
        if (null != query.getState()) {
            lambdaQueryWrapper.eq(DictionaryInfo::getState, query.getState());
        }
        lambdaQueryWrapper.orderByDesc(DictionaryInfo::getCreatedDate);
        List<DictionaryInfo> list = dictionaryInfoMapper.selectList(lambdaQueryWrapper);
        PageInfo<DictionaryInfoDto> pageInfo = PageUtil.convert(new PageInfo<>(list), item -> {
            DictionaryInfoDto dictionaryInfoDto = BeanUtil.toBean(item, DictionaryInfoDto.class);
            return dictionaryInfoDto;
        });

        return pageInfo;
    }

    /**
     * 查询不分页
     *
     * @param query
     * @return
     * @throws Exception
     */
    @Override
    public List<DictionaryInfoDto> queryNoPage(DictionaryInfoQuery query, Integer pageSize) throws Exception {
        return null;
    }



    /**
     * 新增
     *
     * @param dictionaryInfoDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insert(DictionaryInfoDto dictionaryInfoDto) throws Exception {
        String dictCode = dictionaryInfoDto.getDictCode().toUpperCase();
        dictionaryInfoDto.setDictCode(dictCode);
        //校验是否已经存在DictCode
        LambdaQueryWrapper<DictionaryInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DictionaryInfo::getDictCode, dictionaryInfoDto.getDictCode());
        List<DictionaryInfo> list = dictionaryInfoMapper.selectList(lambdaQueryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            throw new Exception(I18nUtils.getMessage("base.check.dictionary.code.exist"));
        }

        dictionaryInfoDto.setCreatedBy(FplUserUtil.getUserId());
        dictionaryInfoDto.setCreatedName(FplUserUtil.getUserName());
        dictionaryInfoDto.setCreatedDate(new Date());
        dictionaryInfoDto.setDeletedFlag(BooleanEnum.FALSE.getCode());
        DictionaryInfo dictionaryInfo = BeanUtil.toBean(dictionaryInfoDto, DictionaryInfo.class);
        Integer ret = dictionaryInfoMapper.insert(dictionaryInfo);
        dictionaryInfoDto.setId(dictionaryInfo.getId());
        return ret;
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictionaryInfoDto queryById(Serializable id) throws Exception {

        DictionaryInfo dictionaryInfo = dictionaryInfoMapper.selectById(id);

        DictionaryInfoDto dictionaryInfoDto = BeanUtil.toBean(dictionaryInfo, DictionaryInfoDto.class);

        return dictionaryInfoDto;

    }

    /**
     * 更新信息
     *
     * @param dictionaryInfoDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateById(DictionaryInfoDto dictionaryInfoDto) throws Exception {
        DictionaryInfo DictionaryInfo = BeanUtil.toBean(dictionaryInfoDto, DictionaryInfo.class);
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        DictionaryInfo.setModifiedBy(currentUser.getUserId());
        DictionaryInfo.setModifiedName(currentUser.getUserName());
        DictionaryInfo.setModifiedDate(new Date());
        Integer ret = dictionaryInfoMapper.updateById(DictionaryInfo);
        return ret;
    }

    /**
     * 批量更新,是否加事物由上层决定
     *
     * @param dictionaryInfoDtoList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchById(List<DictionaryInfoDto> dictionaryInfoDtoList) throws Exception {

        DictionaryInfo dictionaryInfo = null;

        for (DictionaryInfoDto dictionaryInfoDto : dictionaryInfoDtoList) {

            dictionaryInfo = BeanUtil.toBean(dictionaryInfoDto, DictionaryInfo.class);

            dictionaryInfoMapper.updateById(dictionaryInfo);
        }
    }

    /**
     * 批量保存,是否加事物由上层决定
     *
     * @param dictionaryInfoDtoList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertBatch(List<DictionaryInfoDto> dictionaryInfoDtoList) throws Exception {

        DictionaryInfo DictionaryInfo = null;

        for (DictionaryInfoDto dictionaryInfoDto : dictionaryInfoDtoList) {

            DictionaryInfo = BeanUtil.toBean(dictionaryInfoDto, DictionaryInfo.class);

            dictionaryInfoMapper.insert(DictionaryInfo);
        }
    }


    @Override
    public List<BaseDictionaryVo> queryDictionary(BaseDictionaryQuery dictionaryInfoQuery) {
        LambdaQueryWrapper<DictionaryItem> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DictionaryItem::getDictionaryCode, dictionaryInfoQuery.getDictCode());
        if (StringUtils.isNotEmpty(dictionaryInfoQuery.getItemCode())) {
            lambdaQueryWrapper.eq(DictionaryItem::getItemCode, dictionaryInfoQuery.getItemCode());
        }
        lambdaQueryWrapper.eq(DictionaryItem::getDeletedFlag, BooleanEnum.FALSE.getCode());
        if (Objects.equals(dictionaryInfoQuery.getGroupId(), 0L)) {
            lambdaQueryWrapper.eq(DictionaryItem::getGroupId, dictionaryInfoQuery.getGroupId());
        } else {
            lambdaQueryWrapper.in(DictionaryItem::getGroupId, Arrays.asList(0L, dictionaryInfoQuery.getGroupId()));
        }
        lambdaQueryWrapper.orderByAsc(DictionaryItem::getSortNum);
        List<DictionaryItem> list = dictionaryItemMapper.selectList(lambdaQueryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<BaseDictionaryVo> voList = list.stream().map(x -> {
            BaseDictionaryVo baseDictionaryVo = new BaseDictionaryVo();
            baseDictionaryVo.setDictCode(x.getItemCode());
            baseDictionaryVo.setDictName(x.getItemName());
            baseDictionaryVo.setMnemonicCode(x.getMnemonicCode());
            return baseDictionaryVo;
        }).collect(Collectors.toList());
        return voList;
    }

    @Override
    public List<BaseBatchDictionaryVo> queryDictionaryList(BaseDictionaryBatchQuery dictionaryInfoQuery) {
        LambdaQueryWrapper<DictionaryItem> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(DictionaryItem::getDictionaryCode, dictionaryInfoQuery.getDictCodeList());
        lambdaQueryWrapper.eq(DictionaryItem::getDeletedFlag, BooleanEnum.FALSE.getCode());
        if (null == dictionaryInfoQuery.getGroupId() || Objects.equals(dictionaryInfoQuery.getGroupId(), 0L)) {
            lambdaQueryWrapper.eq(DictionaryItem::getGroupId, 0L);
        } else {
            lambdaQueryWrapper.in(DictionaryItem::getGroupId, Arrays.asList(0L, dictionaryInfoQuery.getGroupId()));
        }
        if (null != dictionaryInfoQuery.getState()) {
            lambdaQueryWrapper.eq(DictionaryItem::getState, dictionaryInfoQuery.getState());
        }
        lambdaQueryWrapper.orderByDesc(DictionaryItem::getSortNum);
        List<DictionaryItem> list = dictionaryItemMapper.selectList(lambdaQueryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        List<String> ids = list.stream().map(DictionaryItem::getDictionaryCode).distinct().collect(Collectors.toList());
        LambdaQueryWrapper<DictionaryInfo> dictionaryInfoLambdaQueryWrapper=new LambdaQueryWrapper<>();
        dictionaryInfoLambdaQueryWrapper.in(DictionaryInfo::getDictCode, ids);
        List<DictionaryInfo> dictionaryInfos = dictionaryInfoMapper.selectList(dictionaryInfoLambdaQueryWrapper);

        Map<String, List<DictionaryItem>> map = list.stream().collect(Collectors.groupingBy(DictionaryItem::getDictionaryCode));

        List<BaseBatchDictionaryVo> voList = new ArrayList<>();
        Integer treeFlag = dictionaryInfoQuery.getTreeFlag();
        for (String dictCode : map.keySet()) {
            List<DictionaryItem> itemList = map.get(dictCode);
            Map<Long, List<DictionaryItem>> itemMap = itemList.stream().collect(Collectors.groupingBy(DictionaryItem::getParentId));
            Map<String, Object> subMap = itemList.stream().collect(Collectors.toMap(DictionaryItem::getItemCode,DictionaryItem::getItemName,(v1,v2)->v1));
            List<BaseBatchDictionaryVo.BaseBatchDictionaryDetailVo> voItemList = new ArrayList<>();
            if (Objects.equals(treeFlag,BooleanEnum.TRUE.getCode())) {
                voItemList = itemList.stream().filter(x -> Objects.equals(0L, x.getParentId()) || Objects.isNull(x.getParentId())).map(x -> {
                    BaseBatchDictionaryVo.BaseBatchDictionaryDetailVo baseDictionaryVo = buildItemVo(x, itemMap);
                    return baseDictionaryVo;

                }).collect(Collectors.toList());
            } else {
                voItemList = itemList.stream().map(x -> {
                    BaseBatchDictionaryVo.BaseBatchDictionaryDetailVo baseDictionaryVo = new BaseBatchDictionaryVo.BaseBatchDictionaryDetailVo();
                    baseDictionaryVo.setItemCode(x.getItemCode());
                    baseDictionaryVo.setItemName(x.getItemName());
                    return baseDictionaryVo;
                }).collect(Collectors.toList());
            }


            BaseBatchDictionaryVo dictionaryVo = new BaseBatchDictionaryVo();
            dictionaryVo.setDictCode(dictCode);
            DictionaryInfo dictionaryInfo = dictionaryInfos.stream().filter(x -> x.getDictCode().equals(dictCode)).findFirst().orElse(null);
            dictionaryVo.setDictDesc(dictionaryInfo.getDictName());
            dictionaryVo.setDetailVoList(voItemList);
            voList.add(dictionaryVo);

            redisUtil.hmset(RedisConstants.FPL_DIC_CODE+dictCode+"_"+dictionaryInfoQuery.getGroupId(), subMap);
        }

        return voList;
    }

    private BaseBatchDictionaryVo.BaseBatchDictionaryDetailVo buildItemVo(DictionaryItem item, Map<Long, List<DictionaryItem>> itemMap) {
        BaseBatchDictionaryVo.BaseBatchDictionaryDetailVo baseDictionaryVo = new BaseBatchDictionaryVo.BaseBatchDictionaryDetailVo();
        baseDictionaryVo.setItemCode(item.getItemCode());
        baseDictionaryVo.setItemName(item.getItemName());
        baseDictionaryVo.setChildren(new ArrayList<>());
        List<DictionaryItem> itemList = itemMap.get(item.getId());
        if (!CollectionUtils.isEmpty(itemList)) {
            for (DictionaryItem dictionaryItem : itemList) {
                baseDictionaryVo.getChildren().add(buildItemVo(dictionaryItem, itemMap));
            }
        }
        return baseDictionaryVo;
    }
}
