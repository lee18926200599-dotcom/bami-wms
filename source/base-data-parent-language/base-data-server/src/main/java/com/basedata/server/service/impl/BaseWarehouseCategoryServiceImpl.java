package com.basedata.server.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.basedata.common.dto.BaseWarehouseCategoryDto;
import com.basedata.common.enums.BooleanEnum;
import com.basedata.common.enums.StateEnum;
import com.basedata.common.query.WarehouseCategoryQuery;
import com.basedata.common.util.HuToolUtil;
import com.basedata.common.vo.BaseWarehouseCategoryVO;
import com.basedata.server.entity.BaseWarehouse;
import com.basedata.server.entity.BaseWarehouseCategory;
import com.basedata.server.mapper.BaseWarehouseCategoryMapper;
import com.basedata.server.mapper.BaseWarehouseMapper;
import com.basedata.server.service.BaseWarehouseCategoryService;
import com.common.base.entity.CurrentUser;
import com.common.framework.execption.BizException;
import com.common.framework.number.BaseNoGenerateUtil;
import com.common.framework.number.DocumentTypeEnum;
import com.common.framework.user.FplUserUtil;
import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * 仓储分类
 */
@Slf4j
@Component
public class BaseWarehouseCategoryServiceImpl implements BaseWarehouseCategoryService {

    @Resource
    private BaseWarehouseCategoryMapper baseWarehouseCategoryMapper;
    @Autowired
    private BaseWarehouseMapper baseWarehouseMapper;


