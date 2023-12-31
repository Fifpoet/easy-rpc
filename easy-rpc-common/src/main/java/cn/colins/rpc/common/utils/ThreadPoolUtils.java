package cn.colins.rpc.common.utils;

import java.util.concurrent.*;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/13
 */
public class ThreadPoolUtils {

    /**
     * @Author czl
     * @Description Nacos 订阅监听使用
     **/
    public static Executor subscribeInstancePool = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("Easy-Rpc-nacos-subscribe");
                    return thread;
                }
            });


    /**
     * @Author czl
     * @Description 给netty服务启动使用
     **/
    public static Executor startNettyPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("Easy-Rpc-netty-remote");
                    return thread;
                }
            });

    /**
     * @Author czl
     * @Description 重试任务线程池
     **/
    public static Executor retryTaskPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("Easy-Rpc-retry-task");
                    return thread;
                }
            });


    /**
     * @Author czl
     * @Description 给netty服务端处理使用
     **/
    public static Executor nettyServerAsyncHandler = new ThreadPoolExecutor(4, 10,
            60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(1024*10),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("Easy-Rpc-Server-handler");
                    return thread;
                }
            });

    /**
     * @Author czl
     * @Description 给netty客户端处理使用
     **/
    public static Executor nettyClientAsyncHandler = new ThreadPoolExecutor(4, 10,
            60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(1024*10),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("Easy-Rpc-Client-handler");
                    return thread;
                }
            });
}
