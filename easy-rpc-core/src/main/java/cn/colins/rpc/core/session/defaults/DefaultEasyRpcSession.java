package cn.colins.rpc.core.session.defaults;

import cn.colins.rpc.common.entiy.EasyRpcResultCode;
import cn.colins.rpc.common.entiy.ServiceInstance;
import cn.colins.rpc.core.executor.EasyRpcExecutor;
import cn.colins.rpc.core.session.EasyRpcSession;
import cn.colins.rpc.common.entiy.EasyRpcRequest;
import cn.colins.rpc.common.entiy.EasyRpcResponse;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeoutException;

/**
 * @program: easy-rpc
 * @description: 默认的远程调用会话
 * @author: colins
 * @create: 2023-06-13 22:51
 **/
public class DefaultEasyRpcSession implements EasyRpcSession {
    private static final Logger log = LoggerFactory.getLogger(DefaultEasyRpcSession.class);
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
        try {
            EasyRpcResponse executor = rpcExecutor.executor(future, easyRpcRequest, serviceInstance);
            return executor.getData();
        } catch (TimeoutException e) {
            log.error("异常:{} ", e.getMessage(),e);
            return EasyRpcResponse.error(easyRpcRequest.getRequestId(), EasyRpcResultCode.TIME_OUT,e);
        } catch (Exception e) {
            log.error("异常:{} ", e.getMessage(), e);
            return EasyRpcResponse.error(easyRpcRequest.getRequestId(), e);
        }
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
