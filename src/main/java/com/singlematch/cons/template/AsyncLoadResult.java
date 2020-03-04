package com.singlematch.cons.template;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 异步加载返回的proxy result
 *
 * @author xuliang 2020-1-21 下午09:45:14
 */
public class AsyncLoadResult {

    private Class returnClass;
    private Future future;
    private Long timeout;

    public AsyncLoadResult(Class returnClass, Future future, Long timeout) {
        this.returnClass = returnClass;
        this.future = future;
        this.timeout = timeout;
    }


    /**
     * future.get()的返回对象
     *
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public Object loadFuture() {
        try {
            // 使用cglib lazyLoader，避免每次调用future
            if (timeout <= 0) {// <=0处理，不进行超时控制
                return future.get();
            } else {
                return future.get(timeout, TimeUnit.MILLISECONDS);
            }
        } catch (TimeoutException e) {
            future.cancel(true);
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
