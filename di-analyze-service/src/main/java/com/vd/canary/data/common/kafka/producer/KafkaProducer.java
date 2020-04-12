package com.vd.canary.data.common.kafka.producer;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/*
 * kafka 生产者
 */
@Service
public class KafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void createTopic(String host,String topic,int partNum,short repeatNum) {
        Properties props = new Properties();
        props.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,host);

        NewTopic newTopic = new NewTopic(topic, partNum, repeatNum);

        AdminClient adminClient = AdminClient.create(props);
        List<NewTopic> topicList = Arrays.asList(newTopic);
        adminClient.createTopics(topicList);

        adminClient.close(10, TimeUnit.SECONDS);
    }

    public void sendSyncHello(String topic, String message) throws InterruptedException, ExecutionException {
        logger.debug("发送信息");
        try {
            kafkaTemplate.send(topic, message).get();
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.debug("消费成功"+System.currentTimeMillis());
    }

    /**
     * 异步调用，不指定固定的分区，不关注结果值
     * @param topic
     * @param msg
     */
    @Async("myExecutor")
    public void send(String topic,String msg){
        //Message message=new Message();
        //message.setId(System.currentTimeMillis());
        //message.setMsg(msg);
        //message.setSendTime(new Date());
        kafkaTemplate.send(topic,msg);
    }

    /**
     * 获取异步方法的结果信息
     * @param waiting   是否等待线程执行完成 true:可以及时看到结果; false:让线程继续执行，并跳出此方法返回调用方主程序;
     */
    public void send(String topic,String msg, boolean waiting) {
        Future<String> f = sendForCustom(topic,msg);
        logger.info("current thread:" + Thread.currentThread().getName() + ",msg:" + msg);
        if(waiting){
            try {
                f.get();
            } catch (InterruptedException e) {
                logger.error("Send kafka message job thread pool await termination time out.", e);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 异步调用，自定义指定固定的分区
     * 1、如果指定了某个分区,会只讲消息发到这个分区上
     * 2、如果同时指定了某个分区和key,则也会将消息发送到指定分区上,key不起作用
     * 3、如果没有指定分区和key,那么将会随机发送到topic的分区中 (int)(Math.random()*5)
     * 4、如果指定了key,那么将会以hash<key>的方式发送到分区中
     */
    @Async("myExecutor")
    public Future<String> sendForCustom(String topic,String msg){
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, 0, null, msg);
        kafkaTemplate.send(record);
        return new AsyncResult<>("send kafka message accomplished!");
    }


}
