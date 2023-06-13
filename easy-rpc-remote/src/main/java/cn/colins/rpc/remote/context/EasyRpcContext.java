package cn.colins.rpc.remote.context;


import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/12
 */
public class EasyRpcContext {

    private static Map<String, Channel> CLIENT_CHANNEL_CACHE = new ConcurrentHashMap<>(16);

    public static void registerClientChannel(String serverId, Channel channel) {
        CLIENT_CHANNEL_CACHE.put(serverId, channel);
    }

    public static Channel getClientChannel(String serverId) {
        return CLIENT_CHANNEL_CACHE.get(serverId);
    }
}
