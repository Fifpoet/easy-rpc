package cn.colins.rpc.remote.codec;


import cn.colins.rpc.remote.codec.domain.RpcRemoteMsg;
import cn.colins.rpc.remote.entiy.EasyRpcRequest;
import cn.colins.rpc.remote.entiy.EasyRpcResponse;
import cn.colins.rpc.remote.handler.EasyRpcClientHandlerInit;
import cn.colins.rpc.remote.utils.EncryptUtil;
import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteOrder;


/**
 * @Author czl
 * @Description 解码器
 * @Date 2023/6/12 15:05
 * @Param
 * @return
 **/
public class EasyRpcDecoder extends LengthFieldBasedFrameDecoder {
    private static final Logger log = LoggerFactory.getLogger(EasyRpcDecoder.class);
    // 开始标记
    private final short HEAD_START = (short) 0x01F1;


    public EasyRpcDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    public EasyRpcDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);

    }

    public EasyRpcDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    public EasyRpcDecoder(ByteOrder byteOrder, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(byteOrder, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // 经过父解码器的处理 我们就不需要在考虑沾包和半包了
        // 当然，想要自己处理沾包和半包问题也不是不可以
        ByteBuf decode = (ByteBuf) super.decode(ctx, in);
        if (decode == null) {
            return null;
        }
        // 开始标志校验  开始标志不匹配直接 过滤此条消息
        short startIndex = decode.readShort();
        if (startIndex != HEAD_START) {
            return null;
        }
        // 时间戳
        int timeStamp = decode.readInt();
        // 请求还是响应  0：请求  1：响应
        short msgType = decode.readShort();
        // 加密序列号
        int encryptSequence = decode.readShort();
        // 消息体长度
        int contentLength = decode.readInt();
        // 读取消息
        byte[] msgByte = new byte[contentLength];
        decode.readBytes(msgByte);

        // 将消息转成实体类 传递给下面的数据处理器

        return msgType == 0 ? EncryptUtil.remoteMsgToRequest(encryptSequence,msgByte) : EncryptUtil.remoteMsgToResponse(encryptSequence,msgByte);
//        return msgType == 0 ? JSONObject.parseObject(new String(msgByte), EasyRpcRequest.class) : JSONObject.parseObject(new String(msgByte), EasyRpcResponse.class);
    }
}
