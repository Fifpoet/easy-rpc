package cn.colins.rpc.remote.future;

import cn.colins.rpc.remote.entiy.EasyRpcRequest;
import cn.colins.rpc.remote.entiy.EasyRpcResponse;
import io.netty.channel.ChannelFuture;

import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

public interface EasyRpcWriteFuture<T> extends Future<T> {

    EasyRpcResponse write(ChannelFuture channelFuture, EasyRpcRequest easyRpcRequest) throws TimeoutException, InterruptedException;

    void setResponse(EasyRpcResponse response);

    T getResponse();

    boolean isTimeout();

    long getTimeout();

}
