package com.basedata.server.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
/**
 * comments: 行政区域表实体类型
 */
@Data
@TableName(value = "area_info")
public class AreaInfo {


    @TableId("id")
    @TableField(value = "id")
    private Long id;
    //名称
    @TableField(value = "area_name")
    private String areaName;
    //简称
    @TableField(value = "short_name")
    private String shortName;
    //助记码
    @TableField(value = "mnemonic_code")
    private String mnemonicCode;
    //邮政编码
    @TableField(value = "postcode")
    private String postcode;
    //区域级别
    @TableField(value = "level")
    private Integer level;
    //上级行政区域id
    @TableField(value = "parent_id")
    private Long parentId;
    //国家区域id
    @TableField(value = "country_id")
    private Integer countryId;
    //启用状态 0=停用 1=启用
    @TableField(value = "state")
    private Integer state;
    //名称拼音
    @TableField(value = "spell")
    private String spell;
    //名称首字母
    @TableField(value = "initial")
    private String initial;
    //排序码
    @TableField(value = "sort_code")
    private Integer sortCode;
    //删除标识 0=未删除 1=删除
    @TableField(value = "deleted_flag")
    private Integer deletedFlag;
    //创建人id
    @TableField(value = "created_by")
    private Long createdBy;
    //创建人
    @TableField(value = "created_name")
    private String createdName;
    //
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
}