    /**
     * 查询不分页
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<BaseWarehouseCategoryVO> queryNoPage(String source)throws Exception  {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        BaseWarehouse baseWarehouse = baseWarehouseMapper.selectById(currentUser.getWarehouseId());
        LambdaQueryWrapper<BaseWarehouseCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BaseWarehouseCategory::getParentId,0);
        lambdaQueryWrapper.eq(BaseWarehouseCategory::getDeletedFlag, BooleanEnum.FALSE.getCode());
        if(source.equals("0")){
            lambdaQueryWrapper.eq(BaseWarehouseCategory::getState, StateEnum.ENABLE.getCode());
        }
        lambdaQueryWrapper.eq(BaseWarehouseCategory::getServiceProviderId, baseWarehouse.getServiceProviderId());
        lambdaQueryWrapper.eq(BaseWarehouseCategory::getWarehouseId,currentUser.getWarehouseId());
        lambdaQueryWrapper.eq(BaseWarehouseCategory::getGroupId, baseWarehouse.getGroupId());
        lambdaQueryWrapper.orderByDesc(BaseWarehouseCategory::getSort);
        List<BaseWarehouseCategory> list = baseWarehouseCategoryMapper.selectList(lambdaQueryWrapper);
        List<BaseWarehouseCategoryVO> VOList = null;
        if(CollectionUtil.isNotEmpty(list)){
            VOList = new ArrayList<>();
            VOList = HuToolUtil.exchange(list,BaseWarehouseCategoryVO.class);
            recursion(VOList,source);
        }
        return VOList;

    }
    public void recursion(List<BaseWarehouseCategoryVO> list, String source) throws Exception {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        if(CollectionUtil.isNotEmpty(list)) {
            BaseWarehouseCategoryVO baseWarehouseCategoryVO = null;
            LambdaQueryWrapper<BaseWarehouseCategory> lambdaQueryWrapper;
            List<BaseWarehouseCategory> entityList = null;
            for (int i = 0; i < list.size(); i++) {
                baseWarehouseCategoryVO = new BaseWarehouseCategoryVO();
                entityList = new ArrayList<>();
                baseWarehouseCategoryVO = list.get(i);
                //查询当前分类是否存在下级分类
                lambdaQueryWrapper = new LambdaQueryWrapper<>();
                //查询下级组织列表
                lambdaQueryWrapper.eq(BaseWarehouseCategory::getParentId, baseWarehouseCategoryVO.getId());
                lambdaQueryWrapper.eq(BaseWarehouseCategory::getDeletedFlag, BooleanEnum.FALSE.getCode());
                if (source.equals("0")) {
                    lambdaQueryWrapper.eq(BaseWarehouseCategory::getState, StateEnum.ENABLE.getCode());
                }
                lambdaQueryWrapper.eq(BaseWarehouseCategory::getServiceProviderId, currentUser.getServiceProviderId());
                lambdaQueryWrapper.orderByDesc(BaseWarehouseCategory::getSort);
                entityList = baseWarehouseCategoryMapper.selectList(lambdaQueryWrapper);
                List<BaseWarehouseCategoryVO> VOList = null;
                if (CollectionUtil.isNotEmpty(entityList)) {
                    VOList = new ArrayList<>();
                    VOList = HuToolUtil.exchange(entityList, BaseWarehouseCategoryVO.class);
                    for (BaseWarehouseCategoryVO e : VOList) {
                        e.setParentName(baseWarehouseCategoryVO.getCategoryName());
                    }
                    ;
                    baseWarehouseCategoryVO.setWarehouseCategoryList(VOList);
                    recursion(VOList, source);
                }
            }

        }
    }


    /**
     * 新增
     *
     * @param baseWarehouseCategoryDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insert(BaseWarehouseCategoryDto baseWarehouseCategoryDto) throws Exception {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));
        //根据仓库查询仓储服务商
        BaseWarehouse baseWarehouse = baseWarehouseMapper.selectById(currentUser.getWarehouseId());
        baseWarehouseCategoryDto.setServiceProviderId(baseWarehouse.getServiceProviderId());
        baseWarehouseCategoryDto.setGroupId(baseWarehouse.getGroupId());
        baseWarehouseCategoryDto.setWarehouseId(currentUser.getWarehouseId());
        baseWarehouseCategoryDto.setWarehouseCode(currentUser.getWarehouseCode());
        baseWarehouseCategoryDto.setWarehouseName(currentUser.getWarehouseName());
        baseWarehouseCategoryDto.setWarehouseId(currentUser.getWarehouseId());
        baseWarehouseCategoryDto.setCategoryCode(BaseNoGenerateUtil.generateCode(baseWarehouseCategoryDto.getServiceProviderId(), DocumentTypeEnum.WAREHOUSE_CATEGORY.getCode()));
        //校验仓储分类是否已存在
        LambdaQueryWrapper<BaseWarehouseCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BaseWarehouseCategory::getCategoryCode, baseWarehouseCategoryDto.getCategoryCode());
        List<BaseWarehouseCategory> list = new ArrayList<>();
        list = baseWarehouseCategoryMapper.selectList(lambdaQueryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            throw new BizException(I18nUtils.getMessage("base.check.warehousecategory.code.exist"));
        }
        if (null == baseWarehouseCategoryDto.getParentId()) {
            baseWarehouseCategoryDto.setParentId(0L);
        }
        //校验同级别下名称是否重复
        lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BaseWarehouseCategory::getCategoryName, baseWarehouseCategoryDto.getCategoryName());
        lambdaQueryWrapper.eq(BaseWarehouseCategory::getWarehouseId, baseWarehouseCategoryDto.getWarehouseId());
        lambdaQueryWrapper.eq(BaseWarehouseCategory::getGroupId, baseWarehouseCategoryDto.getGroupId());
        lambdaQueryWrapper.eq(BaseWarehouseCategory::getServiceProviderId, baseWarehouseCategoryDto.getServiceProviderId());
        lambdaQueryWrapper.eq(BaseWarehouseCategory::getDeletedFlag, BooleanEnum.FALSE.getCode());
        list = baseWarehouseCategoryMapper.selectList(lambdaQueryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            throw new BizException(I18nUtils.getMessage("base.check.warehousecategory.name.exist"));
        }
        baseWarehouseCategoryDto.setDeletedFlag(BooleanEnum.FALSE.getCode());
        baseWarehouseCategoryDto.setCreatedBy(FplUserUtil.getUserId());
        baseWarehouseCategoryDto.setCreatedName(FplUserUtil.getUserName());
        baseWarehouseCategoryDto.setCreatedDate(new Date());
        baseWarehouseCategoryDto.setDeletedFlag(BooleanEnum.FALSE.getCode());
        BaseWarehouseCategory baseWarehouseCategory = BeanUtil.toBean(baseWarehouseCategoryDto, BaseWarehouseCategory.class);

        int ret = baseWarehouseCategoryMapper.insert(baseWarehouseCategory);

        baseWarehouseCategoryDto.setId(baseWarehouseCategory.getId());
        baseWarehouseCategory.setSort(Integer.valueOf(String.valueOf(baseWarehouseCategory.getId())));
        baseWarehouseCategoryMapper.updateById(baseWarehouseCategory);
        return ret;
    }

    /**
    * 查询详情
    *
    * @param  id
    * @return
    */
    @Override
    public BaseWarehouseCategoryDto queryById(Serializable id) throws Exception{

        BaseWarehouseCategory baseWarehouseCategory = baseWarehouseCategoryMapper.selectById(id);

        BaseWarehouseCategoryDto baseWarehouseCategoryDto = BeanUtil.toBean(baseWarehouseCategory,BaseWarehouseCategoryDto.class);

        return baseWarehouseCategoryDto;

    }

