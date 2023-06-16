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
import io.netty.util.internal.PlatformDependent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description 服务端处理类
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/12
 */
public class EasyRpcServerHandler extends SimpleChannelInboundHandler<EasyRpcRequest> {
    private static final Logger log = LoggerFactory.getLogger(EasyRpcServerHandler.class);

    private static Map<String, Class<?>> CLASS_CACHE = new ConcurrentHashMap<>(128);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("服务端->连接成功：{}", ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channel, EasyRpcRequest rpcRequest) throws Exception {
        log.info("服务端->收到消息：{}", JSONObject.toJSONString(rpcRequest));
        // 服务端异步处理 增加吞吐量
        ThreadPoolUtils.nettyServerAsyncHandler.execute(() -> {
            try {
                Class<?> classType = CLASS_CACHE.get(rpcRequest.getInterfaces());
                if (classType == null) {
                    classType = Class.forName(rpcRequest.getInterfaces());
                    CLASS_CACHE.put(rpcRequest.getInterfaces(), classType);
                }
                //调用
                Method addMethod = classType.getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
                // 从缓存中获取对应的bean对象，然后反射执行方法
                Object objectBean = EasyRpcRemoteContext.getProducerBean(rpcRequest.getBeanRef(), rpcRequest.getInterfaces());
                Object result = addMethod.invoke(objectBean, rpcRequest.getArgs());
                //反馈
                sendResponse(channel, EasyRpcResponse.success(rpcRequest.getRequestId(), result));
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
