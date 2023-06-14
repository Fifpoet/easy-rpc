package cn.colins.rpc;

import cn.colins.rpc.sdk.annotation.EnableEasyRpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableEasyRpc
@SpringBootApplication
public class EasyRpcTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyRpcTestApplication.class, args);
    }

}
