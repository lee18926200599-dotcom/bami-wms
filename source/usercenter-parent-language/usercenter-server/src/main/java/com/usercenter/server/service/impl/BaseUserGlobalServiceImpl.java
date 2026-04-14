package com.usercenter.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.common.language.util.I18nUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.org.permission.common.org.dto.OrgInfoDto;
import com.usercenter.common.enums.FlagEnum;
import com.usercenter.server.domain.vo.req.globaluser.GlobalUserReq;
import com.usercenter.server.domain.vo.req.globaluser.UpdateGlobalReq;
import com.usercenter.server.domain.vo.resp.PageResult;
import com.usercenter.server.entity.BaseUser;
import com.usercenter.server.entity.BaseUserDetail;
import com.usercenter.server.mapper.BaseUserMapper;
import com.usercenter.server.domain.service.OrgDomainService;
import com.usercenter.server.service.IBaseUserGlobalService;
import com.usercenter.server.utils.UserStaffUtil;
import com.usercenter.server.utils.UserUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 全局用户管理
 */
@Service
public class BaseUserGlobalServiceImpl implements IBaseUserGlobalService {

    /**
     * 用户主表
     */
    @Autowired
    protected BaseUserMapper baseUserMapper;
    @Autowired
    private OrgDomainService orgDomainService;


    @Override
    public PageResult<BaseUser> getGlobalUserList(GlobalUserReq globalUserReq) {
        PageHelper.startPage(globalUserReq.getPageNum(), globalUserReq.getPageSize());
        List<BaseUser> baseUsers = baseUserMapper.selectGlobalList(globalUserReq);
        PageResult<BaseUser> result = new PageResult<>(baseUsers, PageInfo.of(baseUsers));
        return result;
    }


    @Override
    public BaseUser getUserDetail(Long userId) {
        BaseUser baseUser = baseUserMapper.selectBaseUserWithDetailsById(userId);
        if (baseUser == null) {
            throw new RuntimeException(I18nUtils.getMessage("user.check.load.userinfo.fail"));
        }
        List<BaseUserDetail> detailListResult = baseUser.getDetailList();
        //过滤删除的记录
        List<BaseUserDetail> detailList = detailListResult.stream().filter(detail -> detail.getDeletedFlag() == FlagEnum.FALSE.getCode()).collect(Collectors.toList());
        UserStaffUtil.setStaffInfo(detailList);
        if (CollectionUtils.isNotEmpty(detailList)) {
            List<Long> orgIds = detailList.stream().map(u -> u.getGroupId()).distinct().collect(Collectors.toList());
            List<OrgInfoDto> orgInfoDtos = orgDomainService.getOrgByIdList(orgIds);
            Map<Long, String> groupMap = orgInfoDtos.stream().collect(Collectors.toMap(OrgInfoDto::getGroupId, OrgInfoDto::getOrgName, (v1, v2) -> v2));
            detailList.forEach(managerGroupInfo -> {
                managerGroupInfo.setGroupName(groupMap.get(managerGroupInfo.getGroupId()));
            });
        }
        return baseUser;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateUserGlobal(UpdateGlobalReq updateGlobalReq) {
        BaseUser baseUser = BeanUtil.copyProperties(updateGlobalReq, BaseUser.class);
        //更新主表
        baseUser.setModifiedBy(UserUtil.getUserId());
        baseUser.setModifiedDate(new Date());
        this.baseUserMapper.update(baseUser);
        return true;
    }


    /**
     * 生成一个指定元素类型的List, 并把{@code sourceList}的数据复制到新生成的List中的每个元素Bean中。
     * <p>复制使用的方法为{@link org.springframework.beans.BeanUtils#copyProperties(Object, Object)}</p>
     *
     * @param sourceList
     * @param targetClass
     * @param <S>
     * @param <T>
     * @return 返回一个List对象
     */
    public static <S, T> List<T> copyList(List<S> sourceList, Class<T> targetClass) {
        if (null == sourceList) {
            return null;
        }

        List<T> targetList = new ArrayList<>(0);
        if (0 == sourceList.size() || null == targetClass) {
            return targetList;
        }

        try {
            Constructor<T> targetConstructor = targetClass.getConstructor();
            sourceList.stream().forEachOrdered((Object o) -> {
                try {
                    T target = targetConstructor.newInstance();
                    BeanUtil.copyProperties(o, target);
                    targetList.add(target);
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    throw new RuntimeException(I18nUtils.getMessage("user.check.copy.fail") + ":" + e.getMessage());
                }
            });
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(I18nUtils.getMessage("user.check.copy.fail") + ":" + e.getMessage());
        }

        return targetList;
    }
}
