package com.usercenter.server.constant.command.enums;

import com.usercenter.common.dto.request.BatchUpdateReq;
import com.usercenter.server.domain.vo.req.UpdateUserCommandReq;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

public enum UpdateUserStrategyBeanEnum implements BeanEnum {


    /**
     * 更新主表数据
     */
    UPDATE_BASEUSER_STRATEGY(1, "updateBaseUserStrategyServiceImpl", "更新主表"),

    /**
     * 更新字表数据
     */
    UPDATE_BASEUSERDETAIL_STATEGY(2, "updateBaseUserDetailStrategyServiceImpl", "更新子表"),

    /**
     * 重置密码更新主表数据
     */
    UPDATE_PASSWORD_STRATEGY(3, "updatePasswordStrategyServiceImpl", "重置密码");


    private Integer id;
    private String bean;
    private String desc;



    UpdateUserStrategyBeanEnum(Integer id, String bean, String desc) {
        this.id = id;
        this.bean = bean;
        this.desc = desc;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String getBean() {
        return bean;
    }

    public String getDesc() {
        return desc;
    }


    public static UpdateUserStrategyBeanEnum getBeanEnum(UpdateUserCommandReq req) {
        if (req.getId() != null && req.getDetailId() != null) {
            throw new IllegalArgumentException("更新策略目前不支持主子表同时更新");
        }
        if (UpdateUserCommandBeanEnum.RESETPASSWORD.getId().equals(req.getOperate())) {
            if(req.getId()==null&&StringUtils.isEmpty(req.getPhone())){
                throw new IllegalArgumentException("重置密码参数错误");
            }
            return UPDATE_PASSWORD_STRATEGY;
        }
        if (req.getId() != null) {
            return UPDATE_BASEUSER_STRATEGY;
        }
        if (req.getDetailId() != null) {
            return UPDATE_BASEUSERDETAIL_STATEGY;
        }
        throw new IllegalArgumentException("未能找更新策略");
    }

    public static UpdateUserStrategyBeanEnum getBeanEnum(BatchUpdateReq batchUpdateReq) {
        if (batchUpdateReq.getGroupId() == null && CollectionUtils.isEmpty(batchUpdateReq.getIds())) {
            throw new IllegalArgumentException("更新依据不能为空");
        }
        if(batchUpdateReq.getGroupId()!=null&&!CollectionUtils.isEmpty(batchUpdateReq.getIds())){
            throw new IllegalArgumentException("不支持双条件更新");
        }
        if (batchUpdateReq.getGroupId() != null) {
            if(!UpdateUserCommandBeanEnum.ENABLE.getId().equals(batchUpdateReq.getOperate())
                    && !UpdateUserCommandBeanEnum.DISABLE.getId().equals(batchUpdateReq.getOperate())) {
                throw new IllegalArgumentException("集团更新只支持启动禁用");
            }
        }
        return UPDATE_BASEUSERDETAIL_STATEGY;
    }


}
