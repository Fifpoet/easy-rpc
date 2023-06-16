package cn.colins.rpc.remote.future.impl;

import cn.colins.rpc.common.entiy.EasyRpcRequest;
import cn.colins.rpc.common.entiy.EasyRpcResponse;
import cn.colins.rpc.remote.codec.domain.RpcRemoteMsg;
import cn.colins.rpc.remote.context.EasyRpcRemoteContext;
import cn.colins.rpc.remote.future.EasyRpcWriteFuture;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/14
 */
public class SyncEasyRpcWriteFuture implements EasyRpcWriteFuture<EasyRpcResponse> {

    private static final Logger log = LoggerFactory.getLogger(SyncEasyRpcWriteFuture.class);


    private CountDownLatch latch;
    /**
     * 开始时间戳
     */
    private final long beginTime = System.currentTimeMillis();
    /**
     * 响应
     */
    private EasyRpcResponse easyRpcResponse;
    /**
     * 超时时间
     */
    private long timeout;
    /**
     * 是否需要超时判断
     */
    private boolean isTimeout;
    /**
     * 请求唯一标识
     */
    private final String requestId;


    public SyncEasyRpcWriteFuture(String requestId, long timeout) {
        this.requestId = requestId;
        this.timeout = timeout;
        this.isTimeout = timeout <= 0L ? false : true;
        this.latch = new CountDownLatch(1);
    }

    @Override
    public EasyRpcResponse write(ChannelFuture channelFuture, EasyRpcRequest easyRpcRequest) throws TimeoutException, InterruptedException {
        EasyRpcRemoteContext.addRequestCache(easyRpcRequest.getRequestId(),this);
        channelFuture.channel().writeAndFlush(new RpcRemoteMsg(easyRpcRequest));
        EasyRpcResponse easyRpcResponse = isTimeout ? this.get(timeout, TimeUnit.MILLISECONDS) : this.get();
        return easyRpcResponse;
    }


    @Override
    public void setResponse(EasyRpcResponse response) {
        // 添加的时候就代表获取到响应了 所以需要唤醒线程
        this.easyRpcResponse = response;
        latch.countDown();
    }

    @Override
    public EasyRpcResponse getResponse() {
        return easyRpcResponse;
    }

    @Override
    public boolean isTimeout() {
        // true 超时  false 不超时
        if (!isTimeout) {
            return false;
        }

        return System.currentTimeMillis() - beginTime > timeout;
    }

    @Override
    public long getTimeout() {
        return this.timeout;
    }


    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return true;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public EasyRpcResponse get() throws InterruptedException {
        // 获取的时候就需要等待阻塞 等待响应被唤醒
        latch.await();
        return easyRpcResponse;
    }

    @Override
    public EasyRpcResponse get(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
        // 获取的时候就需要等待阻塞 等待响应被唤醒
        // 等待一定时长还没响应 代表超时
        if (latch.await(timeout, unit)) {
            return easyRpcResponse;
        }
        throw new TimeoutException();
    }
}
