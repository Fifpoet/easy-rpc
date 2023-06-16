package cn.colins.rpc.core.center.nacos;


import cn.colins.rpc.common.exception.EasyRpcRunException;
import cn.colins.rpc.core.cache.EasyRpcInstanceCache;
import cn.colins.rpc.core.center.EasyRpcCenter;
import cn.colins.rpc.core.config.EasyRpcApplicationConfig;
import cn.colins.rpc.core.config.EasyRpcCenterConfig;
import cn.colins.rpc.core.domain.ServiceInstance;

import cn.colins.rpc.common.utils.ThreadPoolUtils;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.listener.AbstractEventListener;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.NacosNamingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/12
 */
public class NacosEasyRpcCenter implements EasyRpcCenter {
    private final static Logger log = LoggerFactory.getLogger(NacosEasyRpcCenter.class);

    private final static String DEFAULT_NAMESPACE = "public";
    private final static String DEFAULT_META_PARAM = "easyRpcMeta";

    private NacosNamingService nacosNamingService;

    private EasyRpcApplicationConfig applicationConfig;

    private EasyRpcCenterConfig centerConfig;

    public NacosEasyRpcCenter(EasyRpcCenterConfig centerConfig, EasyRpcApplicationConfig rpcApplicationConfig) {
        Assert.isTrue(StrUtil.isNotEmpty(centerConfig.getAddress()), "registry address cannot be empty");
        Assert.isTrue(StrUtil.isNotEmpty(centerConfig.getGroup()), "registry group cannot be empty");
        this.applicationConfig = rpcApplicationConfig;
        this.centerConfig = centerConfig;
        Properties properties = new Properties();
        properties.setProperty("serverAddr", String.format("%s:%d", centerConfig.getAddress(), centerConfig.getPort()));
        properties.setProperty("namespace", StrUtil.emptyToDefault(centerConfig.getNamespace(), DEFAULT_NAMESPACE));
        try {
            this.nacosNamingService = new NacosNamingService(properties);
        } catch (NacosException e) {
            log.error("Easy-Rpc -> nacos center init error:{}", e.getErrMsg(), e);
            throw new EasyRpcRunException(e.getErrMsg());
        }
    }


    @Override
    public void registerInstance(ServiceInstance serviceInstance) {
        Instance instance = new Instance();
        instance.setIp(serviceInstance.getIp());
        instance.setPort(serviceInstance.getPort());
        Map<String, String> meteData=new HashMap<>(4);
        meteData.put(DEFAULT_META_PARAM,JSONObject.toJSONString(serviceInstance.getMetaDataSet()));
        instance.setMetadata(meteData);
        try {
            nacosNamingService.registerInstance(applicationConfig.getName(), centerConfig.getGroup(),instance);
        } catch (NacosException e) {
            log.error("Easy-Rpc -> nacos center register error:{}", e.getErrMsg(), e);
            throw new EasyRpcRunException(e.getErrMsg());
        }
        log.info("Easy-Rpc -> nacos center register:[ serviceName:{} group:{} ] success",applicationConfig.getName(),centerConfig.getGroup());
    }

    @Override
    public void deregisterInstance(ServiceInstance serviceInstance) {
        try {
            nacosNamingService.deregisterInstance(applicationConfig.getName(), centerConfig.getGroup(),serviceInstance.getIp(),serviceInstance.getPort());
        } catch (NacosException e) {
            log.error("Easy-Rpc -> nacos center deregister error:{}", e.getErrMsg(), e);
        }
        log.info("Easy-Rpc -> nacos center deregister:[ serviceName:{} group:{} ] success",applicationConfig.getName(),centerConfig.getGroup());
    }

    @Override
    public List<ServiceInstance> getAllInstances(String serviceId) {
        List<ServiceInstance> serviceInstanceList = EasyRpcInstanceCache.getServiceInstanceList(serviceId);
        if(CollectionUtil.isEmpty(serviceInstanceList)){
            serviceInstanceList = new CopyOnWriteArrayList<>(new ArrayList<>(8));
            try {
                List<Instance> allInstances = nacosNamingService.getAllInstances(serviceId, centerConfig.getGroup());
                if(CollectionUtil.isNotEmpty(allInstances)){
                    for(Instance instance:allInstances){
                        serviceInstanceList.add(new ServiceInstance(instance.getIp(), instance.getPort()));
                    }
                }
            } catch (NacosException e) {
                log.error("Easy-Rpc -> nacos center deregister error:{}", e.getErrMsg(), e);
            }
            EasyRpcInstanceCache.updateServiceInstanceInfo(serviceId,serviceInstanceList);
        }
        return serviceInstanceList;
    }

    @Override
    public void subscribeInstance(String serviceId) {
        try {
            nacosNamingService.subscribe(serviceId, centerConfig.getGroup(), new AbstractEventListener() {
                @Override
                public Executor getExecutor() {
                    return ThreadPoolUtils.subscribeInstancePool;
                }

                @Override
                public void onEvent(Event event) {
                    NamingEvent namingEvent = (NamingEvent) event;
                    List<Instance> allInstances = namingEvent.getInstances();
                    List<ServiceInstance> serviceInstanceList = new CopyOnWriteArrayList<>(new ArrayList<>(8));
                    if(CollectionUtil.isNotEmpty(allInstances)){
                        for(Instance instance:allInstances){
                            serviceInstanceList.add(new ServiceInstance(instance.getIp(), instance.getPort()));
                        }
                    }
                    // 直接把本地的全量替换
                    EasyRpcInstanceCache.updateServiceInstanceInfo(serviceId,serviceInstanceList);
                }
            });
        } catch (NacosException e) {
            log.error("Easy-Rpc -> nacos center subscribe error:{}", e.getErrMsg(), e);
        }
        log.info("Easy-Rpc -> nacos center subscribe:[{}] success",serviceId);
    }
}
