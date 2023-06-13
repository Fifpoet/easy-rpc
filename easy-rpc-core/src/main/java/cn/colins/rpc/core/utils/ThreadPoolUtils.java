package cn.colins.rpc.core.utils;

import java.util.concurrent.*;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/13
 */
public class ThreadPoolUtils {

    public static Executor subscribeInstancePool = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("Easy-Rpc-nacos-subscribe");
                    return thread;
                }
            });

}
