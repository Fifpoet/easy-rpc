package cn.colins.rpc.remote;

import cn.colins.rpc.common.exception.EasyRpcException;
import cn.colins.rpc.common.task.EasyRpcRetry;
import cn.colins.rpc.common.utils.RetryTaskUtils;
import cn.colins.rpc.common.utils.ThreadPoolUtils;
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
public class EasyRpcServer implements EasyRpcRetry, Runnable {
    private final static Logger log = LoggerFactory.getLogger(EasyRpcClient.class);

    private NioEventLoopGroup bossGroup = null;
    private NioEventLoopGroup workerGroup = null;

    private final int port;

    private final ChannelInitializer channelInitializer;

    private ChannelFuture future;

    public EasyRpcServer(int port, ChannelInitializer channelInitializer) {
        this.port = port;
        this.channelInitializer = channelInitializer;
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

    private final boolean nettyServerStart() throws InterruptedException, EasyRpcException {
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
        future = bootstrap.bind().sync();
        if (future.isSuccess()) {
            log.info("启动 Easy-Rpc Server 成功,端口：{}", port);
            return true;
        }
        return false;
    }

    private final void waitNettyServerClose() throws InterruptedException {
        future.channel().closeFuture().sync();
    }

    @Override
    public boolean exeResult() {
        try {
            return this.nettyServerStart();
        } catch (Exception e) {
            log.error("Easy-Rpc server start error:{}", e.getMessage(), e);
            return false;
        } finally {
            log.info("Easy-Rpc Server shutdown");
            // 优雅的关闭 释放资源
            destroy();
        }
    }

    @Override
    public void exeAfterHandler() {
        try {
            this.waitNettyServerClose();
        } catch (InterruptedException e) {
            log.error("Easy-Rpc server start error:{}", e.getMessage(), e);
        }
    }

    @Override
    public void run() {
        try {
            this.nettyServerStart();
            this.waitNettyServerClose();
        } catch (Exception e) {
            log.error("Easy-Rpc server start error:{}", e.getMessage(), e);
            // 服务端一旦发生异常则需要重新启动
            RetryTaskUtils.taskSubmit(new EasyRpcServer(port, channelInitializer));
        } finally {
            log.info("Easy-Rpc Server shutdown");
            // 优雅的关闭 释放资源
            destroy();
        }
    }
}
