package com.singlematch.cons.template;


import com.singlematch.cons.thread.ExecutorServiceConfig;
import com.singlematch.cons.util.LocalCache;
import com.singlematch.cons.util.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * 基于template模式提供的一套AsyncLoad机制，编程式
 *
 * @author xuliang 2020-2-10 下午07:01:07
 */
@Service
public class AsyncLoadTemplate {

    @Autowired
    ExecutorServiceConfig executorServiceConfig;


    /**
     * 异步执行callback模板,传递config对象
     *
     * @param <R>
     * @param callback
     * @param returnClass
     * @param
     * @return
     */
    public <R> R execute(final AsyncLoadCallback<R> callback, Class<?> returnClass, Long uid) {
        Future<R> future = executorServiceConfig.executorService().submit(new AsyncLoadCallable() {

            @Override
            public R call() throws Exception {

                return callback.doAsyncLoad();
            }

        });
        // 够造一个返回的AsyncLoadResult
        AsyncLoadResult result = new AsyncLoadResult(returnClass, future,
                500l);
        ResultMap.setFuture(uid, result);
        return null;
    }


}
