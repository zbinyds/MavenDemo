package com.zbinyds.asnyc.utils;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 批量异步future对象
 *
 * @author wangxinxing
 * @date 2021/7/9
 */

public class BatchFuture<T> implements Future<T> {
    private T result;

    private boolean isDone = false;
    private final int timeout;

    private final CountDownLatch latch = new CountDownLatch(1);

    BatchFuture(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return this.isDone;
    }

    @Override
    public T get() throws InterruptedException {
        return get(timeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public T get(long timeout, @NotNull TimeUnit unit) throws InterruptedException {
        boolean ret = latch.await(timeout, unit);
        if (!ret) {
            throw new InterruptedException("TIMEOUT");
        }
        return result;
    }

    public T getUninterruptibly() {
        try {
            return get();
        } catch (InterruptedException ignored) {
            return null;
        }
    }


    public void finish(T result) {
        this.result = result;
        this.isDone = true;
        this.latch.countDown();
    }
}
