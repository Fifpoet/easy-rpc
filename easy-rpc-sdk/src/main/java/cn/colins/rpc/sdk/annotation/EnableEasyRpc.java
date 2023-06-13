package cn.colins.rpc.sdk.annotation;

import cn.colins.rpc.sdk.config.EasyRpcAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({EasyRpcAutoConfiguration.class})
public @interface EnableEasyRpc {
}
