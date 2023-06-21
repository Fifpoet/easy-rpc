package cn.colins.rpc.core.cache;

import cn.colins.rpc.common.entiy.ServiceInstance;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/13
 */
public class EasyRpcInstanceCache {
    /** 本地实例缓存  服务名:实例列表 */
    private static Map<String, List<ServiceInstance>> serviceInstanceCache=new ConcurrentHashMap<>(16);

    public static void updateServiceInstanceInfo(String serviceId,List<ServiceInstance> serviceInstanceList){
        serviceInstanceCache.put(serviceId,serviceInstanceList);
    }


    public static List<ServiceInstance> getServiceInstanceList(String serviceId){
        return serviceInstanceCache.get(serviceId);
    }
}