    /**
     * 更新信息
     *
     * @param baseWarehouseCategoryDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateById(BaseWarehouseCategoryDto baseWarehouseCategoryDto) throws Exception{
        //校验同级别下名称是否重复
        LambdaQueryWrapper<BaseWarehouseCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BaseWarehouseCategory::getCategoryName,baseWarehouseCategoryDto.getCategoryName());
        lambdaQueryWrapper.eq(BaseWarehouseCategory::getParentId,baseWarehouseCategoryDto.getParentId());
        List<BaseWarehouseCategory> list = baseWarehouseCategoryMapper.selectList(lambdaQueryWrapper);
        if(CollectionUtil.isNotEmpty(list) && !list.get(0).getId().equals(baseWarehouseCategoryDto.getId())){
            throw new BizException(I18nUtils.getMessage("base.check.warehousecategory.samelevel.name.exist"));
        }
        BaseWarehouseCategory byId = baseWarehouseCategoryMapper.selectById(baseWarehouseCategoryDto.getId());

        if (!baseWarehouseCategoryDto.getCategoryCode().equals(byId.getCategoryCode())){
            throw new BizException(I18nUtils.getMessage("base.check.warehousecategory.code.cannot.modify"));
        }

        if (byId.getState() != StateEnum.NOT_ENABLE.getCode()){
            if (!byId.getParentId().equals(baseWarehouseCategoryDto.getParentId())){
                throw new BizException(I18nUtils.getMessage("base.check.warehousecategory.uplevel.cannot.modify"));
            }
        }
        
        baseWarehouseCategoryDto.setModifiedBy(FplUserUtil.getUserId());
        baseWarehouseCategoryDto.setModifiedName(FplUserUtil.getUserName());
        baseWarehouseCategoryDto.setModifiedDate(new Date());
        BaseWarehouseCategory baseWarehouseCategory=BeanUtil.toBean(baseWarehouseCategoryDto,BaseWarehouseCategory.class);

        int ret= baseWarehouseCategoryMapper.updateById(baseWarehouseCategory);

        return ret;
    }


    /**
    * 批量更新,是否加事物由上层决定
     *
    * @param baseWarehouseCategoryDtoList
    * @throws Exception
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchById(List<BaseWarehouseCategoryDto> baseWarehouseCategoryDtoList) throws Exception {

        BaseWarehouseCategory baseWarehouseCategory=null;

        for (BaseWarehouseCategoryDto baseWarehouseCategoryDto : baseWarehouseCategoryDtoList) {

           baseWarehouseCategory=BeanUtil.toBean(baseWarehouseCategoryDto,BaseWarehouseCategory.class);

           baseWarehouseCategoryMapper.updateById(baseWarehouseCategory);
        }
    }

    /**
     * 批量保存,是否加事物由上层决定
     *
     * @param baseWarehouseCategoryDtoList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertBatch(List<BaseWarehouseCategoryDto> baseWarehouseCategoryDtoList) throws Exception {

        BaseWarehouseCategory baseWarehouseCategory=null;

        for (BaseWarehouseCategoryDto baseWarehouseCategoryDto : baseWarehouseCategoryDtoList) {

            baseWarehouseCategory=BeanUtil.toBean(baseWarehouseCategoryDto,BaseWarehouseCategory.class);

            baseWarehouseCategoryMapper.insert(baseWarehouseCategory);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestMessage enableOrDisable(BaseWarehouseCategoryDto baseWarehouseCategoryDto) throws Exception {
        //查询自己
        BaseWarehouseCategory bwc = baseWarehouseCategoryMapper.selectById(baseWarehouseCategoryDto.getId());
        if(StateEnum.ENABLE.getCode().equals(baseWarehouseCategoryDto.getState())){
            //校验父级是否启用
            if (bwc.getParentId() != null && bwc.getParentId() > 0) {
                BaseWarehouseCategory parent = baseWarehouseCategoryMapper.selectById(bwc.getParentId());
                if (Objects.nonNull(parent) && !StateEnum.ENABLE.getCode().equals(parent.getState())) {
                   return RestMessage.error(I18nUtils.getMessage("base.check.warehousecategory.uplevel.enable"));
                }
            }
            BaseWarehouseCategory baseWarehouseCategory=BeanUtil.toBean(baseWarehouseCategoryDto,BaseWarehouseCategory.class);
            baseWarehouseCategoryMapper.updateById(baseWarehouseCategory);
        }else if(StateEnum.DISABLE.getCode().equals(baseWarehouseCategoryDto.getState())){
            BaseWarehouseCategory baseWarehouseCategory=BeanUtil.toBean(baseWarehouseCategoryDto,BaseWarehouseCategory.class);
            baseWarehouseCategoryMapper.updateById(baseWarehouseCategory);
            BaseWarehouseCategoryDto bwcDto=BeanUtil.toBean(baseWarehouseCategoryDto,BaseWarehouseCategoryDto.class);
            List<BaseWarehouseCategoryDto> dtoList = new ArrayList<>();
            dtoList.add(bwcDto);
            recursionDisable(dtoList,null);
            baseWarehouseCategoryMapper.updateList(dtoList);
        }

        return RestMessage.doSuccess(I18nUtils.getMessage("base.success"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestMessage delete(BaseWarehouseCategoryDto baseWarehouseCategoryDto) throws Exception {
        //根据id查询
        BaseWarehouseCategory bwc = baseWarehouseCategoryMapper.selectById(baseWarehouseCategoryDto.getId());
        if(StateEnum.NOT_ENABLE.getCode() != bwc.getState()){
            throw new BizException(I18nUtils.getMessage("base.check.common.state.cannot.delete"));
        }
        baseWarehouseCategoryDto.setDeletedFlag(BooleanEnum.TRUE.getCode());
        List<BaseWarehouseCategoryDto> dtoList = new ArrayList<>();
        dtoList.add(baseWarehouseCategoryDto);
        recursionDelete(dtoList,null);
        baseWarehouseCategoryMapper.updateList(dtoList);
        return RestMessage.doSuccess(I18nUtils.getMessage("base.success"));
    }

    @Override
    public RestMessage move(Long id, Long id2) throws Exception {
        List<BaseWarehouseCategoryDto> list = new ArrayList<>();
        BaseWarehouseCategoryDto baseWarehouseCategoryDto = queryById(id);
        BaseWarehouseCategoryDto baseWarehouseCategoryDto2 = queryById(id2);
        int sort1 = baseWarehouseCategoryDto.getSort();
        int sort2 = baseWarehouseCategoryDto2.getSort();
        baseWarehouseCategoryDto.setSort(sort2);
        baseWarehouseCategoryDto2.setSort(sort1);
        list.add(baseWarehouseCategoryDto);
        list.add(baseWarehouseCategoryDto2);
        updateBatchById(list);
        return RestMessage.doSuccess(I18nUtils.getMessage("base.success"));
    }

    @Override
    public List<Long> queryCategoryListByParentId(Long id) throws Exception {
        List<Long> list = new ArrayList<>();
        BaseWarehouseCategory baseWarehouseCategory = baseWarehouseCategoryMapper.selectById(id);
        BaseWarehouseCategoryDto bwcDto = BeanUtil.toBean(baseWarehouseCategory,BaseWarehouseCategoryDto.class);
        List<BaseWarehouseCategoryDto> dtoList = new ArrayList<>();
        dtoList.add(bwcDto);
        recursionDisable(dtoList,null);
        dtoList.forEach(e->{
            list.add(e.getId());
        });
        return list;
    }

    @Override
    public BaseWarehouseCategoryDto queryCategoryById(Long id) {
        BaseWarehouseCategory baseWarehouseCategory =  baseWarehouseCategoryMapper.selectById(id);
        BaseWarehouseCategoryDto baseWarehouseCategoryDto = null;
        if(null != baseWarehouseCategory){
            baseWarehouseCategoryDto = new BaseWarehouseCategoryDto();
            baseWarehouseCategoryDto = BeanUtil.toBean(baseWarehouseCategory,BaseWarehouseCategoryDto.class);
        }
        return baseWarehouseCategoryDto;
    }

    public void recursionDisable(List<BaseWarehouseCategoryDto> list,List<BaseWarehouseCategoryDto> resDtoList) throws Exception {
        if(CollectionUtil.isNotEmpty(list)){
            BaseWarehouseCategoryDto baseWarehouseCategoryDto = null;
            LambdaQueryWrapper<BaseWarehouseCategory> lambdaQueryWrapper;
            List<BaseWarehouseCategory> entityList = null;
            for (int i = 0; i < list.size(); i++) {
                baseWarehouseCategoryDto = new BaseWarehouseCategoryDto();
                entityList = new ArrayList<>();
                baseWarehouseCategoryDto = list.get(i);
                //查询当前分类是否存在下级分类
                lambdaQueryWrapper = new LambdaQueryWrapper<>();
                //查询下级组织列表
                lambdaQueryWrapper.eq(BaseWarehouseCategory::getParentId, baseWarehouseCategoryDto.getId());
                lambdaQueryWrapper.eq(BaseWarehouseCategory::getState, StateEnum.ENABLE.getCode());
                entityList = baseWarehouseCategoryMapper.selectList(lambdaQueryWrapper);
                List<BaseWarehouseCategoryDto> dtoList = null;
                if(CollectionUtil.isNotEmpty(entityList)){
                    entityList.forEach(e->{
                        e.setState(StateEnum.DISABLE.getCode());
                    });
                    dtoList = new ArrayList<>();
                    dtoList = HuToolUtil.exchange(entityList,BaseWarehouseCategoryDto.class);
                    list.addAll(dtoList);
                    recursionDisable(dtoList,list);
                }
            }
        }
    }
    public void recursionDelete(List<BaseWarehouseCategoryDto> list,List<BaseWarehouseCategoryDto> resDtoList) throws Exception {
        if(CollectionUtil.isNotEmpty(list)){
            BaseWarehouseCategoryDto baseWarehouseCategoryDto = null;
            LambdaQueryWrapper<BaseWarehouseCategory> lambdaQueryWrapper ;
            List<BaseWarehouseCategory> entityList = null;
            for (int i = 0; i < list.size(); i++) {
                baseWarehouseCategoryDto = new BaseWarehouseCategoryDto();
                entityList = new ArrayList<>();
                baseWarehouseCategoryDto = list.get(i);
                //查询当前分类是否存在下级分类
                lambdaQueryWrapper = new LambdaQueryWrapper<>();
                //查询下级组织列表
                lambdaQueryWrapper.eq(BaseWarehouseCategory::getParentId, baseWarehouseCategoryDto.getId());
                lambdaQueryWrapper.eq(BaseWarehouseCategory::getDeletedFlag, BooleanEnum.FALSE.getCode());
                entityList = baseWarehouseCategoryMapper.selectList(lambdaQueryWrapper);
                List<BaseWarehouseCategoryDto> dtoList = null;
                if(CollectionUtil.isNotEmpty(entityList)){
                    entityList.forEach(e->{
                        e.setDeletedFlag(BooleanEnum.TRUE.getCode());
                    });
                    dtoList = new ArrayList<>();
                    dtoList = HuToolUtil.exchange(entityList,BaseWarehouseCategoryDto.class);
                    list.addAll(dtoList);
                    recursionDisable(dtoList,list);
                }
            }
        }
    }

    /**
     * 根据条件查询分类列表
     * @param query
     * @return
     */
    @Override
    public List<BaseWarehouseCategory> queryCategoryList(WarehouseCategoryQuery query) {
        LambdaQueryWrapper<BaseWarehouseCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(BaseWarehouseCategory::getId, query.getIdList());
        lambdaQueryWrapper.eq(BaseWarehouseCategory::getDeletedFlag, BooleanEnum.FALSE.getCode());
        lambdaQueryWrapper.orderByAsc(BaseWarehouseCategory::getSort);
        return baseWarehouseCategoryMapper.selectList(lambdaQueryWrapper);
    }

}
