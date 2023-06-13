package cn.colins.rpc.remote.handler.server;

import cn.colins.rpc.remote.codec.domain.RpcRemoteMsg;
import cn.colins.rpc.remote.entiy.EasyRpcRequest;
import cn.colins.rpc.remote.entiy.EasyRpcResponse;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description  服务端处理类
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/12
 */
public class EasyRpcServerHandler extends SimpleChannelInboundHandler<EasyRpcRequest> {
    private static final Logger log = LoggerFactory.getLogger(EasyRpcServerHandler.class);


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("服务端->连接成功：{}", ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channel, EasyRpcRequest rpcRequest) throws Exception {
        log.info("服务端->收到消息：{}", JSONObject.toJSONString(rpcRequest));

        channel.writeAndFlush(new RpcRemoteMsg(EasyRpcResponse.success()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("服务端->[ {} ]客户端发生异常下线：{}",ctx.channel().remoteAddress(),cause.getMessage());
    }
}
