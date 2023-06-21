package cn.colins.rpc.core.cluster.strategy;

import cn.colins.rpc.common.entiy.EasyRpcInvokeInfo;
import cn.colins.rpc.common.entiy.EasyRpcRequest;
import cn.colins.rpc.common.entiy.EasyRpcResponse;
import cn.colins.rpc.common.entiy.ServiceInstance;
import cn.colins.rpc.common.exception.EasyRpcException;
import cn.colins.rpc.common.exception.EasyRpcRunException;
import cn.colins.rpc.core.cluster.EasyRpcClusterFactory;
import cn.colins.rpc.core.executor.EasyRpcExecutor;
import cn.colins.rpc.core.executor.impl.BaseEasyRpcExecutor;
import cn.colins.rpc.core.proxy.CglibInvokeProxyFactory;
import cn.colins.rpc.core.session.EasyRpcSession;
import cn.colins.rpc.remote.context.EasyRpcRemoteContext;
import cn.colins.rpc.remote.future.impl.SyncEasyRpcWriteFuture;
import io.netty.channel.ChannelFuture;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;
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
    public EasyRpcExecutor executorProxy(){
        try {
            return CglibInvokeProxyFactory.getExecutorInvokeProxy(EasyRpcExecutor.class,this);
        } catch (Exception e) {
            throw new EasyRpcRunException(e.getMessage());
        }
    }


    protected ServiceInstance instanceSelect(List<ServiceInstance> serviceInstances, EasyRpcInvokeInfo invokeInfo){
        return EasyRpcClusterFactory.getLoadBalanceStrategy(serviceInstances, invokeInfo);
    }
}
