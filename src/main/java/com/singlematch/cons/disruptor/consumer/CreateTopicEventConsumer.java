package com.singlematch.cons.disruptor.consumer;


import com.lmax.disruptor.EventHandler;
import com.singlematch.cons.disruptor.MsgEvent;
import com.singlematch.cons.template.AsyncLoadCallback;
import com.singlematch.cons.template.AsyncLoadTemplate;
import com.singlematch.cons.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * 消费者
 */
@Component
public class CreateTopicEventConsumer implements EventHandler<MsgEvent<Long>> {


    private AtomicInteger atomicInteger = new AtomicInteger(0);

    AsyncLoadTemplate asyncLoadTemplate;

    AsyncLoadCallback asyncLoadCallback;

    @Override
    public void onEvent(MsgEvent<Long> orderMsgMsgEvent, long l, boolean b) throws Exception {
        System.out.println("消费消息数量：" + atomicInteger.getAndIncrement() + ", 消息id" + orderMsgMsgEvent.get());

        if (asyncLoadTemplate == null) {
            asyncLoadTemplate = SpringUtil.getBean(AsyncLoadTemplate.class);
        }

        if (asyncLoadCallback == null) {
            asyncLoadCallback = SpringUtil.getBean(AsyncLoadCallback.class);
        }


        asyncLoadTemplate.execute(asyncLoadCallback, null, orderMsgMsgEvent.get());
    }
}
