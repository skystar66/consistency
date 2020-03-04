package com.singlematch.cons.template;

/**
 * 对应AyncLoad模板的回调函数
 * 
 * @author xuliang 2020-2-10 下午07:38:10
 */
public interface AsyncLoadCallback<R> {

    public R doAsyncLoad();
}
