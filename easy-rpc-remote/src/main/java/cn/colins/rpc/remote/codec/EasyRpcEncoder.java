package cn.colins.rpc.remote.codec;

import cn.colins.rpc.remote.codec.domain.RpcRemoteMsg;
import com.alibaba.fastjson.JSON;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author czl
 * @Description 编码器
 * @Date 2023/6/12 15:09
 * @Param
 * @return
 **/
public class EasyRpcEncoder extends MessageToByteEncoder<RpcRemoteMsg> {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcRemoteMsg rpcRemoteMsg, ByteBuf out) throws Exception {
        // 写入开头的标志
        out.writeShort(rpcRemoteMsg.getStartSign());
        // 写入秒时间戳
        out.writeInt(rpcRemoteMsg.getTimeStamp());
        // 写消息类型
        out.writeShort(rpcRemoteMsg.getMsgType());
        // 写入加密序列号
        out.writeShort(rpcRemoteMsg.getEncryptSequence());
        // 写入消息长度
        out.writeInt(rpcRemoteMsg.getContentLength());
        // 写入消息主体
        out.writeBytes(rpcRemoteMsg.getContent());
    }
}
