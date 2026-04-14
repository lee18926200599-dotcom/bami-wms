package com.basedata.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.common.framework.execption.SystemException;
import com.common.language.util.I18nUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.basedata.common.dto.BasePlatformNetsideQueryDTO;
import com.basedata.common.dto.BasePlatformNetsideReqDTO;
import com.basedata.common.enums.StateEnum;
import com.basedata.common.util.PageUtil;
import com.basedata.server.entity.BasePlatformNetside;
import com.basedata.server.mapper.BasePlatformNetsideMapper;
import com.basedata.server.query.BasePlatformNetsideQueryVo;
import com.basedata.server.service.BasePlatformNetsideService;
import com.basedata.server.vo.BasePlatformNetsideVo;
import com.common.base.entity.CurrentUser;
import com.common.framework.user.FplUserUtil;
import com.common.util.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 平台网点信息 服务实现类
 * </p>
 */
@Slf4j
@Service
public class BasePlatformNetsideServiceImpl implements BasePlatformNetsideService {
    @Resource
    private BasePlatformNetsideMapper platformNetsideMapper;

    /**
     * 新增
     *
     * @param reqDTO
     * @return
     */
    @Override
    public void save(BasePlatformNetsideReqDTO reqDTO) {
        reqDTO.setId(null);
        BasePlatformNetside entity = new BasePlatformNetside();
        BeanUtils.copyProperties(reqDTO, entity);
        platformNetsideMapper.insert(entity);
    }

    /**
     * 更新
     *
     * @param reqDTO
     * @return
     */
    @Override
    public void update(BasePlatformNetsideReqDTO reqDTO) {
        Assert.notNull(reqDTO.getId(), I18nUtils.getMessage("base.check.common.idnotnull"));
        BasePlatformNetside entity = new BasePlatformNetside();
        BeanUtils.copyProperties(reqDTO, entity);
        platformNetsideMapper.update(entity);
    }

    /**
     * 新增或者更新
     *
     * @param reqDTO
     * @return
     */
    @Override
    public void saveOrUpdate(BasePlatformNetsideReqDTO reqDTO) {
        log.info("保存网点，请求参数{}：", JSON.toJSONString(reqDTO));
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));

        Assert.notNull(reqDTO.getServiceProviderId(), I18nUtils.getMessage("base.check.common.serviceidnotnull"));
//        Assert.notEmpty(reqDTO.getLogisticsCode(), "系统承运商编码不能为空");
        Assert.notEmpty(reqDTO.getPlatformLogisticsCode(), I18nUtils.getMessage("base.check.platoformnetside.ec.logisticscode.notnull"));
        Assert.notEmpty(reqDTO.getPlatformCode(), I18nUtils.getMessage("base.check.platoformnetside.ec.platformcode.notnull"));
//        Assert.notEmpty(reqDTO.getNetsiteCode(), "电商平台网点ID不能为空");

        // 唯一值：仓储服务商ID_承运商ID_平台承运商编码_平台编码_网点编码_网点名称_省_市_区_镇_地址_品牌编码_月结账号_attr1_attr2
        StringBuilder unionParam = new StringBuilder();
        unionParam.append(reqDTO.getServiceProviderId()).append("_").append(reqDTO.getLogisticsId()).append("_")
                .append(reqDTO.getPlatformLogisticsCode()).append("_").append(reqDTO.getPlatformCode()).append("_")
                .append(reqDTO.getNetsiteCode()).append("_").append(reqDTO.getNetsiteName()).append("_")
                .append(reqDTO.getProvince()).append("_").append(reqDTO.getCity()).append("_")
                .append(reqDTO.getArea()).append("_").append(reqDTO.getTown()).append("_")
                .append(reqDTO.getAddressDetail()).append("_").append(reqDTO.getBrandCode()).append("_").append(reqDTO.getSettleAccount())
                .append(reqDTO.getAttrStr1()).append("_").append(reqDTO.getAttrStr2());
        String uniqueNo = MD5Util.md5(unionParam.toString());
        log.info("保存网点，网点唯一值：{}，网点唯一md5值：{}", unionParam, uniqueNo);
        BasePlatformNetsideQueryDTO queryDTO = new BasePlatformNetsideQueryDTO();
        queryDTO.setUniqueNo(uniqueNo);
        List<BasePlatformNetside> list = platformNetsideMapper.findList(queryDTO);
        if (!CollectionUtils.isEmpty(list)) {
            if (list.size() > 1) {
                throw new SystemException(I18nUtils.getMessage("base.check.platoformnetside.netside.exist"));
            }
            BasePlatformNetside entity = list.get(0);
            BeanUtils.copyProperties(reqDTO, entity);
            entity.setDefaultUpdateValue(currentUser);
            // 更新
            platformNetsideMapper.update(entity);
        } else {
            BasePlatformNetside entity = new BasePlatformNetside();
            BeanUtils.copyProperties(reqDTO, entity);
            entity.setUniqueNo(uniqueNo);
            entity.setDefaultCreateValue(currentUser);
            entity.setState(StateEnum.ENABLE.getCode());
            // 新增
            platformNetsideMapper.insert(entity);
        }
    }

    /**
     * 分页查询
     *
     * @param queryVo
     * @return
     */
    @Override
    public PageInfo<BasePlatformNetsideVo> queryPageList(BasePlatformNetsideQueryVo queryVo) throws Exception {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));

        PageHelper.startPage(queryVo.getPageNum(), queryVo.getPageSize());
        BasePlatformNetsideQueryDTO queryDTO = new BasePlatformNetsideQueryDTO();
        BeanUtils.copyProperties(queryVo, queryDTO);
        queryDTO.setServiceProviderId(currentUser.getServiceProviderId());
        List<BasePlatformNetside> list = platformNetsideMapper.findList(queryDTO);
        return PageUtil.convert(new PageInfo<>(list), item -> BeanUtil.toBean(item, BasePlatformNetsideVo.class));
    }

    /**
     * 查询列表（不分页）
     *
     * @param queryVo
     * @return
     */
    @Override
    public List<BasePlatformNetside> queryList(BasePlatformNetsideQueryVo queryVo) {
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        Assert.notNull(currentUser, I18nUtils.getMessage("base.check.user.infonull"));

        BasePlatformNetsideQueryDTO queryDTO = new BasePlatformNetsideQueryDTO();
        BeanUtils.copyProperties(queryVo, queryDTO);
        queryDTO.setServiceProviderId(currentUser.getServiceProviderId());
        return platformNetsideMapper.findList(queryDTO);
    }

}
