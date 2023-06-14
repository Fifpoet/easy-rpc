package cn.colins.rpc.controller;

import cn.colins.rpc.EasyRpcTest;
import cn.colins.rpc.sdk.annotation.EasyRpcServiceInvoke;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/14
 */
@RestController
public class EasyRpcTestController {

    @EasyRpcServiceInvoke( serviceId = "Test")
    private EasyRpcTest easyRpcTest;

    @GetMapping("/test")
    public String test(){
        easyRpcTest.test();
        return "ok";
    }
}
