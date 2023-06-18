package cn.colins.rpc.core;

import cn.colins.rpc.core.task.EasyRpcRetry;

/**
 * @program: easy-rpc
 * @description:
 * @author: colins
 * @create: 2023-06-18 22:56
 **/
public class TestRetry implements EasyRpcRetry {
    @Override
    public boolean result() {
        return false;
    }
}
