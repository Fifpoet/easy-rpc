package cn.colins.rpc.remote.handler.client;

import cn.colins.rpc.remote.context.EasyRpcRemoteContext;
import cn.colins.rpc.remote.entiy.EasyRpcResponse;
import cn.colins.rpc.remote.future.impl.SyncEasyRpcWriteFuture;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description  客户端处理类
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/12
 */
public class EasyRpcClientHandler extends SimpleChannelInboundHandler<EasyRpcResponse> {
    private static final Logger log = LoggerFactory.getLogger(EasyRpcClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext context) throws Exception {
        Channel channel = context.channel();
        log.info("客户端-> 连接成功，服务端IP:{}",channel.remoteAddress());


    }

    @Override
    protected void channelRead0(ChannelHandlerContext context, EasyRpcResponse rpcResponse) throws Exception {
        try{
            // 可以异步处理
            SyncEasyRpcWriteFuture requestCache = EasyRpcRemoteContext.getRequestCache(rpcResponse.getRequestId());
            if(requestCache!=null){
                requestCache.setResponse(rpcResponse);
            }
        }finally {
            //释放
            ReferenceCountUtil.release(rpcResponse);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("客户端->[ {} ]服务端发生异常下线：{}",ctx.channel().remoteAddress(),cause.getMessage());
    }
}
