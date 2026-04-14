package com.org.permission.server.org.service.impl;

import com.common.base.entity.CurrentUser;
import com.common.base.enums.StateEnum;
import com.common.framework.user.FplUserUtil;
import com.common.language.util.I18nUtils;
import com.common.util.util.AssertUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.org.permission.server.org.dto.BusinessLineDto;
import com.org.permission.server.org.dto.param.BusinessLineParam;
import com.org.permission.server.org.bean.BaseBusinessLine;
import com.org.permission.server.org.mapper.BusinessLineMapper;
import com.org.permission.server.org.service.BusinessLineService;
import com.org.permission.server.org.util.PageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Service
public class BusinessLineServiceImpl implements BusinessLineService {

    @Autowired
    private BusinessLineMapper businessLineMapper;

    /**
     * 新增业务线
     *
     * @param businessLineDto
     */
    @Override
    public int save(BusinessLineDto businessLineDto) {
        AssertUtils.notNull(businessLineDto, I18nUtils.getMessage("org.common.param.cannot.null"));
        AssertUtils.isNotBlank(businessLineDto.getBusinessLineName(), I18nUtils.getMessage("org.common.param.cannot.null"));
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        BaseBusinessLine businessLine = new BaseBusinessLine();
        businessLine.setBusinessLineName(businessLineDto.getBusinessLineName());
        // 验证名称是否重复
        List<BaseBusinessLine> exist = businessLineMapper.listByParam(businessLine);
        AssertUtils.isEmpty(exist, "name exist");
        businessLine.setRemark(businessLineDto.getRemark());
        businessLine.setCreatedBy(currentUser.getUserId());
        businessLine.setCreatedName(currentUser.getUserName());
        businessLine.setCreatedDate(new Date());
        businessLine.setState(StateEnum.CREATE.getCode());
        BaseBusinessLine last = businessLineMapper.getLastData();
        Long id = 1L;
        if (Objects.nonNull(last)) {
            id = last.getId() + 1;
        }
        businessLine.setBusinessLineCode(getCode(id));
        return businessLineMapper.insert(businessLine);
    }

    /**
     * 修改数据
     *
     * @param businessLineDto
     * @return
     */
    @Override
    public int update(BusinessLineDto businessLineDto) {
        AssertUtils.notNull(businessLineDto, I18nUtils.getMessage("org.common.param.cannot.null"));
        AssertUtils.notNull(businessLineDto.getId(), I18nUtils.getMessage("org.common.param.id.cannot.null"));
        BaseBusinessLine existData = businessLineMapper.load(businessLineDto.getId());
        AssertUtils.notNull(existData, I18nUtils.getMessage("org.common.data.not.exist"));
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        BaseBusinessLine businessLine = new BaseBusinessLine();
        businessLine.setBusinessLineName(businessLineDto.getBusinessLineName());
        // 验证名称是否重复
        List<BaseBusinessLine> exist = businessLineMapper.listByParam(businessLine);
        AssertUtils.isNotTrue(CollectionUtils.isNotEmpty(exist) && exist.stream().anyMatch(item->!item.getId().equals(businessLineDto.getId())), I18nUtils.getMessage("org.common.data.not.exist"));
        businessLine.setId(businessLineDto.getId());
        businessLine.setRemark(businessLineDto.getRemark());
        businessLine.setModifiedBy(currentUser.getUserId());
        businessLine.setModifiedName(currentUser.getUserName());
        businessLine.setModifiedDate(new Date());
        return businessLineMapper.update(businessLine);
    }

    /**
     * 启停用
     *
     * @param businessLineDto
     * @return
     */
    @Override
    public int updateState(BusinessLineDto businessLineDto) {
        AssertUtils.notNull(businessLineDto, I18nUtils.getMessage("org.common.param.cannot.null"));
        AssertUtils.notNull(businessLineDto.getId(), I18nUtils.getMessage("org.common.param.id.cannot.null"));
        AssertUtils.notNull(businessLineDto.getState(), I18nUtils.getMessage("org.common.param.cannot.null"));
        BaseBusinessLine existData = businessLineMapper.load(businessLineDto.getId());
        AssertUtils.notNull(existData, I18nUtils.getMessage("org.common.data.not.exist"));
        CurrentUser currentUser = FplUserUtil.getCurrentUser();
        BaseBusinessLine businessLine = new BaseBusinessLine();
        businessLine.setId(businessLineDto.getId());
        businessLine.setState(businessLineDto.getState());
        businessLine.setModifiedBy(currentUser.getUserId());
        businessLine.setModifiedName(currentUser.getUserName());
        businessLine.setModifiedDate(new Date());
        return businessLineMapper.updateState(businessLine);
    }

    /**
     * 分页查询
     *
     * @param businessLineParam
     * @return
     */
    public PageInfo<BusinessLineDto> getList(BusinessLineParam businessLineParam) {
        PageHelper.startPage(businessLineParam.getPageNum(), businessLineParam.getPageSize()).setOrderBy("id desc");
        List<BaseBusinessLine> baseBusinessLineList = businessLineMapper.getPageList(businessLineParam);
        return PageUtil.convert(new PageInfo<>(baseBusinessLineList), item -> {
            BusinessLineDto businessLineDto = new BusinessLineDto();
            BeanUtils.copyProperties(item, businessLineDto);
            return businessLineDto;
        });
    }

    private String getCode(long id) {
        String prefix = "B";
        if (id > 99999) {
            return prefix + id;
        }
        String code = String.format("%05d", id);
        return prefix + code;
    }

    public List<BaseBusinessLine> getByIdList(List<Long> lineIdList){
        if (CollectionUtils.isEmpty(lineIdList)){
            return new ArrayList<>();
        }
        return businessLineMapper.getByIdList(lineIdList);
    }
}
