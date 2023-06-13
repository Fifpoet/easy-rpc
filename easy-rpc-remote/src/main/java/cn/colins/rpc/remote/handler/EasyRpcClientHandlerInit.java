package cn.colins.rpc.remote.handler;


import cn.colins.rpc.remote.codec.EasyRpcDecoder;
import cn.colins.rpc.remote.codec.EasyRpcEncoder;
import cn.colins.rpc.remote.handler.client.EasyRpcClientHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EasyRpcClientHandlerInit extends ChannelInitializer<Channel> {

    private static final Logger log = LoggerFactory.getLogger(EasyRpcClientHandlerInit.class);


    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(new EasyRpcDecoder(1024,10,4,0,0));
        channel.pipeline().addLast(new EasyRpcEncoder());
        channel.pipeline().addLast(new EasyRpcClientHandler());

    }
}
