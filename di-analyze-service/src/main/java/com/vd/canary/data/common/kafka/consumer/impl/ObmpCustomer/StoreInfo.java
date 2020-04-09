package com.vd.canary.data.common.kafka.consumer.impl.ObmpCustomer;

import com.alibaba.fastjson.JSON;
import com.vd.canary.data.api.response.es.vo.ShopVo;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author shichaoran
 * @Date 2020/4/9 13:57
 * @Version
 */

public class StoreInfo implements Function {
    private static final Logger logger = LoggerFactory.getLogger(StoreInfo.class);
    /**
     * 通过店铺->coustemer->展位编号
     */
    @Override
    public void performES(String msg) {
        logger.info("StoreInfo.msg"+msg);
        HashMap hashMap = JSON.parseObject(msg, HashMap.class);
        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
        ShopVo shopvo = new ShopVo();
        for (Map.Entry<String, String> entry : entries) {
            logger.info("key={},value={}" + entry.getKey(), entry.getValue());
            shopvo.setId(entry.getKey());
            shopvo.setCustomerId(entry.getKey());
        }
    }
}
