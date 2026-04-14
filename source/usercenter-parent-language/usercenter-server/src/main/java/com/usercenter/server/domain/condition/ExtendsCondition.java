package com.usercenter.server.domain.condition;

import com.google.common.collect.Maps;
import com.usercenter.server.common.exception.WarnException;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 加强的数据库查询条件
 */
public class ExtendsCondition {

    /**
     * 开始时间
     */
    private static final String START_TIME = "START_TIME";

    /**
     * 结束时间
     */
    private static final String END_TIME = "END_TIME";

    /**
     * 不等于字段
     */
    protected Map<String, Object> notEqualMap;

    /**
     * 模糊匹配的字段
     */
    protected Map<String, String> likeMap;

    /**
     * in查询的字段
     */
    protected Map<String, List> inMap;

    /**
     * 时间段查询
     */
    protected Map<String, Map<String, Long>> timeMap;


    public Map<String, Object> getNotEqualMap() {
        return notEqualMap;
    }

    /**
     * 模糊匹配的字段
     * @return map
     */
    public Map<String, String> getLikeMap() {
        return likeMap;
    }

    /**
     * 时间范围的字段
     * @return map
     */
    public Map<String, Map<String, Long>> getTimeMap() {
        return timeMap;
    }

    public Map<String, List> getInMap() {
        return inMap;
    }

    /**
     * 添加不等于条件
     * @param column 字段名
     * @param value  不等于的值
     */
    public void addNotEqual(String column, Object value) {
        filterValue(value);
        if(notEqualMap==null){
            notEqualMap = Maps.newHashMap();
        }
        notEqualMap.put(column, value);
    }

    /**
     * 添加模糊匹配条件
     * @param column 字段名
     * @param value  模糊匹配的值
     */
    public void addLike(String column, String value) {
        filterValue(value);
        if(likeMap==null){
            likeMap = Maps.newHashMap();
        }
        likeMap.put(column, value);
    }

    /**
     * 添加不等于条件
     * @param column 字段名
     * @param value  不等于的值
     */
    public void addIn(String column, Object ... value) {
        filterValue(value);
        if(inMap==null){
            inMap = Maps.newHashMap();
        }
        if(!ObjectUtils.isEmpty(value)) {
            inMap.put(column, Arrays.asList(value));
        }

    }

    /**
     * 添加时间范围条件
     * @param column    时间范围的字段
     * @param startTime 时间范围-开始时间
     * @param endTime   时间范围-结束时间
     */
    public void addTime(String column, Long startTime, Long endTime) {
        if(timeMap==null){
            timeMap = Maps.newHashMap();
        }
        Map<String, Long> betweenMap = null;
        if(betweenMap==null){
            betweenMap = Maps.newHashMap();
        }
        if (startTime != null) {
            betweenMap.put(START_TIME, startTime);
        }
        if (endTime != null) {
            betweenMap.put(END_TIME, endTime);
        }
        timeMap.put(column, betweenMap);
    }

    /**
     * 防止sql注入
     * @param value
     */
    private void filterValue(Object value){
        if(value instanceof String){
            String sql = value.toString();
            if(sql.contains("delete ")||sql.contains("or ")||sql.contains("and ")||sql.contains(";")){
                throw new WarnException("发现sql注入！！！");
            }
        }
    }
}
