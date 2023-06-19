package cn.colins.rpc.core;

import cn.colins.rpc.common.task.EasyRpcRetry;

/**
 * @program: easy-rpc
 * @description:
 * @author: colins
 * @create: 2023-06-18 22:56
 **/
public class TestRetry implements EasyRpcRetry {

    @Override
    public boolean exeResult() {
        return false;
    }

    @Override
    public void exeAfterHandler() {

    }
}
