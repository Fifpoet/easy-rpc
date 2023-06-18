package cn.colins.rpc.remote;

import cn.colins.rpc.common.entiy.EasyRpcRequest;
import cn.colins.rpc.common.entiy.EasyRpcResponse;
import cn.colins.rpc.common.utils.HessianUtils;
import cn.colins.rpc.remote.config.EasyRpcClientConfig;
import cn.colins.rpc.remote.handler.EasyRpcClientHandlerInit;
import cn.colins.rpc.remote.handler.EasyRpcServerHandlerInit;
import io.netty.channel.ChannelFuture;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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
        Executors.newCachedThreadPool().execute(easyRpcServer);
        while (true){}
    }

    @Test
    public void clientTest() {
        EasyRpcClientConfig easyRpcClientConfig = new EasyRpcClientConfig();
        easyRpcClientConfig.setAddress("127.0.0.1");
        easyRpcClientConfig.setPort(1111);
        EasyRpcClient easyRpcClient = new EasyRpcClient(easyRpcClientConfig, new EasyRpcClientHandlerInit());
        ChannelFuture connect = easyRpcClient.connect();
        Executors.newCachedThreadPool().execute(easyRpcClient);
        while (true){}
    }
}
