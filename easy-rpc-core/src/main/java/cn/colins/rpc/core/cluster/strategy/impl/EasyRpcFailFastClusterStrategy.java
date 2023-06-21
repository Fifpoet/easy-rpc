package cn.colins.rpc.core.cluster.strategy.impl;

import cn.colins.rpc.common.entiy.EasyRpcInvokeInfo;
import cn.colins.rpc.common.entiy.EasyRpcRequest;
import cn.colins.rpc.common.entiy.EasyRpcResponse;
import cn.colins.rpc.common.entiy.ServiceInstance;
import cn.colins.rpc.common.exception.EasyRpcException;
import cn.colins.rpc.core.cache.EasyRpcInstanceCache;
import cn.colins.rpc.core.cluster.EasyRpcClusterFactory;
import cn.colins.rpc.core.cluster.strategy.EasyRpcAbstractClusterStrategy;
import cn.colins.rpc.core.cluster.strategy.EasyRpcClusterStrategy;
import cn.colins.rpc.core.executor.EasyRpcExecutor;
import cn.colins.rpc.core.session.defaults.DefaultEasyRpcSession;
import cn.colins.rpc.remote.context.EasyRpcRemoteContext;
import cn.hutool.core.collection.CollectionUtil;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/20
 */
public class EasyRpcFailFastClusterStrategy extends EasyRpcAbstractClusterStrategy{
    private static final Logger log = LoggerFactory.getLogger(EasyRpcFailFastClusterStrategy.class);

    @Override
    public void errorHandler(Exception e, EasyRpcRequest rpcRequest) throws Exception {
        log.warn("Easy-Rpc fail fast");
        throw e;
    }

    @Override
    public EasyRpcResponse invoker(EasyRpcRequest rpcRequest, List<ServiceInstance> serviceInstanceList, EasyRpcInvokeInfo invokeInfo) {
        return null;
    }



}
