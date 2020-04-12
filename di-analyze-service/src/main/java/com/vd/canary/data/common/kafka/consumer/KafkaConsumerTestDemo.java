package com.vd.canary.data.common.kafka.consumer;


import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumerTestDemo {

    //@KafkaListener(topics = {"test"})
    @KafkaListener(topics = "test", id = "test_es", containerFactory = "batchFactory",concurrency="3" )
    public void receive(String message){
        log.info("------hello：消费者处理消息------"+message);
        System.out.println("--------------消费完成:"+System.currentTimeMillis()+"ms");
        log.debug(message);
    }

}
