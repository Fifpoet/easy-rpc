package cn.colins.rpc.core.session;

import cn.colins.rpc.core.domain.ServiceInstance;
import cn.colins.rpc.core.executor.impl.BaseEasyRpcExecutor;
import cn.colins.rpc.core.session.defaults.DefaultEasyRpcSession;

import cn.colins.rpc.remote.EasyRpcClient;
import cn.colins.rpc.remote.config.EasyRpcClientConfig;
import cn.colins.rpc.remote.context.EasyRpcRemoteContext;
import cn.colins.rpc.remote.entiy.EasyRpcRequest;
import cn.colins.rpc.remote.exception.EasyRpcRemoteException;
import cn.colins.rpc.remote.handler.EasyRpcClientHandlerInit;
import cn.colins.rpc.remote.handler.server.EasyRpcServerHandler;
import cn.colins.rpc.remote.utils.ThreadPoolUtils;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author colins
 * @Description 远程调用会话工厂  单例
 **/
public class EasyRpcSessionFactory {
    private static final Logger log = LoggerFactory.getLogger(EasyRpcSessionFactory.class);

    private static volatile EasyRpcSessionFactory rpcSessionFactory = null;

    public static EasyRpcSessionFactory getInstance() {
        if (rpcSessionFactory == null) {
            synchronized (EasyRpcSessionFactory.class) {
                if (rpcSessionFactory == null) {
                    rpcSessionFactory = new EasyRpcSessionFactory();
                }
            }
        }
        return rpcSessionFactory;
    }

    private EasyRpcSessionFactory() {

    }

    public EasyRpcSession openSession(String serviceId, EasyRpcRequest rpcRequest, List<ServiceInstance> serviceInstanceList) {

        // 负载、路由、、、、拓展点 SPI机制
        ServiceInstance serviceInstance = serviceInstanceList.get(0);
        // 获取通信管道
        ChannelFuture channelFuture = EasyRpcRemoteContext.getClientChannel(serviceId);
        if (channelFuture == null) {
            channelFuture = getChannelFuture(serviceId, serviceInstance);
        }
        // session里面就是调用执行器执行  可以拓展过滤器、集群策略
        return new DefaultEasyRpcSession(new BaseEasyRpcExecutor(), rpcRequest, serviceInstance, channelFuture);
    }


//    private ChannelFuture getChannelFuture(String serviceId, ServiceInstance serviceInstance) {
//        ChannelFuture clientChannel = EasyRpcRemoteContext.getClientChannel(serviceId);
//        if (clientChannel == null) {
//            synchronized (serviceInstance) {
//                if (clientChannel == null && EasyRpcRemoteContext.getClientChannel(serviceId)==null) {
//                    EasyRpcClientConfig easyRpcClientConfig = new EasyRpcClientConfig();
//                    easyRpcClientConfig.setAddress(serviceInstance.getIp());
//                    easyRpcClientConfig.setPort(serviceInstance.getPort());
//                    EasyRpcClient easyRpcClient = new EasyRpcClient(easyRpcClientConfig, new EasyRpcClientHandlerInit());
//                    ChannelFuture connect = easyRpcClient.connect();
//                    // 异步等待关闭
//                    ThreadPoolUtils.startNettyPool.execute(easyRpcClient);
//                    // 添加缓存
//                    EasyRpcRemoteContext.registerClientChannel(serviceId, connect);
//                    return connect;
//                }
//            }
//        }
//        return clientChannel;
//    }

    private synchronized ChannelFuture getChannelFuture(String serviceId, ServiceInstance serviceInstance) {
        ChannelFuture clientChannel = EasyRpcRemoteContext.getClientChannel(serviceId);
        if (clientChannel == null) {
            EasyRpcClientConfig easyRpcClientConfig = new EasyRpcClientConfig();
            easyRpcClientConfig.setAddress(serviceInstance.getIp());
            easyRpcClientConfig.setPort(serviceInstance.getPort());
            EasyRpcClient easyRpcClient = new EasyRpcClient(easyRpcClientConfig, new EasyRpcClientHandlerInit());
            ChannelFuture connect = easyRpcClient.connect();
            // 异步等待关闭
            ThreadPoolUtils.startNettyPool.execute(easyRpcClient);
            // 添加缓存
            EasyRpcRemoteContext.registerClientChannel(serviceId, connect);
            return connect;
        }
        return clientChannel;
    }

}