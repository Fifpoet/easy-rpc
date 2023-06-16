package cn.colins.rpc.core.session.defaults;

import cn.colins.rpc.core.domain.ServiceInstance;
import cn.colins.rpc.core.executor.EasyRpcExecutor;
import cn.colins.rpc.core.session.EasyRpcSession;
import cn.colins.rpc.common.entiy.EasyRpcRequest;
import cn.colins.rpc.common.entiy.EasyRpcResponse;
import io.netty.channel.ChannelFuture;

/**
 * @program: easy-rpc
 * @description: 默认的远程调用会话
 * @author: colins
 * @create: 2023-06-13 22:51
 **/
public class DefaultEasyRpcSession implements EasyRpcSession {

    private EasyRpcRequest easyRpcRequest;

    private ServiceInstance serviceInstance;

    private ChannelFuture future;

    public EasyRpcExecutor rpcExecutor;

    public DefaultEasyRpcSession(EasyRpcExecutor rpcExecutor, EasyRpcRequest rpcRequest, ServiceInstance serviceInstance, ChannelFuture future) {
        this.easyRpcRequest = rpcRequest;
        this.rpcExecutor = rpcExecutor;
        this.serviceInstance = serviceInstance;
        this.future = future;
    }

    @Override
    public Object exec() {
        EasyRpcResponse executor = rpcExecutor.executor(future, easyRpcRequest, serviceInstance);
        return executor.getData();
    }

    @Override
    public EasyRpcRequest getRpcRequest() {
        return easyRpcRequest;
    }

    @Override
    public ServiceInstance getServiceInstance() {
        return serviceInstance;
    }
}
