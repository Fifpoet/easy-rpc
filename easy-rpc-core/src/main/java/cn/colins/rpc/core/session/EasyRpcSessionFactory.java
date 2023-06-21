package cn.colins.rpc.core.session;

import cn.colins.rpc.common.entiy.EasyRpcInvokeInfo;
import cn.colins.rpc.core.cluster.EasyRpcClusterFactory;
import cn.colins.rpc.common.entiy.ServiceInstance;
import cn.colins.rpc.core.executor.impl.BaseEasyRpcExecutor;
import cn.colins.rpc.core.session.defaults.DefaultEasyRpcSession;

import cn.colins.rpc.common.entiy.EasyRpcRequest;
import cn.colins.rpc.remote.context.EasyRpcRemoteContext;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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

    public EasyRpcSession openSession(EasyRpcRequest rpcRequest, List<ServiceInstance> serviceInstanceList, EasyRpcInvokeInfo invokeInfo) {

        // 路由
        List<ServiceInstance> routerStrategy = EasyRpcClusterFactory.getRouterStrategy(serviceInstanceList, invokeInfo);

        // session里面就是调用执行器执行  可以拓展过滤器、集群策略
        return new DefaultEasyRpcSession(EasyRpcClusterFactory.getClusterStrategy(invokeInfo), rpcRequest, routerStrategy, invokeInfo);
    }



}