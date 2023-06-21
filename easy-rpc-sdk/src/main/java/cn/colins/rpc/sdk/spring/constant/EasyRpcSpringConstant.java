package cn.colins.rpc.sdk.spring.constant;

import cn.colins.rpc.common.entiy.ServiceMetaData;
import cn.hutool.core.collection.ConcurrentHashSet;

import java.util.Set;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/14
 */
public class EasyRpcSpringConstant {

    /** 需要订阅的服务列表 */
    public static Set<String> serviceIdList=new ConcurrentHashSet<>(16);

    /** 需要发布的服务元数据 */
    public static Set<ServiceMetaData> serviceMetaDataList=new ConcurrentHashSet<>(16);
}
