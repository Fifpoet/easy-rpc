package cn.colins.rpc.remote;

import cn.colins.rpc.remote.codec.domain.RpcRemoteMsg;
import cn.colins.rpc.remote.config.EasyRpcClientConfig;
import cn.colins.rpc.remote.entiy.EasyRpcRequest;
import cn.colins.rpc.remote.exception.EasyRpcRemoteException;
import cn.colins.rpc.remote.handler.EasyRpcClientHandlerInit;
import cn.colins.rpc.remote.handler.EasyRpcServerHandlerInit;
import cn.colins.rpc.remote.utils.EncryptUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelFuture;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.concurrent.Executors;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/12
 */

public class RemoteTest {
    private final static Logger log = LoggerFactory.getLogger(RemoteTest.class);

    @Test
    public void test() {
        Assert.notNull(null, "application name cannot be empty");
    }

    @Test
    public void serverTest() {
        EasyRpcServer easyRpcServer = new EasyRpcServer(1111, new EasyRpcServerHandlerInit());
        easyRpcServer.rpcServerStart();
    }

    @Test
    public void clientTest() throws EasyRpcRemoteException {
        EasyRpcClientConfig easyRpcClientConfig = new EasyRpcClientConfig();
        easyRpcClientConfig.setAddress("127.0.0.1");
        easyRpcClientConfig.setPort(1111);
        EasyRpcClient easyRpcClient = new EasyRpcClient(easyRpcClientConfig, new EasyRpcClientHandlerInit());
        ChannelFuture connect = easyRpcClient.connect();
        Executors.newCachedThreadPool().execute(easyRpcClient);
        while (true){}
    }
}
