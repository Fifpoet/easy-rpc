package cn.colins.rpc.remote;

import cn.colins.rpc.common.exception.EasyRpcException;
import cn.colins.rpc.common.exception.EasyRpcRunException;
import cn.colins.rpc.remote.config.EasyRpcClientConfig;
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
public class EasyRpcClient implements Runnable{

    private final static Logger log= LoggerFactory.getLogger(EasyRpcClient.class);

    private EventLoopGroup group = null;

    private ChannelInitializer channelInitializer;

    private EasyRpcClientConfig rpcClientConfig;

    private ChannelFuture future;


    public EasyRpcClient(EasyRpcClientConfig rpcClientConfig, ChannelInitializer channelInitializer) {
        this.rpcClientConfig = rpcClientConfig;
        this.channelInitializer = channelInitializer;
        this.group = new NioEventLoopGroup();
    }

    public ChannelFuture connect() {
        Bootstrap bootstrap = new Bootstrap();
        // 客户端不需要处理连接 所以一个线程组就够了
        bootstrap.group(group)
                // 连接通道
                .channel(NioSocketChannel.class)
                .remoteAddress(rpcClientConfig.getAddress(), rpcClientConfig.getPort())
                .option(ChannelOption.TCP_NODELAY, true)
                // 数据处理
                .handler(channelInitializer);

        future = bootstrap.connect();
        future.addListener(event -> {
            if (event.isSuccess()) {
                log.info("连接Easy-Rpc Server 成功,地址：{},端口：{}", rpcClientConfig.getAddress(), rpcClientConfig.getPort());
            } else {
                throw new EasyRpcException(String.format("Easy-Rpc connect server:[ %s:%d ] fail",rpcClientConfig.getAddress(), rpcClientConfig.getPort()));
            }
        });
        return future;
    }



    public void destroy() {
        // 优雅的关闭 释放资源
        if (group != null) {
            group.shutdownGracefully();
        }
    }

    @Override
    public void run() {
        try {
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("Easy-Rpc Client start error：{},{}", e.getMessage(), e);
            throw new EasyRpcRunException(e.getMessage());
        }finally {
            destroy();
        }
    }
}
