package cn.colins.rpc.core.executor.impl;

import cn.colins.rpc.core.domain.ServiceInstance;
import cn.colins.rpc.core.executor.EasyRpcExecutor;
import cn.colins.rpc.remote.codec.domain.RpcRemoteMsg;
import cn.colins.rpc.remote.entiy.EasyRpcRequest;
import cn.colins.rpc.remote.entiy.EasyRpcResponse;
import cn.colins.rpc.remote.entiy.EasyRpcResultCode;
import cn.colins.rpc.remote.future.impl.SyncEasyRpcWriteFuture;
import io.netty.channel.ChannelFuture;

import java.util.concurrent.TimeoutException;

/**
 * @program: easy-rpc
 * @description:
 * @author: colins
 * @create: 2023-06-13 22:35
 **/
public class BaseEasyRpcExecutor implements EasyRpcExecutor {


    @Override
    public EasyRpcResponse executor(ChannelFuture channelFuture, EasyRpcRequest easyRpcRequest, ServiceInstance serviceInstance) {
        SyncEasyRpcWriteFuture syncEasyRpcWriteFuture = new SyncEasyRpcWriteFuture(easyRpcRequest.getRequestId(), 0L);
        try {
            return syncEasyRpcWriteFuture.writeAndSync(channelFuture,easyRpcRequest);
        } catch (TimeoutException e) {
            return EasyRpcResponse.error(easyRpcRequest.getRequestId(), EasyRpcResultCode.TIME_OUT,e);
        } catch (Exception e) {
            return EasyRpcResponse.error(easyRpcRequest.getRequestId(), e);
        }
    }
}
