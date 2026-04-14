
package com.common.log.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.common.log.entity.*;
import com.common.log.service.LogService;
import com.common.log.FieldCompareUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @Description 日志记录
 */

@Component
@Slf4j
public class LogServiceImpl implements LogService {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(10);


    @Override
    public void saveLog(OperateLogDto operateLogDto) {

        CompletableFuture.runAsync(() -> {
            OperateLog operateLog = BeanUtil.toBean(operateLogDto, OperateLog.class);
            operateLog.setModule(operateLogDto.getModule().name());
            operateLog.setOperate(operateLogDto.getOperate().name());
            FieldCompareUtil fieldCompareUtil = new FieldCompareUtil();
            String content;
            if (operateLogDto.getOperate() == LogOperateEnum.LOG_UPDATE) {
                content = fieldCompareUtil.contrastUpdateObj(operateLogDto.getOldBean(), operateLogDto.getNewBean());
            } else {
                content = fieldCompareUtil.contrastAddObj(operateLogDto.getNewBean());
            }
            operateLog.setLogContent(content);
            operateLog.setOperateTime(currentTime());
            mongoTemplate.save(operateLog);
        }, EXECUTOR_SERVICE);

    }

    @Override
    public void saveLogBatch(List<OperateLogDto> list) {
        CompletableFuture.runAsync(() -> {
            if(CollectionUtil.isNotEmpty(list)){
                String currentTime=currentTime();
                list.forEach(item->{
                    OperateLog operateLog = BeanUtil.toBean(item, OperateLog.class);
                    operateLog.setModule(item.getModule().name());
                    operateLog.setOperate(item.getOperate().name());
                    FieldCompareUtil fieldCompareUtil = new FieldCompareUtil();
                    String content;
                    if (item.getOperate() == LogOperateEnum.LOG_UPDATE) {
                        content = fieldCompareUtil.contrastUpdateObj(item.getOldBean(), item.getNewBean());
                    } else {
                        content = fieldCompareUtil.contrastAddObj(item.getNewBean());
                    }
                    operateLog.setLogContent(content);
                    operateLog.setOperateTime(currentTime);
                    mongoTemplate.save(operateLog);
                });
            }

        }, EXECUTOR_SERVICE);
    }

    @Override
    public void saveBusinessLogBatch(List<BusinessOperateLogDto> list) {
        CompletableFuture.runAsync(() -> {
            if(CollectionUtil.isNotEmpty(list)){
                String currentTime=currentTime();
                list.forEach(item->{
                    BusinessOperateLog operateLog = BeanUtil.toBean(item, BusinessOperateLog.class);
                    operateLog.setNode(item.getLogFlowEnum().getCode());
                    operateLog.setOperateTime(currentTime);
                    mongoTemplate.save(operateLog);
                });
            }

        }, EXECUTOR_SERVICE);
    }

    private String currentTime(){
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 设置日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        // 格式化日期
        return now.format(formatter);
    }

    @Override
    public List<OperateLog> getOperateLog(OperateLogQuery operateLogQuery) {
        Query query=new Query();
        Criteria criteria=new Criteria();
        if(operateLogQuery.getReferenceId()!=null){
            criteria.and("referenceId").is(operateLogQuery.getReferenceId());
        }
        if(StringUtils.isNotBlank(operateLogQuery.getModule())){
            criteria.and("module").is(operateLogQuery.getModule());
        }
        query.addCriteria(criteria);
        query.with(Sort.by("operateTime").descending());
        query.limit(100);
        return mongoTemplate.find(query,OperateLog.class);
    }

    @Override
    public List<BusinessOperateLog> getBusinessOperateLog(BusinessOperateLogQuery businessQuery) {
        Query query=new Query();
        Criteria criteria=new Criteria();
        if(businessQuery.getNode()!=null){
            criteria.and("node").is(businessQuery.getNode());
        }
        if(StringUtils.isNotBlank(businessQuery.getBusinessNo())){
            criteria.and("businessNo").is(businessQuery.getBusinessNo());
        }
        if(StringUtils.isNotBlank(businessQuery.getReferenceNo())){
            criteria.and("referenceNo").is(businessQuery.getReferenceNo());
        }
        query.addCriteria(criteria);
        query.with(Sort.by("operateTime").descending());
        query.limit(100);
        return mongoTemplate.find(query,BusinessOperateLog.class);
    }
}

