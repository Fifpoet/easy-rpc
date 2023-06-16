package cn.colins.rpc.common.utils;


import cn.colins.rpc.common.entiy.EasyRpcRequest;
import cn.colins.rpc.common.entiy.EasyRpcResponse;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/12
 */
public class EncryptUtil {
    // 随便列举几个16位的key
    private final static String[] AES_KEYS={"1234567890asdfgh","a1s2d3f4g5h6j7k8","qwert14785jkl369","ijusgken654789lk"};

    public static short getRandomKeyIndex(){
        return (short) RandomUtil.randomInt(0,AES_KEYS.length);
    }

    static {
        JSON.DEFAULT_PARSER_FEATURE |= Feature.SupportClassForName.getMask();
    }

    public static byte[] encryptMsg(int keyIndex,byte[] msg){
        return SecureUtil.aes(AES_KEYS[keyIndex].getBytes()).encrypt(msg);
    }

    public static byte[] decryptMsg(int keyIndex,byte[] msg){
        return SecureUtil.aes(AES_KEYS[keyIndex].getBytes()).decrypt(msg);
    }


    public static EasyRpcResponse remoteMsgToResponse(int keyIndex, byte[] msg){
        return (EasyRpcResponse) HessianUtils.parseObject(decryptMsg(keyIndex, msg));
    }

    public static EasyRpcRequest remoteMsgToRequest(int keyIndex, byte[] msg){
        return (EasyRpcRequest)HessianUtils.parseObject(decryptMsg(keyIndex, msg));
    }
}
