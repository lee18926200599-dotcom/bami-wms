package com.basedata.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.basedata.common.dto.DictionaryItemDto;
import com.basedata.common.enums.BooleanEnum;
import com.basedata.common.enums.StateEnum;
import com.basedata.common.query.BaseDictionaryQuery;
import com.basedata.common.util.HuToolUtil;
import com.basedata.common.util.PageUtil;
import com.basedata.common.vo.DictionaryItemVO;
import com.basedata.server.entity.DictionaryItem;
import com.basedata.server.mapper.DictionaryItemMapper;
import com.basedata.server.query.DictionaryItemQuery;
import com.basedata.server.service.DictionaryItemService;
import com.common.framework.constants.RedisConstants;
import com.common.framework.redis.RedisUtil;
import com.common.framework.user.FplUserUtil;
import com.common.language.util.I18nUtils;
import com.common.util.util.AssertUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.usercenter.common.enums.FlagEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
public class DictionaryItemServiceImpl implements DictionaryItemService {

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
    public PageInfo<DictionaryItemVO> queryPageList(DictionaryItemQuery query) throws Exception {
        Integer start = query.getPageNum();
        Integer limit = query.getPageSize();

        PageHelper.startPage(start, limit);
        LambdaQueryWrapper<DictionaryItem> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (null == query.getGroupId() || Objects.equals(query.getGroupId(), 0L)) {
            lambdaQueryWrapper.eq(DictionaryItem::getGroupId, 0L);
        } else {
            lambdaQueryWrapper.in(DictionaryItem::getGroupId, Arrays.asList(0L, query.getGroupId()));
        }
        if (null != query.getItemName()) {
            lambdaQueryWrapper.like(DictionaryItem::getItemName, query.getItemName());
        }
        if (StringUtils.isNotEmpty(query.getItemCode())) {
            lambdaQueryWrapper.eq(DictionaryItem::getItemCode, query.getItemCode());
        }
        if (null != query.getDictionaryCode()) {
            lambdaQueryWrapper.eq(DictionaryItem::getDictionaryCode, query.getDictionaryCode());
        }
        if (null != query.getParentId()) {
            lambdaQueryWrapper.eq(DictionaryItem::getParentId, query.getParentId());
        }
        lambdaQueryWrapper.orderByAsc(DictionaryItem::getSortNum);

        List<DictionaryItem> list = dictionaryItemMapper.selectList(lambdaQueryWrapper);

        PageInfo<DictionaryItemVO> pageInfo = PageUtil.convert(new PageInfo<>(list), item -> {
            DictionaryItemVO dictionaryItemVO = BeanUtil.toBean(item, DictionaryItemVO.class);
            return dictionaryItemVO;
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
    public List<DictionaryItemVO> queryNoPage(DictionaryItemQuery query) throws Exception {
        LambdaQueryWrapper<DictionaryItem> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (null == query.getGroupId() || Objects.equals(query.getGroupId(), 0L)) {
            lambdaQueryWrapper.eq(DictionaryItem::getGroupId, 0L);
        } else {
            lambdaQueryWrapper.in(DictionaryItem::getGroupId, Arrays.asList(0L, query.getGroupId()));
        }
        if (null != query.getItemName()) {
            lambdaQueryWrapper.like(DictionaryItem::getItemName, query.getItemName());
        }
        if (StringUtils.isNotEmpty(query.getItemCode())) {
            lambdaQueryWrapper.eq(DictionaryItem::getItemCode, query.getItemCode());
        }
        if (null != query.getDictionaryCode()) {
            lambdaQueryWrapper.eq(DictionaryItem::getDictionaryCode, query.getDictionaryCode());
        }
        lambdaQueryWrapper.orderByDesc(DictionaryItem::getCreatedDate);
        List<DictionaryItem> list = dictionaryItemMapper.selectList(lambdaQueryWrapper);
        List<DictionaryItemVO> voList = HuToolUtil.exchange(list, DictionaryItemVO.class);
        if (!CollectionUtils.isEmpty(voList) && Objects.equals(query.getTreeFlag(), BooleanEnum.TRUE.getCode())) {
            Map<Long, List<DictionaryItemVO>> map = voList.stream().collect(Collectors.groupingBy(DictionaryItemVO::getParentId));
            return buildItemTree(0L, map);
        }
        return voList;
    }

    private List<DictionaryItemVO> buildItemTree(Long parentId, Map<Long, List<DictionaryItemVO>> map) {
        List<DictionaryItemVO> list = map.get(parentId);
        if (CollectionUtils.isEmpty(list)) {
            return list;
        }
        for (DictionaryItemVO itemVO : list) {
            itemVO.setChildren(buildItemTree(itemVO.getId(), map));
        }
        return list;
    }

    /**
     * 新增
     *
     * @param dictionaryItemDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insert(DictionaryItemDto dictionaryItemDto) throws Exception {
        AssertUtils.isNotBlank(dictionaryItemDto.getDictionaryCode(), I18nUtils.getMessage("base.check.dictionary.select"));
        AssertUtils.isNotBlank(dictionaryItemDto.getItemName(), I18nUtils.getMessage("base.check.dictionary.item.name.notnull"));
        AssertUtils.isNotBlank(dictionaryItemDto.getItemCode(), I18nUtils.getMessage("base.check.dictionary.item.code.notnull"));
        //校验是否已经存在
        LambdaQueryWrapper<DictionaryItem> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DictionaryItem::getDeletedFlag, FlagEnum.FALSE.getCode())
                .eq(DictionaryItem::getDictionaryCode, dictionaryItemDto.getDictionaryCode())
                .and(wrapper -> wrapper.eq(DictionaryItem::getItemCode, dictionaryItemDto.getItemCode())
                        .or()
                        .eq(DictionaryItem::getItemName, dictionaryItemDto.getItemName()));
        if (null == dictionaryItemDto.getGroupId() || Objects.equals(dictionaryItemDto.getGroupId(), 0L)) {
            lambdaQueryWrapper.eq(DictionaryItem::getGroupId, 0L);
        } else {
            lambdaQueryWrapper.in(DictionaryItem::getGroupId, Arrays.asList(0L, dictionaryItemDto.getGroupId()));
        }

        List<DictionaryItem> list = dictionaryItemMapper.selectList(lambdaQueryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            throw new Exception(I18nUtils.getMessage("base.check.dictionary.item.exist"));
        }

        dictionaryItemDto.setState(StateEnum.ENABLE.getCode());
        dictionaryItemDto.setCreatedBy(FplUserUtil.getUserId());
        dictionaryItemDto.setCreatedName(FplUserUtil.getUserName());
        dictionaryItemDto.setCreatedDate(new Date());
        dictionaryItemDto.setDeletedFlag(BooleanEnum.FALSE.getCode());

        DictionaryItem dictionaryItem = BeanUtil.toBean(dictionaryItemDto, DictionaryItem.class);
        int ret = dictionaryItemMapper.insert(dictionaryItem);
        dictionaryItemDto.setId(dictionaryItem.getId());
        dictionaryItemDto.setSortNum(dictionaryItem.getId());
        dictionaryItem.setSortNum(dictionaryItem.getId());
        dictionaryItemMapper.updateById(dictionaryItem);
        Map<String, Object> hmget = new HashMap<>();
        String redisKey = RedisConstants.FPL_DIC_CODE + dictionaryItemDto.getDictionaryCode() + "_" + dictionaryItemDto.getGroupId();
        //添加redis缓存
        if (redisUtil.hasKey(redisKey)) {
            Map<Object, Object> subhmget = redisUtil.hmget(dictionaryItemDto.getDictionaryCode());
            for (Object o : subhmget.keySet()) {
                Object val = subhmget.get(o);
                hmget.put(o.toString(), val);
            }
            hmget.put(dictionaryItemDto.getItemCode(), dictionaryItemDto.getItemName());
        } else {
            hmget.put(dictionaryItemDto.getItemCode(), dictionaryItemDto.getItemName());
        }
        redisUtil.hmset(redisKey, hmget);
        return ret;
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Override
    public DictionaryItemDto queryById(Serializable id) throws Exception {

        DictionaryItem dictionaryItem = dictionaryItemMapper.selectById(id);

        DictionaryItemDto dictionaryItemDto = BeanUtil.toBean(dictionaryItem, DictionaryItemDto.class);

        return dictionaryItemDto;

    }

    /**
     * 更新信息
     *
     * @param dictionaryItemDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateById(DictionaryItemDto dictionaryItemDto) throws Exception {
        DictionaryItem existItem = dictionaryItemMapper.selectById(dictionaryItemDto.getId());
        AssertUtils.isNotNull(existItem, I18nUtils.getMessage("base.check.common.data.noexist"));
        AssertUtils.isTrue(Objects.equals(existItem.getGroupId(), dictionaryItemDto.getGroupId()), I18nUtils.getMessage("base.check.common.data.cannot.modify"));

        LambdaQueryWrapper<DictionaryItem> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DictionaryItem::getDictionaryId, existItem.getDictionaryId());
        if (StringUtils.isNotBlank(dictionaryItemDto.getItemName())) {
            lambdaQueryWrapper.like(DictionaryItem::getItemName, dictionaryItemDto.getItemName());
        }
        if (StringUtils.isNotBlank(dictionaryItemDto.getItemCode())) {
            lambdaQueryWrapper.like(DictionaryItem::getItemCode, dictionaryItemDto.getItemCode());
        }
        List<DictionaryItem> list = dictionaryItemMapper.selectList(lambdaQueryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            // 全局修改，验证同字典下是不是重复，集团级，只验证全局和当前集团的是否重复
            if (Objects.equals(0L, dictionaryItemDto.getGroupId())) {
                AssertUtils.isNotTrue(list.stream().anyMatch(item -> !Objects.equals(item.getId(), existItem.getId()) && Objects.equals(item.getItemCode(), dictionaryItemDto.getItemCode())), "条目编码重复");
                AssertUtils.isNotTrue(list.stream().anyMatch(item -> !Objects.equals(item.getId(), existItem.getId()) && Objects.equals(item.getItemName(), dictionaryItemDto.getItemName())), "条目名称重复");
            } else {
                AssertUtils.isNotTrue(list.stream().anyMatch(item -> Objects.equals(item.getGroupId(), existItem.getGroupId()) && !Objects.equals(item.getId(), existItem.getId()) && Objects.equals(item.getItemCode(), dictionaryItemDto.getItemCode())), "条目编码重复");
                AssertUtils.isNotTrue(list.stream().anyMatch(item -> Objects.equals(item.getGroupId(), existItem.getGroupId()) && !Objects.equals(item.getId(), existItem.getId()) && Objects.equals(item.getItemName(), dictionaryItemDto.getItemName())), "条目名称重复");
            }
        }
        dictionaryItemDto.setModifiedBy(FplUserUtil.getUserId());
        dictionaryItemDto.setModifiedName(FplUserUtil.getUserName());
        dictionaryItemDto.setModifiedDate(new Date());
        DictionaryItem dictionaryItem = BeanUtil.toBean(dictionaryItemDto, DictionaryItem.class);
        dictionaryItem.setGroupId(null);
        int ret = dictionaryItemMapper.updateById(dictionaryItem);
        redisUtil.del(RedisConstants.FPL_DIC_CODE + existItem.getDictionaryCode() + "_" + dictionaryItemDto.getGroupId());
        return ret;
    }


    /**
     * 批量更新,是否加事物由上层决定
     *
     * @param dictionaryItemDtoList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchById(List<DictionaryItemDto> dictionaryItemDtoList) throws Exception {

        DictionaryItem DictionaryItem = null;

        for (DictionaryItemDto dictionaryItemDto : dictionaryItemDtoList) {

            DictionaryItem = BeanUtil.toBean(dictionaryItemDto, DictionaryItem.class);

            dictionaryItemMapper.updateById(DictionaryItem);
        }
    }

    /**
     * 批量保存,是否加事物由上层决定
     *
     * @param dictionaryItemDtoList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertBatch(List<DictionaryItemDto> dictionaryItemDtoList) throws Exception {

        DictionaryItem DictionaryItem = null;

        for (DictionaryItemDto dictionaryItemDto : dictionaryItemDtoList) {

            DictionaryItem = BeanUtil.toBean(dictionaryItemDto, DictionaryItem.class);

            dictionaryItemMapper.insert(DictionaryItem);
        }
    }

    @Override
    public DictionaryItemDto queryDictionaryItemByCode(BaseDictionaryQuery dictionaryInfoQuery) {

        LambdaQueryWrapper<DictionaryItem> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (null == dictionaryInfoQuery.getGroupId() || Objects.equals(dictionaryInfoQuery.getGroupId(), 0L)) {
            lambdaQueryWrapper.eq(DictionaryItem::getGroupId, 0L);
        } else {
            lambdaQueryWrapper.in(DictionaryItem::getGroupId, Arrays.asList(0L, dictionaryInfoQuery.getGroupId()));
        }
        if (StringUtils.isNotBlank(dictionaryInfoQuery.getItemCode())) {
            lambdaQueryWrapper.eq(DictionaryItem::getItemCode, dictionaryInfoQuery.getItemCode());
        }
        if (StringUtils.isNotBlank(dictionaryInfoQuery.getDictCode())) {
            lambdaQueryWrapper.eq(DictionaryItem::getDictionaryCode, dictionaryInfoQuery.getDictCode());
        }

        lambdaQueryWrapper.orderByAsc(DictionaryItem::getSortNum);
        List<DictionaryItem> list = dictionaryItemMapper.selectList(lambdaQueryWrapper);
        DictionaryItemDto dictionaryItemDto = null;
        if (CollectionUtil.isNotEmpty(list)) {
            dictionaryItemDto = new DictionaryItemDto();
            BeanUtil.copyProperties(list.get(0), dictionaryItemDto);
        }
        return dictionaryItemDto;
    }


}
