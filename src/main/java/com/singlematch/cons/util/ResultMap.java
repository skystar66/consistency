package com.singlematch.cons.util;

import com.singlematch.cons.template.AsyncLoadResult;

import java.util.concurrent.ConcurrentHashMap;

public class ResultMap {

    //设置超时时间 3s
    public static Long timeout = 3000l;

    /**
     * 重试时间
     */
    private static final int DEFAULT_ACQUIRY_RETRY_MILLIS = 100;

    private static ConcurrentHashMap<Long, AsyncLoadResult> resultMap = new ConcurrentHashMap<>();

    public static Object getResult(Long uid) {
        AsyncLoadResult asyncLoadResult = null;
        try {
            int timeOut = 3000; //获取锁的超时时间
            while (timeOut >= 0) {

                asyncLoadResult = resultMap.get(uid);
                if (asyncLoadResult != null) {
                    break;
                }
                timeout -= DEFAULT_ACQUIRY_RETRY_MILLIS;
                //延时 100ms
                Thread.sleep(DEFAULT_ACQUIRY_RETRY_MILLIS);
            }
        } catch (Exception ex) {

            ex.printStackTrace();
        }
        return asyncLoadResult.loadFuture();
    }

    public static void setFuture(Long uid, AsyncLoadResult asyncLoadResult) {
        resultMap.put(uid, asyncLoadResult);
    }


}
