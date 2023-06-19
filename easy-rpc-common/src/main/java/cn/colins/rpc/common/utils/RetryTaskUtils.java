package cn.colins.rpc.common.utils;

import cn.colins.rpc.common.task.EasyRpcRetry;
import cn.colins.rpc.common.task.EasyRpcRetryTask;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/19
 */
public class RetryTaskUtils {

    public static void taskSubmit(EasyRpcRetry easyRpcRetry) {
        ThreadPoolUtils.retryTaskPool.execute(new EasyRpcRetryTask(easyRpcRetry));
    }

    public static void taskSubmit(EasyRpcRetry easyRpcRetry, int maxRetryNum) {
        ThreadPoolUtils.retryTaskPool.execute(new EasyRpcRetryTask(easyRpcRetry, maxRetryNum));
    }

    public static void taskSubmit(EasyRpcRetry easyRpcRetry, int maxRetryNum, int maxRetryIntervalTime) {
        ThreadPoolUtils.retryTaskPool.execute(new EasyRpcRetryTask(easyRpcRetry, maxRetryNum, maxRetryIntervalTime));
    }

}
