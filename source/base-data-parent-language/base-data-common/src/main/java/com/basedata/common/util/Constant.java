package com.basedata.common.util;

import java.util.Random;


public class Constant {

    //redis缓存时间7天，（单位:秒）
    public static final Long REDIS_TIME_OUT = 604800L;

    public static final String BASE_WAREHOUSE = "BASE:WAREHOUSE:";
    //随机时间
    public static Long getRandomTime(){
        Random r = new Random();
        return Long.valueOf(r.nextInt(10000));
    }

}
