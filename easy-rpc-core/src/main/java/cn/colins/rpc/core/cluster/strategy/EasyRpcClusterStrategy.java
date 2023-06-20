package cn.colins.rpc.core.cluster.strategy;

import cn.colins.rpc.common.entiy.EasyRpcRequest;
import cn.colins.rpc.common.entiy.EasyRpcResponse;

import java.util.List;

/**
 * @program: easy-rpc
 * @description:
 * @author: colins
 * @create: 2023-06-20 23:06
 **/
public interface EasyRpcClusterStrategy {

    EasyRpcResponse clusterStrategy(EasyRpcRequest easyRpcRequest);
}