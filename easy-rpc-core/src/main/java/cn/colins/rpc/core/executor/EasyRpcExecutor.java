package cn.colins.rpc.core.executor;

import cn.colins.rpc.common.entiy.ServiceInstance;
import cn.colins.rpc.common.entiy.EasyRpcRequest;
import cn.colins.rpc.common.entiy.EasyRpcResponse;
import io.netty.channel.ChannelFuture;

import java.util.concurrent.TimeoutException;

/**
 * @program: easy-rpc
 * @description:
 * @author: colins
 * @create: 2023-06-13 22:32
 **/
public interface EasyRpcExecutor {

    EasyRpcResponse executor(ChannelFuture channelFuture, EasyRpcRequest easyRpcRequest, ServiceInstance serviceInstance) throws TimeoutException, InterruptedException;

}