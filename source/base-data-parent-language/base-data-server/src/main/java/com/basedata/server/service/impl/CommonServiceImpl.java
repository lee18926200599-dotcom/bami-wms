package com.basedata.server.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.basedata.common.dto.BaseWarehouseCategoryDto;
import com.basedata.common.enums.StateEnum;
import com.basedata.common.util.HuToolUtil;
import com.basedata.server.entity.BaseWarehouseCategory;
import com.basedata.server.mapper.BaseWarehouseCategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * 仓储分类
 */
@Slf4j
@Component
public class CommonServiceImpl {

    @Autowired
    private BaseWarehouseCategoryMapper baseWarehouseCategoryMapper;

    public void recursionDisable(List<BaseWarehouseCategoryDto> list) throws Exception {
        if(CollectionUtil.isNotEmpty(list)){
            BaseWarehouseCategoryDto baseWarehouseCategoryDto = null;
            List<BaseWarehouseCategory> entityList = null;
            LambdaQueryWrapper<BaseWarehouseCategory> lambdaQueryWrapper = null;
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
                    dtoList = new ArrayList<>();
                    dtoList = HuToolUtil.exchange(entityList,BaseWarehouseCategoryDto.class);
                    entityList.forEach(e->{
                        e.setState(StateEnum.DISABLE.getCode());
                        baseWarehouseCategoryMapper.updateById(e);
                    });
                    recursionDisable(dtoList);
                }
            }
        }
    }


}
