package cn.colins.rpc.remote.handler.server;

import cn.colins.rpc.remote.codec.domain.RpcRemoteMsg;
import cn.colins.rpc.remote.context.EasyRpcRemoteContext;
import cn.colins.rpc.remote.entiy.EasyRpcRequest;
import cn.colins.rpc.remote.entiy.EasyRpcResponse;
import cn.colins.rpc.remote.utils.SpringUtils;
import cn.colins.rpc.remote.utils.ThreadPoolUtils;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @Description 服务端处理类
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
        // 服务端异步处理 增加吞吐量
        ThreadPoolUtils.nettyServerAsyncHandler.execute(()->{
            try {
                //调用
                Class<?> classType = Class.forName(rpcRequest.getInterfaces());
                Method addMethod = classType.getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
                Object objectBean = EasyRpcRemoteContext.getProducerBean(rpcRequest.getBeanRef(),rpcRequest.getInterfaces());
                Object result = addMethod.invoke(objectBean, rpcRequest.getArgs());
                //反馈
                sendResponse(channel, EasyRpcResponse.success(rpcRequest.getRequestId(), result));
                //释放
                ReferenceCountUtil.release(rpcRequest);
            } catch (Exception e) {
                log.error("Easy-Rpc handler msg:[ interfaces:{} method:{} args:{} ] error:{} "
                        , rpcRequest.getInterfaces(), rpcRequest.getMethodName(), JSONObject.toJSON(rpcRequest.getArgs()), e.getMessage(), e);
                //反馈
                sendResponse(channel, EasyRpcResponse.error(rpcRequest.getRequestId(), e));
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("服务端->[ {} ]客户端发生异常下线：{}", ctx.channel().remoteAddress(), cause.getMessage());
    }


    private void sendResponse(ChannelHandlerContext channel, EasyRpcResponse easyRpcResponse) {
        channel.writeAndFlush(new RpcRemoteMsg(easyRpcResponse));
    }
}
