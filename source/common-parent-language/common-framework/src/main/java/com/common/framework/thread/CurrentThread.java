package com.common.framework.thread;

import lombok.Data;

@Data
public class CurrentThread extends Thread{

    private ThreadLocal<String> threadLocal;


}
