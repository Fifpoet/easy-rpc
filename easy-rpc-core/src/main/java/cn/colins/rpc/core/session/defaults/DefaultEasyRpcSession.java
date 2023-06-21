package cn.colins.rpc.core.session.defaults;

import cn.colins.rpc.common.entiy.*;
import cn.colins.rpc.common.exception.EasyRpcRunException;
import cn.colins.rpc.core.executor.EasyRpcExecutor;
import cn.colins.rpc.core.session.EasyRpcSession;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
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

    private List<ServiceInstance> serviceInstanceList;

    private EasyRpcInvokeInfo invokeInfo;

    public EasyRpcExecutor rpcExecutor;

    public DefaultEasyRpcSession(EasyRpcExecutor rpcExecutor, EasyRpcRequest rpcRequest, List<ServiceInstance> serviceInstanceList, EasyRpcInvokeInfo invokeInfo) {
        this.easyRpcRequest = rpcRequest;
        this.serviceInstanceList = serviceInstanceList;
        this.invokeInfo = invokeInfo;
        this.rpcExecutor = rpcExecutor;

    }

    @Override
    public Object exec() {
        try {
            EasyRpcResponse executor = rpcExecutor.executor(easyRpcRequest, serviceInstanceList, invokeInfo);
            return executor.getData();
        } catch (Exception e) {
            log.error("异常:{} ", e.getMessage(), e);
            throw new EasyRpcRunException(e.getMessage());
        }
    }

}
