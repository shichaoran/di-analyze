package com.vd.canary.data.common.canal;

import java.util.List;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;

public class DisruptorConfig {

    //定义用来交换的数据类
    public static class Element{
        private List<CanalEntry.Entry> value;

        public List<CanalEntry.Entry> getValue() {
            return value;
        }

        public void setValue(List<CanalEntry.Entry> value) {
            this.value = value;
        }

    }

    //定义工厂
    public static EventFactory<Element> newEventFactory(){
        return Element::new;
    }
    //定义响应事件
    public static class CanalEventHandle implements EventHandler<Element>{
        KafkaService kafkaService;

        public CanalEventHandle(KafkaService kafkaService) {
            this.kafkaService = kafkaService;
        }
        @Override
        public void onEvent(Element element, long l, boolean b) throws Exception {
            kafkaService.sendMessage(element.getValue());
        }
    }

}
