package com.basedata.server.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.basedata.server.entity.AreaInfo;
import com.basedata.server.mapper.AreaInfoMapper;
import com.basedata.server.service.AreaInfoService;

import com.basedata.common.dto.AreaInfoDto;
import com.basedata.common.query.AreaInfoQuery;
import com.basedata.common.util.HuToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 *  行政区域表
 */
@Slf4j
@Component
public class AreaInfoServiceImpl extends ServiceImpl<AreaInfoMapper,AreaInfo> implements AreaInfoService {

    @Autowired
    private AreaInfoMapper areaInfoMapper;

    /**
     * 查询不分页
     *
     * @param query
     * @return
     * @throws Exception
     */
    @Override
    public List<AreaInfoDto> queryNoPage(AreaInfoQuery query)throws Exception  {
        if(StringUtils.isNotBlank(query.getParentIds()) ){
            List<String> list = Arrays.asList(query.getParentIds().split(","));
            List<Long> longList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                longList.add(Long.valueOf(list.get(i)));
            }
            query.setParentIdList(longList);
        }

        List<AreaInfo> list = areaInfoMapper.selectByParentIdList(query);

        List<AreaInfoDto> dtoList = HuToolUtil.exchange(list,AreaInfoDto.class);

        return dtoList;

    }


    /**
     * 新增
     *
     * @param AreaInfoDto
     * @return
     */
    @Override
    public Integer insert(AreaInfoDto AreaInfoDto) throws Exception{

        AreaInfo areaInfo = BeanUtil.toBean(AreaInfoDto,AreaInfo.class);

        int ret=areaInfoMapper.insert(areaInfo);

        AreaInfoDto.setId(areaInfo.getId());

        return ret;
    }

    /**
    * 查询详情
    *
    * @param  id
    * @return
    */
    @Override
    public AreaInfoDto queryById(Serializable id) throws Exception{

        AreaInfo areaInfo = areaInfoMapper.selectById(id);

        AreaInfoDto areaInfoDto = BeanUtil.toBean(areaInfo,AreaInfoDto.class);

        return areaInfoDto;

    }

    /**
     * 更新信息
     *
     * @param areaInfoDto
     * @return
     */
    @Override
    public Integer updateById(AreaInfoDto areaInfoDto) throws Exception{

        AreaInfo areaInfo=BeanUtil.toBean(areaInfoDto,AreaInfo.class);

        int ret= areaInfoMapper.updateById(areaInfo);

        return ret;
    }

    /**
    * 批量更新,是否加事物由上层决定
     *
    * @param areaInfoDtoList
    * @throws Exception
    */
    @Override
    public void updateBatchById(List<AreaInfoDto> areaInfoDtoList) throws Exception {

        AreaInfo areaInfo=null;

        for (AreaInfoDto areaInfoDto : areaInfoDtoList) {

           areaInfo=BeanUtil.toBean(areaInfoDto,AreaInfo.class);

           areaInfoMapper.updateById(areaInfo);
        }
    }

    /**
     * 批量保存,是否加事物由上层决定
     *
     * @param areaInfoDtoList
     * @throws Exception
     */
    @Override
    public void insertBatch(List<AreaInfoDto> areaInfoDtoList) throws Exception {

        AreaInfo areaInfo=null;

        for (AreaInfoDto areaInfoDto : areaInfoDtoList) {

            areaInfo=BeanUtil.toBean(areaInfoDto,AreaInfo.class);

            areaInfoMapper.insert(areaInfo);
        }
    }



}
