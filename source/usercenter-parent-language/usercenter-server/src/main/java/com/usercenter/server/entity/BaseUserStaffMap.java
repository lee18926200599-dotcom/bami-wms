package com.usercenter.server.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.common.base.enums.StateEnum;
import com.usercenter.common.enums.FlagEnum;
import com.usercenter.common.enums.UserStateEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户人员映射表
 */
@Data
@TableName(value = "base_user_staff_map")
public class BaseUserStaffMap implements Serializable {

    @TableId(value = "id")
    private Long id;
    //用户id
    @TableField(value = "user_id")
    private Long userId;
    //人员id
    @TableField(value = "staff_id")
    private Long staffId;
    //状态1=启用 2=禁用
    @TableField(value = "state")
    private Integer state;
    //是否删除：0未删除， 1 删除
    @TableField(value = "deleted_flag")
    private Integer deletedFlag;
    //创建人id
    @TableField(value = "created_by")
    private Long createdBy;
    //创建人
    @TableField(value = "created_name")
    private String createdName;
    //创建日期
    @TableField(value = "created_date")
    private Date createdDate;
    //修改人id
    @TableField(value = "modified_by")
    private Long modifiedBy;
    //修改人
    @TableField(value = "modified_name")
    private String modifiedName;
    //修改时间
    @TableField(value = "modified_date")
    private Date modifiedDate;


    /**
     * 获取默认示例
     * @return
     */
    public static BaseUserStaffMap defaultInstance(){
        BaseUserStaffMap userStaffMap = new BaseUserStaffMap();
        userStaffMap.setState(UserStateEnum.ENABLE.getCode());
        userStaffMap.setDeletedFlag(FlagEnum.FALSE.getCode());
        return userStaffMap;
    }

    public BaseUserStaffMap() {
    }


    public BaseUserStaffMap(Long userId, Long staffId, Long createdBy, Long modifiedBy) {
        this.userId = userId;
        this.staffId = staffId;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        if (createdBy != null) {
            this.createdDate = new Date();
        }
        if (modifiedBy != null) {
            this.modifiedDate = new Date();
        }
        this.deletedFlag = FlagEnum.FALSE.getCode();
        this.state = StateEnum.ENABLE.getCode();
    }

}