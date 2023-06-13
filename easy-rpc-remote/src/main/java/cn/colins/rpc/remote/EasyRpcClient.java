package cn.colins.rpc.remote;

import cn.colins.rpc.remote.codec.domain.RpcRemoteMsg;
import cn.colins.rpc.remote.config.EasyRpcClientConfig;
import cn.colins.rpc.remote.entiy.EasyRpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/12
 */
public class EasyRpcClient {

    private final static Logger log= LoggerFactory.getLogger(EasyRpcClient.class);

    private EventLoopGroup group = null;

    private ChannelInitializer channelInitializer;

    private EasyRpcClientConfig rpcClientConfig;


    public EasyRpcClient(EasyRpcClientConfig rpcClientConfig, ChannelInitializer channelInitializer) {
        this.rpcClientConfig = rpcClientConfig;
        this.channelInitializer = channelInitializer;
    }

    public void rpcClientStart() {
        group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            // 客户端不需要处理连接 所以一个线程组就够了
            bootstrap.group(group)
                    // 连接通道
                    .channel(NioSocketChannel.class)
                    .remoteAddress(rpcClientConfig.getAddress(), rpcClientConfig.getPort())
                    .option(ChannelOption.TCP_NODELAY, true)
                    // 数据处理
                    .handler(channelInitializer);

            ChannelFuture future = bootstrap.connect();
            future.addListener(event -> {
                if (event.isSuccess()) {
                    EasyRpcRequest easyRpcRequest = new EasyRpcRequest();
                    easyRpcRequest.setAlias("test");
                    easyRpcRequest.setParamTypes(new Class[]{String.class,String.class});
                    easyRpcRequest.setArgs(new Object[]{"1111","3333"});
                    future.channel().writeAndFlush(new RpcRemoteMsg(easyRpcRequest));
                    log.info("连接Easy-Rpc Server 成功,地址：{},端口：{}", rpcClientConfig.getAddress(), rpcClientConfig.getPort());
                } else {
                    log.info("Easy-Rpc Client connect server fail");
                }
            });
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("Easy-Rpc Client start error：{},{}", e.getMessage(), e);
        } finally {
            destroy();
        }
    }

    public void destroy() {
        // 优雅的关闭 释放资源
        if (group != null) {
            group.shutdownGracefully();
        }
    }
}
