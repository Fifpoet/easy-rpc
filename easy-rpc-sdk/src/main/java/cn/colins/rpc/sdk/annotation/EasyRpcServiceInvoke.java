package cn.colins.rpc.sdk.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
@Component
public @interface EasyRpcServiceInvoke {

    String serviceId();

    String beanRefName() default "";

}
