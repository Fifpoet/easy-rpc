package cn.colins.rpc.core;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.AbstractEventListener;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/13
 */
public class NacosTest {
    private final static Logger log= LoggerFactory.getLogger(NacosTest.class);
    @Test
    public void registry() throws NacosException, UnknownHostException {
        Executor executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName("test-thread");
                        return thread;
                    }
                });

        //1.注册中心相关配置
        Properties properties = new Properties();
        properties.setProperty("serverAddr","returnac.cn:8848");
        properties.setProperty("namespace", "public");
        //2.反射初始化NacosNamingService
        NamingService naming = NamingFactory.createNamingService(properties);
        //3.服务注册
        naming.registerInstance("TEST", "TEST_GROUP", InetAddress.getLocalHost().getHostAddress(), 9999, "TEST1");


        naming.subscribe("TEST", "TEST_GROUP", new AbstractEventListener() {
            @Override
            public Executor getExecutor() {
                return executor;
            }

            @Override
            public void onEvent(Event event) {
                log.info("监听配置:{}", JSONObject.toJSON(((NamingEvent)event).getInstances()));
            }
        });



        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        naming.registerInstance("TEST", "TEST_GROUP", InetAddress.getLocalHost().getHostAddress(), 8888, "TEST1");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        naming.deregisterInstance("TEST", "TEST_GROUP", InetAddress.getLocalHost().getHostAddress(), 9999, "TEST1");
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Instance> allInstances = naming.getAllInstances("TEST", "TEST_GROUP");
        log.info("获取配置:{}", JSONObject.toJSON(allInstances));


    }
}
