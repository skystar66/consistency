package com.singlematch.cons.disruptor;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.singlematch.cons.disruptor.consumer.CreateTopicEventConsumer;

public abstract class DisruptorManager {

    public Disruptor<MsgEvent<Long>> disruptor;

    private CreateTopicEventConsumer createTopicEventConsumer;

    @SuppressWarnings("unchecked")
    public DisruptorManager() {

        EventFactory<MsgEvent<Long>> eventFactory = new MsgEventFactory<Long>();
        int bufferSize = 1024 * 16;
        disruptor = new Disruptor<MsgEvent<Long>>(eventFactory, bufferSize,
                new NameThreadFactory("Disruptor"));
        createTopicEventConsumer = new CreateTopicEventConsumer();
        disruptor.handleEventsWith(createTopicEventConsumer);
        disruptor.start();

    }

    public void publishEvent(Long m) {
        RingBuffer<MsgEvent<Long>> rb = disruptor.getRingBuffer();
        long index = rb.next();
        MsgEvent<Long> event = rb.get(index);
        event.set(m);
        rb.publish(index);
    }


    public abstract void eventHandler(Long msg, long sequence);


}
