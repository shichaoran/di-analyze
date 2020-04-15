package com.vd.canary.data;

import com.vd.canary.data.common.kafka.producer.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataAnalyzeApplication.class)
public class KafkaTestCase {

    @Autowired
    private KafkaProducer kafka;

    @Test
    public void sendSyncTest() {
        for (int i = 0; i < 1; i++) {
            String message = UUID.randomUUID().toString();
            System.out.println("发送消息:"+i);
            kafka.send("app_log", message);
            System.out.println("发送完成"+System.currentTimeMillis()+"ms");
        }
    }


}
