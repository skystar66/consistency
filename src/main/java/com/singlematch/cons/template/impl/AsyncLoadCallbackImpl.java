package com.singlematch.cons.template.impl;

import com.singlematch.cons.template.AsyncLoadCallback;
import com.singlematch.cons.util.LocalCache;
import org.springframework.stereotype.Service;

@Service
public class AsyncLoadCallbackImpl implements AsyncLoadCallback {

    @Override
    public Object doAsyncLoad() {
        LocalCache.addCount(1);
        return "deal success";
    }
}
