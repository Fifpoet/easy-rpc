package cn.colins.rpc.remote.context;


import cn.colins.rpc.remote.EasyRpcClient;
import cn.colins.rpc.remote.future.impl.SyncEasyRpcWriteFuture;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/12
 */
public class EasyRpcRemoteContext {
    private final static Logger log= LoggerFactory.getLogger(EasyRpcRemoteContext.class);

    /**
     * 客户端调用实例通道缓存
     */
    private static Map<String, ChannelFuture> CLIENT_CHANNEL_CACHE = new ConcurrentHashMap<>(16);

    /**
     * 生产者Bean缓存 beanName:bean
     */
    private static Map<String, Object> PRODUCER_BEAN_CACHE = new ConcurrentHashMap<>(32);

    /**
     * 生产者Bean缓存 beanName:beanType
     * 因为一个bean接口可能存在多个实现 所以需要找到type——name都一致的对象
     */
    private static Map<String, String> PRODUCER_BEAN_TYPE_CACHE = new ConcurrentHashMap<>(32);

    /**
     * 每次请求都有查找性能上的损耗，这里再加一层缓存， 防止重复查找
     */
    private static Map<String, Object> PRODUCER_BEAN_FIND_CACHE = new ConcurrentHashMap<>(32);


    /**
     * netty同步响应缓存
     */
    public static Map<String, SyncEasyRpcWriteFuture> CLIENT_REQUEST_CACHE = new ConcurrentHashMap<>(1024);

    public static void registerClientChannel(String instanceInfo, ChannelFuture channel) {
        CLIENT_CHANNEL_CACHE.put(instanceInfo, channel);
    }

    public static ChannelFuture getClientChannel(String instanceInfo) {
        return CLIENT_CHANNEL_CACHE.get(instanceInfo);
    }

    public static void registerProducerCache(String beanName, Object producerBen) {
        PRODUCER_BEAN_CACHE.put(beanName, producerBen);
    }

    public static Object getProducerBean(String beanName, String beanType) {
        Object bean = PRODUCER_BEAN_FIND_CACHE.get(beanName + "_" + beanType);
        if(bean==null){
            // 先通过type找 如果有多个再找beanName
            for (Map.Entry<String, String> entry : PRODUCER_BEAN_TYPE_CACHE.entrySet()) {
                // type 存在或者 只有一个则直接返回
                if (entry.getValue().contains(beanType) && entry.getValue().split("_").length == 1) {
                    return PRODUCER_BEAN_CACHE.get(entry.getKey());
                }
            }
            // 到这就说明type 不存在 或者 有多个 此时就只能通过name获取
            bean = PRODUCER_BEAN_CACHE.get(beanName);
            if(bean!=null){
                PRODUCER_BEAN_FIND_CACHE.put(beanName + "_" + beanType, bean);
            }
        }
        return bean;
    }

    public static void registerProducerTypeCache(String beanName, String beanType) {
        String types = PRODUCER_BEAN_TYPE_CACHE.get(beanName);
        PRODUCER_BEAN_TYPE_CACHE.put(beanName, StrUtil.isEmpty(types) ? beanType : types + "_" + beanType);
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
