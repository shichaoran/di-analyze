package com.vd.canary.data.common.kafka.consumer.impl.obmpProduct;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTable implements Function {

    private static final Logger logger = LoggerFactory.getLogger(TestTable.class);

    @Override
    public void performES(String msg) {
        logger.info("TestTable.msg"+msg);

        HashMap hashMap = JSON.parseObject(msg, HashMap.class);

        Set<Map.Entry<String,String>> entries = hashMap.entrySet();
        for(Map.Entry<String,String> entry : entries){
            logger.info("key"+entry.getKey());
            logger.info("value"+entry.getValue());

        }

        // updateES(messages);

    }


}
