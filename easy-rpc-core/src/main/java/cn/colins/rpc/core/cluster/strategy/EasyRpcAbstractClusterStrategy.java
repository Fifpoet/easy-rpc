package cn.colins.rpc.core.cluster.strategy;

import cn.colins.rpc.common.entiy.EasyRpcInvokeInfo;
import cn.colins.rpc.common.entiy.EasyRpcRequest;
import cn.colins.rpc.common.entiy.ServiceInstance;
import cn.colins.rpc.common.exception.EasyRpcException;
import cn.colins.rpc.core.cluster.EasyRpcClusterFactory;
import cn.colins.rpc.core.executor.EasyRpcExecutor;
import cn.colins.rpc.core.executor.impl.BaseEasyRpcExecutor;
import cn.colins.rpc.core.session.EasyRpcSession;
import cn.colins.rpc.remote.context.EasyRpcRemoteContext;
import io.netty.channel.ChannelFuture;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/20
 */
public abstract class EasyRpcAbstractClusterStrategy implements EasyRpcClusterStrategy {

    @Override
    public EasyRpcExecutor clusterStrategy(EasyRpcRequest rpcRequest, List<ServiceInstance> serviceInstanceList, EasyRpcInvokeInfo invokeInfo) throws EasyRpcException, TimeoutException, InterruptedException {
        // 路由
        List<ServiceInstance> routerStrategy = EasyRpcClusterFactory.getRouterStrategy(serviceInstanceList, invokeInfo);

        ServiceInstance serviceInstance = instanceSelect(routerStrategy, invokeInfo);

        // 获取通信管道
        ChannelFuture channelFuture = EasyRpcRemoteContext.chooseClientChannel(serviceInstance);

        return new BaseEasyRpcExecutor();
    }

    protected ServiceInstance instanceSelect(List<ServiceInstance> serviceInstances,EasyRpcInvokeInfo invokeInfo){
        return EasyRpcClusterFactory.getLoadBalanceStrategy(serviceInstances, invokeInfo);
    }
}
