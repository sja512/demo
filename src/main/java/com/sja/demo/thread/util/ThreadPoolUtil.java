package com.sja.demo.thread.util;

import java.util.concurrent.*;

public class ThreadPoolUtil {
    private static final int CORE_POOL_SIZE = 4;
    private static final long KEEP_ALIVE_TIME = 60L;
    private static final int MAXIMUM_POOL_SIZE = 6;
    private static final int CAPACITY = 10;
    private static volatile ThreadPoolExecutor threadPoolExecutor = null;

    private ThreadPoolUtil(){}

    public static ThreadPoolExecutor getThreadPoolExecutor(){
        if(null == threadPoolExecutor){
            synchronized (ThreadPoolUtil.class){
                //当第二个线程访问时 已经不为null了 那么不再创建对象
                if(null == threadPoolExecutor){
                    threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                            new LinkedBlockingQueue<Runnable>(CAPACITY), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
                }
            }
        }
        return threadPoolExecutor;
    }
}
