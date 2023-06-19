package cn.colins.rpc.sdk.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface EasyRpcServiceInvoke {

    String serviceId();

    String beanRefName() default "";

    int weight() default 1;

    String loadBalance() default "";

    String version() default "";

    String router() default "";

}
