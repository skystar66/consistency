package com.singlematch.cons.disruptor.provider;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.singlematch.cons.disruptor.DisruptorManager;
import com.singlematch.cons.disruptor.MsgEvent;
import com.singlematch.cons.disruptor.MsgEventFactory;
import com.singlematch.cons.disruptor.NameThreadFactory;
import com.singlematch.cons.disruptor.consumer.CreateTopicEventConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CreateTopicEventProducer extends DisruptorManager {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @Autowired
    CreateTopicEventConsumer createTopicEventConsumer;

    @PostConstruct
    public void init() {
        EventFactory<MsgEvent<Long>> eventFactory = new MsgEventFactory<Long>();
        int bufferSize = 1024 * 16;
        disruptor = new Disruptor<MsgEvent<Long>>(eventFactory, bufferSize,
                new NameThreadFactory("Disruptor"));
        createTopicEventConsumer = new CreateTopicEventConsumer();
        disruptor.handleEventsWith(createTopicEventConsumer);
        disruptor.start();
    }

    @Override
    public void eventHandler(Long msg, long sequence) {
        System.out.println(" 流水id：" + msg + " 生产消息数量：" + atomicInteger.getAndIncrement());

        publishEvent(msg);
    }






}
