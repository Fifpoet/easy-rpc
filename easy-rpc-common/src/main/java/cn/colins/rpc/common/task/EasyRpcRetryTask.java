package cn.colins.rpc.common.task;

import cn.colins.rpc.common.exception.EasyRpcException;
import cn.colins.rpc.common.exception.EasyRpcRunException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: easy-rpc
 * @description:
 * @author: colins
 * @create: 2023-06-18 20:02
 **/
public class EasyRpcRetryTask implements Runnable {
    private final static Logger log = LoggerFactory.getLogger(EasyRpcRetryTask.class);

    private final EasyRpcRetry task;

    private final int maxRetryNum;

    private final int maxRetryIntervalTime;

    private final int baseRetryIntervalTime = 4;

    private final boolean unInterrupted;

    private int currentRetryNum = 1;

    private boolean retryStatus = false;

    public EasyRpcRetryTask(EasyRpcRetry task) {
        this.task = task;
        this.maxRetryNum = 5;
        this.maxRetryIntervalTime = 0;
        this.unInterrupted = false;
    }

    public EasyRpcRetryTask(EasyRpcRetry task, int maxRetryNum) {
        this.task = task;
        this.maxRetryNum = maxRetryNum;
        this.maxRetryIntervalTime = 0;
        this.unInterrupted = false;
    }

    public EasyRpcRetryTask(EasyRpcRetry task, int maxRetryNum, int maxRetryIntervalTime) {
        this.task = task;
        this.maxRetryNum = maxRetryNum;
        this.maxRetryIntervalTime = maxRetryIntervalTime;
        this.unInterrupted = true;
    }



    @Override
    public void run() {
        while (currentRetryNum <= maxRetryNum) {
            // 代表需要时间间隔
            sleepWait();

            log.warn("Easy-Rpc the {} retry start", currentRetryNum);

            if(task.exeResult()){
                // 成功就重置重试次数
                currentRetryNum=1;
                retryStatus=true;
                task.exeAfterHandler();
                break;
            }
            // 失败
            currentRetryNum++;
        }
        // 到了这说明一次重试结束了
        if(!retryStatus){
            throw new EasyRpcRunException("Easy-Rpc ("+ task.getClass() +")  retry fail");
        }
    }

    private void sleepWait() {
        if(unInterrupted){
            int sleepTime = Math.min(baseRetryIntervalTime << (currentRetryNum - 1), maxRetryIntervalTime);
            try {
                Thread.sleep(sleepTime * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
