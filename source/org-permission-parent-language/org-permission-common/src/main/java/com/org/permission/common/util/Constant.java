package com.org.permission.common.util;

import org.springframework.beans.factory.annotation.Value;

import java.util.Random;

public class Constant {

    //redis缓存时间
    public static final Long REDIS_TIME_OUT = 3600000L;

    /**
     * 基础数据服务
     */
    public static final String ORG_SERVER_NAME = "zzz-org-server";

    //随机时间
    public static Long getRandomTime(){
        Random r = new Random();
        return Long.valueOf(r.nextInt(10000));
    }

}
