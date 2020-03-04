package com.singlematch.cons.util;

import java.util.HashMap;
import java.util.HashSet;

public class LocalCache {


    static HashMap<Thread, Value<Integer>> map = new HashMap<>();


    static HashSet<Value<Integer>> hashSet = new HashSet<>();


    synchronized static void add(Value<Integer> value) {
        map.put(Thread.currentThread(), value);
        hashSet.add(value);
    }

    static ThreadLocal<Value<Integer>> threadLocal = new ThreadLocal<Value<Integer>>() {
        @Override
        protected Value<Integer> initialValue() {
            Value<Integer> value = new Value<Integer>();
            value.set(0);
            add(value);
            return value;
        }
    };


    public static Integer getCount() {
        return map.get(1).get();
    }



    public static Integer getCount2() {
        return hashSet.stream().map(x->x.get()).reduce((a,b)->a+b).get();
    }


    public static void addCount(Integer uid) {
        Value<Integer> value = threadLocal.get();
        value.set(value.get() + 1);
    }


}
