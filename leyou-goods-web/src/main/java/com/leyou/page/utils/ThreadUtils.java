package com.leyou.page.utils;

import java.util.concurrent.*;

public class ThreadUtils {
    //设置核心池大小
    static int corePoolSize = 10;

    //设置线程池最大能接受多少线程
    static int maximumPoolSize = 100;
    //当前线程数大于corePoolSize、小于maximumPoolSize时，超出corePoolSize的线程数的生命周期
    static long keepActiveTime = 200;

    //设置时间单位，秒
    static TimeUnit timeUnit = TimeUnit.SECONDS;

    //设置线程池缓存队列的排队策略为FIFO，并且指定缓存队列大小为5
    static BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(5);

    //创建ThreadPoolExecutor线程池对象，并初始化该对象的各种参数


    public static void execute(Runnable runnable) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepActiveTime, timeUnit,workQueue);
        executor.submit(runnable);
    }
}