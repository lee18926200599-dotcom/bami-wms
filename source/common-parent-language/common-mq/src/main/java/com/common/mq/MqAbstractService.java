package com.common.mq;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.common.base.entity.CurrentUser;
import com.common.framework.redis.RedisUtil;
import com.common.framework.user.FplUserUtil;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RefreshScope
@Component
@Slf4j
public abstract class MqAbstractService<T> {

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private RedisUtil redisUtil;
    public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 重试间隔 10秒,30秒,1分钟,2分钟,3分钟,4分钟,5分钟,10分钟,15分钟,20分钟,30分钟
     */
    public static final List<Long> intervalSeconds= Arrays.asList(10L,30L,60L,120L,180L,240L,300L,600L,900L,1200L,1800L);

    @SneakyThrows
    public void listener(MqMessage mqMessage, Message message, Channel channel){
        String messageId = null;
        Long tag = null;
        T data = (T) mqMessage.getData();
        MqRetryTypeEnum retryType = getRetryType();
        log.info(String.format("%s,%s接收到MQ消息,{}",retryType.getName(),mqMessage.getReferenceNo()),JSON.toJSONString(data));
        try {
            message.getMessageProperties().getMessageId();
            tag = message.getMessageProperties().getDeliveryTag();
            log.info("消息的Tag值为:{}", tag);
            if (StringUtils.isBlank(messageId)) {
                messageId = UUID.randomUUID().toString();
                message.getMessageProperties().setMessageId(messageId);
            }
            log.info("消费的消息Id为:{},当前时间为:{}", messageId, parseStr(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss"));

            if (mqMessage.getUserEntity() != null){
                CurrentUser currentUser = JSON.parseObject(mqMessage.getUserEntity(), CurrentUser.class);
                FplUserUtil.setValue(currentUser);
                //setDefaultUser(currentUser);
            }

            //处理业务
            handle(data);

            Query query = new Query(Criteria.where("warehouseId").is(mqMessage.getWarehouseId()).and("type").is(retryType.name()).and("referenceNo").is(mqMessage.getReferenceNo()));
            mongoTemplate.remove(query,MqRetryLog.class);
        } catch (Exception e) {
            log.error(String.format("%s,%sMQ处理失败",retryType.getName(),mqMessage.getReferenceNo())+",{}",e);

            Query query = new Query(Criteria.where("warehouseId").is(mqMessage.getWarehouseId()).and("type").is(retryType.name()).and("referenceNo").is(mqMessage.getReferenceNo()));
            MqRetryLog one = mongoTemplate.findOne(query, MqRetryLog.class);
            if (one != null){
                Update update = new Update();
                update.inc("retryCount",1);
                update.set("remark",e.getMessage());
                update.set("state", MqRetryStateEnum.CREATE.getCode());
                update.set("updateTime",parseStr(LocalDateTime.now(),DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
                Integer retryCount = one.getRetryCount() + 1;
                if (retryCount != null && retryCount.intValue() < intervalSeconds.size()) {
                    update.set("nextTime", parseStr(LocalDateTime.now().plusSeconds(intervalSeconds.get(retryCount)), DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
                } else {
                    // 默认下次执行时间间隔30s
                    update.set("nextTime", parseStr(LocalDateTime.now().plusSeconds(30L), DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
                }
                Query updateQey = new Query(Criteria.where("_id").is(one.getId()));
                mongoTemplate.updateFirst(updateQey,update,MqRetryLog.class);
            }else {
                MqRetryLog mqRetryLog = new MqRetryLog();
                mqRetryLog.setClassName(data.getClass().getTypeName());
                mqRetryLog.setRetryCount(0);
                mqRetryLog.setWarehouseId(mqMessage.getWarehouseId());
                mqRetryLog.setExchange(mqMessage.getExchange());
                mqRetryLog.setRoutingKey(mqMessage.getRoutingKey());
                mqRetryLog.setState(MqRetryStateEnum.CREATE.getCode());
                mqRetryLog.setMqContent(JSON.toJSONString(mqMessage));
                mqRetryLog.setRemark(e.getMessage());
                mqRetryLog.setMsgId(messageId);
                mqRetryLog.setType(retryType.name());
                mqRetryLog.setReferenceId(mqMessage.getReferenceId());
                mqRetryLog.setReferenceNo(mqMessage.getReferenceNo());
                mqRetryLog.setCreateTime(parseStr(LocalDateTime.now(),DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
                mqRetryLog.setUpdateTime(parseStr(LocalDateTime.now(),DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
                mqRetryLog.setNextTime(parseStr(LocalDateTime.now().plusSeconds(10L),DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
                mongoTemplate.save(mqRetryLog);
                //是否异常处理
                errorHandle(data,e.getMessage());
            }
        }finally {
            //手动确认消息
            channel.basicAck(tag, true);
            //清除threadLocal
            FplUserUtil.remove();
        }
    }

    /**
     * 具体业务实现
     *
     * @param data
     */
    public abstract void handle(T data) throws Exception;

    /**
     * 获取类型
     *
     * @return
     */
    public abstract MqRetryTypeEnum getRetryType();


    /**
     * 异常处理
     *
     * @return
     */
    public void errorHandle(T data,String msg){};


    private void setDefaultUser(CurrentUser currentUser){
        if (!HystrixRequestContext.isCurrentThreadInitialized()) {
            HystrixRequestContext.initializeContext();
        }
        ThreadLocal threadLocal = new ThreadLocal();
        String jsonString = JSON.toJSONString(currentUser);
        threadLocal.set(jsonString);
    }


    public static String parseStr(LocalDateTime localDateTime, String format){
        if (localDateTime == null){
            return null;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return df.format(localDateTime);
    }
}
