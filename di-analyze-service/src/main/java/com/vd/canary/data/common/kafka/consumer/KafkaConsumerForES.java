package com.vd.canary.data.common.kafka.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.alibaba.fastjson.JSONObject;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.data.common.kafka.consumer.impl.FunctionFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class KafkaConsumerForES {

    //@KafkaListener(topics = {"test"})
    @KafkaListener(topics = "test", id = "test_es", containerFactory = "batchFactory",concurrency="3" )
    public void receive(String message){
        log.info("<------this is kafka consumer,topic = test, receive:"+message);
        log.debug(message);
    }

    /**
     * concurrency="3" 即消费者个数(注意，消费者数要小于等于你开的所有topic的分区数总和)
     * @param list
     */
    @KafkaListener(topics = "binglog_obmp_product_2r3p", id = "product_es", containerFactory = "batchFactory",concurrency="3" )
    public void listenProduct(List<ConsumerRecord<?, ?>> list) {
        log.info("<------this is kafka consumer,topic = binglog_obmp_product_2r3p, list = %s",list);
        List<String> messages = new ArrayList<>();
        for(int i=0;i<list.size();i++)  System.out.println("1111111，list:"+list.get(i));
        for (ConsumerRecord<?, ?> record : list) {
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            // 获取消息
            kafkaMessage.ifPresent(o -> messages.add(o.toString()));
        }
        if (messages.size() > 0) {
            // 通过表名不同分别处理每一条数据，更新索引
            try {
                for(String msg : messages) {
                    log.info("<------this is kafka consumer,topic = binglog_obmp_product_2r3p, msg = %s",msg);
                    JSONObject jsonMap = JSONObject.parseObject(msg);
                    String database = jsonMap.getString("database");
                    String table = jsonMap.getString("table");
                    Function function = FunctionFactory.instance().createFunction(database + "." + table);
                    function.performES(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @KafkaListener(topics = "binglog_obmp_customer_2r3p", id = "customer_es", containerFactory = "batchFactory",concurrency="3" )
    public void listenCustomer(List<ConsumerRecord<?, ?>> list) {
        List<String> messages = new ArrayList<>();
        for(int i=0;i<list.size();i++)  System.out.println("2222222，list:"+list.get(i));
        for (ConsumerRecord<?, ?> record : list) {
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            kafkaMessage.ifPresent(o -> messages.add(o.toString()));// 获取消息
        }
        if (messages.size() > 0) {
            // 通过表名不同分别处理每一条数据，更新索引
            try {
                for(String msg : messages) {
                    log.info("<------this is kafka consumer,topic = binglog_obmp_customer_2r3p, msg = %s",msg);
                    JSONObject jsonMap = JSONObject.parseObject(msg);
                    String database = jsonMap.getString("database");
                    String table = jsonMap.getString("table");
                    Function function = FunctionFactory.instance().createFunction(database + "." + table);
                    function.performES(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
