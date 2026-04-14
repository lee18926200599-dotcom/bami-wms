package com.common.excle;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 导入导出任务
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ExcelTask implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id;

    /**
     * 服务商id
     */
    private Long serviceProviderId;

    /**
     * 仓库id
     */
    private Long warehouseId;

    /**
     * 仓库code
     */
    private String warehouseCode;

    /**
     * 仓库名称
     */
    private String warehouseName;
    
    /**
     * 类型：1-导入,2-导出
     */
    private Integer type;
    
    /**
     * 状态：0-初始,1-进行中,2-完成,3-失败
     */
    private Integer status;

    /**
     * 源文件
     */
    private String sourceFile;
    
    /**
     * 预估记录数 可能包含空行数据不准确，但是大部分情况时准确的
     */
    private int estimateCount= 0;
    
    /**
     * 实际总记录数 为成功记录数+失败记录数
     */
    private int totalCount;
    
    /**
     * 成功记录数
     */
    private int successCount;
    
    /**
     * 失败记录数
     */
    private int failedCount;
    
    /**
     * 文件名
     */
    private String fileName;
    
    /**
     * 成功文件路径
     */
    private String fileUrl;
    
    /**
     * 失败文件路径
     */
    private String failedFileUrl;
    
    /**
     * 失败消息
     */
    private String failedMessage;
    
    /**
     * 导入开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 导入结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 业务编码 例如user,product,用于区分不同模块的导入
     */
    private Integer businessCode;

    /**
     * 创建人
     */
    private Long createdBy;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 创建人名称
     */
    private String createdName;

    /**
     * 修改人
     */
    private Long modifiedBy;
    /**
     * 修改时间
     */
    private Date modifiedDate;
    /**
     * 修改人名称
     */
    private String modifiedName;

    /**
     * 是否删除
     */
    private Integer deletedFlag;
}
