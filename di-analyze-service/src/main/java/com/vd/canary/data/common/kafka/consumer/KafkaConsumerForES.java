package com.vd.canary.data.common.kafka.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.alibaba.fastjson.JSONObject;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.data.common.kafka.consumer.impl.FunctionFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerForES {

    /**
     * concurrency="3" 即消费者个数(注意，消费者数要小于等于你开的所有topic的分区数总和)
     * @param list
     */
    @KafkaListener(topics = "binglog_product_2r3p", id = "product_es", containerFactory = "batchFactory",concurrency="3" )
    public void listen(List<ConsumerRecord<?, ?>> list) {
        List<String> messages = new ArrayList<>();
        for (ConsumerRecord<?, ?> record : list) {
            System.out.printf("topic = %s, offset = %d, value = %s \n", record.topic(), record.offset(), record.value());
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            // 获取消息
            kafkaMessage.ifPresent(o -> messages.add(o.toString()));
        }
        if (messages.size() > 0) {
            // 通过表名不同分别处理每一条数据，更新索引
            try {
                for(String msg : messages) {
                    JSONObject jsonMap = JSONObject.parseObject(msg);
                    String table = jsonMap.getString("table");
                    Function function = FunctionFactory.instance().createFunction(table);
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
