package com.common.util.util;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  数据处理、转换工具类
 */
public class DataTransUtil {

    /**
     * 找List中重复元素
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<T> getDuplicateElementList(List<T> list) {
        return list.stream()
                .collect(Collectors.toMap(e -> e, e -> 1, Integer::sum)) // 获得元素出现频率的 Map，键为元素，值为元素出现的次数
                .entrySet().stream() // Set<Entry>转换为Stream<Entry>
                .filter(entry -> entry.getValue() > 1) // 过滤出元素出现次数大于 1 的 entry
                .map(Map.Entry::getKey) // 获得 entry 的键（重复元素）对应的 Stream
                .collect(Collectors.toList()); // 转化为 List
    }

    /**
     * 计算两个时间差（结束时间-开始时间）
     *
     * @param endDate
     * @param startDate
     * @return
     */
    public static String getDateDiffString(Date endDate, Date startDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - startDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;

        StringBuilder sb = new StringBuilder();
        if (day > 0L) {
            sb.append(day).append("天");
        }
        if (hour > 0L) {
            sb.append(hour).append("小时");
        }
        sb.append(min).append("分钟");
//        return day + "天" + hour + "小时" + min + "分钟";
        return sb.toString();
    }

}
