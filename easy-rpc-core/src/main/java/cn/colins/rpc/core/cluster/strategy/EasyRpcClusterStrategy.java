package cn.colins.rpc.core.cluster.strategy;

import cn.colins.rpc.common.entiy.EasyRpcInvokeInfo;
import cn.colins.rpc.common.entiy.EasyRpcRequest;
import cn.colins.rpc.common.entiy.EasyRpcResponse;
import cn.colins.rpc.common.entiy.ServiceInstance;
import cn.colins.rpc.common.exception.EasyRpcException;
import cn.colins.rpc.core.executor.EasyRpcExecutor;
import cn.colins.rpc.core.session.EasyRpcSession;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @program: easy-rpc
 * @description:
 * @author: colins
 * @create: 2023-06-20 23:06
 **/
public interface EasyRpcClusterStrategy {

    EasyRpcExecutor executorProxy();

    void errorHandler(Exception e, EasyRpcRequest rpcRequest) throws Exception;

    EasyRpcResponse invoker(EasyRpcRequest rpcRequest, List<ServiceInstance> serviceInstanceList, EasyRpcInvokeInfo invokeInfo);

}