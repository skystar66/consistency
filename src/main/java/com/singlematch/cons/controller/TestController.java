package com.singlematch.cons.controller;

import com.singlematch.cons.disruptor.provider.CreateTopicEventProducer;
import com.singlematch.cons.util.LocalCache;
import com.singlematch.cons.util.ResultMap;
import com.singlematch.cons.util.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cons")
public class TestController {

    @Autowired
    CreateTopicEventProducer producer;

    @Autowired
    SnowflakeIdWorker snowflakeIdWorker;


    @RequestMapping("/test")
    public String test() {
        Long uid = snowflakeIdWorker.nextId();
        producer.eventHandler(uid, uid);
        return ResultMap.getResult(uid).toString();
    }



    @RequestMapping("/test2")
    public String test2() {
        Long uid = snowflakeIdWorker.nextId();
        producer.eventHandler(uid, uid);
        return "success";
    }


    @RequestMapping("/get")
    public int get() {
        return LocalCache.getCount();
    }


}
