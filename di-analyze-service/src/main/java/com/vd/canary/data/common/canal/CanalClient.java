package com.vd.canary.data.common.canal;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.Message;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

/**
 *Canal client，目前没有直接配置Canal Service直接把数据打到kafka，而是通过转换后按固定的topic和固定额msg定制化打到kafka
 */
@Component
@ConfigurationProperties(prefix = "spring.canal")
public class CanalClient implements CommandLineRunner {
//    @Getter
//    @Setter
//    private String ip;
//
//    @Getter
//    @Setter
//    private Integer port;
//
//    @Getter
//    @Setter
//    private String username;
//
//    @Getter
//    @Setter
//    private String password;
//
//    @Getter
//    @Setter
//    private Map<String, String> destination;

    @Value("${spring.canal.ip}")
    private String ip;
    @Value("${spring.canal.port}")
    private int port;
    //@Value("${spring.canal.destination}")
    private Map<String, String> destination = new HashMap<>(){{
        put("example", "0");
    }};

    //@Value("${spring.canal.username}")
    private String username = "";
    //@Value("${spring.canal.password}")
    private String password = "";

    //@Value("${canal.server.batchSize}")
    private int batchSize = 1000;

    @Autowired
    KafkaService kafkaService;

    public void run(String... args) {
        startClient(ip,port,destination,username,password);
    }

    /**
     * 监测数据库变化，使用disruptor处理消息
     */
    public void startClient(String ip, Integer port,Map<String, String> canalDestination,String canalUsername,String canalPassword){
        canalDestination.forEach((database,partition)->{
            //启动不加锁队列
            RingBuffer<DisruptorConfig.Element> buffer = getDisruptor().start();
            new Thread(()->{
                CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(ip,port), database, canalUsername, canalPassword);
                int batchSize = 100;
                try {
                    connector.connect();
                    connector.subscribe();
                    connector.rollback();
                    while (true) {
                        Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                        long batchId = message.getId();
                        int size = message.getEntries().size();
                        if (batchId == -1 || size == 0) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            //向buffer中填充消息
                            long sequence = buffer.next();

                            DisruptorConfig.Element element = buffer.get(sequence);
                            element.setValue(message.getEntries());

                            buffer.publish(sequence);
                        }

                        connector.ack(batchId); // 提交确认
                        // connector.rollback(batchId); // 处理失败, 回滚数据
                    }

                } finally {
                    connector.disconnect();
                }
            }).start();
        });

    }
    // 不加锁消费队列
    private Disruptor<DisruptorConfig.Element> getDisruptor() {
        // 指定RingBuffer的大小  must be power of 2.
        int bufferSize = 1024;
        // 阻塞策略
        BlockingWaitStrategy strategy = new BlockingWaitStrategy();
        // 处理Event的handler，将消息发送出去send
        DisruptorConfig.CanalEventHandle kafkaEventHandler = new DisruptorConfig.CanalEventHandle(kafkaService);
        // 生产者的线程工厂
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        // 事件工厂
        EventFactory<DisruptorConfig.Element> eventFactory = DisruptorConfig.newEventFactory();

        Disruptor<DisruptorConfig.Element> disruptor = new Disruptor<>(eventFactory, bufferSize, threadFactory, ProducerType.SINGLE, strategy);
        disruptor.handleEventsWith(kafkaEventHandler);
        return disruptor;
    }

}
