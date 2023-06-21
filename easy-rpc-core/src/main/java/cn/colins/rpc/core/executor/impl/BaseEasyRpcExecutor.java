package cn.colins.rpc.core.executor.impl;

import cn.colins.rpc.common.entiy.ServiceInstance;
import cn.colins.rpc.core.executor.EasyRpcExecutor;
import cn.colins.rpc.remote.context.EasyRpcRemoteContext;
import cn.colins.rpc.common.entiy.EasyRpcRequest;
import cn.colins.rpc.common.entiy.EasyRpcResponse;
import cn.colins.rpc.remote.future.impl.SyncEasyRpcWriteFuture;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeoutException;

/**
 * @program: easy-rpc
 * @description:
 * @author: colins
 * @create: 2023-06-13 22:35
 **/
public class BaseEasyRpcExecutor implements EasyRpcExecutor {
    private static final Logger log = LoggerFactory.getLogger(BaseEasyRpcExecutor.class);

    @Override
    public EasyRpcResponse executor(ChannelFuture channelFuture, EasyRpcRequest easyRpcRequest, ServiceInstance serviceInstance) throws TimeoutException, InterruptedException {
        SyncEasyRpcWriteFuture syncEasyRpcWriteFuture = new SyncEasyRpcWriteFuture(easyRpcRequest.getRequestId(), 0L);
        try {
            EasyRpcResponse easyRpcResponse = syncEasyRpcWriteFuture.write(channelFuture, easyRpcRequest);
            log.info("收到数据:{}", JSONObject.toJSON(easyRpcResponse));
            return easyRpcResponse;
        }finally {
            // 可以异步
            EasyRpcRemoteContext.removeRequestCache(easyRpcRequest.getRequestId());
        }
    }

}
