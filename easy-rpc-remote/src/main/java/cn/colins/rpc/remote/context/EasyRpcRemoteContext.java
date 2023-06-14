package cn.colins.rpc.remote.context;


import cn.colins.rpc.remote.future.impl.SyncEasyRpcWriteFuture;
import io.netty.channel.ChannelFuture;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/12
 */
public class EasyRpcRemoteContext {

    /**
     * 客户端调用实例通道缓存
     */
    private static Map<String, ChannelFuture> CLIENT_CHANNEL_CACHE = new ConcurrentHashMap<>(16);

    /**
     * 生产者Bean缓存
     */
    private static Map<String, Object> PRODUCER_BEAN_CACHE = new ConcurrentHashMap<>(32);

    /**
     * netty同步响应缓存
     */
    private static Map<String, SyncEasyRpcWriteFuture> CLIENT_REQUEST_CACHE = new ConcurrentHashMap<>(1024);

    public static void registerClientChannel(String serverId, ChannelFuture channel) {
        CLIENT_CHANNEL_CACHE.put(serverId, channel);
    }

    public static ChannelFuture getClientChannel(String serverId) {
        return CLIENT_CHANNEL_CACHE.get(serverId);
    }

    public static void registerProducerCache(String beanName, Object producerBen) {
        PRODUCER_BEAN_CACHE.put(beanName, producerBen);
    }

    public static Object getProducerBean(String beanName) {
        return PRODUCER_BEAN_CACHE.get(beanName);
    }

    public static void addRequestCache(String requestId, SyncEasyRpcWriteFuture writeFuture) {
        CLIENT_REQUEST_CACHE.put(requestId, writeFuture);
    }

    public static SyncEasyRpcWriteFuture getRequestCache(String requestId) {
        return CLIENT_REQUEST_CACHE.get(requestId);
    }

    public static SyncEasyRpcWriteFuture removeRequestCache(String requestId) {
        return CLIENT_REQUEST_CACHE.remove(requestId);
    }
}
