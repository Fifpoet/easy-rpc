package cn.colins.rpc.core.executor.impl;

import cn.colins.rpc.core.domain.ServiceInstance;
import cn.colins.rpc.core.executor.EasyRpcExecutor;
import cn.colins.rpc.remote.codec.domain.RpcRemoteMsg;
import cn.colins.rpc.remote.context.EasyRpcRemoteContext;
import cn.colins.rpc.remote.entiy.EasyRpcRequest;
import cn.colins.rpc.remote.entiy.EasyRpcResponse;
import cn.colins.rpc.remote.entiy.EasyRpcResultCode;
import cn.colins.rpc.remote.future.impl.SyncEasyRpcWriteFuture;
import cn.colins.rpc.remote.handler.server.EasyRpcServerHandler;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
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
    public EasyRpcResponse executor(ChannelFuture channelFuture, EasyRpcRequest easyRpcRequest, ServiceInstance serviceInstance) {
        SyncEasyRpcWriteFuture syncEasyRpcWriteFuture = new SyncEasyRpcWriteFuture(easyRpcRequest.getRequestId(), 0L);
        try {
            EasyRpcResponse easyRpcResponse = syncEasyRpcWriteFuture.write(channelFuture, easyRpcRequest);
            log.info("收到数据:{}", JSONObject.toJSON(easyRpcResponse));
            return easyRpcResponse;
        } catch (TimeoutException e) {
            log.error("异常:{} ", e.getMessage(),e);
            return EasyRpcResponse.error(easyRpcRequest.getRequestId(), EasyRpcResultCode.TIME_OUT,e);
        } catch (Exception e) {
            log.error("异常:{} ", e.getMessage(),e);
            return EasyRpcResponse.error(easyRpcRequest.getRequestId(), e);
        }finally {
            // 可以异步
            EasyRpcRemoteContext.removeRequestCache(easyRpcRequest.getRequestId());
        }
    }

}
