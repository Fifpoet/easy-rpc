package cn.colins.rpc.remote.codec.domain;

import cn.colins.rpc.remote.entiy.EasyRpcRequest;
import cn.colins.rpc.remote.entiy.EasyRpcResponse;
import cn.colins.rpc.remote.utils.EncryptUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/12
 */
public class RpcRemoteMsg {
    // 消息体开头标记
    private short startSign = (short) 0x01F1;
    // 时间戳
    private int timeStamp;
    // 区分是请求还是响应  0：请求  1：响应
    private short msgType;
    // 加密序列号 随机选择一个秘钥 对称性加密
    private short encryptSequence;
    // 消息体长度
    private int contentLength;
    // 消息体
    private byte[] content;

    public RpcRemoteMsg(EasyRpcRequest rpcRequest) {
        this.timeStamp = (int) (DateUtil.current() / 1000);
        this.encryptSequence = EncryptUtil.getRandomKeyIndex();
        this.msgType = (short) 0;
        this.content = EncryptUtil.encryptMsg(this.encryptSequence, JSONObject.toJSONBytes(rpcRequest));
        this.contentLength = this.content.length;
    }

    public RpcRemoteMsg(EasyRpcResponse rpcResponse) {
        this.timeStamp = (int) (DateUtil.current() / 1000);
        this.encryptSequence = EncryptUtil.getRandomKeyIndex();
        this.msgType = (short) 1;
        this.content = EncryptUtil.encryptMsg(this.encryptSequence, JSONObject.toJSONBytes(rpcResponse));
        this.contentLength = this.content.length;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public short getEncryptSequence() {
        return encryptSequence;
    }

    public short getMsgType() {
        return msgType;
    }

    public short getStartSign() {
        return startSign;
    }
}
