package cn.colins.rpc.remote;

import cn.colins.rpc.remote.codec.domain.RpcRemoteMsg;
import cn.colins.rpc.remote.config.EasyRpcClientConfig;
import cn.colins.rpc.remote.entiy.EasyRpcRequest;
import cn.colins.rpc.remote.handler.EasyRpcClientHandlerInit;
import cn.colins.rpc.remote.handler.EasyRpcServerHandlerInit;
import cn.colins.rpc.remote.utils.EncryptUtil;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/12
 */

public class RemoteTest {
    private final static Logger log= LoggerFactory.getLogger(RemoteTest.class);

    @Test
    public void test(){
        EasyRpcRequest easyRpcRequest = new EasyRpcRequest();
        easyRpcRequest.setAlias("test");
        easyRpcRequest.setParamTypes(new Class[]{String.class,String.class});
        easyRpcRequest.setArgs(new Object[]{"1111","3333"});

        RpcRemoteMsg rpcRemoteMsg = new RpcRemoteMsg(easyRpcRequest);
        log.info("加密前：{},{},{}", JSONObject.toJSON(easyRpcRequest),rpcRemoteMsg.getContentLength(),JSONObject.toJSONBytes(easyRpcRequest).length);


        System.out.println(EncryptUtil.decryptMsg(1,EncryptUtil.encryptMsg(1,JSONObject.toJSONBytes(easyRpcRequest))));;

//        log.info("解密后:{},{}", EncryptUtil.decryptMsg(rpcRemoteMsg.getEncryptSequence(),rpcRemoteMsg.getContent()));


    }

    @Test
    public void serverTest(){
        EasyRpcServer easyRpcServer = new EasyRpcServer(1111, new EasyRpcServerHandlerInit());
        easyRpcServer.rpcServerStart();
    }

    @Test
    public void clientTest(){
        EasyRpcClientConfig easyRpcClientConfig = new EasyRpcClientConfig();
        easyRpcClientConfig.setAddress("127.0.0.1");
        easyRpcClientConfig.setPort(1111);
        EasyRpcClient easyRpcClient = new EasyRpcClient(easyRpcClientConfig,new EasyRpcClientHandlerInit());
        easyRpcClient.rpcClientStart();
    }
}
