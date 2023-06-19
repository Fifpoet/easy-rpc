package cn.colins.rpc.common.task;

/**
 * @program: easy-rpc
 * @description:
 * @author: colins
 * @create: 2023-06-18 22:36
 **/
public interface EasyRpcRetry {

    // 执行结果
    boolean exeResult();

    // 执行成功后处理
    void exeAfterHandler();


}