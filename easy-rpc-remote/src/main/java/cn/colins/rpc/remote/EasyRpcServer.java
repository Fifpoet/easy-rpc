package cn.colins.rpc.remote;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/12
 */
public class EasyRpcServer implements Runnable{
    private final static Logger log= LoggerFactory.getLogger(EasyRpcClient.class);

    private NioEventLoopGroup bossGroup = null;
    private NioEventLoopGroup workerGroup = null;

    private int port;

    private ChannelInitializer channelInitializer;

    public EasyRpcServer(int port, ChannelInitializer channelInitializer) {
        this.port = port;
        this.channelInitializer = channelInitializer;
    }

    public void rpcServerStart(){
        try {
            bossGroup = new NioEventLoopGroup(2);
            workerGroup = new NioEventLoopGroup(16);
            // 服务端启动类
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 传入两个线程组
            bootstrap.group(bossGroup, workerGroup)
                    // 指定Channel 和NIO一样是采用Channel通道的方式通信 所以需要指定服务端通道
                    .channel(NioServerSocketChannel.class)
                    //使用指定的端口设置套接字地址
                    .localAddress(new InetSocketAddress(port))
                    //服务端可连接队列数,对应TCP/IP协议listen函数中backlog参数
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //设置数据处理器
                    .childHandler(channelInitializer);
            // 同步等待成功
            ChannelFuture future = bootstrap.bind().sync();
            if (future.isSuccess()) {
                log.info("启动 Easy-Rpc Server 成功,端口：{}", port);
            }
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("Easy-Rpc server start error:{},{}", e.getMessage(), e);
        } finally {
            log.info("Easy-Rpc Server shutdown");
            // 优雅的关闭 释放资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    protected void destroy() {
        // 优雅的关闭 释放资源
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
    }

    @Override
    public void run() {
        this.rpcServerStart();
    }
}
