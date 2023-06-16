package cn.colins.rpc.remote;

import cn.colins.rpc.remote.codec.domain.RpcRemoteMsg;
import cn.colins.rpc.remote.config.EasyRpcClientConfig;
import cn.colins.rpc.remote.entiy.EasyRpcRequest;
import cn.colins.rpc.remote.entiy.EasyRpcResponse;
import cn.colins.rpc.remote.exception.EasyRpcRemoteException;
import cn.colins.rpc.remote.handler.EasyRpcClientHandlerInit;
import cn.colins.rpc.remote.handler.EasyRpcServerHandlerInit;
import cn.colins.rpc.remote.utils.EncryptUtil;
import cn.colins.rpc.remote.utils.HessianUtils;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import io.netty.channel.ChannelFuture;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.UUID;
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
    public void test() throws IOException {

        EasyRpcRequest easyRpcRequest = new EasyRpcRequest(UUID.randomUUID().toString(),"easyRpcTest",
                "cn.colins.rpc.EasyRpcTest", "test1", new Class[]{EasyRpcResponse.class}, new Object[]{EasyRpcResponse.success("")});
        byte[] bytes = HessianUtils.toBytes(easyRpcRequest);

        EasyRpcRequest student = (EasyRpcRequest) HessianUtils.parseObject(bytes);
        System.out.println(student);
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
