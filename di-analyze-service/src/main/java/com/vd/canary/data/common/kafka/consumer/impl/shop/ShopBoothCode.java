package com.vd.canary.data.common.kafka.consumer.impl.shop;

import com.alibaba.fastjson.JSON;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author shichaoran
 * @Date 2020/4/9 10:53
 * @Version
 */
public class ShopBoothCode implements Function {
    private static final Logger logger = LoggerFactory.getLogger(ShopBoothCode.class);

    @Override
    public void performES(String msg) {
        HashMap hashMap = JSON.parseObject(msg, HashMap.class);
        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            logger.info("key" + entry.getKey());
            logger.info("value" + entry.getValue());

        }
    }

}
