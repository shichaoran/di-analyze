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
        log.info("------this is kafka consumer,topic : test,receive:"+message);
        System.out.println("------this is kafka consumer,topic : test,receive:"+System.currentTimeMillis()+"ms");
        log.debug(message);
    }

}
