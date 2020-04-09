package com.vd.canary.data.common.canal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.vd.canary.data.common.kafka.producer.KafkaProducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ConcurrentReferenceHashMap;

/**
 * 业务数据实时通过canalclient写入kafka，topic格式为:binglog_{database}_2r3p;
 */
@Component
public class KafkaService {

    @Autowired
    KafkaProducer kafkaProducer;

    //@Value("${spring.canal.topic-prefix}")
    //private String canalTopicPrefix;

    /**
     * 发送消息
     */
    public void sendMessage(List<CanalEntry.Entry> entrys) {
        for (CanalEntry.Entry entry : entrys) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }

            CanalEntry.RowChange rowChange = null;
            try {
                rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(), e);
            }

            CanalEntry.EventType eventType = rowChange.getEventType();
            String schemaName = entry.getHeader().getSchemaName();
            String tableName = entry.getHeader().getTableName();
            long executeTime = entry.getHeader().getExecuteTime();
            int type = eventType.getNumber();

            //根据binlog的filename和position来定位
            System.out.println(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
                                             entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                                             entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                                             eventType));

            /* 假如发送到kafka的数据为对象 CanalBean 时用下面的for循环体，否则假如为map对象则不用
            for (CanalEntry.RowData rowData : rowChage.getRowDatasList()) {
                CanalBean canalBean = new CanalBean(entry.getHeader().getSchemaName(),entry.getHeader().getTableName(),
                                                    entry.getHeader().getExecuteTime(),eventType.getNumber(),null);
                Map<String, CanalBean.RowData.ColumnEntry> beforeColumns = printColumnToList(rowData.getBeforeColumnsList());
                Map<String, CanalBean.RowData.ColumnEntry> afterColumns = printColumnToList(rowData.getAfterColumnsList());
                canalBean.setRowData(new CanalBean.RowData(beforeColumns,afterColumns));
                //向kafka发送数据
                handlerProducer.sendMessage(canalBean,true);
            }
            */
            for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                Map<String, Object> map = new HashMap<>();
                map.put("database", schemaName);
                map.put("table", tableName);
                map.put("timestamp", executeTime);
                Map<String, Object> map_info = new HashMap<>();

                if (eventType == CanalEntry.EventType.DELETE) {
                    map.put("type", "delete");
                    for(CanalEntry.Column column : rowData.getBeforeColumnsList()){
                        if(column.getValue()!=null&&!column.getValue().equals(""))
                            map_info.put(column.getName(), column.getValue());
                    }
                } else if(eventType == CanalEntry.EventType.INSERT){
                    map.put("type", "insert");
                    for(CanalEntry.Column column : rowData.getAfterColumnsList()){
                        map_info.put(column.getName(), column.getValue());
                    }
                }else {
                    map.put("type", "update");
                    for(CanalEntry.Column column : rowData.getAfterColumnsList()){
                        map_info.put(column.getName(), column.getValue());
                    }

                    Map<String, Object> beforeMap = new HashMap<>();

                    for(CanalEntry.Column column : rowData.getBeforeColumnsList()){
                        if(column.getValue()!=null&&!column.getValue().equals(""))
                            beforeMap.put(column.getName(), column.getValue());
                    }
                    map.put("beforeColumns", beforeMap);
                }
                map.put("info",map_info);
                System.out.println(map);
                kafkaProducer.send( "binglog_" + schemaName + "_2r3p", JSON.toJSONString(map)); // binglog_{database}_2r3p
            }
        }
    }

    /*
     * 假如发送到kafka的数据为对象CanalBean 时 用下面的方法逐个对象赋值，否则假如为map对象则不用下面的这个方法
     */
    protected Map<String, CanalBean.RowData.ColumnEntry> printColumnToList(List<CanalEntry.Column> columns) {
        Map<String, CanalBean.RowData.ColumnEntry> map = new ConcurrentReferenceHashMap<>();
        for (CanalEntry.Column column : columns) {
            StringBuilder builder = new StringBuilder();
            builder.append("name:" + column.getName() + " + isKey:" + column.getIsKey() + " + updated:" + column.getUpdated() + " + isNull:" + column.getIsNull() + " + value:" + column.getValue());
            System.out.println(builder.toString());
            CanalBean.RowData.ColumnEntry columnEntry = new CanalBean.RowData.ColumnEntry(column.getName(),column.getIsKey(),column.getUpdated(),column.getIsNull(),column.getValue());
            map.put(column.getName(), columnEntry);
        }
        return map;
    }


}
